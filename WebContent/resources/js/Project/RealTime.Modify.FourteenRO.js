$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false,isCardIdValid=false,empmessage='';
	var reg = new RegExp("^[0-9]{10}$");
	ShowAllFourteenRO();
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
     	 $('#workShop').selectpicker('val',['noneSelectedText']);
		 $("#workShop").selectpicker('refresh');
	});
	//resetSubmitOther
	$('#resetSubmitOther').click(function(){
 	    $('#inputCardId').val('');
     	$('#dpick1Other').val('');
     	$('#dpick2Other').val('');
     	$('#dpick3').val('');
     	$('#dpick4').val('');
     	$('#inputRemark').val('');
     	 $('#workShopOther').selectpicker('val',['noneSelectedText']);
		 $("#workShopOther").selectpicker('refresh');
     
	});
	$('#searchFourteenRO').click(function(){
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		if(queryParam==""){
			ShowAllFourteenRO();
		}else{
			getPersonList(curPage,queryCritirea,queryParam)	
		}
	});
	
	//設置員工保密車間權限
	$('#setFourteenRO').click(function(){
		
		 $("#setFourteenRO").attr("disabled", "disabled");
		 setTimeout(function(){ $("#setFourteenRO").attr("disabled",false); }, 100);
//		button_onclick($('#setFourteenRO')[0]);
		var Start =$('#dpick1').val().replace(/\//g,'-');
		var End =$('#dpick2').val().replace(/\//g,'-');
//		console.log(Start,End);+
		var errorMessage='',list=[],WorkShopNoStr;

		var Costid=$('#inputCostid').val();
		
		var arr= Costid.split(",");
		
		if(Costid==null || Costid=="")
			errorMessage+='費用代碼未填寫\n';
		
		if(Start==null || Start=="")
			errorMessage+='為選擇生效起始日期\n';
		
		if(End==null || End=="")
			errorMessage+='為選擇生效結束日期\n';
		if (arr.length >0) {
			if (isRepeat(arr)) {
				errorMessage+='費用代碼不能重複\n';
			}
			for (var i = 0; i < arr.length; i++) {
				if (arr[i]==null||arr[i]==""||arr[i]=='') {
					errorMessage+='未正確填寫工號\n';
				}else{
								    var ioWsPw={};
								    ioWsPw["Costid"] = arr[i];
						  			ioWsPw["Start_date"]= Start;
						  			ioWsPw["End_date"]= End;
						  			list.push(ioWsPw)
						  			console.log(ioWsPw);
						  			//checkEmpidDuplicate(arr[i],ioWsPw["WorkShopNo"]);
						  			//alert(isUserNameValid);
//						  			if (!isUserNameValid) {
//						  				empmessage+='工號'+arr[i]+'不存在\n';
//									}
				}
				
			}
		}
		
		if(errorMessage=='' && empmessage=='' ){
			//alert("進入方法");
			//新增綁定賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../FourteenRO/AddFourteenRO.do',
				data:JSON.stringify(list),
				dataType:'json',
				success:function(data){
					$('#setFourteenRO').prop("disabled",false);
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 $('#inputUserName').val('');
							 $('#workShop').val('');
							 $('#dpick1').val('');
							 $('#dpick2').val('');
							 $('#workShop').selectpicker('val',['noneSelectedText']);
							 $("#workShop").selectpicker('refresh');
							 alert(data.Message);
							 ShowAllFourteenRO();
							// $("#setFourteenRO").attr("enabled", "enabled");
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
							// $("#setFourteenRO").attr("enabled", "enabled");
						 }
					 }else{
						 alert('設置保密車間臨時進出權限失敗!');
					 }
				},
				error:function(e){
					alert('設置保密車間臨時進出權限發生錯誤');
				}
			});
		}else{
	    	if(errorMessage.length>0 ||errorMessage!='' ){
		    alert(errorMessage);		
			event.preventDefault(); //preventDefault() 方法阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
	    	return;
	    	}
	    	if (empmessage.length>0 ||empmessage!='') {
	    		 alert(empmessage);	
	    		 empmessage = '';
	  			 event.preventDefault(); //preventDefault() 方法阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
			}
	    }	
	})
	
	
	function checkEmpidDuplicate(Emp_id,WorkShopNo){
		
		if(Emp_id!=""){
			$.ajax({
				type:'POST',
				url:'../FourteenRO/checkUserName.do',
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
							 //alert(data.Message);
							 empmessage = data.Message
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
	//判斷同一卡號和車間是否有數據
	function checkCardIdDuplicate(CardId,WorkshopNo){
		
		if(CardId!=""){
			$.ajax({
				type:'POST',
				url:'../FourteenRO/checkCardId.do',
				data:{
					CardId:CardId,
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
							 isCardIdValid=false;
						 }
						 else
							{
							 isCardIdValid=true;
							}
				}else{
					isCardIdValid=false;
					}
				}
			});
		}
	}
	function ShowAllFourteenRO(){
		$.ajax({
			type:'POST',
			url:'../FourteenRO/ShowAllFourteenRO',
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
							ShowAllFourteenROTable(rawData);
						else{
							/*$('.left').css('height','727px');*/
							alert('暫無保密車間資料');
						}
					}
				}
			}
		});	
	}
	
	function ShowAllFourteenROTable(rawData){
		$('#FourteenROable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td>'+executeResult[i]["Costid"]+'</td>'+
					'<td>'+executeResult[i]["Start_date"]+'</td>'+
					'<td>'+executeResult[i]["End_date"]+'</td>'+
//					'<td>'+executeResult[i]["Direction"]+'</td>'
//					'<td>'++'</td>'+
					'<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link"><input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td>';
				tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#FourteenROable tbody').append(tableContents);
		}
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
	/*	console.log(currentPage);
		console.log(totalRecord);
		console.log(totalPage);
		console.log(pageSize);*/
		/*goPageY(currentPage,totalRecord,totalPage,pageSize);*/
		
		$(".editBtn").click(function(){
			var parentElement = $(this).parent().parent();
			var Costid=$(parentElement).find('td').eq(0).text();
			
			var Start_Date=$(parentElement).find('td').eq(1).text();
			$(parentElement).find('td').eq(1).html("<input id=\"dpick3\" class=\"Wdate\" type=\"text\" name=\"OVERTIMEDATE\" value="+Start_Date+" onfocus=\"WdatePicker({dateFmt:\'yyyy-MM-dd\',minDate:\'%y-\\#{%M-2}-01\',maxDate:\'#F{$dp.$D(\\\'dpick4\\\')}\'})\" autocomplete=\"off\" />");
			
			var End_Date=$(parentElement).find('td').eq(2).text();
			$(parentElement).find('td').eq(2).html("<input id=\"dpick4\" class=\"Wdate\" type=\"text\" name=\"OVERTIMEDATEEnd\" value="+End_Date+" onfocus=\"WdatePicker({dateFmt:\'yyyy-MM-dd\',minDate:\'#F{$dp.$D(\\\'dpick3\\\')}\'})\" autocomplete=\"off\" />");

			
			$(parentElement).find('td').eq(3).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var FourteenRO=new Object(),errorMessage='';
				var Direction=$(parentElement).find('.changeStatus option:selected').eq(0).text();
				FourteenRO.Costid=Costid;
				FourteenRO.Start_date=$(parentElement).find('td').eq(1).find('input').val();
				FourteenRO.End_date=$(parentElement).find('td').eq(2).find('input').val();

//				if(IOWorkShopPW.Emp_id==="null" || IOWorkShopPW.Emp_id=='')
//					errorMessage+='工號未填寫\n';
				if(FourteenRO.Costid==="null" || FourteenRO.Costid=='')
					errorMessage+='費用代碼未填寫\n';
				if(FourteenRO.Start_date==="null" || FourteenRO.Start_date=='')
					errorMessage+='生效起始日期未填寫\n';
				if(FourteenRO.End_date==="null" || FourteenRO.End_date=='')
					errorMessage+='生效結束日期未填寫\n';
				
				if(errorMessage==''){	
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../FourteenRO/UpdateFourteenRO.do',
						data:JSON.stringify(FourteenRO),
						dataType:'json',
						error:function(e){
							alert(e);
							},
						success:function(data){
							  if(data!=null && data!=''){
								  if(data.StatusCode=="200"){
									  alert(data.Message);
									  $(parentElement).find('.editBtn,.deleteBtn').show();
									  $(parentElement).find('td').eq(1).html(FourteenRO.Start_date);
									  $(parentElement).find('td').eq(2).html(FourteenRO.End_date);
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
				$(parentElement).find('td').eq(1).html(Start_Date);
				$(parentElement).find('td').eq(2).html(End_Date);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})					
		})
		
		$('.deleteBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var Costid=$(parentElement).find('td').eq(0).text();
			//alert("卡号"+deleteCardId);
			var results=confirm("確定刪除此條數據?");
			if(results==true){
				$.ajax({
					type:'GET',
					url:'../FourteenRO/deleteFourteenRO.do',
					data:{Costid:Costid},
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
								 ShowAllFourteenRO();
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
		$('#FourteenROListPagination').empty();
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
		
		$('#FourteenROListPagination').append(paginationElement);
		
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
			url:'../FourteenRO/ShowAllFourteenRO',
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
							ShowAllFourteenROTable(rawData);
							//$('#queryParam').val('');
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
	// 验证重复元素，有重复返回true；否则返回false
	function isRepeat(arr) {
	    var hash = {};
	    for(var i in arr) {
	        if(hash[arr[i].toUpperCase()]) {
	            return true;
	        }
	        // 不存在该元素，则赋值为true，可以赋任意值，相应的修改if判断条件即可
	        hash[arr[i].toUpperCase()] = true;
	    }
	    return false;
	}
})