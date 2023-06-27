<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/styles.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

<section>
    <a href="resume?action=add"><img src="img/add.png"><span>Добавить резюме</span></a>
    <hr/>

    <table class="table">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th style="width:100px;">Удалить</th>
            <th style="width:100px;">Изменить</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.MAIL.toHtml(resume.getContact(ContactType.MAIL))%>
                </td>
                <td style="text-align: center;"><a href="resume?uuid=${resume.uuid}&action=delete"><img
                        src="img/delete.png"></a></td>
                <td style="text-align: center;"><a href="resume?uuid=${resume.uuid}&action=edit"><img
                        src="img/pencil.png"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
