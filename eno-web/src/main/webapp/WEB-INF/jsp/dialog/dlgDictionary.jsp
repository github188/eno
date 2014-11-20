<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-9-17 下午4:35:05
	LastModified Date:
	Description:
 -->
<div class="row-fluid">
	<div class="widget widget-table">
		<div class="widget-content">
			<table class="table table-hover table-bordered table-striped" id="dictionarytable">
			<thead>
				<tr>
					<th></th>
					<th>值</th>
					<th>描述</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</div>
		<!-- .widget-content -->
	</div>
	<!-- .widget -->
</div>
<script>
var oTable;
var selected_id,description;
$(document).ready(function() {
	console.log("type:${type}");


	oTable = $('#companiestable').dataTable({
		"bFilter": false,
	    "oLanguage": {
	         "sUrl":CONTEXT_PATH+"/resources/plugins/datatables/jquery.dataTables.zh_cn.txt"
	      },
	    "fnInitComplete": function () {
	          $("div.dictionarytable-header").append('<div style="width:180px;left:0px;position:absolute;"><span class="icon-list"></span><h3 class=""></h3></div>');
	     },
	     "sDom": '<"widget-header dictionarytable-header"frtipr>t',
		"bStateSave" : false,
        "aaSorting": [[0, "asc"]],
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "${pageContext.request.contextPath}/common/dictionary/aln?dictid=${dictid}",
        "aoColumns" : [ 
		                {"mData" : "dmvalue" },
		       			{"mData" : "description" }
		               ],
		"aoColumnDefs": [
		         		 { "sName": "dmvalue",   "aTargets": [ 0 ] },
		                 { "sName": "description",  "aTargets": [ 1 ] }
        				],
        "fnDrawCallback": function(oSettings) {
            $('#dictionarytable tbody tr').each(function() {
                selected_id = null,description=null;
                $(this).click(function() {
                    oTable.$('tr.row_selected').removeClass('row_selected');
                    $(this).addClass('row_selected');
                    selected_id = $(this).children(":first").html();
                    description = $(this).children(":eq(1)").html();
                    if (selected_id != null) {
						if(parent.$("form#asset").length) {
							parent.$("#asset #specclass").val(num);
						}				
                        parent.$('#dlgDictionary').modal('hide');;
                        
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

    /*  oTable.columnFilter({
			sPlaceHolder: "head:after"
		});  */
});
</script>