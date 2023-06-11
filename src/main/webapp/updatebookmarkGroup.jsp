<%@page import="repository.BookmarkGroupRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
    	
		BookmarkGroupRepository bookmarkGroupRepository =
			(BookmarkGroupRepository)request.getAttribute("bookmarkGroupRepository");
    
    	int id = Integer.valueOf(request.getParameter("ID"));
    	String name = request.getParameter("NAME");
    	int order = Integer.valueOf(request.getParameter("ORDER"));
    	
    	boolean result = bookmarkGroupRepository.updateBookmarkGroup(id, name, order);
    	
    	if(result){
    		out.print("ok");
    	}else{
    		throw new RuntimeException("북마크 수정 오류!!");
    	}
    
    %>