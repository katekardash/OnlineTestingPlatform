<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page trimDirectiveWhitespaces="true" %>

<html>
<head>
<%@ include file = "../head-settings.jsp" %>
<title>Test Center</title>
</head>

<body>
<div id="container">

	<%@ include file = "../header.jsp" %>

    <div id="content">

	<h1><fmt:message key="index.welcome"/>!</h1>

	<img src="css/images/students.jpg">

	<p><fmt:message key="index.guest.message"/>
	</p>

	<h2><fmt:message key="index.guest.sampleusers"/>:</h2>
	<table class="styled">
	<thead><tr>
	      <th><fmt:message key="users.login"/></th>
	      <th><fmt:message key="users.password"/></th>
	      <th><fmt:message key="users.name"/></th>
	      <th><fmt:message key="users.role"/></th>
	    </tr></thead>

	    <tbody>
		    <tr><td>bigboss</td><td>sudologin</td><td>Admin McBoss</td><td><fmt:message key="role.admin"/></td></tr>
		    <tr><td>john1</td><td>hunter77</td><td>John Doe</td><td><fmt:message key="role.student"/></td></tr>
		    <tr><td>maryanne</td><td>kittens</td><td>Mary Anne Collins</td><td><fmt:message key="role.student"/></td></tr>
		</tbody>
	</table>

	<br>
	<p><fmt:message key="index.guest.messagebottom"/></p>

	</div>

    <%@ include file = "../footer.jsp" %>
</div>


</body>

</html>