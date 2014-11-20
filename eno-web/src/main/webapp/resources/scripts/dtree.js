/*--------------------------------------------------|
| dTree 2.05 | www.destroydrop.com/javascript/tree/ |
|---------------------------------------------------|
| Copyright (c) 2002-2003 Geir Landr?              |
|                                                   |
| This script can be used freely as long as all     |
| copyright messages are intact.                    |
|                                                   |
| Updated: 17.04.2003                               |
|--------------------------------------------------*/

// Node object
function Node(id, pid, name,icon, iconOpen, open) {
	this.id = id;
	this.pid = pid;
	this.name = name;
	this.icon = icon;
	this.iconOpen = iconOpen;
	this._io = open || false;
	this._ls = false;
	this._hc = false;
	this._ai = 0;
	this._p;
	
};

// Tree object
function dTree(objName) {
	this.config = {
		clickfolderEvent	: 'clickFolder',
		useLines				: true,
		useIcons				: true,
		closeSameLevel	: false,
		inOrder					: false,
		check:false	
	}
	this.icon = {
		root				: 'foldclose',
		folder			: 'foldclose',
		folderOpen	: 'foldopen',
		node				: 'foldclose',
		empty				: 'empty',
		line				: 'line',
		join				: 'join',
		joinBottom	: 'joinbottom',
		plus				: 'plus',
		plusBottom	: 'plusBottom',
		minus				: 'minus',
		minusBottom	: 'minusBottom',
		nlPlus			: 'nlplus',
		nlMinus			: 'nlMinus'
	};
	this.cbCollection = new Object();
	this.obj = objName;
	this.aNodes = [];
	this.aIndent = [];
	this.root = new Node(-1);
	this.selectedNode = null;
	this.selectedFound = false;
	this.completed = false;
};

// Adds a new node to the node array
dTree.prototype.add = function(id, pid, name,icon,iconOpen,open) {
	this.aNodes[this.aNodes.length] = new Node(id,pid,name,icon,iconOpen, open);
};

// Open/close all nodes
dTree.prototype.openAll = function() {
	this.oAll(true);
};
dTree.prototype.closeAll = function() {
	this.oAll(false);
};

// Outputs the tree to the page
dTree.prototype.toString = function()
 {
	var str = '<div class="dtree">\n';
	str += this.addNode(this.root);
	str += '\n</div>';
	this.selectedNode = this.getSelected();
	this.completed = true;
	return str;
};

// Creates the tree structure
dTree.prototype.addNode = function(pNode) {
	var str = '';
	var n=0;
	if (this.config.inOrder) n = pNode._ai;
	for (n; n<this.aNodes.length; n++)
	{
		if (this.aNodes[n].pid == pNode.id)
		{
			var cn = this.aNodes[n];
			cn._p = pNode;
			cn._ai = n;
			
			this.setCS(cn);
			
			if (cn._hc && !cn._io) cn._io = this.isOpen(cn.id);
			str += this.node(cn,n);
			if (cn._ls) break;
		}
		
	}
	return str;
};


//get checkbox object by id(input by client)
//added by wallimn,
dTree.prototype.co=function(id){
	if (this.cbCollection[id])return this.cbCollection[id];
	for(var n=0; n<this.aNodes.length; n++){
		if(this.aNodes[n].id==id){
			this.cbCollection[id]=document.getElementById("c"+this.obj+n);
			break;
		}
	}
	return this.cbCollection[id];
};
//get the ids of the checkbox which check status is true;
//added by wallimn, updated: 2009-02-13
dTree.prototype.getCheckedNodes=function(){
	var res = new Array();
	var cko;//checkobject
	for(var n=0; n<this.aNodes.length; n++){
		//alert("c"+this.obj+n+(document.getElementById("c"+this.obj+n).checked));
		//cache the object to improve speed when you have very,very many nodes and call this function many times in one page
		//i.e. with ajax technology
		//document.getElementById("c"+this.obj+n).checked
		cko = this.co(this.aNodes[n].id);
		if(cko.checked==true){
			res[res.length]=this.aNodes[n];
		}
	}
	return res;
}
//added by wallimn
dTree.prototype.cc=function(nodeId){
	var cs = document.getElementById("c"+this.obj+nodeId).checked;
	var n,node = this.aNodes[nodeId];
	var len =this.aNodes.length;
	for (n=0; n<len; n++) {
		//alert(this.aNodes[n].pid+"--"+this.aNodes[n].id);
		//if (this.aNodes[n].pid == node.id) {
			//if (this.aNodes[n]._io) this.nodeStatus(false, n, this.aNodes[n]._ls);
			//this.aNodes[n]._io = false;
			//document.getElementById("c"+this.obj+n).checked=cs;
			//this.cc(n);		
		//}
	}
	/*
	if(cs==false)return;
	var pid=node.pid;
	var bSearch;
	do{
		bSearch=false;
		for(n=0;n<len;n++){
			if(this.aNodes[n].id==pid){
				document.getElementById("c"+this.obj+n).checked=true;
				pid=this.aNodes[n].pid;
				bSearch= true;				
				break;
			}
		}
	}while(bSearch==true)
	*/
}

// Creates the node icon and text
dTree.prototype.node = function(node, nodeId)
 {
	var str = '\n<div class="dTreeNode">' +'\n'+ this.indent(node, nodeId);
	if (this.config.useIcons)
	{
		if (!node.icon) node.icon = (this.root.id == node.pid) ? this.icon.root : ((node._hc) ? this.icon.folder : this.icon.node);
		if (!node.iconOpen) node.iconOpen = (node._hc) ? this.icon.folderOpen : this.icon.node;
		if (this.root.id == node.pid)
		{
			node.icon = this.icon.root;
			node.iconOpen = this.icon.root;
		}
		str += '\n<div id="i' + this.obj + nodeId + '" class="'+((node._io) ? node.iconOpen : node.icon)+'"></div>';
	}
	if(this.config.check==true)
	{
		str+= '<input type="checkbox" class="cx" id="c'+  this.obj + nodeId + '" onclick="javascript:'+this.obj+'.cc('+nodeId+')"/>';
		//alert(str);
	}
	//if (node._hc && node.pid != this.root.id)
	//	str += '<a href="javascript: ' + this.obj + '.o(' + nodeId + ');" class="node">';
	str += "\n<a id=\"s" + this.obj + nodeId + "\" href=\"javascript:" + this.config.clickfolderEvent + "('"+node.id+"')\">";
	
		
	str += node.name;
	str += '</a>';
	str += '\n'+'</div>';
	
	if (node._hc)
	{
		str += '\n<div id="d' + this.obj + nodeId + '" class="clip" style="display:' + ((this.root.id == node.pid || node._io) ? 'block' : 'none') + ';">';
		str += '\n'+this.addNode(node);
		str += '\n'+'</div>';
	}
	this.aIndent.pop();
	return str;
};


// Adds the empty and line icons
dTree.prototype.indent = function(node, nodeId)
{
	var str = '';
	if (this.root.id != node.pid) {
		for (var n=0; n<this.aIndent.length; n++)
			str += '<div class="'+ ( (this.aIndent[n] == 1 && this.config.useLines) ? this.icon.line : this.icon.empty )+'"></div>';
		(node._ls) ? this.aIndent.push(0) : this.aIndent.push(1);
		if (node._hc)
		{
			str += '<div id="j' + this.obj + nodeId +'" onclick="javascript:'+this.obj + '.o(' + nodeId + ');" class="';
			if (!this.config.useLines) str += (node._io) ? this.icon.nlMinus : this.icon.nlPlus;
			else str += ( (node._io) ? ((node._ls && this.config.useLines) ? this.icon.minusBottom : this.icon.minus) : ((node._ls && this.config.useLines) ? this.icon.plusBottom : this.icon.plus ) );
			str += '"></div>';
		}
		else str += '<div class="' + ( (this.config.useLines) ? ((node._ls) ? this.icon.joinBottom : this.icon.join ) : this.icon.empty) + '"></div>\n';
	}
	return str;
};

// Checks if a node has any children and if it is the last sibling
dTree.prototype.setCS = function(node)
{
	var lastId;
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.id) node._hc = true;
		if (this.aNodes[n].pid == node.pid) lastId = this.aNodes[n].id;
	}
	if (lastId==node.id) node._ls = true;
};


// Returns the selected node
dTree.prototype.getSelected = function() {
	var sn = this.getCookie('cs' + this.obj);
	return (sn) ? sn : null;
};

// Toggle Open or close
dTree.prototype.o = function(id)
{
	var cn = this.aNodes[id];
	this.nodeStatus(!cn._io, id, cn._ls);
	cn._io = !cn._io;
	if (this.config.closeSameLevel) this.closeLevel(cn);
};

// Open or close all nodes
dTree.prototype.oAll = function(status)
{
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n]._hc && this.aNodes[n].pid != this.root.id) {
			this.nodeStatus(status, n, this.aNodes[n]._ls)
			this.aNodes[n]._io = status;
		}
	}
	if (this.config.useCookies)
	{
		 this.updateCookie();
	}	 
};

// Opens the tree to a specific node
dTree.prototype.openTo = function(nId, bSelect, bFirst)
{
	if (!bFirst) {
		for (var n=0; n<this.aNodes.length; n++) {
			if (this.aNodes[n].id == nId) {
				nId=n;
				break;
			}
		}
	}
	var cn=this.aNodes[nId];
	if (cn.pid==this.root.id || !cn._p) return;
	cn._io = true;
	if (this.completed && cn._hc) this.nodeStatus(true, cn._ai, cn._ls);
	if (this.completed && bSelect) this.s(cn._ai);
	else if (bSelect) this._sn=cn._ai;
	this.openTo(cn._p._ai, false, true);
};

// Closes all nodes on the same level as certain node
dTree.prototype.closeLevel = function(node)
{
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.pid && this.aNodes[n].id != node.id && this.aNodes[n]._hc) {
			this.nodeStatus(false, n, this.aNodes[n]._ls);
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);
		}
	}
}

// Closes all children of a node
dTree.prototype.closeAllChildren = function(node)
{
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.id && this.aNodes[n]._hc) {
			if (this.aNodes[n]._io) this.nodeStatus(false, n, this.aNodes[n]._ls);
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);		
		}
	}
}

// Change the status of a node(open or closed)
dTree.prototype.nodeStatus = function(status, id, bottom)
{
	eDiv	= document.getElementById('d' + this.obj + id);
	eJoin	= document.getElementById('j' + this.obj + id);
	if (this.config.useIcons) {
		eIcon	= document.getElementById('i' + this.obj + id);
		eIcon.className = (status) ? this.aNodes[id].iconOpen : this.aNodes[id].icon;
	}
	eJoin.className = (this.config.useLines)?
	((status)?((bottom)?this.icon.minusBottom:this.icon.minus):((bottom)?this.icon.plusBottom:this.icon.plus)):
	((status)?this.icon.nlMinus:this.icon.nlPlus);
	eDiv.style.display = (status) ? 'block': 'none';
};


// [Cookie] Clears a cookie
dTree.prototype.clearCookie = function() {
	var now = new Date();
	var yesterday = new Date(now.getTime() - 1000 * 60 * 60 * 24);
	this.setCookie('co'+this.obj, 'cookieValue', yesterday);
	this.setCookie('cs'+this.obj, 'cookieValue', yesterday);
};

// [Cookie] Sets value in a cookie
dTree.prototype.setCookie = function(cookieName, cookieValue, expires, path, domain, secure) {
	document.cookie =
		escape(cookieName) + '=' + escape(cookieValue)
		+ (expires ? '; expires=' + expires.toGMTString() : '')
		+ (path ? '; path=' + path : '')
		+ (domain ? '; domain=' + domain : '')
		+ (secure ? '; secure' : '');
};

// [Cookie] Gets a value from a cookie
dTree.prototype.getCookie = function(cookieName) {
	var cookieValue = '';
	var posName = document.cookie.indexOf(escape(cookieName) + '=');
	if (posName != -1) {
		var posValue = posName + (escape(cookieName) + '=').length;
		var endPos = document.cookie.indexOf(';', posValue);
		if (endPos != -1) cookieValue = unescape(document.cookie.substring(posValue, endPos));
		else cookieValue = unescape(document.cookie.substring(posValue));
	}
	return (cookieValue);
};

// [Cookie] Returns ids of open nodes as a string
dTree.prototype.updateCookie = function() {
	var str = '';
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n]._io && this.aNodes[n].pid != this.root.id) {
			if (str) str += '.';
			str += this.aNodes[n].id;
		}
	}
	this.setCookie('co' + this.obj, str);
};

// [Cookie] Checks if a node id is in a cookie
dTree.prototype.isOpen = function(id) {
	var aOpen = this.getCookie('co' + this.obj).split('.');
	for (var n=0; n<aOpen.length; n++)
		if (aOpen[n] == id) return true;
	return false;
};

// If Push and pop is not implemented by the browser
if (!Array.prototype.push) {
	Array.prototype.push = function array_push() {
		for(var i=0;i<arguments.length;i++)
			this[this.length]=arguments[i];
		return this.length;
	}
};
if (!Array.prototype.pop) {
	Array.prototype.pop = function array_pop() {
		lastElement = this[this.length-1];
		this.length = Math.max(this.length-1,0);
		return lastElement;
	}
};
