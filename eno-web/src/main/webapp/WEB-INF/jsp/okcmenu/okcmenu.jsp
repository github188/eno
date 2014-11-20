<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 引入jQuery的ajaxfileupload.js，界面中需要使用其中的方法来上传菜单的配置信息 [ ChengKang 2014-07-25 ] -->
<script src="${pageContext.request.contextPath}/resources/plugins/ajaxfileupload/ajaxfileupload.js"></script>

<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-10-15 下午1:16:27
	LastModified Date: 2014-10-11 下午18:00:00
	Description: 系统菜单配置
 -->

<c:choose>
	<c:when test="${okcMenu.okcmenuid==null}">
		<c:set var="method" value="post" />
		<c:set var="editStatus" value="false" />
	</c:when>
	<c:otherwise>
		<c:set var="method" value="put" />
		<c:set var="editStatus" value="true" />
	</c:otherwise>
</c:choose>

<c:if test="${not empty message}">
	<div class="alert alert-success">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		${message}
	</div>
</c:if>

<div class="row-fluid">
	<div class="span3">

		<div id="sidetreecontrol">
			<a href="?#">Collapse All</a> | <a href="?#">Expand All</a>
		</div>
		<div style="height: 900px; overflow: scroll;">
			<c:out value="${menuTreeView}" escapeXml="false"></c:out>
		</div>

	</div>
	<div class="span9" id="content">

		<div id="detailmsg"></div>
		<form:form modelAttribute="okcMenu" method="post"
			class="form-horizontal">
			<div class="row">
				<div class="span6">
					<div class="control-group">
						<form:label path="headerdescription" class="control-label">导航名称：</form:label>
						<div class="controls">
							<form:input path="headerdescription" class="input-medium"
								data-rule-required="true" />
						</div>
					</div>
				</div>
				<div class="span6">
					<div class="control-group">
						<form:label path="elementvalue" class="control-label">导航编码：</form:label>
						<div class="controls">
							<form:input path="elementvalue" class="input-medium"
								data-rule-required="true" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span6">
					<div class="control-group">
						<form:label path="elementtype" class="control-label">导航类型：</form:label>
						<div class="controls">
							<form:input path="elementtype" class="input-medium" />
						</div>
					</div>
				</div>
				<div class="span6">
					<div class="control-group">
						<form:label path="position" class="control-label">显示序号：</form:label>
						<div class="controls">
							<form:input path="position" class="input-mini"
								data-rule-required="true" />
							SubPostion:
							<form:input path="subposition" class="input-mini"
								data-rule-required="true" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span6">
					<div class="control-group">
						<form:label path="ownerelement" class="control-label">上级导航名称：</form:label>
						<div class="controls">
							<form:input path="ownerelement" class="input-medium" />
						</div>
					</div>
				</div>
				<div class="span6">
					<div class="control-group">
						<form:label path="menutype" class="control-label">上级导航类型：</form:label>
						<div class="controls">
							<form:input path="menutype" class="input-medium"
								data-rule-required="true" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span6">
					<div class="control-group">
						<form:label path="image" class="control-label">图标名称：</form:label>
						<div class="controls">
							<form:input path="image" class="input-medium" />
						</div>
					</div>
				</div>
				<div class="span6">
					<div class="control-group">
						<form:label path="url" class="control-label">链接地址：</form:label>
						<div class="controls">
							<form:input path="url" class="input-medium" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class=" offset1">
					<label class="checkbox"> <form:checkbox path="visible" />是否显示
					</label>

				</div>
			</div>

			<div class="row">
				<div class="offset1">

					<c:if test="${okcMenu.okcmenuid!=null && okcMenu.okcmenuid>0}">
						<form:hidden path="okcmenuid" />
					</c:if>

					<a class="btn btn-info"
						href="${pageContext.request.contextPath}/okcsys/okcmenu.html"><span><i
							class="icon-plus"></i> 新增</span></a>

					<button type="submit" class="btn btn-primary savebutton">
						<i class="icon-ok-sign"></i> 保存
					</button>
					<c:if test="${okcMenu.okcmenuid!=null && okcMenu.okcmenuid>0}">
						<button type="button" class="btn btn-inverse copy"
							link="${pageContext.request.contextPath}/okcsys/okcmenu/copy">
							<i class="icon-plus"></i> 复制并新增
						</button>
						<button type="button" class="btn btn-inverse delete"
							link="${pageContext.request.contextPath}/okcsys/okcmenu/delete">
							<i class="icon-remove"></i> 删除
						</button>
					</c:if>
				</div>
			</div>
			<!-- 增加菜单配置的导入导出 [ ChengKang 2014-07-25 ] -->
			<div class="row">
				<div class="span6">
					<div class="control-group">
						<label class="control-label">上传配置：</label>
						<div class="controls">
							<input id="file" name="file" type="file" />
							<button type="button" class="btn btn-primary"
								id="btnSubmitUpload"
								link="${pageContext.request.contextPath}/okcsys/okcmenu/upload">上传</button>
						</div>
					</div>
				</div>
				<div class="span6">
					<div class="control-group">
						<label class="control-label">导出配置：</label>
						<div class="controls">
							<button type="button" class="btn btn-primary"
								id="btnSubmitDownload"
								link="${pageContext.request.contextPath}/okcsys/okcmenu/download">导出</button>
						</div>
					</div>
				</div>
			</div>
		</form:form>

		<table class="table table-hover">
			<thead>
				<tr>
					<th>导航名称</th>
					<th>导航编码</th>
					<th>导航类型</th>
					<th>主导航序号</th>
					<th>子导航序号</th>
					<th>图标名称</th>
					<th>链接地址</th>
					<th>是否显示</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>公共照明</td>
					<td>LSPUB</td>
					<td>TYPE</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
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