<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: ZOUZHIXIANG
	Created Date: 2014-07-08 上午10:47:27
	Modify Date: 
	LastModified Date:
	Description: 运行报表页面
 -->
<script 
	src="${pageContext.request.contextPath}/resources/scripts/format.js"></script>
<script 
	src="${pageContext.request.contextPath}/resources/scripts/report/runningReport.js"></script>
<!-- 时间控件用 -->
<div class="clearfix"></div>
<iframe name="<portlet:namespace />tmpFrame1"
		id="<portlet:namespace />tmpFrame1" width="1" height="1"
		style="visibility: hidden; position: absolute; display: none"></iframe>
<div class="span12 main row-fluid">
	<div class="span12 content">
    	<div class="reportform_con">
        	<div class="formbar">
            	<div class="droplist_bar">
                	<label>选择子系统：</label>
                    <select id="system_type" style="width:120px" onchange="changeSelect('system')"></select>
                </div>
                <div class="droplist_bar">
                	<label>选择设备类型：</label>
                    <select id="device_types" style="width:120px" onchange="changeSelect('deviceType')"></select>
                </div>
                <div class="droplist_bar" onchange="queryReport()">
                	<label>选择设备：</label>
                    <select id="devices" style="width:120px"></select>
                </div>
                <div class="droplist_bar">
                	<label>选择频率：</label>
                    <select id="select_time_type" class="selectpicker" style="display: none;">
						<option selected="selected" value="day">日报</option>
						<option value="week">周报</option>
						<option value="month">月报</option>
					</select>
                </div>
                <div class="droplist_bar">
                	<label>选择日期：</label>
                    <div class="date_box"><input type="text" id="from_time" onclick="selectdate('from_time','to_time','select_time_type')"><span><i class="icon_date"></i></span></div>
                    <span class="to">至：</span>
                    <div class="date_box"><input type="text" id="to_time" readonly="readonly"><span><i class="icon_date"></i></span></div>
                </div>
                <div class="btn_fun btn_fun_search" onclick="queryReport()">查询</div>
                <div class="btn_fun btn_fun_export" onclick="expDataToExcel()">输出</div>
                <div id="current_search_date" class="date_info"></div>
            </div>
            <div class="table_list">
          		<div id="reportdiv"></div>
            </div>
            <div class="paging" style="margin-left:0; float:left;">
				<img id="btn_first" src="${pageContext.request.contextPath}/resources/images/left_first.png" title="首页">
				<img  id="btn_prev" src="${pageContext.request.contextPath}/resources/images/left.png" title="上一页">
				<span class="page_des1">Page</span>
				<span><input id="cur_page" type="text"></span>
				<span id="page_num_1" class="page_des2">of 0</span>
				<img id="btn_next" src="${pageContext.request.contextPath}/resources/images/right.png" title="下一页">
				<img id="btn_last" src="${pageContext.request.contextPath}/resources/images/right_end.png" title="末页">
			</div>
           <div class="search_result">共<span id='page_num_2' class="pade_num">0</span>条搜索结果</div>
        </div>
	</div>
</div>