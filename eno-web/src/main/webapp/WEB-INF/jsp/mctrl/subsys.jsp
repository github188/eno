<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<%
org.joda.time.DateTimeZone.setDefault(org.joda.time.DateTimeZone.forID("Asia/Shanghai"));
pageContext.setAttribute("now", new org.joda.time.LocalDateTime(org.joda.time.DateTimeZone.getDefault()));
%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-9-26 上午10:03:11
	LastModified Date:
	Description:
 -->
<div id="mask">
</div>
<table id="opera_table">
	<thead>
		<tr>
			<th style="width:50%">设备</th>
			<th style="width:50%">操作</th>
		</tr>
	</thead>
	<tbody>
		
		<tr id="copy_src">
			<td></td>
			<td>
				<div class="del_btn">删除</div>
			</td>
		</tr>
	</tbody>
</table>


<script src="<spring:url value="/resources/scripts/mctrl/submenu.js" />"></script>