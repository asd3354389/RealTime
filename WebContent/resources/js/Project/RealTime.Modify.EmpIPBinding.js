$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	ShowAllEmpIPBinding();
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
		$('#insert_deviceIP').val('');
	     $('#insert_emp_id').val('');
	});
	
	$('#searchEmpIPBinding').click(function(){
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		if(queryParam==""){
			ShowAllEmpIPBinding();
		}else{
			getPersonList(curPage,queryCritirea,queryParam)	
		}
	});
	
	$('.reset').on('click',()=>{
		$('#deleteId .dlTable').find('tr').remove();
	})
	
	
	
	$('.deleteIp').on('click',()=>{
		var size = $('#deleteId .dlTable').children().length;
		if($('#deleteId .dlTable').children().length==0){
			alert("無數據可刪除!");
		}else{
			var relist =[];
			$('#deleteId .dlTable').find('tr').each(function(i,e){
				//				console.log(i);
								var dltr = {};
								var child =$(this).children();
								dltr.deviceIP = child.eq(0).text();
								dltr.emp_id = child.eq(1).text();
								relist.push(dltr);
			})
//			console.log(relist);
			var results=confirm("確定刪除表格内的"+size+"條綁定訊息 ?");
			if(results==true){
				$.ajax({
					type:'POST',
					url:'../EmpIPBinding/deleteEmpIPBinding.do',
					data:JSON.stringify(relist),
					dataType:'json',
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
								 ShowAllEmpIPBinding();
								 $('#deleteId .dlTable').empty();
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
		}
	})
	
	$('#setEmpIPBinding').click(function(){
		button_onclick($('#setEmpIPBinding')[0]);
		var EmpIpBinding=new Object(),errorMessage='';
		EmpIpBinding.deviceIP=$('#insert_deviceIP').val();
		EmpIpBinding.emp_id=$('#insert_emp_id').val();
		console.log(EmpIpBinding);
		
		if(EmpIpBinding.deviceIP==="null" || EmpIpBinding.deviceIP=='')
			errorMessage+='車間ip未填寫\n';
		if(EmpIpBinding.emp_id==="null" || EmpIpBinding.emp_id=='')
			errorMessage+='未填寫車間ip綁定員工工號\n';
		
		if(errorMessage==''){
			//新增綁定賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../EmpIPBinding/AddEmpIPBinding.do',
				data:JSON.stringify(EmpIpBinding),
				dataType:'json',
				success:function(data){
					$('#setIOCardMaIP').prop("disabled",false);
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 $('#insert_deviceIP').val('');
						     $('#insert_emp_id').val('');
							 alert(data.Message);
							 ShowAllEmpIPBinding();
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
	
	function ShowAllEmpIPBinding(){
		$.ajax({
			type:'POST',
			url:'../EmpIPBinding/ShowAllEmpIPBinding',
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
							ShowAllEmpIPBindingTable(rawData);
						else{
							/*$('.left').css('height','727px');*/
							alert('暫無卡機信息資料');
						}
					}
				}
			}
		});	
	}
	
	function ShowAllEmpIPBindingTable(rawData){
		$('#EmpIPBindingTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td class="touch">'+executeResult[i]["deviceIP"]+'</td>'+
					'<td>'+executeResult[i]["emp_id"]+'</td>'+
//					'<td>'+executeResult[i]["Direction"]+'</td>'
//					'<td>'++'</td>'+
					'<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link"></td>';
				tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#EmpIPBindingTable tbody').append(tableContents);
		}
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
		
		
		$(".editBtn").click(function(){
			var parentElement = $(this).parent().parent();
			var deviceIP=$(parentElement).find('td').eq(0).text();
			
			var emp_id=$(parentElement).find('td').eq(1).text();
			$(parentElement).find('td').eq(1).html('<input type="text" value='+emp_id+'>');

//			$(parentElement).children().find('.editBtn .deleteBtn').hide();
			$(parentElement).find('td').eq(2).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn').hide();
			
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var EmpIpBinding=new Object(),errorMessage='';
				var Direction=$(parentElement).find('.changeStatus option:selected').eq(0).text();
				EmpIpBinding.deviceIP=deviceIP;
				EmpIpBinding.emp_id=$(parentElement).find('td').eq(1).find('input').val();
				EmpIpBinding.oldEmp_id=emp_id;
				
				if(EmpIpBinding.deviceIP==="null" || EmpIpBinding.deviceIP=='')
					errorMessage+='車間ip未填寫\n';
				if(EmpIpBinding.emp_id==="null" || EmpIpBinding.emp_id=='')
					errorMessage+='對應人員為填寫\n';
				
				console.log(EmpIpBinding);
				if(errorMessage==''){	
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../EmpIPBinding/UpdateEmpIPBinding.do',
						data:JSON.stringify(EmpIpBinding),
						dataType:'json',
						error:function(e){
							alert(e);
							},
						success:function(data){
							  if(data!=null && data!=''){
								  if(data.StatusCode=="200"){
									  alert(data.Message);
									  $(parentElement).find('.editBtn').show();
									  $(parentElement).find('td').eq(1).html(EmpIpBinding.emp_id);
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
				$(parentElement).find('.editBtn').show();
				$(parentElement).find('td').eq(1).html(emp_id);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})					
		})
		
		$('.touch').click(function(){	
			$('.cancelBtn').click();
			var a = $(this).text();
			var b = $(this).next().text();
			console.log(a,b);
			var list =[];
			if($('#deleteId .dlTable').children().length==0){
				$('#deleteId .dlTable').append('<tr><td>'+a+'</td><td>'+b+'</td></tr>');
			}else{
				$('#deleteId .dlTable').find('tr').each(function(i,e){
	//				console.log(i);
					var dltr = {};
					var child =$(this).children();
					dltr.c = child.eq(0).text();
					dltr.d = child.eq(1).text();
					list.push(dltr);
	//				console.log(list);
					
				})
				var count=0;
				for(var i=0;i<list.length;i++){
					if((list[i].c==a)&&(list[i].d==b)){
						count++;
					}
				}
				if(count==0){
					$('#deleteId .dlTable').append('<tr><td>'+a+'</td><td>'+b+'</td></tr>')
				}
			}
			$('#deleteId .dlTable').find('tr').each(function(i,e){
				$(this).click(function(){
					$(e).remove();
				})
			})
		})
		
		
		/*$('.deleteBtn').click(function(){
			var parentElement=$(this).parent().parent();
			deviceIP=$(parentElement).find('td').eq(0).text();
			emp_id=$(parentElement).find('td').eq(1).text();
			var results=confirm("確定刪除"+deviceIP+"與"+emp_id+"的綁定訊息 ?");
			if(results==true){
				$.ajax({
					type:'GET',
					url:'../EmpIPBinding/deleteEmpIPBinding.do',
					data:{deviceIP:deviceIP,emp_id:emp_id},
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
								  
								 ShowAllEmpIPBinding();
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
		});*/
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
			url:'../EmpIPBinding/ShowEmpIPBindingList',
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
							ShowAllEmpIPBindingTable(rawData);
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