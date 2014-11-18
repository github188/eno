<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <html lang="en">
 <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main.css">
 <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/easyui.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/jquery.easyui.min.js"></script>
<body>
	<form name="myForm" action="updateCgToSendbox" method="post">
		<table height="100" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
			<tr>
				<td width="80" align="right">收信人：</td>
				<td height="35"><input name="userId" id="userId"
					style="width: 262px"></input>
					<input name="Id" id="Id" style="display:none;"/>
			    </td>
			    <td width="80" style="color: red">&nbsp*</td>
			</tr>
			<tr>
				<td width="80" align="right">主题：</td>
				<td width="198" height="39">
				 <input name="messageId" type="text" size="30" id="messageId" value="${message.messageId}"  style="display:none;"/>
				<input name="title" type="text" value="${message.title}"
					style="width: 250px" id="title"></td>
				<td width="80" style="color: red">&nbsp*</td>	
			</tr>
			<tr>
				<td width="80" align="right">内容：</td>
				<td height="35"><textarea name="content" id="content" 
						style="width: 250px; height: 200px" >${message.content}</textarea></td>
			</tr>
			<tr>
				<td align="center"></td>
				<td align="center">
				<button class="btn btn-primary" type="button" onclick="valid();">发送</button>
				<button class="btn btn-primary" type="button" onClick="saveTo();">存草稿</button>
				</td>
			</tr>
		</table>
	</form>
	<div class="alert alert-error" id="sxr" style="display: none;"> 
	<button type="button" class="close" data-dismiss="alert">&times;</button>  
	<strong>Warning!</strong> 收信人不能为空.
	</div>
	<div class="alert alert-error" id="zt" style="display: none;"> 
	<button type="button" class="close" data-dismiss="alert">&times;</button>  
	<strong>Warning!</strong> 主题不能为空.
	</div>
</body>

<script language="javascript">
$("#userId").combogrid({
	panelWidth : 350,
	multiple: true,
	value : $("#userId").val(),
	idField : 'userid',
	textField : 'firstname',
	url:'getUsers',
	fitColumns: true,
	columns : [ [ 
    {
        field:'ck',
        checkbox:true
    }, {
		field : 'userid',
		title : '用户ID',
		width : 100
	}, {
		field : 'firstname',
		title : '用户姓名',
		width : 100
	} ] ],
	keyHandler : {
		up : function() {
		},
		down : function() {
		},
		enter : function() {
		},
		query : function(q) {
			$('#userId').combogrid("grid").datagrid("reload", {
				'keyword' : q
			});
			$('#userId').combogrid("setValue", q);
		}
	},
});
	function valid(){ 
		 var temp1 =$('#title').val(); 
		 var temp2 = $('#userId').combogrid("getValue");
		 var g = $('#userId').combogrid('getValues');
		 if(g == "")
			{ 
			 document.getElementById("zt").style.display="none";//隐藏
			 document.getElementById("sxr").style.display="";//显示
			 return; 
	    }
		 if(temp1 == "")
			{       
			 document.getElementById("zt").style.display="";//隐藏
			 document.getElementById("sxr").style.display="none";//显示 
			 return; 
	        }
			// get datagrid object
		$('#Id').val(g);
		document.forms["myForm"].submit();
	}

	function saveTo() {
        var temp1 = $('#title').val();
        var temp2 = $('#content').val();
        var temp3 = $('#userId').combogrid("getValue");
        var temp4 = messageId.value;
        var g = $('#userId').combogrid("getValues");
		var dcUrl = "updateCgToCg?title="+temp1+"&content="+temp2+"&Id="+g+"&messageId="+temp4;
		if(g == "")
		{ 
		 document.getElementById("zt").style.display="none";//隐藏
		 document.getElementById("sxr").style.display="";//显示
		 return; 
        }
		if(temp1 == "")
		{       
	     document.getElementById("zt").style.display="";//隐藏
		 document.getElementById("sxr").style.display="none";//显示  
		 return; 
        }
	    window.location.href = dcUrl;
	}

	 var userIdsValue = ${userIds};
	 $('#userId').combogrid('setValues', userIdsValue);
</script>
</html>

