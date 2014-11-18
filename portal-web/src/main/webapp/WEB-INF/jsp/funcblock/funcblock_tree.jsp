<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="cxt" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			fontCss: setFontCss
		}

	};
	function setFontCss(treeId, treeNode) {
		return treeNode.level == 0 ? {color:"red"} : {size:"20px"};
	};
	var zNodes = ${jsonArray};
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
	
	function saveUserGroupByFun(){
		if(selectUserGroupId!=null){
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = treeObj.getCheckedNodes(true);
			if(nodes!=null && nodes.length>0){
				var ids = new Array([nodes.length]); 
		        for (var i = 0; i < nodes.length; i++) {
		        	ids[i] = nodes[i].id;
		        }
		        $("#fun_index").load("${cxt}/rule/save?userGroupId="+selectUserGroupId+"&funId="+ids);
			}
		}
	}
	
</script>
<div class="zTreeDemoBackground left" style="width: 40%;height: 90%;float: left;">
	<ul id="treeDemo" class="ztree"></ul>
</div>
<div style="width: 10%;height: 90%;float: right;display: inline-block;text-align: center;">
	<input type="button" onclick="saveUserGroupByFun();" value="保存"/>
</div>