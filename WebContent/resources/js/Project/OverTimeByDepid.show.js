$(document).ready(function() {
	var SDate;
    var EDate;
    /*測試用*/
    var WorkshopNo = '%',byWorkShopOrLineNo='byLineNo',LineNo='';
//    var ASSISTANT_ACCOUNT = '47956';
    var RC_NO = null;
    var CheckState;
    var ClassNo = null;
    init();
    
    $('#showRCInforByDate').click(function() {
        /*查詢*/
        $('#OTSheetWithRCNo').empty();
        $('#OTSheetWithoutRCNo').empty();
        CheckState = $('#checkState').find('option:selected').val();
        LineNo = $('#LineNo').find('option:selected').val();
        SDate = $('#dpick1').val();
        EDate = $('#dpick2').val();
        if (CheckBeforeQuery()) {
            //取得有RCNO的加班單
          //  RC_NO = $('#OTSheetWithRCNo tbody tr').children().eq(3).text();
            GetOTSheetsWithRCFromServer(SDate, EDate, CheckState,byWorkShopOrLineNo, WorkshopNo,LineNo, 0);
            //取得無RCNO的加班單
            GetOTSheetsWithoutRCFromServer(SDate, EDate, CheckState,byWorkShopOrLineNo, WorkshopNo,LineNo, 0);
        }

    });
    
    $('#showSwipeCardAbnormal').click(function() {
        /*忘卡查詢*/
        $('#OTSheetWithRCNo').empty();
        $('#OTSheetWithoutRCNo').empty();
        CheckState = $('#checkState').find('option:selected').val();
        LineNo = $('#LineNo').find('option:selected').val();
        SDate = $('#dpick1').val();
        EDate = $('#dpick2').val();
        if (CheckBeforeQuery()) {
            //取得有RCNO的加班單(異常)
           // RC_NO = $('#OTSheetWithRCNo tbody tr').children().eq(3).text();
            GetOTSheetsWithRCFromServer(SDate, EDate, CheckState,byWorkShopOrLineNo, WorkshopNo,LineNo, 1);
            //取得無RCNO的加班單(異常)
            GetOTSheetsWithoutRCFromServer(SDate, EDate, CheckState,byWorkShopOrLineNo, WorkshopNo,LineNo, 1);
        }
    });
    
    function GetOTSheetsWithRCFromServer(SDate, EDate, CheckState,byWorkShopOrLineNo, WorkshopNO,LineNo, isAbnormal) {
        var CheckStateArray = $('#checkState').find('option:selected').val();
        
        $.ajax({
            type: 'GET',
            url: 'AllOTSheetsByDepid.show',
            data: {
                "RCNO": "TRUE",
                "CheckState": CheckStateArray,
                "WorkshopNO": WorkshopNO,
                "LineNo": LineNo,
                "StartDate": SDate,
                "EndDate": EDate,
                "IsAbnormal": isAbnormal
            },
            error: function(e) {
                alert(e);
            },
            success: function(result) {            	
            	 $('#OTSheetWithRCNo').empty();
                $('#RC_DIV').css('visibility', 'visible');
                var TableThead = '',HTMLElement = '';
             if(result!=null && result!=''){
                if (result.ErrorCode && result.ErrorCode == 500) {
                    HTMLElement = '<tbody><tr><td colspan="8" align="center">無資料</td></tr></tbody>'
                    $('#OTSheetWithRCNo').append(HTMLElement);
                } else {                	
                	if(byWorkShopOrLineNo=='byLineNo'){
                		TableThead ='<thead><tr>'+
						'<th>車間</th>'+
						'<th>線體</th>'+
						'<th>日期</th>'+
						'<th>班別</th>'+
						'<th>指示單號</th>'+
						'<th>料號</th>'+
						'<th>標準人數</th>'+
						'<th>實際加班人數</th>'+
						'<th>詳情</th></tr></thead>'+
						'<tbody></tbody>';
                    }else{
                    	TableThead ='<thead><tr>'+
						'<th>車間</th>'+
						'<th>日期</th>'+
						'<th>班別</th>'+
						'<th>指示單號</th>'+
						'<th>料號</th>'+
						'<th>標準人數</th>'+
						'<th>實際加班人數</th>'+
						'<th>詳情</th></tr></thead>'+
						'<tbody></tbody>';
                    }
                	  $('#OTSheetWithRCNo').append(TableThead);
                    for (var i = 0; i < result.length; i++) {
                    	  HTMLElement = '<tr><td>' + result[i]["WorkshopNo"] + '</td>';
                    	  if(byWorkShopOrLineNo=='byLineNo'){
                           	if(result[i]["LineNo"]==='0')
                           		HTMLElement +='<td>' + '' + '</td>';
                           	else
                           		HTMLElement +='<td>' + result[i]["LineNo"] + '</td>';                       	
                           } 
             HTMLElement += '<td>' + result[i]["SDate"] + '</td>' +
                            '<td>' + result[i]["ClassNo"] + '</td>' +
                            '<td>' + result[i]["RCNo"] + '</td>' +
                            '<td>' + result[i]["PrimaryItemNo"] + '</td>' +
                            '<td>' + result[i]["StdManPower"] + '</td>' +
                            '<td>' + result[i]["Mount"] + '/' + result[i]["TotalManPower"] + '</td>' +
                            '<td><input type="button" class="RCdetailBtn btn btn-primary btn-sm" value="詳情"></td></tr>';
                        $('#OTSheetWithRCNo tbody').append(HTMLElement);
                    }

                    $('.RCdetailBtn').click(function() {
                    	var parentElement = $(this).parent().parent();
                    	 var CheckStateArray = $('#checkState').find('option:selected').val();   
                    	 WorkshopNO = $(parentElement).children().eq(0).text();
                    	 if(byWorkShopOrLineNo=='byLineNo'){
                    		 LineNo = $(parentElement).children().eq(1).text();
                    		 if(LineNo=='null')
                         		LineNo='';
                    		 RC_NO = $(parentElement).children().eq(4).text();
                    		 ClassNo = $(parentElement).children().eq(3).text();
                    		 var OTDate = $(parentElement).children().eq(2).text();
                    		 var ItemNumber=$(parentElement).children().eq(5).text();                      
                    	 }else{
                    		 LineNo='%';
                    		 RC_NO = $(parentElement).children().eq(3).text();
                    		 ClassNo = $(parentElement).children().eq(2).text();
                    		 var OTDate = $(parentElement).children().eq(1).text();
                    		 var ItemNumber=$(parentElement).children().eq(4).text();                    		                    
                    	 }
                    	 ShowEmployeesInOTSheet(WorkshopNO, LineNo, RC_NO, ClassNo, OTDate, CheckStateArray, isAbnormal,ItemNumber);
                    });
                }
                }else{
                	  HTMLElement = '<tbody><tr><td colspan="8" align="center">無資料</td></tr></tbody>'
                          $('#OTSheetWithRCNo').append(HTMLElement);
                }
            }
        });
    }
    
    function GetOTSheetsWithoutRCFromServer(SDate, EDate, CheckState,byWorkShopOrLineNo, WorkshopNO,LineNo, isAbnormal) {

        var CheckStateArray = $('#checkState').find('option:selected').val();  
        $.ajax({
            type: 'GET',
            url: 'AllOTSheetsByDepid.show',
            data: {
                "RCNO": null,
                "CheckState": CheckStateArray,
                "WorkshopNO": WorkshopNO,
                "LineNo": LineNo,
                "StartDate": SDate,
                "EndDate": EDate,
                "IsAbnormal": isAbnormal
            },
            error: function(e) {
                alert(e);
            },
            success: function(result) {
                var TableThead='',HTMLElement = '';
                $('#NO_RC_DIV').css('visibility', 'visible');
                if(result!=null && result!=''){
                	if (result.ErrorCode && result.ErrorCode == 500) {
                    //alert(result.ErrorMsg);
                		HTMLElement = '<tbody><tr><td colspan="5" align="center">無資料</td></tr></tbody>'
                			$('#OTSheetWithoutRCNo').append(HTMLElement);
                	} else {
                		if(byWorkShopOrLineNo=='byLineNo'){
                		 TableThead ='<thead><tr>'+
							'<th>車間</th>'+
							'<th>線體</th>'+
							'<th>日期</th>'+
							'<th>班別</th>'+
							'<th>實際加班人數</th>'+
							'<th>詳情</th></tr></thead>'+
							'<tbody></tbody>';                       	
                		}
                		else{
                    	 TableThead ='<thead><tr>'+
							'<th>車間</th>'+
							'<th>日期</th>'+
							'<th>班別</th>'+
							'<th>實際加班人數</th>'+
							'<th>詳情</th></tr></thead>'+
							'<tbody></tbody>';
                     }
                	  $('#OTSheetWithoutRCNo').append(TableThead);
                	  for (var i = 0; i < result.length; i++) {
                        HTMLElement = '<tr><td>' + result[i]["WorkshopNo"] + '</td>';
                        if(byWorkShopOrLineNo=='byLineNo'){
                        	if(result[i]["LineNo"]==='0')
                        		HTMLElement +='<td>' + '' + '</td>';
                        	else
                        		HTMLElement +='<td>' + result[i]["LineNo"] + '</td>';                       	
                        }                      
                        HTMLElement += '<td>' + result[i]["SDate"] + '</td>' +
                            '<td>' + result[i]["ClassNo"] + '</td>' +
                            '<td>' + result[i]["Mount"] + '/' + result[i]["TotalManPower"] + '</td>' +
                            '<td><input type="button" class="NORCdetailBtn btn btn-primary btn-sm" value="詳情"></td></tr>';
                        $('#OTSheetWithoutRCNo tbody').append(HTMLElement);
                    }
                    $('.NORCdetailBtn').click(function() {
                        var parentElement = $(this).parent().parent();
                        var CheckStateArray = $('#checkState').find('option:selected').val();
                        WorkshopNO = $(parentElement).find('td').eq(0).text();                       
                        RC_NO = '';
                        if(byWorkShopOrLineNo=='byLineNo'){
                        		LineNo = $(parentElement).children().eq(1).text();
                        		if(LineNo==='null')
                        			LineNo='';
                        		var OTDate = $(parentElement).find('td').eq(2).text();
                        		ClassNo = $(parentElement).find('td').eq(3).text();
                        		}else{ 
                        			LineNo='%';
                        			var OTDate = $(parentElement).find('td').eq(1).text();                        	
                        			ClassNo = $(parentElement).find('td').eq(2).text();
                        }          
                        	ShowEmployeesInOTSheet(WorkshopNO,LineNo, RC_NO, ClassNo, OTDate, CheckStateArray, isAbnormal,'');
                    	});
                	}
                }else{
                	HTMLElement = '<tbody><tr><td colspan="5" align="center">無資料</td></tr></tbody>'
            			$('#OTSheetWithoutRCNo').append(HTMLElement);
                }
            }
        });
    }
    
    function ShowEmployeesInOTSheet(WorkshopNO,LineNo, RC_NO, ClassNo, OTDate, CheckStateArray, isAbnormal,ItemNumber) {
        var checkState = $('#checkState').find('option:selected').val().split(":");
        var iWidth = 1200;
        var iHeight = 900;
        var iTop = (window.screen.availHeight - iHeight) / 2;
        var iLeft = (window.screen.availWidth - iWidth) / 2;

        if (checkState[0] == 0 && checkState[1] == 9) {
            //未審核		
            window.open('PendingByDepid.show?WorkshopNo=' + WorkshopNO + '&LineNo=' + LineNo + '&RCNO=' + RC_NO + '&ClassNo=' + ClassNo +
                '&OverTimeDate=' + OTDate + '&IsIdentified=0&IsAbnormal=' + isAbnormal+"&SDate="+SDate+"&EDate="+EDate+"&ItemNumber="+ItemNumber,
                '加班單詳情', 'height=' + iHeight + ', width=' + iWidth + ', top=' + iTop + ', left=' + iLeft + ',location=no');
        } else {
            //已審核
            window.open('IdentifiedByDepid.show?WorkshopNo=' + WorkshopNO + '&LineNo=' + LineNo + '&RCNO=' + RC_NO + '&ClassNo=' + ClassNo +
                '&OverTimeDate=' + OTDate + '&IsIdentified=1&IsAbnormal=' + isAbnormal, '加班單詳情',
                'height=' + iHeight + ', width=' + iWidth + ', top=' + iTop + ', left=' + iLeft + ',location=no,status=no');
        }
    }
    
    function init(){
    	GetWorkshopNoFromServer();
    }
    
    function GetWorkshopNoFromServer() {       
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
					 $('#WorkshopNo').append(htmlAppender);
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
    
    function CheckBeforeQuery() {
        var isValid = true;
        var errorMessage = '';
        if (!SDate || !EDate) {
            errorMessage += '沒有選取 起/迄 時間\n';
        }

        if (!CheckState) {
            errorMessage += '請選擇加班單狀態\n';
        }

        if (!WorkshopNo) {
            errorMessage += '請選擇車間\n';
        }

        if (errorMessage.length > 0) {
            alert(errorMessage);
            isValid = false;
        }

        return isValid;
    }
    
    $("#WorkshopNo").change(function () {
        WorkshopNO = $(this).val();
		getLineNo(WorkshopNO);
   });
    
    function getLineNo(WorkshopNO){
   	 $.ajax({
			type:'GET',
			url:'../WorkShop/GetLineNoByWorkShop.do',
			data:{workShopNo:WorkshopNO},
			async:false,
			success:function(data){
				var htmlAppender='';
				htmlAppender+='<option value="%" selected>All</option>';
				$('#LineNo').empty();
				if(data!=null && data!=''){
					if(data.length>0 && data.StatusCode == null){
						for(var i=0;i<data.length;i++){
							htmlAppender+='<option value="'+data[i]+'">'+data[i]+'</option>';
						}
						 $('#LineNo').append(htmlAppender);
					}
					else{
						alert('無線體資料');
						$('#LineNo').append(htmlAppender);
					}
				}else{
					alert('無線體資料');
					$('#LineNo').append(htmlAppender);
				}
			}
		});       
	}		
});