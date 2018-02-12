<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page trimDirectiveWhitespaces="true" %>

<html>
<head>
<%@ include file = "../head-settings.jsp" %>
<title><fmt:message key="login.title"/></title>
</head>

<body>
<div id="container">

	<%@ include file = "../header.jsp" %>

    <div id="content">

	<h2><fmt:message key="login.header"/></h2>

	<c:if test="${not empty errormessage}">
    	<div id="error-box">
			<h4>Error</h4>
			<p><c:out value="${errormessage}"/></p>
		</div>
	</c:if>

	<form method="POST">
		<input type="text" placeholder="Login" name="login" required>
		<br>
		<input type="password" placeholder="Password" name="pass" required>
		<br>
		<input class="submit" type="submit" value="Log in">
		<input type="hidden" name="command" value="login">
	</form>


	<p><fmt:message key="login.messagebottom"/></p>
	</div>

    <%@ include file = "../footer.jsp" %>
</div>


</body>

</html>