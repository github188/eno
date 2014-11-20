<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
<body>
 <form action="doUpdate" method="post">
	<table height="100"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	
      <tr>
        <td width="80" align="right">部门号：</td>
        <td width="198" height="39">
          <input name="cudeptid" type="text" size="30" id="cudeptid" value="${department.cudeptid}"  style="display:none;"/>
          <input name="deptnum" type="text" size="30" id="deptnum" value="${department.deptnum}">
        </td>
      </tr>
      <tr>
        <td width="80" align="right">部门排序字段：</td>
        <td width="198" height="39">
          <input name="deptorder" type="text" size="30" id="deptorder" value="${department.deptorder}">
        </td>
      </tr>
      <tr>
        <td width="80" align="right">上级部门：</td>
        <td height="35"><input name="parent" type="text" id="parent" size="30" value="${department.parent}">
         </td>
      </tr>
      <tr>
        <td width="80" align="right">描述：</td>
          <td height="35"><textarea name="description" rows="3" id="description" >${department.description}</textarea>
         </td>
      </tr>
      <tr>
        <td width="80" align="right">分管领导：</td>
        <td height="35"><input name="gm" type="text" id="gm" size="30" value="${department.gm}">
         </td>
      </tr>
      <tr>
        <td width="80" align="right">类型：</td>
        <td height="35"><input name="type" type="text" id="type" size="30" value="${department.type}">
         </td>
      </tr>
      <tr >
        <td align="center"></td>
        <td align="center">
            <input name="Submit" type="submit" class="btn btn-primary" value="保存"  >
            <input name="Submit2" type="button" class="btn btn-primary" value="关闭" onClick="javascript:history.back(-1);">
        </td>
       </tr>
    </table>
  </form>
</body>
<script language="javascript">
$('#parent').combotree({
	url : 'departmentListByTree',
	method : 'get',
  });

</script>
</html>
   
     