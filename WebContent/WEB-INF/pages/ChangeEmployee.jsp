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
<c:url value="/resources/js/Project/ChangeEmployee.js?version=${resourceVersion}" var="modifyChangeEmployeeJS" />
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
<script src="${modifyChangeEmployeeJS}" type="text/javascript"></script>
<script src="${tableToExcel}" type="text/javascript"></script>
<script src="${testTableExcel}" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>刷卡人員異動</title>
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

<div class="panel-body" style="padding-bottom: 0px;">
<div class="row" style="border: 1px solid #e1e3e6;display: inline-block;width: 667px;">
<div class="col-md-12" style="margin: 5px;">
					<label for="startDate">開始日期:</label> 
					<input id="startDate" class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-\#{%M-2}-01'})" autocomplete="off" />
					<label>工號:</label> 
					<input type="text" id="employeeID"" name="queryParamId"
					class="inputGray" style="text-align:center;background-color:#e0e0e0;">
					<input type="button"
					id="searchEmpId" name="searchEmpId"
					class="btn btn-primary" value="查詢">
				</div>
</div>

</div>
 <div class="panel-body"  style="padding-top: 0px;">
 <div class="row"   style="border: 1px solid #e1e3e6;width: 40%;display:none;" id="changeTable">
 <center><p style="font-size: 18px;"><b>刷卡人員異動</b></p></center>
<div class="col-md-6" style="width: 50%">
<table id="ProdPerson" class="table table-bordered" style="border:2px solid #f3f5f6;table-layout:fixed;">
						<thead>
						<tr>
								<th style="text-align:center;">工號</th>
								<th style="text-align:center;">姓名</th>			
								<th style="text-align:center;">狀態</th>
							</tr>
						</thead>
						  <tbody>
     <tr>
      <td style="text-align:center;" id="t1"></td>
      <td style="text-align:center;" id="t2"></td>
      <td style="text-align:center;" id="t3"></td>
    </tr>
  </tbody>
					</table>
					</div>
					<div class="col-md-6" style="position:relative;top:-25px;">
					 <center><label>修改狀態</label></center>
					 <select class="form-control" id="select" style="width:50%;">
             <option value="0">請選擇異常類型</option>
            <option value="1">請假</option>
            <option value="2">曠工</option>
            <option value="3">調出</option>
			<option value="4">新進</option>
			<option value="5">全技員</option>
			<option value="6">需重新調班</option>
			<option value="7">調休</option>
        </select>
        <select class="form-control" id="status" style="width:50%;position:relative;left:53%;top:-34px;">
            <option>請選擇具體事由</option>
        </select>
        
        <input type="button" id="updateStatus" class="btn btn-primary" value="更新狀態" style="width:50%;position:relative;left:82px;top:-20px;">
					</div>
 
 </div>

		
</div> 
     </div>
</body>
</html>