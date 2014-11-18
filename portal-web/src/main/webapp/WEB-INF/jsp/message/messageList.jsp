<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <html lang="en">
 <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>
<style>
 img{display:inline;}
</style>	
<div style="margin-top: 10px;" align="center">
	<img src="${pageContext.request.contextPath}/resources/images/messageBox.png" />&nbsp&nbsp我的短信
	&nbsp<img src="${pageContext.request.contextPath}/resources/images/messageWrite.png" />&nbsp&nbsp<a
		href="messageAdd">编辑短信</a>
</div>
<div class="tabbable" style="padding-left: 10px">
  <ul class="navX nav-tabs" >
    <li class="active"><a href="#tab1" data-toggle="tab">收件箱(${acceptSize})</a></li>
    <li><a href="#tab2" data-toggle="tab">发件箱(${senderSize})</a></li>
    <li><a href="#tab3" data-toggle="tab">草稿箱(${cgSize})</a></li>
    <li><a href="#tab4" data-toggle="tab">垃圾箱(${rubishSize})</a></li>
  </ul>
  <div class="tab-content">
     <div class="tab-pane active" id="tab1">
               <table class="table table-striped"
					style="border-collapse: collapse; font-family: sans-serif; background: #fff; width: 100%;">
					<tr style="background-color: #39A6AE">
						<th >发信人</th>
						<th >主题</th>
						<th >操作</th>
					</tr>
					<c:forEach items="${acceptList}" var="accept">
						<tr>
						    <c:if test="${accept.isRead eq  false}">
							<td align="center" ><strong>${accept.sender}</strong></td>
							<td align="center" ><a href="messageView?messageId=${accept.messageId}&ZT=0"><strong>${accept.title}</strong></a></td>
							<td align="center" ><a href="deleteSend?messageId=${accept.messageId}&BZ=0"><strong>删除</strong></a></td>
							</c:if>
							<c:if test="${accept.isRead eq  true}">
							<td align="center">${accept.sender}</td>
							<td align="center"><a href="messageView?messageId=${accept.messageId}&ZT=0">${accept.title}</a></td>
							<td align="center"><a href="deleteSend?messageId=${accept.messageId}&BZ=0">删除</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
    </div>
    <div class="tab-pane" id="tab2">
     <table class="table table-striped"
					style="border-collapse: collapse; font-family: sans-serif; background: #fff; width: 100%;">
					<tr>
						<th >收信人</th>
						<th >主题</th>
						<th >操作</th>
					</tr>
					<c:forEach items="${senderList}" var="send">
						<tr>
							<td align="center">${send.incept}</td>
							<td align="center"><a href="messageView?messageId=${send.messageId}&ZT=1">${send.title}</a></td>
							<td align="center"><a href="deleteSend?messageId=${send.messageId}&BZ=1">删除</a></td>
						</tr>
					</c:forEach>
		</table>
    </div>
    <div class="tab-pane" id="tab3">
    <table class="table table-striped"
					style="border-collapse: collapse; font-family: sans-serif; background: #fff; width: 100%;">
					<tr>
						<th >收信人</th>
						<th >主题</th>
						<th >操作</th>
					</tr>
					<c:forEach items="${cgList}" var="cg">
						<tr>
							<td align="center">${cg.incept}</td>
							<td align="center"><a href="messageView?messageId=${cg.messageId}&ZT=2">${cg.title}</a></td>
							<td align="center"><a href="deleteSend?messageId=${cg.messageId}&BZ=2">删除</a></td>
						</tr>
					</c:forEach>
	    </table>
    </div>
    <div class="tab-pane" id="tab4">
    <table class="table table-striped"
					style="border-collapse: collapse; font-family: sans-serif; background: #fff; width: 100%;">
					<tr>
						<th >发信人</th>
						<th >主题</th>
						<th >操作</th>
					</tr>
					<c:forEach items="${rubishList}" var="rubish">
						<tr>
							<td align="center">${rubish.sender}</td>
							<td align="center"><a href="messageView?messageId=${rubish.messageId}&ZT=3">${rubish.title}</a></td>
							<td align="center"><a href="deleteSend?messageId=${rubish.messageId}&BZ=3">删除</a></td>
						</tr>
					</c:forEach>
	</table>
    </div>
  </div>
</div>
</html>