<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <style>
        <%@include file="/css/monster-edit.css"%>
        <%@include file="/css/main-style.css"%>
    </style>
    <meta charset="UTF-8">
    <title>Monsters</title>
    <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
</head>
<body>

<div class="main-wrap">
    <c:import url="include/header.jsp" charEncoding="utf-8"/>
    <article class="content">
        <form enctype="multipart/form-data" class="monster-form" method="POST" action="../upload">
            <input type="hidden" name="command" value="add-monster"/>
            <p class="picture">Picture:</p>
            <input name="content" class="input-field" type="file" required>
            <p class="monster-name">Monster name:</p>
            <input type="text" class="input-field" name="name" value=""/>
            <p class="monster-race">Race:</p>
            <input type="text" class="input-field" name="race" value=""/>
            <p class="monster-description">Description:</p>
            <textarea name="description" class="text-area"></textarea>
            <output>${result}</output>
            <input type="submit" class="submit-button" value="Create"/>
        </form>
    </article>
    <c:import url="include/footer.jsp" charEncoding="utf-8"/>
</div>

</body>
</html>