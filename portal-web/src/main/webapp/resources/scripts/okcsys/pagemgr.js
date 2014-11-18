/**
 * 页面布局JS操作
 */
var hotArea = "";
// 给JS添加String的replaceAll()函数 [ ChengKang 2014-07-22 ]
String.prototype.replaceAll = function(s1, s2)
{ 
    return this.replace(new RegExp(s1, "gm"), s2); 
}

$(function(){
	
	// 0 TAG 1 MAP-AREA 2 VIDEO 3ASSET 4ALARM 5PASSENGER 99CONTROL
	var pagetagtype = {"TAG":0,"AREA":1,"VIDEO":2,"ASSET":3,"PASSENGER":5,"CONTROL":99};
	var showranges = [{"name":"设备列表","value":"list"},{"name":"系统结构图","value":"structure"},{"name":"系统平面图","value":"plan"}]

	function FileInfo(value,key) {
		this.value = value;
		this.key = key;
	}
	
	function Control(data) {
		var self = this;
		self.ctrlname = data.ctrlname;
		self.controlid = data.controlid;
		self.controluid = data.controluid;
	}
	//设备对象
	function Pagetag(data) {
		var self = this;
		self.pagetagid = data.pagetagid;
		self.tagid = ko.observable(data.tagid);
		self.tagname = ko.observable(data.tagname);
		self.tagtype = ko.observable(data.tagtype);
		self.tagval = ko.observable(data.tagval);
		self.label = ko.observable(data.label);
		self.comments = ko.observable(data.comments);
		self.groupname = ko.observable(data.groupname);
		self.parentid = ko.observable(data.parentid);
		self.measureunitid = ko.observable(data.measureunitid);
		self.controlid = ko.observable(data.controlid);
		self.controlid2 = ko.observable(data.controlid2);
		self.controlid3 = ko.observable(data.controlid3);
		self.left = ko.observable(data.left);
		self.top = ko.observable(data.top);
		self.layoutid = data.layoutid;
		self.createby = ko.observable(data.createby);
		self.pagetagtype = ko.observable(data.pagetagtype);
		self.usesetting = ko.observable(data.usesetting);
		//self.showrange = ko.observable(data.showrange);
		self.zindex = ko.observable(data.zindex);
		
		self.showrange = data.showrange!=null?data.showrange.split(","):[];
		self.coords = ko.observable(data.coords);
		self.setting = ko.observable(data.setting);
		self.coordinate =  ko.dependentObservable(function(){
			
			return (self.coords()!=null && self.coords().length)>2?"已设置":"";
			//return "L:"+ ko.toJS(self.left).substring(0,5) + ",T:"+ ko.toJS(self.top).substring(0,5);
		});
		self.tagtypedDesc = ko.computed(function(){
			if(data.tagtype=='1') {
				return "布尔型";
			} else if (data.tagtype=='2') {
				return "数值型";
			} else if (data.tagtype=='3') {
				return "整数型";
			} else if (data.tagtype=='4') {
				return "字符串";
			}
		});
		
		self.showEditTemplate = ko.computed(function(){
			return data.pagetagtype==3; 
		});
		self.Selected = ko.observable(false);
	}

	//页面设置
	function Pagelayout(data) {
		var self = this;
		self.pagelayoutuid = data.pagelayoutuid;
		self.layoutid = ko.observable(data.layoutid);
		self.menuid = ko.observable(data.menuid);
		self.menuname = ko.observable(data.menuname);
		self.ownerelement = ko.observable(data.ownerelement);
		self.layouttype = ko.observable(data.layouttype);
		self.layoutname = ko.observable(data.layoutname);
		self.description = ko.observable(data.description);
		self.width = ko.observable(data.width);
		self.height = ko.observable(data.height);
		self.background = ko.observable(data.background);
		self.listbg = ko.observable(data.listbg);
		self.planbg = ko.observable(data.planbg);
		self.hasPaging = ko.observable(data.hasPaging);
		self.pagetype = ko.observable(data.pagetype);
		self.preferences =  ko.observable(data.preferences);
		self.configvalue =  ko.observable(data.configvalue);
		self.deviceconfigid =  ko.observable(data.deviceconfigid);
		self.attrctrlid = ko.observable(data.attrctrlid);
	}
	
	//资产对象
	function Asset(data){
		var self = this;
		self.assetnum = data.assetnum;
		self.description = data.description;
		self.classstructureid = data.classstructureid;
		self.specclass = data.specclass;
		self.location = data.location;
		self.Selected = ko.observable(false);
	}
	
	//专业分类对象
	function SpecClass(data) {
		var self = this;
		self.dmvalue = data.dmvalue;
		self.description = data.description + '（'+ data.dmvalue +'）';
	}
	
	//资产类别对象
	function Classification(data) {
		var self = this;
		self.classificationid = data.classificationid;
		self.description = data.description + '（'+ data.classificationid +'）';
	}
	
	//位置对象
	function Location(data) {
		var self = this;
		self.location = data[0];
		self.description = data[1];
	}
	
	//计量单位对象
	function MeasureUnit(data) {
		var self = this;
		self.measureunitid = data.measureunitid;
		self.description = data.description + '（'+ data.measureunitid +'）';
	}
	
	// 资产分类对象
	function SpecClassInfo(val) {
		var self = this;
		self.specclassid = val;
	}

	// 资产位置对象[2014-08-18 zzx]
	function Locationinfo(data) {
		var self = this;
		self.locationid = data[0];
		self.location = data[1];
	}
	
	//操作模型
	function OperateModel() {
		
		 var self = this;
		 
		 //布局列表
		 self.layoutlist = ko.observableArray();
		 
		 self.action = ko.observable();
		 
		 self.message = ko.observable('');
		 
		 //当前布局
		 self.currentRow = ko.observable({});
		 
		 //布局对应的设备列表
		 self.pagetaglist = ko.observableArray();
		 //当前设备对象
		 self.currentPagetagRow = ko.observable({});
		 
		 //资产专业列表
		 self.specclass = ko.observableArray();
		 //资产分类列表
		 self.classifications = ko.observableArray();
		 //计量单位列表
		 self.measureunits = ko.observableArray();
		 // 资产分类列表
		 self.specclasslist = ko.observableArray();
		 // 资产位置列表，用于自动生成资产信息[2014-08-18 zzx]
		 self.locationlist = ko.observableArray();
		 // 资产位置列表
		 self.locations = ko.observableArray();
		 //资产列表
		 self.assets = ko.observableArray();
		 
		 self.totalAssets = ko.dependentObservable(function(){ 
			 return "("+ self.assets.length + ")";
		 });
		 
		 //文件列表
		 self.files = ko.observableArray();
		 //系统组件列表
		 self.controls = ko.observableArray();
		 //设备点显示范围
		 self.showranges= ko.observableArray(showranges);

		 //error,info,success
		 self.tip = function(type,msg) {
			 $.pnotify({
			        title: '操作提示',
			        text: msg,
			        type: type
			    });
		 };
		 
		 //布局列表查询关键字
		 self.keyword = ko.observable("");
		 //布局列表查询结果
		 self.filteredItems = ko.dependentObservable(function() {
		     var filter = self.keyword().toLowerCase();
		    
		     if (!filter) {
		         return self.layoutlist();
		     } else {
		    	 return ko.utils.arrayFilter(self.layoutlist(), function(item) {
		             return ko.toJS(item.layoutname()).toLowerCase().indexOf(filter) !== -1;
		         });
		     }
		 }, self);
		 
		 //新增布局
		 self.newLayout = function() {
			 var row = {
				     "background": "",
				     "description": "",
				     "hasBackground": false,
				     "hasPaging": false,
				     "height": 935,
				     "width": 1462,
				     "layoutid": "",
				     "layoutname": "",
				     "layouttype": "",
				     "menuid": "",
				     "menuname": "",
				     "ownerelement": "",
				     "pageindex": 0,
				     "pagelayoutuid": 0,
				     "configvalue": "",
				     "deviceconfigid": "",
				     "attrctrlid":""
				 };
				 self.currentRow(new Pagelayout(row));
				 self.message('');
				 $(".layoutData>li:eq(0)").addClass("active").siblings().removeClass("active");
				 $(".layoutData").siblings().find(".tab-pane:eq(0)").addClass("in active");
				 $(".layoutData").siblings().find(".tab-pane:eq(1)").removeClass("in active");
				 $(".layoutData>li:eq(1)>a").data("layoutid","");
				 $("#pagedatalist").empty();
		 };
		 
		 
		 //编辑布局
		 self.editLayout  = function(row) { 
			 self.message('');
			 self.currentRow(row);
			 // 设置设备列表的值
			 if(typeof(row.deviceconfigid) != undefined && row.deviceconfigid != null) {
			 	$("#deviceconfigid").val(row.deviceconfigid).trigger("change");
			 } else {
			 	$("#deviceconfigid").val("").trigger("change");
			 }
			 
			 $(".layoutData>li:eq(0)").addClass("active").siblings().removeClass("active");
			 $(".layoutData").siblings().find(".tab-pane:eq(0)").addClass("in active");
			 $(".layoutData").siblings().find(".tab-pane:eq(1)").removeClass("in active");
			 
			 var pagelayoutuid = JSON.stringify(row.pagelayoutuid);
			 $(".layoutData>li:eq(1)>a").data("layoutid",pagelayoutuid);
			 $("#pagedatalist").empty();
			 // 加载布局对应的设备点
			 /*$.getJSON(CONTEXT_PATH +"/okcsys/page/pagetag/"+ pagelayoutuid ,function(data){
				var mappedData= jQuery.map(data, function(item) { return new Pagetag(item);});
				self.pagetaglist(mappedData);	
			});	*/
		 };
		 //删除布局
		 self.removeLayout = function(){
			 var row = self.currentRow();
			 
			 self.message('');
			 if(typeof(row.pagelayoutuid)=='undefined' || row.pagelayoutuid==0) {
				 self.tip('info','新增状态下不能删除布局');
				 return false;
			 } else {
				 if(confirm('删除数据不可恢复，你确认要删除吗?')) {
					 var action = CONTEXT_PATH + "/okcsys/page/layout/remove/" + row.pagelayoutuid;
					 $.post(action,function(data){
						 if(data.success==true) {
							 self.newLayout();
							 self.layoutlist.remove(row);
							 self.tip('success','布局删除成功');
						 } else {
							 self.tip('error',data.msg);
						 }
					 });	 
				 }
			 }
		 };
		 
		// 保存新增的热区 [ ChengKang 2014-07-21 ]
		 self.saveMapCreate = function()
		 {
			 if(window.localStorage.MapJsonArray == null ||
					 window.localStorage.MapJsonArray == undefined || 
					 window.localStorage.MapJsonArray == "null" || 
					 window.localStorage.MapJsonArray == "undefined" ||
					 window.localStorage.MapJsonArray == "")
			 {
				 alert("没有需要保存的热区信息！");		// 如果没有新建的热区信息，则不执行保存新热区操作  [ ChengKang 2014-07-24 ]
				 return false;
			 }
			 
			 var MapJson = "[" + window.localStorage.MapJsonArray + "]";		// 获取本地存储对象中保存的MapJsonArray，并作为字符串，在两边加上[]，将其作为Json数组处理  [ ChengKang 2014-07-21 ]
			 var MapArray = eval(MapJson);					// MapJsonArray中存储了新建的一个或多个热区图形的信息，并以JSON字符串的形式传递给本页面  [ ChengKang 2014-07-21 ]
			 for(var i=0; i<MapArray.length; i++)			// 对每个表示热区信息的JSON对象做处理，新建组件，并保存到数据库，同时JSON信息将作为setting字段存储在该Tag对象的记录里  [ ChengKang 2014-07-21 ]
			 {
				 var action =CONTEXT_PATH + "/okcsys/page/pagetag/"+ self.currentRow().pagelayoutuid;
				 
				 var pagetag = {
						    tagid: MapArray[i].plan.TagID,
						    tagname: MapArray[i].plan.TagName,
						    tagtype: 1,													// 数据类型 1-布尔型
						    tagval: null,
						    label: null,
						    comments : null,
						    groupname: '',
						    parentid: '',
						    measureunitid: '',
						    controlid: '',
						    layoutid: self.currentRow().pagelayoutuid,
						    showrange:'plan',
						    controlid3:'WWWW',
						    pagetagtype: 1,												// 热区的TagType为1  [ ChengKang 2014-08-03 ]
						    usesetting: 1,												// 标记使用了setting字段  [ ChengKang 2014-07-21 ]
						    setting: JSON.stringify(MapArray[i])				// 热区的setting字段，以JSON字符串形式完整保存了热区的基本信息  [ ChengKang 2014-07-21 ]
						};
				
				 /*
				 var pagetag = self.newPagetag();
				 pagetag.tagid(MapArray[i].plan.TagID);
				 pagetag.tagname(MapArray[i].plan.TagName);
				 pagetag.tagtype(1);															// 热区的TagType为1  [ ChengKang 2014-07-24 ]
				 pagetag.layoutid = self.currentRow().pagelayoutuid;
				 pagetag.usesetting(1);														// 标记使用了setting字段  [ ChengKang 2014-07-24 ]
				 pagetag.setting(JSON.stringify(MapArray[i]));					// 热区的setting字段，以JSON字符串形式完整保存了热区的基本信息  [ ChengKang 2014-07-24 ]
				 */
				 
				$.post(action, pagetag, function(TempData)
				{
					if (TempData.message.success == true)
					{
						self.tip('success', '热区设置成功');
						self.message("热区设置成功");
						//if (pagetag.pagetagid == 0)			// 保存完每个热区后，热区的PageTagID将不为0，因此对于新增的热区，总是需要加载到PageTagList中 [ ChengKang 2014-07-24 ]
						{
							self.pagetaglist.push(new Pagetag(TempData.objects[0]) );
						}
						$('#assetModal').modal('hide');
					}
					else
					{
						self.tip('error', TempData.message.msg);
					}
				});
			 }
			 
			 window.localStorage.removeItem("MapJsonArray");			// 保存的热区信息是通过本地存储对象MapJsonArray由热区绘制页面传递给本页面的，保存后清空MapJsonArray对象 [ ChengKang 2014-07-23 ]
		 }
		 
		 //保存布局
		 self.saveLayout = function()
		{
			
			var isValid = $("#pagelayout").validate(
			{
				errorElement : "span",
				errorClass : "text-error",
				success : function(label)
				{
					label.text("ok!").addClass("success");
				}
			}).form();

			if (isValid)
			{
				var action = CONTEXT_PATH + "/okcsys/page/layout";
				if (self.currentRow().pagelayoutuid == '' && self.currentRow().pagelayoutuid != 0)
				{
					self.currentRow().pagelayoutuid($("#pagelayoutuid").val());
				}

				self.currentRow().deviceconfigid = $("#deviceconfigid").select2("val"); // 设置设备列表对应的项
				$.post(action, self.currentRow(), function(data)
				{
					if (data.message.success == true)
					{
						self.tip('success', '页面布局操作成功');
						if (self.currentRow().pagelayoutuid == 0)
						{
							self.layoutlist.push(new Pagelayout(data.objects[0]));
						}
					}
					else
					{
						self.tip('error', data.message.msg);
					}
				});
			}

		};
		 
		 // 添加资产数据到列表
		 self.addAssets = function() {
			 if(typeof(self.currentRow().layoutid)=='undefined') {
				self.message("请选择要操作的布局");
				return false;
			}else {
				 $('#assetModal').modal();
			}
		 };

		 // 添加客流数据到列表
		 self.addPassengers = function() {
			 if(typeof(self.currentRow().layoutid)=='undefined') {
				self.message("请选择要操作的布局");
				return false;
			}else {
				 $('#passengerModal').modal();
			}
		 };
		 
		 //查询资产数据
		 self.queryAssets = function() {
			 
			 //查询地址和查询参数
			 var queryUrl = CONTEXT_PATH + "/asset/query",
			 	dataParam = $("#assetQueryForm").serializeArray();
			 $.ajax({
				 type:'GET',
				 url:queryUrl,
				 data:dataParam,
				 dataType: "json",
				 contentType: "application/json; charset=utf-8",
				 success:function(data) {
					 
					 var mappedData= jQuery.map(data, function(item) { return new Asset(item); });
	                 self.assets(mappedData);
	                 
				 },error:function(err) {
					 self.message(err);
				 }
			 });
		 };

		 // 全选查询出来的资产数据
		 self.checkAllAssets = function() {
			$("input[name='chk']").each(function() {
						$(this).attr("checked", !this.checked);
					});
		 };
		 
		 //点击CheckBox选择资产数据，并设置选中标记
		 self.chosenAssets = function(item) {
		      item.Selected(!(item.Selected()));
		      return true;
		 };
		 
		 //选择所有查询的资产
		 self.selectAll = function(){
			 $("input[name='chk']:unchecked").attr("checked",true);
			 $.each(self.assets(),function(i,n){
				 n.Selected(true);
			 });
		 }
		//排除选中的资产
		 self.selectOthers = function(){
			 $.each($("input[name='chk']"),function(){
				 if($(this).attr("checked")!="checked"&&$(this).attr("checked")!=true){
					 $(this).attr("checked",true);
				 }else{
					 $(this).attr("checked",false);
				 }
			 });
			 $.each(self.assets(),function(i,n){
				 if(self.assets()[i].Selected()==true){
					 n.Selected(false);
				 }else{
					 n.Selected(true); 
				 }
			 });
		 }
		 
		 //保存已选择的资产数据到PAGETAG
		 self.selectAssets = function(item) {
		     
			 //循环已选择的数据项，关保存到数据库中
			 $.each(ko.toJS(self.selectedAssets),function(index,asset){
				 var action = CONTEXT_PATH + "/okcsys/page/pagetag/"+ self.currentRow().pagelayoutuid;
				 var pagetag = {
						    tagid: asset.assetnum,
						    tagname: asset.description,
						    tagtype: 0,
						    tagval: 1,
						    label: asset.description,
						    comments : asset.description,
						    groupname: '',
						    parentid: '',
						    measureunitid: '',
						    controlid: '',
						    layoutid: ko.toJS(self.currentRow().layoutid),
						    pagetagtype: pagetagtype.ASSET,
						    usesetting: 0
						};

				$.post(action,pagetag,function(data){
					
					if(data.message.success==true) {
						self.tip('success','页面设备点操作成功');
						self.message("设备点操作成功");
						if(pagetag.pagetagid==0) {
							self.pagetaglist.push(new Pagetag(data.objects[0]));
						}
						$('#assetModal').modal('hide');
					} else {
						self.tip('error',data.message.msg);
					}
				});
			 });
			 
			 var pagelayoutuid = JSON.stringify(self.currentRow().pagelayoutuid);
		   	 loadData(pagelayoutuid); // 重新刷新列表
		 };
		 
		 //获取已选择的资产数据
		 self.selectedAssets = ko.computed(function() {
		        return self.assets().filter(function(item) {
		            return item.Selected();
		      });
		  }); 
		 
		 self.newPagetag = function() {
			if(typeof(self.currentRow().layoutid)=='undefined' || ko.toJS(self.currentRow()).layoutid==0) {
				self.message("请选择要操作的布局");
				return false;
			}else {
				 var row = {"pagetagid":0,"tagid":"","tagname":"","tagtype":0,"tagval":"","label":"","measureunitid":"","controlid":"","left":'',"top":'',"layoutid":self.currentRow().layoutid,"createby":"","pagetagtype":0};
				 self.currentPagetagRow(row);
				 self.message('');
				 $('#editModal').modal();
			}
		 };

		 // 生成资产信息，[2014-08-17 zzx]
		 self.buildAsset = function() {
			if (typeof(self.currentRow().layoutid) == 'undefined' || ko.toJS(self.currentRow()).layoutid == 0) {
				self.message("请选择要操作的布局");
				return false;
			} else {
				$('#buildAssetModal').modal();
			}
		 };

		 self.addPassengers = function() {
			if (typeof(self.currentRow().layoutid) == 'undefined' || ko.toJS(self.currentRow()).layoutid == 0) {
				self.message("请选择要操作的布局");
				return false;
			} else {
				var row = {
					"pagetagid" : 0,
					"tagid" : "",
					"tagname" : "",
					"tagtype" : 0,
					"tagval" : "",
					"label" : "",
					"measureunitid" : "",
					"controlid" : "",
					"left" : '',
					"top" : '',
					"layoutid" : self.currentRow().layoutid,
					"createby" : "",
					"pagetagtype" : 5
				};
				self.currentPagetagRow(row);
				self.message('');
				$('#passengerModal').modal();
			}
		 };
		 
		 self.newTagControl = function() {
			 if (self.currentRow().layoutid() == '') {
					self.message("请选择要操作的布局");
					return false;
				} else {
					 var row = {"pagetagid":0,"tagid":"","tagname":"","tagtype":0,"tagval":"","label":"","measureunitid":"","controlid":"","left":'',"top":'',"layoutid":self.currentRow().layoutid,"createby":"","pagetagtype":99};
					 self.currentPagetagRow(row);
					 self.message('');
					 $('#editModal').modal();
				}
		 };
		 
		 self.newPanelControl = function(){
			 if (self.currentRow().layoutid() == '') {
					self.message("请选择要操作的布局");
					return false;
				} else {
					 var row = {"pagetagid":0,"tagid":"","tagname":"","tagtype":0,"tagval":"","label":"","measureunitid":"","controlid":"","left":'',"top":'',"layoutid":self.currentRow().layoutid,"createby":"","pagetagtype":98};
					 self.currentPagetagRow(row);
					 self.message('');
					 $('#editModal').modal();
				}
		 }
		 
		 //编辑设备点
		 self.editPagetag = function(row) {
			console.log(row);
			self.currentPagetagRow(row);
			self.message('');
			$('#editModal').modal();
		 };
		 // 删除布局设备点
		 self.removePagetag = function(row) {
			self.message('');
			if (confirm('删除数据不可恢复，你确认要删除吗?')) {

				// 删除数据库记录
				$.post(	CONTEXT_PATH + "/okcsys/page/pagetag/delete/" + row.pagetagid, function(data) {
							if (data.success == true) {
								self.message('删除成功'); // 移除数组中的数据
								self.pagetaglist.remove(row);
							} else {
								self.tip('error', "删除失败");
								self.message('删除失败');
							}
						});
			}

		};
		 
		 //更新设备点信息
		self.submitPagetag = function()
		{
			var isValid = $("#pagetag").validate({
						errorElement : "span",
						errorClass : "text-error",
						rules : {
							tagid : {
								required : true,
								minlength : 2,
								maxlength : 15
							}
						},
						success : function(label) {
							label.text("ok!").addClass("success");
						}
					}).form();

			if (isValid)
			{
				var action = CONTEXT_PATH + "/okcsys/page/pagetag/"
						+ self.currentRow().pagelayoutuid;
				var showrange = self.currentPagetagRow().showrange;
				console.log(showrange);
				try{
					showrange = ko.toJS(showrange).join(",");
				} catch(e) {}
				self.currentPagetagRow().showrange = showrange;

				// 对PageTag中setting字段的JSON信息做修改，将JSON里保存的TagID和TagName也修改成编辑窗口设置的值，保持两者一致 [ ChengKang 2014-07-24 ]
				var pagetag = self.currentPagetagRow();
				// 本窗口在新增设备或组件时，通过new Pagetag()方式创建的对象是Object类型
				// 本窗口在修改Pagetag时，此处获得到的对象是Pagetag类型
				// 因此这里要加以区分，因为Object对象中各个成员不是函数，不能用函数方式获取或修改
				if(pagetag.constructor === Pagetag)		// 如果Pagetag类型
				{
					var settingStr = pagetag.setting();
					if(settingStr != undefined && settingStr != null && settingStr != "" && settingStr != "null" && settingStr != "undefined")
					{
						try {
							var settingJson = $.parseJSON(settingStr);
							settingJson.plan.TagID = pagetag.tagid();
							settingJson.plan.TagName = pagetag.tagname();
							pagetag.setting(JSON.stringify(settingJson));
						} catch(e) {
							//console.log("--error--setting赋值出问题了！");
						}
					}
				}
				$.post(action, pagetag, function(data)
				{
					if (typeof (data.message) == 'undefined')
					{
						self.tip('error', data);
						return false;
					}
					if (data.message.success == true)
					{
						self.tip('success', '页面设备点操作成功');
						self.message("设备点操作成功");
						if (pagetag.pagetagid == 0)
						{
							self.pagetaglist.push( new Pagetag(data.objects[0]) );		// new Pagetag(data.objects[0])
						}
						$('#editModal').modal('hide');
						$('#passengerModal').modal('hide');
						
						loadData(self.currentRow().pagelayoutuid); // 重新加载grid
					}
					else
					{
						self.tip('error', data.message.msg);
					}
				});

			}
		};
		
		// 生成资产及对应的属性信息[2014-08-17 zzx]
		self.buildAssetAttrInfo = function() {
			
			// 组织资产信息
			var assetinfo = new Object(); 
			assetinfo.specid = $("#b_specid").val(); 
			assetinfo.locationid = $("#b_locationid").val(); 
			assetinfo.classstructureid = $("#b_classstructureid").val();
			
			var action = CONTEXT_PATH + "/asset/buildpagetag/" + self.currentRow().pagelayoutuid;
			var pagetag = {
			    tagid: "11",
			    tagname: "11",
			    tagtype: 1,													// 数据类型 1-布尔型
			    tagval: null,
			    label: null,
			    comments : null,
			    groupname: '',
			    parentid: $("#b_specid").val(), // 临时存放选择的分类，用于传递到后台[2014-08-17 zzx]
			    measureunitid: $("#b_locationid").val(), // 临时存放选择的位置，用于传递到后台[2014-08-18 zzx]
			    controlid: $("#b_classstructureid").val(), // 临时存放选择的分类，用于传递到后台[2014-08-18 zzx]
			    layoutid: self.currentRow().pagelayoutuid,
			    showrange:'plan',
			    pagetagtype: pagetagtype.ASSET,
			    usesetting: 1,
				setting: JSON.stringify(assetinfo)
			};
			$.post(action, pagetag, function(data) {
					if (typeof(data.message) == 'undefined') {
						self.tip('error', data);
						return false;
					}
					if (data.message.success == true) {
						self.tip('success', '页面设备点操作成功');
						self.message("设备点操作成功");
						if (pagetag.pagetagid == 0) {
							self.pagetaglist.push(new Pagetag(data.objects[0])); // new Pagetag(data.objects[0])
						}
						$('#editModal').modal('hide');
						$('#buildAssetModal').modal('hide');
						$('#passengerModal').modal('hide');

						loadData(self.currentRow().pagelayoutuid); // 重新加载grid
					} else {
						self.tip('error', data.message.msg);
					}
				});
		};
		 
		 
		 // 全选/反选
		 self.selectAllTag = ko.computed({
		        read: function() {
		            var item = ko.utils.arrayFirst(self.pagetaglist(), function(item) {
		                return !item.Selected();
		            });
		            return item == null;           
		        },
		        write: function(value) {
		            ko.utils.arrayForEach(self.pagetaglist(), function (item) {
		            	item.Selected(value);
		            });
		        }
		});
		 
		 self.pagetagTypes = [
		          {title:'全部', filter: null,group:'tag'},
		          {title:'设备', filter: '0',group:'tag'},
		          {title:'热区', filter: '1',group:'tag'},			// 热区的TagType为1 [ Chen个Kang 2014-07-22 ]
		          {title:'视频', filter: '2',group:'tag'},
		          {title:'资产', filter: '3',group:'tag'},
		          {title:'客流', filter: '5',group:'tag'},
		          {title:'统计面板', filter: '98',group:'tag'},
		          {title:'组件', filter: '99',group:'tag'},
		          {title:'设备列表', filter: 'list',group:'showrange'},
		          {title:'结构图', filter: 'structure',group:'showrange'},
		          {title:'平面图', filter: 'plan',group:'showrange'}
		      ];
		 
		    self.activeFilter = ko.observable(self.pagetagTypes[0]);//set a default filter    
		    self.setActiveFilter = function(model,event){
		        self.activeFilter(model);
		    };
		    self.filteredPagetag = ko.computed(function(){
		        var result;
		        var filter = self.activeFilter().filter;
		        
		        if(filter){
		        	console.log(self.activeFilter());
		            result = ko.utils.arrayFilter(self.pagetaglist(), function(item){
		            	if(self.activeFilter().group=='showrange') {
		            		return ko.toJS(item.showrange).indexOf(filter)!=-1;
		            	} else {
		            		return ko.toJS(item.pagetagtype)== filter;
		            	}
		            });
		        } else {
		            result = self.pagetaglist();
		        }
		        return result;
		    });
		    
		    /*var filter = self.keyword().toLowerCase();
		    
		     if (!filter) {
		         return self.layoutlist();
		     } else {
		    	 return ko.utils.arrayFilter(self.layoutlist(), function(item) {
		             return ko.toJS(item.layoutname()).toLowerCase().indexOf(filter) !== -1;
		         });
		     }*/
		    
		 
		//指量设置设备属性
		 self.batchSetTagProp = function() {
		     if (typeof(self.currentRow().layoutid) == 'undefined') {
		         self.message("请选择要操作的布局");
		         return false;
		     } else {
		         self.message('');
		         $('#batchSetPropModal').modal();
		     }
		 };
		 
		 //批量更新设备点属性
		 self.submitTagPropChange = function() {
			 
			 //提交地址
			 var action =CONTEXT_PATH + "/okcsys/page/pagetag/porps/"+ self.currentRow().pagelayoutuid;
			 
			 //选择的数据
			 var ids = new Array();
			 
			 // 获取treegrid中的checkbox选中项[2014-08-18 zzx]
             $("input[type=checkbox][name=tagbox]:checked").each(function () {
             	 ids.push(this.value);
             });
             if (ids.length == 0) {
             	 alert("请选择要设置的记录！");
	             return false;
             }
			 
//			 ko.utils.arrayFilter(self.pagetaglist(), function(item) {
//				 if( item.Selected()) {
//					 ids.push(item.pagetagid);
//				 }
//	         });

	         // 邹智祥zzx2014-07-31修改，将存放面板的数据转移到attrctrlid -- start
			 //连接数据并设备到暂存字段
			 $("#batchModify #setting").val(ids.join(",") + ";" + $("#attrctrlid").val());
	         // 邹智祥zzx2014-07-31修改-- end
	         
	         //获取提交数据
			 var data = $("#batchModify").serializeArray();
			 data.push({
			 	name : "showrange",
			 	value : ($("#showrange").val() == null) ? "" : $("#showrange").val().join(",")
			 });

			 // 提交数据
			 $.post(action,data,function(result){
				if(result.success==true) {
					self.tip('success','页面设备点属性更新操作成功');
					$('#batchSetPropModal').modal('hide');
				} else {
					self.tip('error',data.msg);
				}
			}); 
		 };
		 
		// 保存热区修改   [ ChengKang 2014-07-23 ]
		 self.btnSaveDrawMap=
			function(row)
			{
				 if(window.localStorage.MapJsonArray == null ||
						 window.localStorage.MapJsonArray == undefined || 
						 window.localStorage.MapJsonArray == "null" || 
						 window.localStorage.MapJsonArray == "undefined" ||
						 window.localStorage.MapJsonArray == "")
				 {
					 alert("没有需要保存的热区信息！");		// 如果用户修改热区时将热区删除，而没有添加新的热区给PageTag，则不会执行热区修改操作，将空的热区存储到数据库 [ ChengKang 2014-07-24 ]
					 return false;
				 }
				 var MapJson = "[" + window.localStorage.MapJsonArray + "]";		// 获取本地存储对象中保存的MapJsonArray，并作为字符串，在两边加上[]，将其作为Json数组处理 [ ChengKang 2014-07-23 ]
				 var MapArray = eval(MapJson);
				 
				 var action =CONTEXT_PATH + "/okcsys/page/pagetag/"+ self.currentRow().pagelayoutuid;
				 var pagetag = row;
				 
				 //  [ ChengKang 2014-07-23 ]
				 // 由于此函数用来保存对某个热区的修改操作，因此只允许对每个PageTag保存一个热区信息
				 // 即：在编辑某个PageTag热区时，即便用户对一个PageTag绘制了多个热区，也只将第一个热区保存起来，忽略其他多余的热区
				 // 因此，虽然绘制热区界面通过MapJsonArray可能传递过来多个热区的JSON，之前接收时也按照数组方式接收和解析，但实际只存储数组中第一个元素
				 pagetag.tagid(MapArray[0].plan.TagID);
				 pagetag.tagname(MapArray[0].plan.TagName);
				 //pagetag.tagtype = 1;
				 pagetag.setting(JSON.stringify(MapArray[0]));
				 
				 var showrange = pagetag.showrange;
				console.log(ko.toJS(showrange));
				showrange = ko.toJS(showrange).join(",");
				pagetag.showrange = showrange;
				 
				$.post(action, pagetag, function(TempData)
				{
					if (typeof (TempData.message) == 'undefined')
					{
						self.tip('error', TempData);
						return false;
					}
					if (TempData.message.success == true)
					{
						window.localStorage.clear();
						
						self.tip('success', '热区修改成功');
						self.message("热区修改成功");
						if (pagetag.pagetagid == 0)
						{
							self.pagetaglist.push(pagetag);
						}
						$('#editModal').modal('hide');
					}
					else
					{
						self.tip('error', TempData.message.msg);
					}
				});

			};
		 
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
	
	var operateModel = new OperateModel();
	ko.applyBindings(operateModel);
	//operateModel.errors = ko.validation.group(operateModel);
	$.getJSON(CONTEXT_PATH +"/okcsys/page/list",function(data){
		 var mappedData= jQuery.map(data, function(item) { return new Pagelayout(item);});
		operateModel.layoutlist(mappedData);	 
	});
	
	//加载子系统
	$.getJSON(CONTEXT_PATH+'/domain/aln/subsys',function(data){
		 var mappedData= jQuery.map(data, function(item) { 
			 return new SpecClass(item);});
		// console.log(mappedData);
		 operateModel.specclass(mappedData);
	});
	
	//类别列表
	$.getJSON(CONTEXT_PATH+'/class/classificationlist',function(data){
		 var mappedData= jQuery.map(data, function(item) { 
			 return new Classification(item);});
		 //console.log(mappedData);
		 operateModel.classifications(mappedData);
	});
	
	//位置列表
//	$.getJSON(CONTEXT_PATH+'/locations',function(data){
//		 var mappedData= jQuery.map(data, function(item) { 
//			 return new Location(item);});
//		 //console.log(mappedData);
//		 operateModel.locations(mappedData);
//	});
	
	//计量单位  
	$.getJSON(CONTEXT_PATH+'/measureunit',function(data){
		 var mappedData= jQuery.map(data, function(item) { 
			 return new MeasureUnit(item);});
		// console.log(mappedData);
		 operateModel.measureunits(mappedData);
	});

	// 动态生成设备信息，构建资产分类信息[2014-08-17 zzx]
	$.getJSON(CONTEXT_PATH+'/asset/specclassinfo/list',function(data){
		 var mappedData= jQuery.map(data, function(item) { 
			 return new SpecClassInfo(item);
		 });
//		 console.log(mappedData);
		 operateModel.specclasslist(mappedData);
	});

	// 动态生成设备信息，构建资产位置信息[2014-08-18 zzx]
	$.getJSON(CONTEXT_PATH+'/asset/locationinfo/list',function(data){
		 var mappedData= jQuery.map(data, function(item) { 
			 return new Locationinfo(item);
		 });
		 operateModel.locationlist(mappedData);
		 
 		 var mappedData= jQuery.map(data, function(item) { 
			 return new Location(item);
		 });
		 operateModel.locations(mappedData);
	});
	
	//图片列表
	$.getJSON(CONTEXT_PATH+'/okcsys/page/files',function(data){
		 var mappedData= jQuery.map(data, function(item) { 
			 return new FileInfo(item.value,item.key);});
//		 console.log(mappedData);
		 operateModel.files(mappedData);
	});
	
	//组件列表
	$.getJSON(CONTEXT_PATH+'/okcsys/controls/list',function(data){
		 var mappedData= jQuery.map(data, function(item) { 
			 return new Control(item);});
		 operateModel.controls(mappedData);
	});
	
	
	operateModel.newLayout();
	
	
	 $('#divicelist').slimScroll({
         height: $(document).height()-$("#navigation").height() - $("#sysnav").height(),
         width: $(document).width() - $(".span3").width()
      });
	
	 
	 $("#layouts").slimScroll({
		 height:'100%',
		 width: '100%',
		 alwaysVisible: true
	 });

	 /*$("#layouts").mCustomScrollbar({scrollButtons:{
			enable:true
		},theme:"dark-thick"});*/

	 
	$("#btnSetCoordinate").click(function(){
		var ownerelement = $("#ownerelement").val();
		var menuid = $("#menuid").val();
		
		if(ownerelement=="" || menuid=="") {
			alert("请选择菜单！");
			$("#menuname").focus();
			return false;
		}
		
		var editUrl = CONTEXT_PATH + "/mctrl/"+ ownerelement + "/"+ menuid +".html";
		
		var vReturnValue = window.open(editUrl,"newwindow","modal=yes,height=1080,width=1920,resizable=no,status=no,location=no,help=no,center=yes")
	});
	
	// 新建热区  [ ChengKang 2014-07-21 ]
	 $("#btnSubmitDrawMap").click
	 (
		function()
		{
			// 清除本地存储对象，防止之前对热区新增或修改后遗留下热区信息。
			// 这样做是为了新增热区时，绘制热区界面不加载任何其他的热区图形，仅显示底图 [ ChengKang 2014-07-24 ]
			window.localStorage.clear();
			
			// 传递PageLayoutID和界面底图的URL给绘制热区界面
			var param = 
			{
					mapUrl : "",
					layoutid : 0,
					pagetagid : 0,
					Reback : function(JsonStr, PageTagId, layoutid)		// 供弹出的Map子窗口回调的函数
					{
						saveMapCreate(JsonStr, PageTagId, layoutid);		// 调用SaveMap函数保存Map修改
					}
			};
			param.mapUrl = CONTEXT_PATH+"/"+$("#planbg").val();
			param.layoutid = $("#layoutid").val();
			param.pagetagid = 0;
			
			if (param.mapUrl == "undefined" || param.mapUrl == "null" || param.mapUrl == "")
			{
				alert("请选择系统平面图或上传图片！");
			}
			else
			{
				hotArea = param;
				// 以弹出窗口形式加载绘制热区界面，并将参数结构体传递过去
				var editUrl = CONTEXT_PATH + "/resources/plugins/map-tools/index.jsp";
				var vReturnValue = window.open(editUrl,"newwindow","modal=yes,height=1080,width=1920,resizable=no,status=no,location=no,help=no,center=yes");
			}
		}
	);
	
	//上传图片
	$("#btnSubmitUpload").click(function(e){
		backgroundFileUpload();	
	});

	function backgroundFileUpload() {
		
		var ownerelement = $("#ownerelement").val();
		var menuname = $("#menuname").val();
		
		var filename = ""; //ownerelement + "_" + menuname;
		
		if(menuname=="") {
			alert("请选择关联菜单");
			return false;
		}
		
		var file = $("#file").val();
		var filename2 = file.substring(file.lastIndexOf('\\')+1,file.length);
		filename2 = filename2.split("\.");
		if(file==""){
			alert("未选择图片文件！");
			return false;
		}else{
			$.ajaxFileUpload({
				url : CONTEXT_PATH +'/okcsys/page/upload/background', 
				secureuri : false, 
				fileElementId : 'file',
				data : {
					filename : filename2[0] //传入重命名名称
				},
				dataType : 'text',
				success : function(data, status) 
				{
					data = $.trim(data);
					/*if(data=="scucess"){
						alert("上传成功！");
					}*/
					if (data.length == 0 || data == "") {
						alert("文件类型错误，上传失败！");
					} else {
						alert("上传成功！");
					}
					//$("#background").val(unescape(data));
//					console.log(data);
					//var fileName = data.substring(data.lastIndexOf('/')+1,data.length);
					operateModel.files.push( new FileInfo(data,filename2[0]+"."+filename2[1]));
					$('#uploadModal').modal('hide');
				},
				error : function(data, status, e) // 相当于java中catch语句块的用法
				{
					console.log(e);
					alert("图片上传失败！");
				}
			});
		}
	}
	
	//ZTree Setting
	var setting = {
		view : {
			showLine : true,
			showIcon : true,
			dblClickExpand : true
		},
		data : {
			key : {
				checked : "checked",
				children : "children",
				name : "name",
				title : "title",
				code : "code",
				parentname : "parentname"
			}
		},
		callback : {
			beforeClick : beforeClick,
			onClick : onClick
		}
	};
	//Ansy Load Menu Data
	var action = CONTEXT_PATH + "/okcsys/okcmenu/MCTRL";
	$.getJSON(action, function(data) {
		var zNodes = data;
		//zNodes.push(data);
		//console.log(zNodes);
		$.fn.zTree.init($("#ztree"), setting, zNodes);

	});

	//
	function beforeClick(treeId, treeNode) {
		var check = (treeNode && !treeNode.isParent);
		if (!check)
			alert("请不要选择父菜单...");
		return check;
	}

	//click event,add info to form
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("ztree"), nodes = zTree
				.getSelectedNodes();
		
		var parentname = nodes[0].parentname;
		var menuname = parentname+"_"+ nodes[0].name;
		var layoutid = nodes[0].title+"_"+nodes[0].name;
		
		operateModel.currentRow().menuid(nodes[0].id);
		operateModel.currentRow().menuname(menuname);
		operateModel.currentRow().ownerelement(nodes[0].topMenu);
	    operateModel.currentRow().layoutid(layoutid); 
	
		$("#menuid").val(nodes[0].id);
		$("#menuname").val(menuname);
		if($("#layoutname").val()==""){
			$("#layoutname").val(menuname);
			operateModel.currentRow().layoutname = menuname;
		}
		$("#ownerelement").val(nodes[0].topMenu);
		$("#layoutid").val(layoutid);
		
		$('#myModal').modal('hide');
	}
	
	//全选/取消全选
    $('#quanxuan').click(function() {
		$("input[name='tagbox']").attr("checked", 'true');
	});

    // 反选
    $('#fanxuan').click(function() {
		$("input[name='tagbox']").each(function() {
			if ($(this).attr("checked")) {
				$(this).removeAttr("checked");
			} else {
				$(this).attr("checked", 'true');
			}
		});
	});

	// 往设备列表下拉列表中填充数据
	$.getJSON(CONTEXT_PATH + '/okcsys/devicelist/list',
			function(data) {
				$("#deviceconfigid").empty();
				$("#deviceconfigid").append("<option value=''>请选择...</option>");
				for (var i = 0; i < data.length; i++) {
					$("#deviceconfigid").append("<option value='" + data[i].id + "'>" + data[i].modul + "</option>");
				}
				$("#deviceconfigid").select2({ width : '200px' });
			});
});


function clickCheckbox() {
	$("input[name='tagbox']").attr("checked", 'true');
}


 function formatProgress(value){
    if (value){
        var s = '<div style="width:100%;border:1px solid #ccc">' +
                '<div style="width:' + value + '%;background:#cc0000;color:#fff">' + value + '%' + '</div>'
                '</div>';
        return s;
    } else {
        return '';
    }
}
function onContextMenu(e,row){
    e.preventDefault();
    $(this).treegrid('select', row.id);
    $('#mm').menu('show',{
        left: e.pageX,
        top: e.pageY
    });
}

/**
 * 重新加载grid
 * 
 * @param {} pagelayoutuid
 */
function loadData(pagelayoutuid) {
	$.post(CONTEXT_PATH +"/okcsys/page/getpagetag/" + pagelayoutuid,{},function(data) {
		console.log(data);
	  	$('#tg').treegrid('loadData',data);
	},'json');
} 

// 修改热区 [ ChengKang 2014-08-19 ]
function btnEditDrawMap(layoutid, pagetagid,  tagtype, setting)
{
 	window.localStorage.clear();		// 清除本地缓存对象中对前一个热区操作的记录
	//var pagetag = row;				// 获得当前编辑的PageTag  [ ChengKang 2014-07-23 ]

	if( typeof( layoutid ) == 'undefined')
	 {
		alert("请选择要操作的布局");
         return false;
     }
	
	// PageTag的setting属性 [ ChengKang 2014-07-23 ]
	var settingJson = setting;
	if(tagtype != 1 || typeof(settingJson) == "undefined"  || settingJson === "" || settingJson === "null")		// 只允许修改TagType为1（表示该PageTag为热区）的对象  [ ChengKang 2014-07-23 ]
	{
		alert("请选择热区进行修改！");
        return false;
	}
	// 将PageTag中保存在setting里的JSON字符串转换为热区绘制界面能识别的JSON格式，供之后加载热区图形 [ ChengKang 2014-07-23 ]
	else		// 表示存在setting [ ChengKang 2014-07-23 ]
	{
		// 解析保存在setting中的JSON字符串模转换为热区绘制界面能识别的JSON格式 [ ChengKang 2014-07-23 ]
		settingJson = settingJson.replaceAll("\"plan\":{", "\"areas\":[{");
		settingJson = settingJson.replaceAll("\"TagID\"", "\"state\"");
		settingJson = settingJson.replaceAll("\"TagName\"", "\"href\"");
		settingJson = settingJson.replaceAll("\"shape\"", "\"type\"");
		settingJson = settingJson.replaceAll("\"poly\"", "\"polygon\"");
		settingJson = settingJson.replaceAll("}}", "}]}");
		
		// [ ChengKang 2014-07-23 ]
		// 热区绘制界面通过读取本地存储对象SummerHTMLImageMapCreator来加载热区图形
		// 因此，此处将解析完的setting信息保存到SummerHTMLImageMapCreator中，供热区绘制界面调用
		window.localStorage.setItem('SummerHTMLImageMapCreator', settingJson);
	}
	
	// 加载热区绘制界面，并传递底图的URL和PageLayoutID [ ChengKang 2014-07-23 ]
	var param = 
	{
			mapUrl : "",
			layoutid : 0,
			pagetagid : 0,
			Reback : function(JsonStr, PageTagId, layoutid)		// 供弹出的Map子窗口回调的函数
			{
				SaveMap(JsonStr, PageTagId, layoutid);		// 调用SaveMap函数保存Map修改
			}
	};
	param.mapUrl = CONTEXT_PATH+"/"+$("#planbg").val();
	param.layoutid = $("#layoutid").val();
	param.pagetagid = pagetagid;
	param.layoutid = layoutid;
	if (param.mapUrl == "undefined" || param.mapUrl == "null" || param.mapUrl == "")
	{
		alert("请选择系统平面图或上传图片！");
	}
	else
	{
		hotArea = param;
		// 以新窗口方式加载热区绘制界面 [ ChengKang 2014-07-23 ]
		var editUrl = CONTEXT_PATH + "/resources/plugins/map-tools/index.jsp";
		var vReturnValue = window.open(editUrl, param,  "dialogHeight=1080px;dialogWidth=1920px;center=yes;resizable=no;status=no;help=no");
	}
}

function saveMapCreate(JsonStr, PageTagId, layoutid)
{
	if(JsonStr == null || typeof(JsonStr) == "undefined" || JsonStr == "null" || JsonStr == "")
	 {
		 alert("没有需要保存的热区信息！");		// 如果没有新建的热区信息，则不执行保存新热区操作  [ ChengKang 2014-07-24 ]
		 return false;
	 }
	 
	//var MapJson = "[" + JsonStr + "]";
	// var MapJson = "[" + window.localStorage.MapJsonArray + "]";		// 获取本地存储对象中保存的MapJsonArray，并作为字符串，在两边加上[]，将其作为Json数组处理  [ ChengKang 2014-07-21 ]
	var MapArray = eval(JsonStr);						// MapJsonArray中存储了新建的一个或多个热区图形的信息，并以JSON字符串的形式传递给本页面  [ ChengKang 2014-07-21 ]
	 for(var i=0; i<MapArray.length; i++)			// 对每个表示热区信息的JSON对象做处理，新建组件，并保存到数据库，同时JSON信息将作为setting字段存储在该Tag对象的记录里  [ ChengKang 2014-07-21 ]
	 {
		 var action = CONTEXT_PATH + "/okcsys/page/pagetag/"+ layoutid;
		 var TempJson = JSON.parse(MapArray[i]);
		 var pagetag = {
				    tagid: TempJson.plan.TagID,
				    tagname: TempJson.plan.TagName,
				    tagtype: 1,										// 数据类型 1-布尔型
				    tagval: null,
				    label: null,
				    comments : null,
				    groupname: '',
				    parentid: '',
				    measureunitid: '',
				    controlid: '',
				    layoutid: layoutid,
				    showrange:'plan',
				    controlid3:'WWWW',
				    pagetagtype: 1,									// 热区的TagType为1  [ ChengKang 2014-08-03 ]
				    usesetting: 1,									// 标记使用了setting字段  [ ChengKang 2014-07-21 ]
				    setting: MapArray[i]							// 热区的setting字段，以JSON字符串形式完整保存了热区的基本信息  [ ChengKang 2014-07-21 ]
				};
		 
		$.post(action, pagetag, function(TempData)
		{
			if (TempData.message.success == true)
			{
				loadData(layoutid);	// 刷新数据
				tip('success', '热区创建成功');
				alert("热区创建成功");
			}
			else
			{
				tip('error', TempData.message.msg);
			}
		});
	 }
	 
	 window.localStorage.removeItem("MapJsonArray");			// 保存的热区信息是通过本地存储对象MapJsonArray由热区绘制页面传递给本页面的，保存后清空MapJsonArray对象 [ ChengKang 2014-07-23 ]
}

function SaveMap(JsonStr, PageTagId, layoutid)
{
	if(JsonStr == null || typeof(JsonStr) == "undefined" || JsonStr == "null" || JsonStr == "")
	{
		alert("没有需要保存的热区信息！");		// 如果用户修改热区时将热区删除，而没有添加新的热区给PageTag，则不会执行热区修改操作，将空的热区存储到数据库 [ ChengKang 2014-07-24 ]
		return false;
	}
	
	JsonStr = JsonStr.substring(1,JsonStr.length-1);	// 去掉首尾的双引号 [ ChengKang 2014-08-21 ]
	//JsonStr = JsonStr.replace(/\\/g, "");					// 将所有的反斜杠去掉 [ ChengKang 2014-08-21 ]
	//var JsonSetting = JSON.parse(JsonStr);				// 转换为JSON对象
	
	var action =CONTEXT_PATH + "/okcsys/page/pageSetting";
	
	$.post(action, {
		"PageTagId" : PageTagId,
		"SettingJsonStr" : JsonStr
	}, function(TempData)
	{
		if (typeof (TempData.message) == 'undefined')
		{
			//self.tip('error', TempData);
			return false;
		}
		if (TempData.message.success == true)
		{
			window.localStorage.clear();
			
			loadData(layoutid);						// 刷新数据
			tip('success', '热区修改成功');
			alert("热区修改成功");
		}
		else
		{
			tip('error', TempData.message.msg);
		}
	});
}

function removePagetag(tagid, layoutid) { 
	 if(confirm('删除数据不可恢复，你确认要删除吗?')) {

		 //删除数据库记录
		 $.post(CONTEXT_PATH + "/okcsys/page/pagetag/delete/" + tagid,function(data){
//			 console.log(data);
			 if(data.success==true) {
				 loadData(layoutid);						// 刷新数据
				 alert('删除成功');
			 } else {
				 tip('error',"删除失败");
				 alert('删除失败');
			 }
		 });
	 }
};
//展示数据列表
function showTagList(obj){
	var table = $("<table id='tg'></table>");
	$("#pagedatalist").append($(table));
	var pagelayoutuid = $(obj).data("layoutid");
	if(typeof(pagelayoutuid)=='undefined'||pagelayoutuid==""){
		return;
	}
	// 生成属性列表
	$('#tg').treegrid({
				url : CONTEXT_PATH +"/okcsys/page/getpagetag/"+pagelayoutuid,
				treeField : 'tagid',
				idField : 'pagetagid',
				method: 'post',
				height: 300,
	            lines: true,
				columns : [[{
							title : '测点ID',
							field : 'tagid',
							width : 150,
							formatter: function(value, rowData, rowIndex) {
								if(rowData._parentId == '') {
						        	return "<input type='checkbox' name='tagbox' value='" + rowData.pagetagid + "' class='check_grid' />" + rowData.tagid;
								} else {
									return rowData.tagid;
								}
					        }
						}, {
							title : '测点名称',
							field : 'tagname',
							width : 150
						}, {
							title : '标签名称',
							field : 'label',
							width : 150
						}, {
							title : '计量单位',
							field : 'measureunitid',
							width : 80
						}, {
							title : '控件类型1',
							field : 'controlid',
							width : 80
						}, {
							title : '控件类型2',
							field : 'controlid2',
							width : 80
						}, {
							title : '控件类型3',
							field : 'controlid3',
							width : 80
						}, {
							title : '值类型',
							field : 'tagtypedDesc',
							width : 80
						}, {
							title : '所在位置',
							field : 'coordinate',
							width : 80
						}, {
							title : '操作',
							field : 'end',
							width : 150,
							formatter : function(value, rowData, rowIndex)
							{
								var returnStr = "";
								// 对于所有的PageTag或属性，都有编辑和删除操作 [ ChengKang 2014-08-19 ]
								returnStr += "<a href='javascript:removePagetag(\""+rowData.pagetagid+"\",\""+ rowData.layoutid +"\");' style='color: blue;'>删除</a>&nbsp;&nbsp;";
								// 只有tagType为1（表示热区）并且是PageTag而不是属性（rowData._parentId）的行，才有热区和保存操作 [ ChengKang 2014-08-19 ]
								if(rowData.controlid3=="WWWW")
								{
									// 将rowData中的Setting转换成能够给JS函数作为参数传递的String [ ChengKang 2014-08-19 ]
									var Setting = JSON.stringify(rowData.setting);
									returnStr += "<a href='javascript:btnEditDrawMap(\"" + rowData.layoutid +"\", \"" + rowData.pagetagid +"\", \"" + rowData.tagtype +"\", " + Setting + ");' style='color: blue;'>热区</a>&nbsp;&nbsp;"
								}
								return returnStr;
							}
						}]],
				onLoadSuccess: function(row, data) { // 加载成功后收缩列表
					$('#tg').treegrid('collapseAll');
					$(".tree-icon,.tree-file").removeClass("tree-icon tree-file"); // 去掉结点前面的文件及文件夹小图标
					$(".tree-icon,.tree-folder").removeClass("tree-icon tree-folder tree-folder-open tree-folder-closed"); // 去掉结点前面的文件及文件夹小图标
                }
			});
}

function tip(type,msg) {
	 $.pnotify({
	        title: '操作提示',
	        text: msg,
	        type: type
	    });
};