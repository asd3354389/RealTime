$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false,isCardIdValid=false,empmessage='';
	var reg = new RegExp("^[0-9]{10}$");
	ShowAllFourteenROPTX();
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
    
    $('#AllCheck').click(function(){
		 var checkALL = document.getElementById("AllCheck");
	      var items = $("#PersonByCostId .spTable").find('input');
	      checkAllBox(checkALL,items); 
	}) 
     
    $('#costId').blur(function(){
    	var costId = $('#costId').val()
    	$.ajax({
    		type:'get',
    		url:'../FourteenROPTX/getPerson.do',
    		data:{
    			costId:costId
    		},
    		success:function(data){
    			$('#PersonByCostId tbody').empty()
    			var executeResult = data;
    			for(var i=0;i<executeResult.length;i++){
    				var	tableContents='<tr><td><input type="checkbox" value='+executeResult[i]["EmpNo"]+' style="width:100%; height:15px"></td>'+
    						'<td>'+executeResult[i]["EmpNo"]+'</td>'+
    						'<td>'+executeResult[i]["EmpName"]+'</td>'
    					tableContents+='</tr>';
    						/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
    						$('#PersonByCostId tbody').append(tableContents);
    			}
    		},
    		error:function(e){
    			console.log(456)
    		}
    	})
    })
	
	$('#resetSubmit').click(function(){
		$('#costId').val('');
     	$('#PersonByCostId .spTable').empty();
     	$('#dpick1').val('');
     	$('#dpick2').val('');
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
	$('#searchFourteenROPTX').click(function(){
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		if(queryParam==""){
			ShowAllFourteenROPTX();
		}else{
			getPersonList(curPage,queryCritirea,queryParam)	
		}
	});
	
	//設置員工十四休一權限
	$('#setFourteenROPTX').click(function(){
		
		 $("#setFourteenROPTX").attr("disabled", "disabled");
		 setTimeout(function(){ $("#setFourteenROPTX").attr("disabled",false); }, 100);
//		button_onclick($('#setFourteenROPTX')[0]);
		var Start =$('#dpick1').val().replace(/\//g,'-');
		var End =$('#dpick2').val().replace(/\//g,'-');
//		console.log(Start,End);+
		var errorMessage='',list=[],WorkShopNoStr;
		
		if(Start==null || Start=="")
			errorMessage+='未選擇生效起始日期\n';
		
		if(End==null || End=="")
			errorMessage+='未選擇生效結束日期\n';
		$("#PersonByCostId .spTable").find('input:checked').each(function (index, item) {
		     $(this).each(function () {
			      var empNo = $(this).val();
			      //td里的内容
			      var ioWsPw={};
			      ioWsPw["id"] = $(this).val();;
		  		  ioWsPw["Start_date"]= Start;
		  		  ioWsPw["End_date"]= End;
		  		  list.push(ioWsPw)
		     })
		})
		if(!list.length>0){
			errorMessage+='未選擇員工號\n'
		}
		if(errorMessage=='' && empmessage=='' ){
			//alert("進入方法");
			//新增綁定賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../FourteenROPTX/AddFourteenROPTX.do',
				data:JSON.stringify(list),
				dataType:'json',
				success:function(data){
					$('#setFourteenROPTX').prop("disabled",false);
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 $('#PersonByCostId tbody').empty()
							 $('#costId').val('');
							 $('#dpick1').val('');
							 $('#dpick2').val('');
//							 $('#workShop').selectpicker('val',['noneSelectedText']);
//							 $("#workShop").selectpicker('refresh');
							 alert(data.Message);
							 ShowAllFourteenROPTX();
							// $("#setFourteenROPTX").attr("enabled", "enabled");
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
							// $("#setFourteenROPTX").attr("enabled", "enabled");
						 }
					 }else{
						 alert('設置十四休一設置權限失敗!');
					 }
				},
				error:function(e){
					alert('設置十四休一設置權限發生錯誤');
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
				url:'../FourteenROPTX/checkUserName.do',
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
				url:'../FourteenROPTX/checkCardId.do',
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
	function ShowAllFourteenROPTX(){
		$.ajax({
			type:'POST',
			url:'../FourteenROPTX/ShowAllFourteenROPTX',
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
							ShowAllFourteenROPTXTable(rawData);
						else{
							/*$('.left').css('height','727px');*/
							alert('暫無十四休一資料');
						}
					}
				}
			}
		});	
	}
	
	function ShowAllFourteenROPTXTable(rawData){
		$('#FourteenROPTXable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td>'+executeResult[i]["id"]+'</td>'+
					'<td>'+executeResult[i]["Start_date"]+'</td>'+
					'<td>'+executeResult[i]["End_date"]+'</td>'+
//					'<td>'+executeResult[i]["Direction"]+'</td>'
//					'<td>'++'</td>'+
					'<td><input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td>';
				tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#FourteenROPTXable tbody').append(tableContents);
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
				var FourteenROPTX=new Object(),errorMessage='';
				var Direction=$(parentElement).find('.changeStatus option:selected').eq(0).text();
				FourteenROPTX.Costid=Costid;
				FourteenROPTX.Start_date=$(parentElement).find('td').eq(1).find('input').val();
				FourteenROPTX.End_date=$(parentElement).find('td').eq(2).find('input').val();

//				if(IOWorkShopPW.Emp_id==="null" || IOWorkShopPW.Emp_id=='')
//					errorMessage+='工號未填寫\n';
				if(FourteenROPTX.Costid==="null" || FourteenROPTX.Costid=='')
					errorMessage+='費用代碼未填寫\n';
				if(FourteenROPTX.Start_date==="null" || FourteenROPTX.Start_date=='')
					errorMessage+='生效起始日期未填寫\n';
				if(FourteenROPTX.End_date==="null" || FourteenROPTX.End_date=='')
					errorMessage+='生效結束日期未填寫\n';
				
				if(errorMessage==''){	
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../FourteenROPTX/UpdateFourteenROPTX.do',
						data:JSON.stringify(FourteenROPTX),
						dataType:'json',
						error:function(e){
							alert(e);
							},
						success:function(data){
							  if(data!=null && data!=''){
								  if(data.StatusCode=="200"){
									  alert(data.Message);
									  $(parentElement).find('.editBtn,.deleteBtn').show();
									  $(parentElement).find('td').eq(1).html(FourteenROPTX.Start_date);
									  $(parentElement).find('td').eq(2).html(FourteenROPTX.End_date);
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
			var id=$(parentElement).find('td').eq(0).text();
			var startDate=$(parentElement).find('td').eq(1).text();
			var endDate=$(parentElement).find('td').eq(2).text();
			//alert("卡号"+deleteCardId);
			var results=confirm("確定刪除此條數據?");
			if(results==true){
				$.ajax({
					type:'GET',
					url:'../FourteenROPTX/deleteFourteenROPTX.do',
					data:{id:id,startDate:startDate,endDate:endDate},
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
								 ShowAllFourteenROPTX();
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
		$('#FourteenROPTXListPagination').empty();
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
		
		$('#FourteenROPTXListPagination').append(paginationElement);
		
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
			url:'../FourteenROPTX/ShowAllFourteenROPTX',
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
							ShowAllFourteenROPTXTable(rawData);
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
	
	function checkAllBox(checkALL,items){
		 if(checkALL.checked==true){
	            //checked判断是否选中
	            for(var i=0;i<items.length;i++)
	            {
	                var box=items.get(i);
	                box.checked=true;
	                
	            }
		 }else{
			 for(var i=0;i<items.length;i++)
	            {
	                var box=items.get(i);
	                box.checked=false;
	            }
		 }
	}
	
	$('#uploadEmpIdFile').change(function(){  
		getFileContent(this, function (str) {
			str = str.replace(/\ +/g,"");
			str = str.replace(/[ ]/g,"");
			str = str.replace(/[\r\n]/g,"");
			var arr = str.split(",");
			var DATE_FORMAT = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
			var Start =$('#dpick1').val().replace(/\//g,'-');
			var End =$('#dpick2').val().replace(/\//g,'-');
			var errorMessage='',list=[];
			
			if(Start==null || Start=="")
				errorMessage+='未選擇生效起始日期\n';
			
			if(End==null || End=="")
				errorMessage+='未選擇生效結束日期\n';
			
			for(var i = 0;i<arr.length;i++){
				var ioWsPw={};
			    ioWsPw["id"] = arr[i];
		  		ioWsPw["Start_date"]= Start;
		  		ioWsPw["End_date"]= End;
				list.push(ioWsPw);
				console.log(ioWsPw);
			}
			console.log(list);
			
			if(errorMessage=='' && empmessage=='' ){
				//alert("進入方法");
				//新增綁定賬號
				$.ajax({
					type:'POST',
					contentType: "application/json",
					url:'../FourteenROPTX/AddFourteenROPTX.do',
					data:JSON.stringify(list),
					dataType:'json',
					success:function(data){
						$('#setFourteenROPTX').prop("disabled",false);
						 if(data!=null && data!=''){
							 if(data.StatusCode=="200"){
//								 $('#workShop').selectpicker('val',['noneSelectedText']);
//								 $("#workShop").selectpicker('refresh');
								 alert(data.Message);
								 ShowAllFourteenROPTX();
								// $("#setFourteenROPTX").attr("enabled", "enabled");
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
								// $("#setFourteenROPTX").attr("enabled", "enabled");
							 }
						 }else{
							 alert('十四休一設置權限失敗!');
						 }
					},
					error:function(e){
						alert('十四休一設置權限發生錯誤');
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
			
			//var exportEmpId=str.replace(/\s+/g,',').replace(/,$/,"");
			//$('#searchEmpId').val(exportEmpId);
			//$('#importEmpIdSum').text(exportEmpId.split(",").length);
            });	   
		 $('#uploadEmpIdFile').val('');
	});	
	
	
		function isValidateDate(date) {
			      date = $.trim(date);
			      var reg = /^(\d{4})-(\d{2})-(\d{2})$/;
			      reg.exec(date);
			      if (!reg.test(date) && RegExp.$2 <= 12 && RegExp.$3 <= 31) {
			          return false;
			      }
			      var year, month, day;
			      year = parseInt(date.split("-")[0], 10);
			      month = parseInt(date.split("-")[1], 10);
			      day = parseInt(date.split("-")[2], 10);
			      if (! ((1 <= month) && (12 >= month) && (31 >= day) && (1 <= day))) {
			          return false;
			      }
			      if ((month <= 7) && ((month % 2) == 0) && (day >= 31)) {
			          return false;
			      }
			      if ((month >= 8) && ((month % 2) == 1) && (day >= 31)) {
			          return false;
			      }
			      if (month == 2) {
			          if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
			              if (day > 29) {
							return false;
			             }
			         } else {
			              if (day > 28) {
			                  return false;
			             }
			         }
			     }
			      return true;
			  }
})