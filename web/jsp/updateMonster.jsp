<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <style>
        <%@include file="../css/login-style.css"%>
    </style>
    <meta charset="UTF-8">
    <title>Monsters</title>
    <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
</head>
<body>

<div class="main-wrap">
    <c:import url="include/header.jsp" charEncoding="utf-8"/>
    <article class="content">
        <form enctype="multipart/form-data" class="login-form" method="POST" action="../upload">
            <input type="hidden" name="command" value="update-monster"/>
            <input type="hidden" name="monsterId" value="${monster.monsterId}"/>
            <p class="login-text">Picture:</p>
            <input name="content" type="file" required>
            <p class="login-text">Monster name:</p>
            <input type="text" class="input-field" name="name" value="${monster.name}"/>
            <p class="password-text">Race:</p>
            <input type="password" class="input-field" name="race" value="${monster.race.race}"/>
            <p class="password-text">Description:</p>
            <textarea name="description">${monster.description}</textarea>
            <output>${result}</output>
            <input type="submit" class="submit-button" value="Update"/>
        </form>
    </article>
    <c:import url="include/footer.jsp" charEncoding="utf-8"/>
</div>

</body>
</html>