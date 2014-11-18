var did = -1; // 存放列表选项的id
var atrr = null; // 存放列表选项的属性
var desc = null; // 存放列表选项的属性中文
var attr_width = '600px'; // 属性下拉列表的长度
var attr_height = '30px'; // 属性下拉列表的高度
var modul_width = '216px'; // 模块下拉列表的长度

$(function() {
			$("#modul").val(""); // 描述置空
			// 初始化，往【模块】下拉列表中填充数据
			$.getJSON(CONTEXT_PATH + '/okcsys/devicelist/getModulList',
					function(data) {
						$("#classid").empty();
						for (var i = 0; i < data.length; i++) {
							if (i == 0) {
								$("#classid").append("<option value=''>请选择...</option>");
							}
							$("#classid").append("<option value='" + data[i][0]+ "'>" + data[i][1] + "(" + data[i][0] + ")</option>");
						}
					});

			// 模块切换
			$("#classid").live("change", function() {
						atrr = null;
						$("#attribute").select2('val', "");
						getListByClassId($(this).val());
					});

			// 保存按钮
			$("#buildAssetAttrInfo").live("click", function() {
						var desc = [], attr = [];
						var obj = $("#attribute").select2('data');
						for (var i = 0; i < obj.length; i++) {
							attr.push(obj[i].id);
							desc.push(obj[i].text);
						}
						// 提交表单
						$('#devicelist').ajaxSubmit({
									url : CONTEXT_PATH + '/okcsys/devicelist/save',
									data : {
										id : did,
										attr : attr.join(),
										description : desc.join()
									},
									method : 'POST',
									success : function(data) {
										var dat = eval(data);
										alert(dat.message.msg);
										$('#newt').datagrid('load', {
													code : '01'
												});
									}
								});
						return false; // 为了防止普通浏览器进行表单提交和产生页面导航（防止页面刷新？）返回false
					});

			// 属性使用select2插件
			$("#attribute").select2({ width : attr_width, height : attr_height });
			$("#classid").select2({ width : modul_width, height : attr_height });

			$('#newt').datagrid({
				url : CONTEXT_PATH + "/okcsys/devicelist/listAll",
				rownumbers : true,
				pagination : true,
				idField : 'id',
				pagination : true,
				pageSize : 6,
				pageList : [6, 12, 20],
				columns : [[{
					title : 'ID',
					field : 'id',
					width : 50,
					formatter : function(value, rowData, rowIndex) {
						if (rowData.id == '') {
							return "<input type='checkbox' name='tagbox' value='"
									+ rowData.id
									+ "' class='check_grid' />"
									+ rowData.id;
						} else {
							return rowData.id;
						}
					}
				}, {
					title : '描述',
					field : 'modul',
					width : 240
				}, {
					title : '模块',
					field : 'classiddesc',
					width : 90
				}, {
					title : '属性描述',
					field : 'description',
					width : 900
				}, {
					title : '操作',
					field : 'action',
					width : 100,
					formatter : function(value, rowData, rowIndex) {
						var returnStr = "";
						returnStr += "<a href='javascript:modify(\""
								+ rowData.attribute
								+ "\",\""
								+ rowData.description
								+ "\",\""
								+ rowData.classid
								+ "\",\""
								+ rowData.modul
								+ "\",\""
								+ rowData.id
								+ "\");' style='color: blue;'>修改</a>&nbsp;&nbsp;";
						returnStr += "<a href='javascript:removestrategy(\""
								+ rowData.id
								+ "\");' style='color: blue;'>删除</a>&nbsp;&nbsp;";
						return returnStr;
					}
				}]]
			});
		});

/**
 * 根据parent找对应的子级
 */
function getclassid(parent) {
	// 往资产分类中填充数据
	$.getJSON(CONTEXT_PATH + '/okcsys/devicelist/getAttributeByClassstructureid?classstructureid=' + parent,
			function(data) {
				$("#attribute").empty();
				for (var i = 0; i < data.length; i++) {
					$("#attribute").append("<option value='"
									+ data[i].classificationid + "'>"
									+ data[i].description + "</option>");
				}
				
				$("#attribute").select2({
							width : attr_width,
							height : attr_height
						});
			});
}

/**
 * 根据分类id找对应的子级
 */
function getListByClassId(classId) {
	// 往资产分类中填充数据
	$.getJSON(CONTEXT_PATH + '/asset/attribute/list?classstructureid=' + classId, function(data) {
				$("#attribute").empty();
				for (var i = 0; i < data.length; i++) {
					$("#attribute").append("<option value='"
							+ data[i].assetattrid + "'>" + data[i].description + "(" + data[i].assetattrid + ")"
							+ "</option>");
				}

				// 修改时，下拉列表赋值
				if (atrr != null && desc != null) {
					var index = atrr.indexOf(",");
					if (index < 0) {
						$("#attribute").select2("val", atrr);
					} else {
						var ay = new Array(), ds = new Array(), dt = new Array();
						ay = atrr.split(",");
						ds = desc.split(",");
						for (var n = 0; n < ay.length; n++) {
							dt.push({
										id : ay[n],
										text : ds[n]
									});
						}
						$("#attribute").select2("data", dt);
					}
				}
				return false;
			});
}
/**
 * 修改功能
 * 
 * @param {} attribute
 * @param {} description
 * @param {} id
 * @param {} system
 * @param {} classiddesc
 * @param {} modul
 */
function modify(attribute, description, classid, modul, id) {
	atrr = attribute;
	desc = description;
	did = id;
	$("#modul").val(modul); // 描述
	$("#classid").select2("val", classid); // 模块下拉列表赋值
	getListByClassId(classid); // 属性栏赋值
}
/**
 * 往模块中填数据
 * 
 * @param parent
 * @param desc
 */
function getclassids(parent, desc) {
	// 往资产分类中填充数据
	$.getJSON(CONTEXT_PATH + '/asset/class/structurelist?parent=' + parent, function(data) {
			$("#classid").empty();
			for (var i = 0; i < data.length; i++) {
				if (desc == data[i].description) {
					$("#classid").append("<option value='"
							+ data[i].classificationid
							+ "'selected='selected',>"
							+ data[i].description + "</option>");
					getListByClassId($("#classid").val());
				} else {
					$("#classid").append("<option value='"
							+ data[i].classificationid + "'>"
							+ data[i].description + "</option>");
				}
			}
		});
}
/**
 * 根据id删除记录
 * 
 * @param id
 */
function removestrategy(id) {
	$.ajax({
				type : "GET",
				url : CONTEXT_PATH + "/okcsys/devicelist/delete?id=" + id,
				dataType : "text",
				success : function(data) {
					alert(data);
					$('#newt').datagrid('load', {
								code : '01'
							});
					return false;
				}
			});
}