<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.Enumeration" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		Enumeration<String> names = session.getAttributeNames();
	
		out.write("속성 이름들 : ");
		while(names.hasMoreElements()){
			out.write(names.nextElement() + " ");
		}
		out.write("<br>");
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd hh:mm:ss");
		
		long creationTime = session.getCreationTime();
		String startTime = sdf.format(creationTime);
		out.write("세션 생성 시간 : " + startTime + "<br>");
		
		long LastAccessedTime = session.getLastAccessedTime();
		String LastTime = sdf.format(creationTime);
		out.write("마지막 요청 시간 : " + LastTime + "<br>");
		
		out.write("세션ID(JSESSIONID) : " + session.getId() + "<br>");
		
		int interval = session.getMaxInactiveInterval();
		out.write("만료 대기 시간 : " + interval + "초");
	%>
</body>
</html>