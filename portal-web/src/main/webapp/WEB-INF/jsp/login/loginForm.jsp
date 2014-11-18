<%@ page import="org.joda.time.LocalDateTime" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
org.joda.time.DateTimeZone.setDefault(org.joda.time.DateTimeZone.forID("Asia/Shanghai"));
pageContext.setAttribute("now", new org.joda.time.LocalDateTime(org.joda.time.DateTimeZone.getDefault()));
    LocalDateTime nowW= new org.joda.time.LocalDateTime(org.joda.time.DateTimeZone.getDefault());
    String nw=nowW.dayOfWeek().getAsText();
    pageContext.setAttribute("nowWeek", nw);
%>
<html lang="en">
<head>
<title>系统登陆</title>
<link href="<spring:url value="/resources/plugin/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" />
<link href="<spring:url value="/resources/css/login.css"/>" rel="stylesheet" />
<link href="<spring:url value="/resources/plugins/mcustomscrollbar/jquery.mCustomScrollbar.css"/>" rel="stylesheet" />
<script type="text/javascript">
	var CONTEXT_PATH = "${pageContext.request.contextPath}";
</script>
<script src="<spring:url value="/resources/plugin/jquery/jquery-1.11.1.min.js" />"></script>
<script src="<spring:url value="/resources/scripts/jquery-migrate-1.2.1.min.js" />"></script>
<script src=" <spring:url value="/resources/plugin/bootstrap/js/bootstrap.min.js" />"></script>
<script src="<spring:url value="/resources/plugins/mcustomscrollbar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script src="<spring:url value="/resources/plugins/jquery-validation/jquery.validate.js" />"></script>
<script src="<spring:url value="/resources/plugins/jquery-validation/jquery.validate.bootstrap.js" />"></script>
<script src="<spring:url value="/resources/scripts/main.js"/>"></script>
<script src="<spring:url value="/resources/scripts/application.js"/>"></script>
</head>

<style type="text/css">
.user_list  li 
  {list-style:none;}
</style>

<body class="wandabj">

	<div class="login_title">
		<div class="wanda_logo"></div>
		<div class="l_division_line"></div>
		<div class="system_name">
			<p style="font-family:'微软雅黑'">${sysinfo.sysname }</p>
			<p style="font-family:'微软雅黑'">${sysinfo.title}</p>
			
		</div>
	</div>
	<div class="user_choice"></div>
	<div class="user_choice1">
		<p id="global_time">
			<joda:format value="${now}" pattern="HH:mm" />
		</p>
		<div class="login_date">
			<p id="global_week"><c:out value="${nowWeek}"></c:out> </p>
			<p id="global_date">
				<joda:format value="${now}" pattern="yyyy-MM-dd" />
			</p>
		</div>
	</div>
	<div class="user_choice2">
		<div class="users">
			<i class="back_icon"></i>
			<c:forEach items="${userclasses}" var="userclass">
				<div class="${fn:toLowerCase(userclass.dmvalue)}_user"
					id="${fn:toLowerCase(userclass.dmvalue)}">
					<i></i>
					<p>${userclass.description}</p>
				</div>
			</c:forEach>
		</div>
		<form name='f' method='POST' id="login_form" action="<spring:url value='/login/check'/>">
			<div class="user_name_pwd">
				${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
				<div class="user_name">
                <i></i><input type='text' name='username' class="user" data-rule-required="true" tabindex="1"
						value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"
						placeholder="" />
					<div class="fold"></div>
				</div>
				<div class="pwd">
                <i></i><input type='password' class="passwd" name='password' placeholder="" tabindex="2" data-rule-required="true"/>
					<div class="confirm">登录</div>
				</div>
				<input class="submit-form" type="submit" value="Submit"  />
				
			</div>
		</form>
	</div>

	<div class="user_group">
		<div id="content_1" class="content">
			<div>
				<ul class="user_list">
				</ul>
			</div>
		</div>
	</div>
	<script src="<spring:url value="/resources/scripts/login.js"/>"></script>
    <script type="text/javascript">
        $(document).ready(function() {
             $(document).keydown(function(e) {
             if (e.keyCode == 13) {
                $("#login_form").submit();
             }
             });
         });
    </script>
</body>
</html>
