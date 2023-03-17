<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%
	// 쿠키 생성
	Cookie cookie1 = new Cookie("COOKIENAME1", "COOKIEVALUE1");
	Cookie cookie2 = new Cookie("COOKIENAME2", "COOKIEVALUE2");
	
	// 쿠키 설정
	cookie1.setComment("This is my cookeis!");
	cookie1.setPath("/");
	cookie1.setMaxAge(60 * 60);	// 한 시간(유효기간)
	
	// 응답객체에 쿠키 추가 (클라이언트 브라우저에 쿠키 저장)
	response.addCookie(cookie1);
	response.addCookie(cookie2);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	쿠키 만듦
</body>
</html>