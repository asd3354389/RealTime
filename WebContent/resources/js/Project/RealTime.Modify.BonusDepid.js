$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	var reg = new RegExp("^[0-9]{4}$");
	ShowAllBonusList();

	$("#setBonusDepid").click(function(){
		var Deptid=$('#Deptid').val();
		var Bonus_Rule=$('#Bonus_Rule').val();
		var errorMessage='';
		//console.log(CostNo.length);
		if(Deptid==null||Deptid==''){
			errorMessage+='組別代码不能为空\n';
		}
		checkBonusByDeptid(Deptid)
		if(errorMessage==''&&isUserNameValid){
			//新增賬號
			$.ajax({
				type:'POST',
				url:'../AdminBonusDepid/AddBonusDeptid.do',
				data:{			
					Deptid:Deptid,
					Bonus_Rule:Bonus_Rule
				},
				success:function(data){
					if(data!=null&&data!=''){
						ShowAllBonusList();
						alert(data.Message);
					}					
				},
				error:function(e){
					alert('新增頂崗津貼信息發生錯誤');
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
								dltr.Deptid = child.eq(0).text();
								dltr.Bonus_Rule = child.eq(1).text();
								relist.push(dltr);
			})
			//console.log(relist);
			var results=confirm("確定刪除表格内的"+size+"條綁定訊息 ?");
			if(results==true){
				$.ajax({
					type:'POST',
					contentType: "application/json",
					url:'../AdminBonusDepid/RelieveBonusDeptid',
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
								 ShowAllBonusList();
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

	$('#searchBtn').click(function(){
		curPage = 1;
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam;
		if(queryCritirea=="Deptid"){
			queryParam=$('#queryParam').val();
		}			
		if(queryParam==""){
			ShowAllBonusList();
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
						 $('#changeWorkShopNo').append(htmlAppender);
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
	
	function ShowAllBonusList(){
		$.ajax({
			type:'GET',
			url:'../AdminBonusDepid/ShowBonusDepidList',
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
							ShowAllBonusListTable(rawData);
						
						else{
							/*$('.left').css('height','727px');*/
							/*ShowAllPersonListTable(rawData);*/
							ShowAllBonusListTable(rawData);
							setTimeout(function(){ alert('找不到頂崗津貼信息資料！'); }, 100);			
//							setTimeout(function(){ alert('暫無離崗卡與費用代碼綁定資料'); }, 100);					
						}
					}
				}
			}
		});	
	}
	
	function ShowAllBonusListTable(rawData){
		$('#Personbinding tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr>'+
					'<td class="touch">'+executeResult[i]["Deptid"]+'</td>'+
					'<td>'+executeResult[i]["Bonus_Rule"]+'</td>'
					var allowed ="";
					if(executeResult[i].Modify_Allowed=='Y'){
						allowed="允许"
					}else if(executeResult[i].Modify_Allowed=='N'){
						allowed="不允许"
					}else{
						allowed=executeResult[i].Modify_Allowed;
					}
					tableContents+='<td>'+allowed+'</td>'+
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
			var Deptid=$(parentElement).find('td').eq(0).text();
			/*$(parentElement).find('td').eq(0).html('<select class="changeWorkShopNo input-small"></select>');
			
			ShowWorkShopNo('changeWorkShopNo');
			
			$(parentElement).find('td .changeWorkShopNo option').each(function(){
				if($(this).val()==WorkShopNo){
					$(this).prop('selected',true);
				}
			});*/
			
			var Bonus_Rule=$(parentElement).find('td').eq(1).text();
			var Modify_Allowed = $(parentElement).find('td').eq(2).text();
			var htmlDom = "";
			if(Modify_Allowed=="允许"){
				htmlDom+='<select class="changeAllowed input-small"><option selected value="Y">Y (允许)</option><option value="N">N (不允许)</option></select>'
			}else if(Modify_Allowed=="不允许"){
				htmlDom+='<select class="changeAllowed input-small"><option value="Y">Y (允许)</option><option selected value="N">N (不允许)</option></select>'
			}
			$(parentElement).find('td').eq(2).html(htmlDom);
;
			$(parentElement).find('td').eq(3).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
     
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var BonusDeptid=new Object(),errorMessage='';
				BonusDeptid.Deptid=Deptid;	
				BonusDeptid.Bonus_Rule = Bonus_Rule;
				BonusDeptid.Modify_Allowed =$(parentElement).find('td .changeAllowed').val();
//				console.log(BonusDeptid.Modify_Allowed);
			
//				checkWorkShopCost(User.CostId,User.O_WorkShopNo);

					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../AdminBonusDepid//UpdateBonusAllowed',
						data:JSON.stringify(BonusDeptid),
						dataType:'json',
						error:function(e){
							alert(e);
							},
						success:function(data){
							  if(data!=null && data!=''){
								  if(data.StatusCode=="200"){
									  ShowAllBonusList();
									  alert(data.Message);
								  }
								  else{
									  alert(data.Message);
								  }
							  }else{
								  alert('操作失敗！')
							  }
							}
					});
				});
			
			$('.cancelBtn').click(function(){
				var parentElement=$(this).parent().parent();
				$(parentElement).find('.editBtn').show();
				$(parentElement).find('td').eq(0).html(Deptid);
				$(parentElement).find('td').eq(1).html(Bonus_Rule);
				$(parentElement).find('td').eq(2).html(Modify_Allowed);
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
			url:'../AdminBonusDepid/ShowBonusDepidList',
			data:{curPage:curPage,queryCritirea:queryCritirea,queryParam:queryParam},
			error:function(e){
				alert('找不到資料');
			},
			success:function(rawData){
				//console.log(rawData);
				if (rawData != null && rawData != "") {
					var executeResult=rawData["list"];
					var errorResponse=executeResult.ErrorMessage;
					if(errorResponse!=null){
						alert('找不到資料');
					}
					else{
						var numOfRecords=executeResult.length;
						if(numOfRecords>0){
							ShowAllBonusListTable(rawData);	
						}
						else{
						
							alert('找不到資料');
						}	
					}
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
	//判斷是否存在此費用代碼
	 function checkCostIdDuplicate(CostId){
//		 alert(1);
			if(CostId!=""){
				$.ajax({
					type:'POST',
					url:'../ExceptionCost/checkExceCost.do',
					data:{
						CostId:CostId
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
	 
	 function checkBonusByDeptid(Deptid){
			if(Deptid!=""){
				$.ajax({
					type:'POST',
					url:'../AdminBonusDepid/checkBonusByDeptid.do',
					data:{
						Deptid:Deptid,
					},
					async:false,
					error:function(e){
						alert(e);
					},
					success:function(data){	
						 if(data!=null && data!=''){
							 if(data.StatusCode==200){
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