$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null;
	
	$('#empInfoTitleBtn').click(function(){
		queryCritirea=$('#queryCritirea option:selected').val();
		queryParam=$('#queryParam').val();
		getEmpInfo(queryCritirea,queryParam);
	});
	
	function getEmpInfo(queryCritirea,queryParam){
		$.ajax({
			type:'GET',
			url:'../AdminActioin',
			data:{queryCritirea:queryCritirea,queryParam:queryParam},
			error:function(e){
				alert('找不到資料');
			},
			success:function(rawData){	
				if (rawData != null && rawData != "") {
					var executeResult=rawData;
					var errorResponse=executeResult.ErrorMessage;
					if(errorResponse!=null){
						alert('找不到資料');
					}
					else{
						var numOfRecords=executeResult.length;
						if(numOfRecords>0)
							ShowEmpInfoTable(rawData);
						else
							alert('找不到資料');
					}
				}
			}
		});
	}
	
	function ShowEmpInfoTable(rawData){
		$('#empInfoTable tbody').empty();
		var executeResult=rawData;
		console.log(rawData);
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td>'+executeResult[i]["EmpNo"]+'</td>'+
					'<td>'+executeResult[i]["EmpName"]+'</td>'+
					'<td>'+executeResult[i]["CostID"]+'</td>'+
					'<td>'+executeResult[i]["deptid"]+'</td>'+
					'<td>'+executeResult[i]["DeptNo"]+'</td>'+
					'<td>'+executeResult[i]["CardID"]+'</td>'+
					'<td>'+executeResult[i]["UpdateDate"]+'</td></tr>';
			$('#empInfoTable tbody').append(tableContents);
		}
	}
	
	/*function refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize){
		$('#jobTitleInfoPagination').empty();
		var paginationElement='頁次：'+currentPage+'/'+totalPage +'&nbsp;每页:&nbsp;'+pageSize+'&nbsp;共&nbsp;'+totalRecord+'&nbsp;條&nbsp;';
		if(currentPage==1)
			paginationElement+='<a href="#">首页</a>';		  
		else
			paginationElement+='<a class="firstPage">首页</a>';

		if(currentPage>1)
			paginationElement+='&nbsp;<a class="previousPage">上一頁</a>';
		else
			paginationElement+='&nbsp;<a href="#">上一頁</a>';
		
	    for(var i=1;i <= totalPage;i++){
	    	paginationElement+='&nbsp;<a class="numPage">' + i +'</a>&nbsp;';	    	
	    }
		if(currentPage<totalPage)
			paginationElement+='<a class="nextPage">下一頁</a>';
		else
			paginationElement+='<a href="#">下一頁</a>';
		
		$('#jobTitleInfoPagination').append(paginationElement);
		
		$('.firstPage').click(function(){
			curPage=1;
			getJobTitleInfo(curPage,queryCritirea,queryParam);				
		});
		
		$('.previousPage').click(function(){
			curPage=currentPage-1;
			getJobTitleInfo(curPage,queryCritirea,queryParam);				
		});	
		
		$('.nextPage').click(function(){
			curPage=currentPage+1;
			getJobTitleInfo(curPage,queryCritirea,queryParam);				
		});	
		
		$('.numPage').click(function(){
			var curPage=$(this).text();
			getJobTitleInfo(curPage,queryCritirea,queryParam);				
    		});
		
	}*/
	
	
	
});