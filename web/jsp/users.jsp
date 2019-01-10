<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <div class="locale">
        <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
        <fmt:setLocale value="${language}" scope="session"/>
        <fmt:setBundle basename="property.messages" var="rb" />
    </div>
    <meta charset="UTF-8">
    <title><fmt:message key="title" bundle="${rb}"/></title>
    <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
</head>
<body>
<div class="main-wrap">
    <c:import url="include/header.jsp" charEncoding="utf-8"/>
    <article class="content">
    <table class="users">
        <tr>
            <th class="head-text"><fmt:message key="page.users.headLogin" bundle="${rb}"/></th>
            <th class="head-text"><fmt:message key="page.users.headEmail" bundle="${rb}"/></th>
            <th class="head-text"><fmt:message key="page.users.headRating" bundle="${rb}"/></th>
            <th class="head-text"><fmt:message key="page.users.headRole" bundle="${rb}"/></th>
        </tr>
        <c:forEach var="user" items="${users}">
            <form name="userForm" class="user-form" method="POST" action="../web">
                <input type="hidden" name="command" value="change-rating"/>
                <input type="hidden" name="userId" value="${user.userId}"/>
                <tr>
                    <th><p class="input-field">${user.login}</p>
                    <th><p class="input-field">${user.email}</p>
                    <th><p class="input-field">${user.rating}</p>
                    <th><select type="text" class="input-field" name="role">
                        <option hidden selected="selected">${user.role.role}</option>
                        <option class="input-field" value="1"><fmt:message key="page.users.optionAdmin" bundle="${rb}"/></option>
                        <option class="input-field" value="2"><fmt:message key="page.users.optionUser" bundle="${rb}"/></option>
                        <option class="input-field" value="3"><fmt:message key="page.users.optionBanned" bundle="${rb}"/></option>
                    </select></th>
                    <th><input type="submit" class="submit-button" value="<fmt:message key="page.users.updateButton" bundle="${rb}"/>"/></th>
                </tr>
            </form>
        </c:forEach>
    </table>
    </article>
    <c:import url="include/footer.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>