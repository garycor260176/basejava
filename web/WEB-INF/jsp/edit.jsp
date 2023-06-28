<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <table class="table">
            <tr>
                <td><b>Имя:</b></td>
                <td>
                    <input id="fullName" type="text" style="width:100%" name="fullName" size=50 value="${resume.fullName}"
                           pattern="^[А-Яа-яЁё\s]+$" required>
                </td>
            </tr>

            <tr>
                <td colspan="2"><b>Контакты:</b>
                    <table class="table">
                        <c:forEach var="type" items="<%=ContactType.values()%>">
                            <tr>
                                <td style="white-space: nowrap;">${type.title}</td>
                                <td style="width:100%;">
                                    <input type="text" name="${type.name()}" size=40 value="${resume.getContact(type)}">
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>

            <c:forEach var="sectionType" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.getSection(sectionType)}"/>
                <c:if test="${section != null}">
                    <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
                    <c:choose>
                        <c:when test="${sectionType == 'PERSONAL' || sectionType == 'OBJECTIVE'}">
                            <tr>
                                <td colspan="2">
                                    <h3 style="background-color: #A6C9E2">${sectionType.title}</h3>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <textarea name='${sectionType}' style="width:100%;" rows=3>${section}</textarea>
                                </td>
                            </tr>
                        </c:when>

                        <c:when test="${sectionType == 'ACHIEVEMENT' || sectionType == 'QUALIFICATIONS'}">
                            <c:set var="listSection" value="${section}"/>
                            <jsp:useBean id="listSection" class="com.urise.webapp.model.ListSection"/>
                            <c:if test="${listSection.items != null}">
                                <tr>
                                    <td colspan="2">
                                        <h3 style="background-color: #A6C9E2">${sectionType.title}</h3>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                <textarea name='${sectionType}' style="width:100%;"
                                          rows=4><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
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
                                    <td colspan="2">
                                        <h3 style="background-color: #A6C9E2">${sectionType.title}</h3>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <table class="table">
                                            <c:forEach var="org" items="${organizationSection.items}"
                                                       varStatus="index">
                                                <tr>
                                                    <td colspan="2">
                                                        <table class="table">
                                                            <tr>
                                                                <td><b>Название организации:</b></td>
                                                                <td>
                                                                    <input type="text" name="${sectionType}" size="40"
                                                                           value="${org.website.name}">
                                                                </td>
                                                                <td>Сайт:</td>
                                                                <td>
                                                                    <input type="text" name="${sectionType}_URL"
                                                                           size="40"
                                                                           value="${org.website.url}">
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <c:if test="${org.period != null}">
                                                    <c:forEach var="period" items="${org.period}">
                                                        <tr>
                                                            <td colspan="2">
                                                                <table class="table">
                                                                    <tr>
                                                                        <td style="width:70px; white-space: nowrap;">
                                                                            Период с
                                                                        </td>
                                                                    <td>
                                                                        <input type="date"
                                                                               name="${sectionType}${index.index}_FROM"
                                                                               size=10
                                                                               value="${period.dateFromFormat}">
                                                                    </td>
                                                                        <td style="width:10px;">по</td>
                                                                    <td>
                                                                        <input type="date"
                                                                               name="${sectionType}${index.index}_TO"
                                                                               size=10
                                                                               value="${period.dateToFormat}">
                                                                    </td>
                                                                <td>
                                                                    <c:choose>
                                                                        <c:when test="${sectionType == 'EXPERIENCE'}">
                                                                            Должность:
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            Описание:
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                    <td style="width:100%;">
                                                                        <input type="text" style="width:100%;"
                                                                               name='${sectionType}${index.index}_POS'
                                                                               value="${period.position}">
                                                                    </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                        <c:if test="${sectionType == 'EXPERIENCE'}">
                                                            <tr>
                                                                <td colspan="2">
                                                                    <table class="table">
                                                                        <tr>
                                                                            <td style="width:70px; white-space: nowrap; vertical-align:top;">
                                                                                Описание:
                                                                            </td>
                                                                            <td style="width:100%;">
                                                                <textarea
                                                                        name="${sectionType}${index.index}_DESC"
                                                                        rows=2
                                                                        style="width:100%;">${period.description}</textarea>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:if>
                                                <tr style="height:2px;">
                                                    <td colspan="2" style="border-bottom: solid black 1px;"></td>
                                                </tr>
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

        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.location.href='resume'">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

