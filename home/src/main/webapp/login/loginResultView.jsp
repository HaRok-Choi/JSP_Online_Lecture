<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page = "/layout/header.jsp" >
	<jsp:param value="Login" name="title"/>
</jsp:include>

<c:choose>
	<c:when test="${ sessionScope.currentNickname != null }">
		<h3>환영합니다. ${ sessionScope.currentNickname }님!</h3>
		<br>
		<h5><a href="/home/signout/signoutView.jsp" >회원탈퇴</a></h5>
	</c:when>
	<c:otherwise>
		<h3>아이디 혹은 비밀번호를 확인해주세요.</h3>
	</c:otherwise>
</c:choose>

<input type="button" value="메인으로" onclick="location.href='/home/index.jsp'">

<jsp:include page="/layout/footer.jsp"/>