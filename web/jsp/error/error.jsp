<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <style>
        <%@include file="/css/main-style.css"%>
        <%@include file="/css/error.css"%>
    </style>
    <meta charset="UTF-8">
    <title>Error Page</title>
</head>
<body>

<div class="main-wrap">
    <c:import url="../include/header.jsp" charEncoding="utf-8"/>
    <article class="content">
        <div class="error-wrapper">
            ${result}
        </div>
    </article>
    <c:import url="../include/footer.jsp" charEncoding="utf-8"/>
</div>

</body>
</html>
