
/**
 * 系统日志
 *  @author CHENPING
 * */

var reqUrl = CONTEXT_PATH + "/okcsys/logs";
$(function(){
	syslogs.query();
	syslogs.list(reqUrl);
	
});



var oLogsDataTable;
/**
 * 系统日志操作
 * */
var syslogs = {
	//列表
	list : function(url) {
		
		console.log(url);
		
		oLogsDataTable = $('#logstable').dataTable({
			"bFilter": false,
		    "oLanguage": {
		         "sUrl":CONTEXT_PATH+"/resources/plugins/datatables/jquery.dataTables.zh_cn.txt"
		      },
		    "fnInitComplete": function () {
		       
		     },
		     "sDom": "<'row-fluid'<'span6'T><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
		     "iDisplayLength": 15,
	         "aLengthMenu": [[15, 50, 100], [15, 50, 100]],
	         "sPaginationType": "full_numbers",
			"bStateSave" : true,
			"aaSorting" : [ [ 0, "desc" ] ],
			"bProcessing" : true,
			"bServerSide" : true,
			"sAjaxSource" : reqUrl,
			"aoColumns" : [ {"mData" : "logid"},
			               /* {"mData" : "userid" },*/
			       			{"mData" : "dated" },
			                {"mData" : "logger"},
			                {"mData" : "level"},
			                {"mData" : "message"},
			               ],
			"aoColumnDefs": [
			                 { "sName": "logid",   "aTargets": [ 0 ] },
			         		 /*{ "sName": "userid",   "aTargets": [ 1 ] ,"bSortable":false },*/
			                 { "sName": "dated",  "aTargets": [ 1 ] ,"bSortable":false },
			                 { "sName": "logger",  "aTargets": [ 2 ] ,"bSortable":false },
			                 { "sName": "level",  "aTargets": [ 3 ] ,"bSortable":false },
			                 { "sName": "message",  "aTargets": [ 4 ] ,"bSortable":false },
			                 { "sClass" : "hide",   "aTargets" : [ 0 ]}
			               ],  
			"fnServerParams":function(aoData) {
				aoData.push({ "name": "startdate", "value": $.trim($("#startdate").val()) });
				aoData.push({ "name": "enddate","value": $.trim($("#enddate").val()) });
				aoData.push({ "name": "level","value":$.trim($("#level").val()) });
			},
		  "fnDrawCallback": function ( oSettings ) {
			                   $('#logstable tbody tr').each( function () {
			                	   select_id = null;
			                       $(this).click( function () {
			                    	   oLogsDataTable.$('tr.row_selected').removeClass('row_selected');
			                           $(this).addClass('row_selected');
			                           select_id =  $(this).children(":first").html();
			                           if(select_id!=null && !isNaN(select_id)) {
										//	window.location.href=CONTEXT_PATH+"/pm/edit/"+ select_id + ".html";
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
		
		
		
	},
	query : function() {
		 // disabling dates
       var nowTemp = new Date();
        var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

        var checkin = $('#startdate').datepicker({
          onRender: function(date) {
            return date.valueOf() < now.valueOf() ? 'disabled' : '';
          }
        }).on('changeDate', function(ev) {
          if (ev.date.valueOf() > checkout.date.valueOf()) {
            var newDate = new Date(ev.date)
            newDate.setDate(newDate.getDate() + 1);
            checkout.setValue(newDate);
          }
          checkin.hide();
          $('#enddate')[0].focus();
        }).data('datepicker');
        var checkout = $('#enddate').datepicker({
          onRender: function(date) {
            return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
          }
        }).on('changeDate', function(ev) {
          checkout.hide();
        }).data('datepicker');
		
		
		
		$("#query").click(function(){
			var startdate = $("#startdate").val();
			var enddate = $("#enddate").val();
			var level = $("#level").val();
			
			reqUrl += "?startdate="+ startdate +"&enddate="+ enddate +"&level="+ level;
			oLogsDataTable.fnDestroy();
			syslogs.list(reqUrl);
			$("#startdate").val(startdate);
			$("#enddate").val(enddate);
			$("#level").val(level);
			
		}); 
	}
};