<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
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
<c:url value="/resources/js/jquery/jquery-1.11.3.min.js" var="JqueryJS" />
<c:url value="/resources/js/Project/IpBinding.js?version=${resourceVersion}" var="modifyFLinePerson" />
<c:url value="/resources/js/bootstrap/bootstrap.min.js" var="bootstrapJS" />
<c:url value="/resources/js/bootstrap/bootstrap-select.min.js" var="bootstrapSelectJS" />
<%-- <c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/>  --%>
<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script src="${bootstrapSelectJS}" type="text/javascript"></script>
<%-- <script type="text/javascript" src='${AjaxCheckSessionJS}'></script> --%>
 <script src="${modifyFLinePerson}" type="text/javascript"></script> 
<script type="text/javascript" src="../resources/js/Project/AjaxCheckSession.js"></script>

<!-- <script type="text/javascript" src="../resources/js/Project/IpBinding.js"></script> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>電腦Ip綁定</title>
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
				   <!--  <option value="UPDATE_USERID">工號</option>	 -->
				    <option value="DEPTID">費用代碼</option>
				    <option value="DEVICEIP">電腦Ip</option>						
				</select> <input type="text" id="queryParam" name="queryParam"
					class="input-sm"> <input type="button"
					id="searchIpListBtn" name="searchPersonListBtn"
					class="btn btn-sm btn-primary" value="Search">
			</div>
			<div style="float: left;margin-top:2%">
					<h4>電腦Ip綁定線組別代碼列表：</h4>
					
					<!-- <a id="addNewAccountBtn" role="button" href="#insertIpBinding"
					class="btn btn-primary btn-sm" data-toggle="modal" style="position: absolute;top: 50px;right: 450px;font-size: 14px;">電腦Ip綁定部門代碼</a>	 -->
			</div>
		<div style="float:left; margin-left: 25%;margin-top: -1%;">
		輸入電腦IP: <input type="text" id="queryParamIp" name="queryParamIp"
					class="input-sm" >	
		</div>
			<div class="middle">
				<div class="left" style="width:40%;height:730px;float:left;border:1px solid #f3f5f6;padding:10px 10px;position: relative;background-attachment: red;margin-top: 5%;margin-left: -57%" >
					<table id="Personbinding" class="table table-hover" style="border:2px solid #f3f5f6;table-layout:fixed; ">
						<thead>
							<tr>								
								<th>電腦Ip</th>
								<th>線組別代碼</th>
								<!-- <th>工號</th>		 -->						
								<th>是否生效</th>
								<th></th>
							</tr>
						</thead>
						<tbody class='spTable'>
						</tbody>
					</table>
					<div id="IpBindingListPagination" align="right" style="height: 20;position:absolute; bottom: -20px;right: 0px;">
					</div>
				<div style="background: red;position: absolute;top: 50px;right: 450px;">
				</div>
			<%--  <jsp:include page="InsertMoreIP.jsp" /> 
			<jsp:include page="InsertMoreDeptId.jsp"></jsp:include> --%>
			</div>
			</div>
			<div style="float: right;margin-right: 52%;margin-top: 300px;">
					<!--  <a id="addNewAccountBtn" role="button" href="#insertIpBinding"
					class="btn btn-primary btn-sm" data-toggle="modal" style="font-size: 14px;">電腦Ip綁定部門代碼</a><br><br>	 -->
					<!-- <a id="addMoreDeptIdBtn" role="button" href="#InsertMoreDeptId"
					class="btn btn-primary btn-sm" data-toggle="modal" style="font-size: 14px;"><<綁定</a><br><br>	 -->
					<!-- <a id="addMoreIpBtn" role="button" href="#InsertMoreIP"
					class="btn btn-primary btn-sm" data-toggle="modal" style="font-size: 14px;">線組別代碼綁定多個電腦Ip</a>	 -->
					<button type="submit" id="MorechangebdOT" class="btn btn-primary"><<綁定</button>
				 
			</div>
			<div style="float: right;margin-top:-23%;margin-right: 40%">
					<h4>線組別代碼列表：</h4>
					
					<!-- <a id="addNewAccountBtn" role="button" href="#insertIpBinding"
					class="btn btn-primary btn-sm" data-toggle="modal" style="position: absolute;top: 50px;right: 450px;font-size: 14px;">電腦Ip綁定部門代碼</a>	 -->
			</div>
			
			<div class="middle">
				<div class="left" style="width:15%;height:730px;float:left;border:1px solid #f3f5f6;padding:10px 10px;position: relative;background-attachment: red;margin-top: -50.5%;margin-left: 50%;overflow: auto;" >
					<table id="DeptNobinding" class="table table-hover" style="border:2px solid #f3f5f6;table-layout:fixed; ">
						<thead>
							<tr>	
							<!-- <input type="checkbox" style="float: left;margin-left: 0px" > -->
							   	<th>勾選</th>				
								<th>線組別代碼</th>						
							</tr>
						</thead>
						<tbody class='spTable'>
						</tbody>
					</table>
					<div id="IpBindingListPagination" align="right" style="height: 20;position:absolute; bottom: -20px;right: -20px;">
					</div>
				<div style="background: red;position: absolute;top: 50px;right: 450px;">
				</div>
			
			</div>
			
			<div style="float:right;width:20%;height:730px;border:1px solid #f3f5f6;position: absolute; bottom: 2%;right: 15%;">
						<div style="padding:10px 10px;overflow-y:auto;height:650px;border-bottom: 1px solid #f3f5f6">
						<table id="deleteId" class="table table-hover" style="border:2px solid #f3f5f6;table-layout:fixed;border-collapse:separate">
							<thead>
								<tr>
									<th colspan='2'>卡機ip列表</th>
								</tr>
							</thead>
							<tbody class='dlTable'>
							</tbody>
						</table>
						</div>
						<button class="deleteIp btn btn-sm btn-primary">刪除</button>
						<button class="reset btn btn-sm btn-primary">清空</button>
			</div>
			</div>

</div>
</body>
</html>