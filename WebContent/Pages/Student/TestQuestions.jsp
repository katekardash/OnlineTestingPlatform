<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>

<title>${test.name}</title>
<%@ include file = "../head-settings.jsp" %>
</head>

<body>
<div id="container">

	<%@ include file = "../header.jsp" %>
    <div id="content">

	<h1>${test.name}</h1>

		<div id="questions-sidebar">
		<p><fmt:message key="testquestions.questions"/><p>
			<div id="sidebar-buttons">
			<c:forEach items="${test.questions}" var="q">
				<button id="navb${q.orderId}">${q.orderId}</button>
			</c:forEach>
			</div>
		<p id="timer">${test.time}:00<p>
		<form method="POST" id="finishform">
			<input type="submit" value="Finish">
			<input type="hidden" name="command" value="finishtest">
			<input type="hidden" name="timetaken" id="timetaken" value="0">
		</form>

		</div>

		<c:forEach items="${test.questions}" var="q">
			<div id="qBox${q.orderId}" class="question-box">
			<p>${q.orderId}. ${q.text}</p>

			<c:forEach items="${q.answers}" var="a">
				<label><input type="checkbox" name="${q.orderId}" value="${a.id}">${a.text}</label><br>
			</c:forEach>

			<div class="boxbuttons">
				<button id="pButton" style="float: left"><fmt:message key="testquestions.back"/></button>
				<button id="qButton${q.orderId}"><fmt:message key="testquestions.confirm"/></button>
				<button id="bButton" style="float: right"><fmt:message key="testquestions.next"/></button>
			</div>

			</div>
		</c:forEach>


	</div>

    <%@ include file = "../footer.jsp" %>

</div>

<script>
	var maxQuestion=${test.questionCount};
	var currentQuestion=1;
	var timeLeft=${timeleft};
	var timeTotal=${test.time};
</script>
<script src="${pageContext.request.contextPath}/js/Scripts/testQuestions.js"></script>

</body>

</html>