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

		  tableData+='<tr style="text-align:center;">';
		  tableData+='<td>'+data[i]['ID']+'</td>';
		  tableData+="<td>"+data[i]['NAME']+"</td>";
		  tableData+="<td>"+data[i]['COSTID']+"</td>";
		  tableData+="<td>"+data[i]['DEPTID']+"</td>";
		  tableData+="<td>"+data[i]['DEPID']+"</td>";
		  tableData+="<td>"+data[i]['DEPNAME']+"</td>";
		  tableData+="<td>"+data[i]['S1']+"</td>";
		  tableData+="<td>"+data[i]['S2']+"</td>";
		  tableData+="<td>"+data[i]['S3']+"</td>";
		  tableData+="<td>"+data[i]['S4']+"</td>";
		  tableData+="<td>"+data[i]['SSUM']+"</td>";
		  tableData+="</tr>";
		 }		 
		 
		 $("#tbody1").html(tableData);
		}
});