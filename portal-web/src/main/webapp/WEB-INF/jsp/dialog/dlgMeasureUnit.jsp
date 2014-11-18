<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 记量单位列表 -->
<div class="widget widget-table">
	<div class="widget-header">
		<span class="icon-list"></span>
		<h3 class="icon chart">计量单位</h3>
	</div>
	<div class="widget-content">
		<table class="table table-bordered table-striped data-table" id="measureunittable">
			<thead>
				<tr>
					<th>计量单位</th>
					<th>缩写</th>
					<th>描述</th>
					<th>地点</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>

	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->
<script>
var oTable;
var selected_id,description;
$(document).ready(function() {
	oTable = $('#measureunittable').dataTable({
		"sDom": 'l<"toolbar">frtip',
		"bFilter" : false,
		"bStateSave" : false,
		"aaSorting" : [ [ 0, "asc" ] ],
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sLengthMenu" : "Display _MENU_ records per page",
			"sZeroRecords" : "--没有要显示的行--",
			"sInfo" : "Showing _START_ to _END_ of _TOTAL_ records",
			"sInfoEmpty" : "显示 0 to 0 of 0 记录"
		},
		"bProcessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "${pageContext.request.contextPath}/measureunit/list",
		"aoColumns" : [ 
		       			{"mData" : "measureunitid" },
		                {"mData" : "abbreviation"},
		                {"mData" : "description"}, 
		                {"mData" : "siteid"}
		               ],
		"aoColumnDefs": [
		                 { "sName": "measureunitid",   "aTargets": [ 0 ] },
		                 { "sName": "abbreviation",  "aTargets": [ 1 ] },
		                 { "sName": "description", "aTargets": [ 2 ] },
		                 { "sName": "siteid",  "aTargets": [ 3 ] }
		               ],           
	  "fnDrawCallback": function ( oSettings ) {
		                   $('#measureunittable tbody tr').each( function () {
		                	   selected_id = null,description= null;
		                       $(this).click( function () {
		                    	   oTable.$('tr.row_selected').removeClass('row_selected');
		                           $(this).addClass('row_selected');
		                           selected_id =  $(this).children(":first").html();
		                           description = $(this).children(":eq(2)").html();
		                           console.log("selected measureunitid is:" + selected_id);
								   parent.$("#measureunitid").val(selected_id);
								  /*  if( parent.$("#description").length) {
									   parent.$("#description").val(description);
									} */
								   if( parent.$("#measureunit\\.desscription").length) {
									   parent.$("#measureunit\\.desscription").val(description);
									}
		                           parent.$('#dlgMeasureUnit').modal('hide');;
		                       });
		                       $(this).hover( function () {
		                    	   $(this).addClass( 'row_highlighted');
		                       }, function(){
		                    	   $(this).removeClass('row_highlighted');
		                       });
		                   });
		               }
	});

	
});

</script>