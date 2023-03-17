<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	// COOKIENAME 쿠키삭제하기
	Cookie[] cookies = request.getCookies();

	for(Cookie c : cookies){
		if("COOKIENAME".equals(c.getName())) {
			c.setMaxAge(0);
			c.setPath("/");
			response.addCookie(c);
			break;
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	쿠키 삭제!
</body>
</html>