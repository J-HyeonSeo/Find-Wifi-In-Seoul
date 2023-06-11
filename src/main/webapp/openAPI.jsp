<%@page import="service.OpenAPILoaderService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
	OpenAPILoaderService openAPILoaderService =
				(OpenAPILoaderService)request.getAttribute("openAPILoaderService");

	String elapsed = openAPILoaderService.loadFromApi();
	
	out.print(elapsed);	

%>