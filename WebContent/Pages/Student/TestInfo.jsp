<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "ttl" uri = "/WEB-INF/TestingTagLib.tld"%>

<html>
<head>
<%@ include file = "../head-settings.jsp" %>
<title>${test.name}</title>
</head>

<body>
<div id="container">

	<%@ include file = "../header.jsp" %>

    <div id="content">

	<div id="summary-box">
		<h3>${test.name}</h3>
		<table style="text-align: center">
			<tr><td><fmt:message key="testinfo.diff"/>: <ttl:difficulty>${test.difficulty}</ttl:difficulty> </td><td><fmt:message key="testinfo.subj"/>: ${test.subject}</td></tr>
			<tr><td><fmt:message key="testinfo.questions"/>: ${test.questionCount}</td><td><fmt:message key="testinfo.time"/>: ${test.time}</td></tr>
		</table>

		<p>${test.description}</p>
	</div>

	<p><fmt:message key="testinfo.message1"/></p>
	<p><fmt:message key="testinfo.message2"/></p>

	<form method="POST">
		<input class="submit" type="submit" value="<fmt:message key="testinfo.starttest"/>">
		<input type="hidden" name="command" value="begintest">
		<input type="hidden" name="testaddress" value="${test.address}">
	</form>

	</div>
    <%@ include file = "../footer.jsp" %>
</div>

</body>

</html>