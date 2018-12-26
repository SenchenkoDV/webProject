<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <style>
        <%@include file="../css/style.css"%>
    </style>
    <meta charset="UTF-8">
    <title>Monsters</title>
    <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
    <script>
        function disable() {
            document.getElementById("name").disabled = true;
        }

        function enable() {
            document.getElementById("area-id").disabled = false;
            document.getElementById("save-id").hidden = false;
            document.getElementById("edit-id").hidden = true;
        }
    </script>


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
        <div class="monster-description">
            <div class="picture">
                <div class="picture-wrapper">
                    <div class="image-wrapper">
                        <img class="current-picture" src="/images/heroes/250px-Captain_Marvel_29.jpg">
                    </div>
                    <div class="image_description">
                        <p class="monster-name">Captain Marvel</p>
                        <p class="monster-race">Race: Kree</p>
                        <p class="average-rating">Average rating: 8.7</p>
                    </div>
                    <div class="short-description-wrapper">
                        <p class="monster-name"></p>
                    </div>
                </div>
            </div>
            <div class="text-description">
                <button class="edit-button" id="edit-id" onclick="enable()">Edit</button>
                <form class="description-form" id="registration-form">
                    <textarea class="text-area" disabled id="area-id">   Captain Marvel (Mar-Vell) is a fictional superhero appearing in American comic books published by Marvel Comics. The character was created by writer-editor Stan Lee and designed by artist Gene Colan and first appeared in Marvel Super-Heroes #12 (December 1967).

The character debuted during the Silver Age of comic books and has made many appearances since then, including a self-titled series and the second volume of the Marvel Spotlight series. Captain Marvel was ranked 24th in IGN's list of "The Top 50 Avengers"[1] and has appeared in television series and video games.

Jude Law will portray Mar-Vell in the Marvel Studios film Captain Marvel, to be released in 2019.</textarea>
                    <button hidden class="save-button" id="save-id" onclick="disable()">Save</button>
                </form>

            </div>


        </div>
        <div class="comments">
            <div class="comments-wrapper">
                <form class="add-comment">
                    ${pageContext.request.contextPath}
                </form>
                <div class="latest-comments">

                </div>
            </div>
        </div>
    </article>
    <footer class="footer">
        <p class="footer-description">HTP & EPAM Systems - 2019, Denis Senchenko, Monsters</p>
    </footer>
</div>

</body>
</html>