// 以下是样式数组
var list = ['p_low', 'p_middle', 'p_high', 'p_fault'];
var icon_list = ['cus_icon_s', 'cus_icon_m', 'cus_icon_b', 'cus_icon_error'];
var text_list = ['cus_text_s', 'cus_text_m', 'cus_text_b', 'cus_text_error'];
var shu = 1; // 用于模拟店铺编号

// 创建平面图上对应的客流点位
function createPosInfo(name, left, top, incount) {
	var num = 1, data = 0;
	if (incount > 5000) {
		num = 2;
		data = Math.round(incount / 1000 * 10) / 10 + "K";
	} else if (incount > 1000) {
		num = 1;
		data = Math.round(incount / 1000 * 10) / 10 + "K";
	} else if (incount > 1) {
		num = 0;
		data = incount;
//	} else if (incount == 0) {
//		num =3;
	}
	list_i = list[num], icon_i = icon_list[num], text_i = text_list[num];
	
    var $storePos = $("<span/>", {
        class: "cus_div " + list_i,
        id: name
    }).hover(function () {
        $(this).css({"z-index": "200"});
    }, function () {
        $(this).css({"z-index": "100"});
    });
    var $rankIcon = $("<i/>", {
        class: "cus_icon " + icon_i
    });
//    var random = Math.round(incount / 1000 * 10) / 10;
    var $rankNum = $("<span/>", {
        class: "cus_text " + text_i,
//        text : (index == 3) ? "故障" : (random == 0 ? Math.floor(Math.random() * 2) : random) + "K"
        text : (num == 3) ? "故障" : data
    });

    $rankIcon.appendTo($storePos);
    $rankNum.appendTo($storePos);
    $storePos.css({
        "left": left,
        "top": top
    });
    $storePos.appendTo($("#stru_id"));
}

// 渲染“客流统计”图表
function renderPassengerCharts(id, datalist, catalist, color) {
	
	$('#' + id).highcharts({
		chart : {
			type : 'spline',
			backgroundColor : color,
			marginBottom : 5
		},
		title : {
			text : ''
		},
		credits : {
			enabled : false
		},
		xAxis : {
			lineColor: color,// 设置X轴颜色  
			tickPixelInterval:0,
			gridLineDashStyle : 'Solid',
			gridLineWidth : 1, 
			gridLineColor: (id == 'tipChart' ? '#CECECE' : '#BDE5E5'), // 纵轴的曲线颜色
			tickmarkPlacement: 'on', // 曲线点和X轴的点对应
			labels : {
		        enabled: false,
				step : 4 // x轴显示的间隔
			},
			tickWidth: 0,
			categories: catalist
		},
		yAxis : {
			tickColor: color,
			gridLineWidth : 0,
			title: {
			   text : ''
			},
			labels : {
		        enabled: false
			}
//			,
//			min: 0
		},
		legend: {
			enabled : false
		},
		series : [{
			name : (id == "tipChart" ? " " : '<span style="color: black;">客流量</span>'),
			color : '#FFF',
			marker : {
				lineWidth : 2,
				lineColor : '#FFF',
				fillColor : color
			},
			data : datalist
		}]
	});		
	
}

function HashMap() {
    var size = 0; // Map大小
    var entry = new Object(); // 对象
    this.put = function (key, value) { // Map的存put方法
        if (!this.containsKey(key)) {
            size++;
            entry[key] = value;
        }
    };
    this.get = function (key) { // Map取get方法
        return this.containsKey(key) ? entry[key] : null;
    };
    this.remove = function (key) { // Map删除remove方法
        if (this.containsKey(key) && (delete entry[key])) {
            size--;
        }
    };
    this.containsKey = function (key) { // 是否包含Key
        return (key in entry);
    };
    this.containsValue = function (value) { // 是否包含Value
        for (var prop in entry) {
            if (entry[prop] == value) {
                return true;
            }
        }
        return false;
    };
    this.values = function () { // 所有的Value
        var values = new Array();
        for (var prop in entry) {
            values.push(entry[prop]);
        }
        return values;
    };
    this.keys = function () { // 所有的 Key
        var keys = new Array();
        for (var prop in entry) {
            keys.push(prop);
        }
        return keys;
    };
    this.size = function () { // Map size
        return size;
    };
    this.clear = function () { // 清空Map
        size = 0;
        entry = new Object();
    };
};

// 存放店铺点位信息
var customConfig = {
	"B1": [
		{"name" : "正一味",		 "pos" : [{"left" : "234px","top" : "170px"}]},
		{"name" : "四川小吃",		 "pos" : [{"left" : "166px","top" : "187px"}]},
		{"name" : "争鲜寿司",		 "pos" : [{"left" : "124px","top" : "241px"}]},
		{"name" : "拿渡",		     "pos" : [{"left" : "104px","top" : "266px"}]},
		{"name" : "面包新语",		 "pos" : [{"left" : "110px","top" : "295px"}]},
		{"name" : "爱有梅有",		 "pos" : [{"left" : "248px","top" : "200px"}]},

		{"name" : "帝娜朵拉",		 "pos" : [{"left" : "213px","top" : "214px"}]},
		{"name" : "卡门贝尔",		 "pos" : [{"left" : "198px","top" : "240px"}]},
		{"name" : "三元梅园",		 "pos" : [{"left" : "260px","top" : "238px"}]},
		{"name" : "西树泡芙",		 "pos" : [{"left" : "197px","top" : "267px"}]},
		{"name" : "悠悠蛋挞",		 "pos" : [{"left" : "259px","top" : "266px"}]},
		{"name" : "弹丸滋地",		 "pos" : [{"left" : "188px","top" : "296px"}]},
		{"name" : "果子叔叔",		 "pos" : [{"left" : "247px","top" : "296px"}]},
		{"name" : "泰焦鸡",		 "pos" : [{"left" : "304px","top" : "292px"}]},
		{"name" : "艾蒂诺",		 "pos" : [{"left" : "252px","top" : "327px"}]},
		{"name" : "周黑鸭",		 "pos" : [{"left" : "187px","top" : "326px"}]},
		{"name" : "白领生活",		 "pos" : [{"left" : "316px","top" : "325px"}]},
		{"name" : "膳魔师",		 "pos" : [{"left" : "201px","top" : "355px"}]},
		{"name" : "川名堂",		 "pos" : [{"left" : "271px","top" : "356px"}]},
		{"name" : "鲜果时刻",		 "pos" : [{"left" : "268px","top" : "385px"}]},
		{"name" : "香记肉干",		 "pos" : [{"left" : "199px","top" : "383px"}]},

		{"name" : "永辉",		 "pos" : [{"left" : "590px","top" : "233px"}]}
	],
	"1F": [
		{"name" : "百货1F",		 "pos" : [{"left" : "560px","top" : "192px"}]},
		{"name" : "ZARA",		 "pos" : [{"left" : "987px","top" : "128px"}]},
		{"name" : "Adidas-Originals",		 "pos" : [{"left" : "180px","top" : "426px"}]},
		{"name" : "CATALOG",		 "pos" : [{"left" : "131px","top" : "403px"}]},
		{"name" : "LEE",		 "pos" : [{"left" : "119px","top" : "373px"}]},
		{"name" : "LEVI'S",		 "pos" : [{"left" : "114px","top" : "335px"}]},
		{"name" : "SWATCH",		 "pos" : [{"left" : "115px","top" : "299.99px"}]},
		{"name" : "必胜客",		 "pos" : [{"left" : "58px","top" : "216px"}]},
		{"name" : "哈根达斯",		 "pos" : [{"left" : "83px","top" : "195px"}]},
		{"name" : "Tissot",		 "pos" : [{"left" : "195px","top" : "214px"}]},
		{"name" : "BLOVES",		 "pos" : [{"left" : "196px","top" : "246px"}]},
		{"name" : "lapalette",		 "pos" : [{"left" : "190px","top" : "298px"}]},
		{"name" : "SLY",		 "pos" : [{"left" : "238px","top" : "269px"}]},
		{"name" : "Moussy",		 "pos" : [{"left" : "271px","top" : "292px"}]},
		{"name" : "CK JEANS",		 "pos" : [{"left" : "384px","top" : "339px"}]},
		{"name" : "CK Underwear",		 "pos" : [{"left" : "444px","top" : "333px"}]},
		{"name" : "ck手表",		 "pos" : [{"left" : "466px","top" : "307px"}]},
		{"name" : "DISSONA",		 "pos" : [{"left" : "520px","top" : "330px"}]},
		{"name" : "Miss Sixty",		 "pos" : [{"left" : "568px","top" : "302.99px"}]},
		{"name" : "DANIEL HECHTER皮具",		 "pos" : [{"left" : "614px","top" : "320px"}]},
		{"name" : "Gabor",		 "pos" : [{"left" : "662px","top" : "301px"}]},
		{"name" : "ENZO",		 "pos" : [{"left" : "715px","top" : "304px"}]},
		{"name" : "innisfree",		 "pos" : [{"left" : "819px","top" : "263px"}]},
		{"name" : "papabubble",		 "pos" : [{"left" : "968px","top" : "218px"}]},
		{"name" : "kipling",		 "pos" : [{"left" : "994px","top" : "191px"}]},
		{"name" : "CHARLES&KEITH",		 "pos" : [{"left" : "1053px","top" : "173px"}]},
		{"name" : "BOSE",		 "pos" : [{"left" : "1090px","top" : "139px"}]},
		{"name" : "TESIRO",		 "pos" : [{"left" : "1088px","top" : "110px"}]},
		{"name" : "SEPHORA",		 "pos" : [{"left" : "1239px","top" : "89px"}]},
		{"name" : "New Look",		 "pos" : [{"left" : "1274px","top" : "157px"}]},
		{"name" : "Apple",		 "pos" : [{"left" : "1266px","top" : "193px"}]},
		{"name" : "星巴克",		 "pos" : [{"left" : "1247px","top" : "220px"}]},
		{"name" : "CASIO",		 "pos" : [{"left" : "1172px","top" : "217px"}]},
		{"name" : "FILA",		 "pos" : [{"left" : "1122px","top" : "235px"}]},
		{"name" : "Hush puppies",		 "pos" : [{"left" : "1077px","top" : "253px"}]},
		{"name" : "PSALTER",		 "pos" : [{"left" : "1030px","top" : "272px"}]},
		{"name" : "DAZZLE",		 "pos" : [{"left" : "981px","top" : "272px"}]},
		{"name" : "Bouthentique",		 "pos" : [{"left" : "940px","top" : "289px"}]},
		{"name" : "LESS",		 "pos" : [{"left" : "905px","top" : "307px"}]},
		{"name" : "衣架",		 "pos" : [{"left" : "873px","top" : "328px"}]},
		{"name" : "ochirly",		 "pos" : [{"left" : "838px","top" : "342px"}]},
		{"name" : "mjuka",		 "pos" : [{"left" : "800px","top" : "358px"}]},
		{"name" : "LensCrafters",		 "pos" : [{"left" : "750px","top" : "369px"}]},
		{"name" : "皇家",		 "pos" : [{"left" : "768px","top" : "436px"}]},
		{"name" : "Steve Madden",		 "pos" : [{"left" : "714px","top" : "391px"}]},
		{"name" : "Clarks",		 "pos" : [{"left" : "674px","top" : "376px"}]},
		{"name" : "sanse",		 "pos" : [{"left" : "634px","top" : "399px"}]},

		{"name" : "Evisu",		 "pos" : [{"left" : "585.99px","top" : "383.99px"}]},
		{"name" : "Hazzys",		 "pos" : [{"left" : "560px","top" : "405px"}]},
		{"name" : "I DO",		 "pos" : [{"left" : "516px","top" : "382px"}]},
		{"name" : "GUESS",		 "pos" : [{"left" : "444px","top" : "398px"}]},
		{"name" : "BROOKS BROTHERS",		 "pos" : [{"left" : "390px","top" : "414px"}]},
		{"name" : "Tommy Hilfiger",		 "pos" : [{"left" : "331px","top" : "418px"}]},
		{"name" : "Yatlas",		 "pos" : [{"left" : "296px","top" : "446px"}]}
	],
	"2F": [
		{"name" : "百货2F",		 "pos" : [{"left" : "560px","top" : "192px"}]},
		{"name" : "万达宝贝王",		 "pos" : [{"left" : "987px","top" : "128px"}]},
		{"name" : "必胜客",		 "pos" : [{"left" : "88px","top" : "206px"}]},
		{"name" : "HopeShow",		 "pos" : [{"left" : "110px","top" : "312px"}]},
		{"name" : "OSANI",		 "pos" : [{"left" : "109px","top" : "345px"}]},
		{"name" : "SEDATE",		 "pos" : [{"left" : "123px","top" : "377px"}]},
		{"name" : "JDING",		 "pos" : [{"left" : "144px","top" : "409px"}]},
		{"name" : "TUTUANNA",		 "pos" : [{"left" : "170px","top" : "435px"}]},
		{"name" : "UBSKIN",		 "pos" : [{"left" : "228px","top" : "432px"}]},
		{"name" : "觅可国际",		 "pos" : [{"left" : "294px","top" : "443px"}]},
		{"name" : "FOREVER NEW",		 "pos" : [{"left" : "343px","top" : "422px"}]},
		{"name" : "MIKIBANA",		 "pos" : [{"left" : "395px","top" : "405px"}]},
		{"name" : "Donoratico",		 "pos" : [{"left" : "452px","top" : "410px"}]},
		{"name" : "MAT Living Material",		 "pos" : [{"left" : "509px","top" : "387px"}]},
		{"name" : "GILLIVO",		 "pos" : [{"left" : "552px","top" : "412px"}]},
		{"name" : "播",		 "pos" : [{"left" : "591px","top" : "389px"}]},
		{"name" : "JNBY",		 "pos" : [{"left" : "653px","top" : "398px"}]},
		{"name" : "clself",		 "pos" : [{"left" : "714px","top" : "391px"}]},
		{"name" : "MOVEUP",		 "pos" : [{"left" : "754px","top" : "372px"}]},
		{"name" : "imi's",		 "pos" : [{"left" : "805px","top" : "356px"}]},
		{"name" : "Body.ing",		 "pos" : [{"left" : "847px","top" : "336px"}]},
		{"name" : "Naivee",		 "pos" : [{"left" : "875px","top" : "325px"}]},
		{"name" : "JECCI FIVE",		 "pos" : [{"left" : "910px","top" : "311px"}]},
		{"name" : "ONE MORE",		 "pos" : [{"left" : "943px","top" : "293px"}]},
		{"name" : "Ou.",		 "pos" : [{"left" : "986px","top" : "278px"}]},
		{"name" : "Mini Peace",		 "pos" : [{"left" : "1036px","top" : "267px"}]},
		{"name" : "gxg.kids",		 "pos" : [{"left" : "1068px","top" : "249px"}]},
		{"name" : "MQD",		 "pos" : [{"left" : "1099px","top" : "235px"}]},
		{"name" : "KAMINEY",		 "pos" : [{"left" : "1134px","top" : "217px"}]},
		{"name" : "CARRERA",		 "pos" : [{"left" : "1186px","top" : "216px"}]},
		{"name" : "kids land",		 "pos" : [{"left" : "1243px","top" : "219px"}]},
		{"name" : "The Green Party",		 "pos" : [{"left" : "1269px","top" : "184px"}]},
		{"name" : "屈臣氏",		 "pos" : [{"left" : "1267px","top" : "151px"}]},
		{"name" : "KFC",		 "pos" : [{"left" : "1235px","top" : "91px"}]},

		{"name" : "指。秀美甲",		 "pos" : [{"left" : "1000px","top" : "192px"}]},
		{"name" : "Carol",		 "pos" : [{"left" : "958px","top" : "199px"}]},
		{"name" : "LNO",		 "pos" : [{"left" : "928px","top" : "217px"}]},
		{"name" : "Dosail",		 "pos" : [{"left" : "890px","top" : "232px"}]},
		{"name" : "AOJO",		 "pos" : [{"left" : "854px","top" : "246px"}]},
		{"name" : "亮视点",		 "pos" : [{"left" : "831px","top" : "264px"}]},
		{"name" : "海盗船",		 "pos" : [{"left" : "805px","top" : "280px"}]},
		{"name" : "LILY",		 "pos" : [{"left" : "767px","top" : "256px"}]},
		{"name" : "Peace bird(F)",		 "pos" : [{"left" : "738px","top" : "271px"}]},
		{"name" : "林清轩",		 "pos" : [{"left" : "716px","top" : "297px"}]},
		{"name" : "Onitsuka Tiger",		 "pos" : [{"left" : "665px","top" : "312px"}]},
		{"name" : "萱子",		 "pos" : [{"left" : "624px","top" : "293px"}]},
		{"name" : "LALABOBO",		 "pos" : [{"left" : "582px","top" : "312px"}]},
		{"name" : "MIROCO",		 "pos" : [{"left" : "534px","top" : "321px"}]},
		{"name" : "AFU",		 "pos" : [{"left" : "489px","top" : "328px"}]},
		{"name" : "JUCY JUDY",		 "pos" : [{"left" : "429px","top" : "315px"}]},
		{"name" : "JPLW",		 "pos" : [{"left" : "395px","top" : "331px"}]},
		{"name" : "VERO MODA",		 "pos" : [{"left" : "275px","top" : "286px"}]},
		{"name" : "Only",		 "pos" : [{"left" : "219px","top" : "256px"}]},
		{"name" : "BONOBO",		 "pos" : [{"left" : "173px","top" : "217px"}]}

	],
	"3F": [
		{"name" : "百货3F",		 "pos" : [{"left" : "560px","top" : "192px"}]},
		{"name" : "韩潮品象",		 "pos" : [{"left" : "7px","top" : "328px"}]},
		{"name" : "酷乐潮玩",		 "pos" : [{"left" : "84px","top" : "375px"}]},
		{"name" : "EUHO",		 "pos" : [{"left" : "105px","top" : "414px"}]},
		{"name" : "品真阁",		 "pos" : [{"left" : "154px","top" : "431px"}]},
		{"name" : "爱茜茜里",		 "pos" : [{"left" : "225px","top" : "434px"}]},
		{"name" : "NORTH LATITUDE 30",		 "pos" : [{"left" : "288px","top" : "462px"}]},
		{"name" : "FAIRWHALE JEANS",		 "pos" : [{"left" : "343px","top" : "419px"}]},
		{"name" : "dealuna",		 "pos" : [{"left" : "392px","top" : "442px"}]},
		{"name" : "POP MART",		 "pos" : [{"left" : "441px","top" : "413px"}]},
		{"name" : "zippo",		 "pos" : [{"left" : "516px","top" : "397px"}]},
		{"name" : "gxg.jeans",		 "pos" : [{"left" : "557px","top" : "434px"}]},
		{"name" : "peace bird(M)",		 "pos" : [{"left" : "593px","top" : "395px"}]},
		{"name" : "CROQUIS",		 "pos" : [{"left" : "636px","top" : "427px"}]},
		{"name" : "Cabbeen Urban",		 "pos" : [{"left" : "668px","top" : "392px"}]},
		{"name" : "SELECTED",		 "pos" : [{"left" : "719px","top" : "416px"}]},

		{"name" : "MLB",		 "pos" : [{"left" : "750px","top" : "377px"}]},
		{"name" : "PANCOAT",		 "pos" : [{"left" : "801px","top" : "354px"}]},
		{"name" : "Paul Frank",		 "pos" : [{"left" : "868px","top" : "379px"}]},
		{"name" : "CONVERSE",		 "pos" : [{"left" : "876px","top" : "329px"}]},
		{"name" : "crz",		 "pos" : [{"left" : "931px","top" : "351px"}]},
		{"name" : "vans",		 "pos" : [{"left" : "949px","top" : "314px"}]},
		{"name" : "小鲸的匹萨",		 "pos" : [{"left" : "972px","top" : "285px"}]},
		{"name" : "热风",		 "pos" : [{"left" : "1068px","top" : "318px"}]},
		{"name" : "艾派19八3",		 "pos" : [{"left" : "1091px","top" : "274px"}]},
		{"name" : "HOZ",		 "pos" : [{"left" : "1110px","top" : "274px"}]},

		{"name" : "阿吉豆",		 "pos" : [{"left" : "1135px","top" : "228px"}]},
		{"name" : "15MINS",		 "pos" : [{"left" : "1159px","top" : "205px"}]},
		{"name" : "绿行平衡车体验馆",		 "pos" : [{"left" : "1243px","top" : "234px"}]},
		{"name" : "华硕",		 "pos" : [{"left" : "1262px","top" : "207px"}]},
		{"name" : "西遇",		 "pos" : [{"left" : "1285px","top" : "179px"}]},
		{"name" : "音之缘",		 "pos" : [{"left" : "1268px","top" : "147px"}]},
		{"name" : "金鼎轩",		 "pos" : [{"left" : "1241px","top" : "87px"}]},
		{"name" : "启路文具",		 "pos" : [{"left" : "1078px","top" : "149px"}]},
		{"name" : "星空琴行",		 "pos" : [{"left" : "1037px","top" : "167px"}]},
		{"name" : "大玩家",		 "pos" : [{"left" : "965px","top" : "120px"}]},
		{"name" : "奢品汇",		 "pos" : [{"left" : "998px","top" : "196px"}]},
		{"name" : "SAMSUNG",		 "pos" : [{"left" : "961px","top" : "182px"}]},
		{"name" : "CC&DD",		 "pos" : [{"left" : "934px","top" : "211px"}]},
		{"name" : "S.DEER",		 "pos" : [{"left" : "882px","top" : "201px"}]},
		{"name" : "Jack&Jones",		 "pos" : [{"left" : "846px","top" : "231px"}]},
		{"name" : "Bosa Magine",		 "pos" : [{"left" : "732px","top" : "268px"}]},
		{"name" : "木九十",		 "pos" : [{"left" : "712px","top" : "295px"}]},
		{"name" : "B.Duck",		 "pos" : [{"left" : "663px","top" : "308px"}]},
		{"name" : "GXG",		 "pos" : [{"left" : "604px","top" : "296px"}]},
		{"name" : "Cabbeen lifestyle",		 "pos" : [{"left" : "558px","top" : "315px"}]},
		{"name" : "KODO",		 "pos" : [{"left" : "529px","top" : "329px"}]},
		{"name" : "爱丽公主屋",		 "pos" : [{"left" : "503px","top" : "305px"}]},
		{"name" : "THE SHOES BAR",		 "pos" : [{"left" : "446px","top" : "324px"}]},
		{"name" : "crocs",		 "pos" : [{"left" : "393px","top" : "324px"}]},
		{"name" : "萬物",		 "pos" : [{"left" : "264px","top" : "269px"}]},

		{"name" : "OMI",		 "pos" : [{"left" : "230px","top" : "285px"}]},
		{"name" : "deans",		 "pos" : [{"left" : "199px","top" : "272px"}]},
		{"name" : "尚品宅配",		 "pos" : [{"left" : "198px","top" : "245px"}]},
		{"name" : "万仟堂",		 "pos" : [{"left" : "182px","top" : "207px"}]},
		{"name" : "佩霖国际自然养生会所",		 "pos" : [{"left" : "81px","top" : "207px"}]}
	],
	"4F": [
		{"name" : "百货4F",		 "pos" : [{"left" : "560px","top" : "192px"}]},
		{"name" : "三个贵州人",		 "pos" : [{"left" : "71px","top" : "318px"}]},
		{"name" : "望湘园",		 "pos" : [{"left" : "91px","top" : "374px"}]},
		{"name" : "妙MUSE",		 "pos" : [{"left" : "151px","top" : "423px"}]},
		{"name" : "MQ",		 "pos" : [{"left" : "239px","top" : "431px"}]},
		{"name" : "我家",		 "pos" : [{"left" : "317px","top" : "434px"}]},
		{"name" : "H2O水货",		 "pos" : [{"left" : "409px","top" : "426px"}]},
		{"name" : "cold stone",		 "pos" : [{"left" : "510px","top" : "397px"}]},
		{"name" : "巴贝拉",		 "pos" : [{"left" : "567px","top" : "417px"}]},
		{"name" : "留下",		 "pos" : [{"left" : "642px","top" : "404px"}]},
		{"name" : "蓉李记",		 "pos" : [{"left" : "720px","top" : "393px"}]},
		{"name" : "汉拿山",		 "pos" : [{"left" : "842px","top" : "347px"}]},
		{"name" : "天万",		 "pos" : [{"left" : "928px","top" : "316px"}]},
		{"name" : "避风塘",		 "pos" : [{"left" : "1043px","top" : "274px"}]},
		{"name" : "西堤",		 "pos" : [{"left" : "1115px","top" : "242px"}]},
		{"name" : "西贝",		 "pos" : [{"left" : "1263px","top" : "204px"}]},
		{"name" : "云海肴",		 "pos" : [{"left" : "1227px","top" : "98px"}]},
		{"name" : "阿三生煎包",		 "pos" : [{"left" : "1062px","top" : "155px"}]},
		{"name" : "月影法式铁板烧",		 "pos" : [{"left" : "946px","top" : "204px"}]},
		{"name" : "去蜀",		 "pos" : [{"left" : "859px","top" : "233px"}]},
		{"name" : "蜀江烤鱼",		 "pos" : [{"left" : "772px","top" : "263px"}]},
		{"name" : "许留山",		 "pos" : [{"left" : "653px","top" : "301px"}]},

		{"name" : "胡椒厨房",		 "pos" : [{"left" : "491px","top" : "315px"}]},
		{"name" : "星米年糕",		 "pos" : [{"left" : "409px","top" : "316px"}]},
		{"name" : "荷花泰",		 "pos" : [{"left" : "250px","top" : "277px"}]},
		{"name" : "丰和日丽",		 "pos" : [{"left" : "203px","top" : "244px"}]},
		{"name" : "外婆家",		 "pos" : [{"left" : "118px","top" : "207px"}]},
		{"name" : "大歌星KTV",		 "pos" : [{"left" : "969px","top" : "123px"}]}
	],
	"5F": [
		{"name": "百货5F", "pos": [{"left" : "478.99px","top" : "214.99px"}]},
		{"name" : "万达影城",		 "pos" : [{"left" : "907px","top" : "190px"}]}
	]
};