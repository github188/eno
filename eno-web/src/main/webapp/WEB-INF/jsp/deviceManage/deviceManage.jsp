<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!-- container start -->
<div class="container">
	<div class="span12 js_subnav">
        <ul>
        	<li class="js_subnav_current">设备台账</li>
            <li>预防性维护</li>
        </ul>
    </div>
    <!-- 设备台账开始 -->
    <div class="span12 js_tab_content">
        <div class="span12 device_content">
        	<div class="deviceBox pubCon_blackOne">
            	<div class="pub_title">
                    <div class="formBar formBar_h60">
                    	<label>选择子系统</label>
                        <div class="filter_widget">
                            <select class="selectpicker">
                                <option>全部子系统</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>给排水</option>
                            </select>
                        </div>
                    </div>
                    <div class="formBar formBar_h60 borderL">
                    	<label>选择位置</label>
                        <div class="filter_widget">
                            <select class="selectpicker">
                                <option>全部子系统</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>给排水</option>
                            </select>
                        </div>
                    </div>
                    <div class="funBtnBar">
                    	<a href="#" class="btn_pub btn_redLight">导入</a>
                    	<a href="#edit" class="btn_pub btn_redLight" data-toggle="modal">增加</a>
                    	<a href="#" class="btn_pub btn_grey btn_edit">修改</a>
                    	<a href="#" class="btn_pub btn_grey btn_delete">删除</a>
                    	<a href="#" class="btn_pub btn_blue">下载列表</a>
                    </div>
                </div>
                <div class="tableBox mt20 ml25 mr25">
                	<table cellpadding="0" cellspacing="0" border="0" class="tableList">
                    	<tr>
                        	<th class="edit">&nbsp;</th>
                        	<th class="delete">&nbsp;</th>
                            <th>设备名称</th>
                            <th>设备类型</th>
                            <th>子系统</th>
                            <th class="tleft">设备位置</th>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td>注意设备名称最多十字</td>
                        	<td>防盗报警</td>
                        	<td class="tleft">注意位置描述字数限制注意位置描述字数限制注意位置描述字数限制注意位置描述字数限制注意位置描述字数限制六十个字</td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td>注意设备名称最多十字</td>
                        	<td>暖通空调</td>
                        	<td class="tleft">注意位置描述字数限制注意位置描述字数限制注意位置描述字数限制注意位置描述字数限制注意位置描述字数限制六十个字</td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td>注意设备名称最多十字</td>
                        	<td>消防系统</td>
                        	<td class="tleft">注意位置描述字数限制注意位置描述字数限制注意位置描述字数限制注意位置描述字数限制注意位置描述字数限制六十个字</td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                        	<td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        </tr>
                    </table>
                    <div class="pagination">
                        <ul class="right">
                            <li><a href="#" class="icon_small pre_disabled"></a></li>
                            <li class="active"><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#" class="icon_small next"></a></li>
                        </ul>
                        <p>共 <em>24</em> 条数据 当前显示第1-8条</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 预防性维护开始 -->
    <div class="span12 js_tab_content" style="display:none;">
        <div class="span12 device_content">
            <div class="deviceBox pubCon_blackOne">
                <div class="device_sidebar pubCon_blackTwo">
                    <h3><i class="flag flag_green mr15 mt20"></i>预防性维护</h3>
                    <div class="device_sidebar_subcon">
                        <ul>
                            <li class="selecton"><a href="#">维护提醒列表</a></li>
                            <li><a href="#">维护计划表</a></li>
                        </ul>
                    </div>
                </div>
                <div class="device_main">
                    <div class="device_viewBar">
                        <div class="download_btn"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                    </div>
                    <div class="device_detail">
                        <table cellpadding="0" cellspacing="0" border="0" class="tableList">
                            <tr>
                                <th>维护描述</th>
                                <th>对象</th>
                                <th>位置</th>
                                <th>维护计划日期</th>
                                <th>距离现在</th>
                                <th>操作</th>
                            </tr>
                            <tr>
                                <td>冷却水系统水处理</td>
                                <td>冷却水系统</td>
                                <td><em>B1</em>层</td>
                                <td><em>2014-12-31</em></td>
                                <td><em>10</em>天</td>
                                <td><a href="#" class="btn_pub btn_red">不再提醒</a></td>
                            </tr>
                            <tr>
                                <td>中央空调系统检测</td>
                                <td>中央空调系统</td>
                                <td><em>B1</em>层</td>
                                <td><em>2014-12-31</em></td>
                                <td><em>10</em>天</td>
                                <td><a href="#" class="btn_pub btn_red">不再提醒</a></td>
                            </tr>
                            <tr>
                                <td>冷水机组检测</td>
                                <td>冷水机组</td>
                                <td><em>B1</em>层</td>
                                <td><em>2014-12-31</em></td>
                                <td><em>10</em>天</td>
                                <td><a href="#" class="btn_pub btn_red">不再提醒</a></td>
                            </tr>
                            <tr>
                                <td>冷却水系统水处理</td>
                                <td>冷却水系统</td>
                                <td><em>B1</em>层</td>
                                <td><em>2014-12-31</em></td>
                                <td><em>10</em>天</td>
                                <td><a href="#" class="btn_pub btn_red">不再提醒</a></td>
                            </tr>
                            <tr>
                                <td>中央空调系统检测</td>
                                <td>中央空调系统</td>
                                <td><em>B1</em>层</td>
                                <td><em>2014-12-31</em></td>
                                <td><em>10</em>天</td>
                                <td><a href="#" class="btn_pub btn_red">不再提醒</a></td>
                            </tr>
                            <tr>
                                <td>冷水机组检测</td>
                                <td>冷水机组</td>
                                <td><em>B1</em>层</td>
                                <td><em>2014-12-31</em></td>
                                <td><em>10</em>天</td>
                                <td><a href="#" class="btn_pub btn_red">不再提醒</a></td>
                            </tr>
                            <tr>
                                <td>冷却水系统水处理</td>
                                <td>冷却水系统</td>
                                <td><em>B1</em>层</td>
                                <td><em>2014-12-31</em></td>
                                <td><em>10</em>天</td>
                                <td><a href="#" class="btn_pub btn_red">不再提醒</a></td>
                            </tr>
                            <tr>
                                <td>中央空调系统检测</td>
                                <td>中央空调系统</td>
                                <td><em>B1</em>层</td>
                                <td><em>2014-12-31</em></td>
                                <td><em>10</em>天</td>
                                <td><a href="#" class="btn_pub btn_red">不再提醒</a></td>
                            </tr>
                            <tr>
                                <td>冷水机组检测</td>
                                <td>冷水机组</td>
                                <td><em>B1</em>层</td>
                                <td><em>2014-12-31</em></td>
                                <td><em>10</em>天</td>
                                <td><a href="#" class="btn_pub btn_red">不再提醒</a></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </table>
                        <div class="pagination">
                            <ul class="right">
                                <li><a href="#" class="icon_small pre_disabled"></a></li>
                                <li class="active"><a href="#">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">4</a></li>
                                <li><a href="#" class="icon_small next"></a></li>
                            </ul>
                            <p>共 <em>24</em> 条数据 当前显示第1-8条</p>
                        </div>
                    </div>
                </div>
                <div class="device_main" style="display: none;">
                    <div class="device_viewBar">
                        <div class="funBtnBar">
                            <a href="#" class="btn_pub btn_redLight">新建</a>
                            <a href="#" class="btn_pub btn_edit btn_grey">修改</a>
                            <a href="#" class="btn_pub btn_delete btn_grey">删除</a>
                            <a href="#" class="btn_pub btn_blue ml25">下载列表</a>
                        </div>
                    </div>
                    <div class="device_detail">
                        <table cellpadding="0" cellspacing="0" border="0" class="tableList">
                            <tr>
                                <th class="edit">&nbsp;</th>
                                <th class="delete">&nbsp;</th>
                                <th>维护描述</th>
                                <th>对象</th>
                                <th>位置</th>
                                <th>维护开始日期</th>
                                <th>维护结束日期</th>
                                <th>维护频率</th>
                                <th>提醒设置</th>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td>冷却水系统水处理</td>
                                <td>冷却水系统</td>
                                <td><em>B1</em>层</td>
                                <td><em>2014-01-01</em></td>
                                <td><em>2014-12-31</em></td>
                                <td><em>1</em>月</td>
                                <td>提前<em>10</em>天</td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td>中央空调系统检测</td>
                                <td>中央空调系统</td>
                                <td><em>B1</em>层</td>
                                <td><em>2014-01-01</em></td>
                                <td><em>2014-12-31</em></td>
                                <td><em>1</em>月</td>
                                <td>提前<em>10</em>天</td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td>冷水机组检测</td>
                                <td>冷水机组</td>
                                <td><em>B1</em>层</td>
                                <td><em>2014-01-01</em></td>
                                <td><em>2014-12-31</em></td>
                                <td><em>1</em>月</td>
                                <td>提前<em>10</em>天</td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="edit"><a href="#edit_preve" data-toggle="modal" class="icon_small tab_edit"></a></td>
                                <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </table>
                        <div class="pagination">
                            <ul class="right">
                                <li><a href="#" class="icon_small pre_disabled"></a></li>
                                <li class="active"><a href="#">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">4</a></li>
                                <li><a href="#" class="icon_small next"></a></li>
                            </ul>
                            <p>共 <em>24</em> 条数据 当前显示第1-8条</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 设备台账 编辑弹出框开始 -->
    <div class="modal hide fade alert_box w800" id="edit">
    	<div class="modal-header alert_box_header"> <a href="#" class="icon_small alert_close" data-dismiss="modal"></a>
            <h4>编辑</h4>
        </div>
        <div class="modal-body alert_body w776">
            <div class="moduleBox_black moduleBox_h200 mb10">
            	<div class="systemBox">
                	<h3 class="pt10"><i class="flag flag_green mr15 mt8"></i>基本信息</h3>
                </div>
                <div class="device_edit_list">
                    <div class="formBar">
                        <label>设备名称</label>
                        <div class="filter_widget">
                            <select class="selectpicker_w150">
                                <option>选择设备名称</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>给排水</option>
                            </select>
                        </div>
                    </div>
                    <div class="formBar">
                        <label>设备类型</label>
                        <div class="filter_widget">
                            <select class="selectpicker_w150">
                                <option>选择设备类型</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>给排水</option>
                            </select>
                        </div>
                    </div>
                    <div class="formBar">
                        <label>子系统</label>
                        <div class="filter_widget">
                            <select class="selectpicker_w150">
                                <option>选择子系统</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>给排水</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="moduleBox_black moduleBox_h240 mb10">
            	<div class="systemBox">
                	<h3 class="pt10"><i class="flag flag_blue mr15 mt8"></i>设备位置</h3>
                </div>
                <div class="device_edit_list">
                    <div class="formBar">
                        <label>所在建筑</label>
                        <div class="filter_widget">
                            <select class="selectpicker_w150">
                                <option>选择所在建筑</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>给排水</option>
                            </select>
                        </div>
                    </div>
                    <div class="formBar">
                        <label>所在楼层</label>
                        <div class="filter_widget">
                            <select class="selectpicker_w150">
                                <option>选择所在楼层</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>给排水</option>
                            </select>
                        </div>
                    </div>
                    <div class="formBar">
                        <label>所在区域</label>
                        <div class="filter_widget">
                            <select class="selectpicker_w150">
                                <option>选择所在区域</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>给排水</option>
                            </select>
                        </div>
                    </div>
                    <div class="formBar">
                        <label>所在房间</label>
                        <div class="filter_widget">
                            <select class="selectpicker_w150">
                                <option>选择所在房间</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>给排水</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal-footer alert_box_footer">
            <input type="button" class="btn_red" value="确认" >
            <input type="button" class="btn_black" value="清空" >
            <input type="button" class="btn_black" value="取消" data-dismiss="modal">
        </div>
    </div>
    <!-- 预防性维护 修改弹出框 -->
    <div class="modal hide fade alert_box w550" id="edit_preve">
        <div class="modal-header alert_box_header"> <a href="#" class="icon_small alert_close" data-dismiss="modal"></a>
            <h4>编辑</h4>
        </div>
        <div class="modal-body alert_body">
            <div class="moduleBox_black device_preve left w535">
                <div class="device_edit_list">
                    <div class="formBar">
                        <label>维护对象：</label>
                        <div class="formInput"><input type="text"></div>
                    </div>
                    <div class="formBar">
                        <label>维护设备：</label>
                        <div class="filter_widget">
                            <select class="selectpicker_w218">
                                <option>选择设备名称</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>给排水</option>
                            </select>
                        </div>
                    </div>
                    <div class="formBar formBar_h75">
                        <label>维护描述：</label>
                        <textarea></textarea>
                    </div>
                    <div class="formBar">
                        <label>维护日期范围：</label>
                        <div class="calendarBar"><input class="Wdate" onclick="WdatePicker()" type="text"></div>
                        <span class="to">至</span>
                        <div class="calendarBar"><input class="Wdate" onclick="WdatePicker()" type="text"></div>
                    </div>
                    <div class="formBar">
                        <label>维护频率：</label>
                        <input type="text" class="w130_input">
                        <div class="filter_widget">
                            <select class="selectpicker">
                                <option>单位</option>
                                <option>120</option>
                                <option>200</option>
                                <option>150</option>
                            </select>
                        </div>
                    </div>
                    <div class="formBar">
                        <label>提醒设置：</label>
                        <span class="input_txt">提前</span>
                        <input type="text" class="w130_input">
                        <div class="filter_widget">
                            <select class="selectpicker">
                                <option>单位</option>
                                <option>120</option>
                                <option>200</option>
                                <option>150</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal-footer alert_box_footer">
            <input type="button" class="btn_red" value="确认" >
            <input type="button" class="btn_black" value="清空" >
            <input type="button" class="btn_black" value="取消" data-dismiss="modal">
        </div>
    </div>
</div>  
