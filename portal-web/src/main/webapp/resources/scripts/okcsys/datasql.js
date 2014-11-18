/**
 * 数据源
 *
 */
$(function() {
	
    //配置sql模型
    function datasql(data) {
        var self = this;
        self.datasqlid = data.datasqlid;
        self.datasourceconfigid = ko.observable(data.datasourceconfigid);
        self.script = ko.observable(data.script);
        self.comments = ko.observable(data.comments);
    }

    function preference(data) {
        var self = this;
        self.name = data.name;
        self.value =data.value;
    }
    //操作模型
    var ViewModel = function() {
        var self = this;
        
        self.datasqls = ko.observableArray();
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
        //新增sql配置
        self.addDataSql = function() {
            self.currentRow(new datasql({
            	datasqlid: 0,
            	datasourceconfigid: "",
            	script: "",
            	comments:""
            }));
        };
        //编辑sql配置
        self.editDataSql = function(row) {
            var data = self.currentRow(row);
            var mappedData = jQuery.map(data, function(item) {           	
            	//赋值
            	$("input[name='"+ item.name +"']").val(item.value);
                return new preference(item);
            });
            self.preferences(mappedData);
        };
        
        self.removeDataSql = function(row) {
        	
        	var datasqlid = self.currentRow().datasqlid;
        	if(null == datasqlid || "" == datasqlid){
        		self.tip('info', "请选择SQL配置");
        		return ;
        	}
            if (confirm('删除数据不可恢复，你确认要删除吗')) {
        		var delUrl = CONTEXT_PATH + "/okcsys/datasql/" + datasqlid;
                //删除数据库记录
                $.ajax({
                    url: delUrl,
                    type: 'DELETE',
                    dataType: "json",
   				 	contentType: "application/json; charset=utf-8",
                    success: function(data) {
                        if (data.success == true) {
                        	self.datasqls.remove(self.currentRow());
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
        
        //保存sql配置
        self.saveDataSql = function() {            
            var isValid = $("#datasql").validate({
				errorElement : "span",
				errorClass : "text-error",
				rules: {
					datasqlid: {
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
            	var action = $("#datasql").attr("action");
				var datasql = self.currentRow();
				$.post(action,datasql,function(data){
					if(data.message.success==true) {
						self.tip('success','操作成功');
						if(datasql.datasqlid==0) {
						   self.datasqls.push(datasql);
						}
					} else {
						self.tip('error',data.message.msg);
					}
				});
            }
            
        };
    };
    var datasqlViewModel = new ViewModel();
    ko.applyBindings(datasqlViewModel);
    
    //查询配置sql列表
    $.getJSON(CONTEXT_PATH + "/okcsys/datasql/list", function(data) {
        var mappedData = jQuery.map(data, function(item) {
            return new datasql(item);
        });
        datasqlViewModel.datasqls(mappedData);
    });
       
});