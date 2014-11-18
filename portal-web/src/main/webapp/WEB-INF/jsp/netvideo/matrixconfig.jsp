<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- HTML START -->
<!-- 
	AUTHOR: LiuChao
	Created Date: 2014年5月8日 18:58:39 
	LastModified Date: 2014年5月9日 11:13:05
	Description: NetVideo模块——矩阵配置页面
 -->
<div class="row-fluid">
	<div class="span2">
		<div class="widget">
			<div class="widget-header">
				<h3 class="icon">小牛配置</h3>
			</div>
			<div class="widget-content"
				style="overflow: auto; height: 300px; width: 165px;">
				<ul class="nav nav-tabs nav-stacked">
					<li><a
						href="<spring:url value="/okcsys/video/sysconfig"></spring:url>"><i
							class="fa fa-home"></i><span>系统配置</span></a></li>
					<li><a href="<spring:url value="/okcsys/video/dvrcfg"></spring:url>"><i
							class="fa fa-users"></i><span>DVR配置</span></a></li>
					<li><a
						href="<spring:url value="/okcsys/video/cameracfg"></spring:url>"><i
							class="fa fa-home"></i><span>摄像机配置</span></a></li>
					<li><a
						href="<spring:url value="/okcsys/video/matrixcfg"></spring:url>"><i
							class="fa fa-home"></i><span>矩阵配置</span></a></li>
					<li><a
						href="<spring:url value="/okcsys/video/monitorconfig"></spring:url>"><i
							class="fa fa-home"></i><span>监视器配置</span></a></li>
					<li><a
						href="<spring:url value="/okcsys/video/rotationcfg"></spring:url>"><i
							class="fa fa-home"></i><span>轮显配置</span></a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="span10">
		<form:form modelAttribute="matrixconfig" method="post"
			class="matrixform">
			<legend class="groupTitle">1号矩阵</legend>
			<table style="width: 97%; height: 90%; margin-left: 3%;">
				<tbody>
					<tr>
						<td style="text-align: left; width: 20%;">型号</td>
						<td style="width: auto;"><form:select path="type_0"
								style="width: 115px;">
								<form:option value="不使用矩阵">不使用矩阵</form:option>
								<form:option value="PELCO-D">PELCO-D</form:option>
								<form:option value="GE">GE</form:option>
								<form:option value="VideoBlox">VideoBlox</form:option>
							</form:select></td>
						<td style="text-align: left; width: 20%;">连接在</td>
						<td style="width: auto;"><form:select path="com_0"
								style="width: 115px;">
								<form:option value="COM1">COM1</form:option>
								<form:option value="COM2">COM2</form:option>
								<form:option value="COM3">COM3</form:option>
								<form:option value="COM4">COM4</form:option>
								<form:option value="COM5">COM5</form:option>
								<form:option value="COM6">COM6</form:option>
								<form:option value="COM7">COM7</form:option>
								<form:option value="COM8">COM8</form:option>
							</form:select></td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">波特率</td>
						<td style="width: auto;"><form:select path="baudRate_0"
								style="width: 115px;">
								<form:option value="110">110</form:option>
								<form:option value="300">300</form:option>
								<form:option value="600">600</form:option>
								<form:option value="900">900</form:option>
								<form:option value="1300">1300</form:option>
								<form:option value="2600">2600</form:option>
								<form:option value="5400">5400</form:option>
								<form:option value="9600">9600</form:option>
							</form:select></td>
						<td style="text-align: left; width: 20%;">校验方式</td>
						<td style="width: auto;"><form:select path="validetype_0"
								style="width: 115px;">
								<form:option value="无校验(NONE)">无校验(NONE)</form:option>
								<form:option value="奇校验(ODD)">奇校验(ODD)</form:option>
								<form:option value="偶校验(EVEN)">偶校验(EVEN)</form:option>
								<form:option value="标志校验(MARK)">标志校验(MARK)</form:option>
								<form:option value="空格校验(SPACE)">空格校验(SPACE)</form:option>
							</form:select></td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">数据位</td>
						<td style="width: auto;"><form:radiobutton path="dataBit_0"
								value="7" />7 <form:radiobutton path="dataBit_0" value="8" />8</td>
						<td style="text-align: left; width: 20%;">停止位</td>
						<td style="width: auto;"><form:radiobutton path="soptBit_0"
								value="1" />1 <form:radiobutton path="soptBit_0" value="2" />2</td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">用户名</td>
						<td style="width: auto;"><form:input path="username_0"
								style="width: 115px;" /></td>
						<td style="text-align: left; width: 20%;">密码</td>
						<td style="width: auto;"><form:input path="password_0"
								style="width: 115px;" /></td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">空闲输出通道</td>
						<td style="width: auto;"><form:input path="freeChannel_0"
								style="width: 115px;" onkeyup="value=value.replace(/[^\d]/g,'')"
								onbeforepaste="clipboardData.setData('text',clipboardData('text').replace(/[^\d]/g,''))" /></td>
						<td style="text-align: left; width: 20%;"></td>
						<td style="width: auto;"></td>
					</tr>
				</tbody>
			</table>
			<legend class="groupTitle">2号矩阵</legend>
			<table style="width: 97%; height: 90%; margin-left: 3%;">
				<tbody>
					<tr>
						<td style="text-align: left; width: 20%;">型号</td>
						<td style="width: auto;"><form:select path="type_1"
								style="width: 115px;">
								<form:option value="不使用矩阵">不使用矩阵</form:option>
								<form:option value="PELCO-D">PELCO-D</form:option>
								<form:option value="GE">GE</form:option>
								<form:option value="VideoBlox">VideoBlox</form:option>
							</form:select></td>
						<td style="text-align: left; width: 20%;">连接在</td>
						<td style="width: auto;"><form:select path="com_1"
								style="width: 115px;">
								<form:option value="COM1">COM1</form:option>
								<form:option value="COM2">COM2</form:option>
								<form:option value="COM3">COM3</form:option>
								<form:option value="COM4">COM4</form:option>
								<form:option value="COM5">COM5</form:option>
								<form:option value="COM6">COM6</form:option>
								<form:option value="COM7">COM7</form:option>
								<form:option value="COM8">COM8</form:option>
							</form:select></td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">波特率</td>
						<td style="width: auto;"><form:select path="baudRate_1"
								style="width: 115px;">
								<form:option value="110">110</form:option>
								<form:option value="300">300</form:option>
								<form:option value="600">600</form:option>
								<form:option value="900">900</form:option>
								<form:option value="1300">1300</form:option>
								<form:option value="2600">2600</form:option>
								<form:option value="5400">5400</form:option>
								<form:option value="9600">9600</form:option>
							</form:select></td>
						<td style="text-align: left; width: 20%;">校验方式</td>
						<td style="width: auto;"><form:select path="validetype_1"
								style="width: 115px;">
								<form:option value="无校验(NONE)">无校验(NONE)</form:option>
								<form:option value="奇校验(ODD)">奇校验(ODD)</form:option>
								<form:option value="偶校验(EVEN)">偶校验(EVEN)</form:option>
								<form:option value="标志校验(MARK)">标志校验(MARK)</form:option>
								<form:option value="空格校验(SPACE)">空格校验(SPACE)</form:option>
							</form:select></td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">数据位</td>
						<td style="width: auto;"><form:radiobutton path="dataBit_1"
								value="7" />7 <form:radiobutton path="dataBit_1" value="8" />8</td>
						<td style="text-align: left; width: 20%;">停止位</td>
						<td style="width: auto;"><form:radiobutton path="soptBit_1"
								value="1" />1 <form:radiobutton path="soptBit_1" value="2" />2</td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">用户名</td>
						<td style="width: auto;"><form:input path="username_1"
								style="width: 115px;" /></td>
						<td style="text-align: left; width: 20%;">密码</td>
						<td style="width: auto;"><form:input path="password_1"
								style="width: 115px;" /></td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">空闲输出通道</td>
						<td style="width: auto;"><form:input path="freeChannel_1"
								style="width: 115px;" onkeyup="value=value.replace(/[^\d]/g,'')"
								onbeforepaste="clipboardData.setData('text',clipboardData('text').replace(/[^\d]/g,''))" /></td>
						<td style="text-align: left; width: 20%;"></td>
						<td style="width: auto;"></td>
					</tr>
				</tbody>
			</table>
			<legend class="groupTitle">3号矩阵</legend>
			<table style="width: 97%; height: 90%; margin-left: 3%;">
				<tbody>
					<tr>
						<td style="text-align: left; width: 20%;">型号</td>
						<td style="width: auto;"><form:select path="type_2"
								style="width: 115px;">
								<form:option value="不使用矩阵">不使用矩阵</form:option>
								<form:option value="PELCO-D">PELCO-D</form:option>
								<form:option value="GE">GE</form:option>
								<form:option value="VideoBlox">VideoBlox</form:option>
							</form:select></td>
						<td style="text-align: left; width: 20%;">连接在</td>
						<td style="width: auto;"><form:select path="com_2"
								style="width: 115px;">
								<form:option value="COM1">COM1</form:option>
								<form:option value="COM2">COM2</form:option>
								<form:option value="COM3">COM3</form:option>
								<form:option value="COM4">COM4</form:option>
								<form:option value="COM5">COM5</form:option>
								<form:option value="COM6">COM6</form:option>
								<form:option value="COM7">COM7</form:option>
								<form:option value="COM8">COM8</form:option>
							</form:select></td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">波特率</td>
						<td style="width: auto;"><form:select path="baudRate_2"
								style="width: 115px;">
								<form:option value="110">110</form:option>
								<form:option value="300">300</form:option>
								<form:option value="600">600</form:option>
								<form:option value="900">900</form:option>
								<form:option value="1300">1300</form:option>
								<form:option value="2600">2600</form:option>
								<form:option value="5400">5400</form:option>
								<form:option value="9600">9600</form:option>
							</form:select></td>
						<td style="text-align: left; width: 20%;">校验方式</td>
						<td style="width: auto;"><form:select path="validetype_2"
								style="width: 115px;">
								<form:option value="无校验(NONE)">无校验(NONE)</form:option>
								<form:option value="奇校验(ODD)">奇校验(ODD)</form:option>
								<form:option value="偶校验(EVEN)">偶校验(EVEN)</form:option>
								<form:option value="标志校验(MARK)">标志校验(MARK)</form:option>
								<form:option value="空格校验(SPACE)">空格校验(SPACE)</form:option>
							</form:select></td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">数据位</td>
						<td style="width: auto;"><form:radiobutton path="dataBit_2"
								value="7" />7 <form:radiobutton path="dataBit_2" value="8" />8</td>
						<td style="text-align: left; width: 20%;">停止位</td>
						<td style="width: auto;"><form:radiobutton path="soptBit_2"
								value="1" />1 <form:radiobutton path="soptBit_2" value="2" />2</td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">用户名</td>
						<td style="width: auto;"><form:input path="username_2"
								style="width: 115px;" /></td>
						<td style="text-align: left; width: 20%;">密码</td>
						<td style="width: auto;"><form:input path="password_2"
								style="width: 115px;" /></td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">空闲输出通道</td>
						<td style="width: auto;"><form:input path="freeChannel_2"
								style="width: 115px;" onkeyup="value=value.replace(/[^\d]/g,'')"
								onbeforepaste="clipboardData.setData('text',clipboardData('text').replace(/[^\d]/g,''))" /></td>
						<td style="text-align: left; width: 20%;"></td>
						<td style="width: auto;"></td>
					</tr>
				</tbody>
			</table>
			<legend class="groupTitle">4号矩阵</legend>
			<table style="width: 97%; height: 90%; margin-left: 3%;">
				<tbody>
					<tr>
						<td style="text-align: left; width: 20%;">型号</td>
						<td style="width: auto;"><form:select path="type_3"
								style="width: 115px;">
								<form:option value="不使用矩阵">不使用矩阵</form:option>
								<form:option value="PELCO-D">PELCO-D</form:option>
								<form:option value="GE">GE</form:option>
								<form:option value="VideoBlox">VideoBlox</form:option>
							</form:select></td>
						<td style="text-align: left; width: 20%;">连接在</td>
						<td style="width: auto;"><form:select path="com_3"
								style="width: 115px;">
								<form:option value="COM1">COM1</form:option>
								<form:option value="COM2">COM2</form:option>
								<form:option value="COM3">COM3</form:option>
								<form:option value="COM4">COM4</form:option>
								<form:option value="COM5">COM5</form:option>
								<form:option value="COM6">COM6</form:option>
								<form:option value="COM7">COM7</form:option>
								<form:option value="COM8">COM8</form:option>
							</form:select></td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">波特率</td>
						<td style="width: auto;"><form:select path="baudRate_3"
								style="width: 115px;">
								<form:option value="110">110</form:option>
								<form:option value="300">300</form:option>
								<form:option value="600">600</form:option>
								<form:option value="900">900</form:option>
								<form:option value="1300">1300</form:option>
								<form:option value="2600">2600</form:option>
								<form:option value="5400">5400</form:option>
								<form:option value="9600">9600</form:option>
							</form:select></td>
						<td style="text-align: left; width: 20%;">校验方式</td>
						<td style="width: auto;"><form:select path="validetype_3"
								style="width: 115px;">
								<form:option value="无校验(NONE)">无校验(NONE)</form:option>
								<form:option value="奇校验(ODD)">奇校验(ODD)</form:option>
								<form:option value="偶校验(EVEN)">偶校验(EVEN)</form:option>
								<form:option value="标志校验(MARK)">标志校验(MARK)</form:option>
								<form:option value="空格校验(SPACE)">空格校验(SPACE)</form:option>
							</form:select></td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">数据位</td>
						<td style="width: auto;"><form:radiobutton path="dataBit_3"
								value="7" />7 <form:radiobutton path="dataBit_3" value="8" />8</td>
						<td style="text-align: left; width: 20%;">停止位</td>
						<td style="width: auto;"><form:radiobutton path="soptBit_3"
								value="1" />1 <form:radiobutton path="soptBit_3" value="2" />2</td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">用户名</td>
						<td style="width: auto;"><form:input path="username_3"
								style="width: 115px;" /></td>
						<td style="text-align: left; width: 20%;">密码</td>
						<td style="width: auto;"><form:input path="password_3"
								style="width: 115px;" /></td>
					</tr>
					<tr>
						<td style="text-align: left; width: 20%;">空闲输出通道</td>
						<td style="width: auto;"><form:input path="freeChannel_3"
								style="width: 115px;" onkeyup="value=value.replace(/[^\d]/g,'')"
								onbeforepaste="clipboardData.setData('text',clipboardData('text').replace(/[^\d]/g,''))" /></td>
						<td style="text-align: left; width: 20%;"></td>
						<td style="width: auto;"></td>
					</tr>
				</tbody>
			</table>

			<div
				style="width: 98%; height: 20px; border: 2px groove; float: left; margin: 10px 0px 0px 10px;">(空闲输出通道必须是矩阵上的有效通道，不能随意设置)</div>
			<div
				style="width: 100%; float: left; text-align: center; margin-top: 20px;">
				<button class="blue_btn" type="button" id="matrixsub">保存</button>
				<button class="blue_btn" type="button" style="margin-left: 20%;"
					onclick="window.location.href='${pageContext.request.contextPath }/okcsys/video/matrixcfg'">还原</button>
			</div>
		</form:form>
	</div>
</div>