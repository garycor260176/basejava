package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Test Resume");

        addContact(resume);
        addTextSections(resume);
        addListSections(resume);
        addOrganizationSections(resume);
        addEducationSections(resume);

        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + resume.getContact(type));
        }
        System.out.println();

        for (SectionType type : SectionType.values()) {
            System.out.println("\n" + type.getTitle() + "\n" + resume.getSection(type));
        }

    }

    private static void addTextSections(Resume resume) {
        resume.addSection(SectionType.OBJECTIVE,
                new TextSection("Ведущий стажировок и корпоративного обучения по " + "Java Web и Enterprise " +
                        "технологиям"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, " +
                "креативность, инициативность. Пурист кода и архитектуры."));
    }

    private static void addOrganizationSections(Resume resume) {
        List<Organization> experienceSections = new ArrayList<>();

        List<Period> PeriodAlcatel = new ArrayList<>();
        PeriodAlcatel.add(new Period(
                LocalDate.of(1997, 9, 1),
                LocalDate.of(2005, 1, 1),
                "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel" +
                        " 1000 S12 (CHILL, ASM)."));

        List<Period> periodSiemensAG = new ArrayList<>();
        periodSiemensAG.add(new Period(
                LocalDate.of(2005, 1, 1),
                LocalDate.of(2007, 2, 1),
                "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и " +
                        "отладка ПО на мобильной " + "IN платформе Siemens @vantage (Java, Unix)."));

        List<Period> periodEnkata = new ArrayList<>();
        periodEnkata.add(new Period(
                LocalDate.of(2007, 3, 1),
                LocalDate.of(2008, 6, 1), "Разработчик ПО",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) " +
                        "частей кластерного J2EE приложения (OLAP, Data mining)."));

        List<Period> periodYota = new ArrayList<>();
        periodYota.add(new Period(
                LocalDate.of(2008, 6, 1),
                LocalDate.of(2010, 12, 1),
                "Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                        "(GlassFish v2.1, v3, OC4J, " + "EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                        "Реализация администрирования, " + "статистики и " + "мониторинга фреймворка. Разработка " +
                        "online JMX клиента (Python/ Jython, Django, " + "ExtJS)"));

        List<Period> periodLuxoft = new ArrayList<>();
        periodLuxoft.add(new Period(
                LocalDate.of(2010, 12, 1),
                LocalDate.of(2012, 4, 1),
                "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, " +
                        "GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                        "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в " +
                        "области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, " +
                        "Commet, HTML5."));

        List<Period> periodRITCenter = new ArrayList<>();
        periodRITCenter.add(new Period(
                LocalDate.of(2012, 4, 1),
                LocalDate.of(2014, 10, 1),
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                        "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                        "конфигурирование" + " системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной " +
                        "части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                        "сервисов общего " + "назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN " +
                        "для online редактирование из браузера документов MS Office. Maven + plugin development," +
                        " Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, " +
                        "Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));

        List<Period> periodWrike = new ArrayList<>();
        periodWrike.add(new Period(
                LocalDate.of(2014, 10, 1),
                LocalDate.of(2016, 1, 1),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, " +
                        "Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));

        List<Period> periodJava = new ArrayList<>();
        periodJava.add(new Period(
                LocalDate.of(2013, 10, 1),
                null,
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."));

        experienceSections.add(new Organization("Alcatel", "http://www.alcatel.ru/", PeriodAlcatel));
        experienceSections.add(new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html",
                periodSiemensAG));
        experienceSections.add(new Organization("Enkata", "http://enkata.com/", periodEnkata));
        experienceSections.add(new Organization("Yota", "https://www.yota.ru/", periodYota));
        experienceSections.add(new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                periodLuxoft));
        experienceSections.add(new Organization("RIT Center", "", periodRITCenter));
        experienceSections.add(new Organization("Wrike", "https://www.wrike.com/", periodWrike));
        experienceSections.add(new Organization("Java Online Projects", "http://javaops.ru/", periodJava));

        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(experienceSections));
    }

    private static void addEducationSections(Resume resume) {
        List<Organization> educationSections = new ArrayList<>();

        List<Period> periodMFTI = new ArrayList<>();
        periodMFTI.add(new Period(
                LocalDate.of(1984, 9, 1),
                LocalDate.of(1987, 6, 1),
                "Закончил с отличием", ""));

        List<Period> periodSP = new ArrayList<>();
        periodSP.add(new Period(
                LocalDate.of(1987, 9, 1),
                LocalDate.of(1993, 7, 1),
                "Инженер (программист Fortran, C)", ""));
        periodSP.add(new Period(
                LocalDate.of(1993, 9, 1),
                LocalDate.of(1996, 7, 1),
                "Аспирантура (программист С, С++)", ""));

        List<Period> periodAlcatel = new ArrayList<>();
        periodAlcatel.add(new Period(
                LocalDate.of(1997, 9, 1),
                LocalDate.of(1998, 3, 1),
                "6 месяцев обучения цифровым телефонным сетям (Москва)", ""));

        List<Period> periodSiemensAG = new ArrayList<>();
        periodSiemensAG.add(new Period(
                LocalDate.of(2005, 1, 1),
                LocalDate.of(2005, 4, 1),
                "3 месяца обучения мобильным IN сетям (Берлин)", ""));

        List<Period> periodLuxoft = new ArrayList<>();
        periodLuxoft.add(new Period(
                LocalDate.of(2011, 3, 1),
                LocalDate.of(2011, 4, 1),
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                ""));

        List<Period> periodCoursera = new ArrayList<>();
        periodCoursera.add(new Period(
                LocalDate.of(2013, 3, 1),
                LocalDate.of(2013, 5, 1),
                "'Functional Programming Principles in Scala' by Martin Odersky", ""));

        educationSections.add(new Organization("Заочная физико-техническая школа при МФТИ",
                "https://mipt.ru/", periodMFTI));
        educationSections.add(new Organization("Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики", "http://www.ifmo.ru/", periodSP));
        educationSections.add(new Organization("Alcatel", "http://www.alcatel.ru/", periodAlcatel));
        educationSections.add(new Organization("Siemens AG", "http://www.siemens.ru/", periodSiemensAG));
        educationSections.add(new Organization("Luxoft",
                "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", periodLuxoft));
        educationSections.add(new Organization("Coursera", "https://www.coursera.org/course/progfun",
                periodCoursera));

        resume.addSection(SectionType.EDUCATION, new OrganizationSection(educationSections));
    }

    private static void addListSections(Resume resume) {
        List<String> achievements = new ArrayList<>();
        List<String> qualifications = new ArrayList<>();

        achievements.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов "
                + "на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для " +
                "комплексных DIY смет");
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное " +
                "взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 " +
                "выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция "
                + "с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " + "Scala" +
                "/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, " +
                "Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии" +
                " через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга " +
                "системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat,"
                + " Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, " +
                "SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, "
                + "Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), " +
                "Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, " +
                "DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, " +
                "OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, "
                + "iReport, OpenCmis, Bonita, pgBouncer");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                "архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achievements));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(qualifications));
    }

    private static void addContact(Resume resume) {
        resume.addContact(ContactType.PHONE_NUMBER, String.valueOf(new TextSection("+7(921) 855-0482")));
        resume.addContact(ContactType.SKYPE, String.valueOf(new TextSection("skype:grigory.kislin")));
        resume.addContact(ContactType.MAIL, String.valueOf(new TextSection("gkislin@yandex.ru")));
        resume.addContact(ContactType.LINKEDIN, String.valueOf(
                new TextSection("https://www.linkedin.com/in/gkislin")));
        resume.addContact(ContactType.GITHUB, String.valueOf(new TextSection("https://github.com/gkislin")));
        resume.addContact(ContactType.STACKOVERFLOW,
                String.valueOf(new TextSection("https://stackoverflow" + ".com" + "/users/548473")));
        resume.addContact(ContactType.HOME_PAGE, String.valueOf(new TextSection("http://gkislin.ru/")));
    }
}
