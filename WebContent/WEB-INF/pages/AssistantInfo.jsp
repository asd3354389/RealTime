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
<c:url value="/resources/js/Project/RealTime.Modify.Assistant.js?version=${resourceVersion}" var="modifyAssistantJS" />
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script type="text/javascript" src='${AjaxCheckSessionJS}'></script>
<script src="${modifyAssistantJS}" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>助理信息管理</title>
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
			<div align="right">
		查詢條件：<select id="queryCritirea" class="input-small">
					<option value="assistantId">工號</option>
					<option value="assistantName">姓名</option>
				</select> 
				<input type="text" id="queryParam" name="queryParam" class="input-sm"> 
				<input type="button" id="searchAssistantBtn" name="searchAssistantBtn" class="btn btn-sm btn-primary" value="Search">
				<a id="addNewAssistantBtn" role="button" href="#insertAssistantDialog"
					class="btn btn-primary btn-sm" data-toggle="modal">新增助理</a>
			</div>
			<div>
				<div>
					<h4>助理列表：</h4>
				</div>
				<div class="panel-body" style="border: 1px solid #e1e3e6;">
					<table id="assistantInfoTable" class="table table-striped">
						<thead>
							<tr>
								<th>工號</th>
								<th>姓名</th>
								<th>費用代碼</th>
								<th>部門代碼</th>
								<th>Notes郵箱</th>
								<th>分機號碼</th>
							</tr>
						</thead>
						<tbody></tbody>
						<tfoot>
							<tr>
								<th>工號</th>
								<th>姓名</th>
								<th>費用代碼</th>
								<th>部門代碼</th>
								<th>Notes郵箱</th>
								<th>分機號碼</th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div id="assistantInfoPagination" align="right" style="height: 20">
			</div>
			<jsp:include page="InsertNewAssistant.jsp" />
		</div>
	</div>
</div>	
</body>
</html>