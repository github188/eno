<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<body>
	<h1>导入Excel</h1>
	<hr>
	<form name="form1" action="importExcelData?source=condition" method="post"
		enctype="multipart/form-data">
		<input type="file" name="file" id="file"> <input type="button" id="importBtn" onclick="clickImport();" value="导入">
	</form>
</body>
</html>

<script>
$(function(){
	$(".navbar-inverse").css("display", "none");
	$("#sysnav").css("display", "none");
});

var i = 0;
function clickImport() {
	if(i == 0) {
		document.getElementById("importBtn").value = "正在导入...";
		form1.submit();
	}
	i++;
}
</script>