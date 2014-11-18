<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-8 下午4:56:37
	LastModified Date:
	Description:
 -->

<!-- 分类结构使用的对象 -->
<div class="widget widget-table">
	<div class="widget-content">
		<table class="table table-bordered table-striped" id="classstructurechildrentable">
			<thead>
				<tr>
					<th width="20"></th>
					<th width="60" align="right">分类</th>
					<th>分类描述</th>
					<th>生成描述</th>
					<th>使用分类</th>
					<th>组织</th>
					<th width="150">地点</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="9" style="padding: 0px;"><spring:url
							value="/class/edit/${classstructureuid}/classstructure/save"
							var="action"></spring:url> <form:form
							modelAttribute="classStructureChildren" action="${action}"
							cssClass="form-horizontal" style="margin:0px;">
							<div id="classClassStructureContent" style="display: none;">

								<div class="row-fluid">
									<div class=" alert alert-info">详细信息</div>
								</div>
								<div id="childrenDetailmsg"></div>
								<div class="row-fluid">

									<div class="${cssGroup}">
										<form:label path="classificationid" class="control-label">* 分类:</form:label>
										<div class="controls">
											<form:hidden path="parent" />
											<form:input path="classificationid" class="input-medium"
												title="必填字段分类不能为空" data-rule-required="true"
												data-placement="bottom" />
											<a class="showClassificationWindow"
												link="<spring:url value="/class/classification/dialog?section=classStructureChildren"></spring:url>"><i
												class="icon-zoom-in" style="cursor: pointer;"></i></a> <input
												type="text" id="classificationdesc"
												name="classificationdesc"
												 disabled="disabled"  class="input-medium" />
											<span class="help-inline"></span>
										</div>
									</div>

									<div class="control-group">

										<label for="hierarchypath" class="control-label">分类路径:</label>
										<div class="controls">
											<input type="text" name="hierarchypath" id="hierarchypath"
												class="input-medium" disabled="disabled" /> <i
												style="padding-left: 7px;">&nbsp;</i>
											<form:input path="description" class="input-medium" />
										</div>
									</div>
									<div class="control-group">
										<form:label path="genassetdesc" class="control-label">生成描述?</form:label>
										<div class="controls">
											<form:checkbox path="genassetdesc" />
										</div>
									</div>
									<div class="control-group">
										<form:label path="useclassindesc" class="control-label">使用分类?</form:label>
										<div class="controls">
											<form:checkbox path="useclassindesc" />
										</div>
									</div>
								</div>
							</div>

							<div class="row-fluid">
								<div class="span6 pull-right"
									style="text-align: right; padding: 5px 15px 5px 0;">
									<a class="btn" id="btnAddClassChildrens"><span><i
											class="icon-plus"></i> 新增</span></a>
									<button type="submit" id="btnSaveClassChildrens" class="btn btn-primary savebutton"
										style="display: none;">
										<i class="icon-ok-sign"></i> 保存
									</button>
								</div>
							</div>
						</form:form></td>
				</tr>
			</tfoot>
		</table>
	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->