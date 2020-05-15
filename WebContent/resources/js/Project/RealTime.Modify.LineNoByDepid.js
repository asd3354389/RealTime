$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	ShowWorkShop();
	ShowLineNoByDepid()
	
	$('#resetSubmit').on('click',()=>{
		$('#LineNo').selectpicker('val',['noneSelectedText'])
		$("#LineNo").selectpicker('refresh');
		$('#depid').val('')
	})
	
	$('#searchLineNoByDepid').click(function(){
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		if(queryParam==""){
			ShowLineNoByDepid();
		}else{
			getPersonList(curPage,queryCritirea,queryParam)	
		}
	});
	
	$('#workShopNo').change(function(){
		var workShopNo = $('#workShopNo').val()
		console.log(workShopNo)
		$('#LineNo').empty()
		$('#LineNo').selectpicker('val',['noneSelectedText'])
		$("#LineNo").selectpicker('refresh');

		$.ajax({
			type:'GET',
			url:'../LineNoByDepid/LineNo.show',
			data:{
				WorkShopNo:workShopNo
			},
			async:false,
			success:function(data){
				var htmlAppender='';
			 if(data!=null && data!=''){	
				if(data.length>0 && data.StatusCode == null){
					for(var i=0;i<data.length;i++){
						if(data[i]==null){
							
						}else{
							htmlAppender+='<option value="'+data[i]+'">'+data[i]+'</option>';
						}
					}
					 $('#LineNo').append(htmlAppender);
					 $('#LineNo').selectpicker('refresh');
				}
				else{
					alert('無綫體資料');
				}
			 }else{
				alert('無綫體資料');
			 }
			}
		});   
	})
	
	$('#setData').on('click',function(){
		var WorkShopNo = $('#workShopNo').val()
		var LineNo = $('#LineNo').val()
		var Depid = $('#depid').val()
		var list=[],errorMessage='';
		if(WorkShopNo==null||WorkShopNo==""){
			errorMessage+='車間號不能爲空\n';
		}if(LineNo.length==0){
			errorMessage+='綫體號不能为空\n';
		}else{				
			for(var i=0;i<LineNo.length;i++){
				 checkWorkShopStatus(WorkShopNo,LineNo[i])
				 if(isUserNameValid){
					 errorMessage+="選擇的車間綫體中有已經存在的，請重新選擇！";
					 break;
				 }
	
					var data = new Object();
					
					data.WorkShopNo=WorkShopNo;
					data.LineNo = LineNo[i];
					data.Depid=Depid;
					list.push(data);
					
			}
		}
		if(errorMessage==''){
			//新增賬號
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../LineNoByDepid/AddLineNoByDepid.do',
				data:JSON.stringify(list),
				dataType:'json',
				success:function(data){
				
					if(data!=null&&data!=''){
						ShowLineNoByDepid();
						alert(data.Message);
						$('#LineNo').selectpicker('val',['noneSelectedText'])
						$("#LineNo").selectpicker('refresh');
						$('#depid').val('')
					}
					
				},
				error:function(e){
					alert('新設車間綫體綁定綫組別代碼發生錯誤');
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
						 $('#workShopNo').append(htmlAppender);
//						 $('#changeWorkShopNo').append(htmlAppender);
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
	 function checkWorkShopStatus(WorkShopNo,LineNo){
//		 alert(1);
			if(WorkShopNo!=""&&LineNo!=""){
				$.ajax({
					type:'POST',
					url:'../LineNoByDepid/checkWorkShopStatud.do',
					data:{
						WorkShopNo:WorkShopNo,
						LineNo:LineNo
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
	 
	 function ShowLineNoByDepid(){
		 $.ajax({
				type:'GET',
				url:'../LineNoByDepid/LineNoByDepid.show',
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
								ShowAllListTable(rawData);
							
							else{
								ShowAllListTable(rawData);
								setTimeout(function(){ alert('找不到車間綫體綁定綫組別代碼資料！'); }, 100);			
//								
							}
						}
					}
				}
			});	
	 }
	 
	 function ShowAllListTable(rawData){
		 $('#LineNoByDepidTable tbody').empty();
			var currentPage=rawData.currentPage;
			var totalRecord=rawData.totalRecord;
			var totalPage=rawData.totalPage;
			var pageSize=rawData.pageSize;
			var executeResult=rawData["list"];
			for(var i=0;i<executeResult.length;i++){
				var	tableContents='<tr>'+
						'<td>'+executeResult[i]["WorkShopNo"]+'</td>'+
						'<td>'+executeResult[i]["LineNo"]+'</td>'+
						'<td>'+executeResult[i]["Depid"]+'</td>'+
						'<td><input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td>';
					   tableContents+='</tr>';
						/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
						$('#LineNoByDepidTable tbody').append(tableContents);
			}
			refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
			

			$('.deleteBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var WorkShopNo=$(parentElement).find('td').eq(0).text();
				var LineNo=$(parentElement).find('td').eq(1).text();
				var Depid=$(parentElement).find('td').eq(2).text();
				//alert("卡号"+deleteCardId);
				var results=confirm("確定刪除此條數據?");
				if(results==true){
					$.ajax({
						type:'GET',
						url:'../LineNoByDepid/deleteLineNoByDepid.do',
						data:{
							WorkShopNo:WorkShopNo,
							LineNo:LineNo,
							Depid:Depid
						},
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
									  
									ShowLineNoByDepid();
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
			$('#LineNoByDepidPagination').empty();
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
			
			$('#LineNoByDepidPagination').append(paginationElement);
			
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
				url:'../LineNoByDepid/LineNoByDepid.show',
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
								ShowAllListTable(rawData);
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
})