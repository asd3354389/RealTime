$(document).ready(function(){
		var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
		//var reg = new RegExp("^[0-9]{4}$");
		ShowAllPersonList();
		$("#setEmpPrivilege").click(function(){
			/*$('#setCostWorkShop').attr("disabled",true);*/
			var id=$('#id').val();
			//var lineNo=line.split(",");
			var privilege=$('#privilege').val();
//			console.log(privilege);
			var list=[],errorMessage='';
			//console.log(id);
			if(privilege==null||privilege==""){
				errorMessage+='權限選項不能爲空\n';
			}if(id==null||id==""){
				errorMessage+='工號不能为空\n';
			}
			

			if(errorMessage==''){
				//新增賬號
				$.ajax({
					type:'POST',
					url:'../EmpPrivilege/AddEmpPrivilege.do',
					data:{id:id,privilege:privilege},
					success:function(data){
						if(data!=null&&data!=''){
							ShowAllPersonList();
							alert(data.Message);
							$('#id').val('');

						}
						
					},
					error:function(e){
						alert('新設車間員工權限發生錯誤');
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
									dltr.id = child.eq(0).text();
									dltr.privilege_Level = child.eq(1).text();
									relist.push(dltr);
				})
			//	console.log(relist);
				var results=confirm("確定刪除表格内的"+size+"條綁定訊息 ?");
				if(results==true){
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../EmpPrivilege/RlEmpPrivilege',
						data:JSON.stringify(relist),
						dataType:'json',
						error:function(e){
							alert(e);
						},
						success:function(data){
							 if(data!=null && data!=''){
								 if(data.StatusCode=="200"){					 
									 ShowAllPersonList();
									 alert(data.Message);
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

		$('#searchExceCostBtn').click(function(){
			curPage = 1;
			var queryCritirea=$('#queryCritirea option:selected').val();
			var queryParam=$('#changeWorkShopNo').val();		
			if(queryParam==""){
				ShowAllPersonList();
			}else{
			
				getPersonList(curPage,queryCritirea,queryParam);
				
			}
		})

		$('#lineNo').selectpicker({
			  'selectedText': 'cat'
				 // size: 6
		 });
		
		$('#workShopNo').selectpicker({
			  'selectedText': 'cat'
				 // size: 6
		 });
		
		$('#workShopNo').change(function(){
			var WorkShopNo=$('#workShopNo').val();

				ShowLineNo(WorkShopNo);
			
		})

		$('.selectpicker').selectpicker('val', 'Mustard');  
		  
		
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
		
		function ShowAllPersonList(){
			$.ajax({
				type:'GET',
				url:'../EmpPrivilege/EmpPrivilegeList.show',
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
								ShowAllExceListTable(rawData);
							
							else{
								ShowAllExceListTable(rawData);
								setTimeout(function(){ alert('找不到車間員工權限資料！'); }, 100);			
//								
							}
						}
					}
				}
			});	
		}
		
		function ShowAllExceListTable(rawData){
			$('#Personbinding tbody').empty();
			var currentPage=rawData.currentPage;
			var totalRecord=rawData.totalRecord;
			var totalPage=rawData.totalPage;
			var pageSize=rawData.pageSize;
			var executeResult=rawData["list"];
			for(var i=0;i<executeResult.length;i++){
				var status = ChinaStatus(executeResult[i]["privilege_Level"]);
				var	tableContents='<tr>'+
						'<td class="touch">'+executeResult[i]["id"]+'</td>'+
						'<td>'+status+'</td>';
//						'<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link"></td>';
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
				
				var CostNo=$(parentElement).find('td').eq(1).text();
				$(parentElement).find('td').eq(1).html('<input type="text" class="changeCostNo input-small" maxlength="4" value="'+CostNo+'">');
//				$(parentElement).find('td').eq(2).html('<textarea class="input-small changeWorkShop_Desc" id="message-text" value="'+WorkShop_Desc+'"></textarea>');
				
//				$(parentElement).children().find('.editBtn .deleteBtn').hide();
				$(parentElement).find('td').eq(3).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
		        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
				$(parentElement).find('.editBtn,.deleteBtn').hide();
	     
				$('.confirmBtn').click(function(){
					var parentElement=$(this).parent().parent();
					var User=new Object(),errorMessage='';
//					var Direction=$(parentElement).find('.changeStatus option:selected').eq(0).text();
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
				url:'../EmpPrivilege/EmpPrivilegeList.show',
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
								ShowAllExceListTable(rawData);
								$('#queryParam').val('');
							}
							else{
							
								alert('找不到資料');
							}	
						}
					}
				}
			});
		}
		
		
		
		//判斷是否存在此費用代碼
		 function checkWorkShopStatus(WorkShopNo,LineNo){
//			 alert(1);
				if(WorkShopNo!=""&&LineNo!=""){
					$.ajax({
						type:'POST',
						url:'../WorkShopStatus/checkWorkShopStatud.do',
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
//									 alert(data.Message);
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
		 
		 function checkWorkShopCost(CostId,WorkShopNo){
//			 alert(1);
				if(CostId!=""&&WorkShopNo!=""){
					$.ajax({
						type:'POST',
						url:'../ExceptionCost/checkWorkShopCost.do',
						data:{
							CostId:CostId,
							WorkShopNo:WorkShopNo
						},
						async:false,
						error:function(e){
							alert(e);
						},
						success:function(data){	
							 if(data!=null && data!=''){
								 if(data.StatusCode==200){
//									 alert(data.Message);
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
		 
		 function ChinaStatus(status){
			 var x ;
				if(status==1){
					x='線長';
				}else if(status==2){
					x='機修';
				}else if(status==3){
					x='QC';
				}
			return x;
		 }
		 
})