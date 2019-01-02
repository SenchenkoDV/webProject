<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">

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
    <c:import url="include/header.jsp" charEncoding="utf-8"/>
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
                    <textarea class="text-area" disabled id="area-id" name="description">   Captain Marvel (Mar-Vell) is a fictional superhero appearing in American comic books published by Marvel Comics. The character was created by writer-editor Stan Lee and designed by artist Gene Colan and first appeared in Marvel Super-Heroes #12 (December 1967).

The character debuted during the Silver Age of comic books and has made many appearances since then, including a self-titled series and the second volume of the Marvel Spotlight series. Captain Marvel was ranked 24th in IGN's list of "The Top 50 Avengers"[1] and has appeared in television series and video games.

Jude Law will portray Mar-Vell in the Marvel Studios film Captain Marvel, to be released in 2019.</textarea>
                    <button hidden class="save-button" id="save-id" onclick="disable()">Save</button>
                </form>

            </div>


        </div>
        <div class="comments">
            <div class="comments-wrapper">
                <div class="add-comment">
                    <form action="" id="review" class="review">
                        <input class="star star-5" id="star-10" type="radio" name="star" value="5"/>
                        <label class="star star-5" for="star-10"></label>
                        <input class="star star-5" id="star-9" type="radio" name="star" value="5"/>
                        <label class="star star-5" for="star-9"></label>
                        <input class="star star-5" id="star-8" type="radio" name="star" value="5"/>
                        <label class="star star-5" for="star-8"></label>
                        <input class="star star-5" id="star-7" type="radio" name="star" value="5"/>
                        <label class="star star-5" for="star-7"></label>
                        <input class="star star-5" id="star-6" type="radio" name="star" value="5"/>
                        <label class="star star-5" for="star-6"></label>
                        <input class="star star-5" id="star-5" type="radio" name="star" value="5"/>
                        <label class="star star-5" for="star-5"></label>
                        <input class="star star-4" id="star-4" type="radio" name="star" value="4"/>
                        <label class="star star-4" for="star-4"></label>
                        <input class="star star-3" id="star-3" type="radio" name="star" value="3"/>
                        <label class="star star-3" for="star-3"></label>
                        <input class="star star-2" id="star-2" type="radio" name="star" value="2"/>
                        <label class="star star-2" for="star-2"></label>
                        <input class="star star-1" id="star-1" type="radio" name="star" value="1"/>
                        <label class="star star-1" for="star-1"></label>
                        <div class="rev-box">
                            <textarea class="review" col="30" name="review"></textarea>
                        </div>
                        <input type="submit" class="send-button" value="Send"/>
                    </form>
                </div>
                <div class="latest-comments">
                    <c:forEach var="comment" items="${comments}">
                        <p class="user">${comment.user.login}</p>
                        <p class="mark-text">
                            <c:forEach begin="1" end="3">
                                <i class="fa fa-star" aria-hidden="true"></i>
                            </c:forEach>

                        </p>
                        <output class="comment" name="" value="comment.comment"/>
                    </c:forEach>
                </div>
            </div>
        </div>
    </article>
    <c:import url="include/footer.jsp" charEncoding="utf-8"/>
</div>

</body>
</html>