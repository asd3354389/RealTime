$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	var reg = new RegExp("^[0-9]{4}$");
	ShowAllClassNoRestInfo();
	ShowClassNo();
	
	
	/*$('#resetSubmit').click(function(){
	    $('#insert_rest_start1').val('');
    	$('#insert_rest_end1').val('');
    	$('#insert_rest_start2').val('');
    	$('#insert_rest_end2').val('');
    	$('#insert_rest_start3').val('');
    	$('#insert_rest_end3').val('');
    	$('#insert_rest_start4').val('');
    	$('#insert_rest_end4').val('');
	});*/
	
	$('#searchClassNoRestInfo').click(function(){
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		if(queryParam==""){
			ShowAllClassNoRestInfo();
		}else{
			getPersonList(curPage,queryCritirea,queryParam)	
		}
	});
	
	$("#classNo").change(function(){
		var Class_No = $("#classNo").find('option:selected').val();
		console.log(Class_No);
		if(Class_No!=""){
			$("#insert_rest_start2").attr("disabled", false); 
			$("#insert_rest_end2").attr("disabled", false); 
			/*$.ajax({
				type:'POST',
				url:'../ClassNoRest/ShowClassNoCotent.show',
				data:{Class_No:Class_No},
				async:false,
				success:function(data){
					if(data.StatusCode==200){	
						var result = eval(data.message);
						console.log(data.message);
						console.log(result);
						console.log(result[0].RestStart1,result[0].RestEnd1,result[0].RestStart2,result[0].RestStart2);
						$('#insert_rest_start1').val(result[0].RestStart1);
						$('#insert_rest_end1').val(result[0].RestEnd1);
						$('#insert_rest_start2').val(result[0].RestStart2);
						$('#insert_rest_end2').val(result[0].RestStart2); 
					}
					else{
						alert(data.message);
					}
				 },error:function(){
					alert('無此班別資料');
				 }
			})*/
		}else{
			/*$("#insert_rest_start1").attr("disabled", true); 
			$("#insert_rest_end1").attr("disabled", true); */
			$("#insert_rest_start2").attr("disabled", true); 
			$("#insert_rest_end2").attr("disabled", true); 
			/*$('#insert_rest_start1').val('');
			$('#insert_rest_end1').val('');*/
			$('#insert_rest_start2').val('');
			$('#insert_rest_end2').val(''); 
		}
	})
	
	$('#setClassNoRestInfo').click(function(){
		/*button_onclick($('#setClassNoRestInfo')[0]);*/
		var ClassNoRest=[],errorMessage='';
		//ClassNoRestInfo.CostId=$('#CostNo').val();
		var Cost=$('#CostNo').val();;
		var CostNo=Cost.split(",");
		
		for(var i=0;i<CostNo.length;i++){
			var ClassNoRestInfo=new Object();
			 checkCostIdDuplicate(CostNo[i])
			 if(!isUserNameValid){
				 errorMessage+="輸入的費用代碼有不存在的費用代碼！";
			 }
			 if(!reg.test(CostNo[i])){
				 errorMessage+='费用代码必须都是4位数字\n';
			 }
			 	ClassNoRestInfo.CostId=CostNo[i];
			 	ClassNoRestInfo.Class_No=$('#classNo').find('option:selected').val();
				ClassNoRestInfo.Rest_Start_S=$('#insert_rest_start2').val();
				ClassNoRestInfo.Rest_End_S=$('#insert_rest_end2').val();
				
				if(ClassNoRestInfo.CostId==="null" || ClassNoRestInfo.CostId=='')
					errorMessage+='費用代碼不能爲空\n';
				if(!reg.test(ClassNoRestInfo.CostId)){
					errorMessage+='費用代碼不符合規格！必須是4位數\n';
				}
				if(ClassNoRestInfo.Class_No==="null" || ClassNoRestInfo.Class_No=='')
					errorMessage+='班別不能爲空\n';
				if(ClassNoRestInfo.Rest_Start_S==="null" || ClassNoRestInfo.Rest_Start_S==''||
						ClassNoRestInfo.Rest_End_S==="null"|| ClassNoRestInfo.Rest_End_S=='')
					errorMessage+='班別加班替換休息時間段必選填寫\n';
				
				ClassNoRest.push(ClassNoRestInfo);
			 
		}
		
		console.log(ClassNoRest);
	/*	ClassNoRestInfo.Class_No=$('#classNo').find('option:selected').val();
		ClassNoRestInfo.Rest_Start_S=$('#insert_rest_start2').val();
		ClassNoRestInfo.Rest_End_S=$('#insert_rest_end2').val();
		console.log(ClassNoRestInfo);
		
		if(ClassNoRestInfo.CostId==="null" || ClassNoRestInfo.CostId=='')
			errorMessage+='費用代碼不能爲空\n';
		if(!reg.test(ClassNoRestInfo.CostId)){
			errorMessage+='費用代碼不符合規格！必須是4位數\n';
		}
		if(ClassNoRestInfo.Class_No==="null" || ClassNoRestInfo.Class_No=='')
			errorMessage+='班別不能爲空\n';
		if(ClassNoRestInfo.Rest_Start_S==="null" || ClassNoRestInfo.Rest_Start_S==''||
				ClassNoRestInfo.Rest_End_S==="null"|| ClassNoRestInfo.Rest_End_S=='')
			errorMessage+='班別加班替換休息時間段必選填寫\n';
		*/
		if(errorMessage==''){
			//新增綁定賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../ClassNoRest/AddClassNoRestInfo.do',
				data:JSON.stringify(ClassNoRest),
				dataType:'json',
				success:function(data){
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 $('#CostNo').val('');
							 $('#classNo').val('');
						     $('#insert_rest_start2').val('');
						     $('#insert_rest_end2').val('');
						     ShowAllClassNoRestInfo();
							 alert(data.Message);
							
							/* alert(data.Message);			
							 $('#inputUserName').val('');
							 $('#inputChineseName').val('');
							 $('#inputDepID').val('');
							 $('#inputCostID').val(null);
							 $("#inputAssistantId").val('');
							 $('#inputPhoneTel').val('');
							 $('#inputRole').val('');
							 $('#insertAccountDialog').modal('hide');
							 ShowAllAccountList();		*/				 
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
	
	
	function ShowAllClassNoRestInfo(){
		$.ajax({
			type:'POST',
			url:'../ClassNoRest/ShowAllClassNoRestInfo',
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
							ShowAllClassNoRestInfoTable(rawData);
						else{
							/*$('.left').css('height','727px');*/
							alert('暫無班別加班休息時間段資料');
						}
					}
				}
			}
		});	
	}
	
	function ShowAllClassNoRestInfoTable(rawData){
		$('#classNoRestInfoTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td>'+executeResult[i]["CostId"]+'</td>'+
					'<td>'+executeResult[i]["Class_No"]+'</td>'+
					/*'<td>'+executeResult[i]["Rest_Start_F"]+'</td>'+
					'<td>'+executeResult[i]["Rest_End_F"]+'</td>'+*/
					'<td>'+executeResult[i]["Rest_Start_S"]+'</td>'+
					'<td>'+executeResult[i]["Rest_End_S"]+'</td>'+
					'<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link"><input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td>';
				tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#classNoRestInfoTable tbody').append(tableContents);
		}
		$("tr").each(function() {
			/*$(this).find('td').eq(1).css("background-color","#eee");
			$(this).find('td').eq(2).css("background-color","#eee");*/
			$(this).find('td').eq(2).css("background-color","#eee");
			$(this).find('td').eq(3).css("background-color","#eee");
		})
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
		
		
		$(".editBtn").click(function(){
			var parentElement = $(this).parent().parent();
			var CostId=$(parentElement).find('td').eq(0).text();
			var Class_No=$(parentElement).find('td').eq(1).text();
			var Rest_Start_S=$(parentElement).find('td').eq(2).text();
			$(parentElement).find('td').eq(2).html('<input id="Rest_Start_S" class="Wdate" type="text" value = "'+Rest_Start_S+'" style="width:60px" onClick="WdatePicker({lang:\'zh-cn\',dateFmt:\'HHmm\'})" autocomplete="off" />');
			var Rest_End_S=$(parentElement).find('td').eq(3).text();
			$(parentElement).find('td').eq(3).html('<input id="Rest_End_S" class="Wdate" type="text" value = "'+Rest_End_S+'" style="width:60px" onClick="WdatePicker({lang:\'zh-cn\',dateFmt:\'HHmm\'})" autocomplete="off" />');
			
			$(parentElement).find('td').eq(4).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var ClassNoRestInfo=new Object(),errorMessage='';
				/*var Direction=$(parentElement).find('.changeStatus option:selected').eq(0).text();*/
				ClassNoRestInfo.CostId=CostId;
				ClassNoRestInfo.Class_No=Class_No;
				/*ClassNoRestInfo.Rest_Start_F=$(parentElement).find('td').eq(2).text();
				ClassNoRestInfo.Rest_End_F=$(parentElement).find('td').eq(3).text();*/
				ClassNoRestInfo.Rest_Start_S=$(parentElement).find('td').eq(2).find('input').val();
				ClassNoRestInfo.Rest_End_S=$(parentElement).find('td').eq(3).find('input').val();	
				console.log(ClassNoRestInfo);

				if(
						ClassNoRestInfo.Rest_Start_S==="null" || ClassNoRestInfo.Rest_Start_S==''||
						ClassNoRestInfo.Rest_End_S==="null"|| ClassNoRestInfo.Rest_End_S=='')
/*					ClassNoRestInfo.Rest_Start_F==="null" || ClassNoRestInfo.Rest_Start_F==''||
						ClassNoRestInfo.Rest_End_F==="null"|| ClassNoRestInfo.Rest_End_F==''||*/
					errorMessage+='班別加班休息時間段必選全部填寫\n';
				
				//console.log(WorkshopNoRestInfo);
				if(errorMessage==''){	
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../ClassNoRest/UpdateClassNoRest.do',
						data:JSON.stringify(ClassNoRestInfo),
						dataType:'json',
						error:function(e){
							alert(e);
							},
						success:function(data){
							  if(data!=null && data!=''){
								  if(data.StatusCode=="200"){
									  alert(data.Message);
									  $(parentElement).find('.editBtn,.deleteBtn').show();
									  $(parentElement).find('td').eq(2).html(ClassNoRestInfo.Rest_Start_S);
									  $(parentElement).find('td').eq(3).html(ClassNoRestInfo.Rest_End_S);
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
				$(parentElement).find('td').eq(2).html(Rest_Start_S);
				$(parentElement).find('td').eq(3).html(Rest_End_S);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})					
		})
		
		$('.deleteBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var CostId=$(parentElement).find('td').eq(0).text();
			var Class_No=$(parentElement).find('td').eq(1).text();
			var results=confirm("確定費用代碼為 "+CostId+" 班別為"+Class_No+"的休息時間段訊息 ?");
			if(results==true){
				$.ajax({
					type:'GET',
					url:'../ClassNoRest/deleteClassNoRest.do',
					data:{
						CostId:CostId,
						Class_No:Class_No	
					},
					error:function(e){
						alert(e);
					},
					success:function(data){
						 if(data!=null && data!=''){
							 if(data.StatusCode=="200"){
								 ShowAllClassNoRestInfo();
								 alert(data.Message);
								 /*
								var parentElement=$(this).parent().parent();
								//刪除，所以將此列從畫面移除
								parentElement.remove();
								  */	 
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
		$('#ClassNoRestInfoListPagination').empty();
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
		
		$('#ClassNoRestInfoListPagination').append(paginationElement);
		
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
			url:'../ClassNoRest/ShowAllClassNoRestInfo',
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
							ShowAllClassNoRestInfoTable(rawData);
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
	
	//顯示所有班別
	function ShowClassNo(){
		$.ajax({
			type:'POST',
			url:'../ClassNoRest/ClassNo.show',
			data:{},
			async:false,
			success:function(data){
				var htmlAppender='';
			 if(data!=null && data!=''){	
				if(data.length>0 && data.StatusCode == null){
					for(var i=0;i<data.length;i++){
						htmlAppender+='<option value="'+data[i]+'">'+data[i]+'班別</option>';
					}
					 $('#classNo').append(htmlAppender);
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
})