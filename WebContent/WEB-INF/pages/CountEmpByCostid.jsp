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
<c:url value="/resources/js/Project/RealTime.Modify.ContrastByCostid.js?version=${resourceVersion}" var="modifyContrastByCostidJS" />
<c:url value="/resources/assets/My97DatePicker/WdatePicker.js" var="wdatePickerJS" />
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script src="${wdatePickerJS}" language="javascript" type="text/javascript"></script>
<script type="text/javascript" src='${AjaxCheckSessionJS}'></script>
<script src="${modifyContrastByCostidJS}" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人力縂表-依課別</title>
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
			<div class="top" style="padding:10px;border: 1px solid #e1e3e6;">
				<div style="margin:10px 0">
					<label class="control-label" for="timeFirst">選擇日期</label>
		    		<div class="controls">
		      			 <input id="timeFirst" class="Wdate" type="text" name="OVERTIMEDATE"onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" autocomplete="off" />  	
		  			</div> 
	  			</div>
	  			<div style="margin:10px 0">
		  			<label class="control-label" for="chekcType">選擇類別</label>
		    		<select id="chekcType" class="input-small">
						<option value="All">人力總表</option>
						<option value="costid">人力總表-依課別</option>
					</select>
					<select id="chekcClass" style="display:none">
						<option value="9628">一課</option>
						<option value="9629">二課</option>
						<option value="6251">三課</option>
						<option value="6252">四課</option>
						<option value="9097">五課</option>
					</select> 
				</div>	
				
				<input type="button" id="searchContrast" name="searchCountEmp"class="btn btn-sm btn-primary" value="查詢">
			</div>						
		</div>
	</div>
		<div class="CountList" style="margin:20px 0px;display: none">
				<div>
					<h4>各線人力明細：</h4>
				</div>
				<div class="panel-body CountEmpList" style="border: 1px solid #e1e3e6;margin:0px 10px 5px;">
				
				</div>
		</div>
		
		<div class="ABCountList" style="margin:20px 0px;display: none">
				<div>
					<h4>機種異動：</h4>
				</div>
				<div class="panel-body ABCountEmpList" style="border: 1px solid #e1e3e6;margin:0px 10px 5px;">
				
				</div>
		</div>
</div>	
</body>
</html>