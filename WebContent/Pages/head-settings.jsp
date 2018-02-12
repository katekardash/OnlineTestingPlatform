<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page trimDirectiveWhitespaces="true" %>


<c:choose>
	<c:when test="${not empty language}">
		<fmt:setLocale value="${language}"/>
	 </c:when>
	<c:otherwise>
		<fmt:setLocale value="ru"/>
	</c:otherwise>
</c:choose>
<fmt:setBundle basename="resources/resources"/>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/css/images/logo-small.png">
<c:choose>
	<c:when test="${sessionScope.theme=='dark'}">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style-dark.css">
	 </c:when>
	<c:otherwise>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
	</c:otherwise>
</c:choose>
