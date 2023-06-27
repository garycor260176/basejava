package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.model.SectionType.EXPERIENCE;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() throws ServletException {
        storage = Config.get().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume r;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view" -> r = storage.get(uuid);
            case "add" -> {
                r = new Resume("");
                for (SectionType st : SectionType.values()) {
                    switch (st) {
                        case PERSONAL, OBJECTIVE -> r.addSection(st, new TextSection(""));
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            List<String> emptyPoints = new ArrayList<>();
                            r.addSection(st, new ListSection(emptyPoints));
                        }
                        case EXPERIENCE, EDUCATION -> {
                            List<Organization> emptyOrganizations = new ArrayList<>();
                            List<Period> Periods = new ArrayList<>();
                            Periods.add(new Period());
                            emptyOrganizations.add(new Organization(null, Periods));
                            r.addSection(st, new OrganizationSection(emptyOrganizations));
                        }
                    }
                }
            }
            case "edit" -> {
                r = storage.get(uuid);
                for (SectionType st : SectionType.values()) {
                    switch (st) {
                        case PERSONAL, OBJECTIVE -> {
                            TextSection textSection = (TextSection) r.getSection(st);
                            if (textSection == null) {
                                r.addSection(st, new TextSection(""));
                            }
                        }
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            ListSection listSection = (ListSection) r.getSection(st);
                            if (listSection == null) {
                                List<String> emptyItems = new ArrayList<>();
                                r.addSection(st, new ListSection(emptyItems));
                            }
                        }
                        case EDUCATION, EXPERIENCE -> {
                            OrganizationSection section = (OrganizationSection) r.getSection(st);
                            List<Organization> emptyOrganizations = new ArrayList<>();
                            List<Period> emptyPeriods = new ArrayList<>();
                            emptyPeriods.add(new Period());
                            emptyOrganizations.add(new Organization(null, emptyPeriods));
                            if (section != null) {
                                for (Organization org : section.getOrganizations()) {
                                    List<Period> periods = new ArrayList<>();
                                    periods.add(new Period());
                                    periods.addAll(org.getPeriod());
                                    emptyOrganizations.add(new Organization(org.getWebsite(), periods));
                                }
                            }
                            r.setSection(st, new OrganizationSection(emptyOrganizations));
                        }
                    }
                }
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        Resume r = null;
        boolean isNew = true;
        if (!StringUtil.checkEmpty(uuid)) {
            try {
                r = storage.get(uuid);
            } catch (NotExistStorageException ignored) {
            }
            isNew = (r == null);
        }
        if (isNew) {
            r = new Resume(fullName);
        } else {
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (!StringUtil.checkEmpty(value)) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType st : SectionType.values()) {
            String value = request.getParameter(st.name());
            String[] values = request.getParameterValues(st.name());
            String replacedValue = null;
            if (value != null) {
                replacedValue = value.replaceAll("\\r\\n", "§")
                        .replaceAll("§+", "\r\n");
            }
            if (values == null || (StringUtil.checkEmpty(value) && values.length < 2)) {
                r.getSections().remove(st);
            } else {
                switch (st) {
                    case PERSONAL, OBJECTIVE -> r.addSection(st, new TextSection(replacedValue));
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            r.addSection(st, new ListSection(List.of(replacedValue.split("\\r\\n"))));
                    case EDUCATION, EXPERIENCE -> {
                        List<Organization> orgs = new ArrayList<>();
                        String[] urls = request.getParameterValues(st.name() + "_URL");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!StringUtil.checkEmpty(name)) {
                                List<Period> periods = new ArrayList<>();
                                String prefix = st.name() + i;
                                String[] startDates = request.getParameterValues(prefix + "_FROM");
                                String[] endDates = request.getParameterValues(prefix + "_TO");
                                String[] titles = request.getParameterValues(prefix + "_POS");
                                String[] descriptions = request.getParameterValues(prefix + "_DESC");
                                if (titles != null) {
                                    for (int j = 0; j < titles.length; j++) {
                                        if (!StringUtil.checkEmpty(titles[j])) {
                                            periods.add(new Period(DateUtil.parse(startDates[j]),
                                                    DateUtil.parse(endDates[j]), titles[j],
                                                    (st == EXPERIENCE ? descriptions[j] : "")));
                                        }
                                    }
                                }
                                orgs.add(new Organization(new Link(name, urls[i]), periods));
                            }
                        }
                        r.setSection(st, new OrganizationSection(orgs));
                    }
                }
            }
        }
        if (isNew) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }
}
