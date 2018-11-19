$(document).ready(function(){
	var curPage=1,queryCritirea=null,queryParam=null;
	showAllLineMapping();
	showAllRTLine();
	
	function showAllRTLine(){
		$.ajax({
			type:'GET',
			url:'../LineMapping/GetAllRTLine.show',
			error:function(e){
				alert('找不到資料');
			},
			success:function(rawData){	
				if (rawData != null && rawData != "") {
					var errorResponse = rawData.ErrorMessage;
					if(errorResponse!=null){
						alert('找不到資料');
					}else{
						var	tableContents = '<option value="">--- 請選擇 ---</option>';
						for(var i = 0;i<rawData.length;i++){
							tableContents+='<option value="'+rawData[i]+'">--- '+rawData[i]+' ---</option>';
						}
						$('#RTLINE').append(tableContents);
					}
				}
			}
		});
	
	}
	
	function showAllLineMapping(){
		$.ajax({
			type:'GET',
			url:'../LineMapping/AllLineMapping.show',
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
							ShowAllLineMappingTable(rawData);
						else
							alert('找不到資料');
					}
				}
			}
		});
	}
	
	function ShowAllLineMappingTable(rawData){
		$('#lineMappingInfoTable tbody').empty();
		var currentPage=rawData.currentPage;
		var totalRecord=rawData.totalRecord;
		var totalPage=rawData.totalPage;
		var pageSize=rawData.pageSize;
		var executeResult=rawData["list"];
		for(var i=0;i<executeResult.length;i++){
			var	tableContents='<tr><td>'+executeResult[i]["ORG_NAME"]+'</td>'+
					'<td>'+executeResult[i]["MES_PDLINE_ID"]+'</td>'+
					'<td>'+executeResult[i]["MES_PDLINE_NAME"]+'</td>'+
					'<td>'+executeResult[i]["MES_PDLINE_DESC"]+'</td>'+
					'<td>'+executeResult[i]["RT_LINENO"]+'</td>'+
					'<td>'+executeResult[i]["STD_MAN_POWER"]+'</td>';
					
					tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';
					$('#lineMappingInfoTable tbody').append(tableContents);
		}
		//refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
		
		$('.editBtn').click(function(){
			var parentElement=$(this).parent().parent();
			var OGNNAME=$(parentElement).find('td').eq(0).text();
			$(parentElement).find('td').eq(0).html('<input type="text" class="changeOGNName input-small" value="'+OGNNAME+'">');
			
			var MESLINEID=$(parentElement).find('td').eq(1).text();
			$(parentElement).find('td').eq(1).html('<input type="text" class="changeMesLineID input-small" value="'+MESLINEID+'">');
			
			var MESLINENAME=$(parentElement).find('td').eq(2).text();
			$(parentElement).find('td').eq(2).html('<input type="text" class="changeMesLineName input-small" value="'+MESLINENAME+'">');
			
			var MESLINEDESC=$(parentElement).find('td').eq(3).text();
			$(parentElement).find('td').eq(3).html('<input type="text" class="changeMesLineDesc input-small" value="'+MESLINEDESC+'">');
			
			var STDMANPOWER=$(parentElement).find('td').eq(5).text();
			$(parentElement).find('td').eq(5).html('<input type="text" class="changeSTDManPower input-small" value="'+STDMANPOWER+'">');
			
			$(parentElement).children().find('.editBtn .deleteBtn').hide();
			$(parentElement).find('td').eq(6).append('<a class="confirmBtn btn btn-xs btn-link" role="button">確認</a>'+
	        		'<a class="cancelBtn btn btn-xs btn-link" role="button">取消</a>');
			$(parentElement).find('.editBtn,.deleteBtn').hide();
			
			$('.confirmBtn').click(function(){
				var parentElement=$(this).parent().parent();
				var LineMapping=new Object(),errorMessage='';
				LineMapping.ORG_NAME=$(parentElement).find('td input:text').eq(0).val();
				LineMapping.MES_PDLINE_ID=$(parentElement).find('td input:text').eq(1).val();
				LineMapping.MES_PDLINE_NAME=$(parentElement).find('td input:text').eq(2).val();
				LineMapping.MES_PDLINE_DESC=$(parentElement).find('td input:text').eq(3).val();
				LineMapping.RT_LINENO=$(parentElement).find('td').eq(4).text();
				LineMapping.STD_MAN_POWER=$(parentElement).find('td input:text').eq(4).val();
				/*if(LineMapping["ORG_NAME"]==''){
					errorMessage+='OGN代碼未填寫\n';
				}else if(LineMapping["ORG_NAME"]=='null'){
					LineMapping.ORG_NAME = '';
				}
				
				if(LineMapping["MES_PDLINE_ID"]==''){
					errorMessage+='MES系統對應的產線號未填寫\n';
				}else if(LineMapping["MES_PDLINE_ID"]=='null'){
					LineMapping.MES_PDLINE_ID = '';
				}
				
				if(LineMapping["MES_PDLINE_NAME"]==''){
					errorMessage+='MES系統對應的產線名稱未填寫\n';
				}else if(LineMapping["MES_PDLINE_NAME"]=='null'){
					LineMapping.MES_PDLINE_NAME = '';
				}
				
				if(LineMapping["MES_PDLINE_DESC"]==''){
					errorMessage+='MES系統對應的產線描述未填寫\n';
				}else if(LineMapping["MES_PDLINE_DESC"]=='null'){
					LineMapping.MES_PDLINE_DESC = '';
				}
				
				if(LineMapping["STD_MAN_POWER"]==''){
					errorMessage+='標準人力未填寫\n';
				}else if(LineMapping["STD_MAN_POWER"]=='null'){
					LineMapping.STD_MAN_POWER = '';
				}*/
				
				if(LineMapping["MES_PDLINE_NAME"]=='' || LineMapping["MES_PDLINE_NAME"]=='null'){
					errorMessage+='MES系統對應的產線名稱未填寫\n';
				}
				
				if(LineMapping["RT_LINENO"]=='' || LineMapping["RT_LINENO"]=='null'){
					errorMessage+='事實系統對應的產線名稱未填寫\n';
				}
				
				if(errorMessage==''){
					$.ajax({
						type:'POST',
						contentType: "application/json",
						url:'../LineMapping/UpdateLineMapping.do',
						data:JSON.stringify(LineMapping),
						dataType:'json',
						error:function(e){
							alert(e);
							},
						success:function(data){
							  if(data!=null && data!=''){
								  if(data.StatusCode=="200"){
									  $(parentElement).find('td').eq(0).html(LineMapping["ORG_NAME"]);
									  $(parentElement).find('td').eq(1).html(LineMapping["MES_PDLINE_ID"]);
									  $(parentElement).find('td').eq(2).html(LineMapping["MES_PDLINE_NAME"]);
									  $(parentElement).find('td').eq(3).html(LineMapping["MES_PDLINE_DESC"]);
									  $(parentElement).find('td').eq(5).html(LineMapping["STD_MAN_POWER"]);
									  $(parentElement).find('.editBtn').show();
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
				$(parentElement).find('td').eq(0).html(OGNNAME);
				$(parentElement).find('td').eq(1).html(MESLINEID);
				$(parentElement).find('td').eq(2).html(MESLINENAME);
				$(parentElement).find('td').eq(3).html(MESLINEDESC);
				$(parentElement).find('td').eq(5).html(STDMANPOWER);
				$(this).parent().find('.confirmBtn,.cancelBtn').remove();
			})
		});
	}
	
	function refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize){
		$('#lineMappingPagination').empty();
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
		
		$('#lineMappingPagination').append(paginationElement);
		
		$('.firstPage').click(function(){
			curPage=1;
			getLineMappingInfo(curPage,queryCritirea,queryParam);				
		});
		
		$('.previousPage').click(function(){
			curPage=currentPage-1;
			getLineMappingInfo(curPage,queryCritirea,queryParam);				
		});	
		
		$('.nextPage').click(function(){
			curPage=currentPage+1;
			getLineMappingInfo(curPage,queryCritirea,queryParam);				
		});	
		
		$('.numPage').click(function(){
			var curPage=$(this).text();
			getLineMappingInfo(curPage,queryCritirea,queryParam);				
    		});
		
	}
	
	$('#searchLineMappingBtn').click(function(){
		curPage = 1;
		var queryCritirea=$('#queryCritirea option:selected').val();
		var queryParam=$('#queryParam').val();
		getLineMappingInfo(curPage,queryCritirea,queryParam)
	});
	
	function getLineMappingInfo(curPage,queryCritirea,queryParam){
		$.ajax({
			type:'GET',
			url:'../LineMapping/AllLineMapping.show',
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
							ShowAllLineMappingTable(rawData);
						else
							alert('找不到資料');
					}
				}
			}
		});
	}
	
	$('#resetSubmit').click(function(){
	    $('#inputOGN').val('');
    	$('#inputMESPDLineId').val('');
    	$('#inputMESPDLineName').val('');
    	$('#inputMESPDLineDesc').val('');
        $('#RTLINE').val('');
    	$('#inputSTDManPower').val('');
  });
	
	$('#submitNewUser').click(function(){
		var LineMapping = new Object();
		var errorMessage = '';
		LineMapping.ORG_NAME=$('#inputOGN').val();
		LineMapping.MES_PDLINE_ID=$('#inputMESPDLineId').val();
		LineMapping.MES_PDLINE_NAME=$('#inputMESPDLineName').val();
		LineMapping.MES_PDLINE_DESC=$('#inputMESPDLineDesc').val();
		LineMapping.RT_LINENO=$('#RTLINE option:selected').val();
		LineMapping.STD_MAN_POWER=$('#inputSTDManPower').val();
		
		if(LineMapping["MES_PDLINE_NAME"]=='' || LineMapping["MES_PDLINE_NAME"]=='null'){
			errorMessage+='MES系統對應的產線名稱未填寫\n';
		}
		
		if(LineMapping["RT_LINENO"]=='' || LineMapping["RT_LINENO"]=='null'){
			errorMessage+='事實系統對應的產線名稱未填寫\n';
		}
		
		if(LineMapping["MES_PDLINE_ID"]!='' && LineMapping["MES_PDLINE_ID"]!='null'){
			var regx='^\\d+$';
			if(!LineMapping["MES_PDLINE_ID"].match(regx)){
				errorMessage+='MES系統對應的線號格式錯誤，只能填寫數字，請重新輸入\n'
			}
		}
		
		if(LineMapping["STD_MAN_POWER"]!='' || LineMapping["STD_MAN_POWER"]!='null'){
			var regx='^\\d+$';
			if(!LineMapping["STD_MAN_POWER"].match(regx)){
				errorMessage+='標準人力格式錯誤，只能填寫數字，請重新輸入\n'
			}
		}
		
		alert(JSON.stringify(LineMapping));
		
		if(errorMessage == ''){
			$.ajax({
				type:'POST',
				contentType: "application/json",
				url:'../LineMapping/AddLineMapping.do',
				data:JSON.stringify(LineMapping),
				dataType:'json',
				error:function(e){
					alert('找不到資料');
				},
				success:function(rawData){	
					if (rawData != null && rawData != "") {
						if(rawData.StatusCode == "200"){
							alert(rawData.Message);
							$('#inputOGN').val('');
					    	$('#inputMESPDLineId').val('');
					    	$('#inputMESPDLineName').val('');
					    	$('#inputMESPDLineDesc').val('');
					        $('#RTLINE').val('');
					    	$('#inputSTDManPower').val('');
						}else{
							alert(rawData.Message);
						}
					}else{
						 alert('新增線別對應關係失敗!');
					}
				}
			});
		}else{
			if(errorMessage.length>0 ||errorMessage!='' ){
			    alert(errorMessage);		
				event.preventDefault(); //preventDefault() 方法阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
			}
		}
	});
});