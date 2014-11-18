<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <%@ page import="java.util.*"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <html lang="en">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main.css">
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/runManage.css">
<script type="text/javascript"
    src="${pageContext.request.contextPath}/resources/scripts/jquery-migrate-1.2.1.min.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/runManage.js"></script>
            <div style="margin-bottom: 20px">
                <form action="doSelect"   method="post" style="text-align: center;">
                    <br>
                    班次类型：<input id="shifttype" type="text" name="shifttype" class="input-medium search-query"/>
                    <input  type="submit" value="查询" class="dialog-btn top_btn">
                    <input type="button" name="button1" id="newWork" value="新增" class="dialog-btn top_btn">
                </form>
            </div>

          <table style="margin:10px 38px;" class="table-bordered">
              <thead>
                  <tr>
                      <th>班次ID</th><th>班次类型</th><th>季节</th><th>开始时间</th><th>结束日期</th><th>描述</th><th>编辑</th>
                  </tr>
              </thead>
              <tbody>
                  <c:forEach items="${shifttypesList}" var="shifttypes" varStatus="status">
                      <tr>
                          <td align="center">${shifttypes.shifttypesid}</td>
                          <td align="center">${shifttypes.shifttype}</td>
                          <td align="center">${shifttypes.season}</td>
                          <c:forEach items="${listsd}" var="listsdEtm" begin="${status.count-1}" end="${status.count-1}">
                              <td align="center">${listsdEtm}</td>
                          </c:forEach>
                          <c:forEach items="${listed}" var="listedEtm" begin="${status.count-1}" end="${status.count-1}">
                              <td align="center">${listedEtm}</td>
                          </c:forEach>
                          <td align="center">${shifttypes.description}</td>
                          <td>
                              <a style="cursor: pointer;" onclick="deleteShifttypes(${shifttypes.shifttypesid})">删除</a>&nbsp&nbsp<a style="cursor: pointer;" onclick="shifttypesUpdate(${shifttypes.shifttypesid})">修改</a></td>
                      </tr>
                  </c:forEach>
              </tbody>
	     </table>
<div class="mask">
    <div class="dialog-popover dialog-xgbc">
        <div class="dialog-header">
            <p class="dialog-title">班次编辑</p>
            <div class="dialog-close close_pop">×</div>
        </div>
        <div style="padding:20px 0px" class="dialog-content">
            <div class="dialog_content2">
             <form action="doInsert" method="post" onsubmit="return validate();"> <table height="100"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	  <tr>
        <td width="80" align="right">班次类型：</td>
       <td height="35" >
        <input name="shifttypesid" type="text" id="shifttypesid" size="30" style="display:none;">
       <input name="shifttypeName" type="text" id="shifttypeName" style="width: 205px;"  required="true">
         </td>
      </tr>
      <tr>
        <td width="80" align="right">季节：</td>
        <td width="198" height="39">
         <select id="season" name="season" class="span1" style="width: 223px">
           <option>春季</option>
           <option>夏季</option>
           <option>秋季</option>
           <option>冬季</option>
         </select>
        </td>
      </tr>
      <tr>
        <td width="80" align="right">上班时间：</td>
         <td height="35">
         <select id="starttime" name="starttime" class="span1" style="width: 223px">
           <option>08:30</option>
           <option>20:30</option>
         </select>
         </td>
      </tr>
      <tr>
        <td width="80" align="right">下班时间：</td>
         <td height="35">
         <select id="endtime" name="endtime" class="span1" style="width: 223px">
           <option>20:30</option>
           <option>08:30</option>
         </select>
         </td>
      </tr>
      <tr>
        <td width="180" align="right">描述：</td>
        <td height="35"><textarea name="description" rows="3" id="description" required="true"></textarea>
         </td>
      </tr> 
    </table>

            </div>
        </div>
        <div class="dialog-footer">
            <div class="modal_btn_2" >
                <input id="pd" style="display: none;">
                <input  class="sure dialog-btn"    name="Submit" type="submit"  value="保存" class="btn btn-primary">
                <input  class="cancel dialog-btn"  name="Submit1" type="button"  onclick="locationCancel();" value="关闭" class="btn btn-primary">
            </div>
        </div>
        </form>
    </div>
</div>
<script language="javascript">
$(function(){
    $("#newWork").on("click", function(){
        $(".mask").show();
        $(".dialog-xgbc").show();
    });

    $(".dialog-popover").draggable();
})

function validate() {
	if ($("#starttime").val() == $("#endtime").val()) {
		alert("上下班时间不能相同.");
		$("#endtime").focus();
		return false;
	}
	return true;
}

function locationCancel(){
  window.location.href = "${pageContext.request.contextPath}/shifts/shifttypesList";
}

$(".close_pop").click(function(){
	window.location.href = "${pageContext.request.contextPath}/shifts/shifttypesList";
});

function shifttypesUpdate(shifttypesid) {
	$("#pd").val("update");
	  $(".mask").show();
      $(".dialog-xgbc").show();
      $(".dialog-popover").draggable();
	var url = "${pageContext.request.contextPath}/shifts/shifttypesUpdate?shifttypesid="+shifttypesid;
	$.ajax({
		 type:"GET",
		 url:url ,
		 cache:false,
		 success:function(data){
		$("#shifttypesid").val(data.shifttypesid);
	    $("#shifttypeName").val(data.shifttype);
	    $("#starttime").val(data.starttime);
	    $("#endtime").val(data.endtime);
	    $("#description").val(data.description);
	}
	});
}

function deleteShifttypes(shifttypesid){
  var url = "${pageContext.request.contextPath}/shifts/deleteShifttypes?shifttypesid="+shifttypesid;
  window.location.href = url;
}
</script>
	</html>