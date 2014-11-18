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
<!-- <div class="top_btn_group"> -->
<!-- 		<div> -->
<!-- 			<div class="Btn-big"><a href="shiftsView"><i class="icon_btn icon_bag"></i><p>班次管理</p></a></div> -->
<!-- 			<div class="Btn-big  cur nomargin"><a href="shiftworkView"><i class="icon_btn icon_key"></i><p>交接班</p></a></div> -->
<!-- 			<div class="Btn-big"><a href="operatingView"><i class="icon_btn icon_book"></i><p>运行记录</p></a></div> -->
<!-- 		</div> -->
<!-- </div> -->
<div class="span12 ymd_switch" style="margin-left: 40px;">
		<img src="${pageContext.request.contextPath}/resources/images/u93_normal.png" onclick="nextOrbefore('before');"><p>${nowdate} - ${nextdate}</p>
		<img src="${pageContext.request.contextPath}/resources/images/u95_normal.png" onclick="nextOrbefore('next');">
</div>
<div class="span12 staffChange nomargin">
		<div class="span7 staff_name">
			<div class="staffChange_title">
				<div class="show_date date1">${nowdateJt}</div>
				<div class="show_date date2">${nextdateJt}</div>
				<input name="yc" type="text" size="30" id="yc" value="${yc}"  style="display:none;"/>
				<div class="press_state press1"  onclick="selectWorkByTime(1,'${nowdate}','${nextdate}')"></div>
				<div class="press_state press2"  onclick="selectWorkByTime(2,'${nowdate}','${nextdate}')"></div>
			</div>
			<table class="table_style1">
				<tbody>
					<tr>
						<th>岗位名称</th>
						<th>交班人</th>
						<th>交班人联系方式</th>
						<th>接班人</th>
						<th>接班人联系方式</th>
						<th>交接班记录</th>
					</tr>
					<c:forEach items="${shiftworkList}" var="shiftwork" varStatus="status">
		     <tr>
			  <td align="center">${shiftwork.shiftitems}</td>
			  <td align="center">${fn:split(shiftwork.shiftendingby,"_")[0]}</td>
			  <td align="center">${fn:split(shiftwork.shiftendingby,"_")[1]}</td>
			  <td align="center">${fn:split(shiftwork.shiftstartingby,"_")[0]}</td>
			  <td align="center">${fn:split(shiftwork.shiftstartingby,"_")[1]}</td>
			   <c:if test="${shiftwork.createdate eq '1999-01-01 00:00:00.0'}">	
	          <td align="center" style="cursor:pointer;" onclick="shiftworkEdit('${shiftwork.shiftitems}','${shiftwork.shiftworkid}')">还未建立</td> 
			   </c:if>
			   <c:if test="${shiftwork.createdate != '1999-01-01 00:00:00.0'}">	
			  <td align="center" style="cursor:pointer;" onclick="shiftworkEdit('${shiftwork.shiftitems}','${shiftwork.shiftworkid}')">查看</td> 
			   </c:if>
		    </tr>
		   </c:forEach>
				</tbody>
	       </table>
			<div class="paging paging1">
<!-- 				<img src="${pageContext.request.contextPath}/resources/images/left_first.png" /> -->
<!-- 			    <img src="${pageContext.request.contextPath}/resources/images/left.png" /> -->
<!-- 				<span class="page_des1">Page</span> -->
<!-- 				<span><input type="text"></span> -->
<!-- 				<span class="page_des2">of 1000</span> -->
<!-- 			 	<img src="${pageContext.request.contextPath}/resources/images/right.png" /> -->
<!-- 			    <img src="${pageContext.request.contextPath}/resources/images/right_end.png" /> -->
			</div>
		</div>
		 <c:if test="${bzTc == '1'}">	
		<div class="span5 right_over nomargin" style="height:85%;background-color:  #CCECED">
			<div class="log_title">
				<div class="log_title_top">
					<div class="title">交接班工作日志</div>
					<div class="weather1">
						<div class="span7 weatherImg"></div>
						<div class="weather_detail1">
<!-- 							<div class="weather_des">阴转小雨</div> -->
<!-- 							<div class="temperature">12°C-20°C</div> -->
						</div>
					</div>
				</div>
				<div class="log_title_bottom">
					<span class="record_time">记录时间：  ${strDate}</span><span class="record_job">专业：${shiftwork.shiftitems}</span>
				</div>
			</div>
			<form action="updateshiftwork" method="post">
			<div class="log_content">
				<div class="blankdiv"></div>
				<div class="work_title">
				<input name="shiftworkid" type="text" size="30" id="shiftworkid" value="${shiftwork.shiftworkid}"  style="display:none;"/>
					<p>工作内容</p><div class="line line1"></div>
				</div>
				<textarea id="workcontent" name="workcontent" rows="4" style="width: 100%">${shiftwork.workcontent}</textarea> 
				<div class="work_title">
					<p>重点工作交接事项</p><div class="line line2"></div>
				</div>
				<textarea id="focuscontent" name="focuscontent" rows="4" style="width: 100%">${shiftwork.focuscontent}</textarea> 
				<div class="work_title">
					<p>领导交办</p><div class="line line1"></div>
				</div>
				<textarea id="leadassign" name="leadassign"  rows="4" style="width: 100%">${shiftwork.leadassign}</textarea>
				<div class="btn_group"  align="right">
					<button class="btn btn-primary" style="background: #009999;" type="submit">提交</button>
                    <button class="btn btn-primary" type="button" style="background: #66CCFF" onclick="cel();">清除</button>
				</div>
				<div class="work_title">
					<p style="color:#333">注解说明：</p>
					<br>
					<p style="color:#333;font-size: 15px;line-height:25px;">1、本表格为万达广场工程部工作日志，请将当日的工作内容、需协调解决事项等填入工作内容一栏。并对工作情况做出总结。同时提出合理化的建议和意见。</p>
					<p style="color:#333;font-size: 15px;line-height:25px;">2、每天上午9：00前专业领班对前一天的工作日志进行审阅。</p>
					<p style="color:#333;font-size: 15px;line-height:25px;">3、日常施工进度、设备安装情况，所有专业技工每日必须填写其主要工作内容。</p>
					<p style="color:#333;font-size: 15px;line-height:25px;">4、对领导临时交办的事项必须进行记录，并及时将结果反馈给上级。</p>
				</div>
<%-- 				<textarea id="comments" name="comments" rows="4" style="width: 100%">${shiftwork.comments}</textarea> --%>
                <input name="nowdate" type="text" size="30" id="nowdate" value="${nowdate}"  style="display:none;"/>
                <input name="nextdate" type="text" size="30" id="nextdate" value="${nextdate}"  style="display:none;"/>
                <input name="paraTime" type="text" size="30" id="paraTime" value="${paraTime}"  style="display:none;"/>
			</div>
			 </form> 
		</div>
		 </c:if>
   </div>
</div>
<script language="javascript">
function nextOrbefore(para){
	 var nowdate = '${nowdate}';
	 var nextdate ='${nextdate}';
	 var url = "";
     url = "refreashDate?para="+para+"&nowdate="+nowdate+"&nextdate="+nextdate;
     window.location.href = url;
}

function selectWorkByTime(paraTime,nowdate,nextdate){
	 var url = "";
     url = "selectWorkByTime?paraTime="+paraTime+"&nowdate="+nowdate+"&nextdate="+nextdate;
     window.location.href = url;
}

function shiftworkEdit(deptName,shiftworkid)
{	
	   var nowdate = '${nowdate}';
	   var nextdate ='${nextdate}';
	   var paraTime = yc.value;
	   var addUrl = 'shiftworkEdit?deptName='+deptName+"&shiftworkid="+shiftworkid+"&paraTime="+paraTime+"&nowdate="+nowdate+"&nextdate="+nextdate;;
	   window.location.href = addUrl;
} 

function cel()
{	
	$('#workcontent').val("");
	$('#focuscontent').val("");
	$('#leadassign').val("");
// 	$('#comments').val("");
} 
</script>
</html>
