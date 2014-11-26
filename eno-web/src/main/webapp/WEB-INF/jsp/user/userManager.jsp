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
              <table cellpadding="0" cellspacing="0" border="0" class="tableList">
                <thead>
                   <tr>
                       <th class="edit">&nbsp;</th>
                       <th class="delete">&nbsp;</th>
                       <th>姓名</th>
                       <th>部门</th>
                       <th>性别</th>
                       <th>生日</th>
                       <th>入职日期</th>
                       <th>手机号码</th>
                   </tr>
                </thead>
                <tbody>
				<tr>
				   <td class="edit"><a href="#edit_user" data-toggle="modal" class="icon_small tab_edit"></a></td>
                   <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                   <td><input type="checkbox" name="" id="张三" class="css-checkbox-small"><label for="张三" class="css-label-small">张三</label></td>
                   <td>注意部门名称最多十字</td>
                   <td>男</td>
                   <td>1986/8/1</td>
                   <td>2012/6/6</td>
                   <td></td>
				 </tr>
				 <tr>
                     <td class="edit"><a href="#edit_user" data-toggle="modal" class="icon_small tab_edit"></a></td>
                     <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                     <td><input type="checkbox" name="" id="李四" class="css-checkbox-small"><label for="李四" class="css-label-small">李四</label></td>
                     <td>注意部门名称最多十字</td>
                     <td>女</td>
                     <td>1986/8/1</td>
                     <td>2012/6/6</td>
                     <td></td>
                 </tr>
			     <tr>
                      <td class="edit"><a href="#edit_user" data-toggle="modal" class="icon_small tab_edit"></a></td>
                      <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                      <td><input type="checkbox" name="" id="王五" class="css-checkbox-small"><label for="王五" class="css-label-small">王五</label></td>
                      <td>注意部门名称最多十字</td>
                      <td>女</td>
                      <td>1986/8/1</td>
                      <td>2012/6/6</td>
                      <td></td>
                   </tr>
                   <tr>
                      <td class="edit"><a href="#edit_user" data-toggle="modal" class="icon_small tab_edit"></a></td>
                      <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                      <td><input type="checkbox" name="" id="王五" class="css-checkbox-small"><label for="王五" class="css-label-small">王五</label></td>
                      <td>注意部门名称最多十字</td>
                      <td>女</td>
                      <td>1986/8/1</td>
                      <td>2012/6/6</td>
                      <td></td>
                   </tr>
                   <tr>
                      <td class="edit"><a href="#edit_user" data-toggle="modal" class="icon_small tab_edit"></a></td>
                      <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                      <td><input type="checkbox" name="" id="王五" class="css-checkbox-small"><label for="王五" class="css-label-small">王五</label></td>
                      <td>注意部门名称最多十字</td>
                      <td>女</td>
                      <td>1986/8/1</td>
                      <td>2012/6/6</td>
                      <td></td>
                   </tr>
                   <tr>
                      <td class="edit"><a href="#edit_user" data-toggle="modal" class="icon_small tab_edit"></a></td>
                      <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                      <td><input type="checkbox" name="" id="王五" class="css-checkbox-small"><label for="王五" class="css-label-small">王五</label></td>
                      <td>注意部门名称最多十字</td>
                      <td>女</td>
                      <td>1986/8/1</td>
                      <td>2012/6/6</td>
                      <td></td>
                   </tr>
                   <tr>
                      <td class="edit"><a href="#edit_user" data-toggle="modal" class="icon_small tab_edit"></a></td>
                      <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                      <td><input type="checkbox" name="" id="王五" class="css-checkbox-small"><label for="王五" class="css-label-small">王五</label></td>
                      <td>注意部门名称最多十字</td>
                      <td>女</td>
                      <td>1986/8/1</td>
                      <td>2012/6/6</td>
                      <td></td>
                   </tr>
                   <tr>
                      <td class="edit"><a href="#edit_user" data-toggle="modal" class="icon_small tab_edit"></a></td>
                      <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                      <td><input type="checkbox" name="" id="王五" class="css-checkbox-small"><label for="王五" class="css-label-small">王五</label></td>
                      <td>注意部门名称最多十字</td>
                      <td>女</td>
                      <td>1986/8/1</td>
                      <td>2012/6/6</td>
                      <td></td>
                   </tr>
                   <tr>
                       <td class="edit"><a href="#edit_user" data-toggle="modal" class="icon_small tab_edit"></a></td>
                      <td class="delete"><a href="#" class="icon_small tab_delete"></a></td>
                      <td><input type="checkbox" name="" id="王五" class="css-checkbox-small"><label for="王五" class="css-label-small">王五</label></td>
                      <td>注意部门名称最多十字</td>
                      <td>女</td>
                      <td>1986/8/1</td>
                      <td>2012/6/6</td>
                      <td></td>
                   </tr>
                </tbody>
              </table>
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
                <div class="quota_setInfo">
                    <label>姓名</label>
                    <div class="formInput"><input type="text" class="quota_input"></div>
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
                    <input type="text" value=" 请输入11位数字" class="w185">
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
                    <div class="formInput"><input type="text" class="quota_input" placeholder="王晓明"></div>
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user.css">
<script src="<spring:url value="/resources/scripts/jquery-1.9.1.min.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/bootstrap/js/bootstrap.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/My97DatePicker/WdatePicker.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/bootstrap-select/bootstrap-select.min.js"></spring:url>"></script>

<script src="<spring:url value="/resources/scripts/user/user.js"></spring:url>"></script>