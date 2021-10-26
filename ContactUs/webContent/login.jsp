<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor=#dfe6e0>
	<div style="background-color: #c6ccc7; height: 70px; margin-left: 25%;"
		margin-right:25%;border:thin;padding:20px">
		Admin Login<br>
		<p style="font-size: 10px">
			Admin Login<br>
	</div>
	<form
		style="margin-left: 25%; margin-right: 25%; background-color: white;"
		border:thin;padding:20px" action="login" method="post">
		user Id <br> <input type="text" name="userId"><br>
		password<br> <input type="password" name="password"><br>
		<input type="submit" value="log in">
	</form>
</body>
</html>