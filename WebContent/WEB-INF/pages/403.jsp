<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Access is denied</title>
<c:url value="/resources/images/403.jpg?version=${resourceVersion}" var="deniedImg"/>
</head>
<body>
	<%-- <h1>HTTP Status 403 - Access is denied</h1>
	<c:choose>
		<c:when test="${empty username}">
		  <h2>You do not have permission to access this page!</h2>
		  <h2>請進行登錄!</h2>
		</c:when>
		<c:otherwise>
		  <h2>親愛的: ${username} 用戶，您沒有權限瀏覽此頁面!</h2>
		</c:otherwise>
	</c:choose> --%>
	<div>
	<img src="${deniedImg}" width="100%" height="100%"/> 
	</div>
</body>
</html>