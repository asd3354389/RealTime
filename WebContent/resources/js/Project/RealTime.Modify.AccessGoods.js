$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false,isCardIdValid=false,empmessage='';
	var reg = new RegExp("^[0-9]{10}$");
	ShowAllAccessGoods();
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
     
    /*$('#costId').blur(function(){
    	var costId = $('#costId').val()
    	$.ajax({
    		type:'get',
    		url:'../FourteenROP/getPerson.do',
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
    						tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';
    						$('#PersonByCostId tbody').append(tableContents);
    			}
    		},
    		error:function(e){
    			console.log(456)
    		}
    	})
    })*/
	
	$('#resetSubmit').click(function(){
 	    $('#userId').val('');
 	    $('#cardId').val('');
     	$('#dpick1').val('');
     	$('#dpick2').val('');
     	$('#workShopNo').selectpicker('val',['noneSelectedText']);
		$("#workShopNo").selectpicker('refresh');
		$('#GoodsList').selectpicker('val',['noneSelectedText']);
		$("#GoodsList").selectpicker('refresh');
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
	$('#searchAccessGoods').click(function(){
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		if(queryParam==""){
			ShowAllAccessGoods();
		}else{
			getPersonList(curPage,queryCritirea,queryParam)	
		}
	});
	

	$('#setAccessGoods').click(function(){
		
		 $("#setAccessGoods").attr("disabled", "disabled");
		 setTimeout(function(){ $("#setAccessGoods").attr("disabled",false); }, 100);
//		button_onclick($('#setFourteenROP')[0]);
		var Start =$('#dpick1').val().replace(/\//g,'-');
		var End =$('#dpick2').val().replace(/\//g,'-');
		var WorkShopNo=$('#workShopNo').val();
		var GoodsList = $('#GoodsList').val();
		var Udisk ="N";
		var Computer ="N"
		var MobilePhone ="N"
		//console.log(WorkShopNo)
		var UserId = $('#userId').val().split(',');
		var CardId = $('#cardId').val().split(',');
//		console.log(Start,End);+
		var errorMessage='',list=[],WorkShopNoStr;
		
		if(UserId==null || UserId==""){
			if(CardId==null || CardId==""){
				errorMessage+='卡號不能爲空\n';
			}
		}else{
			CardId=""
		}
		
				
		if(WorkShopNo==null||WorkShopNo.length==0){
			errorMessage+='車間不能为空\n';
		}else{
			for(var z=0;z<WorkShopNo.length;z++){
				if(WorkShopNo[z]=='ALL'){
					WorkShopNo.length=0
					WorkShopNo.push('ALL')
					break;
				}else{
					
				}
			}
		}

		if(GoodsList==null||GoodsList.length==0){
			errorMessage+='物品不能为空\n';
		}else{
			for(var j=0;j<GoodsList.length;j++){
				if(GoodsList[j]=='U盤'){
					Udisk='Y'
				}else if(GoodsList[j]=='電腦'){
					Computer='Y'
				}else if(GoodsList[j]=='手機'){
					MobilePhone='Y'
				}
			}
		}

		
		if(Start==null || Start=="")
			errorMessage+='未選擇生效起始日期\n';
		
		if(End==null || End=="")
			errorMessage+='未選擇生效結束日期\n';
		/*$("#PersonByCostId .spTable").find('input:checked').each(function (index, item) {
		     $(this).each(function () {
			      var empNo = $(this).val();
			      //td里的内容
			      var ioWsPw={};
			      ioWsPw["id"] = $(this).val();;
		  		  ioWsPw["Start_date"]= Start;
		  		  ioWsPw["End_date"]= End;
		  		  list.push(ioWsPw)
		     })
		})*/
		for(var i=0;i<WorkShopNo.length;i++){
			if(WorkShopNo[i]=='ALL'){
				list.length=0
				if(UserId!=null){
					for(var j=0;j<UserId.length;j++){
						var Ags={};
						Ags.UserId=UserId[j];
						Ags.CardId=CardId;
						Ags.WorkShopNo=WorkShopNo[i];
						Ags.Udisk=Udisk;
						Ags.Computer=Computer;
						Ags.MobilePhone=MobilePhone;
						Ags.Start_date=Start;
						Ags.End_date=End;
						list.push(Ags)
					}
				}else{
					for(var j=0;j<CardId.length;j++){
						var Ags={};
						Ags.UserId=UserId;
						Ags.CardId=CardId[j];
						Ags.WorkShopNo=WorkShopNo[i];
						Ags.Udisk=Udisk;
						Ags.Computer=Computer;
						Ags.MobilePhone=MobilePhone;
						Ags.Start_date=Start;
						Ags.End_date=End;
						list.push(Ags)
					}
				}
//				var Ags={};
//				Ags.UserId=UserId;
//				Ags.CardId=CardId;
//				Ags.WorkShopNo=WorkShopNo[i];
//				Ags.Udisk=Udisk;
//				Ags.Computer=Computer;
//				Ags.MobilePhone=MobilePhone;
//				Ags.Start_date=Start;
//				Ags.End_date=End;
//				list.push(Ags)
				break;
			}else{
				if(UserId!=null){
					for(var j=0;j<UserId.length;j++){
						var Ags={};
						Ags.UserId=UserId[j];
						Ags.CardId=CardId;
						Ags.WorkShopNo=WorkShopNo[i];
						Ags.Udisk=Udisk;
						Ags.Computer=Computer;
						Ags.MobilePhone=MobilePhone;
						Ags.Start_date=Start;
						Ags.End_date=End;
						list.push(Ags)
					}
				}else{
					for(var j=0;j<CardId.length;j++){
						var Ags={};
						Ags.UserId=UserId;
						Ags.CardId=CardId[j];
						Ags.WorkShopNo=WorkShopNo[i];
						Ags.Udisk=Udisk;
						Ags.Computer=Computer;
						Ags.MobilePhone=MobilePhone;
						Ags.Start_date=Start;
						Ags.End_date=End;
						list.push(Ags)
					}
				}
			}
		}
		//checkAccessGoods(list)
		if(errorMessage=='' && empmessage=='' ){
			
			//console.log(list)
			//alert("進入方法");
			//新增綁定賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../AccessGoods/AddAccessGoods.do',
				data:JSON.stringify(list),
				dataType:'json',
				success:function(data){
					$('#setAccessGoods').prop("disabled",false);
					 if(data!=null && data!=''){
						 if(data.StatusCode=="200"){
							 $('#userId').val('');
							 $('#cardId').val('');
							 $('#WorkShopCost').val('');
							 $('#workShopNo').selectpicker('val',['noneSelectedText'])
							 $("#workShopNo").selectpicker('refresh');
							 $('#GoodsList').val('');
							 $('#GoodsList').selectpicker('val',['noneSelectedText'])
							 $("#GoodsList").selectpicker('refresh');
							 $('#dpick1').val('');
							 $('#dpick2').val('')
							 alert(data.Message);
							 //ShowAllFourteenROP();
							 ShowAllAccessGoods();
						 }
						 else{
							 alert(data.Message);
							// $("#setFourteenROP").attr("enabled", "enabled");
						 }
					 }else{
						 alert('設置員工進入車間携帶物品權限失敗!');
					 }
					
				},
				error:function(e){
					alert('設置員工進入車間携帶物品權限錯誤');
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
				url:'../FourteenROP/checkUserName.do',
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
				url:'../FourteenROP/checkCardId.do',
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
	function ShowAllAccessGoods(){
		$.ajax({
			type:'POST',
			url:'../AccessGoods/ShowAllAccessGoods',
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
							//console.log(rawData)
							ShowAllAccessGoodsTable(rawData);
						else{
							/*$('.left').css('height','727px');*/
							alert('暫無員工進入車間携帶物品資料');
						}
					}
				}
			}
		});	
	}
	
	function ShowAllAccessGoodsTable(rawData){
		$('#AccessGoodsTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var Id = executeResult[i]["UserId"]==null?'':executeResult[i]["UserId"]
			var Card = executeResult[i]["CardId"]==null?'':executeResult[i]["CardId"]
			var Udisk = executeResult[i]["Udisk"]=='Y'?'允許':'不允許'
			var Computer = executeResult[i]["Computer"]=='Y'?'允許':'不允許'
			var MobilePhone = executeResult[i]["MobilePhone"]=='Y'?'允許':'不允許'
			var	tableContents='<tr><td>'+Id+'</td>'+
					'<td>'+Card+'</td>'+
					'<td>'+executeResult[i]["WorkShopNo"]+'</td>'+
					'<td>'+Udisk+'</td>'+
					'<td>'+Computer+'</td>'+
					'<td>'+MobilePhone+'</td>'+
					'<td>'+executeResult[i]["Start_date"]+'</td>'+
					'<td>'+executeResult[i]["End_date"]+'</td>'+
//					'<td>'+executeResult[i]["Direction"]+'</td>'
//					'<td>'++'</td>'+
					'<td><input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td>';
				tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#AccessGoodsTable tbody').append(tableContents);
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
				var FourteenROP=new Object(),errorMessage='';
				var Direction=$(parentElement).find('.changeStatus option:selected').eq(0).text();
				FourteenROP.Costid=Costid;
				FourteenROP.Start_date=$(parentElement).find('td').eq(1).find('input').val();
				FourteenROP.End_date=$(parentElement).find('td').eq(2).find('input').val();

//				if(IOWorkShopPW.Emp_id==="null" || IOWorkShopPW.Emp_id=='')
//					errorMessage+='工號未填寫\n';
				if(FourteenROP.Costid==="null" || FourteenROP.Costid=='')
					errorMessage+='費用代碼未填寫\n';
				if(FourteenROP.Start_date==="null" || FourteenROP.Start_date=='')
					errorMessage+='生效起始日期未填寫\n';
				if(FourteenROP.End_date==="null" || FourteenROP.End_date=='')
					errorMessage+='生效結束日期未填寫\n';
				
				if(errorMessage==''){	
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../FourteenROP/UpdateFourteenROP.do',
						data:JSON.stringify(FourteenROP),
						dataType:'json',
						error:function(e){
							alert(e);
							},
						success:function(data){
							  if(data!=null && data!=''){
								  if(data.StatusCode=="200"){
									  alert(data.Message);
									  $(parentElement).find('.editBtn,.deleteBtn').show();
									  $(parentElement).find('td').eq(1).html(FourteenROP.Start_date);
									  $(parentElement).find('td').eq(2).html(FourteenROP.End_date);
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
			var cardId=$(parentElement).find('td').eq(1).text();
			var workShopNo=$(parentElement).find('td').eq(2).text();
			var startDate=$(parentElement).find('td').eq(6).text();
			var endDate=$(parentElement).find('td').eq(7).text();
			//alert("卡号"+deleteCardId);
			var results=confirm("確定刪除此條數據?");
			if(results==true){
				$.ajax({
					type:'GET',
					url:'../AccessGoods/deleteAccessGoods.do',
					data:{id:id,cardId:cardId,workShopNo:workShopNo,startDate:startDate,endDate:endDate},
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
								  
								ShowAllAccessGoods();
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
		$('#AccessGoodsListPagination').empty();
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
		
		$('#AccessGoodsListPagination').append(paginationElement);
		
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
			url:'../AccessGoods/ShowAllAccessGoods',
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
							ShowAllAccessGoodsTable(rawData);
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
			url:'../AccessGoods/WorkshopNo.show',
			data:{},
			async:false,
			success:function(data){
				var htmlAppender='';
			 if(data!=null && data!=''){	
				if(data.length>0 && data.StatusCode == null){
					for(var i=0;i<data.length;i++){
						htmlAppender+='<option value="'+data[i]+'">'+data[i]+'</option>';
					}
					 $('#workShopNo').append(htmlAppender);
				/*	 $('#ChangeWorkShop').append(htmlAppender);*/
				}
				else{
					
				}
			 }else{
				
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
	
	function checkAccessGoods(list){
			if(list.length!=0){
				$.ajax({
					type:'POST',
					url:'../AccessGoods/checkAccessGoods.do',
					data:JSON.stringify(list),
					async:false,
					dataType:'json',
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