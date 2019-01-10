<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
    <div class="locale">
        <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
        <fmt:setLocale value="${language}" scope="session"/>
        <fmt:setBundle basename="property.messages" var="rb" />
    </div>
    <style>
        <%@include file="/css/main-style.css"%>
    </style>
</head>
<body>
<header class="header">
    <a href="/../web?command=monsters" class="header-button"><fmt:message key="header.monsters" bundle="${rb}"/></a>
    <c:if test="${user.role.roleId==1}">
        <a href="/../web?command=add-monster-page" class="header-button"><fmt:message key="header.addMonster" bundle="${rb}"/></a>
        <a href="/../web?command=display-users" class="header-button"><fmt:message key="header.users" bundle="${rb}"/></a>
    </c:if>
    <c:choose>
        <c:when test="${user==null}"><a href="/../web?command=login-page" class="header-button"><fmt:message key="header.login" bundle="${rb}"/></a></c:when>
        <c:otherwise><a href="../web?command=logout" class="header-button"><fmt:message key="header.logout" bundle="${rb}"/><output class="user-head">(${user.login})</output></a></c:otherwise>
    </c:choose>
</header>
</body>
</html>
