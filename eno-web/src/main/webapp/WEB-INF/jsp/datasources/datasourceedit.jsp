<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<div class="row-fluid">
	<div class="span2">
		<div class="widget">
			<div class="widget-header">
				<h3 class="icon">数据源</h3>
				<button class="btn btn-info pull-right" data-bind="click:addDataSource">新增</button>
			</div>
			<div class="widget-content"
				style="overflow: auto;height: 700px;width: 256px;">
				<ul data-bind="foreach:datasourceconfigs" class="nav nav-tabs nav-stacked">
					<li>
					      <a href="#" data-bind="text: sourcename,click:$root.editDataSource"></a>
					</li>
				</ul>
			</div>
		</div>
		<!-- /sidebar -->
	</div>
	
	
	<div class="span8">       
			<div id="myTabContent" class="tab-content">		 
				<div class="tab-pane fade in active" id="home">
				    <form id="datasource" method="post" action="${pageContext.request.contextPath}/okcsys/datasources" class="form-horizontal" role="form">
					<fieldset>
					<legend>基本信息：</legend>
					   
						<div class="control-group">
							<label for="datasourceconfigid" class="control-label">数据源ID：</label>
							<div class="datasourceconfigs">
								<input type="hidden" id="datasourceconfiguid" name="datasourceconfiguid"
									data-bind="value: currentRow().datasourceconfiguid" /> <input
									type="text" class="form-control" placeholder="ID"
									id="datasourceconfigid" name="datasourceconfigid"
									data-bind="value: currentRow().datasourceconfigid" required="required" />

							</div>
						</div>
						
						<div class="control-group">
							<label for="sourcename" class="control-label">数据源名称：</label>
							<div class="datasourceconfigs">
								<input type="text" class="form-control" placeholder="名称"
									id="sourcename" name="sourcename"
									data-bind="value: currentRow().sourcename" required="required" />
							</div>
						</div>
						
						<div class="control-group">
							<label for="dbtype" class="control-label">数据源类型：</label>
							<div class="datasourceconfigs">
							   <select class="form-control" id="dbtype" name="dbtype"
									data-bind="value: currentRow().dbtype" required="required">
									<!-- 添加none   ztl -->
									 <option value="">选择数据库</option>
							         <option value="mysql">mysql</option>
							         <option value="oracle">oracle</option>
							         <option value="sqlserver">sqlserver</option>
							         <option value="access">access</option> 
							   </select>									
							</div>
						</div>
						
						<div class="control-group">
							<label for="dbname" class="control-label">数据库名：</label>
							<div class="datasourceconfigs">
								<input type="text" class="form-control" placeholder="名称"
									id="dbname" name="dbname"
									data-bind="value: currentRow().dbname" required="required" />
							</div>
						</div>
						
						<div class="control-group">
							<label for="ipaddress" class="control-label">数据库IP：</label>
							<div class="datasourceconfigs">
								<input type="text" class="form-control" placeholder="IP"
									id="ipaddress" name="ipaddress"
									data-bind="value: currentRow().ipaddress" required="required" />
							</div>
						</div>
						
						<div class="control-group">
							<label for="port" class="control-label">数据库端口：</label>
							<div class="datasourceconfigs">
								<input type="text" class="form-control" placeholder="端口"
									id="port" name="port"
									data-bind="value: currentRow().port" />
							</div>
						</div>
						
						<div class="control-group">
							<label for="userid" class="control-label">访问用户名：</label>
							<div class="datasourceconfigs">
								<input type="text" class="form-control" placeholder="用户名"
									id="userid" name="userid"
									data-bind="value: currentRow().userid" required="required" />
							</div>
						</div>
						
						<div class="control-group">
							<label for="password" class="control-label">访问密码：</label>
							<div class="datasourceconfigs">
								<input type="text" class="form-control" placeholder="密码"
									id="password" name="password"
									data-bind="value: currentRow().password" required="required" />
							</div>
						</div>
						
						<div class="control-group">
							<label for="description" class="control-label">备注：</label>
							<div class="datasourceconfigs">
							    <textarea id="description" name="description"
									class="form-control" placeholder="备注"
									data-bind="value: currentRow().description"></textarea>
							</div>
						</div>
					</fieldset>
					<div class="control-group">
					        <label for="description" class="control-label"></label>
							<div class="datasourceconfigs">
								 <button type="button"  id="btn_connection"  class="btn btn-success" data-bind="click:connectiontest">连接测试</button>
								 <button type="button" class="btn btn-primary"
									data-bind="click:saveDatasource">保存</button>
								<button type="button" class="btn btn-warning"
									data-bind="click:removeDatasource">删除</button>
							</div>
					</div>
					<input type="hidden" id="datasourceconfiguid">
		          </form>
				</div>
		</div>
	</div>
</div>