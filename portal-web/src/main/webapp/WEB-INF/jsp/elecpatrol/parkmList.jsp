<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"	src="${pageContext.request.contextPath}/resources/scripts/subParking/subParking.js"></script>
<div  id="clone">
    <div style="margin-top: 7px;">
        <div >
            <div>
                <span style="margin-left: 10px;"></span>
									   <span>进场开始时间：<input id="startInDate" name="startInDate" type="text" onClick="WdatePicker()" style="height: 20px;width: 100px;"/>
									   进场结束时间：<input id="endInDate" name="endInDate" type="text" onClick="WdatePicker()" style="height: 20px;width: 100px;"/></span>
				                       <span>出场开始时间：<input id="startOutDate" name="startOutDate" type="text" onClick="WdatePicker()" style="height: 20px;width: 100px;"/>
									   出场结束时间：<input id="endOutDate" name="endOutDate" type="text" onClick="WdatePicker()" style="height: 20px;width: 100px;"/></span>

                <span >车牌号：<input id="cardId" name="cardId" style="height: 20px;width: 100px;"/></span>
                <span >进场口：<input id="comeName" name="comeName" style="height: 20px;width: 100px;"/></span>
                <span >出场口：<input id="goName" name="goName" style="height: 20px;width: 100px;"/></span>
                <span style="cursor: pointer;" onclick="selectShowCc();">查询</span>
            </div>
        </div>
    </div>
    <div class="span12 alert_detail">
        <table class="alert_list">
            <tbody id = "tbodyContent">
            </tbody>
        </table>
        <div class="paging">
            <img src="${pageContext.request.contextPath}/resources/images/left_first.png" onclick="selectFy('First');"/> <img
                src="${pageContext.request.contextPath}/resources/images/left.png" onclick="selectFy('Before');"/>
            <span class="page_des1">Page</span>
            <span class="bc"></span><span class="page_des2">of</span>
            <span class="pageCount"></span> <img src="${pageContext.request.contextPath}/resources/images/right.png" onclick="selectFy('After');"/> <img
                src="${pageContext.request.contextPath}/resources/images/right_end.png" onclick="selectFy('End');"/>
        </div>
    </div>
</div>
