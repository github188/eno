var zTreeObj;
var zTreeSetting = {
	                   "check":{
	                   	       "enable":true,
	                   	       "chkStyle":"checkbox",
	                   	       "chkboxType":{"Y":"ps","N":"ps"}
	                   	       },
	                    "data":{
                             "simpleData": {
                                            "enable":true,
                                            "idKey":"id",
                                            "pIdKey":"parent",
                                            "rootPId":""
                                           }
                             },
                      "async":{
                      	     "enable":true,
                      	     "url":baseurl+'/pattern/getLocalDevices',
                      	     "autoParam":["id=location"],
                      	     "dataFilter":deviceToZTreeData
                              },
                      "callback":{
                             "beforeExpand":reLoadDevice
                      }               
	                 };
	                 
var selectedDeviceList = [];

function createOneItemForOneMode()
{
	var str = "<div id=\"create_mode_item\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">创建模式信息</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
   
  str = str + "<div id=\"tree\" class=\"ztree\"></div>"+"\n";
  str = str + "<div class=\"separation\">";
  str = str + "<div class=\"arrow_right_button\" onclick=\"javascript:refreshDevicePool();\"></div>";
  str = str + "</div>";
  str = str + "<div class=\"device_pool\">"+"\n";
  str = str + "<div style=\"margin:10px 0px 20px 10px\">您当前选择的设备:</div>";
  str = str + "<ul>";
  str = str + "</ul>";
  str = str + "</div>"+"\n";
  
  str = str + "<div class=\"mode_item_info\">"+"\n";
    
  for(var i=0;i<property.length;i++)
  {
  	if (property[i].type == "offset")
    {
    	str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
    	str = str + "<br/>"+"\n";
    	str = str + "<select class=\"" +property[i].code +"\">";
    	str = str + "<option value=\"-\">提前</option>";
    	str = str + "<option value=\"\">延后</option>";
    	str = str + "</select>";
    	str = str + "<br/>"+"\n";
    	str = str + "<br/>"+"\n";
      str = str + "<input type=\"input\" class=\"" +property[i].code +"\" value=\"\" />"+property[i].measurementUnit+"\n";
      str = str + "<br/>"+"\n";
      str = str + "<br/>"+"\n";
      str = str + "<br/>"+"\n";
      str = str + "<br/>"+"\n";
    }
    else if (property[i].type == "onOff")
    {
    	str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
    	str = str + "<br/>"+"\n";
      str = str + "<div class=\""+ property[i].code +"\">ON</div>点击切换"+"\n";
      str = str + "<br/>"+"\n";
      str = str + "<br/>"+"\n";
    }
  }
    
  str = str + "</div>"+"\n";
  
  str = str +	"</div>"+"\n";
  
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:createTimeModeItem();\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "</div>"+"\n";
  
  $('#mask').html('');
  $('#mask').html(str);
  
  $('#create_mode_item').css("width","1400px");
  $('#create_mode_item').css("height","920px");
  $('#create_mode_item').css("margin-left","260px");
  $('#create_mode_item').css("margin-top","80px");
  $('#create_mode_item >.body').css('width','1300px');
  $('#create_mode_item >.body').css('height','820px');
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  });
  
  $('#create_mode_item .onOff').on('click',function(event){
  	if ($(this).html() == "ON")
  	{
  	  $(this).html("OFF");
  	  $(this).css("background-color","#666666");
  	}
  	else if ($(this).html() == "OFF")
    {
  	  $(this).html("ON");
  	  $(this).removeAttr("style");  
  	}
  	else
  		return;
  });
  
  var area_list = getAreaList();
  var system_id = getSystemId();
	zTreeSetting.async.otherParam = {systemType:system_id,classType:"",siteId:""};
  zTreeObj = $.fn.zTree.init($("#tree"), zTreeSetting, area_list);
	var nodes = zTreeObj.transformToArray(zTreeObj.getNodes());
	console.log("nodes count="+nodes.length);
	for (var i=0;i<nodes.length;i++)
	{
		nodes[i].zAsync = false;
	}
	
  $('#mask').show();
  $("#create_mode_item").draggable({cancel:".body"});
}

function reLoadDevice(treeId,treeNode)
{
	if (treeNode.zAsync == true)
	  return;
	zTreeObj.reAsyncChildNodes(treeNode,"norefresh",false);
}

function addNewTimeModeItem(param)
{
  var line = $(".mode_table >table >tbody >tr").length + 1;
	var twos = "even";
	if ((line)%2 == 0)
	    twos = "odd";
	  else
	  	twos = "even";
	var tr = '<tr id="mode_item_'+param.id+'\" class=\"'+twos+'\" onclick=\"javascript:choiceModeItem(event)\">'+'\n';
	var newItem = sortField(param.item);  //不是所有返回的数据都要展现在表格中，展现是也要按照顺序展现
	for (var i=0;i<newItem.length;i++)
	{
		if (newItem[i].type=="offset")
		{
		  if (parseInt(newItem[i].value) > 0)
		    tr = tr +'<td class="'+newItem[i].code+'">延后'+newItem[i].value+'分钟</td>'+'\n';
		  else if (parseInt(newItem[i].value) < 0)
		  	tr = tr +'<td class="'+newItem[i].code+'">提前'+Math.abs(parseInt(newItem[i].value))+'分钟</td>'+'\n';
		  else
		  	tr = tr +'<td class="'+newItem[i].code+'">无偏移</td>'+'\n';	  
		}
		else if (newItem[i].type=="onOff")
		{	
		  tr = tr +'<td class="'+newItem[i].code+'">'+((newItem[i].value=="1")?"ON":"OFF")+'</td>'+'\n';
		}
		else  	 
		  tr = tr +'<td class="'+newItem[i].code+'">'+newItem[i].value+'</td>'+'\n';
	}	  
	tr = tr + '</tr>'+'\n';
	
	$(".mode_table >table >tbody").append(tr);
  
	//获取设备信息，绑定到最后一行，用于编辑的时候，显示在界面上
	for (var m=0;m<param.item.length;m++)
	{
	  if (param.item[m].code=="deviceList")
	  {
	    $(".mode_table >table >tbody tr:last").data("deviceList",param.item[m].value);
      break;
    }  
  }
}

function createTimeModeItem()
{
	var localItemjson = "[";
	var itemjson = "[";
	var tempValue = "";
	var tempCode = "";
	var tempType = "";
	
	for (var i=0;i<property.length;i++)
	{
		if (property[i].type=="deviceTree")
		{
			refreshSelectedDeviceList();
	    tempValue = getDeviceNameList();
      if(tempValue=="" )
        tempValue = "所有设备";
	    localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"string"},';
	
	    //将设备信息放入localItemjson，用于创建成功后，将设备绑定到最后一行
	    localItemjson = localItemjson + '{"code":"deviceList","value":'+getDeviceListString()+',"type":"array"},';
		}
		else if (property[i].type=="offset")
		{
			tempCode = property[i].code;
			tempValue = $(".mode_item_info >input."+property[i].code).val();
			tempType = "offset";
			if (tempValue =="")
			  tempValue = "0";
			if (! isNumber(tempValue))
			{
				showMessageInDialog("请输入"+property[i].name+"!","error");
		    return;
			}
			var tempValue3 = $(".mode_item_info >select."+property[i].code).val();
			if (tempValue !="0")
		  {	
			  localItemjson = localItemjson + '{"code":"'+ tempCode +'","value":"'+tempValue3+tempValue+'","type":"'+ tempType+'"},';
			  itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue3+tempValue+'","type":"'+ tempType+'"},';
			}
			else
			{	
				localItemjson = localItemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';
				itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},'; 
			}	 
		}
		else if (property[i].type=="onOff")
		{
			tempCode = property[i].code;
			tempType = "onOff";
			
			tempValue = $(".mode_item_info >."+property[i].code).html();
			tempValue = (tempValue=="ON")?"1":"0";  
						
			localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"'+tempType+'"},';
			itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';
		}		
	}
	
	localItemjson =  localItemjson.substring(0,localItemjson.length-1); // 	去掉最后的逗号
	localItemjson = localItemjson + "]";
	
	itemjson =  itemjson.substring(0,itemjson.length-1);  //去掉最后的逗号
	itemjson = itemjson + "]";
	
  itemjson=transferUcPattern(current_time_mode_id,"",itemjson);

	var url = baseurl+'/pattern/saveNoTimePatternItem';
	var params ={patternAction:itemjson};
	$.ajax({url:url,type:'POST',dataType:'json',async:true,data:params,success:function(data){
    if ((data.success == true)&&(data.patternId !=null))
    {
    	showMessageInDialog('添加模式参数记录成功!','tip');
    	localItemjson = '{"id":"'+data.patternId+'","item":'+localItemjson+'}';
    	var param = eval('('+localItemjson+')');
     	addNewTimeModeItem(param);
    	resetScrollBar();
    	$(".mode_table >table >tbody >tr :last").trigger('click',['Event']);
    	var t=setTimeout('hideDialog()',1500);
    }  
    else
    {
    	showMessageInDialog("添加模式参数记录失败,请稍后再试","error");
    	var t=setTimeout('hideDialog()',1500);
    }
  }
  }
  );
}

function editOneItemForOneMode()
{
	if ($("#mode_item_"+current_time_mode_item_id).length ==0)
	  return;
	var tds = $("#mode_item_"+current_time_mode_item_id);
	  
	var str = "<div id=\"edit_mode_item\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">编辑模式信息</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
    
  str = str + "<div id=\"tree\" class=\"ztree\"></div>"+"\n";
  str = str + "<div class=\"separation\">";
  str = str + "<div class=\"arrow_right_button\" onclick=\"javascript:refreshDevicePool();\"></div>";
  str = str + "</div>";
  str = str + "<div class=\"device_pool\">"+"\n";
  str = str + "<div style=\"margin:10px 0px 20px 10px\">您当前选择的设备:</div>";
  str = str + "<ul>";
  str = str + "</ul>";
  str = str + "</div>"+"\n";
   
  str = str + "<div class=\"mode_item_info\">"+"\n";
  var tempValue = "";
  
  for(var i=0;i<property.length;i++)
  {
  	if (property[i].type == "offset")
    {
      tempValue = tds.find("."+property[i].code).html();
      str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
    	str = str + "<br/>"+"\n";
    	str = str + "<select class=\"" +property[i].code +"\">";
    	if (tempValue.indexOf("提前") !=-1)
      	 str = str + "<option value=\"-\" selected=\"selected\">提前</option>";
      else 
      	str = str + "<option value=\"-\">提前</option>";	
      if (tempValue.indexOf("延后") !=-1)
        str = str + "<option value=\"\" selected=\"selected\">延后</option>";	
      else    
    	  str = str + "<option value=\"\">延后</option>";
    	
    	str = str + "</select>";
    	str = str + "<br/>"+"\n";
    	str = str + "<br/>"+"\n";
    	if (tempValue =="无偏移")
        str = str + "<input type=\"input\" class=\"" +property[i].code +"\" value=\"\" />"+property[i].measurementUnit+"\n";
      else
      {
      	tempValue = tempValue.replace("分钟","");
      	tempValue = tempValue.replace("提前","");
      	tempValue = tempValue.replace("延后","");
      	str = str + "<input type=\"input\" class=\"" +property[i].code +"\" value=\""+tempValue+"\" />"+property[i].measurementUnit+"\n";
      }	
            	
      str = str + "<br/>"+"\n";
      str = str + "<br/>"+"\n";
      str = str + "<br/>"+"\n";
      str = str + "<br/>"+"\n";
    }
    else if (property[i].type == "onOff")
    {
    	tempValue = tds.find("."+property[i].code).html();
    	str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
      str = str + "<div class=\""+ property[i].code +"\">"+ tempValue+"</div>点击切换"+"\n";
      str = str + "<br/>"+"\n";
      str = str + "<br/>"+"\n";
    }
  }
  
  str = str + "</div>"+"\n";
  
  str = str +	"</div>"+"\n";
  
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:saveTimeModeItem();\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "</div>"+"\n";
  
  $('#mask').html('');
  $('#mask').html(str);
  
  $('#edit_mode_item').css("width","1400px");
  $('#edit_mode_item').css("height","920px");
  $('#edit_mode_item').css("margin-left","260px");
  $('#edit_mode_item').css("margin-top","80px");
  $('#edit_mode_item >.body').css('width','1300px');
  $('#edit_mode_item >.body').css('height','820px');
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  });
  
  $('#edit_mode_item .onOff').on('click',function(event){
  	if ($(this).html() == "ON")
  	{
  	  $(this).html("OFF");
  	  $(this).css("background-color","#666666");
  	}
  	else if ($(this).html() == "OFF")
    {
  	  $(this).html("ON");
  	  $(this).removeAttr("style");  
  	}
  	else
  		return;
  });
  
  var area_list = getAreaList();
  var system_id = getSystemId();
	zTreeSetting.async.otherParam = {systemType:system_id,classType:"",siteId:""};
  zTreeObj = $.fn.zTree.init($("#tree"), zTreeSetting, area_list);
	var nodes = zTreeObj.transformToArray(zTreeObj.getNodes());
	console.log("nodes count="+nodes.length);
	for (var i=0;i<nodes.length;i++)
	{
		nodes[i].zAsync = false;
	}
	showDeviceList();
	
  $('#mask').show();
  $("#edit_mode_item").draggable({cancel:".body"});
}

function showDeviceList()
{
	var tempDeviceList = [];
	var device_li = "";
	tempDeviceList = $("#mode_item_"+current_time_mode_item_id).data("deviceList");
	for(var i=0;i<tempDeviceList.length;i++)
	{
		device_li = "<li><div class=\"device\" id=\"device_"+tempDeviceList[i].id+"\" type=\""+tempDeviceList[i].type+"\">"+tempDeviceList[i].name+"</div></li>"
		$('.dialog .body .device_pool >ul').append(device_li);
	}
}

function saveTimeModeItem()
{
	var localItemjson = "[";
	var itemjson = "[";
	var tempValue = "";
	var tempCode = "";
	var tempType = "";
	for (var i=0;i<property.length;i++)
	{
		if (property[i].type=="deviceTree")
		{
			refreshSelectedDeviceList();
	    tempValue = getDeviceNameList();
      if(tempValue=="" )
        tempValue = "所有设备";
	    localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"string"},';
	
	    //将设备信息放入localItemjson，用于编辑成功后，将设备绑定到最后一行
	    localItemjson = localItemjson + '{"code":"deviceList","value":'+getDeviceListString()+',"type":"array"},';
		}
		else if (property[i].type=="offset")
		{
			tempCode = property[i].code;
			tempValue = $(".mode_item_info >input."+property[i].code).val();
			if (tempValue =="")
			  tempValue = "0"; 
			tempType = "offset";
			if (! isNumber(tempValue))
			{
				showMessageInDialog("请输入"+property[i].name+"!","error");
		    return;
			}
			var tempValue2 = $(".mode_item_info >select."+property[i].code+">option:selected").html();
			
			if (parseInt(tempValue) ==0)
			  localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"无偏移","type":"'+tempType+'"},';
			else  
			  localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue2+tempValue+property[i].measurementUnit+'","type":"'+tempType+'"},';
			
			var tempValue3 = $(".mode_item_info >select."+property[i].code).val();
			if (tempValue !="0")
			  itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue3+tempValue+'","type":"'+ tempType+'"},';
			else
				itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';  
		}	
		else if (property[i].type=="onOff")
		{
			tempCode = property[i].code;
			tempType = "onOff";
			
			tempValue = $(".mode_item_info >."+property[i].code).html();
			localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"onOff"},';
		  
		  tempValue = (tempValue=="ON")?"1":"0"; 
				
		  itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';
		}
	}
	
	localItemjson =  localItemjson.substring(0,localItemjson.length-1);
	localItemjson = localItemjson + "]";
	
	itemjson =  itemjson.substring(0,itemjson.length-1);
	itemjson= itemjson + "]";
  
  itemjson=transferUcPattern(current_time_mode_id,current_time_mode_item_id,itemjson);

	var url = baseurl+'/pattern/saveTimePatternItem';
  var params ={patternAction:itemjson};
	  $.ajax({url:url,type:'POST', dataType:'json',async:true,data:params,success:function(data){
    if (data.success == true)
    {
    	showMessageInDialog("保存成功!","tip");
    	
    	localItemjson = '{"id":"'+current_time_mode_item_id+'","item":'+localItemjson+'}';
    	var param = eval('('+localItemjson+')');
    	modifyTimeModeItem(param);
    	var t=setTimeout('hideDialog()',1500);
    }
    else
    {
    	showMessageInDialog("保存失败,请稍后再试","error");
    	var t=setTimeout('hideDialog()',1500);
    }	
  }
  }
  );
}

function modifyTimeModeItem(param)
{
	var tr = $("#mode_item_"+current_time_mode_item_id);
	var items = param.item;
	var tempValue = "";
	for (var i=0;i<property.length;i++)
	{
		tempValue = "";
		for (var j=0;j<items.length;j++)
		{
			if (property[i].code == items[j].code)
			{
			  tempValue = items[j].value;
			  break;
			}  
		}
		tr.find("."+property[i].code).html(tempValue);
	}
  
  //获取设备信息, 绑定到最后一行
	for (var m=0;m<param.item.length;m++)
	{
		if (param.item[m].code=="deviceList")
		{
			tr.data("deviceList",param.item[m].value); 
			break;
		}
	}
}

function transferUcPattern(patternId,actionId,json){
	
	  var modeList = eval('('+json+')');
    var actionStr="";
    if(actionId!=""){
        actionStr='"id":"'+actionId+'",';
    }
    var paramStr='';
    var deviceStr='';
    var offsetTime='';
    var dbCode = "";  
    var ucPattern='{"id":"'+patternId+'"}';
    var sum=modeList.length;
    for(var i=0;i<sum;i++)
    {
        if(modeList[i].code=='offset'){
            var offset=modeList[i].value;
            actionStr+='"offsetTime":"'+offset+'",';
        }
        dbCode = getDbCodeFromCode(modeList[i].code);
        if(dbCode!=""){
            paramStr+='{"paramName":"'+dbCode+'","paramValue":"'+modeList[i].value+'"},';
        }
    }
    paramStr=paramStr.substring(0,paramStr.length-1);
    
    //设备
    if(selectedDeviceList.length>0)
		{
      for (var j=0;j<selectedDeviceList.length;j++)
      {
         if (j==selectedDeviceList.length-1)
           deviceStr+='{"startTime":"","endTime":"","strategyId":"","selectType":"'+selectedDeviceList[j].type+'","name":"'+selectedDeviceList[j].name+'","device":"'+selectedDeviceList[j].id+'"'+',"ucRunParams":['+paramStr+']}';
         else
           deviceStr+='{"startTime":"","endTime":"","strategyId":"","selectType":"'+selectedDeviceList[j].type+'","name":"'+selectedDeviceList[j].name+'","device":"'+selectedDeviceList[j].id+'"'+',"ucRunParams":['+paramStr+']},';
      }
    }
		else
		{
		  deviceStr+='{"startTime":"","endTime":"","strategyId":"","selectType":"'+'A'+'","name":"所有设备","device":"","ucRunParams":['+paramStr+']}';
	  }
    
    var data='{'+actionStr+'"ucPattern":'+ucPattern+',"ucDevicePatterns":['+deviceStr+']}';
    
    return data;
}

function transferUcPatternToItem(data)
{
	var modeItemList = new Array();
	var modeItem = null;
	var modeli = null;
	var ucPatternActions = data.ucPatternActions;
	var ucRunParams = null;
	
	var deviceName = "";
	var comma = ",";
	var codeType = null;
	var tempDevice = null;
	var tempDevieList = [];
	
	for (var i=0;i<ucPatternActions.length;i++)
	{
		modeItem = {"id":"","item":[]};
		
		modeItem.id = ucPatternActions[i].id;
		
		modeli = {"code":"offset","value":ucPatternActions[i].offsetTime,"type":"offset"};
		modeItem.item.push(modeli);
		
		deviceName = "";
		tempDeviceList = [];
		comma = ",";
		for (var j=0;j<ucPatternActions[i].ucDevicePatterns.length;j++)
		{
			if (j==ucPatternActions[i].ucDevicePatterns.length-1)
			  comma = "";
			if (ucPatternActions[i].ucDevicePatterns[j].selectType != "A")
			{  
			  tempDevice = {"id":ucPatternActions[i].ucDevicePatterns[j].device,"name":ucPatternActions[i].ucDevicePatterns[j].name,"type":ucPatternActions[i].ucDevicePatterns[j].selectType};
			  tempDeviceList.push(tempDevice);	
			}
			deviceName = deviceName +""+ucPatternActions[i].ucDevicePatterns[j].name+comma;
		}
		
		modeli = {"code":"deviceName","value":deviceName,"type":"string"};
		modeItem.item.push(modeli);
		
		modeli = {"code":"deviceList","value":tempDeviceList,"type":"array"};
		modeItem.item.push(modeli);
		
		ucRunParams = ucPatternActions[i].ucDevicePatterns[0].ucRunParams;
		for(var k=0;k<ucRunParams.length;k++)
		{
		  codeType = getCodeAndTypeFromDbCode(ucRunParams[k].paramName)
		  modeli = {"code":codeType.code,"value":ucRunParams[k].paramValue,"type":codeType.type};
		  modeItem.item.push(modeli);
		}
		
		modeItemList.push(modeItem);
	}
	return modeItemList;
}

function getDeviceNameList()
{
	var ccstr = "";
	var slid = ",";
	for (var i=0;i<selectedDeviceList.length;i++)
	{
	   ccstr = ccstr + selectedDeviceList[i].name+slid;
	}
  ccstr = ccstr.substring(0,ccstr.length-1);
  console.log("deviceNameList="+ccstr);
  return ccstr;
}

function refreshSelectedDeviceList()
{
	var deviceId = "";
	var deviceName = "";
	var deviceType = "";
	
	selectedDeviceList = [];
	$('.dialog .body .device_pool > ul > li > div').each(function(index){
	  
	  deviceId = $(this).attr('id').replace('device_','');
	  deviceName = 	$(this).text();
	  deviceType = $(this).attr('type');
	  selectedDeviceList.push({"id":deviceId,"name":deviceName,"type":deviceType});
	
	}
	); 
}

function getAreaList()
{
	var list = [];
	var area = null;
	var url = baseurl+'/pattern/getLocalTree';
	var system_id = getSystemId();
	var params ={systemId:system_id};
	$.ajax({url:url,type:'GET',dataType:'json',async:false,data:params,success:function(data){ 	     
    for (var i=0;i<data.length;i++)
    {
       area = {"id":data[i].id,"name":data[i].text,"children":data[i].children,"parent":data[i].parentid,"isParent":true,"type":"S"};
       list.push(area);     
    }     
	}
  }
  );
  return list;   
}
function deviceToZTreeData(treeId,parentNode,responseData)
{
	var list = [];
	if (responseData)
	{
		for(var i =0; i < responseData.length; i++)
		{
       if ((parentNode.checked == true)&&(parentNode.getCheckStatus().half == false))
         list.push({"id":responseData[i].assetnum,"name":responseData[i].description,"children":[],"parent":parentNode.id,"type":"D","checked":true});
       else
         list.push({"id":responseData[i].assetnum,"name":responseData[i].description,"children":[],"parent":parentNode.id,"type":"D"});
	  }
	}
	return list;
}

function refreshDevicePool()
{
	$('.dialog .body .device_pool >ul').html('');
	var device_li = "";
	var device_name = "";
	var nodes = zTreeObj.getCheckedNodes(true);
	
	for (var i=0;i<nodes.length;i++)
	{
		if (nodes[i].getParentNode() !=null)
		{
		  if ((nodes[i].getCheckStatus().half == false)&&(nodes[i].getParentNode().getCheckStatus().half == true))
		  {
		 	  console.log("id:"+nodes[i].id+" name:"+nodes[i].name+" type:"+nodes[i].type);
		 	  device_name = nodes[i].name;
		 	  if (nodes[i].type=="D")
		 	    device_name = nodes[i].getParentNode().name+"的"+device_name;
		 	  else if (nodes[i].type=="S")
		 	  	device_name = device_name + "的所有设备";
		 	  device_li = "<li><div class=\"device\" id=\"device_"+nodes[i].id+"\" type=\""+nodes[i].type+"\">"+device_name+"</div></li>"
		 	  $('.dialog .body .device_pool >ul').append(device_li);
	    }
	  }
	  else
	  {
	  	if (nodes[i].getCheckStatus().half == false)
	  	{
	  	  device_li = "<li><div class=\"device\" id=\"device_"+nodes[i].id+"\" type=\""+nodes[i].type+"\">"+nodes[i].name+"</div></li>"
		 	  $('.dialog .body .device_pool >ul').append(device_li);
	    }
	  }	  
	}
}
	
function getDeviceListString()
{
	var str = '[';
	for (var i=0;i<selectedDeviceList.length;i++)
	{
		str = str + '{';
		str = str + '"id":"'+selectedDeviceList[i].id+'","name":"'+selectedDeviceList[i].name+'","type":"'+selectedDeviceList[i].type+'"';
		if (i!=selectedDeviceList.length-1)
		  str = str + '},';
		else
			str = str + '}';  
	}
	str = str + ']';
	return str;
}	