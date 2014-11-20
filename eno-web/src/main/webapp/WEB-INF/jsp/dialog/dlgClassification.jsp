<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<script
	src="<spring:url value="/resources/scripts/class/classification.js" />"></script>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-7-24 下午1:47:34
	LastModified Date:
	Description: 类别定义
 -->
<script>
	//编辑信息
	function editable(data, type, entity) {
		return '<a href="#" onclick=setValue("'+ entity.classificationuid +'","'+ entity.classificationuid +'","'+ entity.description +'","'+ entity.orgid +'","'+ entity.siteid +'") style="color:blue">'+ data +'</a>';
	}
			
	function setValue(id,num,description,orgid,siteid) {
		console.log(description);

		 <c:choose>
         <c:when test="${empty dlgtype}">
         	 //显示编辑		                        	  
             $("#classificationFormContent").show();
             $("#classificationid").attr("readonly","readonly");
             $(".savebutton").show();
             $("#classification #description").focus();
             //获取表格数据
             
             console.log($(this).parent());

             if($("#classificationuid").length) {
             	 $('#classificationuid').val(id);
              } else {
             	 $('<input>').attr('type','hidden').attr('id','classificationuid').attr('value',id).appendTo('form');
              }
             $("#classificationid").val(num);
             $("#description").val(description);
             $("#orgid").val(orgid);
             $("#siteid").val(siteid);
  

         </c:when>
         <c:otherwise>

			console.log(section) ;            

             if(section=="classStructure") {
     			parent.$("#classStructure #classificationid").val(num);
     			parent.$("#classStructure #classificationdesc").val(description);
     			parent.$("#classStructure #description").attr('readonly','readonly');
         	}
     		if(section=="classStructureChildren") {
     			parent.$("#classStructureChildren #classificationid").val(num);
     			parent.$("#classStructureChildren #classificationdesc").val(description);
     			parent.$("#classStructureChildren #description").attr('readonly','readonly');
         	} 
             parent.$('#dlgClassification').modal('hide');;
         </c:otherwise>
         </c:choose>

	}
			
			
</script>
<div class="row-fluid">
	<div class="widget widget-table">
		<div class="widget-content">
			<spring:url value="/class/classification" var="url"></spring:url>
			<datatables:table id="classificationtable" url="${url}" serverSide="true" processing="true">
			   <datatables:column title="分类" property="classificationid" filterable="true" renderFunction="editable" />
			   <datatables:column title="描述" property="description" filterable="true" />
			   <datatables:column title="组织" property="orgid" filterable="true" />
			   <datatables:column title="地点" property="siteid" filterable="true" />  
			</datatables:table>
		
			<c:if test="${empty dlgtype}">
			<spring:url value="/class/classification/save" var="action"></spring:url>
			<form:form modelAttribute="classification" action="${action}"
				cssClass="form-horizontal" style="margin:0px;">
				<div id="classificationFormContent" style="display: none;">
					<div class="row-fluid">
						<div class="alert alert-info">详细信息</div>
					</div>
					<div id="detailmsg"></div>
					<div class="row-fluid">
						<div class="control-group ">
							<form:label path="classificationid" cssClass="control-label">* 分类：</form:label>
							<div class="controls">
								<spring:url value="${pageContext.request.contextPath}/class/classification/check.html" var="checkUrl"></spring:url>
								<form:input path="classificationid"
									cssClass="input-medium required" title="必填字段分类名称不能为空"
									data-rule-required="true" data-placement="bottom" data-check-url="${checkUrl}" />
								<form:input path="description" cssClass="input-medium" />
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
				<div class="row-fluid tfoot">
					<div class="pull-right"
						style="text-align: right; padding-right: 15px;">
						<a class="btn" id="btnAddClassification"><span>新增</span></a>
						<button type="submit" class="btn savebutton"
							style="display: none;">
							保存
						</button>
					</div>
				</div>
			</form:form>
			</c:if>
		</div>
		<!-- .widget-content -->
	</div>
	<!-- .widget -->
</div>
<!-- 
<script>
var oTable;
var selected_id,num,description,orgid,siteid;
var section = "${section}";
$(document).ready(function() {

    oTable = $('#classificationtable').dataTable({
       
        "bFilter": false,
        "oLanguage": {
            "sUrl":"${pageContext.request.contextPath}/resources/plugins/datatables/jquery.dataTables.zh_cn.txt"
         },
         "fnInitComplete": function () {
             $("div.widget-header").append('<div style="width:100px;left:0px;position:absolute;"><span class="icon-list"></span><h3 class="">分类</h3></div>');
         },
         "sDom": '<"widget-header custom-table"frtipr>t',
        "bStateSave": false,
        "aaSorting": [[0, "asc"]],
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "${pageContext.request.contextPath}/class/classification",
        "aoColumns": [	{"mData": "classificationuid"},
                      	{"mData": "classificationid"},
        				{"mData": "description"},
        				{"mData": "orgid"},
        				{"mData": "siteid"}
        				<c:if test="${empty dlgtype}">,
        				{"mData": null, "sClass": "center","sDefaultContent": '<a href="#">删除</a>',"bSortable":false}
        				</c:if>
        			],
        "aoColumnDefs": [{"sName": "classificationuid","aTargets": [0]},
        				 {"sName": "classificationid","aTargets": [1]},
        				 {"sName": "description","aTargets": [2]},
        				 {"sName": "orgid","aTargets": [3]},
       					 {"sName": "siteid","aTargets": [4]},
        				 {"sClass": "hide","aTargets": [0]}
        				],
        "fnDrawCallback": function(oSettings) {
            $('#classificationtable tbody tr').each(function() {
                selected_id = null,num=null,description=null,orgid = null,siteid=null;
              //第一列
                firstColumn = $(this).children(":eq(1)");
				$(firstColumn).css('cursor','pointer');
                
                $(firstColumn).click(function(data) {
                	oTable.$('tr.row_selected').removeClass('row_selected');
                    $(this).parent().addClass('row_selected');
                    selected_id = $(this).parent().children(":first").html();                   
                    num = $(this).parent().children(":eq(1)").html();
                    description = $(this).parent().children(":eq(2)").html();
                    orgid = $(this).parent().children(":eq(3)").html();
                    siteid = $(this).parent().children(":eq(4)").html();
                    
                    if (selected_id != null) {
                        <c:choose>
                        <c:when test="${empty dlgtype}">
                        	 //显示编辑		                        	  
                            $("#classificationFormContent").toggle();
                            $("#classificationid").attr("readonly","readonly");
                            $(".savebutton").show();
                            $("#description").focus();
                            //获取表格数据
                            
                            console.log($(this).parent());
                            
                            //var data = oTable.fnGetData($(this).parent());

                            if($("#classificationuid").length) {
                            	 $('#classificationuid').val(selected_id);
                             } else {
                            	 $('<input>').attr('type','hidden').attr('id','classificationuid').attr('value',selected_id).appendTo('form');
                             }
                            $("#classificationid").val(num);
                            $("#description").val(description);
                            $("#orgid").val(orgid);
                            $("#siteid").val(siteid);
                            
                            //为表单赋值
                           // $.each(data, function(field, value) {
                           //     $("#" + field).val(value);
                           // });

                        </c:when>
                        <c:otherwise>

    						console.log(section) ;            

    	                    if(section=="classStructure") {
    	            			parent.$("#classStructure #classificationid").val(num);
    	            			parent.$("#classStructure #classificationdesc").val(description);
    	            			parent.$("#classStructure #description").attr('readonly','readonly');
    	                	}
                    		if(section=="classStructureChildren") {
                    			parent.$("#classStructureChildren #classificationid").val(num);
                    			parent.$("#classStructureChildren #classificationdesc").val(description);
                    			parent.$("#classStructureChildren #description").attr('readonly','readonly');
                        	} 
                            parent.$('#dlgClassification').modal('hide');;
                        </c:otherwise>
                        </c:choose>
                        }
                    
                });
                
                $(this).click(function() {
                    oTable.$('tr.row_selected').removeClass('row_selected');
                    $(this).addClass('row_selected');
                    selected_id = $(this).children(":first").html();
                    num = $(this).children(":eq(1)").html();
                    description = $(this).children(":eq(2)").html();
                   
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
   
    /*  oTable.columnFilter({
			sPlaceHolder: "head:after"
		});  */
});
</script> -->