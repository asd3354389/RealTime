$(document)
		.ready(
				function() {
					
					// 把上传的txt文件内容显示在输入框中，此处是工号
					$("#fu1").change(
							function() {
								// var content =
								// document.getElementById('empno');
								$("#empno").val("");
								getFileContent(this, function(str) {
									$("#empno").val(
											str.replace(/\s+/g, ',').replace(
													/,$/, ""));
									var sumIds = $("#empno").val();
									var idLength = sumIds.split(",");
									// alert(idLength)
									$("#sumId").text(idLength.length);
								});
								$("#fu1").val("");
							});

					// 把上传的txt文件显示在输入框中，此处是费用代码
					$("#fu2").change(
							function() {
								// var content =
								// document.getElementById('empno');
								$("#costid").val("");
								getFileContent(this, function(str) {
									$("#costid").val(
											str.replace(/\s+/g, ',').replace(
													/,$/, ""));
									var sumCostids = $("#costid").val();
									var costidLength = sumCostids.split(",");
									// alert(idLength)
									$("#sumCostid").text(costidLength.length);
								});
								$("#fu2").val("");
							});
					// 把上传的txt文件显示在输入框中，此处是部门代码
					$("#fu3").change(
							function() {
								// var content =
								// document.getElementById('empno');
								$("#deptno").val("");
								getFileContent(this, function(str) {
									$("#deptno").val(
											str.replace(/\s+/g, ',').replace(
													/,$/, ""));
									var sumDepts = $("#deptno").val();
									var depidLength = sumDepts.split(",");
									// alert(idLength)
									$("#sumDept").text(depidLength.length);
								});
								$("#fu3").val("");
							});
					var curPage = 1;
					// 显示全部信息，方便导出
					$("#showAll").click(function() {
						$('#overTime15minPagination').empty();
										/*var ccostid = $("#costid").val();
										if (ccostid.length >= 5) {
											alert("請輸入正確的費用代碼");
											$("#costid").val("");
											$("#costid").focus();

										}*/

										var ssDate = $("#dpick1").val();
										var eeDate = $("#dpick2").val();
										if (ssDate == "" || eeDate == "") {
											alert("請選擇查詢日期")
											return;
										} /*else if (((ssDate && eeDate) != ""
												&& empNo == "" && ccostid == "")) {
											alert("請輸入員工ID或費用代碼")
										}*/
										
												$.ajax({
													type : "post",
													url : 'CheckOT15minJsonAll.show',
													// dataType:"json",
													async : false,
													// data :
													// {curPage:curPage,$("#form1").serialize()},
													data : $('#form1').serialize(),
													error : function(
															XMLHttpRequest,
															textStatus,
															errorThrown) {
														alert(XMLHttpRequest.status);
														alert(XMLHttpRequest.readyState);
														alert(textStatus);
													},
													success : function(data) {
													  if(data!=null && data!=''){
														  if (data.length > 0) {
																var sumdata = data.length;
																$("#sumData").text(sumdata);
																var tableBodyElement = '';

																$('#OverTime15minRecord tbody').empty();
																for (var i = 0; i < data.length; i++) {
																	tableBodyElement += '<tr><td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].id + '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].Name + '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' 
																		+ data[i].costid + '</td>' 
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].depid + '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].depname + '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].class_no + '</td>'
																		+ '<td>' + data[i].swipecardtimeg
																		+ '</td>'
																		+ '<td>' + data[i].swipecardtimeo
																		+ '</td>' 
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].class_start + '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].goWorkAdvance + '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].overtime_start + '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].outWorkOvertime + '</td>'
																		+ '</tr>';
																}

																$('#OverTime15minRecord tbody').append(tableBodyElement);

															} else {
																alert("查詢數據為空");
																$("#empno").focus();
																$("#costId").val("");
																$("#costId").focus();
															}
														}else{
															 alert("查詢數據為空");
																// $("#empno").val("");
																$("#empno").focus();
																$("#costId").val("");
																$("#costId").focus();
														 }
														// refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
													}
												});

									});
					$("#showAllHistory").click(function() {
						$('#overTime15minPagination').empty();
										/*var ccostid = $("#costid").val();
										if (ccostid.length >= 5) {
											alert("請輸入正確的費用代碼");
											$("#costid").val("");
											$("#costid").focus();

										}*/

										var ssDate = $("#dpick1").val();
										var eeDate = $("#dpick2").val();
										if ((ssDate || eeDate) == "") {
											alert("請選擇開始時間和結束時間")
											return;
										} /*else if (((ssDate && eeDate) != ""
												&& empNo == "" && ccostid == "")) {
											alert("請輸入員工ID或費用代碼")
										}*/
										
												$.ajax({
													type : "post",
													url : 'CheckOT15minHistoryJsonAll.show',
													// dataType:"json",
													async : false,
													// data :
													// {curPage:curPage,$("#form1").serialize()},
													data : $('#form1').serialize(),
													error : function(
															XMLHttpRequest,
															textStatus,
															errorThrown) {
														alert(XMLHttpRequest.status);
														alert(XMLHttpRequest.readyState);
														alert(textStatus);
													},
													success : function(data) {
													  if(data!=null && data!=''){
														  if (data.length > 0) {
																var sumdata = data.length;
																$("#sumData").text(sumdata);
																var tableBodyElement = '';

																$('#OverTime15minRecord tbody').empty();
																for (var i = 0; i < data.length; i++) {
																	tableBodyElement += '<tr><td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].id + '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].Name + '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' 
																		+ data[i].costid + '</td>' 
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].depid + '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].depname + '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].class_no + '</td>'
																		+ '<td>' + data[i].swipecardtimeg
																		+ '</td>'
																		+ '<td>' + data[i].swipecardtimeo
																		+ '</td>' 
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].class_start + '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].goWorkAdvance + '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].overtime_start + '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].outWorkOvertime + '</td>'
																		+ '</tr>';
																}

																$('#OverTime15minRecord tbody').append(tableBodyElement);

															} else {
																alert("查詢數據為空");
																$("#empno").focus();
																$("#costId").val("");
																$("#costId").focus();
															}
														}else{
															 alert("查詢數據為空");
																// $("#empno").val("");
																$("#empno").focus();
																$("#costId").val("");
																$("#costId").focus();
														 }
														// refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
													}
												});

									});
					$("#chk").click(function() {
						var checkbox = $("#chk").is(':checked');
						var checkbox2 = $("#chk1").is(':checked');
						var checkbox3 = $("#chk2").is(':checked');
						if (checkbox == true) {
							$("#empno").attr("readonly", true);
							$("#empno").val("");
							$("#costid").attr("readonly", true);
							$("#costid").val("");
							$("#sumCostid").text("0");
							$("#sumDept").text("0");
							$("#deptno").attr("readonly", true);
							$("#deptno").val("");
							if (checkbox2 == true || checkbox3 == true) {
								$("#chk1").prop("checked", false);
								$("#chk2").prop("checked", false);
							}
							$("#fu1").attr("disabled", false);
							$("#span1").attr("disabled", false);
							$("#fu2").attr("disabled", true);
							$("#span2").attr("disabled", true);
							$("#fu3").attr("disabled", true);
							$("#span3").attr("disabled", true);
						} else {
							$("#empno").val("");
							$("#fu1").val("");
							$("#sumCostid").text("0");
							$("#sumId").text("0");
							$("#sumDept").text("0");
							$("#empno").attr("readonly", false);
							$("#costid").attr("readonly", false);
							$("#deptno").attr("readonly", false);
							$("#fu1").attr("disabled", true);
							$("#span1").attr("disabled", true);
							$("#fu3").attr("disabled", true);
							$("#span3").attr("disabled", true);
						}

					});
					$("#chk1").click(function() {
						var checkbox = $("#chk").is(':checked');
						var checkbox2 = $("#chk1").is(':checked');
						var checkbox3 = $("#chk2").is(':checked');
						if (checkbox2 == true) {
							$("#sumId").text("0");
							$("#sumDept").text("0");
							$("#costid").attr("readonly", true);
							$("#costid").val("");
							$("#empno").attr("readonly", true);
							$("#empno").val("");
							$("#deptno").attr("readonly", true);
							$("#deptno").val("");
							if (checkbox == true || checkbox3 == true) {
								$("#chk").prop("checked", false);
								$("#chk2").prop("checked", false);
							}

							$("#fu2").attr("disabled", false);
							$("#span2").attr("disabled", false);
							$("#fu3").attr("disabled", true);
							$("#span3").attr("disabled", true);
							$("#fu1").attr("disabled", true);
							$("#span1").attr("disabled", true);
						} else {
							$("#fu1").val("");
							$("#fu2").val("");
							$("#costid").val("");
							$("#sumCostid").text("0");
							$("#sumId").text("0");
							$("#sumDept").text("0");
							$("#empno").attr("readonly", false);
							$("#costid").attr("readonly", false);
							$("#deptno").attr("readonly", false);
							$("#fu2").attr("disabled", true);
							$("#span2").attr("disabled", true);
							$("#fu3").attr("disabled", true);
							$("#span3").attr("disabled", true);
						}

					});
					$("#chk2").click(function() {
						var checkbox = $("#chk").is(':checked');
						var checkbox2 = $("#chk1").is(':checked');
						var checkbox3 = $("#chk2").is(':checked');
						if (checkbox3 == true) {
							$("#sumCostid").text("0");
							$("#sumId").text("0");
							$("#costid").attr("readonly", true);
							$("#costid").val("");
							$("#empno").attr("readonly", true);
							$("#empno").val("");
							$("#deptno").attr("readonly", true);
							$("#deptno").val("");
							if (checkbox == true || checkbox2 == true) {
								$("#chk").prop("checked", false);
								$("#chk1").prop("checked", false);
							}
							$("#fu1").attr("disabled", true);
							$("#span1").attr("disabled", true);
							$("#fu2").attr("disabled", true);
							$("#span2").attr("disabled", true);
							$("#fu3").attr("disabled", false);
							$("#span3").attr("disabled", false);
						} else {
							$("#fu1").val("");
							$("#fu2").val("");
							$("#fu3").val("");
							$("#costid").val("");
							$("#deptno").val("");
							$("#empno").val("");
							$("#sumCostid").text("0");
							$("#sumId").text("0");
							$("#sumDept").text("0");
							$("#empno").attr("readonly", false);
							$("#costid").attr("readonly", false);
							$("#deptno").attr("readonly", false);
							$("#fu3").attr("disabled", true);
							$("#span3").attr("disabled", true);
						}
					})
					// 清空查询
					$("#clearTable").click(function() {
						$("#empno").val("");
						$("#costid").val("");
						$("#dpick1").val("");
						$("#dpick2").val("");
						$("#deptno").val("");
						$("#SwipeCardRecords tbody").html("");
					})
					$("#submit").click(function() {
						// var checkbox =
						// $("input[type='checkbox']").is(':checked');
						var empNo = $("#empno").val();
						/*if (empNo.length >= 8) {
							alert("請輸入正確的ID")
							$("#empno").val("");
							$("#empno").focus();
							return;
						}*/
						var ccostid = $("#costid").val();
						/*if (ccostid.length >= 5) {
							alert("請輸入正確的費用代碼");
							$("#costid").val("");
							$("#costid").focus();

						}*/

						var ssDate = $("#dpick1").val();
						var eeDate = $("#dpick2").val();
						if ((ssDate || eeDate) == "") {
							alert("請選擇開始時間和結束時間")
							// $("#dpick1").focus();
							// $("#dpick2").focus();
							return;
						}/*
							 * else if(((ssDate && eeDate) !=
							 * ""&&empNo==""&&ccostid=="")){
							 * alert("請輸入員工ID或費用代碼") }
							 */
						// else if((ssDate && eeDate) != ""&&ccostid==""){
						// alert("請輸入員工費用代碼")
						// }
						getAsyncAccountInfo();

					});

					$("#exportData").click(
							function() {
								$("#SwipeCardRecords").table2excel(
										{exclude : ".noExl", // 过滤位置的 css 类名
											filename : "員工刷卡數據"
													+ new Date().getFullYear()
													+ new Date().getMonth()
													+ new Date().getDate()
													+ new Date().getHours()
													+ new Date().getMinutes(), // 文件名称
											name : "Excel Document Name.xlsx",
											exclude_img : true,
											exclude_links : true,
											exclude_inputs : true

										});
							});
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
									getAsyncAccountInfo(curPage, $('#form1')
											.serialize());
								});

						$('.previousPage').click(
								function() {
									curPage = currentPage - 1;
									getAsyncAccountInfo(curPage, $('#form1')
											.serialize());
								});

						$('.nextPage').click(
								function() {
									curPage = currentPage + 1;
									getAsyncAccountInfo(curPage, $('#form1')
											.serialize());
								});

						$('.numPage').click(
								function() {
									var curPage = $(this).text();
									getAsyncAccountInfo(curPage, $('#form1')
											.serialize());
								});

					}
					function getAsyncAccountInfo() {
						var data1 = $.param({
							'curPage' : curPage
						}) + '&' + $('#form1').serialize();
						$.ajax({
							type : "post",
							url : 'CheckSCJson.show',
							// dataType:"json",
							async : false,
							// data : {curPage:curPage,$("#form1").serialize()},
							data : data1,
							error : function(e) {
								alert('找不到資料');
							},
							success : function(rawData) {
								  if(rawData!=null && rawData!=''){
									  var executeResult = rawData["list"];
									  var errorResponse = executeResult.ErrorMessage;
									  if (errorResponse != null) {
										  alert(errorResponse);
									  } else {
										  var numOfRecords = executeResult.length;
										  if (numOfRecords > 0)
											  ShowAllAccountTable(rawData);
										  else
											  alert('找不到資料');
									  }
								  }else{
									  alert('找不到資料');
								  }
							}
						});

					}
					function ShowAllAccountTable(data) {
						var currentPage = data.currentPage;
						var totalRecord = data.totalRecord;
						var totalPage = data.totalPage;
						var pageSize = data.pageSize;
						var queryResult = data['list'];
						if (queryResult.length > 0) {
							var sumdata = queryResult.length;
							$("#sumData").text(totalRecord);
							var tableBodyElement = '';

							$('#SwipeCardRecords tbody').empty();
							for (var i = 0; i < queryResult.length; i++) {
								tableBodyElement += '<tr><td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ queryResult[i].id + '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ queryResult[i].name + '</td>'
										+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + queryResult[i].depid
										+ '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ queryResult[i].costid + '</td>'
										+ '<td>' + queryResult[i].swipe_date
										+ '</td>'
										+ '<td>' + queryResult[i].SwipeCardTime
										+ '</td>' + '<td>'
										+ queryResult[i].SwipeCardTime2
										+ '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ queryResult[i].WorkshopNo
										+ '</td></tr>';
							}

							$('#SwipeCardRecords tbody').append(
									tableBodyElement);
						}
						refreshUserInfoPagination(currentPage, totalRecord,
								totalPage, pageSize);
					}
				});
function Save1(name){
	var filename = name+ new Date().getFullYear() + "-"
	+ (new Date().getMonth()+1) + "-"
	+ new Date().getDate() + " "
	+ new Date().getHours() + ":"
	+ new Date().getMinutes();
	$('#OverTime15minRecord').tableExport({type:'excel'},filename);
};
