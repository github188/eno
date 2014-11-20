<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<jsp:include page="../fragments/headTag.jsp" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/easyui.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/bootstrap.min.js"></script>
<body bgcolor="ffffff" text="#000000" >
<div class="container-fluid">
  <div class="row-fluid">
    <div class="span2" style="background-color: #bbd8e9;height: 100%">
     &nbsp&nbsp&nbsp<b>部门列表</b><br><br>
     <ul id="energyitemdict" class="easyui-tree" ></ul>
    </div>
    <div class="span10" style="background-color:#dceaf4;height: 100%">
     <form action="doSelect"   method="post" style="text-align: center;">
         <br>
  		  DEPTNUM: <input id="deptnum" type="text" name="deptnum"  class="input-medium search-query"/>
  		 <input type="submit" value="查询" class="btn btn-primary" onclick="mc()">
  		 <input type="button" name="button1" value="新增" onclick="insert()" class="btn btn-primary">
  	     </form>
          <table align="center" class="table table-bordered" style=" border-collapse:collapse; font-family:sans-serif;background:#fff;width:100%;">
		    <tr>
			  <th>部门ID</th><th>部门号</th><th>部门排序字段</th><th>描述</th><th>分管领导</th><th>类型</th><th>编辑</th>
		    </tr>
		      <c:forEach items="${departments}" var="department">
		    <tr>
			  <td align="center">${department.cudeptid}</td>
			  <td align="center">${department.deptnum}</td>
			  <td align="center">${department.deptorder}</td>
			  <td align="center">${department.description}</td>
			  <td align="center">${department.gm}</td>
			  <td align="center">${department.type}</td>
			  <td>
			  <a href="deleteDepartment?cudeptid=${department.cudeptid}">删除</a><a href="departmentUpdate?cudeptid=${department.cudeptid}">修改</a></td>
		    </tr>
		      </c:forEach>
	     </table>
    </div>
  </div>
</div>
<script type="text/javascript">$(function() {
$('#energyitemdict').tree({
	url : 'departmentListByTree',
	method : 'get',
	onClick : function(node) {
      departmentListById(node.id);
	}
  });

  function departmentListById(id){
	  var addUrl = 'departmentListById?cudeptid='+id;
	  window.location.href = addUrl;  
  }
})

function insert()
   {	
	   var addUrl = 'departmentAdd';
	   window.location.href = addUrl;
   }
</script>
</body>
</html>


