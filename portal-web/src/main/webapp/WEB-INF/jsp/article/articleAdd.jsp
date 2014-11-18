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
 <form action="doInsert" method="post" >
	<table height="100"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
      <tr>
        <td width="80" align="right">标题：</td>
        <td width="198" height="39">
          <input name="title" type="text" size="30" id="title" >
        </td>
      </tr>
      <tr>
        <td width="80" align="right">版本：</td>
        <td height="35"><input name="version" type="text" id="version" size="30">
         </td>
      </tr>
      <tr>
        <td width="80" align="right">类型：</td>
        <td height="35"><input name="type" type="text" id="type" size="30">
         </td>
      </tr>
      <tr>
        <td width="80" align="center">链接标题：</td>
        <td height="35"><input name="urlTitle" type="text" id="urlTitle" size="30">
         </td>
      </tr>
      <tr>
        <td width="80" align="right">文章类别：</td>
        <td height="35">
        <input name="categoryId" type="text" size="30" id="categoryId" />
        <input name="id" type="text"  id="id" size="30" style="display:none;"/>
         </td>
      </tr>
      <tr>
        <td width="80" align="right">摘要：</td>
         <td height="35"><textarea name="description" rows="3" id="description" ></textarea>
         </td>
      </tr>
      <tr>
        <td width="80" align="right">内容：</td>
        <td height="35"><textarea name="content" rows="3" id="content"  ></textarea>
         </td>
      </tr>
      <tr >
        <td align="center"></td>
        <td align="center">
            <input name="Submit" type="submit"  value="保存" class="btn btn-primary" onClick="show();">
            <input name="Submit2" type="button"  value="关闭" class="btn btn-primary" onClick="javascript:history.back(-1);">
        </td>
       </tr>
    </table>
  </form>
</body>
<script language="javascript">
$("#categoryId").combogrid({
	multiple: true,
	panelWidth:350,
	idField:'categoryId',
	textField:'name',
	url:'getArticleCategory',
	fitColumns: true,
	columns:[[
	    {field:'ck',checkbox:true},
		{field:'categoryId',title:'类别ID',width:100},
		{field:'name',title:'文章类别',width:100}
	]],
  });

function show(){
	  var g = $('#categoryId').combogrid('getValues');	// get datagrid object
	  $('#id').val(g);
}
</script>
</html>
   
     