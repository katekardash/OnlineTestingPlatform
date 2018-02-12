<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page trimDirectiveWhitespaces="true" %>

   <header>
	<nav>
   	<a href="/Testing/Index"><img src="${pageContext.request.contextPath}/css/images/logo.png" alt="Test Center"></a>
		<ul>
		  <li>
			 <div class="dropdown">
				<a href="#"><fmt:message key="header.settings"/></a>
			  <div class="dropdown-content">
			  <c:choose>
				  <c:when test="${sessionScope.role=='admin'}">
				  	<a href="?logout=true"><fmt:message key="header.logout"/></a>
				  </c:when>
				  <c:when test="${sessionScope.role=='student'}">
				  	<a href="?logout=true"><fmt:message key="header.logout"/></a>
				  </c:when>
				</c:choose>
			  	<p><fmt:message key="header.language"/></p>
				<a href="?lang=en"><fmt:message key="header.language.en"/></a>
				<a href="?lang=ru"><fmt:message key="header.language.ru"/></a>
			  	<p><fmt:message key="header.theme"/></p>
				<a href="?theme=light"><fmt:message key="header.theme.light"/></a>
				<a href="?theme=dark"><fmt:message key="header.theme.dark"/></a>
			  </div>
			</div>
		  </li>

		 <c:choose>
		  <c:when test="${sessionScope.role=='admin'}">
		  	<li><a href="/Testing/Users"><fmt:message key="header.users"/></a></li>
		  	<li><a href="/Testing/Tests"><fmt:message key="header.tests"/></a></li>
		  </c:when>
		  <c:when test="${sessionScope.role=='student'}">
		  	<li><a href="/Testing/Journal"><fmt:message key="header.journal"/></a></li>
		  	<li><a href="/Testing/Tests"><fmt:message key="header.tests"/></a></li>
		  </c:when>
		  <c:otherwise>
		  	<li><a href="/Testing/Login"><fmt:message key="header.login"/></a></li>
		  	<li><a href="/Testing/Register"><fmt:message key="header.register"/></a></li>
		  </c:otherwise>
		</c:choose>

		</ul>
	</nav>

</header>