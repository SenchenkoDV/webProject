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
    <c:import url="include/header.jsp" charEncoding="utf-8"/>
    <article class="content">
        <div class="for-wrapper">
        <c:forEach var="monster" items="${monsters}">
                <div class="picture">
                    <a class="picture-wrapper" href="../web?command=monster&name=${monster.name}">
                        <div class="image-wrapper">
                            <img class="current-picture" src="${monster.pictureAddress}">
                        </div>
                        <div class="image_description">
                            <p class="monster-name">${monster.name}</p>
                            <p class="monster-race">Race: ${monster.race.race}</p>
                            <p class="average-rating">Average rating: ${monster.averageRating}</p>
                        </div>
                    </a>
                    <a class="edit-monster" href="../web?command=update-monster-page&name=${monster.name}">Edit</a>
                </div>
        </c:forEach>
        </div>
    </article>
    <c:import url="include/footer.jsp" charEncoding="utf-8"/>
</div>

</body>
</html>