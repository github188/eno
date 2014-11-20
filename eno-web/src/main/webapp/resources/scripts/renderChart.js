var isNotStatic = true; // 使用静态数据
// 全局的变量，所有函数都能使用
var curr_dataList = []; // 当前数据列表
var cataList = [];// x轴列表数据
var loop = 24; // 循环次数
var build_ele_step = 2; // X轴的间隔
var centertitle = ''; // 图表居中标题
var dayDataList = [],weekDataList=[],monthDataList=[],yearDataList=[];// 数据列表（分别是日、周、月、年）
var choose_type_1 = 'day',choose_type_passenger = 'day',choose_type_2 = 'day',choose_type_3 = 'day',choose_type_4 = 'day',choose_type_5 = 'day',choose_type_6 = 'day'; // 记录每个图表的点击类型
var choose_time_1 = getCurrentTime(),choose_time_passenger = choose_time_1,choose_time_2 = choose_time_1,choose_time_3 = choose_time_1,choose_time_4 = choose_time_1,choose_time_5 = choose_time_1,choose_time_6 = choose_time_1; // 记录每个图表的点击时间
var choose_data_1 = [],choose_data_wendu = [],choose_data_passenger = [],choose_data_2 = [],choose_data_3 = [],choose_data_4 = [],choose_data_5 = [],choose_data_6 = []; // 记录每个图表显示的数据列表
var chart_of_1,chart_of_passenger,chart_of_2,chart_of_3,chart_of_4,chart_of_5,chart_of_6; // 图表对象

var pie_num = 7879.7; // 饼图的数据比例 

// 格式化字符串
var dayFormat = '&beforeFormat=yyyy-MM-dd HH:mm:ss&afterFormat=HH';
var weekFormat = '&afterFormat=MM/dd';
var monthFormat = '&afterFormat=MM/dd';
var yearFormat = '&beforeFormat=yyyy-MM&afterFormat=yyyy/MM';

var name1 = 'electricity,t_oa',id1 = 'total,outdoor_1', ispd1 = '0,0'; // 1.建筑总用电趋势与室外温度图表
var name2 = 'electricity,flow_passenger',id2 = 'total,indoor_1', ispd2 = '0,0'; // 2.建筑总用电趋势与客流量图表
var name3 = 'electricity,electricity,electricity,electricity',id3 = 'lighting,hvac,elevator,others', ispd3 = '1,1,1,1,1,'; // 3.建筑用电分项图表
var name4 = 'electricity',id4 = 'hvac', ispd4 = '0'; // 4.空调系统用电趋势图表
var name5 = 'electricity,electricity,electricity,electricity,electricity,electricity',id5 = 'chiller,chwp,ct,ahu,cwp,hvac_others', ispd5 = '1,1,1,1,1,1,'; // 5.空调系统用电分项
var name6 = 'electricity',id6 = 'lighting', ispd6 = '0'; // 6.照明系统用电趋势图表
var name7 = 'electricity',id7 = 'chiller', ispd7 = '0'; // 7.冷机用电趋势图表

// 1.建筑总用电趋势 数据列表（分别是日、周、月、年）
var dayDataList1 = [
		[1431,1455,1444.5,1431,1416,1420.5,1522.5,2019,2185.5,2857.5,2806.5,2824.5,2781,2770.5,2796,2796,2748,2671.5,2715,2538,2590.5,2536.5,2055,1807.5],
		[1713,1663.5,1584,1518,1486.5,1519.5,1671,2016,2109,1732.5,2340,2340,2340,2340,2310,2340,2269.5,1540.5,1255.5,1218,1198.5,1164,1198.5,1197],
		[1225.5,1270.5,1261.5,1240.5,1254,1252.5,1380,1614,1749,2019,2103,2128.5,2154,1887,2470.5,2118,2067,1618.5,1402.5,1347,1324.5,1294.5,1362,1366.5],
		[1363.5,1359,1362,1350,1356,1348.5,1611,1771.5,2242.5,2439,2424,2473.5,2521.5,2535,2566.5,2499,2470.5,1750.5,1564.5,1521,1510.5,1018.5,882,876],
		[913.5,940.5,934.5,943.5,957,1014,1768.5,2011.5,2119.5,1728,1833,1804.5,1740,1725,1908,1957.5,2007,1651.5,1131,1090.5,999,996,1014,1002]
	];
var weekDataList1 = [
		[47147.08,53925,51565.81,50223.2,51000,46934.3,42969],
		[39564,44319,45775.5,50751.99,48110.01,59617.5,58377],
		[58828.5,53556,55948.5,60462.52,66695.48,65157,55845],
		[45775.5,50751.99,48110.01,59617.5,66695.48,65157,39564],
		[51565.80752,50223.2,52000,55321.1,55948.5,60462.52,53556]
	];
var monthDataList1 = [
		[47147.08,53925,51565.8,50223.2,51000,46934.3,42969,39564,44319,45775.5,50751.99,48110.01,59617.5,58377,58828.5,53556,55948.5,60462.52,66695.48,65157,55845,45775.5,50751.99,48110.01,59617.5,66695.48,65157,39564,51565.81,50223.2,52000],
		[46934.3,42969,39564,44319,45775.5,50751.99,48110.01,59617.5,51565.81,50223.2,51000,46934.3,42969,39564,44319,45775.5,50751.99,48110.01,59617.5,58377,58828.5,53556,55948.5,60462.52,66695.48,50751.99,48110.01,59617.5,66695.48,65157,67815.2],
		[45775.5,50751.99,48110.01,59617.5,51565.81,50223.2,51000,46934.3,42969,39564,44319,43969,40564,45319,46775.5,51751.99,49110.01,60617.5,59377,59828.5,54556,56948.5,61462.52,67695.48,51751.99,49110.01,60617.5,67695.48,66157,55550,66157],
		[51457.2,52234,48168.3,44203,45553,53234,49168.3,45203,41798,46553,46203,42798,47553,49009.5,53985.99,51344.01,52799.81,51457.2,52234,48168.3,44203,40798,45553,47009.5,51985.99,52481.99,49840.01,61347.5,68425.48,66887,67815.2]
	];
var yearDataList1 = [
		[1441232,1305000,1261000,1235000,1405000,1567000,1650844,1755450,1705000,800000,1405000,1567000],
		[1436232,1300000,1256000,1230000,1400000,1562000,1645844,1750450,1700000,1400000,1542000,1532000]
	];
	
// 温度
var wendu_dayDataList1 = [
		[23,23.5,23,22,21,18.5,22,23,25,26,27,26.5,26,27,27.5,27,27.1,28,29,28,27,26,24,24],
		[27,27.5,27,26,25,22.5,26,27,29,30,31,30.5,30,31,31.5,31,31.1,32,33,32,31,30,28,28],
		[26,26.5,26,25,24,21.5,25,26,28,29,30,29.5,29,30,30.5,30,30.1,31,32,31,30,29,27,27],
		[26.5,27,26.5,25.5,24.5,22,25.5,26.5,28.5,29.5,30.5,30,29.5,30.5,31,30.5,30.6,31.5,32.5,31.5,30.5,29.5,27.5,27.5],
		[22.5,23,22.5,21.5,20.5,18,21.5,22.5,24.5,25.5,26.5,26,25.5,26.5,27,26.5,26.6,27.5,28.5,27.5,26.5,25.5,23.5,23.5]
	];
var wendu_weekDataList1 = [
		[27,26.5,26,27,27.5,27,27.1],
		[31,30.5,30,31,31.5,31,31.1],
		[30,29.5,29,30,30.5,30,30.1],
		[30.5,30,29.5,30.5,31,30.5,30.6],
		[26.5,26,25.5,26.5,27,26.5,26.6]
	];
var wendu_monthDataList1 = [
		[23,23.5,23,22,21,18.5,22,23,25,26,27,26.5,26,27,27.5,27,27.1,28,29,28,27,26,24,24,27,27.5,27,27.1,28,29,30],
		[26,26.5,26,25,24,21.5,25,26,28,29,30,26,27,26.5,26,27,27.5,27,27.1,28,30,29,27,27,30,30.5,30,30.1,31,32,31],
		[29,29.5,29,28,27,24.5,28,29,31,32,33,29,30,29.5,29,30,30.5,30,30.1,31,33,32,30,30,33,33.5,33,33.1,34,35,3],
		[27.5,28,27.5,26.5,25.5,23,26.5,27.5,29.5,30.5,31.5,27.5,28.5,28,27.5,28.5,29,28.5,28.6,29.5,31.5,30.5,28.5,28.5,31.5,32,31.5,31.6,32.5,33.5,31]
	];
var wendu_yearDataList1 = [
		[29,28,27,26,24,24,27,26,27,26.5,26,27],
		[27,27.1,28,29,27,27,30,29,30,29.5,26,27]
	];

	
// 客流量
var passenger_dayDataList1 = [
		[0,0,0,0,0,0,0,73,100,120,140,200,180,130,300,400,460,460,370,350,180,100,73,0],
		[0,0,0,0,0,0,0,123,150,170,190,250,230,180,350,450,510,510,420,400,230,150,123,0],
		[0,0,0,0,0,0,0,173,200,220,240,300,280,230,400,500,560,560,470,450,280,200,173,0],
		[0,0,0,0,0,0,0,164,191,211,231,291,271,221,391,491,551,551,461,441,271,191,164,0],
		[0,0,0,0,0,0,0,264,291,311,331,391,371,321,491,591,651,651,561,541,371,291,264,0]
	];
var passenger_weekDataList1 = [
		[3600,3200,3400,2900,2800,4000,5000],
		[3700,3300,3500,3000,2900,4100,5100],
		[4100,3700,3900,3400,3300,4500,5500],
		[4300,3900,4100,3600,3500,4700,5700],
		[4200,3800,4000,3500,3400,4600,5600]
	];
var passenger_monthDataList1 = [
		[3600,3200,3400,2900,2800,4000,5000,3700,3300,3500,3000,2900,4100,5100,4100,3700,3900,3400,3300,4500,5500,2800,4000,5000,3700,3300,3500,3000,2900,4100,5100],
		[3800,3400,3600,3100,3000,4200,5200,3900,3500,3700,3200,3100,5000,3700,3300,3500,3000,2900,4100,5100,4100,3000,4200,5200,3900,3500,3700,3200,3100,4300,3100],
		[3696,3296,3496,2996,2896,4096,5096,3796,3396,3596,3096,2996,3700,3300,3500,3000,2900,4100,5100,4100,4300,3200,4096,5096,3796,3396,3596,3096,2996,4196,3400],
		[3620,3220,3420,2920,2820,4020,5020,3720,3320,3520,3020,2920,3624,3224,3424,2924,2824,4024,5024,4024,4224,3124,4020,5020,3720,3320,3520,3020,2920,4120,3400]
	];
var passenger_yearDataList1 = [
		[116000,120000,120500,113000,208902,298018,201837,190265,180298,178299,201876,210938],
		[208902,298018,201837,190265,116000,120000,120500,113000,190265,180298,201876,210938]
	];
	
	
// 2.建筑用电分项  图表 数据列表（分别是日、周、月、年）
var dataList_2 = [
		[30,35,9,6],
		[35,28,11,6],
		[29,35,15,3],
		[34,30,13,6]
	];
var legendList_2 = ['照明','空调','电梯','其它'];
	
// 3.空调系统用电趋势图表 数据列表（分别是日、周、月、年）
var dayDataList3 = [
		[718.18,777.689,767.009,764.272,760.049,757.167,812.183,957.042,1011.082,1236.361,1232.969,1189.258,1225.973,1210.906,1216.825,1220.76,1220.139,1216.033,1234.928,1230.13,1229.045,1217.885,1158.54,1035.062],
		[997.352,987.066,963.131,963.382,957.762,959.639,1075.971,1163.511,1157.904,928.652517,1216.702183,1216.702183,1216.702183,1216.702183,1208.039751,1181.474,1168.69,997.696,937.476,914.465,918.441,906.135,914.48,910.68],
		[921.648,932.317,932.687,928.315,926.426,922.662,1021.735,1153.901,1174.683,1151.769,1161.098,1169.652,1172.216,985.8261667,1355.453833,1167.583,1176.773,1041.471,969.787,969.937,959.967,957.056,966.374,975.872],
		[959.321,964.552,964.125,959.7,959.455,953.948,1137.263,1154.296,1040.197,1037.14,1019.553,1030.898,1034.629,1034.967,1036.684,1043.09,1034.971,863.376,842.232,845.929,838.177,588.917,455.384,457.42],
		[474.012,476.607,475.764,485.759,484.478,505.285,1050.776,1173.89,1038.152,672.482,670.164,669.923,654.27,654.778,777.037,754.164,857.538,695.947,515.604,512.336,486.366,487.653,494.121,496.278]
	];
var weekDataList3 = [
		[25399.487,25078.756,25095.209,22256.224,15563.384,21133.13331,21614.45587],
		[21614.45587,22030.62832,25887.749,25218.48154,25250.52822,28487.306,25236.21502],
		[25446.108,23671.68,24214.891,22729.807,14517.00507,38821.59893,29442.924],
		[28356.431,27515.012,25339.412,28205.3,30915.46929,35028.41271,33790.055],
		[28497.959,28471.54542,33043.94485,24214.891,22729.807,28487.306,15563.384]
	];
var monthDataList3 = [
		[27379.37979,27488.76293,27978.56593,27996.89393,38821.59893,29442.924,28356.431,27515.012,25339.412,28205.3,30915.46929,35028.41271,33790.055,28487.306,28497.959,28471.54542,33043.94485,25399.487,25078.756,25095.209,22256.224,15563.384,21133.13331,21614.45587,21614.45587,22030.62832,25887.749,25218.48154,25250.52822,28487.306,25236.21502],
		[28356.431,27515.012,25339.412,28205.3,30915.46929,35028.41271,33790.055,28487.306,28497.959,28471.54542,33043.94485,25078.756,25095.209,22256.224,15563.384,21133.13331,21614.45587,21614.45587,22030.62832,25887.749,25218.48154,25250.52822,28487.306,25236.21502,28487.306,28497.959,28471.54542,33043.94485,25399.487,25078.756,25095.209],
		[22256.224,15563.384,21133.13331,29915.46929,34028.41271,32790.055,27487.306,27497.959,27471.54542,32043.94485,24078.756,24095.209,21256.224,28356.431,27515.012,25339.412,28205.3,30915.46929,35028.41271,33790.055,28487.306,28497.959,28471.54542,33043.94485,25078.756,25095.209,22256.224,15563.384,25236.21502,28487.306,28497.959],
		[27515.012,25339.412,28205.3,30915.46929,35028.41271,33790.055,28487.306,28497.959,28471.54542,33043.94485,33481.055,28178.306,28188.959,28162.54542,32734.94485,33172.055,27869.306,28153.431,27312.012,25136.412,28002.3,30712.46929,34825.41271,33587.055,28284.306,28294.959,28268.54542,32840.94485,24875.756,24892.209,22053.224]
	];
var yearDataList3 = [
		[10000,20000,7908,16700,30000,574967.7411,843834.516,836642,50000,45000,30000,20019],
		[12087,22087,9995,18787,32087,577054.7411,708532,650897,52087,28765,30000,20019]
	];	

// 4.空调系统用电分项
var legendList_4 = ['冷机','冷冻水泵','冷却塔','空调箱','冷却水泵','其它'];
// 数据列表
var dataList_4 = [
	[32,14,5,20,5,24],
	[35,16,8,14,7,20],
	[30,17,6,15,13,19],
	[36,16,8,12,8,20],
];

// 5.照明系统用电趋势图表 数据列表（分别是日、周、月、年）
var dayDataList5 = [
		[83.26711111,76.603,69.724,70.109,68.745,69.146,63.583,137.911,169.27,167.723,167.133,126.402,166.215,174.26,187.795,166.533,175.622,198.146,201.536,194.008,196.855,151.032,56.11,53.9],
		[50.19311111,49.26,49.238,50.078,49.219,49.209,54.294,94.192,116.393,141.102,141.1,145.505,142.594,121.6607,157.4503,145.001,140.595,96.817,74.248,68.158,68.783,65.521,60.884,56.007],
		[55.13611111,54.842,54.815,55.407,55.796,55.205,92.379,104.088,125.848,136.147,136.699,138.382,136.535,137.727,138.198,136.381,133.813,90.406,72.222,66.08,67.889,68.264,70.826,52.279],
		[52.62311111,52.232,52.532,52.807,51.722,50.787,53.302,121.992,146.95,150.325,153.333,148.213,147.27,150.039,153.4,149.289,145.223,109.484,66.221,58.292,63.178,59.515,51.285,49.875],
		[86.501875,82.153125,77.836875,78.4475,77.838125,77.719375,97.47625,151.249375,184.44875,189.91875,189.895,165.49,189.21875,194.991875,203.745625,189.32125,193.396875,180.345,171.09875,162.555,165.465,137.06,79.335,66.361875]
	];
var weekDataList5 = [
		[1656.337982,1613.953324,1676.105737,1600,1646.21266,1634.22101,1605.94976],
		[1680.981587,1913.24976,1734.49376,1994.15972,1743.424251,1499.99976,1669.884444],
		[1499.99976,1669.884444,1827.974517,1605.94976,1656.32576,1392.43176,1771.706932],
		[1321.974517,1337.12476,1187.77776,1286.58476,891.2860759,1241.586923,1140.21266],
		[1567.355164,1843.88876,1416.884444,1574.974517,1471.53726,1422.05176,1331.496504]
	];
var monthDataList5 = [
		[1656.337982,1613.953324,1676.105737,1600,1646.21266,1634.22101,1605.94976,1656.32576,1392.43176,1771.706932,1680.981587,1913.24976,1734.49376,1994.15972,1743.424251,1499.99976,1669.884444,1827.974517,1843.12476,1693.77776,1792.58476,1397.286076,1747.586923,1913.24976,1734.49376,1994.15972,1743.424251,1499.99976,1669.884444,1827.974517,1843.12476],
		[1734.49376,1994.15972,1743.424251,1499.99976,1669.884444,1827.974517,1605.94976,1656.32576,1392.43176,1771.706932,1680.981587,1913.24976,1499.99976,1669.884444,1827.974517,1843.12476,1693.77776,1792.58476,1397.286076,1747.586923,1646.21266,1634.22101,1605.94976,1656.32576,1392.43176,1771.706932,1680.981587,1913.24976,1734.49376,1656.337982,1613.953324],
		[1265.706932,1174.981587,1391.286076,2187.77776,1163.884444,1321.974517,1337.12476,1187.77776,1286.58476,891.2860759,1241.586923,1140.21266,1128.22101,1163.884444,1321.974517,886.4317597,1265.706932,1174.981587,1407.24976,993.9997597,1163.884444,1099.94976,1150.32576,886.4317597,1265.706932,1174.981587,1407.24976,1228.49376,1150.337982,1107.953324],
		[1500.100346,1584.570654,1567.355164,1843.88876,1416.884444,1574.974517,1471.53726,1422.05176,1339.50826,1331.496504,1461.284255,1526.73121,1314.110385,1416.884444,1574.974517,1364.77826,1479.742346,1483.783173,1402.267918,1370.793342,1405.048552,1367.085385,1378.13776,1271.37876,1329.069346,1473.34426,1544.115673,1570.87176,1442.415871,1382.145653]
	];
var yearDataList5 = [
		[44319,45775.5,30751.99158,48110.00842,49617.5,48377,48828.5,43556,45948.5,50462.52471,46695.47529,55157],
		[58377,48828.5,43556,45948.5,50462.52471,50751.99158,48110.00842,52098.375,50795.85,51269.925,46695.47529,55157]
	];


// 6.冷机用电趋势图表 数据列表（分别是日、周、月、年）
var dayDataList6 = [
	[125.8,128.391,124.704,125.796,122.102,123.801,124.207,137.3,186.81,338.695,328.625,328.891,323.383,320.593,321.922,328.188,325.312,325.688,326.312,325.297,331.313,325.687,322.726,304.063],
	[280.711,272.219,260.382,261.875,260.125,259.892,249.625,301.257,273.493,226.8522857,302.5105714,302.5105714,302.5105714,302.5105714,293.2144286,300.715,293.125,275.051,250.023,242.375,241.739,241.312,244.375,246.523],
	[247.102,247.676,248.273,248.5,247.711,243.891,249.921,279.176,289.841,296.761,301.438,307.062,305.648,260.852,346.699,307.523,309.204,296.023,269.188,259.5,260.585,259.989,260.403,263.108],
	[263.579,264.921,265.091,266.113,265.609,262.613,282.102,297.301,158.448,147.906,146.493,150.609,152.804,154.094,157,156.492,156.501,148.398,139.5,136.5,137.398,94.109,87.399,90.101],
	[90.704,91.398,90.602,92.601,93.797,106.61,304.203,326.39,230.115,93.097,107.301,109.102,107.898,107.398,102.204,101.097,107.602,108.597,107.801,108.602,96.597,109.903,110.801,90.1]
];
var weekDataList6 = [
	[6075.606,6484.926,6606.074,4381.081,3014.92,5595.817743,5243.570166],
	[5243.570166,4687.550104,5109.421,5507.518587,5852.540442,5852.540442,6563.064529],
	[7371.5,6669.336,6402.703,4365.57,7388.732032,7550.267968,8869.001],
	[7735.422,7099.07,6558.906,6986.516,8766.743534,11019.83347,8959.626],
	[7083.195,7132.679,6881.081411,9381.554726,9715.973961,12639.758,7462.80474]
];
var monthDataList6 = [
	[6075.606,6484.926,6606.074,4381.081,3014.92,5595.817743,5243.570166,5243.570166,4687.550104,5109.421,5507.518587,5852.540442,5852.540442,6563.064529,7371.5,6669.336,6402.703,4365.57,7388.732032,7550.267968,8869.001,7735.422,7099.07,6558.906,6986.516,8766.743534,11019.83347,8959.626,7083.195,7132.679,7462.80474],
	[9381.554726,9715.973961,12639.758,7462.80474,6075.606,6484.926,6606.074,4381.081,3014.92,5595.817743,5243.570166,5243.570166,4687.550104,6563.064529,7371.5,6669.336,6402.703,4365.57,7388.732032,7550.267968,8869.001,7099.07,6558.906,6986.516,8766.743534,11019.83347,8959.626,6402.703,4365.57,7388.732032,7550.267968],
	[5075.606,5484.926,5606.074,3381.081,2014.92,4595.817743,5243.570166,5243.570166,4687.550104,6563.064529,7371.5,6669.336,6402.703,4365.57,7388.732032,7550.267968,5402.703,3365.57,6388.732032,6550.267968,7869.001,6735.422,6099.07,5558.906,5986.516,7766.743534,6075.606,6484.926,6083.195,6132.679,6462.80474],
	[7735.422,7099.07,6558.906,6986.516,8766.743534,11019.83347,8959.626,7083.195,7132.679,6881.081411,9381.554726,9715.973961,12639.758,7462.80474,7371.5,6669.336,6402.703,4365.57,7388.732032,7550.267968,8869.001,6075.606,6484.926,6606.074,4381.081,3014.92,5595.817743,5243.570166,4687.550104,5109.421,5507.518587]
];
var yearDataList6 = [
	[4000,8000,3163.2,6680,12000,229987.0964,237533.8064,234656.8,20000,18000,12000,8007.6],
	[6030,10030,5193.2,8710,14030,232017.0964,239563.8064,236686.8,22030,10000,12000,8007.6]
];



// 得到指定日期对应是星期几
function returnDayInWeek(s)
{
	var result = new Date(Date.parse(s.replace(/-/g, '/')));
	return result.getDay() == 0 ? 7 : result.getDay();
}
	
// 当前选择的时间当月的最后一天（例如：当前s为2013-08-22(22为8月的随意一天都可)返回2013-08-31）
function returnMonthLastDay(s)
{
	var d = new Date(Date.parse(s.replace(/-/g, '/')));
	var nextMonthFirstDay=new Date(d.getFullYear(),d.getMonth()+1,1);
	var result=new Date(nextMonthFirstDay-86400000); // 在下个月1号的基础上减一天的毫秒数
	return result.getDate();
}
// 返回1970年1月1日午夜到指定日期（字符串）的毫秒数。
function returnMillSeconds(str){
	return Date.parse(str.replace(/-/g, '/'));
}
// 获取当前时间，格式为：2013-10-09
function getCurrentTime()
{
	var Nowdate = new Date();
	var day = (Nowdate.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((Nowdate.getMonth()+1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [Nowdate.getFullYear(), month, day ];
	return t.join('-');
}

// 根据给出的时间s往后推d天，例如：s=2013-10-13，d=2，则返回2013-10-15
function getTimeByDays(s,d)
{
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	//var WeekFirstDay=new Date(Nowdate-((Nowdate.getDay() == 0 ? 7 : Nowdate.getDay())-d)*86400000);	
	var WeekFirstDay=new Date(Nowdate.getTime() +(d*86400000));
	var day = (WeekFirstDay.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((WeekFirstDay.getMonth()+1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [WeekFirstDay.getFullYear(), month, day ];
	return t.join('-');
}

// 显示当前日期对应的周的第一天
function showWeekFirstDay(s)
{
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	var WeekFirstDay=new Date(Nowdate-((Nowdate.getDay() == 0 ? 7 : Nowdate.getDay())-1)*86400000);
	var day = (WeekFirstDay.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((WeekFirstDay.getMonth()+1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [WeekFirstDay.getFullYear(), month, day ];
	return t.join('-');
}

// 显示传递日期对应的下个月的第一天
function showNextMonthFirstDay(s)
{
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	Nowdate.setMonth(Nowdate.getMonth() + 1); // 当前月份往后推一个月
	var month = ((Nowdate.getMonth() + 1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [Nowdate.getFullYear(), month, "01" ];
	return t.join('-');
}

// 显示当前日期对应的周的最后一天
function showWeekLastDay(s)
{
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	var WeekFirstDay=new Date(Nowdate-((Nowdate.getDay() == 0 ? 7 : Nowdate.getDay())-1)*86400000);
	var WeekLastDay=new Date((WeekFirstDay/1000+6*86400)*1000);	
	var day = (WeekLastDay.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((WeekLastDay.getMonth()+1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [WeekLastDay.getFullYear(), month, day ];
	return t.join('-');
}

// 点击日、周、月、年出现触发的事件
function renderDateTime(choosetype,obj,func){
	// 修改点击对象的样式效果
	$(obj).siblings().removeClass("changeColor");
	$(obj).addClass("changeColor");

	var dformt = 'yyyy-MM-dd';
	if (choosetype == "day"){
		dformt = 'yyyy-MM-dd';
	} else if (choosetype == "week") {
		dformt = 'yyyy-MM-dd';
	} else if (choosetype == "month") {
		dformt = 'MM';
	} else if (choosetype == "year") {
		dformt = 'yyyy';
	}
	
	WdatePicker({
		el : 'showDateTime',
		dateFmt : dformt,
		onpicked : function(dp) {
			// 选择的时间
			var paretime= dp.cal.getDateStr('yyyy-MM-dd');
			if(func == "build_electricity"){ // 1.建筑总用电趋势
				choose_type_1 = choosetype;
				choose_time_1 = paretime;
				getBuildElectricityChart(choosetype,paretime);
				$('#comparetime1').val('');
			} else if(func == "build_electricity_passenger"){ // 1.建筑总用电趋势
				choose_type_passenger = choosetype;
				choose_time_passenger = paretime;
				getBuildElectricityPassengerChart(choosetype,paretime);
				$('#comparetime1_passenger').val('');
			} else if(func == "build_ele_trend"){ // 2.建筑用电分项
				choose_type_2 = choosetype;
				choose_time_2 = paretime;
				getBuildEleTrendChart(choosetype,paretime);
				$('#comparetime2').val('');
			} else if(func == "air_system_trend"){ // 3.空调系统用电趋势
				choose_type_3 = choosetype;
				choose_time_3 = paretime;
				getAirEleTrendChart(choosetype,paretime);
				$('#comparetime3').val('');
			} else if(func == "air_ele_item"){ // 4.空调系统用电分项
				choose_type_4 = choosetype;
				choose_time_4 = paretime;
				getAirEleItemChart(choosetype,paretime);
				$('#comparetime4').val('');
			} else if(func == "light_system"){ // 5.照明系统用电趋势
				choose_type_5 = choosetype;
				choose_time_5 = paretime;
				getLightSystemChart(choosetype,paretime);
				$('#comparetime5').val('');
			} else if(func == "cool_refrigeration"){ // 6.冷机用电趋势
				choose_type_6 = choosetype;
				choose_time_6 = paretime;
				getCoolRefrigerationChart(choosetype,paretime);
				$('#comparetime6').val('');
			}
		}
	});
}

// 修改X轴的坐标以及数据列表
function getCataListAndDataList(temp_dList,temp_wendu_dList,cateroiges,type,choosetime,func){
	curr_dataList = []; // 当前数据列表
	curr_wendu_dataList = []; // 当前数据列表
	curr_passenger_dataList = []; // 当前数据列表
	cataList = [];// x轴列表数据
	switch(type){
		case 'day': // 日视图
			loop = 24;
			build_ele_step = 1; // X轴的间隔
			if(choosetime == getCurrentTime() && loop >= new Date().getHours()){
				loop = new Date().getHours() + 1;
			}
			centertitle = choosetime;// 图表居中标题
			// 如果当前选择的时间大于当前系统时间，则图表中不显示对应的数据
			if(returnMillSeconds(choosetime) > returnMillSeconds(getCurrentTime())){
				cataList.push(choosetime);
				curr_dataList.push(0);
				curr_wendu_dataList.push(0);
				curr_passenger_dataList.push(0);
			} else { // 选择的时间小于或等于当前系统时间的情况
				curr_dataList = temp_dList; // 当前数据列表
				curr_wendu_dataList = temp_wendu_dList; // 当前温度数据列表
				curr_passenger_dataList = temp_wendu_dList; // 当前温度数据列表
				for(var i=0;i<loop;i++){
					cataList.push( i + "" ) ;
					if(choosetime == getCurrentTime() && loop >= new Date().getHours()){
						if(i == 0){
							curr_dataList = []; // 清空当前数据列表
							curr_wendu_dataList = []; // 清空当前数据列表
							curr_passenger_dataList = []; // 清空当前数据列表
						}
						curr_dataList.push(temp_dList[i]);
						curr_wendu_dataList.push(temp_wendu_dList[i]);
						curr_passenger_dataList.push(temp_wendu_dList[i]);
					}
				}
				if(!isNotStatic) { // 使用实时数据则
					cataList = cateroiges;
				}
			}
			break;
		case 'week':
			loop = 7;
			build_ele_step = 1; // X轴的间隔
			if(returnMillSeconds(showWeekFirstDay(choosetime)) == returnMillSeconds(showWeekFirstDay(getCurrentTime()))){ // 判断当前选择的天是不是今天
				loop = (new Date().getDay() == 0 ? 7 : new Date().getDay());
			}
			
			// 如果当前选择的时间大于当前系统时间，则图表中不显示对应的数据
			if(returnMillSeconds(showWeekFirstDay(choosetime)) > returnMillSeconds(showWeekFirstDay(getCurrentTime()))){
				cataList.push(choosetime);
				curr_dataList.push(0);
				curr_wendu_dataList.push(0);
				curr_passenger_dataList.push(0);
			} else { // 选择的时间小于或等于当前系统时间的情况
				// 往后台返回的X轴列表中添加周一~周日
				var cList = ["<br/>周一","<br/>周二","<br/>周三","<br/>周四","<br/>周五","<br/>周六","<br/>周日"];
				var choose_first_day = showWeekFirstDay(choosetime); // 获取选择的时间对应周的第一天 
				for(var i=0;i<loop;i++){
					var curr_week = getTimeByDays(choose_first_day,(i)),val = (curr_week.replace(/-/g, '/')).substring(5, (curr_week.replace(/-/g, '/')).length);
					cataList.push(val + cList[i]);
					curr_dataList.push(temp_dList[i]);
					curr_wendu_dataList.push(temp_wendu_dList[i]);
					curr_passenger_dataList.push(temp_wendu_dList[i]);
				}
				if(!isNotStatic) { // 使用实时数据则
					cataList = cateroiges;
				}
			}
			
			// 设置choosetime，内容为图表的居中标题
			centertitle = showWeekFirstDay(choosetime) + "~~" + showWeekLastDay(choosetime);
			break;
		case 'month':
			build_ele_step = 4; // X轴的间隔
			// 如果当前选择的时间大于当前系统时间，则图表中不显示对应的数据
			if(returnMillSeconds(choosetime) > returnMillSeconds(getCurrentTime())){
				cataList.push(choosetime);
				curr_dataList.push(0);
				curr_wendu_dataList.push(0);
				curr_passenger_dataList.push(0);
			} else { // 选择的时间小于或等于当前系统时间的情况
				loop = returnMonthLastDay(choosetime); // 获取当前选择月份最大天数 
				// 判断选择的月份是不是当前月份，如果是当前月，则只需要显示当前月份的当前天数
				var choose_month = choosetime.substring(5,7); // 获取选择的时间的月份
				var curr_month = getCurrentTime().substring(5,7); // 获取当前时间的月份
				if(parseInt(choose_month) == parseInt(curr_month)) { // 如果选择的月份就是当前月份的话，则只需要显示到今天
					loop = new Date().getDate();
				}
				for(var i=0;i<loop;i++){
					cataList.push(choose_month + "/" + (((i+1)+"").length == 1 ? ("0" + (i+1)) : (i+1)));
					curr_dataList.push(temp_dList[i]);
					curr_wendu_dataList.push(temp_wendu_dList[i]);
					curr_passenger_dataList.push(temp_wendu_dList[i]);
				}
				if(!isNotStatic) { // 使用实时数据则
					cataList = cateroiges;
				}
			}
			centertitle = choosetime.substring(0,7); // 设置图表的居中标题
			break;
		case 'year':
			build_ele_step = 2; // X轴的间隔
			// 如果当前选择的时间大于当前系统时间，则图表中不显示对应的数据
			if(returnMillSeconds(choosetime) > returnMillSeconds(getCurrentTime())){
				cataList.push(choosetime);
				curr_dataList.push(0);
				curr_wendu_dataList.push(0);
				curr_passenger_dataList.push(0);
			} else { // 选择的时间小于或等于当前系统时间的情况
				loop = 12;
				// 判断选择的月份是不是当前月份，如果是当前月，则只需要显示当前月份的当前天数
				var choose_year = choosetime.substring(0,4); // 获取选择的年
				var curr_year = new Date().getFullYear(); // 获取当前年
				if(parseInt(choose_year) == parseInt(curr_year)) { // 如果选择的年就是当前年的话，则只需要显示到当前月
					loop = new Date().getMonth() + 1;
				}
				for(var i=0;i<loop;i++){
					cataList.push(choose_year + "/" + (((i+1)+"").length == 1 ? ("0" + (i+1)) : (i+1)));
					curr_dataList.push(temp_dList[i]);
					curr_wendu_dataList.push(temp_wendu_dList[i]);
					curr_passenger_dataList.push(temp_wendu_dList[i]);
				}
				if(!isNotStatic) { // 使用实时数据则
					cataList = cateroiges;
				}
			}
			
			centertitle = choosetime.substring(0,4); // 设置图表的居中标题
			break;
	}
	
	if(func == "build_electricity"){ // 1.建筑总用电趋势
		choose_data_1 = curr_dataList;
		choose_data_wendu = curr_wendu_dataList;  // 温度
	} else if(func == "build_electricity_passenger"){ // 1.建筑总用电趋势
		choose_data_1 = curr_dataList;
		choose_data_passenger = curr_passenger_dataList;  // 温度
	} else if(func == "build_ele_trend"){ // 2.建筑用电分项
	} else if(func == "air_system_trend"){ // 3.空调系统用电趋势
		choose_data_3 = curr_dataList;
	} else if(func == "air_ele_item"){ // 4.空调系统用电分项
		choose_data_4 = curr_dataList;
	} else if(func == "light_system"){ // 5.照明系统用电趋势
		choose_data_5 = curr_dataList;
	} else if(func == "cool_refrigeration"){ // 6.冷机用电趋势
		choose_data_6 = curr_dataList;
	}
	
	centertitle = '<span style="font-size:12px;">' + centertitle.replace(/-/g,"/") + '</span>'; // 将居中标题字体大小设置为12，并将-替换成/
}

// 1.获取建筑总用电趋势图表			
function getBuildElectricityChart(type,choosetime){
	var temp_dList = [],temp_wendu_dList=[],categoies = [];
	var tfrom = choosetime, tto = getTimeByDays(choosetime,1); // url请求的参数
	switch(type){
		case 'day':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = dayDataList1[Math.floor(Math.random()*5)]; // 随机获取数据列表
				temp_wendu_dList = wendu_dayDataList1[Math.floor(Math.random()*5)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'day';
				var url = baseurl + '/Chart/GetChartData?name=' + name1 + '&id=' + id1 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd1 + '&ipaddress=' + ipaddress + '&port=' + port + dayFormat;
				console.log("1.获取建筑总用电趋势图表day----使用实时数据url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---1.获取建筑总用电趋势图表day---");
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0]; // 随机获取数据列表
							temp_wendu_dList = datalist[1]; // 随机获取数据列表
			           }catch(e){
			                console.log('day获取建筑总用电趋势图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error获取建筑总用电趋势图表day');
					}
				});
			}
			break;
		case 'week':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = weekDataList1[Math.floor(Math.random()*5)]; // 随机获取数据列表
				temp_wendu_dList = wendu_weekDataList1[Math.floor(Math.random()*5)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'week', tfrom = showWeekFirstDay(choosetime), tto = showWeekLastDay(choosetime);
				var url = baseurl + '/Chart/GetChartData?name=' + name1 + '&id=' + id1 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd1 + '&ipaddress=' + ipaddress + '&port=' + port + weekFormat;
				console.log("1.获取建筑总用电趋势图表week--使用实时数据week--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---1.获取建筑总用电趋势图表week---");
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0]; // 随机获取数据列表
							temp_wendu_dList = datalist[1]; // 随机获取数据列表
			           }catch(e){
			                console.log('week获取建筑总用电趋势图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error获取建筑总用电趋势图表week');
					}
				});
			}
			break;
		case 'month':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = monthDataList1[Math.floor(Math.random()*4)]; // 随机获取数据列表
				temp_wendu_dList = wendu_monthDataList1[Math.floor(Math.random()*4)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'month', tfrom = choosetime.substring(0,7) + "-01", tto = showNextMonthFirstDay(choosetime);
				var url = baseurl + '/Chart/GetChartData?name=' + name1 + '&id=' + id1 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd1 + '&ipaddress=' + ipaddress + '&port=' + port + monthFormat;
				console.log("1.获取建筑总用电趋势图表month--使用实时数据month--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---1.获取建筑总用电趋势图表month---");
							console.log(result);
							categoies = result.catalist, temp_dList = datalist[0], temp_wendu_dList = datalist[1]; // 随机获取数据列表
			           }catch(e){
			                console.log('month获取建筑总用电趋势图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error获取建筑总用电趋势图表month');
					}
				});
			}
			break;
		case 'year':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = yearDataList1[Math.floor(Math.random()*2)]; // 随机获取数据列表
				temp_wendu_dList = wendu_yearDataList1[Math.floor(Math.random()*2)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'year', tfrom = choosetime.substring(0,4) + "-01-01", tto = ( parseInt(choosetime.substring(0,4)) + 1 ) + "-01-01";
				var url = baseurl + '/Chart/GetChartData?name=' + name1 + '&id=' + id1 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd1 + '&ipaddress=' + ipaddress + '&port=' + port + yearFormat;
				console.log("1.获取建筑总用电趋势图表year--使用实时数据year--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---1.获取建筑总用电趋势图表year---");
							console.log(result);
							categoies = result.catalist, temp_dList = datalist[0], temp_wendu_dList = datalist[1]; // 随机获取数据列表
			           }catch(e){
			                console.log('year获取建筑总用电趋势图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error获取建筑总用电趋势图表year');
					}
				});
			}
			break;
	}
	// 调用函数修改X轴的坐标列表以及数据列表
	getCataListAndDataList(temp_dList,temp_wendu_dList,categoies,type,choosetime,'build_electricity');
	
	// Build chart
	chart_of_1 = {
		chart : {
			//type : 'spline'
		},
		title : {
			text : centertitle
		},
		credits : {
			enabled : false
		},
		xAxis : {
			labels : {
				step : build_ele_step
			},
			categories : cataList
		},
		yAxis : [ {
			gridLineDashStyle : 'LongDash',
			title: {
			   text : '总用电(kWh)'
			}
		}, {
			opposite : true,
			title : {
			   text : '室外温度(℃)'
			}
		} ],
		tooltip : {
			// backgroundColor : '#64615C', // 提示框背景颜色
			// formatter : function() {
				// return '<b>' + Highcharts.numberFormat(this.y, 2) + ' kWh' +  '</b>'
			// },
			// style: {
				// color: '#FFF', // 提示内容颜色
				// fontSize: '12px', 
				// padding: '8px'
			// },
			// borderWidth : 0
		},
		legend: { 
			enabled:true,
			layout: 'vertical', // 显示形式，支持水平horizontal和垂直vertical
			align: 'right',
			x: -20,
			y: -80,
			itemMarginTop : 8,
			borderWidth: 0
		},
		series : [
				{
					type : 'column',
					name : '总能耗',
					color : '#26C3BC',
					marker : {
						lineWidth : 2,
						lineColor : '#26C3BC',
						fillColor : 'white'
					},				
					tooltip: {
						valueSuffix: 'kWh'
					},
					data : curr_dataList
				},{
					type : 'spline',
					name : '温度',
					color : '#FF86CE',
					marker : {
						lineWidth : 2,
						lineColor : '#FF86CE',
						fillColor : 'white'
					},			
					tooltip: {
						valueSuffix: '℃'
					},
					data : curr_wendu_dataList,
					yAxis : 1
				}]
	};
	$('#build_electricity').highcharts(chart_of_1);
}

// 2.获取  建筑总用电趋势与客流量图表  图表			
function getBuildElectricityPassengerChart(type,choosetime){
	var temp_dList = [],temp_passenger_dList = [],categoies=[];
	var tfrom = choosetime, tto = getTimeByDays(choosetime,1); // url请求的参数
	switch(type){
		case 'day':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = dayDataList1[Math.floor(Math.random()*5)]; // 随机获取数据列表
				temp_passenger_dList = passenger_dayDataList1[Math.floor(Math.random()*5)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'day';
				var url = baseurl + '/Chart/GetChartData?name=' + name2 + '&id=' + id2 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd2 + '&ipaddress=' + ipaddress + '&port=' + port + dayFormat;
				console.log("2.获取  建筑总用电趋势与客流量图表  图表day--使用实时数据day--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---2.获取  建筑总用电趋势与客流量图表  图表day---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0]; // 获取数据列表
							temp_passenger_dList = datalist[1]; // 获取数据列表
			           }catch(e){
			                console.log('day2.获取  建筑总用电趋势与客流量图表  图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error2.获取  建筑总用电趋势与客流量图表  图表day');
					}
				});
			}
			break;
		case 'week':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = weekDataList1[Math.floor(Math.random()*5)]; // 随机获取数据列表
				temp_passenger_dList = passenger_weekDataList1[Math.floor(Math.random()*5)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'week', tfrom = showWeekFirstDay(choosetime), tto = showWeekLastDay(choosetime);
				var url = baseurl + '/Chart/GetChartData?name=' + name2 + '&id=' + id2 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd2 + '&ipaddress=' + ipaddress + '&port=' + port + weekFormat;
				console.log("2.获取  建筑总用电趋势与客流量图表  图表week--使用实时数据week--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---2.获取  建筑总用电趋势与客流量图表  图表week---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0]; // 获取数据列表
							temp_passenger_dList = datalist[1]; // 获取数据列表
			           }catch(e){
			                console.log('week2.获取  建筑总用电趋势与客流量图表  图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error2.获取  建筑总用电趋势与客流量图表  图表week');
					}
				});
			}
			break;
		case 'month':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = monthDataList1[Math.floor(Math.random()*4)]; // 随机获取数据列表
				temp_passenger_dList = passenger_monthDataList1[Math.floor(Math.random()*4)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'month', tfrom = choosetime.substring(0,7) + "-01", tto = showNextMonthFirstDay(choosetime);
				var url = baseurl + '/Chart/GetChartData?name=' + name2 + '&id=' + id2 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd2 + '&ipaddress=' + ipaddress + '&port=' + port + monthFormat;
				console.log("2.获取  建筑总用电趋势与客流量图表  图表month--使用实时数据month--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---2.获取  建筑总用电趋势与客流量图表  图表month---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0]; // 获取数据列表
							temp_passenger_dList = datalist[1]; // 获取数据列表
			           }catch(e){
			                console.log('month2.获取  建筑总用电趋势与客流量图表  图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error2.获取  建筑总用电趋势与客流量图表  图表month');
					}
				});
			}
			break;
		case 'year':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = yearDataList1[Math.floor(Math.random()*2)]; // 随机获取数据列表
				temp_passenger_dList = passenger_yearDataList1[Math.floor(Math.random()*2)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'year', tfrom = choosetime.substring(0,4) + "-01-01", tto = ( parseInt(choosetime.substring(0,4)) + 1 ) + "-01-01";
				var url = baseurl + '/Chart/GetChartData?name=' + name2 + '&id=' + id2 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd2 + '&ipaddress=' + ipaddress + '&port=' + port + yearFormat;
				console.log("2.获取  建筑总用电趋势与客流量图表  图表year--使用实时数据year--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---2.获取  建筑总用电趋势与客流量图表  图表year---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0]; // 获取数据列表
							temp_passenger_dList = datalist[1]; // 获取数据列表
			           }catch(e){
			                console.log('year2.获取  建筑总用电趋势与客流量图表  图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error2.获取  建筑总用电趋势与客流量图表  图表year');
					}
				});
			}
			break;
	}
	// 调用函数修改X轴的坐标列表以及数据列表
	getCataListAndDataList(temp_dList,temp_passenger_dList,categoies,type,choosetime,'build_electricity_passenger');
	
	// Build chart 
	chart_of_passenger = {
		chart : {
			//type : 'spline'
		},
		title : {
			text : centertitle
		},
		credits : {
			enabled : false
		},
		xAxis : {
			labels : {
				step : build_ele_step
			},
			categories : cataList
		},
		// yAxis : {
			// gridLineDashStyle : 'LongDash',
			// title: {
			   // text : ''
			// }
		// },
		yAxis : [ {
			gridLineDashStyle : 'LongDash',
			title: {
			   text : '总用电(kWh)'
			}
			//, min : 0
		}, {
			opposite : true,
			title : {
			   text : '客流量(人)'
			}
			//, min : 0
		} ],
		tooltip : {
		},
		legend: { 
			enabled:true,
			layout: 'vertical', // 显示形式，支持水平horizontal和垂直vertical
			align: 'right',
			x: -20,
			y: -80,
			itemMarginTop : 8,
			borderWidth: 0
		},
		series : [
				{
					type : 'column',
					name : '总能耗',
					color : '#26C3BC',
					marker : {
						lineWidth : 2,
						lineColor : '#26C3BC',
						fillColor : 'white'
					},				
					tooltip: {
						valueSuffix: 'kWh'
					},
					data : curr_dataList
				},{
					type : 'spline',
					name : '客流量',
					color : '#FF86CE',
					marker : {
						lineWidth : 2,
						lineColor : '#FF86CE',
						fillColor : 'white'
					},			
					tooltip: {
						valueSuffix: '(人)'
					},
					data : curr_wendu_dataList,
					yAxis : 1
				}]
	};
	$('#build_electricity_passenger').highcharts(chart_of_passenger);
}

// 获取饼图图表 
function getPieCharts(id,dataList,legendList,choosetype,choosetime){
	var double_num = 7879.7;
	// 设置居中标题
	switch(choosetype){
		case 'day': // 日视图
			double_num = double_num;
			centertitle = choosetime;// 图表居中标题
			break;
		case 'week':
			double_num = double_num * 7;
			// 设置choosetime，内容为图表的居中标题
			centertitle = showWeekFirstDay(choosetime) + "~~" + showWeekLastDay(choosetime);
			break;
		case 'month':
			double_num = double_num * 30;
			centertitle = choosetime.substring(0,7); // 设置图表的居中标题
			break;
		case 'year':
			double_num = double_num * 365;
			centertitle = choosetime.substring(0,4); // 设置图表的居中标题
			break;
	}
	centertitle = '<span style="font-size:12px;">' + centertitle.replace(/-/g,"/") + '</span>'; // 将居中标题字体大小设置为12，并将-替换成/
	
	var seriesList = [];// 最后的数据列表
	var colorList = ['#7CC9DB','#85CF80','#26C3BB','#FCC334','#7FA0D3','#FAAB35'];
	
	for(var i=0;i<dataList.length;i++){
		var tempValue = 0;
		// 如果当前选择的时间大于当前系统时间，则图表中不显示对应的数据
		if(returnMillSeconds(choosetime) > returnMillSeconds(getCurrentTime())){
			tempValue = 0;
		} else { // 选择的时间小于或等于当前系统时间的情况
			tempValue = dataList[i];
		}
		
		seriesList.push({
			name : legendList[i], // 图例名称 
			y : tempValue, // 值
			color : colorList[i] // 颜色
		});
	}
	
	// Build the chart
	chart_of_2 = {
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false
		},
		title: {
			text: centertitle
		},
		tooltip: {
			formatter : function(a,b,c,d) {
				console.log("1double_num---" + double_num * this.y / 100);
				return '<b>' + Highcharts.numberFormat(this.y, 1) + '%，' + Highcharts.numberFormat(double_num * this.y / 100, 1) + 'kWh'  +  '</b>'
			}
		},
		plotOptions: {
			pie: {
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
					enabled :true, // 不在饼图上显示提示信息
					formatter : function() {
						var str = '';
						
						return str + Highcharts.numberFormat(this.y, 1) + "%";
					},
					distance : 5
				},
				showInLegend: true
			}
		},
		legend: { 
			enabled:true,
			layout: 'vertical', // 显示形式，支持水平horizontal和垂直vertical
			align: 'right',
			x: -20,
			y: -20,
			itemMarginTop : 8,
			borderWidth: 0
		},
		series: [{
			type: 'pie',
			name: '',
			size : '100%', // 饼图的大小
			innerSize : '10%', // 饼图中的中间圆圈大小
			data: seriesList
		}]
	};
	if(id=="build_ele_trend"){ // 2.
		choose_data_2 = seriesList;
	} else if(id=="air_ele_item"){// 4.
		choose_data_4 = seriesList;
	}
	
	$('#'+id).highcharts(chart_of_2);
}

// 3.获取建筑用电分项图表		
function getBuildEleTrendChart(choosetype,choosetime){
	var data_ = []; // 数据列表
	var type = choosetype,attribute = 'percents', tfrom = choosetime, tto = getTimeByDays(choosetime,1); // url请求的参数
	if (isNotStatic) { // true则使用静态数据
		data_ = dataList_2[Math.floor(Math.random()*4)]; // 随机获取数据列表
	} else { // 使用实时数据 
		if(type == "day"){
			tfrom = choosetime, tto = getTimeByDays(choosetime,1);
		} else if(type == "week"){
			tfrom = showWeekFirstDay(choosetime), tto = showWeekLastDay(choosetime);
		} else if(type == "month"){
			tfrom = choosetime.substring(0,7) + "-01", tto = showNextMonthFirstDay(choosetime);
		} else if(type == "year"){
			tfrom = choosetime.substring(0,4) + "-01-01", tto = ( parseInt(choosetime.substring(0,4)) + 1 ) + "-01-01";
		}
		var url = baseurl + '/Chart/GetChartData?name=' + name3 + '&id=' + id3 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' +tto + '&ispd=' + ispd3 + '&attribute=' + attribute + '&ipaddress=' + ipaddress + '&port=' + port;
		console.log("3.获取建筑用电分项图表--使用实时数据year--url----" + url);
		$.ajax({
			type : "POST",
			url : url,
			async : false, //同步
			success : function(result) {
	           try{
					var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
					console.log("---3.获取建筑用电分项图表---" + choosetype);
					console.log(datalist);
					for(var i = 0;i < name3.split(",").length; i++){
						data_.push(parseFloat(datalist[i][0]) * 100);
					}
//					data_ = [parseFloat(datalist[0][0]) * 100,parseFloat(datalist[1][0]) * 100,parseFloat(datalist[2][0]) * 100,parseFloat(datalist[3][0]) * 100,parseFloat(datalist[4][0]) * 100]; // 随机获取数据列表
	           }catch(e){
	                console.log(choosetype + '3.获取建筑用电分项图表error'+e);
	           }
			},
			error : function(result) {
				console.log('error3.获取建筑用电分项图表' + choosetype);
			}
		});
	}
	// 获取饼图图表
	getPieCharts("build_ele_trend",data_,legendList_2,choosetype,choosetime);
}

// 4.获取空调系统用电趋势
function getAirEleTrendChart(type,choosetime){
	var temp_dList = [],categoies = [];
	var tfrom = choosetime, tto = getTimeByDays(choosetime,1); // url请求的参数
	switch(type){
		case 'day':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = dayDataList3[Math.floor(Math.random()*5)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'day';
				var url = baseurl + '/Chart/GetChartData?name=' + name4 + '&id=' + id4 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd4 + '&ipaddress=' + ipaddress + '&port=' + port + dayFormat;
				console.log("4.获取空调系统用电趋势day--使用实时数据day--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---4.获取空调系统用电趋势day---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0]; // 获取数据列表
//							temp_dList = dayDataList3[Math.floor(Math.random()*5)]; // 随机获取数据列表
			           }catch(e){
			                console.log('day4.获取空调系统用电趋势error'+e);
			           }
					},
					error : function(result) {
						console.log('error4.获取空调系统用电趋势day');
					}
				});
			}
			break;
		case 'week':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = weekDataList3[Math.floor(Math.random()*5)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'week', tfrom = showWeekFirstDay(choosetime), tto = showWeekLastDay(choosetime);
				var url = baseurl + '/Chart/GetChartData?name=' + name4 + '&id=' + id4 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd4 + '&ipaddress=' + ipaddress + '&port=' + port + weekFormat;
				console.log("4.获取空调系统用电趋势week--使用实时数据week--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---4.获取空调系统用电趋势week---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0]; // 获取数据列表
//							temp_dList = weekDataList3[Math.floor(Math.random()*5)]; // 随机获取数据列表
			           }catch(e){
			                console.log('week4.获取空调系统用电趋势error'+e);
			           }
					},
					error : function(result) {
						console.log('error4.获取空调系统用电趋势week');
					}
				});
			}
			break;
		case 'month':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = monthDataList3[Math.floor(Math.random()*4)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'month', tfrom = choosetime.substring(0,7) + "-01", tto = showNextMonthFirstDay(choosetime);
				var url = baseurl + '/Chart/GetChartData?name=' + name4 + '&id=' + id4 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd4 + '&ipaddress=' + ipaddress + '&port=' + port + monthFormat;
				console.log("4.获取空调系统用电趋势month--使用实时数据month--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---4.获取空调系统用电趋势month---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0]; // 获取数据列表
//							temp_dList = monthDataList3[Math.floor(Math.random()*4)]; // 随机获取数据列表
			           }catch(e){
			                console.log('month4.获取空调系统用电趋势error'+e);
			           }
					},
					error : function(result) {
						console.log('error4.获取空调系统用电趋势month');
					}
				});
			}
			break;
		case 'year':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = yearDataList3[Math.floor(Math.random()*2)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'year', tfrom = choosetime.substring(0,4) + "-01-01", tto = ( parseInt(choosetime.substring(0,4)) + 1 ) + "-01-01";
				var url = baseurl + '/Chart/GetChartData?name=' + name4 + '&id=' + id4 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd4 + '&ipaddress=' + ipaddress + '&port=' + port + yearFormat;
				console.log("4.获取空调系统用电趋势year--使用实时数据year--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---4.获取空调系统用电趋势year---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0]; // 获取数据列表
//							temp_dList = yearDataList3[Math.floor(Math.random()*2)]; // 随机获取数据列表
			           }catch(e){
			                console.log('year4.获取空调系统用电趋势error'+e);
			           }
					},
					error : function(result) {
						console.log('error4.获取空调系统用电趋势year');
					}
				});
			}
			break;
	}
	// 调用函数修改X轴的坐标列表以及数据列表
	getCataListAndDataList(temp_dList,[],categoies,type,choosetime,'air_system_trend');
	
	// Build chart 
	chart_of_3 = {
		chart : {
			type : 'spline'
		},
		title : {
			text : centertitle
		},
		credits : {
			enabled : false
		},
		xAxis : {
			labels : {
				step : build_ele_step
			},
			categories : cataList
		},
		yAxis : {
			gridLineDashStyle : 'LongDash',
			title: {
				text : '总用电(kWh)'
			}
		},
		tooltip : {
			backgroundColor : '#64615C', // 提示框背景颜色
			formatter : function() {
				return '<b>' + Highcharts.numberFormat(this.y, 2) + ' kWh' +  '</b>'
			},
			style: {
				color: '#FFF', // 提示内容颜色
				fontSize: '12px', 
				padding: '8px'
			},
			borderWidth : 0
		},
		legend: {
			enabled : false
		},
		series : [
				{
					name : '',
					color : '#469FE3',
					marker : {
						lineWidth : 2,
						lineColor : '#469FE3',
						fillColor : 'white'
					},
					data : curr_dataList
				}]
	};
	$('#air_system_trend').highcharts(chart_of_3);
}

// 5.获取建筑用电分项图表
function getAirEleItemChart(choosetype,choosetime){
	var data_ = []; // 数据列表
	var type = choosetype, attribute = 'percents', tfrom = choosetime, tto = getTimeByDays(choosetime,1); // url请求的参数
	if (isNotStatic) { // true则使用静态数据
		data_ = dataList_4[Math.floor(Math.random()*4)]; // 随机获取数据列表
	} else { // 使用实时数据 
		if(type == "day"){
			tfrom = choosetime, tto = getTimeByDays(choosetime,1);
		} else if(type == "week"){
			tfrom = showWeekFirstDay(choosetime), tto = showWeekLastDay(choosetime);
		} else if(type == "month"){
			tfrom = choosetime.substring(0,7) + "-01", tto = showNextMonthFirstDay(choosetime);
		} else if(type == "year"){
			tfrom = choosetime.substring(0,4) + "-01-01", tto = ( parseInt(choosetime.substring(0,4)) + 1 ) + "-01-01";
		}
		var url = baseurl + '/Chart/GetChartData?name=' + name5 + '&id=' + id5 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd5 + '&attribute=' + attribute + '&ipaddress=' + ipaddress + '&port=' + port;
		console.log("5.获取建筑用电分项图表--使用实时数据year--url----" + url);
		$.ajax({
			type : "POST",
			url : url,
			async : false, //同步
			success : function(result) {
	           try{
					var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
					console.log("---5.获取建筑用电分项图表---" + choosetype);
					console.log(datalist);
					for(var i = 0;i < name5.split(",").length; i++){
						data_.push(parseFloat(datalist[i][0]) * 100);
					}
//					data_ = dataList_4[Math.floor(Math.random()*4)]; // 随机获取数据列表
	           }catch(e){
	                console.log(choosetype + '5.获取建筑用电分项图表error'+e);
	           }
			},
			error : function(result) {
				console.log('error5.获取建筑用电分项图表' + choosetype);
			}
		});
	}
	// 获取饼图图表
	getPieCharts("air_ele_item",data_,legendList_4,choosetype,choosetime);
}

// 6.获取照明系统图表
function getLightSystemChart(type,choosetime){
	var temp_dList = [],categoies = [];
	var tfrom = choosetime, tto = getTimeByDays(choosetime,1); // url请求的参数
	switch(type){
		case 'day':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = dayDataList5[Math.floor(Math.random()*5)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'day';
				var url = baseurl + '/Chart/GetChartData?name=' + name6 + '&id=' + id6 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd6 + '&ipaddress=' + ipaddress + '&port=' + port + dayFormat;
				console.log("6.获取照明系统图表day--使用实时数据day--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---6.获取照明系统图表---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0];
//							temp_dList = dayDataList5[Math.floor(Math.random()*5)]; // 随机获取数据列表
			           }catch(e){
			                console.log('day6.获取照明系统图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error6.获取照明系统图表day');
					}
				});
			}
			break;
		case 'week':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = weekDataList5[Math.floor(Math.random()*5)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'week', tfrom = showWeekFirstDay(choosetime), tto = showWeekLastDay(choosetime);
				var url = baseurl + '/Chart/GetChartData?name=' + name6 + '&id=' + id6 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd6 + '&ipaddress=' + ipaddress + '&port=' + port + weekFormat;
				console.log("6.获取照明系统图表week--使用实时数据week--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---6.获取照明系统图表---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0];
//							temp_dList = weekDataList5[Math.floor(Math.random()*5)]; // 随机获取数据列表
			           }catch(e){
			                console.log('week6.获取照明系统图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error6.获取照明系统图表week');
					}
				});
			}
			break;
		case 'month':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = weekDataList5[Math.floor(Math.random()*5)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'month', tfrom = choosetime.substring(0,7) + "-01", tto = showNextMonthFirstDay(choosetime);
				var url = baseurl + '/Chart/GetChartData?name=' + name6 + '&id=' + id6 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd6 + '&ipaddress=' + ipaddress + '&port=' + port + monthFormat;
				console.log("6.获取照明系统图表month--使用实时数据month--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---6.获取照明系统图表---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0];
//							temp_dList = monthDataList5[Math.floor(Math.random()*5)]; // 随机获取数据列表
			           }catch(e){
			                console.log('month6.获取照明系统图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error6.获取照明系统图表month');
					}
				});
			}
			temp_dList = monthDataList5[Math.floor(Math.random()*4)]; // 随机获取数据列表
			break;
		case 'year':
			if (isNotStatic) { // true则使用静态数据
				temp_dList = yearDataList5[Math.floor(Math.random()*2)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'year', tfrom = choosetime.substring(0,4) + "-01-01", tto = ( parseInt(choosetime.substring(0,4)) + 1 ) + "-01-01";
				var url = baseurl + '/Chart/GetChartData?name=' + name6 + '&id=' + id6 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd6 + '&ipaddress=' + ipaddress + '&port=' + port + yearFormat;
				console.log("6.获取照明系统图表year--使用实时数据year--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---6.获取照明系统图表---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0];
//							temp_dList = yearDataList5[Math.floor(Math.random()*2)]; // 随机获取数据列表
			           }catch(e){
			                console.log('year6.获取照明系统图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error6.获取照明系统图表year');
					}
				});
			}
			break;
	}
	// 调用函数修改X轴的坐标列表以及数据列表
	getCataListAndDataList(temp_dList,[],categoies,type,choosetime,'light_system');
	
	// Build chart 
	chart_of_5 = {
		chart : {
			type : 'spline'
		},
		title : {
			text : centertitle
		},
		credits : {
			enabled : false
		},
		xAxis : {
			labels : {
				step : build_ele_step
			},
			categories : cataList
		},
		yAxis : {
			gridLineDashStyle : 'LongDash',
			title: {
				text : '总用电(kWh)'
			}
		},
		tooltip : {
			backgroundColor : '#64615C', // 提示框背景颜色
			formatter : function() {
				return '<b>' + Highcharts.numberFormat(this.y, 2) + ' kWh' +  '</b>'
			},
			style: {
				color: '#FFF', // 提示内容颜色
				fontSize: '12px', 
				padding: '8px'
			},
			borderWidth : 0
		},
		legend: {
			enabled : false
		},
		series : [
				{
					name : '',
					color : '#5CC8E4',
					lineWidth : 2,
					marker : {
						lineWidth : 2,
						lineColor : '#5CC8E4',
						fillColor : 'white'
					},
					data : curr_dataList
				}]
	};
	$('#light_system').highcharts(chart_of_5);
}

// 7.获取冷机图表
function getCoolRefrigerationChart(type,choosetime){
	var double_num = 7879.7;
	var temp_dList = [],categoies = [];
	var tfrom = choosetime, tto = getTimeByDays(choosetime,1); // url请求的参数
	switch(type){
		case 'day':
			double_num = double_num;
			if (isNotStatic) { // true则使用静态数据
				temp_dList = dayDataList6[Math.floor(Math.random()*5)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'day';
				var url = baseurl + '/Chart/GetChartData?name=' + name7 + '&id=' + id7 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd7 + '&ipaddress=' + ipaddress + '&port=' + port + dayFormat;
				console.log("7.获取冷机图表day--使用实时数据day--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---7.获取冷机图表---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0];
//							temp_dList = dayDataList6[Math.floor(Math.random()*5)]; // 随机获取数据列表
			           }catch(e){
			                console.log('day7.获取冷机图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error7.获取冷机图表day');
					}
				});
			}
			break;
		case 'week':
			double_num = double_num * 7;
			if (isNotStatic) { // true则使用静态数据
				temp_dList = weekDataList6[Math.floor(Math.random()*5)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'week', tfrom = showWeekFirstDay(choosetime), tto = showWeekLastDay(choosetime);
				var url = baseurl + '/Chart/GetChartData?name=' + name7 + '&id=' + id7 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd7 + '&ipaddress=' + ipaddress + '&port=' + port + weekFormat;
				console.log("7.获取冷机图表week--使用实时数据week--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---7.获取冷机图表---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0];
//							temp_dList = weekDataList6[Math.floor(Math.random()*5)]; // 随机获取数据列表
			           }catch(e){
			                console.log('week7.获取冷机图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error7.获取冷机图表week');
					}
				});
			}
			break;
		case 'month':
			double_num = double_num * 30;
			if (isNotStatic) { // true则使用静态数据
				temp_dList = monthDataList6[Math.floor(Math.random()*4)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'month', tfrom = choosetime.substring(0,7) + "-01", tto = showNextMonthFirstDay(choosetime);
				var url = baseurl + '/Chart/GetChartData?name=' + name7 + '&id=' + id7 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd7 + '&ipaddress=' + ipaddress + '&port=' + port + monthFormat;
				console.log("7.获取冷机图表month--使用实时数据month--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---7.获取冷机图表---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0];
//							temp_dList = monthDataList6[Math.floor(Math.random()*4)]; // 随机获取数据列表
			           }catch(e){
			                console.log('month7.获取冷机图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error7.获取冷机图表month');
					}
				});
			}
			break;
		case 'year':
			double_num = double_num * 365;
			if (isNotStatic) { // true则使用静态数据
				temp_dList = yearDataList6[Math.floor(Math.random()*2)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'year', tfrom = choosetime.substring(0,4) + "-01-01", tto = ( parseInt(choosetime.substring(0,4)) + 1 ) + "-01-01";
				var url = baseurl + '/Chart/GetChartData?name=' + name7 + '&id=' + id7 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd7 + '&ipaddress=' + ipaddress + '&port=' + port + yearFormat;
				console.log("7.获取冷机图表year--使用实时数据year--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
			           try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log("---7.获取冷机图表---");
							console.log(datalist);
							categoies = result.catalist; //  X轴信息
							temp_dList = datalist[0];
//							temp_dList = yearDataList6[Math.floor(Math.random()*2)]; // 随机获取数据列表
			           }catch(e){
			                console.log('year7.获取冷机图表error'+e);
			           }
					},
					error : function(result) {
						console.log('error7.获取冷机图表year');
					}
				});
			}
			break;
	}

	// 调用函数修改X轴的坐标列表以及数据列表
	getCataListAndDataList(temp_dList,[],categoies,type,choosetime,'cool_refrigeration');
	
	// Build chart 
	chart_of_6 = {
		chart : {
			type : 'column'
		},
		title : {
			text : centertitle
		},
		credits : {
			enabled : false
		},
		xAxis : {
			labels : {
				step : build_ele_step
			},
			categories : cataList
		},
		yAxis : {
			gridLineDashStyle : 'LongDash',
			title: {
			   text : ''
			}
		},
		tooltip : {
			backgroundColor : '#64615C', // 提示框背景颜色
			formatter : function() {
				console.log(pie_num + "---2double_num---" + pie_num * this.y / 100);
				return '<b>' + Highcharts.numberFormat(this.y, 1) + '%，' + Highcharts.numberFormat(pie_num * this.y / 100, 1) + 'kWh' +  '</b>'
			},
			style: {
				color: '#FFF', // 提示内容颜色
				fontSize: '12px', 
				padding: '8px'
			},
			borderWidth : 0
		},
		legend: {
			enabled : false
		},
		series : [
				{
					name : '',
					color : '#E66E4C',
					marker : {
						lineWidth : 2,
						lineColor : '#E66E4C',
						fillColor : 'white'
					},
					data : curr_dataList
				}]
	};
	$('#cool_refrigeration').highcharts(chart_of_6);
}

// 文本框选择时间对比
function comparetime(id,func) {
	var choose_type = 'day'; // 对比类型
	var model_time_ = ''; // 对应图表的选择时间
	if(func == "build_electricity"){ // 1.建筑总用电趋势与室外温度
		choose_type = choose_type_1;
		model_time_ = choose_time_1;
	} else if(func == "build_electricity_passenger"){ // 1.建筑总用电趋势与客流量
		choose_type = choose_type_passenger;
		model_time_ = choose_time_passenger;
	} else if(func == "build_ele_trend"){ // 2.建筑用电分项
		choose_type = choose_type_2;
		model_time_ = choose_time_2;
	} else if(func == "air_system_trend"){ // 3.空调系统用电趋势
		choose_type = choose_type_3;
		model_time_ = choose_time_3;
	} else if(func == "air_ele_item"){ // 4.空调系统用电分项
		choose_type = choose_type_4;
		model_time_ = choose_time_4;
	} else if(func == "light_system"){ // 5.照明系统用电趋势
		choose_type = choose_type_5;
		model_time_ = choose_time_5;
	} else if(func == "cool_refrigeration"){ // 6.冷机用电趋势
		choose_type = choose_type_6;
		model_time_ = choose_time_6;
	}
	
	var dformt = 'yyyy-MM-dd';
	if (choose_type == "day"){
		dformt = 'yyyy-MM-dd';
	} else if (choose_type == "week") {
		dformt = 'yyyy-MM-dd';
	} else if (choose_type == "month") {
		dformt = 'MM';
	} else if (choose_type == "year") {
		dformt = 'yyyy';
	}
	
	WdatePicker({
		el : 'showDateTime',
		dateFmt : dformt,
		onpicked : function(dp) {
			// 具体的对比时间，格式为（yyyy-MM-dd），此时间会传递到后台
			var paretime= dp.cal.getDateStr('yyyy-MM-dd');
			
			// 显示在页面上的时间
			var showtime= dp.cal.getDateStr(dformt);
			$('#'+id).val(showtime);
			
			getCompareCataListAndDataList(choose_type,func,model_time_,paretime);
			if(func == "build_electricity"){ // 1.建筑总用电趋势
				comparecharts(chart_of_1,'build_electricity','#26C3BC','#469FE3',choose_data_1,new_compareList,new_wendu_compareList,false,paretime);
			} else if(func == "build_electricity_passenger"){ // 2.建筑总用电趋势
				comparecharts(chart_of_passenger,'build_electricity_passenger','#26C3BC','#469FE3',choose_data_1,new_compareList,new_passenger_compareList,false,paretime);
			} else if(func == "build_ele_trend"){ // 3.建筑用电分项
				var bool = (returnMillSeconds(paretime) == returnMillSeconds(getCurrentTime()) ? true : false);
				comparecharts(chart_of_6,'build_ele_trend','#469FE3','#FB8317',choose_data_2,new_compareList,[],bool,paretime);
			} else if(func == "air_system_trend"){ // 4.空调系统用电趋势
				comparecharts(chart_of_3,'air_system_trend','#469FE3','#FB8317',choose_data_3,new_compareList,[],false,paretime);
			} else if(func == "air_ele_item"){ // 5.空调系统用电分项
				var bool = (returnMillSeconds(paretime) == returnMillSeconds(getCurrentTime()) ? true : false);
				comparecharts(chart_of_6,'air_ele_item','#469FE3','#FB8317',choose_data_4,new_compareList,[],bool,paretime);
			} else if(func == "light_system"){ // 6.照明系统用电趋势
				comparecharts(chart_of_5,'light_system','#5CC8E4','#FAAB35',choose_data_5,new_compareList,[],false,paretime);
			} else if(func == "cool_refrigeration"){ // 7.冷机用电趋势
				comparecharts(chart_of_6,'cool_refrigeration','#E66E4C','#1AADCE',choose_data_6,new_compareList,[],false,paretime);
			}
			
		}
	});
}



var new_xAxis = []; // 对比后的x轴的列表
var new_compareList = []; // 对比后的数据列表
var new_wendu_compareList = []; // 对比后的温度数据列表
var new_passenger_compareList = []; // 对比后的温度数据列表
var temp_compare_list = []; // 对比后的数据列表
var new_legend = ''; // 存放对比图表的图例

// 修改对比后的X轴的坐标以及对比的数据列表
// choosedate通过日周月年选择得到的数据列表
function getCompareCataListAndDataList(type,func,choosedate,comparetime){
	var dayList = [],weekList = [],monthList = [],yearList = [],wendu_dayList = [],wendu_weekList = [],wendu_monthList = [],wendu_yearList = [],passenger_dayList = [],passenger_weekList = [],passenger_monthList = [],passenger_yearList = [],temp_choose_data = [],temp_choose_wendu_data = [],temp_choose_passenger_data = [];
	
	wendu_dayList = wendu_dayDataList1; 
	wendu_weekList = wendu_weekDataList1; 
	wendu_monthList = wendu_monthDataList1; 
	wendu_yearList = wendu_yearDataList1; 
	
	passenger_dayList = passenger_dayDataList1; 
	passenger_weekList = passenger_weekDataList1; 
	passenger_monthList = passenger_monthDataList1; 
	passenger_yearList = passenger_yearDataList1; 
	
	var name1 = 'electricity,t_oa,flow_passenger',id1 = 'total,outdoor_1,indoor_1', ispd1 = '0,0,0', attribute = '';
	if(func == "build_electricity"){ // 1.建筑总用电趋势
		dayList = dayDataList1; 
		weekList = weekDataList1; 
		monthList = monthDataList1; 
		yearList = yearDataList1; 
		
		name1 = 'electricity,t_oa,flow_passenger',id1 = 'total,outdoor_1,indoor_1', ispd1 = '0,0,0', attribute = '';
		temp_choose_data = choose_data_1; // 通过日周月年选择得到的数据列表
		temp_choose_wendu_data = choose_data_wendu; // 通过日周月年选择得到的数据列表
	} else if(func == "build_electricity_passenger"){ // 2.建筑总用电趋势与客流量
		dayList = dayDataList1; 
		weekList = weekDataList1; 
		monthList = monthDataList1; 
		yearList = yearDataList1; 
		name1 = 'electricity,t_oa,flow_passenger',id1 = 'total,outdoor_1,indoor_1', ispd1 = '0,0,0', attribute = '';
		temp_choose_data = choose_data_1; // 通过日周月年选择得到的数据列表
		temp_choose_passenger_data = choose_data_passenger; // 通过日周月年选择得到的数据列表
	} else if(func == "build_ele_trend"){ // 3.建筑用电分项
		dayList = dataList_2; 
		weekList = dataList_2; 
		monthList = dataList_2; 
		yearList = dataList_2;
		name1 = 'electricity,electricity,electricity,electricity',id1 = 'lighting,hvac,elevator,others', ispd1 = '1,1,1,1,', attribute = 'percents'; // 3.建筑用电分项图表
		temp_choose_data = choose_data_2; // 通过日周月年选择得到的数据列表
	} else if(func == "air_system_trend"){ // 4.空调系统用电趋势
		dayList = dayDataList3; 
		weekList = weekDataList3; 
		monthList = monthDataList3; 
		yearList = yearDataList3; 
		name1 = 'electricity,t_oa,flow_passenger',id1 = 'hvac,outdoor_1,indoor_1', ispd1 = '0,0,0', attribute = '';
		temp_choose_data = choose_data_3; // 通过日周月年选择得到的数据列表
	} else if(func == "air_ele_item"){ // 5.空调系统用电分项
		dayList = dataList_4; 
		weekList = dataList_4; 
		monthList = dataList_4; 
		yearList = dataList_4; 
		name1 = 'electricity,electricity,electricity,electricity,electricity,electricity',id1 = 'chiller,chwp,ct,ahu,cwp,hvac_others', ispd1 = '1,1,1,1,1,1,', attribute = 'percents'; // 5.空调系统用电分项
		temp_choose_data = choose_data_4; // 通过日周月年选择得到的数据列表
	} else if(func == "light_system"){ // 6.照明系统用电趋势
		dayList = dayDataList5; 
		weekList = weekDataList5; 
		monthList = monthDataList5; 
		yearList = yearDataList5; 
		name1 = 'electricity,t_oa,flow_passenger',id1 = 'lighting,outdoor_1,indoor_1', ispd1 = '0,0,0', attribute = '';
		temp_choose_data = choose_data_5; // 通过日周月年选择得到的数据列表
	} else if(func == "cool_refrigeration"){ // 7.冷机用电趋势
		dayList = dayDataList6; 
		weekList = weekDataList6; 
		monthList = monthDataList6; 
		yearList = yearDataList6; 
		name1 = 'electricity,t_oa,flow_passenger',id1 = 'chiller,outdoor_1,indoor_1', ispd1 = '0,0,0', attribute = '';
		temp_choose_data = choose_data_6; // 通过日周月年选择得到的数据列表
	}
	temp_compare_list = [];
	temp_compare_wendu_list = [];
	temp_compare_passenger_list = [];
	
	pie_temp_num = 7879.7;
	switch(type){
		case 'day':
			pie_num = pie_temp_num;
			if (isNotStatic) { // true则使用静态数据
				temp_compare_list = dayList[Math.floor(Math.random()*5)]; // 随机获取数据列表
				temp_compare_wendu_list = wendu_dayList[Math.floor(Math.random()*5)]; // 随机获取数据列表
				temp_compare_passenger_list = passenger_dayList[Math.floor(Math.random()*5)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'day',tfrom = comparetime, tto = getTimeByDays(comparetime,1); // 1.建筑总用电趋势与室外温度
				var url = baseurl + '/Chart/GetChartData?name=' + name1 + '&id=' + id1 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd1 + '&attribute=' + attribute + '&ipaddress=' + ipaddress + '&port=' + port + dayFormat;
				console.log("1.获取建筑总用电趋势图表day--compare使用实时数据day--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
					   try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log(datalist);
							if(func == "build_ele_trend" || func == "air_ele_item"){ // 3.建筑用电分项和5.空调系统用电分项zzxtest
								var chang = (func == "build_ele_trend") ? name3.split(",").length : name5.split(",").length;
								for(var k=0;k<chang;k++){
									temp_compare_list.push(parseFloat(datalist[k][0]) * 100);
								}
								temp_compare_wendu_list = temp_compare_list; // 随机获取数据列表
								temp_compare_passenger_list = temp_compare_list; // 随机获取数据列表
							} else {
								temp_compare_list = datalist[0]; // 随机获取数据列表
								temp_compare_wendu_list = datalist[1]; // 随机获取数据列表
								temp_compare_passenger_list = datalist[2]; // 随机获取数据列表
							}
					   }catch(e){
							console.log('day获取建筑总用电趋势图表error---compare'+e);
					   }
					},
					error : function(result) {
						console.log('error获取建筑总用电趋势图表day---compare');
					}
				});
			}
			new_legend = choosedate + "," + comparetime; // 图例
			centertitle = choosedate + "与" + comparetime; // 居中标题
			break;
		case 'week':
			pie_num = pie_temp_num * 7;
			if (isNotStatic) { // true则使用静态数据
				temp_compare_list = dayList[Math.floor(Math.random()*5)]; // 随机获取数据列表
				temp_compare_wendu_list = wendu_dayList[Math.floor(Math.random()*5)]; // 随机获取数据列表
				temp_compare_passenger_list = passenger_dayList[Math.floor(Math.random()*5)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'week', tfrom = showWeekFirstDay(comparetime), tto = showWeekLastDay(comparetime); // 1.建筑总用电趋势与室外温度
				var url = baseurl + '/Chart/GetChartData?name=' + name1 + '&id=' + id1 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd1 + '&attribute=' + attribute + '&ipaddress=' + ipaddress + '&port=' + port + weekFormat;
				console.log("1.获取建筑总用电趋势图表week--compare使用实时数据week--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
					   try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log(datalist);
							if(func == "build_ele_trend" || func == "air_ele_item"){ // 3.建筑用电分项和5.空调系统用电分项zzxtest
								var chang = (func == "build_ele_trend") ? name3.split(",").length : name5.split(",").length;
								for(var k=0;k<chang;k++){
									temp_compare_list.push(parseFloat(datalist[k][0]) * 100);
								}
								temp_compare_wendu_list = temp_compare_list; // 随机获取数据列表
								temp_compare_passenger_list = temp_compare_list; // 随机获取数据列表
							} else {
								temp_compare_list = datalist[0]; // 随机获取数据列表
								temp_compare_wendu_list = datalist[1]; // 随机获取数据列表
								temp_compare_passenger_list = datalist[2]; // 随机获取数据列表
							}
					   }catch(e){
							console.log('week获取建筑总用电趋势图表error---compare'+e);
					   }
					},
					error : function(result) {
						console.log('error获取建筑总用电趋势图表week---compare');
					}
				});
			}
			new_legend = showWeekFirstDay(choosedate) + "~~" + showWeekLastDay(choosedate) + "," + showWeekFirstDay(comparetime) + "~~" + showWeekLastDay(comparetime); // 图例
			centertitle = showWeekFirstDay(choosedate) + "~~" + showWeekLastDay(choosedate) + "与" + showWeekFirstDay(comparetime) + "~~" + showWeekLastDay(comparetime);; // 居中标题
			break;
		case 'month':
			pie_num = pie_temp_num * 30;
			if (isNotStatic) { // true则使用静态数据
				temp_compare_list = monthList[Math.floor(Math.random()*4)]; // 随机获取数据列表
				temp_compare_wendu_list = wendu_monthList[Math.floor(Math.random()*4)]; // 随机获取数据列表
				temp_compare_passenger_list = passenger_monthList[Math.floor(Math.random()*4)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'month', tfrom = comparetime.substring(0,7) + "-01", tto = showNextMonthFirstDay(comparetime); // 1.建筑总用电趋势与室外温度
				var url = baseurl + '/Chart/GetChartData?name=' + name1 + '&id=' + id1 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd1 + '&attribute=' + attribute + '&ipaddress=' + ipaddress + '&port=' + port + monthFormat;
				console.log("1.获取建筑总用电趋势图表month--compare使用实时数据month--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
					   try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log(datalist);
							if(func == "build_ele_trend" || func == "air_ele_item"){ // 3.建筑用电分项和5.空调系统用电分项zzxtest
								var chang = (func == "build_ele_trend") ? name3.split(",").length : name5.split(",").length;
								for(var k=0;k<chang;k++){
									temp_compare_list.push(parseFloat(datalist[k][0]) * 100);
								}
								temp_compare_wendu_list = temp_compare_list; // 随机获取数据列表
								temp_compare_passenger_list = temp_compare_list; // 随机获取数据列表
							} else {
								temp_compare_list = datalist[0]; // 随机获取数据列表
								temp_compare_wendu_list = datalist[1]; // 随机获取数据列表
								temp_compare_passenger_list = datalist[2]; // 随机获取数据列表
							}
					   }catch(e){
							console.log('month获取建筑总用电趋势图表error---compare'+e);
					   }
					},
					error : function(result) {
						console.log('error获取建筑总用电趋势图表month---compare');
					}
				});
			}
			new_legend = choosedate.substring(0,7) + "," + comparetime.substring(0,7); // 图例
			centertitle = choosedate.substring(0,7) + "与" + comparetime.substring(0,7); // 设置图表的居中标题
			break;
		case 'year':
			pie_num = pie_temp_num * 365;
			if (isNotStatic) { // true则使用静态数据
				temp_compare_list = yearList[Math.floor(Math.random()*2)]; // 随机获取数据列表
				temp_compare_wendu_list = wendu_yearList[Math.floor(Math.random()*2)]; // 随机获取数据列表
				temp_compare_passenger_list = passenger_yearList[Math.floor(Math.random()*2)]; // 随机获取数据列表
			} else { // 使用实时数据 
				type = 'year', tfrom = comparetime.substring(0,4) + "-01-01", tto = ( parseInt(comparetime.substring(0,4)) + 1 ) + "-01-01"; // 1.建筑总用电趋势与室外温度
				var url = baseurl + '/Chart/GetChartData?name=' + name1 + '&id=' + id1 + '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd=' + ispd1 + '&attribute=' + attribute + '&ipaddress=' + ipaddress + '&port=' + port + yearFormat;
				console.log("1.获取建筑总用电趋势图表year--compare使用实时数据year--url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false, //同步
					success : function(result) {
					   try{
							var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							console.log(datalist);
							if(func == "build_ele_trend" || func == "air_ele_item"){ // 3.建筑用电分项和5.空调系统用电分项zzxtest
								var chang = (func == "build_ele_trend") ? name3.split(",").length : name5.split(",").length;
								for(var k=0;k<chang;k++){
									temp_compare_list.push(parseFloat(datalist[k][0]) * 100);
								}
								temp_compare_wendu_list = temp_compare_list; // 随机获取数据列表
								temp_compare_passenger_list = temp_compare_list; // 随机获取数据列表
							} else {
								temp_compare_list = datalist[0]; // 随机获取数据列表
								temp_compare_wendu_list = datalist[1]; // 随机获取数据列表
								temp_compare_passenger_list = datalist[2]; // 随机获取数据列表
							}
					   }catch(e){
							console.log('year获取建筑总用电趋势图表error---compare'+e);
					   }
					},
					error : function(result) {
						console.log('error获取建筑总用电趋势图表year---compare');
					}
				});
			}
			new_legend = choosedate.substring(0,4) + "," + comparetime.substring(0,4); // 图例
			centertitle = choosedate.substring(0,4) + "与" + comparetime.substring(0,4); // 设置图表的居中标题
			break;
	}
	
	if (isNotStatic) { // true则使用静态数据
		if(func == "build_ele_trend"){ // 3.建筑用电分项
			temp_compare_list = dataList_2[Math.floor(Math.random()*4)]; // 随机获取数据列表
			temp_compare_wendu_list = temp_compare_list; // 随机获取数据列表
			temp_compare_passenger_list = temp_compare_list; // 随机获取数据列表
		} else if(func == "air_ele_item"){ // 5.空调系统用电分项
			temp_compare_list = dataList_4[Math.floor(Math.random()*4)]; // 随机获取数据列表
			temp_compare_wendu_list = temp_compare_list; // 随机获取数据列表
			temp_compare_passenger_list = temp_compare_list; // 随机获取数据列表
		}
	}
	
	new_compareList = []; // 当前数据列表
	new_wendu_compareList = []; // 当前数据列表
	new_passenger_compareList = []; // 当前数据列表
	new_xAxis = [];// x轴列表数据
	switch(type){
		case 'day': // 日视图
			loop = 24;
			build_ele_step = 1; // X轴的间隔
			if(comparetime == getCurrentTime() && loop >= new Date().getHours()){
				loop = new Date().getHours() + 1;
			}
			if (choosedate == comparetime){ // 如果对比时间和通过日周月年选择的时间一致，则将数据列表设置为一致
				temp_compare_list = temp_choose_data; 
				temp_compare_wendu_list = temp_choose_wendu_data; 
				temp_compare_passenger_list = temp_choose_passenger_data; 
			} 
				
			// 如果当前选择的时间大于当前系统时间，则图表中不显示对应的数据
			if(returnMillSeconds(comparetime) > returnMillSeconds(getCurrentTime())){
				new_xAxis.push(comparetime);
				new_compareList.push(0);
				new_wendu_compareList.push(0);
				new_passenger_compareList.push(0);
			} else { // 选择的时间小于或等于当前系统时间的情况
				new_compareList = temp_compare_list; // 当前数据列表
				new_wendu_compareList = temp_compare_wendu_list; // 当前数据列表
				new_passenger_compareList = temp_compare_passenger_list; // 当前数据列表
				if(func == "build_ele_trend"){ // 3.建筑用电分项
					loop = name3.split(",").length;
				} else if(func == "air_ele_item"){ // 5.空调系统用电分项
					loop = name5.split(",").length;
				}
				for(var i=0;i<loop;i++){
					new_xAxis.push( i + "" ) ;
					if(comparetime == getCurrentTime() && loop >= new Date().getHours()){
						if(i == 0){
							new_compareList = []; // 清空当前数据列表
							new_wendu_compareList = []; // 清空当前数据列表
							new_passenger_compareList = []; // 清空当前数据列表
						}
						new_compareList.push(temp_compare_list[i]);
						new_wendu_compareList.push(temp_compare_wendu_list[i]);
						new_passenger_compareList.push(temp_compare_passenger_list[i]);
					}
				}
			}
			break;
		case 'week':
			loop = 7;
			build_ele_step = 1; // X轴的间隔
			
			// 如果当前选择的时间大于当前系统时间，则图表中不显示对应的数据
			if(returnMillSeconds(showWeekFirstDay(comparetime)) > returnMillSeconds(getCurrentTime())){
				new_xAxis.push(comparetime);
				new_compareList.push(0);
				new_wendu_compareList.push(0);
				new_passenger_compareList.push(0);
			} else { // 选择的时间小于或等于当前系统时间的情况
				// 往后台返回的X轴列表中添加周一~周日
				var cList = ["周一","周二","周三","周四","周五","周六","周日"];
				var choose_first_day = showWeekFirstDay(comparetime); // 获取选择的时间对应周的第一天 
				
				if(returnMillSeconds(showWeekFirstDay(comparetime)) > returnMillSeconds(showWeekFirstDay(choosedate))){ // 对比时间大于选择时间
					loop = 7;
					if(returnMillSeconds(comparetime) >= returnMillSeconds(getCurrentTime()))
						loop = returnDayInWeek(comparetime);
					
					if(func == "build_ele_trend"){ // 3.建筑用电分项
						loop = name3.split(",").length;
					} else if(func == "air_ele_item"){ // 5.空调系统用电分项
						loop = name5.split(",").length;
					}
					for(var i=0;i<loop;i++){
						new_xAxis.push(cList[i]);
						new_compareList.push(temp_compare_list[i]);
						new_wendu_compareList.push(temp_compare_wendu_list[i]);
						new_passenger_compareList.push(temp_compare_passenger_list[i]);
					}
					// x轴数据
					for(var i=0;i<7;i++){
						new_xAxis.push(cList[i]);
					}
				} else if(returnMillSeconds(showWeekFirstDay(comparetime)) < returnMillSeconds(showWeekFirstDay(choosedate))){ // 对比时间小于选择时间
					loop = 7;
					if(func == "build_ele_trend"){ // 3.建筑用电分项
						loop = name3.split(",").length;
					} else if(func == "air_ele_item"){ // 5.空调系统用电分项
						loop = name5.split(",").length;
					}
					// x轴数据
					for(var i=0;i<loop;i++){
						new_xAxis.push(cList[i]);
						new_compareList.push(temp_compare_list[i]);
						new_wendu_compareList.push(temp_compare_wendu_list[i]);
						new_passenger_compareList.push(temp_compare_passenger_list[i]);
					}
				} else if(returnMillSeconds(showWeekFirstDay(comparetime)) == returnMillSeconds(showWeekFirstDay(choosedate))){ // 判断当前选择的天对应周的第一天是否一致
					loop = 7;
					temp_compare_list = temp_choose_data; 
					temp_compare_wendu_list = temp_choose_wendu_data; 
					temp_compare_passenger_list = temp_choose_passenger_data; 
					
					if(func == "build_ele_trend"){ // 3.建筑用电分项
						loop = name3.split(",").length;
					} else if(func == "air_ele_item"){ // 5.空调系统用电分项
						loop = name5.split(",").length;
					}
					for(var i=0;i<loop;i++){
						new_xAxis.push(cList[i]);
						new_compareList.push(temp_compare_list[i]);
						new_wendu_compareList.push(temp_compare_wendu_list[i]);
						new_passenger_compareList.push(temp_compare_passenger_list[i]);
					}
				}
			}
			break;
		case 'month':
			build_ele_step = 4; // X轴的间隔
			// 如果当前选择的时间大于当前系统时间，则图表中不显示对应的数据
			if(returnMillSeconds(comparetime) > returnMillSeconds(getCurrentTime())){
				new_xAxis.push(comparetime);
				new_compareList.push(0);
				new_wendu_compareList.push(0);
				new_passenger_compareList.push(0);
			} else { // 选择的时间小于或等于当前系统时间的情况
				loop = returnMonthLastDay(comparetime); // 获取当前选择月份最大天数
				
				// 判断选择的月份是不是当前月份，如果是当前月，则只需要显示当前月份的当前天数
				var choose_month = comparetime.substring(5,7); // 获取选择的时间的月份
				var curr_month = getCurrentTime().substring(5,7); // 获取当前时间的月份
				
				if(parseInt(choose_month) == parseInt(choosedate.substring(5,7))) { // 如果选择的月份就是当前月份的话，则只需要显示到今天
					temp_compare_list = temp_choose_data;
					temp_compare_wendu_list = temp_choose_wendu_data;
					temp_compare_passenger_list = temp_choose_passenger_data;
				}
				
				if(parseInt(choose_month) == parseInt(curr_month)) { // 对比月为当前月
					loop = new Date().getDate();
					xloop = returnMonthLastDay(choosedate);
					
					if(func == "build_ele_trend"){ // 3.建筑用电分项
						loop = name3.split(",").length;
					} else if(func == "air_ele_item"){ // 5.空调系统用电分项
						loop = name5.split(",").length;
					}
					for(var i=0;i<loop;i++){
						new_compareList.push(temp_compare_list[i]);
						new_wendu_compareList.push(temp_compare_wendu_list[i]);
						new_passenger_compareList.push(temp_compare_passenger_list[i]);
					}
					for(var i=0;i<xloop;i++){
						new_xAxis.push((((i+1)+"").length == 1 ? ("0" + (i+1)) : (i+1)));
					}
				} else {
						 
					var c_num = returnMonthLastDay(choosedate); // 获取当前选择月份最大天数
					var p_num = returnMonthLastDay(comparetime); // 获取对比月份最大天数
					var xloop = 30; // x轴循环次数
					if(c_num < p_num){ // 30<31，根据天数来循环处理数据
						loop = c_num;
						if(func == "build_ele_trend"){ // 3.建筑用电分项
							loop = name3.split(",").length;
						} else if(func == "air_ele_item"){ // 5.空调系统用电分项
							loop = name5.split(",").length;
						}
						xloop = p_num;
						for(var i=0;i<xloop;i++){
							new_compareList.push(temp_compare_list[i]);
							new_wendu_compareList.push(temp_compare_wendu_list[i]);
							new_passenger_compareList.push(temp_compare_passenger_list[i]);
							new_xAxis.push((((i+1)+"").length == 1 ? ("0" + (i+1)) : (i+1)));
						}
					} else if(c_num > p_num){ // 31<30，根据天数来循环处理数据
						loop = p_num;
						xloop = c_num;
						if(func == "build_ele_trend"){ // 3.建筑用电分项
							loop = name3.split(",").length;
						} else if(func == "air_ele_item"){ // 5.空调系统用电分项
							loop = name5.split(",").length;
						}
						for(var i=0;i<loop;i++){
							new_compareList.push(temp_compare_list[i]);
							new_wendu_compareList.push(temp_compare_wendu_list[i]);
							new_passenger_compareList.push(temp_compare_passenger_list[i]);
						}
						for(var i=0;i<xloop;i++){
							new_xAxis.push((((i+1)+"").length == 1 ? ("0" + (i+1)) : (i+1)));
						}
					} else if(c_num == p_num){ // 31=31，根据天数来循环处理数据
						loop = c_num;
						xloop = c_num;
						if(func == "build_ele_trend"){ // 3.建筑用电分项
							loop = name3.split(",").length;
						} else if(func == "air_ele_item"){ // 5.空调系统用电分项
							loop = name5.split(",").length;
						}
						for(var i=0;i<xloop;i++){
							new_compareList.push(temp_compare_list[i]);
							new_wendu_compareList.push(temp_compare_wendu_list[i]);
							new_passenger_compareList.push(temp_compare_passenger_list[i]);
							new_xAxis.push((((i+1)+"").length == 1 ? ("0" + (i+1)) : (i+1)));
						}
					}
				}
			}
			break;
		case 'year':
			build_ele_step = 2; // X轴的间隔
			// 如果当前选择的时间大于当前系统时间，则图表中不显示对应的数据
			if(returnMillSeconds(comparetime) > returnMillSeconds(getCurrentTime())){
				new_xAxis.push(comparetime);
				new_compareList.push(0);
				new_wendu_compareList.push(0);
				new_passenger_compareList.push(0);
			} else { // 选择的时间小于或等于当前系统时间的情况
				loop = 12;
				// 判断选择的月份是不是当前月份，如果是当前月，则只需要显示当前月份的当前天数
				var choose_year = comparetime.substring(0,4); // 获取对比选择的年
				var curr_year = new Date().getFullYear(); // 获取当前年
				var leftYear = choosedate.substring(0,4); // 选择的年份
				
				if(parseInt(choose_year) == parseInt(curr_year)) { // 如果选择的年就是当前年的话，则只需要显示到当前月
					loop = new Date().getMonth() + 1;
					temp_compare_list = temp_choose_data;
					temp_compare_wendu_list = temp_choose_wendu_data;
					temp_compare_passenger_list = temp_choose_passenger_data;
				}
				
				if(parseInt(choose_year) == parseInt(leftYear)) { // 如果选择的年就是当前年的话，则只需要显示到当前月
					if(func == "build_ele_trend"){ // 3.建筑用电分项
						loop = name3.split(",").length;
					} else if(func == "air_ele_item"){ // 5.空调系统用电分项
						loop = name5.split(",").length;
					}
					temp_compare_list = temp_choose_data;
					temp_compare_wendu_list = temp_choose_wendu_data;
					temp_compare_passenger_list = temp_choose_passenger_data;
				}
				
				for(var i=0;i<loop;i++){
					new_xAxis.push(choose_year + "/" + (((i+1)+"").length == 1 ? ("0" + (i+1)) : (i+1)));
					new_compareList.push(temp_compare_list[i]);
					new_wendu_compareList.push(temp_compare_wendu_list[i]);
					new_passenger_compareList.push(temp_compare_passenger_list[i]);
				}
			}
			break;
	}
}

// 对比图表
function comparecharts(chartObj,renderid,line_color1,line_color2,datalist1,datalist2,new_wendu_compareList,isNotToday,paretime){
	var leList = new_legend.split(","); // 图例列表 
	
	chartObj.chart.renderTo = renderid; // 渲染位置
	chartObj.series = new Array();// 设置对应的数据项
	
	var tempList = [];
	
	if(renderid == "build_electricity"){ // 1.建筑总用电趋势
		chartObj.legend.layout = 'horizontal'; // 显示横向图例
		chartObj.legend.align = 'center'; // 显示横向图例
		chartObj.legend.itemMarginTop = 0; // 显示横向图例
		chartObj.legend.borderWidth = 0; // 显示横向图例
		chartObj.legend.x = 15; // 显示横向图例
		chartObj.legend.y = 10; // 显示横向图例
		
		//centertitle = '<span style="font-size:12px;">' + centertitle.replace(/-/g,"/") + '</span>'; // 将居中标题字体大小设置为12，并将-替换成/
		
		// 能耗1
		tempList.push({
			type : 'column',
			tooltip: {
				valueSuffix: 'kWh'
			},
			name : '<span style="font-size:12px;">' + leList[0].replace(/-/g,"/") + ((renderid == "build_electricity" || renderid == "build_electricity_passenger") ? '总能耗' : '') + '</span>',
			color : line_color1,
			marker : {
				lineWidth : 2,
				lineColor : line_color1,
				symbol : 'circle',
				fillColor : 'white'
			},
			data : datalist1 // 第一个数据列表
		});
		
		// 温度1
		tempList.push({
			type : 'spline',
			tooltip: {
				valueSuffix: '°C'
			},
			name : '<span style="font-size:12px;">' + leList[0].replace(/-/g,"/") + "室外温度" + '</span>',
			color : '#FF86CE',
			marker : {
				lineWidth : 2,
				lineColor : '#FF86CE',
				symbol : 'square',
				fillColor : 'white'
			},
			yAxis : 1,
			data : choose_data_wendu // 第二个数据列表
		});
		
		// 能耗2
		tempList.push({
			type : 'column',
			tooltip: {
				valueSuffix: 'kWh'
			},
			name : '<span style="font-size:12px;">' + leList[1].replace(/-/g,"/") + ((renderid == "build_electricity" || renderid == "build_electricity_passenger") ? '总能耗' : '') + '</span>',
			color : line_color2,
			marker : {
				lineWidth : 2,
				lineColor : line_color2,
				symbol : 'circle',
				fillColor : 'white'
			},
			data : datalist2 // 第二个数据列表
		});
		
		// 温度2
		tempList.push({
			type : 'spline',
			tooltip: {
				valueSuffix: '°C'
			},
			name : '<span style="font-size:12px;">' + leList[1].replace(/-/g,"/") + "室外温度" + '</span>',
			color : '#FAAB35',
			marker : {
				lineWidth : 2,
				lineColor : '#FAAB35',
				symbol : 'square',
				fillColor : 'white'
			},
			yAxis : 1,
			data : new_wendu_compareList // 第二个数据列表
		});
	} else if(renderid == "build_electricity_passenger"){ // 1.建筑总用电趋势 与客流量
		chartObj.legend.layout = 'horizontal'; // 显示横向图例
		chartObj.legend.align = 'center'; // 显示横向图例
		chartObj.legend.itemMarginTop = 0; // 显示横向图例
		chartObj.legend.borderWidth = 0; // 显示横向图例
		chartObj.legend.x = 15; // 显示横向图例
		chartObj.legend.y = 10; // 显示横向图例
		
		tempList.push({
			type : 'column',
			tooltip: {
				valueSuffix: 'kWh'
			},
			name : '<span style="font-size:12px;">' + leList[0].replace(/-/g,"/") + ((renderid == "build_electricity" || renderid == "build_electricity_passenger") ? '总能耗' : '') + '</span>',
			color : line_color1,
			marker : {
				lineWidth : 2,
				lineColor : line_color1,
				symbol : 'circle',
				fillColor : 'white'
			},
			data : datalist1 // 第一个数据列表
		});
		
		tempList.push({
			type : 'column',
			tooltip: {
				valueSuffix: 'kWh'
			},
			name : '<span style="font-size:12px;">' + leList[1].replace(/-/g,"/") + ((renderid == "build_electricity" || renderid == "build_electricity_passenger") ? '总能耗' : '') + '</span>',
			color : line_color2,
			marker : {
				lineWidth : 2,
				lineColor : line_color2,
				symbol : 'circle',
				fillColor : 'white'
			},
			data : datalist2 // 第二个数据列表
		});
		
		
		// 客流量1
		tempList.push({
			tooltip: {
				valueSuffix: '人'
			},
			name : '<span style="font-size:12px;">' + leList[0].replace(/-/g,"/") + "客流量" + '</span>',
			color : '#FF86CE',
			marker : {
				lineWidth : 2,
				lineColor : '#FF86CE',
				symbol : 'square',
				fillColor : 'white'
			},
			yAxis : 1,
			data : choose_data_passenger // 第二个数据列表
		});
		
		// 客流量2
		tempList.push({
			tooltip: {
				valueSuffix: '人'
			},
			name : '<span style="font-size:12px;">' + leList[1].replace(/-/g,"/") + "客流量" + '</span>',
			color : '#FAAB35',
			marker : {
				lineWidth : 2,
				lineColor : '#FAAB35',
				symbol : 'square',
				fillColor : 'white'
			},
			yAxis : 1,
			data : new_wendu_compareList // 第二个数据列表
		});
	} else {

		var series1_list = datalist1;
		var series2_list = datalist2;
		if((renderid == "build_ele_trend" || renderid == "air_ele_item")){ // 3.建筑用电分项 // 5.空调系统用电分项
			var temp_data1 = [],temp_data2 = [];
			
			if (returnMillSeconds(paretime) == returnMillSeconds(getCurrentTime())) { // 对比日期和当前日期一致
				try {
					for ( var a = 0; a < datalist1.length; a++) {
						temp_data1.push((datalist1[a].y == undefined) ? datalist1[a] : datalist1[a].y);
						temp_data2.push((datalist2[a].y == undefined) ? datalist2[a] : datalist2[a].y);
					}
				} catch (e) {
					console.log("error对比日期和当前日期一致----" + renderid);
				}
			} else if(returnMillSeconds(paretime) < returnMillSeconds(getCurrentTime())){ // 对比日期小于当前日期
				try {
					for ( var a = 0; a < datalist1.length; a++) {
						temp_data1.push((datalist1[a].y == undefined) ? datalist1[a] : datalist1[a].y);
					}
					for ( var a = 0; a < datalist2.length; a++) {
						temp_data2.push((datalist2[a].y == undefined) ? datalist2[a] : datalist2[a].y);
					}
				} catch (e) {
					console.log("error对比日期小于当前日期----" + renderid);
				}
			} else if(returnMillSeconds(paretime) > returnMillSeconds(getCurrentTime())){ // 对比日期大于当前日期
				try {
					for ( var a = 0; a < datalist1.length; a++) {
						temp_data1.push((datalist1[a].y == undefined) ? datalist1[a] : datalist1[a].y);
					}
					for ( var a = 0; a < datalist2.length; a++) {
						temp_data2.push(0);
					}
				} catch (e) {
					temp_data1 = datalist1;
					for ( var a = 0; a < datalist1.length; a++) {
						temp_data2.push(0);
					}
					console.log("error对比日期大于当前日期----" + renderid);
				}
			}
			
			series1_list = temp_data1;
			series2_list = temp_data2;

		}

		tempList.push({
			tooltip: {
				valueSuffix: 'kWh'
			},
			name : '<span style="font-size:12px;">' + leList[0].replace(/-/g,"/") + ((renderid == "build_electricity" || renderid == "build_electricity_passenger") ? '总能耗' : '') + '</span>',
			color : line_color1,
			marker : {
				lineWidth : 2,
				lineColor : line_color1,
				symbol : 'circle',
				fillColor : 'white'
			},
			data : series1_list // 第一个数据列表
		});
				
		tempList.push({
			tooltip: {
				valueSuffix: 'kWh'
			},
			name : '<span style="font-size:12px;">' + leList[1].replace(/-/g,"/") + ((renderid == "build_electricity" || renderid == "build_electricity_passenger") ? '总能耗' : '') + '</span>',
			color : line_color2,
			marker : {
				lineWidth : 2,
				lineColor : line_color2,
				symbol : 'circle',
				fillColor : 'white'
			},
			data : series2_list // 第二个数据列表
		});
	
	}
	
	if(renderid == "air_ele_item"){ // 5.空调系统用电分项
		new_xAxis = legendList_4;
		chartObj.xAxis.labels.step = 1; // X轴间隔
	} else if(renderid == "build_ele_trend"){ // 2.建筑用电分项或者4.空调系统用电分项
		new_xAxis = legendList_2;
		chartObj.xAxis.labels.step = 1; // X轴间隔
	} else {
		chartObj.xAxis.labels.step = build_ele_step; // X轴间隔
	}
	
	centertitle = '<span style="font-size:12px;">' + centertitle.replace(/-/g,"/") + '</span>'; // 将居中标题字体大小设置为12，并将-替换成/
	chartObj.title.text = centertitle; // 居中标题
	chartObj.legend.enabled = true; // 显示图例
	chartObj.xAxis.categories = new_xAxis; // X轴
	chartObj.series = tempList; // 将数据列表赋值到图表上
	new Highcharts.Chart(chartObj); // 重新渲染图表 
}