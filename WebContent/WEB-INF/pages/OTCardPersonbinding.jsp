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
<title>隨綫人員維護</title>
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
					<option value="Id">工號</option>
					<option value="Name">姓名</option>
					<option value="Depid">部門代碼</option>
					<option value="Costid">費用代碼</option>
				</select> <input type="text" id="queryParam" name="queryParam"
					class="input-sm"> <input type="button"
					id="searchBdPersonListBtn" name="searchBdPersonListBtn"
					class="btn btn-sm btn-primary" value="Search">
					 <a id="addNewAccountBtn" role="button" href="#insertBdOTCard"
					class="btn btn-primary btn-sm" data-toggle="modal">創建員工綁定離崗卡</a>
			</div>
			<div>
					<h4>離崗卡與員工工號綁定列表：</h4>
			</div>
			<div class="middle">
				<div class="left" style="width:60%;height:730px;float:left;border:1px solid #f3f5f6;padding:10px 10px;position: relative;" >
					<table id="Personbinding" class="table table-hover" style="border:2px solid #f3f5f6;table-layout:fixed;">
						<thead>
							<tr>
								<th><input type="checkbox" id="AllCheck">選擇20條</th>
								<th>工號</th>
								<th>姓名</th>
								<th>離崗卡號</th>
								<th>默認使用車間</th>
								<th>是否生效</th>
							</tr>
						</thead>
						<tbody class='spTable'>
						</tbody>
					</table>
					<div id="PersonListPagination" align="right" style="height: 20;position:absolute; bottom: 20px;right: 0px;">
					</div>
				</div>
				<h4 style="display: inline-block;margin-left: 70px;">解除或更改離崗卡綁定員工信息</h4>
				<div class="right" style="width:35%;height:730px;float:right;padding:10px 10px;position: relative;border:2px solid #f3f5f6">
					<div>
						<label for="ChangeOTCard">離崗卡號</label>
    					<input type="text" class="form-control" id="ChangeOTCard" placeholder="離崗卡號">
    					<label for="ChangeWorkShop">默認使用車間</label>
    					<select id="ChangeWorkShop" class="form-control"></select>
					</div>
					<div style="margin-top:10px">
						<div><input type="button" id="changeOTWorkshop" class="btn btn-sm btn-primary" value="更改綁定離崗卡號和使用車間"></div>
						<div style="margin-top:10px"><input type="button" id="relieveBdOTCard" class="btn btn-sm btn-primary" value="解除離崗卡與員工信息綁定"></div>
					</div>
				</div> 
			</div>
			<jsp:include page="InsertBdOTCard.jsp" />
		</div>
	</div>
</div>	
</body>
</html>