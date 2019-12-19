$(document).ready(function(){
	var startDate,endDate,userId;
	$('#searchById').click(function(){
		
		$('#queryById tbody').empty();
		//開始時間
		startDate = $('#startDate').val();
		//結束時間
		endDate = $('#endDate').val();
		userId = $('#employeeID').val();
		console.log("開始日期"+startDate+"結束日期"+endDate+"員工工號"+userId);
		var errorMessage = '';
		if(startDate==="null" || startDate=='')
			errorMessage+='未選擇開始時間\n';
		if(endDate==="null" || endDate=='')
			errorMessage+='未選擇結束時間\n';
		if(userId==="null" || userId=='')
			errorMessage+='未填寫工號\n';
		
		//傳數據給後台
		if(errorMessage==''){	
			$.ajax({ 
				   url:'../QueryById/QueryByIdList',				  
				   type:'POST',
				   data:{startDate:startDate,endDate:endDate,UserId:userId},
				   success:function(result){ 
					 var StatusCode = result.StatusCode;
					  var message = result.Message;
					  if (result.length> 0) {
						/*alert("查詢異常列表成功");
						console.log(result);*/
						ShowSelectByIdListtTable(result);
					} else {
						alert(message);
					}	  
				}  
			});
			}
		  else{
		    	if(errorMessage.length>0 ||errorMessage!='' ){
			    alert(errorMessage);		
				event.preventDefault(); //preventDefault() 方法阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
			}
		  }
		
	});
	
    function ShowSelectByIdListtTable(rawData) {
    	var recordId = null;
    	//<th style="width: 8%; text-align:center;">線別</th>
		/*<th style="width: 6%; text-align:center;">工號</th>			
		<th style="width: 5%; text-align:center;">姓名</th>
		<th style="width: 10%; text-align:center;">異常日期</th>
		<th style="width: 18%; text-align:center;">異常時間起迄</th>
		<th style="width: 10%; text-align:center;">異常原因</th>*/
    	for(var i=0;i<rawData.length;i++){
    		var	tableContents='<tr>'+		
			'<td style="text-align:center;width: 8%;">'+rawData[i]["DEPID"]+'</td>'+			
			'<td style="text-align:center;width: 6%;">'+rawData[i]["USERID"]+'</td>'+
			'<td style="text-align:center;width: 5%;">'+rawData[i]["USERNAME"]+'</td>'+
			'<td style="text-align:center;width: 10%">'+rawData[i]["EXCEPTION_DATE"]+'</td>'+
			'<td style="text-align:center;width: 18%;">'+rawData[i]["EXCEPTION_INTERVAL"]+'</td>'+
		    '<td style="text-align:center;width: 10%;">'+rawData[i]["EXCEPTION_REASON"]+'</td></tr>';
			$('#queryById tbody').append(tableContents);
			$("#queryByIdTable").css("display","inline-block");//显示div
			
    	}	
    	
    }
	
});
