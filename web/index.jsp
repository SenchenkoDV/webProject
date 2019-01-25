<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
  <head>
    <div class="locale">
      <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
      <fmt:setLocale value="${language}" scope="session"/>
      <fmt:setBundle basename="property.messages" var="rb" />
    </div>
    <style>
      <%@include file="/css/main-style.css"%>
    </style>
  </head>
  <body>
  <c:import url="/jsp/include/header.jsp" charEncoding="utf-8"/>
  <jsp:forward page="/jsp/login.jsp"/>
  <c:import url="/jsp/include/footer.jsp" charEncoding="utf-8"/>
  </body>
</html>
