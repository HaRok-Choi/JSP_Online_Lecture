<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Cookie[] cookies = request.getCookies();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	쿠키1 정보<br>
	이름 : <%= cookies[0].getName() %> <br>
	값 : <%= cookies[0].getValue() %> <br>
	<br>
		
	쿠키2 정보<br>
	이름 : <%= cookies[1].getName() %> <br>
	값 : <%= cookies[1].getValue() %> <br>
	<br>
		
	쿠키3 정보<br>
	이름 : <%= cookies[2].getName() %> <br>
	값 : <%= cookies[2].getValue() %> <br>
	<br>	
	
	
</body>
</html>