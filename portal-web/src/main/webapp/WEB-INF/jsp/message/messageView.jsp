<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <html lang="en">
 <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main.css">
 <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/easyui.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/jquery.easyui.min.js"></script>
<div class="ftitle">
	<a href="messageList" class="btn btn-large btn-primary">«返回</a> 
	<c:if test="${ZT eq '0'}"><a href="replyMessage?messageId=${message.messageId}" class="btn btn-large btn-primary ">回复</a> </c:if>
	<c:if test="${ZT eq '1'}"><a href="resendMessage?messageId=${message.messageId}" class="btn btn-large btn-primary ">重发</a> </c:if>
	<c:if test="${ZT eq '2'}"><a href="updateMessage?messageId=${message.messageId}" class="btn btn-large btn-primary ">编辑发送</a> </c:if>
	<c:if test="${ZT eq '3'}"><a href="resumeMessage?messageId=${message.messageId}" class="btn btn-large btn-primary ">恢复</a> </c:if>
</div>
<div class="article-layout" id="message" style="width: 80%; padding-top: 20px" >
	<div class="zt" align="center"><h4> 主题：${message.title}&nbsp&nbsp&nbsp&nbsp
	                                                                                                                               时间：${sendTime}&nbsp&nbsp&nbsp&nbsp
	                                                                                                                                发件人：${message.sender}&nbsp&nbsp&nbsp&nbsp
	                                                                                                                                 收件人：${message.incept}</h4></div><br>
	<div class="nr" style="padding-left: 30px">&nbsp&nbsp&nbsp&nbsp${message.content}</div>
</div>
</html>
   
     