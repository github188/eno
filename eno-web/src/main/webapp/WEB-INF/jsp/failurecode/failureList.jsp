<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-7-9 下午5:21:55
	LastModified Date:
	Description: 故障列表
 -->
 
<div class="widget widget-table">
	<div class="widget-header">
		<span class="icon-list"></span>
		<h3 class="icon chart">故障类别</h3>
	</div>
	<div class="widget-content">
		<!-- <table class="table table-bordered table-striped " ID="failurelisttable">
			<thead>
				<tr>
					<th></th>
					<th width="40%">故障代码</th>
					<th>描述</th>
					<th width="10%">组织</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table> -->
		
		<script>
			//编辑信息
			function editable(data, type, entity) {
				return '<a href="${pageContext.request.contextPath}/failurecode/edit/'+ entity.failurecodeid + '.html"" style="color:blue">'
						+ data + '</a>';
			}
		</script>


		<spring:url value="/failurecode/list" var="url"></spring:url>
		<datatables:table id="failurelisttable" url="${url}" serverSide="true"
			processing="true">
			<datatables:column title="故障代码" property="failurecode" filterable="true" renderFunction="editable" />
			<datatables:column title="描述" property="description"
				filterable="true" />
			<datatables:column title="组织" property="orgid" />
		</datatables:table>

	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->
 
<script>
var oTable;
var selected_id;
$(document).ready(function() {
	
	/* oTable = $('#failurelisttable').dataTable({
		"sDom": 'l<"toolbar">frtip',
		//"bFilter" : false,
		"bStateSave" : false,
		"aaSorting" : [ [ 0, "asc" ] ],
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sLengthMenu" : "Display _MENU_ records per page",
			"sZeroRecords" : "--没有要显示的行--",
			"sInfo" : "第 _START_ - _END_ 个，(共  _TOTAL_ 个)",
			"sInfoEmpty" : "显示 0 to 0 of 0 记录",
			"sSearch": "查找："
		},
		"bProcessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "${pageContext.request.contextPath}/failurecode/list",
		"aoColumns" : [ {"mData" : "failurecodeid"},
		       			{"mData" : "failurecode" },
		                {"mData" : "description"},
						{"mData" : "orgid"}
		               ],
		"aoColumnDefs": [
						 { "sName": "failurecodeid",   "aTargets": [ 0 ] },
		         		 { "sName": "failurecode",   "aTargets": [ 1 ] },
		                 { "sName": "description",  "aTargets": [ 2 ] },
						 { "sName": "orgid",  "aTargets": [ 3 ] },
		                 { "sClass" : "hide",   "aTargets" : [ 0 ]}
		               ],           
	  "fnDrawCallback": function ( oSettings ) {
		                   $('#failurelisttable tbody tr').each( function () {
		                	   selected_id = null;
		                       $(this).click( function () {
		                    	   oTable.$('tr.row_selected').removeClass('row_selected');
		                           $(this).addClass('row_selected');
		                           selected_id =  $(this).children(":first").html();
		                           if(selected_id!=null) {
										window.location.href="${pageContext.request.contextPath}/failurecode/edit/"+ selected_id + ".html";
			                           }
		                       });
		                       $(this).hover( function () {
		                    	   $(this).addClass( 'row_highlighted');
		                       }, function(){
		                    	   $(this).removeClass('row_highlighted');
		                       });
		                   });
		               }
	});
 */

  
});

</script>






