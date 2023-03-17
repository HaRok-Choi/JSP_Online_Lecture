<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:forEach var="c" items="${ cookie }">
		c.key : ${ c.key } <br>
		c.value : ${ c.value }<br> <!-- reference가 찍힘 -->
		c.value.name : ${ c.value.name }<br>
		c.value.value : ${ c.value.value }<br><br>
	</c:forEach>
	<!-- map형식으로 되어있고 key에 따른 value는 쿠키객체로 이루어져 있고 이게 하나의 주소로 찍힘 -->
	<!-- 쿠키객체 내부에도 key, value 등등 여러가지가 있음 -->
</body>
</html>