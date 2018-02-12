<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix = "ttl" uri = "/WEB-INF/TestingTagLib.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>

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

		<div id="error-box" <c:if test="${empty errormessage}"> hidden="true" </c:if> >
			<h4><fmt:message key="register.error.title"/></h4>
			<p id="error-message"><c:if test="${empty errormessage}">${errormessage}</c:if></p>
		</div>

	<form method="POST" id="testeditform">
		<table>
			<tr><td><fmt:message key="tests.name"/>:</td>	<td><input type="text" name="name" value="${test.name}" required></td></tr>
			<tr><td><fmt:message key="tests.descr"/>:</td>	<td><input type="text" name="description" value="${test.description}" required></td></tr>
			<tr><td><fmt:message key="tests.subj"/>:</td>	<td><input type="text" name="subject" value="${test.subject}" required></td></tr>
			<tr><td><fmt:message key="tests.diff"/>:</td>	<td><select name="difficulty">
																    <option value="1"><ttl:difficulty>1</ttl:difficulty></option>
																    <option value="2"><ttl:difficulty>2</ttl:difficulty></option>
																    <option value="3"><ttl:difficulty>3</ttl:difficulty></option>
																    <option value="4"><ttl:difficulty>4</ttl:difficulty></option>
																    <option value="5"><ttl:difficulty>5</ttl:difficulty></option>
																</select></tr>
			<tr><td><fmt:message key="tests.time"/>:</td>	<td><input type="number" name="time" min="1" max="240" value="${test.time}" required></td></tr>
		</table>
			<input class="submit" type="submit" value="<fmt:message key="testedit.button"/>">
			<input type="hidden" name="command" value="edittestinfo">
			<input type="hidden" name="testID" value="${test.id}">
	</form>
	</div>

	<h2><fmt:message key="testedit.questions"/></h2>
		<button id="newQuestionButton"><fmt:message key="testedit.newquestion"/></button>
		<form class="questionedit" method="POST" id="newQuestionForm" hidden="true">
			<div class="questionbox">
			<span><fmt:message key="testedit.newquestion"/></span>
				<input type="text" name="text" required>
				<input type="submit" class="submit" value="<fmt:message key="testedit.create"/>">
				<input type="hidden" name="command" value="createquestion">
			<input type="hidden" name="testID" value="${test.id}">
			</div>
			</form>

		<c:forEach items="${test.questions}" var="q">
			<div class="questionbox">
			<form class="questionedit" method="POST">
			<span>${q.orderId}.</span>
				<input type="text" name="text" value="${q.text}" required>
				<input type="submit" class="submit" name="edit "value="<fmt:message key="testedit.questionedit"/>">
			<input class="submit" type="submit" name="delete" value="<fmt:message key="tests.admin.delete"/>">
				<input type="hidden" name="command" value="editquestion">
				<input type="hidden" name="questionID" value="${q.id}">
			</form>

		<button class="newAnswerButton" id="newAnswer${q.id}"><fmt:message key="testedit.newanswer"/></button>
		<form class="answerdit" method="POST" id="newAnswerForm${q.id}" hidden="true">
			<span><fmt:message key="testedit.newanswer"/></span>
				<input type="text" name="text" required>
				<input type="checkbox" name="correct" value="correct">
				<input type="submit" class="submit" value="<fmt:message key="testedit.create"/>">
				<input type="hidden" name="command" value="createanswer">
				<input type="hidden" name="questionID" value="${q.id}">
			</form>

			<c:forEach items="${q.answers}" var="a">
				<form class="answeredit" method="POST">
				<span>${a.id}.</span>
					<input type="text" name="text" value="${a.text}" required>
					<input type="checkbox" name="correct" value="correct"
					<c:if test = "${a.correct}">
        				 <c:out value = "checked"/>
      				</c:if>
					>
					<input type="submit" class="submit" name="edit" value="<fmt:message key="testedit.questionedit"/>">
					<input class="submit" type="submit" name="delete" value="<fmt:message key="tests.admin.delete"/>">
					<input type="hidden" name="command" value="editanswer">
					<input type="hidden" name="answerID" value="${a.id}">
				</form>
			</c:forEach>
			</div>

		</c:forEach>

	</div>
    <%@ include file = "../footer.jsp" %>
</div>

<script>
$(document).ready( function () {
	//Sets the default select option
	var defaultDiff = ${test.difficulty};
	$("option[value='" + defaultDiff + "']").attr("selected", "selected");

	$("#testeditform").submit(function() {
		var name = document.forms["testeditform"]["name"].value;
		var descr = document.forms["testeditform"]["description"].value;
		var subj = document.forms["testeditform"]["subject"].value;

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

	$("#newQuestionButton").on('click', showQuestionForm);
	function showQuestionForm(){
		$("#newQuestionButton").hide();
		$("#newQuestionForm").show(200);
	}

	$(".newAnswerButton").on('click', showAnswerForm);
	function showAnswerForm(e){
		var questionNum = e.currentTarget.id.substring(9);
		$('#newAnswer'+questionNum).hide();
		$('#newAnswerForm'+questionNum).show(200);
	}

} );
</script>

</body>

</html>

