<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<div class="row-fluid">
	<div class="span2">
		<div class="widget">
			<div class="widget-header">
				<h3 class="icon">APP配置</h3>
				<button class="btn btn-info pull-right" data-bind="click:addDataApp">新增</button>
			</div>
			<div class="widget-content"
				style="overflow: auto;height: 700px;width: 256px;">
				<ul data-bind="foreach:dataconfigapps" class="nav nav-tabs nav-stacked">
					<li>
					      <a href="#" data-bind="text: app,click:$root.editDataApp"></a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="span8">       
			<div id="myTabContent" class="tab-content">		 
				<div class="tab-pane fade in active" id="home">
				    <form id="dataapp" method="post" action="${pageContext.request.contextPath}/okcsys/dataapp" class="form-horizontal" role="form">
					<fieldset>
					<legend>基本信息：</legend>
						<div class="control-group">
							<label for="datasourceconfigid" class="control-label">类型：</label>
							<div class="dataconfigapps">
								<input type="hidden" id="datasqlid" name="datasqlid" data-bind="value: currentRow().dataconfigappid" /> 
								<select name="configtype" id="configtype" data-bind="value: currentRow().configtype">
									<option value="1">导入</option>
									<option value="2">导出</option>
									<option value="3">SQL语句</option>
								</select>
							</div>
						</div>
						
						<div class="control-group">
							<label for="configid" class="control-label">配置SQL：</label>
							<div class="dataconfigapps">
								<select name="configid" id="configid" data-bind="value: currentRow().configid">
									<c:forEach items="${datasqllist}" var="item">
										<option value="${item.datasqlid}">${item.datasqlid}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="control-group">
							<label for="app" class="control-label">应用：</label>
							<div class="dataconfigapps">
								<input type="text" class="form-control" placeholder="应用业务项"
									id="app" name="app"
									data-bind="value: currentRow().app" required="required" />
							</div>
						</div>
					
					</fieldset>
					<div class="control-group">
					        <label for="app" class="control-label"></label>
							<div class="dataconfigapps">
								<button type="button" class="btn btn-primary"
									data-bind="click:saveDataApp">保存</button>
								<button type="button" class="btn btn-warning"
									data-bind="click:removeDataApp">删除</button>
							</div>
					</div>
					<input type="hidden" id="dataconfigappid">
		          </form>
				</div>
		</div>
	</div>
</div>