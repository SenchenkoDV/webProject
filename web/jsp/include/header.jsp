<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
</head>
<body>
<header class="header">
    <a href="/../web?command=monsters" class="header-button">MONSTERS</a>
    <c:if test="${user.role.roleId==1}">
        <a href="/../web?command=add-monster-page" class="header-button">ADD MONSTER</a>
        <a href="/../web?command=display-users" class="header-button">USERS</a>
    </c:if>
    <c:choose>
        <c:when test="${user==null}"><a href="/../web?command=login-page" class="header-button">LOG IN</a></c:when>
        <c:otherwise><a href="../web?command=logout" class="header-button">LOGOUT</a></c:otherwise>
    </c:choose>
    <div class="user">${user.login}</div>
</header>
</body>
</html>
