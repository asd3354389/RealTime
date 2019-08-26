/**
 * 加班審核相關的Function
 */
//var currentClassNoInfo;

function produceClassInfoArray(ClassInfo){
	var ClassInfoArray=[];
	ClassInfoArray[0]=[ClassInfo.classStart,ClassInfo.restStart1];
	ClassInfoArray[1]=[ClassInfo.restEnd1,ClassInfo.classEnd];
	ClassInfoArray[2]=[ClassInfo.restEnd2,ClassInfo.restEnd2];
	ClassInfoArray[3]=[ClassInfo.overTimeStart,ClassInfo.overTimeStart];
	return ClassInfoArray;
}

/* 根据班别计算加班小时数和加班起始时间逻辑*/
function calOverTime(overTimeType, calOverTimeEmps, currentClassNoInfo) {
    var overTimeHours = 0; //計算後的加班時間（小時）
    var calOverTimeStart = 0;
    var calOverTimeEnd = 0;
    var intervalDStartTime, intervalDEndTime; //上班
    var intervalRST1_StartTime, intervalRST1_EndTime; //休息一
    var intervalRST2_StartTime, intervalRST2_EndTime; //休息＝
    var adjustOverTimeEmps = calOverTimeEmps.map(a >= Object.assign({}, a));
    var adjustClassNoInfo = JSON.parse(JSON.stringify(currentClassNoInfo));
    var ClassInfoArray=produceClassInfoArray(adjustClassNoInfo);
    var overTimeStartTime,overTimeEndTime;
    /*遍歷overTimeEmps*/

    for (var i = 0; i < adjustOverTimeEmps.length; i++) {
        var overTimeEmpInfo = adjustOverTimeEmps[i];
        overTimeHours=0;
        calOverTimeStart = 0;
        calOverTimeEnd = 0;
        if (overTimeType == "1") {
            //Normal
            if (adjustClassNoInfo.classEnd < adjustClassNoInfo.classStart) {
            	//夜班
      /*          Date.prototype.addDays = function(days) {
                    var dat = new Date(this.valueOf());
                    dat.setDate(dat.getDate() + days);
                    return dat;
                }
                overTimeEmpInfo.onDutyTime.setDate(overTimeEmpInfo.onDutyTime.addDays(1));
                */
                var tempDay = overTimeEmpInfo.onDutyTime.getDate();    //getDate() 方法可返回月份的某一天。
                overTimeEmpInfo.onDutyTime.setDate(tempDay + 1);
                overTimeEmpInfo.onDutyTime.setHours(adjustClassNoInfo.overTimeStart.substring(0, 2),  adjustClassNoInfo.overTimeStart.substring(2, 4), 0);
                
            } else {
            	//白班
                overTimeEmpInfo.onDutyTime.setHours(adjustClassNoInfo.overTimeStart.substring(0, 2), adjustClassNoInfo.overTimeStart.substring(2, 4), 0);
            }
            overTimeHours = overTimeEmpInfo.offDutyTime - overTimeEmpInfo.onDutyTime;
        } else if (overTimeType == "2") {
            /*holiday on duty*/
        	  	 
            if (adjustClassNoInfo.classEnd > adjustClassNoInfo.classStart) {
                //班別無跨夜
                //adjustClassNoInfo.overTimeStart = null; //假日班忽略加班起始時間
                for(var j=0;j<ClassInfoArray.length;j++){
              		var shiftOnDutyTimeStamp = new Date(overTimeEmpInfo.onDutyTime.valueOf());
              			shiftOnDutyTimeStamp.setHours(ClassInfoArray[j][0].substring(0, 2), ClassInfoArray[j][0].substring(2, 4), 0);
                    var shiftOffDutyTimeStamp = new Date(overTimeEmpInfo.offDutyTime.valueOf());
                    	shiftOffDutyTimeStamp.setHours(ClassInfoArray[j][1].substring(0, 2), ClassInfoArray[j][1].substring(2, 4), 0);
                    calOverTimeStart = overTimeEmpInfo.onDutyTime - shiftOnDutyTimeStamp; //上班时间-上班时段的起始时间
                    calOverTimeEnd = overTimeEmpInfo.offDutyTime - shiftOffDutyTimeStamp; //下班时间-上班时段的终止时间
                    
                    //如果上班刷卡时间<上班时段的起始时间   && 下班刷卡时间<上班时段的起始时间
                    if ((overTimeEmpInfo.onDutyTime - shiftOnDutyTimeStamp) < 0 && (overTimeEmpInfo.offDutyTime - shiftOnDutyTimeStamp) < 0)
                        continue;

					//如果上班刷卡时间>上班时段的终止时间   && 下班刷卡时间>上班时段的终止时间
                    if ((overTimeEmpInfo.onDutyTime - shiftOffDutyTimeStamp) > 0 && (overTimeEmpInfo.offDutyTime - shiftOffDutyTimeStamp) > 0)
                        continue;
                		
                    if (calOverTimeStart > 0) {
                       // 因上刷時間超過班別上刷規定時間，所以加班開始時間以user上刷時間為主
                       // overTimeEmpInfo.onDutyTime.setSeconds(0);
                    	overTimeStartTime=overTimeEmpInfo.onDutyTime.setSeconds(0);
                    } else {
                       // 上刷時間以班別規定的上刷時間為主
                    	//overTimeStartTime=new Date(overTimeEmpInfo.offDutyTime.valueOf());                   	
                    	//overTimeStartTime.setHours(adjustClassNoInfo.classStart.substring(0, 2), adjustClassNoInfo.classStart.substring(2, 4), 0);
                    	overTimeStartTime=shiftOnDutyTimeStamp;
                    	
                       // overTimeEmpInfo.onDutyTime = shiftOnDutyTimeStamp;
                        if (j == 0) {
                        	overTimeEmpInfo.onDutyTime.setHours(ClassInfoArray[0][0].substring(0, 2), ClassInfoArray[0][0].substring(2, 4), 0);
                        }
           
                        if (adjustClassNoInfo.classStart && adjustClassNoInfo.restStart1) {
                            intervalDStartTime = new Date(overTimeEmpInfo.onDutyTime.valueOf());
                            intervalDStartTime.setHours(adjustClassNoInfo.classStart.substring(0, 2), adjustClassNoInfo.classStart.substring(2, 4), 0);
                            intervalDEndTime = new Date(overTimeEmpInfo.offDutyTime.valueOf());
                            intervalDEndTime.setHours(adjustClassNoInfo.restStart1.substring(0, 2), adjustClassNoInfo.restStart1.substring(2, 4), 0);
                        }

                        if (adjustClassNoInfo.restEnd1 && adjustClassNoInfo.classEnd) {
                            intervalRST1_StartTime = new Date(overTimeEmpInfo.onDutyTime.valueOf());
                            intervalRST1_StartTime.setHours(adjustClassNoInfo.restEnd1.substring(0, 2), adjustClassNoInfo.restEnd1.substring(2, 4), 0);
                            intervalRST1_EndTime = new Date(overTimeEmpInfo.offDutyTime.valueOf());
                            intervalRST1_EndTime.setHours(adjustClassNoInfo.classEnd.substring(0, 2), adjustClassNoInfo.classEnd.substring(2, 4), 0);
                        }

                        if (currentClassNoInfo.restEnd2 && currentClassNoInfo.restEnd2) {
                            intervalRST2_StartTime = new Date(overTimeEmpInfo.onDutyTime.valueOf());
                            intervalRST2_StartTime.setHours(adjustClassNoInfo.restEnd2.substring(0, 2), adjustClassNoInfo.restEnd2.substring(2, 4), 0);
                            intervalRST2_EndTime = new Date(overTimeEmpInfo.offDutyTime.valueOf());
                            intervalRST2_EndTime.setHours(adjustClassNoInfo.restEnd2.substring(0, 2), adjustClassNoInfo.restEnd2.substring(2, 4), 0);
                        }

                        if (overTimeEmpInfo.onDutyTime > intervalDEndTime && overTimeEmpInfo.onDutyTime < intervalRST1_StartTime) {
                            overTimeEmpInfo.onDutyTime.setHours(intervalRST1_StartTime.getHours(), intervalRST1_StartTime.getMinutes(), 0);
                        }
                        
                    }


                    if (calOverTimeEnd < 0 || j==ClassInfoArray.length-1) {
                       // overTimeEmpInfo.offDutyTime = new Date(overTimeEmpInfo.offDutyTime.setSeconds(0));
                       // overTimeHours += overTimeEmpInfo.offDutyTime - overTimeEmpInfo.onDutyTime;
                    	overTimeEndTime=overTimeEmpInfo.offDutyTime.setSeconds(0)
                    } else {
                      //  overTimeHours += shiftOffDutyTimeStamp - overTimeEmpInfo.onDutyTime;
                    	overTimeEndTime = shiftOffDutyTimeStamp;
                    }
                    
                    console.log("overTimeStartTime:" + overTimeStartTime);
                    console.log("overTimeEndTime:" + overTimeEndTime);

                    overTimeHours += overTimeEndTime - overTimeStartTime;
                }          
            } else {
                /*night shift*/
                //先取得員工刷卡日期
            		for(var j=0;j<ClassInfoArray.length;j++){
                        var shiftOnDutyTimeStamp = new Date(overTimeEmpInfo.yd);   //yd:下班刷卡时间日期-12小时   yd24小时制
                       
                        //處理上刷
                       /* if (adjustClassNoInfo.classStart.substring(0, 2) >= 0 && adjustClassNoInfo.classStart.substring(0, 2) < 12) {
                            //班別規定的上刷時間（小時）介於凌晨12點至上午12點間
*/                          
                       if (ClassInfoArray[j][0].substring(0, 2) >= 0 && ClassInfoArray[j][0].substring(0, 2) < 12) {
                            shiftOnDutyTimeStamp = new Date(shiftOnDutyTimeStamp.getTime() + 24 * 60 * 60 * 1000);
                            shiftOnDutyTimeStamp.setHours(ClassInfoArray[j][0].substring(0, 2), ClassInfoArray[j][0].substring(2, 4));
                        } else {
                            shiftOnDutyTimeStamp.setHours(ClassInfoArray[j][0].substring(0, 2), ClassInfoArray[j][0].substring(2, 4));
                        }
                       
                       //處理下刷
                        var shiftOffDutyTimeStamp = new Date(overTimeEmpInfo.yd);
                       // if (currentClassNoInfo.classEnd.substring(0, 2) >= 0 && currentClassNoInfo.classEnd.substring(2, 2) < 12) {
                        if (ClassInfoArray[j][1].substring(0, 2) >= 0 && ClassInfoArray[j][1].substring(0, 2) < 12) {    
                            shiftOffDutyTimeStamp = new Date(shiftOffDutyTimeStamp.getTime() + 24 * 60 * 60 * 1000);
                            shiftOffDutyTimeStamp = shiftOffDutyTimeStamp.setHours(ClassInfoArray[j][1].substring(0, 2), ClassInfoArray[j][1].substring(2, 4),0);
                        } else {
                            shiftOffDutyTimeStamp = shiftOffDutyTimeStamp.setHours(ClassInfoArray[j][1].substring(0, 2), ClassInfoArray[j][1].substring(2, 4),0);
                        }


                        calOverTimeStart = overTimeEmpInfo.onDutyTime - shiftOnDutyTimeStamp;//上班时间-上班时段的起始时间
                        calOverTimeEnd = overTimeEmpInfo.offDutyTime - shiftOffDutyTimeStamp;//下班时间-上班时段的終止时间
                        
                        //如果上班刷卡时间<上班时段的起始时间   && 下班刷卡时间<上班时段的起始时间   		
                        if ((overTimeEmpInfo.onDutyTime - shiftOnDutyTimeStamp) < 0 && (overTimeEmpInfo.offDutyTime - shiftOnDutyTimeStamp) < 0)
                            continue;

            			//如果上班刷卡时间>上班时段的终止时间   && 下班刷卡时间>上班时段的终止时间
                        if ((overTimeEmpInfo.onDutyTime - shiftOffDutyTimeStamp) > 0 && (overTimeEmpInfo.offDutyTime - shiftOffDutyTimeStamp) > 0)
                            continue;
                        
                        if (calOverTimeStart > 0) {   //上班刷卡时间 晚于 上班时段的起始时间
                         //   overTimeEmpInfo.onDutyTime.setSeconds(0);
                            overTimeStartTime=overTimeEmpInfo.onDutyTime.setSeconds(0);
                        } else {                      //上班刷卡时间 早于 上班时段的起始时间
                           // overTimeEmpInfo.onDutyTime.setHours(shiftOnDutyTimeStamp.getHours(), shiftOnDutyTimeStamp.getMinutes(), 0);
                        	overTimeStartTime=shiftOnDutyTimeStamp;
                        	
                        	if (j == 0) {
                        		overTimeEmpInfo.onDutyTime.setHours(ClassInfoArray[0][0].substring(0, 2), ClassInfoArray[0][0].substring(2, 4), 0);
                            }
                            /**/
                            intervalDStartTime = new Date(overTimeEmpInfo.onDutyTime.valueOf());
                            intervalDStartTime.setHours(adjustClassNoInfo.classStart.substring(0, 2), adjustClassNoInfo.classStart.substring(2, 4), 0);
                            intervalDEndTime = new Date(overTimeEmpInfo.onDutyTime.valueOf());
                            intervalDEndTime.setHours(adjustClassNoInfo.restStart1.substring(0, 2), adjustClassNoInfo.restStart1.substring(2, 4), 0);

                            /**/
                            intervalRST1_StartTime = new Date(overTimeEmpInfo.onDutyTime.valueOf());
                            intervalRST1_StartTime.setHours(adjustClassNoInfo.restEnd1.substring(0, 2), adjustClassNoInfo.restEnd1.substring(2, 4), 0);
                            intervalRST1_EndTime = new Date(overTimeEmpInfo.offDutyTime.valueOf());
                            intervalRST1_EndTime.setHours(adjustClassNoInfo.classEnd.substring(0, 2), adjustClassNoInfo.classEnd.substring(2, 4), 0);

                            /**/
                            intervalRST2_StartTime = new Date(overTimeEmpInfo.onDutyTime.valueOf());
                            intervalRST2_StartTime.setHours(adjustClassNoInfo.restEnd2.substring(0, 2), adjustClassNoInfo.restEnd2.substring(2, 4), 0);
                            intervalRST2_StartTime = new Date(overTimeEmpInfo.offDutyTime.valueOf());
                            intervalRST2_StartTime.setHours(adjustClassNoInfo.restEnd2.substring(0, 2), adjustClassNoInfo.restEnd2.substring(2, 4), 0);

                            if (overTimeEmpInfo.onDutyTime > intervalDEndTime && overTimeEmpInfo.onDutyTime < intervalRST1_StartTime) {
                                overTimeEmpInfo.onDutyTime.setHours(intervalRST1_StartTime.getHours(), intervalRST1_StartTime.getMinutes(), 0);
                            }
                        }

                     /*   if (calOverTimeEnd < 0 || overTimeEmpInfo.onDutyTime) {
                            overTimeEmpInfo.offDutyTime.setSeconds(0);
                        } else {
                            overTimeHours += shiftOffDutyTimeStamp - overTimeEmpInfo.onDutyTime;
                        }*/
                        
                        
                        if (calOverTimeEnd < 0 || j==ClassInfoArray.length-1) {              
                         	overTimeEndTime=overTimeEmpInfo.offDutyTime.setSeconds(0)
                         } else {
                         	overTimeEndTime = shiftOffDutyTimeStamp;
                         }
                         
                         console.log("overTimeStartTime:" + overTimeStartTime);
                         console.log("overTimeEndTime:" + overTimeEndTime);

                         overTimeHours += overTimeEndTime - overTimeStartTime;
                         
                        
            		}
            }
        }
        overTimeHours = overTimeHours / 3600000;
        overTimeHours = getNum(overTimeHours);
        if (overTimeHours < 0) {
            overTimeHours = 0;
        }
        //顯示加班時段
        overTimeEmpInfo.overTimeInterval = getHour(overTimeEmpInfo.onDutyTime) + "-" + getHour(overTimeEmpInfo.offDutyTime);

        //顯示加班小時
        overTimeEmpInfo.overTimeHours = overTimeHours;
        overTimeHours = 0;
    }
    return adjustOverTimeEmps;
}

function GetOverTimeEmpsFromServer(WorkshopNo, ClassNo, SwipeDate, RCNO, IsAbnormal) {
    var OTEmpList = new Array();
    var URLPath = 'EmpsInOTSheet.show?WorkshopNo=' + WorkshopNo + '&RCNO=' + RCNO +
        '&ClassNo=' + ClassNo + '&OverTimeDate=' + SwipeDate +
        '&IsIdentified=0&IsAbnormal=' + IsAbnormal;
    $.getJSON(URLPath, function(result) {
        if (result.ErrorMsg != null) {
            OTEmpList = null;
        } else {
            for (var i = 0; i < result.length; i++) {
                var OTEmpInfo = new OverTimeSheet(result[i]["id"],
                    result[i]["NAME"],
                    result[i]["depid"],
                    result[i]["depname"],
                    result[i]["direct"],
                    result[i]["costid"],
                    result[i]["yd"],
                    result[i]["swipecardtime"],
                    result[i]["swipecardtime2"]);
                OTEmpList.push(OTEmpInfo);
            }
        }
        return OTEmpList;
    });
}

function SubmitEmployeeOverTimeInfo2Server(IsAbnormal, OThourConfirm) {
	var OTResult = '';
    $.ajax({
        type: 'POST',
        url: 'PendingOT.do',
        async:false,
        data: {
            "IsAbnormal": IsAbnormal,
            "OT_Hour_Confirm": JSON.stringify(OThourConfirm)
        },
        error: function(e) {
        	OTResult = e+'';
        	//alert(e);
        },
        success: function(result) {
        	if(result!=null && result!=''){
        		if (result.ErrorCode == 200) {
        			OTResult = result.ErrorCode+'';
        			/*alert('加班提報成功，視窗將關閉');
        			window.open('', '_self', '');
        			window.close();*/
        		} else {
        			OTResult = result.ErrorMsg+'';
        			//alert(result.ErrorMsg);
        		}
        	}else{
        		OTResult = '加班提報失敗,請聯繫系統維護人員!';
        		//alert('加班提報失敗,請聯繫系統維護人員!');
        	}
        }
    })
    return OTResult;
}

function SubmitEmployeeOverTimeInfo2ServerByDepid(IsAbnormal, OThourConfirm) {
	var OTResult = '';
    $.ajax({
        type: 'POST',
        url: 'PendingOTByDepid.do',
        async:false,
        data: {
            "IsAbnormal": IsAbnormal,
            "OT_Hour_Confirm": JSON.stringify(OThourConfirm)
        },
        error: function(e) {
        	OTResult = e+'';
        	//alert(e);
        },
        success: function(result) {
        	if(result!=null && result!=''){
        		if (result.ErrorCode == 200) {
        			OTResult = result.ErrorCode+'';
        			/*alert('加班提報成功，視窗將關閉');
        			window.open('', '_self', '');
        			window.close();*/
        		} else {
        			OTResult = result.ErrorMsg+'';
        			//alert(result.ErrorMsg);
        		}
        	}else{
        		OTResult = '加班提報失敗,請聯繫系統維護人員!';
        		//alert('加班提報失敗,請聯繫系統維護人員!');
        	}
        }
    })
    return OTResult;
}

function OverTimeSheet(employeeID, employeeName, deptID, direct, costID, yd,
    onDutyTime, offDutyTime, OverTimeInterval, OverTimeHours,BONUS) {
    /*加班人員資訊*/
    this.employeeID = employeeID;
    this.employeeName = employeeName;
    this.deptID = deptID;
    this.direct = direct;
    this.costID = costID;
    this.yd = yd;
    if (onDutyTime) {
        this.onDutyTime = new Date(onDutyTime);
    } else {
        //無上刷-->上刷時間=下刷時間
        this.onDutyTime = new Date(offDutyTime);
    }

    if (offDutyTime) {
        this.offDutyTime = new Date(offDutyTime);
    } else {
        //無下刷-->下刷時間=上刷時間
        this.offDutyTime = new Date(onDutyTime);
    }
    this.overTimeInterval = OverTimeInterval;
    this.overTimeHours = OverTimeHours;
    this.bonus = BONUS;
}


function ClassNoInfo(classStart, restStart1, restEnd1, restStart2, restEnd2, classEnd, overTimeStart) {
    /*班別資訊*/
    this.classStart = classStart;
    this.restStart1 = restStart1;
    this.restEnd1 = restEnd1;
    this.restStart2 = restStart2;
    this.restEnd2 = restEnd2;
    this.classEnd = classEnd;
    this.overTimeStart = overTimeStart;
}

function OThourConfirmInfo(ClassNo, RCNo, WorkshopNo,LineNo, OverTimeDate, CheckState, AssistantID, AssistantAccount,
		OverTimeType,OverTimeType1, ItemNumber,SelectedEmps, IsAbnormal,WorkContent) {
    this.ClassNo = ClassNo;
    this.RCNo = RCNo;
    this.WorkshopNo = WorkshopNo;
    this.LineNo = LineNo;
    this.OverTimeDate = OverTimeDate;
    this.CheckState = CheckState;
    this.AssistantID = AssistantID;
    this.AssistantAccount = AssistantAccount;
    this.OverTimeType = OverTimeType;
    this.OverTimeType1 = OverTimeType1;
    this.ItemNumber = ItemNumber;
    this.SelectedEmps = SelectedEmps;
    this.IsAbnormal = IsAbnormal;
    this.WorkContent = WorkContent;
}

function getNum(Num) {
    var front = 0;
    var surplus = 0;
    front = Math.floor(Num); //floor() 方法执行的是向下取整计算
    surplus = Num - front;
    if (surplus < 0.25) {
        surplus = 0;
    } else if (surplus > 0.25 && surplus < 0.5) {
        surplus = 0.25;
    } else if (surplus >= 0.5 && surplus < 0.75) {
        surplus = 0.5;
    } else if (surplus >= 0.75 && surplus < 1) {
        surplus = 0.75;
    }

    return surplus + front;
}

function getHour(strDate) {
    var hours = strDate.getHours();
    var mins = strDate.getMinutes();

    if (hours < 10) {
        hours = "0" + hours;
    }

    if (mins < 10) {
        mins = "0" + mins;
    }
    var Hour = hours + ":" + mins;
    return Hour;
}