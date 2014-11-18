
<%@page import="java.util.List"%>
<%@page import="com.energicube.eno.common.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" type="text/css"
	  href="${pageContext.request.contextPath}/resources/css/bootstrap-select.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ui/jquery.ui.all.css">
<link rel="stylesheet" type="text/css"
	  href="${pageContext.request.contextPath}/resources/css/main.css">
<link rel="stylesheet" type="text/css"
	  href="${pageContext.request.contextPath}/resources/css/alertManage.css">
<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/scripts/bootstrap-select.min.js"></script>
<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>
<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/scripts/alertManage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/highcharts.js"></script>
<script type="text/javascript">
	var headerdescription = "";
	var pagefirst= 1;
	var pagelast= 0;
	var pagef = 1;
	var pageSize = 10;
	$(function(){
		$("#dialog-modal").dialog({
			width : "700px",
			autoOpen: false,
			show: "blind",
			hide: "explode",
			modal: true
		});
		$(".sure").click(function(){
			$("#dialog-modal").dialog("close");
		});
		$(".cancel").click(function(){
			$("#dialog-modal").dialog("close");
		});
		selectFy('');
	})
	function alarmRefresh(){
	}
	function change(tagid,tagname){

		var params={tagname:tagname,tagid:tagid,disable:'N'};
		$.ajax({
			type:"POST",
			url:CONTEXT_PATH+'/pwarn/restrainAlarm',
			dataType:'json',
			async:true,
			data:params,
			cache : false,
			success:function(data){
				selectFy('');
			}
		});

	}

	function selectFy(para){
		var aft = pagelast;
		if(para == "Before"){
			if(pagef>1){
				pagef = pagef-1;
			}
		}
		if(para == "After"){
			if(pagef<aft){
				pagef = pagef+1;
			}
		}
		if(para == "End"){
			pagef = pagelast;
		}
		if(para == "First"){
			pagef = pagefirst;
		}
		$(".bc").text(pagef);
		if(pagef>0){

			var params={pageNumber:pagef,pageSize:pageSize};
			$.ajax({
				type:"POST",
				url:CONTEXT_PATH+'/pwarn/findRestrainAlarm',
				dataType:'json',
				async:true,
				data:params,
				cache : false,
				success:function(data){
					$("#tbodyContent").html("");
					var html = "";
					var dataLength = data.numberOfElements;
					for(var i=0;i<dataLength;i++){
						var dataObj = data.content[i];
						var htmlk ="";
						var temp='';
						if(i%2==0) {
							temp='class="changeColor">';
						}else {
							temp='>';
						}
						var date=new Date(dataObj.cutoutt.millis).Format("yyyy-MM-dd hh:mm:ss");
						htmlk ="<tr onclick=change('"+dataObj.tagid+"','"+dataObj.tagname+"') style='cursor: pointer;' "+temp+
						'<td>'+date+'</td>'+
						'<td>'+dataObj.almcomment+'</td>'+
						'<td>'+dataObj.groupname+'</td>'+
						'<td>'+dataObj.devicename+'</td>'+
						'<td>'+dataObj.tagcomment+'</td>';
						var htmlkc = "<td ></td>";
						if(dataObj.almpriority==1){
							htmlkc =   '<td style="color: red;">极高</td>';
						}
						if(dataObj.almpriority==2){
							htmlkc =   '<td style="color: #F79646;">高</td>';
						}
						if(dataObj.almpriority==3){
							htmlkc =   '<td style="color: #996633;">中</td>';
						}
						if(dataObj.almpriority==4){
							htmlkc =   '<td style="color: #33CC33;">低</td>';
						}
						var htmlkcc = "<td>"+dataObj.cutoutoperatorname+"</td>"+"<td>"+dataObj.cutoutreason+"</td>"+"<td>启用</td>";

						html =html+htmlk+htmlkc+htmlkcc+'</tr>';

					}
					$("#tbodyContent").append(html);
				}
			});
		}
	}
	// 对Date的扩展，将 Date 转化为指定格式的String
	// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
	// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
	// 例子：
	// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
	// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
	Date.prototype.Format = function(fmt)
	{
		var o = {
			"M+" : this.getMonth()+1,                 //月份
			"d+" : this.getDate(),                    //日
			"h+" : this.getHours(),                   //小时
			"m+" : this.getMinutes(),                 //分
			"s+" : this.getSeconds(),                 //秒
			"q+" : Math.floor((this.getMonth()+3)/3), //季度
			"S"  : this.getMilliseconds()             //毫秒
		};
		if(/(y+)/.test(fmt))
			fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
		for(var k in o)
			if(new RegExp("("+ k +")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
		return fmt;
	}
</script>
<div class="span10">
	<div class="span12 no_right_margin">
		<div class="span12 alert_detail">
			<table class="alert_list">
				<tr>
					<th>抑制时间</th>
					<th>报警描述</th>
					<th>子系统</th>
					<th>设备</th>
					<th>位置</th>
					<th>报警级别</th>
					<th>抑制人</th>
					<th>抑制原因</th>
					<th>处理</th>
				</tr>
				<tbody id = "tbodyContent">
				</tbody>
			</table>
			<div class="paging">
				<img src="${pageContext.request.contextPath}/resources/images/left_first.png" onclick="selectFy('First');"/> <img
					src="${pageContext.request.contextPath}/resources/images/left.png" onclick="selectFy('Before');"/>
				<span class="page_des1">Page</span>
				<span class="bc">0</span>of<span class="page_des2">
					 0</span> <img src="${pageContext.request.contextPath}/resources/images/right.png" onclick="selectFy('After');"/> <img
					src="${pageContext.request.contextPath}/resources/images/right_end.png" onclick="selectFy('End');"/>
			</div>
		</div>
	</div>
</div>

<div id="dialog-modal" class="popover1" title="报警定义页">
	<table class="register">
		<tbody >
		<tr style="width: 120px;">
			<td>子系统：</td>
			<td>
				<input name="tagid" id="tagid" style="display: none;">
				<input name="zxt" id="zxt" disabled="disabled">
			</td>
			<td style="width: 100px;"></td>
			<td>设备：</td>
			<td><input name="sb" id="sb" disabled="disabled"></td>
		</tr>
		<tr>
			<td>报警描述：</td>
			<td><input name="bjms" id="bjms" disabled="disabled" ></td>
			<td></td>
			<td>报警来源：</td>
			<td><input name="bjly" id="bjly" disabled="disabled"></td>
		</tr>
		<!-- 				     <tr>	 -->
		<!-- 						<td>故障代码：</td> -->
		<!-- 						<td><input name="gzdm" id="gzdm" disabled="disabled" ></td> -->
		<!-- 						<td></td> -->
		<!-- 				     </tr> -->
		<tr>
			<td>上限：</td>
			<td><input name="sx" id="sx"></td>
			<td></td>
			<td>下限：</td>
			<td><input name="xx" id="xx"></td>
		</tr>
		<tr>
			<td>报警级别：</td>
			<td>
				<select id="bjjb" name="bjjb"  style="width: 122px; height: 33px">
					<c:forEach items="${alarmLevels}" var="alarmLevel">
						<option value="${alarmLevel.dmvalue }">${alarmLevel.description}</option>
					</c:forEach>
				</select>
			</td>
			<td></td>
			<td>报警压制：</td>
			<td>
				<select id="bjyz" name="bjyz"  style="width: 122px; height: 33px">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
		</tr>
		</tbody>
	</table>
	<div class="modal_btn" style="margin-top: 10px;border-bottom: 0px;">
		<input id="pd" style="display: none;">
		<div class="sure" onclick="saveTo();">确定</div>
		<div class="cancel">取消</div>
	</div>
</div>