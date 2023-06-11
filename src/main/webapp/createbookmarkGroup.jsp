<%@page import="repository.BookmarkGroupRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
	BookmarkGroupRepository bookmarkGroupRepository =
			(BookmarkGroupRepository)request.getAttribute("bookmarkGroupRepository");

	String name = request.getParameter("NAME");
	int order = Integer.parseInt(request.getParameter("ORDER"));
	boolean result = bookmarkGroupRepository.addBookmarkGroup(name, order);
	
	if(result){
		out.print("ok");
	}else{
		throw new RuntimeException("북마크 추가 오류!!");
	}
	

%>