$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null;
	var TypeD,TypeA;
	showAllJobTitleInfo();
	function showAllJobTitleInfo(){
		$.ajax({
			type:'GET',
			url:'../JobTitle/AllJobTitle.show',
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
							ShowAllJobTitleTable(rawData);
						else
							alert('找不到資料');
					}
				}
			}
		});
	}
	
	$('#searchJobTitleBtn').click(function(){
		curPage = 1;
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		getJobTitleInfo(curPage,queryCritirea,queryParam)
	});
	
	function getJobTitleInfo(curPage,queryCritirea,queryParam){
		$.ajax({
			type:'GET',
			url:'../JobTitle/AllJobTitle.show',
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
							ShowAllJobTitleTable(rawData);
						else
							alert('找不到資料');
					}
				}
			}
		});
	}
	
	function ShowAllJobTitleTable(rawData){
		$('#jobTitleInfoTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		findJobInfoList("A");
		findJobInfoList("D");
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td>'+executeResult[i]["EmpNo"]+'</td>'+
					'<td>'+executeResult[i]["EmpName"]+'</td>'+
					'<td>'+executeResult[i]["DeptNo"]+'</td>'+
					'<td>'+executeResult[i]["CostID"]+'</td>';
					if(executeResult[i]["Job_Title"] === null || executeResult[i]["Job_Title"] === ''){
						tableContents+='<td>無職務</td>'
					}else{
						var jobTitle = '';
						for(var j = 0;j<TypeD.length;j++){
							if(executeResult[i]["Job_Title"] === TypeD[j]["Job_Code"]){
								jobTitle = TypeD[j]["Job_Desc"];
								break;
							}
						}
						if(jobTitle === '' || jobTitle === null){
							tableContents+='<td>無職務</td>'
						}else{
							tableContents+='<td>'+jobTitle+'</td>'
						}
						
					}
					if(executeResult[i]["Job_Name"] === null || executeResult[i]["Job_Name"] === ''){
						tableContents+='<td>無職稱</td>'
					}else{
						var jobName = '';
						for(var j = 0;j<TypeA.length;j++){
							if(executeResult[i]["Job_Name"] === TypeA[j]["Job_Code"]){
								jobName = TypeA[j]["Job_Desc"];
								break;
							}
						}
						if(jobName === '' || jobName === null){
							tableContents+='<td>無職稱</td>'
						}else{
							tableContents+='<td>'+jobName+'</td>';
						}
					}
					tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';
					$('#jobTitleInfoTable tbody').append(tableContents);
		}
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
		
		$('.editBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var id=$(parentElement).find('td').eq(0).text();
			var jobTitle=$(parentElement).find('td').eq(4).text();
			var jobName=$(parentElement).find('td').eq(5).text();
			findJobInfoList("A");
			findJobInfoList("D");
			var jobTitleType;
			var jobNameType;
			$(parentElement).find('td').eq(4).html('<select class="changeJobTitle input-small"></select>');
			var htmlAppender='<option value="">-請選擇-</option>';
			for(var i = 0;i<TypeD.length;i++){
				htmlAppender+='<option value='+TypeD[i]["Job_Code"]+'>-'+TypeD[i]["Job_Desc"]+'-</option>';
			}
			$(parentElement).find('td .changeJobTitle').append(htmlAppender);
			for(var i = 0;i<TypeD.length;i++){
				if(jobTitle === TypeD[i]["Job_Desc"]){
					jobTitleType = TypeD[i]["Job_Code"];
					break;
				}
			}
			$(parentElement).find('td .changeJobTitle option').each(function(){
				if($(this).val()==jobTitleType){
					$(this).prop('selected',true);
				}
			});
			
			$(parentElement).find('td').eq(5).html('<select class="changeJobName input-small"></select>');
			var htmlAppender1='<option value="">-請選擇-</option>';
			for(var i = 0;i<TypeA.length;i++){
				htmlAppender1+='<option value='+TypeA[i]["Job_Code"]+'>-'+TypeA[i]["Job_Desc"]+'-</option>';
			}
			$(parentElement).find('td .changeJobName').append(htmlAppender1);
			for(var i = 0;i<TypeA.length;i++){
				if(jobName === TypeA[i]["Job_Desc"]){
					jobNameType = TypeA[i]["Job_Code"];
					break;
				}
			}
			$(parentElement).find('td .changeJobName option').each(function(){
				if($(this).val()==jobNameType){
					$(this).prop('selected',true);
				}
			});
			
			$(parentElement).children().find('.editBtn .deleteBtn').hide();
			$(parentElement).find('td').eq(6).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
			
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var Emp=new Object(),errorMessage='';
				Emp.EmpNo=$(parentElement).find('td').eq(0).text();
				Emp.EmpName=$(parentElement).find('td').eq(1).text();
				Emp.DeptNo=$(parentElement).find('td').eq(2).text();
				Emp.CostID=$(parentElement).find('td').eq(3).text();
				Emp.Job_Title=$(parentElement).find('.changeJobTitle option:selected').eq(0).val();
				Emp.Job_Name=$(parentElement).find('.changeJobName option:selected').eq(0).val();
				if(Emp["EmpNo"]==="null" || Emp["EmpNo"]=='')
					errorMessage+='員工工號未填寫\n';
				if(Emp.EmpName==="null" || Emp.EmpName=='')
					errorMessage+='員工姓名未填寫\n';
				
				if(Emp.DeptNo==="null" || Emp.DeptNo=='')
					errorMessage+='員工部門未選取\n';
				
				if(Emp.CostID==="null" || Emp.CostID=='')
					errorMessage+='員工費用代碼未填寫\n';	
				if(Emp.Job_Name==="null" || Emp.Job_Name=='')
					errorMessage+='員工職稱未選擇\n';
				if(errorMessage==''){
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../JobTitle/UpdateJobTitle.do',
						data:JSON.stringify(Emp),
						dataType:'json',
						error:function(e){
							alert(e);
							},
						success:function(data){
							  if(data!=null && data!=''){
								  if(data.StatusCode=="200"){
									  $(parentElement).find('.editBtn').show();
									  var empJobTitle;
									  var empJobName;
									  for(var i = 0;i<TypeD.length;i++){
											if(Emp.Job_Title === TypeD[i]["Job_Code"]){
												empJobTitle = TypeD[i]["Job_Desc"];
												break;
											}
									  }
									  if(empJobTitle == null || empJobTitle == ''){
										  $(parentElement).find('td').eq(4).html('無職務');
									  }else{
										  $(parentElement).find('td').eq(4).html(empJobTitle);
									  }
									  
									  for(var i = 0;i<TypeA.length;i++){
											if(Emp.Job_Name === TypeA[i]["Job_Code"]){
												empJobName = TypeA[i]["Job_Desc"];
												break;
											}
									  }
									  if(empJobName == null || empJobName == ''){
										  $(parentElement).find('td').eq(5).html('無職稱');
									  }else{
										  $(parentElement).find('td').eq(5).html(empJobName);
									  }
									  $(parentElement).find('.confirmBtn,.cancelBtn').remove();
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
				$(parentElement).find('td').eq(4).html(jobTitle);
				$(parentElement).find('td').eq(5).html(jobName);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})
		});
	}
	
	function findJobInfoList(jobType){
		$.ajax({
			type:'GET',
			url:'../JobTitle/JobType.show',
			async:false,
			data:{JobType:jobType},
			error:function(e){
				alert('找不到資料');
			},
			success:function(rawData){
				if (rawData != null && rawData != "") {
					var errorResponse=rawData.ErrorMessage;
					if(errorResponse!=null){
						alert('找不到資料');
					}else{
						var numOfRecords=rawData.length;
						if(numOfRecords>0){
							if(jobType === 'D'){
								TypeD = rawData;
							}else if(jobType === 'A'){
								TypeA = rawData;
							}
						}else{
							alert('找不到資料');
						}	
					}
				}
			}
		});
	}
	
	function refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize){
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
		
	  /*  for(var i=1;i <= totalPage;i++){
	    	paginationElement+='&nbsp;<a class="numPage">' + i +'</a>&nbsp;';	    	
	    }*/
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
		
	}
	
	
	
});