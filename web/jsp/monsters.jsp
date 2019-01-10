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
        <div class="for-wrapper">
        <c:forEach var="monster" items="${monsters}">
                <div class="picture">
                    <a class="picture-wrapper" href="../web?command=monster&monsterId=${monster.monsterId}">
                        <div class="image-wrapper">
                            <img class="current-picture" src="${monster.pictureAddress}">
                        </div>
                        <div class="image_description">
                            <p class="monster-name">${monster.name}</p>
                            <p class="monster-race"><fmt:message key="page.monster.race" bundle="${rb}"/> ${monster.race.race}</p>
                            <p class="average-rating"><fmt:message key="page.monster.averageRating" bundle="${rb}"/> <fmt:formatNumber value="${monster.averageRating}" maxFractionDigits="2"/></p>
                        </div>
                    </a>
                    <c:if test="${user.role.roleId==1}">
                        <a class="edit-monster" href="../web?command=update-monster-page&name=${monster.name}"><fmt:message key="page.monster.editButton" bundle="${rb}"/></a>
                    </c:if>
                </div>
        </c:forEach>
        </div>
    </article>
    <c:import url="include/footer.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>