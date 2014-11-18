<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		Exception ex = (Exception) request.getAttribute("exception");
	%>

	<h2>
		Data access failure:
		<%=ex.getMessage()%></h2>
	<p />

	<%
		ex.printStackTrace(new java.io.PrintWriter(out));
	%>

	<p />
	<br />
	<a href="<spring:url value="/" htmlEscape="true" />">Home</a>
</body>
</html>