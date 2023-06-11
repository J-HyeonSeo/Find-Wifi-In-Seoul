<%@page import="repository.BookmarkRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
	BookmarkRepository bookmarkRepository = 
					(BookmarkRepository)request.getAttribute("bookmarkRepository");

	int groupdid = Integer.valueOf(request.getParameter("GROUPID"));
	int wifiid = Integer.valueOf(request.getParameter("WIFIID"));
	
	boolean result = bookmarkRepository.addBookmark(groupdid, wifiid);
	
	if(result){
		out.print("ok");
	}else{
		throw new RuntimeException("북마크 추가 오류!!");
	}
	
%>