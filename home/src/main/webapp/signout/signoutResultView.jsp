<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page = "/layout/header.jsp" >
	<jsp:param  value="SignOut" name="title"/>
</jsp:include>

	<!--   boolean result = Boolean.parseBoolean(String.valueOf(request.getAttribute("result"))); 
			 String.valueOf : 자동으로 String으로 형변환 -->
			 
<c:set var="message">
	<c:choose>
		<c:when test="${ requestScope.result != null }">
			회원 탈퇴를 완료하였습니다.
		</c:when>
		<c:otherwise>
			아이디, 비밀번호를 다시 확인해주세요.
		</c:otherwise>
	</c:choose>
</c:set> 


	<script>
		alert('${ message }');
		location.href='/home/index.jsp';
	</script>


<jsp:include page="/layout/footer.jsp"/>