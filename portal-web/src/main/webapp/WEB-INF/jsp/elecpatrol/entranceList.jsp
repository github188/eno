<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/bootstrap-select.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/elecpatrol.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/alertManage.css">
<script src="${pageContext.request.contextPath}/webjars/jquery/1.9.1/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/bootstrap-select.min.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>		
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/alertManage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/highcharts.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/My97DatePicker/WdatePicker.js"></script>
<div class="span10" >
     <div class="span12 no_right_margin" style="margin-top: 0px;">
         <div class="span12 query_bar">
             <div>
                 <span style="margin-left: 30px;"></span>
                 <span>事件类型：</span>
                 <select id="bc" name="bc" class="span1"
                         style="width: 223px; height: 25px">
                     <option value="G">普通事件</option>
                     <option value="V">有效事件</option>
                     <option value="I">非法事件</option>
                 </select>
                 <span style="margin-left: 30px;cursor: pointer;" onclick="selectShow();">查询</span>
             </div>
         </div>
			<div class="span12 alert_detail">
				<table class="alert_list">
					<tbody id = "tbodyContent">
						<tr>
							<th>ID</th>
							<th>姓名</th>
							<th>卡号</th>
							<th>时间</th>
							<th>信息</th>
						</tr>
                        <c:forEach items="${SASACLIST.pageItems}" var="sas" varStatus="dex">
                            <tr <c:if test="${dex.index%2==1}">class='changeColor'</c:if>>
                                <td>${sas.id}</td>
                                <td>${sas.userName}</td>
                                <td>${sas.cardId}</td>
                                <td>${sas.eventTime}</td>
                                <td>${sas.contentMsg}</td>
                                </tr>
                        </c:forEach>

					</tbody>
				</table>
                <div class="paging">
                    <img src="${pageContext.request.contextPath}/resources/images/left_first.png" onclick="selectFy('First');"/> <img
                        src="${pageContext.request.contextPath}/resources/images/left.png" onclick="selectFy('Before');"/>
                    <span class="page_des1">Page</span>
                    <span class="bc"></span><span class="page_des2">of</span>
                    <span class="pageCount"></span> <img src="${pageContext.request.contextPath}/resources/images/right.png" onclick="selectFy('After');"/> <img
                        src="${pageContext.request.contextPath}/resources/images/right_end.png" onclick="selectFy('End');"/>
                </div>
			</div>
		</div>
</div>	
<script type="text/javascript">

    var base_url = "${pageContext.request.contextPath}";
    var pageNum=1;
    var pageCount = 1;
    function selectFy(para){
        var pagef = "";
        if(para == "Before"){
            if(pageNum>1){
                pageNum = pageNum-1;
            }
            pagef = pageNum;
        }
        if(para == "After"){
            if(pageNum<pageCount){
                pageNum = pageNum+1;
            }
            pagef = pageNum;
        }
        if(para == "End"){
            pageNum = pageCount;
            pagef = pageCount;
        }
        if(para == "First"){
            pageNum = 1;
            pagef = 1;
        }
        if(pageCount=="0"){
            pagef = 0;
        }
        $(".bc").text(pagef);

        var html = '<tr><th>ID</th><th>姓名</th><th>卡号</th><th>时间</th><th>信息</th></tr>';
        var bcValue = $('#bc').val();
        var url = base_url + '/other/sasac/getSasacList';
        var params = {pageNumber: 1, pageSize: 10,eventType:bcValue};
        $.ajax({url: url, type: 'GET', dataType: 'json', async: true, data: params, success: function (data) {

            if(data!=null){
                $('#tbodyContent').html("");
                var htmlJc = '';
                pageCount=data.pagesAvailable;
                $(' .paging .bc').html("1");
                $(' .paging .pageCount').html(pageCount);
                for(var i=0;i<data.pageItems.length;i++){
                    var obj = data.pageItems[i];
                    var htmlJcCc = "";
                    if(i%2 == 1)
                        htmlJcCc =" <tr >\n" +
                                "<td>"+obj.id+"</td>\n" +
                                "<td>"+obj.userName+"</td>\n" +
                                "<td>"+obj.cardId+"</td>\n" +
                                "<td>"+obj.eventTime+"</td>\n" +
                                "<td>"+obj.contentMsg+"</td>\n" +
                                "</tr>";
                    else
                        htmlJcCc =" <tr class='changeColor' >\n" +
                                "<td>"+obj.id+"</td>\n" +
                                "<td>"+obj.userName+"</td>\n" +
                                "<td>"+obj.cardId+"</td>\n" +
                                "<td>"+obj.eventTime+"</td>\n" +
                                "<td>"+obj.contentMsg+"</td>\n" +
                                "</tr>";

                    htmlJc = htmlJc+htmlJcCc;
                }
                $("#tbodyContent").append(html+htmlJc);
            }else{
                $("#tbodyContent").html("");
                $("#tbodyContent").append(html);
            }
        }});
    }

function selectShow(){

    var html = '<tr><th>ID</th><th>姓名</th><th>卡号</th><th>时间</th><th>信息</th></tr>';

    var bcValue = $('#bc').val();

    var url = base_url + '/other/sasac/getSasacList';
    var params = {pageNumber: 1, pageSize: 10,eventType:bcValue};
    $.ajax({url: url, type: 'GET', dataType: 'json', async: true, data: params, success: function (data) {

        if(data!=null){
            $('#tbodyContent').html("");
            var htmlJc = '';
            pageCount=data.pagesAvailable;
            $(' .paging .bc').html("1");
            $(' .paging .pageCount').html(pageCount);
            for(var i=0;i<data.pageItems.length;i++){
                var obj = data.pageItems[i];
                var htmlJcCc = "";
                if(i%2 == 1)
                    htmlJcCc =" <tr >\n" +
                            "<td>"+obj.id+"</td>\n" +
                            "<td>"+obj.userName+"</td>\n" +
                            "<td>"+obj.cardId+"</td>\n" +
                            "<td>"+obj.eventTime+"</td>\n" +
                            "<td>"+obj.contentMsg+"</td>\n" +
                            "</tr>";
                else
                    htmlJcCc =" <tr class='changeColor' >\n" +
                            "<td>"+obj.id+"</td>\n" +
                            "<td>"+obj.userName+"</td>\n" +
                            "<td>"+obj.cardId+"</td>\n" +
                            "<td>"+obj.eventTime+"</td>\n" +
                            "<td>"+obj.contentMsg+"</td>\n" +
                            "</tr>";

                htmlJc = htmlJc+htmlJcCc;
            }
            $("#tbodyContent").append(html+htmlJc);
        }else{
            $("#tbodyContent").html("");
            $("#tbodyContent").append(html);
        }
    }});
}

</script>
</html>