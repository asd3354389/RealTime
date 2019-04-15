$(document)
		.ready(
				function() {
					// 把上传的txt文件内容显示在输入框中，此处是工号
					$("#fu1").change(function() {
						// var content = document.getElementById('empno');
						$("#empno").val("");
						getFileContent(this, function(str) {
							$("#empno").val(str.replace(/\s+/g, ',').replace(/,$/,""));
							var sumIds = $("#empno").val();
							var idLength = sumIds.split(",");
							// alert(idLength)
							$("#sumId").text(idLength.length);
						});
						$("#fu1").val("");
					});

					// 把上传的txt文件显示在输入框中，此处是费用代码
					$("#fu2").change(function() {
						// var content = document.getElementById('empno');
						$("#costid").val("");
						getFileContent(this, function(str) {
							$("#costid").val(str.replace(/\s+/g, ',').replace(/,$/,""));
							var sumCostids = $("#costid").val();
							var costidLength = sumCostids.split(",");
							// alert(idLength)
							$("#sumCostid").text(costidLength.length);
						});
						$("#fu2").val("");
					});
					var curPage = 1;
					// 显示全部信息，方便导出
					$("#showAll")
							.click(
									function() {
										$('#overTimeInfoPagination').empty();
										// var checkbox =
										// $("input[type='checkbox']").is(':checked');
										var empNo = $("#empno").val();
										/*
										 * if (empNo.length >= 8) {
										 * alert("請輸入正確的ID")
										 * $("#empno").val("");
										 * $("#empno").focus(); return; }
										 */
										var ccostid = $("#costid").val();
										if (ccostid.length >= 5) {
											alert("請輸入正確的費用代碼");
											$("#costid").val("");
											$("#costid").focus();

										}

										var ssDate = $("#dpick1").val();
										var eeDate = $("#dpick2").val();
										if ((ssDate || eeDate) == "") {
											alert("請選擇開始時間和結束時間")
											// $("#dpick1").focus();
											// $("#dpick2").focus();
											return;
										} else if (((ssDate && eeDate) != ""
												&& empNo == "" && ccostid == "")) {
											alert("請輸入員工ID或費用代碼")
										}
										// getAsyncAccountInfo();
										// else if((ssDate && eeDate) !=
										// ""&&ccostid==""){
										// alert("請輸入員工費用代碼")
										// }

										// getAsyncAccountInfo(curPage,$("#form1").serialize());
										// var data1 = $.param({'curPage':
										// curPage}) + '&' +
										// $('#form1').serialize();
										$
												.ajax({
													type : "post",
													url : 'CheckOverTimeStatusJsonAll.show',
													// dataType:"json",
													async : false,
													// data :
													// {curPage:curPage,$("#form1").serialize()},
													data : $('#form1')
															.serialize(),
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
															var sumdata = data.length;
															$("#sumData").text(
																	sumdata);
															var tableBodyElement = '';

															$(
																	'#OverTimeStatus tbody')
																	.empty();
															for (var i = 0; i < data.length; i++) {
																var noteState=data[i].NOTESSTATES;
																var noteStates="";
																switch (noteState) {
																case 0:
																	noteStates="未生成加班單";
																	
																	break;
																case 1:
																	noteStates="已生成加班單";
																	break;
																case 2:
																	noteStates="生成加班單異常";
																	break;
																}
																tableBodyElement += '<tr><td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].id
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].name
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].depid
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].costId
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].direct
																		+ '</td>'
																		+ '<td>'
																		+ data[i].OVERTIMEDATE
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].SHIFT
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].WORKCONTENT
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].OVERTIMEHOURS
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].OVERTIMETYPE
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].OVERTIMEINTERVAL
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].bonus
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].APPLICATION_PERSON
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].APPLICATION_ID
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		/*+ data[i].NOTESSTATES*/
																		+ noteStates
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].REASON
																		+ '</td>'
																		+ '<td>'
																		+ data[i].BACKTIME
																		+ '</td>'
																		+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
																		+ data[i].WORKSHOPNO
																		+ '</td></tr>';
															}

															$(
																	'#OverTimeStatus tbody')
																	.append(
																			tableBodyElement);

														} else {
															alert("查詢數據為空");
															// $("#empno").val("");
															$("#empno").focus();
															$("#costId")
																	.val("");
															$("#costId")
																	.focus();
														}
														// refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
													}
												});

									});
					$("#chk").click(function() {
						var checkbox = $("#chk").is(':checked');
						var checkbox2 = $("#chk1").is(':checked');
						if (checkbox == true) {
							$("#empno").attr("readonly", true);
							$("#empno").val("");
							$("#costid").attr("readonly", true);
							$("#costid").val("");
							$("#sumCostid").text("0");
							//$("#form1")[0].reset();
							$("#fu1").val("");
							if (checkbox2 == true) {
								$("#chk1").prop("checked", false);
							}
							$("#fu1").attr("disabled", false);
							$("#span1").attr("disabled", false);
							$("#fu2").attr("disabled", true);
							$("#span2").attr("disabled", true);
						} else {
							$("#empno").attr("readonly", false);
							$("#empno").val("");
							$("#costid").val("");
							$("#sumCostid").text("0");
							$("#sumId").text("0");
							$("#fu1").val("");
							$("#costid").attr("readonly", false);
							$("#fu1").attr("disabled", true);
							$("#span1").attr("disabled", true);
						}

					});
					$("#chk1").click(function() {
						var checkbox = $("#chk").is(':checked');
						var checkbox2 = $("#chk1").is(':checked');
						if (checkbox2 == true) {
							$("#costid").attr("readonly", true);
							$("#costid").val("");
							if (checkbox == true) {
								$("#chk").prop("checked", false);
							}
							$("#empno").attr("readonly", true);
							$("#empno").val("");
							$("#fu2").val("");
							$("#sumId").text("0");
							$("#fu2").attr("disabled", false);
							$("#span2").attr("disabled", false);
							$("#fu1").attr("disabled", true);
							$("#span1").attr("disabled", true);
						} else {
							//解决input为file时，因文件名重复输入失效的问题
							$("#fu2").val("");
							$("#empno").val("");
							$("#costid").val("");
							$("#sumCostid").text("0");
							$("#sumId").text("0");
							$("#empno").attr("readonly", false);
							$("#costid").attr("readonly", false);
							$("#fu2").attr("disabled", true);
							$("#span2").attr("disabled", true);
						}

					});
					// 清空查询
					$("#clearTable").click(function() {
						$("#OverTimeStatus tbody").html("");
						$("#empno").val("");
						$("#costid").val("");
						$("#dpick1").val("");
						$("#dpick2").val("");
					})
					$("#submit").click(
							function() {
								// var checkbox =
								// $("input[type='checkbox']").is(':checked');
								var empNo = $("#empno").val();
								/*
								 * if (empNo.length >= 8) { alert("請輸入正確的ID")
								 * $("#empno").val(""); $("#empno").focus();
								 * return; }
								 */
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
								} else if (((ssDate && eeDate) != ""
										&& empNo == "" && ccostid == "")) {
									alert("請輸入員工ID或費用代碼")
								}
								getAsyncAccountInfo();
								// else if((ssDate && eeDate) !=
								// ""&&ccostid==""){
								// alert("請輸入員工費用代碼")
								// }

								// getAsyncAccountInfo(curPage,$("#form1").serialize());
								/*
								 * var data1 = $.param({'curPage': curPage}) +
								 * '&' + $('#form1').serialize(); $.ajax({ type :
								 * "post", url : 'CheckOverTimeStatusJson.show', //
								 * dataType:"json", async : false, //data :
								 * {curPage:curPage,$("#form1").serialize()},
								 * data : data1, error :
								 * function(XMLHttpRequest, textStatus,
								 * errorThrown) { alert(XMLHttpRequest.status);
								 * alert(XMLHttpRequest.readyState);
								 * alert(textStatus); }, success :
								 * function(data) { var
								 * currentPage=data.currentPage; var
								 * totalRecord=data.totalRecord; var
								 * totalPage=data.totalPage; var
								 * pageSize=data.pageSize; var
								 * queryResult=data['list']; if
								 * (queryResult.length > 0) { var sumdata =
								 * queryResult.length;
								 * $("#sumData").text(sumdata); var
								 * tableBodyElement = '';
								 * 
								 * $('#OverTimeStatus tbody').empty(); for (var
								 * i = 0; i < queryResult.length; i++) {
								 * tableBodyElement += '<tr><td>' +
								 * queryResult[i].id + '</td>' + '<td>' +
								 * queryResult[i].name + '</td>' + '<td>' +
								 * queryResult[i].depid + '</td>' + '<td>' +
								 * queryResult[i].costId + '</td>' + '<td>' +
								 * queryResult[i].direct + '</td>' + '<td>' +
								 * queryResult[i].OVERTIMEDATE + '</td>' + '<td>' +
								 * queryResult[i].SHIFT + '</td>' + '<td>' +
								 * queryResult[i].WORKCONTENT + '</td>' + '<td>' +
								 * queryResult[i].OVERTIMEHOURS + '</td>' + '<td>' +
								 * queryResult[i].OVERTIMETYPE + '</td>' + '<td>' +
								 * queryResult[i].OVERTIMEINTERVAL + '</td>' + '<td>' +
								 * queryResult[i].APPLICATION_PERSON + '</td>' + '<td>' +
								 * queryResult[i].APPLICATION_ID + '</td>' + '<td>' +
								 * queryResult[i].NOTESSTATES + '</td>' + '<td>' +
								 * queryResult[i].REASON + '</td>' + '<td>' +
								 * queryResult[i].BACKTIME + '</td>' + '<td>' +
								 * queryResult[i].WORKSHOPNO + '</td></tr>'; }
								 * 
								 * $('#OverTimeStatus tbody').append(
								 * tableBodyElement);
								 *  } else { alert("查詢數據為空"); //
								 * $("#empno").val(""); $("#empno").focus();
								 * $("#costId").val(""); $("#costId").focus(); }
								 * refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize); }
								 * });
								 */

							});
					//导出数据为Excel文档
					$("#exportData").click(
							function() {

								$("#OverTimeStatus").table2excel(
										{
											exclude : ".noExl", // 过滤位置的 css 类名
											filename : "員工加班數據"
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
						$('#overTimeInfoPagination').empty();
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

						$('#overTimeInfoPagination').append(paginationElement);

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
						// alert($("#empno").val());
						var data1 = $.param({
							'curPage' : curPage
						}) + '&' + $('#form1').serialize();
						$.ajax({
							type : "post",
							url : 'CheckOverTimeStatusJson.show',
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
					function ShowAllAccountTable(rawData) {
						var currentPage = rawData.currentPage;
						var totalRecord = rawData.totalRecord;
						var totalPage = rawData.totalPage;
						var pageSize = rawData.pageSize;
						var queryResult = rawData['list'];
						if (queryResult.length > 0) {
							var sumdata = queryResult.length;
							$("#sumData").text(totalRecord);
							var tableBodyElement = '';

							$('#OverTimeStatus tbody').empty();
							for (var i = 0; i < queryResult.length; i++) {
								var noteState=queryResult[i].NOTESSTATES;
								var noteStates="";
								switch (noteState) {
								case 0:
									noteStates="未生成加班單";
									
									break;
								case 1:
									noteStates="已生成加班單";
									break;
								case 2:
									noteStates="生成加班單異常";
									break;
								} 
								tableBodyElement += '<tr><td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ queryResult[i].id + '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ queryResult[i].name + '</td>'
										+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + queryResult[i].depid
										+ '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ queryResult[i].costId + '</td>'
										+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + queryResult[i].direct
										+ '</td>' + '<td>'
										+ queryResult[i].OVERTIMEDATE + '</td>'
										+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + queryResult[i].SHIFT
										+ '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ queryResult[i].WORKCONTENT + '</td>'
										+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + queryResult[i].OVERTIMEHOURS
										+ '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ queryResult[i].OVERTIMETYPE + '</td>'
										+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ queryResult[i].OVERTIMEINTERVAL
										+ '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ queryResult[i].bonus
										+ '</td>'+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ queryResult[i].APPLICATION_PERSON
										+ '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ queryResult[i].APPLICATION_ID
										+ '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
										+ noteStates + '</td>'
										+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + queryResult[i].REASON
										+ '</td>' + '<td>'
										+ queryResult[i].BACKTIME + '</td>'
										+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + queryResult[i].WORKSHOPNO
										+ '</td></tr>';
							}

							$('#OverTimeStatus tbody').append(tableBodyElement);
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
	$('#OverTimeStatus').tableExport({type:'excel'},filename);
};
