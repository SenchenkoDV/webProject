<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <style>
        <%@include file="/css/main-style.css"%>
        <%@include file="/css/users.css"%>
    </style>
    <meta charset="UTF-8">
    <title>Monsters</title>
    <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
</head>
<body>
<div class="main-wrap">
    <c:import url="include/header.jsp" charEncoding="utf-8"/>
    <article class="content">
    <table class="users">
        <tr>
            <th class="head-text">login</th>
            <th class="head-text">email</th>
            <th class="head-text">rating</th>
            <th class="head-text">role</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <form name="userForm" class="user-form" method="POST" action="../web">
                <input type="hidden" name="command" value="change-rating"/>
                <input type="hidden" name="userId" value="${user.userId}"/>
                <tr>
                    <th><p class="input-field">${user.login}</p>
                    <th><p class="input-field">${user.email}</p>
                    <th><p class="input-field">${user.rating}</p>
                    <th><select type="text" class="input-field" name="role">
                        <option hidden selected="selected">${user.role.role}</option>
                        <option class="input-field" value="1">admin</option>
                        <option class="input-field" value="2">user</option>
                        <option class="input-field" value="3">banned</option>
                    </select></th>
                    <th><input type="submit" class="submit-button" value="Update"/></th>
                </tr>
            </form>
        </c:forEach>
    </table>
    </article>
    <c:import url="include/footer.jsp" charEncoding="utf-8"/>
</div>
</body>
</html>