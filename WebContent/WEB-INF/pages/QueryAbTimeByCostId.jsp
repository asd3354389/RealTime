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

<link href="${iconsCSS}" rel="stylesheet">
<link href="${bootstrapCSS}" rel="stylesheet">
<link href="${pluginsCSS}" rel="stylesheet">
<link href="${mainCSS}" rel="stylesheet">

<c:url value="/resources/assets/js/jquery-1.8.3.min.js" var="assetsJqueryJS" />
<c:url value="/resources/js/jquery/jquery-1.11.3.min.js" var="JqueryJS" />
<c:url value="/resources/js/bootstrap/bootstrap.min.js" var="bootstrapJS" />
<c:url value="/resources/js/Project/RealTime.Modify.QueryABTimeBC.js?version=${resourceVersion}" var="modifyQueryABTimeBCJS" /> 
<c:url value="/resources/assets/My97DatePicker/WdatePicker.js" var="wdatePickerJS" />
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script src="${wdatePickerJS}" language="javascript" type="text/javascript"></script>
<script type="text/javascript" src='${AjaxCheckSessionJS}'></script>
<script src="${modifyQueryABTimeBCJS}" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>依課別-線別查詢</title>
</head>
<body>
	<div id="header" class="header-fixed">
		<div class="navbar">
			<a class="navbar-brand" href="Login"> <i
				class="im-windows8 text-logo-element animated bounceIn"></i> <span
				class="text-logo">FOX</span><span class="text-slogan">LINK</span>
			</a>
		</div>
		<!-- Start .header-inner -->
	</div>
<div class="container-fluid" style="margin: 50px 20% 0 0;">
	<div style="top: 55px; margin-left: 10px">
		<div class="panel-body" style="border: 1px solid #e1e3e6;">
			<div class="top" style="padding-bottom:5px;border: 1px solid #e1e3e6;">
	    		<div class="controls" style="display: inline-block;">
				 	<label for="startDate">開始日期:</label> 
					<input id="startDate" class="Wdate" type="text" onClick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-\#{%M-2}-01',maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" />
					<label for="startDate">結束日期:</label> 
					<input id="endDate" class="Wdate" type="text" onClick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off" /> 	
	  			</div> 
	  			<label class="control-label" for="BU">部別</label>
	    		<select id="BU" class="input-small">
					<option>請選擇部別</option>"
					<option value="AllBU">All</option>"
					<option value="B1001">組裝</option>"
					<option value="B1002">成型</option>"
					<option value="B1003">電鍍</option>"
					<option value="B1004">衝壓</option>"
				</select> 
				<label class="control-label" for="costid">課別</label>
	    		<select id="costid" class="input-small">
					<option>請選擇課別</option>"
				</select> 
	  			<label class="control-label" for="depid">線別</label>
	    		<select id="depid" class="input-small">
					<option>請選擇線別</option>"
				</select> 
				<input type="button" id="searchByCostid" name="searchByCostid"class="btn btn-sm btn-primary" value="查詢">
			</div>
    		
			<div style="border: 1px solid #e1e3e6;margin:20px 0px;">
				<div>
					<h4>車間列表：</h4>
				</div>
				<div class="panel-body" style="border: 1px solid #e1e3e6;margin:0px 10px 5px;">
					<!-- <table id="WorkShopInfoTable" class="table table-striped">
						<thead>
							<tr>
								<th>車間名稱</th>
								<th>線體名稱</th>
								<th>創建人</th>
								<th>創建日期</th>								
							</tr>
						</thead>
						<tbody></tbody>
						<tfoot>
							<tr>
								<th>車間名稱</th>
								<th>線體名稱</th>
								<th>創建人</th>
								<th>創建日期</th>	
							</tr>
						</tfoot>
					</table> -->
				</div>
			</div>
			
		
			
		</div>
	</div>
</div>	
</body>
</html>