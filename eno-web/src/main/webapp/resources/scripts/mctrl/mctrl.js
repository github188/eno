var param_ = '', name_ = ''; // 针对客流，区分是楼层还是店铺
var childidList = "";//tagid集合（设备点属性id）
// [ ChengKang 2014-08-01 ]
// imagemapster插件工具的配置，将为热区增加鼠标经过、点击等效果和事件响应
var appModel;
var tagnam = null;
var MapDrow = function() {
	var $statelist, $usamap, default_options = {
		fillOpacity : 0.5, // 透明度
		render_highlight : // 鼠标经过时高亮的效果
		{
			fillColor : '00E5EE',
			stroke : true
		},
		render_select : // 单击选中热区后的效果
		{
			fillColor : 'ff000c',
			stroke : false
		},
		mouseoutDelay : 0,
		fadeInterval : 50,
		isSelectable : true,
		singleSelect : false,
		mapKey : 'state',
		mapValue : 'title',
		listKey : 'name',
		listSelectedAttribute : 'checked',
		// sortList: "",
		// onGetList: addCheckBoxes,
		onClick : mouseClick
		// 鼠标经过时响应的消息函数
	}

	// 位置列表
	// $statelist = $('#statelist');
	$usamap = $('#backgroundImg');
	$usamap.mapster(default_options);

	function mouseClick(e) {
		var Value = $('#' + this.id).mapster("get", "selected") ? 0 : 1;
		jQuery.ajax({
					url : baseUrl + "/tag/change",
					data : "id=" + this.id + "&f=0&p=1&t=1&v=" + Value, // +($('HotMap').mapster('get',
																		// ) ? 1
																		// : 0),
					type : "POST",
					success : function(data) {
						// alert(data.message);
					}
				});
	}
}
var pfe_config_value = "";
//获取input中光标位置  ztl20140806
(function($, undefined) {
    $.fn.getCursorPosition = function() {
        var el = $(this).get(0);
        var pos = 0;
        if ('selectionStart' in el) {
            pos = el.selectionStart;
        } else if ('selection' in document) {
            el.focus();
            var Sel = document.selection.createRange();
            var SelLength = document.selection.createRange().text.length;
            Sel.moveStart('character', -el.value.length);
            pos = Sel.text.length - SelLength;
        }
        return pos;
    }
})(jQuery);
$(function() {
	 $("#datetimepicker").datetimepicker({
		 format: 'yyyy-MM-dd',
	        language: 'zh-CN',
	        pickDate: true,
	        pickTime: true,
	        hourStep: 1,
	        minuteStep: 15,
	        secondStep: 30,
	        inputMask: true
	 });
	$('#datetimepicker').hide();
	$('#sbmt').hide();
	$('#sbmt').click(function(){
		$('#DOORACS').datagrid('load',{
			tagname: tagnam,
			time: $('#timetext').val()
		});
	});
	ko.bindingHandlers.AreaMouseOver = {
		init : function(element, valueAccessor, allBindingsAccessor, viewModel,
				context) {
			var handler = ko.utils.unwrapObservable(valueAccessor()), newValueAccessor = function() {
				return function(data, event) {
					alert("AreaMouseOver...");
					handler.call(ApplicationModel, data, event);
					event.cancelBubble = true;
					if (event.stopPropagation) {
						event.stopPropagation();
					}
				};
			};
			// ko.bindingHandlers.mouseover.init(element, newValueAccessor,
			// allBindingsAccessor, ApplicationModel, context);
		}
	};

	window.structureCssItems = new Array();
	window.planCssItems = new Array();
	window.listCssItems = new Array();
	// 系统控件
	function control(data) {
		var self = this;
		self.controluid = data.controluid;
		self.controlid = data.controlid;
		self.ctrlname = data.ctrlname;
		self.description = data.description;
		self.settting = data.settting;
	}
	//表达式结构
	function expression(data) {
		var self = this;
		self.index = data.expindex;
		self.code = data.expcode;
		self.name = data.expname;
		self.content = data.expcontent;
	}

	function preference(data) {
		var self = this;
		self.name = data.name;
		self.value = data.value;
	}

	// 设备列表列
	function columnlist(text) {
		var self = this;
		self.dcolumn = text;
	}
	
	// 设备列表对应的信息
	function deviceinfo(tagid, value, css) {
		var self = this;
		self.dvalue = value;
		self.dcss = "d_" + tagid + " " + css;
		self.dtagid = tagid;
	}

	// 设备列表列
	function devicevalue(data, value, attr) {
		var self = this;
		self.dlist = ko.observableArray();
		for (var i = 0; i < data.length; i++) {
			if (attr[i] === 'status_sp') { // 设备列表启停控制，特殊处理，value为99999表示显示切换开关按钮
				self.dlist.push(new deviceinfo(data[i], "99999", (i > 0 ? "color_2" : "")));
			} else {
				self.dlist.push(new deviceinfo(data[i], value[i], (i > 0 ? "color_2" : "")));
			}
		}
	}
	
	/**
	 * 
	 * stompClient menuid 菜单id elementvalue 模块id（如：PFE）
	 */
	function ApplicationModel(stompClient, menuid, elementvalue) {
		var self = this;
		self.location = ko.observable(); // 位置
		self.username = ko.observable(); // 用户
		self.taglist = ko.observable(new TagListModel()); // 设备点列表
		self.operate = ko.observable(new OperationModel(stompClient)); // 操作方法
		self.notifications = ko.observableArray(); // 提示信息
		self.controls = ko.observableArray(); // 控件列表
		self.expressions = ko.observableArray();//表达式列表
		self.currentTag = ko.observable();
		self.configvalue = ko.observable(); // 客流用此项，在页面设置中配置
		self.currETDAssetNum = ko.observable(); // 当前ETD设备点参数面板
		self.currETDAssetClassSpecArray = ko.observableArray();// 当前ETD设备点参数面板
		self.allETDAssetClassSpecArray = ko.observableArray();// 当前页所有ETD设备点
		self.allETDAssetClassSpecArrayForTable = ko.observableArray();// 当前页所有ETD设备点及其参数,显示表格用
		
		// 连接Socket
		self.connect = function() {
			stompClient.connect('guest', 'guest', function(frame) {
				console.log('Connected ' + frame);
				// self.username(frame.headers['user-name']);
				if (frame.headers['user-name'] == '') {
					self.username("guest");
				}
				console.log("websocket connection successed!");
				// 订阅页面对应的测点列表+[表达式列表ztl-10-08]
				stompClient.subscribe("/app/positions", function(message) {
							if (elementvalue == 'PFE') { // 客流初始化需要加载一次店铺的数据
			
								var configvalue = "", panelvalue = "";
								try {
									configvalue = pfe_config_value = JSON.parse(message.body).pageLayoutList[0].configvalue;
									var vals = configvalue.split("#");
									param_ = vals[0];
									name_ = vals[1];
									
									// 客流面板
									var allPoint = JSON.parse(message.body).pagetag;
									var setting =  JSON.parse(allPoint[0].pagetage.setting);
									for (var i = 0; i < setting.length; i++) {
										if (setting[i].name == "editorValue") {
											panelvalue = setting[i].value;
										}
									}
									$("#component3").html(panelvalue);
									
									// 请求所有店铺名字
									var shopdata = [];
									for (var i = 0; i < allPoint.length; i++) {
										var data = allPoint[i].pagetage;
										shopdata.push({
											"paramter" : data.tagid,
											"name" : data.tagname
										});
									}
								} catch(e) {
									console.log("获取configvalue失败，请核对[页面设置]中的页面信息的客流编号是否和配置表中一致");
								}
								
								// 构造消息发送内容
								var pfeInfo = {
									"paramter" : param_,
									"name" : name_,
									"childpoint" : JSON.stringify(shopdata),
									"partype" : "switchfloor" // shopdata表示请求具体店铺的客流数据，switchfloor表示楼层切换请求
								};
			
								// 发送更改
								stompClient.send("/app/pfechange", {}, JSON.stringify(pfeInfo));
								
							}
					
							self.taglist().loadPositions(JSON.parse(message.body).pagetag, JSON.parse(message.body).syscontrol);
							var mappedData = jQuery.map(JSON.parse(message.body).syscontrol, function(item) {
										return new control(item);
									});

							self.controls(mappedData);
							//表达式列表赋值
							var expsData = jQuery.map(JSON.parse(message.body).expressions, function(item) {
								return new expression(item);
							});
							self.expressions(expsData);
							
							// 处理设备列表
							try {
								var device = JSON.parse(message.body);
								self.taglist().processDevice(device.column, device.data, device.vdata, device.adata);
							} catch(e) {
								console.log("----catch--device--");
							}
							
							// 目前只针对暖通空调，显示设备列表和面板，使用try，catch防止没有设备列表的页面出现异常
							if (elementvalue === "HVAC" || elementvalue === "WSD") { // 暖通空调和给排水
								// 处理面板
								try {
									self.taglist().processPanel(JSON.parse(message.body).panel);
								} catch(e) {
									console.log("----catch--panel--");
								}
								
								$(".dev_switch").bootstrapSwitch(); // 使设备列表中的switch按钮生效
								$(".panel_switch").bootstrapSwitch(); // 使switch按钮生效
							}
							//统计面板渲染_begin
							var texts = $("#pagetabsContent>.active div.P_98").filter(function(index){
								return $(this).html()!="";
							});
							if(texts.length>0){
								//获取面板表达式的配置信息
								if(typeof($(texts[0]).parent().attr("exp"))!="undefined"&&$(texts[0]).parent().attr("exp")!=""){
									//转化为数组
									
									var exp_array = eval("("+$(texts[0]).parent().attr("exp")+")");
									//获取面板上的占位符
									var input_text = $(texts[0]).find("span.mb_text");
									//表达式赋给相应的占位符
									for (var i = 0; i < input_text.length; i++) {
										var input_id = $(input_text[i]).attr("id");
										for (var j = 0; j < exp_array.length; j++) {
											if(exp_array[j].id=="panel_title"){
												$("#panel_title>span").html(exp_array[j].exp);
											}
											if(exp_array[j].id==input_id){
												//手写数据赋给占位符
												if($("#"+input_id).hasClass("write_n")){
													$("#"+input_id).html(exp_array[j].exp);
												}else{
													//表达式赋给占位符
													$(input_text[i]).attr("script_com",exp_array[j].exp);
													$(input_text[i]).html(8);
												}
											}
										}
									}
								}
							}
							//统计面板渲染_end
							//统计面板元素点击设置_begin
							if(editable){
								$("#panel_title > span").live("click", function() {
									panelTitleEvent(this);
								});
								$(".P_98 td span.mb_text").live("click", function() {
									if ($(this).hasClass("write_n")) {
										panelTitleEvent(this);
									} else {
										panelTextEvent(this);
									}
								});
							}else{
								//点加载完成，进行表达式注册
								getnumbers();
							}
							//统计面板元素点击设置_end
							/*for (var i = 0; i < JSON.parse(message.body).pagetag.length; i++) {
								var pagetager = JSON.parse(message.body).pagetag[i];
								
							}*/
						}, {
							"menuid" : menuid,
							"elementvalue" : elementvalue,
							"layoutid" : layoutid
						});

				//获取某个页面上所有EDT设备的参数面板数据
				if (elementvalue === "ETD") { // 变配电
					stompClient.subscribe("/app/getETDClassSpecList", function(message) {
						var retMap = JSON.parse(message.body);
						var etdClassSpecList = retMap.assetAndClassSpecMapList;
						self.allETDAssetClassSpecArray(etdClassSpecList);
						if (etdClassSpecList.length > 0) {
							self.currETDAssetNum(etdClassSpecList[0].ASSETNUM);
							self.currETDAssetClassSpecArray(etdClassSpecList[0].etdClassSpecList);
						}
						self.allETDAssetClassSpecArrayForTable(retMap.assetAndClassSpecMapListForTable);
					}, {
						"layoutid" : layoutid
					});
				}			
				
				// 订阅页面对应的测点列表
				stompClient.subscribe("/app/lastValue", function(message) {
							$.each(JSON.parse(message.body), function(index, item) {
										self.taglist().updateLastValue(item);
									});
						}, {
							"menuid" : menuid,
							"elementvalue" : elementvalue,
							"layoutid" : layoutid
						});

				// 订阅客流变化值,[2014-07-30 zzx]
				stompClient.subscribe("/topic/pfe.tag.*", function(message) {
							self.taglist().processPfeValue(JSON.parse(message.body));
						});

				// 订阅变化值
				stompClient.subscribe("/topic/value.tag.*", function(message) {
					var tagid = JSON.parse(message.body).id;
					console.log(tagid);
					self.taglist().processValue(JSON.parse(message.body));
				});
				
				// 更新值
				stompClient.subscribe("/queue/position-updates", function(message) {
							self.pushNotification("Position update " + message.body);
							self.taglist().updatePosition(JSON.parse(message.body));
						});
				// 订阅楼层信息（命令由浏览器初始化时发起），获取表达式的值变化
				stompClient.subscribe("/topic/buildinfo.*", function(message) {
							var body = JSON.parse(message.body);
							var div = $("#pagetabsContent>.active div.P_98").filter(function(index){
								return $(this).html()!="";
							});
							//把接收到的数据付给相应的占位符
							if($(div).length>0){
								var input_t = $(div[0]).find("span").filter(".mb_text");
								for (var i = 0; i < input_t.length; i++) {
									if($(input_t)[i].id==body.p1){
										if(body.value==0){//当值为0时，改换数字颜色
											$($(input_t).get(i)).siblings("span").remove();
											$("<span>0</span>").insertAfter($(input_t).get(i));
											$($(input_t).get(i)).html("");
										}else{
											$($(input_t).get(i)).siblings("span").remove();
											$($(input_t).get(i)).html(body.value);
										}
										break;
									}
								}
							}
						});
						
				// 订阅错误消息
				stompClient.subscribe("/queue/errors", function(message) {
							self.pushNotification("Error " + message.body);
						});

			}, function(error) {
				console.log("STOMP protocol error " + error);
				$.getJSON(CONTEXT_PATH + "/app/positions", {
							"menuid" : menuid,
							"elementvalue" : elementvalue,
							"layoutid" : layoutid
						}, function(message) {
							self.taglist().loadPositions(message.pagetag, message.syscontrol);
							var mappedData = jQuery.map(message.syscontrol, function(item) {
									return new control(item);
								});

							self.controls(mappedData);//
						});
			});
		};

		// 记录错误信息
		self.pushNotification = function(text) {
			self.notifications.push({
						notification : text
					});
			if (self.notifications().length > 5) {
				self.notifications.shift();
			}
		};
		self.logout = function() {
			stompClient.disconnect();
		};
	}
	
	var planLookup = {};
	// 这里定义了一个TagInfo的模型对象
	function TagListModel() {
		var self = this;
		// 所有行数据
		self.rows = ko.observableArray();

		self.listItems = ko.observableArray(); // 设备列表
		self.structureItmes = ko.observableArray(); // 结构图
		self.planItems = ko.observableArray(); // 平面图
		
		self.devicecolumn = ko.observableArray(); // 设备列表第一行
		self.devicelist = ko.observableArray(); // 设备列表数据列表
		
		// 查找指定的行
		var rowLookup = {};

//		var planLookup = {};
		var listLookup = {};
		var structureLookup = {};

		var pfeRankList = {}; // 客流排名列表

		self.loadPositions = function(positions, controls) {
			// 载入所有
			var RowsArray = new Array(); // 临时存储Row [2014-7-15 ChengKang]
			var AreaStr = ""; 
			for (var i = 0; i < positions.length; i++) {
				var taginfo = positions[i], childlist;
//				if (elementvalue === "HVAC" || elementvalue === "WSD" || elementvalue === "FAS") { // 暖通空调、给排水、消防系统
					taginfo = positions[i].pagetage;
					childlist = positions[i].pagetags;
//				}
				
				// 如果记录存在控件ID，则附加控件属性
				var control = controls.filter(function(item) {
							return item.controlid == taginfo.controlid;
						});
				var control2 = controls.filter(function(item) {
							return item.controlid == taginfo.controlid2;
						});
				var control3 = controls.filter(function(item) {
							return item.controlid == taginfo.controlid3;
						});
				var row = null;
				// 添加到设备列表
				if (taginfo.showrange != null
						&& taginfo.showrange.toLowerCase().indexOf('list') !== -1) {
					row = new TagRow(taginfo, control);
					self.listItems.push(row);
					listLookup[row.tagid] = row;
					if (row.parentid != '') {
						window.listCssItems.push(row.parentid);
						$("." + row.parentid).hide();
					}
				}

				// 添加到结构图
				if (taginfo.showrange != null
						&& taginfo.showrange.toLowerCase().indexOf('structure') !== -1) {
					row = new TagRow(taginfo, control2);
					self.structureItmes.push(row);
					structureLookup[row.tagid] = row;

					if (row.parentid != '') {
						window.structureCssItems.push(row.parentid);
						$("." + row.parentid).hide();
					}
				}
				// 添加到平面图
				if (taginfo.showrange != null && taginfo.showrange.toLowerCase().indexOf('plan') !== -1) {
					row = new TagRow(taginfo, control3, childlist);
					RowsArray.push(row); // 临时存到创建的Array [2014-7-15 ChengKang]
					if (row.pagetagtype == 5) { // 客流需要特殊处理,[2014-08-02 ZouZhiXiang]
						planLookup[row.tagid + "_" + row.tagname] = row;
					} else {
						planLookup[row.tagid] = row;
					}

					if (row.parentid != '') {
						window.planCssItems.push(row.parentid);
						$("." + row.parentid).hide();
					} 
					
				}
				
				if(RowsArray.length == 400){
					var AreaStr = ""; 
					for (var j = 0, n = 0; j < RowsArray.length; j++) {
						// 判断是不是热区，coord只有在热区的情况下，才会有值，其他类型被赋值为空字符串
						if (RowsArray[j].coord != "") {
							AreaStr += "<area href=\"#\" shape=\"" + RowsArray[j].shape
									+ "\" coords=\"" + RowsArray[j].coord
									+ "\" state=\"" + RowsArray[j].pagetagid
									+ "\" id=\"" + RowsArray[j].tagid + "\" title=\""
									+ RowsArray[j].tagname + "\" />";
						}
						//当RowsArray里有400个元素时，每20个元素一次push进planItem
						if(j>0 && j%20 == 0)
						{
							self.planItems.push(RowsArray[n], RowsArray[n+1], RowsArray[n+2], RowsArray[n+3], RowsArray[n+4], RowsArray[n+5], RowsArray[n+6], RowsArray[n+7], RowsArray[n+8], RowsArray[n+9],RowsArray[n+10], RowsArray[n+11], RowsArray[n+12], RowsArray[n+13], RowsArray[n+14], RowsArray[n+15], RowsArray[n+16], RowsArray[n+17], RowsArray[n+18], RowsArray[n+19]);
							
							n += 20;
						}
					}
					self.planItems.push(RowsArray[n], RowsArray[n+1], RowsArray[n+2], RowsArray[n+3], RowsArray[n+4], RowsArray[n+5], RowsArray[n+6], RowsArray[n+7], RowsArray[n+8], RowsArray[n+9],RowsArray[n+10], RowsArray[n+11], RowsArray[n+12], RowsArray[n+13], RowsArray[n+14], RowsArray[n+15], RowsArray[n+16], RowsArray[n+17], RowsArray[n+18], RowsArray[n+19]);
					RowsArray = []; // 清空本次500数组
				}
				
				
			}// End for
			
			var AreaStr = ""; // 下面的循环将检测所有的PageTag，并将热区的信息全部编写成<area>的HTML代码，并保存在变量中 [ ChengKang 2014-08-01 ]
			for (var j = 0; j < RowsArray.length; j++) {
				// 判断是不是热区，coord只有在热区的情况下，才会有值，其他类型被赋值为空字符串 [ ChengKang 2014-08-01 ]
				if (RowsArray[j].coord != "") {
					AreaStr += "<area href=\"#\" shape=\"" + RowsArray[j].shape
							+ "\" coords=\"" + RowsArray[j].coord
							+ "\" state=\"" + RowsArray[j].pagetagid
							+ "\" id=\"" + RowsArray[j].tagid + "\" title=\""
							+ RowsArray[j].tagname + "\" />";
				}
				// 将PageTag存放到Tag列表 [ ChengKang 2014-08-01 ]
				self.planItems.push(RowsArray[j]);
			}
			
			if(AreaStr != "") { // 控制热区只在平面图上显示 
				// [ ChengKang 2014-08-01 ]
				// AreaStr保存的是所有热区的<area>标签组成的HTML格式代码，将代码插入到JSP网页中id为AREA_START的标记的元素之后
				// AREA_START标记的元素位于背景图MAP热区的结构中，以此实现了动态加载PageTag的目的
				$(AreaStr).insertAfter("#AREA_START");
				// 调用MapDrow()方法，其中是imagemapster插件工具的配置，将为热区增加鼠标经过、点击等效果和事件响应 [ ChengKang 2014-08-01 ]
				MapDrow();
			}
		};
		/**
		 * 处理设备列表
		 * @clist 设备列表第一行数据
		 * @dlist 设备列表对应的tagid数据
		 * @author zouzhixiang
		 */ 
		self.processDevice = function(clist, dlist, vlist, alist) {
			if (typeof(clist) != undefined && clist != null) {
				for (var i = 0; i < clist.length; i++) {
					self.devicecolumn.push(new columnlist(clist[i]));
				}
			}
			if (typeof(dlist) != undefined && dlist != null) {
				for (var i = 0; i < dlist.length; i++) {
					self.devicelist.push(new devicevalue(dlist[i], vlist[i], alist[i]));
				}
			}
		};
		self.processPanel = function(panel) {
			if (typeof(panel) != undefined && panel != null && panel.length > 0) {
				$("#component2").html(panel[0]); // 为结构图上的面板赋值
				$("#component3").html(panel[0]); // 为平面图上的面板赋值
			}
		};
		// 处理值
		self.processValue = function(tagval) {
			var t_id = tagval.id, t_v = tagval.v;
			if (listLookup.hasOwnProperty(t_id)) {
				$(".d_" + t_id).text(t_v); // 动态设置设备列表的值
				listLookup[t_id].updateValue(t_v);
			}
			if (structureLookup.hasOwnProperty(t_id)) {
				structureLookup[t_id].updateValue(t_v);
			}

			if (planLookup.hasOwnProperty(t_id)) {
				planLookup[t_id].updateValue(t_v);
			}

			// 结构图面板属性值更新
			$("#component2 input").each(function(i, obj) {
				if (tagval.id == obj.id) {
					$("#" + tagval.id).val(tagval.v);
				}
			});

			// 平面图面板属性值更新
			$("#component3 input").each(function(i, obj) {
				if (tagval.id == obj.id) {
					$("#" + tagval.id).val(tagval.v);
				}
			});

		};

		// 处理客流数据
		self.processPfeValue = function(tagval) {
			var hourCataList = [], hourDataList = [], shopOrder = [];
			if((tagval.paramter === param_ && tagval.name === name_) || planLookup.hasOwnProperty(tagval.paramter + "_" + tagval.name)) {
				var shopdata = tagval.housedata; // 店铺数据
				if(shopdata != undefined) { // 以下是处理楼层的
					$("#pfe_content table > tbody").html(''); // 清空右侧排名
					for (var i = 0; i < shopdata.length; i++) {
						if (planLookup.hasOwnProperty(shopdata[i].paramter + "_" + shopdata[i].name)) {
							createRankList(shopdata[i].paramter, shopdata[i].sum_day, shopdata[i].change_day);
							planLookup[shopdata[i].paramter + "_" + shopdata[i].name].updateValue(shopdata[i]);
						}
					}
				}

				var hourdata = tagval.hourdata; // 柱状图数据
				for (var i = 0; i < hourdata.length; i++) {
					hourCataList.push(hourdata[i].hour); // 时间
					hourDataList.push(hourdata[i].hoursum); // 每小时的客流量
				}
				var buttomValueList = {
					"pfe_total" : tagval.sum_day,
					"compare_day" : tagval.change_day,
					"pfe_avg_month" : parseInt(tagval.avg_month),
					"pfe_avg_year" : tagval.avg_year
				};
				buildChart(hourCataList, hourDataList, buttomValueList);
			}
		};

		// 更新数据
		self.updatePosition = function(position) {
			rowLookup[position.tagid].value(position.value);
		};

		// 页面初始化时，获取测点最后测量值
		self.updateLastValue = function(position) {
			// 临时测试用start[2014-09-25, zzx]
//			if(position.tagval == null) {
//				position.tagval = "0";
//			}
			// 临时测试用end[2014-09-25, zzx]
			if (position != null && position.tagval != null) {
				var tagid = position.tagid;
				var tagval = (position.pagetagtype == 5 ? position : position.tagval);
				if (listLookup.hasOwnProperty(tagid)) {
					listLookup[tagid].updateValue(tagval);
				}
				if (structureLookup.hasOwnProperty(tagid)) {
					structureLookup[tagid].updateValue(tagval);
				}

				if (planLookup.hasOwnProperty(tagid)) {
					planLookup[tagid].updateValue(tagval);
				}
			}
		};
	}

	// TagInfo明细项
	function TagRow(data, control, pagetags) {
		var self = this;
		self.layoutid = data.layoutid;
		self.pagetagid = data.pagetagid;
		self.tagid = data.tagid; // ID
		self.tagname = data.tagname; // 名称
		self.label = data.label; // 显示名称
		self.comments = data.comments; // 备注
		self.measureunitid = ko.observable(data.measureunitid); // 计量单位位
		self.top = data.top; // TOP
		self.left = data.left; // LEFT
		self.location = data.location; // 位置
		self.value = ko.observable('0'); // 值
		self.lablename = ko.observable('0'); // 值  
		self.time = ko.observable(0);
		self.group = data.groupname;
		self.controlid = data.controlid;
		self.controlid2 = data.controlid2;
		self.controlid3 = data.controlid3;
		self.arrow = ko.observable();
		self.zindex = ko.observable(data.zindex); // 空间位置，相当于CSS中的z-index
		self.classvalue = (data.parentid == null ? "" : data.parentid) + " meter ui-draggable";
		self.parentid = (data.parentid == null ? "" : data.parentid);
		self.classstructureid = (data.classstructureid == null ? "" : data.classstructureid); // 分类id，为了对应数据字典[2014-10-16,zzx]

		// 以下是客流需要的数据, zzx2014-07-30添加, passenger_start
		self.sum_day = ko.observable(1);
		var random = Math.floor(Math.random() * 7);
		self.pfe_sort = ko.observable(random);
		self.pfe_rank_icon = ko.observable('pfe_rank_icon rank_bg_number' + random);
		self.pfe_rank_arrow = ko.observable('pfeArrow pfe_rank_arrow' + random);
		// passenger_end
		
		self.expressions = data.expressions; // 表达式
		self.p = data.tagtype; // 状态值 1：boolean 离散型 2:double 长整数型
									// 初始值，最小值，最大值 3:LONG 4:string
		self.setting = ko.observable(data.setting);
		self.settings = ko.computed(function() {
					if (data.setting != null && data.setting != "null"
							&& data.setting != "") {
						var JsonPlan = JSON.parse(data.setting);
						if (JsonPlan != null) {
							if (JsonPlan.plan != undefined
									&& JsonPlan.plan != 'null') {
								var str = JsonPlan.plan.shape != null ? JSON
										.stringify(JsonPlan.plan.shape) : '""';
								self.shape = str.substring(1, str.length - 1);

								str = JsonPlan.plan.coords != null ? JSON
										.stringify(JsonPlan.plan.coords) : '[]';
								self.coord = str.substring(1, str.length - 1);
							}
						}
					} else {
						self.shape = '';
						self.coord = '';
					}
				});

		// 坐标位置，设备在每个视图都有自己的位置坐标
		self.structure_top = ko.observable('0');
		self.structure_left = ko.observable('0');
		self.list_top = ko.observable('0');
		self.list_left = ko.observable('0');
		self.plan_top = ko.observable('0');
		self.plan_left = ko.observable('0');

		// 解析并设置位置坐标
		self.coords = ko.computed(function() {
			var coord = JSON.parse(data.coords);
			if (coord != null) {
				self.structure_top = (coord.structure != null && coord.structure.top != null) ? coord.structure.top : '0';
				self.structure_left = (coord.structure != null && coord.structure.left != null) ? coord.structure.left : '0';

				self.list_top = (coord.list != null && coord.list.top != null) ? coord.list.top : '0';
				self.list_left = (coord.list != null && coord.list.left != null) ? coord.list.left : '0';

				self.plan_top = (coord.plan != null && coord.plan.top != null) ? coord.plan.top : '0';
				self.plan_left = (coord.plan != null && coord.plan.left != null) ? coord.plan.left : '0';
			}
		});

		self.tagtype = data.tagtype;
		self.pagetagtype = data.pagetagtype; // 0 TAG 1 MAP-AREA 2 VIDEO
												// 3ASSET 4ALARM 5PASSENGER
												// 99CONTROL
		self.visible = ko.observable(false);// 显示与隐藏
		self.showrange = data.showrange; // list,structure,plan
		self.state = ko.observable(''); // 状态提示类型(文字/图标)
		self.state1 = ko.observable(''); // 打开icon
		self.state2 = ko.observable(''); // 关闭icon
		self.state3 = ko.observable(''); // 故障icon
		self.state4 = ko.observable(''); // 报警icon
		self.state1_text = ko.observable(''); // 打开描述
		self.state2_text = ko.observable(''); // 关闭描述
		self.number_max = ko.observable(100); // 最大值
		self.number_min = ko.observable(0); // 最小值
		
		self.m_maxValue = ko.observable(100); // 最大值
		self.m_minValue = ko.observable(0); // 最小值
		
		self.fontsize = ko.observable('12px'); // 默认字号
		self.showlabel = ko.observable(false); // 是否显示设备标签
		self.labelFontSize = ko.observable('12px'); // 标签字体大小
		self.labelClassName = ko.observable(''); // 标签文字样式名称
		self.labelColor = ko.observable(''); // 标签字体颜色
		self.showValue = ko.observable(false); // 是否显示设备测量值
		self.valueFontSize = ko.observable('12px'); // 值字号
		self.valueClassName = ko.observable(''); // 值样式名称
		self.valueColor = ko.observable(''); // 值颜色
		self.showMeasureunit = ko.observable(false); // 是否显示计量单位
		self.unitClassName = ko.observable(''); // 计量单位文字样式
		self.unitFontSize = ko.observable('12px'); // 计量单位字号
		self.unitColor = ko.observable(''); // 计量单位颜色
		self.ifacename = ko.observable(''); // 接口名称
		self.ifaceexitclass = ko.observable(''); // 接口处理类
		self.ifacedesc = ko.observable(''); // 接口描述
		self.showicon = ko.observable(true); // 是否显示图标(文字/图标)
		self.showtext = ko.observable(false); // 是否显示文字(文字/图标)
		self.access = ko.observable('read'); // 控件访问类型,读或读写
		self.ctlElement = ko.observable(''); // 控件控件元素类型，默认在本元素上，textbox输入框，switch开关，modal调出框

		/**
		 *  以下是有关资产的属性设置
		 */
		self.attribute = ko.observable(false); // 是否按照资产来处理，判断依据是是否在组件中设置了资产相关的属性
		self.dynamic = ko.observable(''); // 资产属性
		self.deviceslist = ko.observableArray(); // 资产下所属的属性信息
		self.baseicon = ko.observable(''); // 基础图标
		self.icon1 = ko.observable(''); // 基础图标1
		self.icon1_text = ko.observable(''); // 基础图标1文字
		self.icon1_value = ko.observable('0'); // 基础图标1文字value
		self.show_value1 = ko.observable(''); // 显示值
		self.icon2 = ko.observable(''); // 基础图标2
		self.icon2_text = ko.observable(''); // 基础图标2文字
		self.icon2_value = ko.observable('0'); // 基础图标2文字value
		self.show_value2 = ko.observable(''); // 显示值
		self.icon3 = ko.observable(''); // 基础图标3
		self.icon3_text = ko.observable(''); // 基础图标3文字
		self.icon3_value = ko.observable('0'); // 基础图标3文字value
		self.show_value3 = ko.observable(''); // 显示值
		
		self.childtagid = ko.observable(''); // 资产下挂的tagid属性，方便手推数据时确定对应的tagid
		if (pagetags != undefined) { // 添加设备对应的属性信息
			var child = [];
			for (var k = 0; k < pagetags.length; k++) {
				row = new TagRow(pagetags[k], control);
				self.deviceslist.push(row);
				planLookup[row.tagid] = row;
				child.push(pagetags[k].label + "：" + pagetags[k].tagid);
			}
			self.childtagid(child.join(","));
		}
		
		// 设置链接地址，
		self.deviceUrl = ko.computed(function() {
					self.visible(true);
					if (self.pagetagtype == 4) {
						self.visible(false);
					}
					if (self.pagetagtype == 2) {
						return "location.href='netvideo://" + self.tagid + "'";
					}
				});

		// 模板内容
		self.template = ko.computed(function(){
			// 楼层信息统计面板，面板展示
			if(control.length>0){
				if(self.pagetagtype==98){
					var settting_p = control[0].settting;
					settting_p = eval("("+settting_p+")");
					for (var i = 0; i < settting_p.length; i++) {
						if(settting_p[i].name==="panelValue"){
							return settting_p[i].value;
						}
					}
				}
			}
			return "";
		});
		// 控件属性
		self.preferences = ko.computed(function() {
			if (control == null || control[0] == null) {
				return null;
			}

			// 获取组件设置
			var data = JSON.parse(control[0].settting);
			$.each(data, function(index, item) {
				if (item.name == "state") { // 状态提示类型(文字/图标)
					self.state(item.value);
					self.showicon = (item.value == 'icon'); // 是否显示图标(文字/图标)
					self.showtext = (item.value == 'text'); // 是否显示文字(文字/图标)
				} else if (item.name == "state3") { // 故障时
					self.state3(item.value);
				} else if (item.name == "state1") { // 打开时
					self.state1(item.value);
					self.arrow(item.value);
					self.baseicon(item.value);
				} else if (item.name == "state2") { // 关闭时
					self.state2(item.value);
				} else if (item.name == "state1_text") {
					self.state1_text(item.value);
				} else if (item.name == "state2_text") {
					self.state2_text(item.value);
				} else if (item.name == "number_max") {
					self.number_max(item.value);
				} else if (item.name == "number_min") {
					self.number_min(item.value);
				} else if (item.name == "fontsize") {
					self.fontsize(item.value);
				} else if (item.name == "showlabel") {
					self.showlabel((item.value == "" || item.value == 'false') ? false : true);
				} else if (item.name == "labelFontSize") {
					self.labelFontSize(item.value);
				} else if (item.name == "labelClassName") {
					self.labelClassName(item.value);
				} else if (item.name == "labelColor") {
					self.labelColor(item.value);
				} else if (item.name == "showValue") {
					self.showValue((item.value == "" || item.value == 'false') ? false : true);
				} else if (item.name == "valueFontSize") {
					self.valueFontSize(item.value);
				} else if (item.name == "valueClassName") {
					self.valueClassName(item.value);
				} else if (item.name == "valueColor") {
					self.valueColor(item.value);
				} else if (item.name == "showMeasureunit") {
					self.showMeasureunit((item.value == "" || item.value == 'false') ? false : true);
				} else if (item.name == "unitClassName") {
					self.unitClassName(item.value);
				} else if (item.name == "unitFontSize") {
					self.unitFontSize(item.value);
				} else if (item.name == "unitColor") {
					self.unitColor(item.value);
				} else if (item.name == "ifacename") {
					self.ifacename(item.value);
				} else if (item.name == "ifaceexitclass") {
					self.ifaceexitclass(item.value);
				} else if (item.name == "ifacedesc") {
					self.ifacedesc(item.value);
				} else if (item.name == "access") {
					self.access(item.value);
				} else if (item.name == "ctlElement") {
					self.ctlElement(item.value);
				} else if (item.name == "attribute") { // 处理动态生成的资产信息
					self.dynamic(item.value);
					var d_list = JSON.parse(item.value);
					for (var i = 0; i < d_list.length; i++) {
						self.m_maxValue(d_list[i].m_maxValue);
						self.m_minValue(d_list[i].m_minValue);
						if (i == 0) {
							self.icon1(d_list[i].d_icon);
							self.icon1_text(d_list[i].d_attrValue);
							self.icon1_value(d_list[i].d_icon_value);
							self.show_value1(d_list[i].d_show_value);
						} else if (i == 1) {
							self.icon2(d_list[i].d_icon);
							self.icon2_text(d_list[i].d_attrValue);
							self.icon2_value(d_list[i].d_icon_value);
							self.show_value2(d_list[i].d_show_value);
						} else if (i == 2) {
							self.icon3(d_list[i].d_icon);
							self.icon3_text(d_list[i].d_attrValue);
							self.icon3_value(d_list[i].d_icon_value);
							self.show_value3(d_list[i].d_show_value);
						}
						self.attribute(true); // 说明设置了设备信息
					}
				}
			});
			
		});

		// 更新值，发生变化时更改图标
		self.updateValue = function(newValue) {
			
			if (self.pagetagtype == 5) { // 更新客流值，发生变化时进行更新，zzx

				// 当值为开关量时发生的变化
				if (newValue.sum_day) {
					if (ko.toJS(self.value) != newValue.sum_day) {
						self.pfe_rank_icon('pfe_rank_icon rank_bg_number' + newValue.sort);
						self.pfe_rank_arrow('pfeArrow pfe_rank_arrow' + newValue.sort);
						self.pfe_sort(newValue.sort); // 店铺排名
						self.sum_day(newValue.sum_day);
					}
					self.value(newValue.sum_day);
				}

			} else {

				// 当值为开关量时发生的变化
				if (ko.toJS(self.p) == 1) {
					if (self.coord != "") { // 表示热区
						if (newValue == 1) {
							$('#' + self.tagid).mapster('select');
						} else {
							$('#' + self.tagid).mapster('deselect');
						}
					}
					
					if (ko.toJS(self.value) != newValue) {
						self.arrow((newValue == 0 || newValue == "" || newValue == null) ? ko.toJS(self.state2) : ko.toJS(self.state1));
						
						if (self.pagetagtype == 4) {
							self.visible((newValue == 1));
							self.zindex((newValue == 1) ? 200 : 100);
						}
					}
					
				} else if (ko.toJS(self.tagtype) == 2) { // 当值为模拟值时发生的变化

					if(parseInt(newValue) > parseInt(ko.toJS(self.m_maxValue)) || parseInt(newValue) < parseInt(ko.toJS(self.m_minValue))) {
						self.arrow((newValue == 0 || newValue == "" || newValue == null) ? ko.toJS(self.state2) : ko.toJS(self.state1));
					}
					
				}
				self.value(newValue);
				
				// console.log("icon1_text---" + ko.toJS(self.tagname) + "--icon1_text--" + ko.toJS(self.icon1_text) + "--icon2_text--" + ko.toJS(self.icon2_text) + "--icon3_text--" + ko.toJS(self.icon3_text));
				if (ko.toJS(self.tagname) == ko.toJS(self.icon1_text)) {
					self.arrow(ko.toJS(self.icon1));
				} else if (ko.toJS(self.tagname) == ko.toJS(self.icon2_text)) {
					self.arrow(ko.toJS(self.icon2));
				} else if (ko.toJS(self.tagname) == ko.toJS(self.icon3_text)) {
					self.arrow(ko.toJS(self.icon3));
				}
				$(".d_" + self.tagid).text(newValue); // 动态设置设备列表的值
				$(".p_" + self.tagid).text(newValue); // 动态设置面板的值
			}
		};
	};

	var mousex, mousey;
	function mouseMove(e) {
		var obj = e || window.event;
		var mousePos = mouseCoords(obj);
		mousex = mousePos.x;
		mousey = mousePos.y;
	}
	function mouseCoords(obj) {
		if (obj.pageX || obj.pageY) {// FF浏览器
			return {
				x : obj.pageX,
				y : obj.pageY
			};
		}
		// IE浏览器
		return {
			x : obj.clientX + document.body.scrollLeft
					- document.body.clientLeft,
			y : obj.clientY + document.body.scrollTop - document.body.clientTop
		}
	}
	document.onmousemove = mouseMove;

	/**
	 * 操作模型,所有相关的事件操作，都在此模型完成
	 */
	function OperationModel(stompClient) {
		var self = this;
		self.action = ko.observable();
		self.valueToChange = ko.observable(0);
		self.currentRow = ko.observable({});
		self.selControlid = ko.observable();
		self.selExpid = ko.observable();
		self.error = ko.observable('');
		self.suppressValidation = ko.observable(false);
		self.layouttype = ko.computed(function() {
					return $(".structure").attr("layouttype");
				});

		// 关闭编辑窗口
		self.hideEditable = function() {
			$("#editable-dialog").hide();
		};

		// 保存编辑内容
		self.saveEditable = function() {
			var currView = $("#currView").val();
			var currCtrlid = ko.toJS(self.selControlid());
			var controlid = "", controlid2 = "", controlid3 = "";
			var expressions = ko.toJS(self.selExpid());
			if (currView == "LIST") {
				controlid = currCtrlid;
			} else if (currView == "STRUCTURE") {
				controlid2 = currCtrlid;
			} else {
				controlid3 = currCtrlid;
			}

			// 构造对象
			// 添加表达式字段，实现添加或修改表达式ztl
			var pagetag = {
				"pagetagid" : appModel.currentTag().pagetagid,
				"expressions" : expressions,
				"controlid" : controlid,
				"controlid2" : controlid2,
				"controlid3" : controlid3
			};

			// 发送数据到后台
			stompClient
					.send("/app/update/pagetag", {}, JSON.stringify(pagetag));

			// 更新状态
			var control = appModel.controls().filter(function(item) {
						return item.controlid == currCtrlid;
					});
			if (typeof(control) != 'undefined' && control != null && control.length>0) {
				var data = JSON.parse(control[0].settting);
				$.each(data, function(index, item) {
							if (item.name == "state2") { // 关闭时
								appModel.currentTag().arrow(item.value);
							}
						});
			}
		};

		// 在这里处理设备的控件事件，如开关，弹出窗口等
		self.showOperation = function(row) {
			if (row.pagetagtype == 98) {
				return;
			}
			tagnam = row.tagname;
			if (!editable&&elementvalue === "SASAC") {
				if($(".popover").length>0){
					$("#popoverK").popover('destroy');
				}else{
					var jieyan = "";
					if(row.childtagid().indexOf("戒严")>-1){
						jieyan = '<li id="martial">戒严</li>';
					}
					$("#popoverK").popover({'placement':'right','html': true, content : '<div class="doorBox"><div class="sideLine"><span class="icon_triangle"></span></div><div class="doorList"><ul><li id="doorContrl">开门控制</li>'+jieyan+'<li id="doorList">查看刷卡记录</li></ul></div></div>'});
					$("#popoverK").popover('show');	
					var tagidCssLef = $("#" + row.tagid).css("left");
					var lefLen = tagidCssLef.length;
					var subLef = tagidCssLef.substring(0, lefLen - 2) - 55+72;
					var tagidCssTop = $("#" + row.tagid).css("top");
					var topLen = tagidCssTop.length;
					var subTop = tagidCssTop.substring(0, topLen - 2) - 150+132;
					$(".popover").css({"left":subLef+"px","top":subTop+"px","border":"0px","background-color":"transparent"});
					var str = row.childtagid();
					var childs = new Array();
					$.each(str.split(","),function(i,n){
						childs.push({
							"name":(n.split("："))[0],
							"id":(n.split("："))[1]
						});
					})
					// 开门控制代码
					$("#doorContrl").click(function() {
						var currValu1 = ko.toJS(row.value());
						if (currValu1 == 1) {
							row.arrow(ko.toJS(row.state2));
							row.value(0);
						}
						if (currValu1 == 0) {
							row.arrow(ko.toJS(row.state1));
							row.value(1);
						}
						var tagid_0 = "";
						$.each(childs,function(i,n){
							if(n.name.indexOf("运行")>-1){
								tagid_0 = n;
							}
						})
						var tagInf = {
								"id" : tagid_0.id,
								"f" : 0,
								"p" : 1, // self.currentRow().p
								"t" : 1,
								"v" : currValu1
						};
						// 发送更改
						stompClient.send("/app/change", {}, JSON.stringify(tagInf));
					});	
					
					//发送门禁戒严命令
					$("#martial").click(function(){
						var tagid_0 = "";
						$.each(childs,function(i,n){
							if(n.name.indexOf("戒严")>-1){
								tagid_0 = n;
							}
						})
						var tagInf = {
								"id" : tagid_0.id,
								"f" : 0,
								"p" : 1, // self.currentRow().p
								"t" : 1,
								"v" : 1//此处填写戒严代码
						};
						// 发送更改
						stompClient.send("/app/change", {}, JSON.stringify(tagInf));
					});
					
					// 刷卡记录
					$("#doorList").click(function() {
						$('#datetimepicker').show();
						$("div.P_98").hide();
						$('#sbmt').show();
						$('#DOORACS').datagrid({ 
							url:CONTEXT_PATH +"/doorinfo/list?tagname="+row.tagname,
							rownumbers : true,
							idField : 'id',
							columns : [[{
								title : 'ID',
								field : 'id',
								width : 50
								
							}, {
								title : '门编号',
								field : 'doorNum',
								width : 100
							}, {
								title : '卡号',
								field : 'cardNum',
								width : 100
							}, {
								title : '用户编号',
								field : 'userId',
								width : 100
							}, {
								title : '用户名',
								field : 'userName',
								width : 100
							}, {
								title : '动作',
								field : 'eventType',
								width : 100
							}, {
								title : '动作时间',
								field : 'eventTime',
								width : 100
							}, {
								title : '反馈信息',
								field : 'eventMsg',
								width : 100
							}]]
						});	
					});		
				}
				return;
			}
			if(elementvalue==="LSPUB"){ // 公共照明
				
				var currValu2 = ko.toJS(row.value());
			      if(currValu2==1){
			    	  row.arrow(ko.toJS(row.state2)); 
			    	  row.value(0);
			      }
			      if(currValu2==0){
			    	  row.arrow(ko.toJS(row.state1));
			    	  row.value(1);
			      }
				
				var tagInf = {
						"id" : row.tagid,
						"f" : 0,
						"p" : 1, // self.currentRow().p
						"t" : 1,
						"v" : currValu2
					};
					// 发送更改
					stompClient.send("/app/change", {}, JSON.stringify(tagInf));
			}
               if(elementvalue==="LSN"){
				
				var currValu2 = ko.toJS(row.value());
			      if(currValu2==1){
			    	  row.arrow(ko.toJS(row.state2)); 
			    	  row.value(0);
			      }
			      if(currValu2==0){
			    	  row.arrow(ko.toJS(row.state1));
			    	  row.value(1);
			      }
				
				var tagInf = {
						"id" : row.tagid,
						"f" : 0,
						"p" : 1, // self.currentRow().p
						"t" : 1,
						"v" : currValu2
					};
					// 发送更改
					stompClient.send("/app/change", {}, JSON.stringify(tagInf));
			}
			
			// 客流部分_start
			if (row.pagetagtype == 5) {
				console.log("发送命令,paramter=" + row.tagid + ",name=" + row.tagname);
//				param_ = row.tagid, name_ = row.tagname;
				// 构造消息发送内容
				var pfeInfo = {
					"paramter" : row.tagid,
					"name" : row.tagname,
					"partype" : "shopdata" // shopdata表示请求具体店铺的客流数据，switchfloor表示楼层切换请求
				};
				// 发送更改
				stompClient.send("/app/pfechange", {}, JSON.stringify(pfeInfo));
				return false;
			}
			// 客流部分_end

			appModel.currentTag(row);
			// 编辑状态下不处理设备事件
			if (editable) {
				var tabId = $('#pagetabsContent > .active').attr("id");
				$("#currView").val(tabId);
				var dialog = document.getElementById("editable-dialog");
				dialog.style.display = 'block';
				dialog.style.top = (mousey - 10) + 'px';
				dialog.style.left = (mousex - 320) + 'px';
				return;
			}

			// 如果存在资产名称，设备类型不为控件，则赋值
			if ($(".assetname").length && row.pagetagtype == 99&&row.pagetagtype == 98) {
				$(".assetname").text(row.label);
			}

			// 设备控件中设置访问权限为可读写，则执行以下操作
			if (ko.toJS(row.access) != 'read') {
				// 控件元素类型，用户更新值的方式。如：在图标上直接操作，当更新完成后自动更新图标状态。
				var ctlElement = ko.toJS(row.ctlElement());
				if (ctlElement == 'dialog') {
					self.showModal('operation', row);
				} else {
					// 在当前元素上操作时,获取当前值
					var currValue = ko.toJS(row.value());
					var sendValue = 1; // 默认为1
					if (currValue != null && typeof(currValue) != 'undefined') {
						if (currValue == 0) {
							sendValue = 1;
						} else {
							sendValue = 0;
						}
					}
					console.log("发送命令,tagid=" + row.tagid + ",value="
							+ sendValue);
					// 构造消息发送内容
					var tagInfo = {
						"id" : row.tagid,
						"f" : 0,
						"p" : 1, // self.currentRow().p
						"t" : 1,
						"v" : sendValue
					};

					// 发送更改
					stompClient
							.send("/app/change", {}, JSON.stringify(tagInfo));
					console.log("socket send info:");

					// 更新界面UI模型
					row.arrow(sendValue <= 0 ? ko.toJS(row.state2) : ko
							.toJS(row.state1));
					row.value(sendValue);

				}
			}

			for (var i = 0; i < window.structureCssItems.length; i++) {
				var structureCss = window.structureCssItems[i];
				if (row.tagname == structureCss) {
					$("." + structureCss).show();
				} else {
					$("." + structureCss).hide();
				}
			}

			for (var i = 0; i < window.planCssItems.length; i++) {
				var planCss = window.planCssItems[i];
				if (row.tagname == planCss) {
					$("." + planCss).show();
				} else {
					$("." + planCss).hide();
				}
			}

			for (var i = 0; i < window.listCssItems.length; i++) {
				var listCss = window.listCssItems[i];
				if (row.tagname == listCss) {
					$("." + listCss).show();
				} else {
					$("." + listCss).hide();
				}
			}

			// 目前只针对暖通空调，获取暖通空调面板
			if (elementvalue === "HVAC" || elementvalue === "WSD") {
				$("div.P_98").hide();//隐藏统计面板
				$("#component3").show();
				var url = baseUrl + "/okcsys/page/getPanelValue?layoutid=" + layoutid + "&pagetagid=" + row.pagetagid;
				$.get(url, {} , function(data) {
					try {
						$("#component2").html(data[0]); // 结构图面板赋值
						$("#component3").html(data[0]); // 平面图面板赋值
						$(".panel_switch").bootstrapSwitch();
					} catch(e) {
						console.log("获取面板时出现问题，请检查pagetag表中数据是否正常！");
					}
				});
				
			} else {// 切换属性面板
				$("div.P_98").hide();//隐藏统计面板
				$("#component3").show();
				jQuery.ajax({
						async : false,
						url : baseUrl + "/mctrl/panelBackground/"
								+ row.pagetagid,
						type : "post",
						success : function(data) {
							if ((row.showrange).indexOf("list") > 0) {
								$("#component1").empty();
								$("#component1").html(data);
							} else if ((row.showrange).indexOf("structure") > 0) {
								$("#component2").empty();
								$("#component2").html(data);
							} else if ((row.showrange).indexOf("plan") > 0) {
								$("#component3").empty();
								$("#component3").html(data);
							}
						}
					});
				
			}

		};
		// 显示操作对话框
		self.showModal = function(action, row) {
			self.action(action);
			self.valueToChange(0);
			self.currentRow(row);
			self.error('');
			self.suppressValidation(false);
			$('#operate-dialog').modal();
		};
		$('#operate-dialog').on('shown', function() {
					var input = $('#operate-dialog input');
					input.focus();
					input.select();
				});
		self.executeOperation = function() {
			var taginfo = {
				"id" : self.currentRow().tagid,
				"p" : "2", // self.currentRow().p
				"v" : self.valueToChange()
			};
			stompClient.send("/app/change", {
						user : 'guest'
					}, JSON.stringify(taginfo));
			$('#operate-dialog').modal('hide');
		};
	}

	var socket = new SockJS(wsUrl);
	var stompClient = Stomp.over(socket);
	appModel = new ApplicationModel(stompClient, menuId, elementvalue);
	ko.applyBindings(appModel);
	appModel.connect();
	appModel
			.pushNotification("Trade results take a 2-3 second simulated delay. Notifications will appear.");

	/*
	 * $(".carousel").carousel({ interval: 200000 });
	 */
	$(window).unload(function() {
				stompClient.disconnect(function() {
							// alert("See you next time!");
							console.log("window unload!");
						});
			});
			
	// 监听面板和设备列表切换开关事件，[2014-09-03 zzx]
	$(".panel_switch").bootstrapSwitch();
	$(".panel_switch").live("switch-change", function(e, data) {
		sendCommand(stompClient, $(this).attr("tagid"), data.value);
    });
	$(".dev_switch").live("switch-change", function(e, data) {
		sendCommand(stompClient, $(this).attr("tagid"), data.value);
    });
});

/**
 * 发送命令
 * 
 * @param {} tagid
 * @param {} value
 */
function sendCommand(stompClient, tagid, value) {
	// 构造消息发送内容
	var tagInfo = {
		"id" : tagid,
		"f" : 0,
		"p" : 1,
		"t" : 1,
		"v" : value == true ? "1" : "0"
	};
	stompClient.send("/app/change", { user : 'guest' }, JSON.stringify(tagInfo)); // 发送更改
}
/**
 * 面板组件按钮事件
 * 
 * @param eventScript
 */
function panelButtionEvent(obj) {
	if(editable){
		$("#addexp-dialog").show();
		$("#textId").val($(obj).attr("id"));
		var top = parseInt($(obj).offset().top)+18;
		var left = parseInt($(obj).offset().left)+48;
		$("#addexp-dialog").css("top",top+"px");
		$("#addexp-dialog").css("left",left+"px");
	}else{
		var exps = eval("("+$(obj).parents(".P_98").attr("exp")+")");
		for (var i = 0; i < exps.length; i++) {
			if(exps[i].id==$(obj).attr("id")){
				script_index = exps[i].exp;
			}
		}
		jQuery.ajax({
			url : baseUrl + "/app/panelButtionEvent",
			data : "script_index=" + script_index,
			type : "post",
			success : function(data) {
				alert("已发送命令");
			}
		});
	}
}

var isdb;
/**
 * 面板组件文本框事件
 * 
 * @param obj
 */
function panelInputEvent(obj) {
	if (editable) {
		return;
	}
	// 判断来源操作状态，非编辑状态执行
	isdb = false;
	window.setTimeout(oneclick, 500);
	function oneclick() {
		if (isdb != false)
			return;
		// alert("单击");
		$('#addInputValue').show();
		$('#addInputValue').dialog({
			collapsible : true,
			minimizable : true,
			maximizable : true,
			toolbar : [],
			buttons : [{
				text : '提交',
				iconCls : 'icon-ok',
				handler : function() {
					obj.value = $("#inputval").val();
					jQuery.ajax({
								url : baseUrl + "/app/panelInputEvent",
								data : "p1=John&inputval="
										+ $("#inputval").val(),
								type : "post",
								success : function(data) {
									// alert(data.message);
								}
							});
					$('#addInputValue').dialog('close');
				}
			}, {
				text : '取消',
				handler : function() {
					$('#addInputValue').dialog('close');
				}
			}]
		});
	}
}

/**
 * 面板组件文本框双击事件
 * 
 * @param obj
 */
function panelInputOndblclickEvent(obj) {
	// 判断来源操作状态
	if (editable) {
		isdb = true;
		$('#addTagidValue').show();
		$('#addTagidValue').dialog({
			collapsible : true,
			minimizable : true,
			maximizable : true,
			toolbar : [],
			buttons : [{
				text : '提交',
				iconCls : 'icon-ok',
				handler : function() {
					obj.id = $("#inputtagid").val();
					obj.value = $("#inputtagid").val();
					// alert($("#component3").html());
					var layoutid = $("#layoutid").val();
					var pagetagid = $("#pagetagid").val();
					var componenttype = $("#componenttype").val();
					jQuery.ajax({
								url : baseUrl + "/mctrl/changePagetegSettting",
								data : "layoutid=" + layoutid + "&pagetagid="
										+ pagetagid + "&setting="
										+ $("#" + componenttype).html(),
								type : "post",
								success : function(data) {
									// alert(data.message);
									// $("#pagetagid").val('');
								}
							});
					$('#addTagidValue').dialog('close');
				}
			}, {
				text : '取消',
				handler : function() {
					$('#addTagidValue').dialog('close');
				}
			}]
		});
	}
}
//切换楼层机组状态图片 **spb
function switchMctrlStatus(picUrl, status){
	var $img = $("#STRUCTURE>div>div>div>div");
	var imgStr = $img.css('background-image');
	var imgSrc = imgStr;
	if(status == 'open') {
		if(imgSrc.indexOf('.gif')>0){
			return;
		}else{
			imgStr = imgStr.substring(0,imgStr.lastIndexOf('.'))+'_open.gif")';
			var imgStrTest = imgStr.replace(/url\("/,"").replace(/"\)/,"");
			var img = new Image();
			img.src = imgStrTest;
//			img.onload = function(){
//				imgSrc = imgStr;
//				alert(imgSrc)
//			}
			img.onerror = function(){
				$img.css('background-image',imgSrc);
			}
		}
	}else{
		if(imgSrc.indexOf('.gif')<0){
			return;
		}else{
			imgStr = imgStr.replace(/url\("/,"").replace(/"\)/,"");
			var imgUrl = imgStr.substring(0,imgSrc.lastIndexOf('.')-5);
			imgStr = imgUrl+".jpg";
			var img = new Image();
			img.src = imgStr;
			img.onerror = function(){
				imgStr = imgUrl+".png";
				$img.css('background-image',"url('"+imgStr+"')");
			}
			imgStr = "url('"+imgStr+"')";
		}
	}
	$img.css('background-image',imgStr);
	
}
//获取统计信息-限于平面图
function getnumbers(){
	//获取面板组件99
	var texts = $("#pagetabsContent>.active div.P_98").filter(function(index){
		return $(this).html()!="";
	});
	if(texts.length>0){
		//查找表达式数据
		if($(texts[0]).parent().attr("exp")!=""){
			var inputs = $(texts[0]).find("span.mb_text");
			var fa_exp = new Array();
			//过滤没有配置表达式的占位符
			for (var i = 0; i < inputs.length; i++) {
				var input_id = $(inputs[i]).attr("id");
				var input_exp = $(inputs[i]).attr("script_com");
				if(input_exp==null||input_exp.length<1)
					continue;
				fa_exp.push({
					id:input_id,
					exp:input_exp
				});
			}
			//发送表达式
			if(fa_exp.length>0){
				fa_exp = JSON.stringify(fa_exp);
				jQuery.ajax({
					url : baseUrl + "/app/getinfo",
					data : "map=" + fa_exp,
					type : "post",
					success : function(data) {
						// alert(data.message);
					}
				});
			}
		}
	}
}
//面板title单击事件
function panelTitleEvent(obj){
	var str = $(obj).html();
	var fontsize = $(obj).css("font-size");
	var input = $("<input style='width: 300px; font-size:"+fontsize+";' value='"+str+"' onblur='changetitle(this)'>");
	$(input).insertAfter(obj);
	$(obj).hide();
	$(input).focus();
}
//编辑平面面板标题
function changetitle(obj){
	var str = $(obj).val();
	if(str==""){
		alert("内容不能为空！！");
	}else{
		$(obj).siblings().html(str).show();
		$(obj).remove();
	}
}
//面板占位符单击事件
function panelTextEvent(obj){
	$("#addexp-dialog").show();
	$("#textId").val($(obj).attr("id"));
	var top = parseInt($(obj).offset().top)-34;
	var left = parseInt($(obj).offset().left)+48;
	$("#addexp-dialog").css("top",top+"px");
	$("#addexp-dialog").css("left",left+"px");
}
//隐藏表达式配置框
function hideAdd(id){
	$("#addexp-dialog").hide();
}
//配置表达式-占位符
function addExp(){
	var expIndex = $("#chooseexp").val();
	$("span#"+$("#textId").val()).attr("script_com",expIndex);
	$("span#"+$("#textId").val()).html(8);
	$("#addexp-dialog").hide();
}
/*
 * jQuery.ajax({ url : CONTEXT_PATH + "/pwarn/alarms/confirmReported", data :
 * "alarms=John&ids=" + $('#tagID_value').val(), success : function(data) {
 * alert(data.message); } });
 */