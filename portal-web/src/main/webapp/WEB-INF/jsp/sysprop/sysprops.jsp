<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<link rel="shortcut icon" type="image/ico" href="http://www.datatables.net/favicon.ico" />

<a href="${pageContext.request.contextPath }/okcsys/sysprops/newSysprop"  class="btn" id="create_sysprop">新增</a>
<!-- <a href="javascript:void(0);" class="btn" id="del_sysprop">删除</a>
<a href="javascript:void(0);" class="btn" id="edit_sysprop">编辑</a> -->

			<table id="W_tab" class="table table-bordered table-hover"
				style="text-align: center; width: 100%;">
				<thead>
					<tr style="text-align: center; background-color: #459FE2;">
						<th style="display: none;">属性ID</th>
						<th>属性名称</th>
						<th>属性描述</th>
						<th>系统默认值</th>
						<th>当前值</th>
						<th>是否允许为空</th>
						<th>是否加密</th>
						<th>是否被屏蔽</th>
						<th>权限级别</th>
						<th>修改人</th>
						<th>操作</th>
					</tr>
					</thead>
					<tbody>
					</tbody>
				</table>


