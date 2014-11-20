<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- HTML START -->
<!-- 
	AUTHOR: ztl
	Created Date: 2014年9月26日
	LastModified Date: 
	Description: 表达式配置页面
 -->
<style>
table.hovertable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}
table.hovertable th {
	background-color:#c3dde0;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
table.hovertable tr {
	background-color:#d4e3e5;
}
table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
</style>
<div class="row-fluid">
	<div class="span6">
		<div class="control-group">
			<input type="hidden" id="expindex">
			<input type="hidden" id="name_o">
			<input type="hidden" id="code_o">
			<input type="hidden" id="ismodi">
			<label for="expcode" class="control-label assetclass">编码：</label>
			<div class="controls">
				<input onchange="check(this,'codecheck')" id="expcode" name="expcode" /><span id="codecheck" style="display:none;color:red;">* 编码重复</span>
			</div>
		</div>
		<div class="control-group">
			<label for="expname" class="control-label assetclass">名称：</label>
			<div class="controls">
				<input onchange="check(this,'namecheck')" id="expname" name="expname" /><span id="namecheck" style="display:none;color:red;">* 名称重复</span>
			</div>
		</div>
		<div class="control-group">
			<label for="expcontent" class="control-label assetclass">内容：</label>
			<div class="controls">
				<textarea onchange="nullcheck(this,'check_0')" id="expcontent" style="margin: 0px 0px 10px; width: 740px; height: 80px;" name="expcontent"></textarea>
			</div>
		</div>
		<div class="control-group">
			<div class="controls btnleft">
				<button class="btn btn-primary" id="expressInfo">保存</button>
				<button class="btn btn-default" id="clearInfo">重置</button>
			</div>
		</div>
		<div class="control-group" align="center">
			<span id="check_0" style="color:red;display: none;">所有填写的内容都不能为空！！</span>
			<span id="success_m"style="color:green;display: none;">表达式保存成功</span>
		</div>
	</div>
</div>
<div style="margin: 0px;padding: 0px;">
	<c:choose>
		<c:when test="${!empty expressions.content}">
			<table class="hovertable">
				<caption style="font-size: 20px;">该系统中设置的表达式</caption>
				<thead>
					<tr>
						<th>编号</th>
						<th>名称</th>
						<th>编码</th>
						<th>表达式内容</th>
						<th style="width:auto;">编辑</th>
					</tr></thead>
				<tbody>
					<c:forEach items="${expressions.content}" var="expression" varStatus="statu">
						<tr>
							<td style="display: none;">${expression.expindex }</td>
							<td>${statu.index+1 }</td>
							<td>${expression.expname }</td>
							<td>${expression.expcode }</td>
							<td style="cursor: pointer;" title="双击显示具体内容" ondblclick="showContent(this)">
								<c:if test="${expression.expcontent.length()>50}">${fn:substring(expression.expcontent,0,50)}...</c:if>
								<c:if test="${expression.expcontent.length()<50}">${expression.expcontent}</c:if>
							</td>
							<td align="center">
								<a href="javascript:modify('${expression.expindex }','${expression.expcode }','${expression.expname }','${expression.expcontent}')" style="color: #08c;">修改</a>
								<a href="javascript:deleteone('${expression.expindex }')" style="color: #08c;">删除</a>
							</td>
							<td class="content" style="display:none;">${expression.expcontent }</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td>第${expressions.number+1}页</td>
						<td align="center">共${expressions.totalPages}页</td>
						<td align="center">
							共${expressions.totalElements}条数据
						</td>
						<td align="center">
							<button onclick="getInfos(this)" value="${expressions.number-1}" <c:if test="${expressions.firstPage}">disabled="disabled"</c:if>>上一页</button>
							跳入<select style="width: 70px;" onchange="getInfos(this)">
								<c:forEach begin="1" var="num" end="${expressions.totalPages}" step="1">
									<c:if test="${(num)==(expressions.number+1)}">
										<option value="${num-1}" selected="selected">${num}</option>
									</c:if>
									<option value="${num-1}">${num}</option>
								</c:forEach>
							</select>
							<button onclick="getInfos(this)" value="${expressions.number+1}" <c:if test="${expressions.lastPage}">disabled="disabled"</c:if>>下一页</button>
						</td>
						<td>每页<select id="page_s" style="width: 70px;">
							<option value="5">5</option>
							<option value="10">10</option>
							<option value="15">15</option>
							<option value="20">20</option>
						</select>行</td>
					</tr>
				</tfoot>
			</table>
		</c:when>
		<c:otherwise>
			<h2>本页暂时没有表达式相关的数据，请查询其他页面，或在上面添加！！</h2>
		</c:otherwise>
	</c:choose>
</div>
<script>
</script>