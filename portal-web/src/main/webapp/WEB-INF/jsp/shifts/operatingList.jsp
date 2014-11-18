<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<link rel="stylesheet" type="text/css"
	  href="${pageContext.request.contextPath}/resources/css/main.css">
<link rel="stylesheet" type="text/css"
	  href="${pageContext.request.contextPath}/resources/css/noticeBoard.css">
<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>
<div class="span12 main row-fluid">

	<div class="span12 user_info">
		<div class="table_width">
			<table class="user_table">
				<thead>
				<tr class="table_head">
					<th>消息类型</th>
					<th>消息内容</th>
					<th>负责人</th>
					<th>时刻</th>
				</tr>
				</thead>
				<tbody id="tbodyQz">

				</tbody>
			</table>
			<div class="paging">
				<img src="${pageContext.request.contextPath}/resources/images/left_first.png" onclick="selectLogPage('First');"/> <img
					src="${pageContext.request.contextPath}/resources/images/left.png" onclick="selectLogPage('Before');"/>
				<span class="page_des1">Page</span>
				<span class="bc"></span><span class="page_des2">of</span><span class="pageCount"></span>
				<img src="${pageContext.request.contextPath}/resources/images/right.png" onclick="selectLogPage('After');"/> <img
					src="${pageContext.request.contextPath}/resources/images/right_end.png" onclick="selectLogPage('End');"/>
			</div>
		</div>
	</div>
</div>
<div id="qz" style="display: none;"></div>

<script type="text/javascript">

	selectLogPage("First");
	//以下为设备列表的js
	var pageNum=1;
	var pageCount = 1;
	function selectLogPage(para){

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


		var url = CONTEXT_PATH + '/shifts/getAssetRunLog';
		var params = {pageNumber: pageNum-1, pageSize: 15,category:'AUTO'};
		$.ajax({url: url, type: 'GET', dataType: 'json', async: true, data: params, success: function (data) {

			if(data!=null){
				pageCount=data.totalPages;
				$(' .paging .pageCount').html(pageCount);

				var html = '';
				$("#tbodyQz").html("");
				for(var k=0;k<data.content.length;k++){
					var noticeBoard = data.content[k];
					var htmlk ='<tr><td align="center">'+'设备运行记录'+'</td>'+
							'<td align="center">'+noticeBoard.message+'</td>'+
							'<td align="center">'+noticeBoard.userid+'</td>'+
							'<td align="center">'+noticeBoard.dated+'</td></tr>';
					html = html+htmlk;
				}
				$("#tbodyQz").append(html);
			}
		}});
	}
</script>