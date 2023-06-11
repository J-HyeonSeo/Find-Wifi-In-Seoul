<%@page import="repository.BookmarkRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
		BookmarkRepository bookmarkRepository =
			(BookmarkRepository)request.getAttribute("bookmarkRepository");	
    
    	int id = Integer.valueOf(request.getParameter("ID"));
    
    	boolean result = bookmarkRepository.deleteBookmark(id);
    	
    	if(result){
    		out.println("ok");
    	}else{
    		throw new RuntimeException("북마크 삭제 오류!!");
    	}
    %>