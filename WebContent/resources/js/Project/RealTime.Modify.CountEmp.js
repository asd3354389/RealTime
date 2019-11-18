$(document).ready(function(){
	ShowDepid();
	
	$('#searchCountEmp').click(function(){
		 ShowTable();
	})
	
	$('#type').change(function(){
		var Specific = $('#type').val();
		var SpecificList = $('#status').val();
		switch (Specific) 
		{ 
		  case "X":
			  $('#status').empty();
			  var selectList = '<option>請選擇具體事由</option>';
			  $('#status').append(selectList);
		  break; 
		  case "1":
			  $('#status').empty();
			  var selectList = '<option value="0">事假</option><option value="1">病假</option><option value="2">特休</option><option value="3">婚假</option>'+
			  					'<option value="4">喪假</option><option value="5">產假</option><option value="6">工傷假</option><option value="7">探親假</option>';
			  $('#status').append(selectList);
		  break; 
		  case "2":
			  $('#status').empty();
			  var selectList = '<option value="0">曠工</option>';
			  $('#status').append(selectList);;
		  break; 
		  case "3":
			  $('#status').empty();
			  var selectList = '<option value="0">支援</option><option value="1">調出</option>';
			  $('#status').append(selectList); 
		  break; 
		  case "4":
			  $('#status').empty();
			  var selectList = '<option value="0">新進</option><option value="1">調入</option>';
			  $('#status').append(selectList);
		  break; 
		  case "5":
			  $('#status').empty();
			  var selectList = '<option value="0">離職</option><option value="1">自離</option>';
			  $('#status').append(selectList);
		  break; 
		  case "8":
			  $('#status').empty();
			  var selectList = '<option value="0">調休</option>';
			  $('#status').append(selectList);
		  break; 
		  case "9":
			  $('#status').empty();
			  var selectList = '<option value="0">遲到</option>';
			  $('#status').append(selectList);
		  break; 
		  case "0":
			  $('#status').empty();
			  var selectList = '<option value="0">無異常</option>';
			  $('#status').append(selectList);
		  break; 
		}
	})
	
	$('#changeStatus').click(function(){
			var type = $("#type").val();
		    var status = $("#status").val();
		    var type_status = type + "_" + status;
		    var UserNo = $('#UserNo').val();
		    var depid = $("#depid").val();
		    var Class_No=$("#Class_No").val();
		    var class_no="";
		    var SDate = $("#timeFirst").val();
		    if (type_status == "0_0") {
		        type_status = "0_1";
		    }
		    if(Class_No=='日'){
		    	class_no = "3";
			}else if(Class_No=='夜'){
				class_no = "11";
			}
		    console.log(type_status);
		    //changeDate = getDate1(SDate);
		    // checkDate(changeDate);
		    //checkType(type);

		    $.ajax({
		        type: 'post',
		        url: '../CountEmp/UpdateStatus.do',
		        data: {
		        	UserNo: UserNo,
		        	depid: depid,
		        	SDate: SDate,
		            class_no: class_no,
		            type_status: type_status
		        },
		        success: function(msg) {
		            if(msg.StatusCode=="200"){
		            	ShowTable();
		            	alert(msg.Message)
		            }
		        }
		    });
	})
	function ShowTable(){
		var depid = $("#depid").val();
		var SDate = $("#timeFirst").val();
		$.ajax({
			type:'POST',
			url:'../CountEmp/ShowCountEmpList',
			data:{
				depid:depid,
				SDate:SDate
			},
			async:true,
			success : function(res) {
				//console.log(res);
				if(res.StatusCode=="200"){
					EachCountEmp(res);
					var SunNum = [];
					var NightNum =[];
					var CountList = JSON.parse(res.countEmpList);
					console.log(CountList);
					for(var i=0;i<CountList.length;i++){
						//console.log(res.countEmpList[i]);
						if(CountList[i].Class_No!='11'&&CountList[i].Status!='0_1'){
							SunNum.push(CountList[i]);
						}else if(CountList[i].Class_No='11'&&CountList[i].Status!='0_1'){
							NightNum.push(CountList[i]);
						}
					}
					AbsentPeopleList(SunNum,NightNum);
				}else{
					alert("查无数据！");
				}
			},
			error:function(e){
				alert('查詢發生錯誤！');
			}
		})
	}
	
	function EachCountEmp(data){
		$('#EachLineMachine tbody').empty();
		var EmpList=JSON.parse(data.countEmpList);
		//console.log(EmpList);
		var SunNewEmployee = 0; //日班新進人數
		var SunLeaveNum = 0; //日班調出人數
		var SunDimissionNum = 0;//日班離職人數
		var SunVacateNume = 0; //日班请假人数
		var SunAbsenteeismNum = 0; //日班旷工人数
		var SunAlltechnicalNum = 0; //日班全技员
		var SunProductionNum = 0; //日班生产人数 
		
		
		var NightNewEmployee = 0; //夜班新進人數
		var NightLeaveNum = 0; //夜班調出人數
		var NightDimissionNum = 0;//夜班離職人數
		var NightVacateNume = 0; //夜班请假人数
		var NightAbsenteeismNum = 0; //夜班旷工人数
		var NightAlltechnicalNum = 0; //夜班全技员
		var NightProductionNum = 0; //夜班生产人数 
		
		for(var i = 0;i<EmpList.length;i++){
			if(EmpList[i].Class_No!=11){
				if(EmpList[i].Status.indexOf("4_")!= -1){
					SunNewEmployee++;
				}else if(EmpList[i].Status.indexOf("3_")!= -1){
					SunLeaveNum++;
				}else if(EmpList[i].Status.indexOf("5_")!= -1){
					SunDimissionNum++;
				}else if(EmpList[i].Status.indexOf("1_")!= -1){
					SunVacateNume++;
				}else if(EmpList[i].Status.indexOf("2_")!= -1){
					SunAbsenteeismNum++;
				}else if(EmpList[i].Status.indexOf("6_")!= -1){
					SunAlltechnicalNum++;
				}
			}else{
				if(EmpList[i].Status.indexOf("4_")!= -1){
					NightNewEmployee++;
				}else if(EmpList[i].Status.indexOf("3_")!= -1){
					NightLeaveNum++;
				}else if(EmpList[i].Status.indexOf("5_")!= -1){
					NightDimissionNum++;
				}else if(EmpList[i].Status.indexOf("1_")!= -1){
					NightVacateNume++;
				}else if(EmpList[i].Status.indexOf("2_")!= -1){
					NightAbsenteeismNum++;
				}else if(EmpList[i].Status.indexOf("6_")!= -1){
					NightAlltechnicalNum++;
				}
			}		
		}
		SunProductionNum=parseInt(data.Sun)-SunNewEmployee-SunLeaveNum-SunDimissionNum-SunVacateNume-SunAbsenteeismNum-SunAlltechnicalNum;
		NightProductionNum=parseInt(data.Night)-NightNewEmployee-NightLeaveNum-NightDimissionNum-NightAbsenteeismNum-NightAlltechnicalNum;
		var tableContents='<tr><td>'+$("#depid").val()+'</td><td>日</td>'+
						'<td>'+data.Sun+'</td><td>'+SunNewEmployee+'</td><td>'+SunLeaveNum+'</td><td>'+SunDimissionNum+'</td><td>'+SunVacateNume+'</td><td>'+SunAbsenteeismNum+'</td><td>'+SunAlltechnicalNum+'</td><td>'+SunProductionNum+'</td></tr>';
		tableContents+='<tr><td>'+$("#depid").val()+'</td><td>夜</td>'+
						'<td>'+data.Night+'</td><td>'+NightNewEmployee+'</td><td>'+NightLeaveNum+'</td><td>'+NightDimissionNum+'</td><td>'+NightVacateNume+'</td><td>'+NightAbsenteeismNum+'</td><td>'+NightAlltechnicalNum+'</td><td>'+NightProductionNum+'</td></tr>';
		
		//console.log(tableContents);
		$('#EachLineMachine>tbody').append(tableContents);
		$('.showTable').css("display","block");
	}
	
	function AbsentPeopleList(SunNum,NightNum){
		$('.SpecificList').empty();
		var tableContents= '<div><h4 style="text-align: center;">缺席人员名单：</h4></div><div class="panel-body" style="border: 1px solid #e1e3e6;margin:0px 10px 5px;"><table id="AbsentPeopleList" class="table table-striped">';
			tableContents+='<thead><tr><th>線  別</th><th>班別</th><th>工號</th><th>姓名</th><th>狀態</th><th>修改</th></tr></thead><tbody>';
		var sunLength=SunNum.length;
		var nightLength=NightNum.length;
		var Shift="";
		if(SunNum.length>0){
			for(var k=0;k<sunLength;k++){
				if(SunNum[k].Class_No!='11'){
					Shift="日";
				}else{
					Shift="夜";
				}
				if(k==0){
					tableContents+='<tr><td rowspan='+sunLength+' style="color: red;">'+SunNum[k].Depid+'</td><td rowspan='+sunLength+' style="color: red;">'+Shift+'</td><td>'+SunNum[k].ID+'</td><td>'+SunNum[k].Name+'</td><td>'+getCNStatus(SunNum[k].Status)+'</td><td><a role="button" href=".ChangeStatus" class="changeStatus btn btn-primary btn-sm" data-toggle="modal" value='+SunNum[k].ID+'>詳情</a></td></tr>';
				}else{
					tableContents+='<tr><td style="display:none"></td><td style="display:none">'+Shift+'</td><td>'+SunNum[k].ID+'</td><td>'+SunNum[k].Name+'</td><td>'+getCNStatus(SunNum[k].Status)+'</td><td><a role="button" href=".ChangeStatus" class="changeStatus btn btn-primary btn-sm" data-toggle="modal" value='+SunNum[k].ID+'>詳情</a></td></tr>';
				}
			}
			
			//console.log(tableContents);
		}
		if(NightNum.length>0){
			for(var j=0;j<nightLength;j++){
				if(NightNum[j].Class_No='11'){
					Shift="夜";
				}else{
					Shift="日";
				}
				if(j==0){
					tableContents+='<tr><td rowspan='+nightLength+' style="color: red;">'+NightNum[j].Depid+'</td><td rowspan='+nightLength+' style="color: red;">'+Shift+'</td><td>'+NightNum[j].ID+'</td><td>'+NightNum[j].Name+'</td><td>'+getCNStatus(NightNum[j].Status)+'</td><td><a role="button" href=".ChangeStatus" class="changeStatus btn btn-primary btn-sm" data-toggle="modal" value='+NightNum[j].ID+'>詳情</a></td></tr>';
				}else{
					tableContents+='<tr><td style="display:none"></td><td style="display:none">'+Shift+'</td><td>'+NightNum[ji].ID+'</td><td>'+NightNum[j].Name+'</td><td>'+getCNStatus(NightNum[j].Status)+'</td><td><a role="button" href=".ChangeStatus" class="changeStatus btn btn-primary btn-sm" data-toggle="modal" value='+NightNum[j].ID+'>詳情</a></td></tr>';
				}
			}
			//console.log(tableContents);
		}
		tableContents+='</tbody></table></div>';
		$('.SpecificList').append(tableContents);
		$('.changeStatus').click(function(){
			$('#UserNo').empty();
			var trDom = $(this).parent().parent();
			console.log(trDom.children().eq(2).text());
			$('#UserNo').val(trDom.children().eq(2).text());
			$('#Class_No').val(trDom.children().eq(1).text());
		})
	}
	
	function getCNStatus(value){
		//console.log(value);
		var Status = [];
		Status[0] = Array('未刷卡');
		Status[1] = Array('事假','病假','特休','婚假','喪假','產假','工傷假','探親假');
		Status[2] = Array('曠工');
		Status[3] = Array('支援','調出');
		Status[4] = Array('新進','調入');
		Status[5] = Array('離職','自離');
		Status[6] = Array('全技員');
		Status[7] = Array('白班','夜班');
		Status[8] = Array('調休');
		Status[9] = Array('遲到');
		var PersonStatus ="";
		var List = [];
		List = value.split("_");
		PersonStatus=Status[List[0]][List[1]];
		return PersonStatus;
	}
	
	function ShowDepid(){
		$.ajax({
			type:'POST',
			url:'../CountEmp/ShowDepid.show',
			data:{},
			async:false,
			success:function(data){
			 var htmlAppender='';
			 if(data!=null && data!=''){	
				if(data.length>0 && data.StatusCode == null){
					for(var i=0;i<data.length;i++){
						htmlAppender+='<option value="'+data[i]+'">'+data[i]+'</option>';
					}
					 $('#depid').append(htmlAppender);
				}
				else{
					console.log('無車間資料');
				}
			 }else{
				 console.log('無車間資料');
			 }
			}
		});   
	}
})