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
	href="${pageContext.request.contextPath}/resources/css/noticeBoard.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>
<body>
<div class="span12 main row-fluid">
	<div class="span12 content">
		<div class="span12 btnGroup">
		  <c:choose>
         	<c:when test="${bzw eq  '1'}"><div class="btnStyle1 btn2 cur"><p><a href="noticeBoardView">全部消息</a></p></div></c:when>
	        <c:otherwise><div class="btnStyle1 btn2"><p><a href="noticeBoardView">全部消息</a></p></div></c:otherwise>
	     </c:choose>
	     <c:choose>
         	<c:when test="${bzw eq  '2'}"><div class="btnStyle1 btn2 cur"><p><a href="operatingModelView">运行模式</a></p></div></c:when>
	        <c:otherwise><div class="btnStyle1 btn2"><p><a href="operatingModelView">运行模式</a></p></div></c:otherwise>
	     </c:choose>
	     <c:choose>
         	<c:when test="${bzw eq  '3'}"><div class="btnStyle1 btn2 cur"><p><a href="meteInformation">气象信息</a></p></div></c:when>
	        <c:otherwise><div class="btnStyle1 btn2"><p><a href="meteInformation">气象信息</a></p></div></c:otherwise>
	     </c:choose>
	     <c:choose>
         	<c:when test="${bzw eq  '4'}"><div class="btnStyle1 btn2 cur"><p><a href="activityView">活动</a></p></div></c:when>
	        <c:otherwise><div class="btnStyle1 btn2"><p><a href="activityView">活动</a></p></div></c:otherwise>
	     </c:choose>
	     <c:choose>
         	<c:when test="${bzw eq  '5'}"><div class="btnStyle1 btn2 cur"><p><a href="holidayView">节假日</a></p></div></c:when>
	        <c:otherwise><div class="btnStyle1 btn2"><p><a href="holidayView">节假日</a></p></div></c:otherwise>
	     </c:choose>
		 <c:choose>
         	<c:when test="${bzw eq  '6'}"><div class="btnStyle1 btn2 cur"><p><a href="noticeShiftsView">交接班</a></p></div></c:when>
	        <c:otherwise><div class="btnStyle1 btn2"><p><a href="noticeShiftsView">交接班</a></p></div></c:otherwise>
	     </c:choose>
<%-- 	     <c:choose> --%>
<%--          	<c:when test="${bzw eq  '7'}"><div class="btnStyle1 btn2 cur"><p><a href="operatingView">运行记录</a></p></div></c:when> --%>
<%-- 	        <c:otherwise><div class="btnStyle1 btn2"><p><a href="operatingView">运行记录</a></p></div></c:otherwise> --%>
<%-- 	     </c:choose> --%>
		</div>
	 </div>
   <div class="span12 user_info">
		<div class="table_width">
			<table class="user_table">
                <thead>
                    <tr class="table_head">
                        <th>消息类型</th>
                        <th>消息内容</th>
                        <th>负责人</th>
                        <th>时刻</th>
                    </tr>
                </thead>
				<tbody id="tbodyQz">
					<c:forEach items="${noticeBoardList}" var="noticeBoard">
					<tr>
					<td align="center">${noticeBoard.msType}</td>
			        <td align="center">${noticeBoard.msContent}</td>
			        <td align="center">${noticeBoard.responsiblePerson}</td>
			        <td align="center">${noticeBoard.moment}</td>
					</tr>
					</c:forEach>
				</tbody>
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
</div>
<div id="qz" style="display: none;"></div>
</body>

<script type="text/javascript">
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
		var bzClick=${bzw};
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
		var url = "";
		if(bzClick=="1"){
		   url = 'selectNoticeBoardPage?pagef='+pagef;
		}
		if(bzClick=="2"){
			url = 'selectNoticeBoardoperatingModelPage?pagef='+pagef;
		}
		if(bzClick=="3"){
			url = 'selectNoticeBoardmeteInfoPage?pagef='+pagef;
		}
		if(bzClick=="4"){
			url = 'selectNoticeBoardActivityPage?pagef='+pagef;
		}
		if(bzClick=="5"){
			url = 'selectNoticeBoardHolidayPage?pagef='+pagef;
		}
		if(bzClick=="6"){
			url = 'selectNoticeBoardNoticeShiftsPage?pagef='+pagef;
		}
		if(bzClick=="7"){
			url = 'selectNoticeBoardOperatingPage?pagef='+pagef;
		}
		$.ajax({
			 type:"GET",
			 url:url ,
			 cache:false,
			 success:function(data){
			 var dataList = data;
			 var html ='';
			 $("#tbodyQz").html("");
			 for(var k=0;k<dataList.length;k++){
				 var noticeBoard = dataList[k];
				 var htmlk ='<tr><td align="center">'+noticeBoard.msType+'</td>'+
				        '<td align="center">'+noticeBoard.msContent+'</td>'+
			            '<td align="center">'+noticeBoard.responsiblePerson+'</td>'+
			            '<td align="center">'+noticeBoard.moment+'</td></tr>';
				 html = html+htmlk;        
			 }
			 $("#tbodyQz").append(html);
			}
		});
	}
</script>
</html>
