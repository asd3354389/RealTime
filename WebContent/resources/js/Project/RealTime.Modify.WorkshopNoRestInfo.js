$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	ShowAllWorkshopNoRestInfo();
	ShowWorkShop();
	
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
	    $('#insert_rest_start1').val('');
    	$('#insert_rest_end1').val('');
    	$('#insert_rest_start2').val('');
    	$('#insert_rest_end2').val('');
    	$('#insert_rest_start3').val('');
    	$('#insert_rest_end3').val('');
    	$('#insert_rest_start4').val('');
    	$('#insert_rest_end4').val('');
	});
	
	$('#searchWorkshopNoRestInfo').click(function(){
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		if(queryParam==""){
			ShowAllWorkshopNoRestInfo();
		}else{
			getPersonList(curPage,queryCritirea,queryParam)	
		}
	});
	
	$('#setWorkshopNoRestInfo').click(function(){
		button_onclick($('#setWorkshopNoRestInfo')[0]);
		var WorkshopNoRestInfo=new Object(),errorMessage='';
		WorkshopNoRestInfo.workshopno=$('#workShop').val();
		WorkshopNoRestInfo.rest_start1=$('#insert_rest_start1').val();
		WorkshopNoRestInfo.rest_end1=$('#insert_rest_end1').val();
		WorkshopNoRestInfo.rest_start2=$('#insert_rest_start2').val();
		WorkshopNoRestInfo.rest_end2=$('#insert_rest_end2').val();
		WorkshopNoRestInfo.rest_start3=$('#insert_rest_start3').val();
		WorkshopNoRestInfo.rest_end3=$('#insert_rest_end3').val();
		WorkshopNoRestInfo.rest_start4=$('#insert_rest_start4').val();
		WorkshopNoRestInfo.rest_end4=$('#insert_rest_end4').val();
		console.log(WorkshopNoRestInfo);
		
		if(WorkshopNoRestInfo.workshopno==="null" || WorkshopNoRestInfo.workshopno=='')
			errorMessage+='車間號未填寫\n';
		if(WorkshopNoRestInfo.rest_start1==="null" || WorkshopNoRestInfo.rest_start1==''||
			WorkshopNoRestInfo.rest_end1==="null"|| WorkshopNoRestInfo.rest_end1==''||
			WorkshopNoRestInfo.rest_start2==="null" || WorkshopNoRestInfo.rest_start2==''||
			WorkshopNoRestInfo.rest_end2==="null"|| WorkshopNoRestInfo.rest_end2==''||
			WorkshopNoRestInfo.rest_start3==="null" || WorkshopNoRestInfo.rest_start3==''||
			WorkshopNoRestInfo.rest_end3==="null"|| WorkshopNoRestInfo.rest_end3==''||
			WorkshopNoRestInfo.rest_start4==="null" || WorkshopNoRestInfo.rest_start4==''||
			WorkshopNoRestInfo.rest_end4==="null"|| WorkshopNoRestInfo.rest_end4=='')
			errorMessage+='車間休息時間段必選全部填寫，如果無四個休息時間段，可填寫相同的休息時間\n';
		
		if(errorMessage==''){
			//新增綁定賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../WorkShopNoRest/AddWorkShopNoRestInfo.do',
				data:JSON.stringify(WorkshopNoRestInfo),
				dataType:'json',
				success:function(data){
					$('#setIOCardMaIP').prop("disabled",false);
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 $('#insert_rest_start1').val('');
						     $('#insert_rest_end1').val('');
						     $('#insert_rest_start2').val('');
						     $('#insert_rest_end2').val('');
						     $('#insert_rest_start3').val('');
						     $('#insert_rest_end3').val('');
						     $('#insert_rest_start4').val('');
						     $('#insert_rest_end4').val('');
							 alert(data.Message);
							 ShowAllWorkshopNoRestInfo();
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
						 alert('新增車間休息時間段失敗!');
					 }
				},
				error:function(e){
					alert('新增車間休息時間段發生錯誤');
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
	
	function ShowAllWorkshopNoRestInfo(){
		$.ajax({
			type:'POST',
			url:'../WorkShopNoRest/ShowAllWorkShopNoRestInfo',
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
							ShowAllWorkshopNoRestInfoTable(rawData);
						else{
							/*$('.left').css('height','727px');*/
							alert('暫無卡機信息資料');
						}
					}
				}
			}
		});	
	}
	
	function ShowAllWorkshopNoRestInfoTable(rawData){
		$('#WorkshopNoRestInfoTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td>'+executeResult[i]["workshopno"]+'</td>'+
					'<td>'+executeResult[i]["rest_start1"]+'</td>'+
					'<td>'+executeResult[i]["rest_end1"]+'</td>'+
					'<td>'+executeResult[i]["rest_start2"]+'</td>'+
					'<td>'+executeResult[i]["rest_end2"]+'</td>'+
					'<td>'+executeResult[i]["rest_start3"]+'</td>'+
					'<td>'+executeResult[i]["rest_end3"]+'</td>'+
					'<td>'+executeResult[i]["rest_start4"]+'</td>'+
					'<td>'+executeResult[i]["rest_end4"]+'</td>'+
//					'<td>'+executeResult[i]["Direction"]+'</td>'
//					'<td>'++'</td>'+
					'<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link"><input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td>';
				tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#WorkshopNoRestInfoTable tbody').append(tableContents);
		}
		$("tr").each(function() {
			$(this).find('td').eq(1).css("background-color","#eee");
			$(this).find('td').eq(2).css("background-color","#eee");
			$(this).find('td').eq(5).css("background-color","#eee");
			$(this).find('td').eq(6).css("background-color","#eee");
		});
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
	/*	console.log(currentPage);
		console.log(totalRecord);
		console.log(totalPage);
		console.log(pageSize);*/
		/*goPageY(currentPage,totalRecord,totalPage,pageSize);*/
		
		$(".editBtn").click(function(){
			var parentElement = $(this).parent().parent();
			var WorkShopNo=$(parentElement).find('td').eq(0).text();
			
			var rest_start1=$(parentElement).find('td').eq(1).text();
			$(parentElement).find('td').eq(1).html('<input id="rest_start1" class="Wdate" type="text" value = "'+rest_start1+'" style="width:60px" onClick="WdatePicker({lang:\'zh-cn\',dateFmt:\'HHmm\'})" autocomplete="off" />');
			var rest_end1=$(parentElement).find('td').eq(2).text();
			$(parentElement).find('td').eq(2).html('<input id="rest_end1" class="Wdate" type="text" value = "'+rest_end1+'" style="width:60px" onClick="WdatePicker({lang:\'zh-cn\',dateFmt:\'HHmm\'})" autocomplete="off" />');
			
			var rest_start2=$(parentElement).find('td').eq(3).text();
			$(parentElement).find('td').eq(3).html('<input id="rest_start2" class="Wdate" type="text" value = "'+rest_start2+'" style="width:60px" onClick="WdatePicker({lang:\'zh-cn\',dateFmt:\'HHmm\'})" autocomplete="off" />');
			var rest_end2=$(parentElement).find('td').eq(4).text();
			$(parentElement).find('td').eq(4).html('<input id="rest_end2" class="Wdate" type="text" value = "'+rest_end2+'" style="width:60px" onClick="WdatePicker({lang:\'zh-cn\',dateFmt:\'HHmm\'})" autocomplete="off" />');

			var rest_start3=$(parentElement).find('td').eq(5).text();
			$(parentElement).find('td').eq(5).html('<input id="rest_start3" class="Wdate" type="text" value = "'+rest_start3+'" style="width:60px" onClick="WdatePicker({lang:\'zh-cn\',dateFmt:\'HHmm\'})" autocomplete="off" />');
			var rest_end3=$(parentElement).find('td').eq(6).text();
			$(parentElement).find('td').eq(6).html('<input id="rest_end3" class="Wdate" type="text" value = "'+rest_end3+'" style="width:60px" onClick="WdatePicker({lang:\'zh-cn\',dateFmt:\'HHmm\'})" autocomplete="off" />');
			
			var rest_start4=$(parentElement).find('td').eq(7).text();
			$(parentElement).find('td').eq(7).html('<input id="rest_start4" class="Wdate" type="text" value = "'+rest_start4+'" style="width:60px" onClick="WdatePicker({lang:\'zh-cn\',dateFmt:\'HHmm\'})" autocomplete="off" />');
			var rest_end4=$(parentElement).find('td').eq(8).text();
			$(parentElement).find('td').eq(8).html('<input id="rest_end4" class="Wdate" type="text" value = "'+rest_end4+'" style="width:60px" onClick="WdatePicker({lang:\'zh-cn\',dateFmt:\'HHmm\'})" autocomplete="off" />');

//			$(parentElement).children().find('.editBtn .deleteBtn').hide();
			$(parentElement).find('td').eq(9).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var WorkshopNoRestInfo=new Object(),errorMessage='';
				var Direction=$(parentElement).find('.changeStatus option:selected').eq(0).text();
				WorkshopNoRestInfo.workshopno=WorkShopNo;
				WorkshopNoRestInfo.rest_start1=$(parentElement).find('td').eq(1).find('input').val();
				WorkshopNoRestInfo.rest_end1=$(parentElement).find('td').eq(2).find('input').val();
				WorkshopNoRestInfo.rest_start2=$(parentElement).find('td').eq(3).find('input').val();
				WorkshopNoRestInfo.rest_end2=$(parentElement).find('td').eq(4).find('input').val();
				WorkshopNoRestInfo.rest_start3=$(parentElement).find('td').eq(5).find('input').val();
				WorkshopNoRestInfo.rest_end3=$(parentElement).find('td').eq(6).find('input').val();
				WorkshopNoRestInfo.rest_start4=$(parentElement).find('td').eq(7).find('input').val();
				WorkshopNoRestInfo.rest_end4=$(parentElement).find('td').eq(8).find('input').val();
				
				if(WorkshopNoRestInfo.workshopno==="null" || WorkshopNoRestInfo.workshopno=='')
					errorMessage+='車間號未填寫\n';
				if(WorkshopNoRestInfo.rest_start1==="null" || WorkshopNoRestInfo.rest_start1==''||
					WorkshopNoRestInfo.rest_end1==="null"|| WorkshopNoRestInfo.rest_end1==''||
					WorkshopNoRestInfo.rest_start2==="null" || WorkshopNoRestInfo.rest_start2==''||
					WorkshopNoRestInfo.rest_end2==="null"|| WorkshopNoRestInfo.rest_end2==''||
					WorkshopNoRestInfo.rest_start3==="null" || WorkshopNoRestInfo.rest_start3==''||
					WorkshopNoRestInfo.rest_end3==="null"|| WorkshopNoRestInfo.rest_end3==''||
					WorkshopNoRestInfo.rest_start4==="null" || WorkshopNoRestInfo.rest_start4==''||
					WorkshopNoRestInfo.rest_end4==="null"|| WorkshopNoRestInfo.rest_end4=='')
					errorMessage+='車間休息時間段必選全部填寫，如果無四個休息時間段，可填寫相同的休息時間\n';
				
				console.log(WorkshopNoRestInfo);
				if(errorMessage==''){	
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../WorkShopNoRest/UpdateWorkShopNoRest.do',
						data:JSON.stringify(WorkshopNoRestInfo),
						dataType:'json',
						error:function(e){
							alert(e);
							},
						success:function(data){
							  if(data!=null && data!=''){
								  if(data.StatusCode=="200"){
									  alert(data.Message);
									  $(parentElement).find('.editBtn,.deleteBtn').show();
									  $(parentElement).find('td').eq(1).html(WorkshopNoRestInfo.rest_start1);
									  $(parentElement).find('td').eq(2).html(WorkshopNoRestInfo.rest_end1);
									  $(parentElement).find('td').eq(3).html(WorkshopNoRestInfo.rest_start2);
									  $(parentElement).find('td').eq(4).html(WorkshopNoRestInfo.rest_end2);
									  $(parentElement).find('td').eq(5).html(WorkshopNoRestInfo.rest_start3);
									  $(parentElement).find('td').eq(6).html(WorkshopNoRestInfo.rest_end3);
									  $(parentElement).find('td').eq(7).html(WorkshopNoRestInfo.rest_start4);
									  $(parentElement).find('td').eq(8).html(WorkshopNoRestInfo.rest_end4);
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
				$(parentElement).find('td').eq(1).html(rest_start1);
				$(parentElement).find('td').eq(2).html(rest_end1);
				$(parentElement).find('td').eq(3).html(rest_start2);
				$(parentElement).find('td').eq(4).html(rest_end2);
				$(parentElement).find('td').eq(5).html(rest_start3);
				$(parentElement).find('td').eq(6).html(rest_end3);
				$(parentElement).find('td').eq(7).html(rest_start4);
				$(parentElement).find('td').eq(8).html(rest_end4);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})					
		})
		
		$('.deleteBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var workshopNo=$(parentElement).find('td').eq(0).text();
			var results=confirm("確定刪除車間為 "+workshopNo+" 的訊息 ?");
			if(results==true){
				$.ajax({
					type:'GET',
					url:'../WorkShopNoRest/deleteWorkShopNoRest.do',
					data:{workshopNo:workshopNo},
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
								 ShowAllWorkshopNoRestInfo();
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
							ShowAllWorkshopNoRestInfoTable(rawData);
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
	
	 function checkDeviceipDuplicate(Deviceip){
			if(Deviceip!=""){
				$.ajax({
					type:'POST',
					url:'../IOCardBdIP/checkDeviceip.do',
					data:{
						Deviceip:Deviceip
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
								{
								 isUserNameValid=true;
								}
					}else{
						 isUserNameValid=false;
						}
					}
				});
			}
		}
})