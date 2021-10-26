<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
	if (session.getAttribute("userId") == null) {
		response.sendRedirect("login.jsp");
	}
	%>
	Active State
	<table bgcolor="black" width=100%>
		<tr bgcolor=#ebd69d>
			<th>Name</th>
			<th>Email</th>
			<th>Message</th>
			<th>TimeStamp</th>
			<th>Active</th>
		</tr>
		<c:forEach items="${requests}" var="data">
			<c:if test="${data.getActive() == true}">
				<tr bgcolor="lightgrey" align="center">
					<td>${data.getName()}</td>
					<td>${data.getEmail()}</td>
					<td>${data.getMessage()}</td>
					<td>${data.getTime()}</td>
					<td><form action="request" method="post">
							<input type="hidden" name="id" value=${ data.getId()} /> <input
								type="submit" name="active" value="Archive">
						</form></td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
	<br> Archive State
	<br>
	<table bgcolor="black" width=100%>
		<tr bgcolor=#ebd69d>
			<th>Name</th>
			<th>Email</th>
			<th>Message</th>
			<th>TimeStamp</th>
			<th>Archive</th>
		</tr>
		<c:forEach items="${requests}" var="data">
			<c:if test="${data.getActive()== false}">
				<tr bgcolor="lightgrey" align="center">
					<td>${data.getName()}</td>
					<td>${data.getEmail()}</td>
					<td>${data.getMessage()}</td>
					<td>${data.getTime()}</td>
					<td><form action="request" method="post">
							<input type="hidden" name="id" value=${ data.getId()} /> <input
								type="submit" name="active" value="Active">
						</form></td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
	<form action="logout">
		<input type="submit" value="Logout">
	</form>
</body>
</html>