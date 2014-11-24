<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/taglib.jsp"%>

<html lang="en">
<head>
<title>系统登录</title>
<script type="text/javascript">
	var CONTEXT_PATH = "${pageContext.request.contextPath}";
</script>
<link href="<spring:url value="/resources/css/base.css" />" rel="stylesheet" />
<link href="<spring:url value="/resources/css/common.css"/>" rel="stylesheet" />
<link href="<spring:url value="/resources/css/login.css"/>" rel="stylesheet" />
<script src="<spring:url value="/resources/scripts/jquery-1.9.1.min.js" />"></script>
<script src="<spring:url value="/resources/scripts/login.js" />"></script>
</head>

<body>
	<div class="login_page">
		<div class="login_box">
			<h1 class="login_logo">
				<i></i>ENO
			</h1>
			<form name='f' method='POST' id="login_form" action="<spring:url value='/login/check'/>">
				<div class="login_formBar">
					<label>USERNAME 用户名</label> <input type="text" name="username"
						placeholder="USERNAME 用户名">
				</div>
				<div class="login_formBar">
					<label>PASSWORD 密码</label> <input type="password" name="password"
						placeholder="PASSWORD 密码">
				</div>
				<div class="login_btnBar">
					<input name="airplane" id="airplane" class="css-checkbox" type="checkbox" checked="checked"> 
					<label for="airplane" class="css-label">保存密码</label> 
					<input type="submit" value="login" id="loginBtn">
				</div>
			</form>
		</div>
	</div>

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