$(document).ready(function(){
	$('#outputExcel').click(function(){
		var type=$('input:radio[name="changeType"]:checked').val();
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		var data = $('#data').val();
		var errorMessage=''
	    if(startDate==null || startDate=="")
			errorMessage+='未選擇生效起始日期\n';
		if(endDate==null || endDate=="")
			errorMessage+='未選擇生效結束日期\n';	
		if(data==null || data=="")
			errorMessage+='輸入框不能爲空';
		if(errorMessage==''){
			$.ajax({
				type:'GET',
				url:'../ThreeMergeOne/judgeDownload',
				data:{
					StartDate:startDate,
					EndDate:endDate,
					Type:type,
					Data:data
				},
				success:function(res){
	//				console.log(res)
					if(res.StatusCode=='200'){
						window.open('../ThreeMergeOne/outPutExcel?StartDate='+startDate+'&EndDate='+endDate+'&Type='+type+'&Data='+data)
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
		}
		else{
	    	if(errorMessage.length>0 ||errorMessage!='' ){
			    alert(errorMessage);		
				event.preventDefault(); //preventDefault() 方法阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
		    	return;
	    	}
	    }	
//        window.open('../ThreeMergeOne/outPutExcel?StartDate='+startDate+'&EndDate='+endDate+'&Type='+type+'&Data='+data)
//		window.location.href='../ThreeMergeOne/outPutExcel?StartDate='+startDate+'&EndDate='+endDate+'&Type='+type+'&Data='+data

	})
})