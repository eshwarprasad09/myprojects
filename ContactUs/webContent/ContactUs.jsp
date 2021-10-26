`<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor=#dfe6e0>
<div style = background-color:#c6ccc7;height:70px;margin-left:25%;margin-right:25%;
border:thin;padding:20px>Contact Us<br>
<p style = font-size:10px>please fill form in decent manner<br></div>
<form style=margin-left:25%;margin-right:25%;background-color:white;border:thin;
padding:20px action = "contactus" method = "post">
Full Name<sup style=color:red>*</sup> <br>
<input type="text" name="fullname" required> <br>
E-mail<sup style=color:red>*</sup>  <br>
<input type="email" name="email" required>
<p style=font-size:10px>example@gmail.com</p>
message<sup style=color:red>*</sup> <br>
<input type="text" name="message" style=height:150px;width:350px required> <br>
<p style = margin-left:120px><input type="submit" value="submit"></p>
</form>
</body>
</html>