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
<c:url value="/resources/js/Project/RealTime.Modify.SearchWorkDayCount.js?version=${resourceVersion}" var="modifySearchWorkDayCountJS" />
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
<script src="${modifySearchWorkDayCountJS}" type="text/javascript"></script>
<script src="${modifyUntilsJS}" type="text/javascript"></script>
<script src="${tableToExcel}" type="text/javascript"></script>
<script src="${testTableExcel}" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>連續上班時數查詢</title>
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
       		<h1 style="color:red;">根據人資規定，所有刷卡記錄只允許查詢近三個月的記錄</h1>
			<div class="panel-body" style="border: 1px solid #e1e3e6;">
<!-- 			        			 <label for="startDate">開始日期:</label> 
								<input id="startDate" class="Wdate" type="text" onClick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'%y-\#{%M-2}-01',maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" />
								<label for="startDate">結束日期:</label> 
								<input id="endDate" class="Wdate" type="text" onClick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off" /> -->
								<input type="radio" name="changeType" checked id="empid" value="empid"><label for="empid">員工工號</label>
								<input type="radio" name="changeType" id="depid" value="depid"><label for="depid">部門代碼</label>
								<input type="radio" name="changeType" id="costid" value="costid"><label for="costid">費用代碼</label>
						        <input id="data"   class="inputdata inputGray btn1-sm btn1-brown" style="text-align:center;background-color:#e0e0e0;" autocomplete="off">
						        <!-- <select id="data" class="selected " autocomplete="off" style="visibility: hidden"></select> -->
						<input id="outputExcel" class="btn btn-primary" type="button"
							 value="查詢" / >
						<input type="button"
						class="btn btn-primary" value="顯示全部" " id="showAll"/>
					    <input type="button" class="btn btn-primary" id="dateOut"
						value="導出" />
			</div>		
			<div
			style="margin-top: 10px; margin-left: 0px; border: 1px solid #C0C0C0; overflow: auto;height: 680px;position: relative;">
			<table id="SwipeCardRecords"
				class="table table-hover "  >
				<thead>
					<tr>
						<th>員工號</th>
						<th>姓名</th>
						<th>部門代碼</th>
						<th>費用代碼</th>
						<th>連續上班開始日期</th>
						<th>連續上班結束日期</th>
						<th>連續上班天數</th>
					</tr>
				</thead>
				<tbody class='spTable'></tbody>
			</table>
		</div>	
		<div id="swipeCardInfoPagination" style="height: 20;position:absolute; bottom: -20px;left: 0px;"></div>
		
 	  </div>
 	  
 	 

</body>

</html>