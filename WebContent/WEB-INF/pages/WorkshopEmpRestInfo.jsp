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

<c:url value="/resources/assets/My97DatePicker/WdatePicker.js" var="wdatePickerJS" />
<c:url value="/resources/assets/js/jquery-1.8.3.min.js" var="assetsJqueryJS" />
<c:url value="/resources/js/Project/RealTime.Modify.WorkshopEmpRestInfo.js?version=${resourceVersion}" var="WorkshopEmpRestInfo" /> 
<c:url value="/resources/js/jquery/jquery-1.11.3.min.js" var="JqueryJS" />
<c:url value="/resources/js/bootstrap/bootstrap.min.js" var="bootstrapJS" />
<c:url value="/resources/js/bootstrap/bootstrap-select.min.js" var="bootstrapSelectJS" />
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script src="${wdatePickerJS}" language="javascript" type="text/javascript"></script>
<script src="${bootstrapSelectJS}" type="text/javascript"></script>
<script type="text/javascript" src='${AjaxCheckSessionJS}'></script>
<script src="${WorkshopEmpRestInfo}" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>維護車間休息時間段</title>
</head>
<body style="position:relative;">
		<div id="header" class="header-fixed">
		<div class="navbar">
			<a class="navbar-brand" href="Login"> <i
				class="im-windows8 text-logo-element animated bounceIn"></i> <span
				class="text-logo">FOX</span><span class="text-slogan">LINK</span>
			</a>
		</div>
		<!-- Start .header-inner -->
	</div>
<div class="container-fluid"  style="margin: 50px 10% 0 0;">
	<div style="top: 55px; margin-left: 10px">
		<div class="panel-body" style="border: 1px solid #e1e3e6;">
			<div align="right">
				查詢條件：<select id="queryCritirea" class="input-small">
					<option value="workshopno">工號</option>
				</select> <input type="text" id="queryParam" name="queryParam"
					class="input-sm"> <input type="button"
					id="searchWorkshopNoRestInfo" name="searchWorkshopNoRestInfo"
					class="btn btn-sm btn-primary" value="Search">
			</div>
			<div>
					<h4 style="position: relative;">員工休息時間段列表信息：</h4>
					<a id="addNewIOCardMaIP" role="button" href="#insertIOCardMaIP"class="btn btn-sm" data-toggle="modal" style="position: absolute;top: 50px;right: 500px;font-size: 14px;"><i class="glyphicon glyphicon-plus"></i>創建新員工休息時間段</a>
			</div>
			<div class="middle">
				<div class="left" style="width:80%;height:730px;float:left;border:1px solid #f3f5f6;padding:10px 10px;position: relative;" >
					<table id="WorkshopNoRestInfoTable" class="table table-hover" style="border:2px solid #f3f5f6;table-layout:fixed;">
						<thead>
							<tr>
								<th>工號</th>
								<th>姓名</th>
								<th>班別</th>
								<th>第一次休息開始時間</th>
								<th>第一次休息結束時間</th>
								<th>第二次休息開始時間</th>
								<th>第二次休息結束時間</th>
								<!-- <th>上半夜中休開始時間</th>
								<th>上半夜中休結束時間</th>
								<th>下半夜中休開始時間</th>
								<th>下半夜中休結束時間</th> -->
								<th></th>
							</tr>
						</thead>
						<tbody class='spTable'>
						</tbody>
					</table>
					<div id="WorkshopNoRestInfoListPagination" align="right" style="height: 20;position:absolute; bottom: -20px;right: 0px;">
					</div>
				</div>
			</div>
			<jsp:include page="InsertNewWorkshopEmpRestInfo.jsp" />
		</div>
	</div>
</div>	
</body>
</html>