$(document).ready(function(){
	$("#id-query2").click(function(){
		$("#idEcharts").empty();
		var varCostID=$('#CostID').val();
		var varStartTime=$('#id-StartTimeValue').val();
		var varEndTime=$('#id-EndTimeValue').val();
		if(varStartTime==="null" || varStartTime==''){alert("請選擇開始時間！");return;}
		if(varEndTime==="null" || varEndTime==''){alert("請選擇結束時間！");return;}
		$('#id-query2').attr("disabled",true);
		$.ajax({
			   url:"../EChartss/getChartSwipeCardABByID", 
			   type:'post',
			   data:{varStartTime:varStartTime,varEndTime:varEndTime},
			   success:function(data){
				if(data == null || data == ''){
					alert("查無料號資料！");
					$('#id-query2').attr("disabled",false);
					return;
				}else{
					creatTable(data);
					$('#id-query2').attr("disabled",false);
				}
			   }, 
			   error:function(err){ 
			    alert("查詢ALERT_SWIPECARD_AB_WECHAT，失敗原因："+err); 
			    $('#id-query2').attr("disabled",false);
			   } 
		}) 
	});
	function creatTable(data){
		 
		 var tableData="";		 
		 for(var i=0;i<data.length;i++){
			 var varStatus=data[i]['STATUS'];
			 switch (varStatus) {
			case '1':
				varStatus = '上班超時15分鐘刷卡';
				break;
			case '2':
				varStatus = '下班超時15分鐘刷卡';
				break;
			case '3':
				varStatus = '超7休1';
				break;
			case '4':
				varStatus = '下班超15分鐘未刷卡';
				break;
			}
		  tableData+='<tr style="text-align:center;">';
		  tableData+='<td>'+data[i]['ID']+'</td>';
		  tableData+="<td>"+data[i]['NAME']+"</td>";
		  tableData+="<td>"+data[i]['DEPID']+"</td>";
		  tableData+="<td>"+data[i]['DEPTID']+"</td>";
		  tableData+="<td>"+data[i]['COSTID']+"</td>";
		  tableData+="<td>"+varStatus+"</td>";
		  tableData+="<td>"+data[i]['SUM']+"</td>";
		  tableData+="</tr>"
		 }		 
		 
		 $("#tbody1").html(tableData)
		}
});