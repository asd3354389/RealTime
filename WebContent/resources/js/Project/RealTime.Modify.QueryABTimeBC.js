$(document).ready(function(){	
	var Role = "";
	ShowRole();
	$('#BU').change(function(){
		var Bu = $('#BU').val();
		//console.log(Bu);
		if(Bu=="AllReSet"){
			$('#costid').empty();
			var Coptions='<option>請選擇課別</option>';
			var Doptions='<option>請選擇線別</option>';
			$('#costid').append(Coptions);
			$('#depid').append(Doptions);
		}else if(Bu=="AllBU"){
			$('#costid').empty();
			var options='<option>請選擇課別</option>';
			$('#costid').append(options);
		}else if(Bu=="B1001"){
			$('#costid').empty();
			var options='<option value="AllCost">All</option><option value="1726">安檢課</option><option value="2443">組裝生技課</option><option value="6251">三課</option>'+
			'<option value="6252">四課</option><option value="9097">五課</option><option value="9628">一課</option><option value="9629">二課</option><option value="9682">組裝IE課</option>'+
			'<option value="9886">設備維護一課</option>';
			$('#costid').append(options);
		}else if(Bu=="B1002"){
			$('#costid').empty();
			var options='<option value="AllCost">All</option><option value="1718">設備維修課</option><option value="3456">成型自動化課</option><option value="6285">成型生產二課</option>'+
			'<option value="6437">塑件成型部</option><option value="6438">塑模二課</option><option value="8835">塑模一課</option><option value="9636">塑件開發部</option><option value="9637">成型生產一課</option>'+
			'<option value="9638">成型技術課</option><option value="9650">塑件部</option><option value="9684">成型生產三課</option>';
			$('#costid').append(options);
		}else if(Bu=="B1003"){
			$('#costid').empty();
			var options='<option value="AllCost">All</option><option value="1769">電泳課</option><option value="3457">清洗課</option><option value="5863">生產三課</option>'+
			'<option value="5864">設備維修課</option><option value="5865">藥水監控開發課</option><option value="5866">環工課</option><option value="6255">企劃課</option><option value="9640">生產二課</option>'+
			'<option value="9641">新產品開發課</option><option value="9686">噴砂課</option><option value="9889">研磨課</option>';
			$('#costid').append(options);
		}else if(Bu=="B1004"){
			$('#costid').empty();
			var options='<option value="AllCost">All</option><option value="5857">沖模二課</option><option value="5860">沖壓三課</option><option value="8852">沖模一課</option>'+
			'<option value="9635">沖壓一課</option><option value="9644">沖壓二課</option><option value="9670">沖壓技術課</option>';
			$('#costid').append(options);
		}
		
		$('#costid').change(function(){
			$('#ABTimeList tbody').empty();
			$('#ShowABTime').css('display','none');
			var costid=$('#costid').val();
			if(costid=="AllCost"){
				$('#depid').empty();
				var options='<option>請選擇線別</option>';
				$('#depid').append(options);
			}else{
				$('#depid').empty();
				$.ajax({
					url:'../QuertAbTimeByCostId/ShowDepid.show',
					type:'post',
					data:{
						costid:costid
					},
					async:false,
					success:function(res){
						//console.log(res);
						var options='<option value="AllDepid">All</option>';
						if(res.length>0){
							for(var i=0;i<res.length;i++){
								options+='<option value='+res[i]+'>'+res[i]+'</option>'
							}	
						}
						$('#depid').append(options);
					},
					error:function(){
						alert('發生錯誤！');
					}
				})
			}
		})
	})
	
	$('#searchByCostid').click(function(){
		var errorMessage = '';
		var Bu ="";
		var costid=""; 
		var depid=""; 
		if(Role=='ROLE_VIC_ADMIN'||Role=='ROLE_VIC_ASSISTANT'||Role=='ALL'){
			 Bu = $('#BU').val();
			 costid=$('#costid').val(); 
			 depid=$('#depid').val(); 
		}else if(Role=='ROLE_VIC_LineLeader'){
			 depid=$('#Assistant_depid').val(); 
		}
		var SDate = $('#startDate').val();
		var EDate = $('#endDate').val();
		if(SDate=="null" || SDate=='')
			errorMessage+='未選擇開始時間\n';
		if(EDate=="null" || EDate=='')
			errorMessage+='未選擇結束時間\n';
		if(errorMessage==''){	
			$.ajax({
				url:'../QuertAbTimeByCostId/ShowABTimeByCostid',
				type:'post',
				data:{
					Bu:Bu,
					costid:costid,
					depid:depid,
					SDate:SDate,
					EDate:EDate
				},
				async:false,
				success:function(res){
					//console.log(res);
					if(res.StatusCode=="200"){
						ShowABTimeList(res.ABTimeList);
					}
					else{
						  alert(res.message);
					}
				},
				error:function(){
					alert('發生錯誤！');
				}
			})
		}else{
		    	if(errorMessage.length>0 ||errorMessage!='' ){
			    alert(errorMessage);		
				event.preventDefault(); //preventDefault() 方法阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
			}
		}
	})
	
	function ShowABTimeList(data){
		$('#ABTimeList tbody').empty();
		var ABTime = JSON.parse(data);
		var tableContents='';
		for(var i=0;i<ABTime.length;i++){
			tableContents+='<tr><td>'+ABTime[i].DEPID+'</td><td>'+ABTime[i].USERID+'</td><td>'+ABTime[i].USERNAME+'</td><td>'+ABTime[i].Count+'</td><td>'+ABTime[i].SumTime+'</td></tr>'
		}
		$('#ABTimeList tbody').append(tableContents);
		$('#ShowABTime').css('display','block');
	}
	
	function ShowRole(){
		$.ajax({
			type:'POST',
			url:'../CountEmp/ShowRole',
			data:{},
			async:false,
			success:function(data){
				//console.log(data);
				Role=data;
				if(Role=='ROLE_VIC_ADMIN'||Role=='ROLE_VIC_ASSISTANT'||Role=='ALL'){
					
					$('.Admin_Depid').css('display','inline-block');
					$('.Assistant_Depid').css('display','none');
				}else if(Role=='ROLE_VIC_LineLeader'){
					$('.Admin_Depid').css('display','none');
					$('.Assistant_Depid').css('display','inline-block');
					ShowAssistantDepid();
				}
			}
		});   
	}
	function ShowAssistantDepid(){
		$.ajax({
			type:'POST',
			url:'../CountEmp/ShowAssistantDepid.show',
			data:{},
			async:false,
			success:function(data){
			 var htmlAppender='';
			 if(data!=null && data!=''){	
				if(data.length>0 && data.StatusCode == null){
					for(var i=0;i<data.length;i++){
						htmlAppender+='<option value="'+data[i]+'">'+data[i]+'</option>';
					}
					 $('#Assistant_depid').append(htmlAppender);
				}
				else{
					alert('無資料');
				}
			 }else{
				 alert('無資料');
			 }
			}
		});   
	}
})