$(document).ready(function(){

	$('#Add_New_Support').click(function(){
		var autoNum = generateMixed(5);	
		var costId = $("#checkClass").val();
		var SDate = $("#timeFirst").val();
		var ProdAllLine=new Object()
		ProdAllLine.RecordId=$("#timeFirst").val().replace(/-/g,"")+autoNum;
		ProdAllLine.CostId = $("#costid").val();
		ProdAllLine.Shift = $("#shift").val();
		ProdAllLine.Transition_Reason = $("#transition_reason").val();
		ProdAllLine.Prod_Out_Name = $("#prod_out_name").val();
		ProdAllLine.Prod_Out_Code = $("#prod_out_code").val();
		ProdAllLine.Transition_Out_Number = $("#transition_out_number").val();
		ProdAllLine.Type_In = $("#type_in").val();
		ProdAllLine.Prod_In_Name = $("#prod_in_name").val();
		ProdAllLine.Prod_In_Code = $("#prod_in_code").val();
		ProdAllLine.Transition_In_Number = $("#transition_in_number").val();
		ProdAllLine.Insert_Date=$("#timeFirst").val();

		$.ajax({
			type:'POST',
			contentType: "application/json",
			url:'../ProdAllLine/AddSupportMachine',
			data:JSON.stringify(ProdAllLine),
			dataType:'json',
			success:function(res) {
				if(res.StatusCode=='200'){
	        		alert(res.Message);
	        		SupportMachineList(costId,SDate);
	        	}else{
	        		alert(res.Message);
	        	}	
			},
			error:function(){
				alert("添加異動人員發生錯誤");
			}
		});
	})
	
	$('#searchCountEmp').click(function(){
		var costId = $("#checkClass").val();
		var SDate = $("#timeFirst").val();
		SupportMachineList(costId,SDate);	
		
		$.ajax({
			type:'POST',
			url:'../ProdAllLine/SupportMachineNum',
			data:{
				costId:costId,
				SDate:SDate
			},
			async:true,
			success : function(res) {
				/*$('#SetSupportMachine').css('display','block');
				ShowSupportMachine(res);*/
//				console.log(res);
				ShowManPowerList(res.countNumeList,res.depidNumList);
			},
			error:function(e){
				
			}
		})
	})
	
	function ShowManPowerList(countNumeList,depidNumList){
		$('#ManPowerList').empty();
		var Count=JSON.parse(countNumeList);
		var DepidCount=JSON.parse(depidNumList);
		//console.log(DepidCount);
		var MoCount=0;
		var NiCount=0;
		for(var j=0;j<Count.length;j++){
			if(Count[j].Class_No!='11'){
				MoCount+=Count[j].Count;
			}else{
				NiCount+=Count[j].Count;
			}
		}
		var tableContents='<div><h4 style="text-align:center">各線人力明細：</h4></div><div class="panel-body" style="display:inline-block;border: 1px solid #e1e3e6;margin:0px 0px 5px 0px;">';
		tableContents+='<table id="SupportMachineList" class="table table-striped"><thead><tr><th>綫別</th><th>班別</th><th>生產人數</th></tr></thead><tbody>'
		for(var i=0;i<DepidCount.length;i++){
				var z = i+1;
				var y = i-1;
				//tableContents+='<tr><td>夜</td>';
				if(DepidCount[i]["Class_No"]!="11"){
					
						tableContents+='<tr class="numTr"><td>'+DepidCount[i]["Depid"]+'</td><td>日</td><td>'+DepidCount[i]["Count"]+'</td></tr>';
						if(z<DepidCount.length){
							if(DepidCount[z]["Depid"]==DepidCount[i]["Depid"]){
								
							}else{
								tableContents+='<tr class="numTr"><td>'+DepidCount[i]["Depid"]+'</td><td>夜</td><td>0</td></tr>';
							}
						}else{
							tableContents+='<tr class="numTr"><td>'+DepidCount[i]["Depid"]+'</td><td>夜</td><td>0</td></tr>';
						}				
				}else{

						if(DepidCount[y]["Depid"]==DepidCount[i]["Depid"]){
							tableContents+='<tr class="numTr"><td>'+DepidCount[i]["Depid"]+'</td><td>夜</td><td>'+DepidCount[i]["Count"]+'</td></tr>';
						}else{
							tableContents+='<tr class="numTr"><td>'+DepidCount[i]["Depid"]+'</td><td>日</td><td>0</td></tr>';
							tableContents+='<tr class="numTr"><td>'+DepidCount[i]["Depid"]+'</td><td>夜</td><td>'+DepidCount[i]["Count"]+'</td></tr>';
						}
					
				}
			
		}
		tableContents+='<tr class="AllTrCount"><td>匯總</td><td>日</td><td>'+MoCount+'</td></tr><tr class="AllTrCount"><td>匯總</td><td>夜</td><td>'+NiCount+'</td></tr>';
		tableContents+='</tbody></table><div style="margin:5px 10px 0px"><input class="showDetail" type="button" value="點擊詳情"></div></div>';
		tableContents+='<div class="panel-body" id="addTable" style="display:none;border: 1px solid #e1e3e6;margin:0px 0px 2px 0px;"></div>'
		$('#ManPowerList').append(tableContents);
		
		$('.showDetail').click(function(){
			var costId = $("#checkClass").val();
			var SDate = $("#timeFirst").val();
			$('#addTable').empty();
			$.ajax({
				type:'POST',
				url:'../ProdAllLine/SupportMachineNumDetail',
				data:{
					costId:costId,
					SDate:SDate
				},
				async:true,
				success : function(res) {
					/*$('#SetSupportMachine').css('display','block');
					ShowSupportMachine(res);*/
					//console.log(res);
					/*ShowManPowerList(res.countNumeList,res.depidNumList);*/
//					console.log(res);
					ShowMachineNumDetail(res.depidNumDetail,res.countNumDetail);
				},
				error:function(e){
					
				}
			})
		})
	}
	
	function ShowMachineNumDetail(depidDetail,countDetail){
		var depidNumDetail=JSON.parse(depidDetail);
		var countNumDetail=JSON.parse(countDetail);
//		console.log(countNumDetail);
		//var tableContents='<table id="SupportMachineList" class="table table-striped"><thead><tr><th>機種</th><th>人數</th><th>機種</th><th>人數</th><th>機種</th><th>人數</th><th>機種</th><th>人數</th><th>機種</th><th>人數</th><th>機種</th><th>人數</th><th>排配總人數</th><th>差異</th></tr></thead><tbody>';
		if(depidDetail=="null"){
			var num = $(".numTr").length;
			var tableContents='<table id="SupportMachineDetail" class="table table-striped"><thead><tr><th>機種</th><th>人數</th><th>機種</th><th>人數</th><th>機種</th><th>人數</th><th>機種</th><th>人數</th><th>機種</th><th>人數</th><th>機種</th><th>人數</th><th>排配總人數</th><th>差異</th></tr></thead><tbody>';
			for(var c=0;c<num;c++){
				var depidNum = $(".numTr").eq(c).children().last().text();
				//console.log(depidNum);
				tableContents+='<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>'+depidNum+'</td></tr>';
			}
			tableContents+='<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>0</td></tr>';
			tableContents+='<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>0</td></tr>';
			tableContents+='</tbody></table><div style="margin:5px 10px 0px"><input class="hiddenDetail" type="button" value="收起"></div>';
			$("#addTable").append(tableContents);
			$("#addTable").css("display","inline-block");
			$(".hiddenDetail").click(function(){
				$("#addTable").css("display","none");
			})
		}else{	
			var num = $(".numTr").length;
			if(countNumDetail.length<2){
				var size = num + parseInt(countNumDetail.length)+1;
			}else{
				var size = num + parseInt(countNumDetail.length);
			}
			
			var tableContents="";
			var b=0;
			for(var d=0;d<num;d++){
				var depid = $(".numTr").eq(d).children().eq(0).text();
				var shift = $(".numTr").eq(d).children().eq(1).text();
				var a =0;
				for(var e=0;e<depidNumDetail.length;e++){
					if(depidNumDetail[e].Depid==depid && depidNumDetail[e].Shift==shift){
						a+=1;
					}	
				}
				if(a>b){
					b=a;
				}
				
			}
			for(var g=0;g<size;g++){			
				if(g>=num){						
					tableContents+='<tr>';
					for(var h=0;h<b;h++){
						tableContents+='<td></td><td></td>';
						if(h==b-1){
							var Count=$(".AllTrCount").eq(g-num).children().eq(2).text();
							if(countNumDetail.length<2){
								if(countNumDetail[0].Shift=="日"){	
									if(g==size){
										tableContents+='<td>0</td><td>'+Count+'</td></tr>';
									}else{
										var diff=Count-parseInt(countNumDetail[0].Sum_Of_People);
										tableContents+='<td>'+countNumDetail[0].Sum_Of_People+'</td><td>'+diff+'</td></tr>';
									}
								}else if(countNumDetail[0].Shift=="夜"){
									if(g==size){
										var diff=Count-parseInt(countNumDetail[0].Sum_Of_People);
										tableContents+='<td>'+countNumDetail[0].Sum_Of_People+'</td><td>'+diff+'</td></tr>';
									}else{
										tableContents+='<td>0</td><td>'+Count+'</td></tr>';
									}
								}

								
							}else{
								var Count=$(".AllTrCount").eq(g-num).children().eq(2).text();
								var diff=Count-parseInt(countNumDetail[g-num].Sum_Of_People);
								tableContents+='<td>'+countNumDetail[g-num].Sum_Of_People+'</td><td>'+diff+'</td></tr>';
							}
						
						}
					}
					
				}else{
					var depid = $(".numTr").eq(g).children().eq(0).text();
					var shift = $(".numTr").eq(g).children().eq(1).text();
					var allPeople = $(".numTr").eq(g).children().eq(2).text();
					var people = 0;
					tableContents+='<tr>';
					var i=0;
					var n =0;
					for(var m=0;m<depidNumDetail.length;m++){
//						console.log(i);
						var z=b-i-1;
						var l=m+1;
						if(depidNumDetail[m].Depid==depid && depidNumDetail[m].Shift==shift){
							tableContents+='<td>'+depidNumDetail[m].Prod_Name+'</td><td>'+depidNumDetail[m].Number_Of_People+'</td>';
							people+=parseInt(depidNumDetail[m].Number_Of_People);
							if(l<=depidNumDetail.length){
								if(l!=depidNumDetail.length&&depidNumDetail[l].Depid==depid && depidNumDetail[l].Shift==shift){
									i++;
								}else{
										for(var j=0;j<=z;j++){
											if(j==z){
												var diff=allPeople-people;
												tableContents+='<td>'+people+'</td><td>'+diff+'</td></tr>';
											}else{
												tableContents+='<td></td><td></td>';
												
											}

										}
									i=0;
								}
							}
						}else{
							if(depidNumDetail[m].Depid!=depid || depidNumDetail[m].Shift!=shift){
								n++;
							}
							if(n==depidNumDetail.length){
								for(var r=0;r<=b;r++){
									
									if(r==b){
										//var diff=allPeople-people;
										tableContents+='<td>0</td><td>'+allPeople+'</td>';
									}else{
										tableContents+='<td></td><td></td>';
									}
								}
								n=0;
							}
						}
					}
					tableContents+='</tr>';
					/**/
				}
			}
				
			var table='<table id="SupportMachineDetail" class="table table-striped"><thead><tr>'
			for(var f=0;f<b;f++){
				table+='<th>機種</th><th>人數</th>';
			}
			table+='<th>排配總人數</th><th>差異</th></tr></thead><tbody></tbody></table><div style="margin:5px 10px 0px"><input class="hiddenDetail" type="button" value="收起"></div>';
			
			
			$("#addTable").append(table);
			$('#SupportMachineDetail tbody').append(tableContents);

			
			$("#addTable").css("display","inline-block");
			$(".hiddenDetail").click(function(){
				$("#addTable").css("display","none");
			})
		}
	}
	
	function SupportMachineList(costId,SDate){
		$.ajax({
			type:'POST',
			url:'../ProdAllLine/SupportMachine.show',
			data:{
				costId:costId,
				SDate:SDate
			},
			async:true,
			success : function(res) {
				$('#SetSupportMachine').css('display','block');
				ShowSupportMachine(res);
			},
			error:function(e){
				
			}
		});
	}
	
	function ShowSupportMachine(data){
		if(data.StatusCode=="500"){
			$('#ShowSupportMachine').empty();
			alert("暫無機種異動資料");
		}else{
			$('#ShowSupportMachine').empty();
			var rawData =data.message;
			var executeResult=JSON.parse(rawData);
			var tableContents='<div><h4 style="text-align:center">機種異動列表：</h4></div><div class="panel-body" style="border: 1px solid #e1e3e6;margin:0px 10px 5px;">';
			tableContents+='<table id="SupportMachineList" class="table table-striped"><thead><tr><th colspan="6">異動</th><th colspan="5">人力動向</th></tr><tr><th>課別</th><th>班別</th><th>異動原因</th><th>機種名稱</th><th>機種料號</th><th>人數</th><th>類型</th><th>機種名稱/課別</th><th>機種料號</th><th>人數</th><th>刪除</th></tr></thead><tbody>'
			for(var i=0;i<executeResult.length;i++){
					tableContents+='<tr>'+
						'<td style="display:none">'+executeResult[i]["RecordId"]+'</td>'+
						'<td>'+executeResult[i]["CostId"]+'</td>'+
						'<td>'+executeResult[i]["Shift"]+'</td>'+
						'<td>'+executeResult[i]["Transition_Reason"]+'</td>'+
						'<td>'+executeResult[i]["Prod_Out_Name"]+'</td>'+
						'<td>'+executeResult[i]["Prod_Out_Code"]+'</td>'+
						'<td>'+executeResult[i]["Transition_Out_Number"]+'</td>'+
						'<td>'+executeResult[i]["Type_In"]+'</td>'+
						'<td>'+executeResult[i]["Prod_In_Name"]+'</td>'+
						'<td>'+executeResult[i]["Prod_In_Code"]+'</td>'+
						'<td>'+executeResult[i]["Transition_In_Number"]+'</td>'+
						'<td><input class="Delete_Support" type="button" value="刪除異動人數"\"></td></tr>'
	//					'<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link"></td>';
	/*					tableContents+='<td><input type="button" value="編輯" class="editBtn btn btn-xs btn-link">';	*/
			}
			tableContents+='</tbody></table></div>'
			$('#ShowSupportMachine').append(tableContents);
			
			$('.Delete_Support').click(function(){
				var costId = $("#checkClass").val();
				var SDate = $("#timeFirst").val();
				var recordid=$(this).parent().parent().find('td').eq(0).text();
				    $.ajax({
				        type: 'post',
				        url: '../ProdAllLine/DelSupportMachine',
				        data: {
				        	recordid: recordid
				        },
				        success: function(res) {
				        	if(res.StatusCode=='200'){
				        		alert(res.Message);
				        		SupportMachineList(costId,SDate);
				        	}else{
				        		alert(res.Message);
				        	}
				        },
				        error:function(){
							alert("刪除異動人員發生錯誤");
						}
				    });
				
			})
		}
	}
	
	function generateMixed(n) {
		 var chars = ['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
	     var res = "";
	     for(var i = 0; i <= n ; i ++) {
	         var id = Math.ceil(Math.random()*61);
	         res += chars[id];
	     }
	     return res;
	}
	
})