$(function(){

	//"addMoreDeptIdBtn"
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	//顯示列表的信息
	var messageList;
	var DeptArr;
	ShowCostIdList();
	//ShowAllIpList();
	
	//初始化下拉框
	 $('#MoredeptNo').selectpicker({
	       'selectedText': 'cat'
		 // size: 6
	    });
	 $('.selectpicker').selectpicker('val', 'Mustard');  
///////////////////////////////
		//綁定方法
		$('#changebdOT').click(function(){	
			
			 
 			  
				//alert("點擊綁定");	
			BindingIp();
					
				
		});
		//changebdOTMoreIp
		//點擊綁定多個線組別代碼
		$('#MorechangebdOT').click(function(){	
			
			//獲取輸入ip的大小
			var ipStr = $('#queryParamIp').val();
			var arr= ipStr.split(",");
//			console.log(arr);
			
			
			if (ipStr == '') {
				alert("你輸入的信息不完整,請重新輸入");
			} else {
				var Emp=[];
				var deptidArr;
				$("#DeptNobinding .spTable").find('input:checked').each(function (index, item) {
				     $(this).each(function () {
					      var depid = $(this).val();
					      //td里的内容
					     //depid=depid.join('*');
					      var Empid = new Object();
					      Empid.depid = depid;
					      Emp.push(depid);
					      deptidArr = Emp.join('*');
					      console.log(deptidArr);	
					      
					     
				     })
				})
				if(Emp.length>0){
					if (arr.length==1&&Emp.length>=1) {
						//綁定多個部門代碼
						MoreBindingIp(deptidArr,ipStr);
					} else if(arr.length>=1&&Emp.length==1) {
						//綁定多個Ip
						BindingIpMore(deptidArr,ipStr);
						
					}else{
						alert("多個部門不能同時綁定多個IP");
					}
				}else {
					alert("未勾選需要綁定的部門代碼");
				}	
				
			}
			
			//MoreBindingIp();
							
	});
		//點擊綁定多個Ip
		$('#IpchangebdOT').click(function(){	
			
			BindingIpMore();
							
	});
///////////////////////////////
///////////////////////////////
		//電腦綁定ip
		 $('#CostNo').change(function(){
	    	  var CostId = $("#CostNo").val();
	    	  if(CostId!=""){
	    		  $('#deptNo').empty();
	    		  ShowDepNo(CostId);
	    		 
	    	  }else{
	    		  
	    	  }   	 
	      })
	      //綁定多個線組別代碼
	       $('#MoreCostNo').change(function(){
	    	  var CostId = $("#MoreCostNo").val();
	    	 
	    	  if(CostId!=""){
	    		  $('#MoredeptNo').empty();
	    		  ShowMoreDepNo(CostId);
	    		 
	    	  }else{
	    		  
	    	  }   	 
	      })
	       //綁定多個線Ip
	       $('#MoreIpCost').change(function(){
	    	  var CostId = $("#MoreIpCost").val();
	    	 
	    	  if(CostId!=""){
	    		  $('#MoreIpdept').empty();
	    		  ShowMoreDepNo(CostId);
	    		 
	    	  }else{
	    		  
	    	  }   	 
	      })
	      
      //點擊綁定多個線組別代碼
	//清除
	$('#resetSubmit').click(function(){	
		
		$("#inputIp").val("");
		//$("#inputCostId").val("");
		//$("#inputId").val("");
	
	});
		 
		 $('#MoreresetSubmit').click(function(){	
				
				$("#MoreinputIp").val("");
				
			
			});
		 $('#IpresetSubmit').click(function(){	
				
				$("#inputIpMore").val("");
				
			
			});
///////////////////////////////
	//searchIpListBtn
	$('#searchIpListBtn').click(function(){
		
		//選擇查詢的條件
		var queryCritirea=$('#queryCritirea option:selected').val();
		//輸入的信息
		var queryParam=$('#queryParam').val();
		
		
		if(queryParam==""){
			//ShowAllIpList();
		}else{
			//
			
			getSelectIpList(curPage,queryCritirea,queryParam);
			//$("#queryCritirea").change(function(){
				 console.log($('#queryCritirea').val()); 
				//alert($(this).val());

				//})
				 if ( $('#queryCritirea').val() == "DEPTID") {
					
					 ShowDepNoList(queryParam);
				} 
			
		}
		
		
	});
	
/////////////////////////////////////////////////////////////////////////////////////
	//綁定Ip
	function BindingIp() {
		
		//ip地址
		//10.64.119.3
		//MoreinputIp
		var DeviceIp = $("#inputIp").val();
		//var MoreDeviceIp = $("#MoreinputIp").val();
	
		var DeptId = $('#deptNo option:selected').val(); 
		//var MoreDeptId = $('#MoredeptNo option:selected').val(); 
		if (  DeviceIp == ''  ||DeptId == '') {
			alert("你輸入的信息不完整,請重新輸入!!");
		}else{
			$.ajax({ 
				   url:"../IpBinding/BindingIp", 
				   type:"POST",
				   async: false,
				   data:{"DeviceIp":DeviceIp,"DeptId":DeptId},
				   success:function(result){ 
				    var StatusCode = result.StatusCode;
				    var message = result.message;
				if(StatusCode == "500"){
					alert(message);
				}else if(StatusCode == "200"){
					alert(message);
					$("#inputIp").val("");
					
					
					//$("#inputCostId").val("");	
					//$("#inputId").val("");
					ShowAllIpList();					
				}  
				   }, 
				   error:function(err){ 
				    console.log("NG:"+err); 
				   } 	
			}) 
		}	
}	
	
	
	//綁定多个部门
	function MoreBindingIp(Emp,ipStr) {
		
		
		
//		var MoreDeviceIp = $("#MoreinputIp").val();
//	
//		
//		var MoreDeptId = $('#MoredeptNo').val(); 
//		console.log(MoreDeviceIp);
//		if(MoreDeptId===null){
//			MoreDeptId='';
//		}else{
//			MoreDeptId=MoreDeptId.join('*');
//	}
//		
//		
//		if (MoreDeptId== ''||MoreDeviceIp== '') {
//			alert("你輸入的信息不完整,請重新輸入!!");
//		}else{
			$.ajax({ 
				   url:"../IpBinding/BindingIp", 
				   type:"POST",
				   async: false,
				   data:{"DeviceIp":ipStr,"DeptId":Emp,"Dif":false},
				   success:function(result){ 
				    var StatusCode = result.StatusCode;
				    var message = result.message;
				if(StatusCode == "500"){
					alert(message);
				}else if(StatusCode == "200"){
					alert(message);
					
					//$("#MoreinputIp").val("");
					
					//$("#inputCostId").val("");	
					//$("#inputId").val("");
					//var bindingT = true;
					//ShowAllIpList($('#queryParam').val(),bindingT);		
					//選擇查詢的條件
					var queryCritirea=$('#queryCritirea option:selected').val();
					//輸入的信息
					var queryParam=$('#queryParam').val();
					getSelectIpList(curPage,queryCritirea,queryParam);
				}  
				   }, 
				   error:function(err){ 
					alert("绑定失败")
				   // console.log("NG:"+err); 
				   } 	
			}) 
		//}	
}	
	//綁定多个ip
	function BindingIpMore(Emp,ipStr) {
		
		//ip地址
		//10.64.119.3
		//MoreinputIp
		
//		var MoreDeviceIp = $("#inputIpMore").val();
//	
//		
//		var MoreDeptId = $('#MoreIpdept').val(); 
//		console.log(MoreDeviceIp);
//		if(MoreDeptId===null){
//			MoreDeptId='';
//		}else{
//			MoreDeptId=MoreDeptId.join('*');
//	}
		
		//alert(MoreDeptId);
//		if (MoreDeptId== ''||MoreDeviceIp== '') {
//			alert("你輸入的信息不完整,請重新輸入!!");
//		}else{
			$.ajax({ 
				   url:"../IpBinding/BindingIp", 
				   type:"POST",
				   async: false,
				   data:{"DeviceIp":ipStr,"DeptId":Emp,"Dif":true},
				   success:function(result){ 
				    var StatusCode = result.StatusCode;
				    var message = result.message;
				if(StatusCode == "500"){
					alert(message);
				}else if(StatusCode == "200"){
					alert(message);
					
					//$("#inputIpMore").val("");
					
					//$("#inputCostId").val("");	
					//$("#inputId").val("");
					var queryCritirea=$('#queryCritirea option:selected').val();
					//輸入的信息
					var queryParam=$('#queryParam').val();
					getSelectIpList(curPage,queryCritirea,queryParam);			
				}  
				   }, 
				   error:function(err){ 
				    console.log("NG:"+err); 
				   } 	
			}) 
		//}	
}	
	//顯示綁定的Ip
	function ShowAllIpList(queryParam,Boolean) {

		$.ajax({
			type:'POST',
			url:'../IpBinding/ShowSelectIpList',
			data:{curPage:curPage,queryCritirea:queryCritirea,queryParam:queryParam},
			error:function(e){
				alert(e);
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
							ShowAllIpListTable(rawData);
							//$('#queryParam').val('');
						}
						else{
							
							ShowAllIpListTable(rawData);
							setTimeout(function(){ alert('暫無電腦Ip綁定資料'); }, 100);
							
						}	
					}
				}
			}
		});
	}
	
//	function ShowAllIpListDelete(queryCritirea,queryParam) {
//		
//		$.ajax({
//			type:'POST',
//			url:'../IpBinding/ShowSelectIpList',
//			data:{curPage:curPage,queryCritirea:queryCritirea,queryParam:queryParam},
//			error:function(e){
//				alert(e);
//			},
//			success:function(rawData){	
//				if (rawData != null && rawData != "") {
//					var executeResult=rawData["list"];
//					var errorResponse=executeResult.ErrorMessage;
//					if(errorResponse!=null){
//						alert('找不到資料');
//					}
//					else{
//						var numOfRecords=executeResult.length;
//						if(numOfRecords>0){
//							ShowAllIpListTable(rawData);
//							//$('#queryParam').val('');
//						}
//						else{
//							
//							ShowAllIpListTable(rawData);
//							setTimeout(function(){ alert('暫無信息為'+queryParam+'的綁定資料'); }, 100);
//							
//						}	
//					}
//				}
//			}
//		});
//	}
	//顯示刪除之後的綁定的信息
/////////////////////////////////////////////////////////////////////////////////////
	function ShowAllIp() {
		$.ajax({
			url:"../IpBinding/ShowAllIpList",
			type:"POST",
			async : false,
			success:function(result){ 
				var StatusCode = result.StatusCode;
				var message = result.message;
				if(StatusCode == "500"){
					alert(message);
				}else if(StatusCode == "200"){
					ShowAllIpList(message);
				}
			}, 
			error:function(err){ 
				 alert("NG:"+err+"顯示錯誤"); 
			} 
		})
	}
	
	function ShowAllIpListTable(rawData) {
	
		$('#Personbinding tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		//var obj = JSON.parse(result)
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr>'+
					'<td>'+executeResult[i]["DEVICEIP"]+'</td>'+
					'<td>'+executeResult[i]["DEPTID"]+'</td>';
					//'<td>'+executeResult[i]["UPDATE_USERID"]+'</td>';
//					+
//					'<td>'+obj[i].ENABLED+'</td>';
			var enabled =executeResult[i]["ENABLED"]=="Y"?'已生效':'';		
			tableContents+='<td>'+enabled+'</td>'
		       +'<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link"><input type="button" value="刪除" class="deleteBtn btn btn-xs btn-link"></td>'
				tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#Personbinding tbody').append(tableContents);
		}
		
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
		//點擊編輯按鈕
		
		$(".editBtn").click(function(){
			
			var parentElement = $(this).parent().parent();
		
			//電腦ip
			var DeviceIP = $(parentElement).find('td').eq(0).text();
			//部門代碼
			var DeptId=$(parentElement).find('td').eq(1).text();
			
			//電腦io
			
			//修改部門代碼
			$(parentElement).find('td').eq(1).html('<input type="text" class="changeDeptId input-small" maxlength="60" value="'+DeptId+'">');
			
			//編輯按鈕
			$(parentElement).find('td').eq(3).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
    		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
			
			//點擊編輯按鈕執行的方法
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var Dept_Id='',errorMessage='';
				//新的部門代碼
				Dept_Id=$(parentElement).find('td input:text').eq(0).val();
				if (Dept_Id === "null" || Dept_Id == '') {
					errorMessage += '部門代碼未填寫\n';
				}

				if(errorMessage==''){	
					$.ajax({
						url:'../IpBinding/UpdateBindingIP.do',
						type:'POST',
						async: false,
						data:{"DeviceIp":DeviceIP,"DeptID":Dept_Id,"OldDeptID":DeptId},
						error:function(e){
							alert(e);
							},
						success:function(data){
							  if(data!=null && data!=''){
								  if(data.StatusCode=="200"){
									  alert(data.Message);
									  $(parentElement).find('.editBtn,.deleteBtn').show();									 
									  $(parentElement).find('td').eq(1).html(Dept_Id);								 
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
				$(parentElement).find('td').eq(1).html(DeptId);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})					
	});
	
		$('.deleteBtn').click(function(){
			
			var parentElement=$(this).parent().parent();
			var deleteDeviceip=$(parentElement).find('td').eq(0).text();
			//部門代碼
			var DeptId=$(parentElement).find('td').eq(1).text();
			var results=confirm("確定刪除卡機IP為 "+deleteDeviceip+"線組別代碼為"+DeptId+" 的狀態 ?");
			
			if(results==true){
				$.ajax({
					type:'GET',
					url:'../IpBinding/deleteBindingIP.do',
					data:{Deviceip:deleteDeviceip,DeptId:DeptId},
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
								// $("#Personbinding tbody").html("");
//								var queryCritirea=$('#queryCritirea option:selected').val();
//									//輸入的信息
//								var queryParam=$('#queryParam').val();
								getSelectIpList(curPage,queryCritirea,queryParam);
							 }
							 else{
								 alert(data.Message);
								getSelectIpList(curPage,queryCritirea,queryParam);
							 }
						 }else{
							 alert('操作失敗!')
						 }
					}
				});
			}
		});
	}
	
	//翻頁
	function refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize){
		$('#IpBindingListPagination').empty();
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
		
		$('#IpBindingListPagination').append(paginationElement);
		
		$('.firstPage').click(function(){
			curPage=1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getSelectIpList(curPage,select,text);
			}else{
				getSelectIpList(curPage,queryCritirea,queryParam);				
			}	
		});
		
		$('.previousPage').click(function(){
			curPage=currentPage-1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getSelectIpList(curPage,select,text);
			}else{
				getSelectIpList(curPage,queryCritirea,queryParam);				
			}		
		});	
		
		$('.nextPage').click(function(){
			curPage=currentPage+1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getSelectIpList(curPage,select,text);
			}else{
				getSelectIpList(curPage,queryCritirea,queryParam);				
			}				
		});	
		
		$('.numPage').click(function(){
			var curPage=$(this).text();
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getSelectIpList(curPage,select,text);
			}else{
				getSelectIpList(curPage,queryCritirea,queryParam);				
			}		
    	});
		
	}
	

	
	//獲取按條件查詢的ip信息
	function getSelectIpList(curPage,queryCritirea,queryParam) {
		
		$.ajax({
			type:'POST',
			url:'../IpBinding/ShowSelectIpList',
			data:{curPage:curPage,queryCritirea:queryCritirea,queryParam:queryParam},
			error:function(e){
				alert('無綁定資料');
			},
			success:function(rawData){	
				if (rawData != null && rawData != "") {
					
					var executeResult=rawData["list"];
					var errorResponse=executeResult.ErrorMessage;
					if(errorResponse!=null){
						
						alert('無信息為'+queryParam+'的綁定資料');
						
					}
					else{
						var numOfRecords=executeResult.length;
						if(numOfRecords>0){
							ShowAllIpListTable(rawData);
							console.log(rawData);	
							//$('#queryParam').val('');
						}
						else{
							alert('無綁定資料');
							ShowAllIpListTable(rawData);
						}	
					}
				}
			}
		});
		
	}
	
	//助理部門權責
	
	function ShowCostIdList() {
		
		$.ajax({
			type:'POST',
			url:'../IpBinding/ShowCostNo',
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
					 $('#MoreCostNo').append(htmlAppender);
					 $('#MoreIpCost').append(htmlAppender);
				/*	 $('#ChangeWorkShop').append(htmlAppender);*/
				}
				else{
					alert('無資料');
				}
			 }else{
				alert('無資料');
			 }
			}
		});   
	}
	//================================================
	//顯示查詢的部門代碼
	function ShowDepNo(CostId){
		//alert("部門代碼");
		  $.ajax({
	  			type:'POST',
	  			url:'../IpBinding/ShowDeptNo',
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
	  							// $('#MoredeptNo').append(htmlAppender);
	  				}else{
	  					alert("此費用代碼錯誤或無相應的綫組別號！請重新輸入");
						 // $("#CostNo").val("");
						  //$("#MoreCostNo").val("");
//				    	  $('#inputOTCarid').val("")
	  				}
	  			},
	  			error:function(e){
	  				alert(e);
	  			}
	  		})
	}
	
	function ShowMoreDepNo(CostId){
		//alert("部門代碼");
		  $.ajax({
	  			type:'POST',
	  			url:'../IpBinding/ShowDeptNo',
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
	  							 $('#MoredeptNo').append(htmlAppender);
	  							 $('#MoreIpdept').append(htmlAppender);
	  							 //MoreIpdept
	  							// $('#MoredeptNo').append(htmlAppender);
	  							$("#MoredeptNo").selectpicker("refresh");
	  							$("#MoreIpdept ").selectpicker("refresh");
	  				}else{
	  					alert("此費用代碼錯誤或無相應的綫組別號！請重新輸入");
						  $("#MoreCostNo").val("");
						  $("#MoreIpCost").val("");
						  //$("#MoreCostNo").val("");
//				    	  $('#inputOTCarid').val("")
	  				}
	  			},
	  			error:function(e){
	  				alert(e);
	  			}
	  		})
	}
	
	//顯示部門代碼列表
	function ShowDepNoList(CostId){
		//alert("部門代碼");
		  $.ajax({
	  			type:'POST',
	  			url:'../IpBinding/ShowDeptNo',
	  			data:{CostId:CostId},
//	  			async:false,
	  			success:function(data){
	  				
	  				var StatusCode = data.StatusCode;
	  				var message = data.message;
	  				var htmlAppender='';
	  				 if(StatusCode=="200"){	
	  					var obj=eval(message);
	  					console.log(obj);	
	  					DeptArr = obj;
	  					ShowAllDepNoListTable(obj);
	  				}else{
	  					alert("此費用代碼錯誤或無相應的綫組別號！請重新輸入");
						  //$("#CostNo").val("");
						  //$("#MoreCostNo").val("");
//				    	  $('#inputOTCarid').val("")
	  				}
	  			},
	  			error:function(e){
	  				alert(e);
	  			}
	  		})
	}
	
	function ShowAllDepNoListTable(obj){
		//DeptNobinding
		$('#DeptNobinding tbody').empty();
		for(var i=0;i<obj.length;i++){
			var	tableContents='<tr><td><input type="checkbox" value='+obj[i].depid+' style="float: left;margin-left: 10px"></td>'+
			'<td>'+obj[i].depid+'</td>';
			tableContents+='</tr>';
			
			$('#DeptNobinding tbody').append(tableContents);
			}
		
	}
	//$('#queryParam').val()
	//================================================
});