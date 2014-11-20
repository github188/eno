<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-1 上午8:59:51
	LastModified Date:
	Description:
 -->
 <spring:url value="/class/list/dialog" var="list"></spring:url>
 <ul id="classstructure" class="easyui-tree" data-options="url:'${list}',method:'get',animate:true,lines:true,expandAll:true"></ul>
 
 <script>
	$(function(){
	
		  $('#classstructure').tree('expandAll');

		  $('#classstructure').tree({
			  onSelect:function(node) {
				  if(parent.$("#asset").length) {
				  	parent.$("#asset #classstructureid").val(node.id);
				  	parent.$("#asset #classstructure\\.description").val(node.text);

					var path = CONTEXT_PATH  + "/asset/${value}/specs/saveattribute"
					$.post(path,{classificationid:node.id},function(){
						
					}).done(function() { 
						 parent.$('#dlgClassStructure').modal('hide');	
					});

								  	
				  }
				  
				  if(parent.$("#measurePoint").length) {
					  
					  parent.$("#measurePoint #classstructureid").val(node.id);
					  parent.$("#measurePoint #classStructure\\.description").val(node.text);
					  	
					  
				  }
				  parent.$('#dlgClassStructure').modal('hide');	
				  
		      }
		   });
		  
		  
		
	});

 </script>