<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: ZOUZHIXIANG
	Created Date: 2014-07-08 上午10:47:27
	Modify Date: 
	LastModified Date:
	Description: 设备运行趋势页面
 -->

<link rel="stylesheet" type="text/css" 
	href="${pageContext.request.contextPath}/resources/css/runManage.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/echarts-plain.js"></script>
<script 
	src="${pageContext.request.contextPath}/resources/scripts/report/report.js"></script>
<script 
	src="${pageContext.request.contextPath}/resources/scripts/report/deviceTrend.js"></script>
<script 
	src="${pageContext.request.contextPath}/resources/scripts/format.js"></script>
<style type="text/css">
a {
	text-decoration: none;
	color: #FFF;
}

a:hover,a:active,a:focus {
	text-decoration: none;
	color: #FFF;
}
</style>
<!-- 时间控件用 -->
<div class="clearfix"></div>
<!-- 类型选择 -->
<div class="span12 main row-fluid">
	<div class="span12 content">
    	<div class="reportform_con">
        	<div class="formbar">
            	<div class="droplist_bar">
                	<label>选择子系统：</label>
                    <select id="system_type" style="width:100px"></select>
                </div>
                <div class="droplist_bar">
                	<label>选择设备类型：</label>
                    <select id="device_type" style="width:100px" onchange="queryDevices()"></select>
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
                    <div class="date_box">
	                    <input type="text" id="from_time" onclick="selectdate('from_time','to_time','select_time_type')"><span><i class="icon_date"></i></span></div>
	                    <span class="to">至：</span>
	                    <div class="date_box"><input type="text" id="to_time" readonly="readonly"><span><i class="icon_date"></i></span>
                    </div>
                </div>
                <div class="btn_fun btn_fun_search" onclick="queryDevices()">查询</div>
                <div class="btn_pl_fun btn_fun_pl_export" onclick="exportSelect()">批量导出</div>
                <div id="current_search_date" class="date_info"></div>
            </div>
            <!-- 报表内容展示页面 -->
            <div class="trend_con">
                <div id="devices_ontent" class="equip_list">
                </div>
                <div class="trend_chart_con">
                	<div id="report_content" ></div>
                    <div id="report_page_num" class="page_bar">
                    </div>
                    <div id="progressMask" class="trend_chart_con" style="position: absolute; display: none">
                   		<div class="loading"></div>
                	</div>
                </div>
            </div>

        </div>
    </div>
</div>
<div id="mask">
	<div id="choice_event_type" class="dialog">
		<div class="header">
			<div class="title">选择需要导出设备</div>
			<div class="close cancle">×</div>
		</div>
		<div class="body">
			<div class="control-group">
				<div class="controls">
					<table border="0" style="margin-left: 50px">
						<tr><td>设备列表</td><td></td><td>已选设备</td></tr>
						<tr>
							<td><select id="deviceSource" multiple size="7" name="list1" style="width: 200px; height: 300px; font-size: 14px;"></select>
							</td>
							<td><input type="button" value="    >>   " onclick="moveall('deviceSource','deviceSelect')" name="B3" title="全部导出" style="margin: 15px 0;"><br/>
								<input type="button" value="    >   " onclick="move('deviceSource','deviceSelect')" name="B1" title="添加" style="margin: 15px 0;"> <br /> 
								<input type="button" value="    <   " onclick="move('deviceSelect','deviceSource')" name="B2" title="移除" style="margin: 15px 0;"> <br /> 
								<input type="button" value="    <<   " onclick="moveall('deviceSelect','deviceSource')" name="B4" title="全部移除" style="margin: 15px 0;">
							</td>
							<td><select id="deviceSelect" multiple size="7" name="list2" style="width: 200px; height: 300px; font-size: 14px;"></select>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="footer">
			<div class="button_group" style="width:300px">
				<div class="button" onclick="javascript:expBatchDataToExcel();">确定</div>
				<div class="button cancle">取消</div>
			</div>
		</div>
	</div>
</div>
