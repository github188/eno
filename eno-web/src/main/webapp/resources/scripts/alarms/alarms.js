$(function(){
	$(".selectpicker").selectpicker();
	$(".btn").addClass("reiken");

	$(".alert_list>tbody>tr:odd:not(0)").addClass("changeColor");

	$(".popover_nav>ul>li").each(function(index){
		$(this).click(function(){
			$(this).siblings().removeClass("cur");
			$(this).addClass("cur");
			$($(".alert_tab>div")[index]).siblings().hide();
			$($(".alert_tab>div")[index]).show();
		});
	});
});


$(function(){
	$(".navigation>li:gt(0):lt(10)").addClass("nav_bar");

	$(".root_li:not('.root_li:first')").each(function(index){
		$(this).click(function(){
			if ($($(".sub_ul")[index]).css("display") == "none"){
				$(".sub_ul").hide();
				$($(".sub_ul")[index]).show();
				$(".root_li>img").hide();
				$(".root_li").find("img:eq(1)").show();
				$(this).find("img").hide();
				$(this).find("img:eq(0)").show();
			} else {
				$($(".sub_ul")[index]).hide();
				$(this).find("img").hide();
				$(this).find("img:eq(1)").show();
			}
		});
	});

	$(".cataloge_class_two>li:not('.cataloge_class_three')").click(function(){
		var curdom = $(".current");
		curdom.removeClass("current");
		$(this).addClass("current");
	});

	$(".sub_ul li").click(function(){
		$(".sub_ul li").removeClass("changeBg");
		$(this).addClass("changeBg")
	});
        //窗口切换
        $("#windowChange").click(function () {
            closeAllWindow();
        });

	$("input[type='radio'][name='signaltype']").click(function(){
		$(".alert").hide();
	});


	$("#fireMonitorModal").dialog({
		width: "900px",
		autoOpen: false,
		show: "blind",
		hide: "explode",
		modal: true
	});


	var global_cm_countdownSeconds = 0;


	/**
	 * 查岗消息MODEL,设置和管理查岗消息的内容
	 * */
	function CheckMonitorModel(data,servTime) {
		var self = this;
		self.msgid = data.msgid;    //消息ID
		self.msgtype =data.msgtype; //消息类型CG
		self.syscode = data.syscode; //慧云系统编码
		self.timestamp = ko.computed(function(){
			var datetime = data.timestamp.year+"-"
				+ data.timestamp.monthOfYear+ "-"
				+ data.timestamp.dayOfMonth + " "
				+ data.timestamp.hourOfDay + ":"
				+ data.timestamp.minuteOfHour +":"
				+ data.timestamp.secondOfMinute;

			return datetime;
		});  //时间戳

		self.checktype = ko.observable(data.checktype);  // 查岗类型 CG:查岗 ZG:在岗 TG:脱岗
		self.responsetime = data.responsetime;  // 最大响应时间(秒)
		self.relatemsg = ko.observable(data.relatemsg);  // 相关消息，取查岗请求的msgid值
		self.description = ko.observable(data.description);  // 描述信息
		self.userid = ko.observable(data.userid);  //值班人员ID
		self.usertel = ko.observable(data.usertel); // 电话号码
		self.timeoutHours = ko.observable(0);    //超时小时数
		self.timeoutMinutes = ko.observable(0);  //超时分钟数
		self.timeoutSeconds = ko.observable(0);  //超时秒数
		self.countdownSeconds =  ko.observable(0);  //倒计时秒数
		self.servTime =  servTime;
		self.timeout = ko.computed(function(){  //超时总秒数

			//由于IE兼容性问题，这里的时间转换必须使用原生的JS支持
			var t =  JSON.parse(ko.toJSON(self.timestamp));

			var checktime = Date.parse(t.replace('-',"/")); //moment(t).valueOf();  //查岗消息下发时间
			console.log("check monitor timestamp:"+ checktime);

			var t = moment().valueOf();

			//当前时间取服务器时间
			var currtime = self.servTime; //moment(time).valueOf(); //当前时间

			console.log("current time:"+ moment(currtime).format('YYYY-MM-DD HH:mm:ss'));

			console.log(self.responsetime * 1000);

			var diff = self.responsetime * 1000 - (currtime - checktime);  //当前时间-查岗消息下发时间-响应时长=超时差值

			console.log("diff:" + diff);


			//超时小时数
			var hours = parseInt(diff/(1000*60*60));
			self.timeoutHours(hours);
			//超时分钟数
			var minutes = parseInt((diff - hours*1000*60*60)/(1000*60));
			self.timeoutMinutes(minutes);

			//超时秒数
			var secondOfMinute = parseInt((diff - hours*1000*60*60 - minutes * 1000*60)/1000);
			self.timeoutSeconds(secondOfMinute);

			console.log("current time:"+ currtime);
			console.log("hours:"+ hours);
			console.log("minutes:" + minutes);
			console.log("second:" + secondOfMinute);
			console.log(diff/1000);

			//计算倒计时开始秒数,如果超过响应时间，取消倒计时功能
			var cdseconds = self.responsetime - parseInt((currtime - checktime)/1000);
			if((currtime - checktime) == 0) {
				cdseconds = self.responsetime();
			}
			console.log("倒计时开始时间："+cdseconds);

			//if(cdseconds>0) {
			self.countdownSeconds(cdseconds);
			global_cm_countdownSeconds = cdseconds;
			//}

			//返回超时总秒数
			return (diff / 1000);

		});


	}

	/**
	 * 消防火警信息
	 * */
	function fireMonitorModel(data) {
		var self = this;
		self.almlogid = ko.observable(data.almlogid);   //实时报警主键ID
		self.signaltype = ko.observable(data.signaltype);   //消防信号类型
		self.signaltime = data.signaltime;  //发生时间
		self.devicetype = data.devicetype;  //设备类型
		self.devicecode = data.devicecode;  //设备代码
		self.displaycode = data.displaycode;    //CRT编码
		self.devicelocation = ko.observable(data.devicelocation);   //设备位置
		self.relatefile = ko.observable(data.relatefile);   //楼层图片名称
		self.coordinate = ko.observable(data.coordinate);   //报警点坐标
		self.description = ko.observable(data.description); //描述
		self.userid = ko.observable(data.userid);   //值班人员
		self.usertel = ko.observable(data.usertel); //值班电话

		self.timeoutMinutes = ko.observable(0);  //超时分钟数
		self.timeoutSeconds = ko.observable(0);  //超时秒数
		self.countdownSeconds =  ko.observable("0");    //倒计时秒数

		self.responsetime =  ko.observable(180);  //跑点时间默认为3分钟

		self.timeout = ko.computed(function(){  //超时总秒数

			var t =  JSON.parse(ko.toJSON(self.signaltime));   //报警发生时间(秒)
			var alarmtime = Date.parse(t.replace('T'," ").replace('-',"/"));

			var currtime = moment().valueOf();  //当前时间

			var diff = currtime - alarmtime;  //当前时间-查岗消息下发时间-响应时长=超时差值


			//超时分钟数
			var minutes = parseInt(diff/(1000*60));
			self.timeoutMinutes(minutes);

			//超时秒数
			var secondOfMinute = parseInt((diff - minutes * 1000*60)/1000);
			self.timeoutSeconds(secondOfMinute);

			//计算倒计时开始秒数,如果超过响应时间，取消倒计时功能
			var cdseconds = self.responsetime() - parseInt((currtime - alarmtime)/1000);

			//console.log("minutes:" + minutes + ",secondOfMinute:"+ secondOfMinute );
			console.log("跑点倒计时开始时间："+cdseconds);

			//计时秒数
			self.countdownSeconds(cdseconds.toString());

			console.log(diff / 1000);

			//返回超时总秒数
			return (diff / 1000);
		});
	}


	var checkInterval = null;  //查岗计时对象
	var fireInterval = null;    //消防跑点计时对象

	/**
	 * 程序模块
	 *
	 * @param stompClient
	 * */
	function ApplicationModel(stompClient) {
		var self = this;
		self.checkMonitor = ko.observable({});  //定义查岗消息对象
		self.fireMonitor = ko.observable({});  //定义查岗消息对象
		self.fireMonitors = ko.observableArray([]);

		var fireMonitorLookup = {};

		self.responsetime = ko.observable(0);
		self.minute = ko.observable(0); //分钟数
		self.second = ko.observable(60);  //秒数
		self.chekmonitorTitle = ko.observable("查岗倒计时");
		self.fireMonitorTitle = ko.observable("确认火警倒计时");

		self.confirmFireMinute = ko.observable(0);  //确认火警分钟数
		self.confirmFireSecond = ko.observable(0);  //确认火警秒数

		self.connect = function() {

			stompClient.connect('guest', 'guest', function(frame) {
				console.log('Connected ' + frame);
				// self.username(frame.headers['user-name']);
				if (frame.headers['user-name'] == '') {
					self.username("guest");
				}
				console.log("websocket connection successed!");


				//判断查岗对象是否定义（只有取得订阅数据后，查岗对象表示为定义）
				//如果未定义(例如浏览器刷新导致websocket重新创建了实例等)，则从数据库中获取最后一条未响应的查岗消息
				if(typeof(ko.toJS(self.checkMonitor()))=='undefined' || typeof(ko.toJS(self.checkMonitor().msgid))=='undefined' || self.checkMonitor().msgid==null) {
					//console.log('i am here!');
					stompClient.subscribe("/app/lastnoresponse", function(message){
						console.log(JSON.parse(message.body));
						if(JSON.parse(message.body).checkMonitor!=null) {
							self.processCheckMonitorData(JSON.parse(message.body).checkMonitor,JSON.parse(message.body).servTime);
						}
					});
				}

				// 订阅查岗消息队列
				stompClient.subscribe("/queue/checkmonitor", function(message) {
					console.log(JSON.parse(message.body));
					self.processCheckMonitorData(JSON.parse(message.body).checkMonitor,JSON.parse(message.body).servTime);
				});


				// 订阅火警消息队列
				stompClient.subscribe("/queue/firemonitor", function(message) {
					//console.log(JSON.parse(message.body));
					self.processFireMonitorData(JSON.parse(message.body));
				});

				// 订阅页面数据消息队列
				stompClient.subscribe("/queue/pageData", function(message) {
					//console.log(JSON.parse(message.body));
					self.processPageData(JSON.parse(message.body));
				});


			}, function(error) {
				console.log(error);
//                alert('Websocket通信连接已断开。');
			});
		};

		//处理查岗数据
		self.processCheckMonitorData = function(data,servTime) {

			//设置查岗数据
			self.checkMonitor(new CheckMonitorModel(data,servTime));
			//显示查岗窗口
			$("#checkMonitorModal").modal();

			//self.responsetime(responsetime);
			$(".cont-footer").hide();
			$("#cont-timeout").hide();
			$("#cont-countdown").show();

			//判断是否超时,如果查岗未超时，则显示查岗倒计时
			var timeouts = self.checkMonitor().timeout();

			console.log("查岗超时时长(s):"+ parseInt(timeouts));

			if(timeouts<0) {
				self.chekmonitorTitle("查岗超时");
				$(".cont-footer").show();
				self.checkMonitor().countdownSeconds(parseInt(timeouts));
			}

			self.countdown();

		};

		//查岗倒计时
		self.countdown = function() {

			checkInterval = window.setInterval(function(){

				var restime = ko.toJSON(self.checkMonitor().countdownSeconds());
				if(isNaN(restime))
					restime = global_cm_countdownSeconds;

				console.log("restime:"+ restime);

				//大于1分钟时，显示分钟数字,否则只显示秒数
				var minuteout,secondout;
				if(restime<0) {
					//正计时，由于restime是负值，这里必须输换为正数
					var s = (restime/60).toString();
					minuteout = 0 - parseInt(s,10);
					secondout = parseInt((0 - restime)-minuteout*60,10);

					//更改标题
					self.responsetime(0);
					self.chekmonitorTitle("查岗超时");
					$(".cont-footer").show();
					self.checkMonitor().checktype = "TG";
				} else {
					//倒计时
					minuteout = parseInt(restime/60);
					secondout = restime-minuteout*60;
				}
				//alert(minuteout);

				var r1 = isNaN(minuteout)?0:minuteout;

				self.minute(r1);
				self.second(secondout);


				restime--;
				self.checkMonitor().countdownSeconds(restime);

			},1000);

		};

		//响应查岗事件，发送响应消息
		self.responseCheckMonitor = function() {

			console.log("response checkmonitor");

			//console.log(ko.toJS(self.checkMonitor()));
			$.ajax({
				url: CONTEXT_PATH + "/fssm/checkmonitor",
				type: 'POST',
				data: ko.toJS(self.checkMonitor()),
				success:function(data) {
					window.clearInterval(checkInterval);  //结束查岗计时事件
					console.log(data);
				},error:function(e) {
					console.log("post checkmonitor exception:" + e);
				}
			});


			$("#checkMonitorModal").modal('hide');

		};

		var firstItem = {};
		var processingLookup = {};

		/**
		 * 火警消息处理
		 * */
		self.processFireMonitorData = function(data) {
			var row;
			//获取定时取得的火警数据，并数据到数组中，只存数组中不存在的对象
			var mappedData = $.map(data,function(item){
				if(!fireMonitorLookup.hasOwnProperty(item.almlogid)) {
					row = new fireMonitorModel(item);
					fireMonitorLookup[item.almlogid] = row;
				}
				return row;
			});
			//self.fireMonitors(mappedData);

			//从数组中获取第一条数据
			var i = 0;
			$.each(fireMonitorLookup,function(index,item){
				//console.log(ko.toJS(item));
				if(i==0) {
					firstItem = item;
					if(processingLookup==null) {
						processingLookup[fireItemKey] = firstItem;
					}
					return false;
				}
				i++;
			});

			//设置正在处理的火警对象
			var fireItemKey = ko.toJS(firstItem.almlogid());
			if(!processingLookup.hasOwnProperty(fireItemKey)) {
				processingLookup[fireItemKey] = firstItem;
				//设置火警数据
				self.fireMonitor(firstItem);

				//判断是否超时,如果查岗未超时，则显示查岗倒计时
				//countdowns =
			}

			//显示火警处理窗口
			$("#fireMonitorModal").dialog('open');

			var countdowns = self.fireMonitor().countdownSeconds();
			console.log("火警超时时长(s):"+ countdowns);
			if(countdowns<=0) {
				self.fireMonitorTitle("跑点时间已到！");
				self.submitFireMonitor("ZDHJ");
			} else {
				//由于alarmsController后台中执行了一个定时任务，所以可能会多次创建fireInterval，了为防止冲突，必须清空此对象
				if(fireInterval!=null){
					console.log('clear fireInterval...');
					window.clearInterval(fireInterval);
				}
				self.fireMonitorCountdown();
			}
		};


		//火警跑点倒计时
		self.fireMonitorCountdown = function() {

			var restime = self.fireMonitor().countdownSeconds();
			var totalSubmit = 0;
			fireInterval = window.setInterval(function(){

				console.log(restime);

				//大于1分钟时，显示分钟数字,否则只显示秒数
				var minuteout=0,secondout=0;

				if(restime==0) {
					console.log('命中....');
					if(totalSubmit==0) {
						window.clearInterval(fireInterval);
						console.log('auto submit  fire msg');
						//跑点时间到后无人应答，自动上传消息
						self.submitFireMonitor("ZDHJ");
						totalSubmit++;
					}

				} else if(restime<0) {
					//如果执行了这段代码，说明程序有问题，请检查....
					self.responsetime(0);
					self.confirmFireMinute(0);
					self.confirmFireSecond(0);
					window.clearInterval(fireInterval);
				} else {
					//倒计时
					minuteout = parseInt(restime/60);
					secondout = restime-minuteout*60;
				}
				console.log("countdown minute:"+ minuteout + ",countdown second:"+ secondout);
				self.confirmFireMinute(minuteout);
				self.confirmFireSecond(secondout);


				restime--;
				self.fireMonitor().countdownSeconds(restime);

			},1000);

		};

		//手动报警
		self.manualAlarm = function(){
			$("#manualAlarmModal").modal();

		};
		//手动发送紧急火警
		self.sendManualAlarm = function(){
			console.log("send mergency fire msg");

			$.ajax({
				url: CONTEXT_PATH + "/fssm/mergencyfire",
				type: 'POST',
				success:function(data) {
					$("#manualAlarmModal").modal('hide');
				},error:function(e) {
					console.log("post firemonitor exception:" + e);
				}
			});
		};

		//确认火警消息
		self.confirmAlarm = function() {
			console.log("send confirm fire msg");

			var len = jQuery("input[type='radio'][name='signaltype']:checked").length;
			$(".alert").hide();
			if(len==0) {
				$(".alert").show();

				return false;
			}
			var signaltype = jQuery('input[type="radio"][name="signaltype"]:checked').val();
			console.log("手动提交");
			self.submitFireMonitor(signaltype);
		};

		/**
		 * 提交火警信息
		 * */
		self.submitFireMonitor = function(signaltype) {
			self.fireMonitor().signaltype=signaltype;

			var currId = ko.toJS(self.fireMonitor().almlogid);

			console.log('submit fire monitor msg...');
			console.log(ko.toJS(self.fireMonitor()));

			$.ajax({
				url: CONTEXT_PATH + "/fssm/fire",
				type: 'POST',
				data: ko.toJS(self.fireMonitor()),
				success:function(data) {

					$("#fireMonitorModal").dialog('close');
					processingLookup = {};
					fireMonitorLookup[currId] = null;
					$.each(fireMonitorLookup,function(index,item){
						if(currId==index) {
							delete  fireMonitorLookup[currId];
						}
					});

				},error:function(e) {
					console.log("post firemonitor exception:" + e);
				}
			});
		};
		//响应查岗事件，发送响应消息
		self.processPageData = function(data) {
			console.log("response processPageData");
			pageDataRefresh("");

		};

	};


	var socket = new SockJS(wsUrl);
	var stompClient = Stomp.over(socket);
	var appModel = new ApplicationModel(stompClient);
	ko.applyBindings(appModel);
	appModel.connect();


	//
	$(window).unload(function(){
		stompClient.disconnect(function() {
			console.log("window unload!");
		});
	});




});

//返回报警级别显示的html
function getAlarmLevelHtml(obj){
	switch (obj){
		case 1:
			return '<td style="color: red;">极高</td>';
		case 2:
			return '<td style="color: #F79646;">高</td>';
		case 3:
			return '<td style="color: #996633;">中</td>';
		case 4:
			return '<td style="color: #33CC33;">低</td>';
		default:
			return'<td></td>';
	}
}

//返回报警级别文字
function getAlarmLevel(obj){
	switch (obj){
		case 1:
			return '极高';
		case 2:
			return '高';
		case 3:
			return '中';
		case 4:
			return '低';
		default:
			return '';
	}
}

/**
 * @param obj 数据列表
 *return 返回报警列表html
 */
function getAlarmListHtml(obj){
	var html = " <tr><th>报警日期</th><th>报警时间</th><th>报警描述</th><th>子系统</th><th>设备</th><th>位置</th><th>报警级别</th><th>报警处理</th></tr>";
	if(obj!=null){
		for (var k = 0; k < obj.length; k++) {
			var ucAlarmactive = obj[k];
			if (ucAlarmactive.almpriority < 800) {
				var D = new Date(ucAlarmactive.almt);
				var y = D.getFullYear();
				var m = D.getMonth() + 1;
				if (m < 10) {
					m = '0' + m;
				}
				var d = D.getDate();
				if (d < 10) {
					d = '0' + d;
				}
				var h = D.getHours();
				if (h < 10) {
					h = '0' + h;
				}
				var i = D.getMinutes();
				if (i < 10) {
					i = '0' + i;
				}
				var s = D.getSeconds();
				if (s < 10) {
					s = '0' + s;
				}
				var dateBj = y + '年' + m + '月' + d + '日';
				var timeBj = h + '点' + i + '分' + s + '秒';
				html += '<tr  onclick="answer('
				+ ucAlarmactive.almlogid + ','
				+ ucAlarmactive.almt
				+ ');"  style="cursor: pointer;"'
				+ (k % 2 == 0 ? ' class="changeColor"' : '') + '><td>'
				+ dateBj + '</td>'
				+ '<td>' + timeBj+ '</td>'
				+ '<td>' + ucAlarmactive.almcomment + '</td>'
				+ '<td>' + ucAlarmactive.groupname+ '</td>'
				+ '<td>' + ucAlarmactive.devicename + '</td>'
				+ '<td>' + ucAlarmactive.tagcomment
				+ '</td>' + getAlarmLevelHtml(ucAlarmactive.almpriority)
				+ '<td>' + (ucAlarmactive.reviewer > '2000' ? '已应答' : '未应答') + '</td></tr>';
			}
		}
	}
	return html;
}

