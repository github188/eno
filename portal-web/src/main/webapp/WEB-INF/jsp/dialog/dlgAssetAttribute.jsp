<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-7-24 下午1:48:37
	LastModified Date:
	Description: 资产属性定义
 -->
<script
	src="<spring:url value="/resources/scripts/class/assetattribute.js" />"></script>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-7-24 下午1:47:34
	LastModified Date:
	Description: 类别定义
 -->

<div class="row-fluid">
	<div class="widget widget-table">
		<div class="widget-content">			
			<script>
			//编辑信息
			function editable(data, type, entity) {
				 <c:choose>
                 <c:when test="${empty dlgtype}">
                	return data;
                 </c:when>
                 <c:otherwise>
                 return '<a href="#" onclick=setValue("'+ entity.assetattrid +'","'+ entity.description +'","'+ entity.measureunitid +'") style="color:blue">'+ data +'</a>';
                 </c:otherwise>
                 </c:choose>
                 
				
			}
			function setValue(num,description,measureunitid) {
				
				parent.$("#assetattrid").val(num);
            	if(description.length>0) {
                	if(parent.$("#assetAttribute\\.description").length) {
                		parent.$("#assetAttribute\\.description").val(description);
                    }
                if(parent.$("#assetattributeid").length) {
                	parent.$("#assetattributeid").val(num);
                 }

            	}
            	if(parent.$("#measureunitid").length) {
                	parent.$("#measureunitid").val(measureunitid);
                 }
            	
				
				
				console.log(description);

				parent.$('#dlgAssetAttribute').modal('hide');		
				
				
			}
			
			</script>
			
			<spring:url value="/class/assetattribute" var="url"></spring:url>
			<datatables:table id="assetattributetable" url="${url}" serverSide="true"
				processing="true" theme="bootstrap2" filterPlaceholder="head_after">
				<datatables:column title="属性" property="assetattrid" filterable="true"
					renderFunction="editable" />
				<datatables:column title="描述" property="description"
					filterable="true" />
				<datatables:column title="计量单位" property="measureunitid" filterable="true" />
				<datatables:column title="数据类型" property="datatype" filterable="true" />
				<datatables:column title="域" property="domainid" filterable="true" />
				<datatables:column title="前缀" property="attrdescprefix" filterable="true" />
			</datatables:table>
			
			
			<spring:url value="/class/assetattribute/save" var="action"></spring:url>
			<form:form modelAttribute="assetAttribute" action="${action}"
				cssClass="form-horizontal" style="margin:0px;">
				<div id="assetAttributeFormContent" style="display: none;">
					<div class="row-fluid">
						<div class="alert alert-info">详细信息</div>
					</div>
					<div id="detailmsg"></div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group ">
								<form:label path="assetattrid" cssClass="control-label">* 属性：</form:label>
								<div class="controls">
									<spring:url
										value="/class/assetattribute/check.html"
										var="checkUrl"></spring:url>
									<form:input path="assetattrid" cssClass="input-medium required"
										title="必填字段属性名称不能为空" data-rule-required="true"
										data-placement="bottom" data-check-url="${checkUrl}" placeholder="属性编码"  />
									<form:input path="description" cssClass="input-medium" placeholder="属性名称" />
		
								</div>
							</div>
							<div class="control-group ">
								<form:label path="measureunitid" cssClass="control-label">计量单位：</form:label>
								<div class="controls">
									<form:input path="measureunitid" cssClass="span10" /> 
									<i class="icon-zoom-in showMeasureunitWindow"
													link="<spring:url value="/measureunit/list.html" />"
													style="cursor: pointer;" title="选择计量单位"></i>
								</div>
							</div>
							<div class="control-group ">
								<form:label path="datatype" cssClass="control-label">数据类型：</form:label>
								<div class="controls">
									<form:input path="datatype" cssClass="input-medium" />
								</div>
							</div>
							<div class="control-group ">
								<form:label path="domainid" cssClass="control-label">域：</form:label>
								<div class="controls">
									<form:input path="domainid" cssClass="input-medium" />
								</div>
							</div>
						</div>
						<div class="span6">
						<div class="control-group ">
								<form:label path="attrdescprefix" cssClass="control-label">前缀：</form:label>
								<div class="controls">
									<form:input path="attrdescprefix" cssClass="input-medium" />
								</div>
							</div>
							<div class="control-group ">
								<form:label path="orgid" cssClass="control-label">组织：</form:label>
								<div class="controls">
									<form:input path="orgid" cssClass="input-medium" />
								</div>
							</div>
							<div class="control-group ">
								<form:label path="siteid" cssClass="control-label">地点：</form:label>
								<div class="controls">
									<form:input path="siteid" cssClass="input-medium" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${empty dlgtype}">
				<div class="row-fluid tfoot" id="toolbar">
					<div class="pull-right"
						style="text-align: right; padding-right: 15px;">
						<a class="btn" id="btnAddAssetAttribute"><span>新增</span></a>
						<button type="submit" class="btn btn-primary savebutton"
							style="display: none;">
							保存
						</button>
					</div>
				</div>
				</c:if>
			</form:form>
		</div>
		<!-- .widget-content -->
	</div>
	<!-- .widget -->
</div>

<%@ include file="../common/dialog/dlgMeasureUnit.jsp" %>
<script>
var oTable;
var selected_id,num,description,measureunitid;
$(document).ready(function() {


	//显示计量单位定义窗口	
	dialog.measureunit();

   /*  oTable = $('#assetattributetable').dataTable({
        "sDom": '<"widget-header"frtipr>t',
        "bFilter": false,
        // sPaginationType: "full_numbers",
        oLanguage: {
            "sProcessing":   "处理中...",
            "sLengthMenu":   "显示 _MENU_ 项结果",
            "sZeroRecords":  "没有匹配结果",
            "sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix":  "",
            "sSearch":       "搜索:",
            "sUrl":          "",
            "oPaginate": {
                "sFirst":    "首页",
                "sPrevious": "上页",
                "sNext":     "下页",
                "sLast":     "末页"
            }
        },
        // "sDom": '<"toolbar"iflp>rt<"clear">',
         "iDisplayLength": 15,
        "aLengthMenu": [[15, 30, 50,100], [15, 30, 50,100]],
        "bStateSave": false,
        "aaSorting": [[0, "asc"]],
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "${pageContext.request.contextPath}/class/assetattribute",
        "aoColumns": [	{"mData": "assetattributeid"},
                      	{"mData": "assetattrid"},
        				{"mData": "description"}, 
        				{"mData": "measureunitid"},
        				{"mData": "datatype"},
        				{"mData": "domainid"},
        				{"mData": "attrdescprefix"}<c:if test="${empty dlgtype}">,
        				{"mData": null,"bSortable":false, "sClass": "center","sDefaultContent": '<a href="#">删除</a>'}</c:if>
        			],
        "aoColumnDefs": [{"sName": "assetattributeid","aTargets": [0]},
        				 {"sName": "assetattrid","aTargets": [1]},
        				 {"sName": "description","aTargets": [2]},
        				 {"sName": "measureunitid","aTargets": [3]},
        				 {"sName": "datatype","aTargets": [4]},
        				 {"sName": "domainid","aTargets": [5]},
       					 {"sName": "attrdescprefix","aTargets": [6]},
        				 {"sClass": "hide","aTargets": [0]}
        				],
        "fnDrawCallback": function(oSettings) {
            $('#assetattributetable tbody tr').each(function() {
                selected_id = null,num=null,description=null,measureunitid=null;
                $(this).click(function() {
                    oTable.$('tr.row_selected').removeClass('row_selected');
                    $(this).addClass('row_selected');
                    selected_id = $(this).children(":first").html();
                    num = $(this).children(":eq(1)").html();
                    description = $(this).children(":eq(2)").html();
                    measureunitid = $(this).children(":eq(3)").html();
                    if (selected_id != null) {
                          <c:choose>
                        <c:when test="${empty dlgtype}">
	                        //显示编辑		                        	  
	                        $("#assetAttributeFormContent").toggle();
	                        $("#assetattrid").attr("readonly","readonly");
	                        $(".savebutton").show();
	                        $("#description").focus();
	                        //获取表格数据
	                        var data = oTable.fnGetData(this);
	                        $('<input>').attr('type','hidden').attr('id','assetattributeid').appendTo('form');
	                        //console.log(data);
	                        //为表单赋值
	                        $.each(data,
	                        	function(field, value) {
	                           	 	$("#" + field).val(value);
	                        });

                        </c:when>
                        <c:otherwise>
                        	parent.$("#assetattrid").val(num);
                        	if(description.length>0) {
                            	if(parent.$("#assetAttribute\\.description").length) {
                            		parent.$("#assetAttribute\\.description").val(description);
                                }
                            if(parent.$("#assetattributeid").length) {
                            	parent.$("#assetattributeid").val(selected_id);
                             }
                        		//parent.$("#description").val(description);
                        		//parent.$("#description").attr('readonly','readonly');
                        	}
                        	if(parent.$("#measureunitid").length) {
                            	parent.$("#measureunitid").val(measureunitid);
                             }
                        	
                        	
                            parent.$('#dlgAssetAttribute').modal('hide');
                        </c:otherwise>
                        </c:choose>
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
    $("div.widget-header").append('<div style="width:100px;left:0px;position:absolute;"><span class="icon-list"></span><h3 class="">属性</h3></div>');
 */
    /*  oTable.columnFilter({
			sPlaceHolder: "head:after"
		});  */
});
</script>