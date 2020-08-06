<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <style type="text/css">
  table tr:nth-child(even){
background: #DCDCDC;
 </style>
<c:url value="/resources/assets/css/icons.css" var="iconsCSS" />
<c:url value="/resources/assets/css/bootstrap.css" var="bootstrapCSS" />
<c:url value="/resources/assets/css/plugins.css" var="pluginsCSS" />
<c:url value="/resources/assets/css/main.css" var="mainCSS" />

<link href="${iconsCSS}" rel="stylesheet">
<link href="${bootstrapCSS}" rel="stylesheet">
<link href="${pluginsCSS}" rel="stylesheet">
<link href="${mainCSS}" rel="stylesheet">

<c:url value="/resources/js/jquery/jquery-1.11.3.min.js" var="JqueryJS" />
<c:url value="/resources/assets/My97DatePicker/WdatePicker.js" var="wdatePickerJS" />
<c:url value="/resources/js/bootstrap/bootstrap.min.js" var="bootstrapJS" />
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<c:url value="/resources/js/Project/ChartSwipeCardABByID.js?version=${resourceVersion}" var="ByIDJS" />

<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${wdatePickerJS}" language="javascript" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script src="${ByIDJS}" type="text/javascript"></script>
<script src="${AjaxCheckSessionJS}" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
 <div class="container-fluid" style="margin: 50px 10% 0 0;">
 <table frame=void class="table table-condensed">
	        <colgroup>
				<col style="width:4%">
				<col style="width:15%">
				<col style="width:4%">
				<col style="width:15%">
				<col style="width:15%">
				
			</colgroup>
     <tbody>
	   <tr>
		  <td align="right">開始日期:</td>
		  <td>
		     <input type="text" id="id-StartTimeValue" name="id-StartTimeValue" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" autocomplete="off" class="Wdate"/>
		  </td>
		  <td align="right">結束日期:</td>
		  <td>
		     <input type="text" id="id-EndTimeValue" name="id-EndTimeValue" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" autocomplete="off" class="Wdate"/>
		  </td>
		  
		  <td>
		     <input style="display:none" id="id-query" class="btn btn-primary btn-sm" type="button"   value="查詢" />
		      <input id="id-query2" class="btn btn-primary btn-sm" type="submit"    value="查詢" />
		      <iframe name="form" id="form" style="display:none"></iframe>	
		  </td>
	   </tr>
	 </tbody>
	</table>
		
			<table style="font-size: 18px" border="1" cellspacing="0" cellpadding="3" width="100%">
				<thead>
					<tr style="color: #1a608f;" bgcolor="#75B9E6">
						<th style="text-align:center;">工號</th>
						<th style="text-align:center;">姓名</th>
						<th style="text-align:center;">費用代碼</th>
						<th style="text-align:center;">組別代碼</th>
						<th style="text-align:center;">綫別代碼</th>
						<th style="text-align:center;">部門名稱</th>
						<th style="text-align:center;">上班超時15分鐘刷卡</th>
						<th style="text-align:center;">下班超時15分鐘刷卡</th>
						<th style="text-align:center;">超7休1</th>
						<th style="text-align:center;">下班超15分鐘未刷卡</th>
						<th style="text-align:center;">異常合計</th>
					</tr>
				</thead>
				<tbody id="tbody1"> </tbody>
			</table>

		</div>
	


</body>
</html>