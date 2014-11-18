/**
 * 停车管理JS控制
 * */
    var pageNum=0;
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

        var startInDate = $("#startInDate").val();
        var endInDate = $("#endInDate").val();
        var startOutDate = $("#startOutDate").val();
        var endOutDate = $("#endOutDate").val();
        var carIdValue = $("#cardId").val();
        var goName = $("#goName").val();
        var comeName = $("#comeName").val();
        var url =CONTEXT_PATH+ '/park/operate/query';
        var params = {pageNumber: pageNum, pageSize: 4,startInDate:startInDate,endInDate:endInDate,endOutDate:endOutDate,startOutDate:startOutDate,carNum:carIdValue,comeName:comeName,goName:goName};
        $.ajax({url: url, type: 'GET', dataType: 'json', contentType : "application/json; charset=utf-8", data: params, success: function (data) {

        	if (data != null) {
        		  pageCount=data.totalPages;
                  $(' .paging .bc').html(pageNum);
                  $(' .paging .pageCount').html(pageCount);
                  
                  $('#tbodyContent').html("");
                  var htmlJc = '';
                  
				//更新表格
				if(data.content!=null&&data.content!=""){
					for(var i=0;i<data.content.length;i++){
						   if(i%2 == 1)
		                        htmlJcCc = '<tr><td>'+data.content[i].carNum+'</td><td>'+data.content[i].cardNum+'</td><td>'+data.content[i].cardType+'</td><td>'+data.content[i].comeName+'</td><td>'+data.content[i].inDate+'</td><td>'+data.content[i].goName+'</td><td>'+data.content[i].outDate+'</td><td>'+data.content[i].payMoney+'</td></tr>';
		                    else
		                        htmlJcCc = '<tr class="changeColor"><td>'+data.content[i].carNum+'</td><td>'+data.content[i].cardNum+'</td><td>'+data.content[i].cardType+'</td><td>'+data.content[i].comeName+'</td><td>'+data.content[i].inDate+'</td><td>'+data.content[i].goName+'</td><td>'+data.content[i].outDate+'</td><td>'+data.content[i].payMoney+'</td></tr>';
		                       htmlJc = htmlJc+htmlJcCc;
					} 
					$("#tbodyContent").append('<tr><th>车牌号</th><th>卡号</th><th>卡类型</th><th>进场口</th><th>进场时间</th><th>出场口</th><th>出场时间</th><th>费用</th></tr>'+htmlJc);
				}
			}else {
				alert("操作失败！");
			}
        }});
    }

    function selectShowCc(){
        var startInDate =$('#startInDate').val();
        var endInDate = $('#endInDate').val();
        var startOutDate = $('#startOutDate').val();
        var endOutDate = $('#endOutDate').val();
        var carIdValue = $('#cardId').val();
        var goName = $('#goName').val();
        var comeName = $('#comeName').val();
        var url =CONTEXT_PATH+'/park/operate/query';
        var params = {pageNumber: 1, pageSize: 4,startInDate:startInDate,endInDate:endInDate,endOutDate:endOutDate,startOutDate:startOutDate,carNum:carIdValue,comeName:comeName,goName:goName};
        $.ajax({url: url, type: 'GET', dataType: 'json', contentType : "application/json; charset=utf-8", data: params, success: function (data) {

        	if (data != null) {
        		  pageCount=data.totalPages;
                  $(' .paging .bc').html("1");
                  $(' .paging .pageCount').html(pageCount);
                  
                  $('#tbodyContent').html("");
                  var htmlJc = '';
                  
				//更新表格
				if(data.content!=null && data.content!=""){
					for(var i=0;i<data.content.length;i++){
						   if(i%2 == 1)
		                        htmlJcCc = '<tr><td>'+data.content[i].carNum+'</td><td>'+data.content[i].cardNum+'</td><td>'+data.content[i].cardType+'</td><td>'+data.content[i].comeName+'</td><td>'+data.content[i].inDate+'</td><td>'+data.content[i].goName+'</td><td>'+data.content[i].outDate+'</td><td>'+data.content[i].payMoney+'</td></tr>';
		                    else
		                        htmlJcCc = '<tr class="changeColor"><td>'+data.content[i].carNum+'</td><td>'+data.content[i].cardNum+'</td><td>'+data.content[i].cardType+'</td><td>'+data.content[i].comeName+'</td><td>'+data.content[i].inDate+'</td><td>'+data.content[i].goName+'</td><td>'+data.content[i].outDate+'</td><td>'+data.content[i].payMoney+'</td></tr>';
		                       htmlJc = htmlJc+htmlJcCc;
					} 
					$("#tbodyContent").append('<tr><th>车牌号</th><th>卡号</th><th>卡类型</th><th>进场口</th><th>进场时间</th><th>出场口</th><th>出场时间</th><th>费用</th></tr>'+htmlJc);
				}
			}else {
				alert("操作失败！");
			}
        }});
    }

    selectShowCc();
