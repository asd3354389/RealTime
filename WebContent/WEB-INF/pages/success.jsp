<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>

	<c:url value="/resources/js/jquery/jquery-3.2.1.min.js" var="jqueryJs"/>
	<script type="text/javascript" src="${jqueryJs }"></script>
<script>
$(function(){
	/* var s=""; 
	var obj=${jsonResults};
	for(var i=0;i<obj.length;i++){
		} */
		
	$("#click").click(function(){
		var userId=$("#userid").val();
		// alert(userId);
		$.ajax({
			type:"post",
			url:"success",
			dataType:"json",
			data:{"userid":userId},
			success:function(msg){
				alert(111);
			}
		})
	})
	
})
</script>
</head>
<body>
	<form id="form1" >
		id:<input type="text" id="userid" name="id" > <input type="submit"
			value="提交" id="click">
	</form>
	<div id="out">
	${jsonResults }
	</div>
</body>
</html>