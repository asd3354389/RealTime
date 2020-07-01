<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<c:url value="/resources/js/bootstrap/bootstrap.min.js" var="bootstrapJS" />
<c:url value="/resources/assets/My97DatePicker/WdatePicker.js" var="wdatePickerJS" />
<c:url value="/resources/js/echarts.min.js" var="echartsJS" />
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<c:url value="/resources/js/Project/ChartSwipeCardAB.js?version=${resourceVersion}" var="ChartSwipeCardABJS" />

<script src="${assetsJqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script src="${wdatePickerJS}" language="javascript" type="text/javascript"></script>
<script src="${echartsJS}" type="text/javascript"></script>
<script type="text/javascript" src='${AjaxCheckSessionJS}'></script>
<script src="/resources/js/echarts.min.js"></script>
<script src="${ChartSwipeCardABJS}" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>異常趨勢圖</title>
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
	<div id="idEcharts"></div>
</div>	


</body>
</html>