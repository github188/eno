<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <%@ page import="java.util.*"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <html lang="en">
<jsp:include page="../fragments/headTag.jsp" />
<link rel="stylesheet" type="text/css"
	href="../resources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="../resources/css/easyui.css">	
<script type="text/javascript"
	src="../resources/scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="../resources/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../resources/scripts/bootstrap.min.js"></script>
         <form action="doSelect"   method="post" style="text-align: center;">
         <br>
  		    类别名称: <input id="name" type="text" name="name" class="input-medium search-query"/>
  		 <input type="submit" value="查询" class="btn btn-primary">
  		 <input type="button" name="button1" value="新增" onclick="insert()" class="btn btn-primary">
  	     </form>
          <table align="center" class="table table-bordered" style=" border-collapse:collapse; font-family:sans-serif;background:#fff;width:100%;">
		    <tr>
			  <th>类别ID</th><th>类别名称</th><th>描述</th><th>编辑</th>
		    </tr>
		      <c:forEach items="${articleCategorys}" var="articleCategory">
		    <tr>
			  <td align="center">${articleCategory.categoryId}</td>
			  <td align="center">${articleCategory.name}</td>
			  <td align="center">${articleCategory.description}</td>
			  <td>
			  <a href="deleteArticleCategory?categoryId=${articleCategory.categoryId}">删除</a><a href="articleCategoryUpdate?categoryId=${articleCategory.categoryId}">修改</a></td>
		    </tr>
		      </c:forEach>
	     </table>
   <script language="javascript">
   function insert()
   {	
	   var addUrl = 'articleCategoryAdd';
	   window.location.href = addUrl;
   }
	</script>
</html>