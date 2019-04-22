$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null,isUserNameValid=false;
	var reg = new RegExp("^[0-9]{10}$");
	ShowAllPersonList();
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

	//全選20條
	$('#AllCheck').click(function(){
		 var checkALL = document.getElementById("AllCheck");
	      var items = $("#Personbinding .spTable").find('input');
	      checkAllBox(checkALL,items); 
	})
	//更改綁定離崗卡
	$('#changeOTWorkshop').click(function(){
		button_onclick($('#changeOTWorkshop')[0]);
		var OTCardNo = $('#ChangeOTCard').val();
		var WorkShop=$('#ChangeWorkShop option:selected').val();
		var WorkShopb=$('#ChangeWorkShop').val();
		var Emp=[];
		$("#Personbinding .spTable").find('input:checked').each(function (index, item) {
		     $(this).each(function () {
			      var empNo = $(this).val();
			      //td里的内容
			      var Empid = new Object();
			      Empid.Dmp_id = empNo;
			      Empid.D_CardID = OTCardNo;
			      Empid.Default_WorkShop= WorkShop;
			      Emp.push(Empid);     
		     })
		})
		
			if(OTCardNo!=null&&OTCardNo!=""){
				if(reg.test(OTCardNo)){
					if(WorkShopb!=null&&WorkShopb!=""){
						if(Emp.length>0){
							getToPerson(Emp)
						}else{
							alert('未選擇員工信息!');
						}
					}else{
						alert('默認使用車間不能爲空!')
					}
				}else{
					alert('離崗卡號不符合規格！必須是10位數')
				}
			}else{
				alert('離崗卡號不能為空！')
			}
	})
	
	$('#relieveBdOTCard').click(function(){
		button_onclick($('#relieveBdOTCard')[0]);
//		console.log($('#relieveBdOTCard')[0]);
		var Emp=[];
		$("#Personbinding .spTable").find('input:checked').each(function (index, item) {
		    var tdDom = $(item).parent().parent().children();
		    var EmpNo = tdDom.eq(1).text();
		    var OTCardNo = tdDom.eq(3).text();
		    var Default_WorkShopNo = tdDom.eq(4).text();
		    var Empid = new Object();
		      Empid.Dmp_id = EmpNo;
		      Empid.D_CardID = OTCardNo;
		      Empid.Default_WorkShop= Default_WorkShopNo;
		      Emp.push(Empid);     
		   /* console.log(EmpNo,OTCardNo,Default_WorkShopNo);*/
		})
/*		console.log(Emp);*/
		if(Emp.length>0){
			relieveOTCard(Emp);
		}else{
			alert("未選擇解綁的員工信息！解綁失敗！");
		}
		
	})
	
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
	
	//離崗卡綁定工號
	$('#changebdOT').click(function(){
		button_onclick($('#changebdOT')[0])
		var user={},errorMessage='';
		user["Dmp_id"]=$('#inputUserName').val();
		user["D_CardID"]=$('#inputOTCarid').val();
		user["Default_WorkShop"]=$('#workShop option:selected').val();
		console.log(user);
		
		if(user["Dmp_id"]==="null" || user["Dmp_id"]=='')
			errorMessage+='工號未填寫\n';
		
		checkUserNameDuplicate(user["Dmp_id"]);
		
		if(user["D_CardID"]=='' || user["D_CardID"]==null){
			errorMessage+='離崗卡號必填 \n';
		}
		if(!reg.test(user["D_CardID"])){
			errorMessage+='離崗卡號不符合規格！必須是10位數\n';
		}
		if(user["Default_WorkShop"]==="null" || user["Default_WorkShop"]=='')
			errorMessage+='未選擇使用的車間\n';
		
		if(errorMessage=='' && isUserNameValid){
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
							 $('#inputUserName').val('');
							 $('#inputOTCarid').val('');
							 alert(data.Message);
//							 $('#changebdOT').prop("disabled",false);
							 ShowAllPersonList();
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
		    $('#inputUserName').val('');
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
	//顯示綁定離崗卡的工號
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
							alert('暫無離崗卡與人員綁定資料');
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
			var	tableContents='<tr><td style="padding: 5px 0px;"><input type="checkbox" value='+executeResult[i]["Dmp_id"]+' style="width:100%; height:15px"></td>'+
					'<td>'+executeResult[i]["Dmp_id"]+'</td>'+
					'<td>'+executeResult[i]["name"]+'</td>'+
					'<td>'+executeResult[i]["D_CardID"]+'</td>'+
					'<td>'+executeResult[i]["Default_WorkShop"]+'</td>'
					var enabled =executeResult[i].Enable=="Y"?'已生效':'';		
					tableContents+='<td>'+enabled+'</td>'
				tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#Personbinding tbody').append(tableContents);
		}
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
	/*	console.log(currentPage);
		console.log(totalRecord);
		console.log(totalPage);
		console.log(pageSize);*/
		/*goPageY(currentPage,totalRecord,totalPage,pageSize);*/
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
})