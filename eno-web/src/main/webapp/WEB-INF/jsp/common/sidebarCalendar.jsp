<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    //显示菜单元素，只有子元素的菜单添加过滤链接
%>
<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="request"/>
<script src="${pageContext.request.contextPath}/resources/scripts/pattern/showMenu.js"></script>
<div class="left_menu">
    <table id="MENU_START">
    </table>
</div>
<script type = "text/javascript" charset="utf-8">
window.onload=getMenu();
function getMenu()		// 获取菜单 [ ChengKang 2014-08-28 ]
{
    var systemCode ='${systemCode}';
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
//					HtmlStr += "<td><i class=\"icon_"+ ChildrenJson[j].code.toLowerCase() +"\"></i>";
//					if(ChildrenJson[j].url != "undefined" && ChildrenJson[j].url != "")
                    if(systemCode == ChildrenJson[j].code){
                        HtmlStr += "<td style=\"background-color:#39a6ae\"><i class=\"icon_"+ ChildrenJson[j].code.toLowerCase() +"\"></i>";
                    }else{
                        if((systemCode=='CoolingSource'||systemCode=='HeatSource'||systemCode=='FAVU'||systemCode=='MAHU'||systemCode=='BUAHU'||systemCode=='AVU')&&(ChildrenJson[j].code=='HVAC')){
                            HtmlStr += "<td style=\"background-color:#39a6ae\"><i class=\"icon_"+ ChildrenJson[j].code.toLowerCase() +"\"></i>";
                        }else{
                            HtmlStr += "<td><i class=\"icon_"+ ChildrenJson[j].code.toLowerCase() +"\"></i>";
                        }
                    }


					if(ChildrenJson[j].code != "undefined" && (ChildrenJson[j].code == "LSPUB" || ChildrenJson[j].code == "LSN" || ChildrenJson[j].code == "HVAC"))
					{
						HtmlStr += "<a href=\"${pageContext.request.contextPath}/calendar/showMonthCalendar?systemId="+ ChildrenJson[j].code +"\">";
						HtmlStr += ChildrenJson[j].name;
						HtmlStr += "</a></td>";
					}
					else
					{
						HtmlStr += "<a href=\"javascript:void('0')\" style='cursor:default'>"+ChildrenJson[j].name +"</a>";
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