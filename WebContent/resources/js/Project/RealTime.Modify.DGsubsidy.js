$(document).ready(function(){
	var curPage=1,empId=null,depId=null,costId=null,startDate=null,endDate=null,isShowAll=false;;
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
    
     $("#exportDGsubsidyBtn").click(function() {
		
		$("#DGsubsidyTable").table2excel(
				{
					exclude : ".noExl", // 过滤位置的 css 类名
					filename : "崗位津貼記錄"
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
	
	$('#searchDGsubsidyBtn').click(function(){	
		SearchDGsubsidys(false);
	});
	
	$('#showAllDGsubsidyBtn').click(function(){	
		SearchDGsubsidys(true);
	});
	
	function SearchDGsubsidys(isShowAll){	
		empId=$('#searchEmpId').val();		
		depId=$('#searchDepId').val();
		costId=$('#searchCostId').val();
		startDate = $("#startDate").val();
		endDate = $("#endDate").val();
		
		var errorMessage='';
		
		if(startDate==="null" || startDate=='')
			errorMessage+='請選擇查詢的起始時間!\n';
		
		if(endDate==="null" || endDate=='')
			errorMessage+='請選擇查詢的結束時間!\n';
		
		if(errorMessage==''){
			getAsyncDGsubsidyInfo(curPage,empId,depId,costId,startDate,endDate,isShowAll);
		}else{
			if(errorMessage.length>0 ||errorMessage!='' ){
	    		alert(errorMessage);		
	    		event.preventDefault(); //preventDefault() 方法阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
	    		}
		}
	}
	
	function ShowSearchDGsubsidyTable(rawData,isShowAll){
		$('#DGsubsidyTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		if(totalRecord>0){
			$('#searchDGsubsidyCounts').html("");
			$('#searchDGsubsidyCounts').html("記錄數: "+totalRecord+" 條");;
		}
		
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			
			var goWorkTimeStr=executeResult[i]["SwipeCardTime"];
			var endWorkTimeStr=executeResult[i]["SwipeCardTime2"];
			var classStartTimeStr=goWorkTimeStr.substr(0, 10)+' '+executeResult[i]["ClassStart"].substr(0, 2)+':'+ executeResult[i]["ClassStart"].substr(2, 2);
			var goWorkTime = new Date(Date.parse(goWorkTimeStr.replace(/-/g,   "/"))); 
			var goWorkoldTime = (new Date(goWorkTimeStr.replace(".0",   ""))).getTime();
			var goWorkcurTime = new Date(goWorkoldTime).format("yyyy-MM-dd hh:mm");
			var endWorkTime = new Date(Date.parse(endWorkTimeStr.replace(/-/g,   "/")));
			var endWorkoldTime = (new Date(endWorkTimeStr.replace(".0",   ""))).getTime();
			var endWorkcurTime = new Date(endWorkoldTime).format("yyyy-MM-dd hh:mm");
			var classStartTime = new Date(Date.parse(classStartTimeStr.replace(/-/g,   "/"))); 
		
			 if (goWorkTime < classStartTime)
				 goWorkTime = classStartTime;
			var subOnWorkMinus=endWorkTime - goWorkTime;
			 if (subOnWorkMinus < 0) {
				 subOnWorkMinus = 0;
             }
			    subOnWorkMinus = subOnWorkMinus / 3600000;
			var subOnWorkTime = getSubOnWorkTime(subOnWorkMinus);
			var overplus = subOnWorkTime-10;
			var	tableContents='<tr><td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["EmpId"]+'</td>'+
			'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["Name"]+'</td>'+
			'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["DepId"]+'</td>'+
			'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["CostId"]+'</td>'+
			'<td>'+goWorkcurTime+'</td>'+
			'<td>'+endWorkcurTime+'</td>'+
			'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+subOnWorkTime+'</td>';
			
			if(overplus>=0){
				tableContents+='<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+overplus+'</td></tr>';
			}else{
				tableContents+='<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">0</td></tr>';
			}
			$('#DGsubsidyTable tbody').append(tableContents);			
		}	
		if(!isShowAll){
			refreshDGsubsidyInfoPagination(currentPage,totalRecord,totalPage,pageSize);
		}
		else{
			$('#DGsubsidyPagination').empty();
		}
	}
	
	 function getSubOnWorkTime(subOnWorkMinus) {
         var front = 0;
         var surplus = 0;
         front = Math.floor(subOnWorkMinus);           //floor() 方法执行的是向下取整计算
         surplus = subOnWorkMinus - front;

         if (surplus < 0.25) {
             surplus = 0;
         } else if (surplus > 0.25 && surplus < 0.5) {
             surplus = 0.25;
         } else if (surplus >= 0.5 && surplus < 0.75) {
             surplus = 0.5;
         } else if (surplus >= 0.75 && surplus < 1) {
             surplus = 0.75;
         }

         return surplus + front;
     }
	
	function refreshDGsubsidyInfoPagination(currentPage,totalRecord,totalPage,pageSize){
		$('#DGsubsidyPagination').empty();
		var paginationElement='頁次：'+currentPage+'/'+totalPage +'&nbsp;每页:&nbsp;'+pageSize+'&nbsp;共&nbsp;'+totalRecord+'&nbsp;條&nbsp;';
		if(currentPage==1)
			paginationElement+='<a href="#">首页</a>';		  
		else
			paginationElement+='<a class="firstPage">首页</a>';

		if(currentPage>1)
			paginationElement+='&nbsp;<a class="previousPage">上一頁</a>';
		else
			paginationElement+='&nbsp;<a href="#">上一頁</a>';
		
	  /*  for(var i=1;i <= totalPage;i++){
	    	paginationElement+='&nbsp;<a class="numPage">' + i +'</a>&nbsp;';	    	
	    }*/
		if(currentPage<totalPage)
			paginationElement+='<a class="nextPage">下一頁</a>';
		else
			paginationElement+='<a href="#">下一頁</a>';
		
		$('#DGsubsidyPagination').append(paginationElement);
		
		$('.firstPage').click(function(){
			curPage=1;
			getAsyncDGsubsidyInfo(curPage,empId,depId,costId,startDate,endDate,false);			
		});
		
		$('.previousPage').click(function(){
			curPage=currentPage-1;
			getAsyncDGsubsidyInfo(curPage,empId,depId,costId,startDate,endDate,false);				
		});	
		
		$('.nextPage').click(function(){
			curPage=currentPage+1;
			getAsyncDGsubsidyInfo(curPage,empId,depId,costId,startDate,endDate,false);			
		});	
		
		$('.numPage').click(function(){
			var curPage=$(this).text();
			getAsyncDGsubsidyInfo(curPage,empId,depId,costId,startDate,endDate,false);		
    		});
		
	}
	
	function getAsyncDGsubsidyInfo(curPage,empId,depId,costId,startDate,endDate,isShowAll){
		$.ajax({
			type:'POST',
			url:'../DGsubsidy/SearchDGsubsidy.show',
			data:{curPage:curPage,empId:empId,depId:depId,costId:costId,startDate:startDate,endDate:endDate,isShowAll:isShowAll},
			error:function(e){
				alert('找不到資料');
			},
			success:function(rawData){
				$('#DGsubsidyTable tbody').empty();
				if (rawData != null && rawData != "") {
					var errorResponse=rawData.ErrorMessage;
					if(errorResponse!=null){
						alert(errorResponse);
					}
					else{
						var executeResult=rawData["list"];
						var numOfRecords=executeResult.length;
						if(numOfRecords>0)
							ShowSearchDGsubsidyTable(rawData,isShowAll);
						else
							alert('找不到資料');
					}
				}
				else{
					alert('找不到資料');
				}
			}
		});		
	}
	
	Date.prototype.format = function(format)
	{
	 var o = {
	 "M+" : this.getMonth()+1, //month
	 "d+" : this.getDate(),    //day
	 "h+" : this.getHours(),   //hour
	 "m+" : this.getMinutes(), //minute
	 "s+" : this.getSeconds(), //second
	 "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
	 "S" : this.getMilliseconds() //millisecond
	 }
	 if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	 (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	 for(var k in o)if(new RegExp("("+ k +")").test(format))
	 format = format.replace(RegExp.$1,
	 RegExp.$1.length==1 ? o[k] :
	 ("00"+ o[k]).substr((""+ o[k]).length));
	 return format;
	}
	
});

function Save1(name){
	var filename = name+ new Date().getFullYear() + "-"
	+ (new Date().getMonth()+1) + "-"
	+ new Date().getDate() + " "
	+ new Date().getHours() + ":"
	+ new Date().getMinutes();
	$('#DGsubsidyTable').tableExport({type:'excel'},filename);
};