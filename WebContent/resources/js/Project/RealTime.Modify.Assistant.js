$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isAssistantIdValid=false,isAssistantEmailValid=false;
	init();
	
	function init(callback){
		ShowAllAssistantList();
	}
	
	function ShowAllAssistantList(){
		$.getJSON('../Assistant/AllAssistants.show?curPage=1&queryCritirea=&queryParam=',function(rawData){
			 if(rawData!=null && rawData!=''){
				 var executeResult=rawData["list"];
				 if(executeResult.StatusCode!="500"){
					 if(executeResult.length>0){
						 ShowAllAssistantTable(rawData);
					 }
					 else{
						 alert("NO Assistant Data!!");
					 }
				 }
				 else{
					 alert(executeResult.ErrorMessage);
				 }
			 }else{
				 alert("NO Assistant Data!!");
			 }
		});
	}
	
	function ShowAllAssistantTable(rawData){
		$('#assistantInfoTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td>'+executeResult[i]["ASSISTANTID"]+'</td>'+
					'<td>'+executeResult[i]["ASSISTANTNAME"]+'</td>'+
					'<td>'+executeResult[i]["ASSISTANTCOSTID"]+'</td>'+
					'<td>'+executeResult[i]["ASSISTANTDEP"]+'</td>'+
					'<td>'+executeResult[i]["ASSISTANTEMAIL"]+'</td>'+
					'<td>'+executeResult[i]["ASSISTANTTEL"]+'</td>';
					tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">'+
					'<input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td></tr>';
					$('#assistantInfoTable tbody').append(tableContents);
		}	
		refreshAssistantInfoPagination(currentPage,totalRecord,totalPage,pageSize);
		
		$('.editBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var assistantId=$(parentElement).find('td').eq(0).text();
			var assistantName=$(parentElement).find('td').eq(1).text();
			$(parentElement).find('td').eq(1).html('<input type="text" class="changeAssistantName input-small" value="'+assistantName+'">');
			
			var assistantCostId=$(parentElement).find('td').eq(2).text();
			$(parentElement).find('td').eq(2).html('<input type="text" class="changeAssistantCostId input-small" value="'+assistantCostId+'">');
			
			var assistantDepId=$(parentElement).find('td').eq(3).text();
			$(parentElement).find('td').eq(3).html('<input type="text" class="changeAssistantDepId input-small" value="'+assistantDepId+'">');
			
			var assistantEmail=$(parentElement).find('td').eq(4).text();
			$(parentElement).find('td').eq(4).html('<input type="text" class="changeAssistantEmail input-small" value="'+assistantEmail+'">');
			
			var assistantTel=$(parentElement).find('td').eq(5).text();
			$(parentElement).find('td').eq(5).html('<input type="text" class="changeAssistantTel input-small" value="'+assistantTel+'">');
			
			$(parentElement).children().find('.editBtn .deleteBtn').hide();
			$(parentElement).find('td').eq(6).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
			
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var Assistant=new Object(),errorMessage='';
				Assistant.ASSISTANTID=$(parentElement).find('td').eq(0).text();
				Assistant.ASSISTANTNAME=$(parentElement).find('td input:text').eq(0).val();
				Assistant.ASSISTANTCOSTID=$(parentElement).find('td input:text').eq(1).val();
				Assistant.ASSISTANTDEP=$(parentElement).find('td input:text').eq(2).val();
				Assistant.ASSISTANTEMAIL=$(parentElement).find('td input:text').eq(3).val();
				Assistant.ASSISTANTTEL=$(parentElement).find('td input:text').eq(4).val();				
				
				if(Assistant.ASSISTANTNAME=='' || Assistant.ASSISTANTNAME==null){
					errorMessage+='姓名（中文）必填 \n';
				}
				else{
					var regx=/^[\u0391-\uFFE5]+$/;
					if(!Assistant.ASSISTANTNAME.match(regx)){
						errorMessage+='姓名（中文）格式錯誤，請重新輸入\n'
					}
				}
				
				if(Assistant.ASSISTANTCOSTID==="null" || Assistant.ASSISTANTCOSTID=='')
					errorMessage+='費用代碼未填寫\n';
				
				if(Assistant.ASSISTANTDEP==="null" || Assistant.ASSISTANTDEP=='')
					errorMessage+='部門代碼未填寫\n';
				
				if(Assistant.ASSISTANTTEL==="null" || Assistant.ASSISTANTTEL=='')
					errorMessage+='分機未填寫未填寫\n';
				
				if(Assistant.ASSISTANTEMAIL==="null" || Assistant.ASSISTANTEMAIL==''){			
					errorMessage+='郵箱未填寫\n';
				}
				else{
					var regx = /^([a-zA-Z_-\s])+@([a-zA-Z_-])+$/;
					if(!Assistant.ASSISTANTEMAIL.match(regx)){
						errorMessage+='郵箱格式錯誤，請重新輸入\n'
					}
				}	
				if(!(Assistant.ASSISTANTEMAIL.toLowerCase()==assistantEmail.toLowerCase())){
					checkAssistantEmailDuplicate(Assistant.ASSISTANTEMAIL);
				}
				else{
					isAssistantEmailValid=true;
				}
				
			
			if(errorMessage=='' && isAssistantEmailValid){
				
				$.ajax({
					type:'POST',
					contentType: "application/json",
					url:'../Assistant/UpdateAssistant.do',
					data:JSON.stringify(Assistant),
					dataType:'json',
					error:function(e){
						alert(e);
					},
					success:function(data){
						 if(data!=null && data!=''){
							 if(data.StatusCode=="200"){
								 alert(data.Message);
								 $(parentElement).find('.editBtn,.deleteBtn').show();
								 $(parentElement).find('td').eq(1).html(Assistant.ASSISTANTNAME);
								 $(parentElement).find('td').eq(2).html(Assistant.ASSISTANTCOSTID);
								 $(parentElement).find('td').eq(3).html(Assistant.ASSISTANTDEP);
								 $(parentElement).find('td').eq(4).html(Assistant.ASSISTANTEMAIL);
								 $(parentElement).find('td').eq(5).html(Assistant.ASSISTANTTEL);
								 $(parentElement).find('.confirmBtn,.cancelBtn').remove();
							 }
							 else{
								 alert(data.Message);
							 }
						 }else{
							 alert('操作失敗!');
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
				$(parentElement).find('td').eq(1).html(assistantId);
				$(parentElement).find('td').eq(2).html(assistantName);
				$(parentElement).find('td').eq(3).html(assistantCostId);
				$(parentElement).find('td').eq(4).html(assistantEmail);
				$(parentElement).find('td').eq(5).html(assistantTel);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})
		});
		
		
		$('.deleteBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var deleteAssistantId=$(parentElement).find('td').eq(0).text();
			var results=confirm("確定刪除 "+deleteAssistantId+" 的基本資料 ?");
			if(results==true){
				$.ajax({
					type:'GET',
					url:'../Assistant/DisableAssistant.do',
					data:{assistantId:deleteAssistantId},
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
								 ShowAllAssistantList();
							 }
							 else{
								 alert(data.Message);
							 }
						 }else{
							 alert('操作失敗！');
						 }
					}
				});
			}
		});
	}
	
	$('#searchAssistantBtn').click(function(){
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		getAsyncAssistantInfo(curPage,queryCritirea,queryParam)	
	});
	
	$('#submitNewAssistant').click(function(){
		var Assistant=new Object(),errorMessage='';
		Assistant.ASSISTANTID=$('#inputAssistantId').val();
		Assistant.ASSISTANTNAME=$('#inputAssistantName').val();
		Assistant.ASSISTANTCOSTID=$('#inputCostID').val();
		Assistant.ASSISTANTDEP=$('#inputDepID').val();
		Assistant.ASSISTANTEMAIL=$('#inputEmail').val();
		Assistant.ASSISTANTTEL=$('#inputPhoneTel').val();			
		
		if(Assistant.ASSISTANTID==="null" || Assistant.ASSISTANTID=='')
			errorMessage+='工號未填寫\n';
		
		checkAssistantIdDuplicate(Assistant.ASSISTANTID);
		
		if(Assistant.ASSISTANTNAME=='' || Assistant.ASSISTANTNAME==null){
			errorMessage+='姓名（中文）必填 \n';
		}
		else{
			var regx=/^[\u0391-\uFFE5]+$/;
			if(!Assistant.ASSISTANTNAME.match(regx)){
				errorMessage+='姓名（中文）格式錯誤，請重新輸入\n'
			}
		}
		
		if(Assistant.ASSISTANTCOSTID==="null" || Assistant.ASSISTANTCOSTID=='')
			errorMessage+='費用代碼未填寫\n';
		
		if(Assistant.ASSISTANTDEP==="null" || Assistant.ASSISTANTDEP=='')
			errorMessage+='部門代碼未填寫\n';
		
		if(Assistant.ASSISTANTTEL==="null" || Assistant.ASSISTANTTEL=='')
			errorMessage+='分機未填寫未填寫\n';
		
		if(Assistant.ASSISTANTEMAIL==="null" || Assistant.ASSISTANTEMAIL==''){			
			errorMessage+='郵箱未填寫\n';
		}
		else{
			regx = /^([a-zA-Z0-9_-\s])+@([a-zA-Z_-])+$/;
			if(!Assistant.ASSISTANTEMAIL.match(regx)){
				errorMessage+='郵箱格式錯誤，請重新輸入\n'
			}
		}	
		checkAssistantEmailDuplicate(Assistant.ASSISTANTEMAIL);
	
	if(errorMessage=='' && isAssistantIdValid && isAssistantEmailValid){
			//新增賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../Assistant/AddAssistant.do',
				data:JSON.stringify(Assistant),
				dataType:'json',
				success:function(data){
					$('#submitNewUser').prop("disabled",false);
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 alert(data.Message);			
							 $('#inputAssistantId').val('');
							 $('#inputAssistantName').val('');
							 $('#inputCostID').val('');
							 $('#inputDepID').val('');
							 $("#inputEmail ").val('');
							 $('#inputPhoneTel').val('');
							 $('#insertAssistantDialog').modal('hide');
							 ShowAllAssistantList();
						 }
						 else{
							 alert(data.Message);
						 }
					 }else{
						 alert('操作失敗!');
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
	
	  function checkAssistantIdDuplicate(assistantId){
			if(assistantId!=""){
				$.ajax({
					type:'POST',
					url:'../Assistant/checkAssistantId.do',
					data:{
						assistantId:assistantId
					},
					async:false,
					error:function(e){
						alert(e);
					},
					success:function(data){	
						 if(data!=null && data!=''){
							 if(data.StatusCode==500){
								 alert(data.Message);
								 isAssistantIdValid=false;
							 }
							 else
								 isAssistantIdValid=true;
						 }
						 else{
							 isAssistantIdValid=false;
							 }
						 }
				});
			}
		}
	  
	  function checkAssistantEmailDuplicate(assistantEmail){
			if(assistantEmail!=""){
				$.ajax({
					type:'POST',
					url:'../Assistant/checkAssistantEmail.do',
					data:{
						assistantEmail:assistantEmail
					},
					async:false,
					error:function(e){
						alert(e);
					},
					success:function(data){	
						 if(data!=null && data!=''){
							 if(data.StatusCode==500){
								 alert(data.Message);
								 isAssistantEmailValid=false;
							 }
							 else
								 isAssistantEmailValid=true;
						 }else{
							 isAssistantEmailValid=false;
						 }
					}
				});
			}
		}
	  
	  $('#resetSubmit').click(function(){
			$('#inputAssistantId').val('');
	    	$('#inputAssistantName').val('');
	    	$('#inputCostID').val('');
	    	$('#inputDepID').val('');
	        $("#inputEmail ").val('');
	    	$('#inputPhoneTel').val('');
	  });
	
	function refreshAssistantInfoPagination(currentPage,totalRecord,totalPage,pageSize){
		$('#assistantInfoPagination').empty();
		var paginationElement='頁次：'+currentPage+'/'+totalPage +'&nbsp;每页:&nbsp;'+pageSize+'&nbsp;共&nbsp;'+totalRecord+'&nbsp;條&nbsp;';
		if(currentPage==1)
			paginationElement+='<a href="#">首页</a>';		  
		else
			paginationElement+='<a class="firstPage">首页</a>';

		if(currentPage>1)
			paginationElement+='&nbsp;<a class="previousPage">上一頁</a>';
		else
			paginationElement+='&nbsp;<a href="#">上一頁</a>';
		
	   /* for(var i=1;i <= totalPage;i++){
	    	paginationElement+='&nbsp;<a class="numPage">' + i +'</a>&nbsp;';	    	
	    }*/
		if(currentPage<totalPage)
			paginationElement+='<a class="nextPage">下一頁</a>';
		else
			paginationElement+='<a href="#">下一頁</a>';
		
		$('#assistantInfoPagination').append(paginationElement);
		
		$('.firstPage').click(function(){
			curPage=1;
			getAsyncAssistantInfo(curPage,queryCritirea,queryParam);				
		});
		
		$('.previousPage').click(function(){
			curPage=currentPage-1;
			getAsyncAssistantInfo(curPage,queryCritirea,queryParam);				
		});	
		
		$('.nextPage').click(function(){
			curPage=currentPage+1;
			getAsyncAssistantInfo(curPage,queryCritirea,queryParam);				
		});	
		
		$('.numPage').click(function(){
			var curPage=$(this).text();
    		getAsyncAssistantInfo(curPage,queryCritirea,queryParam);				
    		});
		
	}
	
	function getAsyncAssistantInfo(curPage,queryCritirea,queryParam){
		$.ajax({
			type:'GET',
			url:'../Assistant/AllAssistants.show',
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
							ShowAllAssistantTable(rawData);
						else
							alert('找不到資料');
					}
				}
			}
		});		
	}
		
}); 