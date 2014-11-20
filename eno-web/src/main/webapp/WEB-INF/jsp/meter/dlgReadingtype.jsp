<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<table id="dgReadingtype" class="easyui-datagrid" title="读数类型"
	width="100%" style="height: auto;" singleSelect="true"
	fitColumns="true">
	<thead>
		<tr>
			<th field="readingtype" width="100">类型</th>
			<th field="description" width="250">类型</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>ACTUAL</td>
			<td>Cumulative usage</td>
		</tr>
		<tr>
			<td>DELTA</td>
			<td>Incremental usage</td>
		</tr>
	</tbody>
</table>