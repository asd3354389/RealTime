$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	var reg = new RegExp("^[0-9]{10}$");
	ShowAllPersonList();
//	ShowCostNo()
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

//      $('#CostNo').blur(function(){
//    	  var CostId = $("#CostNo").val();
//    	  if(CostId!=""){
//    		  $('#deptNo').empty();
//    		  ShowDepNo(CostId);
//    	  }else{
//    		  
//    	  }   	 
//      })
		
	$('#searchBdPersonListBtn').click(function(){
		curPage = 1;
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
	
		if(queryParam==""){
			ShowAllPersonList();
		}else{
			/*searchPersonList(curPage,queryCritirea,queryParam);
			queryParam=='';*/
			getPersonList(curPage,queryCritirea,queryParam);
			
		}
			/*ShowAllFLPersonListN(curPage,queryCritirea,queryParam);*/

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
								dltr.CostId = child.eq(0).text();
								dltr.D_CardID = child.eq(1).text();
								dltr.Default_WorkShop = child.eq(2).text();
								relist.push(dltr);
			})
			console.log(relist);
			var results=confirm("確定刪除表格内的"+size+"條綁定訊息 ?");
			if(results==true){
				$.ajax({
					type:'POST',
					contentType: "application/json",
					url:'../OTCardPerson/RelieveOTCard',
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
								 ShowAllPersonList();
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
	
	//離崗卡綁定費用代碼
	$('#changebdOT').click(function(){
		button_onclick($('#changebdOT')[0])
		var user={},errorMessage='';
		user["D_CardID"]=$('#inputOTCarid').val();
		user["CostId"]=$('#CostNo').val();
		user["Default_WorkShop"]=$('#workShop option:selected').val();
		console.log(user);
		
		if(user["CostId"]==="null" || user["CostId"]=='')
			errorMessage+='費用代碼不能爲空\n';		
//		checkCostIdDuplicate(user["CostId"]);
		if(user["D_CardID"]=='' || user["D_CardID"]==null){
			errorMessage+='離崗卡號必填 \n';
		}
		if(!reg.test(user["D_CardID"])){
			errorMessage+='離崗卡號不符合規格！必須是10位數\n';
		}
		checkDcard(user["CostId"],user["D_CardID"]);
		if(user["Default_WorkShop"]==="null" || user["Default_WorkShop"]=='')
			errorMessage+='未選擇使用的車間\n';
		if(errorMessage==''&& isUserNameValid){
			//新增綁定賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../OTCardPerson/AddOTCardBdPerson.do',
				data:JSON.stringify(user),
				dataType:'json',
				success:function(data){
					
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 $('#CostNo').val('');
							 $('#inputOTCarid').val('');
//							 $('#deptNo').empty();
							 ShowAllPersonList();
							 alert(data.Message);
//							 $('#changebdOT').prop("disabled",false);
							
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
						 alert('綁定離崗卡失敗!');
					 }
				},
				error:function(e){
					alert('綁定離崗卡發生錯誤');
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
	
	 $('#resetSubmit').click(function(){
		 	$('#CostNo').val('');
		    $('#deptNo').empty();
	    	$('#inputOTCarid').val('');
	  });
	
	//判斷工號是否綁定
	  function checkUserNameDuplicate(Dmp_id){
			if(Dmp_id!=""){
				$.ajax({
					type:'POST',
					url:'../OTCardPerson/checkUserName.do',
					data:{
						Dmp_id:Dmp_id
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
	function ShowDepNo(CostId){
		  
		  $.ajax({
	  			type:'POST',
	  			url:'../OTCardPerson/ShowDeptNo',
	  			data:{CostId:CostId},
//	  			async:false,
	  			success:function(data){
	  				
	  				var StatusCode = data.StatusCode;
	  				var message = data.message;
	  				var htmlAppender='';
	  				 if(StatusCode=="200"){	
	  					var obj=eval(message);
	  					console.log(obj);
	  						for(var i=0;i<obj.length;i++){
	  								htmlAppender+='<option value="'+obj[i].depid+'">'+obj[i].depid+'</option>';
	  							}
	  							 $('#deptNo').append(htmlAppender);
	  				}else{
	  					alert("此費用代碼錯誤或無相應的綫組別號！請重新輸入");
						  $("#CostNo").val("");
//				    	  $('#inputOTCarid').val("")
	  				}
	  			},
	  			error:function(e){
	  				alert(e);
	  			}
	  		})
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
	//顯示綁定離崗卡的費用代碼
	function ShowAllPersonList(){
		$.ajax({
			type:'POST',
			url:'../OTCardPerson/ShowPersonList',
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
							ShowAllPersonListTable(rawData);
						else{
							/*$('.left').css('height','727px');*/
							ShowAllPersonListTable(rawData);
							setTimeout(function(){ alert('暫無離崗卡與費用代碼綁定資料'); }, 100);					
						}
					}
				}
			}
		});	
	}
	
	function ShowAllPersonListTable(rawData){
		$('#Personbinding tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr>'+
					'<td class="touch">'+executeResult[i]["CostId"]+'</td>'+
					'<td>'+executeResult[i]["D_CardID"]+'</td>'+
					'<td>'+executeResult[i]["Default_WorkShop"]+'</td>'
					var enabled =executeResult[i].Enabled=="Y"?'已生效':'';		
					tableContents+='<td>'+enabled+'</td>'+
					'<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link"></td>';
				   tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#Personbinding tbody').append(tableContents);
		}
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
	
		$('.touch').click(function(){	
			$('.cancelBtn').click();
			var a = $(this).text();
			var b = $(this).next().text();
			var c = $(this).nextAll().eq(1).text();
			console.log(a,b);
			var list =[];
			if($('#deleteId .dlTable').children().length==0){
				$('#deleteId .dlTable').append('<tr><td>'+a+'</td><td>'+b+'</td><td>'+c+'</td></tr>');
			}else{
				$('#deleteId .dlTable').find('tr').each(function(i,e){
	//				console.log(i);
					var dltr = {};
					var child =$(this).children();
					dltr.d = child.eq(0).text();
					dltr.e = child.eq(1).text();
					dltr.f = child.eq(2).text();
					list.push(dltr);
	//				console.log(list);
					
				})
				var count=0;
				for(var i=0;i<list.length;i++){
					if((list[i].d==a)&&(list[i].e==b)&&(list[i].f==c)){
						count++;
					}
				}
				if(count==0){
					$('#deleteId .dlTable').append('<tr><td>'+a+'</td><td>'+b+'</td><td>'+c+'</td></tr>')
				}
			}
			$('#deleteId .dlTable').find('tr').each(function(i,e){
				$(this).click(function(){
					$(e).remove();
				})
			})
		})
		
		$(".editBtn").click(function(){
			var parentElement = $(this).parent().parent();
			var WorkShopNo=$(parentElement).find('td').eq(2).text();
			$(parentElement).find('td').eq(2).html('<select class="changeWorkShopNo input-small"></select>');
			
			ShowWorkShopNo('changeWorkShopNo');
			
			$(parentElement).find('td .changeWorkShopNo option').each(function(){
				if($(this).val()==WorkShopNo){
					$(this).prop('selected',true);
				}
			});
			
			var o_CardNo=$(parentElement).find('td').eq(1).text();
			$(parentElement).find('td').eq(1).html('<input type="text" class="changeOTCard input-small" maxlength="10" value="'+o_CardNo+'">');
//			$(parentElement).find('td').eq(2).html('<textarea class="input-small changeWorkShop_Desc" id="message-text" value="'+WorkShop_Desc+'"></textarea>');
			
//			$(parentElement).children().find('.editBtn .deleteBtn').hide();
			$(parentElement).find('td').eq(4).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
     
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var User=new Object(),errorMessage='';
//				var Direction=$(parentElement).find('.changeStatus option:selected').eq(0).text();
				User.CostId=$(parentElement).find('td').eq(0).text();
				User.D_CardID=$(parentElement).find('td input:text').eq(0).val();
				User.Default_WorkShop=$(parentElement).find('option:selected').eq(0).val();
				User.O_CardID = o_CardNo;
				
				
				if(User.D_CardID==="null" || User.D_CardID=='')
					errorMessage+='離崗卡未填寫\n';
				if(!reg.test(User.D_CardID))	
					errorMessage+='離崗卡號不符合規格！必須是10位數\n';
				if(User.Default_WorkShop==="null" || User.Default_WorkShop=='')
					errorMessage+='默認使用車間未填寫\n';

				checkData(User);
				console.log(User);
				if(errorMessage==''&&isUserNameValid){	
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../OTCardPerson//UpdateBdOTCard',
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
									  $(parentElement).find('td').eq(0).html(User.Deptid);
									  $(parentElement).find('td').eq(1).html(User.D_CardID);
									  $(parentElement).find('td').eq(2).html(User.Default_WorkShop);
//									  $(parentElement).find('td').eq(6).html(User.ROLE);
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
				$(parentElement).find('td').eq(1).html(o_CardNo);
				$(parentElement).find('td').eq(2).html(WorkShopNo);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})					
		})
/*		$('.deleteBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var deleteDeptid=$(parentElement).find('td').eq(0).text();
			var o_CardNo=$(parentElement).find('td').eq(1).text();
			var WorkShopNo=$(parentElement).find('td').eq(2).text();
			var user={};
			user["D_CardID"]=o_CardNo;
			user["CostId"]=deleteDeptid;		
			user["Default_WorkShop"]=WorkShopNo
			var results=confirm("確定刪除綫號為 "+deleteDeptid+" 的離崗卡綁定 ?");
			console.log(user);
			if(results==true){
				$.ajax({
					type:'POST',
					url:'../OTCardPerson/RelieveOTCard',
					contentType: "application/json",
					data:JSON.stringify(user),
					dataType:'json',
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
								  
								 ShowAllPersonList();
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
		$('#PersonListPagination').empty();
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
		
		$('#PersonListPagination').append(paginationElement);
		
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
			url:'../OTCardPerson/ShowPersonList',
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
							ShowAllPersonListTable(rawData);
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
					 $('#ChangeWorkShop').append(htmlAppender);
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
	
	function getToPerson(Emp){
	
		$.ajax({
			type:'POST',
			contentType: "application/json",
			url:'../OTCardPerson/UpdateBdOTCard',
			data:JSON.stringify(Emp),
			dataType:'json',
			async:false,
			error:function(e){
				alert(e);
				},
			success:function(data){
				
				 if(data!=null && data!=''){
					  if(data.StatusCode=="200"){
						  ShowAllPersonList();
						  $('#ChangeOTCard').val("");
						  alert(data.Message);
//						  $('#changeOTWorkshop').prop("disabled",false);
						 /* location.reload();*/
						 
					  }
					  else{
						  alert(data.Message);
					  }
				  }else{
					  alert('操作失敗！')
				  }
				}
		})
	}
	
	function relieveOTCard(Emp){
//		$('#relieveBdOTCard').prop("disabled",false);
		$.ajax({
			type:'POST',
			contentType: "application/json",
			url:'../OTCardPerson/RelieveOTCard',
			data:JSON.stringify(Emp),
			dataType:'json',
			async:false,
			error:function(e){
				alert(e);
				},
			success:function(data){
				
				 if(data!=null && data!=''){
					  if(data.StatusCode=="200"){
						  
						  ShowAllPersonList();
						  alert(data.Message);
						 /* location.reload();*/
						 
					  }
					  else{
						  alert(data.Message);
					  }
				  }else{
					  alert('操作失敗！')
				  }
				}
		})
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
	
	function ShowCostNo(){
		$.ajax({
			type:'POST',
			url:'../OTCardPerson/ShowCostNo',
			dataType:"text",
			data:{},
			async:false,
			success:function(result){
				var data = result.split('*');
				var htmlAppender='';
//				console.log(data);
			 if(data!=null && data!=''){	
				if(data.length>0 && data.StatusCode == null){
					for(var i=0;i<data.length;i++){
						htmlAppender+='<option value="'+data[i]+'">'+data[i]+'</option>';
					}
					 $('#CostNo').append(htmlAppender);
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
					url:'../OTCardPerson/checkCostIdExistence.do',
					data:{
						CostId:CostId
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
	 
	 function checkDcard(CostId,D_CardId){
//		 alert(1);
			if(CostId!=""&& D_CardId!=""){
				$.ajax({
					type:'POST',
					url:'../OTCardPerson/checkCostIDCard.do',
					data:{
						CostId:CostId,
						D_CardId:D_CardId
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
	 
	 function checkData(User){
//		 alert(1);
			if(User!=null){
				$.ajax({
					type:'POST',
					contentType: "application/json",
					url:'../OTCardPerson/checkData.do',
					data:JSON.stringify(User),
					async:false,
					dataType:'json',
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
})