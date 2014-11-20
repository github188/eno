<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-27 上午10:08:39
	LastModified Date:
	Description:
 -->
<div class="widget widget-table">
	<div class="widget-content">
		<table class="table table-bordered table-striped " ID="failurelisttable">
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
		</table>

	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->


<script>
var oTable;
var selected_id,code;
$(document).ready(function() {
	
	oTable = $('#failurelisttable').dataTable({
		"bFilter": false,
	    "oLanguage": {
	         "sUrl":CONTEXT_PATH+"/resources/plugins/datatables/jquery.dataTables.zh_cn.txt"
	      },
	    "fnInitComplete": function () {
	          $("div.failurelisttable-header").append('<div style="width:180px;left:0px;position:absolute;"><span class="icon-list"></span><h3 class="">故障</h3></div>');
	     },
	         "sDom": '<"widget-header failurelisttable-header"frtipr>t',
		"bStateSave" : false,
		"aaSorting" : [ [ 0, "asc" ] ],
		"bProcessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "${pageContext.request.contextPath}/failurecode/list",
		"aoColumns" : [ {"mData" : "failurecodeid"},
		       			{"mData" : "failurecode" },
		                {"mData" : "description","bSortable":false},
						{"mData" : "orgid","bSortable":false}
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
		                	   selected_id = null,code = null;
		                       $(this).click( function () {
		                    	   oTable.$('tr.row_selected').removeClass('row_selected');
		                           $(this).addClass('row_selected');
		                           selected_id =  $(this).children(":first").html();
		                           code = $(this).children(":eq(1)").html();
		                           if(selected_id!=null) {
		                        	   if(parent.$("form#asset").length) {
		                        		   parent.$("#asset #failurecode").val(code);
		                        	   }
		                        	   parent.$('#dlgFailureList').modal('hide');;
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


  
});

</script>