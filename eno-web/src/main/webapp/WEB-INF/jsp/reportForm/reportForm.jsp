<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!--container start-->
<div class="container">
	<div class="span12 js_subnav">
        <ul>
        	<li class="js_subnav_current">能耗报表</li>
            <li>能效报表</li>
            <li>设备运行报表</li>
            <li>计量报表</li>
        </ul>
    </div>
    <!-- 能耗报表 -->
    <div class="span12 js_tab_content">
        <div class="span12 reportForm_content">
        	<div class="reportBox pubCon_blackOne">
            	<div class="pub_title">
                	<div class="funBtnBar">
                    	<a href="#" class="btn_pub btn_redLight">查询</a>
                    	<a href="#" class="btn_pub btn_blue">下载列表</a>
                    </div>
                	<div class="reportForm_viewBar">
                    	<ul class="viewList">
                            <li><i class="icon_small"></i>日报</li>
                            <li class="changeColor"><i class="icon_small"></i>周报</li>
                            <li><i class="icon_small"></i>月报</li>
                            <li><i class="icon_small"></i>年报</li>
                        </ul>
                    </div>
                </div>
                <div class="reportForm_category">
                	<h2>选择报表内容（多选）</h2>
                    <div class="reportF_category_box">
                    	<ul>
                    		<li><input type="checkbox" name="" id="1" class="css-checkbox"><label for="1" class="css-label">建筑总能耗</label></li>
                    		<li><input type="checkbox" name="" id="2" class="css-checkbox"><label for="2" class="css-label">建筑总用电</label></li>
                    		<li><input type="checkbox" name="" id="3" class="css-checkbox"><label for="3" class="css-label">建筑总用水</label></li>
                    		<li><input type="checkbox" name="" id="4" class="css-checkbox"><label for="4" class="css-label">空调能耗</label></li>
                    		<li><input type="checkbox" name="" id="5" class="css-checkbox"><label for="5" class="css-label">建筑总用电</label></li>
                    		<li><input type="checkbox" name="" id="6" class="css-checkbox"><label for="6" class="css-label">建筑总能耗</label></li>
                    		<li><input type="checkbox" name="" id="7" class="css-checkbox"><label for="7" class="css-label">建筑总用电</label></li>
                    		<li><input type="checkbox" name="" id="8" class="css-checkbox"><label for="8" class="css-label">建筑总用电</label></li>
                    		<li><input type="checkbox" name="" id="9" class="css-checkbox"><label for="9" class="css-label">建筑总用电</label></li>
                    		<li><input type="checkbox" name="" id="10" class="css-checkbox"><label for="10" class="css-label">电梯能耗</label></li>
                    		<li><input type="checkbox" name="" id="11" class="css-checkbox"><label for="11" class="css-label">建筑总能耗</label></li>
                    		<li><input type="checkbox" name="" id="12" class="css-checkbox"><label for="12" class="css-label">建筑总用电</label></li>
                    		<li><input type="checkbox" name="" id="13" class="css-checkbox"><label for="13" class="css-label">建筑总用电</label></li>
                    		<li><input type="checkbox" name="" id="14" class="css-checkbox"><label for="14" class="css-label">建筑总用电</label></li>
                    		<li><input type="checkbox" name="" id="15" class="css-checkbox"><label for="15" class="css-label">建筑总用电</label></li>
                    		<li><input type="checkbox" name="" id="17" class="css-checkbox"><label for="17" class="css-label">建筑总用电</label></li>
                    		<li><input type="checkbox" name="" id="18" class="css-checkbox"><label for="18" class="css-label">建筑总用电</label></li>
                    		<li><input type="checkbox" name="" id="19" class="css-checkbox"><label for="19" class="css-label">建筑总用电</label></li>
                    		<li><input type="checkbox" name="" id="20" class="css-checkbox"><label for="20" class="css-label">电梯能耗</label></li>
                    	</ul>
                    </div>
                </div>
                <div class="triangle"><span class="triangle_show"></span></div>
                <div class="tableBox mt10 ml25 mr25">
                	<!--报表初始页 开始-->
                	<div class="reporF_noCon">
                    	<div class="reportF_nopic"></div>
                        <p>请选择报表内容</p>
                        <p>点击查询，显示报表</p>
                    </div>
                    <!--报表初始页 结束-->
                    <table cellpadding="0" cellspacing="0" border="0" class="tableList" style="display:none;">
                    	<tr>
                            <th>报警日期</th>
                            <th>报警时间</th>
                            <th>建筑总用电量(Kwh)</th>
                            <th>空调用电量(Kwh)</th>
                            <th>照明用电量(Kwh)</th>
                            <th>数据中心用电量(Kwh)</th>
                            <th>电梯用电量(Kwh)</th>
                            <th>其他用电量(Kwh)</th>
                        </tr>
                        <tr>
                        	<td><em>2013-11-09</em></td>
                        	<td><em>21：15：21</em></td>
                        	<td>288.9</td>
                        	<td>288.9</td>
                        	<td>288.9</td>
                        	<td>288.9</td>
                        	<td>288.9</td>
                        	<td>288.9</td>
                        </tr>
                        <tr>
                        	<td><em>2013-11-09</em></td>
                        	<td><em>21：15：21</em></td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        </tr>
                        <tr>
                        	<td><em>2013-11-09</em></td>
                        	<td><em>21：15：21</em></td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        </tr>
                        <tr>
                        	<td></td>
                        	<td></td>
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
    <!-- 能效报表 -->
    <div class="span12 js_tab_content" style="display:none;">
        <div class="span12 reportForm_content">能效报表</div>
    </div>
    <!-- 设备运行报表 -->
    <div class="span12 js_tab_content" style="display:none;">
        <div class="span12 reportForm_content">
        	<div class="reportBox pubCon_blackOne">
            	<div class="pub_title">
                	<div class="reportForm_viewBar">
                    	<ul class="viewList">
                            <li><i class="icon_small"></i>日报</li>
                            <li class="changeColor"><i class="icon_small"></i>周报</li>
                            <li><i class="icon_small"></i>月报</li>
                            <li><i class="icon_small"></i>年报</li>
                        </ul>
                    </div>
                    <div class="formBar formBar_h60 borderL">
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
                    <div class="formBar formBar_h60">
                    	<label>选择设备类型</label>
                        <div class="filter_widget">
                            <select class="selectpicker">
                                <option>全部设备</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>给排水</option>
                            </select>
                        </div>
                    </div>
                    <div class="formBar formBar_h60">
                    	<label>选择设备位置</label>
                        <div class="filter_widget">
                            <select class="selectpicker">
                                <option>全部设备</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>给排水</option>
                            </select>
                        </div>
                    </div>
                    <div class="formBar formBar_h60">
                    	<label>选择设备编码</label>
                        <div class="filter_widget">
                            <select class="selectpicker">
                                <option>全部设备</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>给排水</option>
                            </select>
                        </div>
                    </div>
                    <span class="multiple">可多选</span>
                    <div class="funBtnBar">
                        <a href="#" class="btn_pub btn_redLight">查询</a>
                        <a href="#" class="btn_pub btn_blue">下载列表</a>
                    </div>
                </div>
                <div class="reportForm_category">
                	<h2>选择报表内容（多选）</h2>
                    <div class="reportF_category_box reportF_category_boxW146">
                    	<ul>
                    		<li><input type="checkbox" name="" id="21" class="css-checkbox" checked><label for="21" class="css-label">启停状态</label></li>
                    		<li><input type="checkbox" name="" id="22" class="css-checkbox"><label for="22" class="css-label">冷冻室供水温度</label></li>
                    		<li><input type="checkbox" name="" id="23" class="css-checkbox" checked><label for="23" class="css-label">冷却水供水温度</label></li>
                    		<li><input type="checkbox" name="" id="24" class="css-checkbox"><label for="24" class="css-label">冷冻水回水温度</label></li>
                    		<li><input type="checkbox" name="" id="25" class="css-checkbox"><label for="25" class="css-label">冷却水回水温度</label></li>
                    		<li><input type="checkbox" name="" id="26" class="css-checkbox"><label for="26" class="css-label">蒸发温度</label></li>
                    		<li><input type="checkbox" name="" id="27" class="css-checkbox"><label for="27" class="css-label">冷凝温度</label></li>
                    		<li><input type="checkbox" name="" id="28" class="css-checkbox"><label for="28" class="css-label">蒸发压力</label></li>
                    		<li><input type="checkbox" name="" id="29" class="css-checkbox" checked><label for="29" class="css-label">冷凝压力</label></li>
                    	</ul>
                    </div>
                </div>
                <div class="triangle"><span class="triangle_show"></span></div>
                <div class="tableBox mt10 ml25 mr25">
                	<table cellpadding="0" cellspacing="0" border="0" class="tableList">
                    	<tr>
                            <th>报警日期</th>
                            <th>报警时间</th>
                            <th>建筑总用电量(Kwh)</th>
                            <th>空调用电量(Kwh)</th>
                            <th>照明用电量(Kwh)</th>
                            <th>数据中心用电量(Kwh)</th>
                            <th>电梯用电量(Kwh)</th>
                            <th>其他用电量(Kwh)</th>
                        </tr>
                        <tr>
                        	<td><em>2013-11-09</em></td>
                        	<td><em>21：15：21</em></td>
                        	<td>288.9</td>
                        	<td>288.9</td>
                        	<td>288.9</td>
                        	<td>288.9</td>
                        	<td>288.9</td>
                        	<td>288.9</td>
                        </tr>
                        <tr>
                        	<td><em>2013-11-09</em></td>
                        	<td><em>21：15：21</em></td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        </tr>
                        <tr>
                        	<td><em>2013-11-09</em></td>
                        	<td><em>21：15：21</em></td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        	<td>214.6</td>
                        </tr>
                        <tr>
                        	<td></td>
                        	<td></td>
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
    <!-- 计量表据 -->
    <div class="span12 js_tab_content" style="display: none;">
        <div class="span12 reportForm_content">
            <div class="reportBox pubCon_blackOne">
                <div class="pub_title">
                    <div class="funBtnBar">
                        <a href="#" class="btn_pub btn_blue">下载列表</a>
                    </div>
                    <div class="reportForm_viewBar">
                        <ul class="viewList">
                            <li><i class="icon_small"></i>日报</li>
                            <li class="changeColor"><i class="icon_small"></i>周报</li>
                            <li><i class="icon_small"></i>月报</li>
                            <li><i class="icon_small"></i>年报</li>
                        </ul>
                    </div>
                </div>
                <div class="reportBox_main">
                    <div class="report_sidebar_menu">
                        <img src="<spring:url value="/resources/images/report_tree.jpg"></spring:url>">
                    </div>
                    <!-- 一级菜单 -->
                    <!--<div class="span2 main_menu">-->
                        <!--<ul>-->
                            <!--<li class="main_menu_current"><a href="#">P1\2#\3#楼</a></li>-->
                            <!--<li><a href="#">1#楼区块</a></li>-->
                            <!--<li><a href="#">1#楼区块</a></li>-->
                            <!--<li><a href="#">1#楼区块</a></li>-->
                            <!--<li><a href="#">1#楼区块</a></li>-->
                        <!--</ul>-->
                    <!--</div>-->
                    <!-- 二级菜单 -->
                    <!--<div class="span2 second_menu">-->
                        <!--<ul>-->
                            <!--<li>-->
                                <!--<h2><i class="icon_big"></i>P1楼空调锅炉房</h2>-->
                            <!--</li>-->
                            <!--<li>-->
                                <!--<h2><i class="icon_big"></i>2#楼点点餐厅</h2>-->
                            <!--</li>-->
                            <!--<li class="on">-->
                                <!--<h2><i class="icon_big"></i>2#点热水器</h2>-->
                                <!--<div class="subcon">-->
                                    <!--<a href="#" class="focus">灶具设备</a>-->
                                    <!--<a href="#">灶具设备</a>-->
                                    <!--<a href="#">灶具设备</a>-->
                                    <!--<a href="#">灶具设备</a>-->
                                    <!--<a href="#">F1</a>-->
                                    <!--<a href="#">F2</a>-->
                                    <!--<a href="#">F3</a>-->
                                    <!--<a href="#">F4</a>-->
                                <!--</div>-->
                            <!--</li>-->
                            <!--<li>-->
                                <!--<h2><i class="icon_big"></i>LINK室外热水器</h2>-->
                            <!--</li>-->
                            <!--<li>-->
                                <!--<h2><i class="icon_big"></i>3#楼点点餐厅</h2>-->
                            <!--</li>-->
                            <!--<li>-->
                                <!--<h2><i class="icon_big"></i>4#楼点点餐厅</h2>-->
                            <!--</li>-->
                        <!--</ul>-->
                    <!--</div>-->
                    <div class="report_main_table">
                        <!-- 表格 -->
                        <table class="tableList" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <th>时间</th>
                                <th>对应表名</th>
                                <th>2014-4</th>
                            </tr>
                            <tr>
                                <td>00:00 - 01:00</td>
                                <td class="tleft">P1楼空调锅炉房</td>
                                <td>700</td>
                            </tr>
                            <tr>
                                <td>00:00 - 01:00</td>
                                <td class="tleft">2#楼点点餐厅</td>
                                <td>700</td>
                            </tr>
                            <tr>
                                <td>00:00 - 01:00</td>
                                <td class="tleft">A.O史密斯热水器-4台22A.O史密斯热水器-4台23</td>
                                <td>700</td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </table>
                        <!-- 页码分页 -->
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
    <!--弹出框-->
    <div class="modal hide fade alert_box w800" id="edit">
    	<div class="modal-header alert_box_header"> <a href="#" class="icon_small alert_close" data-dismiss="modal"></a>
            <h4>编辑</h4>
        </div>
        <div class="modal-body alert_body">
            <div class="moduleBox_black moduleBox_h200 mb10">
            	<div class="systemBox">
                	<h3 class="pt10"><i class="flag flag_green mr15 mt8"></i>基本信息</h3>
                </div>
                <div class="device_edit_list">
                    <div class="formBar">
                        <label>设备名称</label>
                        <div class="draopdownBox left">
                            <div class="btn-group"> <a href="#" class="btn dropdown-toggle w160" data-toggle="dropdown">选择设备名称<span class="caret"></span></a>
                                <ul class="dropdown-menu min_W160">
                                    <li><a href="#" class="dropdown-toggle">全部系统</a></li>
                                    <li><a href="#">暖通空调</a></li>
                                    <li><a href="#">照明系统</a></li>
                                    <li><a href="#">变配电系统</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="formBar">
                        <label>设备类型</label>
                        <div class="draopdownBox left">
                            <div class="btn-group"> <a href="#" class="btn dropdown-toggle w160" data-toggle="dropdown">选择设备类型<span class="caret"></span></a>
                                <ul class="dropdown-menu min_W160">
                                    <li><a href="#" class="dropdown-toggle">全部系统</a></li>
                                    <li><a href="#">暖通空调</a></li>
                                    <li><a href="#">照明系统</a></li>
                                    <li><a href="#">变配电系统</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="formBar">
                        <label>子系统</label>
                        <div class="draopdownBox left">
                            <div class="btn-group"> <a href="#" class="btn dropdown-toggle w160" data-toggle="dropdown">选择子系统<span class="caret"></span></a>
                                <ul class="dropdown-menu min_W160">
                                    <li><a href="#" class="dropdown-toggle">全部系统</a></li>
                                    <li><a href="#">暖通空调</a></li>
                                    <li><a href="#">照明系统</a></li>
                                    <li><a href="#">变配电系统</a></li>
                                </ul>
                            </div>
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
                        <div class="draopdownBox left">
                            <div class="btn-group"> <a href="#" class="btn dropdown-toggle w160" data-toggle="dropdown">选择所在建筑<span class="caret"></span></a>
                                <ul class="dropdown-menu min_W160">
                                    <li><a href="#" class="dropdown-toggle">全部系统</a></li>
                                    <li><a href="#">暖通空调</a></li>
                                    <li><a href="#">照明系统</a></li>
                                    <li><a href="#">变配电系统</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="formBar">
                        <label>所在楼层</label>
                        <div class="draopdownBox left">
                            <div class="btn-group"> <a href="#" class="btn dropdown-toggle w160" data-toggle="dropdown">选择所在楼层<span class="caret"></span></a>
                                <ul class="dropdown-menu min_W160">
                                    <li><a href="#" class="dropdown-toggle">一层</a></li>
                                    <li><a href="#">二层</a></li>
                                    <li><a href="#">三层</a></li>
                                    <li><a href="#">四层</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="formBar">
                        <label>所在区域</label>
                        <div class="draopdownBox left">
                            <div class="btn-group"> <a href="#" class="btn dropdown-toggle w160" data-toggle="dropdown">选择所在区域<span class="caret"></span></a>
                                <ul class="dropdown-menu min_W160">
                                    <li><a href="#" class="dropdown-toggle">一区</a></li>
                                    <li><a href="#">二区</a></li>
                                    <li><a href="#">三区</a></li>
                                    <li><a href="#">四区</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="formBar">
                        <label>所在房间</label>
                        <div class="draopdownBox left">
                            <div class="btn-group"> <a href="#" class="btn dropdown-toggle w160" data-toggle="dropdown">选择所在房间<span class="caret"></span></a>
                                <ul class="dropdown-menu min_W160">
                                    <li><a href="#" class="dropdown-toggle">101</a></li>
                                    <li><a href="#">二区</a></li>
                                    <li><a href="#">三区</a></li>
                                    <li><a href="#">四区</a></li>
                                </ul>
                            </div>
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