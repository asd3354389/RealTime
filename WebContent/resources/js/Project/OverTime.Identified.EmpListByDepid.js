/**
 * 顯示已審核的加班人員列表
 */
$(document).ready(function(){	
	init();
	function init(callback){
		//填入QueryString Value
		var WorkshopNo=getParameterByName("WorkshopNo");
		var LineNo=getParameterByName("LineNo");
		var RCNO=getParameterByName("RCNO");
		var ClassNo=getParameterByName("ClassNo");
		var OverTimeDate=getParameterByName("OverTimeDate");
		var IsIdentified=getParameterByName("IsIdentified");
		var IsAbnormal=getParameterByName("IsAbnormal");
		ShowIdentifiedEmpList(WorkshopNo,LineNo,RCNO,ClassNo,OverTimeDate,IsIdentified,IsAbnormal);
	}
	
	function ShowIdentifiedEmpList(WorkshopNo,LineNo,RCNO,ClassNo,OverTimeDate,
			IsIdentified,IsAbnormal){
		$.ajax({
			type:'GET',
			url:'EmpsInOTSheetByDepid.show',
			data:{"WorkshopNo":WorkshopNo,"LineNo":LineNo,"RCNO":RCNO,
				"ClassNo":ClassNo,"OverTimeDate":OverTimeDate,
				"IsIdentified":IsIdentified,"IsAbnormal":IsAbnormal},
			error:function(e){
				alert(e);
			},
			success:function(result){
				$('#OTIdentifiedEmpTable tbody').empty();
				var HTMLElement='';
				if(result!=null && result!=''){
					if(result.ErrorCode && result.ErrorCode==500){
						alert('無資料');
						HTMLElement='<tr><td colspan="10">無資料</td></tr>'
							$('#OTIdentifiedEmpTable tbody').append(HTMLElement);
					}
					else{
						var j=1;
						for(var i=0;i<result.length;i++){
							HTMLElement='<tr><td>'+j+'</td>'+
							'<td>'+result[i]["Id"]+'</td>'+
							'<td>'+result[i]["Name"]+'</td>'+
							'<td>'+result[i]["DepID"]+'</td>'+
							'<td>'+result[i]["CostID"]+'</td>'+
							'<td>'+result[i]["Direct"]+'</td>'+
							'<td>'+result[i]["OverTimeDate"]+'</td>'+
							'<td>'+result[i]["OverTimeInterval"]+'</td>'+
							'<td>'+result[i]["OverTimeHours"]+'</td>';
							switch(result[i]["OverTimeType"]){
							case 1:
								HTMLElement+='<td>延時加班</td>';
								break;
							case 2:
								HTMLElement+='<td>例假日加班</td>';
								break;
							case 3:
								HTMLElement+='<td>節假日加班</td>';
								break;
							default:						
								break;
							}		
							HTMLElement+='<td>'+result[i]["Bonus"]+'</td>';
							switch(result[i]["NoteStates"]){
							case 0:
								HTMLElement+='<td>待產生加班單</td>';
								break;
							case 1:
								HTMLElement+='<td>已產生加班單</td>';
								break;
							case 2:
								HTMLElement+='<td>不符規定'+result[i]["Reason"]+'</td>';
								break;
							default:						
								break;
							}		
							HTMLElement+='</tr>';
							j++;
							$('#OTIdentifiedEmpTable tbody').append(HTMLElement);
						}
					}
				}else{
					alert('無資料');
					HTMLElement='<tr><td colspan="10">無資料</td></tr>'
						$('#OTIdentifiedEmpTable tbody').append(HTMLElement);
				}
			}
		});
	}
});