$(document).ready(function(){
//	ShowDepid()
	var curPage = 1,type=null,startDate=null,endDate=null,data=null;
	$('input:radio[name="changeType"]').click(function(){
		 type = $('input:radio[name="changeType"]:checked').val();
		if(type=="empid"){
//			$('.inputdata').css('visibility','visible')
//			$('.inputdata').attr('disabled',false)
//			$('.selected').attr('disabled',true)
//			$('.selected').css('visibility','hidden')
			$('.inputdata').remove()
			$('.selected').remove()
			$('#outputExcel').before("<input id='data'   class='inputdata inputGray btn1-sm btn1-brown' style='text-align:center;background-color:#e0e0e0;' autocomplete='off'>")
		}else if(type=="depid"){
//			$('.inputdata').attr('disabled',true)
//			$('.inputdata').css('visibility','hidden')
//			$('.selected').attr('disabled',false)
//			$('.selected').css('visibility','visible')
			$('.inputdata').remove()
			$('.selected').remove()
			$('#outputExcel').before("<select id='data' class='selected' autocomplete='off' ></select>")
			ShowDepid()
		}else if(type=="costid"){
//			$('.inputdata').attr('disabled',true)
//			$('.inputdata').css('visibility','hidden')
//			$('.selected').attr('disabled',false)
//			$('.selected').css('visibility','visible')
			$('.inputdata').remove()
			$('.selected').remove()
			$('#outputExcel').before("<select id='data' class='selected' autocomplete='off' ></select>")
			ShowCostId()
		}
	})
	
	$('#outputExcel').click(function(){
		 curPage = 1;
		 type=$('input:radio[name="changeType"]:checked').val();
//		 startDate = $('#startDate').val();
//		 endDate = $('#endDate').val();
		 data = $('#data').val();
		if(type=="empid"){
			 data= $('.inputdata').val();
		}else if(type=="depid"){	
			 data= $('.selected').val();
		}else if(type=="costid"){
			 data= $('.selected').val();
		}
		var errorMessage=''
//	    if(startDate==null || startDate=="")
//			errorMessage+='未選擇生效起始日期\n';
//		if(endDate==null || endDate=="")
//			errorMessage+='未選擇生效結束日期\n';	
		if(data==null || data=="")
			errorMessage+='輸入框不能爲空';
		if(errorMessage==''){
			getAsyncAccountInfo(curPage,type,data)
		}
		else{
	    	if(errorMessage.length>0 ||errorMessage!='' ){
			    alert(errorMessage);		
				event.preventDefault(); //preventDefault() 方法阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
		    	return;
	    	}
	    }	
	})
	
	$('#showAll').click(function(){
		 type=$('input:radio[name="changeType"]:checked').val();
//		 startDate = $('#startDate').val();
//		 endDate = $('#endDate').val();
		 data = $('#data').val();
		if(type=="empid"){
			 data= $('.inputdata').val();
		}else if(type=="depid"){	
			 data= $('.selected').val();
		}else if(type=="costid"){
			 data= $('.selected').val();
		}
		var errorMessage=''
//	    if(startDate==null || startDate=="")
//			errorMessage+='未選擇生效起始日期\n';
//		if(endDate==null || endDate=="")
//			errorMessage+='未選擇生效結束日期\n';	
		if(data==null || data=="")
			errorMessage+='輸入框不能爲空';
		if(errorMessage==''){
			getAsyncAccountAll(type,data)
		}
		else{
	    	if(errorMessage.length>0 ||errorMessage!='' ){
			    alert(errorMessage);		
				event.preventDefault(); //preventDefault() 方法阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
		    	return;
	    	}
	    }	
	})
	
		function ShowAllAccountTable(data) {
						$('#SwipeCardRecords .spTable').empty();	
						var currentPage = data.currentPage;
						var totalRecord = data.totalRecord;
						var totalPage = data.totalPage;
						var pageSize = data.pageSize;
						var queryResult = data['list'];
						if (queryResult.length > 0) {						
							for (var i = 0; i < queryResult.length; i++) {
								var tableContents = '<tr><td>'
									+ queryResult[i].Id + '</td>' + '<td>'
									+ queryResult[i].Name + '</td>'
									+ '<td>' + queryResult[i].Depid
									+ '</td>' + '<td>'
									+ queryResult[i].Costid + '</td>'
									+ '<td>' + queryResult[i].Start_Date
									+ '</td>'
									+ '<td>' + queryResult[i].End_Date
									+ '</td>' + '<td>'
									+ queryResult[i].Continuous_Days
									+ '</td></tr>';
								$('#SwipeCardRecords .spTable').append(tableContents);
							}
							refreshUserInfoPagination(currentPage, totalRecord,
									totalPage, pageSize);
						}
						
					}
	
	function refreshUserInfoPagination(currentPage,
			totalRecord, totalPage, pageSize) {
		$('#swipeCardInfoPagination').empty();
		var paginationElement = '頁次：' + currentPage + '/'
				+ totalPage + '&nbsp;每页:&nbsp;' + pageSize
				+ '&nbsp;共&nbsp;' + totalRecord
				+ '&nbsp;條&nbsp;';
		if (currentPage == 1)
			paginationElement += '<a href="#">首页</a>';
		else
			paginationElement += '<a class="firstPage">首页</a>';

		if (currentPage > 1)
			paginationElement += '&nbsp;<a class="previousPage">上一頁</a>';
		else
			paginationElement += '&nbsp;<a href="#">上一頁</a>';

		/*
		 * for(var i=1;i <= totalPage;i++){
		 * paginationElement+='&nbsp;<a class="numPage">' + i +'</a>&nbsp;'; }
		 */
		if (currentPage < totalPage)
			paginationElement += '<a class="nextPage">下一頁</a>';
		else
			paginationElement += '<a href="#">下一頁</a>';

		$('#swipeCardInfoPagination').append(paginationElement);

		$('.firstPage').click(
				function() {
					curPage = 1;
					getAsyncAccountInfo(curPage,type,data)
				});

		$('.previousPage').click(
				function() {
					curPage = currentPage - 1;
					getAsyncAccountInfo(curPage,type,data)
				});

		$('.nextPage').click(
				function() {
					curPage = currentPage + 1;
					getAsyncAccountInfo(curPage,type,data)
					
				});

//		$('.numPage').click(
//				function() {
//					var curPage = $(this).text();
//					getAsyncAccountInfo(curPage,startDate,endDate,type,data)
//				});

	}
	
	function getAsyncAccountInfo(CurPage,Type,Data){
//		console.log(CurPage)
//		console.log(StartDate)
//		console.log(EndDate)
//		console.log(Type)
//		console.log(Data)
		$.ajax({
			type:'POST',
			url:'../SearchWorkDayCount/SearchWorkDayCount.show',
			data:{curPage:CurPage,Type:Type,Data:Data},
			error:function(e){
				alert('找不到資料');
			},
			success:function(rawData){
				$('#SwipeCardRecords .spTable').empty();
										console.log(rawData)
										if (rawData != null && rawData != "") {
											var executeResult = rawData["list"];
											var errorResponse = rawData.ErrorMessage;
											if (errorResponse != null) {
												alert(errorResponse);
											} else {
												var numOfRecords = executeResult.length;
												if (numOfRecords > 0)
													ShowAllAccountTable(rawData);
												else
													alert('找不到資料');
											}
										}
			}
		});		
	}
	
	function getAsyncAccountAll(Type,Data){
		$.ajax({
			type:'POST',
			url:'../SearchWorkDayCount/SearchAll.show',
			data:{Type:Type,Data:Data},
			error:function(e){
				alert('找不到資料');
			},
			success:function(rawData){
				$('#SwipeCardRecords .spTable').empty();
										console.log(rawData)
										if (rawData != null && rawData != "") {
												var queryResult = rawData;
												if (queryResult.length > 0)
													for (var i = 0; i < queryResult.length; i++) {
														var tableContents = '<tr><td>'
															+ queryResult[i].Id + '</td>' + '<td>'
															+ queryResult[i].Name + '</td>'
															+ '<td>' + queryResult[i].Depid
															+ '</td>' + '<td>'
															+ queryResult[i].Costid + '</td>'
															+ '<td>' + queryResult[i].Start_Date
															+ '</td>'
															+ '<td>' + queryResult[i].End_Date
															+ '</td>' + '<td>'
															+ queryResult[i].Continuous_Days
															+ '</td></tr>';
														$('#SwipeCardRecords .spTable').append(tableContents);
														$('#swipeCardInfoPagination').empty();
													}
												else
													alert('找不到資料');
										}else{
											var errorResponse = rawData.ErrorMessage;
											if (errorResponse != null) {
												alert(errorResponse);
											}
										}
			}
		});		
	}
	
	function ShowDepid(){
		$.ajax({
			type:'POST',
			url:'../ThreeMergeOne/ShowDepid.show',
			data:{},
			async:false,
			success:function(data){
//				console.log(data)
			$('.selected').empty()
			var htmlAppender='';
			 if(data!=null && data!=''){	
				if(data.length>0 && data.StatusCode == null){
					for(var i=0;i<data.length;i++){
						htmlAppender+='<option value="'+data[i]+'">'+data[i]+'</option>';
					}
					 $('.selected').append(htmlAppender);
				}
				else{
					alert('無部門代碼資料');
				}
			 }else{
				alert('無部門代碼資料');
			 }
			}
		});   
	}
	
	function ShowCostId(){
		$.ajax({
			type:'POST',
			url:'../ThreeMergeOne/ShowCostId.show',
			data:{},
			async:false,
			success:function(data){
//			var b = data.split('*')
			
			$('.selected').empty()
			var htmlAppender='';
			 if(data!=null && data!=''){	
				if(data.length>0 && data.StatusCode == null){
					for(var i=0;i<data.length;i++){
						htmlAppender+='<option value="'+data[i]+'">'+data[i]+'</option>';
					}
					 $('.selected').append(htmlAppender);
				}
				else{
					alert('無部門代碼資料');
				}
			 }else{
				alert('無部門代碼資料');
			 }
			}
		});   
	}
	
	$('#dateOut').click(function(){
		Save1('連續上班超5天記錄')
	})
	
	function Save1(name){
		var filename = name+ new Date().getFullYear() + "-"
		+ (new Date().getMonth()+1) + "-"
		+ new Date().getDate() + " "
		+ new Date().getHours() + ":"
		+ new Date().getMinutes();
		$('#SwipeCardRecords').tableExport({type:'excel'},filename);
	};
})