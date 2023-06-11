<%@page import="repository.BookmarkGroupRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
    	
		BookmarkGroupRepository bookmarkGroupRepository =
				(BookmarkGroupRepository)request.getAttribute("bookmarkGroupRepository");
    
    	int id = Integer.valueOf(request.getParameter("ID"));
    	
    	boolean result = bookmarkGroupRepository.deleteBoolmarkGroup(id);
    	
    	if(result){
    		out.print("ok");
    	}else{
    		throw new RuntimeException("북마크 삭제 오류!!");
    	}
    
    %>