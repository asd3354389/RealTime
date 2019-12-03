$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	ShowAllIpBindingCostSCInfo();
	getCostId()
	//ShowWorkShop();
	
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
		$('#inputIp').val('');
	     $('#inputCostID').val('');
	});
	
	$('#searchIpBindingCostSC').click(function(){
		queryCritirea=$('#queryCritirea option:selected').val();
		queryParam=$('#queryParam').val();
		ShowAllIpBindingCostSCInfo();
		
	});
	
	$('#submitIpBindingCostSC').click(function(){
		button_onclick($('#submitIpBindingCostSC')[0]);
		var IpBinding=new Object(),errorMessage='';
		IpBinding.DEVICEIP=$('#inputIp').val();
		var costIDArr= $("#inputCostID").val();
		if(costIDArr===null)
			IpBinding.COSTID='';
		else
			IpBinding.COSTID=costIDArr.join('*');
		console.log(IpBinding);
		if(IpBinding.COSTID==''||IpBinding.COSTID==null)
			errorMessage+='費用代碼不能為空';
		if(IpBinding.DEVICEIP==''||IpBinding.DEVICEIP==null)
			errorMessage+='ip不能為空';
		var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
		if(!reg.test(IpBinding.DEVICEIP))
			errorMessage+='非法ip';
		if(errorMessage==''){
			//新增綁定賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../AdminActioin/AddIpBindingCostSCInfo.do',
				data:JSON.stringify(IpBinding),
				dataType:'json',
				success:function(data){
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 $('#inputIp').val('');
						     $('#inputCostID').val('');
							 alert(data.Message);
							 ShowAllIpBindingCostSCInfo();
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
	
	function ShowAllIpBindingCostSCInfo(){
		$.ajax({
			type:'POST',
			url:'../AdminActioin/ShowAllIpBindingCostSCInfo',
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
							ShowAllIpBindingCostSCInfoTable(rawData);
						else{
							/*$('.left').css('height','727px');*/
							alert('暫無卡機信息資料');
						}
					}
				}
			}
		});	
	}
	
	function ShowAllIpBindingCostSCInfoTable(rawData){
		$('#IpBindingCostSCInfoTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td>'+executeResult[i]["DEVICEIP"]+'</td>'+
					'<td>'+executeResult[i]["COSTID"]+'</td>'+
//					'<td>'+executeResult[i]["Direction"]+'</td>'
//					'<td>'++'</td>'+
					'<td><input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td>';
				tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#IpBindingCostSCInfoTable tbody').append(tableContents);
		}
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
	/*	console.log(currentPage);
		console.log(totalRecord);
		console.log(totalPage);
		console.log(pageSize);*/
		/*goPageY(currentPage,totalRecord,totalPage,pageSize);*/
		
		
		$('.deleteBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var ip=$(parentElement).find('td').eq(0).text();
			var costid=$(parentElement).find('td').eq(1).text();
			var results=confirm("確定刪除ip為 "+ip+"費用代碼為"+costid+" 的訊息 ?");
			if(results==true){
				$.ajax({
					type:'POST',
					url:'../AdminActioin/deleteIpBindingCostSC.do',
					data:{ip:ip,costid:costid},
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
								 ShowAllIpBindingCostSCInfo();
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
		$('#WorkshopNoRestInfoListPagination').empty();
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
		
		$('#WorkshopNoRestInfoListPagination').append(paginationElement);
		
		$('.firstPage').click(function(){
			curPage=1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getPersonList(curPage,select,text);
			}else{
				getPersonList(curPage,queryCritirea,queryParam);				
			}	
		});
		
		$('.previousPage').click(function(){
			curPage=currentPage-1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getPersonList(curPage,select,text);
			}else{
				getPersonList(curPage,queryCritirea,queryParam);				
			}		
		});	
		
		$('.nextPage').click(function(){
			curPage=currentPage+1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getPersonList(curPage,select,text);
			}else{
				getPersonList(curPage,queryCritirea,queryParam);				
			}				
		});	
		
		$('.numPage').click(function(){
			var curPage=$(this).text();
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getPersonList(curPage,select,text);
			}else{
				getPersonList(curPage,queryCritirea,queryParam);				
			}		
    	});
		
	}
	
	function getPersonList(curPage,queryCritirea,queryParam){
		$.ajax({
			type:'POST',
			url:'../WorkShopNoRest/ShowWorkShopNoRestList',
			data:{curPage:curPage,queryCritirea:queryCritirea,queryParam:queryParam},
			error:function(e){
				alert('找不到資料');
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
						if(numOfRecords>0){
							ShowAllIpBindingCostSCInfoTable(rawData);
							$('#queryParam').val('');
						}
						else{
						/*	var currentPage=rawData.currentPage;
							console.log();
							var totalRecord=rawData.totalRecord;
							console.log(totalRecord);
							var totalPage=rawData.totalPage;
							var pageSize=rawData.pageSize;
							$('#FLinePersonMtY tbody').empty()
							$('.left').css('height','727px');
							refreshUserInfoPaginationY(currentPage,totalRecord,totalPage,pageSize);*/
							alert('找不到資料');
						}	
					}
				}
			}
		});
	}
	
	//顯示所有車間
	function ShowWorkShop(){
		$.ajax({
			type:'GET',
			url:'../Utils/WorkshopNo.show',
			data:{},
			async:false,
			success:function(data){
				var htmlAppender='';
			 if(data!=null && data!=''){	
				if(data.length>0 && data.StatusCode == null){
					for(var i=0;i<data.length;i++){
						htmlAppender+='<option value="'+data[i]+'">'+data[i]+'</option>';
					}
					 $('#workShop').append(htmlAppender);
				/*	 $('#ChangeWorkShop').append(htmlAppender);*/
				}
				else{
					alert('無車間資料');
				}
			 }else{
				alert('無車間資料');
			 }
			}
		});   
	}	
	
	function ShowWorkShopNo(selectClass){
		$.ajax({
			type:'GET',
			url:'../Utils/WorkshopNo.show',
			data:{},
			async:false,
			success:function(data){
				var htmlAppender='';
			 if(data!=null && data!=''){	
				if(data.length>0 && data.StatusCode == null){
					for(var i=0;i<data.length;i++){
						htmlAppender+='<option value="'+data[i]+'">'+data[i]+'</option>';
					}
					 $('.'+selectClass).append(htmlAppender);
				/*	 $('#ChangeWorkShop').append(htmlAppender);*/
				}
				else{
					alert('無車間資料');
				}
			 }else{
				alert('無車間資料');
			 }
			}
		});   
	}	
	 
	 function getCostId(){
			$.ajax({
				type:'GET',
				url:'../Account/AllCostId.show',
				data:{},
				async:false,
				success:function(data){
					var htmlAppender='';
					 if(data!=null && data!=''){
						 if(data.length>0){
							 for(var i=0;i<data.length;i++){
								 htmlAppender+='<option value="'+data[i]+'">'+data[i]+'</option>';
							 }
							 $('#inputCostID').append(htmlAppender);
						 }
						 else{
							 alert('No COSTID Data');
						 }
					 }else{
						 alert('No COSTID Data');
					 }
				}
			});
		}
})