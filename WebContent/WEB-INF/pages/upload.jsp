<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="http://10.8.91.142:8080/WeChatWebService/WeChat/SendEnterpriseLinkMessage" method="post" enctype="multipart/form-data">
		ReceivedGroupName:<input type="text" name="ReceivedGroupName"><br/>
		Title:<input type="text" name="Title"><br/>
		messageContent:<input type="text" name="MessageContent"><br/>
		details:<input type="text" name="details"><br/>
		isSingleWeChatMessage:<input type="text" name="isSingleWeChatMessage"><br/>
		<span>summary:</span><input type="file" name="summary"> <br/>
		<input type="submit" value="提交">
	</form>
	
	<form action="http://10.8.91.142:8080/WeChatWebService/WeChat/SendEnterpriseNewsMessage" method="post" enctype="multipart/form-data">
		ReceivedGroupName:<input type="text" name="ReceivedGroupName"><br/>
		Title:<input type="text" name="Title"><br/>
		messageContent:<input type="text" name="MessageContent"><br/>
		details:<input type="text" name="details"><br/>
		isSingleWeChatMessage:<input type="text" name="isSingleWeChatMessage"><br/>
		<span>summary:</span><input type="text" name="summary"> <br/>
		<input type="submit" value="提交">
	</form>
</body>
</html>