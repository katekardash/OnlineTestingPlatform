<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	<h1><fmt:message key="index.welcome"/>, ${sessionScope.name} !</h1>

	<img src="css/images/students.jpg">

	<br>

	<p>
		<fmt:message key="index.student.message"/>
	</p>

	<h2><fmt:message key="index.student.header"/>:</h2>
	<table class="styled">
	<thead><tr>
	      <th><fmt:message key="tests.name"/></th>
	      <th><fmt:message key="tests.descr"/></th>
	      <th><fmt:message key="tests.diff"/></th>
	      <th><fmt:message key="tests.time"/></th>
	    </tr></thead>

	    <tbody>
		    <tr><td>Mathematics 101</td><td>A test on basics of high school mathematics and entry calculus, designed for graduating high school students.</td><td><fmt:message key="diff.easy"/></td><td>30</td></tr>
		    <tr><td>Java Basics</td><td>Basic Java test. Tests understanding on syntaxis, some entry-level programming knowlege and general theory.</td><td><fmt:message key="diff.veasy"/></td><td>20</td></tr>
		    <tr><td>Biology 101</td><td>Entry-level biology test designed for high-school graduates who plan on continuing education in fields of biology.</td><td><fmt:message key="diff.hard"/></td><td>45</td></tr>
		</tbody>
	</table>

	<br>
	<p><fmt:message key="index.student.messagebottom"/></p>
	<br>

	</div>

    <%@ include file = "../footer.jsp" %>
</div>


</body>

</html>