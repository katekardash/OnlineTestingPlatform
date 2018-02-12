<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "ttl" uri = "/WEB-INF/TestingTagLib.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page trimDirectiveWhitespaces="true" %>

<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/DataTables/datatables.css">
<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/js/DataTables/datatables.js"></script>

<html>
<head>
<%@ include file = "../head-settings.jsp" %>
<title><fmt:message key="tests.title"/></title>
</head>

<body>
<div id="container">

	<%@ include file = "../header.jsp" %>

    <div id="content">

	<h3><fmt:message key="tests.student.header"/></h3>
		<p><fmt:message key="tests.student.message"/></p>

		<br>

		<table class="styled" id="sortableTable">
		<thead><tr>
	      <th><fmt:message key="tests.name"/></th>
	      <th><fmt:message key="tests.subj"/></th>
	      <th><fmt:message key="tests.diff"/></th>
	      <th><fmt:message key="tests.questions"/></th>
	      <th><fmt:message key="tests.time"/></th>
	    </tr></thead>

	    <tbody>
	    	<c:forEach items="${tests}" var="t">
			    <tr>
			      <td><a href="Tests/${t.address}">${t.name}</a></td>
			      <td>${t.subject}</td>
			      <td><span class="invisible">${t.difficulty}"</span><ttl:difficulty>${t.difficulty}</ttl:difficulty></td>
			      <td>${t.questionCount}</td>
			      <td>${t.time} min</td>
			    </tr>
	  		</c:forEach>

		</tbody>
		</table>


	</div>
    <%@ include file = "../footer.jsp" %>
</div>

<script src="${pageContext.request.contextPath}/js/Scripts/datatableInit.js"></script>

</body>

</html>