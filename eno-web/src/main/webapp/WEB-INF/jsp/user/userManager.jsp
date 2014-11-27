<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="modal hide userwidth alert_box w1455" id="userManager">
   <div class="modal-header alert_box_header"> <a href="#" class="icon_small alert_close" data-dismiss="modal"></a>
       <h4>用户管理</h4>
   </div>
<div class="modal-body alert_body w1430">
    <div class="moduleBox_black user_manage">
         <div class="userform_Bar">
             <div class="filter_widget">
                 <select class="selectpicker">
                     <option>所有用户</option>
                     <option>张三</option>
                     <option>李四</option>
                     <option>王五</option>
                 </select>
             </div>
             <div class="funBtnBar" style="margin: 0;">
                 <a href="#new_user" data-toggle="modal" class="btn_pub btn_redLight mr5">新建</a><a href="#" class="btn_pub btn_grey btn_edit mr5">修改</a><a href="#delete_user" class="btn_pub btn_grey btn_delete">删除</a>
             </div>
         </div>
          <div class="user_manage_info">
          <form name="tableform" id="tableform">
              <table cellpadding="0" cellspacing="0" border="0" class="tableList">
                <tbody id="tbodyQz">
                </tbody>
              </table>
           </form>
          </div>
        </div>
    </div>
</div>

<!-- 新建用户弹出框 -->
<div class="modal hide fade alert_box w550 new_user" id="new_user">
    <div class="modal-header alert_box_header"> <a href="#" class="icon_small alert_close" data-dismiss="modal"></a>
        <h4>新建用户</h4>
    </div>
    <div class="modal-body alert_body">
        <div class="moduleBox_black user_manage_new left">
            <div class="quota_setAll_list quota_setAll_listS">
            <form name="form" id="form">
                <div class="quota_setInfo">
                    <label>姓名</label>
                    <div class="formInput"><input type="text" class="quota_input" id="firstname" name="firstname"></div>
                </div>
                <div class="quota_setInfo">
                    <label>性别</label>
                    <input type="radio" name="sex" id="sex-male" class="css-radio" checked="checked" /><label for="sex-male" class="css-label-radio radGroup1" >男</label>
                    <input type="radio" name="sex" id="sex-female" class="css-radio" /><label for="sex-female" class="css-label-radio radGroup1">女</label>
                </div>
                <div class="quota_setInfo">
                    <label>生日</label>
                    <div class="calendarBar"><input id="month_date" class="Wdate" type="text" onclick="WdatePicker()" name="birthday"></div>
                </div>
                <div class="quota_setInfo">
                    <label>入职日期</label>
                    <div class="calendarBar"><input id="month_date" class="Wdate" type="text" onclick="WdatePicker()" name="workdate"></div>
                </div>
                <div class="quota_setInfo">
                    <label>部门</label>
                    <div class="filter_widget">
                        <select class="selectpicker_w197" name="department">
                            <option>产品部</option>
                            <option>宣传部</option>
                            <option>人事</option>
                            <option>测试部</option>
                        </select>
                    </div>
                </div>
                <div class="quota_setInfo">
                    <label>手机</label>
                    <input type="text" value=" 请输入11位数字" class="w185" name="phone">
                </div>
            </form>
            </div>
        </div>
    </div>
    <div class="modal-footer alert_box_footer">
        <input type="button" class="btn_red" value="确认" >
        <input type="button" class="btn_black" value="清空" >
        <input type="button" class="btn_black" value="取消" data-dismiss="modal">
    </div>
</div>
<!-- 修改用户弹出框 -->
<div class="modal hide fade alert_box w550 new_user" id="edit_user">
    <div class="modal-header alert_box_header"> <a href="#" class="icon_small alert_close" data-dismiss="modal"></a>
        <h4>修改用户</h4>
    </div>
    <div class="modal-body alert_body">
        <div class="moduleBox_black user_manage_new left">
            <div class="quota_setAll_list quota_setAll_listS">
                <div class="quota_setInfo">
                    <label>姓名</label>
                    <div class="formInput"><input type="text" class="quota_input" id="firstname" name="firstname" placeholder="王晓明"></div>
                </div>
                <div class="quota_setInfo">
                    <label>性别</label>
                    <input type="radio" name="sex" id="sex-male" class="css-radio" checked="checked" /><label for="sex-male" class="css-label-radio radGroup1">男</label>
                    <input type="radio" name="sex" id="sex-female" class="css-radio" /><label for="sex-female" class="css-label-radio radGroup1">女</label>
                </div>
                <div class="quota_setInfo">
                    <label>生日</label>
                    <div class="calendarBar"><input id="month_date" class="Wdate" type="text" onclick="WdatePicker()"></div>
                </div>
                <div class="quota_setInfo">
                    <label>入职日期</label>
                    <div class="calendarBar"><input id="month_date" class="Wdate" type="text" onclick="WdatePicker()"></div>
                </div>
                <div class="quota_setInfo">
                    <label>部门</label>
                    <div class="filter_widget">
                        <select class="selectpicker_w197">
                            <option>产品部</option>
                            <option>宣传部</option>
                            <option>人事</option>
                            <option>测试部</option>
                        </select>
                    </div>
                </div>
                <div class="quota_setInfo">
                    <label>手机</label>
                    <input type="text" value=" 15010102222" class="w185">
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer alert_box_footer">
        <input type="button" class="btn_red" value="确认" >
        <input type="button" class="btn_black" value="清空" >
        <input type="button" class="btn_black" value="取消" data-dismiss="modal">
    </div>
</div>
<!-- 修改密码 弹出框 -->
<div class="modal hide fade alert_box w550 new_user" id="changePwd">
    <div class="modal-header alert_box_header"> <a href="#" class="icon_small alert_close" data-dismiss="modal"></a>
        <h4>修改密码</h4>
    </div>
    <div class="modal-body alert_body">
        <div class="moduleBox_black user_manage_new left">
            <div class="quota_setAll_list quota_setAll_listS">
                <div class="quota_setInfo">
                    <label>当前密码</label>
                    <input type="text" value="请输入原密码" class="w185">
                </div>
                <div class="quota_setInfo">
                    <label>新密码</label>
                    <input type="password" value="" class="w185">
                </div>
                <div class="quota_setInfo">
                    <label>重复新密码</label>
                    <input type="password" value="" class="w185">
                    <div class="error_tip">与第一次输入不一致</div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer alert_box_footer">
        <input type="button" class="btn_red" value="确认" >
        <input type="button" class="btn_black" value="清空" >
        <input type="button" class="btn_black" value="取消" data-dismiss="modal">
    </div>
</div>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/quota.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user.css">
<%-- <script src="<spring:url value="/resources/plugins/bootstrap/js/bootstrap.js"></spring:url>"></script> --%>
<script src="<spring:url value="/resources/plugins/My97DatePicker/WdatePicker.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/bootstrap/js/bootstrap.js"></spring:url>"></script>
<script src="<spring:url value="/resources/scripts/user/user.js"></spring:url>"></script>