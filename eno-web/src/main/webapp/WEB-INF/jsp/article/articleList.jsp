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
  		     标题: <input id="title" type="text" name="title" class="input-medium search-query"/>
  		 <input type="submit" value="查询" class="btn btn-primary">
  		 <input type="button" name="button1" value="新增" onclick="insert()" class="btn btn-primary">
  <!--   <input type="button" name="button4" value="导入" onclick="dr()">-->  
         <input type="button" name="button5" value="导出" onclick="dc()" class="btn btn-primary">
  	     </form>
          <table align="center" class="table table-bordered" style=" border-collapse:collapse; font-family:sans-serif;background:#fff;width:100%;">
          <thead>
		    <tr>
			  <th>文章ID</th><th>标题</th><th>链接标题</th><th>摘要</th><th>版本</th><th>类型</th><th>编辑</th>
		    </tr>
		   </thead>
		      <c:forEach items="${articles}" var="article">
		    <tr>
			  <td align="center">${article.id}</td>
			  <td align="center">${article.title}</td>
			  <td align="center">${article.urlTitle}</td>
			  <td align="center">${article.description}</td>
			  <td align="center">${article.version}</td>
			  <td align="center">${article.type}</td>
			  <td>
			  <a href="deleteArticle?id=${article.id}" >删除</a><a href="articleUpdate?id=${article.id}">修改</a></td>
		    </tr>
		      </c:forEach>
	     </table>
    
   <script language="javascript">
   function insert()
   {	
	   var addUrl = 'articleAdd';
	   window.location.href = addUrl;
   }
   function dr()
   {
	   openwindow('importExcel','',400,200);
   }
   function dc()
   {
	   var dcUrl = 'exportExcel';
	   location.href = dcUrl;   
   }

   function openwindow(url,name,iWidth,iHeight)
   {
   var url; //转向网页的地址;
   var name; //网页名称，可为空;
   var iWidth; //弹出窗口的宽度;
   var iHeight; //弹出窗口的高度;
   var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
   var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
   window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
   } 
	</script>
</html>