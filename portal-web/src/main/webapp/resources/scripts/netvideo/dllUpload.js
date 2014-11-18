function getFiledata(zNodes){
	var setting = {
		view: {
			selectedMulti: true
		},
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeRemove: beforeRemove,
			beforeRename: beforeRename,
			onRename:onRename,
			onRemove:onRemove
		}
	};

	function beforeRemove(treeId, treeNode) {//删除前提示
		if(treeNode.isParent){
			if(typeof(treeNode.children)!='undefined'&&treeNode.children.length>0){
				return confirm("确认删除选中文件夹及其下的子文件吗？");
			}else{
				return confirm("确认删除选中空文件夹吗？");
			}
		}else{
			return confirm("确认删除文件" + treeNode.name + " 吗？");
		}
	}
	function beforeRename(treeId, treeNode, newName) {//重命名前执行，名称不能为“”
		if(treeNode.isParent){
			if (newName.length == 0) {
				alert("节点名称不能为空.");
				var zTree = $.fn.zTree.getZTreeObj("group_tree");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			return true;
		}else{
			alert("请选择一个文件夹");
			return false;
		}
	}
	function onRename(event, treeId, treeNode, isCancel){
		if(typeof(treeNode.path)=='undefined'){
			$.ajax({
				url:CONTEXT_PATH+"/okcsys/video/process/add",
				data:"name="+treeNode.name+"&path="+treeNode.getParentNode().path,
				type:"post",
				success:function(data){
					$.fn.zTree.destroy("file_tree");
					getFiledata(processToArray(data));
				}
			});
		}else{
			$.ajax({
				url:CONTEXT_PATH+"/okcsys/video/process/modify",
				data:"name="+treeNode.name+"&path="+treeNode.path,
				type:"post",
				success:function(data){
					$.fn.zTree.destroy("file_tree");
					getFiledata(processToArray(data));
				}
			});
		}
	}
	function onRemove(event, treeId, treeNode){
		$.ajax({
			url:CONTEXT_PATH+"/okcsys/video/process/remove",
			data:"name="+treeNode.name+"&path="+treeNode.path,
			type:"post",
			success:function(data){
				$.fn.zTree.destroy("file_tree");
				getFiledata(processToArray(data));
			}
		});
	}
	
	$(document).ready(function(){
		$.fn.zTree.init($("#file_tree"), setting, zNodes);//按配置初始化
	});
}
//添加文件夹
function add_dir(e) {//添加（兄弟/子）节点
	var zTree = $.fn.zTree.getZTreeObj("file_tree");
	var nodes = zTree.getSelectedNodes();
	if(nodes.length>1){
		alert("该操作只能作用于一个文件夹！！");
		return;
	}
	var treeNode = nodes[0];
	if(treeNode){
		if (treeNode.isParent) {
			treeNode = zTree.addNodes(treeNode, {id:1000, pId:treeNode.id,open:true,isParent:true, name:"空文件夹"});
		} else {
			alert("请选中一个文件夹！！");
		}
		zTree.editName(treeNode[0]);
	}else{
		alert("请选中一个文件夹！！");
	}

};
//修改文件名称
function edit_file() {
	var zTree = $.fn.zTree.getZTreeObj("file_tree"),
	nodes = zTree.getSelectedNodes(),
	treeNode = nodes[0];
	if(treeNode.length>0){
		alert("该操作只能作用于一个文件夹或文件！！");
		return;
	}
	if (nodes.length == 0) {
		alert("请先选择一个文件夹或文件");
		return;
	}
	zTree.editName(treeNode);
};
//删除文件夹或文件
function remove_file(e) {//移除一个节点方法
	var zTree = $.fn.zTree.getZTreeObj("file_tree");
	var nodes = zTree.getSelectedNodes();
	if(nodes.length>1){
		alert("该操作只能作用于一个文件或文件夹！！");
		return;
	}
	var treeNode = nodes[0];
	if (nodes.length == 0) {
		alert("请先选择一个节点");
		return;
	}else if(nodes[0].getParentNode()==null){
		alert("根节点不可删除！！");
		return;
	}
	zTree.removeNode(treeNode, true);
};
//属性扩展和收缩
function expandNode_file(e){//展开/收缩方法
	var zTree = $.fn.zTree.getZTreeObj("file_tree"),
	type = e.data.type;
	if (type == "expandAll") {
		zTree.expandAll(true);
	} else if (type == "collapseAll") {
		zTree.expandAll(false);
	} else {
		for (var i=0, l=nodes.length; i<l; i++) {
			zTree.setting.view.fontCss = {};
			if (type == "expand") {
				zTree.expandNode(nodes[i], true, null, null);
			} else if (type == "collapse") {
				zTree.expandNode(nodes[i], false, null, null);
			} else if (type == "toggle") {
				zTree.expandNode(nodes[i], null, null, null);
			} else if (type == "expandSon") {
				zTree.expandNode(nodes[i], true, true, null);
			} else if (type == "collapseSon") {
				zTree.expandNode(nodes[i], false, true, null);
			}
		}
	}
}
$("#remove_file").bind("click", remove_file);
$("#add_file").bind("click", add_dir);
$("#edit_file").bind("click", edit_file);
$("#expandNodes_file").bind("click",{type:"expandAll"}, expandNode_file);
$("#collapseNodes_file").bind("click",{type:"collapseAll"}, expandNode_file);
function getFilesdata(){
	//获取驱动文件夹内容数组
	$.ajax({
		url:CONTEXT_PATH+'/okcsys/video/get/files',
		type:"post",
		async:false,
		success:function(data){
			getFiledata(processToArray(data));
		}
	});
}
function processToArray(data){
	var files = new Array();
	for(var i=0;i<data.length;i++){
		var f_sin = data[i].split("*");
		if(f_sin[3]=="dire"){
			files.push({
				"id":f_sin[0],
				"pId":f_sin[1],
				"name":f_sin[2],
				"type":f_sin[3],
				"path":f_sin[4],
				"open":true,
				"isParent":true
			});
		}else{
			files.push({
				"id":f_sin[0],
				"pId":f_sin[1],
				"name":f_sin[2],
				"type":f_sin[3],
				"path":f_sin[4]
			});
		}
	}
	return files;
}