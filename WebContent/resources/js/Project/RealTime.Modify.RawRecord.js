$(document).ready(function(){
	var curPage=1,empId=null,depId=null,costId=null,startDate=null,endDate=null,recordStatus=null,isShowAll=false;
	$('#empIdcheck').click(function(){
	  	$('#searchEmpId').val('');
    	$('#searchDepId').val('');
    	$('#searchCostId').val('');
    	$('#importEmpIdSum').text('0');
    	$('#importDepIdSum').text('0');
    	$('#importCostIdSum').text('0');
	    if($(this).prop("checked")==true){
	        //当前为选中状态
	    	$("#searchEmpId").attr("disabled", true); //设置为不可编辑
	    	$("#searchDepId").attr("disabled", true);
	    	$("#searchCostId").attr("disabled", true);	  
	    	$("#importEmpIdSearchBtn").attr("disabled", false); 
	    	$("#importDepIdSearchBtn").attr("disabled", true); 
	    	$("#importCostIdSearchBtn").attr("disabled", true); 
	    	$("#depIdcheck").attr("checked",false);
	    	$("#costIdcheck").attr("checked",false);
	    }else{
	        //当前为不选中状态	    	
	    	$("#searchEmpId").attr("disabled", false); //设置为可编辑
	    	$("#searchDepId").attr("disabled", false);
	    	$("#searchCostId").attr("disabled", false);
	    	$("#importEmpIdSearchBtn").attr("disabled", true); 
	    	$("#importDepIdSearchBtn").attr("disabled", true); 
	    	$("#importCostIdSearchBtn").attr("disabled", true); 

	    }
	});
	
	$('#depIdcheck').click(function(){
	  	$('#searchEmpId').val('');
    	$('#searchDepId').val('');
    	$('#searchCostId').val('');
    	$('#importEmpIdSum').text('0');
    	$('#importDepIdSum').text('0');
    	$('#importCostIdSum').text('0');
	    if($(this).prop("checked")==true){
	        //当前为选中状态
	    	$("#searchEmpId").attr("disabled", true); //设置为不可编辑
	    	$("#searchDepId").attr("disabled", true);
	    	$("#searchCostId").attr("disabled", true);	    	
	    	$("#importDepIdSearchBtn").attr("disabled", false); 
	    	$("#importEmpIdSearchBtn").attr("disabled", true); 
	    	$("#importCostIdSearchBtn").attr("disabled", true); 
	    	$("#empIdcheck").attr("checked",false);
	    	$("#costIdcheck").attr("checked",false);
	    }else{
	        //当前为不选中状态	    	
	    	$("#searchDepId").attr("disabled", false); //设置为可编辑
	    	$("#searchEmpId").attr("disabled", false); 
	    	$("#searchCostId").attr("disabled", false);
	    	$("#importDepIdSearchBtn").attr("disabled", true); 
	    	$("#importEmpIdSearchBtn").attr("disabled", true); 
	    	$("#importCostIdSearchBtn").attr("disabled", true); 

	    }
	});
	
	$('#costIdcheck').click(function(){
	  	$('#searchEmpId').val('');
    	$('#searchDepId').val('');
    	$('#searchCostId').val('');
    	$('#importEmpIdSum').text('0');
    	$('#importDepIdSum').text('0');
    	$('#importCostIdSum').text('0');
	    if($(this).prop("checked")==true){
	        //当前为选中状态
	    	$("#searchEmpId").attr("disabled", true); //设置为不可编辑
	    	$("#searchDepId").attr("disabled", true);
	    	$("#searchCostId").attr("disabled", true); 	    	
	    	$("#importEmpIdSearchBtn").attr("disabled", true); 
	    	$("#importDepIdSearchBtn").attr("disabled", true); 
	    	$("#importCostIdSearchBtn").attr("disabled", false); 	
	    	$("#empIdcheck").attr("checked",false);
	    	$("#depIdcheck").attr("checked",false);
	    }else{
	        //当前为不选中状态
	    	$("#searchEmpId").attr("disabled", false); //设置为不可编辑
	    	$("#searchDepId").attr("disabled", false);
	    	$("#searchCostId").attr("disabled", false);
	    	$("#importEmpIdSearchBtn").attr("disabled", true); 
	    	$("#importDepIdSearchBtn").attr("disabled", true); 
	    	$("#importCostIdSearchBtn").attr("disabled", true); 
	    }
	});
	
	$('#resetBtn').click(function(){
		$('#searchEmpId').val('');
    	$('#searchDepId').val('');
    	$('#searchCostId').val('');
    	$('#startDate').val('');
    	$('#endDate').val('');
		$("#empIdcheck").attr("checked",false);
		$("#depIdcheck").attr("checked",false);
    	$("#costIdcheck").attr("checked",false);
		$("#searchEmpId").attr("disabled", false); //设置为不可编辑
	    $("#searchDepId").attr("disabled", false);
	    $("#searchCostId").attr("disabled", false);
	    $("#importEmpIdSearchBtn").attr("disabled", true); 
	    $("#importDepIdSearchBtn").attr("disabled", true); 
	    $("#importCostIdSearchBtn").attr("disabled", true); 
	
	});
	
	$('#uploadEmpIdFile').change(function(){  
		$('#searchEmpId').val('');		
		$('#searchDepId').val('');
		$('#searchCostId').val('');		
		getFileContent(this, function (str) {
			var exportEmpId=str.replace(/\s+/g,',').replace(/,$/,"");
			$('#searchEmpId').val(exportEmpId);
			$('#importEmpIdSum').text(exportEmpId.split(",").length);
            });	   
		 $('#uploadEmpIdFile').val('');
	});	
	
	$('#uploadDepIdFile').change(function(){  
		$('#searchEmpId').val('');		
		$('#searchDepId').val('');
		$('#searchCostId').val('');	
		 getFileContent(this, function (str) {
			 var exportDepId=str.replace(/\s+/g,',').replace(/,$/,"");
			 $('#searchDepId').val(exportDepId);
			 $('#importDepIdSum').text(exportDepId.split(",").length);
          });	
		 $('#uploadDepIdFile').val(''); 
	});
	
	$('#uploadCostIdFile').change(function(){  
		$('#searchEmpId').val('');		
		$('#searchDepId').val('');
		$('#searchCostId').val('');		
		 getFileContent(this, function (str) {
			 var exportCostId=str.replace(/\s+/g,',').replace(/,$/,"");
			 $('#searchCostId').val(exportCostId);
			 $('#importCostIdSum').text(exportCostId.split(",").length);
          });	   
		 $('#uploadCostIdFile').val(''); 
	});

    $("#exportRawRecordBtn").click(function() {
		
		$("#rawRecordTable").table2excel(
				{
					exclude : ".noExl", // 过滤位置的 css 类名
					filename : "原始刷卡記錄"
							+ new Date().getFullYear() + "-"
							+ new Date().getMonth()+1 + "-"
							+ new Date().getDate() + " "
							+ new Date().getHours() + "："
							+ new Date().getMinutes(), 
					name : "Excel Document Name.xlsx",
					exclude_img : true,
					exclude_links : true,
					exclude_inputs : true

				});
	});
	
	$('#searchRawRecordBtn').click(function(){
		SearchRawRecords(false);		
	});
	
	$('#showAllRawRecordBtn').click(function(){		
		SearchRawRecords(true);
	});
	
	function SearchRawRecords(isShowAll){		
		empId=$('#searchEmpId').val();		
		depId=$('#searchDepId').val();
		costId=$('#searchCostId').val();
		startDate = $("#startDate").val();
		endDate = $("#endDate").val();
		
		var recordStatusArray =[],errorMessage='';
		
		if(startDate==="null" || startDate=='')
			errorMessage+='請選擇查詢的起始時間!\n';
		
		if(endDate==="null" || endDate=='')
			errorMessage+='請選擇查詢的結束時間!\n';
		
		$('input[name="recordStatus"]:checked').each(function(){
			recordStatusArray.push($(this).val());
		});
		if(recordStatusArray.length>0)
			recordStatus = recordStatusArray.join(",");
		else
			recordStatus = "";		
	
		if(errorMessage==''){
			getAsyncRawRecordInfo(curPage,empId,depId,costId,startDate,endDate,recordStatus,isShowAll);
		}else{
			if(errorMessage.length>0 ||errorMessage!='' ){
	    		alert(errorMessage);		
	    		event.preventDefault(); //preventDefault() 方法阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
	    		}
		}
	}
	
	function ShowSearchRawRecordTable(rawData,isShowAll){
		$('#rawRecordTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		if(totalRecord>0){
			$('#searchRawRecordCounts').html("");
			$('#searchRawRecordCounts').html("記錄數: "+totalRecord+" 條");;
		}
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["empId"]+'</td>'+
					'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["name"]+'</td>'+
					'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["depId"]+'</td>'+
					'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["costId"]+'</td>'+
					'<td>'+executeResult[i]["swipeCardTime"]+'</td>'+
					'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["swipeCardIpAddress"]+'</td></tr>';
					$('#rawRecordTable tbody').append(tableContents);
		}	
		if(!isShowAll){
			refreshRawRecordInfoPagination(currentPage,totalRecord,totalPage,pageSize);
		}
		else{
			$('#rawRecordPagination').empty();
		}
		
	}
	
	function refreshRawRecordInfoPagination(currentPage,totalRecord,totalPage,pageSize){
		$('#rawRecordPagination').empty();
		var paginationElement='頁次：'+currentPage+'/'+totalPage +'&nbsp;每页:&nbsp;'+pageSize+'&nbsp;共&nbsp;'+totalRecord+'&nbsp;條&nbsp;';
		if(currentPage==1)
			paginationElement+='<a href="#">首页</a>';		  
		else
			paginationElement+='<a class="firstPage">首页</a>';

		if(currentPage>1)
			paginationElement+='&nbsp;<a class="previousPage">上一頁</a>';
		else
			paginationElement+='&nbsp;<a href="#">上一頁</a>';
		
	   /* for(var i=1;i <= totalPage;i++){
	    	paginationElement+='&nbsp;<a class="numPage">' + i +'</a>&nbsp;';	    	
	    }*/
		if(currentPage<totalPage)
			paginationElement+='<a class="nextPage">下一頁</a>';
		else
			paginationElement+='<a href="#">下一頁</a>';
		
		$('#rawRecordPagination').append(paginationElement);
		
		$('.firstPage').click(function(){
			curPage=1;
			getAsyncRawRecordInfo(curPage,empId,depId,costId,startDate,endDate,recordStatus,false);			
		});
		
		$('.previousPage').click(function(){
			curPage=currentPage-1;
			getAsyncRawRecordInfo(curPage,empId,depId,costId,startDate,endDate,recordStatus,false);				
		});	
		
		$('.nextPage').click(function(){
			curPage=currentPage+1;
			getAsyncRawRecordInfo(curPage,empId,depId,costId,startDate,endDate,recordStatus,false);			
		});	
		
		$('.numPage').click(function(){
			var curPage=$(this).text();
			getAsyncRawRecordInfo(curPage,empId,depId,costId,startDate,endDate,recordStatus,false);		
    		});
		
	}
	
	function getAsyncRawRecordInfo(curPage,empId,depId,costId,startDate,endDate,recordStatus,isShowAll){
		$.ajax({
			type:'POST',
			url:'../RawRecord/SearchRawRecord.show',
			data:{curPage:curPage,empId:empId,depId:depId,costId:costId,startDate:startDate,endDate:endDate,recordStatus:recordStatus,isShowAll:isShowAll},
			error:function(e){
				alert('找不到資料');
			},
			success:function(rawData){
				$('#rawRecordTable tbody').empty();
										if (rawData != null && rawData != "") {
											var errorResponse = rawData.ErrorMessage;
											if (errorResponse != null) {
												alert(errorResponse);
											} else {
												var executeResult = rawData["list"];
												var numOfRecords = executeResult.length;
												if (numOfRecords > 0)
													ShowSearchRawRecordTable(
															rawData, isShowAll);
												else
													alert('找不到資料');
											}
										}
			}
		});		
	}
	
});

function Save1(name){
	var filename = name+ new Date().getFullYear() + "-"
	+ (new Date().getMonth()+1) + "-"
	+ new Date().getDate() + " "
	+ new Date().getHours() + ":"
	+ new Date().getMinutes();
	$('#rawRecordTable').tableExport({type:'excel'},filename);
};