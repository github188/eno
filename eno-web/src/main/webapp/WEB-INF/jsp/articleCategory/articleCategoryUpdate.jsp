<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
<body>
 <form action="doUpdate" method="post">
	<table height="100"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	
      <tr>
        <td width="80" align="right">类别名称：</td>
        <td width="198" height="39">
          <input name="categoryId" type="text" size="30" id="categoryId" value="${articleCategory.categoryId}"  style="display:none;"/>
          <input name="name" type="text" size="30" id="name" value="${articleCategory.name}">
        </td>
      </tr>
      <tr>
        <td width="80" align="right">摘要：</td>
           <td height="35" ><textarea name="description" rows="3"  id="description" >${articleCategory.description}</textarea>
        </td>
      </tr>
      <tr >
        <td align="center"></td>
        <td align="center">
            <input name="Submit" type="submit" class="btn btn-primary" value="保存" onClick="show();">
            <input name="Submit2" type="button" class="btn btn-primary" value="关闭" onClick="javascript:history.back(-1);">
        </td>
       </tr>
    </table>
  </form>
</body>
<script language="javascript">
</script>
</html>
   
     