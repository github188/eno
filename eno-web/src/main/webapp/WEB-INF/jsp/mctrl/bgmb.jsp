<%--
  Created by IntelliJ IDEA.
  User: EnergyUser
  Date: 14-1-3
  Time: 下午4:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/resources/css/bootstrap-switch.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/bootstrap-switch.js"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/mctrl/light_func.js"></script>--%>

<div class="pmt_block">
    <div class="operate_board">
			<div class="mode_operate">
				<h1 class="mode_name" style="font-size: 24px; width: ${currentStructurePagelayout.width}px;">园区西区<span style="float: right;">状态：<span style="color: green;">播放</span></span></h1>
				<div class="eachFatherBlock" style="width: 348px; height:100px; border-right: 1px solid #CCC;margin-top: -10px; padding-top: 10px ;">
					<div style="font-size: 18px;">音量控制<img style="display: inline; margin-left: 5px;" src="/portal/resources/img/ylkz.jpg">
					</div>
					<ul>
						<li style="float: left; padding: 0 10px;"><div class="mode_btn" style="border: 5px solid #fff; ">音量 -</div></li>
						<li style="float: left; padding: 0 10px;"><div class="mode_btn" style="border: 5px solid #fff; ">音量 +</div></li>
					</ul>
				</div>
				<div class="eachFatherBlock" style="width: 690px;height:100px; border-right: 1px solid #CCC; margin-top: -10px; padding: 10px 0 0 20px;">
					<div style="font-size: 18px;">外部音源</div>
					<ul>
						<li style="float: left; padding: 0 10px;"><div class="mode_btn" style="border: 5px solid #fff;">音源1</div></li>
						<li style="float: left; padding: 0 10px;"><div class="mode_btn" style="border: 5px solid #fff;">音源2</div></li>
						<li style="float: left; padding: 0 10px;"><div class="mode_btn" style="border: 5px solid #fff;">音源3</div></li>
						<li style="float: left; padding: 0 10px;"><div class="mode_btn" style="border: 5px solid #fff;">音源4</div></li>
					</ul>
				</div>
				<div class="allopenclose" style="width: 348px;height:100px;margin-top: -10px; padding: 10px 0 0 20px;">
					<div style="font-size: 18px;">紧急</div>
					<ul>
						<li style="float: left; padding: 0 10px;"><div class="mode_btn" style="background-color: #FFD39B; border: 5px solid #fff; ">消防音源</div></li>
						<li style="float: left; padding: 0 10px;"><div class="mode_btn" style="border: 5px solid #fff; ">暂停</div></li>
					</ul>
				</div>
			</div>
    </div>
</div>
