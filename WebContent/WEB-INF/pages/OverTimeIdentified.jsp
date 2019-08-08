<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- CSS -->
<c:url value="/resources/assets/css/icons.css" var="iconsCSS" />
<c:url value="/resources/assets/css/bootstrap.css" var="bootstrapCSS" />
<c:url value="/resources/assets/css/plugins.css" var="pluginsCSS" />
<c:url value="/resources/assets/css/main.css" var="mainCSS" />

<link href="${iconsCSS}" rel="stylesheet">
<link href="${bootstrapCSS}" rel="stylesheet">
<link href="${pluginsCSS}" rel="stylesheet">
<link href="${mainCSS}" rel="stylesheet">

<!-- javaScript -->
<c:url value="/resources/js/jquery/jquery-1.11.3.min.js" var="JqueryJS" />
<c:url value="/resources/js/bootstrap/bootstrap.min.js" var="bootstrapJS" />
<c:url value="/resources/js/Project/OverTime.Identified.EmpList.js?version=${resourceVersion}" var="IdentifiedEmpList"/>
<c:url value="/resources/js/Project/Common.Utils.js?version=${resourceVersion}" var="CommonUtils"/>
<script src="${JqueryJS}" type="text/javascript"></script>
<script src="${bootstrapJS}" type="text/javascript"></script>
<script src="${IdentifiedEmpList}" type="text/javascript"></script>
<script src="${CommonUtils }" type="text/javascript"></script>

<title>加班單已審核人員</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-12 col-md-12">
				<table id="OTIdentifiedEmpTable" class="table table-striped">
					<thead>
						<tr>
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
							<th>頂崗時數</th>
							<th>審核狀態</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>