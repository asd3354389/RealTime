$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	ShowAllIOSpecialWSEmp();
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
 	    $('#inputUserName').val('');
     	$('#dpick1').val('');
     	$('#dpick2').val('');
     	$('#dpick3').val('');
     	$('#dpick4').val('');
	});
	
	$('#searchIOSpecialWSEmp').click(function(){
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		if(queryParam==""){
			ShowAllIOSpecialWSEmp();
		}else{
			getPersonList(curPage,queryCritirea,queryParam)	
		}
	});
	
	$('#setIOWorkShopPW').click(function(){
//		button_onclick($('#setIOWorkShopPW')[0]);
		var Start =$('#dpick1').val().replace(/\//g,'-');
		var End =$('#dpick2').val().replace(/\//g,'-');
//		console.log(Start,End);+
		var ioWsPw={},errorMessage='';
		ioWsPw["Emp_id"]=$('#inputUserName').val();
		ioWsPw["WorkShopNo"]=$('#workShop option:selected').val();
		ioWsPw["Start_Date"]= Start;
		ioWsPw["End_Date"]= End;
		console.log(ioWsPw);
		
		if(ioWsPw["Emp_id"]==="null" || ioWsPw["Emp_id"]=='')
			errorMessage+='工號未填寫\n';
		
		/*checkEmpidDuplicate(ioWsPw["Emp_id"],ioWsPw["WorkShopNo"]);*/
		
		/*if(machine["WorkShop_Desc"]=='' || machine["WorkShop_Desc"]==null){
			errorMessage+='未填寫卡機描述 \n';
		}*/
		if(ioWsPw["WorkShopNo"]==="null" || ioWsPw["WorkShopNo"]=='')
			errorMessage+='未選擇使用的車間\n';
		
		if(ioWsPw["Start_Date"]==="null" || ioWsPw["Start_Date"]=='')
			errorMessage+='為選擇生效起始日期\n';
		
		if(ioWsPw["End_Date"]==="null" || ioWsPw["End_Date"]=='')
			errorMessage+='為選擇生效結束日期\n';
		
		if(errorMessage==''){
			//新增綁定賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../IOSpecialWSEmp/AddIOSpecialWSEmp.do',
				data:JSON.stringify(ioWsPw),
				dataType:'json',
				success:function(data){
					$('#setIOWorkShopPW').prop("disabled",false);
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 $('#inputUserName').val('');
							 $('#workShop').val('');
							 $('#dpick1').val('');
							 $('#dpick2').val('');
							 alert(data.Message);
							 ShowAllIOSpecialWSEmp();
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
						 alert('設置保密車間臨時進出權限失敗!');
					 }
				},
				error:function(e){
					alert('設置保密車間臨時進出權限發生錯誤');
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
	
	function checkEmpidDuplicate(Emp_id,WorkShopNo){
		if(Emp_id!=""){
			$.ajax({
				type:'POST',
				url:'../IOSpecialWSEmp/checkUserName.do',
				data:{
					Emp_id:Emp_id,
					WorkShopNo:WorkShopNo
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
	
	function ShowAllIOSpecialWSEmp(){
		$.ajax({
			type:'POST',
			url:'../IOSpecialWSEmp/ShowAllIOSpecialWSEmp',
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
							ShowAllIOSpecialWSEmpTable(rawData);
						else{
							/*$('.left').css('height','727px');*/
							alert('暫無保密車間資料');
						}
					}
				}
			}
		});	
	}
	
	function ShowAllIOSpecialWSEmpTable(rawData){
		$('#IOSpecialWSEmpTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td>'+executeResult[i]["Emp_id"]+'</td>'+
					'<td>'+executeResult[i]["WorkShopNo"]+'</td>'+
					'<td>'+executeResult[i]["Start_Date"]+'</td>'+
					'<td>'+executeResult[i]["End_Date"]+'</td>'+
//					'<td>'+executeResult[i]["Direction"]+'</td>'
//					'<td>'++'</td>'+
					'<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link"><input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td>';
				tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#IOSpecialWSEmpTable tbody').append(tableContents);
		}
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
	/*	console.log(currentPage);
		console.log(totalRecord);
		console.log(totalPage);
		console.log(pageSize);*/
		/*goPageY(currentPage,totalRecord,totalPage,pageSize);*/
		
		$(".editBtn").click(function(){
			var parentElement = $(this).parent().parent();
			var Emp_id=$(parentElement).find('td').eq(0).text();
			
			var WorkShopNo=$(parentElement).find('td').eq(1).text();
			/*$(parentElement).find('td').eq(1).html('<select class="changeWorkShopNo input-small"></select>');
			ShowWorkShopNo('changeWorkShopNo');
			$('.changeWorkShopNo').val(WorkShopNo);*/
			var Start_Date=$(parentElement).find('td').eq(2).text();
			$(parentElement).find('td').eq(2).html("<input id=\"dpick3\" class=\"Wdate\" type=\"text\" name=\"OVERTIMEDATE\" value="+Start_Date+" onfocus=\"WdatePicker({dateFmt:\'yyyy-MM-dd\',minDate:\'%y-\\#{%M-2}-01\',maxDate:\'#F{$dp.$D(\\\'dpick4\\\')}\'})\" autocomplete=\"off\" />");
			
			var End_Date=$(parentElement).find('td').eq(3).text();
			$(parentElement).find('td').eq(3).html("<input id=\"dpick4\" class=\"Wdate\" type=\"text\" name=\"OVERTIMEDATEEnd\" value="+End_Date+" onfocus=\"WdatePicker({dateFmt:\'yyyy-MM-dd\',minDate:\'#F{$dp.$D(\\\'dpick3\\\')}\'})\" autocomplete=\"off\" />");

//			$(parentElement).children().find('.editBtn .deleteBtn').hide();
			$(parentElement).find('td').eq(4).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var IOWorkShopPW=new Object(),errorMessage='';
				var Direction=$(parentElement).find('.changeStatus option:selected').eq(0).text();
				IOWorkShopPW.Emp_id=Emp_id;
				IOWorkShopPW.WorkShopNo=WorkShopNo;
				IOWorkShopPW.Start_Date=$(parentElement).find('td').eq(2).find('input').val();
				IOWorkShopPW.End_Date=$(parentElement).find('td').eq(3).find('input').val();
				

				if(IOWorkShopPW.Emp_id==="null" || IOWorkShopPW.Emp_id=='')
					errorMessage+='工號未填寫\n';
				if(IOWorkShopPW.WorkShopNo==="null" || IOWorkShopPW.WorkShopNo=='')
					errorMessage+='車間名稱未填寫\n';
				if(IOWorkShopPW.Start_Date==="null" || IOWorkShopPW.Start_Date=='')
					errorMessage+='生效起始日期未填寫\n';
				if(IOWorkShopPW.End_Date==="null" || IOWorkShopPW.End_Date=='')
					errorMessage+='生效結束日期未填寫\n';
				
				if(errorMessage==''){	
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../IOSpecialWSEmp/UpdateIOWorkShopPW.do',
						data:JSON.stringify(IOWorkShopPW),
						dataType:'json',
						error:function(e){
							alert(e);
							},
						success:function(data){
							  if(data!=null && data!=''){
								  if(data.StatusCode=="200"){
									  alert(data.Message);
									  $(parentElement).find('.editBtn,.deleteBtn').show();
									  $(parentElement).find('td').eq(2).html(IOWorkShopPW.Start_Date);
									  $(parentElement).find('td').eq(3).html(IOWorkShopPW.End_Date);
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
				$(parentElement).find('td').eq(2).html(Start_Date);
				$(parentElement).find('td').eq(3).html(End_Date);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})					
		})
		
		$('.deleteBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var Emp_id=$(parentElement).find('td').eq(0).text();
			var WorkShopNo=$(parentElement).find('td').eq(1).text();
			var results=confirm("確定刪除工號 "+Emp_id+"與"+WorkShopNo+"的綁定訊息 ?");
			if(results==true){
				$.ajax({
					type:'GET',
					url:'../IOSpecialWSEmp/deleteIOSpecialWSEmp.do',
					data:{Emp_id:Emp_id,WorkShopNo:WorkShopNo},
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
								 ShowAllIOSpecialWSEmp();
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
		$('#IOSpecialWSEmpListPagination').empty();
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
		
		$('#IOSpecialWSEmpListPagination').append(paginationElement);
		
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
			url:'../IOSpecialWSEmp/ShowAllIOSpecialWSEmp',
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
							ShowAllIOSpecialWSEmpTable(rawData);
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
	
})