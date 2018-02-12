<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<%@ include file = "../head-settings.jsp" %>
<title><fmt:message key="register.title"/></title>
</head>

<body>
<div id="container">

	<%@ include file = "../header.jsp" %>

    <div id="content">

	<h2><fmt:message key="register.header"/></h2>

	<div id="error-box" <c:if test="${empty errormessage}"> hidden="true" </c:if> >
		<h4><fmt:message key="register.error.title"/></h4>
		<p id="error-message"><c:if test="${empty errormessage}">${errormessage}"</c:if> </p>
	</div>


	<form method="POST" name="register" onsubmit="return(checkRegistration())">

	<table>
		<tr><td><fmt:message key="register.login"/>:</td>	<td><input type="text" name="login" required></td></tr>

		<tr><td><fmt:message key="register.password"/>:</td> <td><input type="password" name="pass" required></td></tr>

		<tr><td><fmt:message key="register.password2"/>:</td> <td><input type="password" name="pass2" required></td></tr>

		<tr><td><fmt:message key="register.name"/>:</td> <td><input type="text" name="name" required></td></tr>
	</table>

		<input class="submit" type="submit" value="Sign up">
		<input type="hidden" name="command" value="register">
	</form>


	<p><fmt:message key="register.messagebottom"/></p>

	</div>

    <%@ include file = "../footer.jsp" %>
</div>


<script src="${pageContext.request.contextPath}/js/Scripts/registerValidation.js"></script>

</body>
</html>