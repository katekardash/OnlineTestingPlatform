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
<title><fmt:message key="users.title"/></title>
</head>

<body>
<div id="container">

	<%@ include file = "../header.jsp" %>

    <div id="content">

	<h3><fmt:message key="users.header"/></h3>
		<p><fmt:message key="users.message"/></p>

	<div id="error-box" <c:if test="${empty errormessage}"> hidden="true" </c:if> >
		<h4><fmt:message key="register.error.title"/></h4>
		<p id="error-message"><c:if test="${empty errormessage}">${errormessage}</c:if></p>
	</div>

	<button id="createuserbutton"><fmt:message key="users.button"/></button>

	<form method="POST" name="register" id="userform" hidden="true" >

	<table>
		<tr><td><fmt:message key="register.login"/>:</td>	<td><input type="text" name="login" required></td></tr>

		<tr><td><fmt:message key="register.password"/>:</td> <td><input type="password" name="pass" required></td></tr>

		<tr><td><fmt:message key="register.password2"/>:</td> <td><input type="password" name="pass2" required></td></tr>

		<tr><td><fmt:message key="register.name"/>:</td> <td><input type="text" name="name" required></td></tr>

		<tr><td><fmt:message key="users.role"/>:</td> <td>
				<select name="role">
				    <option value="admin">Admin</option>
				    <option value="student">Student</option>
				 </select>
				</td></tr>
	</table>
		<input class="submit" type="submit" value="<fmt:message key="users.createuser"/>">
		<input type="hidden" name="command" value="createuser">
	</form>

		<table class="styled" id="sortableTable">
		<thead><tr>
	      <th><fmt:message key="users.login"/></th>
	      <th><fmt:message key="users.name"/></th>
	      <th><fmt:message key="users.role"/></th>
	      <th><fmt:message key="users.blocked"/></th>
	      <th><fmt:message key="users.setban"/></th>
	    </tr></thead>

	    <tbody>
	    	<c:forEach items="${users}" var="u">
			    <tr>
			      <td>${u.login}</td>
			      <td>${u.name}</td>
			      <td>${u.role}</td>
			      <td>${u.blocked}</td>
			      <td>
			      <form method="POST">
			      	<c:choose>
						<c:when test="${u.blocked}">
							<input type="submit" value="Unblock">
					  	</c:when>
					  	<c:otherwise>
							<input type="submit" value="Block">
				 		</c:otherwise>
					</c:choose>
						<input type="hidden" name="status" value="${u.blocked}">
						<input type="hidden" name="userID" value="${u.id}">
						<input type="hidden" name="command" value="setblock">
					</form>
				</td>
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
		    { "orderable": false, "targets": 4 }
		  ]
	});

	$("#createuserbutton").on('click', showUserForm);

	function showUserForm(){
		$("#createuserbutton").hide();
		$("#userform").show(200);
	}

	$("#userform").submit(function() {
		var pass1 = document.forms["userform"]["pass"].value;
	    var pass2 = document.forms["userform"]["pass2"].value;
	    var name = document.forms["userform"]["name"].value;
	    var login = document.forms["userform"]["login"].value;

	    if (pass1 != pass2) {
			displayError("Passwords must match");
	        return false;
	    }

		if(name.length>64){
			displayError("Name must not be longer than 64 characters");
	        return false;
		}

		if(login.length>32){
			displayError("Login must not be longer than 32 characters");
	        return false;
		}

		if(pass.length>32){
			displayError("Password must not be longer than 32 characters");
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