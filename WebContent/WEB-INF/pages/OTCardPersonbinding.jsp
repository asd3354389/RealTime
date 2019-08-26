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

<c:url value="/resources/assets/js/jquery-1.8.3.min.js" var="assetsJqueryJS" />
<c:url value="/resources/js/Project/RealTime.Modify.OTCardbdPerson.js?version=${resourceVersion}" var="modifyOTCardbdPerson" /> 
<c:url value="/resources/js/jquery/jquery-1.11.3.min.js" var="JqueryJS" />
<c:url value="/resources/js/bootstrap/bootstrap.min.js" var="bootstrapJS" />
<c:url value="/resources/js/bootstrap/bootstrap-select.min.js" var="bootstrapSelectJS" />
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script src="${bootstrapSelectJS}" type="text/javascript"></script>
<script type="text/javascript" src='${AjaxCheckSessionJS}'></script>
<script src="${modifyOTCardbdPerson}" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>費用代碼綁定離崗卡維護</title>
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
					<!-- <option value="Depid">部門代碼</option> -->
					<option value="Costid">費用代碼</option>
					<option value="D_CardID">離崗卡號</option>
				</select> <input type="text" id="queryParam" name="queryParam"
					class="input-sm"> <input type="button"
					id="searchBdPersonListBtn" name="searchBdPersonListBtn"
					class="btn btn-sm btn-primary" value="Search">
					 <a id="addNewBdOTCardBtn" role="button" href="#insertBdOTCard"
					class="btn btn-primary btn-sm" data-toggle="modal">創建綫組別綁定離崗卡</a>
			</div>
			<div>
					<h4>離崗卡與費用代碼綁定列表：</h4>
			</div>
			<div class="middle">
				<div class="left" style="width:70%;height:730px;float:left;border:1px solid #f3f5f6;padding:10px 10px;position: relative;" >
					<table id="Personbinding" class="table table-hover" style="border:2px solid #f3f5f6;table-layout:fixed;">
						<thead>
							<tr>
								<th>費用代碼</th>
								<th>離崗卡號</th>
								<th>默認使用車間</th>
								<th>是否生效</th>
								<th></th>
							</tr>
						</thead>
						<tbody class='spTable'>
						</tbody>
					</table>
					<div id="PersonListPagination" align="right" style="height: 20;position:absolute; bottom: -5px;right: 0px;">
					</div>
				</div>
				<div style="float:right;width:25%;height:730px;border:1px solid #f3f5f6;">
						<div style="padding:10px 10px;overflow-y:auto;height:650px;border-bottom: 1px solid #f3f5f6">
						<table id="deleteId" class="table table-hover" style="border:2px solid #f3f5f6;table-layout:fixed;border-collapse:separate">
							<thead>
								<tr>
									<th colspan='3'>離崗卡信息列表</th>
								</tr>
							</thead>
							<tbody class='dlTable'>
							</tbody>
						</table>
						</div>
						<button class="deleteIp btn btn-sm btn-primary">刪除</button>
						<button class="reset btn btn-sm btn-primary">清空</button>
					</div>
			<jsp:include page="InsertBdOTCard.jsp" />
		</div>
	</div>
</div>	
</body>
</html>