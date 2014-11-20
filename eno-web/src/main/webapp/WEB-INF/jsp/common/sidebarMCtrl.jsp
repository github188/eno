<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<script type = "text/javascript" charset="utf-8">
window.onload=getMenu();
function getMenu()		// 获取菜单 [ ChengKang 2014-08-28 ]
{
	//console.log("getMenu");
	// 依据URL地址，获取当前页面二级菜单的编码和进入的三级或四级菜单的MuneId [ ChengKang 2014-08-29 ]
	var path = window.location.href;
	var pathsub = path.split("/");
	var TitleCode = pathsub[pathsub.length-2];
	var MenuHtmSub = pathsub[pathsub.length-1].split(".");
	var MenuId = MenuHtmSub[0];
	var TitleName = "";
	
	// 获取整个目录的JSON结构并绘制一级、二级菜单 [ ChengKang 2014-08-29 ]
	$.post(CONTEXT_PATH + '/okcsys/okcmenu/init', {}, 
	function(data)
	{
		if(data != null && typeof(data) != "undefined" && data.length>0)
		{
			//console.log(data);
			var MenuJson = $.parseJSON(data);
			var HtmlStr = "";
			HtmlStr += "<tbody>";
			for(var i=0; i<MenuJson.length; i++)
			{
				HtmlStr += "<tr>";
				HtmlStr += "<td class=\"" + MenuJson[i].code + " bt\"";
				if(MenuJson[i].children.length>1)
				{
					HtmlStr += " rowspan=\"" + MenuJson[i].children.length + "\"";
				}
				HtmlStr += ">";
				if(MenuJson[i].children.length <= 2)
				{
                    var stemp=MenuJson[i].name;
                    if(stemp.indexOf("PD")>=0){
                        HtmlStr += MenuJson[i].name.substr(0, 3) + "<br>";
                        HtmlStr += MenuJson[i].name.substr(3);
                    }else{
                        HtmlStr += MenuJson[i].name.substr(0, 2) + "<br>";
                        HtmlStr += MenuJson[i].name.substr(2);
                    }

				}
				else
				{
					for(var j=0; j<MenuJson[i].children.length-1; j++)
					{
						HtmlStr += MenuJson[i].name.substr(j, 1) + "<br>";
					}
					HtmlStr += MenuJson[i].name.substr(MenuJson[i].children.length-1, 1);
				}
				HtmlStr += "</td>";
				
				var ChildrenJson = MenuJson[i].children;
				var childrenNum = ChildrenJson.length;
				for(var j=0; j<childrenNum; j++)
				{
					if(j>0)
					{
						HtmlStr += "<tr>";
					}
					if(TitleCode == ChildrenJson[j].code)
					{
						HtmlStr += "<td class=\"cur\">";
						TitleName = ChildrenJson[j].name;
					}
					else
					{
						HtmlStr += "<td>";
					}
					HtmlStr += "<i class=\"icon_"+ ChildrenJson[j].code.toLowerCase() +"\"" +"id=\""+ChildrenJson[j].code+"\"></i>";
					if( ChildrenJson[j].url != "undefined" && ChildrenJson[j].url != "")
					{
						HtmlStr += "<a href=\""+ ChildrenJson[j].url +"\">";
						HtmlStr += ChildrenJson[j].name;
						HtmlStr += "</a></td>";
					}
					else
					{
						HtmlStr += ChildrenJson[j].name;
						HtmlStr += "</td>";
					}
					HtmlStr += "</tr>";
				}
			}
			HtmlStr += "</tbody>";
			//console.log(HtmlStr);
			$(HtmlStr).insertAfter("#MENU_START");
		}
		
		// 根据菜单编码，获得菜单名，即得到二级菜单的名称 [ ChengKang 2014-08-29 ]
		
		// 根据菜单编码得到其所有子菜单，得到一个JSONArray字符串 [ ChengKang 2014-08-29 ]
		var ChildrenJsonStr = "";
		$.post(CONTEXT_PATH + '/okcsys/okcmenu/children', {"menuCode" : TitleCode }, 
		function(data)
		{
			if(data != null && typeof(data) != "undefined" && data.length>0)
			{
				//console.log("ChildrenJsonStr" + data);
				ChildrenJsonStr = data;
			}
			
			// 根据返回的子菜单JSONArray字符串解析三、四级菜单结构，并组建成HTML [ ChengKang 2014-08-29 ]
			var MenuChildrenJson = ChildrenJsonStr;
			var ChildrenHtmlStr = "<ul class=\"cataloge_class_one\"><li><div id=\"ascplanar\" >";
			ChildrenHtmlStr += "<div class=\"cataloge_name\">" + TitleName + " </div>";		// 菜单标题  [ ChengKang 2014-08-29 ]
			
			//console.log("ChildrenHtmlStr" + ChildrenHtmlStr);
			
			if(MenuChildrenJson!=null && MenuChildrenJson.length > 0)
			{
				ChildrenHtmlStr += "<ul class=\"cataloge_class_two\">";
				// 对JSONArray循环获取三级目录结构信息，并组建HTML [ ChengKang 2014-08-29 ]
				for(var i=0; i<MenuChildrenJson.length; i++)
				{
					// 三级目录 [ ChengKang 2014-08-29 ]
					if(MenuChildrenJson[i].menuid != MenuId)
					{
						ChildrenHtmlStr += "<li ";
					}
					else
					{
						ChildrenHtmlStr += "<li class=\"current\" ";
					}
					ChildrenHtmlStr += "id=\"" + MenuChildrenJson[i].code + "\">";
					if(MenuChildrenJson[i].url == "")
					{
						ChildrenHtmlStr += "<a href=\"#\">";
					}
					else
					{
						ChildrenHtmlStr += "<a href=\"" + MenuChildrenJson[i].url + "\">";
					}
					ChildrenHtmlStr += MenuChildrenJson[i].name + "</a>";
					ChildrenHtmlStr += "</li>"
					
					// 解析四级目录 [ ChengKang 2014-08-29 ]
					var ChildChildrenJson = MenuChildrenJson[i].children;
					var ChildChildrenNum = ChildChildrenJson.length;
					if(ChildChildrenNum > 0)
					{
						// 查看选择的四级目录是否是是当前的三级的下级，如果不是，就不需要显示该层四级目录
						var bool_show = false;
						for(var n=0; n<ChildChildrenNum; n++)
						{
							if(ChildChildrenJson[n].menuid == MenuId)
							{
								bool_show = true;
								break;
							}
						}
						
						if(bool_show)
						{
							ChildrenHtmlStr += "<li class=\"cataloge_class_three\"><ul>";
							for(var j=0; j<ChildChildrenNum; j++)
							{
								if(ChildChildrenJson[j].menuid != MenuId)
								{
									ChildrenHtmlStr += "<li ";
								}
								else
								{
									ChildrenHtmlStr += "<li class=\"current\" ";
								}
								ChildrenHtmlStr += "parentid=\"" + MenuChildrenJson[i].code + "\">";
								ChildrenHtmlStr += "<a href=\"" + ChildChildrenJson[j].url + "\" ";
								ChildrenHtmlStr += "title=\"" +MenuChildrenJson[i].name +  ChildChildrenJson[j].name + "\" ";
								ChildrenHtmlStr += "menuid=\"" + ChildChildrenJson[j].menuid + "\">";
								ChildrenHtmlStr += ChildChildrenJson[j].name;
								ChildrenHtmlStr += "</a></li>";
							}
							ChildrenHtmlStr += "</ul></li>";
						}
						
					}
				}
				ChildrenHtmlStr += "</ul>";
			}
			
			ChildrenHtmlStr += "</div></li></ul><div class=\"mode_cataloge_mb\"></div>";
			
			//console.log("ChildrenHtmlStr" + ChildrenHtmlStr);
			$(ChildrenHtmlStr).insertAfter("#CHILDREN_MENU_START");
            console.log("--TitleCode----"+TitleCode);
            changePattern(TitleCode);
            // 加载完菜单后   初始化 照明下发面板
            initLight();
		})
	})
}
</script>

<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="request" />
<div class="left_menu">
    <table id="MENU_START">
    </table>
    <div class="left_menu_mb"></div>
</div>

<!-- 子菜单项 -->
<div class="mode_cataloge">
	<table id="CHILDREN_MENU_START"></table>
</div>
<div class="span10 right_content nomargin" >
	<div class="menu_layout">
        <script type="text/javascript" src="<spring:url value="/resources/scripts/slidebar.js"/>" ></script>
		<c:if test="${fn:contains(requestURL,'/HVAC') or fn:contains(requestURL,'/LSPUB') or fn:contains(requestURL,'/LSN')}">
		<div class="mode_switch">
			<ul>
                <c:if test="${fn:contains(requestURL,'/HVAC')}">
				  <li class="current_mode" id="automode"><p>自动运行</p>
					<div class="countdown">
						<p><!-- （夏季供冷平日） --></p>
					</div></li>
				 <li style="display: none;"></li>
				<c:if test="${fn:substring(sidebarMCtrs.HVAC,0,1) eq 'M'}">
				<li id="handmode"><p>手动模式</p>
					<div class="countdown">
						<p><!-- （夏季供冷平日） --></p>
					</div></li>
				<li style="display: none;"></li>
				 </c:if>
				 <c:if test="${fn:substring(sidebarMCtrs.HVAC,1,2) eq 'C'}">
				<li><p onclick="javasript:closeAllWindow();showCreateModeApplyDialog();">模式切换</p>
					<div class="countdown"></div></li>
				 </c:if>
			  </c:if>
			  <c:if test="${fn:contains(requestURL,'/LSPUB')}">
				  <li class="current_mode" id="automode"><p>自动运行</p>
					<div class="countdown">
						<p><!-- （夏季供冷平日） --></p>
					</div></li>
				<li style="display: none;"></li>
				<c:if test="${fn:substring(sidebarMCtrs.LSPUB,0,1) eq 'M'}">  	
				<li id="handmode"><p>手动模式</p>
					<div class="countdown">
						<p><!-- （夏季供冷平日） --></p>
					</div></li>
				<li style="display: none;"></li>	
				 </c:if>
				 <c:if test="${fn:substring(sidebarMCtrs.LSPUB,1,2) eq 'C'}">  		
				<li><p onclick="javasript:closeAllWindow();showCreateModeApplyDialog();">模式切换</p>
					<div class="countdown"></div></li>
				 </c:if>	
			  </c:if>
			  <c:if test="${fn:contains(requestURL,'/LSN')}">
				  <li class="current_mode" id="automode"><p>自动运行</p>
					<div class="countdown">
						<p><!-- （夏季供冷平日） --></p>
					</div></li>
				<li style="display: none;"></li>	
				<c:if test="${fn:substring(sidebarMCtrs.LSN,0,1) eq 'M'}">  	
				<li id="handmode"><p>手动模式</p>
					<div class="countdown">
						<p><!-- （夏季供冷平日） --></p>
					</div></li>
				<li style="display: none;"></li>	
				 </c:if>
				 <c:if test="${fn:substring(sidebarMCtrs.LSN,1,2) eq 'C'}">  		
				<li><p onclick="javasript:closeAllWindow();showCreateModeApplyDialog();">模式切换</p>
					<div class="countdown"></div></li>
				 </c:if>	
			  </c:if>
			</ul>
		</div>
		</c:if>
			<!-- 根据不同的子系统设置不同的控制按钮，如：能源管理 -->
		   <c:choose>
		   	<c:when test="${fn:contains(requestURL,'/ENVMS')}">
			   	<div class="auto_btn">
				<button id="btn_asccompn" class="Btn-big system_construction_drawing energyDashbord" type="button"><i class="icon_thrc"></i> 能耗仪表盘 </button>			
				<button id="btn_ascplanar" class="Btn-big cur energyChart" type="button"><i class="icon_chart"></i> 能耗分析</button>
			   </div>
		   	</c:when>
               <c:when test="${fn:contains(requestURL,'/PFE11111')}">
                   <div class="auto_btn [${okcMenu.views}]">
                       <button id="btn_asccompn" class="Btn-big passengerview system_construction_drawing cur " type="button"><i class="icon_chart"></i> 客流量视图 </button>
                       <button id="btn_ascplanar" class="Btn-big inperson" type="button"><i class="icon_thrc"></i> 场内人数</button>
                   </div>
               </c:when>
		   <c:otherwise>

				<!-- 修改了三个视图的位置顺序 [ ChengKang 2014-09-17 ] -->
				<c:if test="${not fn:contains(okcMenu.views,'LIST')}">
					<c:set var="css_asc" value="disabled disabledTab"></c:set>
				</c:if>
				<c:if test="${not fn:contains(okcMenu.views,'STRUCTURE')}">
					<c:set var="css_comp" value="disabled disabledTab"></c:set>
				</c:if>
				<c:if test="${not fn:contains(okcMenu.views,'PLAN')}">
					<c:set var="css_planar" value="disabled disabledTab"></c:set>
				</c:if>
				<div class="auto_btn">
				
				
				<ul class="nav-tabs" id="pagetabs">
					<!--
					<li class="${css_planar}"><a href="#PLAN" data-toggle="tab"><i class="icon_chart"></i>${fn:split(okcMenu.viewnames,',')[2]}</a></li>
					<li class="${css_comp}"><a href="#STRUCTURE" data-toggle="tab"><i class="icon_thrc"></i>${fn:split(okcMenu.viewnames,',')[1]}</a></li>
					<li class="${css_asc}"><a href="#LIST" data-toggle="tab"><i class="icon_list"></i>${fn:split(okcMenu.viewnames,',')[0]}</a></li>
					  -->
					  <!-- 修改了三个视图的位置顺序 [ ChengKang 2014-09-17 ] -->
					<li class="${css_asc}"><a href="#LIST" data-toggle="tab"><i class="icon_list"></i>设备列表</a></li>
					<li class="${css_comp}"><a href="#STRUCTURE" data-toggle="tab"><i class="icon_thrc"></i>系统结构图</a></li>
					<li class="${css_planar}"><a href="#PLAN" data-toggle="tab"><i class="icon_chart"></i>系统平面图</a></li>
				</ul>
			   </div>
		   </c:otherwise>
		   </c:choose>
		   
		   
		  
		   
		   
	</div>
<script type="text/javascript">
	var wsUrl = "<spring:url value="/ws"></spring:url>";
	var elementvalue = "${elementvalue}"; // 模块id（如：PFE）
	var menuId = "${menuid}";
	var layoutid = "${currentPlanPagelayout.layoutid}";
	var views = "${okcMenu.views}";
	var defaultView = "${okcMenu.defaultView}";
</script>
    <%--<c:if test="${fn:contains(requestURL,'/LSPUB')}">--%>
        <%--<%@ include file="../mctrl/lspub.jsp"%>--%>
    <%--</c:if>--%>

    <%--<c:if test="${fn:contains(requestURL,'/LSN')}">--%>
        <%--<%@ include file="../mctrl/lsn.jsp"%>--%>
    <%--</c:if>--%>

	<c:if test="${fn:contains(requestURL,'/ENVMS')}">
		<%@ include file="../mctrl/energyDashbord.jsp"%>
		<%@ include file="../mctrl/energyChart.jsp"%>
	</c:if>
	
<%-- 	<c:if test="${fn:contains(requestURL,'/PFE11111')}"> --%>
<%-- 		<%@ include file="../pfe/passengerView.jsp"%> --%>
<%-- 	</c:if> --%>
</div>



