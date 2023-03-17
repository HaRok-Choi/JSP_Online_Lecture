<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// session 관련 메소드 (set 메소드)
	session.setAttribute("att1", "att1_value");
	session.setMaxInactiveInterval(5);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="Test02.jsp">Test02 이동</a><br>
	<a href="Test03.jsp">Test03 이동</a>
</body>
</html>