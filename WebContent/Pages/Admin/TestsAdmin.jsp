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

	<h3><fmt:message key="tests.admin.header"/></h3>
		<p><fmt:message key="tests.admin.message"/></p>

	<div id="error-box" <c:if test="${empty errormessage}"> hidden="true" </c:if> >
		<h4><fmt:message key="register.error.title"/></h4>
		<p id="error-message"><c:if test="${empty errormessage}">${errormessage}</c:if></p>
	</div>

	<button id="createtestbutton"><fmt:message key="tests.admin.button"/></button>

	<form method="POST" id="testform" hidden="true" >
	<table>
		<tr><td><fmt:message key="tests.name"/>:</td>	<td><input type="text" name="name" required></td></tr>
		<tr><td><fmt:message key="tests.descr"/>:</td>	<td><input type="text" name="description" required></td></tr>
		<tr><td><fmt:message key="tests.subj"/>:</td>	<td><input type="text" name="subject" required></td></tr>
		<tr><td><fmt:message key="tests.diff"/>:</td>	<td><select name="difficulty">
															    <option value="1" selected><ttl:difficulty>1</ttl:difficulty></option>
															    <option value="2"><ttl:difficulty>2</ttl:difficulty></option>
															    <option value="3"><ttl:difficulty>3</ttl:difficulty></option>
															    <option value="4"><ttl:difficulty>4</ttl:difficulty></option>
															    <option value="5"><ttl:difficulty>5</ttl:difficulty></option>
															</select></tr>
		<tr><td><fmt:message key="tests.time"/>:</td>	<td><input type="number" name="time" min="1" max="240" required></td></tr>
	</table>
		<input class="submit" type="submit" value="<fmt:message key="tests.admin.button"/>">
		<input type="hidden" name="command" value="createtest">
	</form>

		<table class="styled" id="sortableTable">
		<thead><tr>
	      <th><fmt:message key="tests.name"/></th>
	      <th><fmt:message key="tests.descr"/></th>
	      <th><fmt:message key="tests.subj"/></th>
	      <th><fmt:message key="tests.diff"/></th>
	      <th><fmt:message key="tests.questions"/></th>
	      <th><fmt:message key="tests.time"/></th>
	      <th></th>
	    </tr></thead>

	    <tbody>
	    	<c:forEach items="${tests}" var="t">
			    <tr>
			      <td><a href="Tests/${t.address}/edit">${t.name}</a></td>
			      <td>${t.description}</td>
			      <td>${t.subject}</td>
			      <td><span class="invisible">${t.difficulty}"</span><ttl:difficulty>${t.difficulty}</ttl:difficulty></td>
			      <td>${t.questionCount}</td>
			      <td>${t.time} <fmt:message key="tests.min"/></td>
			      <td><form method="POST" class="delete">
					<input type="submit" value="<fmt:message key="tests.admin.delete"/>">
					<input type="hidden" name="command" value="deletetest">
					<input type="hidden" name="testID" value="${t.id}">
				</form></td>
			    </tr>
	  		</c:forEach>

		</tbody>
		</table>


	</div>
    <%@ include file = "../footer.jsp" %>
</div>

<script>
$(document).ready( function () {
	$('#sortableTable').DataTable({
		info: false,
		paging: false,
		autoWidth: false,
		"order": [],
		"columnDefs": [
		    { "orderable": false, "targets": 6 }
		  ]
	});

	$(".delete").submit(function() {
	    var c = confirm("Confirm deletion");
	    return c;
	});

	$("#createtestbutton").on('click', showTestForm);

	function showTestForm(){
		$("#createtestbutton").hide();
		$("#testform").show(200);
	}

	$("#testform").submit(function() {
		var name = document.forms["testform"]["name"].value;
		var descr = document.forms["testform"]["description"].value;
		var subj = document.forms["testform"]["subject"].value;

		if(name.length>64){
			displayError("Name must not be longer than 64 characters");
	        return false;
		}
		if(descr.length>256){
			displayError("Description must not be longer than 256 characters");
	        return false;
		}
		if(subj.length>64){
			displayError("Subject must not be longer than 64 characters");
	        return false;
		}

		$("#error-box").hide();

		return true;
	});

	function displayError(message){
		$("#error-box").show();
		$("#error-message").text(message);
	}

} );
</script>


</body>

</html>