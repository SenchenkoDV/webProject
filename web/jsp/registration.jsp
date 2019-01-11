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
    <style>
        <%@include file="/css/main-style.css"%>
        <%@include file="/css/login.css"%>
    </style>
    <meta charset="UTF-8">
    <title><fmt:message key="title" bundle="${rb}"/></title>
    <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
</head>
<body>

<div class="main-wrap">
    <c:import url="include/header.jsp" charEncoding="utf-8"/>
    <article class="content">
        <form name="loginForm" class="login-form" method="POST" action="../web">
            <input type="hidden" name="command" value="registration"/>
            <p class="login-text"><fmt:message key="form.login.login" bundle="${rb}"/></p>
            <input type="text" class="input-field" name="login" value=""/>
            <p class="password-text"><fmt:message key="form.login.password" bundle="${rb}"/></p>
            <input type="password" class="input-field" name="password" value=""/>
            <p class="email-text"><fmt:message key="form.registration.email" bundle="${rb}"/></p>
            <input type="text" class="input-field" name="password" value=""/>
            <br/>
            ${requirements}
            <input type="submit" class="submit-button" value="<fmt:message key="form.registration.buttonSignUp" bundle="${rb}"/>"/>
        </form>
    </article>
    <c:import url="include/footer.jsp" charEncoding="utf-8"/>
</div>

</body>
</html>