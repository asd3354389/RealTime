$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
//	var reg = new RegExp("^[0-9]{4}$");
	ShowAllWSListStatus();
	ShowReasonClass();
	$("#setWSListStatus").click(function(){
		/*$('#setCostWorkShop').attr("disabled",true);*/
		var Reason_Class=$('#reason-class').val();
//		var CostNo=Cost.split(",");
		var Reason_Desc=$('#reason-desc').val();
//		console.log(WorkShopNo);
		var list=[],errorMessage='',CostId=[];
		//console.log(CostNo.length);
		if(Reason_Class==null||Reason_Class==''){
			errorMessage+='原因分類不能为空\n';
		}
		if(Reason_Desc==null||Reason_Desc==""){
			errorMessage+='原因描述不能爲空\n';
		}/*else{
			for(var i=0;i<CostNo.length;i++){
				if(CostId.indexOf(CostNo[i])==-1){
					CostId.push(CostNo[i]);
				}
			}

			
			for(var i=0;i<CostId.length;i++){
				 checkCostIdDuplicate(CostId[i])
				 if(!isUserNameValid){
					 errorMessage+="輸入的費用代碼有不存在的費用代碼！";
					 break;
				 }
				for(var j=0;j<WorkShopNo.length;j++){
					var data = new Object();
					if(!reg.test(CostId[i])){
						errorMessage+='费用代码必须都是4位数字\n';
						break;
					}
					data.CostId = CostId[i];
					data.WorkShopNo=WorkShopNo[j];
//					console.log(data.WorkShopNo);
//					console.log(data);
					list.push(data);
				}
			}
		}*/
		
//		console.log(list);
//		alert(errorMessage);
		checkWSListStatus(Reason_Class,Reason_Desc);
		if(errorMessage==''&&!isUserNameValid){
			//新增賬號
			$.ajax({
				type:'POST',
				url:'../WSListStatus/AddWSListStatus.do',
				data:{
					Reason_Class:Reason_Class,
					Reason_Desc:Reason_Desc
				},
				dataType:'json',
				success:function(data){
					if(data!=null&&data!=''){
						ShowAllWSListStatus();
						alert(data.Message);
						$('#reason-desc').val('');

					}
					
				},
				error:function(e){
					alert('新增產綫原因描述發生錯誤');
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
								dltr.Reason_Class = child.eq(0).text();
								dltr.Reason_Desc = child.eq(1).text();
								relist.push(dltr);
			})
			console.log(relist);
			var results=confirm("確定刪除表格内的"+size+"條綁定訊息 ?");
			if(results==true){
				$.ajax({
					type:'POST',
					contentType: "application/json",
					url:'../WSListStatus/RlWSListStatus',
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
								 ShowAllWSListStatus();
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
	
	$('#submitReasonClass').click(function(){
		var Reason_Class=$('#inputReasonClass').val();
		var htmlAppender='<option value="'+Reason_Class+'">'+Reason_Class+'</option>'
		 $('#reason-class').append(htmlAppender);
		alert('創建成功');
	})
	
	$('#searchWSLStatusBtn').click(function(){
		curPage = 1;
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam;
		if(queryCritirea=="Reason_Class"){
			queryParam=$('#changeReasonClass').val();
		}		
		console.log(queryCritirea,queryParam);
		if(queryParam==""){
			ShowAllWSListStatus();
		}else{
			/*searchPersonList(curPage,queryCritirea,queryParam);
			queryParam=='';*/
			getPersonList(curPage,queryCritirea,queryParam);
			
		}
	})	  
	
	$('#queryCritirea').change(function(){
		var type= $(this).find('option:selected').val();
		if(type=="CostId"){
			$('#queryParam').css("display","inline-block");
			$('#changeWorkShopNo').css("display","none");
		}else if(type=="WorkShopNo"){
			$('#queryParam').css("display","none");
			$('#changeWorkShopNo').css("display","inline-block");
		}else{
			
		}
	})
	function ShowReasonClass(){
			$.ajax({
				type:'POST',
				url:'../WSListStatus/ReasonClass.show',
				data:{},
				async:false,
				success:function(data){
					var htmlAppender='';
				 if(data!=null && data!=''){	
					if(data.length>0 && data.StatusCode == null){
						for(var i=0;i<data.length;i++){
							htmlAppender+='<option value="'+data[i]+'">'+data[i]+'</option>';
						}
						 $('#reason-class').append(htmlAppender);
						 $('#changeReasonClass').append(htmlAppender);
					}
					else{
						alert('無原因狀態分類資料');
					}
				 }else{
					alert('無原因狀態分類資料');
				 }
				}
			});   
		}
	
	function ShowAllWSListStatus(){
		$.ajax({
			type:'GET',
			url:'../WSListStatus/ShowWSStatusList',
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
							ShowAllWSListTable(rawData);
						
						else{
							/*$('.left').css('height','727px');*/
							/*ShowAllWSListStatusTable(rawData);*/
							ShowAllWSListTable(rawData);
							setTimeout(function(){ alert('找不到產綫維護資料！'); }, 100);			
//							setTimeout(function(){ alert('暫無離崗卡與費用代碼綁定資料'); }, 100);					
						}
					}
				}
			}
		});	
	}
	
	function ShowAllWSListTable(rawData){
		$('#Personbinding tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr>'+
					'<td class="touch">'+executeResult[i]["Reason_Class"]+'</td>'+
					'<td>'+executeResult[i]["Reason_Desc"]+'</td>'+
				/*	var enabled =executeResult[i].Enabled=="Y"?'已生效':'';		
					tableContents+='<td>'+enabled+'</td>'+*/
					'<td></td>';
				   tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#Personbinding tbody').append(tableContents);
		}
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
		
		$('.touch').click(function(){	
			$('.cancelBtn').click();
			var a = $(this).text();
			var b = $(this).next().text();
	//		console.log(a,b);
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
		
		$(".editBtn").click(function(){
			var parentElement = $(this).parent().parent();
			var WorkShopNo=$(parentElement).find('td').eq(0).text();
			/*$(parentElement).find('td').eq(0).html('<select class="changeWorkShopNo input-small"></select>');
			
			ShowWorkShopNo('changeWorkShopNo');
			
			$(parentElement).find('td .changeWorkShopNo option').each(function(){
				if($(this).val()==WorkShopNo){
					$(this).prop('selected',true);
				}
			});*/
			
			var CostNo=$(parentElement).find('td').eq(1).text();
			$(parentElement).find('td').eq(1).html('<input type="text" class="changeCostNo input-small" maxlength="4" value="'+CostNo+'">');
//			$(parentElement).find('td').eq(2).html('<textarea class="input-small changeWorkShop_Desc" id="message-text" value="'+WorkShop_Desc+'"></textarea>');
			
//			$(parentElement).children().find('.editBtn .deleteBtn').hide();
			$(parentElement).find('td').eq(3).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
     
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var User=new Object(),errorMessage='';
//				var Direction=$(parentElement).find('.changeStatus option:selected').eq(0).text();
				/*User.WorkShopNo = $(parentElement).find('td option:selected').eq(0).val();*/
				User.CostId=$(parentElement).find('td input:text').eq(0).val();	
				User.O_CostId = CostNo;
				User.O_WorkShopNo = WorkShopNo;
				
				
				if(User.CostId==="null" || User.CostId=='')
					errorMessage+='費用代碼未填寫\n';
				if(!reg.test(User.CostId))	
					errorMessage+='費用代碼不符合規格！必須是4位數\n';
				checkCostIdDuplicate(User.CostId);
				if(!isUserNameValid){
					errorMessage+='此费用代码資料不存在';
				}
				checkWorkShopCost(User.CostId,User.O_WorkShopNo);
				if(isUserNameValid){
					errorMessage+='此费用代码綁定此車間資料已存在，請重新輸入！';
				}
				/*if(User.WorkShopNo==="null" || User.WorkShopNo=='')
					errorMessage+='車間號未填寫\n';*/
				
//				console.log(User);
				if(errorMessage==''){	
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../ExceptionCost//UpdateExceCost',
						data:JSON.stringify(User),
						dataType:'json',
						error:function(e){
							alert(e);
							},
						success:function(data){
							  if(data!=null && data!=''){
								  if(data.StatusCode=="200"){
									  alert(data.Message);
									  $(parentElement).find('.editBtn').show();
									  $(parentElement).find('td').eq(0).html(User.WorkShopNo);
									  $(parentElement).find('td').eq(1).html(User.CostId);
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
				$(parentElement).find('td').eq(0).html(WorkShopNo);
				$(parentElement).find('td').eq(1).html(CostNo);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})					
		})
		
		
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
			type:'GET',
			url:'../WSListStatus/ShowWSStatusList',
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
							ShowAllWSListTable(rawData);
							//$('#queryParam').val('');
						}
						else{
						
							alert('找不到資料');
						}	
					}
				}
			}
		});
	}
	
	 
	 function checkWSListStatus(ReasonClass,ReasonDesc){
//		 alert(1);
			if(ReasonClass!=""&&ReasonDesc!=""){
				$.ajax({
					type:'POST',
					url:'../WSListStatus/checkWSListStatus.do',
					data:{
						Reason_Class:ReasonClass,
						Reason_Desc:ReasonDesc
					},
					async:false,
					error:function(e){
						alert(e);
					},
					success:function(data){	
						 if(data!=null && data!=''){
							 if(data.StatusCode==200){
								 alert(data.Message);
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