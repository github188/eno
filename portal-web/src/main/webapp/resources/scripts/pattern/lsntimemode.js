var selectedDeviceList = [];

function createOneItemForOneMode()
{
	var str = "<div id=\"create_mode_item\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">创建模式信息</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
   
  str = str + "<div class=\"deviceList\">";
  str = str + "<ul>";
  str = str + "</ul>";
  str = str + "</div>"+"\n";
    
  str = str + "<div class=\"mode_item_info\">"+"\n";
    
  for(var i=0;i<property.length;i++)
  {
  	if (property[i].type == "time")
  	{
  	  str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
  	  str = str + "<select class=\""+property[i].code +"\">";
  	  str = str + "<option value=\"0\">固定时间</option>";	
      str = str + "<option value=\"-90\">日出时间</option>";	
      str = str + "<option value=\"-91\">日落时间</option>";
      str = str + "</select>";
      str = str + "<div id=\"tempdatatimepicker\" class=\"input-append\">";	
  	  str = str + "<input data-format=\"hh:mm\" type=\"input\" class=\"" +property[i].code +"\" value=\"\" />"+property[i].measurementUnit+"\n";
  	  str = str + "<span class=\"add-on\">"+"\n";
      str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
      str = str + "</span>"+"\n";
      str = str + "</div>"+"\n";

  	  str = str + "<br/>"+"\n";
  	  str = str + "<br/>"+"\n";
    }
  	else if (property[i].type == "number")
    {
      str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
      str = str + "<input type=\"input\" class=\"" +property[i].code +"\" value=\"\" />"+property[i].measurementUnit+"\n";
      str = str + "<br/>"+"\n";	
      str = str + "<br/>"+"\n";	
    }
    else if (property[i].type == "offset")
    {
    	str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
    	str = str + "<select class=\"" +property[i].code +"\">";
    	str = str + "<option value=\"-\">提前</option>";
    	str = str + "<option value=\"\">延后</option>";
    	str = str + "</select>";
    	str = str + "<br/>"+"\n";
      str = str + "<input type=\"input\" class=\"" +property[i].code +"\" value=\"\" />"+property[i].measurementUnit+"\n";
      str = str + "<br/>"+"\n";
      str = str + "<br/>"+"\n";
     
    }
    else if (property[i].type == "list")
    {
      str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
      str = str + "<select class=\""+property[i].code +"\">";
      for (var j= 0;j<actionList.length;j++)
      {
        str = str + "<option value=\""+ actionList[j].code+"\">"+ actionList[j].name+"</option>";	
      }
      
      str = str + "</select>";
      str = str + "<br/>"+"\n";	
      str = str + "<br/>"+"\n";	
    }
    else if (property[i].type == "strategy")
    {
    	str = str + "<div class=\"button\" onclick=\"javascript:showChoiceStrategyDialog()\">选择策略</div>";
    	str = str + "<div class=\"now_strategy\"><span class=\"strategy_name\">当前未使用任何策略</div>";
      str = str + "<div class=\"strategyFailTip\">如果选择的策略失效，则继续采用下面的设定:</div>";
      str = str + "<br/>"+"\n";
      str = str + "<br/>"+"\n";
    }
    else if (property[i].type == "onOff")
    {
    	str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
      str = str + "<div class=\""+ property[i].code +"\">ON</div>点击切换"+"\n";
      str = str + "<br/>"+"\n";
      str = str + "<br/>"+"\n";
    }
    else if (property[i].type == "text")
    {
    	str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
      str = str + "<textarea class=\""+property[i].code+"\"></textarea>"+"\n";
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
     
  $('#create_mode_item select.time').on('change',function(event){
  	if (($(this).val() == "-90")||($(this).val() == "-91"))
  	{
  	  $(this).next().children().eq(0).attr("disabled",true); 
  	  $(this).next().children().eq(0).attr("value","");
  	}
  	else if ($(this).val() == "0")
  		$(this).next().children().eq(0).attr("disabled",false);
  	else	
  		return;
  });
  
  $('#tempdatatimepicker').datetimepicker({
      maskInput: true,           // disables the text input mask
      pickDate: false,            // disables the date picker
      pickTime: true,            // disables de time picker
      pick12HourFormat: false,   // enables the 12-hour format time picker
      pickSeconds: false         // disables seconds in the time picker
    });
      	
  var deviceClass_li = "";
  var deviceClass = getDeviceClass();
  for (var j=0;j<deviceClass.length;j++)
  {
  	deviceClass_li = deviceClass_li + "<li style=\"min-width:120px;margin-bottom:50px\"><input type=\"checkbox\" id=\"deviceClass_"+ deviceClass[j].id +"\"><span>"+deviceClass[j].name+"</span></li>"
  }
  $(".dialog .body .deviceList ul").append(deviceClass_li);
  
  $('#mask').show();
  $("#create_mode_item").draggable({cancel:".body"});
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
		if (property[i].type=="deviceTree_deviceGroup")
		{
			refreshSelectedDeviceList();
	    tempValue = getDeviceNameList();
      if(tempValue=="" )
        tempValue = "所有设备";
	    localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"string"},';
	
	    //将设备信息放入localItemjson，用于创建成功后，将设备绑定到最后一行
	    localItemjson = localItemjson + '{"code":"deviceList","value":'+getDeviceListString()+',"type":"array"},';
	  }
		else if (property[i].type=="time")
		{
			tempCode = property[i].code;
			var timeType = $(".mode_item_info >select."+property[i].code).val();
			if (timeType == "0")
			{
			  tempValue = $(".mode_item_info #tempdatatimepicker >input."+property[i].code).val();
			  if (! isTime(tempValue))
			  {
				  showMessageInDialog("请输入"+property[i].name+"!","error");
		      return;
			  }
			  tempValue = formateTime(tempValue);
			}
			else
			{
				tempValue = timeType;
			}	  
			tempType = "time";
			localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"'+tempType+'"},';
			itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';
		}
		else if (property[i].type=="number")
		{
			tempCode = property[i].code;
			tempValue = $(".mode_item_info >."+property[i].code).val();
			tempType = "number";
			
			if (! isNumber(tempValue))
			{
				showMessageInDialog("请输入"+property[i].name+"!","error");  
		    return;
			}  
			localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"'+tempType+'"},';
			itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';
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
		else if (property[i].type=="text")
		{
			tempCode = property[i].code;
			tempValue = $(".mode_item_info >."+property[i].code).val();
			tempType = "text";
			if (tempValue=="")
			{
				showMessageInDialog("请输入"+property[i].name+"!","error");
		    return;
			}
			localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"'+tempType+'"},';
			itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';
		}
		else if (property[i].type=="list")
		{
			tempCode = property[i].code;
			tempValue = $(".mode_item_info >."+property[i].code +" >option:selected").val();  //因为是增加行，统一调用addnewtimemodeitem，自己会解析
			tempType = "list";
			localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"'+tempType+'"},';
			tempValue = $(".mode_item_info >."+property[i].code).val();
			itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';
		}
		else if (property[i].type=="strategy")
		{
			tempCode = property[i].code;
			tempType = "strategy";
						
			localItemjson = localItemjson + '{"code":"strategy","value":"'+nowstrategy.name+'","type":"'+tempType+'"},';
			localItemjson = localItemjson + '{"code":"strategyId","value":"'+nowstrategy.id+'","type":"strategyId"},';
			
			//itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+nowstrategy.id+'","type":"'+ tempType+'"},';  向后台传递时，从nowstrategy中取id
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

	var url = baseurl+'/pattern/saveTimePatternItem';
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
		if (newItem[i].type=="onOff")
		  tr = tr +'<td class="'+newItem[i].code+'">'+((newItem[i].value=="1")?"ON":"OFF")+'</td>'+'\n';
		else if (newItem[i].type=="on_Off")
		{
			tr = tr +'<td class="'+newItem[i].code+'">'+((newItem[i].value=="1")?"ON":(newItem[i].value=="0")?"OFF":"-")+'</td>'+'\n';
		}	
		else if (newItem[i].type=="time")
		{
			if (newItem[i].value == "-90")
			   tr = tr +'<td class="'+newItem[i].code+'">日出时间</td>'+'\n';
			else if (newItem[i].value == "-91")
				 tr = tr +'<td class="'+newItem[i].code+'">日落时间</td>'+'\n';
			else
			{	 	     
			  var time = timeToString(newItem[i].value);
			  tr = tr +'<td class="'+newItem[i].code+'">'+time+'</td>'+'\n';
			}  
		}
		else if (newItem[i].type=="offset")
		{
		  if (parseInt(newItem[i].value) > 0)
		    tr = tr +'<td class="'+newItem[i].code+'">延后'+newItem[i].value+'分钟</td>'+'\n';
		  else if (parseInt(newItem[i].value) < 0)
		  	tr = tr +'<td class="'+newItem[i].code+'">提前'+Math.abs(parseInt(newItem[i].value))+'分钟</td>'+'\n';
		  else
		  	tr = tr +'<td class="'+newItem[i].code+'">无偏移</td>'+'\n';	  
		}
		else if (newItem[i].type == "strategy")
		{
			if (newItem[i].value == "")
			  tr = tr +'<td class="'+newItem[i].code+'">未使用</td>'+'\n';
			else 
				tr = tr +'<td class="'+newItem[i].code+'">'+newItem[i].value+'</td>'+'\n';  	  
		}
		else if (newItem[i].type=="list")
		{
			var actionDesc = newItem[i].value;
			for (var j=0;j<actionList.length;j++)
			{
				if (newItem[i].value == actionList[j].code)
				{
					actionDesc = actionList[j].name;
					break;
				}
			}
			tr = tr +'<td class="'+newItem[i].code+'">'+actionDesc+'</td>'+'\n';
		}
		else	 
		  tr = tr +'<td class="'+newItem[i].code+'">'+newItem[i].value+'</td>'+'\n';
	}	  
	tr = tr + '</tr>'+'\n';
	
	$(".mode_table >table >tbody").append(tr);
  
	//获取设备信息及strategyId, 绑定到最后一行
	
	for (var m=0;m<param.item.length;m++)
	{
		if (param.item[m].code=="deviceList")
		{
			$(".mode_table >table >tbody tr:last").data("deviceList",param.item[m].value);
		}
		else if (param.item[m].code=="strategyId")
		{
			$(".mode_table >table >tbody tr:last").data("strategyId",param.item[m].value);
		}
	}
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
  
  str = str + "<div class=\"deviceList\">";
  str = str + "<ul>";
  var deviceLi = "";
  var deviceList = getDeviceClass();
  var now_deviceList = $("#mode_item_"+current_time_mode_item_id).data("deviceList");
  
  var findDevice = false;
  for (var j=0;j<deviceList.length;j++)
  {
    findDevice = false;
  	for(var k=0;k<now_deviceList.length;k++)
  	{
  	  if (deviceList[j].id == now_deviceList[k].id)
  	  {
  	    findDevice = true;
  	    break;	
  	  }
  	}
  	if (findDevice)
  		deviceLi = deviceLi + "<li style=\"min-width:120px;margin-bottom:50px\"><input type=\"checkbox\" id=\"deviceClass_"+ deviceList[j].id +"\" checked=\"checked\"><span>"+deviceList[j].name+"</span></li>"
  	else
  	  deviceLi = deviceLi + "<li style=\"min-width:120px;margin-bottom:50px\"><input type=\"checkbox\" id=\"deviceClass_"+ deviceList[j].id +"\"><span>"+deviceList[j].name+"</span></li>"
  }
  str = str + deviceLi;
  str = str + "</ul>";
  str = str + "</div>"+"\n";
    
  str = str + "<div class=\"mode_item_info\">"+"\n";
  var tempValue = "";
  
  for(var i=0;i<property.length;i++)
  {
  	if (property[i].type == "time")
  	{
  		tempValue = tds.find("."+property[i].code).html();
  	  str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
  	  str = str + "<select class=\""+property[i].code +"\">";
  	  if ((tempValue!="日出时间")&&(tempValue!="日落时间"))
  	    str = str + "<option value=\"0\" selected=\"selected\">固定时间</option>";
  	  else
  	  	str = str + "<option value=\"0\">固定时间</option>";   
  	  
  	  if (tempValue=="日出时间") 
  	    str = str + "<option value=\"-90\" selected=\"selected\">日出时间</option>";	
  	  else  	
        str = str + "<option value=\"-90\">日出时间</option>";	
      
      if (tempValue=="日落时间")
        str = str + "<option value=\"-91\" selected=\"selected\">日落时间</option>";
      else
        str = str + "<option value=\"-91\">日落时间</option>";
      
      str = str + "</select>";
      
      str = str + "<div id=\"tempdatatimepicker\" class=\"input-append\">";	
      if ((tempValue=="日出时间")||(tempValue=="日落时间"))
      {
  	    str = str + "<input data-format=\"hh:mm\" type=\"input\" class=\"" +property[i].code +"\" value=\"\" disabled=\"disabled\" />"+property[i].measurementUnit+"\n";
  	  }
  	  else
  	  {	
  	  	str = str + "<input data-format=\"hh:mm\" type=\"input\" class=\"" +property[i].code +"\" value=\""+tempValue+"\" />"+property[i].measurementUnit+"\n";  
  	  }
  	  str = str + "<span class=\"add-on\">"+"\n";
      str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
      str = str + "</span>"+"\n";
      str = str + "</div>"+"\n";
      
  	  str = str + "<br/>"+"\n";
  	  str = str + "<br/>"+"\n";
    }
  	else if (property[i].type == "number")
    {
      tempValue = tds.find("."+property[i].code).html();
      str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
      str = str + "<input type=\"input\" class=\"" +property[i].code +"\" value=\""+tempValue+"\" />"+property[i].measurementUnit+"\n";
      str = str + "<br/>"+"\n";	
      str = str + "<br/>"+"\n";	
    }
    else if (property[i].type == "offset")
    {
      tempValue = tds.find("."+property[i].code).html();
      str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
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
    }
    else if (property[i].type == "list")
    {
      tempValue = tds.find("."+property[i].code).html();
      str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
      str = str + "<select class=\""+property[i].code +"\">";
      for (var j= 0;j<actionList.length;j++)
      {
        if (tempValue == actionList[j].name)
           str = str + "<option value=\""+ actionList[j].code+"\" selected=\"selected\">"+ actionList[j].name+"</option>";
        else   	
           str = str + "<option value=\""+ actionList[j].code+"\">"+ actionList[j].name+"</option>";	
      }
      str = str + "</select>";
      str = str + "<br/>"+"\n";	
      str = str + "<br/>"+"\n";	
    }
    else if (property[i].type == "strategy")
    {
    	str = str + "<div class=\"button\" onclick=\"javascript:showChoiceStrategyDialog()\">选择策略</div>";
    	nowstrategy.id = $("#mode_item_"+current_time_mode_item_id).data("strategyId");
    	if (nowstrategy.id =="")
    	  str = str + "<div class=\"now_strategy\"><span class=\"strategy_name\">当前未使用任何策略</div>";
    	else   
    	  str = str + "<div class=\"now_strategy\"><span class=\"strategy_name\">当前选择的策略 : "+nowstrategy.name+"</div>";
      str = str + "<div class=\"strategyFailTip\">如果选择的策略失效，则继续采用下面的设定:</div>";
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
    else if (property[i].type == "text")
    {
    	tempValue = tds.find("."+property[i].code).html();
    	str = str + "<div class=\"normal_text\">"+property[i].name+"</div>"+"\n";
      str = str + "<textarea class=\""+property[i].code+"\">"+ tempValue+"</textarea>"+"\n";
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
    
  $('#edit_mode_item select.time').on('change',function(event){
  	if (($(this).val() == "-90")||($(this).val() == "-91"))
  	{
  	  $(this).next().children().eq(0).attr("disabled",true); 
  	  $(this).next().children().eq(0).attr("value","");
  	}
  	else if ($(this).val() == "0")
  		$(this).next().children().eq(0).attr("disabled",false);
  	else	
  		return;
  });
  
  $('#tempdatatimepicker').datetimepicker({
      maskInput: true,           // disables the text input mask
      pickDate: false,            // disables the date picker
      pickTime: true,            // disables de time picker
      pick12HourFormat: false,   // enables the 12-hour format time picker
      pickSeconds: false         // disables seconds in the time picker
    });
  
  $('#mask').show();
  $("#edit_mode_item").draggable({cancel:".body"});
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
		if (property[i].type=="deviceTree_deviceGroup")
		{
			refreshSelectedDeviceList();
	    tempValue = getDeviceNameList();
      if(tempValue=="" )
        tempValue = "所有设备";
	    localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"string"},';
	
	    //将设备信息放入localItemjson，用于创建成功后，将设备绑定到最后一行
	    localItemjson = localItemjson + '{"code":"deviceList","value":'+getDeviceListString()+',"type":"array"},';	
		} 
		else if (property[i].type=="time")
		{
			tempCode = property[i].code;
			tempType = "time";
			var timeType = $(".mode_item_info >select."+property[i].code).val();
			if (timeType == "0")
			{
			  tempValue = $(".mode_item_info #tempdatatimepicker >input."+property[i].code).val();
			  if (! isTime(tempValue))
			  {
				  showMessageInDialog("请输入"+property[i].name+"!","error");
		      return;
			  }
			  localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"'+tempType+'"},';
			  tempValue = formateTime(tempValue);
			  itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';
			}
			else
			{
				tempValue = timeType;
				localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+$(".mode_item_info >select."+property[i].code +">option:selected").html()+'","type":"'+tempType+'"},';
			  itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';
			}	
		}
		else if (property[i].type=="number")
		{
			tempCode = property[i].code;
			tempValue = $(".mode_item_info >."+property[i].code).val();
			tempType = "number";
			
			 if (! isNumber(tempValue))
			 {
				 	showMessageInDialog("请输入"+property[i].name+"!","error");  
		      return;
			 }
			
			localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"'+tempType+'"},';
			itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';
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
		else if (property[i].type=="list")
		{
			tempCode = property[i].code;
			tempValue = $(".mode_item_info >."+property[i].code +" >option:selected").html();  //因为是修改行,所以直接取描述了
			tempType = "list";
			localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"'+tempType+'"},';
			tempValue = $(".mode_item_info >."+property[i].code).val();
			itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';
		}
		
		else if (property[i].type=="text")
		{
			tempCode = property[i].code;
			tempValue = $(".mode_item_info >."+property[i].code).val();
			tempType = "text";
			if (tempValue=="")
			{
				showMessageInDialog("请输入"+property[i].name+"!","error");
		    return;
			}
			localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"'+tempType+'"},';
			itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';
		}
		else if (property[i].type=="strategy")
		{
			tempCode = property[i].code;
			tempType = "strategy";
			
			tempValue = nowstrategy.name;
			if (tempValue =="")
			  tempValue = "未使用";
			localItemjson = localItemjson + '{"code":"'+ property[i].code +'","value":"'+tempValue+'","type":"'+tempType+'"},';
			localItemjson = localItemjson + '{"code":"strategyId","value":"'+nowstrategy.id+'","type":"strategyId"},';			
			//itemjson = itemjson + '{"code":"'+ tempCode +'","value":"'+tempValue+'","type":"'+ tempType+'"},';  向后台传递时,从nowstrategy中取ID
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
    
  //获取设备信息以及strategyId, 绑定到最后一行
	
	for (var m=0;m<param.item.length;m++)
	{
		if (param.item[m].code=="deviceList")
		{
			tr.data("deviceList",param.item[m].value);
		}
		else if (param.item[m].code=="strategyId")
		{
			tr.data("strategyId",param.item[m].value);
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
    var baseTime = '';
    var offsetTime='';
    
    var dbCode = "";  
    var ucPattern='{"id":"'+patternId+'"}';
    var sum=modeList.length;
    for(var i=0;i<sum;i++)
    {
        if(modeList[i].code=='time'){
        	if ((modeList[i].value=="-90")||(modeList[i].value=="-91"))
        	{
        	  actionStr+='"baseTime":"'+modeList[i].value+'",';
        	  baseTime = modeList[i].value;
        	}
        	else
        	{
            var t=modeList[i].value;
            baseTime = getMinuteFromHhmm(t);
            actionStr+='"baseTime":"'+baseTime+'",';
          }  
        }
        if(modeList[i].code=='offset'){
            var offset=modeList[i].value;
            actionStr+='"offsetTime":"'+offset+'",';
        }
        if(modeList[i].code=='actionDesc'){
            var desc=modeList[i].value;
            actionStr+='"actionType":"'+desc+'",';
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
            deviceStr+='{"startTime":"'+baseTime+'","endTime":"'+nowstrategy.endTime+'","strategyId":"'+nowstrategy.id+'","selectType":"'+selectedDeviceList[j].type+'","name":"'+selectedDeviceList[j].name+'","device":"'+selectedDeviceList[j].id+'"'+',"ucRunParams":['+paramStr+']}';
         else
           deviceStr+='{"startTime":"'+baseTime+'","endTime":"'+nowstrategy.endTime+'","strategyId":"'+nowstrategy.id+'","selectType":"'+selectedDeviceList[j].type+'","name":"'+selectedDeviceList[j].name+'","device":"'+selectedDeviceList[j].id+'"'+',"ucRunParams":['+paramStr+']},';
      }
    }
		else
		{
		  deviceStr+='{"startTime":"'+baseTime+'","endTime":"'+nowstrategy.endTime+'","strategyId":"'+nowstrategy.id+'","selectType":"'+'A'+'","name":"所有设备","device":"","ucRunParams":['+paramStr+']}';
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
		
		if ((ucPatternActions[i].baseTime =="-90")||(ucPatternActions[i].baseTime =="-91"))
		  modeli = {"code":"time","value":ucPatternActions[i].baseTime,"type":"time"};
		else
			modeli = {"code":"time","value":getHhmmFromMinute(ucPatternActions[i].baseTime),"type":"time"};
		modeItem.item.push(modeli);
		
		modeli = {"code":"offset","value":ucPatternActions[i].offsetTime,"type":"offset"};
		modeItem.item.push(modeli);
		
		modeli = {"code":"actionDesc","value":ucPatternActions[i].actionType,"type":"list"};
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
		
		var strategyName = "";
		if (ucPatternActions[i].ucDevicePatterns.length >0)
		{
		  strategyName = getStrategyNameById(ucPatternActions[i].ucDevicePatterns[0].strategyId);
		  if (strategyName =="");
		    strategyName = "未使用"
		  modeli = {"code":"strategy","value":strategyName,"type":"strategy"};
		  modeItem.item.push(modeli);
		
		  modeli = {"code":"strategyId","value":ucPatternActions[i].ucDevicePatterns[0].strategyId,"type":"strategyId"};
		  modeItem.item.push(modeli);
		}
		if (ucPatternActions[i].ucDevicePatterns.length >0)
		{
		  ucRunParams = ucPatternActions[i].ucDevicePatterns[0].ucRunParams;
		  for(k=0;k<ucRunParams.length;k++)
		  {
		    codeType = getCodeAndTypeFromDbCode(ucRunParams[k].paramName)
		    modeli = {"code":codeType.code,"value":ucRunParams[k].paramValue,"type":codeType.type};
		    modeItem.item.push(modeli);
		  }
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
	$(".dialog .body .deviceList >ul >li >input:checked").each(function(index){
		
		deviceId = $(this).attr('id').replace('deviceClass_','');
		deviceName = $(this).next().text();
		selectedDeviceList.push({"id":deviceId,"name":deviceName,"type":"M"});  //M代表专业分类，L已经弃用 
	}
	);
}

function getDeviceClass()
{
	var list = [];
	var deviceClass = null;
	var url = baseurl+'/pattern/getSpecialtyTree';
	
	$.ajax({url:url,type:'GET',dataType:'json',async:false,success:function(data){ 	     
   for (var i=0;i<data.length;i++)
    {
       if (data[i].parent =="P0105")
       {
           //if(data[i].description.indexOf("虚拟")>0){
               deviceClass = {"id":data[i].classstructureid,"name":data[i].description};
               list.push(deviceClass);
           //}
       }       
    }     
	}
  }
  );
  return list;
}

function getSelectedDeviceClassList()
{
	var tempDeviceClassList = [];
	var deviceClassId =""
	var tempDeviceClassObj = $(".dialog .body .device_group_list >ul >li >input:checked");
	for (var j=0;j<tempDeviceClassObj.length;j++)
	{
		deviceClassId = tempDeviceClassObj.eq(j).attr('id').replace('deviceClass_','');
		deviceClassName = tempDeviceClassObj.eq(j).next().html();
		tempDeviceClassList.push({"id":deviceClassId,"name":deviceClassName,"type":"M"});  //M代表专业分类 L已经弃用
	}
  return tempDeviceClassList;
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