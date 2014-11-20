/**
 * 系统组件
 *
 */
$(function() {
	
    //数据源模型
    function datasourceconfig(data) {
        var self = this;
        self.datasourceconfiguid = data.datasourceconfiguid;
        self.datasourceconfigid = ko.observable(data.datasourceconfigid);
        self.sourcename = ko.observable(data.sourcename);
        self.dbtype = ko.observable(data.dbtype);
        self.dbname = ko.observable(data.dbname);
        self.ipaddress = ko.observable(data.ipaddress);
        self.port = ko.observable(data.port);
        self.userid = ko.observable(data.userid);
        self.password = ko.observable(data.password);
        self.description = ko.observable(data.description);
    }

    function preference(data) {
        var self = this;
        self.name = data.name;
        self.value =data.value;
    }
    //操作模型
    var ViewModel = function() {
        var self = this;
        
        self.datasourceconfigs = ko.observableArray();
        self.currentRow = ko.observable({});
        
        self.preferences = ko.observable({});
        
        //提示信息
        self.tip = function(type, msg) {
        	console.log(msg);
            $.pnotify({
                title: '操作提示',
                text: msg,
                type: type
            });
        };
        //新增数据源
        self.addDataSource = function() {
            self.currentRow(new datasourceconfig({
            	datasourceconfiguid: 0,
            	datasourceconfigid: "",
            	sourcename: "",
            	dbtype:"",
            	dbname:"",
            	ipaddress:"",
            	port:1433,
            	userid:"",
            	password:"",
            	description:""
            }));
            $("#btn_connection").html("连接测试");
            $("#btn_connection").removeAttr("disabled");
        };
        //编辑数据源
        self.editDataSource = function(row) {
            var data = self.currentRow(row);
            var mappedData = jQuery.map(data, function(item) {           	
            	//赋值
            	$("input[name='"+ item.name +"']").val(item.value);
                return new preference(item);
            });
            self.preferences(mappedData);
            $("#btn_connection").html("连接测试");
            $("#btn_connection").removeAttr("disabled");
        };
      //删除数据源
        self.removeDatasource = function(row) {
        	
        	var datasourceconfiguid = self.currentRow().datasourceconfiguid;
        	if(null == datasourceconfiguid || "" == datasourceconfiguid){
        		self.tip('info', "请选择数据源");
        		return ;
        	}
            if (confirm('删除数据不可恢复，你确认要删除吗')) {
        		var delUrl = CONTEXT_PATH + "/okcsys/datasources/" + datasourceconfiguid;
                //删除数据库记录
                $.ajax({
                    url: delUrl,
                    type: 'DELETE',
                    dataType: "json",
   				 	contentType: "application/json; charset=utf-8",
                    success: function(data) {
                    	console.log(data);
                        if (data.success == true) {
                        	self.datasourceconfigs.remove(self.currentRow());
                            self.tip('success', "删除成功");
                        } else {
                            self.tip('error', "删除失败");
                        }
                    },
                    error: function(err) {
                    	console.log(err);
                        self.tip('error', "删除失败," + err);
                    }
                });
            }
        };
        //保存数据源
        self.saveDatasource = function() {            
            var isValid = $("#datasource").validate({
				errorElement : "span",
				errorClass : "text-error",
				rules: {
					datasourceconfiguid: {
						required:true,
						minlength:2,
						maxlength:15,
						number:true	
					}
				},
				success: function(label) {
					label.text("ok!").addClass("success");
				}}).form();
            if (isValid) {
            	var action = $("#datasource").attr("action");
				var datasourceconfig = self.currentRow();
				$.post(action,datasourceconfig,function(data){
					if(data.message.success==true) {
						self.tip('success','操作成功');
						if(datasourceconfig.datasourceconfiguid==0) {
						   self.datasourceconfigs.push(datasourceconfig);
						}
					} else {
						self.tip('error',data.message.msg);
					}
				});
            }
            
        };
        //测试数据源
        self.connectiontest = function() {  
        	var btn = $("#btn_connection");
        	btn.html("正在连接中.......");
        	var type=$("#dbtype").val();
        	var name=$("#dbname").val();
        	var ip = $("#ipaddress").val();
        	var port=$("#port").val();
        	var user=$("#userid").val();
        	var pwd =$("#password").val();
        	if(type==null||type==""||name==null||name==""||ip==null||ip==""||port==null||port==""||user==null||user==""||pwd==null||pwd==""){
        		self.tip('info', "数据源数据不全！！");
        	     btn.html("连接测试");
        	     return;
        	}
        	  $.ajax({
        		  url: CONTEXT_PATH+ "/okcsys/datasources/connect/test",
        		  type:"post",
        		  data:"type="+type+"&name="+name+"&ip="+ip+"&port="+port+"&user="+user+"&pwd="+pwd,
        		  success:function(data){
        			 if(1==data) {
        				 btn.html("测试成功");
        			 };
        			 if(2==data){ btn.html("测试失败，请检查配置！");};
        		  }});
        };
    };
    
    var datasourceViewModel = new ViewModel();
    ko.applyBindings(datasourceViewModel);
    //查询数据源列表
    $.getJSON(CONTEXT_PATH + "/okcsys/datasources/list", function(data) {
        var mappedData = jQuery.map(data, function(item) {
            return new datasourceconfig(item);
        });
        datasourceViewModel.datasourceconfigs(mappedData);
    });
       
});
//添加默认端口号 ztl 【08031558】
$("#dbtype").change(function(){
	var dbtype = $(this).val();
	if(dbtype==="mysql"){
		$("#port").val(3306);
	}
	if(dbtype==="oracle"){
		$("#port").val(1521);
	}
	if(dbtype==="sqlserver"){
		$("#port").val(1433);
	}
	if(dbtype==="access"){
		$("#port").val("");
	}
});