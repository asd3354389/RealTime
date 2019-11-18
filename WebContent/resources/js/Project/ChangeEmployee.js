$(document).ready(function(){
	var selcetValue,StatusValue,startDate,userId;

	//點擊查詢
	$('#searchEmpId').click(function(){
		
		$('#select option:first').prop('selected', 'selected');
		selcetValue =$('#select option:selected').val();
		console.log("最開始的異常類型"+selcetValue);
		
		var errorMessage = '';
		//開始時間
		startDate = $('#startDate').val();
		//工號
		userId = $('#employeeID').val();
		if(startDate==="null" || startDate=='')
			errorMessage+='未選擇時間\n';
		if(userId==="null" || userId=='')
			errorMessage+='未填寫工號\n';
		
		/*console.log(startDate);
		console.log(userId);*/
		//傳數據給後台
		if(errorMessage==''){	
			$.ajax({ 
				   url:'../ChangeEmployee/ShowEmployeeList',
				   type:'POST',
				   dataType:'json',
				   data:{"startdate":startDate,"empId":userId},
				   success:function(result){ 
					
				   var StatusCode = result.StatusCode;
				    var message = result.Message;
				if(StatusCode == "500"){
					alert(message);
				}else {
					ShowQueryList(result);
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
		/*alert("點擊查詢");*/
    //賦值後台的數據
	function ShowQueryList(result){
		for(var i=0;i<result.length;i++){
			if (result[i]["Status"]=="200") {
				 $('#t1').html(result[i]["EMP_ID"]);
				 $('#t2').html(result[i]["NAME"]);
				 $('#t3').html('');
				 $("#changeTable").css("display","inline-block");//显示div
			} else {
				 $('#t1').html(result[i]["ID"]);
				 $('#t2').html(result[i]["NAME"]);
				 $('#t3').html(result[i]["NoCard"]);
				 $("#changeTable").css("display","inline-block");//显示div
				 console.log(result[i]["ID"]);
			}
			
			
		}
	}
	
	//  
	//下拉框的聯動
	$('#select').change(function(){
		/*  <option value="0">請選擇異常類型</option>
            <option value="1">請假</option>
            <option value="2">曠工</option>
            <option value="3">調出</option>
			<option value="4">新進</option>
			<option value="5">全技員</option>
			<option value="6">需重新調班</option>
			<option value="7">調休</option>*/
		var selvalue = $('#select').val();
		var status = $("#status")[0].innerHTML;
		switch (selvalue) {
		case "0": $('#status')[0].innerHTML='<option value="0">請選擇具體事由</option>';break;
		case "1": $('#status')[0].innerHTML='<option value="0">事假</option><option value="1">病假</option><option value="2">特休</option><option value="3">婚假</option><option value="4">喪假</option><option value="5">產假</option><option value="6">工傷假</option>';break;
		case "2": $('#status')[0].innerHTML='<option value="0">曠工</option>';break;
		case "3": $('#status')[0].innerHTML='<option value="0">支援</option><option value="1">調出</option>';break;
		case "4": $('#status')[0].innerHTML='<option value="0">新進</option><option value="1">調入</option>';break;
		case "5": $('#status')[0].innerHTML='<option value="0">全技員</option>';break;
		case "6": $('#status')[0].innerHTML='<option value="0">白班</option><option value="1">夜班</option>';break;
		case "7": $('#status')[0].innerHTML='<option value="0">調休</option>';break;		
		default:
			alert("erro");;
		}
		
		//$("#selectId").val()
		/*$('#status option:first').prop('selected', 'selected');*/
		selcetValue =$("#select").val();
		StatusValue = $('#status option:selected').val();
		console.log("異常類型"+selcetValue);
		console.log("具體事由"+StatusValue);
	});
	$('#status').change(function(){
		StatusValue = $('#status option:selected').val();
		console.log("具體事由"+StatusValue);
	});
	
	$('#updateStatus').click(function(){
		
		console.log("最後的異常類型"+selcetValue);
		console.log("最後的具體事由"+StatusValue);
	var errorMessage = '';
		//開始時間
		if(selcetValue=="0")
			errorMessage+='未選擇修改的狀態\n';
		//傳數據給後台
		if(errorMessage==''){	
		$.ajax({ 
				   url:'../ChangeEmployee/UpdateStatus',
				   type:'POST',
				   dataType:'json',
				   data:{"selcetValue":selcetValue,"StatusValue":StatusValue,"startdate":startDate,"empId":userId},
				   success:function(result){ 
					
				   var StatusCode = result.StatusCode;
				    var message = result.Message;
				if(StatusCode == "200"){
					alert(message);
				}else {
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
		//alert("點擊了更新狀態");
		
	});
	
   //下拉框的聯動
});