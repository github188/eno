<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<%@ taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>

<div class="row-fluid">
	<form class="form-horizontal">
		<div class="box">
			<div class="box-content">
				<div class="control-group">
					<div class="control-group">
						<label for="location" class="control-label">位置:</label>
						<div class="controls">
							<input type="text" name="location" class="input-small required"
								readonly="readonly" value="${locations.location}">
							&nbsp; <input type="text" name="description"
								value="${locations.description}">
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>

<div class="widget widget-table">
	<div class="widget-header">
		<span class="icon-list"></span>
		<h3 class="icon chart">资产</h3>
	</div>
	<div class="widget-content">
		<datatables:table id="assetList" data="${assetList}" row="asset"
			cssClass="table table-bordered table-striped" paginate="true">
			<datatables:column title="资产" property="assetnum" />
			<datatables:column title="描述" property="description" />
			<datatables:column title="父级" property="parent" />
			<datatables:column title="优先级" property="priority" />
			<datatables:column title="资产运行" property="isrunning" />
		</datatables:table>

	</div>
</div>