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
<c:url value="/resources/js/Project/RealTime.Modify.AbReasonReply.js?version=${resourceVersion}" var="modifyAbReasonReplyJS" />
<c:url value="/resources/assets/My97DatePicker/WdatePicker.js" var="wdatePickerJS" />
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script src="${wdatePickerJS}" language="javascript" type="text/javascript"></script>
<script type="text/javascript" src='${AjaxCheckSessionJS}'></script>
<script src="${modifyAbReasonReplyJS}" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>異常原因回復</title>
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
					<input id="startDate" class="Wdate" type="text" onClick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'%y-\#{%M-2}-01',maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" />
					<label for="startDate">結束日期:</label> 
					<input id="endDate" class="Wdate" type="text" onClick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off" /> 	
	  			</div> 
	  			<div class="Admin_Depid" style="display:none">
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
				</div>
				<div class="Assistant_Depid" style="display:inline-block">
		  			<label class="control-label" for="Assistant_depid">線別</label>
		    		<select id="Assistant_depid" class="input-small">
						
					</select> 
	  			</div>
				<input type="button" id="searchABReason" name="searchABReason"class="btn btn-sm btn-primary" value="查詢">
			</div>	
		</div>
	</div>
	
	<div class="ShowABReason" style="display:none;margin:20px 0px;">
				<div>
					<h4>異常回覆畫面：</h4>
					<label>異常原因：</label>
					<select id="exception_reason">
						<option value="請假">請假</option>
						<option value="曠工">曠工</option>
						<option value="異常">異常</option>
						<option value="新人">新人</option>
						<option value="班別錯誤">班別錯誤</option>
						<option value="連班換崗">連班換崗</option>
						<option value="支援別課">支援別課</option>
						<option value="補辦廠證">補辦廠證</option>
						<option value="卡機異常">卡機異常</option>
						<option value="員工漏刷卡">員工漏刷卡</option>
						<option value="跨廠區作業">跨廠區作業</option>
						<option value="換班配合生產">換班配合生產</option>
					</select>				
					<input class="btn btn-primary" type="button" id="Reply" value="回覆" />
				</div>
				<div class="panel-body" style="border: 1px solid #e1e3e6;margin:0px 10px 5px;">
				  <table id="ABReasonList" class="table table-striped">
						<thead>
							<tr>
								<th class="per5"><input name="checkbox" type="checkbox"
									id="inlineCheckbox1" value="option1" >
								</th>
								<th>課別</th>
								<th>線別</th>
								<th>工號</th>
								<th>姓名</th>	
								<th>異常日期</th>	
								<th>班別</th>	
								<th>異常時間起迄</th>	
								<th>異常時間(分)</th>	
								<th>異常原因</th>						
							</tr>
						</thead>
						<tbody></tbody>
					</table> 
				</div>
			</div>
</div>	
</body>
</html>