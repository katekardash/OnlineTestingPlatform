<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix ="ttl" uri = "/WEB-INF/TestingTagLib.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/DataTables/datatables.css">
<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/js/DataTables/datatables.js"></script>

<html>
<head>
<%@ include file = "../head-settings.jsp" %>
<title><fmt:message key="journal.title"/></title>
</head>

<body>
<div id="container">

	<%@ include file = "../header.jsp" %>

    <div id="content">

	<h2><fmt:message key="journal.title"/></h2>

	<p><fmt:message key="index.welcome"/>, ${name} (${login}). <fmt:message key="journal.message"/></p>

		<table class="styled" id="sortableTable">
		<thead><tr>
	      <th><fmt:message key="journal.name"/></th>
	      <th><fmt:message key="journal.grade"/></th>
	      <th><fmt:message key="journal.average"/></th>
	      <th><fmt:message key="journal.time"/></th>
	      <th><fmt:message key="journal.date"/></th>
	    </tr></thead>

	    <tbody>
	    	<c:forEach items="${testResults}" var="tr">
			    <tr>
					<td>${tr.testName}</td>
					<td>${tr.grade}</td>
					<td>${tr.average}</td>
					<td><ttl:timeTaken>${tr.timeElapsed}</ttl:timeTaken></td>
					<td><fmt:formatDate value="${tr.dateTaken}" pattern="MM/dd/yyyy HH:mm"/></td>
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