<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/styles.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <table>
        <c:if test="${resume.contacts.size() > 0}">
            <tr>
                <td>
                    <table>
                        <c:forEach var="contactEntry" items="${resume.contacts}">
                            <tr>
                    <td>
                        <jsp:useBean id="contactEntry"
                                     type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                        <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
                    </td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </c:if>

        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(sectionType)}"/>
            <c:if test="${section != null}">
                <jsp:useBean id="sectionType" type="com.urise.webapp.model.SectionType"/>
                <c:choose>
                    <c:when test="${sectionType == 'PERSONAL' || sectionType == 'OBJECTIVE'}">
                        <tr>
                            <td>
                                <h3>${sectionType.title}</h3>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                    ${section}
                            </td>
                        </tr>
                    </c:when>

                    <c:when test="${sectionType == 'ACHIEVEMENT' || sectionType == 'QUALIFICATIONS'}">
                        <c:set var="listSection" value="${section}"/>
                        <jsp:useBean id="listSection" class="com.urise.webapp.model.ListSection"/>
                        <c:if test="${listSection.items != null}">
                            <tr>
                                <td>
                                    <h3>${sectionType.title}</h3>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <c:forEach var="item" items="${listSection.items}">
                                        <li>${item} </li>
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:if>
                    </c:when>

                    <c:when test="${sectionType == 'EXPERIENCE' || sectionType == 'EDUCATION'}">
                        <c:set var="organizationSection" value="${section}"/>
                        <jsp:useBean id="organizationSection"
                                     class="com.urise.webapp.model.OrganizationSection"/>
                        <c:if test="${organizationSection.items != null}">
                            <tr>
                                <td>
                                    <h3>${sectionType.title}</h3>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table>
                                        <c:forEach var="org" items="${organizationSection.items}">
                                            <tr>
                                                <td colspan="2">
                                                    <c:choose>
                                                        <c:when test="${not empty org.website.url}">
                                                            <a href="${org.website.url}">${org.website.name}</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${org.website.name}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <c:if test="${org.period != null}">
                                                <c:forEach var="period" items="${org.period}">
                                                    <tr>
                                                        <td style="width:200px;">${period.dateFromFormat}
                                                            - ${period.dateToFormat}</td>
                                                        <td><b>${period.position}</b></td>
                                                    </tr>
                                                    <tr>
                                                        <td></td>
                                                        <td>${period.description}</td>
                                                    </tr>
                                                </c:forEach>
                                            </c:if>
                                        </c:forEach>
                                    </table>
                                </td>
                            </tr>
                        </c:if>
                    </c:when>
                </c:choose>
            </c:if>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

