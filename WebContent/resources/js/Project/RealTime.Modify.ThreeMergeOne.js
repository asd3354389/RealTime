$(document).ready(function(){
//	ShowDepid()
//	
	$('input:radio[name="changeType"]').click(function(){
		var type = $('input:radio[name="changeType"]:checked').val();
		if(type=="empid"){
//			$('.inputdata').css('visibility','visible')
//			$('.inputdata').attr('disabled',false)
//			$('.selected').attr('disabled',true)
//			$('.selected').css('visibility','hidden')
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
		var type=$('input:radio[name="changeType"]:checked').val();
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		var data = $('#data').val();
		if(type=="empid"){
			var data= $('.inputdata').val();
		}else if(type=="depid"){	
			var data= $('.selected').val();
		}else if(type=="costid"){
			var data= $('.selected').val();
		}
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
//						 $('#data').val('');
						$('.inputdata').val('');
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

//		function ShowRole(){
//			$.ajax({
//				type:'POST',
//				url:'../CountEmp/ShowRole',
//				data:{},
//				async:false,
//				success:function(data){
//					//console.log(data);
//					Role=data;
//					if(Role=='ROLE_VIC_ADMIN'||Role=='ROLE_VIC_ASSISTANT'||Role=='ALL'){
//						ShowDepid();
//					}else if(Role=='ROLE_VIC_LineLeader'){
//						ShowAssistantDepid();
//					}
//				}
//			});   
//		}
		

	})
	
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
			console.log(data)
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
})