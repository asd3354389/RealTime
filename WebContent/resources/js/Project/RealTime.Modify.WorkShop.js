$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isWorkShopNoValid=false,isLineNoValid=false;
	init();
	
	function init(callback){
		ShowAllWorkShopList();
		getWorkShops();
	}
	
	function ShowAllWorkShopList(){
		$.getJSON('../WorkShop/AllWorkShops.show?curPage=1&queryCritirea=&queryParam=',function(rawData){
			var executeResult=rawData["list"];
			if(executeResult.StatusCode!="500"){
				if(executeResult.length>0){
					ShowAllWorkShopTable(rawData);
				}
				else{
					alert("NO WorkShop Data!!");
				}
			}
			else{
				alert(executeResult.ErrorMessage);
			}
		});
	}
	
	function getWorkShops(){
		$.ajax({
			type:'GET',
			url:'../WorkShop/GetWorkShops.do',
			data:{},
			async:false,
			success:function(data){
				var htmlAppender='<option value="">--- 請選擇 ---</option>';
				if (data != null && data != "") {
					if(data.length>0){
						for(var i=0;i<data.length;i++){
							htmlAppender+='<option value="'+data[i]+'">'+data[i]+'</option>';
						}
						$('#selectWorkShopNo').append(htmlAppender);
					}
					else{
						alert('No WorkShops Data');
					}
				}else{
					alert('No WorkShops Data');
				}
			}
		});
	}
	
	function ShowAllWorkShopTable(rawData){
		$('#WorkShopInfoTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td style="display:none">'+executeResult[i]["ID"]+'</td>'+
				    '<td>'+executeResult[i]["WORKSHOPNO"]+'</td>'+
			        '<td>'+executeResult[i]["LINENO"]+'</td>'+
					'<td>'+executeResult[i]["UPDATE_USER"]+'</td>'+
					'<td>'+executeResult[i]["CREATE_DATE"]+'</td>';
					tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">'+
					'<input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td></tr>';
					$('#WorkShopInfoTable tbody').append(tableContents);
		}	
		refreshWorkShopInfoPagination(currentPage,totalRecord,totalPage,pageSize);
		
		$('.editBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var WorkShopNo=$(parentElement).find('td').eq(1).text();
			var LineNo=$(parentElement).find('td').eq(2).text();		
			$(parentElement).find('td').eq(2).html('<input type="text" class="changeLineNo input-small" value="'+LineNo+'">');
			
			$(parentElement).children().find('.editBtn .deleteBtn').hide();
			$(parentElement).find('td').eq(5).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
			
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var WorkShop=new Object(),errorMessage='';
				WorkShop.ID=$(parentElement).find('td').eq(0).text();	
				WorkShop.WORKSHOPNO=WorkShopNo;			
				WorkShop.LINENO=$(parentElement).find('td input:text').eq(0).val();			
				
				if(WorkShop.LINENO=='' || WorkShop.LINENO==null){
					errorMessage+='線體名稱必填 \n';
				}else{
					checkLineNoDuplicate(WorkShop);
				}
				
			
			if(errorMessage=='' && isLineNoValid){
				
				$.ajax({
					type:'POST',
					contentType: "application/json",
					url:'../WorkShop/UpdateWorkShop.do',
					data:JSON.stringify(WorkShop),
					dataType:'json',
					error:function(e){
						alert(e);
					},
					success:function(data){
						if (data != null && data != "") {
							if(data.StatusCode=="200"){
								alert(data.Message);
								$(parentElement).find('.editBtn,.deleteBtn').show();
								$(parentElement).find('td').eq(2).html(WorkShop.LINENO);							
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
				$(parentElement).find('td').eq(2).html(LineNo);				
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})
		});
		
		
		$('.deleteBtn').click(function(){
			var WorkShop=new Object();
			var parentElement=$(this).parent().parent();
			WorkShop.ID=$(parentElement).find('td').eq(0).text();
			WorkShop.WORKSHOPNO=$(parentElement).find('td').eq(1).text();
			WorkShop.LINENO=$(parentElement).find('td').eq(2).text();;
			var results=confirm("確定刪除 "+WorkShop.WORKSHOPNO,+" 的基本資料 ?");
			if(results==true){
				$.ajax({
					type:'POST',
					url:'../WorkShop/DisableWorkShop.do',
					contentType: "application/json",
					data:JSON.stringify(WorkShop),
					error:function(e){
						alert(e);
					},
					success:function(data){
						if (data != null && data != "") {
							if(data.StatusCode=="200"){
								alert(data.Message);
								/*
								var parentElement=$(this).parent().parent();
								//刪除，所以將此列從畫面移除
								parentElement.remove();
								 */
								ShowAllWorkShopList();
							}
							else{
								alert(data.Message);
							}
						}
						else{
							alert('操作失敗!');
							}
					}
				});
			}
		});
	}
	
	$('#searchWorkShopBtn').click(function(){
		curPage=1
		queryCritirea=$('#queryCritirea option:selected').val();
		queryParam=$('#queryParam').val();
		getAsyncWorkShopInfo(curPage,queryCritirea,queryParam)	
	});
	
	$('#submitNewWorkShop').click(function(){
		var WorkShop=new Object(),errorMessage='';
		WorkShop.WORKSHOPNO=$('#inputWorkShopNo').val();	
		
		if(WorkShop.WORKSHOPNO==="null" || WorkShop.WORKSHOPNO=='')
			errorMessage+='車間名稱未填寫\n';
		
		checkWorkShopNoDuplicate(WorkShop.WORKSHOPNO);	
	
	if(errorMessage=='' && isWorkShopNoValid){
			//新增車間
			$.ajax({
				type:'POST',
				url:'../WorkShop/AddWorkShop.do',
				contentType: "application/json",
				data:JSON.stringify(WorkShop),
				dataType:'json',
				success:function(data){
					$('#submitNewUser').prop("disabled",false);
					if (data != null && data != "") {
						if(data.StatusCode=="200"){
							alert(data.Message);			
							$('#inputWorkShopNo').val('');
							$('#insertWorkShopDialog').modal('hide');
							ShowAllWorkShopList();
						}
						else{
							alert(data.Message);
						}
					}else{
						alert('操作失敗!');
					}
				},
				error:function(e){
					alert('新增車間基本資料發生錯誤');
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
	
	  function checkWorkShopNoDuplicate(workShopNo){
			if(workShopNo!=""){
				$.ajax({
					type:'POST',
					url:'../WorkShop/checkWorkShopNo.do',
					data:{
						workShopNo:workShopNo
					},
					async:false,
					error:function(e){
						alert(e);
					},
					success:function(data){	
						if (data != null && data != "") {
							if(data.StatusCode==500){
								alert(data.Message);
								isWorkShopNoValid=false;
							}
							else
								isWorkShopNoValid=true;
						}else{
							isWorkShopNoValid=false;
						}
					}
				});
			}
		}
	  
	  $('#submitNewLineNo').click(function(){
			var WorkShop=new Object(),errorMessage='';
			WorkShop.WORKSHOPNO=$('#selectWorkShopNo option:selected').val();
			WorkShop.LINENO=$('#inputLineNo').val();	
			
			if(WorkShop.WORKSHOPNO==="null" || WorkShop.WORKSHOPNO=='')
				errorMessage+='車間名稱未選\n';
			
			if(WorkShop.LINENO==="null" || WorkShop.LINENO=='')
				errorMessage+='線體名稱未填寫\n';
			
			checkLineNoDuplicate(WorkShop);	
		
		if(errorMessage=='' && isLineNoValid){
				//新增線體
				$.ajax({
					type:'POST',
					contentType: "application/json",
					url:'../WorkShop/AddLineNo.do',
					data:JSON.stringify(WorkShop),
					dataType:'json',
					success:function(data){
						$('#submitNewLineNo').prop("disabled",false);
						if (data != null && data != "") {
							if(data.StatusCode=="200"){
								alert(data.Message);			
								$('#inputLineNo').val('');
								$('#insertLineNoDialog').modal('hide');
								ShowAllWorkShopList();
							}
							else{
								alert(data.Message);
							}
						}else{
							alert('操作失敗!');
						}
					},
					error:function(e){
						alert('新增線體基本資料發生錯誤');
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
		
		  function checkLineNoDuplicate(WorkShop){
				if(WorkShop.WORKSHOPNO!="" && WorkShop.LINENO!=""){
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../WorkShop/checkLineNo.do',
						data:JSON.stringify(WorkShop),
						async:false,
						error:function(e){
							alert(e);
						},
						success:function(data){
							if (data != null && data != "") {
								if(data.StatusCode==500){
									alert(data.Message);
									isLineNoValid=false;
								}
								else
									isLineNoValid=true;
							}else{
								isLineNoValid=false;
							}
						}
					});
				}
			}
	  
	  $('#resetNewWorkShopSubmit').click(function(){
			$('#inputWorkShopNo').val('');
	  });
	  
	  $('#resetNewLineNoSubmit').click(function(){
			$('#inputLineNo').val('');
	  });
	  
	  
	
	function refreshWorkShopInfoPagination(currentPage,totalRecord,totalPage,pageSize){
		$('#WorkShopInfoPagination').empty();
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
		
		$('#WorkShopInfoPagination').append(paginationElement);
		
		$('.firstPage').click(function(){
			curPage=1;
			getAsyncWorkShopInfo(curPage,queryCritirea,queryParam);				
		});
		
		$('.previousPage').click(function(){
			curPage=currentPage-1;
			getAsyncWorkShopInfo(curPage,queryCritirea,queryParam);				
		});	
		
		$('.nextPage').click(function(){
			curPage=currentPage+1;
			getAsyncWorkShopInfo(curPage,queryCritirea,queryParam);				
		});	
		
		$('.numPage').click(function(){
			var curPage=$(this).text();
    		getAsyncWorkShopInfo(curPage,queryCritirea,queryParam);				
    		});
		
	}
	
	function getAsyncWorkShopInfo(curPage,queryCritirea,queryParam){
		$.ajax({
			type:'GET',
			url:'../WorkShop/AllWorkShops.show',
			data:{curPage:curPage,queryCritirea:queryCritirea,queryParam:queryParam},
			error:function(e){
				alert('找不到資料');
			},
			success:function(rawData){	

									if (rawData != null && rawData != "") {
											var executeResult = rawData["list"];
											var errorResponse = executeResult.ErrorMessage;
											if (errorResponse != null) {
												alert('找不到資料');
											} else {
												var numOfRecords = executeResult.length;
												if (numOfRecords > 0)
													ShowAllWorkShopTable(rawData);
												else
													alert('找不到資料');
											}
										}
									}
		});		
	}
		
});