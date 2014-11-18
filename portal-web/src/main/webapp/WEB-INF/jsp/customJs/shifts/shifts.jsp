
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery-migrate-1.2.1.min.js"></script>
<!--
<script src="${pageContext.request.contextPath}/resources/scripts/shifts/shifts.js"></script>
<style type="text/css">
.triangle {
	left: 300px;
}

.table_frame {
	width: 1700px;
	margin: 0 auto;
	font-size: 14px;
}

.table_frame table {
	background-color: #fff;
	border: 1px solid #999;
	cursor: pointer;
}

.table_frame td:hover{
	background-color: #00CCFF;
}

.table_title {
	border: 1px solid #999;
}

.table_title ~ tr {
	height: 30px;
}

th,td {
	text-align: center;
}

table>tfoot>tr:nth-child(even){
	background-color: #e7e7e7;
}

.notes {
	text-align: left !important;
	text-indent: 1em;
}

.th_job {
	width: 75px;
	border: 1px solid #999;
}

.th_job_2 {
	border-right: 1px solid #999;
}

.th_date_name {
	width: 75px; 
	border: 1px solid #999;
}

.th_date_name_2 {
	border-right: 1px solid #999; 
}

.date {
	text-align: right;
}

.name {
	text-align: left;
}

.day_week {
	width: 39px;
}

.tel {
	width: 110px;
	border-left: 1px solid #999;
}

.tel_2 {
	border-left: 1px solid #999;
	border-right: 1px solid #999;
}

.popSelect {
	width: 180px;
	height: 580px;
	background-color: #e4e4e4;
	position: absolute;
	display: none;
	cursor: pointer;
}

.popSelect div {
	width: 127px;
	height: 32px;
	line-height: 32px;
	color: #fff;
	background-color: #999;
	margin: 22px auto;
	text-align: center;
}

.popSelect div:hover {
	background-color: #66ccff !important;
}

.popSelect p {
	width: 20px;
	float: right;
	text-align: right;
	padding-right: 15px;
	cursor: pointer;
	font-size: 26px;
	color: #999;
}

.changeColor {
	background-color: #e7e7e7;
}

.highLight {
	background-color: #4f81bd;
	color: #fff;
}

.displaynone {
	display: none;
}

.top_btn_group {
	margin: 20px auto;
}

.select_bar {
	margin: 20px 0;
	height: 30px;
	line-height: 30px;
	font-size: 14px;
}

.select_bar>*{
	float: left;	
}

.select_bar>div {
	margin-left: 30px;
}

.select_bar>ul {
	color: #26C3BC;
}

.select_bar ul>li {
	float: left;
	margin-left: 35px;
	height: 30px;
	line-height: 30px;
	width: 66px;
	text-align: center;
	cursor: pointer;
}

.edit {
	width: 88px;
}

.left-icon {
	background:url("../images/all_icon.png") -70px -487px no-repeat;
	width: 22px;
	height: 30px;
}

.right-icon {
	background:url("../images/all_icon.png") -327px -487px no-repeat;
	width: 22px;
	height: 30px;
}


.year_sele > *{
	float: left;
}

.td-middle {
	vertical-align: middle !important;
	text-align: center !important;
}

.ymd_switch {
	width: 100%;
	height: 38px;
	line-height: 38px;
}

.staffChange {
	margin-top: 
}

.staffChange_title {
	background:url("../images/jjb.png") no-repeat;
	height: 110px;
	position: relative;
}

.show_date {
	font-size: 30px;
	color: #fff;
	position: absolute;
	top: 10px;
}

.date1 {
	left: 214px;
}

.date2 {
	left: 955px;
}

.press_state {
	background:url("../images/press.png") no-repeat;
	width: 14px;
	height: 14px;
	position: absolute;
	top: 79px;
	cursor: pointer;
}

.press1 {
	left: 445px;
}

.press2 {
	left: 816px;
}

.staff_name table {
	width: 100%;
}

.staff_name table th{
	height: 36px;
	background-color: #999;
	color: #fff;
	font-size: 16px;
}

.staff_name table td{
	height: 50px;
	color: #666;
	font-size: 16px;
}

.staff_name table tr:nth-child(even){
	background-color: #eaeaea;
}

.log_title {
	height: 110px;
	background-color: #666;
}

.log_title_top {
	width: 100%;
	height: 60px;
	line-height: 60px;
}

.log_title_top .title {
	font-size: 30px;
	color: #fff;
	float: left;
	padding-left: 15px;
}

.weather1 {
	float: right;
	padding-right: 20px;
}

.weather1 .weatherImg {
	margin-top: 10px;
	width: 45px;
}

.weather_detail1 {
	float: left;
	width: 75px;
}

.weather_detail1 div {
	height: 30px;
	line-height: 30px;
	color: #fff;
}

.weather_detail1 .weather_des {
	line-height: 41px;
	font-size: 18px;
}

.weather_detail1 .temperature {
	font-size: 15px;
	line-height: 22px;
}

.log_title_bottom {
	color: #fff;
	font-size: 16px;
	margin-top: 22px;
	padding-left: 393px;
}

.record_job {
	margin-left: 96px;
}

.log_content {
	width: 100%;
	height: 686px;
	background-color: #cceced;
	overflow: hidden;
}

.blankdiv {
	height: 20px;
	width: 100%;
}

.work_title {
	height: 40px;
	line-height: 40px;
	margin-top: 15px;
}

.work_title p {
	padding-left: 26px;
	float: left;
	color: #39A5AD;
	font-size: 20px;
}

.work_title .line {
	height: 1px;
	background-color: #68C8CA;
	float: left;
	margin: 19px 0 0 12px;
}

.line1 {
	width: 626px;
}

.line2 {
	width: 548px;
}

.textInput {
	width: 726px;
	margin-left: 26px;
	padding: 0px;
}

.text1 {
	height: 152px;
}

.text2 {
	height: 82px;
}

.text3 {
	height: 72px;
}

.btn_group {
	width: 100%;
	height: 40px;
	margin-top: 10px;
}

.sub_btn {
	width: 100px;
	height: 30px;
	line-height: 30px;
	border-radius: 6px;
	float: left;
	margin-left: 10px;
	color: #fff;
	font-size: 16px;
	text-align: center;
}

.btn_group .submit {
	background-color: #39a6ae;
	margin-left: 544px;
}

.btn_group .cancel {
	background-color: #25a0d9;
}
</style>
 -->