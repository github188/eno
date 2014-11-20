/**
 * 系统组件
 *
 */
//统计面板行数据
var ele_model = "";
//统计面板行
var line_model = "";
$(function() {
	//统计面板行数据
    ele_model = $(".ele_model:eq(0)");
    //统计面板行
    line_model = $(".line_model:eq(0)");
    //控件模型
    function syscontrol(data) {
        var self = this;
        self.controluid = data.controluid;
        self.controlid = ko.observable(data.controlid);
        self.ctrlname = ko.observable(data.ctrlname);
        self.description = ko.observable(data.description);
        self.settting = ko.observable(data.settting);
    }
    
    function FileInfo(value,key) {
		this.value = value;
		this.key = key;
	}

    function preference(data) {
        var self = this;
        self.name = data.name;
        self.value = data.value;
    }
    
	ko.bindingHandlers.select2 = {
		    init: function(element, valueAccessor) {
		      var options = ko.toJS(valueAccessor()) || {};
		      setTimeout(function() { 
		          $(element).select2(options);
		      }, 0);
		      ko.utils.domNodeDisposal.addDisposeCallback(element, function() {
		            $(element).select2('destroy');
		        });
		    },
		    update: function(element, valueAccessor, allBindingsAccessor) {
		       /* var allBindings = allBindingsAccessor(),
		            value = ko.utils.unwrapObservable(allBindings.value || allBindings.selectedOptions);
		        if (value) {
		            $(element).select2('val', value);
		        }*/
		    	$(element).trigger('change');
		    }
	};
	
    //操作模型
    var ViewModel = function() {
        var self = this;
        self.fontSizes = ko.observableArray(fontSize);
        self.fontfamilies = ko.observableArray(fontfamily);
        
        self.controls = ko.observableArray();
        self.currentRow = ko.observable({});
        self.extendList = ko.observableArray(); // 扩展列表[2014-08-06 zzx]
        self.attrlists = ko.observableArray(); // 扩展列表，存放属性列表[2014-08-06 zzx]
	    self.d_valueType = ko.observableArray(inputType); // 值类型
	    self.d_stateTip = ko.observableArray(stateIcon); // 状态提示
	    
        self.preferences = ko.observable({});
        
         // Operations
	    self.addRow = function() {
	        self.extendList.push(new ExtendRow({
		    	d_selValueType : "开关量",
		    	d_tip : "",
		    	d_selStateTip : "icon",
		    	d_icon : "开关量",
		    	d_icon_value : "",
		    	d_openText : "",
		    	m_maxValue : "",
		    	m_minValue : "",
		    	d_classValue : "",
		    	d_attrValue : ""
		    }));
		    
	        $("#control_display").hide();
			$("#cate").change(function(event) {
						var datatype = $("#cate").val();
						if (datatype == "模拟值") {
							$("#control_display").show();
						}
						if (datatype == "开关量") {
							$("#control_display").hide();
						}
						if (datatype == "字符串") {
							$("#control_display").hide();
						}
					});
	    }
	    self.removeRow = function(row) { self.extendList.remove(row) }
	    
        //提示信息
        self.tip = function(type, msg) {
            $.pnotify({
                title: '操作提示',
                text: msg,
                type: type
            });
        };
        //新增控件
        self.addControl = function() {
        	$("#controlid").attr("disabled",false);
            self.currentRow(new syscontrol({
                controluid: 0,
                controlid: "",
                ctrlname: "",
                description: "",
                ctrltype : 0,
                settting : ''
            }));
            $('.template').code('');
            $("#ctlElement").attr("disabled",true);
        };
        //编辑控件
        self.editControl = function(row) {
        	$("ul#contTab>li:eq(0)").addClass("active").siblings().removeClass("active");
        	$($("ul#contTab>li:eq(0)>a").attr("href")).addClass("active in").siblings().removeClass("active in")
        	$("#controlid").attr("disabled", true);
			self.currentRow(row);
            if (ko.toJS(row).settting == null || ko.toJS(row).settting.length == 0) {
				return false;
			}
            var data = JSON.parse(ko.toJS(row).settting);
            var mappedData = jQuery.map(data, function(item) {
            	if (item.name != "controluid" || item.name != "controlid" || item.name != "ctrlname" || item.name != "description") {
	            	// 针对设置进行赋值
	            	if (item.name == "state1") {
						$("#state1_icon").attr('class', item.value);
					} else if (item.name == "state2") {
						$("#state2_icon").attr('class', item.value);
					} else if (item.name == "state4") {
						$("#state4_icon").attr('class', item.value);
					} else if (item.name == "template") {
						if (item.value.length > 0) {
							$('.template').code(item.value);
						} else {
							$('.template').code('');
						}
					} else if (item.name == "access") {
						$("#ctlElement").attr("disabled", (item.value == 'read'));
					} else if (item.name == "attribute") { // 处理动态生成的设备信息
						self.extendList.removeAll(); // 清空数组数据
						var d_list = JSON.parse(item.value);
						for (var i = 0; i < d_list.length; i++) {
						
							self.extendList.push(new ExtendRow({
						    	d_selValueType : d_list[i].d_selValueType,
						    	d_tip : d_list[i].d_tip,
						    	d_selStateTip : d_list[i].d_selStateTip,
						    	d_icon : d_list[i].d_icon,
						    	d_icon_value : d_list[i].d_icon_value,
						    	d_openText : d_list[i].d_openText,
						    	m_maxValue : d_list[i].m_maxValue,
						    	m_minValue : d_list[i].m_minValue,
						    	d_classValue : d_list[i].d_classValue,
						    	d_attrValue : d_list[i].d_attrValue
						    }));
						    // 设置图标显示
//							$("#device_attr").find(".d_state" + (i + 1) + "_i").eq(i + 1).attr('class', d_list[i].d_icon);
							
						}
					} else {
						if ($("#" + item.name).length) {
							$("#" + item.name).val(item.value);
						}
					}
            	}
            	//面板内容清空
            	if (item.name != "panelValue") {
					$("#panel_set").data("panel","");
            	}
            	//面板内容赋值
            	if (item.name == "panelValue") {
					var panel_code = item.value;
					$("#panel_set").data("panel",panel_code);
            	}
            	//模板内容填充
            	if (item.name == "editorValue") {
					var editor = UE.getEditor('setting2');
					editor.setContent(item.value);
				}
            	
                return new preference(item);
            });
            //console.log(mappedData);
            self.preferences(mappedData);

        };
        self.removeControl = function(row) {
        	
            if (confirm('删除数据不可恢复，你确认要删除吗?')) {
            	var delUrl = CONTEXT_PATH + "/okcsys/controls/" + self.currentRow().controluid;
                // 删除数据库记录
                $.ajax({
                    url: delUrl,
                    type: 'DELETE',
                    dataType: "json",
   				 	contentType: "application/json; charset=utf-8",
                    success: function(data) {
                        if (data.success == true) {
                        	self.extendList.removeAll(); // 清空数组数据
                        	self.controls.remove(self.currentRow());
                            self.tip('success', "删除成功");
                            self.addControl();
                        } else {
                        	var msg = (data.msg=="") ? "删除失败" : data.msg;
                            self.tip('error', msg);
                        }
                    },
                    error: function(err) {
                    	console.log(err);
                        self.tip('error', "删除失败," + err);
                    }
                });
            }
        };
        
        //保存控件
        self.saveControl = function() {
        	$("#attribute").val(JSON.stringify(ko.toJS(self.extendList), null, 2));
            var isValid = $("#syscontrol").validate({
				errorElement : "span",
				errorClass : "text-error",
				rules: {
					controluid: {
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
            	$(".template").val($('.template').code());
            	var contrl_arr = $("#syscontrol").serializeArray();
            	//获取并附加设置内容
            	var p_code = "";
            	var p_line = $("#panelContent .line_model");
            	if(p_line.length>0){
            		var p_title = "标题";
            		if($("#exit_title").val()==0){
            			p_title="";
            		}
            		var p = $("<div style='width:1433px;height:213px;'>");
            		$("<div id='panel_title'>").append($("<span>").html(p_title)).appendTo(p);
            		var p_line_code = new Array();
            		var table = $("<table>");
            		$.each($(p_line),function(i,n){//i为行
            			var tr = $("<tr></tr>");
            			var p_ele = $(n).find(".ele_model");
            			$.each($(p_ele),function(j,m){//j为列
            				var nam = $(m).find(".name:eq(0)").val();
            				$("<td>").html(nam).appendTo(tr);
            				var val = $(m).find(".value:eq(0)").val();
            				var uni = $(m).find(".unit:eq(0)").val();
            				var col = $(m).find(".color:eq(0)").val();
            				if(val==4){
            					if(col==null||col==""){
            						col="#000";
            					}
            					$("<td>").html("<span class='mb_text' id='line_"+i+"_"+j+"' script_com='' style='color:"+col+"'>6</span>"+uni).appendTo(tr);
            				}else if(val==0){
            					$("<td>").html("<span class='mb_text write_n' id='line_"+i+"_"+j+"'>6</span>"+uni).appendTo(tr);
            				}else if(val==1){
            					$("<td>").html("<span class='mb_text normal_n' id='line_"+i+"_"+j+"' script_com=''>6</span>"+uni).appendTo(tr);
            				}else if(val==2){
            					$("<td>").html("<span class='mb_text break_n' id='line_"+i+"_"+j+"' script_com=''>6</span>"+uni).appendTo(tr);
            				}else if(val==3){
            					$("<td>").html("<span class='mb_text warn_n' id='line_"+i+"_"+j+"' script_com=''>6</span>"+uni).appendTo(tr);
            				}
            			});
            			$(tr).appendTo(table);
            		})
            		$(table).appendTo(p);
            		var tem = $("<div>");p.appendTo(tem);
            		p_code = tem[0].innerHTML;
            	}
            	if(p_code.length>0){
            		var panel = {
            				"name":"panelValue",
            				"value":p_code
            		}
            		contrl_arr.push(panel);
            	}
            	var settings = JSON.stringify(contrl_arr);
            	//console.log(settings);
            	self.currentRow().settting(settings);
            	
            	console.log(ko.toJS(self.currentRow()));
				var syscontrol = self.currentRow();

				//POST Submit Data
				$.post($("#syscontrol").attr("action"),syscontrol,function(data){
					if(data.message.success==true) {
						self.tip('success','组件操作成功');
						// if is new data,update list data and update current object
						if(self.currentRow().controluid==0) {
							self.controls.push(data.objects[0]);
							self.currentRow(data.objects[0]);
						}
					} else {
						self.tip('error',data.message.msg);
					}
				}).fail(function(e) {
					self.tip('error',e);
				});
            }
            
        };
        
    };
    var syscontrolViewModel = new ViewModel();
    ko.applyBindings(syscontrolViewModel);
    
    // 设备属性，动态生成[2014-08-14 zzx]
	function ExtendRow(data) {
		var self = this;
		self.d_selValueType = ko.observable(data.d_selValueType); // 值类型value
		self.d_tip = ko.observable(data.d_tip); // 提示信息
		self.d_selStateTip = ko.observable(data.d_selStateTip); // 状态提示value
		self.d_icon = ko.observable(data.d_icon); // 对应图标
		self.d_icon_value = ko.observable(data.d_icon_value); // 显示对应图标应对应的值
		self.d_openText = ko.observable(data.d_openText); // 打开文字
		self.d_maxValue = ko.observable(data.d_maxValue); // 最大值
		self.d_minValue = ko.observable(data.d_minValue); // 最小值
		self.m_maxValue = ko.observable(data.m_maxValue); // 设备设置最大值
		self.m_minValue = ko.observable(data.m_minValue); // 设备设置最小值
		self.d_classValue = ko.observable(data.d_classValue); // 动态分类，值
		self.d_attrValue = ko.observable(data.d_attrValue); // 动态属性，值
	}

    //获取组件列表
    $.getJSON(CONTEXT_PATH + "/okcsys/controls/list", function(data) {
        var mappedData = jQuery.map(data, function(item) {
            return new syscontrol(item);
        });
        syscontrolViewModel.controls(mappedData);
    });

    syscontrolViewModel.addControl();

	// 设备属性信息列表
	$.getJSON(CONTEXT_PATH + '/asset/class/classspec/listAll', function(data) {
		var mappedData = jQuery.map(data, function(item) {
			return new FileInfo(item[2], item[0] + "-" + item[1] + "(" + item[2] + ")");
		});
		mappedData.unshift(new FileInfo("", "请选择...")); // 在下拉列表中的第一个元素显示空
		// console.log(mappedData);
		syscontrolViewModel.attrlists(mappedData);
	});
	
    // template editor
	$('.template').summernote({
				height : 150,
				codemirror : {
					theme : 'monokai'
				}
			});
    
    $(".switch").show();
    $(".switch_text").hide();
    $(".number").hide();
    $("#datatype").change(function(event) {
        var datatype = $("#datatype").val();
        if (datatype == "开关量") {
            $(".switch").show();
            $(".number").hide();
        } else if (datatype == "模拟值") {
            $(".switch").hide();
            $(".switch_text").hide();
            $(".number").show();
        } else {
            $(".switch").hide();
            $(".switch_text").hide();
            $(".number").hide();
        }
    });
    
    $("#state").change(function(event) {
        var state = $("#state").val();
        if (state == "icon") {
            $(".switch").show();
            $(".switch_text").hide();
        }  else {
            $(".switch").hide();
            $(".switch_text").show();
        }
    });
    
    // 设备相关的动态属性
//    $(".d_switch").show();
//    $(".d_switch_text").hide();
//    $(".d_number").hide();
//    $("#d_datatype").live("change", function(event) {
//    	var index = $(this).attr("index");
//        var datatype = $(".d_datatype").eq(index).val();
//        console.log("datatype--" + datatype + "--index--" + index);
//        if (datatype == "开关量") {
//            $("#device_attr > fieldset:eq(" + index + ") .d_switch").show();
//            $("#device_attr > fieldset:eq(" + index + ") .d_number").hide();
//        } else if (datatype == "模拟值") {
//            $("#device_attr > fieldset:eq(" + index + ") .d_switch").hide();
//            $("#device_attr > fieldset:eq(" + index + ") .d_switch_text").hide();
//            $("#device_attr > fieldset:eq(" + index + ") .d_number").show();
//        } else {
//            $("#device_attr > fieldset:eq(" + index + ") .d_switch").hide();
//            $("#device_attr > fieldset:eq(" + index + ") .d_switch_text").hide();
//            $("#device_attr > fieldset:eq(" + index + ") .d_number").hide();
//        }
//    });
    
//    $(".d_state").live("change", function(event) {
//    	var index = $(this).attr("index");
//		var state = $(".d_state").eq(index).val();
//		if (state == "icon") {
//			$("#device_attr > fieldset:eq(" + index + ") .d_switch").show();
//			$("#device_attr > fieldset:eq(" + index + ") .d_switch_text").hide();
//		} else {
//			$("#device_attr > fieldset:eq(" + index + ") .d_switch").hide();
//			$("#device_attr > fieldset:eq(" + index + ") .d_switch_text").show();
//		}
//	});
	

    var stateindex = 1;
    $("#state1_modal").click(function(){
    	$("#myModal").modal();
    	stateindex = 1;
    });
    $("#state2_modal").click(function(){
    	$("#myModal").modal();
    	stateindex = 2;
    });
    $("#state3_modal").click(function(){
    	$("#myModal").modal();
    	stateindex = 3;
    });
    $("#state4_modal").click(function(){
    	$("#myModal").modal();
    	stateindex = 4;
    });
    
    var d_stateindex = 0; // 判断动态添加的第几个
    var d_title; 
    // 动态添加的图标设置
    $(".controls > a").live("click", function(){
    	d_title = $(this).prev().prev().attr("tip"); // 确定点击的位置
    	var index = $(this).attr("index");
    	if (d_title) {
	    	stateindex = 5; // 5表示动态生成的设备信息
	    	d_stateindex = index;
	    	$("#myModal").modal();
    	}
    });
    
    //更新访问题状态
    $("#access").change(function(){
    	var accessVal = $("#access").val();
    	if(accessVal!="read") {
    		$("#ctlElement").attr("disabled",false);
    	} else {
    		$("#ctlElement").attr("disabled",true);
    	}
    	
    });

    // 动态设备图标，选择图标后，给相应位置赋值
    $(".the-icons li").click(function(){
    	var className = $(this).find('i').attr('class');
    	if (stateindex == 1) {
			$("#state1").val(className);
			$("#state1_icon").attr('class', className);
		} else if (stateindex == 2) {
			$("#state2").val(className);
			$("#state2_icon").attr('class', className);
		} else if (stateindex == 3) {
			$("#state3").val(className);
			$("#state3_icon").attr('class', className);
		} else if (stateindex == 4) {
			$("#state4").val(className);
			$("#state4_icon").attr('class', className);
		} else if (stateindex == 5) { // 5表示动态生成的设备信息
			className = d_title + '_i ' + className;
			$("." + d_title).eq(d_stateindex).val(className).change();
			$("#device_attr").find("." + d_title + "_i").eq(d_stateindex).attr('class', className);
		}
    	$("#myModal").modal('hide');
    	
    });

    
    $('.colorselector').colorpicker();
    
    $("#contTabTabContent").slimScroll({
		height : $(window).height() - 380,
		width : '100%',
		alwaysVisible : true
	});
});

function reset(target) {
	$("#" + target).val('');
	$("#" + target + "_icon").attr('class', '');
}

// 重置动态生成的图标
function resetDynamic(target) {
	var index = $("." + target + "_i").next().attr("index");
	$("#device_attr > fieldset:eq(" + index + ") ." + target).val(''); // 重置文字为空
	$("#device_attr > fieldset:eq(" + index + ") ." + target).next().attr("class", target + "_i"); // 重置图标为空
}
//添加统计面板行数据
function add_ele(obj){
	var ele = $(ele_model).clone();
	$(obj).before(ele);
	$('.colorselector').colorpicker();
}
//展示面板颜色选项框
function show_colorbox(obj){
	var v = $(obj).val();
	if(v==4){
		$(obj).siblings(".input-prepend").show();
	}else{
		$(obj).siblings(".input-prepend").hide();
	}
}
//添加面板行
function add_line(obj){
	var line = $(line_model).clone();
	var sum = $(obj).siblings(".line_model").length;
	$(line).find(".line_n").html(sum+1);
	$(obj).before(line);
	$('.colorselector').colorpicker();
}
//删除行内容
function dele_ele(obj){
	var sum = $(obj).parent().siblings(".ele_model").length;
	if(sum==0){
		$(obj).parent().parent().parent().remove();
		$.each($(".line_model"),function(i,n){
			$(n).find(".line_n").html(i+1);
		});
	}else{
		$(obj).parent().remove();
	}
}
//删除行
function dele_line(obj){
	$(obj).parent().parent().remove();
	$.each($(".line_model"),function(i,n){
		$(n).find(".line_n").html(i+1);
	});
}
//统计面板tab点击事件
function dele_model(obj){
	$(".line_model").remove();
	var panel_code = $(obj).data("panel");
	//根据数据还原面板设置
	if(panel_code!=null&&panel_code.length>0){
		var panel_div = $(panel_code);
		//标题处理
		var exit_title = $(panel_div).find("div#panel_title>span:eq(0)").html().length>0?1:0;
		var option = $("#exit_title option").filter(function(){
			return $(this).val()==exit_title;
		})
		$(option).attr("selected",true);
		//行处理
		var tr_sum = $(panel_div).find("tbody>tr").length;
		for (var i = 0; i < tr_sum; i++) {
			//复制行模板
			var line = $(line_model).clone();
			$(line).find("span.line_n:eq(0)").html(i+1);
			var tds = $(panel_div).find("tbody>tr:eq("+i+") td");
			$(line).find(".ele_model").remove();
			for (var j = 0; j < tds.length; j+=2) {
				var ele = $(ele_model).clone();
				var td_0 = $(tds)[j].innerText;
				var td_1 = $($(tds)[j+1]).find("span:eq(0)");
				var td_1_class = $(td_1).attr("class");
				var td_1_unit = "";
				if($($(tds)[j+1])[0].childNodes.length>1){
					td_1_unit = $($(tds)[j+1])[0].childNodes[1].data;
				}
				var td_1_color = $(td_1).css("color");
				$(ele).find("input.name:eq(0)").val(td_0);
				$(ele).find("input.unit:eq(0)").val(td_1_unit);
				//根据class选择类型（手写、正常。。）
				if(td_1_class.indexOf("write_n")>-1){
					var value_op = $(ele).find("select.value:eq(0) option").filter(function(){
						return $(this).val()==0;
					});
					$(value_op).attr("selected",true);
				}else if(td_1_class.indexOf("normal_n")>-1){
					var value_op = $(ele).find("select.value:eq(0) option").filter(function(){
						return $(this).val()==1;
					});
					$(value_op).attr("selected",true);
				}else if(td_1_class.indexOf("break_n")>-1){
					var value_op = $(ele).find("select.value:eq(0) option").filter(function(){
						return $(this).val()==2;
					});
					$(value_op).attr("selected",true);
				}else if(td_1_class.indexOf("warn_n")>-1){
					var value_op = $(ele).find("select.value:eq(0) option").filter(function(){
						return $(this).val()==3;
					});
					$(value_op).attr("selected",true);
				}else{
					var value_op = $(ele).find("select.value:eq(0) option").filter(function(){
						return $(this).val()==4;
					});
					$(value_op).attr("selected",true);
					$(ele).find("div.input-prepend:eq(0)").css("display","inline");
					$(ele).find("div.input-prepend:eq(0)>.color").val(td_1_color);
				}
				$(line).find("input.addAele:eq(0)").before(ele);
			}
			$("#addAline").before(line);
			$('.colorselector').colorpicker();
		}
	}
}