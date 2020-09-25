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
<c:url value="/resources/js/Project/RealTime.Modify.IOWSRecord.js?version=${resourceVersion}" var="modifyIOWSRecordJS" />
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
<script src="${modifyIOWSRecordJS}" type="text/javascript"></script>
<script src="${tableToExcel}" type="text/javascript"></script>
<script src="${testTableExcel}" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>进出车间门禁記錄查詢</title>
</head>
<body>
	<div id="header" class="header-fixed">
		<div class="navbar">
			<a class="navbar-brand" href="Login"> <i
				class="im-windows8 text-logo-element animated bounceIn"></i> <span
				class="text-logo">FOX</span><span class="text-slogan">LINK</span>
			</a>
			<span style="color:red;font-size:20px;">根據人資規定，所有刷卡記錄只允許查詢近三個月的記錄</span>
		</div>
		<!-- Start .header-inner -->
	</div>
	<div class="container-fluid"  style="margin: 50px 20% 0 0;">
		<div style="top: 55px; margin-left: 10px">
			<div class="panel-body" style="border: 1px solid #e1e3e6;">
				<div class="col-sm-12 col-sm-12" style="margin: 5px;">
				    <form action="#" id="uploadEmpIdForm" method="POST" enctype="multipart/form-data">
						<label for="searchEmpId">員工工號:</label>
						<input type="text" id="searchEmpId" name="searchEmpId" class="input-sm" autocomplete="off"> 
						<input type="checkbox" id="empIdcheck" name="empIdcheck" value="0">
						<span class="btn btn-primary fileinput-button"
							id="importEmpIdSearchBtn" name="importEmpIdSearchBtn" disabled="disabled">
							 <span>導入查詢工號</span> 
							 <input type="file" id="uploadEmpIdFile" accept="text/plain">
						</span>
						&nbsp;此次共導入:<span id="importEmpIdSum">0</span> 個工號
					</form>
				</div>
				
				<div class="col-sm-12 col-sm-12" style="margin: 5px;">
				    <form action="#" id="uploadWorkShopNoForm" method="POST" enctype="multipart/form-data">
						<label for="searchWorkShopNo">車間號:</label>
						<!-- <input type="text" id="searchWorkShopNo" name="searchWorkShopNo" class="input-sm" autocomplete="off">
						<select id="searchWorkShopNo" name='searchWorkShopNo' class="input-sm">
    						<option value=""></option>
    					</select>  	
    					
    					<select id="searchWorkShopNo" name="searchWorkShopNo" class="selectpicker show-tick" multiple data-live-search="true">
                  		</select>	
						<input type="checkbox" id="WorkShopNocheck" name="WorkShopNocheck" value="0">
						<span class="btn btn-primary fileinput-button"
							id="importWorkShopNoSearchBtn" name="importWorkShopNoSearchBtn" disabled="disabled">
							 <span>導入查詢車間號</span> 
							 <input type="file" id="uploadWorkShopNoFile" accept="text/plain">
						</span>
						&nbsp;此次共選擇:<span id="importWorkShopNoSum">0</span> 個車間號 -->
						 <select id="workShop" name="workShopNo" class="selectpicker show-tick" multiple data-live-search="true"></select> 
					</form>
				</div>
				
				<div class="col-sm-12 col-sm-12" style="margin: 5px;">
					<form action="#" id="uploadDepIdForm" method="POST" enctype="multipart/form-data">
						<label for="searchDepId">部門代碼:</label>
						<input type="text" id="searchDepId" name="searchDepId" class="input-sm" autocomplete="off"> 
						<input type="checkbox" id="depIdcheck" name="depIdcheck" value="0">
						<span class="btn btn-primary fileinput-button"
							id="importDepIdSearchBtn" name="importDepIdSearchBtn" disabled="disabled">
							 <span>導入查詢部門代碼</span> 
							 <input type="file" id="uploadDepIdFile" accept="text/plain">
						</span>
						&nbsp;此次共導入:<span id="importDepIdSum">0</span> 個部門代碼
					</form>
				</div>
				<div class="col-sm-12 col-sm-12" style="margin: 5px;">
				<form action="#" id="uploadCostIdForm" method="POST" enctype="multipart/form-data">
						<label for="searchCostId">費用代碼:</label>
						<input type="text" id="searchCostId" name="searchCostId" class="input-sm" autocomplete="off"> 
						<input type="checkbox" id="costIdcheck" name="costIdcheck" value="0">
						<span class="btn btn-primary fileinput-button"
							id="importCostIdSearchBtn" name="importCostIdSearchBtn" disabled="disabled">
							 <span>導入查詢費用代碼</span> 
							 <input type="file" id="uploadCostIdFile" accept="text/plain">
						</span>
						&nbsp;此次共導入:<span id="importCostIdSum">0</span> 個費用代碼
					</form>					
				</div>
				<div class="col-sm-12 col-sm-12" style="margin: 5px;">
					<label for="startDate">開始日期:</label> 
					<input id="startDate" class="Wdate" type="text" onClick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-\#{%M-2}-01',maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" />
					<label for="startDate">結束日期:</label> 
					<input id="endDate" class="Wdate" type="text" onClick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off" />
				</div>
				<div class="col-sm-12 col-sm-12" style="margin: 5px;">
					<input type="checkbox" name="recordStatus" value="21" class="normal"
						checked="checked">隨綫人員上班時間外出正常刷卡
					<!-- 	<input type="checkbox" name="recordStatus" value="1">無人員資料 -->
					<input type="checkbox" name="recordStatus" value="01" class="normal">正常普通刷卡
					<input type="checkbox" name="recordStatus" value="10" class="normal">非本事業処人員異常刷卡
					<input type="checkbox" name="recordStatus" value="11" class="normal">非本事業処人員正常刷卡
					<input type="checkbox" name="recordStatus" value="20" class="normal">隨綫人員上班時間外出異常刷卡
					<input type="checkbox" name="recordStatus" value="31" class="normal">保密車間正常刷卡
					<input type="checkbox" name="recordStatus" value="32" class="normal">保密車間異常刷卡
					<input type="checkbox" name="recordStatus" value="41">供應商及東坑廠區以外的員工刷卡
				</div>
				<div class="col-sm-12 col-sm-12" style="margin: 5px;">				   
					<input type="button" id="searchRawRecordBtn" name="searchRawRecordBtn" class="btn btn-sm btn-primary" value="查詢">
					<input type="button" id="showAllRawRecordBtn" name="showAllRawRecordBtn" class="btn btn-sm btn-primary" value="顯示全部">
					<a href="#" class="easyui-linkbutton" id="exportExcel" data-options="iconCls:'icon-search'"></a>
					<input type="button" onclick="javascript:Save1('門禁刷卡記錄')" name="exportRawRecordBtn" class="btn btn-sm btn-primary" value="導出">
					<input type="button" id="resetBtn" name="resetBtn" class="btn btn-sm btn-primary" value="清空">
				<div>
					<div>
						<h4>原始刷卡記錄查詢列表：</h4> <label id="searchRawRecordCounts">記錄數: 0  條</label>
					</div>
					<div class="panel-body" style="border: 1px solid #e1e3e6; width: 100%">
						<table id="rawRecordTable" class="table table-striped">
							<thead>
								<tr>
									<th>工號</th>
									<th>姓名</th>
									<th>離崗卡號</th>
									<th>刷卡車間</th>
									<th>費用代碼</th>
									<th>部門代碼</th>	
									<th>線別代碼</th>			
									<th>刷卡記錄</th>
									<th>卡機狀態</th>
								</tr>
							</thead>
							<tbody></tbody>
							<tfoot>
								<tr>
									<th>工號</th>
									<th>姓名</th>
									<th>離崗卡號</th>
									<th>刷卡車間</th>
									<th>費用代碼</th>	
									<th>部門代碼</th>	
									<th>線別代碼</th>			
									<th>刷卡記錄</th>
									<th>卡機狀態</th>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
				<div id="rawRecordPagination" align="right" style="height: 20">
				</div>
			</div>
		</div>
	</div>
</body>
</html>