$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	var reg = new RegExp("^[0-9]{4}$");
	ShowAllPersonList();
	ShowWorkShop();
	$("#setCostWorkShop").click(function(){
		$('#setCostWorkShop').attr("disabled",true);
		var Cost=$('#WorkShopCost').val();
		var CostNo=Cost.split(",");
		var WorkShopNo=$('#workShopNo').val();
//		console.log(WorkShopNo);
		var list=[],errorMessage='',CostId=[];
		//console.log(CostNo.length);
		if(CostNo==null||CostNo==''){
			errorMessage+='费用代码不能为空\n';
		}
		if(WorkShopNo==null||WorkShopNo==""){
			errorMessage+='車間號不能爲空\n';
		}else{
			for(var i=0;i<CostNo.length;i++){
				if(CostId.indexOf(CostNo[i])==-1){
					CostId.push(CostNo[i]);
				}
			}
//			console.log(CostId);
			
			for(var i=0;i<CostId.length;i++){
				 checkCostIdDuplicate(CostId[i])
				 if(!isUserNameValid){
					 errorMessage+="輸入的費用代碼有不存在的費用代碼！";
					 break;
				 }
				for(var j=0;j<WorkShopNo.length;j++){
					var data = new Object();
					if(!reg.test(CostId[i])){
						errorMessage+='费用代码必须都是4位数字\n';
						break;
					}
					data.CostId = CostId[i];
					data.WorkShopNo=WorkShopNo[j];
//					console.log(data.WorkShopNo);
//					console.log(data);
					list.push(data);
				}
			}
		}
		
//		console.log(list);
//		alert(errorMessage);
		if(errorMessage==''){
			//新增賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../ExceptionCost/AddExceptionCost.do',
				data:JSON.stringify(list),
				dataType:'json',
				success:function(data){
//					$('#submitNewUser').prop("disabled",false);
//					 if(data!=null && data!=''){
//						 if(data.StatusCode=="200"){
//							 alert(data.Message);			
//							 $('#inputUserName').val('');
//							 $('#inputChineseName').val('');
//							 $('#inputDepID').val('');
//							 $('#inputCostID').val(null);
//							 $("#inputAssistantId").val('');
//							 $('#inputPhoneTel').val('');
//							 $('#inputRole').val('');
//							 $('#insertAccountDialog').modal('hide');
//							 ShowAllAccountList();
//						 }
//						 else{
//							 alert(data.Message);
//						 }
//					 }else{
//						 alert('新增賬號基本資料失敗!');
//					 }
					if(data!=null&&data!=''){
						ShowAllPersonList();
						alert(data.Message);
						$('#WorkShopCost').val('');
						$('#workShopNo').selectpicker('val',['noneSelectedText'])
						$("#workShopNo").selectpicker('refresh');

					}
					
				},
				error:function(e){
					alert('新增車間例外費用代碼發生錯誤');
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
	
	$('#searchExceCostBtn').click(function(){
		curPage = 1;
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam;
		if(queryCritirea=="CostId"){
			queryParam=$('#queryParam').val();
		}else if(queryCritirea=="WorkShopNo"){
			queryParam=$('#changeWorkShopNo').val();
		}
		
	
		if(queryParam==""){
			ShowAllPersonList();
		}else{
			/*searchPersonList(curPage,queryCritirea,queryParam);
			queryParam=='';*/
			getPersonList(curPage,queryCritirea,queryParam);
			
		}
	})

	$('#workShopNo').selectpicker({
		  'selectedText': 'cat'
			 // size: 6
	 });

	$('.selectpicker').selectpicker('val', 'Mustard');  
	  
	
	$('#queryCritirea').change(function(){
		var type= $(this).find('option:selected').val();
		if(type=="CostId"){
			$('#queryParam').css("display","inline-block");
			$('#changeWorkShopNo').css("display","none");
		}else if(type=="WorkShopNo"){
			$('#queryParam').css("display","none");
			$('#changeWorkShopNo').css("display","inline-block");
		}else{
			
		}
	})
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
						 $('#workShopNo').append(htmlAppender);
						 $('#changeWorkShopNo').append(htmlAppender);
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
	
	function ShowAllPersonList(){
		$.ajax({
			type:'GET',
			url:'../ExceptionCost/ShowExceptionList',
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
						if(numOfRecords>0)	
							ShowAllExceListTable(rawData);
						
						else{
							/*$('.left').css('height','727px');*/
							/*ShowAllPersonListTable(rawData);*/
							ShowAllExceListTable(rawData);
							setTimeout(function(){ alert('找不到車間例外費用代碼資料！'); }, 100);			
//							setTimeout(function(){ alert('暫無離崗卡與費用代碼綁定資料'); }, 100);					
						}
					}
				}
			}
		});	
	}
	
	function ShowAllExceListTable(rawData){
		$('#Personbinding tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr>'+
					'<td>'+executeResult[i]["WorkShopNo"]+'</td>'+
					'<td>'+executeResult[i]["CostId"]+'</td>'
					var enabled =executeResult[i].Enabled=="Y"?'已生效':'';		
					tableContents+='<td>'+enabled+'</td>'+
					'<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link"><input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td>';
				   tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#Personbinding tbody').append(tableContents);
		}
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
		
		$(".editBtn").click(function(){
			var parentElement = $(this).parent().parent();
			var WorkShopNo=$(parentElement).find('td').eq(0).text();
			/*$(parentElement).find('td').eq(0).html('<select class="changeWorkShopNo input-small"></select>');
			
			ShowWorkShopNo('changeWorkShopNo');
			
			$(parentElement).find('td .changeWorkShopNo option').each(function(){
				if($(this).val()==WorkShopNo){
					$(this).prop('selected',true);
				}
			});*/
			
			var CostNo=$(parentElement).find('td').eq(1).text();
			$(parentElement).find('td').eq(1).html('<input type="text" class="changeCostNo input-small" maxlength="4" value="'+CostNo+'">');
//			$(parentElement).find('td').eq(2).html('<textarea class="input-small changeWorkShop_Desc" id="message-text" value="'+WorkShop_Desc+'"></textarea>');
			
//			$(parentElement).children().find('.editBtn .deleteBtn').hide();
			$(parentElement).find('td').eq(3).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
     
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var User=new Object(),errorMessage='';
//				var Direction=$(parentElement).find('.changeStatus option:selected').eq(0).text();
				/*User.WorkShopNo = $(parentElement).find('td option:selected').eq(0).val();*/
				User.CostId=$(parentElement).find('td input:text').eq(0).val();	
				User.O_CostId = CostNo;
				User.O_WorkShopNo = WorkShopNo;
				
				
				if(User.CostId==="null" || User.CostId=='')
					errorMessage+='費用代碼未填寫\n';
				if(!reg.test(User.CostId))	
					errorMessage+='費用代碼不符合規格！必須是4位數\n';
				checkCostIdDuplicate(User.CostId);
				if(!isUserNameValid){
					errorMessage+='此费用代码資料不存在';
				}
				checkWorkShopCost(User.CostId,User.O_WorkShopNo);
				if(isUserNameValid){
					errorMessage+='此费用代码綁定此車間資料已存在，請重新輸入！';
				}
				/*if(User.WorkShopNo==="null" || User.WorkShopNo=='')
					errorMessage+='車間號未填寫\n';*/
				
//				console.log(User);
				if(errorMessage==''){	
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../ExceptionCost//UpdateExceCost',
						data:JSON.stringify(User),
						dataType:'json',
						error:function(e){
							alert(e);
							},
						success:function(data){
							  if(data!=null && data!=''){
								  if(data.StatusCode=="200"){
									  alert(data.Message);
									  $(parentElement).find('.editBtn,.deleteBtn').show();
									  $(parentElement).find('td').eq(0).html(User.WorkShopNo);
									  $(parentElement).find('td').eq(1).html(User.CostId);
									  $(parentElement).find('.confirmBtn,.cancelBtn').remove();
								  }
								  else{
									  alert(data.Message);
								  }
							  }else{
								  alert('操作失敗！')
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
			
			$('.cancelBtn').click(function(){
				var parentElement=$(this).parent().parent();
				$(parentElement).find('.editBtn,.deleteBtn').show();
				$(parentElement).find('td').eq(0).html(WorkShopNo);
				$(parentElement).find('td').eq(1).html(CostNo);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})					
		})
		
		$('.deleteBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var CosttID=$(parentElement).find('td').eq(1).text();
			var WorkShopNo=$(parentElement).find('td').eq(0).text();
			var user={};
			user["CostId"]=CosttID;		
			user["WorkShopNo"]=WorkShopNo;
			var results=confirm("確定刪除車間號為 "+WorkShopNo+" 的例外費用代碼"+CosttID+"嗎?");
			console.log(user);
			if(results==true){
				$.ajax({
					type:'POST',
					url:'../ExceptionCost/RelieveExceCost',
					contentType: "application/json",
					data:JSON.stringify(user),
					dataType:'json',
					error:function(e){
						alert(e);
					},
					success:function(data){
						 if(data!=null && data!=''){
							 if(data.StatusCode=="200"){
								 alert(data.Message);
								 
								var parentElement=$(this).parent().parent();
								//刪除，所以將此列從畫面移除
								parentElement.remove();
								  
								ShowAllPersonList();
							 }
							 else{
								 alert(data.Message);
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
		$('#PersonListPagination').empty();
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
		
		$('#PersonListPagination').append(paginationElement);
		
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
			type:'GET',
			url:'../ExceptionCost/ShowExceptionList',
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
							ShowAllExceListTable(rawData);
							$('#queryParam').val('');
						}
						else{
						
							alert('找不到資料');
						}	
					}
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
	//判斷是否存在此費用代碼
	 function checkCostIdDuplicate(CostId){
//		 alert(1);
			if(CostId!=""){
				$.ajax({
					type:'POST',
					url:'../ExceptionCost/checkExceCost.do',
					data:{
						CostId:CostId
					},
					async:false,
					error:function(e){
						alert(e);
					},
					success:function(data){	
						 if(data!=null && data!=''){
							 if(data.StatusCode==200){
//								 alert(data.Message);
								 isUserNameValid=true;
							 }
							 else
								 isUserNameValid=false;
						 }else{
							 isUserNameValid=false;
						 }
					}
				});
			}
		}
	 
	 function checkWorkShopCost(CostId,WorkShopNo){
//		 alert(1);
			if(CostId!=""&&WorkShopNo!=""){
				$.ajax({
					type:'POST',
					url:'../ExceptionCost/checkWorkShopCost.do',
					data:{
						CostId:CostId,
						WorkShopNo:WorkShopNo
					},
					async:false,
					error:function(e){
						alert(e);
					},
					success:function(data){	
						 if(data!=null && data!=''){
							 if(data.StatusCode==200){
//								 alert(data.Message);
								 isUserNameValid=true;
							 }
							 else
								 isUserNameValid=false;
						 }else{
							 isUserNameValid=false;
						 }
					}
				});
			}
		}
})