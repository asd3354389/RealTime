$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	var reg = new RegExp("^[0-9]{10}$");
	ShowIOWorkShopPWList();
	ShowWorkShop();
	
	
	

     $('#searchIOWorkShopPW').click(function(){
 		var queryCritirea=$('#queryCritirea option:selected').val();
 		var queryParam=$('#queryParam').val();
 		if(queryParam==""){
 			ShowIOWorkShopPWList();
 		}else{
 			getPersonList(curPage,queryCritirea,queryParam)	
 		}
 	});
     
     $('.resetSubmit').click(function(){
 	    $('#inputUserName').val('');
     	$('#dpick1').val('');
     	$('#dpick2').val('');
     	$('#dpick3').val('');
     	$('#dpick4').val('');
 	});
     
     //設置進出車間臨時權限
	$('#setIOWorkShopPW').click(function(){

		var Start =$('#dpick1').val().replace(/\//g,'-');
		var End =$('#dpick2').val().replace(/\//g,'-');

		 var errorMessage='', list=[],WorkShopNoStr;
		
		var Emp_id=$('#inputUserName').val();
		
		var WorkShopNo = $('#workShop').val();
		
		var arr= Emp_id.split(",");

		//WorkShopNo==null||WorkShopNo==""
		if(Emp_id==null || Emp_id==""){
			
			errorMessage+='工號未填寫\n';
		}
		if(Start==null|| Start=="")
				errorMessage+='為選擇生效起始日期\n';
			
			if(End==null || End=="")
				errorMessage+='為選擇生效結束日期\n';
				
		
		for (var i = 0; i < arr.length; i++) {
			if(WorkShopNo==null || WorkShopNo==""){
					
				errorMessage+='未選擇使用的車間\n';
			}else{
					 for(var j=0;j<WorkShopNo.length;j++){
						 
						    var ioWsPw={};
						    ioWsPw["Emp_id"] = arr[i];
				  			ioWsPw["WorkShopNo"]=WorkShopNo[j];
				  			ioWsPw["Start_Date"]= Start;
				  			ioWsPw["End_Date"]= End;
				  			list.push(ioWsPw)
				  			checkEmpidDuplicate(arr[i],ioWsPw["WorkShopNo"]);
				  			
				  			
				}
			}
		}
		
		console.log(list);
		/*if(machine["WorkShop_Desc"]=='' || machine["WorkShop_Desc"]==null){
			errorMessage+='未填寫卡機描述 \n';
		}*/
		
		
		if(errorMessage=='' && isUserNameValid){
			//新增綁定賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../IOWorkShopPower/AddIOWorkShopPW.do',
				data:JSON.stringify(list),
				dataType:'json',
				success:function(data){
					$('#setIOWorkShopPW').prop("disabled",false);
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 $('#inputUserName').val('');
							 $('#workShop').val('');
							 $('#dpick1').val('');
							 $('#dpick2').val('');
							 $('#workShop').selectpicker('val',['noneSelectedText'])
							$("#workShop").selectpicker('refresh');
							 alert(data.Message);
							 ShowIOWorkShopPWList();
												 
						 }
						 else{
							 alert(data.Message);
						 }
					 }else{
						 alert('設置車間臨時進出權限失敗!');
					 }
				},
				error:function(e){
					alert('設置車間臨時進出權限發生錯誤');
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

	
	 //設置廠商和台籍人員臨時權限
	$('#setIOWorkShopPWOther').click(function(){
//		button_onclick($('#setIOWorkShopPW')[0]);
		var Start =$('#dpick1Other').val().replace(/\//g,'-');
		var End =$('#dpick2Other').val().replace(/\//g,'-');
//		console.log(Start,End);+
		var errorMessage='',list=[],WorkShopNoStr;
        var CardId=$('#inputCardId').val();
		
		var WorkShopNo = $('#workShopOther').val();
		
		var arr= CardId.split(",");

		if(CardId==null || CardId==""){
				errorMessage+='卡號未填寫\n';
			}
			
			if(Start==null || Start==""){
				errorMessage+='未選擇生效起始日期\n';
			}
				
			
			if(End==null || End==""){
				errorMessage+='未選擇生效結束日期\n';
			}
				
			
			if ($('#inputRemark').val()==null || $('#inputRemark').val()=="") {
				errorMessage+='備註未填寫\n';
			}
		
			
		//console.log(ioWsPw);
		for (var i = 0; i < arr.length; i++) {
			if(!reg.test(arr[i])){
				errorMessage+='卡號不符合規格！必須是10位數\n';
			}
			if(WorkShopNo==null || WorkShopNo==""){
				errorMessage+='未選擇使用的車間\n';
			}else{
				for(var j=0;j<WorkShopNo.length;j++){
					 
				    var ioWsPw={};
				    ioWsPw["CardId"] = arr[i];
		  			ioWsPw["WorkShopNo"]=WorkShopNo[j];
		  			ioWsPw["Start_Date"]= Start;
		  			ioWsPw["End_Date"]= End;
		  			ioWsPw["Remark"]= $('#inputRemark').val();
		  			list.push(ioWsPw)
		  			//checkEmpidDuplicate(arr[i],ioWsPw["WorkShopNo"]);
		  		
		  		}
			}
			 
		}
		
		
		//checkEmpidDuplicate(ioWsPw["Emp_id"],ioWsPw["WorkShopNo"]);
	
		
		
		if(errorMessage==''){
			//新增綁定賬號
			//alert(ioWsPw["Remark"]);
			console.log(ioWsPw);
			//AddIOWorkShopPWOther
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../IOWorkShopPower/AddIOWorkShopPWOther.do',
				data:JSON.stringify(list),
				dataType:'json',
				success:function(data){
					$('#setIOWorkShopPWOther').prop("disabled",false);
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 $('#inputCardId').val('');
							 $('#dpick1Other').val('');
							 $('#dpick2Other').val('');
							 $('#inputRemark').val('');
							 $('#workShopOther').selectpicker('val',['noneSelectedText'])
							 $("#workShopOther").selectpicker('refresh');
							 alert(data.Message); 
							 ShowIOWorkShopPWList();		 
						 }
						 else{
							 alert(data.Message);
						 }
					 }else{
						 alert('設置車間臨時進出權限失敗!');
					 }
				},
				error:function(e){
					alert('設置車間臨時進出權限發生錯誤');
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
	
	function ShowIOWorkShopPWList(){
		$.ajax({
			type:'POST',
			url:'../IOWorkShopPower/ShowIOWorkShopList',
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
						if(numOfRecords>0)	{
							console.log(rawData);
							ShowAllIOWorkShopPWTable(rawData);
// 						    alert(123);
						}else{
							/*$('.left').css('height','727px');*/
							ShowAllIOWorkShopPWTable(rawData);
							setTimeout(function(){ alert('暫無進出車間臨時權限綁定資料'); }, 100);
						}
					}
				}
			}
		});	
	}
	
	function ShowAllIOWorkShopPWTable(rawData){
		$('#IOWorkShopPWTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		console.log(rawData["list"]);
			for(var i=0;i<executeResult.length;i++){
				
				var	tableContents='<tr><td class="empid">'+executeResult[i]["Emp_id"]+'</td>'+
						'<td>'+executeResult[i]["WorkShopNo"]+'</td>'+
						'<td>'+executeResult[i]["Start_Date"]+'</td>'+
						'<td>'+executeResult[i]["End_Date"]+'</td>'+		
						'<td>'+executeResult[i]["CardId"]+'</td>'+
						'<td>'+executeResult[i]["Remark"]+'</td>';
//						'<td>'++'</td>'+
						var enabled =executeResult[i].Enabled=="Y"?'已生效':'';		
						tableContents+='<td>'+enabled+'</td>'+
						'<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link"><input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td>';
					tableContents+='</tr>';
						/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
						$('#IOWorkShopPWTable tbody').append(tableContents);
		
			}
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
			
	
		$(".editBtn").click(function(){
			var parentElement = $(this).parent().parent();
			var WorkShopNo=$(parentElement).find('td').eq(1).text();
//			console.log(WorkShopNo);
			//編輯車間
			$(parentElement).find('td').eq(1).html('<select class="changeWorkShopNo input-small"></select>');
			
			ShowWorkShopNo('changeWorkShopNo');
			
			$("#workShopUpdate").find('option').each(function(){
				if($(this).val()==WorkShopNo){
					$(this).prop('selected',true);
				}
			});
			//編輯開始時間
			var Start_Date=$(parentElement).find('td').eq(2).text();
			$(parentElement).find('td').eq(2).html("<input id=\"dpick3\" class=\"Wdate\" type=\"text\" name=\"OVERTIMEDATE\" value="+Start_Date+" onfocus=\"WdatePicker({dateFmt:\'yyyy-MM-dd\',minDate:\'%y-\\#{%M-2}-01\',maxDate:\'#F{$dp.$D(\\\'dpick4\\\')}\'})\" autocomplete=\"off\" />");
			//編輯結束時間
			var End_Date=$(parentElement).find('td').eq(3).text();
			$(parentElement).find('td').eq(3).html("<input id=\"dpick4\" class=\"Wdate\" type=\"text\" name=\"OVERTIMEDATEEnd\" value="+End_Date+" onfocus=\"WdatePicker({dateFmt:\'yyyy-MM-dd\',minDate:\'#F{$dp.$D(\\\'dpick3\\\')}\'})\" autocomplete=\"off\" />");
            //編輯備註
			var Remark = $(parentElement).find('td').eq(5).text();
			$(parentElement).find('td').eq(5).html('<input id=\"Remark1\"  type="text" class="changeRemark input-small" maxlength="60" value="'+Remark+'">');
			
			
//			$(parentElement).children().find('.editBtn .deleteBtn').hide();
			$(parentElement).find('td').eq(7).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide(); 
			
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var User=new Object(),errorMessage='';
//				var Direction=$(parentElement).find('.changeStatus option:selected').eq(0).text();
				User.Emp_id=$(parentElement).find('td').eq(0).text();
//				User.Emp_id=Emp_id;
				User.WorkShopNo=$(parentElement).find('td option:selected').eq(0).val();
				User.Start_Date=$("#dpick3").val();
				User.End_Date=$("#dpick4").val();
				User.CardId = $(parentElement).find('td').eq(4).text();
				User.Remark=$("#Remark1").val();
				if(User.WorkShopNo==="null" || User.WorkShopNo=='')
					errorMessage+='車間號未填寫\n';
				if(User.Start_Date==="null" || User.Start_Date=='')
					errorMessage+='生效起始时间未填寫\n';
				if(User.End_Date==="null" || User.End_Date=='')
					errorMessage+='生效截止时间未填寫\n';
				if (User.Emp_id === "null" ||User.Emp_id =='' ) {
					if (User.Remark==="null" || User.Remark=='') {
						errorMessage+='備註未填寫\n';
					}
				}
				
				console.log(User);
			//alert(User);
				if(errorMessage==''){	
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../IOWorkShopPower/UpdateIOWorkShopPW.do',
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
									  $(parentElement).find('td').eq(0).html(User.Emp_id);
									  $(parentElement).find('td').eq(1).html(User.WorkShopNo);
									  $(parentElement).find('td').eq(2).html(User.Start_Date);
									  $(parentElement).find('td').eq(3).html(User.End_Date);
									  $(parentElement).find('td').eq(5).html(User.Remark);
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
				$(parentElement).find('td').eq(1).html(WorkShopNo);
				$(parentElement).find('td').eq(2).html(Start_Date);
				$(parentElement).find('td').eq(3).html(End_Date);
				$(parentElement).find('td').eq(5).html(Remark);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			});
		});
		
			
		
		$('.deleteBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var deleteEmpid=$(parentElement).find('td').eq(0).text();
		    var deleteCardId = $(parentElement).find('td').eq(4).text();
		    //車間號
		    var deleteWorkShopNo = $(parentElement).find('td').eq(1).text();
			console.log(deleteCardId);
			var results=confirm("確定刪除此條數據");
			if(results==true){
				$.ajax({
					type:'GET',
					url:'../IOWorkShopPower/deleteIOWorkShopPW.do',
					data:{Emp_id:deleteEmpid,CardID:deleteCardId,WorkShopNo:deleteWorkShopNo},
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
								 ShowIOWorkShopPWList();
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
					 $('#workShopOther').append(htmlAppender);
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
	
	function checkEmpidDuplicate(Emp_id,WorkshopNo){
		if(Emp_id!=""){
			$.ajax({
				type:'POST',
				url:'../IOWorkShopPower/checkUserName.do',
				data:{
					Emp_id:Emp_id,
					WorkshopNo:WorkshopNo
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
	
	function refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize){
		$('#IOWorkShopPWPagination').empty();
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
		
		$('#IOWorkShopPWPagination').append(paginationElement);
		
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
			url:'../IOWorkShopPower/ShowIOWorkShopList',
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
							ShowAllIOWorkShopPWTable(rawData);
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
});
