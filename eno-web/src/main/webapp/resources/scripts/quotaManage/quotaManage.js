$(function() {

//	quotaManage.chooseTimeY(); 
//	quotaManage.chooseTimeM(); 
	
});

var quotaManage = {
	chooseTimeY: function(format) { // 生成碳足迹曲线
		WdatePicker({
            el : 'year_date',	//只操作这个日历控件
            dateFmt : "yyyy", //定义时间的格式
            onpicked : function(dp) {
                // 具体的对比时间，格式为（yyyy），此时间会传递到后台
                var paretime= dp.cal.getDateStr("yyyy");
            }
        });
	},
	chooseTimeM : function(format) { // 生成用能定额图表
		WdatePicker({
            el : 'month_date',	//只操作这个日历控件
            dateFmt : "yyyy-MM", //定义时间的格式
            onpicked : function(dp) {
                // 具体的对比时间，格式为（yyyy-MM），此时间会传递到后台
                var paretime= dp.cal.getDateStr("yyyy-MM");
            }
        });
	}
	
};
