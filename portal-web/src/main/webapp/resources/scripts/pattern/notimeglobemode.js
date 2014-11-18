var baseurl = CONTEXT_PATH;
var current_device_id = 0;
var new_mode = "";
var blank_mode = "";

var noTimeSubSystem = [{"code":"HVAC","name":"暖通"},{"code":"ETD","name":"变配电"},{"code":"FAS","name":"消防系统"},{"code":"MSEM","name":"电梯监视"},{"code":"INFP","name":"信息发布"},{"code":"BGMB","name":"背景音乐"},{"code":"LSPUB","name":"公共照明"},{"code":"LSN","name":"夜景照明"}];

function getSystemId()
{
	return systemId;
}


//将某个dialog销毁
function hideDialog()
{
	$("#mask").html('');
	$("#mask").hide();
}
// 在弹出dialog中footer位置给用户一些操作提示，比如输入有效性检查等
function showMessageInDialog(msg,message_type)
{
	if (message_type =="error")
	  $(".dialog >.footer >#dialogTip").attr('class','error');
	else
		$(".dialog >.footer >#dialogTip").attr('class','tip');	
	$(".dialog >.footer >#dialogTip").html(msg);
  var t=setTimeout('clearMessageInDialog()',3000);
}
// 将弹出dialog中的操作提示删除掉
function clearMessageInDialog()
{
	$(".dialog >.footer >#dialogTip").html('');
}
//减少marginTop，向上滚动
function decreaseMarginTop()
{
	var one_slide_height = $(".mode_list >ul >li").outerHeight(true)*2; //显示2排
	var mt = parseInt($(".mode_list >ul").css('margin-top'));
	var ul_h = $(".mode_list >ul").height();
	if (mt <= -(ul_h - one_slide_height))
	  return;
	mt = mt - one_slide_height;
	$(".mode_list >ul").animate(
	{
	 'margin-top':mt+"px"
	},
	500
	);
}
//增大marginTop，向下滚动
function increaseMarginTop()
{
	var one_slide_height = $(".mode_list >ul >li").outerHeight(true)*2; //显示2排
	var mt = parseInt($(".mode_list >ul").css('margin-top'));
	if (mt >=0)
	  return;
	mt = mt + one_slide_height;
	$(".mode_list >ul").animate(
	{
	 'margin-top':mt+"px"
	},
	500
	);
}

//初始化模式列表
function initModeLi()
{
  new_mode = "<li>"+"\n";
  new_mode = new_mode + "<div class=\"create_new_mode\">"+"\n";
  new_mode = new_mode + "<div class=\"cross\"></div>"+"\n";
  new_mode = new_mode + "</div>"+"\n";
  new_mode = new_mode + "</li>"+"\n";
  		 
  blank_mode = "<li>"+"\n";
  blank_mode = blank_mode + "<div class=\"one_notime_mode\">"+"\n"
  blank_mode = blank_mode +"</div>"+"\n";
  blank_mode = blank_mode + "</li>"+"\n";
}

function renderCreateNewLi(li_count)
{
	switch(li_count)
  {
    case 1:
  	  li_str = "";
  	  for (var i=0;i<3;i++)
  	  {
  	    li_str = li_str + blank_mode;
  	  }
  	  li_str = li_str + new_mode;
  	  for (var i=0;i<3;i++)
  	  {
  	    li_str = li_str + blank_mode;
  	  }
  	  break;
  	case 2:
  	  li_str = "";
  	  for (var i=0;i<2;i++)
  	  {
  	    li_str = li_str + blank_mode;
  	  }
  	  li_str = li_str + new_mode;
  	  for (var i=0;i<3;i++)
  	  {
  	    li_str = li_str + blank_mode;
  	  }
  	  break;
  	case 3:
  	  li_str = blank_mode + new_mode;
  	  for (var i=0;i<3;i++)
  	  {
  	    li_str = li_str + blank_mode;
  	  }
  	  break;
  	case 4:
  	  li_str = new_mode;
  	  for ( var i=0;i<3;i++)
  	  {
  	    li_str = li_str + blank_mode;
  	  }
  	  break;
  	case 5:
  	  li_str = new_mode;
  	  for (var i=0;i<2;i++)
  	  {
  	    li_str = li_str + blank_mode;
  	  }
  	  break;
  	case 6:
  	  li_str = new_mode;
  	  for (var i=0;i<1;i++)
  	  {
  	    li_str = li_str + blank_mode;
  	  }
  	  break; 
  	case 7:
  	  li_str = new_mode;
  	  break;  
  }
  return li_str;
}


function showNotimeModeDetail()
{
	$(".mode_detail > .mode_table > table > tbody").html('');	
	$(".mode_detail").show();
	var url = baseurl+'/pattern/getPatternItem';
	
	var d = new Date();
	var r = Math.random()+""+d.getMilliseconds();
	var params ={patternId:current_notime_mode_id,r:r};
	
	$.ajax({url:url,type:'GET',dataType:'json',async:true,data:params,success:function(data){ 
		     
  var modeItemList = data.ucPatternActions;
  var item_id = "";
  var offset_time = "";
  var device_id ="";
  var device_name = "";
  var cmd = "OFF";
  var desc = "";
  
  for (var i=0;i<modeItemList.length;i++)
  {
  	item_id = modeItemList[i].id;
  	offset_time = modeItemList[i].offsetTime;
  	desc = modeItemList[i].description
  	var ucDevicePatterns = modeItemList[i].ucDevicePatterns;
  	if (ucDevicePatterns.length > 0)
  	{
  	  device_id = ucDevicePatterns[0].device;
  	  device_name = ucDevicePatterns[0].name;
  	
  	  if (ucDevicePatterns[0].ucRunParams.length > 0)
  	    cmd = (ucDevicePatterns[0].ucRunParams[0].paramValue==1)?"ON":"OFF";
      else
    	  cmd = "OFF";	
  	  addNewNotimeModeItem(item_id,offset_time,device_id,device_name,cmd,desc);
    }
  }
  if (modeItemList.length > 0)
  {
    $(".mode_detail > .mode_table > table > tbody >tr").each(function(index){
	  if ((index + 1)%2 != 0)
	  	$(this).attr('class',"even");
	  else
	  	$(this).attr('class',"odd");
	  });
	}
	resetScrollBar();
	}
  }
  );
}

function resetScrollBar()
{
	var tbody_h = $(".mode_detail >.mode_table > table > tbody").height();
	if (tbody_h > 0)
	{
	  $(".mode_detail >.actionbar").show();
	  $(".mode_detail >.scrollbar").show();
	  var sld = new Slider("idSlider", "idBar", { Horizontal: false,
			MaxValue: $$("mode_table").scrollHeight - $$("mode_table").clientHeight,
			onMove: function(){ $$("mode_table").scrollTop = this.GetValue(); }
		});
    sld.SetPercent(0);
		sld.Ease = true;
	}
}

/* 以下为全局触发模式相关javascript */
function renderGlobeModeList()
{
	var li_str = "";
	var li_slide = "";
	
	var li_count = modeList.length;
    
  if (li_count == 0)  //一条记录都没有
  {
  	li_str = new_mode;
  	for (var i=0;i<6;i++)
  	{
  	  li_str = li_str + blank_mode;
  	}
  }
	else  //只要有记录，不管几条
  {
    var slide_count = Math.floor(li_count/7);
  	var mod = li_count%7;
  	if (mod !=0)
  	  slide_count = slide_count + 1;
    	     
  	var index = 0;
  	for (var i=0;i<slide_count;i++)
  	{
  	  li_slide = "";
  	  if (i != slide_count-1) //只要不是最后一页，这一页肯定是7个，在最后加一个
  	  {
  	    for (var j=0;j<7;j++)
  	    {
  	  	  index = (i*7) + j;
  	  	  li_slide = li_slide + renderGlobeNotimeMode(modeList[index]);	  
  	    }
  	    li_slide = li_slide + new_mode;
  	  }
  	  else //最后一页或第一页  
  	  {
  	     var last_li_count = li_count - i*7;
  	     for (var j=0;j<last_li_count;j++)
  	     { 
  	  	   index = (i*7) + j;
  	  	   li_slide = li_slide + renderGlobeNotimeMode(modeList[index]);	  
  	     }
  	     li_slide = li_slide + renderCreateNewLi(last_li_count); 
  	  }
  	  li_str = li_str + li_slide;
  	} 
  }
  $(".mode_list > ul").html(li_str);
  $(".mode_list  .action_group").hide(); 
  $(".mode_list > ul > li >.create_new_mode").on("click",function(event)
  {
    showCreateGlobeNoTimeModeDialog();
  }
  );
  $(".mode_list > ul > li >.one_notime_mode >.title").on("click",function(event)
   {
  	  if (($(event.target).attr('class') =='edit')||($(event.target).attr('class') =='delete'))
  	  {
  	  	return;
  	  }
  	  $(".mode_list > ul > li >.one_notime_mode >.title").css("background-color","#78CDD0");
  	  $(".mode_list > ul > li >.one_notime_mode >.mode_desc").css("border-color","#ffffff");
  	  $(".mode_list >ul >li .action_group").hide();
  	
  	  $(event.currentTarget).css("background-color","#FFCC33");
  	  $(event.currentTarget).next().css("border-left","2px solid #FFCC33");
  	  $(event.currentTarget).next().css("border-right","2px solid #FFCC33");
  	  $(event.currentTarget).next().css("border-bottom","2px solid #FFCC33");
  	  $(event.currentTarget).children().eq(1).show();
  	  current_globe_notime_mode_id = $(event.currentTarget.parentNode).attr("id").replace('notime_mode_',"");
  	  current_globe_notime_mode_name = $(event.currentTarget).children().eq(0).children().eq(0).html();
  	  renderRelationSystem();
  	} 
  );
}

function renderGlobeNotimeMode(obj)
{
	var str = "";
	str = str +'<li>'+'\n';
	str = str +'<div id="notime_mode_'+obj.id+'\" class="one_notime_mode">'+'\n';
  str = str +'<div class="title">'+'\n';
  str = str +'<div class="mode_name"><span>'+obj.patternName+'</span></div>'+'\n';
  str = str +'<div class="action_group">'+'\n';
  str = str +'<div class="delete" onclick="javascript:showConfirmDeleteGlobeNotimeModeDialog()"></div>'+'\n';
  str = str +'<div class="edit" onclick="javascript:showEditGlobeNotimeModeDialog()"></div>'+'\n';
  str = str +'</div>'+'\n';
  str = str +'</div>'+'\n';
  str = str +'<div class="mode_desc"><span>'+obj.patternDescription+'</span></div>'+'\n';
  str = str +'</div>'+'\n';
  str = str +'</li>'+'\n';
	return str;
}

function showCreateGlobeNoTimeModeDialog()
{
	var str = "<div id=\"create_notime_mode\" class=\"dialog\" >"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">创建新的全局触发模式</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
  str = str + "<br/>"+"\n";
  
  str = str + "<div class=\"normal_text\">模式名称</div>"+"\n";
  str = str + "<input type=\"input\" class=\"mode_name\" value=\"\" />"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  
  str = str + "<div class=\"normal_text\">模式备注</div>"+"\n";
  str = str + "<textarea class=\"mode_desc\" style=\"height:90px\"></textarea>"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  
  str = str +"<div class=\"system_check_list\">"+"\n";
  str = str +"<ul>"+"\n";
  
  for (var i=0;i<noTimeSubSystem.length;i++)
  {
    str = str +"<li>"+"\n";
    str = str +"<input type=\"checkbox\" id=\""+noTimeSubSystem[i].code+"\"/><span>"+noTimeSubSystem[i].name+"</span>"+"\n";
    str = str +"</li>"+"\n";	
  }
   
  str = str +"</ul>"+"\n";
  str = str +"</div>"+"\n"; 
  
  str = str +	"</div>"+"\n";
  
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" style=\"margin-left:50px\"></div>"+"\n";
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:createGlobeNotimeMode();\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "</div>"+"\n";
  
  $('#mask').html('');
  $('#mask').html(str);
  
  $('#create_notime_mode').css("width","600px");
  $('#create_notime_mode').css("height","500px");
  $('#create_notime_mode').css("margin-left","605px");
  $('#create_notime_mode').css("margin-top","350px");
  $('#create_notime_mode >.body').css('width','500px');
  $('#create_notime_mode >.body').css('height','400px');
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  });
  
  $('#mask').show();
  $("#create_notime_mode").draggable();

}
function renderRelationSystem()
{
	var systemCode = "";
	var systemName = "";
	var li = "";
	$(".globe_mode .mode_relation_system >.relation_system_list >ul").html('');
	for(var i=0;i<modeList.length;i++)
	{
		if (current_globe_notime_mode_id == modeList[i].id)
		{
			for (var j=0;j<modeList[i].ucCombinationPatterns.length;j++)
			{
				 systemCode = modeList[i].ucCombinationPatterns[j].systemId;
	       systemName = getSystemNameByCode(systemCode);;
	       
	       li = renderOneSystem(systemCode,systemName);
				 $(".globe_mode .mode_relation_system >.relation_system_list >ul").append(li);
			}
			choiceSystem($(".globe_mode .mode_relation_system >.relation_system_list >ul >li").get(0));
			break;
		}
	}
}

function renderOneSystem(systemCode,systemName)
{
	var smallLetterCode = systemCode.toLocaleLowerCase();
	return '<li id="system_'+systemCode+'" onclick="javascript:choiceSystem(this)"><div class="'+smallLetterCode+'_green"></div><div class="system_name">'+systemName+'</div></li>';
}

function createGlobeNotimeMode()
{
	var mode_name = $("#create_notime_mode .mode_name").val();
	if (mode_name =="")
	{
		showMessageInDialog("请输入模式名称","error");
		return;
	}
	var mode_desc = $("#create_notime_mode .mode_desc").val();
	if (mode_desc =="")
	{
		showMessageInDialog("请输入模式描述","error");
		return;
	}
	if ($("#create_notime_mode .system_check_list > ul > li > input:checked").length ==0)
	{
		showMessageInDialog("请至少选择一个关联的子系统","error");
		return;
	}
	var system_list = new Array();
	var system_list_str = "";
	$("#create_notime_mode .system_check_list > ul > li > input:checked").each(function(index){
		system_list.push($(this).attr('id'));
		}
	);
	system_list_str = system_list.join();
	var url = baseurl+'/pattern/saveGlobalPatternNoTime';
	var params ={patternName:mode_name,patternDescription:mode_desc,systemList:system_list_str};
	$.ajax({url:url,type:'POST',dataType:'json',async:true,data:params,success:function(data){ 	     
    if (data.success == true)
    {
    	showMessageInDialog("模式创建成功!","tip");
    	addNewGlobeNotimeMode(data.patternId,mode_name,mode_desc,system_list); //有问题 system_list的顺序和jsp页面中返回的顺序不一定相同
    }
    else
    {
    	showMessageInDialog("模式创建失败,请稍后再试!","error");
    } 
    var t=setTimeout('hideDialog()',1500);    
  }
  }
  );
}

function addNewGlobeNotimeMode(mode_id,mode_name,mode_desc,systemList)
{
	var ucCombinationPatterns = [];
	for (var i=0;i<systemList.length;i++)
	{
		ucCombinationPatterns.push({"systemId":systemList[i]});
	}
		
	var newMode = {"id":mode_id,"patternName":mode_name,"patternDescription":mode_desc,"ucCombinationPatterns":ucCombinationPatterns};
	modeList.push(newMode);
	renderGlobeModeList();
	$(".mode_list > ul > li >.one_notime_mode >.title").css("background-color","#78CDD0");
  $(".mode_list > ul > li >.one_notime_mode >.mode_desc").css("border-color","#ffffff");
	$("#notime_mode_"+mode_id).children().eq(0).css("background-color","#FFCC33");
	$("#notime_mode_"+mode_id).children().eq(0).children().eq(1).show();
	$("#notime_mode_"+mode_id).children().eq(1).css("border-left","2px solid #FFCC33");
  $("#notime_mode_"+mode_id).children().eq(1).css("border-right","2px solid #FFCC33");
  $("#notime_mode_"+mode_id).children().eq(1).css("border-bottom","2px solid #FFCC33");
  current_globe_notime_mode_id = mode_id;
  $(".mode_detail >.mode_table >table >tbody").html('');
  renderRelationSystem();
}

function choiceSystem(li)
{
	var css = "";
	var newcss = "";
	$(".globe_mode .mode_relation_system >.relation_system_list >ul >li.current").removeClass('current');
	$(".globe_mode .mode_relation_system >.relation_system_list >ul >li>div:first-child").each(function(index){
	  css = $(this).attr("class");
	  newcss = css.substring(0,css.indexOf("_")+1)+"green";
	  $(this).attr("class",newcss);
	}
	);
	$(li).addClass('current');
	css = $(li).children().eq(0).attr("class");
	newcss = css.substring(0,css.indexOf("_")+1)+"white";
	$(li).children().eq(0).attr("class",newcss);
	current_system_code = $(li).attr('id').replace('system_','');
	current_system_name = $(li).children().eq(1).html();
	
	for(var i=0;i<modeList.length;i++)
	{
		if (current_globe_notime_mode_id == modeList[i].id)
		{
			for (var j=0;j<modeList[i].ucCombinationPatterns.length;j++)
			{
				 if (current_system_id == modeList[i].ucCombinationPatterns[j].systemId)
				 {
				   if (modeList[i].ucCombinationPatterns[j].ucPattern !=null)
				      current_relation_notime_mode_id = modeList[i].ucCombinationPatterns[j].ucPattern.id;
				   else
				   	  current_relation_notime_mode_id = "";   
				   current_notime_mode_id = current_relation_notime_mode_id ;
				   break;
				 }	 
		  }
		  break;
		}  
	}
	renderRelationMode();
}

function renderRelationMode()
{
	var str = "";
	str = str + "<div class=\"title\"></div>";
	str = str + "<div id=\"relation_mode_name\" onclick=\"javascript:showRelationModeDetail()\"></div>";
	str = str + "<select onchange=\"showOtherModeDetail()\"></select>";
	str = str + "<div class=\"button_group\">";
	str = str + "<div onclick=\"\" style=\"text-align:center;background-color:#999999\"><span style=\"margin-left:0px;\">关联此模式</span></div>";
	str = str + "</div>";
	$(".globe_mode .mode_detail .actionbar").html(str);
	
	var modeName = "";
	var option = "";
	var notimeModeList = null;
	
	//如果还没有关联该子系统下的任何一个触发模式
	if (current_notime_mode_id =="")
	{
		$(".globe_mode .mode_detail .actionbar .title").html("当前全局模式尚未关联该子系统下任何一个触发模式!");
		$(".globe_mode .mode_detail .actionbar #relation_mode_name").hide();
		showActionBar();
		clearModeItemList();
	}
	else
	{
		$(".globe_mode .mode_detail .actionbar .title").html("当前全局模式关联的子系统模式为: ");
		$(".globe_mode .mode_detail .actionbar #relation_mode_name").show();
		 //展现当前选择的触发模式详细参数表格，前面已经将当前关联的模式作为默认的选择
		showNotimeModeDetail();
	}	
	  
	//下面生成下拉列表
	//首先找到所有子系统中，当前所选子系统的所有触发模式
	for (var i=0;i<systemModeList.length;i++)
	{
		if (systemModeList[i].code == current_system_code)
		{
			notimeModeList = systemModeList[i].item;
			break;
		}
	}
	
	
	//如果存在触发模式，除了当前关联的模式外，把其它模式都放入下拉列表,如果遇到当前关联的模式，则把这个模式的名字放在.relation_mode_name
	if ((notimeModeList !=null)&&(notimeModeList.length >0))
	{
	   var defaultOption = '<option value="" selected="selected">查看该子系统的其它触发模式</option>';
	   //生成默认的下拉列表
	   $(".globe_mode .mode_detail .actionbar select").html('');
	   $(".globe_mode .mode_detail .actionbar select").append(defaultOption);
	   
	   for (var j=0;j<notimeModeList.length;j++)
	   {
	   	  if (current_notime_mode_id != notimeModeList[j].id)
	   	  {
	   	    option = '<option value="'+notimeModeList[j].id+'">'+notimeModeList[j].name+'</option>'+'\n';
	   	    $(".globe_mode .mode_detail .actionbar select").append(option);
	      }
	      else
	      {
	      	modeName = notimeModeList[j].name;
	      	$(".globe_mode .mode_detail .actionbar #relation_mode_name").html(modeName);
	      	$(".globe_mode .mode_detail .mode_table .mode_name_title").html(modeName);
	      	current_relation_notime_mode_name = modeName;
	      }	
	   }
	}
	else  //如果这个子系统还没有任何触发模式
	{
		$(".globe_mode .mode_detail .actionbar").html("这个系统还没有任何触发模式,要关联此系统的模式,请先在该系统中创建触发模式!");
	}	
}

function showActionBar()
{
  $(".globe_mode .mode_detail").show();
}

function showRelationModeDetail()
{
	current_notime_mode_id = current_relation_notime_mode_id ;
	$(".globe_mode .mode_detail .mode_table .mode_name_title").html(current_relation_notime_mode_name);
	$(".globe_mode .mode_detail .actionbar select option :eq(0)").attr('selected','selected');
	$(".globe_mode .mode_detail .actionbar .button_group div").attr('onclick','');
	$(".globe_mode .mode_detail .actionbar .button_group div").css('background-color','#999999');
	showNotimeModeDetail();
}

function clearModeItemList()
{
	$(".mode_table >table >tbody").html('');
	$(".mode_table .mode_name_title").html('');
}

function showOtherModeDetail()
{
	var selected_notime_mode_id = $(".globe_mode .mode_detail .actionbar select").val();
	if(selected_notime_mode_id !="")
	{
	  current_notime_mode_id = selected_notime_mode_id;
	  var modeName = $(".globe_mode .mode_detail .actionbar select option:selected").text();
	  $(".globe_mode .mode_detail .mode_table .mode_name_title").html(modeName);
	  $(".globe_mode .mode_detail .actionbar .button_group div").attr('onclick','javascript:showBindNotimeModeDialog()');
	  $(".globe_mode .mode_detail .actionbar .button_group div").css('background-color','#26C3BC');
	  showNotimeModeDetail();
	}
	else
	{
		
	}  
}


function showBindNotimeModeDialog()
{
	var selected_notime_mode_name = $(".globe_mode .mode_detail .mode_table .mode_name_title").html();
	var str = "<div id=\"bind_notime_mode\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">关联子系统的触发模式</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
  str = str + "<br/>"+"\n";
  
  str = str + "<div class=\"normal_text\">模式名称: "+current_globe_notime_mode_name+"</div>"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  
  str = str + "<div class=\"normal_text\">关联的子系统: "+current_system_name+"</div>"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  
  str = str + "<div class=\"normal_text\">关联的触发模式: "+selected_notime_mode_name+"</div>"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  
  str = str +	"</div>"+"\n";
  
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" style=\"margin-left:50px\"></div>"+"\n";
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:bindNotimeMode();\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "</div>"+"\n";
  
  $('#mask').html('');
  $('#mask').html(str);
  
  $('#bind_notime_mode').css("width","550px");
  $('#bind_notime_mode').css("height","300px");
  $('#bind_notime_mode').css("margin-left","665px");
  $('#bind_notime_mode').css("margin-top","390px");
  $('#bind_notime_mode >.body').css('width','350px');
  $('#bind_notime_mode >.body').css('height','200px');
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  });
  
  $('#mask').show();
  $("#bind_notime_mode").draggable();
}

function bindNotimeMode()
{
	var url = baseurl+'/pattern/saveGlobalPatternToPattern';
	var glbalPattern = '{"ucGlobalPattern":{"id":"'+current_globe_notime_mode_id+'"},"ucPattern":{"id":"'+current_notime_mode_id+'"},"systemId":"'+current_system_code+'"}';
	var params ={glbalPattern:glbalPattern};
	$.ajax({url:url,type:'POST',dataType:'json',async:true,data:params,success:function(data){ 	     
    if (data.success == true)
    {
    	showMessageInDialog("模式关联成功!","tip");
    	editBindNotimeMode(current_globe_notime_mode_id,current_system_code,current_notime_mode_id);
    	current_relation_notime_mode_id = current_notime_mode_id;
    	renderRelationMode();
    }
    else
    {
    	showMessageInDialog("模式关联失败,请稍后再试!","error");
    } 
    var t=setTimeout('hideDialog()',1500);    
  }
  }
  );
}

function editBindNotimeMode(globeModeId,bindSystemCode,bindModeId)
{
	for (var i=0;i<modeList.length;i++)
	{
	   if (modeList[i].id == globeModeId)
	   {
	   	  for(var j=0;j<modeList[i].ucCombinationPatterns.length;j++)
	   	  {
	   	  	if (modeList[i].ucCombinationPatterns[j].systemId == bindSystemCode)
	   	  	{
	   	  		modeList[i].ucCombinationPatterns[j].id = bindModeId;
	   	  		return;
	   	  	}
	   	  }
	   }
	}  
}

function showConfirmDeleteGlobeNotimeModeDialog()
{
	if ($("#notime_mode_"+current_globe_notime_mode_id).length ==0)
	  return;
	
  var str = "<div id=\"delete_notime_mode\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">删除触发模式</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
  str = str + "</br>"+"\n";
  str = str + "</br>"+"\n";
  str = str + "<div>您确认要删除这条触发模式吗?</div>"+"\n";
    
  str = str +	"</div>"+"\n";
  
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
  
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:deleteGlobeNotimeMode();\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "</div>"+"\n";
  
  $('#mask').html('');
  $('#mask').html(str);
  
  $('#delete_notime_mode').css("width","600px");
  $('#delete_notime_mode').css("height","200px");
  $('#delete_notime_mode').css("margin-left","655px");
  $('#delete_notime_mode').css("margin-top","440px");
  $('#delete_notime_mode >.body').css('width','500px');
  $('#delete_notime_mode >.body').css('height','100px');
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  });
  $('#mask').show();
  $("#delete_notime_mode").draggable();
}

function deleteGlobeNotimeMode()
{
	var url = baseurl+'/pattern/deleteGlobalPattern';
	var params ={patternId:current_globe_notime_mode_id};
	$.ajax({url:url,type:'POST',dataType:'json',async:true,data:params,success:function(data){ 	     
    if (data.success == true)
    {
       showMessageInDialog("删除模式成功!","tip");
       for (var i=0;i<modeList.length;i++)
       {
       	  if (current_globe_notime_mode_id == modeList[i].id)
       	  {
       	    modeList.splice(i,1);
       	    break;
       	  }  
       }
       
       renderGlobeModeList();
       $(".mode_detail >.mode_table >table >tbody").html('');
       $(".mode_detail").hide();
       $(".globe_mode .mode_relation_system >.relation_system_list >ul").html('');
       var t=setTimeout('hideDialog()',1500);    
    }
    else
    {
    	showMessageInDialog("删除模式失败,请稍后再试!","error");
    	var t=setTimeout('hideDialog()',1500);
    }
  }
  }
  );
}

function showEditGlobeNotimeModeDialog()
{
	var mode_name = $("#notime_mode_"+current_globe_notime_mode_id+" >.title >.mode_name >span").html();
	var mode_desc = $("#notime_mode_"+current_globe_notime_mode_id+" >.mode_desc >span").html();
	var tempsystemlist = "";
  for (var i=0;i<modeList.length;i++)
	{
		if (current_globe_notime_mode_id == modeList[i].id)
		{
			for (var j=0;j<modeList[i].ucCombinationPatterns.length;j++)
			{
				 tempsystemlist = tempsystemlist + modeList[i].ucCombinationPatterns[j].systemId+',';
			}
		  break;	
	  }
  }		
  	
	var str = "<div id=\"edit_notime_mode\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">编辑全局触发模式</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
  str = str + "<br/>"+"\n";
  
  str = str + "<div class=\"normal_text\">模式名称</div>"+"\n";
  str = str + "<input type=\"input\" class=\"mode_name\" value=\""+mode_name+"\" />"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  
  str = str + "<div class=\"normal_text\">模式备注</div>"+"\n";
  str = str + "<textarea class=\"mode_desc\" style=\"height:90px\">"+mode_desc+"</textarea>"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  
  str = str +"<div class=\"system_check_list\">"+"\n";
  str = str +"<ul>"+"\n";
  
  for (var m=0;m<noTimeSubSystem.length;m++)
  {
    str = str +"<li>"+"\n";
    if (tempsystemlist.indexOf(noTimeSubSystem[m].code) != -1)
      str = str +"<input type=\"checkbox\" id=\""+noTimeSubSystem[m].code+"\" checked=\"checked\"/><span>"+noTimeSubSystem[m].name+"</span>"+"\n";	
    else
    	str = str +"<input type=\"checkbox\" id=\""+noTimeSubSystem[m].code+"\"/><span>"+noTimeSubSystem[m].name+"</span>"+"\n";
    str = str +"</li>"+"\n";	
  }
    
  str = str +"</ul>"+"\n";
  str = str +"</div>"+"\n"; 
    
  str = str +	"</div>"+"\n";
    
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" style=\"margin-left:50px\"></div>"+"\n";
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:saveGlobeNotimeMode();\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "</div>"+"\n";
  
  $('#mask').html('');
  $('#mask').html(str);
  
  $('#edit_notime_mode').css("width","600px");
  $('#edit_notime_mode').css("height","500px");
  $('#edit_notime_mode').css("margin-left","605px");
  $('#edit_notime_mode').css("margin-top","350px");
  $('#edit_notime_mode >.body').css('width','500px');
  $('#edit_notime_mode >.body').css('height','400px');
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  });
  $('#mask').show();
  $("#edit_notime_mode").draggable();
}

function saveGlobeNotimeMode()
{
  var mode_name = $("#edit_notime_mode .mode_name").val();
	if (mode_name =="")
	{
		showMessageInDialog("请输入模式名称","error");
		return;
	}
	var mode_desc = $("#edit_notime_mode .mode_desc").val();
	if (mode_desc =="")
	{
		showMessageInDialog("请输入模式描述","error");
		return;
	}
	
	var system_list = new Array();
	var system_list_str = "";
	$("#edit_notime_mode .system_check_list > ul > li > input:checked").each(function(index){
		system_list.push($(this).attr('id'));
		}
	);
	system_list_str = system_list.join();
	var url = baseurl+'/pattern/saveGlobalPatternNoTime';
	var params ={patternId:current_globe_notime_mode_id,patternName:mode_name,patternDescription:mode_desc,systemList:system_list_str};
	$.ajax({url:url,type:'POST',dataType:'json',async:true,data:params,success:function(data){ 	     
  if (data.success == true)
    {
    	showMessageInDialog("模式修改成功!","tip");
    	modifyGlobeNotimeMode(mode_name,mode_desc,system_list,system_list_str);
    }
    else
    {
    	showMessageInDialog("模式修改失败,请稍后再试!","tip");
    } 
    var t=setTimeout('hideDialog()',1500);    
  }
  }
  );
}  


function modifyGlobeNotimeMode(mode_name,mode_desc,system_list,system_list_str)
{
	var isNew = true;
	for (var i=0;i<modeList.length;i++)
	{
		if (current_globe_notime_mode_id == modeList[i].id)
		{
		   //先修改名字和描述
		   modeList[i].patternName = mode_name;
		   modeList[i].patternDescription = mode_desc;
		   
		   //第一次循环，去掉多余的
		   for(var j=0;j<modeList[i].ucCombinationPatterns.length;j++)
		   {
		   	  if (system_list_str.search(modeList[i].ucCombinationPatterns[j].systemId) == -1)
		   	    modeList[i].ucCombinationPatterns.splice(j,1);
		   }
		   //第二次循环，加上新增的
		   for (var m=0;m<system_list.length;m++)
		   {
		   	 isNew = true;
		   	 for(var j=0;j<modeList[i].ucCombinationPatterns.length;j++)
		     {
		   	    if (system_list[m] == modeList[i].ucCombinationPatterns[j].systemId)
		   	    {
		   	       isNew = false;
		   	       break;
		   	    } 
		     }
		     if (isNew)
		     {
		        modeList[i].ucCombinationPatterns.push({"systemId":system_list[m]});
	       }
	     }
	     break;
		}  
	}
	renderRelationSystem();
}

function goLastPage()
{
	var li_count = modeList.length;
	var slide_count = Math.floor(li_count/7);
  var mod = li_count%7;
  if (mod !=0)
    slide_count = slide_count + 1;
  
  var one_slide_height = $(".mode_list >ul >li").outerHeight(true)*2; //显示2排
	
	var ul_h = $(".mode_list >ul").height();
	var mt = 0;
	if ((ul_h - one_slide_height) > 0) 
	  mt = -(ul_h - one_slide_height);
	else
		mt = 0;  
	$(".mode_list >ul").animate(
	{
	 'margin-top':mt+"px"
	},
	500
	);  
}

function getSystemNameByCode(code)
{
	
	var name = "";
	for (var i=0;i<noTimeSubSystem.length;i++)
	{
		if (noTimeSubSystem[i].code == code)
		{
			name = noTimeSubSystem[i].name;
			break;
		}
	}
	
	return name;
}
function addNewNotimeModeItem(item_id,offset_time,device_id,device_name,cmd,desc)
{
	var offsetTimeStr = "";
	if (parseInt(offset_time) < 0)
	  offsetTimeStr = "提前"+Math.abs(parseInt(offset_time))+"分钟";
	else if  (parseInt(offset_time) > 0)
		offsetTimeStr = "延后"+offset_time+"分钟"; 
	else
		offsetTimeStr = "无偏移";	
	if (desc == null)
	  desc = "";	
	var line = $(".mode_table >table >tbody >tr").length + 1;
	var twos = "even";
	if ((line)%2 == 0)
	    twos = "odd";
	  else
	  	twos = "even";
	var tr = '<tr id="mode_item_'+item_id+'\" class=\"'+twos+'\" onclick=\"javascript:choiceModeItem(event)\">'+'\n';
	tr = tr +'<td>'+offsetTimeStr+'</td>'+'\n';
	tr = tr +'<td id=\"device_'+device_id+'\">'+device_name+'</td>'+'\n';
	tr = tr +'<td>'+cmd+'</td>'+'\n';
	tr = tr +'<td>'+desc+'</td>'+'\n';
	tr = tr + '</tr>'+'\n';
	$(".mode_table >table >tbody").append(tr);
}
function choiceModeItem(e)
{
	$(".mode_detail > .mode_table > table > tbody >tr").removeAttr("style");
  if (typeof(e.currentTarget) == "undefined")
  {
  	$(e.target).css("background-color","#26C3BC");
    current_notime_mode_item_id = $(e.target).attr("id").replace("mode_item_","");
    current_device_id = $(e.target).children().eq(1).attr('id').replace("device_",""); 
  }
  else
  {	
    $(e.currentTarget).css("background-color","#26C3BC");
    current_notime_mode_item_id = $(e.currentTarget).attr("id").replace("mode_item_","");
    current_device_id = $(e.currentTarget).children().eq(1).attr('id').replace("device_","");
  }  
}