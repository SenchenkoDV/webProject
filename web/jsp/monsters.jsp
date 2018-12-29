<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <style>
        <%@include file="../css/monster-style.css"%>
    </style>
    <meta charset="UTF-8">
    <title>Monsters</title>
    <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
</head>
<body>

<div class="main-wrap">
    <header class="header">
        <a href="../web?command=monsters" class="header-button">MONSTERS</a>
        <a href="" class="header-button">USERS</a>
        <c:choose>
            <c:when test="${user[0]==null}"><a href="../web?command=login" class="header-button">LOG IN</a></c:when>
            <c:otherwise><a href="../web?command=logout" class="header-button">LOGOUT</a></c:otherwise>
        </c:choose>
        <div class="user">${user[0]}</div>
    </header>
    <article class="content">
        <div class="for-wrapper">
        <c:forEach var="monster" items="${monsters}">
                <div class="picture">
                    <div class="picture-wrapper">
                        <div class="image-wrapper">
                            <img class="current-picture" src="../images/heroes/250px-Captain_Marvel_29.jpg">
                        </div>
                        <div class="image_description">
                            <p class="monster-name">${monster.name}</p>
                            <p class="monster-race">Race: ${monster.race.race}</p>
                            <p class="average-rating">Average rating: ${monster.averageRating}</p>
                        </div>
                        <div class="short-description-wrapper">
                            <p class="monster-name"></p>
                        </div>
                    </div>
                </div>
        </c:forEach>
        </div>
    </article>
    <footer class="footer">
        <p class="footer-description">HTP & EPAM Systems - 2019, Denis Senchenko, Monsters</p>
    </footer>
</div>

</body>
</html>