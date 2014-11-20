/**
 * 系统组件
 *
 */
$(function() {
	
    //app模型
    function dataconfigapp(data) {
        var self = this;
        self.dataconfigappid = data.dataconfigappid;
        self.configid = ko.observable(data.configid);
        self.configtype = ko.observable(data.configtype);
        self.app = ko.observable(data.app);
    }

    function preference(data) {
        var self = this;
        self.name = data.name;
        self.value =data.value;
    }
    //操作模型
    var ViewModel = function() {
        var self = this;
        
        self.dataconfigapps = ko.observableArray();
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
        //新增APP
        self.addDataApp = function() {
            self.currentRow(new dataconfigapp({
            	dataconfigappid: 0,
            	configid: "",
            	configtype: 3,
            	app:""
            }));
        };
        //编辑APP
        self.editDataApp = function(row) {
            var data = self.currentRow(row);
            var mappedData = jQuery.map(data, function(item) {           	
            	//赋值
            	$("input[name='"+ item.name +"']").val(item.value);
                return new preference(item);
            });
            self.preferences(mappedData);
        };
        
        //删除
        self.removeDataApp = function(row) {
        	
        	var dataconfigappid = self.currentRow().dataconfigappid;
        	if(null == dataconfigappid || "" == dataconfigappid){
        		self.tip('info', "请选择APP配置");
        		return;
        	}
        	
            if (confirm('删除数据不可恢复，你确认要删除吗')) {
            	var delUrl = CONTEXT_PATH + "/okcsys/dataapp/" + dataconfigappid;
                //删除数据库记录
                $.ajax({
                    url: delUrl,
                    type: 'DELETE',
                    dataType: "json",
   				 	contentType: "application/json; charset=utf-8",
                    success: function(data) {
                    	console.log(data);
                        if (data.success == true) {
                        	self.dataconfigapps.remove(self.currentRow());
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
        //保存APP
        self.saveDataApp = function() {            
            var isValid = $("#dataapp").validate({
				errorElement : "span",
				errorClass : "text-error",
				rules: {
					dataconfigappid: {
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
            	var action = $("#dataapp").attr("action");
				var dataconfigapp = self.currentRow();
				$.post(action,dataconfigapp,function(data){
					if(data.message.success==true) {
						self.tip('success','操作成功');
						if(dataconfigapp.dataconfigappid==0) {
						   self.dataconfigapps.push(dataconfigapp);
						}
					} else {
						self.tip('error',data.message.msg);
					}
				});
            }
            
        };
    };
    var dataappViewModel = new ViewModel();
    ko.applyBindings(dataappViewModel);
    //查询数据源列表
    $.getJSON(CONTEXT_PATH + "/okcsys/dataapp/list", function(data) {
        var mappedData = jQuery.map(data, function(item) {
            return new dataconfigapp(item);
        });
        dataappViewModel.dataconfigapps(mappedData);
    });
       
});