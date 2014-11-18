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
        <td width="80" align="right">标题：</td>
        <td width="198" height="39">
          <input name="articleid" type="text" size="30" id="articleid" value="${article.id}"  style="display:none;"/>
          <input name="title" type="text" size="30" id="title" value="${article.title}">
        </td>
      </tr>
      <tr>
        <td width="80" align="right">版本：</td>
        <td height="35"><input name="version" type="text" id="version" size="30" value="${article.version}">
         </td>
      </tr>
      <tr>
        <td width="80" align="right">类型：</td>
        <td height="35"><input name="type" type="text" id="type" size="30" value="${article.type}">
         </td>
      </tr>
      <tr>
        <td width="80" align="center">链接标题：</td>
        <td height="35"><input name="urlTitle" type="text" id="urlTitle" size="30" value="${article.urlTitle}">
         </td>
      </tr>
      <tr>
        <td width="80" align="right">文章类别：</td>
        <td height="35">
        <input name="id" type="text"  id="id" style="display:none;"/>
        <input name="categoryId" type="text" size="30" id="categoryId"/>
         </td>
      </tr>
      <tr>
        <td width="80" align="right">摘要：</td>
         <td height="35"><textarea name="description"  rows="3"  id="description" >${article.description}</textarea>
         </td>
      </tr>
      <tr>
        <td width="80" align="right">内容：</td>
        <td height="35"><textarea name="content"  rows="3" id="content">${article.content}</textarea>
         </td>
      </tr>
      <tr >
        <td align="center"></td>
        <td align="center">
            <input name="Submit" type="submit" class="btn btn-primary" value="保存"  onClick="show();">
            <input name="Submit2" type="button" class="btn btn-primary" value="关闭" onClick="javascript:history.back(-1);">
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

var categoryIdValue = ${articleCategoryIds};
if(categoryIdValue!="-1")
$('#categoryId').combogrid('setValues', categoryIdValue);
function show(){
var g = $('#categoryId').combogrid('getValues');	// get datagrid object
$('#id').val(g);
}
</script>
</html>
   
     