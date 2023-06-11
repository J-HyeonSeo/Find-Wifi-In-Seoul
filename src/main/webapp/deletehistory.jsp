<%@page import="repository.HistoryRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	HistoryRepository historyRepository =
			(HistoryRepository)request.getAttribute("historyRepository");

	int id = Integer.valueOf(request.getParameter("ID"));
	
	boolean result = historyRepository.deleteHistory(id);
	
	if(result){
		out.print("ok");
	}else{
		throw new RuntimeException("위치 히스토리 삭제 오류");
	}
	
%>