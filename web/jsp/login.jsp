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
        <form name="loginForm" class="login-form" method="POST" action="../web">
            <input type="hidden" name="command" value="login"/>
            <p class="login-text">Login:</p>
            <input type="text" class="input-field" name="login" value=""/>
            <p class="password-text">Password:</p>
            <input type="password" class="input-field" name="password" value=""/>
            <a href="../web?command=registration-page" class="registration">Registration</a>
            <br/>
            ${errorLoginPassMessage[0]}
            <br/>
            ${wrongAction}
            <br/>
            ${nullPage}
            <br/>
            <input type="submit" class="submit-button" value="Log in"/>
        </form>
    </article>
    <c:import url="include/footer.jsp" charEncoding="utf-8"/>
</div>

</body>
</html>