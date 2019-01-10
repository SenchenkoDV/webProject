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
        <%@include file="/css/monster-edit.css"%>
        <%@include file="/css/main-style.css"%>
    </style>
    <meta charset="UTF-8">
    <title><fmt:message key="title" bundle="${rb}"/></title>
    <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
</head>
<body>
<div class="main-wrap">
    <c:import url="include/header.jsp" charEncoding="utf-8"/>
    <article class="content">
        <form enctype="multipart/form-data" class="monster-form" method="POST" action="../upload">
            <input type="hidden" name="command" value="add-monster"/>
            <p class="picture"><fmt:message key="form.addMonster.picture" bundle="${rb}"/></p>
            <input name="content" class="input-field" type="file" required>
            <p class="monster-name"><fmt:message key="form.addMonster.monsterName" bundle="${rb}"/></p>
            <input type="text" class="input-field" name="name" value=""/>
            <p class="monster-race"><fmt:message key="form.addMonster.monsterRace" bundle="${rb}"/></p>
            <input type="text" class="input-field" name="race" value=""/>
            <p class="monster-description"><fmt:message key="form.addMonster.monsterDescription" bundle="${rb}"/></p>
            <textarea name="description" class="text-area"></textarea>
            <output>${result}</output>
            <input type="submit" class="submit-button"
                   value="<fmt:message key="form.addMonster.createButton" bundle="${rb}"/>"/>
        </form>
    </article>
    <c:import url="include/footer.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>