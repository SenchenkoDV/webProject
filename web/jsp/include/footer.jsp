<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<footer class="footer">
    <p class="footer-description"><fmt:message key="footer" bundle="${rb}"/></p>
</footer>
</body>
</html>