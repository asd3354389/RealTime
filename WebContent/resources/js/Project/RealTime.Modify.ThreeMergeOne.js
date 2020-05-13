$(document).ready(function(){
	$('#outputExcel').click(function(){
		var type=$('input:radio[name="changeType"]:checked').val();
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		var data = $('#data').val();
		$.ajax({
			type:'GET',
			url:'../ThreeMergeOne/outPutExcel',
			data:{
				StartDate:startDate,
				EndDate:endDate,
				Type:type,
				Data:data
			},
			success:function(res){
				if(res.StatusCode=='200'){
					alert(res.Message)
					 $('#data').val('');
				}else{
					alert(res.Message)
				}
			},
			error:function(){
				alert('發生錯誤！')
			}
		})
	})
})