$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	ShowAllAppLoginInfo();
	
	 var CLICKTAG = 0;
     function button_onclick(pElement){
         if (CLICKTAG == 0) {  
             CLICKTAG = 1;  
             pElement.disabled=true;
             // 等待3s后重置按钮可用
             setTimeout(function () { CLICKTAG = 0 ; pElement.disabled=false;}, 2000);  
         }
     }
	
	$('#resetSubmit').click(function(){
		$('#com_name').val('');
		$('#inputIp').val('');
	     $('#inputCostID').val('');
	     $('#inputId').val('');
	     $('#inputTel').val('');
	});
	
	$('#searchAppLogin').click(function(){
		queryCritirea=$('#queryCritirea option:selected').val();
		queryParam=$('#queryParam').val();
		ShowAllAppLoginInfo();
		
	});
	
	$('#submitAppLogin').click(function(){
		button_onclick($('#submitAppLogin')[0]);
		var AllLoin=new Object(),errorMessage='';
		AllLoin.com_name=$('#com_name').val();
		AllLoin.ip=$('#inputIp').val();
		AllLoin.costid=$('#inputCostID').val();
		AllLoin.id=$('#inputId').val();
		AllLoin.tel=$('#inputTel').val();
		AllLoin.control_type=$('#inputType').val();
		
		console.log(AllLoin);
		if(AllLoin.control_type==''||AllLoin.control_type==null)
			errorMessage+='費用代碼不能為空';
		if(AllLoin.ip==''||AllLoin.ip==null)
			errorMessage+='ip不能為空';
		var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
		if(AllLoin.control_type == 'C'){
			if(!reg.test(AllLoin.ip))
				errorMessage+='非法ip';
		}
		if(errorMessage==''){
			//新增綁定賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../AdminActioin/AddAppLoginInfo.do',
				data:JSON.stringify(AllLoin),
				dataType:'json',
				success:function(data){
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 $('#com_name').val('');
								$('#inputIp').val('');
							     $('#inputCostID').val('');
							     $('#inputId').val('');
							     $('#inputTel').val('');
							 alert(data.Message);
							 ShowAllAppLoginInfo();
							/* alert(data.Message);			
							 $('#inputUserName').val('');
							 $('#inputChineseName').val('');
							 $('#inputDepID').val('');
							 $('#inputCostID').val(null);
							 $("#inputAssistantId").val('');
							 $('#inputPhoneTel').val('');
							 $('#inputRole').val('');
							 $('#insertAccountDialog').modal('hide');
							 ShowAllAccountList();*/						 
						 }
						 else{
							 alert(data.Message);
						 }
					 }else{
						 alert('新增卡機綁定可刷卡費用代碼失敗!');
					 }
				},
				error:function(e){
					alert('新增卡機綁定可刷卡費用代碼發生錯誤');
				}
			});
		}
	    else{
	    	if(errorMessage.length>0 ||errorMessage!='' ){
		    alert(errorMessage);		
			event.preventDefault(); //preventDefault() 方法阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
	    	}
	    }	
	})
	
	function ShowAllAppLoginInfo(){
		$.ajax({
			type:'POST',
			url:'../AdminActioin/ShowAllAppLoginInfo',
			data:{curPage:curPage,queryCritirea:queryCritirea,queryParam:queryParam},
			error:function(e){
//				alert('找不到資料');
				alert(0);
			},
			success:function(rawData){	
				if (rawData != null && rawData != "") {
					var executeResult=rawData["list"];
					var errorResponse=executeResult.ErrorMessage;
					if(errorResponse!=null){
						alert('找不到資料');
					}
					else{
						var numOfRecords=executeResult.length;
						if(numOfRecords>0)	
							ShowAllAppLoginInfoTable(rawData);
						else{
							/*$('.left').css('height','727px');*/
							alert('暫無卡機信息資料');
						}
					}
				}
			}
		});	
	}
	
	function ShowAllAppLoginInfoTable(rawData){
		$('#AppLoginInfoTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td>'+executeResult[i]["com_name"]+'</td>'+
					'<td>'+executeResult[i]["ip"]+'</td>'+
					'<td>'+executeResult[i]["costid"]+'</td>'+
					'<td>'+executeResult[i]["id"]+'</td>'+
					'<td>'+executeResult[i]["tel"]+'</td>';
			if(executeResult[i]["control_type"]=="C"){
				tableContents+='<td>例外電腦ip</td>';
			}else if(executeResult[i]["control_type"]=="E"){
				tableContents+='<td>受管控ip</td>';
			}else{
				tableContents+='<td>未知類型</td>';
			}
			
					
					tableContents+='<td><input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td></tr>';
				
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#AppLoginInfoTable tbody').append(tableContents);
		}
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
	/*	console.log(currentPage);
		console.log(totalRecord);
		console.log(totalPage);
		console.log(pageSize);*/
		/*goPageY(currentPage,totalRecord,totalPage,pageSize);*/
		
		
		$('.deleteBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var ip=$(parentElement).find('td').eq(1).text();
			var results=confirm("確定刪除ip為 "+ip+" 的訊息 ?");
			if(results==true){
				$.ajax({
					type:'POST',
					url:'../AdminActioin/deleteAppLogin.do',
					data:{ip:ip},
					error:function(e){
						alert(e);
					},
					success:function(data){
						 if(data!=null && data!=''){
							 if(data.StatusCode=="200"){
								 alert(data.ErrorMessage);
								 /*
								var parentElement=$(this).parent().parent();
								//刪除，所以將此列從畫面移除
								parentElement.remove();
								  */
								 ShowAllAppLoginInfo();
							 }
							 else{
								 alert(data.ErrorMessage);
							 }
						 }else{
							 alert('操作失敗!')
						 }
					}
				});
			}
		});
	}
	
	
	
	function refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize){
		$('#AppLoginInfoListPagination').empty();
		var paginationElement='頁次：'+currentPage+'/'+totalPage +'&nbsp;每页:&nbsp;'+pageSize+'&nbsp;共&nbsp;'+totalRecord+'&nbsp;條&nbsp;';
		if(currentPage==1)
			paginationElement+='<a href ="javascript:return false;">首页</a>';		  
		else
			paginationElement+='<a class="firstPage">首页</a>';

		if(currentPage>1)
			paginationElement+='&nbsp;<a class="previousPage">上一頁</a>';
		else
			paginationElement+='&nbsp;<a href ="javascript:return false;">上一頁</a>';
		
	   /* for(var i=1;i <= totalPage;i++){
	    	paginationElement+='&nbsp;<a class="numPage">' + i +'</a>&nbsp;';	    	
	    }*/
		if(currentPage<totalPage)
			paginationElement+='<a class="nextPage">下一頁</a>';
		else
			paginationElement+='<a href ="javascript:return false;">下一頁</a>';
		
		$('#AppLoginInfoListPagination').append(paginationElement);
		
		$('.firstPage').click(function(){
			curPage=1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				ShowAllAppLoginInfo(curPage,select,text);
			}else{
				ShowAllAppLoginInfo(curPage,queryCritirea,queryParam);				
			}	
		});
		
		$('.previousPage').click(function(){
			curPage=currentPage-1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				ShowAllAppLoginInfo(curPage,select,text);
			}else{
				ShowAllAppLoginInfo(curPage,queryCritirea,queryParam);				
			}		
		});	
		
		$('.nextPage').click(function(){
			curPage=currentPage+1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				ShowAllAppLoginInfo(curPage,select,text);
			}else{
				ShowAllAppLoginInfo(curPage,queryCritirea,queryParam);				
			}				
		});	
		
		$('.numPage').click(function(){
			var curPage=$(this).text();
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				ShowAllAppLoginInfo(curPage,select,text);
			}else{
				ShowAllAppLoginInfo(curPage,queryCritirea,queryParam);				
			}		
    	});
		
	}
})