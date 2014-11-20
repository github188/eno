<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<spring:url value="/alarms/actives.html?pageNumber=1&pageSize=5" var="realtimeAlarm"></spring:url>
<spring:url value="/alarms/logs.html?pageNumber=1&pageSize=5" var="historyAlarm"></spring:url>
<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="request" />
<c:if test="${fn:contains(requestURL,'/alarms/actives.html')}">
	<c:set var="clsRealtimeActive" value="active" />
</c:if>
<c:if test="${fn:contains(requestURL,'/alarms/logs.html')}">
	<c:set var="clsHistoryActive" value="active" />
</c:if>
<div class="span10">
	<div class="alarms-container">
		<ul class="alarms-nav">
			<c:forEach items="${applist}" var="item" >
				<c:set value="${pageContext.request.contextPath}/${fn:toLowerCase(item.ownerelement)}/${fn:toLowerCase(item.elementvalue)}" var="url"></c:set>
				<c:set value="${pageContext.request.contextPath}/${fn:toLowerCase(item.ownerelement)}/${fn:toLowerCase(item.elementvalue)}.html" var="htmlUrl"></c:set>
				<c:set value="${pageContext.request.contextPath}/${fn:toLowerCase(item.ownerelement)}/${fn:toLowerCase(item.elementvalue)}find" var="selecturl"></c:set>
				<li><a href="${url}" <c:choose><c:when test="${requestURL eq htmlUrl}">class="active" </c:when><c:when test="${requestURL eq url}">class="active" </c:when><c:when test="${fn:contains(requestURL,selecturl)}">class="active" </c:when></c:choose> ><span class="btn-${fn:toLowerCase(item.elementvalue)}"></span>${item.headerdescription}</a></li>
			</c:forEach>
		</ul>
	</div>
</div>
