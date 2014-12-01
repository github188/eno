<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="modal hide fade alert_box w1455" id="userManager">
    <div class="modal-header alert_box_header"> <a href="#" class="icon_small alert_close" data-dismiss="modal"></a>
        <h4>用户管理</h4>
    </div>
    <div class="modal-body alert_body w1430">
        <div class="moduleBox_black user_manage">
            <div class="userform_Bar">
                <div class="filter_widget">
                    <select class="selectpicker" id="finddepartment">
                        <option value="0">所有用户</option>
                        <option value="1">产品部</option>
                        <option value="2">宣传部</option>
                        <option value="3">人事</option>
                        <option value="4">测试部</option>
                    </select>
                </div>
                <div class="funBtnBar" style="margin: 0;">
                    <a href="#new_user" data-toggle="modal" class="btn_pub btn_redLight mr5">新建</a><a href="#" class="btn_pub btn_grey mr5 btn_edit">修改</a><a href="#delete_user" class="btn_pub btn_grey btn_delete">删除</a>
                </div>
            </div>
            <div class="user_manage_info">
                <table cellpadding="0" cellspacing="0" border="0" class="tableList">
                   <tbody id="tbodyQz">
                </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- 新建用户弹出框 -->
<div class="modal hide fade alert_box w550 new_user" id="new_user">
    <div class="modal-header alert_box_header"> <a href="#" class="icon_small alert_close" data-dismiss="modal"></a>
        <h4>用户信息</h4>
    </div>
    <div class="modal-body alert_body">
        <div class="moduleBox_black user_manage_new left">
            <div class="quota_setAll_list quota_setAll_listS">
            	<form name="userform" id="userform" action="#">
            		<input type="hidden" name="useruid" id="useruid" >
	                <div class="quota_setInfo">
	                    <label>姓名</label>
	                    <div class="formInput"><input type="text" class="quota_input" name="firstname" id="firstname" ></div>
	                </div>
	                <div class="quota_setInfo">
	                    <label>登录id</label>
	                    <div class="formInput"><input type="text" class="quota_input" name="loginid" id="loginid"></div>
	                </div>
	                <div class="quota_setInfo">
	                    <label>密码</label>
	                    <div class="formInput"><input type="password" class="quota_input" name="password" id="password"></div>
	                </div>
	                <div class="quota_setInfo">
	                    <label>性别</label>
	                    <input type="radio" name="sex" id="sex-male" class="css-radio" checked="checked" /><label for="sex-male" class="css-label-radio radGroup1">男</label>
	                    <input type="radio" name="sex" id="sex-female" class="css-radio" /><label for="sex-female" class="css-label-radio radGroup1">女</label>
	                </div>
	                <div class="quota_setInfo">
	                    <label>生日</label>
	                    <div class="calendarBar"><input  name="birthday" id="birthday" class="Wdate" type="text" onclick="WdatePicker()"></div>
	                </div>
	                <div class="quota_setInfo">
	                    <label>入职日期</label>
	                    <div class="calendarBar"><input  name="workdate" id="workdate" class="Wdate" type="text" onclick="WdatePicker()"></div>
	                </div>
	                <div class="quota_setInfo">
	                    <label>部门</label>
	                    <div class="filter_widget">
	                        <select class="selectpicker_w197" name="department" id="department">
	                            <option>产品部</option>
	                            <option>宣传部</option>
	                            <option>人事</option>
	                            <option>测试部</option>
	                        </select>
	                    </div>
	                </div>
	                <div class="quota_setInfo">
	                    <label>手机</label>
	                    <input type="text" placeholder="请输入11位手机号码" class="w185" name="phone" id="phone">
	                </div>
	        	</form>
            </div>
        </div>
    </div>
    <div class="modal-footer alert_box_footer">
        <input type="button" class="btn_red" id="saveUsers" value="确认" >
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
           		<form name="updatepw" id="updatepw" action="#">
            		<input type="hidden" name="useruid" id="useruid" value="1" >
	                <div class="quota_setInfo">
	                    <label>当前密码</label>
	                    <input type="text" placeholder="请输入原密码" class="w185" name="password" id="password">
	                </div>
	                <div class="quota_setInfo">
	                    <label>新密码</label>
	                    <input type="password" value="" class="w185" name="passwordnew" id="passwordnew">
	                </div>
	                <div class="quota_setInfo">
	                    <label>重复新密码</label>
	                    <input type="password" value="" class="w185" name="passwordnew1" id="passwordnew1">
	                    <div class="error_tip">与第一次输入不一致</div>
	                </div>
                </form>
            </div>
        </div>
    </div>
    <div class="modal-footer alert_box_footer">
        <input type="button" class="btn_red" id="updatePassword" value="确认" >
        <input type="button" class="btn_black" value="清空" >
        <input type="button" class="btn_black" value="取消" data-dismiss="modal">
    </div>
</div>