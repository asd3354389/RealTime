$(function(){

	
	//顯示列表的信息
	var messageList;
	ShowAllIp();
	$('#addNewAccountBtn').click(function(){
	//$('#closeBtn').click(function(){}
		//closeBtn
		$('#changebdOT').click(function(){	
		
			BindingIp();
		
		});
	
	});

	$('#resetSubmit').click(function(){	
		
		$("#inputIp").val("");
		$("#inputCostId").val("");
		$("#inputId").val("");
	
	});
	//綁定Ip
	function BindingIp() {

		//ip地址
		var DeviceIp = $("#inputIp").val();
		//費用代碼
		var DeptId = $("#inputCostId").val();
		//工號
		var ID = $("#inputId").val();
		//alert(ID);
		if (DeviceIp == "" || DeptId == ""||ID == "" ) {
			alert("你輸入的信息不完整,請重新輸入!!");
		}else{
			$.ajax({ 
				   url:"../IpBinding/BindingIp", 
				   type:"POST",
				   async: false,
				   data:{"DeviceIp":DeviceIp,"DeptId":DeptId,"ID":ID},
				   success:function(result){ 
				    var StatusCode = result.StatusCode;
				    var message = result.message;
				if(StatusCode == "500"){
					alert(message);
				}else if(StatusCode == "200"){
					alert(message);
					$("#inputIp").val("");
					$("#inputCostId").val("");
					$("#inputId").val("");
					
					ShowAllIp();
					//$("#closeBtn").click();
					//ShowMessage(message);
				}  
				   }, 
				   error:function(err){ 
				    console.log("NG:"+err); 
				   } 
			
			}) 
		}
		
		
}	
	
	//顯示綁定的Ip
	function ShowAllIp() {
		$.ajax({
			url:"../IpBinding/ShowAllIpList",
			type:"POST",
			async : false,
			success:function(result){ 
				var StatusCode = result.StatusCode;
				var message = result.message;
				if(StatusCode == "500"){
					alert(message);
				}else if(StatusCode == "200"){
					ShowAllIpList(message);
				}
			}, 
			error:function(err){ 
				 alert("NG:"+err+"顯示錯誤"); 
			} 
		})
	}
	
	function ShowAllIpList(result) {
	
		$('#Personbinding tbody').empty();
		var obj = JSON.parse(result)
		for(var i=0;i<obj.length;i++){
			var	tableContents='<tr>'+
					'<td>'+obj[i].DEVICEIP+'</td>'+
					'<td>'+obj[i].DEPTID+'</td>'+
					'<td>'+obj[i].UPDATE_USERID+'</td>'+
					'<td>'+obj[i].ENABLED+'</td>';
				tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#Personbinding tbody').append(tableContents);
		}
	}
});