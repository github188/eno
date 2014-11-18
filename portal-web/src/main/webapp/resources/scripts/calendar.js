//以下为日历模块中日、周、月、日程表公共的javascript

var baseurl = CONTEXT_PATH;
var system_code = "HVAC";
var current_clock_h =0;
var current_clock_m =0;
var day_clock_code = null;
var week_clock_code = null;
var device_list = null;
var activityList = null;

var weatherList = new Array();
weatherList[0] = {"name":"晴天","code":"sunny","wundergroundCode":['sunny','clear','mostlysunny']};
//    weatherList[1] = {"name":"雷阵雨转晴","code":"raintosunny","wundergroundCode":['']};
weatherList[1] = {"name":"大雪","code":"bigsnow","wundergroundCode":['snow']};
weatherList[2] = {"name":"大雨","code":"bigrain","wundergroundCode":['rain']};
weatherList[3] = {"name":"多云","code":"cloudy","wundergroundCode":['partlysunny','mostlycloudy']};
//    weatherList[5] = {"name":"中雨","code":"middlerain","wundergroundCode":['']};
weatherList[4] = {"name":"夜间多云","code":"nightcloudy","wundergroundCode":['nt_cloudy','nt_partlycloudy','nt_mostlysunny']};
weatherList[5] = {"name":"阴天","code":"overcast","wundergroundCode":['cloudy']};
//    weatherList[8] = {"name":"晴转小雨","code":"sunnytosmallrain","wundergroundCode":['']};
weatherList[6] = {"name":"雨夹雪","code":"rainandsnow","wundergroundCode":['sleet','chancesleet']};
weatherList[7] = {"name":"冰雹","code":"hail","wundergroundCode":['chancetstorms','tstorms']};
//    weatherList[11] = {"name":"雷阵雨","code":"thunderstorm","wundergroundCode":['','']};
weatherList[8] = {"name":"小雪","code":"smallsnow","wundergroundCode":['chancesnow']};
weatherList[9] = {"name":"小雨","code":"smallrain","wundergroundCode":['chancerain']};
weatherList[10] = {"name":"夜间晴天","code":"nightsunny","wundergroundCode":['nt_sunny','nt_clear']};
//    weatherList[15] = {"name":"雨夹冰雹","code":"rainandhail","wundergroundCode":['']};
weatherList[11] = {"name":"风","code":"wind","wundergroundCode":['flurries','chanceflurries']};
weatherList[12] = {"name":"雾霭","code":"fog","wundergroundCode":['hazy','fog']};
weatherList[13] = {"name":"晴间多云","code":"suncloudy","wundergroundCode":['partlycloudy']};

var ucParamList = new Array();
ucParamList[0] = {"code":"status_sp","name":"启停状态","unit":""};
ucParamList[1] = {"code":"number_on","name":"纳入群控的冷机","unit":""};
ucParamList[2] = {"code":"max_number_on","name":"最多开启的冷机","unit":""};
ucParamList[3] = {"code":"t_chw_s_sp","name":"供水温度","unit":"℃"};
ucParamList[4] = {"code":"dp_chw_s_sp","name":"供回水压差","unit":"mH2O"};
ucParamList[5] = {"code":"dt_chw_s_sp","name":"供回水温差","unit":"℃"};
ucParamList[6] = {"code":"t_hw_s_sp","name":"供水温度","unit":"℃"};
ucParamList[7] = {"code":"p_hw_s_sp","name":"供水压力","unit":"mH2O"};
ucParamList[8] = {"code":"frequency_sp","name":"水泵频率","unit":"Hz"};
ucParamList[9] = {"code":"t_sa_sp","name":"送风温度","unit":"℃"};
ucParamList[10] = {"code":"t_freeze_sp","name":"防冻温度","unit":"℃"};
ucParamList[11] = {"code":"frequency_sf_sp","name":"风机频率","unit":"Hz"};
ucParamList[12] = {"code":"t_ra_sp","name":"回风温度","unit":"℃"};
ucParamList[13] = {"code":"u_damper_oa_sp","name":"新风阀开度","unit":"%"};
ucParamList[14] = {"code":"co2_ra_sp","name":"回风CO2浓度","unit":"ppm"};

var coldDeviceGroupControl = [{"code":"1","name":"冷机1"},{"code":"2","name":"冷机2"},{"code":"3","name":"冷机3"},{"code":"4","name":"冷机4"}];

var todayWeatherIcon = "";
var todayWeatherDesc = "";
var todayMinTemperature = "";
var todayMaxTemperature = "";
var todaySunriseTime = "";
var todaySundownTime ="";
function getCssFromWeatherIcon(icon)
{
    if(icon == null)
      return "";
    
    var cssName = ""; 
    
    var findIt = false;
    for (var i=0;i<weatherList.length;i++)
    {
        for(var j=0;j<weatherList[i].wundergroundCode.length;j++)
        {
            if (weatherList[i].wundergroundCode[j] == icon)
            {
                cssName = weatherList[i].code;
                findIt = true;
                break;
            }
        }
        if (findIt)
            break;
    }
    if (findIt)
        return cssName;
    else
    {
        if (icon.substring(0,3)=="nt_")
        {
            icon = icon.substring(3);
            return getCssFromWeatherIcon(icon);
        }
        else
            return "picnotfound";
    }
}

function getWeatherIconFromCss(cssName)
{
    var icon = "";
    for (var i=0;i<weatherList.length;i++)
    {
        if (weatherList[i].code == cssName)
        {
            icon = weatherList[i].wundergroundCode[0];
            break;
        }
    }
    return icon;
}

function getWeatherIconFromName(name)
{
    var icon = "";
    for (var i=0;i<weatherList.length;i++)
    {
        if (weatherList[i].name == name)
        {
            icon = weatherList[i].wundergroundCode[0];
            break;
        }
    }
    return icon;
}


var holidayList = [{"name":"国家法定节假日","code":"G"},{"name":"自定义假日","code":"C"},{"name":"平日","code":"O"}];

function getSystemId()
{
    return systemId;
}

function getDateTime(timestamp)
{
    var dateTime = {"year":"","month":"","date":"","hour":"","minute":""};
    var today = new Date();
    today.setTime(timestamp);

    dateTime.year = today.getFullYear()+"";

    var month = "";
    month = today.getMonth() + 1;
    if (parseInt(month) < 10)
        month = "0"+parseInt(month);
    dateTime.month = month;

    var date = "";
    date = today.getDate();
    if (parseInt(date) < 10)
        date = "0" + parseInt(date);
    dateTime.date = date;

    var hour = "";
    hour = today.getHours();
    if (parseInt(hour) < 10)
        hour = "0"+parseInt(hour);
    dateTime.hour = hour;

    var minute = "";
    minute = today.getMinutes();
    if (parseInt(minute) < 10)
        minute = "0" + parseInt(minute);
    dateTime.minute = minute;

    return dateTime;
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

/*
 以下为日视图相关的javascript function
 */
function resetDayCurrentClockLine(hour,minute)
{
    current_clock_h = parseInt(hour);
    current_clock_m = parseInt(minute);
    if ((parseInt(hour) <0) || (parseInt(hour) >23) || (parseInt(minute) <0) || (parseInt(minute) >59))
    {
        $('.current_clock').hide();
        $('#now_time_line').hide();
        return;
    }
    if (parseInt(minute) < 10)
        minute = "0"+parseInt(minute);
    $('.current_clock_time').html(hour+":"+minute);
    var base_left = 0;

    var m = parseInt(hour)*60 + parseInt(minute);
    //var base_left = Math.round(m * (1650 - 102 - 102 -3 - 3)/(24 * 60)) + 105 + 255;  // 刚好能除尽，1个像素1分钟，所以用round或floor都行
    var base_left = Math.round(m * (1650 - 102 - 102 -3 - 3)/(24 * 60)) + 105 + 15;
    $('.current_clock').css('left',base_left-30+'px');
    $('#now_time_line').css('left',(base_left-1)+'px');   // because #now_time_line 's width = 2px

    day_clock_code = setInterval('refreshDayClock()',60000);
}

function refreshDayClock()
{
    window.clearInterval(day_clock_code);
    if (current_clock_m == 59)
    {
        current_clock_h = current_clock_h + 1;
        current_clock_m = 0;
    }
    else
    {
        current_clock_m = current_clock_m + 1;
    }
    resetDayCurrentClockLine(current_clock_h,current_clock_m);
}

function resetDaySunUpClockLine(hour,minute)
{
    if ((parseInt(hour) <0) || (parseInt(hour) >23) || (parseInt(minute) <0) || (parseInt(minute) >59))
        return;

    var m = parseInt(hour)*60 + parseInt(minute);

    var w = Math.round(m * (1650 - 102 -102 -3 -3)/(24 * 60)) + 105 ;

    $('.night_left').css('width',w+'px');
}

function resetDaySunDownClockLine(hour,minute)
{
    if ((parseInt(hour) <0) || (parseInt(hour) >23) || (parseInt(minute) <0) || (parseInt(minute) >59))
        return;

    var m = parseInt(hour)*60 + parseInt(minute);

    var w = Math.round(m * (1650 - 102 - 102 - -3 )/(24 * 60)) + 105;

    w = 1650 - w ;
    $('.night_right').css('width',w+'px');
}

function resetMaxDate(yyyy,mm)
{
    var d = new Date(yyyy,parseInt(mm),0);
    max_date = d.getDate();
}

function gotoFirstDate(yyyy,mm)
{
    var dd = "01";

    if (parseInt(mm) < 10)
        mm = "0"+parseInt(mm);
    selectDate(yyyy,mm,dd);
}

function selectDate(yyyy,mm,dd)
{
    selected_yyyy = yyyy;
    selected_mm = mm;
    selected_dd = dd;
    d = new Date(parseInt(selected_yyyy),parseInt(selected_mm)-1,parseInt(selected_dd));
    str = selected_mm + "/"+selected_dd+ " "+weekday[d.getDay()];      // here have a question:  if month and day number < 10 , plus 0 in the left?
    $('#selected_day').html(str);
}

function gotoCurrentDate()
{
    $('.year_list').hide();
    $('.month_list').hide();
    if ((selected_yyyy == current_yyyy) && (selected_mm == current_mm) &&(selected_dd == current_dd))
        return;
    $('#current_year').html(current_yyyy);
    $('#current_month').html(current_mm);
    selected_yyyy = current_yyyy;
    selected_mm = current_mm;
    selected_dd = current_dd;
    selectDate(selected_yyyy,selected_mm,selected_dd);
    clearDayView();
    getDayModeApply();
    getDayActivity();
    getDayWeather();
}

function gotoLastDate()
{
    if (parseInt(selected_dd) == 1) return;          // here have a question: can I goto last month when goto last day?
    selected_dd = parseInt(selected_dd) - 1;
    if (parseInt(selected_dd) < 10)
        selected_dd = "0" + parseInt(selected_dd);
    selectDate(selected_yyyy,selected_mm,selected_dd);
    clearDayView();
    getDayModeApply();
    getDayActivity();
    getDayWeather();
}
function gotoNextDate()
{
    if (parseInt(selected_dd) == max_date) return;
    selected_dd = parseInt(selected_dd) + 1;
    if (parseInt(selected_dd) < 10)
        selected_dd = "0" + parseInt(selected_dd);
    selectDate(selected_yyyy,selected_mm,selected_dd);
    clearDayView();
    getDayModeApply();
    getDayActivity();
    getDayWeather();
}

function clearDayView()
{
    $(".mode_list_for_oneday > ul").html('');
    $("#today_activity >span").html('');
    $("#today_mode >span").html('');
    $("#today_weather >span").html('');
}

function getDayModeApply()
{
    var url = baseurl+'/calendar/getCalendarPattern';
    var d = new Date();
    var r = Math.random()+""+d.getMilliseconds();
    var system_id = getSystemId();
    var params ={systemId:system_id,startDate:selected_yyyy+""+selected_mm+""+selected_dd,endDate:selected_yyyy+""+selected_mm+""+selected_dd,r:r};
    $.ajax({url:url,type:'GET',dataType:'json',async:true,data:params,success:function(data){
            var start_time = "";
            var end_time = "";
            var equpment_type ="";
            var li = "";
            var mode_name ="";
            var default_mode_name = "";
            var modelist = data;
            var deviceRecords = null;
            var f_d = new Date();
            var l_d = new Date();
            var h = "";
            var m = "";
            if (modelist.length > 0)
              default_mode_name = modelist[0].patternName;
            for (var i=0;i<modelist.length;i++)
            {
                deviceRecords = modelist[i].ucDeviceRecords;
                equpment_type = modelist[i].systemId;
                mode_name = modelist[i].patternName;
                if (modelist[i].systemId=="MAHU")
                    default_mode_name = modelist[i].patternName;
                for(var j=0;j<deviceRecords.length;j++)
                {
                    if (deviceRecords[j].startTime ==null)
                    {
                        start_time = "";
                    }
                    else
                    {
                        f_d.setTime(deviceRecords[j].startTime);
                        if (f_d.getHours()<10)
                            h = "0"+f_d.getHours();
                        else
                            h = f_d.getHours();
                        if (f_d.getMinutes()<10)
                            m = "0"+f_d.getMinutes();
                        else
                            m = f_d.getMinutes();
                        start_time = h+""+m;
                    }
                    if (deviceRecords[j].endTime ==null)
                    {
                        end_time = "";
                    }
                    else
                    {
                        l_d.setTime(deviceRecords[j].endTime);
                        if (l_d.getHours()<10)
                            h = "0"+l_d.getHours();
                        else
                            h = l_d.getHours();
                        if (l_d.getMinutes()<10)
                            m = "0"+l_d.getMinutes();
                        else
                            m = l_d.getMinutes();
                        end_time = h+""+m;
                    }
                    console.log("i="+i+" mode_name:"+mode_name+" start_time:"+start_time+" end_time:"+end_time);
                    li = renderDayMode(start_time,end_time,mode_name,equpment_type,deviceRecords[j].ucParamRecords);
                    $(".mode_list_for_oneday > ul").append(li);
                }
            }
            $("#today_mode >span").html(default_mode_name);
        }
        }
    );
}

function renderDayMode(start_time,end_time,mode_name,equpment_type,ucParamRecords)
{
    if ((start_time=="")&&(end_time==""))
      return "";
    if (start_time=="")
      start_time = end_time;
    if (end_time =="")
      end_time = start_time;    
    var start_hour = start_time.substring(0,2);
    var start_minute = 	start_time.substring(2,4);
    var end_hour = end_time.substring(0,2);
    var end_minute = 	end_time.substring(2,4);
    var systemName = "";
    if (equpment_type=="CoolingSource")
      systemName = "冷源";
    else if  (equpment_type=="HeatSource")
    	systemName = "热源";
    else if  (equpment_type=="FAVU")
    	systemName = "新风机组";
    else if  (equpment_type=="MAHU")
    	systemName = "组合空调";	
    else if  (equpment_type=="BUAHU")
    	systemName = "吊装空调";
    else if  (equpment_type=="AVU")
    	systemName = "通风机组";	
    			
    var style_w ="";
    var w = 0;
    var start_left = getDayViewLeft(start_hour,start_minute)-3;
    var li = '<li style="margin-left:0px;min-height:100px;font-size:0px">';
    start_left = start_left - 100;
    li = li +'<div style="margin-left:20px;width:80px;height:100px;line-height:100px;display:inline-block;font-size:18px">'+systemName+'</div>';
    li = li+ "\n" +'<div class="mode_section '+equpment_type+'" style="display:inline-block;vertical-align:top;min-height:100px;margin-left:'+start_left+'px">';
    li = li+ "\n" +'<div class="clock_segment" style="margin-left:0px" onmouseout="javascript:hideClockDetailMode()" onmouseover="javascript:showClockDetailModeInDayView(event,\''+mode_name+'\')">';
    li = li+ "\n" +'<div class="big_hour_dot"></div>';

    var hour_count = parseInt(end_hour)-parseInt(start_hour);

    for(var i=0;i<hour_count;i++)
    {
        if((i==0)&&(parseInt(start_minute)>0))
        {
            w = 60 - parseInt(start_minute) - 6 ;
            style_w = 'width:'+w+'px';
            li = li + "\n" + '<div class="open_line" style="'+style_w+'"></div>';
        }
        else
        {
            li = li + "\n" + '<div class="open_line"></div>';
        }
        li = li + "\n" + '<div class="big_hour_dot"></div>';
    }
    if (parseInt(end_minute) > 0)
    {
        w = parseInt(end_minute) -6 ;
        style_w = 'width:'+w+'px';
        li = li + "\n" + '<div class="open_line" style="'+style_w+'"></div>';
        li = li + "\n" + '<div class="big_hour_dot"></div>';
    }

    li = li + "\n" +"</div>"

    var separate_line_h = ucParamRecords.length * 20;
    li = li + "\n" +'<div class="detail_mode">';
    li = li + "\n" +'<div class="separate_line" style="height:'+separate_line_h+'px"></div>';
    li = li + "\n" +'<div class="mode_desc">';
    li = li + "\n" +'<ul>';

    var paramName = "";
    var newValue = "";
    var unit = "";
    for(var k=0;k<ucParamRecords.length;k++)
    {
        paramName = getParamNameByCode(ucParamRecords[k].paramName);
        newValue = transferParamValueByCode(ucParamRecords[k].paramName,ucParamRecords[k].paramValue);
        unit = getParamUnitByCode(ucParamRecords[k].paramName);
        li = li + "\n" + '<li>'+paramName+":"+newValue+unit+'</li>'; 
    }

    li = li + "\n" +'</ul>';
    li = li + "\n"	+'</div>';
    li = li + "\n"	+'</div>';

    li = li + "\n" +"</div>"
    li = li + "\n"+"</li>";
    return li;
}

function getParamNameByCode(code)
{
	var name = "";
	for (var i=0;i<ucParamList.length;i++)
	{
		if (ucParamList[i].code == code)
		{
			name = ucParamList[i].name;
			break;
		}
	}
	return name;
}

function getParamUnitByCode(code)
{
	var unit = "";
	for (var i=0;i<ucParamList.length;i++)
	{
		if (ucParamList[i].code == code)
		{
			unit = ucParamList[i].unit;
			break;
		}
	}
	return unit;
}

function transferParamValueByCode(code,value)
{
	var newValue = "";
	switch(code)
	{
		case "status_sp":
		  newValue = (value==1)?"ON":"OFF";
		  break;
		case "number_on":
		  var list = value.split(",");
			for (var m=0;m<list.length;m++)
			{
				for (var j=0;j<coldDeviceGroupControl.length;j++)
			  {
				  if (list[m] == coldDeviceGroupControl[j].code)
				  {
				  	if (m<list.length-1)
				  	  newValue = newValue + coldDeviceGroupControl[j].name +",";
				  	else
				  		newValue = newValue + coldDeviceGroupControl[j].name;  
				    break;		
				  }
			  }  
			}
		  break;
		case "max_number_on":
		  newValue = "大"+value.substring(0,1)+",小"+value.substring(2);
		  break;
		default:
		  newValue = value;  
	}
	return newValue;
}

function getDayViewLeft(hour,minute)
{
    var m = parseInt(hour)*60 + parseInt(minute);
    var base_left = Math.round(m * (1650 - 102 - 102 -3 -3)/(24 * 60)) + 102 +3;
    return base_left;
}
function showClockDetailModeInDayView(e,mode_name)
{
    var h = Math.floor((e.clientX - 255 - 102 -3 )/60);
    var m = (e.clientX - 255 - 102 -3 )%60;
    if (m<10)
        m = "0"+m;
    $("#mode_toast").html(h+":"+m+" "+mode_name);
    $("#mode_toast").css('left',(e.clientX-70)+"px");
    $("#mode_toast").css('top',(e.clientY-30)+"px");
    $("#mode_toast").show();
}

function hideClockDetailMode()
{
    $("#mode_toast").html('');
    $("#mode_toast").hide();
}

function getDayActivity()
{
    var url = baseurl+'/calendar/getCalendarEvent';
    var d = new Date();
    var r = Math.random()+""+d.getMilliseconds();
    var params ={startDate:selected_yyyy+""+selected_mm+""+selected_dd,endDate:selected_yyyy+""+selected_mm+""+selected_dd,r:r};
    $.ajax({url:url,type:'GET',dataType:'json',async:true,data:params,success:function(data){
            var a_name = "";
            var separate = " , ";
            var activity = data;
            for (var i=0;i<activity.length;i++)
            {
                if (i == activity.length -1)
                    separate = "";
                a_name = a_name + activity[i].title + separate;
            }
            $("#today_activity >span").html(a_name);
        }
        }
    );
}

function getDayWeather()
{
    var url = baseurl+'/calendar/getCalendarWeather';
    var d = new Date();
    var r = Math.random()+""+d.getMilliseconds();
    var params ={startDate:selected_yyyy+""+selected_mm+""+selected_dd,endDate:selected_yyyy+""+selected_mm+""+selected_dd,r:r};
    $.ajax({url:url,type:'GET',dataType:'json',async:true,data:params,success:function(data){
            var w_name = "";
            var weather = data;
            if(weather.length >0){
                todayWeatherIcon = weather[0].ucWeathers[0].weatherIcon;
                todayWeatherDesc = weather[0].ucWeathers[0].weatherDescription;
                todayMinTemperature = weather[0].ucWeathers[0].minTemperature;
                todayMaxTemperature = weather[0].ucWeathers[0].maxTemperature;
                todaySunriseTime = weather[0].ucWeathers[0].sunrise;
                todaySundownTime = weather[0].ucWeathers[0].sunset;
                w_name = weather[0].ucWeathers[0].weatherDescription + 	" , " + weather[0].ucWeathers[0].minTemperature+"℃ - " + weather[0].ucWeathers[0].maxTemperature+"℃ 日出 "+weather[0].ucWeathers[0].sunrise+" AM , 日落 " + weather[0].ucWeathers[0].sunset+" PM" ;
                $("#today_weather >span").html(w_name);
                var sunrise = weather[0].ucWeathers[0].sunrise;
                var sunrise_h = sunrise.substring(0,2);          //sunrise如果为NULL?
                var sunrise_m = sunrise.substring(3,5);
                var sunset = weather[0].ucWeathers[0].sunset;    //sunset如果为NULL?
                var sunset_h = sunset.substring(0,2);
                var sunset_m = sunset.substring(3,5);
                resetDaySunUpClockLine(sunrise_h,sunrise_m);
                resetDaySunDownClockLine(sunset_h,sunset_m);
            } else {
                return false;
            }

        }
        }
    );
}

/*
 以下是周视图相关的javascript function
 */

function resetWeekCurrentClockLine(hour,minute)
{
    current_clock_h = parseInt(hour);
    current_clock_m = parseInt(minute);
    if ((parseInt(hour) <0) || (parseInt(hour) >23) || (parseInt(minute) <0) || (parseInt(minute) >59))
    {
        $('.current_clock').hide();
        $('#now_time_line').hide();
        return;
    }
    if (parseInt(minute) < 10)
        minute = "0"+parseInt(minute);
    $('.current_clock_time').html(hour+":"+minute);
    var base_left = 0;

    var m = parseInt(hour)*60 + parseInt(minute);
    //var base_left = Math.round(m * (1300 - 47 - 3 - 47 -3)/(24 * 60)) + 255 + 350 + 47 +3;  // 这里因为有小数点,进行了四舍五入，因此并不精确
    var base_left = Math.round(m * (1300 - 47 - 3 - 47 -3)/(24 * 60)) + 15 + 350 + 47 +3;
    $('.current_clock').css('left',base_left-30+'px');
    $('#now_time_line').css('left',(base_left-1)+'px');

    week_clock_code = setInterval('refreshWeekClock()',60000);
}

function refreshWeekClock()
{
    window.clearInterval(week_clock_code);
    if (current_clock_m == 59)
    {
        current_clock_h = current_clock_h + 1;
        current_clock_m = 0;
    }
    else
    {
        current_clock_m = current_clock_m + 1;
    }
    resetWeekCurrentClockLine(current_clock_h,current_clock_m);
}

function getWeekSunUpClockLineWidth(hour,minute)
{
    if ((parseInt(hour) <0) || (parseInt(hour) >23) || (parseInt(minute) <0) || (parseInt(minute) >59))
        return;

    var m = parseInt(hour)*60 + parseInt(minute);

    var w = Math.round(m * (1300 -47 -3 -47 -3)/(24 * 60)) + 47 +3;

    return w;
}

function getWeekSunDownClockLineWidth(hour,minute)
{
    if ((parseInt(hour) <0) || (parseInt(hour) >23) || (parseInt(minute) <0) || (parseInt(minute) >59))
        return;

    var m = parseInt(hour)*60 + parseInt(minute);

    var w = Math.round(m * (1300 - 47 -3 -47 -3 )/(24 * 60)) +47 +3;

    w = 1300 - w ;

    return w;
}

//包括今天在内的这一周，从周一开始的7天
function resetDateRangeCrossMonth(yyyy,mm,dd)
{
    selected_yyyy = yyyy;
    selected_mm = mm;

    d = new Date();
    d.setFullYear(parseInt(yyyy),parseInt(mm)-1,parseInt(dd));
    var i = d.getDay();

    switch(i)
    {
        case 0:
            first_date.setFullYear(d.getFullYear());
            first_date.setMonth(d.getMonth());
            first_date.setDate(d.getDate()-6);
            last_date.setFullYear(d.getFullYear());
            last_date.setMonth(d.getMonth());
            last_date.setDate(d.getDate());
            break;
        case 1:
            first_date.setFullYear(d.getFullYear());
            first_date.setMonth(d.getMonth());
            first_date.setDate(d.getDate());
            last_date.setFullYear(first_date.getFullYear());
            last_date.setMonth(first_date.getMonth());
            last_date.setDate(first_date.getDate()+6);
            break;
        case 2:
            first_date.setFullYear(d.getFullYear());
            first_date.setMonth(d.getMonth());
            first_date.setDate(d.getDate()-1);
            last_date.setFullYear(first_date.getFullYear());
            last_date.setMonth(first_date.getMonth());
            last_date.setDate(first_date.getDate()+6);
            break;
        case 3:
            first_date.setFullYear(d.getFullYear());
            first_date.setMonth(d.getMonth());
            first_date.setDate(d.getDate()-2);
            last_date.setFullYear(first_date.getFullYear());
            last_date.setMonth(first_date.getMonth());
            last_date.setDate(first_date.getDate()+6);
            break;
        case 4:
            first_date.setFullYear(d.getFullYear());
            first_date.setMonth(d.getMonth());
            first_date.setDate(d.getDate()-3);
            last_date.setFullYear(first_date.getFullYear());
            last_date.setMonth(first_date.getMonth());
            last_date.setDate(first_date.getDate()+6);
            break;
        case 5:
            first_date.setFullYear(d.getFullYear());
            first_date.setMonth(d.getMonth());
            first_date.setDate(d.getDate()-4);
            last_date.setFullYear(first_date.getFullYear());
            last_date.setMonth(first_date.getMonth());
            last_date.setDate(first_date.getDate()+6);
            break;
        case 6:
            first_date.setFullYear(d.getFullYear());
            first_date.setMonth(d.getMonth());
            first_date.setDate(d.getDate()-5);
            last_date.setFullYear(first_date.getFullYear());
            last_date.setMonth(first_date.getMonth());
            last_date.setDate(first_date.getDate()+6);
            break;
    }
}

//这个不能跨月，暂时不用了
function resetDateRange(yyyy,mm,dd)
{
    selected_yyyy = yyyy;
    selected_mm = mm;
    d = new Date();
    d.setFullYear(parseInt(yyyy),parseInt(mm)-1,parseInt(dd));
    var i = d.getDay();

    switch(i)
    {
        case 0:
            selected_first_day = dd - 6;
            if (selected_first_day < 1)
                selected_first_day = 1;
            selected_last_day = dd;
            break;
        case 1:
            selected_first_day = dd;
            selected_last_day = dd + 6;
            if (selected_last_day > max_date)
                selected_last_day = max_date;
            break;
        case 2:
            selected_first_day = dd - 1;
            if (selected_first_day < 1)
                selected_first_day = 1;
            selected_last_day = dd + 5;
            if (selected_last_day > max_date)
                selected_last_day = max_date;
            break;
        case 3:
            selected_first_day = dd - 2;
            if (selected_first_day < 1)
                selected_first_day = 1;
            selected_last_day = dd + 4;
            if (selected_last_day > max_date)
                selected_last_day = max_date;
            break;
        case 4:
            selected_first_day = dd - 3;
            if (selected_first_day < 1)
                selected_first_day = 1;
            selected_last_day = dd + 3;
            if (selected_last_day > max_date)
                selected_last_day = max_date;
            break;
        case 5:
            selected_first_day = dd - 4;
            if (selected_first_day < 1)
                selected_first_day = 1;
            selected_last_day = dd + 2;
            if (selected_last_day > max_date)
                selected_last_day = max_date;
            break;
        case 6:
            selected_first_day = dd - 5;
            if (selected_first_day < 1)
                selected_first_day = 1;
            selected_last_day = dd + 1;
            if (selected_last_day > max_date)
                selected_last_day = max_date;
            break;
    }
}

function gotoCurrentWeek(){
    $('.year_list').hide();
    $('.month_list').hide();
    $('#current_year').html(current_yyyy);
    $('#current_month').html(current_mm);
    selected_yyyy = current_yyyy;

    selected_mm = current_mm;

    resetDateRangeCrossMonth(current_yyyy,current_mm,current_dd);
    $("#selected_week").html((first_date.getMonth()+1)+"/"+first_date.getDate()+"-"+(last_date.getMonth()+1)+"/"+last_date.getDate());
    $("#week_range").html((first_date.getMonth()+1)+"."+first_date.getDate()+"-"+(last_date.getMonth()+1)+"."+last_date.getDate());
    clearWeekView();
    highLightCurrentDayInWeekView();
    getWeekModeApply();
    getWeekActivity();
    getWeekWeather();
}

function gotoLastWeek()
{
    first_date.setDate(first_date.getDate()-7);
    last_date.setDate(last_date.getDate()-7);

    $("#selected_week").html((first_date.getMonth()+1)+"/"+first_date.getDate()+"-"+(last_date.getMonth()+1)+"/"+last_date.getDate());
    $("#week_range").html((first_date.getMonth()+1)+"."+first_date.getDate()+"-"+(last_date.getMonth()+1)+"."+last_date.getDate());
    clearWeekView();
    highLightCurrentDayInWeekView();
    getWeekModeApply();
    getWeekWeather();
    getWeekActivity();
}

function gotoNextWeek()
{
    first_date.setDate(first_date.getDate()+7);
    last_date.setDate(last_date.getDate()+7);

    $("#selected_week").html((first_date.getMonth()+1)+"/"+first_date.getDate()+"-"+(last_date.getMonth()+1)+"/"+last_date.getDate());
    $("#week_range").html((first_date.getMonth()+1)+"."+first_date.getDate()+"-"+(last_date.getMonth()+1)+"."+last_date.getDate());
    clearWeekView();
    highLightCurrentDayInWeekView();
    getWeekModeApply();
    getWeekWeather();
    getWeekActivity();
}

function getWeekModeApply()
{
    var url = baseurl+'/calendar/getScheduler';
    var system_id = getSystemId();
    var f_month = first_date.getMonth()+1;
    if (f_month < 10)
        f_month = "0"+f_month;
    var f_day = first_date.getDate();
    if (f_day < 10)
        f_day = "0"+f_day;
    var l_month = last_date.getMonth()+1;
    if (l_month < 10)
        l_month = "0"+l_month;
    var l_day = last_date.getDate();
    if (l_day < 10)
        l_day = "0"+l_day;
    var d = new Date();
    var r = Math.random()+""+d.getMilliseconds();
    var params ={systemId:system_id,startDate:first_date.getFullYear()+""+f_month+""+f_day,endDate:last_date.getFullYear()+""+l_month+""+l_day,r:r};
    $.ajax({url:url,type:'GET',dataType:'json',async:true,data:params,success:function(data){

            hideLoading();
            var default_mode_name ="";
            var mode_name = "";
            var dayWeek = "";
            var index_d = 1;
            var start_time = "";
            var end_time = "";
            var f_d = new Date();
            var l_d = new Date();
            var h = "";
            var m = "";
            var clock_segment = "";

            var modelist = data;
            var ucPatternRecords = null;
            var ucDeviceRecords = null;
            var dayType = "pastDay";
            var crossMonth = last_date.getMonth()+1; //如果跨月，取后面一个月的月份 
            if (crossMonth < 10);
            crossMonth = "0"+crossMonth;

            for (var i=0;i<modelist.length;i++)
            {

                ucCalendar = modelist[i].ucCalendar ;
                if (ucCalendar.id >= current_yyyy+current_mm+current_dd)
                    dayType = "futureDay";
                else if (ucCalendar.id < last_date.getFullYear()+""+crossMonth+"01")  //确定是上个月的  
                    dayType = "pastMonth";
                else
                    dayType = "pastDay";

                ucPatternRecords = modelist[i].ucPatternRecords;

                if (ucPatternRecords.length > 0)
                  default_mode_name = ucPatternRecords[0].patternName;
                for(var j=0;j<ucPatternRecords.length;j++)
                {
                    system_code = ucPatternRecords[j].systemId;
                    if (system_code=="MAHU")
                      default_mode_name = ucPatternRecords[j].patternName;
                    
                    mode_name = ucPatternRecords[j].patternName;
                    
                    ucDeviceRecords = ucPatternRecords[j].ucDeviceRecords;
                    for (var k=0;k<ucDeviceRecords.length;k++)
                    {
                        if (ucDeviceRecords[k].startTime ==null)
                        {
                            start_time = "";
                        }
                        else
                        {
                            f_d.setTime(ucDeviceRecords[k].startTime);
                            if (f_d.getHours()<10)
                                h = "0"+f_d.getHours();
                            else
                                h = f_d.getHours();
                            if (f_d.getMinutes()<10)
                                m = "0"+f_d.getMinutes();
                            else
                                m = f_d.getMinutes();
                            start_time = h+""+m;
                        }

                        if (ucDeviceRecords[k].endTime ==null)
                        {
                            end_time = "";
                        }
                        else
                        {
                            l_d.setTime(ucDeviceRecords[k].endTime);
                            if (l_d.getHours()<10)
                                h = "0"+l_d.getHours();
                            else
                                h = l_d.getHours();
                            if (l_d.getMinutes()<10)
                                m = "0"+l_d.getMinutes();
                            else
                                m = l_d.getMinutes();
                            end_time = h+""+m;
                        }
                        console.log("i="+i+" start_time:"+start_time+" end_time:"+end_time+" mode_name:"+mode_name);
                        clock_segment = renderWeekMode(start_time,end_time,mode_name);
                        $("#mode_group > ul > li :eq("+i+") > .mode_group_line > ."+system_code).append(clock_segment);
                    }
                }

                if (system_code == "CoolingSource" || system_code == "HeatSource" || system_code == "FAVU" || system_code == "MAHU" || system_code == "BUAHU" || system_code == "AVU" ) {
                    system_code = "HVAC";
                }

                $(".mode_for_oneday :eq("+i+") ."+system_code).show()
                $(".mode_for_oneday :eq("+i+") ."+system_code).addClass(system_code+"_"+dayType);
                $(".mode_for_oneday :eq("+i+") ."+system_code).next().html(default_mode_name);
                if (dayType=="futureDay")
                  $(".mode_for_oneday :eq("+i+") ."+system_code).next().css("color","#FFAB32");
                if (dayType=="pastDay")
                  $(".mode_for_oneday :eq("+i+") ."+system_code).next().css("color","#69B0E6");
                if (dayType=="pastMonth")
                  $(".mode_for_oneday :eq("+i+") ."+system_code).next().css("color","#26C3BC");  
                  
                $(".mode_for_oneday :eq("+i+") ."+system_code).next().attr('onclick','javascript:showOneDayDetailMode('+'\''+modelist[i].ucCalendar.calendarYear+'\',\''+modelist[i].ucCalendar.calendarMonth+'\',\''+modelist[i].ucCalendar.calendarDay+'\')');
            }
        }
        }
    );
}

function renderWeekMode(start_time,end_time,mode_name)
{
    if ((start_time=="")&&(end_time==""))
      return "";
    if (start_time=="")
      start_time = end_time;
    if (end_time =="")
      end_time = start_time; 
    var start_hour = start_time.substring(0,2);
    var start_minute = 	start_time.substring(2,4);
    var end_hour = end_time.substring(0,2);
    var end_minute = 	end_time.substring(2,4);
    var clock_segment = "";
    var style_w ="";
    var w = 0;
    var start_left = getWeekViewLeft(start_hour,start_minute)-3;
    var clock_segment = '<div class="clock_segment" onmouseout="hideClockDetailMode()" onmouseover="javascript:showClockDetailModeInWeekView(event,\''+mode_name+'\')" style="margin-left:'+start_left+'px">';

    clock_segment = clock_segment + "\n" +'<div class="big_hour_dot"></div>';

    var hour_count = parseInt(end_hour)-parseInt(start_hour);
    for(var i=0;i<hour_count;i++)
    {
        if((i==0)&&(parseInt(start_minute)>0))
        {
            w = Math.round((60 - parseInt(start_minute))*5/6) - 6 ;
            style_w = 'width:'+w+'px';
            clock_segment = clock_segment + "\n" + '<div class="open_line" style="'+style_w+'"></div>';
        }
        else
        {
            clock_segment = clock_segment + "\n" + '<div class="open_line"></div>';
        }
        clock_segment = clock_segment + "\n" + '<div class="big_hour_dot"></div>';
    }
    if (parseInt(end_minute) > 0)
    {
        w = (parseInt(end_minute) * 5/6)  -6 ;
        style_w = 'width:'+w+'px';
        clock_segment = clock_segment + "\n" + '<div class="open_line" style="'+style_w+'"></div>';
        clock_segment = clock_segment + "\n" + '<div class="big_hour_dot"></div>';
    }

    clock_segment = clock_segment + "\n" +"</div>"
    return clock_segment;
}

function getWeekViewLeft(hour,minute)
{
    var m = parseInt(hour)*60 + parseInt(minute);
    var base_left = Math.round(m * (1300 - 50 - 50)/(24 * 60));
    return base_left;
}

function showClockDetailModeInWeekView(e,mode_name)
{
    //alert("e.clientX="+e.clientX);
    var h = Math.floor((e.clientX -255 -350 -47 -3 )/50);
    if (h<0) h=0;
    if (h<10) h = "0"+h;
    
    var m = (e.clientX - 255 -350 -47 -3 )%50;
    if (m<0) m=0;
    if (m<10) m = "0"+m;
    
    $("#mode_toast").html(h+":"+m+" "+mode_name);
    $("#mode_toast").css('left',(e.clientX-70)+"px");
    $("#mode_toast").css('top',(e.clientY-30)+"px");
    $("#mode_toast").show();
}
function getWeekActivity()
{
    var f_month = first_date.getMonth()+1;
    if (f_month < 10)
        f_month = "0"+f_month;
    var f_day = first_date.getDate();
    if (f_day < 10)
        f_day = "0"+f_day;
    var l_month = last_date.getMonth()+1;
    if (l_month < 10)
        l_month = "0"+l_month;
    var l_day = last_date.getDate();
    if (l_day < 10)
        l_day = "0"+l_day;
    var url = baseurl+'/calendar/getCalendarEvent';
    var d = new Date();
    var r = Math.random()+""+d.getMilliseconds();
    var params ={startDate:first_date.getFullYear()+""+f_month+""+f_day,endDate:last_date.getFullYear()+""+l_month+""+l_day,r:r};
    $.ajax({url:url,type:'GET',dataType:'json',async:true,data:params,success:function(data){

            var a_name = "";
            var separate = " , ";
            var activity = data;
            for (var i=0;i<activity.length;i++)
            {
                if (i == activity.length -1)
                    separate = "";
                a_name = a_name + activity[i].title + separate;
            }
            $('#active_whole_week').html(a_name);
        }
        }
    );
}

function getWeekWeather()
{
    var f_month = first_date.getMonth()+1;
    if (f_month < 10)
        f_month = "0"+f_month;
    var f_day = first_date.getDate();
    if (f_day < 10)
        f_day = "0"+f_day;
    var l_month = last_date.getMonth()+1;
    if (l_month < 10)
        l_month = "0"+l_month;
    var l_day = last_date.getDate();
    if (l_day < 10)
        l_day = "0"+l_day;
    var url = baseurl+'/calendar/getCalendarWeather';
    var d = new Date();
    var r = Math.random()+""+d.getMilliseconds();
    var params ={startDate:first_date.getFullYear()+""+f_month+""+f_day,endDate:last_date.getFullYear()+""+l_month+""+l_day,r:r};
    $.ajax({url:url,type:'GET',dataType:'json',async:true,data:params,success:function(data){

            var desc = "";
            var icon = "";
            var cssName = "";
            var temperature = "";
            var sunrise ="";
            var sunset ="";
            var day = "";
            var dayWeek = "";
            var index_d = 0;
            var holiday = "";
            var dayType = "pastDay";
            var weather = data;
            for (var i=0;i<weather.length;i++)
            {
                day = weather[i].id;
                holiday = "";

                if (day >= current_yyyy+current_mm+current_dd)
                    dayType = "futureDay";
                else if (day < last_date.getFullYear()+""+l_month+"01")  //确定是上个月的  
                    dayType = "pastMonth";
                else
                    dayType = "pastDay";

                dayWeek = weather[i].dayWeek;
                index_d = parseInt(dayWeek)-1;

                for (var j=0;j<weather[i].ucHolidays.length;j++)
                {
                	 if ((weather[i].ucHolidays[j].holidayName !=null)&&(weather[i].ucHolidays[j].holidayName !=""))
                	    holiday = holiday + " " + weather[i].ucHolidays[j].holidayName;
                }
                desc = weather[i].ucWeathers[0].weatherDescription;
                icon = weather[i].ucWeathers[0].weatherIcon;
                cssName = getCssFromWeatherIcon(icon);
                temperature  = weather[i].ucWeathers[0].minTemperature+"&#8451;" +"~" +weather[i].ucWeathers[0].maxTemperature+"&#8451;";
                sunrise = weather[i].ucWeathers[0].sunrise;
                sunset = weather[i].ucWeathers[0].sunset;

                sunrise = formatUcWeatherTime(sunrise);
                sunset = formatUcWeatherTime(sunset);

                if (holiday==""||holiday==null)
                    $(".holiday_desc :eq("+index_d+")").hide();
                else
                {
                    $(".holiday_desc :eq("+index_d+")").show();
                    $(".holiday_desc :eq("+index_d+")").html(holiday);
                }
                if (day == current_yyyy+current_mm+current_dd)
                {
                    $(".holiday_desc :eq("+index_d+")").show();
                    $(".holiday_desc :eq("+index_d+")").css("background-color","#FF9900");
                    $(".holiday_desc :eq("+index_d+")").html("今日"+$(".holiday_desc :eq("+index_d+")").html());
                }
                $(".weather_label_normal :eq("+index_d+")").addClass(cssName);

                $(".temperature :eq("+index_d+")").html(temperature);
                $(".weather_desc :eq("+index_d+")").html(desc);
                $(".sun_up_time :eq("+index_d+")").html(sunrise+" AM");
                $(".sun_down_time :eq("+index_d+")").html(sunset+" PM");

                var sunrise_h = sunrise.substring(0,2);
                var sunrise_m = sunrise.substring(3,5);

                var sunset_h = sunset.substring(0,2);
                var sunset_m = sunset.substring(3,5);
                var w = getWeekSunUpClockLineWidth(sunrise_h,sunrise_m);
                $(".night_left :eq("+index_d+")").css("width",w+"px");
                var w = getWeekSunDownClockLineWidth(sunset_h,sunset_m);
                $(".night_right :eq("+index_d+")").css("width",w+"px");

                switch (dayType)
                {
                    case "pastDay":
                        $(".weather_for_oneday :eq("+index_d+")").css("border-left","2px solid #3366FF");
                        $(".weather_for_oneday :eq("+index_d+") >.weather_remark >.weather_am >.sun_up_icon").addClass("sun_up_icon_pastDay");
                        $(".weather_for_oneday :eq("+index_d+") >.weather_remark >.weather_pm >.sun_down_icon").addClass("sun_down_icon_pastDay");
                        break;
                    case "pastMonth":
                        $(".weather_for_oneday :eq("+index_d+")").css("border-left","2px solid #99CCFF");
                        $(".weather_for_oneday :eq("+index_d+") >.weather_remark >.weather_am >.sun_up_icon").addClass("sun_up_icon_pastMonth");
                        $(".weather_for_oneday :eq("+index_d+") >.weather_remark >.weather_pm >.sun_down_icon").addClass("sun_down_icon_pastMonth");
                        break;
                    case "futureDay":
                        $(".weather_for_oneday :eq("+index_d+")").css("border-left","2px solid #FF9900");
                        $(".weather_for_oneday :eq("+index_d+") >.weather_remark >.weather_am >.sun_up_icon").addClass("sun_up_icon_futureDay");
                        $(".weather_for_oneday :eq("+index_d+") >.weather_remark >.weather_pm >.sun_down_icon").addClass("sun_down_icon_futureDay");
                        break;
                }
            }
        }
        }
    );
}

function clearWeekView()
{
    showLoading();
    clearWeekWeather();
    clearWeekMode();
    clearWeekActivity();
}

function clearWeekWeather()
{
    $(".night_left").css("width","0px");
    $(".night_right").css("width","0px");
    $(".holiday_desc").html('');
    $(".holiday_desc").hide();
    $(".temperature").html('');
    $(".weather_desc").html('');
    $(".sun_up_time").html('');
    $(".sun_down_time").html('');
    $(".sun_up_icon").attr("class",'sun_up_icon');
    $(".sun_down_icon").attr('class','sun_down_icon');
    $(".weather_for_oneday").css("border-left","2px solid #ffffff");
    $(".weather_label_normal").attr("class","weather_label_normal");
}

function clearWeekMode()
{
    $('.one_mode .mode_name').html('');
    $('.one_mode .mode_icon').hide();
    $('.one_mode .HVAC').attr('class','mode_icon HVAC');
    $('.one_mode .LSN').attr('class','mode_icon LSN');
    $('.one_mode .LSPUB').attr('class','mode_icon LSPUB');
    $(".mode_group_line").html('');
    
    switch (systemId)
    {
        case "HVAC":
            $(".mode_group_line").append('<div class="CoolingSource" style="margin-left:50px"></div>');
            $(".mode_group_line").append('<div class="HeatSource" style="margin-left:50px"></div>');
            $(".mode_group_line").append('<div class="FAVU" style="margin-left:50px"></div>');
            $(".mode_group_line").append('<div class="MAHU" style="margin-left:50px"></div>');
            $(".mode_group_line").append('<div class="BUAHU" style="margin-left:50px"></div>');
            $(".mode_group_line").append('<div class="AVU" style="margin-left:50px"></div>');
            break;
        case "LSPUB":
            $(".mode_group_line").append('<div class="LSPUB" style="margin-left:50px"></div>');
            break;
        case "LSN":
            $(".mode_group_line").append('<div class="LSN" style="margin-left:50px"></div>');
            break;
    }
}

function clearWeekActivity()
{
    $("#active_whole_week").html('');
}

function highLightCurrentDayInWeekView()
{
    $('#mode_group >ul >li').removeClass('current_day');

    var c_d = new Date();
    c_d.setFullYear(parseInt(current_yyyy),parseInt(current_mm)-1,parseInt(current_dd));
    $("#week_range").html((first_date.getMonth()+1)+"."+first_date.getDate()+"-"+(last_date.getMonth()+1)+"."+last_date.getDate());
    
    var s_f_d = first_date;
    var s_l_d = last_date;
    //var s_f_d = new Date(parseInt(selected_yyyy),parseInt(selected_mm)-1,parseInt(selected_first_day));
    //var s_l_d = new Date(parseInt(selected_yyyy),parseInt(selected_mm)-1,parseInt(selected_last_day));
    //console.log("c_d:"+current_yyyy+"-"+current_mm+"-"+current_dd);
    var c_d_str = c_d.getFullYear()+""+(c_d.getMonth()<10?"0"+c_d.getMonth():c_d.getMonth())+""+(c_d.getDate()<10?"0"+c_d.getDate():""+c_d.getDate());
    var s_f_d_str = s_f_d.getFullYear()+""+(s_f_d.getMonth()<10?"0"+s_f_d.getMonth():s_f_d.getMonth())+""+(s_f_d.getDate()<10?"0"+s_f_d.getDate():""+s_f_d.getDate());
    var s_l_d_str = s_l_d.getFullYear()+""+(s_l_d.getMonth()<10?"0"+s_l_d.getMonth():s_l_d.getMonth())+""+(s_l_d.getDate()<10?"0"+s_l_d.getDate():""+s_l_d.getDate());
    console.log("c_d_str:"+c_d_str);
    console.log("s_f_d_str:"+s_f_d_str);
    console.log("s_l_d_str:"+s_l_d_str);
    if ((c_d_str >= s_f_d_str)&&(c_d_str <= s_l_d_str))
    {
        var inde_i = 0;
        var index_d = c_d.getDay();
        console.log("index_d:"+index_d);
        switch(index_d)
        {
            case 0 :
                index_i = 6;
                break;
            case 1 :
                index_i = 0;
                break;
            case 2 :
                index_i = 1;
                break;
            case 3 :
                index_i = 2;
                break;
            case 4 :
                index_i = 3;
                break;
            case 5 :
                index_i = 4;
                break;
            case 6 :
                index_i = 5;
                break;
        }
        $('#mode_group >ul >li :eq('+index_i+')').addClass('current_day');
    }
}


/*
 以下是月视图相关的javascript function
 */

function gotoCurrentMonth()
{
    $('.year_list').hide();
    $('.month_list').hide();
    selected_yyyy = current_yyyy;
    selected_mm = current_mm;
    $('#current_year').html(selected_yyyy);
    $('#current_month').html(selected_mm);
    $("#month_slider > span").html(selected_yyyy+"年"+selected_mm+"月");
    resetMaxDate(selected_yyyy,selected_mm);
    resetMonthRange(selected_yyyy,selected_mm);
    clearMonthView();
    setDefaultDaysBgInMonthView();
    highLightCurrentDayInMonthView();
    setLastMonthDayBgInMonthView();
    setNextMonthDayBgInMonthView();
    getMonthSchedule();
}

function lastMonth()
{
    selected_mm = parseInt(selected_mm) - 1;
    if (selected_mm <1)
    {
        selected_mm = 12;
        selected_yyyy = parseInt(selected_yyyy) -1;
    }
    $('#current_year').html(selected_yyyy);
    $('#current_month').html(selected_mm);
    resetMaxDate(selected_yyyy,selected_mm);
    
    resetMonthRange(selected_yyyy,selected_mm);
    $("#month_slider > span").html(selected_yyyy+"年"+parseInt(selected_mm)+"月");
    clearMonthView();
    setDefaultDaysBgInMonthView();
    highLightCurrentDayInMonthView();
    setLastMonthDayBgInMonthView();
    setNextMonthDayBgInMonthView();
    getMonthSchedule();
}

function nextMonth()
{
    selected_mm = parseInt(selected_mm) + 1;
    if (selected_mm >12)
    {
        selected_mm = 1;
        selected_yyyy = parseInt(selected_yyyy) +1;
    }
    $('#current_year').html(selected_yyyy);
    $('#current_month').html(selected_mm);

    resetMaxDate(selected_yyyy,selected_mm);

    resetMonthRange(selected_yyyy,selected_mm);
    $("#month_slider > span").html(selected_yyyy+"年"+parseInt(selected_mm)+"月");
    clearMonthView();
    setDefaultDaysBgInMonthView();
    highLightCurrentDayInMonthView();
    setLastMonthDayBgInMonthView();
    setNextMonthDayBgInMonthView();
    getMonthSchedule()
}

function resetMonthRange(yyyy,mm)
{
    var firstDate = new Date();
    var lastDate = new Date();
    var month = "01";
    var day ="01";
    var d = new Date(parseInt(yyyy),parseInt(mm)-1,1);
    var i = d.getDay();
    firstDate.setFullYear(d.getFullYear());
    firstDate.setMonth(d.getMonth());
    firstDate.setDate(d.getDate()-i);

    month = firstDate.getMonth()+1;
    if (month < 10)
        month = "0"+month;
    day = firstDate.getDate();
    if (day < 10)
        day = "0"+day;
    first_day = firstDate.getFullYear()+""+month+""+day;

    lastDate.setFullYear(firstDate.getFullYear());
    lastDate.setMonth(firstDate.getMonth());
    lastDate.setDate(firstDate.getDate()+41);

    month = lastDate.getMonth()+1;
    if (month < 10)
        month = "0"+month;
    day = lastDate.getDate();
    if (day < 10)
        day = "0"+day;
    last_day = lastDate.getFullYear()+""+month+""+day;
}

function getMonthSchedule()
{
    var url = baseurl+'/calendar/getScheduler';
    var d = new Date();
    var r = Math.random()+""+d.getMilliseconds();
    var params ={systemId:systemId,startDate:first_day,endDate:last_day,r:r};
    $.ajax({url:url,type:'GET',dataType:'json',async:true,data:params,success:function(data){
        hideLoading();
        var scheduler = data;
        var weather_desc = "";
        var min_temperature = "";
        var max_temperature = "";
        var temperature = "";
        var sunrise ="";
        var sunset ="";
        var day = "";
        var holiday = "";
        var activity_name ="";
        var activity_list = null;
        var mode_list = null;
        var mode_name = "";

        for (var i=0;i<scheduler.length;i++)
        {
            activity_name="";
            holiday = "";
            day = scheduler[i].ucCalendar.calendarDay;

            for (var j=0;j<scheduler[i].ucCalendar.ucHolidays.length;j++)
            {
            	 if ((scheduler[i].ucCalendar.ucHolidays[j].holidayName !=null)&&(scheduler[i].ucCalendar.ucHolidays[j].holidayName !=""))
             	    holiday = holiday + " " + scheduler[i].ucCalendar.ucHolidays[j].holidayName;
            }
            
            weather_desc = scheduler[i].ucCalendar.ucWeathers[0].weatherDescription;
            min_temperature  = scheduler[i].ucCalendar.ucWeathers[0].minTemperature;
            max_temperature = scheduler[i].ucCalendar.ucWeathers[0].maxTemperature;
            if (min_temperature==null)
              min_temperature = "--";
            if (max_temperature==null)
              max_temperature = "--";  
            sunrise = scheduler[i].ucCalendar.ucWeathers[0].sunrise;//
            sunset = scheduler[i].ucCalendar.ucWeathers[0].sunset;
            var className=getCssFromWeatherIcon(scheduler[i].ucCalendar.ucWeathers[0].weatherIcon);
            sunrise = formatUcWeatherTime(sunrise);
            sunset = formatUcWeatherTime(sunset);

            $("#day"+(i+1)+" .date").html(day);

            if (holiday==""||holiday==null)
                $("#day"+(i+1)+" .holiday").hide();
            else
            {
                $("#day"+(i+1)+" .holiday").show();
                $("#day"+(i+1)+" .holiday").html(holiday);
            }
            
            $("#day"+(i+1)+" .temperature").html(min_temperature+"&#8451;"+"~"+max_temperature+"&#8451;");
            $("#day"+(i+1)+" .weather_desc").html(weather_desc);
            $("#day"+(i+1)+" .weather_icon").addClass(className);
            $("#day"+(i+1)+" .sun_up_time").html(sunrise+" AM");
            $("#day"+(i+1)+" .sun_down_time").html(sunset+" PM");

            activity_list = scheduler[i].eventList;
            var se = ";"
            for (var j=0;j<activity_list.length;j++)
            {
                if (j==activity_list.length-1)
                   se = "";
                activity_name = activity_name + activity_list[j].title + se;
            }
            if (activity_name!="")
            {
              $("#day"+(i+1)+" .activity_name").html(activity_name);
              $("#day"+(i+1)+" .activity").attr('onclick','javascript:showOneDayActivity('+scheduler[i].date+')');
            }
            else
            {
            	$("#day"+(i+1)+" .activity").attr('onclick',"javascript:showCreateActivityDialog()");
            }	
            mode_name = "";
            mode_list = scheduler[i].ucPatternRecords;
            if (mode_list.length > 0)
              mode_name = mode_list[0].patternName; //这是默认值
            for(var k=0;k<mode_list.length;k++)
            {
                //如果是暖通，则取组合式空调的
                if (mode_list[k].systemId=="MAHU")
                  mode_name = mode_list[k].patternName;
            }
            $("#day"+(i+1)+" .mode_name").html(mode_name);
            $("#day"+(i+1)+" .mode").attr('onclick','javascript:showOneDayDetailMode('+'\''+scheduler[i].ucCalendar.calendarYear+'\',\''+scheduler[i].ucCalendar.calendarMonth+'\',\''+scheduler[i].ucCalendar.calendarDay+'\')');

            var dayType = "currentMonth";
            var c_day = scheduler[i].date;
            var d = new Date(current_yyyy,current_mm,0);
            var current_max_date = d.getDate();
            if ((c_day >= current_yyyy+current_mm+"01")&&(c_day <= current_yyyy+current_mm+current_max_date))
                dayType = "currentMonth";
            else if (c_day < current_yyyy+current_mm+"01")  //确定是上个月的  
                dayType = "pastMonth";
            else
                dayType = "futureMonth";  //未来月份的

            switch (dayType)
            {
                case "futureMonth":
                    $("#day"+(i+1)+" .mode").css("border-top-color","#cccccc");
                    $("#day"+(i+1)+" .mode").css("border-right-color","#cccccc");
                    $("#day"+(i+1)+" .activity").css("border-top-color","#cccccc");
                    break;
                case "pastMonth":
                    $("#day"+(i+1)+" .mode").css("border-top-color","#cccccc");
                    $("#day"+(i+1)+" .mode").css("border-right-color","#cccccc");
                    $("#day"+(i+1)+" .activity").css("border-top-color","#cccccc");
                    break;
            }
        }
    }
    });
}

function clearMonthView()
{
    showLoading();
    $("#month .date").html('');
    $("#month .holiday").hide();
    $("#month .holiday").html('');
    $("#month .temperature").html('');
    $("#month .weather_desc").html('');
    $("#month .sun_up_time").html('');
    $("#month .sun_down_time").html('');
    $("#month .activity_name").html('');
    $("#month .mode_name").html('');
    $("#month .mode").removeAttr('style');
    $("#month .activity").removeAttr('style');
    $("#month .weather_icon").attr("class","weather_icon");
}

function setDefaultDaysBgInMonthView()
{
    $("#month td").attr("class",'current_month_day');
}

function highLightCurrentDayInMonthView()
{
    $('.current_month_current_day').removeClass('current_month_current_day');
    var c_d = new Date();
    c_d.setFullYear(parseInt(current_yyyy),parseInt(current_mm)-1,parseInt(current_dd));
    
    var s_f_d = new Date();
    s_f_d.setFullYear(parseInt(first_day.substring(0,4)),parseInt(first_day.substring(4,6))-1,parseInt(first_day.substring(6,8)));
    s_f_d.setHours(c_d.getHours());
    s_f_d.setMinutes(c_d.getMinutes());
    s_f_d.setSeconds(c_d.getSeconds());
    s_f_d.setMilliseconds(c_d.getMilliseconds());
    
    var s_l_d = new Date();
    s_l_d.setFullYear(parseInt(last_day.substring(0,4)),parseInt(last_day.substring(4,6))-1,parseInt(last_day.substring(6,8)));
    s_l_d.setHours(c_d.getHours());
    s_l_d.setMinutes(c_d.getMinutes());
    s_l_d.setSeconds(c_d.getSeconds());
    s_l_d.setMilliseconds(c_d.getMilliseconds());
    
    
    var c_d_str = c_d.getFullYear()+""+(c_d.getMonth()<10?"0"+c_d.getMonth():c_d.getMonth())+""+(c_d.getDate()<10?"0"+c_d.getDate():""+c_d.getDate());
    var s_f_d_str = s_f_d.getFullYear()+""+(s_f_d.getMonth()<10?"0"+s_f_d.getMonth():s_f_d.getMonth())+""+(s_f_d.getDate()<10?"0"+s_f_d.getDate():""+s_f_d.getDate());
    var s_l_d_str = s_l_d.getFullYear()+""+(s_l_d.getMonth()<10?"0"+s_l_d.getMonth():s_l_d.getMonth())+""+(s_l_d.getDate()<10?"0"+s_l_d.getDate():""+s_l_d.getDate());
    console.log("c_d_str:"+c_d_str);
    console.log("s_f_d_str:"+s_f_d_str);
    console.log("s_l_d_str:"+s_l_d_str);
    if ((c_d_str >= s_f_d_str)&&(c_d_str <= s_l_d_str))
    {
       var offset_ms = c_d.getTime() - s_f_d.getTime();
       var d = Math.floor(offset_ms/(24*3600*1000))+1;
       $("#day"+d+"").addClass('current_month_current_day');
       $("#day"+d+"").removeClass('current_month_day');
    }
}

function setLastMonthDayBgInMonthView()
{
    $('.last_month_day').removeClass('last_month_day');
    var c_d = new Date(parseInt(selected_yyyy),parseInt(selected_mm)-1,1);
    var s_f_d = new Date(parseInt(first_day.substring(0,4)),parseInt(first_day.substring(4,6))-1,parseInt(first_day.substring(6,8)));

    if (c_d >= s_f_d)
    {
        var offset_ms = c_d.getTime() - s_f_d.getTime();
        var d = Math.floor(offset_ms/(24*3600*1000))+1;

        for (var i=1;i<d;i++)
        {
            $("#day"+i+"").addClass('last_month_day');
            $("#day"+i+"").removeClass('current_month_day');
        }
    }
}

function setNextMonthDayBgInMonthView()
{
    $('.next_month_day').removeClass('next_month_day');
    var c_d = new Date(parseInt(selected_yyyy),parseInt(selected_mm)-1,max_date);
    var s_f_d = new Date(parseInt(first_day.substring(0,4)),parseInt(first_day.substring(4,6))-1,parseInt(first_day.substring(6,8)));

    if (c_d >= s_f_d)
    {
        var offset_ms = c_d.getTime() - s_f_d.getTime();
        var d = Math.floor(offset_ms/(24*3600*1000))+1;

        for (var i=d+1;i<43;i++)
        {
            $("#day"+i+"").addClass('next_month_day');
            $("#day"+i+"").removeClass('current_month_day');
        }
    }
}

/*
 以下是日程表视图相关的javascript function
 */

function gotoCurrentWeekInListView()
{
    $('.year_list').hide();
    $('.month_list').hide();
    $('#current_year').html(current_yyyy);
    $('#current_month').html(current_mm);
    selected_yyyy = current_yyyy;
    selected_mm = current_mm;
    resetDateRangeCrossMonth(current_yyyy,current_mm,current_dd);
    $("#selected_week").html((first_date.getMonth()+1)+"/"+first_date.getDate()+"-"+(last_date.getMonth()+1)+"/"+last_date.getDate());
    $("#month_slider > span").html(first_date.getFullYear()+"年"+(first_date.getMonth()+1)+"月"+first_date.getDate()+"日-"+last_date.getFullYear()+"年"+(last_date.getMonth()+1)+"月"+last_date.getDate()+"日");
    clearListView();
    getListSchedule();
}

function gotoLastWeekInListView()
{
    first_date.setDate(first_date.getDate()-7);
    last_date.setDate(last_date.getDate()-7);
    $("#selected_week").html((first_date.getMonth()+1)+"/"+first_date.getDate()+"-"+(last_date.getMonth()+1)+"/"+last_date.getDate());
    $("#month_slider > span").html(first_date.getFullYear()+"年"+(first_date.getMonth()+1)+"月"+first_date.getDate()+"日-"+last_date.getFullYear()+"年"+(last_date.getMonth()+1)+"月"+last_date.getDate()+"日");
    clearListView();
    getListSchedule();
}

function gotoNextWeekInListView()
{
    first_date.setDate(first_date.getDate()+7);
    last_date.setDate(last_date.getDate()+7);
    $("#selected_week").html((first_date.getMonth()+1)+"/"+first_date.getDate()+"-"+(last_date.getMonth()+1)+"/"+last_date.getDate());
    $("#month_slider > span").html(first_date.getFullYear()+"年"+(first_date.getMonth()+1)+"月"+first_date.getDate()+"日-"+last_date.getFullYear()+"年"+(last_date.getMonth()+1)+"月"+last_date.getDate()+"日");
    clearListView();
    getListSchedule();
}

function getListSchedule()
{
    var f_month = first_date.getMonth()+1;
    if (f_month < 10)
        f_month = "0"+f_month;
    var f_day = first_date.getDate();
    if (f_day < 10)
        f_day = "0"+f_day;
    var l_month = last_date.getMonth()+1;
    if (l_month < 10)
        l_month = "0"+l_month;
    var l_day = last_date.getDate();
    if (l_day < 10)
        l_day = "0"+l_day;
    var url = baseurl+'/calendar/getScheduler';
    var d = new Date();
    var r = Math.random()+""+d.getMilliseconds();
    var params ={startDate:first_date.getFullYear()+""+f_month+""+f_day,endDate:last_date.getFullYear()+""+l_month+""+l_day,r:r};
    $.ajax({url:url,type:'GET',dataType:'json',async:true,data:params,success:function(data){

        var scheduler = data;
        var weather_desc = "";
        var temperature = "";
        var weather_icon = "";
        var weather_css = ""; 
        var sunrise ="";
        var sunset ="";
        var day = "";
        var holiday = "";
        var date = '';
        var startDate = new Date();
        var endDate = new Date();
        var activity_time ="";
        var activity_name ="";
        var activity_list = null;

        for (var i=0;i<scheduler.length;i++)
        {
            holiday = "";
            date = scheduler[i].date;
            day = scheduler[i].ucCalendar.calendarDay;

            for (var j=0;j<scheduler[i].ucCalendar.ucHolidays.length;j++)
            {
            	 if ((scheduler[i].ucCalendar.ucHolidays[j].holidayName !=null)&&(scheduler[i].ucCalendar.ucHolidays[j].holidayName !=""))
             	    holiday = holiday + " " + scheduler[i].ucCalendar.ucHolidays[j].holidayName;
            }
            if ((current_yyyy+""+current_mm+""+current_dd) == scheduler[i].date)
            {
              if (holiday =="")
                holiday = "今日";
              else  
                holiday = "今日 "+holiday;
            }
            weather_icon = scheduler[i].ucCalendar.ucWeathers[0].weatherIcon;
            weather_css = getCssFromWeatherIcon(weather_icon);
            weather_desc = scheduler[i].ucCalendar.ucWeathers[0].weatherDescription;
            temperature  = scheduler[i].ucCalendar.ucWeathers[0].minTemperature +"&#8451;"+"~"+scheduler[i].ucCalendar.ucWeathers[0].maxTemperature+"&#8451;";
            sunrise = scheduler[i].ucCalendar.ucWeathers[0].sunrise;
            sunset = scheduler[i].ucCalendar.ucWeathers[0].sunset;
            sunrise = formatUcWeatherTime(sunrise);
            sunset = formatUcWeatherTime(sunset);
                        
            $(".day :eq("+i+")").html(day);
            $(".day :eq("+i+")").removeAttr('style');
            if (holiday==""||holiday==null)
                $(".holiday_desc :eq("+i+")").hide();
            else
            {
                $(".weather_detail_list >.holiday :eq("+i+")").css("height","30px");
                $(".holiday_desc :eq("+i+")").show();
                $(".holiday_desc :eq("+i+")").html(holiday);
                if ((current_yyyy+""+current_mm+""+current_dd) == scheduler[i].date)
                {
                	$(".holiday_desc :eq("+i+")").css("background-color","#FF9900");
                }
            }
            $(".weather_label_normal :eq("+i+")").addClass(weather_css);
            $(".big_temperature :eq("+i+")").html(temperature);
            
            $(".weather_detail_list > .weather_desc >.desc :eq("+i+")").html(weather_desc);
            $(".sunrise :eq("+i+")").html(sunrise+" AM");
            $(".sunset :eq("+i+")").html(sunset+" PM");

            if ((current_yyyy+""+current_mm+""+current_dd) == scheduler[i].date)
            {
                $(".weather_caption :eq("+i+")").css('border-left-color','#FF9900');
                $(".weather_caption :eq("+i+")").parent().parent().prevAll().find(".weather_for_oneday").addClass("week_today");
                $(".day :eq("+i+")").css('color','#FF9900');
            }
            else if ((current_yyyy+""+current_mm+""+current_dd) < scheduler[i].date)
            {
                $(".weather_caption :eq("+i+")").css('border-left-color','#66cccc');
                $(".day :eq("+i+")").css('color','#66cccc');
            }

            activity_list = scheduler[i].eventList;
            for (var j=0;j<activity_list.length;j++)
            {
                if (activity_list[j].allDay == true)
                {
                    activity_time = "全天";
                    $(".activity_for_oneday :eq("+i+") > ul > li > .activity_time :eq("+j+")").css('color','#FF9966');
                    $(".activity_for_oneday :eq("+i+") > ul > li > .activity_name :eq("+j+")").css('color','#FF9966');
                }
                else
                {
                    startDate.setTime(activity_list[j].startDate);
                    endDate.setTime(activity_list[j].endDate);
                    var start_mm = startDate.getMonth()+1;
                    if (start_mm < 10)
                        start_mm = "0"+start_mm;
                    var start_dd = startDate.getDay();
                    if (start_dd < 10)
                        start_dd = "0" +start_dd;
                    var end_mm = endDate.getMonth()+1;
                    if (end_mm < 10)
                        end_mm = "0"+end_mm;
                    var end_dd = endDate.getDay();
                    if (end_dd < 10)
                        end_dd = "0" +end_dd;
                        
                    //alert("startdate="+startDate.getFullYear()+""+start_mm+""+start_dd+" enddate="+endDate.getFullYear()+""+end_mm+""+end_dd);
                    if ((parseInt(date) > parseInt(startDate.getFullYear()+""+start_mm+""+start_dd)) && (parseInt(date) < parseInt(endDate.getFullYear()+""+end_mm+""+end_dd)))
                    {
                        activity_time = '00:00-23:59';
                    }
                    else if ((parseInt(date) == parseInt(startDate.getFullYear()+""+start_mm+""+start_dd)) && (parseInt(date) == parseInt(endDate.getFullYear()+""+end_mm+""+end_dd)))
                    {
                        activity_time =startDate.getHours()+":"+startDate.getMinutes()+'-'+endDate.getHours()+":"+endDate.getMinutes();
                    }
                    else if ( parseInt(date) == parseInt(startDate.getFullYear()+""+start_mm+""+start_dd))
                    {
                        activity_time =startDate.getHours()+":"+startDate.getMinutes()+"-23:59";
                    }
                    else if ( parseInt(date) == parseInt(endDate.getFullYear()+""+end_mm+""+end_dd))
                    {
                        activity_time ='00:00-'+endDate.getHours()+":"+endDate.getMinutes();
                    }
                    $(".activity_for_oneday :eq("+i+") > ul > li > .activity_time :eq("+j+")").css('color','#78CDD0');
                    $(".activity_for_oneday :eq("+i+") > ul > li > .activity_name :eq("+j+")").css('color','#78CDD0');
                }
                activity_name = activity_list[j].title;

                $(".activity_for_oneday :eq("+i+") > ul > li > .activity_time :eq("+j+")").html(activity_time);
                $(".activity_for_oneday :eq("+i+") > ul > li > .activity_name :eq("+j+")").html(activity_name);
                $(".activity_for_oneday :eq("+i+") > ul > li :eq("+j+")").addClass("hasActivity");
                if (activity_list[j].organizer == null)
                  activity_list[j].organizer = "";
                if (activity_list[j].eventAddress == null)
                  activity_list[j].eventAddress = "";  
                $(".activity_for_oneday :eq("+i+") > ul > li :eq("+j+")").data('activity',{id:activity_list[j].id,name:activity_name,description:activity_list[j].description,organizer:activity_list[j].organizer,eventAddress:activity_list[j].eventAddress,allDay:activity_list[j].allDay,startDate:activity_list[j].startDate,endDate:activity_list[j].endDate});
                $(".activity_for_oneday :eq("+i+") > ul > li :eq("+j+")").attr('onclick','showOneActivity(this)');
            }
            if ((current_yyyy+""+current_mm+""+current_dd) == scheduler[i].date)
            {
                $(".activity_for_oneday :eq("+i+")").css('border-left-color','#FF9900');
                $(".activity_for_oneday :eq("+i+")").parent().prevAll().find(".activity_for_oneday").addClass("week_today_ul");
                $(".activity_for_oneday :eq("+i+") > ul > li.hasActivity").css('background-color','#FEC68E');
                $(".activity_for_oneday :eq("+i+") > ul > li.hasActivity").css('border-right','1px solid #ff9900');
                $(".activity_for_oneday :eq("+i+") > ul > li > .activity_time").css('color','#ffffff');
                $(".activity_for_oneday :eq("+i+") > ul > li > .activity_name").css('color','#ffffff');
            }
            else if ((current_yyyy+""+current_mm+""+current_dd) < scheduler[i].date)
            {

            }
        }
    }
    });
}

function clearListWeather()
{
    $("#list .day").attr('style','color:#ffffff');
    $("#list .small_temperature").html('');
    $("#list .weather_detail_list >.holiday").removeAttr("style");
    $("#list .holiday_desc").html('');
    $("#list .holiday_desc").hide();
    $("#list .weather_desc > .desc").html('');
    $("#list .big_temperature").html('');
    $("#list .sunrise").html('');
    $("#list .sunset").html('');
    $("#list .weather_label_normal").attr('class','weather_label_normal');
    $("#list .weather_caption").removeAttr('style');
    $("#list .weather_for_oneday").removeClass("week_today");
    $("#list .weather_for_oneday").removeAttr('style');
    $("#list .day").removeAttr('style');
}

function clearListActivity()
{
    $(".activity_time").html('');
    $(".activity_name").html('');
    $(".activity_time").css('border-bottom-color:','#ffffff');
    $(".activity_for_oneday > ul > li").removeClass('hasActivity');
    $(".activity_for_oneday > ul > li").removeAttr('style');
    $(".activity_for_oneday").removeAttr('style');
}

function clearListView()
{
    clearListWeather();
    clearListActivity();
}


/*
 以下是创建事件相关的javascript function
 */
function showChoiceEventTypeDialog()
{
    var str = "<div id=\"choice_event_type\" class=\"dialog\">"+"\n";
    str = str + "<div class=\"header\">"+"\n";
    str = str + "<div class=\"title\">事件</div>"+"\n";
    str = str +	"<div class=\"close cancle\">×</div>"+"\n";
    str = str + "</div>"+"\n";
    str = str +	"<div class=\"body\">"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">事件类型：</div>"+"\n";
    str = str + "<div class=\"event_type_selector\">"+"\n";
    str = str + "<input type=\"radio\" name=\"event_type\" value=\"weather\" checked=\"checked\"/>天气"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<input type=\"radio\" name=\"event_type\" value=\"holiday\"/>节假日"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<input type=\"radio\" name=\"event_type\" value=\"activity\"/>活动"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<input type=\"radio\" name=\"event_type\" value=\"mode_apply\"/>系统模式应用"+"\n";
    str = str + "</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str + "<div class=\"footer\">"+"\n";
    str = str + "<div class=\"button_group\" style=\"width:400px\">"+"\n";
    str = str + "<div class=\"button cancle\">取消</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:showCreateEventDialog();\">创建</div>"+"\n";
    str = str + "<div class=\"button close_btn\">删除</div>"+"\n";
    
    str = str + "<div class=\"button\" onclick=\"javascript:showEditEventDialog();\">编辑</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str + "</div>"+"\n";
    $('#mask').html('');
    $('#mask').html(str);

    $('#choice_event_type').css("width","624px");
    $('#choice_event_type').css("height","290px");
    $('#choice_event_type').css("margin-left","680px");
    $('#choice_event_type').css("margin-top","350px");
    $('#choice_event_type >.body').css('height','190px');
    $("#choice_event_type .close_btn").css({"background-color":"#bdbdbd"});

    $('#mask .cancle').on('click',function(event){
        $('#mask').html('');
        $('#mask').hide();
    });

    $(".event_type_selector input").click(function(){
        var inputVal = $(this).val();
        console.log(inputVal);
        judgeShowBtn(inputVal);
    });

    $(".dialog").draggable({cancel:".body"});

    $('#mask').show();
}

function judgeShowBtn(val){
    switch (val) {
        case "weather" : 
            $("#choice_event_type .close_btn").css({"background-color":"#bdbdbd"});
            $("#choice_event_type .close_btn").off("click","div",showDeleteEventDialog);
            break;
        case "holiday" :
             $("#choice_event_type .close_btn").css({"background-color":"#7ecfd2"});
            $("#choice_event_type .close_btn").on("click",showDeleteEventDialog);
            break;
        case "activity" :
             $("#choice_event_type .close_btn").css({"background-color":"#7ecfd2"});
             $("#choice_event_type .close_btn").on("click",showDeleteEventDialog);
            break;
        case "mode_apply" :
             $("#choice_event_type .close_btn").css({"background-color":"#bdbdbd"});
             $("#choice_event_type .close_btn").off("click",showDeleteEventDialog);
            break;
        default : 
            return false;
    }
}

function showCreateEventDialog()
{
    var event_type = $('#choice_event_type input:checked').attr('value');
    switch (event_type)
    {
        case "weather":
            showCreateWeatherDialog();
            break;
        case "holiday":
            showCreateHolidayDialog();
            break;
        case "activity":
            showCreateActivityDialog();
            break;
        case "mode_apply":
            showCreateModeApplyDialog();
            break;
    }
}

function showDeleteEventDialog()
{
    var event_type = $('#choice_event_type input:checked').attr('value');
    switch (event_type)
    {
        case "weather":
            showDeleteWeatherDialog();
            break;
        case "holiday":
            showDeleteHolidayDialog();
            break;
        case "activity":
            showDeleteActivityDialog();
            break;
        case "mode_apply":
            showDeleteModeApplyDialog();
            break;
    }
}

function showDeleteHolidayDialog()
{
    var str = "<div id=\"delete_holiday\" class=\"dialog\">"+"\n";
    str = str + "<div class=\"header\">"+"\n";
    str = str + "<div class=\"title\">清除假日</div>"+"\n";
    str = str +	"<div class=\"close cancle\">×</div>"+"\n";
    str = str + "</div>"+"\n";

    str = str +	"<div class=\"body\">"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">开始日期</div>"+"\n";
    str = str + "<div id=\"datetimepicker13\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"start_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div class=\"normal_text\">结束日期</div>"+"\n";
    str = str + "<div id=\"datetimepicker14\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"end_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str +	"</div>"+"\n";

    str = str + "<div class=\"footer\">"+"\n";
    str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
    str = str + "<div class=\"button_group\">"+"\n";
    str = str + "<div class=\"button cancle\">取消</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:deleteHoliday();\">删除</div>"+"\n";

    str = str +	"</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str + "</div>"+"\n";
    $('#mask').html('');
    $('#mask').html(str);

    $('#delete_holiday').css("width","500px");
    $('#delete_holiday').css("height","290px");
    $('#delete_holiday').css("margin-left","680px");
    $('#delete_holiday').css("margin-top","350px");
    $('#delete_holiday >.body').css('width','260px');
    $('#delete_holiday >.body').css('height','190px');

    $('#mask .cancle').on('click',function(event){
        $('#mask').html('');
        $('#mask').hide();
    });

    $('#mask').show();

    $(".dialog").draggable({cancel:".body"});

    $("#datetimepicker13, #datetimepicker14").datetimepicker({
        pickTime: false,
        language: 'zh-CN',
        pick12HourFormat: true
    });
}

function deleteHoliday()
{
    var startDate = $(".dialog .body #start_date").val();
    if (! isDate(startDate))
    {
        showMessageInDialog('开始日期格式应为YYYY-MM-DD','error');
        return;
    }
    startDate = formateDate(startDate);

    var endDate = $(".dialog .body #end_date").val();
    if (! isDate(endDate))
    {
        showMessageInDialog('结束日期格式应为YYYY-MM-DD','error');
        return;
    }
    endDate = formateDate(endDate);

    var url = baseurl+'/calendar/clearHoliday';
    var params ={startDate:startDate,endDate:endDate};
    $.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){
            if (data.success == true)
            {
                showMessageInDialog('节假日信息已成功删除!','tip');
                deleteHolidayInCurrentView(startDate,endDate);
            }
            else
            {
                showMessageInDialog('节假日信息删除失败,请稍后再试!','error');
            }
            var t=setTimeout('hideDialog()',1500);
        }
        }
    );
}

function showDeleteActivityDialog()
{
    var str = "<div id=\"batch_delete_activity\" class=\"dialog\">"+"\n";
    str = str + "<div class=\"header\">"+"\n";
    str = str + "<div class=\"title\">批量删除活动</div>"+"\n";
    str = str +	"<div class=\"close cancle\">×</div>"+"\n";
    str = str + "</div>"+"\n";

    str = str +	"<div class=\"body\">"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">开始日期</div>"+"\n";
    str = str + "<div id=\"datetimepicker15\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"start_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div class=\"normal_text\">结束日期</div>"+"\n";
    str = str + "<div id=\"datetimepicker16\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"end_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";
    str = str +	"</div>"+"\n";

    str = str + "<div class=\"footer\">"+"\n";
    str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
    str = str + "<div class=\"button_group\">"+"\n";
    str = str + "<div class=\"button cancle\">取消</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:batchDeleteActivity();\">删除</div>"+"\n";

    str = str +	"</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str + "</div>"+"\n";
    $('#mask').html('');
    $('#mask').html(str);

    $('#batch_delete_activity').css("width","500px");
    $('#batch_delete_activity').css("height","290px");
    $('#batch_delete_activity').css("margin-left","680px");
    $('#batch_delete_activity').css("margin-top","350px");
    $('#batch_delete_activity >.body').css('width','260px');
    $('#batch_delete_activity >.body').css('height','190px');

    $('#mask .cancle').on('click',function(event){
        $('#mask').html('');
        $('#mask').hide();
    });

    $("#datetimepicker15, #datetimepicker16").datetimepicker({
        pickTime: false,
        language: 'zh-CN',
        pick12HourFormat: true
    });

    $(".dialog").draggable({cancel:".body"});

    $('#mask').show();
}

function batchDeleteActivity()
{
    var startDate = $(".dialog .body #start_date").val();
    if (! isDate(startDate))
    {
        showMessageInDialog('开始日期格式应为YYYY-MM-DD','error');
        return;
    }
    startDate = formateDate(startDate);

    var endDate = $(".dialog .body #end_date").val();
    if (! isDate(endDate))
    {
        showMessageInDialog('结束日期格式应为YYYY-MM-DD','error');
        return;
    }
    endDate = formateDate(endDate);

    var url = baseurl+'/calendar/clearEvent';
    var params ={startDate:startDate,endDate:endDate};
    $.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){
            if (data.success == true)
            {
                showMessageInDialog('活动信息已成功删除!','tip');
                if (($('#day').length ==1))
                  getDayActivity();
                else if (($('#week').length ==1))
                  getWeekActivity();
                else if (($('#month').length ==1))
                  deleteAllActivityInMonthView(startDate,endDate);
                else if ($('#list').length ==1)
                  deleteAllActivityInListView(startDate,endDate);
            }
            else
            {
                showMessageInDialog('活动信息删除失败,请稍后再试!','error');
            }
            var t=setTimeout('hideDialog()',1500);
        }
        }
    );
}

function showDeleteModeApplyDialog()
{
    var str = "<div id=\"batch_delete_mode_apply\" class=\"dialog\">"+"\n";
    str = str + "<div class=\"header\">"+"\n";
    str = str + "<div class=\"title\">批量删除模式应用计划</div>"+"\n";
    str = str +	"<div class=\"close cancle\">×</div>"+"\n";
    str = str + "</div>"+"\n";

    str = str +	"<div class=\"body\">"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div class=\"normal_text\">当前系统</div>"+"\n";

    var systemList = getSubSystem();
    if (systemList.length > 0)
        str = str + "<select id=\"subsystem\" style=\"width:150px\">"+"\n";
    var i = 0;
    for (i=0;i<systemList.length;i++)
    {
        str = str + "<option value=\""+ systemList[i].id+"\">"+systemList[i].name +"</option>"+"\n";
    }
    if (systemList.length > 0)
        str = str + "</select>"+"\n";

    if (systemList.length ==0)
        str = str + "<div class=\"normal_text\">XXX系统</div>"+"\n";           //获得系统的名字

    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div class=\"normal_text\">开始日期</div>"+"\n";
    str = str + "<input type=\"input\" id=\"start_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">结束日期</div>"+"\n";
    str = str + "<input type=\"input\" id=\"end_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str +	"</div>"+"\n";

    str = str + "<div class=\"footer\">"+"\n";
    str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
    str = str + "<div class=\"button_group\">"+"\n";
    str = str + "<div class=\"button cancle\">取消</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:batchDeleteModeApply();\">删除</div>"+"\n";

    str = str +	"</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str + "</div>"+"\n";
    $('#mask').html('');
    $('#mask').html(str);

    $('#batch_delete_mode_apply').css("width","500px");
    $('#batch_delete_mode_apply').css("height","320px");
    $('#batch_delete_mode_apply').css("margin-left","680px");
    $('#batch_delete_mode_apply').css("margin-top","310px");
    $('#batch_delete_mode_apply >.body').css('width','260px');
    $('#batch_delete_mode_apply >.body').css('height','220px');

    $('#mask .cancle').on('click',function(event){
        $('#mask').html('');
        $('#mask').hide();
    });

    $(".dialog").draggable({cancel:".body"});

    $('#mask').show();
}

function batchDeleteModeApply()
{
    var startDate = $(".dialog .body #start_date").val();
    if (! isDate(startDate))
    {
        showMessageInDialog('开始日期格式应为YYYY-MM-DD','error');
        return;
    }
    startDate = formateDate(startDate);

    var endDate = $(".dialog .body #end_date").val();
    if (! isDate(endDate))
    {
        showMessageInDialog('结束日期格式应为YYYY-MM-DD','error');
        return;
    }
    endDate = formateDate(endDate);

    var system_id = getSystemId();
    if ($(".dialog .body #subsystem").length ==1)
        system_id = $(".dialog .body #subsystem").val();

    var url = baseurl+'/calendar/batchDeleteModeApply';
    var params ={systemId:system_id,startDate:startDate,endDate:endDate};
    $.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){
            if (data.success == true)
            {
                showMessageInDialog('模式应用计划已成功删除!','tip');
            }
            else
            {
                showMessageInDialog('模式应用计划删除失败,请稍后再试!','error');
            }
            var t=setTimeout('hideDialog()',1500);
        }
        }
    );
}

function showEditEventDialog()
{
    var event_type = $('#choice_event_type input:checked').attr('value');
    switch (event_type)
    {
        case "weather":
            showEditWeatherDialog();
            break;
        case "holiday":
            showEditHolidayDialog();
            break;
        case "activity":
            showEditActivityDialog();
            break;
        case "mode_apply":
            showEditModeApplyDialog();
            break;
    }
}
function showEditWeatherDialog()
{
	
}

function showEditHolidayDialog()
{
	
}

function showEditActivityDialog()
{

}

function showEditModeApplyDialog()
{
	
}

function showCreateWeatherDialog()
{
    var str = "<div id=\"create_normal_event\" class=\"dialog\">"+"\n";
    str = str + "<div class=\"header\">"+"\n";
    str = str + "<div class=\"title\">设置天气</div>"+"\n";
    str = str +	"<div class=\"close cancle\">×</div>"+"\n";
    str = str + "</div>"+"\n";
    str = str +	"<div class=\"body\">"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div class=\"normal_text\">开始日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker9\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"start_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<div class=\"normal_text\">结束日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker10\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"end_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div class=\"normal_text\">天气描述：</div>"+"\n";
    str = str + "<select id=\"weather_type\">"+"\n";
    for (var i=0;i<weatherList.length;i++)
    {
        str = str + "<option value=\""+ weatherList[i].code+"\">"+ weatherList[i].name +"</option> "+"\n";
    }
    str = str + "</select>"+"\n"
    // str = str + "<br/>"+"\n";
    // str = str + "<br/>"+"\n";
    // str = str + "<div class=\"normal_text\">天气描述</div>"+"\n"; 
    // str = str + "<input type=\"input\" class=\"weather_desc\" value=\"\" />"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">最低气温：</div>"+"\n";
    str = str + "<input type=\"input\" id=\"min_temperature\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<div style=\"margin-left:33px\" class=\"normal_text has_margin_left\">最高气温：</div>"+"\n";
    str = str + "<input type=\"input\" id=\"max_temperature\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div class=\"normal_text\">日出时间：</div>"+"\n";
    str = str + "<div id=\"datetimepicker11\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"hh:mm\" type=\"input\" id=\"sunrise_time\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<div class=\"normal_text\">日落时间：</div>"+"\n";
    str = str + "<div id=\"datetimepicker12\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"hh:mm\" type=\"input\" id=\"sundown_time\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str +	"</div>"+"\n";
    str = str + "<div class=\"footer\">"+"\n";
    str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
    str = str + "<div class=\"button_group\">"+"\n";
    str = str + "<div class=\"button cancle\">取消</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:saveWeather();\">保存</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str + "</div>"+"\n";
    $('#mask').html('');
    $('#mask').html(str);

    $("#datetimepicker9, #datetimepicker10").datetimepicker({
        pickTime: false,
        language: 'zh-CN',
        pick12HourFormat: true
    })

    $("#datetimepicker11, #datetimepicker12").datetimepicker({
        pickDate: false,
        pickSeconds: false
    })

    $('#create_normal_event').css("width","650px");
    $('#create_normal_event').css("height","420px");
    $('#create_normal_event').css("margin-left","625px");
    $('#create_normal_event').css("margin-top","300px");
    $('#create_normal_event >.body').css('height','320px');

    $('#mask .cancle').on('click',function(event){
        $('#mask').html('');
        $('#mask').hide();
    });

    $(".dialog").draggable({cancel:".body"});

    $('#mask').show();
}

function showCreateHolidayDialog()
{
    var str = "<div id=\"create_normal_event\" class=\"dialog\">"+"\n";
    str = str + "<div class=\"header\">"+"\n";
    str = str + "<div class=\"title\">设置假日</div>"+"\n";
    str = str +	"<div class=\"close cancle\">×</div>"+"\n";
    str = str + "</div>"+"\n";
    str = str +	"<div class=\"body\">"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div class=\"normal_text\">开始日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker7\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"start_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<div class=\"normal_text has_margin_left\">结束日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker8\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"end_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">假日类型：</div>"+"\n";
    str = str + "<select id=\"holiday_type\">"+"\n";
    for (var i=0;i<holidayList.length;i++)
    {
        str = str + "<option value=\"" + holidayList[i].code+ "\">" +holidayList[i].name+"</option>"+"\n";
    }

    str = str + "</select>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">假日名称：</div>"+"\n";
    str = str + "<input type=\"input\" class=\"holiday_name\" value=\"\" />"+"\n";

    str = str +	"</div>"+"\n";
    str = str + "<div class=\"footer\">"+"\n";
    str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
    str = str + "<div class=\"button_group\">"+"\n";
    str = str + "<div class=\"button cancle\">取消</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:saveHoliday();\">保存</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str + "</div>"+"\n";
    $('#mask').html('');
    $('#mask').html(str);

    $("#datetimepicker7, #datetimepicker8").datetimepicker({
        pickTime: false,
        language: 'zh-CN',
        pick12HourFormat: true
    })

    $('#create_normal_event').css("width","650px");
    $('#create_normal_event').css("height","320px");
    $('#create_normal_event').css("margin-left","625px");
    $('#create_normal_event').css("margin-top","300px");
    $('#create_normal_event >.body').css('height','220px');

    $('#mask .cancle').on('click',function(event){
        $('#mask').html('');
        $('#mask').hide();
    });

    $(".dialog").draggable({cancel:".body"});

    $('#mask').show();
}

function showCreateActivityDialog()
{
    var str = "<div id=\"create_normal_event\" class=\"dialog\">"+"\n";
    str = str + "<div class=\"header\">"+"\n";
    str = str + "<div class=\"title\">创建一个新的活动</div>"+"\n";
    str = str +	"<div class=\"close cancle\">×</div>"+"\n";
    str = str + "</div>"+"\n";
    str = str +	"<div class=\"body\">"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div class=\"normal_text\">开始日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker1\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"start_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<div class=\"normal_text has_margin_left\">结束日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker2\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"end_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div class=\"normal_text\">开始时间：</div>"+"\n";
    str = str + "<div id=\"datetimepicker3\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"hh:mm\" type=\"input\" id=\"start_time\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<div class=\"normal_text has_margin_left\">结束时间：</div>"+"\n";
    str = str + "<div id=\"datetimepicker4\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"hh:mm\" type=\"input\" id=\"end_time\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">全天活动：</div>"+"\n";
    str = str + "<input type=\"checkbox\" id=\"all_day\" checked=\"checked\"/>"+"\n";

    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">活动名称：</div>"+"\n";
    str = str + "<input type=\"input\" class=\"activity_name\" value=\"\" />"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\" style=\"width:80px\">组&nbsp;&nbsp;织&nbsp;者：</div>"+"\n";
    str = str + "<input type=\"input\" class=\"activity_organizer\" value=\"\" />"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">活动地址：</div>"+"\n";
    str = str + "<input type=\"input\" class=\"activity_address\" value=\"\" />"+"\n";
        
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">活动详情：</div>"+"\n";
    str = str + "<textarea id=\"activity_desc\"></textarea>"+"\n";

    str = str +	"</div>"+"\n";
    str = str + "<div class=\"footer\">"+"\n";
    str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
    str = str + "<div class=\"button_group\">"+"\n";
    str = str + "<div class=\"button cancle\">取消</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:createActivity();\">保存</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str + "</div>"+"\n";
    $('#mask').html('');
    $('#mask').html(str);

    $('#create_normal_event').css("width","650px");
    $('#create_normal_event').css("height","620px");
    $('#create_normal_event').css("margin-left","625px");
    $('#create_normal_event').css("margin-top","290px");
    $('#create_normal_event >.body').css('height','520px');

    $('#mask .cancle').on('click',function(event){
        $('#mask').html('');
        $('#mask').hide();
    });

    $(".dialog").draggable({cancel:".body"});

    $('#mask').show();

    $('#datetimepicker1, #datetimepicker2').datetimepicker({
        pickTime: false,
        language: 'zh-CN',
        pick12HourFormat: true
    });

    $('#datetimepicker3, #datetimepicker4').datetimepicker({
        pickDate: false,
        pickSeconds: false
    });
}

function saveWeather()
{
    var startDate = $(".dialog .body #start_date").val();
    if (! isDate(startDate))
    {
        showMessageInDialog('开始日期格式应为YYYY-MM-DD','error');
        return;
    }
    startDate = formateDate(startDate);

    var endDate = $(".dialog .body #end_date").val();
    if (! isDate(endDate))
    {
        showMessageInDialog('结束日期格式应为YYYY-MM-DD','error');
        return;
    }
    endDate = formateDate(endDate);

    var weather_type = $(".dialog .body #weather_type").val();
    var weather_icon = getWeatherIconFromCss(weather_type);

    var weather_desc = $(".dialog .body #weather_type").find("option:selected").text();
    if(typeof(weather_desc) == "undefined")
        weather_desc = "";
    
    var weather = "1";
    if(weather_desc.contains("晴天"))
      weather="1";
    else
      weather="0";
    
    var min_temperature = $(".dialog .body #min_temperature").val();
    
    if (! isNumber(Math.abs(min_temperature)))
    {
        showMessageInDialog('最低气温应该为数字，单位℃','error');
        return;
    }

    var max_temperature = $(".dialog .body #max_temperature").val();
    if (! isNumber(Math.abs(max_temperature)))
    {
        showMessageInDialog('最高气温应该为数字，单位℃','error');
        return;
    }

    var sunrise_time = $(".dialog .body #sunrise_time").val();
    if (! isTime(sunrise_time))
    {
  	  showMessageInDialog('日出时间格式应为HH:MM','error');
	    return;
    }
    //	sunrise_time = formateTime(sunrise_time);

    var sundown_time = $(".dialog .body #sundown_time").val();
	  if (! isTime(sundown_time))
    {
  	  showMessageInDialog('日落时间格式应为HH:MM','error');
	    return;
    }
    //  sundown_time = formateTime(sundown_time);

    var url = baseurl+'/calendar/saveWeather';

    var params ={startDate:startDate,endDate:endDate,weather:weather,weatherIcon:weather_icon,weatherDescription:weather_desc,minTemperature:min_temperature,maxTemperature:max_temperature,sunrise:sunrise_time,sunset:sundown_time};
    $.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){
            if (data.success == true)
            {
                showMessageInDialog('天气信息保存成功!','tip');
                resetWeatherForManyDay(startDate,endDate,weather_desc,weather_type,weather_icon,min_temperature,max_temperature,sunrise_time,sundown_time)
            }
            else
            {
                showMessageInDialog('天气信息保存失败,请稍后再试!','error');
            }
            var t=setTimeout('hideDialog()',1500);
        }
        }
    );
}

function saveHoliday()
{
    var startDate = $(".dialog #start_date").val();
    if (! isDate(startDate))
    {
        showMessageInDialog('开始日期格式应为YYYY-MM-DD','error');
        return;
    }
    startDate = formateDate(startDate);

    var endDate = $(".dialog #end_date").val();
    if (! isDate(endDate))
    {
        showMessageInDialog('结束日期格式应为YYYY-MM-DD','error');
        return;
    }
    endDate = formateDate(endDate);

    var holiday_type = $(".dialog #holiday_type").val();
    var holiday_name = $(".dialog .holiday_name").val();
    if (holiday_name == "")
    {
        showMessageInDialog('假日名称不能为空','error');
        return;
    }

    var url = baseurl + '/calendar/saveHoliday';
    var params ={startDate:startDate,endDate:endDate,holidayType:holiday_type,holidayName:holiday_name};
    $.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){
            if (data.success == true)
            {
                showMessageInDialog('节假日信息保存成功!','tip');
                resetHoliday(startDate,endDate,holiday_name);
            }
            else
            {
                showMessageInDialog('节假日信息保存失败,请稍后再试!','error');
            }
            var t=setTimeout('hideDialog()',1500);

        }
        }
    );
}

function createActivity()
{
    var startDate = $(".dialog #start_date").val();
    console.log(startDate);
    if (! isDate(startDate))
    {
        showMessageInDialog('开始日期格式应为YYYY-MM-DD','error');
        return;
    }
    startDate = formateDate(startDate);

    var endDate = $(".dialog #end_date").val();
    console.log(endDate);
    if (! isDate(endDate))
    {
        showMessageInDialog('结束日期格式应为YYYY-MM-DD','error');
        return;
    }
    endDate = formateDate(endDate);

    var startTime = $(".dialog #start_time").val();
    console.log(startTime);
    if (! isTime(startTime))
    {
        showMessageInDialog('开始时间格式应为HH:MM','error');
        return;
    }
    startTime = formateTime(startTime);

    var endTime = $(".dialog #end_time").val();
    if (! isTime(endTime))
    {
        showMessageInDialog('结束时间格式应为HH:MM','error');
        return;
    }
    endTime = formateTime(endTime);

    var all_day = $(".dialog #all_day").prop('checked');
    
    var activity_organizer = $(".dialog .activity_organizer").val();
    if (activity_organizer =="")
    {
        showMessageInDialog('组织者不能为空','error');
        return;
    }
    
    var activity_address = $(".dialog .activity_address").val();
    if (activity_address =="")
    {
        showMessageInDialog('活动地址不能为空','error');
        return;
    }
    
    var activity_name = $(".dialog .activity_name").val();
    if (activity_name =="")
    {
        showMessageInDialog('活动名称不能为空','error');
        return;
    }

    var activity_desc = $(".dialog #activity_desc").val();

    var url = baseurl + '/calendar/saveEvent';
    var params ={startDate:startDate,endDate:endDate,startTime:startTime,endTime:endTime,all_day:all_day,organizer:activity_organizer,eventAddress:activity_address,title:activity_name,description:activity_desc};
    console.log(params);
    $.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){
            if (data.success == true)
            {
                showMessageInDialog('活动信息保存成功!','tip');
                if ($('#week').length ==1)
                {
                    getWeekActivity();
                }
                else if ($('#month').length ==1)
                {
                  addActivityInMonthView(startDate,endDate,activity_name);
                }
                else if ($('#list').length ==1)
                {
                	addActivityInListView(startDate,endDate,startTime,endTime,all_day,activity_name);
                }
            }
            else
            {
                showMessageInDialog('活动信息保存失败,请稍后再试!','error');
            }
            var t=setTimeout('hideDialog()',1500);
        }
        }
    );
}

function getSubSystem()
{
    var systemList = new Array();
    var system = null;
    var url = baseurl+'/calendar/getSubSystem';
    var system_id = getSystemId();

    var params ={systemId:system_id};
    $.ajax({url:url,type:'GET',dataType:'json',async:false,data:params,success:function(data){
        var i = 0;
        for (i=0;i<data.length;i++)
        {
            system = {"id":data[i].systemCode,"name":data[i].systemName};
            systemList.push(system);
        }
    }
    });
    return systemList;
}

function showCreateModeApplyDialog()
{
    var str = "<div id=\"create_mode_apply\" class=\"dialog\">"+"\n";
    str = str + "<div class=\"header\">"+"\n";
    str = str + "<div class=\"title\">模式预置</div>"+"\n";
    str = str +	"<div class=\"close cancle\">×</div>"+"\n";
    str = str + "</div>"+"\n";

    str = str +	"<div class=\"body\">"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div>"+"\n";
    
    str = str + "<div class=\"normal_text\">开始日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker5\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"system_start_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<div class=\"normal_text\">结束日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker6\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"system_end_date\" class=\"date_input\" value=\"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";
    
    str = str + "</div>";
    
    var systemList = getSubSystem();
    if (systemList.length > 0)
    {
      str = str + "<ul id=\"subsystem\">";
      for (var i=0;i<systemList.length;i++)
      {
         str = str + "<li>";
         str = str + "<div value=\""+ systemList[i].id+"\">"+systemList[i].name +"</div>";
         str = str + "<select id=\"system_mode_"+systemList[i].id +"\" name=\"system_mode_"+systemList[i].id+"\"></select>";
         str = str + "</li>";
      }
      str = str + "</ul>";
      str = str + "<div class=\"loadmode\">正在读取模式列表...</div>"
    }  
    else
    {
      var systemId = getSystemId();
    	var modeList = getModeListBySystemId(systemId);
    	str = str + "<ul id=\"subsystem\">";
    	str = str + "<li>";
    	str = str + "<select id=\"system_mode_"+systemId+"\" name=\"system_mode_"+systemId+"\">";
    	for (var i=0;i<modeList.length;i++)
      {
        str = str + "<option value=\"" + modeList[i].id+"\">"+modeList[i].name+"</option>"+"\n";
      }
    	str = str + "</select>";
    	str = str + "</li>";
    	str = str + "</ul>";
    }    
        
    str = str + "</div>"+"\n";

    str = str + "<div class=\"footer\">"+"\n";
    str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
    str = str + "<div class=\"button_group\">"+"\n";
    str = str + "<div class=\"button cancle\">取消</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:saveModeApply();\">保存</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str +	"</div>"+"\n";

    str = str +	"</div>"+"\n";

    $('#mask').html('');
    $('#mask').html(str);

    $('#create_mode_apply').css("width","1300px");
    $('#create_mode_apply').css("height","400px");
    $('#create_mode_apply').css("margin-left","310px");
    $('#create_mode_apply').css("margin-top","340px");
    $('#create_mode_apply >.body').css('height','300px');

    $('#mask .cancle').on('click',function(event){
        $('#mask').html('');
        $('#mask').hide();
    });

    $("#datetimepicker5, #datetimepicker6").datetimepicker({
        pickTime: false,
        language: 'zh-CN',
        pick12HourFormat: true
    });

    $(".dialog").draggable({cancel:".body"});
    $('#mask').show();
    
    if (systemList.length > 0)
    {    
      for (var i=0;i<systemList.length;i++)
      {
        var modeList = getModeListBySystemId(systemList[i].id);
        var option = "";
        for (var j=0;j<modeList.length;j++)
        {
           option = "<option value=\"" + modeList[j].id+"\">"+modeList[j].name+"</option>"+"\n";
           $('#create_mode_apply .body #system_mode_'+systemList[i].id).append(option);
        }
      }
      $('#create_mode_apply .body .loadmode').hide();
    }
}

function getModeListBySystemId(system_id)
{
    var modeList = new Array();
    var mode = null;
    var url = baseurl+'/calendar/getPattern';
    var d = new Date();
    var r = Math.random()+""+d.getMilliseconds();

    var params ={systemId:system_id,r:r};
    $.ajax({url:url,type:'GET',dataType:'json',async:false,data:params,success:function(data){
        var i = 0;
        for (i=0;i<data.length;i++)
        {
            mode = {"id":data[i].id,"name":data[i].name};
            modeList.push(mode);
        }
    }
    });
    return modeList;
}

function toggleDeviceException()
{
    if ($("#device_exception").length > 0)
    {
        hideDeviceException();
    }
    else
    {
        showDeviceException();
    }
}

function hideDeviceException()
{
    $("#device_exception").remove();
    $('#create_mode_apply').css("height","180px");
    $('#create_mode_apply').css("margin-top","190px");
    $('#create_mode_apply >.body').css('height','100px');
    $("#create_mode_apply >.body .arrow_up").addClass("arrow_down");
    $("#create_mode_apply >.body .arrow_up").removeClass("arrow_up");
}
function showDeviceException()
{
    var start_date = $("#system_start_date").val();
    if (start_date =="")
    {
        showMessageInDialog("请设置一个开始日期",'error');
        return;
    }
    var end_date = $("#system_end_date").val();
    if (end_date =="")
    {
        showMessageInDialog("请设置一个结束日期",'error');
        return;
    }
    var str = "";
    str = str + "<div id=\"device_exception\">"+"\n";
    str = str + "<div class=\"device_exception_title\">设备列外</div>"+"\n"

    str = str + "<div class=\"device_group_tree\">"+"\n";
    str = str + "<div id=\"tree\"></div>"+"\n";
    str = str + "</div>"+"\n";

    str = str +"<div class=\"device_exception_table\">"+"\n";
    str = str +"<div class=\"device_name\">例外的设备:<span></span></div>"+"\n";
    str = str + "<div class=\"normal_text\">开始日期</div>"+"\n";
    str = str + "<input type=\"input\" id=\"system_start_date_for_exception\" class=\"date_input\" value=\""+start_date+"\" />"+"\n";
    str = str + "<div class=\"normal_text\">结束日期</div>"+"\n";
    str = str + "<input type=\"input\" id=\"system_end_date_for_exception\" class=\"date_input\" value=\""+end_date+"\" />"+"\n";


    var mode_name_list = new Array();
    mode_name_list[0] = '平日夏季供冷1';
    mode_name_list[1] = '平日夏季供冷2';
    mode_name_list[2] = '平日夏季供冷3';

    str = str + "<select id=\"system_mode_name_for_exception\" name=\"system_mode_name_for_exception\">"+"\n";
    for (var i=0;i<mode_name_list.length;i++)
    {
        str = str + "<option>"+mode_name_list[i]+"</option>"+"\n";
    }
    str = str + "</select>"+"\n";

    str = str + "<div class=\"button\" onclick=\"javascript:createDeviceException()\">添加</div>"+"\n";

    str = str + "<table style=\"margin-top:10px\">"+"\n";
    str = str + "<thead>"+"\n";
    str = str + "<tr>"+"\n";
    str = str + "<th class=\"device\">设备</th>"+"\n";
    str = str + "<th class=\"start_date\">开始日期</th>"+"\n";
    str = str + "<th class=\"end_date\">截至日期</th>"+"\n";
    str = str + "<th class=\"mode_name\">运行模式</th>"+"\n";
    str = str + "<th class=\"action\"></th>"+"\n";
    str = str + "</tr>"+"\n";
    str = str + "</thead>"+"\n";

    str = str + "<tbody>"+"\n";
    str = str + "</tbody>"+"\n";
    str = str + "</table>"+"\n";

    str = str +"</div>"+"\n";

    str = str + "</div>"+"\n";

    $('#create_mode_apply .body').append(str);

    $('#create_mode_apply').css("height","700px");
    $('#create_mode_apply').css("margin-top","190px");
    $('#create_mode_apply >.body').css('height','620px');

    var deviceTree = '[{"Equpment":{"id":"1","name":"设备1","parent":"0"}},{"Equpment":{"id":"2","name":"设备2","parent":"0"}},{"Equpment":{"id":"3","name":"设备3","parent":"0"}},{"Equpment":{"id":"4","name":"设备4","parent":"2"}},{"Equpment":{"id":"5","name":"设备5","parent":"2"}}]';
    device_list = eval ("("+deviceTree+")");

    var id ="";
    var name ="";
    var parent ="";
    dd.config.clickfolderEvent = "addNewDeviceException";
    dd.add(0,-1,"所有设备");// id=0代表目录树的根
    var i = 0;
    for (i=0;i<device_list.length;i++)
    {
        id = device_list[i].Equpment.id;
        name = device_list[i].Equpment.name;
        parent = device_list[i].Equpment.parent;
        dd.add(id,parent,name);
    }
    document.getElementById('tree').innerHTML = dd;

    $("#create_mode_apply >.body .arrow_down").addClass("arrow_up");
    $("#create_mode_apply >.body .arrow_down").removeClass("arrow_down");
    loadDeviceException();
}

function loadDeviceException()
{
    var json = "("+'[{deviceName:"xxx设备名称1",startDate:"2013-09-10",endDate:"2013-09-15",modeName:"平日夏季供冷1"},{deviceName:"xxx设备名称2",startDate:"2013-09-12",endDate:"2013-09-14",modeName:"平日夏季供冷2"}]'+")";
    var deviceExceptionList = eval(json);
    var str = "";
    var twos = "even";
    for (var i=0;i<deviceExceptionList.length;i++)
    {
        str = "";
        if ((i+1)%2 == 0)
            twos = "odd";
        else
            twos = "even";

        str = str + "<tr class=\""+twos+"\">"+"\n";
        str = str + "<td>"+deviceExceptionList[i].deviceName+"</td>"+"\n";
        str = str + "<td>"+deviceExceptionList[i].startDate+"</td>"+"\n";
        str = str + "<td>"+deviceExceptionList[i].endDate+"</td>"+"\n";
        str = str + "<td>"+deviceExceptionList[i].modeName+"</td>"+"\n";
        str = str + "<td>"+"\n";
        str = str + "<div class=\"button\" onclick=\"javascript:editDeviceException(this)\">编辑</div>"+"\n";
        str = str + "<div class=\"button\" onclick=\"javascript:deleteDeviceException(this)\">删除</div>"+"\n";
        str = str + "</td>"+"\n";
        str = str + "</tr>"+"\n";
        $(".dialog >.body .device_exception_table > table > tbody").append(str);
    }
}

function addNewDeviceException(device_id)
{
    for(var i=0;i<device_list.length;i++)
    {
        if (device_id == parseInt(device_list[i].Equpment.id))
        {
            $(".device_exception_table > .device_name >span").html(device_list[i].Equpment.name);
            break;
        }
    }
}

function createDeviceException()
{
    var device_name = $(".device_exception_table >.device_name >span").html();
    if (device_name =="")
    {
        showMessageInDialog("请选择一组或一个设备!",'error');
        return;
    }
    var start_date = $(".device_exception_table > #system_start_date_for_exception").val();
    if (start_date=="")
    {
        showMessageInDialog("请设置一个开始日期!",'error');
        return;
    }
    var end_date = $(".device_exception_table > #system_end_date_for_exception").val();
    if (end_date=="")
    {
        showMessageInDialog("请设置一个结束日期!",'error');
        return;
    }
    var mode_name = $(".device_exception_table > #system_mode_name_for_exception").val();

    var twos = "even";
    var count = $('.device_exception_table > table> tbody >tr').length+1;
    if (count%2 == 0)
        twos = "odd";
    var str = "";
    str = str + "<tr class=\""+twos+"\">"+"\n";
    str = str + "<td>"+device_name+"</td>"+"\n";
    str = str + "<td>"+start_date+"</td>"+"\n";
    str = str + "<td>"+end_date+"</td>"+"\n";
    str = str + "<td>"+mode_name+"</td>"+"\n";
    str = str + "<td>"+"\n"
    str = str + "<div class=\"button\" onclick=\"javascript:editDeviceException(this)\">编辑</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:deleteDeviceException(this)\">删除</div>"+"\n";
    str = str + "</td>"+"\n"
    str = str + "</tr>"+"\n";
    $('.device_exception_table > table> tbody').append(str);
    $(".device_exception_table >.device_name >span").html('');
}

function deleteDeviceException(e)
{
    e.parentNode.parentNode.remove();
    var tr_list = $('.device_exception_table > table> tbody >tr');
    resetTwos(tr_list);
}
function editDeviceException(e)
{
    var tds = $(e.parentNode.parentNode).children();
    var start_date = tds.eq(1).html();
    var td = "<input type=\"input\" value=\""+start_date+"\" class=\"date_input\" id=\"start_date\" style=\"width:99px\">";
    tds.eq(1).html(td);
    var end_date = tds.eq(2).html();
    td = "<input type=\"input\" value=\""+end_date+"\" class=\"date_input\" id=\"end_date\" style=\"width:99px\">";
    tds.eq(2).html(td);
    var mode_name = tds.eq(3).html();
    var mode_name_list = new Array();
    mode_name_list[0] = '平日夏季供冷1';
    mode_name_list[1] = '平日夏季供冷2';
    mode_name_list[2] = '平日夏季供冷3';

    td =""
    td = td + "<select>"+"\n";
    for (var i=0;i<mode_name_list.length;i++)
    {
        if (mode_name == mode_name_list[i])
            td = td + "<option selected=\"selected\">"+mode_name_list[i]+"</option>"+"\n";
        else
            td = td + "<option>"+mode_name_list[i]+"</option>"+"\n";
    }
    td = td + "</select>"+"\n";
    tds.eq(3).html(td);
    $(e).html('保存');
    $(e).attr('onclick','javascript:saveDeviceException(this)');
}

function saveDeviceException(e)
{
    var tds = $(e.parentNode.parentNode).children();
    var start_date = tds.eq(1).children().first().val();
    if (start_date =="")
    {
        showMessageInDialog("请设置一个开始日期",'error');
        return;
    }
    var end_date = tds.eq(2).children().first().val();
    if (end_date =="")
    {
        showMessageInDialog("请设置一个结束日期",'error');
        return;
    }
    tds.eq(1).html(start_date);
    tds.eq(2).html(end_date);

    var mode_name = tds.eq(3).children().first().val();
    tds.eq(3).html(mode_name);
    $(e).html('编辑');
    $(e).attr('onclick','javascript:editDeviceException(this)');
}

function saveModeApply()
{
    var system_start_date = $("#system_start_date").val();
    if (! isDate(system_start_date))
    {
        showMessageInDialog('开始日期格式应为YYYY-MM-DD','error');
        return;
    }
    system_start_date = formateDate(system_start_date);

    var system_end_date = $(".dialog .body #system_end_date").val();
    if (! isDate(system_end_date))
    {
        showMessageInDialog('结束日期格式应为YYYY-MM-DD','error');
        return;
    }
    system_end_date = formateDate(system_end_date);

    /*
    systemId = getSystemId();
    if ($("#subsystem >li >div").length ==1)
        systemId = $("#subsystem >li >div").attr('value');
    */
    
    var system_mode_id = "";
    $("#subsystem >li >select").each(function(index){
      system_mode_id = system_mode_id + $(this).val()+",";
    }
    );
    if (system_mode_id.charAt(system_mode_id.length-1) ==",")
      system_mode_id = system_mode_id.substr(0,system_mode_id.length-1);
    
    var deviceName ="";
    var deviceId ="";
    var device_start_date = "";
    var device_end_date = "";
    var device_mode_name ="";
    var device_mode_id = "";
    /*var trs = $("#create_mode_apply .device_exception_table > table > tbody >tr");
     var tds = null;
     var de = "";
     var i=0;
     for (i=0;i<trs.length;i++)
     {
     tds = trs.eq(i).children();
     deviceName = tds.eq(0).html();
     device_start_date = tds.eq(1).html();
     device_end_date = tds.eq(2).html();
     device_mode_name = tds.eq(3).html();
     de = deviceName + ','+device_start_date+','+device_end_date+','+device_mode_name +';';	
     alert(de);
     }
     */
    //var modeId = "001";

    var url = baseurl+'/calendar/savePredictPattern';
    var params ={patternId:system_mode_id,startDate:system_start_date,endDate:system_end_date};
    console.log(params);
    $.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){

        if (data.success == true)
        {
            showMessageInDialog('系统模式应用计划保存成功!','tip');
            if ($('#week').length ==1)
            {
                getWeekModeApply();
            }
            else if ($('#month').length ==1)
            {
                getMonthSchedule();
            }
            var t=setTimeout('hideDialog()',1500);
        }
        else
        {
            showMessageInDialog('系统模式应用计划保存失败,请稍后再试!','error');
        }
        
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

function showOneDayDetailMode(yyyy,mm,dd)
{
    if ($('#day').length ==1)
    {
        renderOneDayDetailMode(selected_yyyy,selected_mm,selected_dd,1);
    }
    else if ($('#week').length ==1)
    {
        renderOneDayDetailMode(yyyy,mm,dd);
    }
    else if ($('#month').length ==1)
    {
        renderOneDayDetailMode(yyyy,mm,dd);
    }
}

function renderOneDayDetailMode(selected_yyyy,selected_mm,selected_dd)
{
    var str = "<div id=\"edit_mode_apply\" class=\"dialog\">"+"\n";
    str = str + "<div class=\"header\">"+"\n";
    str = str + "<div class=\"title\">模式预置</div>"+"\n";
    str = str +	"<div class=\"close cancle\">×</div>"+"\n";
    str = str + "</div>"+"\n";

    str = str +	"<div class=\"body\">"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div class=\"date\">"+selected_yyyy+"年"+selected_mm+"月"+selected_dd+"日</div>"+"\n";

    var systemList = getSubSystem();
    if (systemList.length > 0)
    {
      str = str + "<ul id=\"subsystem\">";
      for (var i=0;i<systemList.length;i++)
      {
         str = str + "<li>";
         str = str + "<div value=\""+ systemList[i].id+"\">"+systemList[i].name +"</div>";
         str = str + "<select id=\"system_mode_"+systemList[i].id +"\" name=\"system_mode_"+systemList[i].id+"\"></select>";
         str = str + "</li>";
      }
      str = str + "</ul>";
      str = str + "<div class=\"loadmode\">正在读取模式列表...</div>"
    }  
    else
    {
      var systemId = getSystemId();
    	var modeList = getModeListBySystemId(systemId);
    	str = str + "<ul id=\"subsystem\">";
    	str = str + "<li>";
    	str = str + "<select id=\"system_mode_"+systemId+"\" name=\"system_mode_"+systemId+"\">";
    	for (var i=0;i<modeList.length;i++)
      {
        str = str + "<option value=\"" + modeList[i].id+"\">"+modeList[i].name+"</option>"+"\n";
      }
    	str = str + "</select>";
    	str = str + "</li>";
    	str = str + "</ul>";
    }  
    
    str = str + "</div>"+"\n";

    str = str + "<div class=\"footer\">"+"\n";
    str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
    str = str + "<div class=\"button_group\">"+"\n";
    str = str + "<div class=\"button cancle\">取消</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:saveOneDayModeApply('"+selected_yyyy+"','"+selected_mm+"','"+selected_dd+"');\">保存</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str +	"</div>"+"\n";

    str = str +	"</div>"+"\n";


    $('#mask').html('');
    $('#mask').html(str);

    $('#edit_mode_apply').css("width","1300px");
    $('#edit_mode_apply').css("height","400px");
    $('#edit_mode_apply').css("margin-left","310px");
    $('#edit_mode_apply').css("margin-top","340px");
    $('#edit_mode_apply >.body').css('height','300px');
    
    $('#mask .cancle').on('click',function(event){
        $('#mask').html('');
        $('#mask').hide();
    });
    
    $(".dialog").draggable({cancel:".body"});
    $('#mask').show();
    
    if (systemList.length > 0)
    {    
      for (var i=0;i<systemList.length;i++)
      {
        var modeList = getModeListBySystemId(systemList[i].id);
        var option = "";
        for (var j=0;j<modeList.length;j++)
        {
           option = "<option value=\"" + modeList[j].id+"\">"+modeList[j].name+"</option>"+"\n";
           $('#edit_mode_apply .body #system_mode_'+systemList[i].id).append(option);
        }
      }
      $('#edit_mode_apply .body .loadmode').hide();
    }
}

function toggleLoadDeviceException()
{
    if ($("#device_exception").length > 0)
    {
        hideLoadDeviceException();
    }
    else
    {
        loadOneDayDeviceException();
    }
}

function hideLoadDeviceException()
{
    $("#device_exception").remove();
    $('#edit_mode_apply').css("height","220px");
    $('#edit_mode_apply >.body').css('height','120px');
    $("#edit_mode_apply >.body .arrow_up").addClass("arrow_down");
    $("#edit_mode_apply >.body .arrow_up").removeClass("arrow_up");
}

function loadOneDayDeviceException()
{
    var str = "";
    str = str + "<div id=\"device_exception\">"+"\n";
    str = str + "<div class=\"device_exception_title\">设备列外</div>"+"\n"

    str = str + "<div class=\"device_group_tree\">"+"\n";
    str = str + "<div id=\"tree\"></div>"+"\n";
    str = str + "</div>"+"\n";

    str = str +"<div class=\"device_exception_table\">"+"\n";
    str = str +"<div class=\"device_name\">例外的设备:<span></span></div>"+"\n";


    var mode_name_list = new Array();
    mode_name_list[0] = '平日夏季供冷1';
    mode_name_list[1] = '平日夏季供冷2';
    mode_name_list[2] = '平日夏季供冷3';

    str = str + "<select id=\"system_mode_name_for_exception\" name=\"system_mode_name_for_exception\">"+"\n";
    for (i=0;i<mode_name_list.length;i++)
    {
        str = str + "<option>"+mode_name_list[i]+"</option>"+"\n";
    }
    str = str + "</select>"+"\n";

    str = str + "<div class=\"button\" onclick=\"javascript:createOneDayDeviceException()\">添加</div>"+"\n";

    str = str + "<table style=\"margin-top:10px\">"+"\n";
    str = str + "<thead>"+"\n";
    str = str + "<tr>"+"\n";
    str = str + "<th class=\"device\">设备</th>"+"\n";
    str = str + "<th class=\"mode_name\">运行模式</th>"+"\n";
    str = str + "<th class=\"action\"></th>"+"\n";
    str = str + "</tr>"+"\n";
    str = str + "</thead>"+"\n";

    str = str + "<tbody>"+"\n";
    str = str + "</tbody>"+"\n";
    str = str + "</table>"+"\n";

    str = str +"</div>"+"\n";

    str = str + "</div>"+"\n";

    $('#edit_mode_apply .body').append(str);

    $('#edit_mode_apply').css("height","700px");
    $('#edit_mode_apply >.body').css('height','640px');

    var deviceTree = '[{"Equpment":{"id":"1","name":"设备1","parent":"0"}},{"Equpment":{"id":"2","name":"设备2","parent":"0"}},{"Equpment":{"id":"3","name":"设备3","parent":"0"}},{"Equpment":{"id":"4","name":"设备4","parent":"2"}},{"Equpment":{"id":"5","name":"设备5","parent":"2"}}]';
    device_list = eval ("("+deviceTree+")");

    var id ="";
    var name ="";
    var parent ="";
    dd.config.clickfolderEvent = "addNewDeviceException";
    dd.add(0,-1,"所有设备");// id=0代表目录树的根
    var i = 0;
    for (i=0;i<device_list.length;i++)
    {
        id = device_list[i].Equpment.id;
        name = device_list[i].Equpment.name;
        parent = device_list[i].Equpment.parent;
        dd.add(id,parent,name);
    }
    document.getElementById('tree').innerHTML = dd;

    $("#edit_mode_apply >.body .arrow_down").addClass("arrow_up");
    $("#edit_mode_apply >.body .arrow_down").removeClass("arrow_down");
}

function createOneDayDeviceException()
{
    var device_name = $(".device_exception_table >.device_name >span").html();
    if (device_name =="")
    {
        showMessageInDialog("请选择一组或一个设备!",'error');
        return;
    }

    var mode_name = $(".device_exception_table > #system_mode_name_for_exception").val();

    var twos = "even";
    var count = $('.device_exception_table > table> tbody >tr').length+1;
    if (count%2 == 0)
        twos = "odd";
    var str = "";
    str = str + "<tr class=\""+twos+"\">"+"\n";
    str = str + "<td>"+device_name+"</td>"+"\n";
    str = str + "<td>"+mode_name+"</td>"+"\n";
    str = str + "<td>"+"\n"
    str = str + "<div class=\"button\" onclick=\"javascript:editOneDayDeviceException(this)\">编辑</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:deleteOneDayDeviceException(this)\">删除</div>"+"\n";
    str = str + "</td>"+"\n"
    str = str + "</tr>"+"\n";
    $('.device_exception_table > table> tbody').append(str);
    $(".device_exception_table >.device_name >span").html('');
}

function editOneDayDeviceException(e)
{
    var tds = $(e.parentNode.parentNode).children();
    var mode_name = tds.eq(1).html();
    var mode_name_list = new Array();
    mode_name_list[0] = '平日夏季供冷1';
    mode_name_list[1] = '平日夏季供冷2';
    mode_name_list[2] = '平日夏季供冷3';

    td =""
    td = td + "<select>"+"\n";
    for (var i=0;i<mode_name_list.length;i++)
    {
        if (mode_name == mode_name_list[i])
            td = td + "<option selected=\"selected\">"+mode_name_list[i]+"</option>"+"\n";
        else
            td = td + "<option>"+mode_name_list[i]+"</option>"+"\n";
    }
    td = td + "</select>"+"\n";
    tds.eq(1).html(td);
    $(e).html('保存');
    $(e).attr('onclick','javascript:saveOneDayDeviceException(this)');
}

function deleteOneDayDeviceException(e)
{
    e.parentNode.parentNode.remove();
    var tr_list = $('.device_exception_table > table> tbody >tr');
    resetTwos(tr_list);
}

function saveOneDayDeviceException(e)
{
    var tds = $(e.parentNode.parentNode).children();
    var mode_name = tds.eq(1).children().first().val();
    tds.eq(1).html(mode_name);
    $(e).html('编辑');
    $(e).attr('onclick','javascript:editOneDayDeviceException(this)');
}

function saveOneDayModeApply(yyyy,mm,dd)
{
    if (dd.length == 1) {
        dd = "0" + dd;
    }
    if (mm.length == 1) {
        mm = "0" + mm;
    }
    /*
    var systemId = getSystemId();
    if ($(".dialog .body #subsystem").length ==1)
        systemId = $(".dialog .body #subsystem >li >div").attr('value');
    */
    
    var system_mode_id = "";
    $("#subsystem >li >select").each(function(index){
      system_mode_id = system_mode_id + $(this).val()+",";
    }
    );
    if (system_mode_id.charAt(system_mode_id.length-1) ==",")
      system_mode_id = system_mode_id.substr(0,system_mode_id.length-1);
       
    var system_start_date = yyyy+""+mm+""+dd ;
    var system_end_date = system_start_date;

    var url = baseurl + '/calendar/savePredictPattern';
    var params ={patternId:system_mode_id,startDate:system_start_date,endDate:system_end_date};
    $.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){

        if (data.success == true)
        {
            showMessageInDialog('系统模式应用计划保存成功!','tip');
            if ($('#week').length ==1)
            {
                getWeekModeApply();
            }
            else if ($('#month').length ==1)
            {
                getMonthSchedule();
            }
            var t=setTimeout('hideDialog()',1500);
        }
        else
        {
            showMessageInDialog('系统模式应用计划保存失败,请稍后再试!','error');
        }
    }
    });
}

function showOneDayWeather(day_index)
{
    if ($('#day').length ==1)
    {
        renderOneDayWeather(selected_yyyy,selected_mm,selected_dd);
    }
    else if ($('#week').length ==1)
    {
        var tempDate = new Date();
        tempDate.setFullYear(first_date.getFullYear());
        tempDate.setMonth(first_date.getMonth());
        tempDate.setDate(first_date.getDate());
        var yyyy = tempDate.getFullYear();
        var mm = tempDate.getMonth()+1;
        if (mm < 10)
            mm = "0"+mm;
        var dd = tempDate.getDate();
        if (dd < 10)
            dd = "0"+dd;
        dayIndex = day_index;
        todayWeatherDesc = $("#week #weather_slider .weather_for_oneday :eq("+day_index+") .weather_desc").html();
        todayWeatherIcon = getWeatherIconFromName(todayWeatherDesc);
        var temperature = $("#week #weather_slider .weather_for_oneday :eq("+day_index+") .temperature").html();
        var first_t_char = temperature.indexOf("℃");
        var t_char = temperature.indexOf("~");
        var last_t_char = temperature.lastIndexOf("℃");
        todayMinTemperature = temperature.substring(0,first_t_char);
        todayMaxTemperature = temperature.substring(t_char+1,last_t_char);
        todaySunriseTime = $("#week #weather_slider .weather_for_oneday :eq("+day_index+") .sun_up_time").html().substring(0,5);
        todaySundownTime = $("#week #weather_slider .weather_for_oneday :eq("+day_index+") .sun_down_time").html().substring(0,5);
        renderOneDayWeather(yyyy,mm,dd);
    }
    else if ($('#month').length ==1)
    {
        var tempDate = new Date();
        tempDate.setFullYear(parseInt(first_day.substring(0,4)));
        tempDate.setMonth(parseInt(first_day.substring(4,6))-1);
        tempDate.setDate(parseInt(first_day.substring(6,8))+day_index -1);
        var yyyy = tempDate.getFullYear();
        var mm = tempDate.getMonth()+1;
        if (mm < 10)
            mm = "0"+mm;
        var dd = tempDate.getDate();
        if (dd < 10)
            dd = "0"+dd;
        dayIndex = day_index;
        todayWeatherDesc = $("#month #day_grid #day"+day_index+" .weather_list .weather_desc").html();
        todayWeatherIcon = getWeatherIconFromName(todayWeatherDesc);
        var temperature = $("#month #day_grid #day"+day_index+" .weather_list .temperature").html();
        var first_t_char = temperature.indexOf("℃");
        var t_char = temperature.indexOf("~");
        var last_t_char = temperature.lastIndexOf("℃");
        todayMinTemperature = temperature.substring(0,first_t_char);
        todayMaxTemperature = temperature.substring(t_char+1,last_t_char);
        todaySunriseTime = $("#month #day_grid #day"+day_index+" .weather_list .sun_up_time").html().substring(0,5);
        todaySunriseTime = trim(todaySunriseTime);
        if (todaySunriseTime.length == 4) {
            todaySunriseTime = "0" + todaySunriseTime;
        }
        todaySundownTime = $("#month #day_grid #day"+day_index+" .weather_list .sun_down_time").html().substring(0,5);
        renderOneDayWeather(yyyy,mm,dd);
    }
    else if ($('#list').length == 1)
    {
        var tempDate = new Date();
        tempDate.setFullYear(first_date.getFullYear());
        tempDate.setMonth(first_date.getMonth());
        tempDate.setDate(first_date.getDate()+day_index);
        var yyyy = tempDate.getFullYear();
        var mm = tempDate.getMonth()+1;
        if (mm < 10)
            mm = "0"+mm;
        var dd = tempDate.getDate();
        if (dd < 10)
            dd = "0"+dd;
        dayIndex = day_index;
        todayWeatherDesc = $("#list #weather_slider .weather_detail_list :eq("+day_index+") .weather_desc .desc").html();
        todayWeatherIcon = getWeatherIconFromName(todayWeatherDesc);
        var temperature = $("#list #weather_slider .weather_detail_list :eq("+day_index+") .weather_desc .temperature").html();
        var first_t_char = temperature.indexOf("℃");
        var t_char = temperature.indexOf("~");
        var last_t_char = temperature.lastIndexOf("℃");
        todayMinTemperature = temperature.substring(0,first_t_char);
        todayMaxTemperature = temperature.substring(t_char+1,last_t_char);
        todaySunriseTime = $("#list #weather_slider .weather_detail_list :eq("+day_index+") .sunrise").html().substring(0,5);
        todaySundownTime = $("#list #weather_slider .weather_detail_list :eq("+day_index+") .sunset").html().substring(0,5);
        renderOneDayWeather(yyyy,mm,dd);
    }
}

//删除左右两端的空格
function trim(str)
{ 
   return str.replace(/(^\s*)|(\s*$)/g, "");
}

function renderOneDayWeather(yyyy,mm,dd)
{
    var str = "<div id=\"edit_delete_weather\" class=\"dialog\">"+"\n";
    str = str + "<div class=\"header\">"+"\n";
    str = str + "<div class=\"title\">天气详情</div>"+"\n";
    str = str +	"<div class=\"close cancle\">×</div>"+"\n";
    str = str + "</div>"+"\n";

    str = str +	"<div class=\"body\">"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div  class=\"date\">"+yyyy+"年"+mm+"月"+dd+"日</div>"+"\n";
    str = str + "<input type=\"hidden\" class=\"weather_day\" value=\""+ yyyy+mm+dd +"\" />"+"\n";

    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">天气类型：</div>"+"\n";
    str = str + "<select id=\"weather_type\">"+"\n";
    var todayWeatherType = getCssFromWeatherIcon(todayWeatherIcon);
    for (var i=0;i<weatherList.length;i++)
    {
        if (weatherList[i].code == todayWeatherType)
            str = str + "<option value=\""+ weatherList[i].code+"\" selected=\"selected\">"+ weatherList[i].name +"</option> "+"\n";
        else
            str = str + "<option value=\""+ weatherList[i].code+"\">"+ weatherList[i].name +"</option> "+"\n";
    }
    str = str + "</select>"+"\n"
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";

    str = str + "<div class=\"normal_text\">最低气温：</div>"+"\n";
    str = str + "<input type=\"input\" id=\"min_temperature\" class=\"date_input\" value=\""+ todayMinTemperature +"\" />"+"\n";
    str = str + "<div class=\"normal_text has_margin_left\">最高气温：</div>"+"\n";
    str = str + "<input type=\"input\" id=\"max_temperature\" class=\"date_input\" value=\"" + todayMaxTemperature+"\" />"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">日出时间：</div>"+"\n";
    str = str + "<div id=\"datetimepicker17\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"hh:mm\" type=\"input\" id=\"sunrise_time\" class=\"date_input\" value=\""+todaySunriseTime +"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<div class=\"normal_text has_margin_left\">日落时间：</div>"+"\n";
    str = str + "<div id=\"datetimepicker18\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"hh:mm\" type=\"input\" id=\"sundown_time\" class=\"date_input\" value=\"" + todaySundownTime+"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "</div>"+"\n";

    str = str + "<div class=\"footer\">"+"\n";
    str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
    str = str + "<div class=\"button_group\">"+"\n";
    str = str + "<div class=\"button cancle\">取消</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:saveOneDayWeather();\">保存</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str +	"</div>"+"\n";

    str = str +	"</div>"+"\n";

    $('#mask').html('');
    $('#mask').html(str);

    $('#edit_delete_weather').css("width","650px");
    $('#edit_delete_weather').css("height","420px");
    $('#edit_delete_weather').css("margin-left","625px");
    $('#edit_delete_weather').css("margin-top","300px");
    $('#edit_delete_weather >.body').css('height','320px');
    $('#edit_delete_weather >.body').css('width','550px');

    $('#mask .cancle').on('click',function(event){
        $('#mask').html('');
        $('#mask').hide();
    });

    $("#datetimepicker17, #datetimepicker18").datetimepicker({
        pickDate: false,
        pickSeconds: false
    });

    $(".dialog").draggable({cancel:".body"});

    $('#mask').show();
}

function saveOneDayWeather()
{
    var weather_day = $(".dialog .body .weather_day").val();
    var weather_type = $(".dialog .body #weather_type").val();
    var weather_icon = getWeatherIconFromCss(weather_type);
    
    var weather_desc = $(".dialog .body #weather_type option:selected").text();
    if(typeof(weather_desc) == "undefined")
        weather_desc = "";
    
    var weather = "1";
    if(weather_desc.contains("晴天"))
      weather="1";
    else
      weather="0";
     
    var min_temperature = $(".dialog .body #min_temperature").val();
    if (! isNumber(Math.abs(min_temperature)))
    {
        showMessageInDialog('最低气温必须为数字，单位℃','error');
        return;
    }

    var max_temperature = $(".dialog .body #max_temperature").val();
    if (! isNumber(Math.abs(max_temperature)))
    {
        showMessageInDialog('最高气温必须为数字，单位℃','error');
        return;
    }

    var sunrise_time = $(".dialog .body #sunrise_time").val();
    if (! isTime(sunrise_time))
    {
        showMessageInDialog('日出时间格式应为HH:MM','error');
        return;
    }
   //  var t_sunrise_time = formateTime(sunrise_time);

    var sundown_time = $(".dialog .body #sundown_time").val();
    if (! isTime(sundown_time))
    {
        showMessageInDialog('日落时间格式应为HH:MM','error');
        return;
    }
    //  var t_sundown_time = formateTime(sundown_time);

    var url = baseurl + '/calendar/saveWeather';

    var params ={startDate:weather_day,endDate:weather_day,weather:weather,weatherIcon:weather_icon,weatherDescription:weather_desc,minTemperature:min_temperature,maxTemperatue:max_temperature,sunrise:sunrise_time,sunset:sundown_time};
    $.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){
            if (data.success == true)
            {
                showMessageInDialog('天气信息保存成功!','tip');
                resetWeather(weather_desc,weather_type,weather_icon,min_temperature,max_temperature,sunrise_time,sundown_time);
            }
            else
            {
                showMessageInDialog('天气信息保存失败,请稍后再试!','error');
            }
            var t=setTimeout('hideDialog()',1500);
        }
        }
    );
}

function resetWeather(weather_desc,weather_type,weather_icon,min_temperature,max_temperature,sunrise_time,sundown_time)
{
    if ($('#day').length ==1)
    {
        resetWeatherInDayView(weather_desc,weather_type,weather_icon,min_temperature,max_temperature,sunrise_time,sundown_time);
    }
    else if ($('#week').length ==1)
    {
        resetWeatherInWeekView(weather_desc,weather_type,weather_icon,min_temperature,max_temperature,sunrise_time,sundown_time);
    }
    else if ($('#month').length ==1)
    {
        resetWeatherInMonthView(weather_desc,weather_type,weather_icon,min_temperature,max_temperature,sunrise_time,sundown_time);
    }
    else if ($('#list').length ==1)
    {
        resetWeatherInListView(weather_desc,weather_type,weather_icon,min_temperature,max_temperature,sunrise_time,sundown_time);
    }
}

function resetWeatherInDayView(weather_desc,weather_type,weather_icon,min_temperature,max_temperature,sunrise_time,sundown_time)
{
    w_name = weather_desc + " , " + min_temperature+"℃ - " + max_temperature+"℃ 日出 "+sunrise_time+" AM , 日落 " + sundown_time+" PM" ;
    $("#today_weather >span").html(w_name);
    todayWeatherDesc = weather_desc;
    todayMinTemperature = min_temperature;
    todayMaxTemperature = max_temperature;
    todaySunriseTime = sunrise_time;
    todaySundownTime = sundown_time;
    todayWeatherIcon = weather_icon;
                
    var sunrise_h = sunrise_time.substring(0,2);
    var sunrise_m = sunrise_time.substring(3,5);
    var sunset_h = sundown_time.substring(0,2);
    var sunset_m = sundown_time.substring(3,5);
    resetDaySunUpClockLine(sunrise_h,sunrise_m);
    resetDaySunDownClockLine(sunset_h,sunset_m);
}

function resetWeatherInWeekView(weather_desc,weather_type,weather_icon,min_temperature,max_temperature,sunrise_time,sundown_time)
{
    $("#week #weather_slider .weather_for_oneday :eq("+dayIndex+") .weather_desc").html(weather_desc);
    $("#week #weather_slider .weather_for_oneday :eq("+dayIndex+") .temperature").html(min_temperature+"℃~"+max_temperature+"℃");
    $("#week #weather_slider .weather_for_oneday :eq("+dayIndex+") .sun_up_time").html(sunrise_time+" AM");
    $("#week #weather_slider .weather_for_oneday :eq("+dayIndex+") .sun_down_time").html(sundown_time+" PM");
    $("#week #weather_slider .weather_label_normal :eq("+dayIndex+")").attr("class","weather_label_normal");
    $("#week #weather_slider .weather_label_normal :eq("+dayIndex+")").addClass(weather_type);
    //todayWeatherIcon = weather_icon;
}

function resetWeatherInMonthView(weather_desc,weather_type,weather_icon,min_temperature,max_temperature,sunrise_time,sundown_time)
{
    $("#month #day_grid #day"+dayIndex+" .weather_list .weather_desc").html(weather_desc);
    $("#month #day_grid #day"+dayIndex+" .weather_list .temperature").html(min_temperature+"℃~"+max_temperature+"℃");
    $("#month #day_grid #day"+dayIndex+" .weather_list .sun_up_time").html(sunrise_time+" AM");
    $("#month #day_grid #day"+dayIndex+" .weather_list .sun_down_time").html(sundown_time+" PM");
    $("#month #day_grid #day"+dayIndex+" .weather_list .weather_icon").attr("class","weather_icon")
    $("#month #day_grid #day"+dayIndex+" .weather_list .weather_icon").addClass(weather_type);
}

function resetWeatherInListView(weather_desc,weather_type,weather_icon,min_temperature,max_temperature,sunrise_time,sundown_time)
{
    $("#list #weather_slider .weather_detail_list :eq("+dayIndex+") .weather_desc .desc").html(weather_desc);
    $("#list #weather_slider .weather_detail_list :eq("+dayIndex+") .weather_desc .temperature").html(min_temperature+"℃~"+max_temperature+"℃");
    $("#list #weather_slider .weather_detail_list :eq("+dayIndex+") .sunrise").html(sunrise_time+" AM");
    $("#list #weather_slider .weather_detail_list :eq("+dayIndex+") .sunset").html(sundown_time+" PM");
    $("#list #weather_slider .weather_detail_list :eq("+dayIndex+") .weather_label_normal").attr("class","weather_label_normal");
    $("#list #weather_slider .weather_detail_list :eq("+dayIndex+") .weather_label_normal").addClass(weather_type);
}

function resetWeatherForManyDay(startDate,endDate,weather_desc,weather_type,weather_icon,min_temperature,max_temperature,sunrise_time,sundown_time)
{
    if ($('#day').length ==1)
    {
      if ((selected_yyyy+""+selected_mm+""+selected_dd >=startDate)&&(selected_yyyy+selected_mm+selected_dd <=endDate))  
        resetWeatherInDayView(weather_desc,weather_type,weather_icon,min_temperature,max_temperature,sunrise_time,sundown_time);
    }
    else if ($('#week').length ==1)
    {
        var tempday = new Date();
        tempday.setFullYear(first_date.getFullYear());
        tempday.setMonth(first_date.getMonth());
        tempday.setDate(first_date.getDate());
        tempday.setHours(0);
        tempday.setMinutes(0);
        tempday.setSeconds(0);
        tempday.setMilliseconds(0); 
        var day_index = 0;
        while(tempday <=last_date)
        {
        	var t_month = tempday.getMonth()+1;
          if (t_month < 10)
            t_month = "0"+t_month;
        
          var t_day = tempday.getDate();
          if (t_day < 10)
            t_day = "0"+t_day;
        	
        	var t_d = tempday.getFullYear()+""+t_month+""+t_day;
        	if ((t_d>=startDate)&&(t_d<=endDate))
        	{
        		$("#week #weather_slider .weather_for_oneday :eq("+day_index+") .weather_desc").html(weather_desc);
            $("#week #weather_slider .weather_for_oneday :eq("+day_index+") .temperature").html(min_temperature+"℃~"+max_temperature+"℃");
            $("#week #weather_slider .weather_for_oneday :eq("+day_index+") .sun_up_time").html(sunrise_time+" AM");
            $("#week #weather_slider .weather_for_oneday :eq("+day_index+") .sun_down_time").html(sundown_time+" PM");
            $("#week #weather_slider .weather_label_normal :eq("+day_index+")").attr("class","weather_label_normal");
            $("#week #weather_slider .weather_label_normal :eq("+day_index+")").addClass(weather_type);
        	}
        	tempday.setDate(tempday.getDate()+1);
        	day_index = day_index +1;
        }
    }
    else if ($('#month').length ==1)
    {
        var tempday = new Date();
        tempday.setFullYear(parseInt(first_day.substr(0,4)));
        tempday.setMonth(parseInt(first_day.substr(4,2))-1);
        tempday.setDate(parseInt(first_day.substr(6,2)));
        tempday.setHours(0);
        tempday.setMinutes(0);
        tempday.setSeconds(0);
        tempday.setMilliseconds(0);
        var day_index = 1;
        var l_date = new Date();
        l_date.setFullYear(parseInt(last_day.substr(0,4)));
        l_date.setMonth(parseInt(last_day.substr(4,2))-1);
        l_date.setDate(parseInt(last_day.substr(6,2)));
        l_date.setHours(23);
        l_date.setMinutes(59);
        l_date.setSeconds(59);
        l_date.setMilliseconds(999);
        while(tempday <=l_date)
        {
        	var t_month = tempday.getMonth()+1;
          if (t_month < 10)
            t_month = "0"+t_month;
        
          var t_day = tempday.getDate();
          if (t_day < 10)
            t_day = "0"+t_day;
        	
        	var t_d = tempday.getFullYear()+""+t_month+""+t_day;
        	if ((t_d>=startDate)&&(t_d<=endDate))
        	{
        		$("#month #day_grid #day"+day_index+" .weather_list .weather_desc").html(weather_desc);
            $("#month #day_grid #day"+day_index+" .weather_list .temperature").html(min_temperature+"℃~"+max_temperature+"℃");
            $("#month #day_grid #day"+day_index+" .weather_list .sun_up_time").html(sunrise_time+" AM");
            $("#month #day_grid #day"+day_index+" .weather_list .sun_down_time").html(sundown_time+" PM");
            $("#month #day_grid #day"+day_index+" .weather_list .weather_icon").attr("class","weather_icon")
            $("#month #day_grid #day"+day_index+" .weather_list .weather_icon").addClass(weather_type);
        	}
        	tempday.setDate(tempday.getDate()+1);
        	day_index = day_index +1;
        }
    }
    else if ($('#list').length ==1)
    {
        var tempday = new Date();
        tempday.setFullYear(first_date.getFullYear());
        tempday.setMonth(first_date.getMonth());
        tempday.setDate(first_date.getDate())
        tempday.setHours(0);
        tempday.setMinutes(0);
        tempday.setSeconds(0);
        tempday.setMilliseconds(0);
        var day_index = 0;
        while(tempday <=last_date)
        {
        	var t_month = tempday.getMonth()+1;
          if (t_month < 10)
            t_month = "0"+t_month;
        
          var t_day = tempday.getDate();
          if (t_day < 10)
            t_day = "0"+t_day;
        	
        	var t_d = tempday.getFullYear()+""+t_month+""+t_day;
        	if ((t_d>=startDate)&&(t_d<=endDate))
        	{
        		$("#list #weather_slider .weather_detail_list :eq("+day_index+") .weather_desc .desc").html(weather_desc);
            $("#list #weather_slider .weather_detail_list :eq("+day_index+") .weather_desc .temperature").html(min_temperature+"℃~"+max_temperature+"℃");
            $("#list #weather_slider .weather_detail_list :eq("+day_index+") .sunrise").html(sunrise_time+" AM");
            $("#list #weather_slider .weather_detail_list :eq("+day_index+") .sunset").html(sundown_time+" PM");
            $("#list #weather_slider .weather_detail_list :eq("+day_index+") .weather_label_normal").attr("class","weather_label_normal");
            $("#list #weather_slider .weather_detail_list :eq("+day_index+") .weather_label_normal").addClass(weather_type);
        	}
        	tempday.setDate(tempday.getDate()+1);
        	day_index = day_index +1;
        }
    }
}

function resetHoliday(startDate,endDate,holiday_name)
{
    if ($('#week').length ==1)
    {
    	  var tempday = new Date();
        tempday.setFullYear(first_date.getFullYear());
        tempday.setMonth(first_date.getMonth());
        tempday.setDate(first_date.getDate());
        tempday.setHours(0);
        tempday.setMinutes(0);
        tempday.setSeconds(0);
        tempday.setMilliseconds(0); 
        var day_index = 0;
        var current_holiday = "";
        while(tempday <=last_date)
        {
        	var t_month = tempday.getMonth()+1;
          if (t_month < 10)
            t_month = "0"+t_month;
        
          var t_day = tempday.getDate();
          if (t_day < 10)
            t_day = "0"+t_day;
        	
        	var t_d = tempday.getFullYear()+""+t_month+""+t_day;
        	if ((t_d>=startDate)&&(t_d<=endDate))
        	{
        		current_holiday = $("#week #weather_slider .weather_for_oneday :eq("+day_index+") .holiday_desc").html();
        		current_holiday = current_holiday + " " +holiday_name;
        		$("#week #weather_slider .weather_for_oneday :eq("+day_index+") .holiday_desc").html(current_holiday);
            $("#week #weather_slider .weather_for_oneday :eq("+day_index+") .holiday_desc").show();
        	}
        	tempday.setDate(tempday.getDate()+1);
        	day_index = day_index +1;
        }
    }
    else if ($('#month').length ==1)
    {
        var tempday = new Date();
        tempday.setFullYear(parseInt(first_day.substr(0,4)));
        tempday.setMonth(parseInt(first_day.substr(4,2))-1);
        tempday.setDate(parseInt(first_day.substr(6,2)));
        tempday.setHours(0);
        tempday.setMinutes(0);
        tempday.setSeconds(0);
        tempday.setMilliseconds(0);
        var day_index = 1;
        var current_holiday = "";
        var l_date = new Date();
        l_date.setFullYear(parseInt(last_day.substr(0,4)));
        l_date.setMonth(parseInt(last_day.substr(4,2))-1);
        l_date.setDate(parseInt(last_day.substr(6,2)));
        l_date.setHours(23);
        l_date.setMinutes(59);
        l_date.setSeconds(59);
        l_date.setMilliseconds(999);
        while(tempday <=l_date)
        {
        	var t_month = tempday.getMonth()+1;
          if (t_month < 10)
            t_month = "0"+t_month;
        
          var t_day = tempday.getDate();
          if (t_day < 10)
            t_day = "0"+t_day;
        	
        	var t_d = tempday.getFullYear()+""+t_month+""+t_day;
        	if ((t_d>=startDate)&&(t_d<=endDate))
        	{
        		current_holiday = $("#month #day_grid #day"+day_index+" .date_holiday .holiday").html();
        		current_holiday = current_holiday + " " +holiday_name;
        		$("#month #day_grid #day"+day_index+" .date_holiday .holiday").html(current_holiday);
            $("#month #day_grid #day"+day_index+" .date_holiday .holiday").show();
          }
        	tempday.setDate(tempday.getDate()+1);
        	day_index = day_index +1;
        }
    }
    else if ($('#list').length ==1)
    {
        var tempday = new Date();
        tempday.setFullYear(first_date.getFullYear());
        tempday.setMonth(first_date.getMonth());
        tempday.setDate(first_date.getDate())
        tempday.setHours(0);
        tempday.setMinutes(0);
        tempday.setSeconds(0);
        tempday.setMilliseconds(0);
        var day_index = 0;
        var current_holiday = "";
        while(tempday <=last_date)
        {
        	var t_month = tempday.getMonth()+1;
          if (t_month < 10)
            t_month = "0"+t_month;
        
          var t_day = tempday.getDate();
          if (t_day < 10)
            t_day = "0"+t_day;
        	
        	var t_d = tempday.getFullYear()+""+t_month+""+t_day;
        	if ((t_d>=startDate)&&(t_d<=endDate))
        	{
        		current_holiday = $("#list #weather_slider .weather_detail_list :eq("+day_index+") .holiday .holiday_desc").html();
        		current_holiday = current_holiday + " " +holiday_name;
        		$("#list #weather_slider .weather_detail_list :eq("+day_index+") .holiday .holiday_desc").html(current_holiday);
            $("#list #weather_slider .weather_detail_list :eq("+day_index+") .holiday .holiday_desc").show();
          }
        	tempday.setDate(tempday.getDate()+1);
        	day_index = day_index +1;
        }
    }
}

function deleteHolidayInCurrentView(startDate,endDate)
{
	  if ($('#week').length ==1)
    {
    	  var tempday = new Date();
        tempday.setFullYear(first_date.getFullYear());
        tempday.setMonth(first_date.getMonth());
        tempday.setDate(first_date.getDate());
        tempday.setHours(0);
        tempday.setMinutes(0);
        tempday.setSeconds(0);
        tempday.setMilliseconds(0); 
        var day_index = 0;
        var current_holiday = "";
        while(tempday <=last_date)
        {
        	var t_month = tempday.getMonth()+1;
          if (t_month < 10)
            t_month = "0"+t_month;
        
          var t_day = tempday.getDate();
          if (t_day < 10)
            t_day = "0"+t_day;
        	
        	var t_d = tempday.getFullYear()+""+t_month+""+t_day;
        	if ((t_d>=startDate)&&(t_d<=endDate))
        	{
        		current_holiday = $("#week #weather_slider .weather_for_oneday :eq("+day_index+") .holiday_desc").html();
        		if (current_holiday.indexOf("今日") !=-1)
        		{
        			current_holiday = "今日";
        			$("#week #weather_slider .weather_for_oneday :eq("+day_index+") .holiday_desc").html(current_holiday);
        		}
        		else
        		{
        			current_holiday = "";
        			$("#week #weather_slider .weather_for_oneday :eq("+day_index+") .holiday_desc").hide();
        		}
         	}
        	tempday.setDate(tempday.getDate()+1);
        	day_index = day_index +1;
        }
    }
    else if ($('#month').length ==1)
    {
        var tempday = new Date();
        tempday.setFullYear(parseInt(first_day.substr(0,4)));
        tempday.setMonth(parseInt(first_day.substr(4,2))-1);
        tempday.setDate(parseInt(first_day.substr(6,2)));
        tempday.setHours(0);
        tempday.setMinutes(0);
        tempday.setSeconds(0);
        tempday.setMilliseconds(0);
        var day_index = 1;
        var current_holiday = "";
        var l_date = new Date();
        l_date.setFullYear(parseInt(last_day.substr(0,4)));
        l_date.setMonth(parseInt(last_day.substr(4,2))-1);
        l_date.setDate(parseInt(last_day.substr(6,2)));
        l_date.setHours(23);
        l_date.setMinutes(59);
        l_date.setSeconds(59);
        l_date.setMilliseconds(999);
        while(tempday <=l_date)
        {
        	var t_month = tempday.getMonth()+1;
          if (t_month < 10)
            t_month = "0"+t_month;
        
          var t_day = tempday.getDate();
          if (t_day < 10)
            t_day = "0"+t_day;
        	
        	var t_d = tempday.getFullYear()+""+t_month+""+t_day;
        	if ((t_d>=startDate)&&(t_d<=endDate))
        	{
        		$("#month #day_grid #day"+day_index+" .date_holiday .holiday").html("");
        		$("#month #day_grid #day"+day_index+" .date_holiday .holiday").hide();
        	}
        	tempday.setDate(tempday.getDate()+1);
        	day_index = day_index +1;
        }
    }
    else if ($('#list').length ==1)
    {
        var tempday = new Date();
        tempday.setFullYear(first_date.getFullYear());
        tempday.setMonth(first_date.getMonth());
        tempday.setDate(first_date.getDate())
        tempday.setHours(0);
        tempday.setMinutes(0);
        tempday.setSeconds(0);
        tempday.setMilliseconds(0);
        var day_index = 0;
        var current_holiday = "";
        while(tempday <=last_date)
        {
        	var t_month = tempday.getMonth()+1;
          if (t_month < 10)
            t_month = "0"+t_month;
        
          var t_day = tempday.getDate();
          if (t_day < 10)
            t_day = "0"+t_day;
        	
        	var t_d = tempday.getFullYear()+""+t_month+""+t_day;
        	if ((t_d>=startDate)&&(t_d<=endDate))
        	{
        		$("#list #weather_slider .weather_detail_list :eq("+day_index+") .holiday .holiday_desc").html("");
            $("#list #weather_slider .weather_detail_list :eq("+day_index+") .holiday .holiday_desc").hide();
          }
        	tempday.setDate(tempday.getDate()+1);
        	day_index = day_index +1;
        }
    }
}

function addActivityInMonthView(startDate,endDate,activity_name)
{
	  var tempday = new Date();
    tempday.setFullYear(parseInt(first_day.substr(0,4)));
    tempday.setMonth(parseInt(first_day.substr(4,2))-1);
    tempday.setDate(parseInt(first_day.substr(6,2)));
    tempday.setHours(0);
    tempday.setMinutes(0);
    tempday.setSeconds(0);
    tempday.setMilliseconds(0);
    var day_index = 1;
    var current_activity = "";
    var l_date = new Date();
    l_date.setFullYear(parseInt(last_day.substr(0,4)));
    l_date.setMonth(parseInt(last_day.substr(4,2))-1);
    l_date.setDate(parseInt(last_day.substr(6,2)));
    l_date.setHours(23);
    l_date.setMinutes(59);
    l_date.setSeconds(59);
    l_date.setMilliseconds(999);
    while(tempday <=l_date)
    {
     	 var t_month = tempday.getMonth()+1;
       if (t_month < 10)
          t_month = "0"+t_month;
        
       var t_day = tempday.getDate();
       if (t_day < 10)
         t_day = "0"+t_day;
        	
       var t_d = tempday.getFullYear()+""+t_month+""+t_day;
       if ((t_d>=startDate)&&(t_d<=endDate))
       {
       	 current_activity = $("#month #day_grid #day"+day_index+" .mode_activity .activity .activity_name").html();
       	 if (current_activity=="")
       	   current_activity = activity_name;
       	 else   
       	   current_activity = current_activity +  ";" +activity_name;
         $("#month #day_grid #day"+day_index+" .mode_activity .activity .activity_name").html(current_activity);
         $("#month #day_grid #day"+day_index+" .mode_activity .activity").attr("onclick",'javascript:showOneDayActivity('+t_d+')');
       }
       tempday.setDate(tempday.getDate()+1);
       day_index = day_index +1;
    }
}

function deleteActivityInMonthView(startDate,endDate,activity_name)
{
	  var tempday = new Date();
    tempday.setFullYear(parseInt(first_day.substr(0,4)));
    tempday.setMonth(parseInt(first_day.substr(4,2))-1);
    tempday.setDate(parseInt(first_day.substr(6,2)));
    tempday.setHours(0);
    tempday.setMinutes(0);
    tempday.setSeconds(0);
    tempday.setMilliseconds(0);
    var day_index = 1;
    var current_activity = "";
    var current_activity_ar = null;
    var l_date = new Date();
    l_date.setFullYear(parseInt(last_day.substr(0,4)));
    l_date.setMonth(parseInt(last_day.substr(4,2))-1);
    l_date.setDate(parseInt(last_day.substr(6,2)));
    l_date.setHours(23);
    l_date.setMinutes(59);
    l_date.setSeconds(59);
    l_date.setMilliseconds(999);
    while(tempday <=l_date)
    {
     	 var t_month = tempday.getMonth()+1;
       if (t_month < 10)
          t_month = "0"+t_month;
        
       var t_day = tempday.getDate();
       if (t_day < 10)
         t_day = "0"+t_day;
        	
       var t_d = tempday.getFullYear()+""+t_month+""+t_day;
       if ((t_d>=startDate)&&(t_d<=endDate))
       {
       	 current_activity = $("#month #day_grid #day"+day_index+" .mode_activity .activity .activity_name").html();
       	 current_activity_ar = current_activity.split(';');
       	 for (var j=0;j<current_activity_ar.length;j++)
       	 {
       	 	 if (current_activity_ar[j] == activity_name)
       	 	    current_activity_ar.splice(j,1);
         }
         if (current_activity_ar.length>0)
         {
           current_activity =  current_activity_ar.join(';');
           $("#month #day_grid #day"+day_index+" .mode_activity .activity .activity_name").html(current_activity);
         }
         else
         {
           $("#month #day_grid #day"+day_index+" .mode_activity .activity .activity_name").html('');
           $("#month #day_grid #day"+day_index+" .mode_activity .activity").attr('onclick',"javascript:showCreateActivityDialog()");	
         }	
       }
       tempday.setDate(tempday.getDate()+1);
       day_index = day_index +1;
    }
}

function deleteAllActivityInMonthView(startDate,endDate)
{
	  var tempday = new Date();
    tempday.setFullYear(parseInt(first_day.substr(0,4)));
    tempday.setMonth(parseInt(first_day.substr(4,2))-1);
    tempday.setDate(parseInt(first_day.substr(6,2)));
    tempday.setHours(0);
    tempday.setMinutes(0);
    tempday.setSeconds(0);
    tempday.setMilliseconds(0);
    var day_index = 1;
    var current_activity = "";
    var current_activity_ar = null;
    var l_date = new Date();
    l_date.setFullYear(parseInt(last_day.substr(0,4)));
    l_date.setMonth(parseInt(last_day.substr(4,2))-1);
    l_date.setDate(parseInt(last_day.substr(6,2)));
    l_date.setHours(23);
    l_date.setMinutes(59);
    l_date.setSeconds(59);
    l_date.setMilliseconds(999);
    while(tempday <=l_date)
    {
     	 var t_month = tempday.getMonth()+1;
       if (t_month < 10)
          t_month = "0"+t_month;
        
       var t_day = tempday.getDate();
       if (t_day < 10)
         t_day = "0"+t_day;
        	
       var t_d = tempday.getFullYear()+""+t_month+""+t_day;
       if ((t_d>=startDate)&&(t_d<=endDate))
       {
       	 $("#month #day_grid #day"+day_index+" .mode_activity .activity .activity_name").html('');
       	 $("#month #day_grid #day"+day_index+" .mode_activity .activity").attr('onclick',"javascript:showCreateActivityDialog()");	
       }
       tempday.setDate(tempday.getDate()+1);
       day_index = day_index +1;
    }
}

function modifyActivityInMonthView(startDate,endDate,activity_name,old_startDate,old_endDate,old_activity_name)
{
	  var tempday = new Date();
    tempday.setFullYear(parseInt(first_day.substr(0,4)));
    tempday.setMonth(parseInt(first_day.substr(4,2))-1);
    tempday.setDate(parseInt(first_day.substr(6,2)));
    tempday.setHours(0);
    tempday.setMinutes(0);
    tempday.setSeconds(0);
    tempday.setMilliseconds(0);
    var day_index = 1;
    var current_activity = "";
    var current_activity_ar = null;
    var l_date = new Date();
    l_date.setFullYear(parseInt(last_day.substr(0,4)));
    l_date.setMonth(parseInt(last_day.substr(4,2))-1);
    l_date.setDate(parseInt(last_day.substr(6,2)));
    l_date.setHours(23);
    l_date.setMinutes(59);
    l_date.setSeconds(59);
    l_date.setMilliseconds(999);
    while(tempday <=l_date)
    {
     	 var t_month = tempday.getMonth()+1;
       if (t_month < 10)
          t_month = "0"+t_month;
        
       var t_day = tempday.getDate();
       if (t_day < 10)
         t_day = "0"+t_day;
        	
       var t_d = tempday.getFullYear()+""+t_month+""+t_day;
       //不在原有日期范围，也不在新的日期范围，什么也不做
       if (((t_d<startDate)||(t_d>endDate))&&((t_d<old_startDate)||(t_d>old_endDate)))
       {
       	  
       }
       //如果在原有日期范围，但不在新的日期范围，如果找到原有的活动就删除
       else if (((t_d<startDate)||(t_d>endDate))&&((t_d>=old_startDate)&&(t_d<=old_endDate)))
       {
       	  current_activity = $("#month #day_grid #day"+day_index+" .mode_activity .activity .activity_name").html();
       	  current_activity_ar = current_activity.split(';');
       	  for (var j=0;j<current_activity_ar.length;j++)
       	  {
       	 	  if (current_activity_ar[j] == old_activity_name)
       	 	  {
       	 	     current_activity_ar.splice(j,1);
       	 	  }
       	  }
       	  current_activity =  current_activity_ar.join(';');
          $("#month #day_grid #day"+day_index+" .mode_activity .activity .activity_name").html(current_activity);
          //删除之后，发现没有任何一个活动了
          if (current_activity_ar.length ==0)
          {
            $("#month #day_grid #day"+day_index+" .mode_activity .activity .activity_name").html('');
            $("#month #day_grid #day"+day_index+" .mode_activity .activity").attr('onclick',"javascript:showCreateActivityDialog()");	
          }
       }
       //如果不在原有日期范围，但在新的日期范围，就要添加这个新的活动	
       else if (((t_d>=startDate)&&(t_d<=endDate))&&((t_d<old_startDate)||(t_d>old_endDate)))
       {
       	 current_activity = $("#month #day_grid #day"+day_index+" .mode_activity .activity .activity_name").html();
       	 if (current_activity =="")
       	   current_activity = activity_name;
       	 else
       	 {  
       	   current_activity_ar = current_activity.split(';');
       	   current_activity_ar.push(activity_name);
       	   current_activity =  current_activity_ar.join(';');
         }
         $("#month #day_grid #day"+day_index+" .mode_activity .activity .activity_name").html(current_activity);
         $("#month #day_grid #day"+day_index+" .mode_activity .activity").attr("onclick",'javascript:showOneDayActivity('+t_d+')');
       }
       //如果在原有日期范围，也在新的日期范围，如果找到原有的活动，就要将原有的活动修改为新的活动	
       else if (((t_d>=startDate)&&(t_d<=endDate))&&((t_d>=old_startDate)&&(t_d<=old_endDate)))
       {	
       	 current_activity = $("#month #day_grid #day"+day_index+" .mode_activity .activity .activity_name").html();
       	 current_activity_ar = current_activity.split(';');
       	 for (var j=0;j<current_activity_ar.length;j++)
       	 {
       	 	 if (current_activity_ar[j] == old_activity_name)
       	 	 {
       	 	    current_activity_ar.splice(j,1,activity_name);
       	 	    break;  //找到一个修改后，就退出循环，避免把其它同名的活动也修改了
       	 	 }  
       	 }
       	 current_activity =  current_activity_ar.join(';');
         $("#month #day_grid #day"+day_index+" .mode_activity .activity .activity_name").html(current_activity);
       }  
            
       tempday.setDate(tempday.getDate()+1);
       day_index = day_index +1;
    }
}

function addActivityInListView(startDate,endDate,startTime,endTime,all_day,activity_name)
{
	  var tempday = new Date();
    tempday.setFullYear(first_date.getFullYear());
    tempday.setMonth(first_date.getMonth());
    tempday.setDate(first_date.getDate());
    tempday.setHours(0);
    tempday.setMinutes(0);
    tempday.setSeconds(0);
    tempday.setMilliseconds(0); 
    var day_index = 0;
    var activity_time = "";
    while(tempday <=last_date)
    {
     	var t_month = tempday.getMonth()+1;
      if (t_month < 10)
        t_month = "0"+t_month;
        
      var t_day = tempday.getDate();
      if (t_day < 10)
        t_day = "0"+t_day;
        	
      var t_d = tempday.getFullYear()+""+t_month+""+t_day;
      if ((t_d>=startDate)&&(t_d<=endDate))
      {
       	 var oneday_activity_li = $("#list #activity_slider > ul >li :eq("+day_index+") >div >ul >li");
       	 for (var k=0;k<oneday_activity_li.length;k++)
       	 {
       	   if (oneday_activity_li.eq(k).find('.activity_name').html()=="")
       	   {
       	     oneday_activity_li.eq(k).find('.activity_name').html(activity_name);
       	     if (all_day == true)
       	     {
       	       oneday_activity_li.eq(k).find('.activity_time').html('全天');
       	       oneday_activity_li.eq(k).find('.activity_time').css('color','#FF9966');
               oneday_activity_li.eq(k).find('.activity_name').css('color','#FF9966');
       	     }
       	     else
       	     	 oneday_activity_li.eq(k).find('.activity_time').html(TimeToString(startTime)+"-"+TimeToString(endTime));
       	     if(t_d ==current_yyyy+""+current_mm+""+current_dd)
       	     {
       	     	  oneday_activity_li.eq(k).css('background-color','#FEC68E');       	     	              
                oneday_activity_li.eq(k).css('border-right','1px solid #ff9900');
                oneday_activity_li.eq(k).find('.activity_time').css('color','#ffffff');
                oneday_activity_li.eq(k).find('.activity_name').css('color','#ffffff');
             }
       	     break;
       	   }  
         }
      }
      tempday.setDate(tempday.getDate()+1);
      day_index = day_index +1;
    }
}

function deleteAllActivityInListView(startDate,endDate)
{
	  var tempday = new Date();
    tempday.setFullYear(first_date.getFullYear());
    tempday.setMonth(first_date.getMonth());
    tempday.setDate(first_date.getDate());
    tempday.setHours(0);
    tempday.setMinutes(0);
    tempday.setSeconds(0);
    tempday.setMilliseconds(0); 
    var day_index = 0;
    var activity_time = "";
    while(tempday <=last_date)
    {
     	var t_month = tempday.getMonth()+1;
      if (t_month < 10)
        t_month = "0"+t_month;
        
      var t_day = tempday.getDate();
      if (t_day < 10)
        t_day = "0"+t_day;
        	
      var t_d = tempday.getFullYear()+""+t_month+""+t_day;
      if ((t_d>=startDate)&&(t_d<=endDate))
      {
       	 var oneday_activity_li = $("#list #activity_slider > ul >li :eq("+day_index+") >div >ul >li");
       	 for (var k=0;k<oneday_activity_li.length;k++)
       	 {
       	     oneday_activity_li.eq(k).find('.activity_name').html('');
       	     oneday_activity_li.eq(k).find('.activity_time').html('');
       	     oneday_activity_li.eq(k).find('.activity_name').removeAttr('style');
       	     oneday_activity_li.eq(k).find('.activity_time').removeAttr('style');
       	     oneday_activity_li.eq(k).removeAttr('style');
       	 }
      }
      tempday.setDate(tempday.getDate()+1);
      day_index = day_index +1;
    }
}

function modifyActivityInListView(startDate,endDate,startTime,endTime,all_day,activity_name,old_startDate,old_endDate,old_activity_name)
{
	  //alert("startDate="+startDate+" endDate="+endDate+" old_startDate="+old_startDate+" old_endDate="+old_endDate+" activity_name"+activity_name+" old_activity_name"+old_activity_name);
	  var tempday = new Date();
    tempday.setFullYear(first_date.getFullYear());
    tempday.setMonth(first_date.getMonth());
    tempday.setDate(first_date.getDate());
    tempday.setHours(0);
    tempday.setMinutes(0);
    tempday.setSeconds(0);
    tempday.setMilliseconds(0); 
    var day_index = 0;
    var current_activity = "";
    var current_activity_ar = null;
    while(tempday <=last_date)
    {
     	 var t_month = tempday.getMonth()+1;
       if (t_month < 10)
          t_month = "0"+t_month;
        
       var t_day = tempday.getDate();
       if (t_day < 10)
         t_day = "0"+t_day;
        	
       var t_d = tempday.getFullYear()+""+t_month+""+t_day;
       var oneday_activity_li = $("#list #activity_slider > ul >li :eq("+day_index+") >div >ul >li");
       //不在原有日期范围，也不在新的日期范围，什么也不做
       if (((t_d<startDate)||(t_d>endDate))&&((t_d<old_startDate)||(t_d>old_endDate)))
       {
       	  
       }
       //如果在原有日期范围，但不在新的日期范围，如果找到原有的活动就删除
       else if (((t_d<startDate)||(t_d>endDate))&&((t_d>=old_startDate)&&(t_d<=old_endDate)))
       {
       	 for (var k=0;k<oneday_activity_li.length;k++)
       	 {
       	     if ( oneday_activity_li.eq(k).find('.activity_name').html() == old_activity_name)
       	     {
       	       oneday_activity_li.eq(k).find('.activity_name').html('');
       	       oneday_activity_li.eq(k).find('.activity_time').html('');
       	       oneday_activity_li.eq(k).find('.activity_name').removeAttr('style');
       	       oneday_activity_li.eq(k).find('.activity_time').removeAttr('style');
       	       oneday_activity_li.eq(k).removeAttr('style');
       	       break;
       	     }  
       	 }
       }
       //如果不在原有日期范围，但在新的日期范围，就要添加这个新的活动	
       else if (((t_d>=startDate)&&(t_d<=endDate))&&((t_d<old_startDate)||(t_d>old_endDate)))
       {
       	 for (var k=0;k<oneday_activity_li.length;k++)
       	 {
       	   if (oneday_activity_li.eq(k).find('.activity_name').html()=="")
       	   {
       	     oneday_activity_li.eq(k).find('.activity_name').html(activity_name);
       	     if (all_day == true)
       	     {
       	       oneday_activity_li.eq(k).find('.activity_time').html('全天');
       	       oneday_activity_li.eq(k).find('.activity_time').css('color','#FF9966');
               oneday_activity_li.eq(k).find('.activity_name').css('color','#FF9966');
       	     }
       	     else
       	     	 oneday_activity_li.eq(k).find('.activity_time').html(TimeToString(startTime)+"-"+TimeToString(endTime));
       	     if(t_d ==current_yyyy+""+current_mm+""+current_dd)
       	     {
       	     	  oneday_activity_li.eq(k).css('background-color','#FEC68E');       	     	              
                oneday_activity_li.eq(k).css('border-right','1px solid #ff9900');
                oneday_activity_li.eq(k).find('.activity_time').css('color','#ffffff');
                oneday_activity_li.eq(k).find('.activity_name').css('color','#ffffff');
             }
       	     break;
       	   }  
         }
       }
       //如果在原有日期范围，也在新的日期范围，如果找到原有的活动，就要将原有的活动修改为新的活动	
       else if (((t_d>=startDate)&&(t_d<=endDate))&&((t_d>=old_startDate)&&(t_d<=old_endDate)))
       {	
       	 for (var k=0;k<oneday_activity_li.length;k++)
       	 {
       	     if (oneday_activity_li.eq(k).find('.activity_name').html() == old_activity_name)
       	     {
       	       oneday_activity_li.eq(k).find('.activity_name').html(activity_name);
       	       if (all_day == true)
       	       {
       	         oneday_activity_li.eq(k).find('.activity_time').html('全天');
       	         oneday_activity_li.eq(k).find('.activity_time').css('color','#FF9966');
                 oneday_activity_li.eq(k).find('.activity_name').css('color','#FF9966');
       	       }
       	       else
       	     	   oneday_activity_li.eq(k).find('.activity_time').html(TimeToString(startTime)+"-"+TimeToString(endTime));
       	     	 break;
       	     }  
       	 }
       }  
            
       tempday.setDate(tempday.getDate()+1);
       day_index = day_index +1;
    }
}

function showDeleteWeatherDialog()
{

}

function showOneDayActivity(date)
{
    var url = baseurl+'/calendar/getCalendarEvent';
    var d = new Date();
    var r = Math.random()+""+d.getMilliseconds();
    if (typeof(date) == "undefined")
        var params ={startDate:selected_yyyy+""+selected_mm+""+selected_dd,endDate:selected_yyyy+""+selected_mm+""+selected_dd,r:r};
    else
    {
        var params ={startDate:date,endDate:date,r:r};
    }

    $.ajax({url:url,type:'GET',dataType:'json',async:false,data:params,success:function(data){
            activityList = data;
        }
        }
    );
    if (activityList.length < 1)
    {
        return;
    }
    var startDate = getDateTime(activityList[0].startDate);
    var endDate = getDateTime(activityList[0].endDate);
    var startDateStr = startDate.year + "-" + startDate.month + "-" +startDate.date;
    var endDateStr = endDate.year + "-" + endDate.month + "-" +endDate.date;
    var old_startDate = startDate.year + "" + startDate.month + "" +startDate.date;
    var old_endDate = endDate.year + "" + endDate.month + "" +endDate.date;
    var startTimeStr = startDate.hour + ":" +startDate.minute;
    var endTimeStr = endDate.hour + ":" + endDate.minute;

    var str = "<div id=\"create_normal_event\" class=\"dialog\">"+"\n";
    str = str + "<div class=\"header\">"+"\n";
    str = str + "<div class=\"title\">对现有活动进行修改</div>"+"\n";
    str = str +	"<div class=\"close cancle\">×</div>"+"\n";
    str = str + "</div>"+"\n";
    str = str +	"<div class=\"body\" style=\"width:60%\">"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<input type=\"hidden\" class=\"activityIndex\" value=\"0\" />"+"\n";
    str = str + "<input type=\"hidden\" class=\"activityId\" value=\""+activityList[0].id+"\" />"+"\n";
    str = str + "<input type=\"hidden\" class=\"old_activity_name\" value=\""+activityList[0].title+"\" />"+"\n";
    str = str + "<input type=\"hidden\" class=\"old_startDate\" value=\""+old_startDate+"\" />"+"\n";
    str = str + "<input type=\"hidden\" class=\"old_endDate\" value=\""+old_endDate+"\" />"+"\n";
    str = str + "<div class=\"normal_text\">开始日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker19\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"start_date\" class=\"date_input\" value=\""+ startDateStr +"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<div class=\"normal_text has_margin_left\">结束日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker20\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"end_date\" class=\"date_input\" value=\"" + endDateStr+"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">开始时间：</div>"+"\n";
    str = str + "<div id=\"datetimepicker21\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"hh:mm\" type=\"input\" id=\"start_time\" class=\"date_input\" value=\""+ startTimeStr +"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<div class=\"normal_text has_margin_left\">结束时间：</div>"+"\n";
    str = str + "<div id=\"datetimepicker22\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"hh:mm\" type=\"input\" id=\"end_time\" class=\"date_input\" value=\"" + endTimeStr+ "\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";

    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">全天活动：</div>"+"\n";
    if (activityList[0].allDay == true)
        str = str + "<input type=\"checkbox\" id=\"all_day\" checked=\"checked\"/>"+"\n";
    else
        str = str + "<input type=\"checkbox\" id=\"all_day\"/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">活动名称：</div>"+"\n";
    str = str + "<input type=\"input\" class=\"activity_name\" value=\""+activityList[0].title +"\" />"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\" style=\"width:80px\">组&nbsp;&nbsp;织&nbsp;者：</div>"+"\n";
    if (activityList[0].organizer==null)
      activityList[0].organizer = "";
    str = str + "<input type=\"input\" class=\"activity_organizer\" value=\""+activityList[0].organizer +"\" />"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">活动地址：</div>"+"\n";
    if (activityList[0].eventAddress==null)
      activityList[0].eventAddress = "";
    str = str + "<input type=\"input\" class=\"activity_address\" value=\""+activityList[0].eventAddress +"\" />"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">活动详情：</div>"+"\n";
    str = str + "<textarea id=\"activity_desc\">"+ activityList[0].description +"</textarea>"+"\n";

    str = str +	"</div>"+"\n";
    str = str + "<div class=\"footer\">"+"\n";
    str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
    str = str + "<div class=\"button_group\" style=\"width:501px\">"+"\n";
    str = str + "<div class=\"button cancle\">取消</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:deleteActivity();\">删除</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:saveActivity();\">保存</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:lastActivity(activityList);\">上一条</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:nextActivity(activityList);\">下一条</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str + "</div>"+"\n";
    $('#mask').html('');
    $('#mask').html(str);

    $('#create_normal_event').css("width","850px");
    $('#create_normal_event').css("height","620px");
    $('#create_normal_event').css("margin-left","625px");
    $('#create_normal_event').css("margin-top","230px");
    $('#create_normal_event >.body').css('height','520px');

    $('#datetimepicker19, #datetimepicker20').datetimepicker({
        pickTime: false,
        language: 'zh-CN',
        pick12HourFormat: true
    });

    $("#datetimepicker21, #datetimepicker22").datetimepicker({
        pickDate: false,
        pickSeconds: false
    });

    $('#mask .cancle').on('click',function(event){
        $('#mask').html('');
        $('#mask').hide();
    });

    $(".dialog").draggable({cancel:".body"});

    $('#mask').show();
}

function showOneWeekActivity()
{
    var f_month = first_date.getMonth()+1;
    if (f_month < 10)
        f_month = "0"+f_month;
    var f_day = first_date.getDate();
    if (f_day < 10)
        f_day = "0"+f_day;
    var l_month = last_date.getMonth()+1;
    if (l_month < 10)
        l_month = "0"+l_month;
    var l_day = last_date.getDate();
    if (l_day < 10)
        l_day = "0"+l_day;
    var url = baseurl+'/calendar/getCalendarEvent';
    var d = new Date();
    var r = Math.random()+""+d.getMilliseconds();

    var params ={startDate:first_date.getFullYear()+""+f_month+""+f_day,endDate:last_date.getFullYear()+""+l_month+""+l_day,r:r};
    $.ajax({url:url,type:'GET',dataType:'json',async:false,data:params,success:function(data){
            activityList = data;
        }
        }
    );
    if (activityList.length < 1)
        return;

    var startDate = getDateTime(activityList[0].startDate);
    var endDate = getDateTime(activityList[0].endDate);
    var startDateStr = startDate.year + "-" + startDate.month + "-" +startDate.date;
    var endDateStr = endDate.year + "-" + endDate.month + "-" +endDate.date;
    var startTimeStr = startDate.hour + ":" +startDate.minute;
    var endTimeStr = endDate.hour + ":" + endDate.minute;

    var str = "<div id=\"create_normal_event\" class=\"dialog\">"+"\n";
    str = str + "<div class=\"header\">"+"\n";
    str = str + "<div class=\"title\">对现有活动进行修改</div>"+"\n";
    str = str +	"<div class=\"close cancle\">×</div>"+"\n";
    str = str + "</div>"+"\n";
    str = str +	"<div class=\"body\" style=\"width:60%\">"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<input type=\"hidden\" class=\"activityIndex\" value=\"0\" />"+"\n";
    str = str + "<input type=\"hidden\" class=\"activityId\" value=\""+activityList[0].id+"\" />"+"\n";
    
    str = str + "<div class=\"normal_text\">开始日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker80\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"start_date\" class=\"date_input\" value=\""+ startDateStr +"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";
    
    //str = str + "<input type=\"input\" id=\"start_date\" class=\"date_input\" value=\""+ startDateStr +"\" />"+"\n";
    
    str = str + "<div class=\"normal_text has_margin_left\">结束日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker81\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"end_date\" class=\"date_input\" value=\"" + endDateStr+"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";
    
    //str = str + "<input type=\"input\" id=\"end_date\" class=\"date_input\" value=\"" + endDateStr+"\" />"+"\n";
    
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">开始时间：</div>"+"\n";
    str = str + "<div id=\"datetimepicker82\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"hh:mm\" type=\"input\" id=\"start_time\" class=\"date_input\" value=\""+ startTimeStr +"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";
    
    //str = str + "<input type=\"input\" id=\"start_time\" class=\"date_input\" value=\""+ startTimeStr +"\" />"+"\n";
    
    str = str + "<div class=\"normal_text has_margin_left\">结束时间：</div>"+"\n";
    str = str + "<div id=\"datetimepicker83\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"hh:mm\" type=\"input\" id=\"end_time\" class=\"date_input\" value=\"" + endTimeStr+ "\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";
    
    //str = str + "<input type=\"input\" id=\"end_time\" class=\"date_input\" value=\"" + endTimeStr+ "\" />"+"\n";
    
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">全天活动：</div>"+"\n";
    if (activityList[0].allDay == true)
        str = str + "<input type=\"checkbox\" id=\"all_day\" checked=\"checked\"/>"+"\n";
    else
        str = str + "<input type=\"checkbox\" id=\"all_day\"/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">活动名称：</div>"+"\n";
    str = str + "<input type=\"input\" class=\"activity_name\" value=\""+activityList[0].title +"\" />"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\" style=\"width:80px\">组&nbsp;&nbsp;织&nbsp;者：</div>"+"\n";
    if (activityList[0].organizer ==null)
      activityList[0].organizer = "";
    str = str + "<input type=\"input\" class=\"activity_organizer\" value=\""+activityList[0].organizer +"\" />"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">活动地址：</div>"+"\n";
    if (activityList[0].eventAddress ==null)
      activityList[0].eventAddress = "";
    str = str + "<input type=\"input\" class=\"activity_address\" value=\""+activityList[0].eventAddress +"\" />"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">活动详情：</div>"+"\n";
    str = str + "<textarea id=\"activity_desc\">"+ activityList[0].description +"</textarea>"+"\n";

    str = str +	"</div>"+"\n";
    str = str + "<div class=\"footer\">"+"\n";
    str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
    str = str + "<div class=\"button_group\" style=\"width:501px\">"+"\n";
    str = str + "<div class=\"button cancle\">取消</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:deleteActivity();\">删除</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:saveActivity();\">保存</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:lastActivity(activityList);\">上一条</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:nextActivity(activityList);\">下一条</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str + "</div>"+"\n";
    $('#mask').html('');
    $('#mask').html(str);

    $('#create_normal_event').css("width","800px");
    $('#create_normal_event').css("height","620px");
    $('#create_normal_event').css("margin-left","560px");
    $('#create_normal_event').css("margin-top","230px");
    $('#create_normal_event >.body').css('height','520px');

    $('#datetimepicker80, #datetimepicker81').datetimepicker({
        pickTime: false,
        language: 'zh-CN',
        pick12HourFormat: true
    });

    $("#datetimepicker82, #datetimepicker83").datetimepicker({
        pickDate: false,
        pickSeconds: false
    });
    
    $('#mask .cancle').on('click',function(event){
        $('#mask').html('');
        $('#mask').hide();
    });

    $(".dialog").draggable({cancel:".body"});

    $('#mask').show();
}
function lastActivity(activityList)
{
    var i = $(".body .activityIndex").val();
    if (i < 1)
    {
        showMessageInDialog('已经是第一条活动了!','tip');
        return;
    }
    else
    {
        i = parseInt(i) -1;
        var id = activityList[i].id;
        var title =  activityList[i].title;
        var desc = activityList[i].description;
        var organizer = activityList[i].organizer;
        var eventAddress = activityList[i].eventAddress;
        var allDay = activityList[i].allDay;
        var startDate = getDateTime(activityList[i].startDate);
        var endDate = getDateTime(activityList[i].endDate);
        var startDateStr = startDate.year + "-" + startDate.month + "-" +startDate.date;
        var endDateStr = endDate.year + "-" + endDate.month + "-" +endDate.date;
        var old_startDate = startDate.year + "" + startDate.month + "" +startDate.date;
        var old_endDate = endDate.year + "" + endDate.month + "" +endDate.date;
        var startTimeStr = startDate.hour + ":" +startDate.minute;
        var endTimeStr = endDate.hour + ":" + endDate.minute;

        $(".body .activityIndex").prop("value",i+"");
        $(".body .activityId").prop("value",id);
        $(".body .old_activity_name").prop("value",title);
        $(".body .old_startDate").prop("value",old_startDate);
        $(".body .old_endDate").prop("value",old_endDate);
        $(".body #start_date").prop("value",startDateStr);
        $(".body #end_date").prop("value",endDateStr);
        $(".body #start_time").prop("value",startTimeStr);
        $(".body #end_time").prop("value",endTimeStr);
        $(".body #all_day").prop("checked",allDay);
        $(".body .activity_name").prop("value",title);
        $(".body #activity_desc").prop("value",desc);
        $(".body .activity_organizer").prop("value",organizer);
        $(".body .activity_address").prop("value",eventAddress);
    }
}

function nextActivity(activityList)
{
    var i = $(".body .activityIndex").val();
    if (i > activityList.length - 2)
    {
        showMessageInDialog('已经是最后一条活动了!','tip');
        return;
    }
    else
    {
        i = parseInt(i) + 1;
        var id = activityList[i].id;
        var title =  activityList[i].title;
        var desc = activityList[i].description;
        var organizer = activityList[i].organizer;
        var eventAddress = activityList[i].eventAddress;
        var allDay = activityList[i].allDay;
        var startDate = getDateTime(activityList[i].startDate);
        var endDate = getDateTime(activityList[i].endDate);
        var startDateStr = startDate.year + "-" + startDate.month + "-" +startDate.date;
        var endDateStr = endDate.year + "-" + endDate.month + "-" +endDate.date;
        var old_startDate = startDate.year + "" + startDate.month + "" +startDate.date;
        var old_endDate = endDate.year + "" + endDate.month + "" +endDate.date;
        var startTimeStr = startDate.hour + ":" +startDate.minute;
        var endTimeStr = endDate.hour + ":" + endDate.minute;

        $(".body .activityIndex").prop("value",i+"");
        $(".body .activityId").prop("value",id);
        $(".body .old_activity_name").prop("value",title);
        $(".body .old_startDate").prop("value",old_startDate);
        $(".body .old_endDate").prop("value",old_endDate);
        $(".body #start_date").prop("value",startDateStr);
        $(".body #end_date").prop("value",endDateStr);
        $(".body #start_time").prop("value",startTimeStr);
        $(".body #end_time").prop("value",endTimeStr);
        $(".body #all_day").prop("checked",allDay);
        $("#create_normal_event .body .activity_name").prop("value",title);
        $("#create_normal_event .body #activity_desc").prop("value",desc);
        $("#create_normal_event .body .activity_organizer").prop("value",organizer);
        $("#create_normal_event .body .activity_address").prop("value",eventAddress);
    }
}

function gotoSomeActivity(i)
{
    var id = activityList[i].id;
    var title =  activityList[i].title;
    var desc = activityList[i].description;
    var organizer = activityList[i].organizer;
    var eventAddress = activityList[i].eventAddress;
    var allDay = activityList[i].allDay;
    var startDate = getDateTime(activityList[i].startDate);
    var endDate = getDateTime(activityList[i].endDate);
    var startDateStr = startDate.year + "-" + startDate.month + "-" +startDate.date;
    var endDateStr = endDate.year + "-" + endDate.month + "-" +endDate.date;
    var startTimeStr = startDate.hour + ":" +startDate.minute;
    var endTimeStr = endDate.hour + ":" + endDate.minute;

    $(".body .activityIndex").prop("value",i+"");
    $(".body .activityId").prop("value",id);
    $(".body #start_date").prop("value",startDateStr);
    $(".body #end_date").prop("value",endDateStr);
    $(".body #start_time").prop("value",startTimeStr);
    $(".body #end_time").prop("value",endTimeStr);
    $(".body #all_day").prop("checked",allDay);
    $(".body .activity_name").prop("value",title);
    $(".body #activity_desc").prop("value",desc);
    $(".body .activity_organizer").prop("value",organizer);
    $(".body .activity_address").prop("value",eventAddress);
}
function deleteActivity()
{
    var activityId = $(".body .activityId").val();
    var activity_name = $(".body .activity_name").val();
    var startDate = $("#create_normal_event #start_date").val();
    var endDate = $("#create_normal_event #end_date").val();
    startDate = formateDate(startDate);
    endDate = formateDate(endDate);
    var url = baseurl + '/calendar/clearEvent';
    var params ={id:activityId,startDate:startDate,endDate:endDate};

    $.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){
            if (data.success == true)
            {
                var oldCount = 0;
                var index = 0;
                showMessageInDialog('此活动以删除成功!','tip');
                if ($("#day").length ==1)
                {
                    oldCount = activityList.length;
                    for (var i=0;i<activityList.length;i++)
                    {
                        if (activityList[i].id ==activityId)
                        {
                            index = i;
                            activityList.splice(i,1);
                            break;
                        }
                    }
                    if (activityList.length ==0)
                        var t=setTimeout('hideDialog()',1500);
                    else if (activityList.length ==1)
                        gotoSomeActivity(0);
                    else if (index < oldCount - 1 ) //不是最后一个
                        gotoSomeActivity(index);
                    else if (index == oldCount -1)  //如果是最后一个 			
                        gotoSomeActivity(index-1);

                    getDayActivity();
                }
                else if ($("#week").length ==1)
                {
                    oldCount = activityList.length;
                    for (var i=0;i<activityList.length;i++)
                    {
                        if (activityList[i].id ==activityId)
                        {
                            index = i;
                            activityList.splice(i,1);
                            break;
                        }
                    }
                    if (activityList.length ==0)
                        var t=setTimeout('hideDialog()',1500);
                    else if (activityList.length ==1)
                        gotoSomeActivity(0);
                    else if (index < oldCount - 1 ) //不是最后一个
                        gotoSomeActivity(index);
                    else if (index == oldCount -1)  //如果是最后一个 			
                        gotoSomeActivity(index-1);

                    getWeekActivity();
                }
                else if ($("#month").length ==1)
                {
                    oldCount = activityList.length;
                    for (var i=0;i<activityList.length;i++)
                    {
                        if (activityList[i].id ==activityId)
                        {
                            index = i;
                            activityList.splice(i,1);
                            break;
                        }
                    }
                    deleteActivityInMonthView(startDate,endDate,activity_name);
                    if (activityList.length ==0)
                        var t=setTimeout('hideDialog()',1500);
                    else if (activityList.length ==1)
                        gotoSomeActivity(0);
                    else if (index < oldCount - 1 ) //不是最后一个
                        gotoSomeActivity(index);
                    else if (index == oldCount -1)  //如果是最后一个 			
                        gotoSomeActivity(index-1);
                }
                else if ($("#list").length ==1)
                {
                    clearListView();
                    getListSchedule();
                    var t=setTimeout('hideDialog()',1500);
                }
            }
            else
            {
                showMessageInDialog('此活动删除失败，请稍后再试!','error');
            }
        }
        }
    );
}

function saveActivity()
{
    var id =  $("#create_normal_event .body .activityId").val();
    var old_activity_name = $("#create_normal_event .body .old_activity_name").val();
    var old_startDate = $("#create_normal_event .body .old_startDate").val();
    var old_endDate = $("#create_normal_event .body .old_endDate").val();
    var startDate = $("#create_normal_event .body #start_date").val();
    if (! isDate(startDate))
    {
        showMessageInDialog('开始日期格式应为YYYY-MM-DD','error');
        return;
    }
    startDate = formateDate(startDate);

    var endDate = $("#create_normal_event .body #end_date").val();
    if (! isDate(endDate))
    {
        showMessageInDialog('结束日期格式应为YYYY-MM-DD','error');
        return;
    }
    endDate = formateDate(endDate);

    var startTime = $("#create_normal_event .body #start_time").val();
    if (! isTime(startTime))
    {
        showMessageInDialog('开始时间格式应为HH:MM','error');
        return;
    }
    startTime = formateTime(startTime);

    var endTime = $("#create_normal_event .body #end_time").val();
    if (! isTime(endTime))
    {
        showMessageInDialog('结束时间格式应为HH:MM','error');
        return;
    }
    endTime = formateTime(endTime);

    var all_day = $("#create_normal_event .body #all_day").prop('checked');
    var activity_name = $("#create_normal_event .body .activity_name").val();
    if (activity_name =="")
    {
        showMessageInDialog('活动名称不能为空','error');
        return;
    }
    
    var activity_organizer = $("#create_normal_event .body .activity_organizer").val();
    if (activity_organizer =="")
    {
        showMessageInDialog('组织者不能为空','error');
        return;
    }
    
    var activity_address = $("#create_normal_event .body .activity_address").val();
    if (activity_address =="")
    {
        showMessageInDialog('活动地址不能为空','error');
        return;
    }
    
    var activity_desc = $("#create_normal_event .body #activity_desc").val();
    var url = baseurl + '/calendar/saveEvent';
    var params ={oper:"edit",id:id,startDate:startDate,endDate:endDate,startTime:startTime,endTime:endTime,all_day:all_day,organizer:activity_organizer,eventAddress:activity_address,title:activity_name,description:activity_desc};
    $.ajax({url:url,type:'POST',dataType:'json',async:false,data:params,success:function(data){
            if (data.success == true)
            {
                showMessageInDialog('活动信息保存成功!','tip');
                if (activityList == null)
                {
                  if ($("#list").length ==1)
                  {
                    modifyActivityInListView(startDate,endDate,startTime,endTime,all_day,activity_name,old_startDate,old_endDate,old_activity_name);
                    var t=setTimeout('hideDialog()',1500);
                  }
                  return;
                }
                for (var i=0;i<activityList.length;i++)
                {
                    if (id == activityList[i].id)
                    {
                        activityList[i].title = activity_name;
                        activityList[i].description = activity_desc;
                        activityList[i].organizer = activity_organizer;
                        activityList[i].eventAddress = activity_address;
                        
                        activityList[i].allDay = all_day;
                        var tempDate = new Date();
                        tempDate.setFullYear(parseInt(startDate.substring(0,4)));
                        tempDate.setMonth(parseInt(startDate.substring(5,7))-1);
                        tempDate.setDate(parseInt(startDate.substring(8,10)));
                        tempDate.setHours(parseInt(startTime.substring(0,2)));
                        tempDate.setMinutes(parseInt(startTime.substring(3,5)));
                        activityList[i].startDate = tempDate.getTime();

                        tempDate.setFullYear(parseInt(endDate.substring(0,4)));
                        tempDate.setMonth(parseInt(endDate.substring(5,7))-1);
                        tempDate.setDate(parseInt(endDate.substring(8,10)));
                        tempDate.setHours(parseInt(endTime.substring(0,2)));
                        tempDate.setMinutes(parseInt(endTime.substring(3,5)));
                        activityList[i].endDate = tempDate.getTime();

                        break;
                    }
                }
                //resetActivity();
                
                if ($("#day").length ==1)
                {
                  getDayActivity();
                }
                else if ($("#week").length ==1)
                {
                  getWeekActivity();
                }
                else if ($("#month").length ==1)
                {
                  modifyActivityInMonthView(startDate,endDate,activity_name,old_startDate,old_endDate,old_activity_name);
                }
                else if ($("#list").length ==1)
                {
                  
                }
            }
            else
            {
                showMessageInDialog('活动信息保存失败,请稍后再试!','error');
            }

        }
        }
    );
}

function resetActivity()
{
    if ($("#day").length ==1)
    {
        getDayActivity();
    }
    else if ($("#week").length ==1)
    {
        getWeekActivity();
    }
    else if ($("#month").length ==1)
    {
        clearMonthView();
        getMonthSchedule();
    }
    else if ($("#list").length ==1)
    {
        clearListView();
        getListSchedule();
        var t=setTimeout('hideDialog()',1500);
    }
}
function resetActivityInDayView()
{
    /*
     var a_name = "";
     var separate = " , ";
     for (i=0;i<activityList.length;i++)
     {
     if (i == activityList.length -1)
     separate = "";
     a_name = a_name + activityList[i].title + separate;
     }
     $("#today_activity >span").html(a_name);	
     */
}

function showOneActivity(li)
{
    var startDate = getDateTime($(li).data('activity').startDate);
    var endDate = getDateTime($(li).data('activity').endDate);
    var startDateStr = startDate.year + "-" + startDate.month + "-" +startDate.date;
    var endDateStr = endDate.year + "-" + endDate.month + "-" +endDate.date;
    var old_startDate = startDate.year + "" + startDate.month + "" +startDate.date;
    var old_endDate = endDate.year + "" + endDate.month + "" +endDate.date;
    var startTimeStr = startDate.hour + ":" +startDate.minute;
    var endTimeStr = endDate.hour + ":" + endDate.minute;

    var str = "<div id=\"create_normal_event\" class=\"dialog\">"+"\n";
    str = str + "<div class=\"header\">"+"\n";
    str = str + "<div class=\"title\">对现有活动进行修改</div>"+"\n";
    str = str +	"<div class=\"close cancle\">×</div>"+"\n";
    str = str + "</div>"+"\n";
    str = str +	"<div class=\"body\">"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<input type=\"hidden\" class=\"activityId\" value=\""+$(li).data('activity').id+"\" />"+"\n";
    str = str + "<input type=\"hidden\" class=\"old_startDate\" value=\""+old_startDate+"\" />"+"\n";
    str = str + "<input type=\"hidden\" class=\"old_endDate\" value=\""+old_endDate+"\" />"+"\n";
    str = str + "<input type=\"hidden\" class=\"old_activity_name\" value=\""+$(li).data('activity').name+"\" />"+"\n";
    
    str = str + "<div class=\"normal_text\">开始日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker90\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"start_date\" class=\"date_input\" value=\""+ startDateStr +"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";
    
    //str = str + "<input type=\"input\" id=\"start_date\" class=\"date_input\" value=\""+ startDateStr +"\" />"+"\n";
    
    str = str + "<div class=\"normal_text has_margin_left\">结束日期：</div>"+"\n";
    str = str + "<div id=\"datetimepicker91\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"yyyy-MM-dd\" type=\"input\" id=\"end_date\" class=\"date_input\" value=\"" + endDateStr+"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";
    
    //str = str + "<input type=\"input\" id=\"end_date\" class=\"date_input\" value=\"" + endDateStr+"\" />"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">开始时间：</div>"+"\n";
    str = str + "<div id=\"datetimepicker92\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"hh:mm\" type=\"input\" id=\"start_time\" class=\"date_input\" value=\""+ startTimeStr +"\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";
    
    //str = str + "<input type=\"input\" id=\"start_time\" class=\"date_input\" value=\""+ startTimeStr +"\" />"+"\n";
    
    str = str + "<div class=\"normal_text has_margin_left\">结束时间：</div>"+"\n";
    str = str + "<div id=\"datetimepicker93\" class=\"input-append\">"+"\n";
    str = str + "<input data-format=\"hh:mm\" type=\"input\" id=\"end_time\" class=\"date_input\" value=\"" + endTimeStr+ "\" />"+"\n";
    str = str + "<span class=\"add-on\">"+"\n";
    str = str + "<i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\"></i>"+"\n";
    str = str + "</span>"+"\n";
    str = str + "</div>"+"\n";
    
    //str = str + "<input type=\"input\" id=\"end_time\" class=\"date_input\" value=\"" + endTimeStr+ "\" />"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">全天活动：</div>"+"\n";
    if ($(li).data('activity').allDay == true)
        str = str + "<input type=\"checkbox\" id=\"all_day\" checked=\"checked\"/>"+"\n";
    else
        str = str + "<input type=\"checkbox\" id=\"all_day\"/>"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">活动名称：</div>"+"\n";
    str = str + "<input type=\"input\" class=\"activity_name\" value=\""+$(li).data('activity').name +"\" />"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\" style=\"width:80px\">组&nbsp;&nbsp;织&nbsp;者：</div>"+"\n";
    str = str + "<input type=\"input\" class=\"activity_organizer\" value=\""+$(li).data('activity').organizer +"\" />"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">活动地址：</div>"+"\n";
    str = str + "<input type=\"input\" class=\"activity_address\" value=\""+$(li).data('activity').eventAddress +"\" />"+"\n";
    
    str = str + "<br/>"+"\n";
    str = str + "<br/>"+"\n";
    str = str + "<div class=\"normal_text\">活动详情：</div>"+"\n";
    str = str + "<textarea id=\"activity_desc\">"+ $(li).data('activity').description +"</textarea>"+"\n";

    str = str +	"</div>"+"\n";
    str = str + "<div class=\"footer\">"+"\n";
    str = str + "<div id=\"dialogTip\" class=\"error\" style=\"margin-left:50px\"></div>"+"\n";
    str = str + "<div class=\"button_group\" style=\"width:300px\">"+"\n";
    str = str + "<div class=\"button cancle\">取消</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:deleteActivity();\">删除</div>"+"\n";
    str = str + "<div class=\"button\" onclick=\"javascript:saveActivity();\">保存</div>"+"\n";

    str = str +	"</div>"+"\n";
    str = str +	"</div>"+"\n";
    str = str + "</div>"+"\n";
    $('#mask').html('');
    $('#mask').html(str);

    $('#create_normal_event').css("width","650px");
    $('#create_normal_event').css("height","620px");
    $('#create_normal_event').css("margin-left","625px");
    $('#create_normal_event').css("margin-top","230px");
    $('#create_normal_event >.body').css('height','520px');

    $('#datetimepicker90, #datetimepicker91').datetimepicker({
        pickTime: false,
        language: 'zh-CN',
        pick12HourFormat: true
    });

    $("#datetimepicker92, #datetimepicker93").datetimepicker({
        pickDate: false,
        pickSeconds: false
    });

    $('#mask .cancle').on('click',function(event){
        $('#mask').html('');
        $('#mask').hide();
    });

    $(".dialog").draggable({cancel:".body"});

    $('#mask').show();
}

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

//时间的格式为hh:mm
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

function formateTime(input)
{
    var diffIndex = input.indexOf(":");
    if (((diffIndex !=1)&&(diffIndex !=2))||(input.length!=5))
       return "0000";
    
    var hour = input.substring(0,diffIndex);
    var minute = input.substring(diffIndex+1);

    if (hour.length<2)
        hour = "0"+hour;
    if (minute.length<2)
        minute = "0"+minute;

    return hour+""+minute;
}

// 将后台ajax返回的时间数据格式化，用于前台界面展现
function formatUcWeatherTime(input)
{
   if(input == null)
     return "";
       
   var diffIndex = input.indexOf(":");
   if ((diffIndex <0)&&(input.length==4))
      return input.substring(0,2)+":"+input.substring(2,4);
   else if ((diffIndex !=1)&&(diffIndex !=2))
      return "";
       
   var hour = input.substring(0,diffIndex);
   var minute = input.substring(diffIndex+1);

   if (hour.length<2)
       hour = "0"+hour;
   if (minute.length<2)
       minute = "0"+minute;

   return hour+":"+minute;
}

function TimeToString(input)
{
    var hour = input.substring(0,2);
    var minute = input.substring(2,4);

    return hour+":"+minute;
}

//日期格式为yyy-mm-dd
function isDate(input)
{
    console.log(input);
    if (input.length < 10)
        return false;
    var firstDiffIndex = input.indexOf("-");
    if (firstDiffIndex !=4)
        return false;
    var lastDiffIndex = input.lastIndexOf("-");
    if (lastDiffIndex !=7)
        return false;

    var yyyy = input.substring(0,firstDiffIndex);
    var mm = input.substring(firstDiffIndex+1,7);
    var dd = input.substring(lastDiffIndex+1);

    if ((! isNumber(yyyy))||(! isNumber(mm))||(! isNumber(dd))||(parseInt(mm)>12)||(parseInt(dd)>31))
        return false;
    else
        return true;
}

function formateDate(input)
{
    var firstDiffIndex = input.indexOf("-");
    var lastDiffIndex = input.lastIndexOf("-");

    var yyyy = input.substring(0,firstDiffIndex);
    var mm = input.substring(firstDiffIndex+1,7);
    var dd = input.substring(lastDiffIndex+1);

    return yyyy+""+mm+""+dd;
}

function showLoading()
{
    var loading = '<div class="loadwindow"><div class="loading"></div></div>';
    $("#mask").append(loading);
    $("#mask").css("opacity","1");
    $("#mask").css("background-color","transparent");
    $("#mask").show();
}
function hideLoading()
{
    $("#mask .loadwindow").remove();
    $("#mask").hide();
    $("#mask").removeAttr("style");
}