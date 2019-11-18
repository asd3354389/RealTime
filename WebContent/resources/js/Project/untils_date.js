 $(document).ready(function(){
	

	 //一天含 86,400,000 毫秒(24* 60 * 60*1000)    
      
    //获取本周是一年中的第几周  
     
    function getWeekOfYear(date) {   
        var d1 = date;  
        var d2 = new Date(date.getFullYear(), 0, 1);  
        var d = Math.round((d1 - d2) / 86400000);   
        return Math.ceil((d + ((d2.getDay() + 1) - 1)) / 7);   
    };  
   //根据某年某周获取该周的起始和截止日期  create by lishaodng
    function getStartAndEndByWeek(year,week){
        var Nowdate=new Date(year, 0, 1);  
        var nowweek_start=new Date(Nowdate-(Nowdate.getDay()-1)*86400000);    
        var nowweek = 1;
        var start = new Date(nowweek_start-86400000*7*(nowweek-week)).format("yyyy-MM-dd");        
        var end = new Date(nowweek_start-86400000*7*(nowweek-week) + 86400000*6).format("yyyy-MM-dd");    
        return start + '_' + end;
    }
    
    //本周第一天    
    function showWeekFirstDay()    
    {    
        var Nowdate=new Date();    
        var WeekFirstDay=new Date(Nowdate-(Nowdate.getDay()-1)*86400000);    
        return WeekFirstDay;    
    }    
    //本周最后一天    
    function showWeekLastDay()    
    {    
        var Nowdate=new Date();    
        var WeekFirstDay=new Date(Nowdate-(Nowdate.getDay()-1)*86400000);    
        var WeekLastDay=new Date((WeekFirstDay/1000+6*86400)*1000);    
        return WeekLastDay;    
    }    
          
    //上周第一天    
    function showPreviousFirstWeekDay(firstDay)    
    {    
        var WeekFirstDay=(firstDay==null?showWeekFirstDay():firstDay)  
        return new Date(WeekFirstDay-86400000*7)    
    }    
    //上周最后一天    
    function showPreviousLastWeekDay(firstDay)    
    {    
        var WeekFirstDay=(firstDay==null?showWeekFirstDay():firstDay)    
        return new Date(WeekFirstDay-86400000)    
    }    
      
    //下周第一天    
    function showNextFirstWeekDay(lastDay)    
    {    
        var MonthFirstDay=(lastDay==null?showWeekLastDay():lastDay)  
        return new Date((MonthFirstDay/1000+86400)*1000)    
    }    
    //下周最后一天    
    function showNextLastWeekDay(lastDay)    
    {    
        var MonthFirstDay=(lastDay==null?showWeekLastDay():lastDay)  
        return new Date((MonthFirstDay/1000+7*86400)*1000)    
    }    
      
    // 计算当前日期在本年度的周数    
    Date.prototype.getWeekOfYear = function(weekStart) { // weekStart：每周开始于周几：周日：0，周一：1，周二：2 ...，默认为周日    
        weekStart = (weekStart || 0) - 0;    
        if(isNaN(weekStart) || weekStart > 6)    
            weekStart = 0;  
        var year = this.getFullYear();    
        var firstDay = new Date(year, 0, 1);    
        var firstWeekDays = 7 - firstDay.getDay() + weekStart;    
        var dayOfYear = (((new Date(year, this.getMonth(), this.getDate())) - firstDay) / 86400000) + 1;    
        return Math.ceil((dayOfYear - firstWeekDays) / 7) + 1;    
    }  
        
    Date.prototype.format=function(fmt) {       
        var o = {       
        "M+" : this.getMonth()+1, //月份       
        "d+" : this.getDate(), //日       
        "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时       
        "H+" : this.getHours(), //小时       
        "m+" : this.getMinutes(), //分       
        "s+" : this.getSeconds(), //秒       
        "q+" : Math.floor((this.getMonth()+3)/3), //季度       
        "S" : this.getMilliseconds() //毫秒       
        };       
        var week = {       
        "0" : "\u65e5",       
        "1" : "\u4e00",       
        "2" : "\u4e8c",       
        "3" : "\u4e09",       
        "4" : "\u56db",       
        "5" : "\u4e94",       
        "6" : "\u516d"      
        };       
        if(/(y+)/.test(fmt)){       
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));       
        }       
        if(/(E+)/.test(fmt)){       
            fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);       
        }       
        for(var k in o){       
            if(new RegExp("("+ k +")").test(fmt)){       
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));       
            }       
        }       
        return fmt;       
    }  
       
    function getWeeks(num, obj){  
        var firstDay = showWeekFirstDay();  
        var lastDay = showWeekLastDay();  
        var divContent = "";  
        for(var i=0;i<num;i++){  
            lastDay = showPreviousLastWeekDay(firstDay);  
            firstDay = showPreviousFirstWeekDay(firstDay);  
            var year = lastDay.getFullYear();  
            var week = lastDay.getWeekOfYear(1);  
            divContent = "<option year='"+year+"' week='"+week+"'>"+year+"第" +week+ "周("+firstDay.format("yyyy-MM-dd")+"至"+lastDay.format("yyyy-MM-dd")+")</option>"+divContent;  
        }  
        firstDay = showWeekFirstDay();  
        lastDay = showWeekLastDay();  
        for(var i=0;i<num;i++){  
            var year = lastDay.getFullYear();  
            var week = lastDay.getWeekOfYear(1);  
            if(i!=0) {  
                divContent = divContent +"<option year='"+year+"' week='"+week+"'>"+ year+"第"+ week +"周("+firstDay.format("yyyy-MM-dd")+"至"+lastDay.format("yyyy-MM-dd")+")</option>";  
            } else {  
                divContent = divContent +"<option year='"+year+"' week='"+week+"'>"+ year+"第"+ week +"周("+firstDay.format("yyyy-MM-dd")+"至"+lastDay.format("yyyy-MM-dd")+")</option>";  
            }  
            firstDay = showNextFirstWeekDay(lastDay);  
            lastDay = showNextLastWeekDay(lastDay);  
        }  
        divContent = "<select id='yearWeekSelect' onchange='changeYearWeek(this);'>"+divContent+"</select>"  
          
        obj.html(divContent);  
    }  
    // 修改year、month字段值  
    function changeYearWeek(obj){  
        var $option = $(obj).children("option[selected]");  
        var year = $option.attr("year");  
        var week = $option.attr("week");  
        $("#_year").val(year);  
        $("#_week").val(week);  
    }  
      
    function changeSelected() {  
        var _year = $("#_year").val();  
        var _week = $("#_week").val();  
        $("#yearWeekSelect").children("option").each(function(){  
            if($(this).attr("year") == _year && $(this).attr("week") == _week) {  
                $(this).attr("selected", true);  
            }  
        })  
    }  
    //alert("ss:"+(new Date(2012,0,1).getWeekOfYear(1)));  
    //new Date(2012,0,1).getWeekOfYear(1)  
	/** 
	* 获取本周、本季度、本月、上月的开端日期、停止日期 
	*/ 
	var now = new Date(); //当前日期 
	var nowDayOfWeek = now.getDay(); //今天本周的第几天 
	var nowDay = now.getDate(); //当前日 
	var nowMonth = now.getMonth(); //当前月 
	var nowYear = now.getYear(); //当前年 
	nowYear += (nowYear < 2000) ? 1900 : 0; // 
	var lastMonthDate = new Date(); //上月日期 
	lastMonthDate.setDate(1); 
	lastMonthDate.setMonth(lastMonthDate.getMonth()-1); 
	var lastYear = lastMonthDate.getYear(); 
	var lastMonth = lastMonthDate.getMonth(); 
	//格局化日期：yyyy-MM-dd 
	function formatDate(date) { 
		var myyear = date.getFullYear(); 
		var mymonth = date.getMonth()+1; 
		var myweekday = date.getDate(); 
		if(mymonth < 10){ 
		mymonth = "0" + mymonth; 
		} 
		if(myweekday < 10){ 
		myweekday = "0" + myweekday; 
		} 
		return (myyear+"-"+mymonth + "-" + myweekday); 
	} 
	//获得某月的天数 
	function getMonthDays(myMonth){ 
		var monthStartDate = new Date(nowYear, myMonth, 1); 
		var monthEndDate = new Date(nowYear, myMonth + 1, 1); 
		var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24); 
		return days; 
	} 
 
	//获得本季度的开端月份 
	function getQuarterStartMonth(){ 
		var quarterStartMonth = 0; 
		if(nowMonth<3){ 
			quarterStartMonth = 0; 
		} 
		if(2<nowMonth && nowMonth<6){ 
		quarterStartMonth = 3; 
		} 
		if(5<nowMonth && nowMonth<9){ 
		quarterStartMonth = 6; 
		} 
		if(nowMonth>8){ 
		quarterStartMonth = 9; 
		} 
		return quarterStartMonth; 
	} 
 
	//获得本周的开端日期 
	function getWeekStartDate() { 
		var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek); 
		return formatDate(weekStartDate); 
	} 
 
	//获得本周的停止日期 
	function getWeekEndDate() { 
		var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek)); 
		return formatDate(weekEndDate); 
	} 
 
	//获得本月的开端日期 
	function getMonthStartDate(){ 
		var monthStartDate = new Date(nowYear, nowMonth, 1); 
		return formatDate(monthStartDate); 
	} 
 
	//获得本月的停止日期 
	function getMonthEndDate(){ 
		var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth)); 
		return formatDate(monthEndDate); 
	} 
 
	//获得上月开端时候 
	function getLastMonthStartDate(){ 
		var lastMonthStartDate = new Date(nowYear, lastMonth, 1); 
		return formatDate(lastMonthStartDate); 
	} 
 
	//获得上月停止时候 
	function getLastMonthEndDate(){ 
		var lastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth)); 
		return formatDate(lastMonthEndDate); 
	} 
 
	//获得本季度的开端日期 
	function getQuarterStartDate(){ 
		var quarterStartDate = new Date(nowYear, getQuarterStartMonth(), 1); 
		return formatDate(quarterStartDate); 
	} 
 
	//或的本季度的停止日期 
	function getQuarterEndDate(){ 
		var quarterEndMonth = getQuarterStartMonth() + 2; 
		var quarterStartDate = new Date(nowYear, quarterEndMonth, getMonthDays(quarterEndMonth)); 
		return formatDate(quarterStartDate); 
	}
	
	
});
   