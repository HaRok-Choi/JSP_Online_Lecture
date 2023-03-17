<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	고객님이 수강하신 과목은, <br>
	param.subject : ${param.subject } <br> 	<!-- 가장 먼저 선택된 것만 일단 나옴 -->
	${paramValues.subject[0] } <br>
	${paramValues.subject[1] } <br>
	${paramValues.subject[2] } <br>
	${paramValues.subject[3] } <br> <!-- null이긴 하지만 EL이기 때문에 보이지않음 -->
	${paramValues.subject[4] } <br>
	${paramValues.subject[5] } <br>
</body>
</html>