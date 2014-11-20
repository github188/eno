var baseurl = CONTEXT_PATH;
//var baseurl = "";
var new_strategy = "";
var blank_strategy = "";
var operator = "create";
var flag_1 = 0;
var flag_2 = 0;
var flag_3 = 0;
var	flag_4 = 0;
var	flag_5 = 0;
var deviceItemId = "";


var currentStrategy = {"id":"","name":"","pic":"","description":"","ifs":{"name":"","itemCode":"","paramCode":""},"operator":"=","comparison":{"name":"","type":"","value":"","itemCode":"","paramCode":""},"then":[]};

function strategyToString(strategy)
{
	var json= "";
	json = '{"id":"'+strategy.id+'","name":"'+strategy.name+'","description":"'+strategy.description+'",';
	json = json + '"ifs":' +'{"name":' + strategy.ifs.name+'","itemCode":"'+strategy.ifs.itemCode+'","paramCode":"'+strategy.ifs.paramCode+'"},';
	json = json + '"operator":"'+strategy.operator+'",';
	json = json + '"comparison":'+'{"name":"'+strategy.comparison.name+'","type":"'+strategy.comparison.type+'","value":"'+strategy.comparison.value+'","itemCode":"'+strategy.comparison.itemCode+'","paramCode":"'+strategy.comparison.paramCode+'"},';
	json = json + '"then":[';
	for (var i=0;i<strategy.then.length;i++)
	{
		if (i==strategy.then.length-1)
		  json = json + '{"deviceIndex":{"code":"' + strategy.then[i].deviceIndex.code + '","value":"'+strategy.then[i].deviceIndex.value+'","type":"' +strategy.then[i].deviceIndex.type+'"}';
		else
			json = json + '{"deviceIndex":{"code":"' + strategy.then[i].deviceIndex.code + '","value":"'+strategy.then[i].deviceIndex.value+'","type":"' +strategy.then[i].deviceIndex.type+'"},';
	}
	json = json + ']}';
	return json;
}

function transferStrategy(strategy)
{
	var json= "{";
	if (strategy.id !=null)
	  json = json + '"id":"'+strategy.id+'",';
	var system_id = getSystemId();
	json = json + '"systemId":"'+system_id+'",';   
	json = json + '"strategyName":"'+strategy.name+'","strategyDescription":"'+strategy.description+'",';
	json = json + '"itemMain":"' + strategy.ifs.itemCode+'","itemParam":"'+strategy.ifs.paramCode+'",';
	json = json + '"compareSymbol":"'+strategy.operator+'",';
	if (strategy.comparison.type == "number")
	  json = json + '"valueType":"N"';
	else if (strategy.comparison.type == "item")
		json = json + '"valueType":"P"';
	else if (strategy.comparison.type == "boolean")
	{
	  if (strategy.comparison.value == true)
		  json = json + '"valueType":"T"';
    else if (strategy.comparison.value == false)
		  json = json + '"valueType":"F"';	
	}		 
	json = json + ',"compareValue":"'+strategy.comparison.value+'","compareMain":"'+strategy.comparison.itemCode+'","compareParam":"'+strategy.comparison.paramCode+'",';
	json = json + '"ucStrategyParams":[';
	for (var i=0;i<strategy.then.length;i++)
	{
		if (i==strategy.then.length-1)
		  json = json + '{"paramName":"' + strategy.then[i].deviceIndex.code + '","paramValue":"'+strategy.then[i].deviceIndex.value+'","valueType":"' +strategy.then[i].deviceIndex.type+'"}';
		else
			json = json + '{"paramName":"' + strategy.then[i].deviceIndex.code + '","paramValue":"'+strategy.then[i].deviceIndex.value+'","valueType":"' +strategy.then[i].deviceIndex.type+'"},';
	}
	json = json + ']}';
	return json;
}




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

// 在策略编辑面板的最后一步给用户一些操作提示，比如输入有效性检查等
function showMessage(msg,message_type)
{
	if (message_type =="error")
	  $(".swap_mod_4 .strategy_panel .operator_tip #dialogTip").attr('class','error');
	else
		$(".swap_mod_4 .strategy_panel .operator_tip #dialogTip").attr('class','tip');	
	$(".swap_mod_4 .strategy_panel .operator_tip #dialogTip").html(msg);
  var t=setTimeout('clearMessage()',3000);
}
// 将策略面板最后一步中的操作提示删除掉
function clearMessage()
{
	$(".swap_mod_4 .strategy_panel .operator_tip #dialogTip").html('');
}

//减少marginTop，向上滚动
function decreaseMarginTop()
{
	var one_slide_height = $(".strategy_list >ul >li").outerHeight(true)*2; //显示2排
	var mt = parseInt($(".strategy_list >ul").css('margin-top'));
	var ul_h = $(".strategy_list >ul").height();
	if (mt <= -(ul_h - one_slide_height))
	  return;
	mt = mt - one_slide_height;
	$(".strategy_list >ul").animate(
	{
	 'margin-top':mt+"px"
	},
	500
	);
}
//增大marginTop，向下滚动
function increaseMarginTop()
{
	var one_slide_height = $(".strategy_list >ul >li").outerHeight(true)*2; //显示2排
	var mt = parseInt($(".strategy_list >ul").css('margin-top'));
	if (mt >=0)
	  return;
	mt = mt + one_slide_height;
	$(".strategy_list >ul").animate(
	{
	 'margin-top':mt+"px"
	},
	500
	);
}

//初始化策略列表
function initStrategyLi()
{
  new_strategy = "<li>"+"\n";
  new_strategy = new_strategy + "<div class=\"create_new_strategy\">"+"\n";
  new_strategy = new_strategy + "<div class=\"cross\"></div>"+"\n";
  new_strategy = new_strategy + "</div>"+"\n";
  new_strategy = new_strategy + "</li>"+"\n";
  		 
  blank_strategy = "<li>"+"\n";
  blank_strategy = blank_strategy + "<div class=\"one_strategy\">"+"\n"
  blank_strategy = blank_strategy +"</div>"+"\n";
  blank_strategy = blank_strategy + "</li>"+"\n";
}

//在界面中画出一个创建策略的十字方框以及空的策略方框
function renderCreateNewLi(li_count)
{
	switch(li_count)
  {
    case 1:
  	  li_str = "";
  	  for (var i=0;i<3;i++)
  	  {
  	    li_str = li_str + blank_strategy;
  	  }
  	  li_str = li_str + new_strategy;
  	  for (var i=0;i<3;i++)
  	  {
  	    li_str = li_str + blank_strategy;
  	  }
  	  break;
  	case 2:
  	  li_str = "";
  	  for (var i=0;i<2;i++)
  	  {
  	    li_str = li_str + blank_strategy;
  	  }
  	  li_str = li_str + new_strategy;
  	  for (var i=0;i<3;i++)
  	  {
  	    li_str = li_str + blank_strategy;
  	  }
  	  break;
  	case 3:
  	  li_str = blank_strategy + new_strategy;
  	  for (var i=0;i<3;i++)
  	  {
  	    li_str = li_str + blank_strategy;
  	  }
  	  break;
  	case 4:
  	  li_str = new_strategy;
  	  for (var i=0;i<3;i++)
  	  {
  	    li_str = li_str + blank_strategy;
  	  }
  	  break;
  	case 5:
  	  li_str = new_strategy;
  	  for (var i=0;i<2;i++)
  	  {
  	    li_str = li_str + blank_strategy;
  	  }
  	  break;
  	case 6:
  	  li_str = new_strategy;
  	  for (var i=0;i<1;i++)
  	  {
  	    li_str = li_str + blank_strategy;
  	  }
  	  break; 
  	case 7:
  	  li_str = new_strategy;
  	  break;  
  }
  return li_str;
}

//在界面中画出一个策略的方框
function renderStrategy(obj)
{
	var str = "";
	str = str +'<li>'+'\n';
	str = str +'<div id="strategy_'+obj.id+'\" class="one_strategy">'+'\n';
  str = str +'<div class="title">'+'\n';
  str = str +'<div class="strategy_name"><span>'+obj.strategyName+'</span></div>'+'\n';
  str = str +'<div class="action_group">'+'\n';
  str = str +'<div class="delete" onclick="javascript:showConfirmDeleteStrategyDialog()"></div>'+'\n';
  str = str +'<div class="edit" onclick="javascript:begainEditStrategy()"></div>'+'\n';
  str = str +'</div>'+'\n';
  str = str +'</div>'+'\n';
  if (typeof(obj.author) =="undefined")
    str = str +'<div class="strategy_desc"><span></span></div>'+'\n';
  else  
    str = str +'<div class="strategy_desc"><span>'+obj.author+'</span></div>'+'\n';
  str = str +'</div>'+'\n';
  str = str +'</li>'+'\n';
	return str;
}
//在界面中将所有策略的方框排列，一页2排4列
function renderStrategyList()
{
	var li_str = "";
	var li_slide = "";
	
	var li_count = strategyList.length;
    
  if (li_count == 0)  //一条记录都没有
  {
  	li_str = new_strategy;
  	for (var i=0;i<6;i++)
  	{
  	  li_str = li_str + blank_strategy;
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
  	  	  li_slide = li_slide + renderStrategy(strategyList[index]);	  
  	    }
  	    li_slide = li_slide + new_strategy;
  	  }
  	  else //最后一页或第一页  
  	  {
  	     var last_li_count = li_count - i*7;
  	     for (var j=0;j<last_li_count;j++)
  	     { 
  	  	   index = (i*7) + j;
  	  	   li_slide = li_slide + renderStrategy(strategyList[index]);	  
  	     }
  	     li_slide = li_slide + renderCreateNewLi(last_li_count); 
  	  }
  	  li_str = li_str + li_slide;
  	} 
  }
  $(".strategy_list > ul").html(li_str);
  $(".strategy_list  .action_group").hide(); 
  $(".strategy_list > ul > li >.create_new_strategy").on("click",function(event)
  {
    operator = "create";
    showStrategyStep1();
  }
  );
  $(".strategy_list > ul > li >.one_strategy >.title").on("click",function(event)
   {
  	  if (($(event.target).attr('class') =='action_group')||($(event.target).attr('class') =='delete')||($(event.target).attr('class') =='edit'))
  	  {
  	  	return;
  	  }
  	  $(".strategy_list > ul > li >.one_strategy >.title").css("background-color","#78CDD0");
  	  $(".strategy_list > ul > li >.one_strategy >.strategy_desc").css("border-color","#ffffff");
  	  $(".strategy_list >ul >li .action_group").hide();
  	
  	  $(event.currentTarget).css("background-color","#FFCC33");
  	  $(event.currentTarget).next().css("border-left","2px solid #FFCC33");
  	  $(event.currentTarget).next().css("border-right","2px solid #FFCC33");
  	  $(event.currentTarget).next().css("border-bottom","2px solid #FFCC33");
  	  $(event.currentTarget).children().eq(1).show();
  	  current_strategy_id = $(event.currentTarget.parentNode).attr("id").replace('strategy_',"");
  	  showStrategyDetail();
  	} 
  );
}

//显示一个确认是否删除策略的对话框
function showConfirmDeleteStrategyDialog()
{
	if ($("#strategy_"+current_strategy_id).length ==0)
	  return;
	
  var str = "<div id=\"delete_strategy\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">删除策略</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
  str = str + "</br>"+"\n";
  str = str + "</br>"+"\n";
  str = str + "<div>您确认要删除这条策略吗?</div>"+"\n";
    
  str = str +	"</div>"+"\n";
  
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
  
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:deleteStrategy();\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "</div>"+"\n";
  
  $('#mask').html('');
  $('#mask').html(str);
  
  $('#delete_strategy').css("width","600px");
  $('#delete_strategy').css("height","200px");
  $('#delete_strategy').css("margin-left","655px");
  $('#delete_strategy').css("margin-top","440px");
  $('#delete_strategy >.body').css('width','500px');
  $('#delete_strategy >.body').css('height','100px');
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  });
  $('#mask').show();
  $("#delete_strategy").draggable();
}
//删除一个策略，如果删除成功，将本地缓存的策略数组中的数据也删除掉，然后重新画界面
function deleteStrategy()
{
	var url = baseurl+'/strategy/deleteStrategy';
	var params ={strategyId:current_strategy_id};
	$.ajax({url:url,type:'POST',dataType:'json',async:true,data:params,success:function(data){ 	     
    if (data.success == true)
    {
       showMessageInDialog("策略删除成功!","tip");
       for (var i=0;i<strategyList.length;i++)
       {
       	  if (current_strategy_id == strategyList[i].id)
       	  {
       	    strategyList.splice(i,1);
       	    break;
       	  }  
       }
       renderStrategyList();
       $(".swap_mod_1").hide();
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

//开始编辑一个策略
function begainEditStrategy()
{
	operator = "edit";
	showStrategyStep1();
}

//显示策略创建/编辑的第一步界面
function showStrategyStep1()
{
	$(".swap_mod_1").hide();
  $(".swap_mod_3").hide();
  $(".swap_mod_4").hide();
  
  $(".swap_mod_2").show(); 
  
  $(".swap_mod_2 .strategy_panel .sub_mode_choice").html('');
  var div = "";
  
  for (var i=0;i<deviceItem.length;i++)
  {
    div = "<div>";
    div = div + "<div id=\"deviceItem_" + deviceItem[i].id+"\" class=\""+deviceItem[i].itemValue +"\">";
    div = div + "<p>"+deviceItem[i].itemName+"</p>";
    div = div + "</div>";
    div = div + "<i class=\"icon_arrow\"></i>";
    div = div + "</div>";
    $(".swap_mod_2 .strategy_panel .sub_mode_choice").append(div);
  } 
  deviceItemId = deviceItem[0].id; //默认显示第一个的参数
  $(".swap_mod_2 .strategy_panel .sub_mode_choice div div").click(function(){
		flag_1 = 1;
		$(".swap_mod_2 .strategy_panel .sub_mode_choice div div").removeClass("wind_highlight");
		$(this).addClass("wind_highlight");
		deviceItemId = $(this).attr("id").replace("deviceItem_","");
		$(".swap_mod_2 .strategy_panel .step_2_para").html('');
    $(".swap_mod_2 .strategy_panel .step_2_para").append(loadDeviceParam());
    $(".swap_mod_2 .strategy_panel .step_2_para div").click(function(){
		  flag_2 = 1;
		  $(".swap_mod_2 .strategy_panel .step_2_para div").removeClass("temp_highlight");
		  $(".swap_mod_2 .strategy_panel .step_2_para div").find("p").removeClass("word_change");
		  $(this).addClass("temp_highlight");
		  $(this).find("p").addClass("word_change");
	  });
	});
	
	if (operator=="edit")
	{
		$(".swap_mod_2 .strategy_panel .sub_mode_choice div ."+currentStrategy.ifs.itemCode+"").addClass("wind_highlight");
		flag_1 = 1;
		deviceItemId = $(".swap_mod_2 .strategy_panel .sub_mode_choice div ."+currentStrategy.ifs.itemCode+"").attr("id").replace("deviceItem_","");
	}
	$(".swap_mod_2 .strategy_panel .step_2_para").html('');
  $(".swap_mod_2 .strategy_panel .step_2_para").append(loadDeviceParam());
	$(".swap_mod_2 .strategy_panel .step_2_para div").click(function(){
		flag_2 = 1;
		$(".swap_mod_2 .strategy_panel .step_2_para div").removeClass("temp_highlight");
		$(".swap_mod_2 .strategy_panel .step_2_para div").find("p").removeClass("word_change");
		$(this).addClass("temp_highlight");
		$(this).find("p").addClass("word_change");
	});
	
	if (operator=="edit")
	{
		$(".swap_mod_2 .strategy_panel .step_2_para ."+currentStrategy.ifs.paramCode).addClass("temp_highlight");
		$(".swap_mod_2 .strategy_panel .step_2_para ."+currentStrategy.ifs.paramCode+" p").addClass("word_change");
		flag_2 = 1;
	}
			
	$(".next_step_3").click(function(){
		if (flag_1 && flag_2) {
			showStrategyStep2();
		}
	});
  $(".swap_mod_2 .cancel_create").click(function(){
		$(".swap_mod_2").hide();
		$(".swap_mod_3").hide();
		$(".swap_mod_4").hide();
		if (operator =="edit")
		  $(".swap_mod_1").show();
		else
			$(".swap_mod_1").hide();  
	});
}

function loadDeviceParam() 
{
  var div = "";
  for (var i=0;i<deviceParam.length;i++)
  {
    if (deviceParam[i].parentId == deviceItemId) //默认显示第一个的参数，其它参数跳过
    {
      div = div + "<div class=\""+deviceParam[i].itemValue +"\">";
      div = div + "<p>"+deviceParam[i].itemName+"</p>";
      div = div + "</div>";
    }  
  }
  return div ;
}
//显示创建/编辑策略的第二部界面
function showStrategyStep2()
{
	$(".swap_mod_2").hide();
	$(".swap_mod_3").show();
	
	var temptitle = $(".step_1_para .wind_highlight >p").text() + "的" +$(".step_2_para>.temp_highlight").find("p").text();
	$(".swap_mod_3 .title_setting h3").html(temptitle);
	
	if (operator =="edit")
	{
		$(".swap_mod_3 >.strategy_panel > .title_setting .selectpicker_sign option").each(function(index){
			if ($(this).html()==currentStrategy.operator)
			 $(this).attr("selected","selected");
		});
		
		flag_3 = 1;
	}
	
	$(".selectpicker_sign").selectpicker({
		width : "252px",
	});

  if (operator =="edit")
	{
		var comparisonName = "数值";
		switch (currentStrategy.comparison.type)
		{
				case "boolean":
				  comparisonName = (currentStrategy.comparison.value)?"True":"False";
				  break;
				case "item":
				  comparisonName = "项目";  
				  break;
		} 
		$(".swap_mod_3 >.strategy_panel > .title_setting .selectpicker_value option").each(function(index){
			
			if ($(this).html() == comparisonName)
			{
			  $(this).attr("selected","selected");
			} 
		});
	}
  
	$(".selectpicker_value").selectpicker({
		width : "142px"
	});
	
	$(".step_3_para").css("margin-top","0px");
	$(".step_3_para").css("margin-left","18px");
	
	$(".value_choice").css("margin-top","0px");
	$(".value_choice").css("margin-left","18px");
	
	$(".step_3_para").change(function(){
		if ($(this).val() != "选择运算符号") {
			flag_3 = 1;
		} else {
			return false;
		}
	});
	
	resetValue();
	if (operator =="edit")
	{
		 $(".strategy .swap_mod_3 input").val(currentStrategy.comparison.value);
  }
  else 
	  $(".strategy .swap_mod_3 input").val('');
	
	$(".swap_mod_3 .strategy_panel .sub_mode_choice").html('');
	var div = "";
  for (var i=0;i<deviceItem.length;i++)
  {
    div = "<div>";
    div = div + "<div id=\"deviceItem_"+ deviceItem[i].id +"\" class=\""+deviceItem[i].itemValue +"\">";
    div = div + "<p>"+deviceItem[i].itemName+"</p>";
    div = div + "</div>";
    div = div + "<i class=\"icon_arrow\"></i>";
    div = div + "</div>";
    $(".swap_mod_3 .strategy_panel .sub_mode_choice").append(div);
  } 
  deviceItemId = deviceItem[0].id;
  $(".swap_mod_3 .strategy_panel .sub_mode_choice div div").click(function(){
		flag_4 = 1;
		$(".swap_mod_3 .strategy_panel .sub_mode_choice div div").removeClass("wind_highlight");
		$(this).addClass("wind_highlight");
		deviceItemId = $(this).attr("id").replace("deviceItem_","");
		$(".swap_mod_3 .strategy_panel .step_5_para").html('');
    $(".swap_mod_3 .strategy_panel .step_5_para").append(loadDeviceParam());
    $(".swap_mod_3 .strategy_panel .step_5_para div").click(function(){
		  flag_5 = 1;
		  $(".swap_mod_3 .strategy_panel .step_5_para div").removeClass("temp_highlight");
	 	  $(".swap_mod_3 .strategy_panel .step_5_para div").find("p").removeClass("word_change");
	  	$(this).addClass("temp_highlight");
		  $(this).find("p").addClass("word_change");
	  });
	});
	
	if ((operator=="edit")&&(comparisonName=="项目"))
	{
		$(".swap_mod_3 .strategy_panel .sub_mode_choice div ."+currentStrategy.comparison.itemCode+"").addClass("wind_highlight");
		flag_4 = 1;
		deviceItemId = $(".swap_mod_3 .strategy_panel .sub_mode_choice div ."+currentStrategy.comparison.itemCode+"").attr("id").replace("deviceItem_","");
	}
	
	$(".swap_mod_3 .strategy_panel .step_5_para").html('');
  $(".swap_mod_3 .strategy_panel .step_5_para").append(loadDeviceParam());
  $(".swap_mod_3 .strategy_panel .step_5_para div").click(function(){
		flag_5 = 1;
		$(".swap_mod_3 .strategy_panel .step_5_para div").removeClass("temp_highlight");
		$(".swap_mod_3 .strategy_panel .step_5_para div").find("p").removeClass("word_change");
		$(this).addClass("temp_highlight");
		$(this).find("p").addClass("word_change");
	});
  
  if ((operator=="edit")&&(comparisonName=="项目"))
	{
		$(".swap_mod_3 .strategy_panel .step_5_para ."+currentStrategy.comparison.paramCode).addClass("temp_highlight");
		$(".swap_mod_3 .strategy_panel .step_5_para ."+currentStrategy.comparison.paramCode+" p").addClass("word_change");
		flag_5 = 1;
	}
	
		
  $(".swap_mod_3 .prev_step").click(function(){
		$(".swap_mod_3").hide();
		$(".swap_mod_2").show();
	});
	
	$(".next_step_4").click(function(){
		var goNext = false;
		var optionName = $(".swap_mod_3 ._value > button > div").html();
		switch (optionName)
	  { 
		  case "数值":
			  if ($(".strategy .swap_mod_3 input").val() != "")
			    goNext = true;
		    break;
	    case "True":
	      goNext = true;
		    break; 
		  case "False":
		    goNext = true;
		    break;
		  case "项目":
		    if (flag_4 && flag_5) 
		      goNext = true;
		    break;  
    }
    if (flag_3 && goNext)
		{
			showStrategyStep3();
		}
	});
  $(".swap_mod_3 .cancel_create").click(function(){
		$(".swap_mod_2").hide();
		$(".swap_mod_3").hide();
		$(".swap_mod_4").hide();
		if (operator =="edit")
		  $(".swap_mod_1").show();
		else
			$(".swap_mod_1").hide();  
	});
}

//显示创建/编辑策略的第三布(也是最后一步)界面
function showStrategyStep3()
{
  $(".swap_mod_3").hide();
  $(".swap_mod_4 .strategy_value").hide();
  $(".swap_mod_4 .strategy_item").hide();
  $(".swap_mod_4 .strategy_name").val('');
	$(".swap_mod_4").show();
  
  var temptitle = $(".swap_mod_3 .title_setting h3").html();
  $(".swap_mod_4 .if_name >p").html(temptitle);
  
  var tempOperator = $(".swap_mod_3 .step_3_para").val();
  
  $(".swap_mod_4 .operator >p").html(tempOperator);
  
  var optionName = $(".swap_mod_3 ._value > button > div").html();
	switch (optionName)
	{
		case "数值":
		  $(".swap_mod_4 .strategy_value").show();
		  var v = $(".strategy .swap_mod_3 input").val();
		  $(".swap_mod_4 .strategy_value >div >p").html(v);
		  break;
		case "False":
		  $(".swap_mod_4 .strategy_value").show();
		  $(".swap_mod_4 .strategy_value >div >p").html("False");
		  break;
		case "True":
		  $(".swap_mod_4 .strategy_value").show();
		  $(".swap_mod_4 .strategy_value >div >p").html("True");
		  break;
		case "项目":
		  $(".swap_mod_4 .strategy_item").show();
		  var tempvalue = $(".step_4_para .wind_highlight >p").text() + "的" +$(".step_5_para > .temp_highlight >p").text();
		  $(".swap_mod_4 .strategy_item >div >p").html(tempvalue); 
		  break;      
  }	
  
  $(".swap_mod_4 .strategy_panel .temp_setting3").html('');
  var div = "";
	for (var i=0;i<deviceIndex.length;i++)
	{
		div = "<div>";
		div = div + "<p>"+deviceIndex[i].name+"</p>";
		
		if (deviceIndex[i].type =="onOff")
		{
		  div = div +"<div class=\""+ deviceIndex[i].code+"\">";
		  div = div +"<p onclick=\"javascript:changeOnOff(this)\">OFF</p>";
		  div = div +"</div>";
		}
		else if (deviceIndex[i].type =="number")
		{
			div = div + "<input class=\""+deviceIndex[i].code+"\" type=\"number\" value=\"\">";
		}
		div = div + "</div>";
		
		$(".swap_mod_4 .strategy_panel .temp_setting3").append(div);  
	}
	
	if (operator =="edit")
	{
	  for(var i=0;i<currentStrategy.then.length;i++)
	  {
	  	if (currentStrategy.then[i].deviceIndex.type =="onOff")
	  	{
	  	  var onOff = (currentStrategy.then[i].deviceIndex.value=="1")?"ON":"OFF";
	  	  
	  	  $(".swap_mod_4 .strategy_panel .temp_setting3 >div >div > p").html(onOff);
	  	}
	  	else
	  	{
	  		$(".swap_mod_4 .strategy_panel .temp_setting3 div ."+currentStrategy.then[i].deviceIndex.code).val(currentStrategy.then[i].deviceIndex.value);
	  	}  
	  }
	  
	  $(".swap_mod_4 .strategy_panel .name_setting .strategy_name").val(currentStrategy.name);
	  $(".swap_mod_4 .strategy_panel .name_setting .strategy_desc").val(currentStrategy.description);
  }
  
	
  $(".swap_mod_4 .prev_step").click(function(){
	$(".swap_mod_4").hide();
	$(".swap_mod_3").show();
	});
	
	$(".swap_mod_4 .complete").off('click');
  $(".swap_mod_4 .complete").click(function(){
  	
	var valueList = $(".swap_mod_4 .temp_setting3 >div");
	for(var i=0;i<valueList.length;i++)
	{
	  paramName = valueList.eq(i).find("p").text();
		if (valueList.eq(i).find("div p").length ==1)
		  paramValue = valueList.eq(i).find("div p").text();
		else if (valueList.eq(i).find("input").length ==1) 
			paramValue = valueList.eq(i).find("input").val(); 
		  
	  if (paramValue =="")
	  {
	  	showMessage("请输入"+paramName,"error");
	    return;	
	  }
	  else if ((paramValue !="OFF")&&(paramValue !="ON")&&(! isNumber(paramValue)))
	  {
	  	showMessage(paramName+"必须是数字","error");
	  	return;
	  }	
	}
		
	var strategyName = $(".swap_mod_4 .strategy_panel .name_setting .strategy_name").val();
	if(strategyName =="")
	{
	  showMessage("请为这个策略设置一个好记的名字","error");
	  return;
	}
	var strategyDesc = $(".swap_mod_4 .strategy_panel .name_setting .strategy_desc").val();
	if(strategyDesc =="")
	{
	  showMessage("请为这个策略设置一些备注信息","error");
	  return;
	} 
  
  currentStrategy.name =  $(".swap_mod_4 .strategy_panel .name_setting .strategy_name").val();
	currentStrategy.description = $(".swap_mod_4 .strategy_panel .name_setting .strategy_desc").val();
	currentStrategy.ifs.name = $(".swap_mod_4 .if_name >p").html();
  
  var divClass = $(".swap_mod_2 .strategy_panel .step_1_para div .wind_highlight").attr("class");
	var divClassList = divClass.split(" ");
	currentStrategy.ifs.itemCode = divClassList[0];
		  
	divClass = $(".swap_mod_2 .strategy_panel .step_2_para .temp_highlight").attr("class");
	divClassList = divClass.split(" ");
	currentStrategy.ifs.paramCode = divClassList[0];
	
	currentStrategy.operator = $(".swap_mod_4 .operator >p").text();
	//alert("operator is "+currentStrategy.operator);
	var optionName = $(".swap_mod_3 ._value > button > div").html();
	switch (optionName)
	{
		case "数值":
		  currentStrategy.comparison.type = "number";
		  currentStrategy.comparison.value = $(".swap_mod_4 .strategy_value >div >p").html();
	    break;
		case "False":
		  currentStrategy.comparison.type = "boolean";
		  currentStrategy.comparison.value = false;
		  break;
		case "True":
		  currentStrategy.comparison.type = "boolean";
		  currentStrategy.comparison.value = true;
		  break;
		case "项目":
		  currentStrategy.comparison.type = "item";
		  currentStrategy.comparison.value = "";
		  currentStrategy.comparison.name = $(".swap_mod_4 .strategy_item .comparison_name >p").html();
		  
		  divClass = $(".swap_mod_3 .strategy_panel .step_4_para div .wind_highlight").attr("class");
		  divClassList = divClass.split(" ");
		  currentStrategy.comparison.itemCode = divClassList[0];
		  
		  divClass = $(".swap_mod_3 .strategy_panel .step_5_para .temp_highlight").attr("class");
		  divClassList = divClass.split(" ");
		  currentStrategy.comparison.paramCode = divClassList[0];
		  break;      
  }	
	var newThen = null;
	currentStrategy.then = [];
	$(".swap_mod_4 .temp_setting3 >div").each(function(index){
		if ($(this).find("div").length ==1)
		{
		  paramType = "onOff";
		  paramCode = $(this).find("div").attr("class");
		  paramValue = ($(this).find("div p").text()=="ON")?"1":"0";
		}
		else if ($(this).find("input").length ==1)
		{	 
			paramType = "number";
			paramCode = $(this).find("input").attr("class");
			paramValue = $(this).find("input").val(); 
		}
		newThen = {"deviceIndex":{"code":paramCode,"value":paramValue,"type":paramType}};  
	  currentStrategy.then.push(newThen);
	}
	);
	if (operator =="create")
		createStrategy();
	else 
		saveStrategy();  
	});
	
	$(".swap_mod_4 .cancel_create").click(function(){
	$(".swap_mod_2").hide();
	$(".swap_mod_3").hide();
	$(".swap_mod_4").hide();
  }
  );
}
//在创建/编辑策略的第二步界面，隐藏或展现 项目区域
function resetValue()
{
	var optionName = $(".swap_mod_3 ._value > button > div").html();
	switch (optionName)
	{ 
		case "数值":
			$(".strategy .swap_mod_3 input").show();
		  $(".step_4_para").hide();
		  $(".step_5_para").hide();
		  $(".swap_mod_3 .strategy_panel").animate(
	    {
	      'height':"130px"
	    },
	    200
	    );
		  break;
	  case "True":
	    $(".strategy .swap_mod_3 input").hide();
		  $(".step_4_para").hide();
		  $(".step_5_para").hide();
		  $(".swap_mod_3 .strategy_panel").animate(
	    {
	      'height':"130px"
	    },
	    200
	    );
		  break; 
		case "False":
		  $(".strategy .swap_mod_3 input").hide();
		  $(".step_4_para").hide();
		  $(".step_5_para").hide();
		  $(".swap_mod_3 .strategy_panel").animate(
	    {
	      'height':"130px"
	    },
	    200
	    );
		  break;
		case "项目":
		  $(".strategy .swap_mod_3 input").hide();
		  $(".step_4_para").show();
		  $(".step_5_para").show();
		  $(".swap_mod_3 .strategy_panel").animate(
	    {
	      'height':"356px"
	    },
	    200
	    );  
  }
}


function showStrategyDetail()
{
	var url = baseurl+'/strategy/getStrategyDetail';
	
	var d = new Date();
	var r = Math.random()+""+d.getMilliseconds();
	var params ={strategyId:current_strategy_id,r:r};
	
	$.ajax({url:url,type:'GET',dataType:'json',async:true,data:params,success:function(data){ 
		var strategy = data;
			
		currentStrategy.id = current_strategy_id;
		currentStrategy.name = strategy.strategyName;
		//currentStrategy.pic = strategy.pic;
		currentStrategy.pic = "";
		//currentStrategy.description = strategy.description;
		currentStrategy.description =""; 
		currentStrategy.ifs.itemCode = strategy.itemMain;
		currentStrategy.ifs.paramCode = strategy.itemParam;
		currentStrategy.ifs.name = getName(currentStrategy.ifs.itemCode,currentStrategy.ifs.paramCode);
				
		currentStrategy.operator = strategy.compareSymbol;
		
		if ((strategy.compareValue ==null)||(strategy.compareValue==""))
		{
			currentStrategy.comparison.type = "item";
		}
		else if ((strategy.compareValue == false)||(strategy.compareValue == true))
		{
			currentStrategy.comparison.type = "boolean";
		}	
		else
			currentStrategy.comparison.type = "number"; 	
		
		currentStrategy.comparison.value = strategy.compareValue;
		
		currentStrategy.comparison.itemCode = strategy.compareMain;
		currentStrategy.comparison.paramCode = strategy.compareParam;
		currentStrategy.comparison.name = getName(currentStrategy.comparison.itemCode,currentStrategy.comparison.paramCode);
		
		currentStrategy.then = [];
	  for(var i=0;i<strategy.ucStrategyParams.length;i++)
	  {
	  	var tempType = getTypeByCode(strategy.ucStrategyParams[i].paramName)
	  	tempIndex = {"deviceIndex":{"code":strategy.ucStrategyParams[i].paramName,"value":strategy.ucStrategyParams[i].paramValue,"type":tempType}};
	  	currentStrategy.then[i] = tempIndex;
	  }
		
		$(".swap_mod_1 .para_and_chart .setting_table table > tbody").html('');
		var tr1 = "<tr>"+"\n";
		tr1 = tr1 +"<td>if</td>"+"\n";
		tr1 = tr1 +"<td>"+currentStrategy.ifs.name+"</td>"+"\n";
		tr1 = tr1 +"<td>"+currentStrategy.operator+"</td>"+"\n";
		if (currentStrategy.comparison.type=="number" || currentStrategy.comparison.type=="boolean")
		  tr1 = tr1 +"<td>"+currentStrategy.comparison.value+"</td>"+"\n";
		else
			tr1 = tr1 +"<td>"+currentStrategy.comparison.name+"</td>"+"\n";  
		tr1 + tr1 +"</tr>"+"\n";
		$(".swap_mod_1 .para_and_chart .setting_table table > tbody").append(tr1);
		
		var thenTr = "";
		var then = "";
		var index = 0;
		var settingCount = currentStrategy.then.length;
		thenTrCount = Math.floor(settingCount/3);
		if ((settingCount%3) !=0)
		  thenTrCount = thenTrCount + 1;
		
		for (var i=0;i<thenTrCount;i++)
		{
			if (i==0)
			  then = "then";
			else
				then = "";  
			
			var thenName = "";
			thenTr = thenTr + "<tr>"+"\n";
			thenTr = thenTr + "<td>"+then+"</td>"+"\n";
			for (var j=0;j<3;j++)
			{
				index = i * 3 + j;
				if (index < settingCount)
				{
				  thenName = getIndexNameByCode(currentStrategy.then[index].deviceIndex.code);
				  thenTr = thenTr + "<td>"+thenName+"</td>"+"\n";
				}
				else
					thenTr = thenTr + "<td></td>"+"\n";   
			}
			thenTr = thenTr + "</tr>"+"\n";
			
			thenTr = thenTr + "<tr>"+"\n";
			thenTr = thenTr + "<td></td>"+"\n";
			for (var k=0;k<3;k++)
			{
				index = i * 3 + k;
				if (index < settingCount)
				{
				  if (currentStrategy.then[index].deviceIndex.type =="number")
				    thenTr = thenTr + "<td>"+currentStrategy.then[index].deviceIndex.value+"</td>"+"\n";
				  else if (currentStrategy.then[index].deviceIndex.type =="onOff")
				  {	
				  	var onOff = (currentStrategy.then[index].deviceIndex.value=="1")?"ON":"OFF";
				  	thenTr = thenTr + "<td>"+onOff+"</td>"+"\n";  
				  }	
				}
				else
					thenTr = thenTr + "<td></td>"+"\n";  
			}
			thenTr = thenTr + "</tr>"+"\n";
			
		}
		$(".swap_mod_1 .para_and_chart .setting_table table > tbody").append(thenTr);
		
		var picdiv = "";
		if ((currentStrategy.pic !=null)&&(currentStrategy.pic !=""))
		{
			picdiv = "<img src=\""+ currentStrategy.pic + "\" alt=\"策略示意图\"/>";
		}
		else
		{
			picdiv = "<div onclick=\"javascript:uploadStrategyPic()\">点击此处可以为这个策略上传一张示意图</div>";
		}  
		$(".swap_mod_1 .para_and_chart .setting_chart").html('');
		$(".swap_mod_1 .para_and_chart .setting_chart").append(picdiv);
		$(".swap_mod_1").show();
	}
  }
  );
}

function getName(item,param)
{
	var itemname = "";
	for (var i=0;i<deviceItem.length;i++)
	{
		if (deviceItem[i].itemValue == item)
		{
			 itemname = deviceItem[i].itemName;
			 break;
		}
	}
		
	var paramname = "";
	for (var j=0;j<deviceParam.length;j++)
	{
		if (deviceParam[j].itemValue == param)
		{
			 paramname = deviceParam[j].itemName;
			 break;
		}
	}
	
	var name = itemname + "的" + paramname ;
	return name; 
}

function getTypeByCode(code)
{
	var type = "number";
	
	for (var i=0;i<deviceIndex.length;i++)
	{
		if (deviceIndex[i].code == code)
		{
			type = deviceIndex[i].type;
			break;
		}
	}
	
	return type;
}

function getIndexNameByCode(code)
{
	var name = "";
	for (var i=0;i<deviceIndex.length;i++)
	{
		if (deviceIndex[i].code == code)
		{
			name = deviceIndex[i].name;
			break;
		}
	}

	return name;
}


//为策略上传一个示意图
function uploadStrategyPic()
{
	var str = "<div id=\"upload_img\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"title\">为策略上传一个示意图</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  str = str +	"<div class=\"body\">"+"\n";
  str = str + "</br>"+"\n";
  str = str + "</br>"+"\n";
  
	str = str + "<div id=\"upload_left\">"; 
	str = str + "<label for=\"image_file\">请点击\"浏览\"按钮，开始上传的图片</label>";
	
	str = str +"<form id=\"upload_form\" action=\"/imgs/upload\" method=\"post\" enctype=\"multipart/form-data\">";
	str = str +"<input id=\"image_file\" type=\"file\" name=\"image_file\" onchange=\"fileSelected();\"/>";
  str = str +"</form>";
  
  str = str +"<div id=\"error\">请检查文件的格式,本上传只支持图片格式!</div>";
  str = str +"<div id=\"error1\">请点击\"浏览\"按钮选择一个图片文件!</div>";
  str = str +"<div id=\"error2\">图片上传失败!</div>";
  str = str +"<div id=\"abort\">图片上传被取消</div>";
  str = str +"<div id=\"warnsize\">你的文件太大了,请选择更小一些的图片</div>";
  
  str = str +"<div id=\"upload_response\"></div>";
  
  str = str +"</div>";
  
  str = str +"<div id=\"preview_right\">";
  str = str +"<img id=\"preview\" />";
  str = str +"</div>";
  
  str = str +"</div>";
  
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
  
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:uploadImg();\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  str = str + "</div>"+"\n";
  
  $('#mask').html('');
  $('#mask').html(str);
  
  $('#upload_img').css("width","900px");
  $('#upload_img').css("height","600px");
  $('#upload_img').css("margin-left","510px");
  $('#upload_img').css("margin-top","240px");
  $('#upload_img >.body').css('width','800px');
  $('#upload_img >.body').css('height','500px');
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  });
  $('#mask').show();
  $("#upload_img").draggable();
}


function uploadImg()
{
	var url = '/imgs/upload/';
	startUploading(url);
	if (document.getElementById('success') == null)
	  return;
	var success = document.getElementById('success').value;
	if (success)
	{
	  var img_url = document.getElementById('imgurl').value;
		showImg(img_url);
		var t=setTimeout('hideDialog()',1500); 
	}
}


function showImg(img_url)
{
	var picdiv = "<img src=\""+ img_url + "\" alt=\"策略示意图\"/>";
	$(".swap_mod_1 .para_and_chart .setting_chart").html('');
	$(".swap_mod_1 .para_and_chart .setting_chart").append(picdiv);
}


//创建一个策略,创建成功后，将这个策略的基本信息加入策略列表中，然后再重新画出策略方框列表
function createStrategy()
{
	currentStrategy.id = null;
	var json = strategyToString(currentStrategy);
	json = transferStrategy(currentStrategy);
	var url = baseurl+'/strategy/saveStrategy';
	var params ={strategy:json};
	$.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){ 	     
    if (data.success == true)
    {
    	showMessage("策略创建成功!","tip");
    	if ((typeof(data.author) == "undefined")||(data.author == null))
    	  addNewStrategy(data.patternId,'',currentStrategy.name);
    	else  
    	  addNewStrategy(data.patternId,data.author,currentStrategy.name);
    	goLastPage();
    	var t=setTimeout('$(".swap_mod_4").hide()',1500);  
    }
    else
    {
    	showMessage("策略创建失败,请稍后再试!","error");
    } 
  }
  }
  );
}

function addNewStrategy(strategy_id,strategy_author,strategy_name)
{
	var newStrategy = {"id":strategy_id,"author":strategy_author,"strategyName":strategy_name};
	strategyList.push(newStrategy);
	renderStrategyList();
	$(".strategy_list > ul > li >.one_strategy >.title").css("background-color","#78CDD0");
  $(".strategy_list > ul > li >.one_strategy >.strategy_desc").css("border-color","#ffffff");
	$("#strategy_"+strategy_id).children().eq(0).css("background-color","#FFCC33");
	$("#strategy_"+strategy_id).children().eq(0).children().eq(1).show();
	$("#strategy_"+strategy_id).children().eq(1).css("border-left","2px solid #FFCC33");
  $("#strategy_"+strategy_id).children().eq(1).css("border-right","2px solid #FFCC33");
  $("#strategy_"+strategy_id).children().eq(1).css("border-bottom","2px solid #FFCC33");
  current_strategy_id = strategy_id;
  showStrategyDetail();
}


function saveStrategy()
{
	var json = transferStrategy(currentStrategy);
	var url = baseurl+'/strategy/saveStrategy';
	var params ={strategy:json};
	
	$.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){ 	     
    if (data.success == true)
    {
    	showMessage("策略修改成功!","tip");
    	if (typeof(data.author) == "undefined")
    	  modifyStrategy(currentStrategy.name,'');
    	else
    	  modifyStrategy(currentStrategy.name,data.author);
    	var t=setTimeout('$(".swap_mod_4").hide()',1500);
    	showStrategyDetail();
    }
    else
    {
    	showMessage("策略修改失败,请稍后再试!","error");
    } 
  }
  }
  );
}

function modifyStrategy(strategy_name,strategy_author)
{
	$("#strategy_"+current_strategy_id+" .strategy_name >span").html(strategy_name);
	$("#strategy_"+current_strategy_id+" .strategy_desc >span").html(strategy_author);
		
	for (var i=0;i<strategyList.length;i++)
	{
		if (current_strategy_id == strategyList[i].id)
		{
			strategyList[i].strategyName = strategy_name;
			//strategyList[i].author = strategy_author;  现在没有author
			break; 
		}
	}
}


function goLastPage()
{
	var li_count = strategyList.length;
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

function isNumber(inputNumber)
{
	var sReNumber = /^[0-9]*$/;
	if (sReNumber.test(inputNumber)) {
		return true;
	}
	else {
		return false;
	}
}

function changeOnOff(p)
{
	if ($(p).html()=="OFF")
	{
		$(p).html("ON");
	}
	else if ($(p).html()=="ON")
  {
  	$(p).html("OFF");
  }
}

function escape2Html(str) {
 var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
 return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
}

function html2Escape(sHtml) {
 return sHtml.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];});
}