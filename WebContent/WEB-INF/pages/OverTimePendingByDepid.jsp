<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- CSS -->
<c:url value="/resources/assets/css/bootstrap.css" var="bootstrapCSS" />
<c:url value="/resources/assets/css/icons.css" var="iconsCSS" />
<c:url value="/resources/assets/css/plugins.css" var="pluginsCSS" />
<c:url value="/resources/assets/css/main.css" var="mainCSS" />

<link href="${iconsCSS}" rel="stylesheet">
<link href="${bootstrapCSS}" rel="stylesheet">
<link href="${pluginsCSS}" rel="stylesheet">
<link href="${mainCSS}" rel="stylesheet">
<!-- javaScript -->
<c:url value="/resources/js/jquery/jquery-1.11.3.min.js" var="JqueryJS" />
<c:url value="/resources/js/bootstrap/bootstrap.min.js" var="bootstrapJS" />
<c:url value="/resources/js/Project/Common.Utils.js?version=${resourceVersion}" var="Common"/>
<c:url value="/resources/js/Project/OverTime.Pending.EmpListByDepid.js?version=${resourceVersion}" var="PendingEmpList"/>
<c:url value="/resources/js/Project/OverTime.Admit.js?version=${resourceVersion}" var="OTAdmit"/>
<c:url value="/resources/img/ajax-loader.gif" var="ajaxLoaderPic"/>
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script type="text/javascript" src='${AjaxCheckSessionJS}'></script>
<script src="${PendingEmpList}" type="text/javascript"></script>
<script src="${OTAdmit}" type="text/javascript"></script>
<script src="${Common}" type="text/javascript"></script>
<img src="${ajaxLoaderPic}" id="ajaxLoader" style="display:none" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.16.0/moment-with-locales.min.js"></script>


<title>加班單未審核人員</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row" style="margin: 5px;">
			<br>
			<label>時間:</label>					
			<select id="overtimeCal">
				<option value="0">待選</option>
				<option value="1">正常班</option>
				<option value="2">假日班</option>
			</select>
			<label>加班類型：</label>
			<select id="overtimeType">
				<option value="0">待選</option>
        		<option value="1">加班1</option>
        		<option value="2">加班2</option>
        		<option value="3">加班3</option>
			</select>
			<label>工作內容：</label>				
			<input type="text" id="workcontent" class="input-sm" />		
			<label style="color:red">(加班單內容不宜過長，請保持在100字以內)</label>			
		</div>
		
		<div class="row" style="margin: 5px;">		
			<span class="btn btn-primary fileinput-button" id="importEmpIdSearchBtn" name="importEmpIdSearchBtn">
			<span>導入工號</span> 
			<input type="file" id="uploadEmpIdFile" accept="text/plain">
			</span>
			&nbsp;此次共導入:<span id="importEmpIdSum">0</span> 個工號
		</div>
		
		<div class="row">
			<div class="col-sm-12 col-md-12">
				<table id="OTPendingEmpTable" class="table">
					<thead>
						<tr>
							<th><input type="checkbox" id="selectedAllEmps">全選</th>
							<th>序號</th>
							<th>工號</th>
							<th>名字</th>
							<th>部門代碼</th>
							<th>費用代碼</th>
							<th>直間接人員</th>
							<th>加班日期</th>
							<th>加班時段</th>
							<th>加班小時</th>
							<th>加班類型</th>
							<th>頂崗津貼</th>
							<th>審核狀態</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
				<br>
			</div>
		</div>
		<div class="row" style="margin: 5px;">
			<input type="button" class="OTHrsSubmitBtn" class="btn btn-primary btn-sm" value="提交" />
		</div>
	</div>
</body>
</html>