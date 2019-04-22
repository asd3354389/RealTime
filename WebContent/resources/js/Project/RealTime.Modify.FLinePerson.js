$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null;
	ShowAllFLPersonListY();
	ShowAllFLPersonListN();	
	
	$('#searchPersonListBtn').click(function(){
		curPage = 1;
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
	
		if(queryParam==""){
			ShowAllFLPersonListY();
			ShowAllFLPersonListN();
		}else{
			/*searchPersonList(curPage,queryCritirea,queryParam);
			queryParam=='';*/
			getPersonListY(curPage,queryCritirea,queryParam);
			getPersonListN(curPage,queryCritirea,queryParam);
		}
			/*ShowAllFLPersonListN(curPage,queryCritirea,queryParam);*/

	});
	
	$('#AllCheckY').click(function(){
		 var checkALL = document.getElementById("AllCheckY");
	      var items = $("#FLinePersonMtY .spTable").find('input');
	      checkAllBox(checkALL,items); 
	})
	
	$('#AllCheckN').click(function(){
		 var checkALL = document.getElementById("AllCheckN");
		 var items = $("#FLinePersonMtN .spTable").find('input');
		 checkAllBox(checkALL,items); 
	})
	
	$(".getAllToN").click(function(){
		var status = "N";
		var html = $('#queryCritirea option:selected').text();
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		if(queryParam==""){
			if(confirm("是否要將所有人員隨綫狀態進行更換")) {
				getAllToPerson(status);
    	    }
		}else{
			if(confirm("是否要將 "+html+":"+queryParam+"人員隨綫狀態進行更換")) {
				getAllToPersonCondition(status,queryCritirea,queryParam);
    	    }
		}
	})
	$(".getAllToY").click(function(){
		var status = "Y";
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		if(queryParam==""){
			if(confirm("是否要將所有人員隨綫狀態進行更換")) {
				getAllToPerson(status);
    	    }
		}else{
			if(confirm("是否要將 "+html+":"+queryParam+"人員隨綫狀態進行更換")) {
				getAllToPersonCondition(status,queryCritirea,queryParam);
    	    }
		}
	})
		
	function getAllToPerson(status){
		$.ajax({
			type:'POST',
			url:'../FlinePerson/getAllPerson',
			data:{status:status},
			error:function(e){
					alert(e);
				},
			success:function(data){
				  if(data!=null && data!=''){
					  if(data.StatusCode=="200"){
						 /* ShowAllFLPersonListY(1);
						  ShowAllFLPersonListN(1);*/
						  alert(data.Message);
						  location.reload();	 
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
	
	function getAllToPersonCondition(status,queryCritirea,queryParam){
		$.ajax({
			type:'POST',
			url:'../FlinePerson/getAllPersonCondition',
			data:{status:status,queryCritirea:queryCritirea,queryParam:queryParam},
			error:function(e){
					alert(3);
				},
			success:function(data){
				  if(data!=null && data!=''){
					  if(data.StatusCode=="200"){
						  alert(data.Message);
						  location.reload();	 
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
	/*function cancelAllCheck(checkALL){
	 $("#FLinePersonMtN .spTable").find('input').click(function(){
		 
	 })
		for(var i=0;i<items.length;i++)
        {
            var box=items.get(i);
            var status =box.getAttribute("checked");
            if(status){
            	checkALL.removeAttribute("checked");
            }
        }
	}
	*/
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
	
	function ShowAllFLPersonListY(){
		$.ajax({
			type:'GET',
			url:'../FlinePerson/ShowPersonListY',
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
							ShowAllFLPersonListTableY(rawData);
						else{
							/*$('.left').css('height','727px');*/
							alert('暫無隨綫人員資料');
						}
					}
				}
			}
		});	
	}
	
	function ShowAllFLPersonListN(){
		$.ajax({
			type:'GET',
			url:'../FlinePerson/ShowPersonListN',
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
							ShowAllFLPersonListTableN(rawData);
						else{
							/*$('.right').css('height','727px');*/
							alert('暫無不隨綫人員資料');
						}	
					}
				}
			}
		});	
	}
	
	function ShowAllFLPersonListTableY(rawData){
		$('#FLinePersonMtY tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td style="padding: 5px 0px;"><input type="checkbox" value='+executeResult[i]["EmpNo"]+' style="width:100%; height:15px"></td>'+
					'<td>'+executeResult[i]["EmpNo"]+'</td>'+
					'<td>'+executeResult[i]["EmpName"]+'</td>'+
					'<td>'+executeResult[i]["DeptNo"]+'</td>'+
					'<td>'+executeResult[i]["CostID"]+'</td>'
					var Line_Personnel =executeResult[i].Line_Personnel=="Y"?'隨綫':'';		
				tableContents+='<td>'+Line_Personnel+'</td>'
//					'<td>'+executeResult[i]["Line_Personnel"]+'</td>';
				tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#FLinePersonMtY tbody').append(tableContents);
		}
		refreshUserInfoPaginationY(currentPage,totalRecord,totalPage,pageSize);
	/*	console.log(currentPage);
		console.log(totalRecord);
		console.log(totalPage);
		console.log(pageSize);*/
		/*goPageY(currentPage,totalRecord,totalPage,pageSize);*/
	}
	
	function ShowAllFLPersonListTableN(rawData){
		$('#FLinePersonMtN tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td style="padding: 5px 0px;"><input type="checkbox" value='+executeResult[i]["EmpNo"]+' style="width:100%; height:15px"></td>'+
					'<td>'+executeResult[i]["EmpNo"]+'</td>'+
					'<td>'+executeResult[i]["EmpName"]+'</td>'+
					'<td>'+executeResult[i]["DeptNo"]+'</td>'+
					'<td>'+executeResult[i]["CostID"]+'</td>'
				var Line_Personnel =executeResult[i].Line_Personnel=="N"?'非隨綫':'';		
				tableContents+='<td>'+Line_Personnel+'</td>'
//					'<td>'+executeResult[i]["Line_Personnel"]+'</td>';
				tableContents+='</tr>';
					/*tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';*/
					$('#FLinePersonMtN tbody').append(tableContents);
		}
		refreshUserInfoPaginationN(currentPage,totalRecord,totalPage,pageSize);
		/*goPageN(currentPage,totalRecord,totalPage,pageSize);*/
	}
	
	function refreshUserInfoPaginationY(currentPage,totalRecord,totalPage,pageSize){
		$('#PersonListPaginationY').empty();
		var FLPerson = 'Y';
		var paginationElement='頁次：'+currentPage+'/'+totalPage +'&nbsp;每页:&nbsp;'+pageSize+'&nbsp;共&nbsp;'+totalRecord+'&nbsp;條&nbsp;';
		if(currentPage==1)
			paginationElement+='<a href ="javascript:return false;">首页</a>';		  
		else
			paginationElement+='<a class="firstPageY">首页</a>';

		if(currentPage>1)
			paginationElement+='&nbsp;<a class="previousPageY">上一頁</a>';
		else
			paginationElement+='&nbsp;<a href ="javascript:return false;">上一頁</a>';
		
	   /* for(var i=1;i <= totalPage;i++){
	    	paginationElement+='&nbsp;<a class="numPage">' + i +'</a>&nbsp;';	    	
	    }*/
		if(currentPage<totalPage)
			paginationElement+='<a class="nextPageY">下一頁</a>';
		else
			paginationElement+='<a href ="javascript:return false;">下一頁</a>';
		
		$('#PersonListPaginationY').append(paginationElement);
		
		$('.firstPageY').click(function(){
			curPage=1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getPersonListY(curPage,select,text);
			}else{
				getPersonListY(curPage,queryCritirea,queryParam);				
			}	
		});
		
		$('.previousPageY').click(function(){
			curPage=currentPage-1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getPersonListY(curPage,select,text);
			}else{
				getPersonListY(curPage,queryCritirea,queryParam);				
			}		
		});	
		
		$('.nextPageY').click(function(){
			curPage=currentPage+1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				console.log(123);
				console.log(text);
				getPersonListY(curPage,select,text);
			}else{
				getPersonListY(curPage,queryCritirea,queryParam);				
			}				
		});	
		
		$('.numPageY').click(function(){
			var curPage=$(this).text();
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getPersonListY(curPage,select,text);
			}else{
				getPersonListY(curPage,queryCritirea,queryParam);				
			}		
    	});
		
	}
	
	function refreshUserInfoPaginationN(currentPage,totalRecord,totalPage,pageSize){
		$('#PersonListPaginationN').empty();
		var FLPerson='N';
		var paginationElement='頁次：'+currentPage+'/'+totalPage +'&nbsp;每页:&nbsp;'+pageSize+'&nbsp;共&nbsp;'+totalRecord+'&nbsp;條&nbsp;';
		if(currentPage==1)
			paginationElement+='<a href ="javascript:return false;">首页</a>';		  
		else
			paginationElement+='<a class="firstPageN">首页</a>';

		if(currentPage>1)
			paginationElement+='&nbsp;<a class="previousPageN">上一頁</a>';
		else
			paginationElement+='&nbsp;<a href ="javascript:return false;">上一頁</a>';
		
	    /*for(var i=1;i <= totalPage;i++){
	    	paginationElement+='&nbsp;<a class="numPage">' + i +'</a>&nbsp;';	    	
	    }*/
		if(currentPage<totalPage)
			paginationElement+='<a class="nextPageN">下一頁</a>';
		else
			paginationElement+='<a href ="javascript:return false;">下一頁</a>';
		
		$('#PersonListPaginationN').append(paginationElement);
		
	
		$('.firstPageN').click(function(){
			curPage=1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getPersonListN(curPage,select,text);
			}else{
				getPersonListN(curPage,queryCritirea,queryParam);				
			}			
		});
		
		$('.previousPageN').click(function(){
			curPage=currentPage-1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getPersonListN(curPage,select,text);
			}else{
				getPersonListN(curPage,queryCritirea,queryParam);				
			}					
		});	
		
		$('.nextPageN').click(function(){
			curPage=currentPage+1;
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getPersonListN(curPage,select,text);
			}else{
				getPersonListN(curPage,queryCritirea,queryParam);				
			}				
		});	
		
		$('.numPageN').click(function(){
			var curPage=$(this).text();
			if($('#queryParam').val()!=null){
				var text =$('#queryParam').val();
				var select = $('#queryCritirea option:selected').val();
				getPersonListN(curPage,select,text);
			}else{
				getPersonListN(curPage,queryCritirea,queryParam);				
			}				
    	});

	}
	
	//分頁顯示
	function getPersonListY(curPage,queryCritirea,queryParam){
			$.ajax({
				type:'GET',
				url:'../FlinePerson/ShowPersonListY',
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
								ShowAllFLPersonListTableY(rawData);
							else{
								var currentPage=rawData.currentPage;
								console.log();
								var totalRecord=rawData.totalRecord;
								console.log(totalRecord);
								var totalPage=rawData.totalPage;
								var pageSize=rawData.pageSize;
								$('#FLinePersonMtY tbody').empty()
								$('.left').css('height','727px');
								refreshUserInfoPaginationY(currentPage,totalRecord,totalPage,pageSize);
							}	
						}
					}
				}
			});
	}
	function getPersonListN(curPage,queryCritirea,queryParam){
			$.ajax({
				type:'GET',
				url:'../FlinePerson/ShowPersonListN',
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
								ShowAllFLPersonListTableN(rawData);
							}
							else{
								var currentPage=rawData.currentPage;
								var totalRecord=rawData.totalRecord;
								var totalPage=rawData.totalPage;
								var pageSize=rawData.pageSize;
								$('#FLinePersonMtN tbody').empty()
								$('.right').css('height','727px');
								refreshUserInfoPaginationN(currentPage,totalRecord,totalPage,pageSize);
							}	
						}
					}
				}
			});	
		}/*else{
			$.ajax({
				type:'GET',
				url:'../FlinePerson/ShowPersonListY',
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
								ShowAllFLPersonListTableY(rawData);
							else{
								$('.left').css('height','727px');
								alert('此隨綫人員狀態為:N!');
						
							}
						}
					}
				}
			});	
		}
	}*/
	
	/*function searchPersonList(curPage,queryCritirea,queryParam){
		$.ajax({
			type:'GET',
			url:'../FlinePerson/ShowPersonList',
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
						if(executeResult.length>0){
							var status=executeResult[0]["Line_Personnel"];
							if(status=="Y"){
								ShowAllFLPersonListTableY(rawData);
							}else if (status=="N") {
								ShowAllFLPersonListTableN(rawData);
							}else{
								alert('找不到資料');
							}
						}else{
							alert('找不到資料');
						}	
					}
				}
			}
		});	
	}*/
	
	/*function goPageN(currentPage,totalRecord,totalPage,pageSize){
		  var itable = document.getElementById("FLinePersonMtN");
		  	var startRow = (currentPage - 1) * pageSize+1;//开始显示的行 31
		    var endRow = currentPage * pageSize;//结束显示的行  40
		    endRow = (endRow > totalRecord)? totalRecord : endRow;  //40
		    //遍历显示数据实现分页
		  for(var i=1;i<=totalRecord;i++){
			  var irow = itable.rows[i];
		    if(i>=startRow && i<=endRow){
		      irow.style.display = "table-row";	
		    }else{
		      irow.style.display = "none";
		    
		    }
		  }
		  var paginationElement='頁次：'+currentPage+'/'+totalPage +'&nbsp;每页:&nbsp;'+pageSize+'&nbsp;共&nbsp;'+totalRecord+'&nbsp;條&nbsp;';

			if(currentPage==1)
				paginationElement+='<a href ="javascript:return false;">首页</a>';		  
			else
				paginationElement+='<a class="firstPageN">首页</a>';

			if(currentPage>1)
				paginationElement+='&nbsp;<a class="previousPageN">上一頁</a>';
			else
				paginationElement+='&nbsp;<a href ="javascript:return false;">上一頁</a>';
			
		    for(var i=1;i <= totalPage;i++){
		    	paginationElement+='&nbsp;<a class="numPage">' + i +'</a>&nbsp;';	    	
		    }
			if(currentPage<totalPage)
				paginationElement+='<a class="nextPageN">下一頁</a>';
			else
				paginationElement+='<a href ="javascript:return false;">下一頁</a>';
			
			$('#PersonListPaginationN').empty();
			$('#PersonListPaginationN').append(paginationElement);
			
			$('.firstPageN').click(function(){
				curPage=1;
				goPageN(curPage,totalRecord,totalPage,pageSize);				
			});
			
			$('.previousPageN').click(function(){
				curPage=currentPage-1;
				goPageN(curPage,totalRecord,totalPage,pageSize);				
			});	
			
			$('.nextPageN').click(function(){
				curPage=currentPage+1;
				goPageN(curPage,totalRecord,totalPage,pageSize);				
			});	
			
			$('.numPageN').click(function(){
				var curPage=$(this).text();
				goPageN(curPage,totalRecord,totalPage,pageSize);				
	    	});
		  document.getElementById("barcon").innerHTML = tempStr;
		}
	
	function goPageY(currentPage,totalRecord,totalPage,pageSize){
		
		  var itable = document.getElementById("FLinePersonMtY");
	  	var startRow = (currentPage - 1) * pageSize+1;//开始显示的行 31
		    var endRow = currentPage * pageSize;//结束显示的行  40
		    endRow = (endRow > totalRecord)? totalRecord : endRow;  //40
		    //遍历显示数据实现分页
		  for(var i=1;i<=totalRecord;i++){
			  var irow = itable.rows[i];
		    if(i>=startRow && i<=endRow){	
		      irow.style.display = "table-row";
		    }else{
		      irow.style.display = "none";
		    }
		  }
		  var paginationElement='頁次：'+currentPage+'/'+totalPage +'&nbsp;每页:&nbsp;'+pageSize+'&nbsp;共&nbsp;'+totalRecord+'&nbsp;條&nbsp;';
			if(currentPage==1)
				paginationElement+='<a href ="javascript:return false;">首页</a>';		  
			else
				paginationElement+='<a class="firstPageY">首页</a>';

			if(currentPage>1)
				paginationElement+='&nbsp;<a class="previousPageY">上一頁</a>';
			else
				paginationElement+='&nbsp;<a href ="javascript:return false;">上一頁</a>';
			
		    for(var i=1;i <= totalPage;i++){
		    	paginationElement+='&nbsp;<a class="numPage">' + i +'</a>&nbsp;';	    	
		    }
			if(currentPage<totalPage)
				paginationElement+='<a class="nextPageY">下一頁</a>';
			else
				paginationElement+='<a href ="javascript:return false;">下一頁</a>';
			$('#PersonListPaginationY').empty();
			$('#PersonListPaginationY').append(paginationElement);
			
			$('.firstPageY').click(function(){
				curPage=1;
				goPageY(curPage,totalRecord,totalPage,pageSize);				
			});
			
			$('.previousPageY').click(function(){
				curPage=currentPage-1;
				goPageY(curPage,totalRecord,totalPage,pageSize);				
			});	
			
			$('.nextPageY').click(function(){
				curPage=currentPage+1;
				goPageY(curPage,totalRecord,totalPage,pageSize);				
			});	
			
			$('.numPageY').click(function(){
				var curPage=$(this).text();
				goPageY(curPage,totalRecord,totalPage,pageSize);				
	    	});
		  document.getElementById("barcon").innerHTML = tempStr;
		}*/
	$('.getToN').click(function(){		
		var Emp=[];
		
		$("#FLinePersonMtY .spTable").find('input:checked').each(function (index, item) {
		     $(this).each(function () {
			      var empNo = $(this).val();
			      //td里的内容
			      var Empid = new Object();
			      Empid.EmpNo = empNo;
			      Empid.Line_Personnel = 'N';
			      Emp.push(Empid);
			      
		     })
		})
		if(Emp.length>0){
			getToPerson(Emp);
		}else {
			alert("未選中需要修改隨綫狀態人員信息");
		}	
	})
	
	$('.getToY').click(function(){
		/*var Empid=$('#FLinePersonMtY input:checked').val();	
		var Empline_personnel='N';*/
		
		var Emp=[];
		
		$("#FLinePersonMtN .spTable").find('input:checked').each(function (index, item) {
		     $(this).each(function () {
			      var empNo = $(this).val();
			      //td里的内容
			      var Empid = new Object();
			      Empid.EmpNo = empNo;
			      Empid.Line_Personnel = 'Y';
			      Emp.push(Empid);
			      
		     })
		})
		if(Emp.length>0){
			getToPerson(Emp);
		}else {
			alert("未選中需要修改隨綫狀態人員信息");
		}	
	})
	
	function getToPerson(Emp){
		
		$.ajax({
			type:'POST',
			contentType: "application/json",
			url:'../FlinePerson/getPersonList',
			data:JSON.stringify(Emp),
			dataType:'json',
			async:false,
			error:function(e){
				alert(e);
				},
			success:function(data){
				  if(data!=null && data!=''){
					  if(data.StatusCode=="200"){
						 /* ShowAllFLPersonListY();
						  ShowAllFLPersonListN();*/
						alert(data.Message);
						  location.reload();
						 
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