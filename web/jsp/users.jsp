<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <style>
        <%@include file="../css/login-style.css"%>
        <%@include file="../css/users.css"%>
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
            <th>login</th>
            <th>email</th>
            <th>rating</th>
            <th>role</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <form name="userForm" class="user-form" method="POST" action="../web">
                <input type="hidden" name="command" value="change-rating"/>
                <input type="hidden" name="userId" value="${user.userId}"/>
                <tr>
                    <th><p>${user.login}</p>
                    <th><p>${user.email}</p>
                    <th><p>${user.rating}</p>
                    <th><select type="text" class="input-field" name="role">
                        <option hidden selected="selected">${user.role.role}</option>
                        <option value="1">admin</option>
                        <option value="2">user</option>
                        <option value="3">banned</option>
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