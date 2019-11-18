<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<html>
<head>
<c:url value="/resources/assets/css/icons.css" var="iconsCSS" />
<c:url value="/resources/assets/css/bootstrap.css" var="bootstrapCSS" />
<c:url value="/resources/assets/css/plugins.css" var="pluginsCSS" />
<c:url value="/resources/assets/css/main.css" var="mainCSS" />
<c:url value="/resources/css/bootstrap/bootstrap-select.min.css" var="bootstrapSelectCSS" />

<link href="${iconsCSS}" rel="stylesheet">
<link href="${bootstrapCSS}" rel="stylesheet">
<link href="${pluginsCSS}" rel="stylesheet">
<link href="${mainCSS}" rel="stylesheet">
<link href="${bootstrapSelectCSS}" rel="stylesheet">

<c:url value="/resources/assets/js/jquery-1.8.3.min.js"
	var="assetsJqueryJS" />
<c:url value="/resources/assets/My97DatePicker/WdatePicker.js" var="wdatePickerJS" />
<c:url value="/resources/js/Project/ProdPerson.js?version=${resourceVersion}" var="modifyProdPersonJS" />
<c:url value="/resources/js/Project/untils_date.js?version=${resourceVersion}" var="modifyUntilsJS" />
<c:url value="/resources/js/jquery/jquery-1.11.3.min.js" var="JqueryJS" />
<c:url value="/resources/js/bootstrap/bootstrap.min.js" var="bootstrapJS" />
<c:url value="/resources/js/TableExport/jquery.table2excel.min.js" var="tableExportJS" />
<c:url value="/resources/js/Project/Common.Utils.js?version=${resourceVersion}" var="Common"/>
<c:url value="/resources/js/bootstrap/bootstrap-select.min.js" var="bootstrapSelectJS" />
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<c:url value="/resources/js/Project/tableToExcel.js?version=${resourceVersion}" var="tableToExcel" />
<c:url value="/resources/js/Project/testTableExcel.js?version=${resourceVersion}" var="testTableExcel" />
<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script src="${bootstrapSelectJS}" type="text/javascript"></script>
<script src="${wdatePickerJS}" language="javascript" type="text/javascript"></script>
<script src="${Common}" type="text/javascript"></script>
<script src="${tableExportJS}" type="text/javascript"></script>
<script type="text/javascript" src='${AjaxCheckSessionJS}'></script>
<script src="${modifyProdPersonJS}" type="text/javascript"></script>
<script src="${modifyUntilsJS}" type="text/javascript"></script>
<script src="${tableToExcel}" type="text/javascript"></script>
<script src="${testTableExcel}" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <script type="text/javascript">
function checkInput(){
	var lineno = $("#lineno").val();
	var shift = $("#shift").val();
	var Prod_Name = $("#Prod_Name").val();
	var Prod_Code = $("#Prod_Code").val();
	var Number_of_People = $("#Number_of_People").val();
	if(lineno.length<=0||Prod_Name.length<=0||Prod_Code.length<=0||Number_of_People.length<=0){
		alert("排配信息缺失，請確認后再提交！");
		return false;
	}
}

function getWeekDate(){
	var year = $("#selectYear").val();
	var week = $("#selectWeek").val();
	console.log(week);
	var start_end = getStartAndEndByWeek(year,week);
	var se_arr = start_end.split("_");
	var startDate = se_arr[0];
	var endDate = se_arr[1];
	var sumDate = "起止時間為："+startDate+" 至 "+ endDate;
	
	document.getElementById("showWeekDate").innerHTML=sumDate;
}

//根据某年某周获取该周的起始和截止日期  create by lishaodng
    function getStartAndEndByWeek(year,week){
        var Nowdate=new Date(year, 0, 1);  
        var nowweek_start=new Date(Nowdate-(Nowdate.getDay()-1)*86400000);    
        var nowweek = 1;
        var start = new Date(nowweek_start-86400000*7*(nowweek-week)).format("yyyy-MM-dd");        
        var end = new Date(nowweek_start-86400000*7*(nowweek-week) + 86400000*6).format("yyyy-MM-dd");    
        return start + '_' + end;
    }
</script> -->
<title>排配機種</title>
</head>
<body style="position:relative;">
		<div id="header" class="header-fixed">
		<div class="navbar" id='nav-other'>
			<a class="navbar-brand" href="Login"> <i
				class="im-windows8 text-logo-element animated bounceIn"></i> <span
				class="text-logo">FOX</span><span class="text-slogan" id='font-other'>LINK</span>
			</a>
		</div>
		<!-- Start .header-inner -->
	</div>

       <div style="position: absolute; top: 55px; margin-left: 10px">
<div class="container-fluid"  style="margin: 50px 10% 0 0;">
<p style="top: 55px; margin-left: 10px;width:50%;float:left;">排配機種輸入:</p>
<div  style="top:55px; margin-left: 10px;width:52%;float:left;">
<div class="panel-body" style="border: 1px solid #e1e3e6;">

                      <p>選擇年份-
                      <select id="selectYear">
				<option id="year" value="2019">2019</option>
			</select></p>
			<p>
			選擇周數-<select id="selectWeek">
			</select>
			<script>
				var cch = "";
				for(var i=1;i<=53;i++){
					cch += "<option value = "+i+">"+i+"</option>"
				}
				document.getElementById("selectWeek").innerHTML=cch;
				
			</script>
			</p>
			<p id="showWeekDate">起止時間為：</p>
			<p>
			<table id="ProdPerson" class="table table-bordered" style="border:2px solid #f3f5f6;table-layout:fixed;">
						<thead>
						<tr>
								<th style="width: 13%; text-align:center;">線別</th>
								<th style="width: 8%"; text-align:center;">班別</th>			
								<th style="width: 22% ; text-align:center;">機種名稱</th>
								<th style="width: 22%; text-align:center;">機種料號</th>
								<th style="width: 22%; text-align:center;">人數</th>
								<th style="width: 13%; text-align:center;">新增</th>
							</tr>
						</thead>
						  <tbody>
    <tr">
      <td><select id="depid" style="width: 100%;">
		</select></td>
      <td><select id="shift" style="width: 100%;">
								<option value="日">日</option>
					<option value="夜">夜</option>
				</select></td>
      <td><input id="MachineName" style="width: 100%;"></td>
      <td><input id="MachineNo" style="width: 100%;"></td>
      <td><input id="Number" style="width: 100%;"></td>
      <td><button id="InsertProdperson" type="button">新增人數</button></td>
    </tr>
  </tbody>
					</table>
			</p>
					</div>
				</div>
				<p style="top:55px; margin-left: 10px;width:50%;float:left;margin-top: 10px;">刪除排配機種:</p>
				<div  style="top:55px; margin-left: 10px;width:52%;float:left;">
<div class="panel-body" style="border: 1px solid #e1e3e6;">

                      <p>選擇年份-
                      <select id="selectYearD">
				<option id="yearD" value="2019">2019</option>
			</select></p>
			<p>
			選擇周數-<select id="selectWeekD">
			</select>
			<script>
				var cch = "";
				for(var i=1;i<=53;i++){
					cch += "<option value = "+i+">"+i+"</option>"
				}
				document.getElementById("selectWeekD").innerHTML=cch;
			</script>
			</p>
			<p id="showWeekDateD">起止時間為：</p>
			<p>線別:<select id="depidD">
		</select></p>
		<p><input type="button"
					id="DeleteProdperson" name="DeleteProdperson"
					class="btn btn-primary" value="查詢"></p>
					</div>
					
			<div class="panel-body"  style="padding-top: 0px;">
 <div class="row"   style="border: 1px solid #e1e3e6;display:none;" id="prodPersonTable">
 <center><p style="font-size: 28px;"><b>各線人力明細</b></p></center>
<div class="col-md-12">
<table id="ProdPersonD" class="table table-bordered" style="border:2px solid #f3f5f6;table-layout:fixed;">
						<thead>
						<tr>
								<th style="width: 8%; text-align:center;">線別</th>
								<th style="width: 6%; text-align:center;">周數</th>			
								<th style="width: 5%; text-align:center;">班別</th>
								<th style="width: 10%; text-align:center;">機種名稱</th>
								<th style="width: 18%; text-align:center;">機種料號</th>
								<th style="width: 10%; text-align:center;">人數</th>
								<th style="width: 13%; text-align:center;">刪除</th>
							</tr>
						</thead>
						  <tbody>
    <!--  <tr>
      <td style="text-align:center;" id="t1"></td>
      <td style="text-align:center;" id="t2"></td>
      <td style="text-align:center;" id="t3"></td>
    </tr> -->
  </tbody>
					</table>
					</div>
			
 
 </div>

		
</div> 
				</div>
</div>

     </div>
</body>

</html>