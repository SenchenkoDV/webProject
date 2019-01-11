<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">

<html>
<head>
    <div class="locale">
        <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
        <fmt:setLocale value="${language}" scope="session"/>
        <fmt:setBundle basename="property.messages" var="rb" />
    </div>
    <style>
        <%@include file="/css/main-style.css"%>
        <%@include file="/css/monster-page.css"%>
    </style>
    <meta charset="UTF-8">
    <title><fmt:message key="title" bundle="${rb}"/></title>
    <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
    <script><%@include file="/js/ButtonController.js"%></script>
</head>
<body>

<div class="main-wrap">
    <c:import url="include/header.jsp" charEncoding="utf-8"/>
    <article class="content">
        <div class="monster-description">
            <div class="picture">
                <div class="picture-wrapper">
                    <div class="image-wrapper">
                        <img class="current-picture" src="${monster.pictureAddress}">
                    </div>
                    <div class="image_description">
                        <p class="monster-name">${monster.name}</p>
                        <p class="monster-race"><fmt:message key="page.monster.race" bundle="${rb}"/> ${monster.race.race}</p>
                        <p class="average-rating"><fmt:message key="page.monster.averageRating" bundle="${rb}"/> <fmt:formatNumber value="${monster.averageRating}"
                                                                                    maxFractionDigits="2"/></p>
                    </div>
                    <div class="short-description-wrapper">
                        <p class="monster-name"></p>
                    </div>
                </div>
            </div>
            <div class="text-description">
                <c:if test="${user.role.roleId==1||user.role.roleId==2}">
                    <button class="edit-button" id="edit-id" onclick="enable()"><fmt:message key="page.monster.editButton" bundle="${rb}"/></button>
                </c:if>
                <form action="../web" class="description-form" id="registration-form">
                    <input type="hidden" name="command" value="change-monster-description"/>
                    <textarea class="text-area" disabled id="area-id" name="description">
                        ${monster.description}
                    </textarea>
                    <button type="submit" hidden class="save-button" id="save-id" onclick="disable()"><fmt:message key="page.monster.saveButton" bundle="${rb}"/></button>
                </form>
            </div>
        </div>
        <div class="comments">
            <div class="comments-wrapper">
                <c:if test="${user.role.roleId==1||user.role.roleId==2}">
                    <div class="add-comment">
                        <form action="../web" method="POST" id="review" class="review">
                            <input class="star star-5" id="star-10" type="radio" name="star" value="10"/>
                            <label class="star star-5" for="star-10"></label>
                            <input class="star star-5" id="star-9" type="radio" name="star" value="9"/>
                            <label class="star star-5" for="star-9"></label>
                            <input class="star star-5" id="star-8" type="radio" name="star" value="8"/>
                            <label class="star star-5" for="star-8"></label>
                            <input class="star star-5" id="star-7" type="radio" name="star" value="7"/>
                            <label class="star star-5" for="star-7"></label>
                            <input class="star star-5" id="star-6" type="radio" name="star" value="6"/>
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
                                <textarea class="review" col="30" name="comment"></textarea>
                            </div>
                            <input type="hidden" name="monsterId" value="${monster.monsterId}">
                            <input type="hidden" name="command" value="add-comment"/>
                            <input type="submit" class="send-button" value="<fmt:message key="page.monster.sendButton" bundle="${rb}"/>"/>
                        </form>
                    </div>
                </c:if>
                <div class="latest-comments">
                    <c:forEach var="comment" items="${comments}">
                        <div class="comment-wrapper">
                            <p class="user">${comment.user.login}</p>
                            <p class="mark-text">
                                <c:forEach begin="1" end="${comment.mark}">
                                    <i class="fa fa-star" aria-hidden="true"></i>
                                </c:forEach>
                            </p>
                            <p class="comment">${comment.comment}</p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </article>
    <c:import url="include/footer.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>