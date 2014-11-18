<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<style type="text/css">  
.textareastyle {  
 width:80%;
 overflow:auto;  
 word-break:break-all;  
}  
</style>  

<div class="row-fluid">
	<div class="span2">
		<div class="widget">
			<div class="widget-header">
				<h3 class="icon">SQL配置</h3>
				<button class="btn btn-info pull-right" data-bind="click:addDataSql">新增</button>
			</div>
			<div class="widget-content"
				style="overflow: auto;height: 700px;width: 256px;">
				<ul data-bind="foreach:datasqls" class="nav nav-tabs nav-stacked">
					<li>
					      <a href="#" data-bind="text: datasqlid,click:$root.editDataSql"></a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	
	
	<div class="span8">       
			<div id="myTabContent" class="tab-content">		 
				<div class="tab-pane fade in active" id="home">
				    <form id="datasql" method="post" action="${pageContext.request.contextPath}/okcsys/datasql" class="form-horizontal" role="form">
					<fieldset>
					<legend>基本信息：</legend>
						<div class="control-group">
							<label for="datasourceconfigid" class="control-label">数据源：</label>
							<div class="datasqls">
								<input type="hidden" id="datasqlid" name="datasqlid" data-bind="value: currentRow().datasqlid" /> 
								<select name="datasourceconfigid" id="datasourceconfigid" data-bind="value: currentRow().datasourceconfigid">
									<c:forEach items="${datasourceconfiglist}" var="item">
										<option value="${item.datasourceconfiguid}">${item.sourcename}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="control-group">
							<label for="script" class="control-label">SQL：</label>
							<div class="datasqls">
								 <textarea id="script" name="script"
									class="textareastyle" placeholder="sql" rows="4" cols="10"
									data-bind="value: currentRow().script"></textarea>
							</div>
						</div>
						
						<div class="control-group">
							<label for="comments" class="control-label">备注：</label>
							<div class="datasqls">
								 <textarea id="comments" name="comments"
									class="textareastyle" placeholder="备注" rows="4" cols="10"
									data-bind="value: currentRow().comments"></textarea>
							</div>
						</div>
					</fieldset>
					<div class="control-group">
					        <label for="comments" class="control-label"></label>
							<div class="datasqls">
								<button type="button" class="btn btn-primary"
									data-bind="click:saveDataSql">保存</button>
								<button type="button" class="btn btn-warning"
									data-bind="click:removeDataSql">删除</button>
							</div>
					</div>
					<input type="hidden" id="datasqlid">
		          </form>
				</div>
		</div>
	</div>
</div>