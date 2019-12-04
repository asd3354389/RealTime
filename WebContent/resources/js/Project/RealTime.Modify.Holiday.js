$(document).ready(function(){
	var queryParam=null;
	getHolidayYList();
	queryParam=$('#queryParam').val();
	getHolidayInfo();
	
	$('#HolidayInfoTitleBtn').click(function(){
		queryParam=$('#queryParam').val();
		getHolidayInfo();
	});
	
	function getHolidayYList(){
		$.ajax({
			type:'GET',
			url:'../AdminActioin/HolidayYList',
			async:false, 
			error:function(e){
				alert('找不到資料');
			},
			success:function(rawData){	
				if (rawData != null && rawData != "") {
					var executeResult=rawData;
					var errorResponse=executeResult.ErrorMessage;
					if(errorResponse!=null){
						alert('找不到資料');
					}
					else{
						var numOfRecords=executeResult.length;
						if(numOfRecords>0){
							var htmlAppender;
							for(var i=0;i<numOfRecords;i++){
								htmlAppender+='<option value="'+executeResult[i]+'">'+executeResult[i]+'</option>';
							}
							$('#queryParam').append(htmlAppender);
						}
						else
							alert('找不到資料');
					}
				}
			}
		});
	}
	
	function getHolidayInfo(){
		$.ajax({
			type:'GET',
			url:'../AdminActioin/Holiday',
			data:{queryParam:queryParam},
			error:function(e){
				alert('找不到資料');
			},
			success:function(rawData){	
				if (rawData != null && rawData != "") {
					var executeResult=rawData;
					var errorResponse=executeResult.ErrorMessage;
					if(errorResponse!=null){
						alert('找不到資料');
					}
					else{
						var numOfRecordsL=JSON.parse(executeResult.L).length;
						var numOfRecordsS=JSON.parse(executeResult.S).length;
						if(numOfRecordsL>0||numOfRecordsS>0){
							ShowHolidayInfoTable(rawData);
						}
						else
							alert('找不到資料');
					}
				}
			}
		});
	}
	
	function ShowHolidayInfoTable(rawData){
		var executeResult=rawData;
		var holidayL,holidayS; 
		var message = "";
		holidayL = JSON.parse(executeResult.L);
		holidayS = JSON.parse(executeResult.S);
		$('#HolidayLInfoTable tbody').empty();
		var numOfRecords=holidayL.length;
		var htmlAppender;
		for(var i=1;i<=numOfRecords;i++){
			if(i%4==1){
				htmlAppender+='<tr><td><input type="button" value="'+holidayL[i-1]+'" class="deleteBtn btn btn-xs btn-link"></td>';
			}else if(i%4==0){
				htmlAppender+='<td><input type="button" value="'+holidayL[i-1]+'" class="deleteBtn btn btn-xs btn-link"></td></tr>';
			}else{
				htmlAppender+='<td><input type="button" value="'+holidayL[i-1]+'" class="deleteBtn btn btn-xs btn-link"></td>';
			}
			if(i==numOfRecords){
				htmlAppender+='</tr>';
			}
		}
		$('#HolidayLInfoTable tbody').append(htmlAppender);
			
		$('#HolidaySInfoTable tbody').empty();
		var htmlAppenderS;
		var numOfRecords=holidayS.length;
		for(var i=1;i<=numOfRecords;i++){
			if(i%4==1){
				htmlAppenderS+='<tr><td><input type="button" value="'+holidayS[i-1]+'" class="deleteBtn btn btn-xs btn-link"></td>';
			}else if(i%4==0){
				htmlAppenderS+='<td><input type="button" value="'+holidayS[i-1]+'" class="deleteBtn btn btn-xs btn-link"></td></tr>';
			}else{
				htmlAppenderS+='<td><input type="button" value="'+holidayS[i-1]+'" class="deleteBtn btn btn-xs btn-link"></td>';
			}
			if(i==numOfRecords){
				htmlAppenderS+='</tr>';
			}
		}
		$('#HolidaySInfoTable tbody').append(htmlAppenderS);
		
		
		$(".deleteBtn").click(function(){
			var delete_date = $(this).val();
			if(confirm("您確定要刪除日期為"+delete_date+"的節假日嗎？")){
				$.ajax({
					type:'POST',
					url:'../AdminActioin/DeleteHoliday',
					data:{delete_date:delete_date},
					error:function(e){
						alert('找不到資料');
					},
					success:function(rawData){	
						if (rawData != null && rawData != "") {
							var executeResult=rawData;
							var errorResponse=executeResult.ErrorMessage;
							var StatusCode = executeResult.executeResult;
							
						if(StatusCode=="200"){
							alert(errorResponse);
							queryParam=$('#queryParam').val();
							getHolidayInfo();
						}
						else
							alert(errorResponse);
						}
					}
				});
			}
		});
	}
	
	$("#setHoliday").click(function(){
		var message="";
		var holidayType = $("#selectHolidayType").val();
		var holidayDate = $("#HolidayDate").val();
		if(holidayType==null||holidayType==""){
			message += "假日類型不能為空\n";
		}
		if(holidayDate==null||holidayDate==""){
			message += "日期不能為空\n";
		}
		if(message==""){
			$.ajax({
				type:"POST",
				url:"../AdminActioin/AddHoliday",
				data:{holidayType:holidayType,holidayDate:holidayDate},
				error:function(e){
					alert('添加失敗'+e);
				},
				success:function(rawData){	
					if (rawData != null && rawData != "") {
						var executeResult=rawData;
						var errorResponse=executeResult.ErrorMessage;
						var StatusCode = executeResult.StatusCode;
						
						if(StatusCode=="200"){
							alert(errorResponse);
							queryParam=$('#queryParam').val();
							getHolidayInfo();
						}
						else
							alert(errorResponse);
						}
				}
			});
		}else{
			alert(message);
		}
	});
	
	
	
});