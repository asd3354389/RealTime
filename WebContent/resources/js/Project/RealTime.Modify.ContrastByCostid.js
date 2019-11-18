$(document).ready(function(){
	$('#chekcType').change(function(){
		var type = $("#chekcType").val();
		if(type=="All"){
			$("#chekcClass").hide();
		}else if(type=="costid"){
			$("#chekcClass").show();
		}
	})
	
	$("#searchContrast").click(function(){
		var type = $("#chekcType").val();
		var SDate = $('#timeFirst').val();
		if(type=="All"){
			$.ajax({
				type: 'post',
				url:"../CountEmpByCostID/ShowCountEmpAll",
				data:{
					SDate:SDate
				},
				success:function(res){
					if(res.StatusCode=="200"){
						ShowCountEmpList(res);
					}else{
						$('.CountEmpList').empty();
						$('.CountList').css('display','none');
						alert("各綫人力明細"+res.message);
					}
				},
				error:function(){
					alert("失敗");
				}
			})
			
			$.ajax({
				type: 'post',
				url:"../CountEmpByCostID/ShowABEmp",
				data:{
					costid:'All',
					SDate:SDate
				},
				success:function(res){
					if(res.StatusCode=="200"){
						
						ShowABEmpList(res);
					}else{
						$('.ABCountEmpList').empty();
						$('.ABCountList').css('display','none');
						alert("機種異動表:"+res.message);
					}
				},
				error:function(){
					alert("失敗");
				}
			})
		}else{
			var costid=$('#chekcClass').val();
			$.ajax({
				type: 'post',
				url:"../CountEmpByCostID/ShowCountEmpByCostId",
				data:{
					costid:costid,
					SDate:SDate
				},
				success:function(res){
					//console.log(res)
					if(res.StatusCode=="200"){
						ShowCountEmpBCList(res);
					}else{
						$('.CountEmpList').empty();
						$('.CountList').css('display','none');
						alert("各綫人力明細"+res.message);
					}
				},
				error:function(){
					alert("失敗");
				}
			})
			
			$.ajax({
				type: 'post',
				url:"../CountEmpByCostID/ShowABEmp",
				data:{
					costid:costid,
					SDate:SDate
				},
				success:function(res){
					if(res.StatusCode=="200"){
						ShowABEmpList(res);
					}else{
						$('.ABCountEmpList').empty();
						$('.ABCountList').css('display','none');
						alert("機種異動表:"+res.message);
					}
				},
				error:function(){
					alert("失敗");
				}
			})
		}
		
	})
	
	function ShowCountEmpList(data){
		console.log(JSON.parse(data.Fivth));
		$('.CountEmpList').empty();
		var First = JSON.parse(data.First);
		var Second = JSON.parse(data.Second);
		var Third = JSON.parse(data.Third);
		var Fouth = JSON.parse(data.Fouth);
		var Fivth = JSON.parse(data.Fivth);
		
/*		var First = data.First;
		var Second =data.Second;
		var Third = data.Third;
		var Fouth = data.Fouth;
		var Fivth = data.First;
		*/
		var tableContents = '<table id="EmpList" class="table table-striped"><thead><tr><th>課  別</th><th>班別</th><th>組裝人數</th><th>新進人數</th><th>調出人數</th>'+
							'<th>離職人數</th><th>請假人數</th><th>曠工人數</th><th>全技員</th><th>生產人數</th><th>線外人數</th><th>線內生產人數</th><th>排配人數</th><th>差異</th></tr></thead><tbody>'
		for(var a=0;a<First.length;a++){
			var shift="";
			if(First[a].Class_No!=11){
				shift="日";
			}else{
				shift="夜";
			}
			var prodCount=First[a].Count-First[a].NP-First[a].Expor-First[a].Dimiss-First[a].Leave-First[a].ABS-First[a].ATP;
			var inLine = prodCount-First[a].OTL;
			var diff=inLine-First[a].PC;
			tableContents+='<tr><td>'+First[a].Cost+'_一課</td><td>'+shift+'</td><td>'+First[a].Count+'</td><td>'+First[a].NP+'</td><td>'+First[a].Expor+'</td>'+
			'<td>'+First[a].Dimiss+'</td><td>'+First[a].Leave+'</td><td>'+First[a].ABS+'</td><td>'+First[a].ATP+'</td><td>'+prodCount+'</td><td>'+First[a].OTL+'</td>'+
			'<td>'+inLine+'</td><td>'+First[a].PC+'</td><td>'+diff+'</td></tr>'
		}
		for(var b=0;b<Second.length;b++){
			var shift="";
			if(Second[b].Class_No!=11){
				shift="日";
			}else{
				shift="夜";
			}
			var prodCount=Second[b].Count-Second[b].NP-Second[b].Expor-Second[b].Dimiss-Second[b].Leave-Second[b].ABS-Second[b].ATP;
			var inLine = prodCount-Second[b].OTL;
			var diff=inLine-Second[b].PC;
			tableContents+='<tr><td>'+Second[b].Cost+'_一課</td><td>'+shift+'</td><td>'+Second[b].Count+'</td><td>'+Second[b].NP+'</td><td>'+Second[b].Expor+'</td>'+
			'<td>'+Second[b].Dimiss+'</td><td>'+Second[b].Leave+'</td><td>'+Second[b].ABS+'</td><td>'+Second[b].ATP+'</td><td>'+prodCount+'</td><td>'+Second[b].OTL+'</td>'+
			'<td>'+inLine+'</td><td>'+Second[b].PC+'</td><td>'+diff+'</td></tr>'
		}
		for(var c=0;c<Third.length;c++){
			var shift="";
			if(Third[c].Class_No!=11){
				shift="日";
			}else{
				shift="夜";
			}
			var prodCount=Third[c].Count-Third[c].NP-Third[c].Expor-Third[c].Dimiss-Third[c].Leave-Third[c].ABS-Third[c].ATP;
			var inLine = prodCount-Third[c].OTL;
			var diff=inLine-Third[c].PC;
			tableContents+='<tr><td>'+Third[c].Cost+'_一課</td><td>'+shift+'</td><td>'+Third[c].Count+'</td><td>'+Third[c].NP+'</td><td>'+Third[c].Expor+'</td>'+
			'<td>'+Third[c].Dimiss+'</td><td>'+Third[c].Leave+'</td><td>'+Third[c].ABS+'</td><td>'+Third[c].ATP+'</td><td>'+prodCount+'</td><td>'+Third[c].OTL+'</td>'+
			'<td>'+inLine+'</td><td>'+Third[c].PC+'</td><td>'+diff+'</td></tr>'
		}
		for(var d=0;d<Fouth.length;d++){
			var shift="";
			if(Fouth[d].Class_No!=11){
				shift="日";
			}else{
				shift="夜";
			}
			var prodCount=Fouth[d].Count-Fouth[d].NP-Fouth[d].Expor-Fouth[d].Dimiss-Fouth[d].Leave-Fouth[d].ABS-Fouth[d].ATP;
			var inLine = prodCount-Fouth[d].OTL;
			var diff=inLine-Fouth[d].PC;
			tableContents+='<tr><td>'+Fouth[d].Cost+'_一課</td><td>'+shift+'</td><td>'+Fouth[d].Count+'</td><td>'+Fouth[d].NP+'</td><td>'+Fouth[d].Expor+'</td>'+
			'<td>'+Fouth[d].Dimiss+'</td><td>'+Fouth[d].Leave+'</td><td>'+Fouth[d].ABS+'</td><td>'+Fouth[d].ATP+'</td><td>'+prodCount+'</td><td>'+Fouth[d].OTL+'</td>'+
			'<td>'+inLine+'</td><td>'+Fouth[d].PC+'</td><td>'+diff+'</td></tr>'
		}
		for(var e=0;e<Fivth.length;e++){
			var shift="";
			if(Fivth[e].Class_No!=11){
				shift="日";
			}else{
				shift="夜";
			}
			var prodCount=Fivth[e].Count-Fivth[e].NP-Fivth[e].Expor-Fivth[e].Dimiss-Fivth[e].Leave-Fivth[e].ABS-Fivth[e].ATP;
			var inLine = prodCount-Fivth[e].OTL;
			var diff=inLine-Fivth[e].PC;
			tableContents+='<tr><td>'+Fivth[e].Cost+'_一課</td><td>'+shift+'</td><td>'+Fivth[e].Count+'</td><td>'+Fivth[e].NP+'</td><td>'+Fivth[e].Expor+'</td>'+
			'<td>'+Fivth[e].Dimiss+'</td><td>'+Fivth[e].Leave+'</td><td>'+Fivth[e].ABS+'</td><td>'+Fivth[e].ATP+'</td><td>'+prodCount+'</td><td>'+Fivth[e].OTL+'</td>'+
			'<td>'+inLine+'</td><td>'+Fivth[e].PC+'</td><td>'+diff+'</td></tr>'
		}
		var AllSunCount=First[0].Count+Second[0].Count+Third[0].Count+Fouth[0].Count+Fivth[0].Count;
		var AllSunNP=First[0].NP+Second[0].NP+Third[0].NP+Third[1].NP+Fouth[0].NP+Fivth[0].NP;
		var AllSunExpor=First[0].Expor+Second[0].Expor+Third[0].Expor+Fouth[0].Expor+Fivth[0].Expor;
		var AllSunDimiss=First[0].Dimiss+Second[0].Dimiss+Third[0].Dimiss+Fouth[0].Dimiss+Fivth[0].Dimiss;
		var AllSunLeave=First[0].Leave+Second[0].Leave+Third[0].Leave+Fouth[0].Leave+Fivth[0].Leave;
		var AllSunABS=First[0].ABS+Second[0].ABS+Third[0].ABS+Fouth[0].ABS+Fivth[0].ABS;
		var AllSunATP=First[0].ATP+Second[0].ATP+Third[0].ATP+Fouth[0].ATP+Fivth[0].ATP;
		var AllSunProd = AllSunCount-AllSunNP-AllSunExpor-AllSunDimiss-AllSunLeave-AllSunABS-AllSunATP;
		var AllSunOTL=First[0].OTL+Second[0].OTL+Third[0].OTL+Fouth[0].OTL+Fivth[0].OTL;
		var AllSunLine=AllSunProd-AllSunOTL;
		var AllSunPC=First[0].PC+Second[0].PC+Third[0].PC+Fouth[0].PC+Fivth[0].PC;
		var AllSunDiff=AllSunLine-AllSunPC;
		
		var AllNightCount=First[1].Count+Second[1].Count+Third[1].Count+Fouth[1].Count+Fivth[1].Count;
		var AllNightNP=First[1].NP+Second[1].NP+Third[1].NP+Third[1].NP+Fouth[1].NP+Fivth[1].NP;
		var AllNightExpor=First[1].Expor+Second[1].Expor+Third[1].Expor+Fouth[1].Expor+Fivth[1].Expor;
		var AllNightDimiss=First[1].Dimiss+Second[1].Dimiss+Third[1].Dimiss+Fouth[1].Dimiss+Fivth[1].Dimiss;
		var AllNightLeave=First[1].Leave+Second[1].Leave+Third[1].Leave+Fouth[1].Leave+Fivth[1].Leave;
		var AllNightABS=First[1].ABS+Second[1].ABS+Third[1].ABS+Fouth[1].ABS+Fivth[1].ABS;
		var AllNightATP=First[1].ATP+Second[1].ATP+Third[1].ATP+Fouth[1].ATP+Fivth[1].ATP;
		var AllNightProd = AllNightCount-AllNightNP-AllNightExpor-AllNightDimiss-AllNightLeave-AllNightABS-AllNightATP;
		var AllNightOTL=First[1].OTL+Second[1].OTL+Third[1].OTL+Fouth[1].OTL+Fivth[1].OTL;
		var AllNightLine=AllNightProd-AllNightOTL;
		var AllNightPC=First[1].PC+Second[1].PC+Third[1].PC+Fouth[1].PC+Fivth[1].PC;
		var AllNightDiff=AllNightLine-AllNightPC;
		
		
		tableContents+='<tr><td>匯總</td><td>日</td><td>'+AllSunCount+'</td><td>'+AllSunNP+'</td><td>'+AllSunExpor+'</td>'+
		'<td>'+AllSunDimiss+'</td><td>'+AllSunLeave+'</td><td>'+AllSunABS+'</td><td>'+AllSunATP+'</td><td>'+AllSunProd+'</td><td>'+AllSunOTL+'</td>'+
		'<td>'+AllSunLine+'</td><td>'+AllSunPC+'</td><td>'+AllSunDiff+'</td></tr>';
		tableContents+='<tr><td>匯總</td><td>夜</td><td>'+AllNightCount+'</td><td>'+AllNightNP+'</td><td>'+AllNightExpor+'</td>'+
		'<td>'+AllNightDimiss+'</td><td>'+AllNightLeave+'</td><td>'+AllNightABS+'</td><td>'+AllNightATP+'</td><td>'+AllNightProd+'</td><td>'+AllNightOTL+'</td>'+
		'<td>'+AllNightLine+'</td><td>'+AllNightPC+'</td><td>'+AllNightDiff+'</td></tr>';
		tableContents+="</tbody></table>"; 
		$('.CountEmpList').append(tableContents);
		$('.CountList').css('display','block');
	}
	
	function ShowCountEmpBCList(data){
		$('.CountEmpList').empty();
		var CountEmpBC=JSON.parse(data.countEmpBCList);
		var ProdEmp=JSON.parse(data.ProdEmp);
		console.log(CountEmpBC);
		var Depid=JSON.parse(data.Depid);
		var tableContents='<table id="EmpList" class="table table-striped"><thead><tr><th>課  別</th><th>班別</th><th>組裝人數</th><th>新進人數</th><th>調出人數</th>'+
		'<th>離職人數</th><th>請假人數</th><th>曠工人數</th><th>全技員</th><th>生產人數</th><th>排配人數</th><th>差異</th></tr></thead><tbody>';
		var AllSunCount=0;
		var AllSunNP=0;
		var AllSunExpor=0;
		var AllSunDimiss=0;
		var AllSunLeave=0;
		var AllSunABS=0;
		var AllSunATP=0;
		var AllSunProd = 0;
		var AllSunPC=0;
		var AllSunDiff=0;
		
		var AllNightCount=0;
		var AllNightNP=0;
		var AllNightExpor=0;
		var AllNightDimiss=0;
		var AllNightLeave=0;
		var AllNightABS=0;
		var AllNightATP=0;
		var AllNightProd =0;
		var AllNightPC=0;
		var AllNightDiff=0;
		
		for(var i=0;i<Depid.length;i++){
			tableContents+='<tr>';
			var SunDepidCount=0;SunNP=0;SunExport=0;SunDimiss=0;SunLeave=0;SunABS=0;SunATS=0;SunPD=0;SunPd=0;
			var NightDepidCount=0;NightNP=0;NightExport=0;NightDimiss=0;NightLeave=0;NightABS=0;NightATS=0;NightPD=0;NightPd=0;
			for(var j=0;j<CountEmpBC.length;j++){
				if(CountEmpBC[j].Depid==Depid[i]){
					if(CountEmpBC[j].Class_No!="11"){
						SunDepidCount++;
						
						if(CountEmpBC[j].Status.indexOf("4_")!= -1){
							SunNP++;
							
						}else if(CountEmpBC[j].Status.indexOf("3_")!= -1){
							SunExport++;
							
						}else if(CountEmpBC[j].Status.indexOf("5_")!= -1){
							SunDimiss++;
							
						}else if(CountEmpBC[j].Status.indexOf("1_")!= -1){
							SunLeave++;
							
						}else if(CountEmpBC[j].Status.indexOf("2_")!= -1){
							SunABS++;
							
						}else if(CountEmpBC[j].Status.indexOf("6_")!= -1){
							SunATS++;
							
						}
						SunPD=SunDepidCount-SunNP-SunExport-SunDimiss-SunLeave-SunABS-SunATS;
						
					}else{
						NightDepidCount++;
						
						if(CountEmpBC[j].Status.indexOf("4_")!= -1){
							NightNP++;
							
						}else if(CountEmpBC[j].Status.indexOf("3_")!= -1){
							NightExport++;
							
						}else if(CountEmpBC[j].Status.indexOf("5_")!= -1){
							NightDimiss++;
							
						}else if(CountEmpBC[j].Status.indexOf("1_")!= -1){
							NightLeave++;
							
						}else if(CountEmpBC[j].Status.indexOf("2_")!= -1){
							NightABS++;
							
						}else if(CountEmpBC[j].Status.indexOf("6_")!= -1){
							NightATS++;
							
						}
						NightPD=NightDepidCount-NightNP-NightExport-NightDimiss-NightLeave-NightABS-NightATS;
						
					}
					
				}
			}
			for(var z=0;z<ProdEmp.length;z++){
				
				if(ProdEmp[z].Depid==Depid[i]&&ProdEmp[z].Shift=="日"){
					var s=z+1
					SunPd=ProdEmp[z].Number_Of_People;
					if(z==ProdEmp.length-1){
						NightPd=0;
					}else{
						if(ProdEmp[s].Depid!=Depid[i]){
							NightPd=0;
						}
					}	
				}else if(ProdEmp[z].Depid==Depid[i]&&ProdEmp[z].Shift=="夜"){
					NightPd=ProdEmp[z].Number_Of_People;
				}
			}

			var SunDiff = SunPD-SunPd;
			var NightDiff=NightPD-NightPd;
			AllSunCount+=SunDepidCount;
			AllSunNP+=SunNP;
			AllSunExpor+=SunExport;
			AllSunDimiss+=SunDimiss;
			AllSunLeave+=SunLeave;
			AllSunABS+=SunABS;
			AllSunATP+=SunATS;
			AllSunProd=AllSunCount-AllSunNP-AllSunExpor-AllSunDimiss-AllSunLeave-AllSunABS-AllSunATP;
			AllSunPC+=parseInt(SunPd);
			AllSunDiff+=SunDiff;
			
			
			AllNightCount+=NightDepidCount;
			AllNightNP+=NightNP;
			AllNightExpor+=NightExport;
			AllNightDimiss+=NightDimiss;
			AllNightLeave+=NightLeave;
			AllNightABS+=NightABS;
			AllNightATP+=NightATS;
			AllNightProd=AllNightCount-AllNightNP-AllNightExpor-AllNightDimiss-AllNightLeave-AllNightABS-AllNightATP;
			AllNightPC+=parseInt(NightPd);
			AllNightDiff+=NightDiff;
			
			tableContents+='<td>'+Depid[i]+'</td><td>日</td><td>'+SunDepidCount+'</td><td>'+SunNP+'</td><td>'+SunExport+'</td><td>'+SunDimiss+'</td><td>'+SunLeave+'</td><td>'+SunABS+'</td><td>'+SunATS+'</td>'+
			'<td>'+SunPD+'</td><td>'+SunPd+'</td><td>'+SunDiff+'</td></tr>';
			tableContents+='<tr><td>'+Depid[i]+'</td><td>夜</td><td>'+NightDepidCount+'</td><td>'+NightNP+'</td><td>'+NightExport+'</td><td>'+NightDimiss+'</td><td>'+NightLeave+'</td><td>'+NightABS+'</td><td>'+NightATS+'</td>'+
			'<td>'+NightPD+'</td><td>'+NightPd+'</td><td>'+NightDiff+'</td></tr>';
		}
		tableContents+='<td>匯總</td><td>日</td><td>'+AllSunCount+'</td><td>'+AllSunNP+'</td><td>'+AllSunExpor+'</td><td>'+AllSunDimiss+'</td><td>'+AllSunLeave+'</td><td>'+AllSunABS+'</td><td>'+AllSunATP+'</td>'+
		'<td>'+AllSunProd+'</td><td>'+AllSunPC+'</td><td>'+AllSunDiff+'</td></tr>';
		tableContents+='<tr><td>匯總</td><td>夜</td><td>'+AllNightCount+'</td><td>'+AllNightNP+'</td><td>'+AllNightExpor+'</td><td>'+AllNightDimiss+'</td><td>'+AllNightLeave+'</td><td>'+AllNightABS+'</td><td>'+AllNightATP+'</td>'+
		'<td>'+AllNightProd+'</td><td>'+AllNightPC+'</td><td>'+AllNightDiff+'</td></tr>';
		tableContents+='</tbody></table>'
		$('.CountEmpList').append(tableContents);
		$('.CountList').css('display','block');
		//console.log(tableContents);
	}
	function ShowABEmpList(data){
		$('.ABCountEmpList').empty();
		var CountABEmp=JSON.parse(data.ABEmp);
		var tableContents=`<table id="ABEmpList" class="table table-striped"><thead><th>線  別</th><th>異動機種</th><th>異動原因</th><th>人數</th></thead><tbody>`;
		for(var i=0;i<CountABEmp.length;i++){
			tableContents+=`<tr><td>${CountABEmp[i].Depid}</td><td>${CountABEmp[i].Prod_Name}</td><td>${CountABEmp[i].Reason}</td><td>${CountABEmp[i].Number_Of_People}</td></tr>`;
		}
		tableContents+=`</tbody></table>`;
		$('.ABCountEmpList').append(tableContents);
		$('.ABCountList').css('display','block');
	}
})