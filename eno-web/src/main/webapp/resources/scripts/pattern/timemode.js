/* 以下为时序模式管理的公用的javascript  */

var baseurl = CONTEXT_PATH;
var new_mode = "";
var blank_mode = "";
var nowstrategy = {"id":"","name":"","endTime":""};
var strategyList = null;
var actionList = [{"code":"CI","name":"闭店中"},{"code":"C1","name":"商业楼闭店"},{"code":"C2","name":"娱乐楼闭店"},{"code":"R","name":"营业前准备"},{"code":"O","name":"营业"},{"code":"B","name":"营业中"},{"code":"CR","name":"闭店前准备"},{"code":"C","name":"闭店"}];

function getSystemId()
{
	return systemId;
}


//将所有dialog销毁
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

//初始化模式方块列表
function initModeLi()
{
  new_mode = "<li>"+"\n";
  new_mode = new_mode + "<div class=\"create_new_mode\">"+"\n";
  new_mode = new_mode + "<div class=\"cross\"></div>"+"\n";
  new_mode = new_mode + "</div>"+"\n";
  new_mode = new_mode + "</li>"+"\n";
  		 
  blank_mode = "<li>"+"\n";
  blank_mode = blank_mode + "<div class=\"one_time_mode\">"+"\n"
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
  	  for (var i=0;i<3;i++)
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

function renderModeList()
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
  	  	  li_slide = li_slide + renderTimeMode(modeList[index]);	  
  	    }
  	    li_slide = li_slide + new_mode;
  	  }
  	  else //最后一页或第一页  
  	  {
  	     var last_li_count = li_count - i*7;
  	     for (var j=0;j<last_li_count;j++)
  	     { 
  	  	   index = (i*7) + j;
  	  	   li_slide = li_slide + renderTimeMode(modeList[index]);	  
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
    showCreateTimeModeDialog();
  }
  );
  $(".mode_list > ul > li >.one_time_mode >.title").on("click",function(event)
   {
  	  if (($(event.target).attr('class') =='action_group')||($(event.target).attr('class') =='delete')||($(event.target).attr('class') =='edit'))
  	  {
  	  	return;
  	  }
  	  $(".mode_list > ul > li >.one_time_mode >.title").css("background-color","#78CDD0");
  	  $(".mode_list > ul > li >.one_time_mode >.mode_desc").css("border-color","#ffffff");
  	  $(".mode_list >ul >li .action_group").hide();
  	
  	  $(event.currentTarget).css("background-color","#FE9900");
  	  $(event.currentTarget).next().css("border-left","2px solid #FE9900");
  	  $(event.currentTarget).next().css("border-right","2px solid #FE9900");
  	  $(event.currentTarget).next().css("border-bottom","2px solid #FE9900");
  	  $(event.currentTarget).children().eq(1).show();
  	  current_time_mode_id = $(event.currentTarget.parentNode).attr("id").replace('time_mode_',"");
  	  showTimeModeDetail(current_time_mode_id);
  	} 
  );
}

function renderTimeMode(obj)
{
	var str = "";
	str = str +'<li>'+'\n';
	str = str +'<div id="time_mode_'+obj.id+'\" class="one_time_mode">'+'\n';
  str = str +'<div class="title">'+'\n';
  str = str +'<div class="mode_name"><span>'+obj.name+'</span></div>'+'\n';
  str = str +'<div class="action_group">'+'\n';
  str = str +'<div class="delete" onclick="javascript:showConfirmDeleteTimeModeDialog()"></div>'+'\n';
  str = str +'<div class="edit" onclick="javascript:showEditTimeModeDialog()"></div>'+'\n';
  str = str +'</div>'+'\n';
  str = str +'</div>'+'\n';
  str = str +'<div class="mode_desc"><span>'+obj.description+'</span></div>'+'\n';
  str = str +'</div>'+'\n';
  str = str +'</li>'+'\n';
	return str;
}

function showConfirmDeleteTimeModeDialog()
{
	if ($("#time_mode_"+current_time_mode_id).length ==0)
	  return;
	
  var str = "<div id=\"delete_time_mode\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">删除时序模式</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
  str = str + "</br>"+"\n";
  str = str + "</br>"+"\n";
  str = str + "<div>您确认要删除这条时序模式吗?</div>"+"\n";
    
  str = str +	"</div>"+"\n";
  
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
  
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:deleteTimeMode();\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "</div>"+"\n";
  
  $('#mask').html('');
  $('#mask').html(str);
  
  $('#delete_time_mode').css("width","600px");
  $('#delete_time_mode').css("height","200px");
  $('#delete_time_mode').css("margin-left","655px");
  $('#delete_time_mode').css("margin-top","440px");
  $('#delete_time_mode >.body').css('width','500px');
  $('#delete_time_mode >.body').css('height','100px');
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  });
  $('#mask').show();
  $("#delete_time_mode").draggable();
}

function deleteTimeMode()
{
	var url = baseurl+'/pattern/deletePattern';
	var params ={patternId:current_time_mode_id};
	$.ajax({url:url,type:'POST',dataType:'json',async:true,data:params,success:function(data){ 	     
    if (data.success == true)
    {
       showMessageInDialog("删除模式成功!","tip");
       
       if (modeType=="standar")
	     {
	        for (var i=0;i<modeList_standar.length;i++)
          {
       	    if (current_time_mode_id == modeList_standar[i].id)
       	    {
       	      modeList_standar.splice(i,1);
       	      break;
       	    }   
          }
	        modeList = modeList_standar;
	     }
	     else if (modeType=="customer")
	     {	
		      for (var i=0;i<modeList_customer.length;i++)
          {
       	    if (current_time_mode_id == modeList_customer[i].id)
       	    {
       	      modeList_customer.splice(i,1);
       	      break;
       	    }   
          }
		      modeList = modeList_customer; 
	     }	
             
       renderModeList();
       $(".mode_detail >.mode_table >table >tbody").html('');
       $(".mode_detail").hide();
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

function showCreateTimeModeDialog()
{
	var str = "<div id=\"create_time_mode\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">创建新的时序模式</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<div class=\"big_text\">模式名称:</div>"+"\n";
  str = str + "<input type=\"input\" class=\"mode_name\" value=\"\" />";
  
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<div class=\"big_text\">模式描述:</div>"+"\n";
  str = str + "<input type=\"input\" class=\"mode_desc\" value=\"\" />";
  
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  
  for (var i=0;i<scene.length;i++)
  {
  	str = str + "<div class=\"scenelist " +scene[i].code +"\">" +"\n";
  	str = str + "<div class=\"title\">"+ scene[i].name +":</div>"+ "\n";
  	str = str + "<ul>"+"\n";
  	for (var j=0;j<scene[i].item.length;j++)
  	{
  		str = str + "<li><div class=\"btn-small\" code=\""+ scene[i].item[j].id +"\">"+ scene[i].item[j].name +"</div></li>" +"\n";
  	}
  	str = str + "</ul>";
    str = str +	"</div>"+"\n";
  }
   
  str = str +	"</div>"+"\n";
  
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" style=\"margin-left:50px\"></div>"+"\n";
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:createTimeMode();\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "</div>"+"\n";
  
  $('#mask').html('');
  $('#mask').html(str);
  
  $('#create_time_mode').css("width","750px");
  $('#create_time_mode').css("height","520px");
  $('#create_time_mode').css("margin-left","565px");
  $('#create_time_mode').css("margin-top","265px");
  $('#create_time_mode >.body').css('width','650px');
  $('#create_time_mode >.body').css('height','420px');
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  });
  
  $('#mask').show();
  $("#create_time_mode").draggable();
  $("#create_time_mode .body .scenelist .btn-small").click(function(){
		//$("#create_time_mode .body .scenelist .btn-small").removeClass("changeColor");
		$(this).parent().siblings().find(".btn-small").removeClass("changeColor");
		$(this).addClass("changeColor");
	});
	
}

function createTimeMode()
{
	var mode_name = $("#create_time_mode .body .mode_name").val();
  var mode_desc = $("#create_time_mode .body .mode_desc").val();
		
	var code = ""
	//var name = "";
	var param = null;
	//var paramList = new Array();
	var paramList = '[';
		
	if (mode_name =="")
	{
		showMessageInDialog("请为这个模式设定一个名称","error");
		return;
	}
	
	for(var i=0;i<scene.length;i++)
	{
		if ($("#create_time_mode .body ." +scene[i].code +" .changeColor").length !=1)
		{
		  showMessageInDialog("请选择一个"+scene[i].name,"error");
		  return;
		}
		else
		{
			code = $("#create_time_mode .body ." +scene[i].code +" .changeColor").attr("code");
			param = '{"code":"'+code+'"}';
			paramList = paramList + param;
			if (i<scene.length-1)
			  paramList = paramList + ',';
			
		}	  
	}
	paramList = paramList + ']';
	var url = baseurl+'/pattern/saveSystemPatternTime';
	var system_id = getSystemId();
    var patternType=transferPatternType(modeType);
	var params ={systemId:system_id,patternType:patternType,patternName:mode_name,patternDescription:mode_desc,factor:paramList};
	$.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){ 	     
    if (data.success == true)
    {
    	showMessageInDialog("模式创建成功!","tip");
    	addNewTimeMode(data.patternId,mode_name,mode_desc,data.author);
    	goLastPage();
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


function addNewTimeMode(mode_id,mode_name,mode_desc,author)
{
	var newMode = {"id":mode_id,"name":mode_name,"description":mode_desc,"author":author};
	
	if (modeType=="standar")
	{
	  modeList_standar.push(newMode);
	  modeList = modeList_standar;
	}
	else if (modeType=="customer")
	{	
		modeList_customer.push(newMode);
		modeList = modeList_customer; 
	}	
	
	renderModeList();
	$(".mode_list > ul > li >.one_time_mode >.title").css("background-color","#78CDD0");
  $(".mode_list > ul > li >.one_time_mode >.mode_desc").css("border-color","#ffffff");
	$("#time_mode_"+mode_id).children().eq(0).css("background-color","#FE9900");
	$("#time_mode_"+mode_id).children().eq(0).children().eq(1).show();
	$("#time_mode_"+mode_id).children().eq(1).css("border-left","2px solid #FE9900");
  $("#time_mode_"+mode_id).children().eq(1).css("border-right","2px solid #FE9900");
  $("#time_mode_"+mode_id).children().eq(1).css("border-bottom","2px solid #FE9900");
  current_time_mode_id = mode_id;
  $(".mode_detail >.mode_table >table >tbody").html('');
  $(".mode_detail").show();
  $(".mode_detail >.actionbar").show();
}

function showEditTimeModeDialog()
{
	var mode_name = $("#time_mode_"+current_time_mode_id+" .mode_name >span").html();
	var mode_desc = $("#time_mode_"+current_time_mode_id+" .mode_desc >span").html();
	
	var str = "<div id=\"edit_time_mode\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">编辑时序模式</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
  
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<div class=\"big_text\">模式名称:</div>"+"\n";
  str = str + "<input type=\"input\" class=\"mode_name\" value=\""+ mode_name+"\" />";
  
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<div class=\"big_text\">模式描述:</div>"+"\n";
  str = str + "<input type=\"input\" class=\"mode_desc\" value=\""+ mode_desc+"\" />";
  
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  str = str + "<br/>"+"\n";
  
  for (var i=0;i<scene.length;i++)
  {
  	str = str + "<div class=\"scenelist " +scene[i].code +"\">" +"\n";
  	str = str + "<div class=\"title\">"+ scene[i].name +":</div>"+ "\n";
  	str = str + "<ul>"+"\n";
  	for (var j=0;j<scene[i].item.length;j++)
  	{
  		if (findScene(scene[i].item[j].id) == true)
  		  str = str + "<li><div class=\"btn-small changeColor\" code=\""+ scene[i].item[j].id +"\">"+ scene[i].item[j].name +"</div></li>" +"\n";
  		else
  		  str = str + "<li><div class=\"btn-small\" code=\""+ scene[i].item[j].id +"\">"+ scene[i].item[j].name +"</div></li>" +"\n";
  	}
  	str = str + "</ul>";
    str = str +	"</div>"+"\n";
  }
  str = str +	"</div>"+"\n";
  
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" style=\"margin-left:50px\"></div>"+"\n";
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:saveTimeMode();\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "</div>"+"\n";
  
  $('#mask').html('');
  $('#mask').html(str);
  
  $('#edit_time_mode').css("width","750px");
  $('#edit_time_mode').css("height","520px");
  $('#edit_time_mode').css("margin-left","565px");
  $('#edit_time_mode').css("margin-top","290px");
  $('#edit_time_mode >.body').css('width','650px');
  $('#edit_time_mode >.body').css('height','420px');
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  });
  
  $('#mask').show();
	$("#edit_time_mode").draggable();
	$("#edit_time_mode .body .scenelist .btn-small").click(function(){
		$(this).parent().siblings().find(".btn-small").removeClass("changeColor");
		$(this).addClass("changeColor");
	});
}

function findScene(sceneId)
{
	var tempModeList ;
	if (modeType=="standar")
	  tempModeList = modeList_standar;
	else if (modeType=="customer")
	  tempModeList = modeList_customer;
	else
		return false;
		   		
	var findit ;
	for (var i=0;i<tempModeList.length;i++)
	{
	  if(tempModeList[i].id == current_time_mode_id)
	  {
		   findit = false;
		   for (var j=0;j<tempModeList[i].ucPatternFactors.length;j++)
		   {
		     if (tempModeList[i].ucPatternFactors[j].ucFactor.id == sceneId)
		     {
		  	   findit = true;
		  	   break;
		     }
	     }
	     break;
	  }
	}
	return findit;
}

function saveTimeMode()
{
	var mode_name = $("#edit_time_mode .body .mode_name").val();
	var mode_desc = $("#edit_time_mode .body .mode_desc").val();
	var code = ""
	var name = "";
	var param = null;
	//var paramList = new Array();
	var paramList = '[';
	
	if (mode_name =="")
	{
		showMessageInDialog("请为这个模式设定一个名称","error");
		return;
	}
	
	for(var i=0;i<scene.length;i++)
	{
		if ($("#edit_time_mode .body ." +scene[i].code +" .changeColor").length !=1)
		{
		  showMessageInDialog("请选择一个"+scene[i].name,"error");
		  return;
		}
		else
		{
			code = $("#edit_time_mode .body ." +scene[i].code +" .changeColor").attr("code");
			param = '{"code":"'+code+'"}';
			paramList = paramList +param;
			if (i<scene.length-1)
			  paramList = paramList + ',';
			//paramList.push(param);
			name = $("#edit_time_mode .body ." +scene[i].code +" .changeColor").html();
			//mode_name = mode_name + name;
		}	  
	}
	paramList = paramList + ']';
	var url = baseurl+'/pattern/saveSystemPatternTime';
    var patternType=transferPatternType(modeType);
    var system_id = getSystemId();
	var params ={systemId:system_id,patternId:current_time_mode_id,patternType:patternType,patternName:mode_name,patternDescription:mode_desc,factor:paramList};
	$.ajax({url:url,type:'POST',dataType:'json',async:true,data:params,success:function(data){ 	     
  if (data.success == true)
    {
    	showMessageInDialog("模式修改成功!","tip");
    	modifyTimeMode(mode_name,mode_desc,paramList);
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

function modifyTimeMode(mode_name,mode_desc,paramList)
{
	$("#time_mode_"+current_time_mode_id+" .mode_name >span").html(mode_name);
	$("#time_mode_"+current_time_mode_id+" .mode_desc >span").html(mode_desc);
	var sceneList = eval('('+paramList+')');
	if (modeType=="standar")
	{
	    for (var i=0;i<modeList_standar.length;i++)
      {
        if (current_time_mode_id == modeList_standar[i].id)
        {
          modeList_standar[i].name = mode_name;
		      modeList_standar[i].description = mode_desc;
		      for (var j=0;j<modeList_standar[i].ucPatternFactors.length;j++)
		      {
		         modeList_standar[i].ucPatternFactors[j].ucFactor.id = sceneList[j].code;
          }
          break;
        }   
      }
	    modeList = modeList_standar;
	}  
	else if (modeType=="customer")
	{	
	   for (var i=0;i<modeList_customer.length;i++)
     {
       if (current_time_mode_id == modeList_customer[i].id)
       {
         modeList_customer[i].name = mode_name;
			   modeList_customer[i].description = mode_desc;
         for (var j=0;j<modeList_customer[i].ucPatternFactors.length;j++)
		      {
		         modeList_customer[i].ucPatternFactors[j].ucFactor.id = sceneList[j].code;
          }
         break;
       }   
     }
		 modeList = modeList_customer; 
	}
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

function showTimeModeDetail()
{
	$(".mode_detail > .mode_table > table > tbody").html('');	
	$(".mode_detail").show();
	var url = baseurl+'/pattern/getPatternItem';

    var d = new Date();
    var r = Math.random()+""+d.getMilliseconds();
    var params ={patternId:current_time_mode_id,r:r};

	$.ajax({url:url,type:'GET',dataType:'json',async:true,data:params,success:function(data){ 
	
	var modeItemList = transferUcPatternToItem(data);	     
  for (var i=0;i<modeItemList.length;i++)
  {
  	addNewTimeModeItem(modeItemList[i]);
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

function initTableTh()
{
	var th = "";
	for (var i=0;i<property.length;i++)
	{
		th = "<th class=\""+ property[i].code +"\">"+ property[i].name +"</th>";
		$(".mode_table >table >thead >tr").append(th);
	}
}

function choiceModeItem(e)
{
  $(".mode_detail > .mode_table > table > tbody >tr").removeAttr("style");
  if (typeof(e.currentTarget) == "undefined")
  {
    current_time_mode_item_id = $(e.target).attr("id").replace("mode_item_","");
    $(e.target).css("background-color","#26C3BC");
  }
  else
  {	
    current_time_mode_item_id = $(e.currentTarget).attr("id").replace("mode_item_","");
    $(e.currentTarget).css("background-color","#26C3BC");
  }  
}

function sortField(param)
{
	var findit = false;
	var newParam = new Array();
	for (var i=0;i<property.length;i++)
	{
	  findit = false;
	  for(var j=0;j<param.length;j++)
	  {
	  	if (property[i].code == param[j].code)
	  	{
	  		newParam.push(param[j]);
	  		findit = true;
	  		break;
	  	}
	  }
	  if (!findit)
	  {
	  	newParam.push({"code":property[i].code,"value":"","type":property[i].type});
	  }
	}
	return newParam;
}

function changeOn(e)
{
	if ($(e).html() == "ON")
	{
		$(e).html("OFF");
		$(e).css("background-color","#666666");
	}
	else if ($(e).html() == "OFF")
	{
		$(e).html("ON");
		$(e).removeAttr("style");
	}	
  else
   return;		
}

function showConfirmDeleteModeItemDialog()
{
  if ($("#mode_item_"+current_time_mode_item_id).length ==0)
	  return;
	
  var str = "<div id=\"delete_mode_item\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">删除模式信息</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
  str = str + "</br>"+"\n";
  str = str + "</br>"+"\n";
  str = str + "<div>您确认要删除这条模式信息?</div>"+"\n";
    
  str = str +	"</div>"+"\n";
  
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
  
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:deleteTimeModeItem();\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "</div>"+"\n";
  
  $('#mask').html('');
  $('#mask').html(str);
  
  $('#delete_mode_item').css("width","600px");
  $('#delete_mode_item').css("height","200px");
  $('#delete_mode_item').css("margin-left","655px");
  $('#delete_mode_item').css("margin-top","440px");
  $('#delete_mode_item >.body').css('width','500px');
  $('#delete_mode_item >.body').css('height','100px');
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  });
  $('#mask').show();
  $("#delete_mode_item").draggable();
}

function deleteTimeModeItem()
{
	var url = baseurl+'/pattern/deletePatternAction';
	var params ={itemId:current_time_mode_item_id,patternId:current_time_mode_id};
	$.ajax({url:url,type:'POST',dataType:'json',async:true,data:params,success:function(data){ 	     
    if (data.success == true)
    {
       showMessageInDialog("删除模式参数记录成功!","tip");
       $("#mode_item_"+current_time_mode_item_id).remove();
	     $(".mode_detail > .mode_table > table > tbody >tr").each(function(index){
	      if ((index + 1)%2 != 0)
	  	    $(this).attr('class',"even");
	      else
	  	    $(this).removeClass("even"); 	
	     }
	     );
	     resetScrollBar();
	     var t=setTimeout('hideDialog()',1500);    
    }
    else
    {
    	showMessageInDialog("删除模式参数记录失败,请稍后再试!","error");
    	var t=setTimeout('hideDialog()',1500);
    }
  }
  }
  );
}

function showChoiceStrategyDialog()
{
	var str = "<div class=\"choiceStrategy\">";
	str = str + "<div class=\"title\">请在下面选择任意一个策略</div>"; 
	str = str + "<div class=\"strategy_list\">";
	str = str + "<ul>";
  loadStrategyList();
  for (var i=0;i<strategyList.length;i++)
  {
  	str = str + "<li id=\"strategy_"+strategyList[i].id+"\" class=\"strategy\">"+strategyList[i].name+"</li>";
  }
	str = str + "</ul>"
	str = str + "</div>";
	
	str = str + "<div style=\"margin-left:25px\">";
	str = str + "<div>请输入策略结束的时间</div>";
	str = str + "<div id=\"strategyendtimedatatimepicker\" class=\"input-append\">";	
  str = str + "<input data-format=\"hh:mm\" type=\"input\" id=\"strategy_endTime\" value=\"\" />";
  str = str + "<span class=\"add-on\">"+"\n";
  str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
  str = str + "</span>";
  str = str + "</div>";
	str = str + "</div>";
	
	str = str + "<div class=\"button_group\" style=\"width:300px\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:unChoiceStrategy();\">不使用策略</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:choiceStrategy();\">使用策略</div>"+"\n";
  str = str +	"</div>"+"\n";
  
	str = str + "</div>";
	$("#mask .choiceStrategy").remove(); 
  
  $("#mask").append(str); 	
  $('#mask .choiceStrategy .cancle').on('click',function(event){
  	$("#mask .choiceStrategy").remove(); 
  });
  
  $('#mask .choiceStrategy .strategy_list >ul > li').on('click',function(event){
  	$(this).siblings().removeClass("selected");
  	$(this).addClass("selected");
  });
  
	$('#strategyendtimedatatimepicker').datetimepicker({
      maskInput: true,           // disables the text input mask
      pickDate: false,            // disables the date picker
      pickTime: true,            // disables de time picker
      pick12HourFormat: false,   // enables the 12-hour format time picker
      pickSeconds: false         // disables seconds in the time picker
    });
}

function choiceStrategy()
{
	if ($("#mask .choiceStrategy .strategy_list >ul >li.selected").length ==0)
	{
	  return;
	}
	var tempValue = $("#mask .choiceStrategy #strategy_endTime").val();
  if (! isTime(tempValue))
	{
		return;
	}  
	nowstrategy.id = $("#mask .choiceStrategy .strategy_list >ul >li.selected :eq(0)").attr("id").replace("strategy_","");
	nowstrategy.name = $("#mask .choiceStrategy .strategy_list >ul >li.selected :eq(0)").html();
	nowstrategy.endTime = formateTime(tempValue);
	$("#mask .choiceStrategy").remove(); 
	$(".dialog .body .mode_item_info .now_strategy > .strategy_name").html("当前选择的策略 : "+nowstrategy.name);
  $(".dialog .body .mode_item_info .strategyFailTip").show();
}

function unChoiceStrategy()
{
	nowstrategy.id = "";
	nowstrategy.name = "";
	nowstrategy.endTime = "";
	$("#mask .choiceStrategy").remove(); 
	$(".dialog .body .mode_item_info .now_strategy > .strategy_name").html("当前未使用任何策略");
  $(".dialog .body .mode_item_info .strategyFailTip").hide();
}

function getStrategyNameById(strategyId)
{
	if(strategyId=="")
	  return "";
	  
	var name = "";  
	loadStrategyList();
	for (var i=0;i<strategyList.length;i++)
	{
		if (strategyList[i].id == strategyId)
		{
			name = strategyList[i].name;
			break;
		}
	}
	return name;
}

function loadStrategyList()
{
	if (strategyList != null)
	  return;
	strategyList = [];
	var strategy = null;
	var url = baseurl+'/pattern/getStrategy';
	var system_id = getSystemId();
	var params ={systemId:system_id};
	$.ajax({url:url,type:'GET',dataType:'json',async:false,data:params,success:function(data){ 	     
   for (var i=0;i<data.length;i++)
    {
       strategy = {"id":data[i].id,"name":data[i].strategyName};
       strategyList.push(strategy);     
    }     
	}
  }
  );
}

//判断是否是数字
function isNumber(inputNumber)
{
	var sReNumber = /^[0-9]+$/;
	if (sReNumber.test(inputNumber)) {
		return true;
	}
	else {
		return false;
	}
}

//判断是否是时间，时间的格式为hh:mm
function isTime(input)
{
	var diffIndex = input.indexOf(":");
	if ((diffIndex <1)||(input.length<3))
	  return false;
	
	var hour = input.substring(0,diffIndex);
	var minute = input.substring(diffIndex+1);
  
	if ((! isNumber(hour))||(! isNumber(minute))||(parseInt(hour)>23)||(parseInt(minute)>59))
		return false;
	else
		return true;  
}

//将带冒号的时间转为hhmm
function formateTime(input)
{
	var diffIndex = input.indexOf(":");
	if ((diffIndex <1)||(input.length<3))
	  return false;
	
	var hour = input.substring(0,diffIndex);
	var minute = input.substring(diffIndex+1);
	
	if (hour.length<2)
	  hour = "0"+hour;
	if (minute.length<2)
	  minute = "0"+minute;  
	  
	return hour+""+minute;  
}

//在hhmm格式的时间的中间加上冒号
function timeToString(input)
{
	var hour = input.substring(0,2);
	var minute = input.substring(2,4);
		  
	return hour+":"+minute; 
}

function getCodeAndTypeFromDbCode(dbCode)
{
	var codeType = {"code":"","type":""};
	for (var i=0;i<property.length;i++)
	{
		if (dbCode == property[i].dbCode)
		{
		  codeType.code = property[i].code;
		  codeType.type = property[i].type;
		  break;
		}  
	}
	
	return codeType;
}

function getDbCodeFromCode(code)
{
	var dbCode = "";
	for (var i=0;i<property.length;i++)
	{
		if (code == property[i].code)
		{
		  dbCode = property[i].dbCode;
		  break;
		}  
	}
	return dbCode;
}

//从分钟的整数类型，转换为hhmm
function getHhmmFromMinute(input)
{
	var hh = "";
	var mm = "";
	hh = Math.floor(input/60) + "";
	mm = (input % 60) + "";
	
	if (hh.length < 2)
	  hh = "0"+hh;
	  
	if (mm.length <2)
	  mm = "0"+mm;
	  
	return hh+""+mm;  
}

//从hhmm转换为分钟的整数类型
function getMinuteFromHhmm(input)
{
	var h= input.substring(0,2);
  var m= input.substring(2, input.length);
  return parseInt(h)*60+parseInt(m);
}

function showChoiceTemplete(event)
{
	$(".templete_ul").addClass("show_style");
	$(".hide_templete").show();
	$(".display_templete").addClass("border_botom_line");
	event.stopPropagation();
}

function hideChoiceTemplete(li,type)
{
	var text = $(li).text();
	$(".display_templete>span:eq(0)").text(text);
	//$(".down_icon_2").hide();
	$(".templete_ul").removeClass("show_style");
	$(".display_templete").removeClass("border_botom_line");
	//$(".down_icon_2").hide();
	//$(li).find(".down_icon_2").show();
	$(li).parent().parent().hide();
	
	if(type ==modeType)
	   return;
	
	modeType = type;
	if (modeType=="standar")
	  modeList = modeList_standar;
	else if (modeType=="customer")
		modeList = modeList_customer; 
  
  renderModeList();
  $(".mode_detail > .mode_table > table > tbody").html('');	 
  $(".mode_detail").hide();
}

function hideTemplete()
{
  $(".templete_ul").removeClass("show_style");
	$(".hide_templete").hide();
	$(".display_templete").removeClass("border_botom_line");
}

function transferPatternType(pType){
    if (pType=="standar")
        return "S";
    else if (pType=="customer")
        return "C";
}

function uploadPatternItem()
{
	if ($("#time_mode_"+current_time_mode_id).length ==0)
	  return;
	
  var str = "<div id=\"upload_time_mode\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">上传时序模式设定表</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
  str = str + "</br>"+"\n";
  
  str = str + "<label for=\"image_file\">请点击\"浏览\"按钮选择EXCEL文件后点击\"确定\"，开始上传的EXCEL文件</label>";
	
	str = str + "<div class=\"file-box\">" 
	str = str + "<div class=\"button\">浏览</div>";
	str = str + "<input type='text' name='filetextfield' id='filetextfield' class='filename' style='border:0px;margin-bottom:0px'/>";
	str = str + "<input id=\"fileupload\" type=\"file\" class=\"file\" style=\"left:0px;width:60px\" name=\"files[]\" onchange=\"document.getElementById('filetextfield').value=this.value\" data-url=\""+baseurl+"/pattern/upload?patternId="+current_time_mode_id+"\"/>";
  str = str + "</div>";
  str = str + "</br>"+"\n";
  str = str +"<div id=\"progress\">";
  str = str +"<div class=\"bar\" style=\"width: 0%;\"></div>";
     
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
  
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button startupload\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "</div>"+"\n";
  
  $('#mask').html('');
  $('#mask').html(str);
  
  $('#upload_time_mode').css("width","600px");
  $('#upload_time_mode').css("height","300px");
  $('#upload_time_mode').css("margin-left","655px");
  $('#upload_time_mode').css("margin-top","440px");
  $('#upload_time_mode >.body').css('width','500px');
  $('#upload_time_mode >.body').css('height','200px');
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  });
  $('#mask').show();
  $("#upload_time_mode").draggable();
  var startupload =  $("#upload_time_mode .button_group .startupload");
    $('#fileupload').fileupload({
        dataType: 'json',
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                'width',
                progress + '%'
            );
        },
        add: function (e, data) {
            startupload.click(function () {
                var jqXHR = data.submit()
                    .success(function (result,textStatus,jqXHR){
                        if (result.success == true)
                        {
                            showMessageInDialog("模式记录批量导入成功!","tip");
                            var t=setTimeout('hideDialog()',1500);
                            showTimeModeDetail(current_time_mode_id);
                        }
                        else
                        {
                            showMessageInDialog("模式记录批量导入失败，请稍后再试!","error");
                        }
                    })
                    .error(function (jqXHR, textStatus, errorThrown){
                        showMessageInDialog("模式记录批量导入失败，请稍后再试!","error");
                    })
            });
        }
    });
}

function downloadPatternItem()
{
	var fileUrl = baseurl+"/pattern/download?patternId="+current_time_mode_id;
	window.open(fileUrl);
}