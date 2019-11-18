$(document).ready(function(){
	var startDate,endDate,MachineName,MachineNoNumber,Costid,DeptIdValue,weekValue,weekValueD,DeptIdValueD;
	var lineNo = new Array();
	
	
	
//================查詢費用代碼========================================================================================================
	$.ajax({ 
		   url:'../ProdPerson/ShowCostId',
		   cache: false,
		   type:'POST',
		   dataType:'json',
		   success:function(result){ 
			Costid = result.toString();
			console.log(typeof Costid);
			switch (Costid) {
			case "8146": lineNo = ['XR-01','XR-08','XR-12','XR-27','XR-31','XR-40','XR-41','XR-49'];
				break;
			case "9629": lineNo = ['XR-13','XR-18','XR-20','XR-21','XR-24','XR-60','XR-62'];
			break;
			case "6251": lineNo = ['XR-05','XR-19','XR-23','XR-26','XR-29','XR-37','XR-52','XR-54'];
			break;
			case "6252": lineNo = ['XR-09','XR-10','XR-36','XR-39','XR-43','XR-45','XR-47'];
			break;
			case "9097": lineNo = ['XR-80','XR-81','XR-82','XR-83','XR-84'];
			break;
			default:
				break;
			}
			
			var cch = "";
			for(var i=0;i<lineNo.length;i++){
				console.log(lineNo[i]);
				cch += "<option value = "+lineNo[i]+">"+lineNo[i]+"</option>"
			}
			$('#depid')[0].innerHTML=cch;
			$('#depidD')[0].innerHTML=cch;
			
			$('#selectWeek option:first').prop('selected', 'selected');
			$('#selectWeekD option:first').prop('selected', 'selected');
			$('#depid option:first').prop('selected', 'selected');
			$('#depidD option:first').prop('selected', 'selected');
			DeptIdValue =$('#depid option:selected').val();
			DeptIdValueD =$('#depidD option:selected').val();
			weekValue = $('#selectWeek option:selected').val();
			weekValueD = $('#selectWeekD option:selected').val();
			/*console.log("默认选择的代码"+DeptIdValue);
			console.log("默认选择的週數"+weekValue);*/
			console.log("刪除默認選擇的週數"+weekValueD);
		}  
	});	
	$('#depid').change(function(){
		
		//console.log("选择的部门代码"+$('#depid option:selected').val());
		DeptIdValue = $('#depid option:selected').val();
		console.log("更改部门代码"+DeptIdValue);
	});
$('#depidD').change(function(){
		
		//console.log("选择的部门代码"+$('#depid option:selected').val());
		DeptIdValueD = $('#depidD option:selected').val();
		console.log("更改部门代码"+DeptIdValueD);
	});
 
//================查詢費用代碼========================================================================================================
//|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
//================新增日期操作===========================================================================================================
	//選擇週數
	$('#selectWeek').change(function getWeekDate(){
		var year = $("#selectYear").val();
		var week = $("#selectWeek").val();
		console.log(week);
		var start_end = getStartAndEndByWeek(year,week);
		var se_arr = start_end.split("_");
		startDate = se_arr[0];
		endDate = se_arr[1];
		var sumDate = "起止時間為："+startDate+" 至 "+ endDate;
		$('#showWeekDate')[0].innerHTML='<option value="0">'+sumDate+'</option>';
	});
	
	//根据某年某周获取该周的起始和截止日期  create by lishaodng
	    function getStartAndEndByWeek(year,week){
	        var Nowdate=new Date(year, 0, 1);  
	        var nowweek_start=new Date(Nowdate-(Nowdate.getDay()-1)*86400000);    
	        var nowweek = 1;
	        var start = new Date(nowweek_start-86400000*7*(nowweek-week)).format("yyyy-MM-dd");        
	        var end = new Date(nowweek_start-86400000*7*(nowweek-week) + 86400000*6).format("yyyy-MM-dd");    
	        return start + '_' + end;
	    }
	
	//(2019-01-14,0)
	function addDate(date, days) {
        var date = new Date(date);
        date.setDate(date.getDate() + days);
        var month = date.getMonth() + 1;
        var day = date.getDate();
        return date.getFullYear() + '-' + getFormatDate(month) + '-' + getFormatDate(day);
    }

	 // 日期月份/天的显示，如果是1位数，则在前面加上'0'
    function getFormatDate(arg) {
        if (arg == undefined || arg == '') {
            return '';
        }
 
        var re = arg + '';
        if (re.length < 2) {
            re = '0' + re;
        }
 
        return re;
    }
 //================新增日期操作===========================================================================================================
//|||||||||||||||||||||||||||||||||||||||||||||

//================新增操作===========================================================================================================
    $('#InsertProdperson').click(function() {
    	
    	DeptIdValue = $('#depid option:selected').val();
		console.log("最后选择的部门代码"+DeptIdValue);
		var errorMessage = '';
		//起止時間 MachineName MachineNo  Number
		startEndDate = $('#showWeekDate').val();
		//幾種名稱
		MachineName = $('#MachineName').val();
		//幾種料號
		MachineNo = $('#MachineNo').val();
		//人數
		Number = $('#Number').val();
		if(MachineName==="null" || MachineName=='')
			errorMessage+='未填寫幾種名稱\n';
		if(MachineName==="null" || MachineName=='')
			errorMessage+='未填寫幾種料號\n';
		if(Number==="null" || Number=='')
			errorMessage+='未填寫人數\n';
		var Prod=[];
		var dataArr = new Array(7);
		for (var i = 0; i < dataArr.length; i++) {
			dataArr[i] = addDate(startDate,i);
		}
		//Costid
		console.log("最後选择的週數"+weekValue);
	    console.log("费用代码=="+Costid);
		var ProdPerson={PLAN_DATE_YEAR:$('#year').val(),
				PLAN_DATE_WEEK:$('#selectWeek option:selected').val(),
				SHIFT:$('#shift').val(),
				COSTID:Costid,
				DEPID:$('#depid').val(),
				PROD_NAME:$('#MachineName').val(),
				PROD_CODE:$('#MachineNo').val(),
				NUMBER_OF_PEOPLE:$('#Number').val(),
				DataList:dataArr};
		console.log(ProdPerson);
		//傳數據給後台
		if(errorMessage==''){	
			$.ajax({ 
				   url:'../ProdPerson/InsertProdPersonList',
				   cache: false,
				   type:'POST',
				   contentType: "application/json",
				   dataType:'json',
				   data:JSON.stringify(ProdPerson),
				   success:function(result){ 
					
			      var StatusCode = result.StatusCode;
				  var message = result.Message;
				  alert(message);
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
//================新增操作===========================================================================================================	
//|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
//================刪除操作===========================================================================================================
    $('#DeleteProdperson').click(function() {
    	GetSelectProdList();
    });
    function GetSelectProdList() {
    	$('#ProdPersonD tbody').empty();
    	DeptIdValueD = $('#depidD option:selected').val();
		console.log("最后选择的部门代码"+DeptIdValueD);
    	var ProdPerson={PLAN_DATE_YEAR:$('#yearD').val(),
				PLAN_DATE_WEEK:$('#selectWeekD option:selected').val(),
				COSTID:Costid,
				DEPID:$('#depidD').val()
				};
    	 console.log(ProdPerson);
    	
    	$.ajax({ 
			   url:'../ProdPerson/SelectProdPersonList',
			   cache: false,
			   type:'POST',
			   contentType: "application/json",
			   dataType:'json',
			   data:JSON.stringify(ProdPerson),
			   success:function(result){ 
			  var message = result.Message;
			  if (result.length> 0) {
				  console.log(result);
				  //alert("成功");
				  ShowSelectProdListtTable(result);
			} else {
				  alert("失敗");
			}
			}  
		});
	}
    function ShowSelectProdListtTable(rawData) {
    	
    	var recordId = null;
    	for(var i=0;i<rawData.length;i++){
    		var	tableContents='<tr>'+
    		'<td style="text-align:center;display:none;" id="t0">'+rawData[i]["RECORDID"]+'</td>'+
			'<td style="text-align:center;" id="t1">'+rawData[i]["DEPID"]+'</td>'+
			'<td style="text-align:center;" id="t2">'+rawData[i]["PLAN_DATE_WEEK"]+'</td>'+
			'<td style="text-align:center;" id="t3">'+rawData[i]["SHIFT"]+'</td>'+
			'<td style="text-align:center;" id="t4">'+rawData[i]["PROD_NAME"]+'</td>'+
			'<td style="text-align:center;" id="t5">'+rawData[i]["PROD_CODE"]+'</td>'+
			'<td style="text-align:center;" id="t6">'+rawData[i]["NUMBER_OF_PEOPLE"]+'</td>'+
		    '<td><input type="button" value="刪除排配" style="margin-left: 5px;width:90%;" class="deleteProd"></td></tr>';
			$('#ProdPersonD tbody').append(tableContents);
			$("#prodPersonTable").css("display","inline-block");//显示div
			/*recordId = rawData[i]["RECORDID"];
			console.log(recordId);*/
    	}	
    	
    	 $(".deleteProd").click(function(){ 
    		 
    		
    	
    		var parentElement = $(this).parent().parent();
 			var RECORDID = $(parentElement).find('td').eq(0).text();
 			var results=confirm("確定刪除機種排配信息?");
 			console.log(RECORDID);
 			if(results==true){
 				 $.ajax({ 
     				   url:'../ProdPerson/DeleteProdPersonList',
     				   type:'POST',    				  
     				   data:{RECORDID:RECORDID},
     				   success:function(result){ 
     				  var message = result.Message;	
     				  var StatusCode = result.StatusCode;
     				  if (StatusCode == "200") {
     					  alert(message);
     					  //alert("成功");
     					  GetSelectProdList();
     				} else {
     					 alert(message);
     				}
     				}  
     			});
 			}
    	
    	   
    	 });
    }
   
//================刪除操作===========================================================================================================
//|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
//================刪除日期操作=========================================================================================================
  //選擇週數
	$('#selectWeekD').change(function getWeekDate(){
		var year = $("#selectYearD").val();
		var week = $("#selectWeekD").val();
		console.log(week);
		var start_end = getStartAndEndByWeek(year,week);
		var se_arr = start_end.split("_");
		startDate = se_arr[0];
		endDate = se_arr[1];
		var sumDate = "起止時間為："+startDate+" 至 "+ endDate;
		$('#showWeekDateD')[0].innerHTML='<option value="0">'+sumDate+'</option>';
	});
//================刪除日期操作========================================================================================================= 
  //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
});