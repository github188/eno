<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<link rel="stylesheet" href="<spring:url value="/resources/css/pfe/pfeMain.css"></spring:url>" />
<link rel="stylesheet" href="<spring:url value="/resources/css/jquery.mCustomScrollbar.css"/>" />
<link rel="stylesheet" href="<spring:url value="/resources/plugins/jeasyui/themes/default/easyui.css"></spring:url>" />
<link rel="stylesheet" href="<spring:url value="/resources/plugins/jeasyui/themes/icon.css"></spring:url>" />
<link rel="stylesheet" href="<spring:url value="/resources/css/system_icon/style.css"></spring:url>" />
<link rel="stylesheet" href="<spring:url value="/resources/css/icon/style.css"></spring:url>" />
<link rel="stylesheet" href="<spring:url value="/resources/plugins/bootstrap-switch/bootstrap-switch.css"></spring:url>" />
<link rel="stylesheet" href="<spring:url value="/resources/css/pfe/pfeCustom.css"></spring:url>" />
<link rel="stylesheet" href="<spring:url value="/resources/plugins/select2/select2.css"></spring:url>" />
<link rel="stylesheet" href="<spring:url value="/resources/css/slider.css"></spring:url>" />

<script src="<spring:url value="/resources/plugins/jeasyui/jquery.easyui.min.js"></spring:url>"></script>
<script src="<spring:url value="/resources/scripts/pfe/jquery.mCustomScrollbar.concat.min.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/imagemapster/jquery.imagemapster.min.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/bootstrap-switch/bootstrap-switch.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/select2/select2.min.js"></spring:url>"></script>
<script src="<spring:url value="/resources/scripts/My97DatePicker/WdatePicker.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/treetable/jquery.ui.draggable.js"></spring:url>"></script>
<script src="<spring:url value="/resources/scripts/sliderZoom.js"></spring:url>"></script>
<script src="<spring:url value="/resources/scripts/shiftzoom.js"></spring:url>"></script>
<!-- 监视控制页 -->
<div class="span10 right_content nomargin">
<input id="layoutid" value="${layoutid }" type="hidden">
<input id="pagetagid" value="" type="hidden">
<input id="componenttype" value="component3" type="hidden">
<!-- 复制粘贴标记 -->
<input id="copyflag" value="0" type="hidden">

	<c:set var="currentUrl" value="${pageContext.request.contextPath}/mctrl/${elementvalue}/${menuid}"></c:set>

	<div id="structure-toolbar" style="display:none;">
		<span>拖动以确定位置，然后点击保存按钮。</span>
		<button type="button" id="btnSavePagetag" class="btn">保存</button>
		<button type="button" id="toshowTagname" class="btn">显示tagname</button>
		<button type="button" id="btnClose" class="btn" onclick="self.close();">关闭</button>
		<span>输入TagName以定位查找设备位置</span>
		<input id="SearchTagName" type="text" />
		<button type="button" id="btnSearchTag" class="btn">搜索</button>
		
	</div>
	
	<!-- <div style="width:500; height:500; overflow:auto; border:1 solid red; z-index:999;" id="SearchDiv">00000</div> -->
	
	<div id="pagetabsContent" class="tab-content">
		<!-- 设备列表视图 -->
		<div class="tab-pane fade" id="LIST">
			<!-- 同一菜单ID中,设备列表的数量大于1时，按照分页处理。 -->

			<div id="listCarousel" class="carousel slide">
				<div class="carousel-inner">
					<div class="item active"
						style="height:${currentListPagelayout.height }px">
						
						<!-- background:url('${fn:trim(currentListPagelayout.listbg)}') no-repeat top left; -->
						<c:if test="${elementvalue != 'EP' }">
						<div class="structure"
							style="position:absolute;width:${currentListPagelayout.width}px;height:${currentListPagelayout.height }px;"
							layoutid="${currentListPagelayout.layoutid}"
							layouttype="${currentPagelayout.layouttype}">
							<!-- 增加class以与其他div区分 【ztl】-->
							<div id="component1" class="panel" style="position:absolute;top:${currentStructurePagelayout.height-200 }px;width:${currentStructurePagelayout.width}px;"></div>
							
							<table class="tablist">
						        <thead>
						            <tr data-bind='foreach: taglist().devicecolumn'>
						                <th data-bind="text: dcolumn"></th>
						            </tr>
						        </thead>
						        <tbody data-bind="foreach: { data: taglist().devicelist}">
									<tr data-bind="foreach: dlist">
										<!-- ko if: (dvalue == 99999)  -->
						                <td>
											<div class="switch-small dev_switch" data-bind="attr: { tagid: dtagid }">
									            <input type="checkbox" checked />
<!-- 									             data-bind='checked: $index % 2 == 0' -->
									        </div>
										</td>
						                <!-- /ko -->
										<!-- ko if: (dvalue == 99998)  -->
						                <td>
											<div class="switch-small dev_switch" data-bind="attr: { tagid: dtagid }">
									            <input type="checkbox" />
									        </div>
										</td>
						                <!-- /ko -->
										
										<!-- ko if: (dvalue != 99999 && dvalue != 99998)  -->
						                <td data-bind="css: dcss, text: dvalue"></td>
						                <!-- /ko -->
						            </tr>
							    </tbody>
						    </table>
						</div>
						</c:if>
						<c:if test="${elementvalue == 'EP' }">
							<div class="span12 no_right_margin" style="margin-top: 0px;" onload="initPE()">
								<div class="span12 query_bar" style="padding-left: 40px;">
									<span>巡更时间：</span> <span><input id="startDate"
										name="startDate" type="text" onClick="WdatePicker()"
										style="height: 20px; width: 100px;" /></span> <span
										style="margin-left: 30px; cursor: pointer;"
										onclick="selectShow();">查询</span>
								</div>
								<div class="span12 alert_detail">
									<table class="table_style1 sub_table_style1">
										<tbody id="tbodyContent">
											<tr>
												<th>ID</th>
												<th>线路</th>
												<th>班次</th>
												<th>开始时间</th>
												<th>结束时间</th>
												<th>核查时间</th>
												<th>核查结果</th>
												<th>巡逻员</th>
												<th>漏巡个数</th>
												<th>准时个数</th>
												<th>早巡个数</th>
												<th>晚巡个数</th>
												<th>排班类型</th>
											</tr>
										</tbody>
									</table>
									<div class="paging">
										<img
											src="${pageContext.request.contextPath}/resources/images/left_first.png"
											onclick="selectFy('First');" /> <img
											src="${pageContext.request.contextPath}/resources/images/left.png"
											onclick="selectFy('Before');" /> <span class="page_des1">Page</span>
										<span class="bc">1</span><span class="page_des2">of</span> <span
											class="pageCount"></span> <img
											src="${pageContext.request.contextPath}/resources/images/right.png"
											onclick="selectFy('After');" /> <img
											src="${pageContext.request.contextPath}/resources/images/right_end.png"
											onclick="selectFy('End');" />
									</div>
									<!-- <div class="search_result">共<span class="pade_num">1000</span>条搜索结果</div> -->
								</div>
							</div>
						</c:if>
					</div>
				</div>
				<c:if test="${fn:length(listViews)>1}">
					<c:choose>
						<c:when test="${currentListPagelayout.pageindex==0}">
							<c:set var="prevNo" value="0"></c:set>
							<c:set var="nextNo" value="1"></c:set>
							<a class="carousel-control left" href="${currentUrl}.html#LIST">&lsaquo;</a>
							<a class="carousel-control right"
								href="${currentUrl}.html?pageindex=1#LIST">&rsaquo;</a>
						</c:when>
						<c:when
							test="${currentListPagelayout.pageindex==fn:length(listViews)-1}">
							<a class="carousel-control left"
								href="${currentUrl}.html?pageindex=${currentListPagelayout.pageindex-1}#LIST">&lsaquo;</a>
							<a class="carousel-control right"
								href="${currentUrl}.html?pageindex=${fn:length(listViews)-1}#LIST">&rsaquo;</a>
						</c:when>
						<c:otherwise>
							<a class="carousel-control left"
								href="${currentUrl}.html?pageindex=${currentListPagelayout.pageindex-1}#LIST">&lsaquo;</a>
							<a class="carousel-control right"
								href="${currentUrl}.html?pageindex=${currentListPagelayout.pageindex+1}#LIST">&rsaquo;</a>
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>

		</div>
		<!-- /设备列表视图 -->

		<!-- 系统结构图视图 -->
		<div class="tab-pane fade" id="STRUCTURE">
			<div id="structureCarousel" class="carousel slide">
				<div class="carousel-inner">
					<div class="item active"
						style="height:${currentStructurePagelayout.height}px">
						<div class="structure tagdiv"
							style="position:absolute;width:${currentStructurePagelayout.width}px;height:${currentStructurePagelayout.height }px;background:url('${pageContext.request.contextPath}/${fn:trim(currentStructurePagelayout.background)}') no-repeat top left;"
							layoutid="${currentStructurePagelayout.layoutid}"
							layouttype="${currentStructurePagelayout.layouttype}">
							
							<!-- 变配电右上角面板 -->
							<c:if test="${fn:contains(requestURL,'/ETD')}">
								<div class="etd_right_panel" data-bind="visible: currETDAssetClassSpecArray().length>0">
									<div class="parameter_box">
								    	<div class="parameter_title">
								        	<div class="on_off">闭合状态：<span>断开</span></div>
								        	<h3 data-bind="text: currETDAssetNum">WY-1Y-01参数</h3>
								        </div>
								    	<div class="parameter_detail">
								        	<ul data-bind="foreach: currETDAssetClassSpecArray">
								        		<li>
								        			<span class="options" data-bind="text: label">A相电流</span>
								        			<span data-bind="attr: { class:'p_'+tagid}, text: tagvalue"></span>
								        			<span class="unit" data-bind="text: measureunitid=='—'?'':measureunitid">A</span>
							        			</li>
								        	</ul>
								        </div>
								    </div>								
								</div>
							</c:if>
							
							<div id="component2" class="panel" style="position:absolute;top:${fn:contains(requestURL,'/ETD') ?currentStructurePagelayout.height-400 : currentStructurePagelayout.height-200 }px;width:${currentStructurePagelayout.width}px;">
								<!-- 变配电面板 --> 
								<c:if test="${fn:contains(requestURL,'/ETD')}">
									<c:import url="../../etd/etdPanel.jsp"/>			
								</c:if>
							</div>
							<!-- ko foreach: taglist().structureItmes -->
<!-- 							<div style="position:absolute;" -->
<!-- 								data-bind="attr: { class: classvalue,id: tagid,pagetagid: pagetagid,pagetagtype:pagetagtype,pagetab:'structure',exp:expressions },draggable: { cursor: 'pointer' },style:{background:value,top:structure_top + 'px',left:structure_left + 'px',zIndex:zindex},click:$root.operate().showOperation" > -->
								<!-- <span data-bind="text: value"></span> -->
<!-- 								<span data-bind="css: arrow"><span -->
<!-- 									data-bind="text: label,style:{fontSize: labelFontSize},css:labelClassName,visible:showlabel"></span></span> -->
<!-- 								<div data-bind="html:template,visible:pagetagtype==99"></div> -->

								
								<div style="position:absolute;"
									data-bind="visible:visible,attr: { id: tagid,pagetagid: pagetagid,pagetagtype:pagetagtype,pagetab:'plan',title:tagname,exp:expressions },draggable: { cursor: 'pointer' },style:{background:value,top:plan_top + 'px',left:plan_left + 'px',zIndex:zindex},click:$root.operate().showOperation"
									class="meter ui-draggable custompage">
									<!-- ko if: (pagetagtype==2) -->
									<a data-bind="attr:{onclick:deviceUrl,title:label}"><span data-bind="css: arrow"></span></a>
									<!-- /ko -->
									<!-- ko if: (pagetagtype<2 || (pagetagtype>2&&pagetagtype<98))  -->
										<c:choose>
										  	<c:when test="${fn:contains(requestURL,'/HVAC')}">
											   	<!-- 暖通空调图标组件显示，2014-09-18，zouzhixiang -->
												<i style="position: absolute; z-index: 1;" data-bind="css: baseicon, attr:{title: $parent.tagname, childtagid: childtagid}"></i>
												<!-- ko foreach: deviceslist -->
													<!-- ko if: (tagname === icon1_text() && value() === icon1_value()) -->
														<i data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 3;"></i>
													<!-- /ko -->
													<!-- ko if: (tagname === icon2_text() && value() === icon2_value()) -->
														<i data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 2;"></i>
													<!-- /ko -->
												<!-- /ko -->
												<div data-bind="text: tagname, css:labelClassName"></div> 
										   	</c:when>
										    <c:otherwise>
											  
											 	<!--  系统图标组件显示，2014-09-26，zouzhixiang -->
												<span style="position: absolute; z-index: 1;" data-bind="css: baseicon, attr:{title: $parent.tagname, childtagid: childtagid}">
												</span>
												<!-- ko foreach: deviceslist -->
													<!-- ko if: (tagname === icon1_text() && value() === icon1_value()) -->
														<span data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 5;"></span>
													<!-- /ko -->
													<!-- ko if: (tagname === icon2_text() && value() === icon2_value()) -->
														<span data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 4;"></span>
													<!-- /ko -->
												<!-- /ko -->
												<span style="background: url('') no-repeat;" data-bind="css: baseicon"></span>
	
										    </c:otherwise>
										</c:choose>
									<!-- /ko -->
									<!-- ko if:(pagetagtype!=98) -->
									<span class="showTagname" style="display:none;" data-bind="text:tagname"></span>
									<!-- /ko -->
									<div class="P_98" style="cursor: Default;" data-bind="html:template,visible:pagetagtype==98"></div>
									<div style="cursor: Default;" data-bind="html:template,visible:pagetagtype==99"></div>
								</div>
								
								




<!-- 							</div> -->
							<!-- /ko -->
						</div>
					</div>
				</div>
				<c:if test="${fn:length(structureViews)>1}">
					<c:choose>
						<c:when test="${currentStructurePagelayout.pageindex==0}">
							<c:set var="prevNo" value="0"></c:set>
							<c:set var="nextNo" value="1"></c:set>
							<a class="carousel-control left" href="${currentUrl}.html#STRUCTURE">&lsaquo;</a>
							<a class="carousel-control right"
								href="${currentUrl}.html?pageindex=1#STRUCTURE">&rsaquo;</a>
						</c:when>
						<c:when
							test="${currentStructurePagelayout.pageindex==fn:length(structureViews)-1}">
							<a class="carousel-control left"
								href="${currentUrl}.html?pageindex=${currentStructurePagelayout.pageindex-1}#STRUCTURE">&lsaquo;</a>
							<a class="carousel-control right"
								href="${currentUrl}.html?pageindex=${fn:length(listViews)-1}#STRUCTURE">&rsaquo;</a>
						</c:when>
						<c:otherwise>
							<a class="carousel-control left"
								href="${currentUrl}.html?pageindex=${currentStructurePagelayout.pageindex-1}#STRUCTURE">&lsaquo;</a>
							<a class="carousel-control right"
								href="${currentUrl}.html?pageindex=${currentStructurePagelayout.pageindex+1}#STRUCTURE">&rsaquo;</a>
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>


		</div>
		<!-- /系统结构图视图 -->

		<!-- 系统平面图视图 -->
		<div class="tab-pane fade" id="PLAN">

			<div id="planCarousel" class="carousel slide">

		<!-- 平面图 sliderZoom -->
		<!-- 
		<div class="BMap_stdMpPan" >
			<div class="BMap_button BMap_panN"></div>
			<div class="BMap_button BMap_panE"></div>
			<div class="BMap_button BMap_panW"></div>
			<div class="BMap_button BMap_panS"></div>
		</div>
		<div class="map-slider">
			<div class="buttons">
			   <span class="fa fa-plus">+</span>
				<div class="drag-line">
				  <div class="line"></div> 
				  <div class="draggable-button">1</div>   
				</div>
				<div class="draggable-buton"></div>   
				<span class="fa fa-minus">-</span>
			</div>
		</div>
		 -->
		<!-- 平面图 sliderZoom  end-->
        <div class="carousel-inner">
					<div class="item active"
						style="height:${currentPlanPagelayout.height}px">
						<div id="stru_id" onLoad="shiftzoom.add(this,{showcoords:true,zoom:100})" class="structure tagdiv"
							style="position:absolute;width:${currentPlanPagelayout.width}px;height:${currentPlanPagelayout.height }px;background:url('${pageContext.request.contextPath}/${fn:trim(currentPlanPagelayout.planbg)}') no-repeat top left;"
							layoutid="${currentPlanPagelayout.layoutid}" layouttype="${currentPlanPagelayout.layouttype}">
							<c:if test="${!fn:contains(requestURL,'/PFE')}">
								<!-- 客流 -->
							       <img id="backgroundImg" src = "${pageContext.request.contextPath}/${fn:trim(currentPlanPagelayout.planbg)}" border="0" usemap="#HotMap" id="HotMap" style="border:0;">
						    </c:if>
							<div style="float:left;width:100px;" id="statelist"></div>
							<div id="component3" class="panel" style="position:absolute;top:${(fn:contains(requestURL,'/PFE') or fn:contains(requestURL,'/LSPUB') or fn:contains(requestURL,'/BGMB')) ? currentStructurePagelayout.height-238 : (fn:contains(requestURL,'/LSN') ? currentStructurePagelayout.height-180 : currentStructurePagelayout.height-285) }px;width:${currentStructurePagelayout.width}px;">
								<!-- 背景音乐面板 -->
								<c:if test="${fn:contains(requestURL,'/BGMB')}">
							       <c:import url="../../mctrl/bgmb.jsp"/>
							    </c:if>
								<!-- 公共照明面板 -->
								<c:if test="${fn:contains(requestURL,'/LSPUB')}">
							       <c:import url="../../mctrl/lspub.jsp"/>
							    </c:if>
							    <!-- 夜景照明面板 -->
								<c:if test="${fn:contains(requestURL,'/LSN')}">
							       <c:import url="../../mctrl/lsn.jsp"/>
							    </c:if>
							    <!-- 客流 -->
							    <c:if test="${fn:contains(requestURL,'/PFE')}">
							       <c:import url="../../pfe/pfePanel.jsp"/>
							    </c:if>
							</div>
							
					<div style="overflow: hidden;margin: 0px;padding: 0px;"><div id="datetimepicker" class="input-append date" style="width: 160px;margin: 0px;padding: 0px;">
                         <input id="timetext" type="text"></input>
                          <span class="add-on">
                         <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
                       </span>
                      </div>
                      
                      <div style="float: left;width: 50px;margin: 0px;padding: 0px;"><input id="sbmt" class="btn btn-primary"  type="button" value="查询"></div></div>
                      <div style="display: block;margin: 0px;padding: 0px;"><table id="DOORACS"></table></div>
                      
                      <div id="popoverK"></div>
							<!-- 停车管理-中央收费图标组件--中央收费刷卡记录面板- LiHuiHui -->
						 <c:if test="${fn:contains(requestURL,'/PARKM')}">
                             <c:import url="../../elecpatrol/parkmList.jsp"/>
						</c:if>
					    	<c:if test="${fn:contains(requestURL,'/PFE')}">
								<c:import url="../../pfe/passengerView.jsp" />
							</c:if>
							
							<!-- 热区定义  [ ChengKang 2014-08-01 ] -->
							<!-- 这里的AREA_START是一个标记作用，mctrl.js中将引用到这个标记位置，在这之后添加PageTag的热区  [ ChengKang 2014-08-01 ] -->
							<map name="HotMap" id="HotMap">
								<area id="AREA_START" href="#" shape="circle" coords="0,0,0" state="0" />
							</map>
							
							<!-- ko foreach: taglist().planItems -->
							<div style="position:absolute;"
								data-bind="visible:visible,attr: { id: tagid,pagetagid: pagetagid,pagetagtype:pagetagtype,pagetab:'plan',title:tagname,exp:expressions },draggable: { cursor: 'pointer' },style:{background:value,top:plan_top + 'px',left:plan_left + 'px',zIndex:zindex},click:$root.operate().showOperation"
								class="meter ui-draggable custompage">
								<!-- ko if: (pagetagtype==2) -->
								<a data-bind="attr:{onclick:deviceUrl,title:label}"><span data-bind="css: arrow"></span></a>
								<!-- /ko -->
								<!-- ko if: (pagetagtype<2 || (pagetagtype>2&&pagetagtype<98))  -->
									<c:choose>
										<c:when test="${fn:contains(requestURL,'/PFE')}">
											<!-- 客流 -->
											<span data-bind="attr:{id:label}, css:passenger.showClass" >
												<input name="inCount" type="hidden" data-bind="value: passenger.inCount">
												<input name="outCount" type="hidden" data-bind="value: passenger.outCount">
												<input name="insideCount" type="hidden" data-bind="value: passenger.insideCount">
												<i class="cus_icon cus_icon_b"></i>
												<span class="cus_text cus_text_b" data-bind="text: passenger.showinCount"></span>
											</span>
									   	</c:when>
									  	<c:when test="${fn:contains(requestURL,'/HVAC')}">
										   	<!-- 暖通空调图标组件显示，2014-09-18，zouzhixiang -->
											<i style="position: absolute; z-index: 1;" data-bind="css: baseicon, attr:{title: $parent.tagname, childtagid: childtagid}"></i>
											<!-- ko foreach: deviceslist -->
												<!-- ko if: (tagname === icon1_text() && value() === icon1_value()) -->
													<i data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 3;"></i>
												<!-- /ko -->
												<!-- ko if: (tagname === icon2_text() && value() === icon2_value()) -->
													<i data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 2;"></i>
												<!-- /ko -->
											<!-- /ko -->
											<div data-bind="text: tagname, css:labelClassName"></div> 
									   	</c:when>
									  	<c:when test="${fn:contains(requestURL,'/FAS')}">
										   	<!--  消防系统图标组件显示，2014-09-26，zouzhixiang -->
											<span style="position: absolute; z-index: 1;" data-bind="css: baseicon, attr:{title: $parent.tagname, childtagid: childtagid}">
											</span>
											<!-- ko foreach: deviceslist -->
												<!-- ko if: (tagname === icon1_text() && value() === icon1_value()) -->
													<span data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 3;"></span>
												<!-- /ko -->
												<!-- ko if: (tagname === icon2_text() && value() === icon2_value()) -->
													<span data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 2;"></span>
												<!-- /ko -->
												<!-- 消防系统有显示文字的需求 -->
<!-- 												<span data-bind="text: value, visible: $parent.showValue, css: valueClassName, attr:{style:{fontSize: valueFontSize}, title: $parent.tagname}"></span> -->
											<!-- /ko -->
											<span style="background: url('') no-repeat;" data-bind="css: baseicon"></span>
									   	</c:when>
									  	<c:when test="${fn:contains(requestURL,'/MSEM')}">
										   	<!--  电梯图标组件显示，2014-09-27，zouzhixiang -->
											<span style="position: absolute; z-index: 1;" data-bind="css: baseicon, attr:{title: $parent.tagname, childtagid: childtagid}">
											</span>
											<!-- ko foreach: deviceslist -->
												<!-- ko if: (tagname === icon1_text() && value() === icon1_value()) -->
													<span data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 100;"></span>
												<!-- /ko -->
												<!-- ko if: (tagname === icon2_text() && value() === icon2_value()) -->
													<span data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 4;"></span>
												<!-- /ko -->
												<!-- ko if: (tagname === 'position') -->
													<span class="msem_pos" data-bind="text: value, attr:{title: $parent.tagname}"></span>
												<!-- /ko -->
												<!-- ko if: (tagname === 'direction') -->
													<span class="msem_direct" data-bind="text: (value() === '1' ? '↑' : '↓'), attr:{title: $parent.tagname}"></span>
												<!-- /ko -->
											<!-- /ko -->
											<span style="background: url('') no-repeat;" data-bind="css: baseicon"></span>
									   	</c:when>
									  	<c:when test="${fn:contains(requestURL,'/SASAC')}">
										   	<!--  门禁图标组件显示，2014-09-27，zouzhixiang -->
											<span style="position: absolute; z-index: 1;" data-bind="css: baseicon, attr:{title: $parent.tagname, childtagid: childtagid}">
											</span>
											<!-- ko foreach: deviceslist -->
												<!-- ko if: (tagname === icon1_text() && value() === icon1_value()) -->
													<span data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 5;"></span>
												<!-- /ko -->
												<!-- ko if: (tagname === icon2_text() && value() === icon2_value()) -->
													<span data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 4;"></span>
												<!-- /ko -->
												<!-- ko if: (tagname === icon3_text() && value() === icon3_value()) -->
													<span data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 3;"></span>
												<!-- /ko -->
											<!-- /ko -->
											<span style="background: url('') no-repeat;" data-bind="css: baseicon"></span>
									   	</c:when>
									    <c:otherwise>
										  
										 	<!--  系统图标组件显示，2014-09-26，zouzhixiang -->
											<span style="position: absolute; z-index: 1;" data-bind="css: baseicon, attr:{title: $parent.tagname, childtagid: childtagid}">
											</span>
											<!-- ko foreach: deviceslist -->
												<!-- ko if: (tagname === icon1_text() && value() === icon1_value()) -->
													<span data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 5;"></span>
												<!-- /ko -->
												<!-- ko if: (tagname === icon2_text() && value() === icon2_value()) -->
													<span data-bind="css: arrow,attr:{id: 'show_' + tagid, title: $parent.tagname}" style="position: absolute; z-index: 4;"></span>
												<!-- /ko -->
											<!-- /ko -->
											<span style="background: url('') no-repeat;" data-bind="css: baseicon"></span>

									    </c:otherwise>
									</c:choose>
								<!-- /ko -->
								<!-- ko if:(pagetagtype!=98) -->
								<span class="showTagname" style="display:none;" data-bind="text:tagname"></span>
								<!-- /ko -->
								<div class="P_98" style="cursor: Default;" data-bind="html:template,visible:pagetagtype==98"></div>
								<div style="cursor: Default;" data-bind="html:template,visible:pagetagtype==99"></div>
							</div>
							<!-- /ko -->
						</div>
					</div>
				</div>
				<c:if test="${fn:length(planViews)>1}">
					<c:choose>
						<c:when test="${currentPlanPagelayout.pageindex==0}">
							<c:set var="prevNo" value="0"></c:set>
							<c:set var="nextNo" value="1"></c:set>
							<a class="carousel-control left" href="${currentUrl}.html#PLAN">&lsaquo;</a>
							<a class="carousel-control right"
								href="${currentUrl}.html?pageindex=1#PLAN">&rsaquo;</a>
						</c:when>
						<c:when
							test="${currentPlanPagelayout.pageindex==fn:length(planViews)-1}">
							<a class="carousel-control left"
								href="${currentUrl}.html?pageindex=${currentPlanPagelayout.pageindex-1}#PLAN">&lsaquo;</a>
							<a class="carousel-control right"
								href="${currentUrl}.html?pageindex=${fn:length(planViews)-1}#PLAN">&rsaquo;</a>
						</c:when>
						<c:otherwise>
							<a class="carousel-control left"
								href="${currentUrl}.html?pageindex=${currentPlanPagelayout.pageindex-1}#PLAN">&lsaquo;</a>
							<a class="carousel-control right"
								href="${currentUrl}.html?pageindex=${currentPlanPagelayout.pageindex+1}#PLAN">&rsaquo;</a>
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>
		</div>
		<!-- /系统平面图视图 -->

	</div>


	<div id="operate-dialog" class="modal hide fade" tabindex="-1">
		<div class="modal-body">
			<form class="form-horizontal"
				data-bind="submit: operate().executeOperation">
				输入操作值：<input id="value" type="text"
					data-bind="value: operate().valueToChange">
				<button class="btn" data-dismiss="modal">关闭</button>
				<button class="btn btn-primary" type="submit">
					<span data-bind="text: operate().action"></span>
				</button>
			</form>
		</div>
	</div>



	<!-- 编辑对话框 -->
	<div id="editable-dialog" class="editable-modal" style="display:none;">
		<div class="editable-modal-header">

			<button type="button" class="close"
				style=" padding:0px; margin:0px 0px 0px 0px;"
				data-bind="click:$root.operate().hideEditable">&times;</button>
		</div>
		<div class="modal-body">

			选择控件：<select id="controlid" name="controlid"
				data-bind="options: controls,
                       optionsText: 'ctrlname',
                       optionsValue: 'controlid',
                       value: $root.operate().selControlid,
                       optionsCaption: '选择...'"></select><br>
			<input type="hidden" id="currView" /><br/>
			<button class="btn btn-primary" type="submit">
				<span data-bind="click:$root.operate().saveEditable">保存</span>
			</button>
		</div>
	</div>
	<div id="addexp-dialog" class="editable-modal" style="display:none;">
		<input type="hidden" id="textId" value="">
		<div class="editable-modal-header">

			<button type="button" class="close"
				style=" padding:0px; margin:0px 0px 0px 0px;"
				onclick="hideAdd('addexp-dialog')">&times;</button>
		</div>
		<div class="modal-body">
			选择表达式:<br>
			<c:choose>
				<c:when test="${empty exps}">
					<span>暂无表达式，如需要请进入表达式维护页面进行表达式预设，添加</span>				
				</c:when>
				<c:otherwise>
					<select id="chooseexp">
						<c:forEach items="${exps }" var="expression">
							<option value="${expression.expindex}">${expression.expname}</option>
						</c:forEach>
					</select><br>
				</c:otherwise>
			</c:choose>
			<input type="hidden" id="currView" /><br/>
			<button class="btn btn-primary" type="submit">
				<span onclick="addExp()">确定</span>
			</button>
		</div>
	</div>
</div>


<div id="addInputValue" style="display:none;padding:5px;width:400px;height:200px;" title="填写数值">
	<label class="lbInfo">数值：</label>
	<input id="inputval" type="text" class="easyui-validatebox"/>
</div> 

<div id="addTagidValue" style="display:none;padding:5px;width:400px;height:200px;" title="填写tagid">
	<label class="lbInfo">TAGID：</label>
	<input id="inputtagid" type="text" class="easyui-validatebox"/>
</div> 

<div id="mask">
	<div class="dialog-popover shop" ng-controller="businessTime">

		<div class="dialog-header">
			<p class="dialog-title">设置开/闭店时间</p>
			<div class="dialog-close close_pop">×</div>
		</div>

		<div class="shop-content shop-open">

			<div class="shop-panel open-shop">

				开店日期：
				<div id="datetimepicker1" class="input-append date">
					<input data-format="yyyy-MM-dd" class="shop-state open-date"
						type="text" ng-model="cal.startDate" name="startDate" /> <span
						class="add-on"> <i data-time-icon="icon-time"
						data-date-icon="icon-calendar"> </i>
					</span>
				</div>
				开店时间：
				<div id="datetimepicker2" class="input-append date">
					<input data-format="hh:mm" class="shop-state open-time" type="text"
						ng-model="cal.startTime" name="startTime" /> <span class="add-on">
						<i data-time-icon="icon-time" data-date-icon="icon-calendar">
					</i>
					</span>
				</div>

				<table class="shop_table">
					<thead>
						<tr>
							<th>开店日期</th>
							<th>开店时间</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody ng-class-odd="'tbody_show_tr_diff'"
						ng-class-even="'tbody_show_tr_diff2'"
						ng-repeat="calendar in calendars">
						<tr>
							<td>{{calendar.calendarYear}}年{{calendar.calendarMonth}}月{{calendar.calendarDay}}日</td>
							<td>{{calendar.openTime}}</td>
							<td>系统默认</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="self-inspection-popover-footer">
				<div class="dialogTip"></div>
				<div class="confirm_btn dialog-btn" ng-click="saveOpen(cal)">确定</div>
				<div class="close_btn dialog-btn">取消</div>
			</div>
		</div>

		<div class="shop-content shop-close">
			<div class="shop-panel close-shop">

				闭店日期：
				<div id="datetimepicker3" class="input-append date">
					<input data-format="yyyy-MM-dd" class="shop-state open-date"
						type="text" name="endDate" /> <span class="add-on"> <i
						data-time-icon="icon-time" data-date-icon="icon-calendar"> </i>
					</span>
				</div>
				闭店时间：
				<div id="datetimepicker4" class="input-append date">
					<input data-format="hh:mm" class="shop-state open-time" type="text"
						name="endTime" /> <span class="add-on"> <i
						data-time-icon="icon-time" data-date-icon="icon-calendar"> </i>
					</span>
				</div>
				<table class="shop_table">
					<thead>
						<tr>
							<th>闭店日期</th>
							<th>闭店时间</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody ng-class-odd="'tbody_show_tr_diff'"
						ng-class-even="'tbody_show_tr_diff2'"
						ng-repeat="calendar in calendars">
						<tr>
							<td>{{calendar.calendarYear}}年{{calendar.calendarMonth}}月{{calendar.calendarDay}}日</td>
							<td>{{calendar.closeTime}}</td>
							<td>系统默认</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="self-inspection-popover-footer">
				<div class="dialogTip"></div>
				<div class="confirm_btn dialog-btn" ng-click="saveClose()">确定</div>
				<div class="close_btn dialog-btn">取消</div>
			</div>
		</div>

	</div>
	<div class="dialog-popover self-inspection-popover">
		<div class="dialog-header">
			<p class="dialog-title">子系统服务概况</p>
			<div class="dialog-close close_pop">×</div>
		</div>
		<div class="self-inspection-popover-content">
			<div class="left_nav">
				<ul class="nav_list_ul">
					<li>
						<h1>服务运行状况</h1>
						<p>无服务问题</p>
					</li>
					<li>
						<h1>消息中心</h1>
						<p>今日有5条消息</p>
					</li>
					<li>
						<h1>计划内维护</h1>
						<p>未安排计划内维护</p>
					</li>
				</ul>
			</div>
			<div class="right_content_block">
				<div class="current_block">
					<h1>当前运行状态</h1>
					<div class="mode_list_block">
						<ul class="mode_list">
							<li>
								<div class="sys_name">暖通空调</div>
								<div class="run_mode" id="HAVC_CHECK_PATTERN"></div>
								<div class="has_info">
									<img
										src="${pageContext.request.contextPath}/resources/images/information.png">
								</div>
							</li>
							<li>
								<div class="sys_name">给排水</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">变配电</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">火灾报警</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">消防漏电</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">消防水泡</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">视频监控</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">门禁系统</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">公共照明</div>
								<div class="run_mode" id="LSPUB_CHECK_PATTERN"></div>
								<div class="has_info">
									<img
										src="${pageContext.request.contextPath}/resources/images/information.png">
								</div>
							</li>
							<li>
								<div class="sys_name">夜景照明</div>
								<div class="run_mode" id="LSN_CHECK_PATTERN"></div>
								<div class="has_info">
									<img
										src="${pageContext.request.contextPath}/resources/images/information.png">
								</div>
							</li>
							<li>
								<div class="sys_name">信息发布</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">停车管理</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">背景音乐</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">电梯监控</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">能耗计量</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
						</ul>
					</div>
				</div>
				<div ng-controller="msgCenter">
					<table class="now_info">
						<thead>
							<tr>
								<th class="title_now_info"><h2>当前消息</h2></th>
								<th class="title_operate"><h2>需要执行的操作</h2></th>
							</tr>
						</thead>
						<tbody ng-class-odd="'tbody_show_tr_diff'"
							ng-class-even="'tbody_show_tr_diff2'"
							ng-repeat="itemEvent in itemEvents">
							<tr>
								<td style="font-size: 16px;">{{itemEvent.name}}</td>
								<td>无</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div>
					<div class="now_operate_state">
						<h1>当前运行状态</h1>
						<div>
							<p>公共照明子系统将于今天23：00至明天2：00进行维护</p>
							<h4>公共照明子系统</h4>
						</div>
						<div>
							<p>变配电子系统将于今天2：30至明天3：00进行维护</p>
							<h4>变配电子系统</h4>
						</div>
						<div>
							<p>夜景照明子系统将于今天6：00至明天10：00进行维护</p>
							<h4>夜景照明子系统</h4>
						</div>
						<div>
							<p>视频监控子系统将于今天15：00至明天16：00进行维护</p>
							<h4>视频监控子系统</h4>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="self-inspection-popover-footer">
			<div class="lunxun_btn dialog-btn">视频轮巡</div>
			<div class="ok_btn dialog-btn">确定</div>
		</div>
	</div>

</div>
<%-- 提示框内容Start --%>
<div class="custom_info_box" style="display: none;">
	<span class="triangle-up"></span> <a href="#" class="close_btn"></a>
	<div class="wd">
		<div class="wd_l">
			<div class="wd_icon"></div>
		</div>
		<div class="wd_r">
			<div class="wd_title">
				<span id="tipText">万达百货</span><span class="sp_video"></span>
			</div>
			<p id="tipNum" style="display: none;"></p>
			<%--<p>联系电话： 13717850772</p>--%>
			<div class="wd_chart">
				<div class="wd_chart_pic" id="tipChart">
					<div class="wd_chart_time">今日</div>
				</div>
				<div class="wd_chart_state">
					<p class="wd_custom_list">
						<a href="#" onClick="trendChart('in', 'tip')" class="trendChart">总进</a>|<a
							href="#" onClick="trendChart('out', 'tip')">总出</a>|<a href="#"
							onClick="trendChart('inside', 'tip')" style="display: none;">场内</a>
					</p>
					<span class="wd_chart_tit">客流趋势图</span>
				</div>
			</div>
		</div>
	</div>
	<div class="wd_bottom">
		<ul>
			<li><span>总进</span><cite id="zongjin"></cite></li>
			<li><span>总出</span><cite id="zongchu"></cite></li>
			<li><span>场内</span><cite id="changnei" class="red"></cite></li>
		</ul>
	</div>
</div>
<%-- 提示框内容End --%>


<script src="<spring:url value="/resources/scripts/mctrl/submenu.js" />"></script>
<script src="<spring:url value="/resources/plugins/knockout/knockout-3.0.0.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/sockjs/sockjs.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/stomp/dist/stomp.js"></spring:url>"></script>
<!-- application -->
<script src="<spring:url value="/resources/scripts/okcsys/configuration.js"></spring:url>"></script>
<script src="<spring:url value="/resources/scripts/mctrl/mctrl.js"></spring:url>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/pfe/pfecommon.js"></script>
<script src="<spring:url value="/resources/scripts/highcharts.js"></spring:url>"></script>
<script src="<spring:url value="/resources/scripts/pfe/pfeConfig.js"></spring:url>"></script>

<script type="text/javascript">
	var base_url = "${pageContext.request.contextPath}";
	var pageNum = 1; // 当前页
	var pageCount = 1; // 总页数
	var pageSize = 15; // 每页显示数量
	$(function() {
		$('.ui-draggable').draggable();
		// 只在URL中存在PFE的情况下进来处理客流数据
		if("${fn:contains(requestURL,'/PFE')}" == "true") {
			//buildChart();
		}
		
		// 初始化查询
		if(elementvalue == 'EP') { // 电子巡更子系统查询巡更记录
			var Nowdate = new Date();
			Nowdate.setDate(Nowdate.getDate()-1);
			var year = Nowdate.getFullYear();
			var month = (Nowdate.getMonth()+1)+"";
			month = (month.length == 1 ? ("0" + month) : month);
			var day = Nowdate.getDate()+"";
			day = day.length == 1 ? ("0" + day) : day;
			$("#startDate").val(year+"-"+month+"-"+day);
			selectShow();
		}
		
		$("#chooseexp").select2();
		$("#selectexp_0").select2();
	});
	
	// 电子巡更分页
	function selectPatrolFy(para) {
		var pagef = "";
		if (para == "Before") {
			if (pageNum > 1) {
				pageNum = pageNum - 1;
			}
			pagef = pageNum;
		}
		if (para == "After") {
			if (pageNum < pageCount) {
				pageNum = pageNum + 1;
			}
			pagef = pageNum;
		}
		if (para == "End") {
			pageNum = pageCount;
			pagef = pageCount;
		}
		if (para == "First") {
			pageNum = 1;
			pagef = 1;
		}
		if (pageCount == "0") {
			pagef = 0;
		}
		$(".bc").text(pagef);
		var checkDate = $("#startDate").val();
		getSubPatrols(pageNum, pageSize, checkDate);

	}
	
	// 点击查询事件
	function selectShow() {
		var checkDate = $("#startDate").val();
		getSubPatrols(pageNum, pageSize, checkDate);
	}
	
	// 与后台交互，发送查询，处理返回数据
	function getSubPatrols(pageNum, pageSize, checkDate) {
		var url = base_url + '/elecpatrol/getPatrolList';
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			data : {
				pageNum : pageNum,
				pageSize : pageSize,
				checkDate : checkDate
			},
			success : function(data) {
				console.log(data);
				var dataHtml = '';
				var sub;
				for (var i = 0; i < data.pageItems.length; i++) {
					sub = data.pageItems[i];
					dataHtml += '<tr><td>'+sub.id+'</td><td>'+sub.lineNum+'</td><td>'+sub.lineName+'</td><td>'+sub.startTime+'</td><td>'+sub.endTime+'</td><td>'+sub.checkTime+'</td><td>'+sub.checkResult+'</td>'
						+'<td>'+sub.userName+'</td><td>'+sub.missedNum+'</td><td>'+sub.onTimeNum+'</td><td>'+sub.earlyNum+'</td><td>'+sub.lateNum+'</td><td>'+sub.shifts+'</td></tr>';
				}
				$("#tbodyContent tr:gt(0)").remove();
				$("#tbodyContent").append(dataHtml);
				pageCount = data.pagesAvailable;
				$(' .paging .pageCount').html(pageCount);
			},
			error : function(e) {
				console.log("get EpList error---:" + e);
			}
		});
	}
</script>