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
<c:url value="/resources/js/Project/RealTime.Modify.CountEmp.js?version=${resourceVersion}" var="modifyCountEmpJS" />
<c:url value="/resources/assets/My97DatePicker/WdatePicker.js" var="wdatePickerJS" />
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script src="${wdatePickerJS}" language="javascript" type="text/javascript"></script>
<script type="text/javascript" src='${AjaxCheckSessionJS}'></script>
<script src="${modifyCountEmpJS}" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>各綫人力統計</title>
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
				<label class="control-label" for="timeFirst">選擇日期</label>
	    		<div class="controls" style="display: inline-block;">
	      			 <input id="timeFirst" class="Wdate" type="text" name="OVERTIMEDATE"onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" autocomplete="off" />  	
	  			</div> 
	  			
	  			<label class="control-label" for="depid">綫別</label>
	    		<select id="depid" class="input-small">
					<!-- <option value="Id">工號</option>
					<option value="Name">姓名</option>
					<option value="Deptid">部門代碼</option>
					<option value="Costid">費用代碼</option> -->
				</select> 
				<input type="button" id="searchCountEmp" name="searchCountEmp"class="btn btn-sm btn-primary" value="查詢">
			</div>
			
		</div>
	</div>
	
	<div class="showTable" style="display:none">
		<div>
			<h4 style="text-align: center;">各線人力明細：</h4>
		</div>
		<div class="panel-body" style="border: 1px solid #e1e3e6;margin:0px 10px 5px;">
			<table id="EachLineMachine" class="table table-striped">
				<thead>
					<tr>
						<th>線  別</th>
						<th>班別</th>
						<th>組裝人數</th>
						<th>新進人數</th>
						<th>調出人數</th>
						<th>離職人數</th>
						<th>請假人數</th>
						<th>曠工人數</th>
						<th>全技員</th>
						<th>生產人數</th>							
					</tr>
				</thead>
				<tbody>
					
					
				</tbody>

			</table>
		</div>		
	</div>
	<div class="SpecificList">
	</div>
</div>	
<jsp:include page="UpdateStatus.jsp" />
</body>
</html>