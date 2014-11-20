<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="request" />
<%-- <div class="alarms-container">
	<ul class="alarms-nav">
		<c:forEach items="${applist}" var="item">
			<c:set value="${pageContext.request.contextPath}/${fn:toLowerCase(item.ownerelement)}/${fn:toLowerCase(item.elementvalue)}.html" var="url"></c:set>
			<li><a href="${url}" class="<c:if test="${requestURL eq url}">active</c:if>"><span class="btn-${fn:toLowerCase(item.elementvalue)}"></span>${item.headerdescription}</a></li>
		</c:forEach>
	</ul>
</div> --%>

