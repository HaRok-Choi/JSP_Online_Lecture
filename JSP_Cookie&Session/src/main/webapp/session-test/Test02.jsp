<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	att1 : ${ sessionScope.att1 }
	<%-- 5초 동안 클라이언트가 요청하지 않으면 자동으로 세션은 만료되고 att1도 사라짐 --%>
</body>
</html>