var baseurl = CONTEXT_PATH; //"/ibms-portal-web";
var device_list = null;
var modeList = null;
var strategyList = null;
var actionList = [{"code":"CI","name":"闭店中"},{"code":"C1","name":"商业楼闭店"},{"code":"C2","name":"娱乐楼闭店"},{"code":"R","name":"营业前准备"},{"code":"O","name":"营业"},{"code":"B","name":"营业中"},{"code":"CR","name":"闭店前准备"},{"code":"C","name":"闭店"}];
var coldDeviceGroupControl = [{"code":"1","name":"冷机1"},{"code":"2","name":"冷机2"},{"code":"3","name":"冷机3"},{"code":"4","name":"冷机4"}];





//var property = [{"name":"CoolingSource","value":[{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"纳入群控的冷机","code":"groupControlCount","dbCode":"number_on","type":"checkboxGroup","measurementUnit":""},{"name":"最多开启的冷机","code":"maxControlCount","dbCode":"max_number_on","type":"multiList","measurementUnit":""},{"name":"供水温度","code":"waterTemperature","dbCode":"t_chw_s_sp","type":"number","measurementUnit":"℃"},{"name":"供回水压差","code":"waterPressure","dbCode":"dp_chw_s_sp","type":"number","measurementUnit":"mH2O"},{"name":"供回水温差","code":"waterDiff","dbCode":"dt_chw_s_sp","type":"number","measurementUnit":"℃"}]},
//                {"name":"HeatSource","value":[{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"供水温度","code":"waterTemperature","dbCode":"t_hw_s_sp","type":"number","measurementUnit":"℃"},{"name":"供水压力","code":"waterPressure2","dbCode":"p_hw_s_sp","type":"number","measurementUnit":"mH2O"},{"name":"水泵频率","code":"hz","dbCode":"frequency_sp","type":"number","measurementUnit":"Hz"}]},
//                {"name":"FAVU","value":[{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"策略","code":"strategy","dbCode":"strategy","type":"strategy","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"送风温度","code":"windTemperature","dbCode":"t_sa_sp","type":"number","measurementUnit":"℃"},{"name":"防冻温度","code":"freezeTemperature","dbCode":"t_freeze_sp","type":"number","measurementUnit":"℃"},{"name":"风机频率","code":"hz","dbCode":"frequency_sf_sp","type":"number","measurementUnit":"Hz"}]},
//                {"name":"MAHU","value":[{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"策略","code":"strategy","dbCode":"strategy","type":"strategy","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"送风温度","code":"windTemperature","dbCode":"t_sa_sp","type":"number","measurementUnit":"℃"},{"name":"防冻温度","code":"freezeTemperature","dbCode":"t_freeze_sp","type":"number","measurementUnit":"℃"},{"name":"回风温度","code":"lookWindTemperature","dbCode":"t_ra_sp","type":"number","measurementUnit":"℃"},{"name":"新风阀开度","code":"windDamper","dbCode":"u_damper_oa_sp","type":"number","measurementUnit":"%"},{"name":"风机频率","code":"hz","dbCode":"frequency_sf_sp","type":"number","measurementUnit":"Hz"},{"name":"回风CO2浓度","code":"co2_ra_sp","dbCode":"co2_ra_sp","type":"number","measurementUnit":"ppm"}]},
//                {"name":"BUAHU","value":[{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"策略","code":"strategy","dbCode":"strategy","type":"strategy","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"送风温度","code":"windTemperature","dbCode":"t_sa_sp","type":"number","measurementUnit":"℃"},{"name":"防冻温度","code":"freezeTemperature","dbCode":"t_freeze_sp","type":"number","measurementUnit":"℃"},{"name":"回风温度","code":"lookWindTemperature","dbCode":"t_ra_sp","type":"number","measurementUnit":"℃"},{"name":"新风阀开度","code":"windDamper","dbCode":"u_damper_oa_sp","type":"number","measurementUnit":"%"},{"name":"风机频率","code":"hz","dbCode":"frequency_sf_sp","type":"number","measurementUnit":"Hz"},{"name":"回风CO2浓度","code":"co2_ra_sp","dbCode":"co2_ra_sp","type":"number","measurementUnit":"ppm"}]},
//                {"name":"AVU","value":[{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""}]},
//                {"name":"LSPUB","value":[{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree_deviceGroup","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""}]},
//                {"name":"LSN","value": [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree_deviceGroup","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""}]}]

var  property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"纳入群控的冷机","code":"groupControlCount","dbCode":"number_on","type":"checkboxGroup","measurementUnit":""},{"name":"最多开启的冷机","code":"maxControlCount","dbCode":"max_number_on","type":"multiList","measurementUnit":""},{"name":"供水温度","code":"waterTemperature","dbCode":"t_chw_s_sp","type":"number","measurementUnit":"℃"},{"name":"供回水压差","code":"waterPressure","dbCode":"dp_chw_s_sp","type":"number","measurementUnit":"mH2O"},{"name":"供回水温差","code":"waterDiff","dbCode":"dt_chw_s_sp","type":"number","measurementUnit":"℃"}];
function changeProperty (systemCode){
    if(systemCode=='CoolingSource'){
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"纳入群控的冷机","code":"groupControlCount","dbCode":"number_on","type":"checkboxGroup","measurementUnit":""},{"name":"最多开启的冷机","code":"maxControlCount","dbCode":"max_number_on","type":"multiList","measurementUnit":""},{"name":"供水温度","code":"waterTemperature","dbCode":"t_chw_s_sp","type":"number","measurementUnit":"℃"},{"name":"供回水压差","code":"waterPressure","dbCode":"dp_chw_s_sp","type":"number","measurementUnit":"mH2O"},{"name":"供回水温差","code":"waterDiff","dbCode":"dt_chw_s_sp","type":"number","measurementUnit":"℃"}];
    }
    if(systemCode=='HeatSource'){
//    热源：
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"供水温度","code":"waterTemperature","dbCode":"t_hw_s_sp","type":"number","measurementUnit":"℃"},{"name":"供水压力","code":"waterPressure2","dbCode":"p_hw_s_sp","type":"number","measurementUnit":"mH2O"},{"name":"水泵频率","code":"hz","dbCode":"frequency_sp","type":"number","measurementUnit":"Hz"}];
    }
    if(systemCode=='FAVU'){
//    新风机组：
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"策略","code":"strategy","dbCode":"strategy","type":"strategy","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"送风温度","code":"windTemperature","dbCode":"t_sa_sp","type":"number","measurementUnit":"℃"},{"name":"防冻温度","code":"freezeTemperature","dbCode":"t_freeze_sp","type":"number","measurementUnit":"℃"},{"name":"风机频率","code":"hz","dbCode":"frequency_sf_sp","type":"number","measurementUnit":"Hz"}];
    }
    if(systemCode=='MAHU'){
//    组合式空调机组：
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"策略","code":"strategy","dbCode":"strategy","type":"strategy","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"送风温度","code":"windTemperature","dbCode":"t_sa_sp","type":"number","measurementUnit":"℃"},{"name":"防冻温度","code":"freezeTemperature","dbCode":"t_freeze_sp","type":"number","measurementUnit":"℃"},{"name":"回风温度","code":"lookWindTemperature","dbCode":"t_ra_sp","type":"number","measurementUnit":"℃"},{"name":"新风阀开度","code":"windDamper","dbCode":"u_damper_oa_sp","type":"number","measurementUnit":"%"},{"name":"风机频率","code":"hz","dbCode":"frequency_sf_sp","type":"number","measurementUnit":"Hz"},{"name":"回风CO2浓度","code":"co2_ra_sp","dbCode":"co2_ra_sp","type":"number","measurementUnit":"ppm"}];
    }
    if(systemCode=='BUAHU'){
//    吊装式空调机组
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"策略","code":"strategy","dbCode":"strategy","type":"strategy","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"送风温度","code":"windTemperature","dbCode":"t_sa_sp","type":"number","measurementUnit":"℃"},{"name":"防冻温度","code":"freezeTemperature","dbCode":"t_freeze_sp","type":"number","measurementUnit":"℃"},{"name":"回风温度","code":"lookWindTemperature","dbCode":"t_ra_sp","type":"number","measurementUnit":"℃"},{"name":"新风阀开度","code":"windDamper","dbCode":"u_damper_oa_sp","type":"number","measurementUnit":"%"},{"name":"风机频率","code":"hz","dbCode":"frequency_sf_sp","type":"number","measurementUnit":"Hz"},{"name":"回风CO2浓度","code":"co2_ra_sp","dbCode":"co2_ra_sp","type":"number","measurementUnit":"ppm"}];
    }
    if(systemCode=='AVU'){
//    通风机组
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""}];
    }
    if(systemCode=='LSPUB'){
//    公共照明
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree_deviceGroup","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""}];
    }
    if(systemCode=='LSN'){
//    夜景照明
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree_deviceGroup","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""}];
    }
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

function getSystemId()
{
	return "";
}

function showCreateModeApplyDialog()
{
    var subment_current = $(".left_menu .cur i");
    var id = $(subment_current).attr('id');
    if(id==null || id=="" || id=="HVAC"){

         subment_current = $(".cataloge_class_three .current");
        if(subment_current==null || subment_current.length==0){

            subment_current = $(".cataloge_class_two .current");
            id = $(subment_current).attr('id');
        }else{

            var parent=subment_current.parent().parent();
            var el=parent.prev();
            id=el.attr("id");
        }

    }

  var str = "<div id=\"create_mode_apply\" class=\"dialog\">"+"\n";
	str = str + "<div class=\"header\">"+"\n";
  str = str + "<div class=\"header_title\">模式切换</div>"+"\n";
  str = str +	"<div class=\"close cancle\">×</div>"+"\n";
  str = str + "</div>"+"\n";
  
  str = str +	"<div class=\"body\">"+"\n";
    //str = str + "<div>"+"\n";
//    str = str + "<div>请选择一个模式后，点击\"确定\"按钮</div>";
   // str = str + "<br/>"+"\n";


    modeList = getModeListBySystemCode(id);
		
//	if (modeList.length > 0){
//
//        str = str + "<select id='system_mode' name='system_mode' onChange='javascript:selectMode(); 'systemId='"+modeList[0].systemId+"' >";
//
//        for (i=0;i<modeList.length;i++)
//        {
//            str = str + "<option desc='"+modeList[i].description+"' value=\"" + modeList[i].id+"\">"+modeList[i].name+"</option>"+"\n";
//        }
//        str = str + "</select>"+"\n";
//
//    }



//  str = str + "<div class=\"button\" style=\"width:105px\" onclick=\"javascript:toggleDeviceException()\">设备例外<div style=\"margin-left:15px\" class=\"arrow_down\"></div></div>"+"\n";

  //str = str + "</div>"+"\n";
    //str = str + "<p  style='height: 5px'></p>"+"\n";
    //str = str + "<p id='patternDesc'></p>"+"\n";

  str = str + "</div>"+"\n";
  
  str = str + "<div class=\"footer\">"+"\n";
  str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
  str = str + "<div class=\"button_group\">"+"\n";
  str = str + "<div class=\"button cancle\">取消</div>"+"\n";
  str = str + "<div class=\"button\" onclick=\"javascript:saveModeApply();\">确定</div>"+"\n";
  str = str +	"</div>"+"\n";
  str = str +	"</div>"+"\n";
  
  
  str = str +	"</div>"+"\n";


    
  $('#mask').html(str);
  
  
  
  $('#create_mode_apply').css("width","90%");
  $('#create_mode_apply').css({"top":"0px","left":"0px","margin":"30px auto","position":"relative"});
  $('#create_mode_apply').css("height","90%");
//  $('#create_mode_apply').css("margin-left","460px");
//  $('#create_mode_apply').css("margin-top","190px");
  $('#create_mode_apply >.body').css('height','auto');
  $("#create_mode_apply > .footer").css("position","absolute");
  
  $('#mask .cancle').on('click',function(event){
  	$('#mask').html('');
  	$('#mask').hide();
  	//显示当前选择窗体
      if( id!="LSPUB" && id!="LSN"){
  	        submenu.showCurWindow();
      }
  });

    if (modeList.length > 0){
        var $inputdom = $("<input>",{
            value : "",
            class : "hideValue",
            style : "display:none"
        });
        var $selectedInfo = $("<p/>",{
            class : "mode_info"
        });

        var $fatherFdom = $("<div/>",{
            class : "wcBlock",
            id : "wcBlock"
        });
        var fatherDom = $("<div/>",{
            class : "allModeList",
            systemId : modeList[0].systemId,
            name : "system_mode",
            id : "system_mode"
        });

        var $domRightBar = $("<div/>",{
            class : "right_slide_bar"
        });
        var $domIdSliderUp = $("<div/>", {
            class : "idSliderUp"
        });
        var $domIdSlider = $("<div/>",{
            id : "idSlider_2"
        });
        var $domIdBar = $("<div/>",{
            id : "idBar_2"
        });

        var $domIdSliderDown = $("<div/>",{
            class : "idSliderDown"
        });

        $domIdBar.appendTo($domIdSlider);
        $domIdSliderUp.appendTo($domRightBar);
        $domIdSlider.appendTo($domRightBar);
        $domIdSliderDown.appendTo($domRightBar);
        $domRightBar.appendTo($fatherFdom);
        $inputdom.appendTo($("#create_mode_apply .body"));
        fatherDom.appendTo($fatherFdom);
        $fatherFdom.appendTo($("#create_mode_apply .body"));
        $selectedInfo.appendTo($("#create_mode_apply .body"));

        var sld = new Slider("idSlider_2", "idBar_2", { Horizontal: false,
            MaxValue: $$("wcBlock").scrollHeight - $$("wcBlock").clientHeight,
            onMove: function(){ $$("wcBlock").scrollTop = this.GetValue(); }
        });

        for(var i = 0; i < modeList.length; ++i){
            var domModeSelect = $("<div/>");
            domModeSelect.attr({"desc": modeList[i].description,"value" : modeList[i].id});
            domModeSelect.text(modeList[i].name);
            domModeSelect.appendTo(fatherDom);
        }
    }

  if($('#mask').length) {
	  console.log('exist');
  }
  
 // console.log(str);
  
  console.log('showCreateModeApplyDialog');
  
  $('#mask').show();

  $(".allModeList div").on("click", function(){
      var thisId = $(this).attr("value");
      var systemCode = $(".allModeList").attr("systemid");
      console.log(thisId);
      changeProperty(systemCode);
      $(this).siblings().removeClass("selected_mode");
      $(this).addClass("selected_mode");
      $(".hideValue").val($(this).val());
      $(".mode_detail").remove();
      selectMode();
      showModeDetail(thisId);
      var sld = new Slider("idSlider", "idBar", { Horizontal: false,
          MaxValue: $$("mode_table").scrollHeight - $$("mode_table").clientHeight,
          onMove: function(){ $$("mode_table").scrollTop = this.GetValue(); }
      });
      sld.SetPercent(0);
      sld.Ease = true;
  })
//    $(".dialog").draggable();
}

function  selectMode(){

 var patternDes=$(".allModeList").find(".selected_mode").attr("desc");

    $(".mode_info").text("模式描述: "+patternDes);

}

function showModeDetail(thisId){
    var $domDeatil = $("<div/>",{
        class: "mode_detail"
    });
    var $domModeTable = $("<div/>",{
        class : "mode_table",
        id : "mode_table"
    });
    var $domTable = $("<table/>");
    var $domThead = $("<thead/>");
    var $domTbody = $("<tbody/>");
    var $domTr = $("<tr/>");

    var $domRightBar = $("<div/>",{
        class : "right_slide_bar"
    });
    var $domIdSliderUp = $("<div/>", {
        class : "idSliderUp"
    });
    var $domIdSlider = $("<div/>",{
        id : "idSlider"
    });
    var $domIdBar = $("<div/>",{
        id : "idBar"
    });

    var $domIdSliderDown = $("<div/>",{
        class : "idSliderDown"
    });

    $domTr.appendTo($domThead);
    $domModeTable.appendTo($domDeatil);
    $domTable.appendTo($domModeTable);
    $domThead.appendTo($domTable);
    $domTbody.appendTo($domTable);

    $domIdBar.appendTo($domIdSlider);
    $domIdSliderUp.appendTo($domRightBar);
    $domIdSlider.appendTo($domRightBar);
    $domIdSliderDown.appendTo($domRightBar);
    $domRightBar.appendTo($domDeatil);



    $domDeatil.appendTo($("#create_mode_apply .body"));
    initTableTh();
    showTimeModeDetail(thisId);


}

function getModeListBySystemCode(system_id)
{
	var modeList = new Array();
	var mode = null;
	var url = baseurl+'/pattern/getPatternBySystemCode';
    var d = new Date();
    var r = Math.random()+""+d.getMilliseconds();
	var params ={systemCode:system_id,r:r};
	$.ajax({url:url,type:'GET',dataType:'json',async:false,data:params,success:function(data){
	var i = 0;
	for (i=0;i<data.length;i++)
  {
    mode = {"id":data[i].id,"systemId":data[i].systemId,"name":data[i].name,"description":data[i].description};
    modeList.push(mode);   	
  }
  } 	     
  });
  return modeList;
}


function addNewDeviceException(device_id)
{
	/*
	for(i=0;i<device_list.length;i++)
	{
	  if (device_id == parseInt(device_list[i].Equpment.id))
	  {
	    $(".device_exception_table > .device_name >span").html(device_list[i].Equpment.name);
	    break;
	  }  
	} 
	*/ 
}


function saveModeApply()
{
	
//	var system_mode_id = $("#system_mode").val();
//	var systemId = $("#system_mode").attr('systemId');
    var systemId = $(".allModeList").attr('systemId');
    var system_mode_id = $(".hideValue").val();
	var reason = $("#changeReason").val();
	
	var url = baseurl+'/pattern/control/changePatternRun';
	var params ={systemId:systemId,patternId:system_mode_id,reason:reason};
	$.ajax({url:url,type:'POST',dataType:'json',async:true,data:params,success:function(data){
	  if (data.success == true)
    {
    	showMessageInDialog('系统模式切换成功!','tip');
        changePattern();
        clearControlBtn();
        addMode();
        if(systemId=='LSPUB' || systemId=='LSN'){

        }else{
            submenu.showCurWindow();
        }
    }
    else 
    {
    	showMessageInDialog('系统模式切换失败,请稍后再试!','error');
    }
    var t=setTimeout('hideDialog()',0);
	
	} 	     
  });
}
function resetTwos(tr_list)
{
	if (tr_list.length == 0)
	  return;
	
	var twos = "even";
	var i = 0 ;
	for(i=0;i<tr_list.length;i++)
	{
		if ((i+1)%2 == 0)
	    twos = "odd";
	  else
	  	twos = "even";
	  if ($(tr_list.get(i)).attr('class') != twos)
		  $(tr_list.get(i)).attr('class',twos);
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

function showTimeModeDetail(current_time_mode_id)
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

function addNewTimeModeItem(param)
{
    var line = $(".mode_table >table >tbody >tr").length + 1;
    var twos = "even";
    if ((line)%2 == 0)
        twos = "odd";
    else
        twos = "even";

    var tr = '<tr id="mode_item_'+param.id+'\" class=\"'+twos+'\">'+'\n';
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
        else if (newItem[i].type=="checkboxGroup")
        {
            var tempValue = "";
            var list = newItem[i].value.split(",");
            for (var m=0;m<list.length;m++)
            {
                for (var j=0;j<coldDeviceGroupControl.length;j++)
                {
                    if (list[m] == coldDeviceGroupControl[j].code)
                    {
                        if (m<list.length-1)
                            tempValue = tempValue + coldDeviceGroupControl[j].name +",";
                        else
                            tempValue = tempValue + coldDeviceGroupControl[j].name;
                        break;
                    }
                }
            }
            tr = tr +'<td class="'+newItem[i].code+'">'+tempValue+'</td>'+'\n';
        }
        else if (newItem[i].type == "multiList")
        {
            tr = tr +'<td class="'+newItem[i].code+'">'+"大"+newItem[i].value.substring(0,1)+",小"+newItem[i].value.substring(2)+'</td>'+'\n';
        }
        else
            tr = tr +'<td class="'+newItem[i].code+'">'+newItem[i].value+'</td>'+'\n';
    }
    tr = tr + '</tr>'+'\n';

    $(".mode_table >table >tbody").append(tr);


//    //获取设备信息以及strategyId, 绑定到最后一行，用于编辑的时候，显示在界面上
//    for (var m=0;m<param.item.length;m++)
//    {
//        if (param.item[m].code=="deviceList")
//        {
//            $(".mode_table >table >tbody tr:last").data("deviceList",param.item[m].value);
//        }
//        else if (param.item[m].code=="strategyId")
//        {
//            $(".mode_table >table >tbody tr:last").data("strategyId",param.item[m].value);
//        }
//    }
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
            for(var k=0;k<ucRunParams.length;k++)
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

function timeToString(input)
{
    var hour = input.substring(0,2);
    var minute = input.substring(2,4);

    return hour+":"+minute;
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
