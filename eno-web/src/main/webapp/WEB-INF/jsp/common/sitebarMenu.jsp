<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-10-15 下午1:30:18
	LastModified Date:
	Description: 菜单管理导航
 -->
 <div class="span2">
 <div id="sidetreecontrol">
 <a href="?#">Collapse All</a> | <a href="?#">Expand All</a></div>
 <div  style="height:900px;overflow:scroll;">
<c:out value="${menuTreeView}" escapeXml="false"></c:out>
</div>
 </div>
 
 <script type="text/javascript">
<!--
	$(function() {
		$("#menutree").treeview({
			collapsed: true,
			animated: "medium",
			control:"#sidetreecontrol",
			persist: "location"
		});
	})
//-->
</script>


 