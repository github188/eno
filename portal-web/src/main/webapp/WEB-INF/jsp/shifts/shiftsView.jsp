<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main.css">
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/runManage.css">
<script type="text/javascript"
    src="${pageContext.request.contextPath}/resources/scripts/jquery-migrate-1.2.1.min.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/runManage.js"></script>
<div class="span12 main row-fluid">
<!-- 	<div class="top_btn_group"> -->
<!-- 		<div> -->
<!-- 		    <div class="Btn-big cur nomargin"><a href="shiftsView"><i class="icon_btn icon_bag"></i><p>班次管理</p></a></div> -->
<!-- 			<div class="Btn-big"><a href="shiftworkView"><i class="icon_btn icon_key"></i><p>交接班</p></a></div> -->
<!-- 			<div class="Btn-big"><a href="operatingView"><i class="icon_btn icon_book"></i><p>运行记录</p></a></div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<div class="table_frame" style="margin-left: 40px;">
		<div class="select_bar">
			<div class="btn-small nomargin edit">开启编辑</div>
			<div class="btn-small" style="width: 88px" onclick="editShiftTypes();">班次维护</div>
			<div class="year_sele">
				<div class="left-icon"  onclick="nextOrbefore('before');"></div>
				<div class="btn-small nowYear">${yearNow}</div>
				<div class="right-icon" onclick="nextOrbefore('next');"></div>
			</div>
			  <ul>
				<c:if test="${monthchange eq '1'}">
					<li class="btn-small" onclick="change(1)">1月</li>
				</c:if>
				<c:if test="${monthchange != '1'}">
					<li onclick="change(1)">1月</li>
				</c:if>
				<c:if test="${monthchange eq '2'}">
					<li class="btn-small" onclick="change(2)">2月</li>
				</c:if>
				<c:if test="${monthchange != '2'}">
					<li onclick="change(2)">2月</li>
				</c:if>
				<c:if test="${monthchange eq '3'}">
					<li class="btn-small" onclick="change(3)">3月</li>
				</c:if>
				<c:if test="${monthchange != '3'}">
					<li onclick="change(3)">3月</li>
				</c:if>
				<c:if test="${monthchange eq '4'}">
					<li class="btn-small" onclick="change(4)">4月</li>
				</c:if>
				<c:if test="${monthchange != '4'}">
					<li onclick="change(4)">4月</li>
				</c:if>
				<c:if test="${monthchange eq '5'}">
					<li class="btn-small" onclick="change(5)">5月</li>
				</c:if>
				<c:if test="${monthchange != '5'}">
					<li onclick="change(5)">5月</li>
				</c:if>
				<c:if test="${monthchange eq '6'}">
					<li class="btn-small" onclick="change(6)">6月</li>
				</c:if>
				<c:if test="${monthchange != '6'}">
					<li onclick="change(6)">6月</li>
				</c:if>
				<c:if test="${monthchange eq '7'}">
					<li class="btn-small" onclick="change(7)">7月</li>
				</c:if>
				<c:if test="${monthchange != '7'}">
					<li onclick="change(7)">7月</li>
				</c:if>
				<c:if test="${monthchange eq '8'}">
					<li class="btn-small" onclick="change(8)">8月</li>
				</c:if>
				<c:if test="${monthchange != '8'}">
					<li onclick="change(8)">8月</li>
				</c:if>
				<c:if test="${monthchange eq '9'}">
					<li class="btn-small" onclick="change(9)">9月</li>
				</c:if>
				<c:if test="${monthchange != '9'}">
					<li onclick="change(9)">9月</li>
				</c:if>
				<c:if test="${monthchange eq '10'}">
					<li class="btn-small" onclick="change(10)">10月</li>
				</c:if>
				<c:if test="${monthchange != '10'}">
					<li onclick="change(10)">10月</li>
				</c:if>
				<c:if test="${monthchange eq '11'}">
					<li class="btn-small" onclick="change(11)">11月</li>
				</c:if>
				<c:if test="${monthchange != '11'}">
					<li onclick="change(11)">11月</li>
				</c:if>
				<c:if test="${monthchange eq '12'}">
					<li class="btn-small" onclick="change(12)">12月</li>
				</c:if>
				<c:if test="${monthchange != '12'}">
					<li onclick="change(12)">12月</li>
				</c:if>
			</ul>
			<div class="btn-small thisMonth" onclick="change(13)">本月</div>
		</div>
		<div class="division_line"></div>
		<table style="width: 1700px;">
            <thead>
                <tr class="table_title">
                    <th class="th_job">职位</th>
                    <th class="th_date_name"><p class="date_mark">日期</p><p class="name">姓名</p></th>
                    <c:forEach items="${dateToWeeks}" var="dateToWeek">
                        <th class="day_week">
                            <p>${dateToWeek.dat}</p><p>${dateToWeek.week}</p>
                        </th>
                    </c:forEach>
                    <th class="tel">联系电话</th>
                </tr>
            </thead>
			<tbody id="content">
				<c:forEach items="${shiftsListView}" var="shifts">
				  <tr>
				  <c:if test="${shifts.deptname!= ''}">
					<td rowspan="${fn:split(shifts.description,'&')[1]}" class="th_job_2">${shifts.deptname}</td>
					<td class="th_date_name_2">${shifts.name}</td>
					 </c:if>
					 <c:if test="${shifts.deptname eq ''}">
					 <td class="th_date_name_2">${shifts.name}</td>
					 <td style="display:none"></td>
					 </c:if>
					 <c:forEach items="${shifts.dateToWeeklist}" var="dateToWeek">
					 <td onclick="show('${dateToWeek.shiftsid}','${fn:split(shifts.description,'&')[0]}','${dateToWeek.wzDate}','${dateToWeek.shifttype}')">${dateToWeek.shifttypeName}</td>
					 </c:forEach>
				     <td class="tel_2">${shifts.mobile}</td>
				  </tr>
				 </c:forEach>
			</tbody>
			<tfoot>
			<c:if test="${fn:length(shifttypesCjList)> 0}">
				<tr>
					<td class="notes" colspan="34">春季：
					 <c:forEach items="${shifttypesCjList}" var="shifttypes">
	                                   ${shifttypes.shifttype}&nbsp(${shifttypes.description})&nbsp&nbsp&nbsp
                      </c:forEach>
					</td>
				</tr>
			</c:if>	
			<c:if test="${fn:length(shifttypesXjList)> 0}">
				<tr>
					<td class="notes" colspan="34">夏季：
					<c:forEach items="${shifttypesXjList}" var="shifttypes">
	         ${shifttypes.shifttype}&nbsp(${shifttypes.description})&nbsp&nbsp&nbsp
                    </c:forEach>
					</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(shifttypesQjList)> 0}">
				<tr>
					<td class="notes" colspan="34">秋季：
					<c:forEach items="${shifttypesQjList}" var="shifttypes">
	         ${shifttypes.shifttype}&nbsp(${shifttypes.description})&nbsp&nbsp&nbsp
                    </c:forEach>
					</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(shifttypesQjList)> 0}">
				<tr>
					<td class="notes" colspan="34">冬季：
					<c:forEach items="${shifttypesDjList}" var="shifttypes">
	         ${shifttypes.shifttype}&nbsp(${shifttypes.description})&nbsp&nbsp&nbsp
                    </c:forEach>
					</td>
				</tr>
			</c:if>
			</tfoot>
		</table>
           <div class="paging">
				<img src="${pageContext.request.contextPath}/resources/images/left_first.png" onclick="selectFy('First');"/> <img
					src="${pageContext.request.contextPath}/resources/images/left.png" onclick="selectFy('Before');"/>
					 <span class="page_des1">Page</span>
				     <span class="bc"></span><span class="page_des2">of
					 ${endIndex}</span> <img src="${pageContext.request.contextPath}/resources/images/right.png" onclick="selectFy('After');"/> <img
					src="${pageContext.request.contextPath}/resources/images/right_end.png" onclick="selectFy('End');"/>
			</div>
    </div>
  </div> 
  <div class="popSelect" style="height: auto">
    <input id="shiftsid" style="display:none">
    <input id="userId" style="display:none">
    <input id="wzDate" style="display:none">
    <input id="shifttype" style="display:none">
    <p>×</p>
    <div onclick="insertOrUpdate('sc')">/</div>
    <c:forEach items="${shifttypesList}" var="shifttypes">
	<div onclick="insertOrUpdate('${shifttypes.shifttypesid}')">${shifttypes.shifttype}</div>
	</c:forEach>
  </div>
  
  <div id="qz" style="display: none;"></div>
<script language="javascript">
 var monthNowPage = ${monthNow};
 var yearNowPage = ${yearNow};
 $(function() {
	var endIndexInt = ${endIndex};
	if(endIndexInt>0){
	$(".bc").text(1);
	}
	else{
	$(".bc").text(0);	
	}
	qz.value = 1;
 });
 
    var aft = ${endIndex};
	function selectFy(para){
		var pagef = "";
		if(para == "Before"){
			if(qz.value>1){
				qz.value = qz.value-1;
				}
			pagef = qz.value;
		}
		if(para == "After"){
			if(qz.value<aft){
				qz.value = qz.value+1;
		}
			pagef = qz.value;
		}
		if(para == "End"){
			qz.value = ${endIndex};
			pagef = ${endIndex};
		}
		if(para == "First"){
			qz.value = 1;
			pagef = 1;
		}
		if(aft=="0"){
			pagef = 0;
		}
		$(".bc").text(pagef);
		var url = 'selectShiftsListViewPage?pagef='+pagef+'&monthNowPage='+monthNowPage+'&yearNowPage='+yearNowPage;
		$.ajax({
			 type:"GET",
			 url:url ,
			 cache:false,
			 success:function(data){
			 var dataList = data;
			 var html ='';
			 $("#content").html("");
			 for(var k=0;k<dataList.length;k++){
				 var shifts = dataList[k];
				 var htmlk = '';
				 if(shifts.deptname!= ''){
					    if(k%2==0)
					      htmlk = '<tr><td rowspan="'+shifts.description.split("&")[1]+'" class="th_job_2">'+shifts.deptname+'</td>'
					            +'<td class="th_date_name_2">'+shifts.name+'</td>';
					    else
					      htmlk = '<tr class="changeColor"><td rowspan="'+shifts.description.split("&")[1]+'" class="th_job_2">'+shifts.deptname+'</td>'
				            +'<td class="th_date_name_2">'+shifts.name+'</td>'; 	
					            	
				}
				else{
					      if(k%2==0) htmlk = '<tr><td class="th_date_name_2">'+shifts.name+'</td><td style="display:none"></td>';
					      else htmlk = '<tr class="changeColor"><td class="th_date_name_2">'+shifts.name+'</td><td style="display:none"></td>';
					      
				}
				 var dateToWeek = shifts.dateToWeeklist;
				 var htmlWeek = "";
				 for(var m=0;m<dateToWeek.length;m++){
					 if(dateToWeek[m].shifttypeName!='/')
					 htmlWeek = htmlWeek+'<td onclick="show(\''+dateToWeek[m].shiftsid+'\',\''+shifts.description.split("&")[0]+'\',\''+dateToWeek[m].wzDate+'\',\''+dateToWeek[m].shifttype+'\')" class="highLight">'+dateToWeek[m].shifttypeName+'</td>';
					 else
					 htmlWeek = htmlWeek+'<td onclick="show(\''+dateToWeek[m].shiftsid+'\',\''+shifts.description.split("&")[0]+'\',\''+dateToWeek[m].wzDate+'\',\''+dateToWeek[m].shifttype+'\')">'+dateToWeek[m].shifttypeName+'</td>';	 
				 }
				 htmlk = htmlk+htmlWeek+'<td class="tel_2">'+shifts.mobile+'</td></tr>';
				 html = html+htmlk;
			 }
			 $("#content").append(html); 
			 if($(".edit").text() == "关闭编辑")
			 openEdit();
			}
		});
	}
 
 function change(month){
	 var yearNow = ${yearNow};
	 var url = "refreashMonth?month="+month+"&year="+yearNow;
	 window.location.href = url;
 }

 function nextOrbefore(para){
	 var yearNow = ${yearNow};
	 var url = "";
     url = "refreashYear?para="+para+"&year="+yearNow;
    window.location.href = url;
 }

 function changeShifts(season,shiftsid){
	 if(season=="春季"){
		 para="1";
		}
	 if(season=="夏季"){
		 para="2";
		}
	 if(season=="秋季"){
		 para="3";
		}
	 if(season=="冬季"){
		 para="4";
		}
     openWin("shiftsSelect?season="+para+"&shiftsid="+shiftsid,300,200);
  }
 
 function openWin(u, w, h) { 
       var l = (screen.width - w) / 2; 
       var t = (screen.height - h) / 2; 
       var s = 'width=' + w + ', height=' + h + ', top=' + t + ', left=' + l; 
       s += ', toolbar=no, scrollbars=no, menubar=no, location=no, resizable=no'; 
       open(u, 'oWin', s); 
 }

 function insertOrUpdate(shifttypesid){
	    var shiftsids = shiftsid.value;
	    if(shiftsids=="null")
	    shiftsids = '';
	    var userIds = userId.value;
	    if(userIds=="null")
	    userIds = '';	
	    var wzDates = wzDate.value;
	    if(wzDates=="null")
	    wzDates	= '';
	    var shifttypes = shifttype.value;
	    if(shifttypes=="null")
	    shifttypes	= '';
		var mUrl = 'insertOrUpdate?shiftsid='+shiftsids+"&userId="+userIds+"&wzDate="+wzDates+"&shifttype="+shifttypes+"&shifttypesid="+shifttypesid;
		$.ajax({
			 type:"GET",
			 url:mUrl ,
			 cache:false,
			 success:function(data){
			 } 
		});
 }
 
 function editShiftTypes(){
	 var url = "${pageContext.request.contextPath}/shifts/shifttypesList"
	 window.location.href = url; 
 }
 function show(pa1,pa2,pa3,pa4){
	 shiftsid.value = pa1;
	 userId.value = pa2;
	 wzDate.value = pa3;
	 shifttype.value = pa4;
 }   
</script>
</html>