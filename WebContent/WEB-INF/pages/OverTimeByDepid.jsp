<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<c:url value="/resources/js/Project/OverTimeByDepid.show.js?version=${resourceVersion}" var="ShowOverTimeJS" />
<c:url value="/resources/assets/My97DatePicker/WdatePicker.js" var="DatePcikerJS"/>
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script type="text/javascript" src='${AjaxCheckSessionJS}'></script>
<script src="${ShowOverTimeJS}" type="text/javascript"></script>
<script src="${DatePcikerJS}" type="text/javascript"></script>

<title>加班單審核-富強線長</title>
</head>
<body>
	<div id="header" class="header-fixed">
		<div class="navbar">
			<a class="navbar-brand" href="Login"> 
				<iclass="im-windows8 text-logo-element animated bounceIn"></i> 
				<span class="text-logo">FOX</span><span class="text-slogan">LINK</span>
			</a>
			<span style="color:red;font-size:20px;">根據人資規定，所有刷卡記錄只允許查詢近三個月的記錄</span>
		</div>
	</div>
	<div class="container-fluid">
		<div style="position: absolute; top: 55px; margin-left: 10px">
			<div class="panel-body" style="border: 1px solid #e1e3e6;padding:10px 30px 10px 30px;">
				<div class="row">
					<label>開始日期:</label>
					<input id="dpick1" class="Wdate" type="text" onClick="WdatePicker({maxDate:'%y-%M-{%d-1}',minDate:'%y-\#{%M-2}-01'})" autocomplete="off">
					<label>結束日期:</label>
					<input id="dpick2" class="Wdate" type="text" onClick="WdatePicker({minDate:'#F{$dp.$D(\'dpick1\')}',maxDate:'%y-%M-{%d-1}'})" autocomplete="off">	
					<label>車間:</label>
					<select id="WorkshopNo">
						<option value="%" selected>All</option>
					</select>
					<label>線名:</label>
					<span id="lineSpan">
                    <select  id="LineNo" name="LineNo">
                    	<option value="%" selected>All</option>
                   </select>
                   </span>
					<label>加班單狀態:</label>
					<select id="checkState">
						<option value="0:9" selected>未審核</option>
						<option value="1">已審核</option>
					</select>
					<input id="showRCInforByDate" class="btn btn-primary" type="button"   value="查詢" />
					<input id="showSwipeCardAbnormal" class="btn btn-primary" type="button"  value="忘卡查詢" />			
				</div>
				<div id="RC_DIV" class="row" style="visibility:hidden">
					<div class="col-md-12 col-sm-12">
						<h3>有指示單號列表</h3>
						<table id="OTSheetWithRCNo" class="table table-striped">
							<!-- <thead>
								<tr>
									<th>車間</th>
									<th>日期</th>
									<th>班別</th>
									<th>指示單號</th>
									<th>料號</th>
									<th>標準人數</th>
									<th>實際加班人數</th>
									<th>詳情</th>
								</tr>
							</thead>
							<tbody></tbody> -->
						</table>
					</div>
				</div>
				
				<div id="NO_RC_DIV" class="row" style="visibility:hidden">
					<div class="col-md-12 col-sm-12">
						<h3>無指示單號列表</h3>
						<table id="OTSheetWithoutRCNo" class="table table-striped">
							<!-- <thead>
								<tr>
									<th>車間</th>
									<th>日期</th>
									<th>班別</th>
									<th>實際加班人數</th>
									<th>詳情</th>
								</tr>
							</thead>
							<tbody></tbody> -->
						</table>
					</div>
				</div>
			</div>	
		</div>
	</div>
</body>
</html>