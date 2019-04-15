$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	init();
	
	function init(callback){
		ShowAllAccountList();
		getAssistantId('inputAssistantId');
		getCostId();
	}
	
	function ShowAllAccountList(){
		$.getJSON('../Account/AllAccount.show?curPage=1&queryCritirea=&queryParam=',function(rawData){
			var executeResult=rawData["list"];
			if(executeResult.StatusCode!="500"){
				if(executeResult.length>0){
					ShowAllAccountTable(rawData);
				}
				else{
					alert("NO Account Data!!");
				}
			}
			else{
				alert(executeResult.ErrorMessage);
			}
		});
	}
	
	function ShowAllAccountTable(rawData){
		$('#accountInfoTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td>'+executeResult[i]["USERNAME"]+'</td>'+
					'<td>'+executeResult[i]["CHINESENAME"]+'</td>'+
					'<td>'+executeResult[i]["ASSISTANT_ID"]+'</td>'+
					'<td>'+executeResult[i]["DEPARTMENTCODE"]+'</td>'+
					'<td style="word-wrap:break-word;">'+executeResult[i]["COSTID"]+'</td>'+
					'<td>'+executeResult[i]["PHONE_TEL"]+'</td>'+
			        '<td>'+executeResult[i]["ROLE"]+'</td>';
					/*switch(executeResult[i]["ENABLED"]){
					case 0:
						tableContents+='<td>Disable</td>';
						break;
					case 1:
						tableContents+='<td>Enable</td>';
						break;
					default:						
						break;
					}		*/
					tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">'+
					'<input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td></tr>';
					$('#accountInfoTable tbody').append(tableContents);
		}	
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
		
		$('.editBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var userName=$(parentElement).find('td').eq(0).text();
			var assistantID=$(parentElement).find('td').eq(2).text();
			$(parentElement).find('td').eq(2).html('<select class="changeAssistantId input-small"></select>');
			
			getAssistantId('changeAssistantId');
			
			$(parentElement).find('td .changeAssistantId option').each(function(){
				if($(this).val()==assistantID){
					$(this).prop('selected',true);
				}
			});
			
			var depid=$(parentElement).find('td').eq(3).text();
			$(parentElement).find('td').eq(3).html('<input type="text" class="changeCostId input-small" value="'+depid+'">');
			
			var costId=$(parentElement).find('td').eq(4).text();
			$(parentElement).find('td').eq(4).html('<input type="text" class="changeCostId input-small" value="'+costId+'">');
			
			var phoneTel=$(parentElement).find('td').eq(5).text();
			$(parentElement).find('td').eq(5).html('<input type="text" class="changePhoneTel input-small" value="'+phoneTel+'">');
			
			var role=$(parentElement).find('td').eq(6).text();
			$(parentElement).find('td').eq(6).html('<input type="text" class="changeRole input-small" value="'+role+'">');
			
			$(parentElement).children().find('.editBtn .deleteBtn').hide();
			$(parentElement).find('td').eq(7).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
			
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var User=new Object(),errorMessage='';
				User.USERNAME=$(parentElement).find('td').eq(0).text();
				User.ASSISTANT_ID=$(parentElement).find('option:selected').eq(0).val();
				User.DEPARTMENTCODE=$(parentElement).find('td input:text').eq(0).val();
				User.COSTID=$(parentElement).find('td input:text').eq(1).val();
				User.PHONE_TEL=$(parentElement).find('td input:text').eq(2).val();							
				User.ROLE=$(parentElement).find('td input:text').eq(3).val();
					
				if(User.DEPARTMENTCODE==="null" || User.DEPARTMENTCODE=='')
					errorMessage+='部門代碼未填寫\n';
				if(User.COSTID==="null" || User.COSTID=='')
					errorMessage+='所報加班部門費用代碼未填寫\n';
				
				if(User.ASSISTANT_ID==="null" || User.ASSISTANT_ID=='')
					errorMessage+='報加班助理ID未選取\n';
				
				if(User.PHONE_TEL==="null" || User.PHONE_TEL=='')
					errorMessage+='分機未填寫\n';	
				
				if(User.ROLE==="null" || User.ROLE==''){
					errorMessage+='賬號權限未填寫\n';	
				}
				else{
					var regx=/^ROLE_/;
					if(!User.ROLE.match(regx)){
						errorMessage+='賬號權限格式錯誤，請重新輸入\n'
					}
				}
				
				if(errorMessage==''){
					
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../Account/UpdateAccount.do',
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
									  $(parentElement).find('td').eq(2).html(User.ASSISTANT_ID);
									  $(parentElement).find('td').eq(3).html(User.DEPARTMENTCODE);
									  $(parentElement).find('td').eq(4).html(User.COSTID);
									  $(parentElement).find('td').eq(5).html(User.PHONE_TEL);
									  $(parentElement).find('td').eq(6).html(User.ROLE);
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
				$(parentElement).find('td').eq(2).html(assistantID);
				$(parentElement).find('td').eq(3).html(depid);
				$(parentElement).find('td').eq(4).html(costId);
				$(parentElement).find('td').eq(5).html(phoneTel);
				$(parentElement).find('td').eq(6).html(role);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})
		});
		
		
		$('.deleteBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var deleteUserName=$(parentElement).find('td').eq(0).text();
			var results=confirm("確定刪除 "+deleteUserName+" 的基本資料 ?");
			if(results==true){
				$.ajax({
					type:'GET',
					url:'../Account/DisableAccount.do',
					data:{userName:deleteUserName},
					error:function(e){
						alert(e);
					},
					success:function(data){
						 if(data!=null && data!=''){
							 if(data.StatusCode=="200"){
								 alert(data.Message);
								 /*
								var parentElement=$(this).parent().parent();
								//刪除，所以將此列從畫面移除
								parentElement.remove();
								  */
								 ShowAllAccountList();
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
	
		
	function getAssistantId(selectClass){
		$.ajax({
			type:'GET',
			url:'../Account/AllAssistant.show',
			data:{},
			async:false,
			success:function(data){
				var htmlAppender='<option value="">--- 請選擇 ---</option>';
				 if(data!=null && data!=''){
					 if(data.length>0){
						 for(var i=0;i<data.length;i++){
							 htmlAppender+='<option value="'+data[i]["ASSISTANTID"]+'">'+data[i]["ASSISTANTID"]+'--'+data[i]["ASSISTANTNAME"]+'</option>';
						 }
						 $('.'+selectClass).append(htmlAppender);
					 }
					 else{
						 alert('No Assistant Data');
					 }
				 }else{
					 alert('No Assistant Data');
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
	
	$('#searchAccountBtn').click(function(){
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		getAsyncAccountInfo(curPage,queryCritirea,queryParam)	
	});
	
	 $('#inputCostID').selectpicker({
	       'selectedText': 'cat'
		 // size: 6
	    });

	   $('.selectpicker').selectpicker('val', 'Mustard');  

	    
	 //  $('#inputCostID').selectpicker('val', costIDArr);
	
	$('#submitNewUser').click(function(){
		var user={},errorMessage='';
		user["USERNAME"]=$('#inputUserName').val();
		user["CHINESENAME"]=$('#inputChineseName').val();
		user["DEPARTMENTCODE"]=$('#inputDepID').val();
		var costIDArr= $("#inputCostID").val();
		if(costIDArr===null)
			user["COSTID"]='';
		else
			user["COSTID"]=costIDArr.join('*');
		
		user["ASSISTANT_ID"]=$('#inputAssistantId option:selected').val();
		user["PHONE_TEL"]=$('#inputPhoneTel').val();
		user["ROLE"]=$('#inputRole').val();		
		
		if(user["USERNAME"]==="null" || user["USERNAME"]=='')
			errorMessage+='帳號未填寫\n';
		
		checkUserNameDuplicate(user["USERNAME"]);
		
		if(user["CHINESENAME"]=='' || user["CHINESENAME"]==null){
			errorMessage+='姓名（中文）必填 \n';
		}
		else{
			var regx=/^[\u0391-\uFFE5]+$/;
			if(!user["CHINESENAME"].match(regx)){
				errorMessage+='姓名（中文）格式錯誤，請重新輸入\n'
			}
		}
		
		if(user["DEPARTMENTCODE"]==="null" || user["DEPARTMENTCODE"]=='')
			errorMessage+='部門代碼未填寫\n';
		if(user["COSTID"]==="null" || user["COSTID"]=='')
			errorMessage+='所報加班部門費用代碼未填寫\n';
		
		if(user["ASSISTANT_ID"]==="null" || user["ASSISTANT_ID"]=='')
			errorMessage+='報加班助理ID未選取\n';
		
		if(user["PHONE_TEL"]==="null" || user["PHONE_TEL"]=='')
			errorMessage+='分機未填寫\n';
		
		if(user["ROLE"]==="null" || user["ROLE"]==''){
			errorMessage+='賬號權限未填寫\n';
		}
		else{
			var regx=/^ROLE_/;
			if(!user["ROLE"].match(regx)){
				errorMessage+='賬號權限格式錯誤，請重新輸入\n'
			}
		}
	
	if(errorMessage=='' && isUserNameValid){
			//新增賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../Account/AddAccount.do',
				data:JSON.stringify(user),
				dataType:'json',
				success:function(data){
					$('#submitNewUser').prop("disabled",false);
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 alert(data.Message);			
							 $('#inputUserName').val('');
							 $('#inputChineseName').val('');
							 $('#inputDepID').val('');
							 $('#inputCostID').val(null);
							 $("#inputAssistantId").val('');
							 $('#inputPhoneTel').val('');
							 $('#inputRole').val('');
							 $('#insertAccountDialog').modal('hide');
							 ShowAllAccountList();
						 }
						 else{
							 alert(data.Message);
						 }
					 }else{
						 alert('新增賬號基本資料失敗!');
					 }
				},
				error:function(e){
					alert('新增賬號基本資料發生錯誤');
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
	
	  function checkUserNameDuplicate(userName){
			if(userName!=""){
				$.ajax({
					type:'POST',
					url:'../Account/checkUserName.do',
					data:{
						userName:userName
					},
					async:false,
					error:function(e){
						alert(e);
					},
					success:function(data){	
						 if(data!=null && data!=''){
							 if(data.StatusCode==500){
								 alert(data.Message);
								 isUserNameValid=false;
							 }
							 else
								 isUserNameValid=true;
					}else{
						 isUserNameValid=false;
						}
					}
				});
			}
		}
	  $('#resetSubmit').click(function(){
		    $('#inputUserName').val('');
	    	$('#inputChineseName').val('');
	    	$('#inputDepID').val('');
	    	$('#inputCostID').val('');
	        $('#inputAssistantId').val('');
	    	$('#inputPhoneTel').val('');
	  });
	
	function refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize){
		$('#accountInfoPagination').empty();
		var paginationElement='頁次：'+currentPage+'/'+totalPage +'&nbsp;每页:&nbsp;'+pageSize+'&nbsp;共&nbsp;'+totalRecord+'&nbsp;條&nbsp;';
		if(currentPage==1)
			paginationElement+='<a href="#">首页</a>';		  
		else
			paginationElement+='<a class="firstPage">首页</a>';

		if(currentPage>1)
			paginationElement+='&nbsp;<a class="previousPage">上一頁</a>';
		else
			paginationElement+='&nbsp;<a href="#">上一頁</a>';
		
	  /*  for(var i=1;i <= totalPage;i++){
	    	paginationElement+='&nbsp;<a class="numPage">' + i +'</a>&nbsp;';	    	
	    }*/
		if(currentPage<totalPage)
			paginationElement+='<a class="nextPage">下一頁</a>';
		else
			paginationElement+='<a href="#">下一頁</a>';
		
		$('#accountInfoPagination').append(paginationElement);
		
		$('.firstPage').click(function(){
			curPage=1;
			getAsyncAccountInfo(curPage,queryCritirea,queryParam);				
		});
		
		$('.previousPage').click(function(){
			curPage=currentPage-1;
			getAsyncAccountInfo(curPage,queryCritirea,queryParam);				
		});	
		
		$('.nextPage').click(function(){
			curPage=currentPage+1;
			getAsyncAccountInfo(curPage,queryCritirea,queryParam);				
		});	
		
		$('.numPage').click(function(){
			var curPage=$(this).text();
    		getAsyncAccountInfo(curPage,queryCritirea,queryParam);				
    		});
		
	}
	
	function getAsyncAccountInfo(curPage,queryCritirea,queryParam){
		$.ajax({
			type:'GET',
			url:'../Account/AllAccount.show',
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
							ShowAllAccountTable(rawData);
						else
							alert('找不到資料');
					}
				}
			}
		});
		
	}
	
	
});