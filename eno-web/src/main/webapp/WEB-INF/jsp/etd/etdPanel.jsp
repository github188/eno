<%--
  Created by IntelliJ IDEA.
  User: shangpeibao
  Date: 14-12-2
  Time: 上午11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- <script type="text/javascript" src="<spring:url value="/resources/scripts/etd/etd.js"></spring:url>"></script> -->
<style type="text/css">
	.table_style {
	    width: 100%;
	    background-color: #F2F2F2 !important;
	}
	.table_style th {
	    height: 36px;
	    background-color: #999;
	    color: #FFF;
	    font-size: 12px;
	    border: 1px solid #FFF;
	    text-align: center;
	}
	.table_style td {
	    height: 30px;
	    color: #666;
	    font-size: 12px;
	    border: 1px solid #FFF;
	}
	.table_style td[class^="p_"] {
		font-size: 13px;
		text-align: center;
	}
	.table_style tr:nth-child(even) {
		background-color: #e4e4e4;
	}
	.out{
	   border-top:40px #999 solid;/*上边框宽度等于表格第一行行高*/
	   width:0px;/*让容器宽度为0*/
	   height:0px;/*让容器高度为0*/
	   border-left:152px #e4e4e4 solid;/*左边框宽度等于表格第一行第一格宽度*/	
	   position:relative;/*让里面的两个子容器绝对定位*/
	   margin: -1px;
	}
	b{font-style:normal;display:block;position:absolute;top:-35px;left:-50px;width:35px;}
	em{font-style:normal;display:block;position:absolute;top:-20px;left:-130px;width:55x;}
	.t1{background:#BDBABD;}
</style>
<div class="table_list">
	<table id="etd_panel" class="table_style">
		<tbody>
		</tbody>
	</table>
</div>
