<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
//显示菜单元素，只有子元素的菜单添加过滤链接
%>
<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="request" />
<script src="${pageContext.request.contextPath}/resources/scripts/pattern/showMenu.js"></script>


<div class="left_menu">
    <table id="MENU_START">
    </table>
</div>

<%--子菜单--%>
<c:if test="${!fn:contains(requestURL,'showGlobalPatternNoTime')}">
    <div class="mode_cataloge">
        <ul class="cataloge_class_one">
            <li>
                <c:if test="${hasAsc}">
                    <div id="asc">
                        <c:forEach items="${ascChildrenItems}" var="item" varStatus="status">
                            <c:if test="${fn:toUpperCase( item.okcMenu.elementtype) eq 'THEADER'}">
                                <div class="cataloge_name"><c:out value="${item.okcMenu.headerdescription}"></c:out> </div>
                            </c:if>
                            <c:if test="${fn:toUpperCase( item.okcMenu.elementtype) ne 'THEADER'}">
                                <ul class="cataloge_class_two" ${systemCode}>

                                    <c:if test="${!fn:contains(requestURL,'nt')}">
                                        <c:if test="${fn:contains(item.okcMenu.url,'nt')}">
                                            <li  class="" id="${item.okcMenu.elementvalue}"><a href="${pageContext.request.contextPath}${item.okcMenu.url}" title="${item.okcMenu.headerdescription}" >${item.okcMenu.headerdescription}</a></li>
                                        </c:if>

                                        <c:if test="${!fn:contains(item.okcMenu.url,'nt')}">
                                            <li  class="<c:if test="${fn:contains(item.okcMenu.elementvalue,systemCode) }">current</c:if>" id="${item.okcMenu.elementvalue}"><a href="${pageContext.request.contextPath}${item.okcMenu.url}" title="${item.okcMenu.headerdescription}" >${item.okcMenu.headerdescription}</a></li>
                                        </c:if>
                                    </c:if>

                                    <c:if test="${fn:contains(requestURL,'nt')}">
                                        <c:if test="${!fn:contains(item.okcMenu.url,'nt')}">
                                            <li  class="" id="${item.okcMenu.elementvalue}"><a href="${pageContext.request.contextPath}${item.okcMenu.url}" title="${item.okcMenu.headerdescription}" >${item.okcMenu.headerdescription}</a></li>
                                        </c:if>

                                        <c:if test="${fn:contains(item.okcMenu.url,'nt')}">
                                            <li  class="<c:if test="${fn:contains(item.okcMenu.url,'nt')}">current</c:if>" id="${item.okcMenu.elementvalue}"><a href="${pageContext.request.contextPath}${item.okcMenu.url}" title="${item.okcMenu.headerdescription}" >${item.okcMenu.headerdescription}</a></li>
                                        </c:if>
                                    </c:if>
                                </ul>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:if>
            </li>
        </ul>
    </div>
</c:if>
<script type = "text/javascript" charset="utf-8">
window.onload=getMenu();
function getMenu()		// 获取菜单 [ ChengKang 2014-08-28 ]
{
    var path = window.location.href;
    var pathsub = path.split("/");
    var TitleCode = pathsub[pathsub.length-2];
    var MenuHtmSub = pathsub[pathsub.length-1].split(".");
    var systemCode = MenuHtmSub[0];
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
					HtmlStr += MenuJson[i].name.substr(0, 2) + "<br>";
					HtmlStr += MenuJson[i].name.substr(2);
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
                    if(systemCode == ChildrenJson[j].code){
                        HtmlStr += "<td style=\"background-color:#39a6ae\"><i class=\"icon_"+ ChildrenJson[j].code.toLowerCase() +"\"></i>";
                    }else{
                        if((systemCode=='CoolingSource'||systemCode=='HeatSource'||systemCode=='FAVU'||systemCode=='MAHU'||systemCode=='BUAHU'||systemCode=='AVU')&&(ChildrenJson[j].code=='HVAC')){
                            HtmlStr += "<td style=\"background-color:#39a6ae\"><i class=\"icon_"+ ChildrenJson[j].code.toLowerCase() +"\"></i>";
                        }else{
                            HtmlStr += "<td><i class=\"icon_"+ ChildrenJson[j].code.toLowerCase() +"\"></i>";
                        }
                    }

					if( ChildrenJson[j].url != "undefined" && ChildrenJson[j].url != "")
					{
                        if(ChildrenJson[j].code.toUpperCase()=='LSPUB' || ChildrenJson[j].code.toUpperCase()=='LSN' || ChildrenJson[j].code.toUpperCase()=='HVAC'){
                            HtmlStr += "<a href="+CONTEXT_PATH+"/pattern/menu/"+ ChildrenJson[j].code.toUpperCase() +".html>";
                            HtmlStr += ChildrenJson[j].name;
                            HtmlStr += "</a></td>";
                        }else{
                            HtmlStr += "<a href='#' style='cursor:default'>";
                            HtmlStr += ChildrenJson[j].name;
                            HtmlStr += "</a></td>";
                        }

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
	})
}
</script>
