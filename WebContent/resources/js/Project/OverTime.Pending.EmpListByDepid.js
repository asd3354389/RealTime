/**
 * 顯示未審核加班的人員列表
 */
$(document).ready(function(){
	var OverTimeType,OverTimeType1,OverTimeCal,ItemNumber,SelectedEmps;//加班類型,時間
	var selectedOTEmpIDs=new Array();//報加班的員工id
	var WorkContent;//工作內容
	var HolidayType = "N";//假日類型,默認正常類型
	var ClassNo,WorkshopNo,LineNo,RCNO,AssistantAccount,
		OverTimeDate,IsIdentified,IsAbnormal,OverTimeTypeText='';
	var SDate='2017-12-26',EDate='2017-12-26',ItemNumber='';
	var currentClassNoInfo;
	var overTimeEmps=new Array();
	init();
	GetHoliday();
	
	
	$(document).ajaxSend(function(event, request, settings) {
	    $('#ajaxLoader').show();
	});

	$(document).ajaxComplete(function(event, request, settings) {
	    $('#ajaxLoader').hide();
	});
	
	$('#uploadEmpIdFile').change(function(){  	
		getFileContent(this, function (str) {
			var exportEmpId=str.replace(/\s+/g,',').replace(/,$/,"");
			$('#importEmpIdSum').text(exportEmpId.split(",").length);
	        var exportEmpIdArray=exportEmpId.split(",");
			var allEmpIds=new Array();
			$(":checkbox[name='selectedEmp']").each(function(){				
				allEmpIds.push($(this).val());
				});
			
			  for (var i = 0; i < allEmpIds.length; i++) {
                  //$.inArray函数功能是判断所选元素是否在数组里，不在返回-1，其他返回0++
                  var res = $.inArray(allEmpIds[i], exportEmpIdArray);
                  if (res != -1) {
                	  var overTimehour =  $(":checkbox[value='"+allEmpIds[i]+"']").parents("tr").children().eq(9).text();
      				if(overTimehour=="0"){
      					$(this).children().children().eq(0).prop('checked',false);
      					 $(":checkbox[value='"+allEmpIds[i]+"']").parents("tr").attr("style", "background-color: white"); 
    				}else{     				
                	  $(":checkbox[value='"+allEmpIds[i]+"']").prop("checked",true);
                	  $(":checkbox[value='"+allEmpIds[i]+"']").parents("tr").attr("style", "background-color: #e6f0fc"); 
    				}
                  }
              }
			  
            });	   
		 $('#uploadEmpIdFile').val('');
	});
	
	$('#selectedAllEmps').change(function(){
		if(this.checked){
			$('#OTPendingEmpTable tbody tr').each(function(){
				var overTimehour = $(this).children().eq(9).text();
				var dghour = $(this).children().eq(11).text();
				if(overTimehour=="0"&&dghour=="0"){
					$(this).children().children().eq(0).prop('checked',false);
					$(this).attr("style", "background-color: white"); 
				}else{
				    $(this).children().children().eq(0).prop('checked',true);
				    $(this).attr("style", "background-color: #e6f0fc"); 
				}
				
			});
			
		}
		else{
			$('#OTPendingEmpTable tbody tr').each(function(){
				$(this).attr("style", "background-color: white"); 
				$(this).children().children().eq(0).prop('checked',false);
			});
		}
	});
		
	$('#overtimeCal').change(function(){
		//選擇時間觸發的事件
		OverTimeCal=$(this).find('option:selected').val();
		//呼叫計算加班時數的function
	//	GetPendingEmpList(WorkshopNo,RCNO,ClassNo,OverTimeDate,IsIdentified,IsAbnormal,false,OverTimeCal,currentClassNoInfo);
		var selectOverTimeDate = new Date(OverTimeDate);
		var weekday=new Array(7);
		weekday[0]="星期日";
		weekday[1]="星期一";
		weekday[2]="星期二";
		weekday[3]="星期三";
		weekday[4]="星期四";
		weekday[5]="星期五";
		weekday[6]="星期六";
		if(HolidayType == "N"){
			var vdate = OverTimeDate.replace(/-/g, '/');
			var monthDate = new Date(vdate);
			var OTDate = ""+(monthDate.getMonth()+1)+monthDate.getDate();
			if(OTDate=="38"){
				alert("今天為婦女節，加班一在實時系統提報，若要提報加班三，請用聯絡單提報");
			}
			if(weekday[selectOverTimeDate.getDay()]=="星期日" || weekday[selectOverTimeDate.getDay()]=="星期六"){
				if(OverTimeCal=="2"){
					GetCalOverTimeFromServer(WorkshopNo,LineNo,RCNO,ClassNo,OverTimeDate,
							OverTimeCal,ItemNumber,IsAbnormal);
				}else if(OverTimeCal=="1"){
					alert("報加班的日期為"+weekday[selectOverTimeDate.getDay()]+",是否選擇正常班？")
					GetCalOverTimeFromServer(WorkshopNo,LineNo,RCNO,ClassNo,OverTimeDate,
							OverTimeCal,ItemNumber,IsAbnormal);
				}
			}
			else{
				if(OverTimeCal=="2"){
					alert("報加班的日期為"+weekday[selectOverTimeDate.getDay()]+",正常班不允許選假日班時間類型，如果為調班，加班二的部分請用聯絡單作業！！！")
					/*if (confirm("報加班的日期為"+weekday[selectOverTimeDate.getDay()]+",您確定報假日班嗎？")) {
						GetCalOverTimeFromServer(WorkshopNo,LineNo,RCNO,ClassNo,OverTimeDate,
							OverTimeCal,ItemNumber,IsAbnormal);
					}*/
					$(this).prop('selectedIndex', 0);
				}
				else{
					GetCalOverTimeFromServer(WorkshopNo,LineNo,RCNO,ClassNo,OverTimeDate,
						OverTimeCal,ItemNumber,IsAbnormal);
				}
			}
		}else if(HolidayType == "L" || HolidayType == "S"){
			if(OverTimeCal=="1"){
				alert("今日為法定節假日或法定節假日補休，只允許選假日班時間類型！")
				/*if (confirm("報加班的日期為"+weekday[selectOverTimeDate.getDay()]+",您確定報正常班嗎？")) {
					GetCalOverTimeFromServer(WorkshopNo,LineNo,RCNO,ClassNo,OverTimeDate,
						OverTimeCal,ItemNumber,IsAbnormal);
				}*/
				$(this).prop('selectedIndex', 0);
			}
			else{
				GetCalOverTimeFromServer(WorkshopNo,LineNo,RCNO,ClassNo,OverTimeDate,
						OverTimeCal,ItemNumber,IsAbnormal);
			}
		}
		
		
	});
	
	$('#overtimeType').change(function(){
		//加班類型改變的觸發事件
		OverTimeType=$(this).find('option:selected').val();
		switch(OverTimeType){
			case "1":
				OverTimeTypeText='延時加班';
				break;
			case "2":
				OverTimeTypeText='例假日加班';
				break;
			case "3":
				OverTimeTypeText='節假日加班';
				break;
			case "0":
				OverTimeTypeText='';
				break;
			default:
				OverTimeTypeText='';
		}
		
		var selectOverTimeDate = new Date(OverTimeDate);
		var weekday=new Array(7);
		weekday[0]="星期日";
		weekday[1]="星期一";
		weekday[2]="星期二";
		weekday[3]="星期三";
		weekday[4]="星期四";
		weekday[5]="星期五";
		weekday[6]="星期六";
		if(HolidayType == "N"){
			if(weekday[selectOverTimeDate.getDay()]=="星期日" || weekday[selectOverTimeDate.getDay()]=="星期六"){
				if(OverTimeType=="3" || OverTimeType=="1"){ //延時加班
					alert("報加班的日期為"+weekday[selectOverTimeDate.getDay()]+",只能選加班二類型！");
					$(this).prop('selectedIndex', 0);
					/*if (!confirm("報加班的日期為"+weekday[selectOverTimeDate.getDay()]+",您確定報"+OverTimeTypeText+"嗎？")) {
						OverTimeTypeText='';
					}	*/			
				}
			}
			else{
				if(OverTimeType=="2" || OverTimeType=="3"){
					alert("報加班的日期為"+weekday[selectOverTimeDate.getDay()]+",不允許報"+OverTimeTypeText+"只允許報加班一！");
					$(this).prop('selectedIndex', 0);
					/*if (!confirm("報加班的日期為"+weekday[selectOverTimeDate.getDay()]+",您確定報"+OverTimeTypeText+"嗎？")){
						OverTimeTypeText='';
					}*/				
				}
			}
		}else if(HolidayType == "S"){
			if(OverTimeType=="1" || OverTimeType=="3"){
				alert("今日為補休，只允許報加班二！");
				$(this).prop('selectedIndex', 0);
				/*if (!confirm("報加班的日期為"+weekday[selectOverTimeDate.getDay()]+",您確定報"+OverTimeTypeText+"嗎？")){
					OverTimeTypeText='';
				}*/				
			}
		}else{
			if(OverTimeType=="1" || OverTimeType=="2"){
				alert("今日為法定節假日，只允許報加班三！");
				$(this).prop('selectedIndex', 0);
				/*if (!confirm("報加班的日期為"+weekday[selectOverTimeDate.getDay()]+",您確定報"+OverTimeTypeText+"嗎？")){
					OverTimeTypeText='';
				}*/				
			}
		}
		
		/*顯示加班類型*/
		$('#OTPendingEmpTable tbody tr').each(function(){
			$(this).children().eq(10).html(OverTimeTypeText);
		});
		
	});

	/*送出加班單審核*/
	$('.OTHrsSubmitBtn').click(function(){
		OverTimeType=$('#overtimeType').find('option:selected').val();
		OverTimeType1=$('#overtimeCal').find('option:selected').val();
		WorkContent=$('#workcontent').val();
		OverTimeCal=$('#overtimeCal').find('option:selected').val();
		selectedOTEmpIDs=GetOTSubmitEmps();//取得選取的人員id數組
	    //SelectedEmps=GetOTSubmitEmps(); //取得選取的人員
		if(CheckConditionValid()){
			if (confirm("你确定提交當前選擇人員名單吗？")) {
				var OTBoolean = true;
				var OTResult;
				var max = 400;
				var length = selectedOTEmpIDs.length;
				var curlength ;
				var count = Math.ceil(length/max);
				for(var i = 0 ;i<count;i++){
					SelectedEmps='';
					OTResult=''
					if(i*max+max>=length){
						curlength = length; 
					}else{
						curlength = i*max+max;
					}
					for(var j=i*max;j<curlength;j++){
						if(j==i*max)
							SelectedEmps+=selectedOTEmpIDs[j];
						else
							SelectedEmps+='*'+selectedOTEmpIDs[j];
					}
					var OTConfirmInfo=new OThourConfirmInfo(ClassNo,RCNO,WorkshopNo,LineNo,OverTimeDate,0,null,null,
							OverTimeType,OverTimeType1,ItemNumber,SelectedEmps,IsAbnormal,WorkContent);
					OTResult = SubmitEmployeeOverTimeInfo2ServerByDepid(IsAbnormal,OTConfirmInfo);
					if(OTResult != 200){
						OTBoolean = false;
						break;
					}
				}
				if(OTBoolean){
					alert('加班提報成功，視窗將關閉');
        			window.open('', '_self', '');
        			window.close();
				}else{
					alert(OTResult);
				}
				var OTConfirmInfo=new OThourConfirmInfo(ClassNo,RCNO,WorkshopNo,LineNo,OverTimeDate,0,null,null,
						OverTimeType,OverTimeType1,ItemNumber,SelectedEmps,IsAbnormal,WorkContent);
				SubmitEmployeeOverTimeInfo2Server(IsAbnormal,OTConfirmInfo);
			}
		}
		//(JSON.stringify(OThourConfirm));
	});
	
	function CheckConditionValid(){
		var alertMessage='',isValid=true;

		if(OverTimeType=="0"){
			alertMessage+='請選擇加班類型\n';
		}
		
		if(OverTimeCal=="0"){
			alertMessage+='請選擇加班時間\n'
		}
		
		if(WorkContent==""){
			alertMessage+='請填寫加班內容\n'
		}
		else if(WorkContent.length>100){
			alertMessage+='填寫加班內容不得超過100個字\n'
		}
		
		if(selectedOTEmpIDs.length=="0"){
			alertMessage+='請選擇所要提報加班的人員\n'
		}
		
		if(alertMessage.length>0){
			alert(alertMessage);
			isValid=false;
		}
		return isValid;
	}
	
	function init(callback){
		WorkshopNo=getParameterByName("WorkshopNo");
		LineNo=getParameterByName("LineNo");
		RCNO=getParameterByName("RCNO");
	//	AssistantAccount=getParameterByName("AssistantAccount");
		ClassNo=getParameterByName("ClassNo");
		OverTimeDate=getParameterByName("OverTimeDate");
		IsIdentified=getParameterByName("IsIdentified");
		IsAbnormal=getParameterByName("IsAbnormal");
		SDate=getParameterByName("SDate");
		EDate=getParameterByName("EDate");
		ItemNumber=getParameterByName("ItemNumber");
		GetClassNoFromServer(ClassNo,true);
		if(RCNO!=''){
			$('#workcontent').val(RCNO+'_'+ItemNumber);
		}
	}
	
	/*取得班別詳細資訊*/
	function GetClassNoFromServer(CurrentShift,isInit){
		$.getJSON("../Utils/ClassInfo.show?currentShift="+CurrentShift,function(result){
			currentClassNoInfo=new ClassNoInfo(result.ClassStart,result.RestStart1,result.RestEnd1,
				result.RestStart2,result.RestEnd2,result.ClassEnd,result.OvertimeStart);
			GetPendingEmpList(WorkshopNo,LineNo,RCNO,ClassNo,OverTimeDate,
					IsIdentified,IsAbnormal,null,null);
	    });
		
	}
	
	function ShowPendingEmpList(EmployeeInfos,isInit){
		var HTMLElement;
		
		$('#OTPendingEmpTable tbody').empty();
		var j=1;
		for(var i=0;i<EmployeeInfos.length;i++){
			//將取出的資料塞入Table
			var EmpInfo=EmployeeInfos[i];
			if(isInit){
				HTMLElement='<tr><td><input type="checkbox" class="selectedEmp" name="selectedEmp" value='+EmpInfo.employeeID+'></td>'+
				'<td>'+j+'</td>'+
				'<td>'+EmpInfo.employeeID+'</td>'+
				'<td>'+EmpInfo.employeeName+'</td>'+
				'<td>'+EmpInfo.deptID+'</td>'+
				'<td>'+EmpInfo.costID+'</td>'+
				'<td>'+EmpInfo.direct+'</td>'+
				'<td>'+EmpInfo.yd+'</td>'+
				'<td></td>'+
				'<td>0</td>'+
				'<td></td>'+
				'<td>0</td>'+
				'<td>未審核</td></tr>';				
			}
			else{
				HTMLElement='<tr><td><input type="checkbox" class="selectedEmp" name="selectedEmp" value='+EmpInfo.employeeID+'></td>'+
				'<td>'+j+'</td>'+
				'<td>'+EmpInfo.employeeID+'</td>'+
				'<td>'+EmpInfo.employeeName+'</td>'+
				'<td>'+EmpInfo.deptID+'</td>'+
				'<td>'+EmpInfo.costID+'</td>'+
				'<td>'+EmpInfo.direct+'</td>'+
				'<td>'+EmpInfo.yd+'</td>'+
				'<td>'+EmpInfo.overTimeInterval+'</td>'+
				'<td>'+EmpInfo.overTimeHours+'</td>'+
				'<td>'+OverTimeTypeText+'</td>'+
				'<td>'+EmpInfo.bonus+'</td>'+
				'<td>未審核</td></tr>';
				
			}
			j++;
			$('#OTPendingEmpTable tbody').append(HTMLElement);			
		}
		
		$('.selectedEmp').click(function(){
		    if($(this).prop("checked")==true){
		        //当前为选中状态
		    	$(this).parent().parent().attr("style", "background-color: #e6f0fc"); 
		    }else{
		        //
		    	$(this).parent().parent().attr("style", "background-color: white"); 
		    }
		});
		
	}
	
	function GetPendingEmpList(WorkshopNo,LineNo,RCNO,ClassNo,OverTimeDate,
			IsIdentified,IsAbnormal,OverTimeCal,currentClassNoInfo){				
		return $.ajax({
			type:'GET',
			url:'EmpsInOTSheetByDepid.show',
			data:{"WorkshopNo":WorkshopNo,"LineNo":LineNo,"RCNO":RCNO,
				"ClassNo":ClassNo,"OverTimeDate":OverTimeDate,
				"IsIdentified":IsIdentified,"IsAbnormal":IsAbnormal},
			error:function(e){
				alert(e);
			},
			success:function(result){
				var HTMLElement='';
				if(result!=null && result!=''){
					if(result.ErrorCode && result.ErrorCode==500){
						alert('無資料');
					}
					else{
						SetOverTimeEmps(result);
						
						ShowPendingEmpList(overTimeEmps,true);
					
					}
				}else{
					alert('無資料');
				}
			}
		});
	}
	
	function GetCalOverTimeFromServer(WorkshopNo,LineNo,RCNO,ClassNo,OverTimeDate,
			OverTimeType,ItemNumber,IsAbnormal) {

	    $.ajax({
	        type: 'GET',
	        url: 'GetCalOverTimeByDepid.show',
	        data: {
	        	"WorkshopNo": WorkshopNo,
	        	"LineNo":LineNo,
	            "RCNO": RCNO,
	            "ClassNo": ClassNo,
	            "CheckState": 0,
	            "OverTimeDate": OverTimeDate,
	            "OverTimeType": OverTimeType,
	            "ItemNumber":ItemNumber,
	            "IsAbnormal": IsAbnormal
	        },
	        error: function(e) {
	            alert(e);
	        },
	        success: function(result) {
				var HTMLElement='';
				if(result!=null && result!=''){
					if(result.ErrorCode && result.ErrorCode==500){
						alert('無資料,请检查所使用的賬號對應助理是否存在，或核對班別是否正確！！！');
					}
					else{
						SetOverTimeEmps(result);
					
						ShowPendingEmpList(overTimeEmps,false);
					
					}
				}
				else{
					alert('無資料,请检查所使用的賬號對應助理是否存在，或核對班別是否正確！！！');
				}
			}
	    });
	}

	
	function SetOverTimeEmps(result){
		overTimeEmps=[];
		for(var i=0;i<result.length;i++){
			//將Server取出的資料放入javaScript Object中
			var OTEmpInfo=new OverTimeSheet(result[i]["EmpID"],
					result[i]["EmpName"],
					result[i]["DeptID"],
					result[i]["Direct"],
					result[i]["CostID"],
					result[i]["SwipeCardDate"],
					result[i]["OnDutyTime"],
					result[i]["OffDutyTime"],
					result[i]["OverTimeInterval"],
					result[i]["OverTimeHour"],
					result[i]["BONUS"]);
			overTimeEmps.push(OTEmpInfo);
		}
	}
	
	/* *
	 * 將提報加班的人員資訊從DB取出後，存入Array
	 * 降低DB存取次數
	 * */
	function GetOTSubmitEmps(){
		//var selectedOTEmps='';
		var selectedEmpIDs=new Array();
		/*將已勾選人員的工號存入Array*/
		$('#OTPendingEmpTable tbody .selectedEmp:checked').each(function(){
			var xhr=$(this).parent().parent();		
			 var overTimehour = $(xhr).children().eq(9).text();
			 var dghour = $(xhr).children().eq(11).text();
				if(overTimehour=="0"&&dghour=="0"){
					  alert("工時小於等於0，有誤，請重新選擇加班人員！");
				}
				else{
					selectedEmpIDs.push($(xhr).children().eq(2).text());
				}
			
		});
		/*將已勾選人員的詳細刷卡訊息存入Array*/
		/*for(var i=0;i<selectedEmpIDs.length;i++){
			if(i==0)
				selectedOTEmps+=selectedEmpIDs[i];
			else
				selectedOTEmps+='*'+selectedEmpIDs[i];
		}*/
		return selectedEmpIDs;
	}
	
	function GetSelectedEmps(){
		var SelectedEmps=new Array();
		$('#OTPendingEmpTable tbody tr').each(function(){
			if($(this).children().children().eq(0).checked){								
				  var overTimehour = $(this).children().children().eq(9).text();
    				if(overTimehour=="0"){
    					  alert("工時小於等於0，有誤，請重新選擇加班人員！");
    				}
    				else{
    					SelectedEmps.push($(this).children().children().eq(2).text());
    				}
			}
		});
		return SelectedEmps;
	}
	
	function GetHoliday(){
		$.ajax({
	        type: 'GET',
	        url: 'checkHoliday.show',
	        data: {
	        	"OverTimeDate": OverTimeDate
	        },
	        error: function(e) {
	            alert("獲取節假日失敗！！！");
	        },
	        success: function(result) {
				if(result!=null && result!=''){
					HolidayType = result[0]["HolidayType"];
				}
				else{
					HolidayTpye = "N";
				}
			}
	    });
	}
	
});