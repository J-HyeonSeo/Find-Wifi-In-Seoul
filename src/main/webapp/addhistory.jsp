<%@page import="repository.HistoryRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	HistoryRepository historyRepository = 
				(HistoryRepository)request.getAttribute("historyRepository");

	double lat = Double.valueOf(request.getParameter("LAT"));
	double lnt = Double.valueOf(request.getParameter("LNT"));
	
	boolean result = historyRepository.addHistory(lat, lnt);
	
	if(result){
		out.print("ok");
	}else{
		throw new RuntimeException("위치 히스토리 저장 오류");
	}
	
%>