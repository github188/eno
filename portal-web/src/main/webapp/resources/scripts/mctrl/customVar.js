/**
 * 此js为自定义js，主要用于声明首页、能源管理仪表盘、能源管理页面的获取数据的方式以及调用数据的ip和端口
 * @author ZouZhiXiang
 * 
 */

var isNotStatic = true; // 是否使用静态数据，默认为false表示使用实时数据，true表示使用静态数据
var ipaddress = "127.0.0.1", port = "8087"; // 首页请求数据ip和端口
var type = "day"; // 此处传day表明是查询天的数据

//存储当前时间，和当前时间往后一天的值 
var _now_date = new Date(), _temp_date = new Date();
var yearMonth = _now_date.getFullYear() + "-" + (_now_date.getMonth() + 1) + "-";
var _now_tfrom = yearMonth + _now_date.getDate();
_temp_date.setDate(_temp_date.getDate() + 1); var _now_tto = _temp_date.getFullYear() + "-" + (_temp_date.getMonth() + 1) + "-" + _temp_date.getDate(); // 当前日期往后一天
var lastDate = new Date();lastDate.setDate(lastDate.getDate() - 1); var _lastDay =  lastDate.getFullYear() + "-" + (lastDate.getMonth() + 1) + "-" + lastDate.getDate(); // 当前日期往前一天

var precents_val = 0.5; // 总用电量圆环的比例
// 返回当前时间最近的5分钟
function getRecentlyFiveMin() {
	var current_date = new Date(), curr_minutes = current_date.getMinutes();
	if(curr_minutes % 5 != 0) current_date.setMinutes(curr_minutes - 5); //  如果分钟不是5的倍数的话，则在当前基础上-5
	return current_date.getFullYear() + "-" + (current_date.getMonth() + 1) + "-" + current_date.getDate() + "T" + current_date.getHours() + ":" + current_date.getMinutes() + ":" + current_date.getSeconds();
}