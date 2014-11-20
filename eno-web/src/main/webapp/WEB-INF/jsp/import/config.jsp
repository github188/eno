<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp" %>
<!-- HTML START -->
<!--
AUTHOR: zouzhixiang
Created Date: 2014年8月20日 13:34:57
LastModified Date:
Description: 导入配置页面
-->
<style type="text/css">
    .file-box{ position:relative;width: 760px}
    .filename{ height:22px; border:1px solid #cdcdcd;width: 500px}
    .file{ position:absolute; top:0; right:80px; height:24px; filter:alpha(opacity:0);opacity: 0;width:260px }
    .button {
        background-color:#78CDD0;
        color:#ffffff;
        height:30px;
        line-height:30px;
        width:60px;
        display:inline-block;
        text-align:center;
        font-size:14px;
        cursor:pointer;
    }
    .bar {
        height: 18px;
        background: green;
    }
</style>
<div class="row-fluid">
<div class="span1">
    <%--<div class="widget" id="navlayout">--%>
        <%--<div class="widget-header">--%>
            <%--<h3 class="icon ">布局列表</h3>--%>

            <%--<button class="btn btn-info pull-right" data-bind="click:newLayout">添加布局</button>--%>
        <%--</div>--%>
        <%--<div class="widget-content" style="height:760px;">--%>
            <%--查找：<input type="text" name="findLayout"--%>
                      <%--data-bind="value:keyword,valueUpdate: 'afterkeydown'" />--%>
            <%--<ul data-bind="foreach:filteredItems()"--%>
                <%--class="nav nav-tabs nav-stacked" id="layouts">--%>
                <%--<li><a href="#"--%>
                       <%--data-bind="text: layoutname,click:$root.editLayout"></a></li>--%>
            <%--</ul>--%>

        <%--</div>--%>
    <%--</div>--%>

    <%--<!-- /sidebar -->--%>
</div>
<div class="span10" id="content">


<c:if test="${not empty message}">
    <div class="alert alert-success">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
            ${message}
    </div>
</c:if>

<ul class="nav nav-tabs" id="configTab">
    <li class="active"><a href="#home" data-toggle="tab">首页配置表</a></li>
    <li><a href="#indexBackground" data-toggle="tab">首页背景图</a></li>
    <li><a href="#asset" data-toggle="tab">设备台账</a></li>
    <li><a href="#night" data-toggle="tab">夜景照明背景图</a></li>
    <li><a href="#report" data-toggle="tab">报表配置表</a></li>
    <li><a href="#expression" data-toggle="tab">表达式配置表</a></li>
</ul>
<div class="tab-content">
    <div class="tab-pane fade in active" id="home">
        <%--<div class="widget">--%>
            <%--<div class="widget-header">--%>
                <%--<h3 class="icon ">页面布局信息</h3>--%>
            <%--</div>--%>

            <div class="widget-content">
                <div class="file-box">

                    <div class="button">浏览</div>
                    <input type="text" style="margin-bottom:0px" class="filename" id="filetextfield" name="filetextfield">
                    <input type="file" data-url="/portal/okcsys/import/dataconfig/index/upload" onchange="document.getElementById('filetextfield').value=this.value" name="files[]" style="left:0px;width:60px" class="file" id="fileupload">
                    <div class="button startupload">上传</div>
                    <div >请点击"浏览"按钮选择文件,选择后会自动上传</div>
                    <br/>
                    <div id="progress"  class="progress progress-striped active" style="width: 645px;display: none">
                        <div class="bar" style="width: 0%;"></div>
                    </div>
                    <div id="alertSuccess"  class="alert alert-success" style="display: none">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <div class="alertMsg"> </div>
                    </div>
                    <div id="alertError"  class="alert alert-error" style="display: none">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <div class="alertMsg"> </div>
                    </div>
                </div>
            </div>
            <!-- /layout form -->
        <%--</div>--%>
    </div>
	<div class="tab-pane fade in" id="expression">
		<div class="widget-content">
		    <div class="file-box" style="width:1040px;">
		        <div class="button">浏览</div>
		        <input type="text" style="margin-bottom:0px" class="filename" id="excelfilePath" name="excelfilePath">
		        <input type="file" data-url="/okcsys/import/dataconfig/expression" onchange="showfilePath(this,'excelfilePath')" name="file" style="left:0px;width:60px" class="file" id="file">
		        <select id="import_T" onchange="type_c(this)">
		        	<option value="0">覆盖原数据</option>
		        	<option value="1">原数据追加</option>
		        </select>
		        <div class="button startimport">导入</div>
		        <div >请点击"浏览"按钮选择文件,请选择导入方式</div>
		        <div id="note_1" style="display:none;color:red;">追加时请注意：表达式编号和名称不能重复</div>
		        <br/>
		        <div id="alert_0"  class="alert alert-success" style="display: none">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <div class="alertMsg"> </div>
                </div>
                <div id="alert_12"  class="alert alert-error" style="display: none">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <div class="alertMsg"> </div>
                </div>
		    </div>
		</div>
    </div>
</div>
</div>

</div>
