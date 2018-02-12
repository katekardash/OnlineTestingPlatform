<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page trimDirectiveWhitespaces="true" %>

<html>
<head>
<%@ include file = "../head-settings.jsp" %>
<title><fmt:message key="errorpage.title"/></title>
</head>

<body>
<div id="container">

	<%@ include file = "../header.jsp" %>

    <div id="content">

	<h2><fmt:message key="errorpage.header"/></h2>
    <div id="error-box">
			<p>
			<c:choose>
			  <c:when test="${not empty errormessage}">
			  ${errormessage}
			  </c:when>
			  <c:otherwise>
			  	<fmt:message key="errorpage.defmessage"/>
			  </c:otherwise>
			</c:choose>
			</p>
	</div>

    <%@ include file = "../footer.jsp" %>

	</div>
</div>


</body>
</html>