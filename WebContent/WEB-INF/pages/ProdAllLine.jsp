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
<c:url value="/resources/js/Project/RealTime.Modify.ProdAllLine.js?version=${resourceVersion}" var="modifyProdAllLineJS" />
<c:url value="/resources/assets/My97DatePicker/WdatePicker.js" var="wdatePickerJS" />
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script src="${wdatePickerJS}" language="javascript" type="text/javascript"></script>
<script type="text/javascript" src='${AjaxCheckSessionJS}'></script>
<script src="${modifyProdAllLineJS}" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>各課排配人力</title>
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
					<label class="control-label" for="timeFirst">選擇日期</label>
		    		<div class="controls" style="display: inline-block;">
		      			 <input id="timeFirst" class="Wdate" type="text" name="OVERTIMEDATE"onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" autocomplete="off" />  	
		  			</div> 
		  			
		  			<label class="control-label" for="checkClass">選擇課別</label>
		    		<select id="checkClass" class="input-small">
					<option value="9628">一課</option>
						<option value="9629">二課</option>
						<option value="6251">三課</option>
						<option value="6252">四課</option>
						<option value="9097">五課</option>
					</select> 
					<input type="button" id="searchCountEmp" name="searchCountEmp"class="btn btn-sm btn-primary" value="查詢">
				</div>
	    		
			
				
				
					
			</div>
				
		</div>
	</div>
	
	<div id="ManPowerList" style="margin:20px 10px;padding:10px"></div>
	
	<div id="SetSupportMachine" style="display:none;margin:0px 10px 5px;padding:10px">	
			<table border='1'>
			<caption>機種異動設置</caption>
				<thead>
					<tr>
					<th colspan="6">異動</th>
					<th colspan="5">人力動向</th>
					</tr>
					<tr>
						<th>課別</th>
						<th>班別</th>
						<th>異動原因</th>
						<th>機種名稱</th>
						<th>機種料號</th>
						<th>人數</th>
						<th>類型</th>
						<th>機種名稱/課別</th>
						<th>機種料號</th>
						<th>人數</th>
						<th>新增</th>
					</tr>
				</thead>
				<tbody>
				<tr>
					<th>
						<select id="costid">
							<option value="9628">一課</option>
							<option value="9629">二課</option>
							<option value="6251">三課</option>
							<option value="6252">四課</option>
							<option value="9097">五課</option>
						</select>
					</th>
					<th>
						<select id ="shift">
							<option value="D">白班</option>
							<option value="N">夜班</option>
						</select>
					</th>
					<th>
						<select id="transition_reason">
							<option value=""></option>
							<option value="待料">待料</option>
							<option value="拆線">拆線</option>
							<option value="機種異常">機種異常</option>
							<option value="轉其他機種">轉其他機種</option>
							<option value="生產轉重工">生產轉重工</option>
							<option value="停線">停線</option>
							<option value="多餘人力">多餘人力</option>
						</select>
					</th>
					<th><input id = "prod_out_name" type="text" maxlength="20" size="20"/></th>
					<th><input id = "prod_out_code" type="text" maxlength="20" size="20"/></th>
					<th><input id = "transition_out_number" type="text" maxlength="3" size="3"/></th>
					
					<th>
						<select id="type_in">
							<option value="支援">支援</option>
							<option value="重工">重工</option>
							<option value="生產">生產</option>
							<option value="增加人力">增加人力</option>
						</select>
					</th>
					<th>
						<input id = "prod_in_name" type="text" maxlength="20" size="20"/>
					</th>
					<th><input id = "prod_in_code" type="text" maxlength="20" size="20"/></th>
					<th><input id = "transition_in_number" type="text" maxlength="3" size="3"/></th>
					<th>
						<input id = "Add_New_Support" type="button" value="新增人數" >
					</th>
				</tr>
				</tbody>
			</table>
		
	</div>
	<div id="ShowSupportMachine" style="width:60%"></div>
</body>
</html>