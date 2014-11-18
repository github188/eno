<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<script
	src="<spring:url value="/resources/scripts/measureunit/measureunit.js" />"></script>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-7-24 下午1:47:34
	LastModified Date:
	Description: 计量单位定义
 -->
<div class="row-fluid">
	<div class="widget widget-table">
		<div class="widget-content">
			<table class="table table-bordered" id="measureunittable">
				<thead>
					<tr>
						<th></th>
						<th>计量单位</th>
						<th>描述</th>
						<th width="15%">缩写</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<spring:url value="/measureunit/save" var="action"></spring:url>
			<form:form modelAttribute="measureUnit" action="${action}"
				cssClass="form-horizontal" style="margin:0px;">
				<div id="measureUnitFormContent" style="display: none;">
					<div class="row-fluid">
						<div class="alert alert-info">详细信息</div>
					</div>
					<div id="detailmsg"></div>
					<div class="row-fluid">
						<div class="control-group ">
							<form:label path="measureunitid" cssClass="control-label">* 计量单位：</form:label>
							<div class="controls">
								<spring:url value="/measureunit/check.html" var="checkUrl"></spring:url>
								<form:input path="measureunitid"
									cssClass="input-medium required" title="必填字段分类名称不能为空"
									data-rule-required="true" data-placement="bottom" data-check-url="${checkUrl}" />
								<form:input path="description" cssClass="input-medium" />
								

							</div>
						</div>
						<div class="control-group ">
							<form:label path="abbreviation" cssClass="control-label">缩写：</form:label>
							<div class="controls">
								<form:input path="abbreviation" cssClass="input-medium" />
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid tfoot">
					<div class="pull-right"
						style="text-align: right; padding-right: 15px;">
						<a class="btn" id="btnAddMeasureUnit"><span><i
								class="icon-plus"></i> 新增</span></a>
						<button type="submit" class="btn btn-primary savebutton"
							style="display: none;">
							<i class="icon-ok-sign"></i> 保存
						</button>
					</div>
				</div>
			</form:form>
		</div>
		<!-- .widget-content -->
	</div>
	<!-- .widget -->
</div>


<script>
var oTable;
var selected_id;
$(document).ready(function() {

    oTable = $('#measureunittable').dataTable({
        "sDom": '<"widget-header"frtipr>t',
        "bFilter": false,
        // sPaginationType: "full_numbers",
        oLanguage: {
            sSearch: "<span>搜索:</span> ",
            sLengthMenu: "_MENU_ <span>条第页</span>",
            sZeroRecords: "--没有要显示的行--",
            sInfo: "第 _START_ - _END_ 个,(共  _TOTAL_ 个)",
            sInfoEmpty: "显示 0 到 0 共 0 记录",
            "oPaginate": {
                "sPrevious": "上页",
                "sNext": "下页"
            }
        },
        // "sDom": '<"toolbar"iflp>rt<"clear">',
        "bStateSave": false,
        "aaSorting": [[0, "asc"]],
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "${pageContext.request.contextPath}/measureunit/list",
        "aoColumns": [	{"mData": "measureunituid"},
                      	{"mData": "measureunitid"},
        				{"mData": "description"},
        				{"mData": "abbreviation"},
        				{"mData": null, "sClass": "center","sDefaultContent": '<a href="#">删除</a>'}
        			],
        "aoColumnDefs": [{"sName": "measureunituid","aTargets": [0]},
        				 {"sName": "measureunitid","aTargets": [1]},
        				 {"sName": "description","aTargets": [2]},
        				 {"sName": "abbreviation","aTargets": [3]},
        				 {"sClass": "hide","aTargets": [0]}
        				],
        "fnDrawCallback": function(oSettings) {
            $('#measureunittable tbody tr').each(function() {
                selected_id = null;
                $(this).click(function() {
                    oTable.$('tr.row_selected').removeClass('row_selected');
                    $(this).addClass('row_selected');
                    selected_id = $(this).children(":first").html();
                    if (selected_id != null) {
                        //window.location.href="${pageContext.request.contextPath}/classification/"+ selected_id + ".html";
                        //显示编辑		                        	  
                        $("#measureUnitFormContent").toggle();
                        $(".savebutton").show();
                        $("#measureunitid").attr("readonly","readonly");
                        $("#description").focus();
                        
                        //获取表格数据
                        var data = oTable.fnGetData(this);
                        $('<input>').attr('type','hidden').attr('id','measureunituid').appendTo('form');
                        //console.log(data);
                        //为表单赋值
                        $.each(data, function(field, value) {
                            //console.log(field);
                            $("#" + field).val(value);
                        });
                    }
                });
                $(this).hover(function() {
                    $(this).addClass('row_highlighted');
                },
                function() {
                    $(this).removeClass('row_highlighted');
                });
            });
        }
    });
    $("div.widget-header").append('<div style="width:160px;left:0px;position:absolute;"><span class="icon-list"></span><h3 class="">计量单位</h3></div>');

    /*  oTable.columnFilter({
			sPlaceHolder: "head:after"
		});  */
});
</script>