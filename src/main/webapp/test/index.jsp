<%@page import="libTest.httptest"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	
	<%
		String result = request.getParameter("user");
		//String result = httptest.callDatafromApi();
		out.print(result);
		
	%>
	
</body>
</html>