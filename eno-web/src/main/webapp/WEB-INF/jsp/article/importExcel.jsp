<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<jsp:include page="../fragments/headTag.jsp" />
<body>
   <h1>导入Excel</h1>
  <hr>
  <form action="importExcel" method="post" enctype="multipart/form-data">
  <input type="file" name="importExcel" id="importExcel">
  <input type="submit" value="导入"> 
  </form>
</body>
</html>