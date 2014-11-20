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
    <li class="active"><a href="#home" data-toggle="tab">首页参数配置</a></li>
</ul>
<div class="tab-content">
    <div class="tab-pane fade in active" id="home">
        <%--<div class="widget">--%>
            <%--<div class="widget-header">--%>
                <%--<h3 class="icon ">页面布局信息</h3>--%>
            <%--</div>--%>

            <div class="widget-content">
                <div class="file-box">

					<form method="post" action="${pageContext.request.contextPath}/okcsys/homepage/savaparameterconfig">
					<table>
						<tr><td><textarea rows="30"  style="width:800px;height:500px" name="propertiesFile" id="propertiesFile" >${propertiesFile}</textarea></td></tr>
						<tr><td style="margin-left:40px;"><button type="submit"  data-bind="">保存</button><input type="reset"  value="重置"></td></tr>
					</table>
			
					</form>
                </div>
            </div>
            <!-- /layout form -->
        <%--</div>--%>
    </div>

</div>
</div>

</div>
