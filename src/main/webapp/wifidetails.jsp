<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="repository.BookmarkGroupRepository"%>
<%@page import="java.awt.datatransfer.StringSelection"%>
<%@page import="java.util.stream.Collector"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.stream.IntStream"%>
<%@page import="repository.WifiRepository"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	Date now = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	String formatedNow = formatter.format(now);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles/base.css?ver=<%=formatedNow%>">
    <link rel="stylesheet" href="styles/subtable.css?ver=<%=formatedNow%>">
    <link rel="stylesheet" href="styles/form.css?ver=<%=formatedNow%>">
    <link rel="stylesheet" href="styles/modal.css?ver=<%=formatedNow%>">
    <script src="js/loaddatas.js?ver=<%=formatedNow%>" defer></script>
	<script src="js/addbookmark.js?ver=<%=formatedNow%>" defer></script>

    <title>와이파이 정보 구하기</title>
</head>
<body>
    
    <%
		BookmarkGroupRepository bookmarkGroupRepository =
			(BookmarkGroupRepository)request.getAttribute("bookmarkGroupRepository");
		WifiRepository wifiRepository =
			(WifiRepository)request.getAttribute("wifiRepository");
    %>
    
    <div id="myModal" class="modal">
      <div class="modal-content">
        <h2>데이터 로딩 중....</h2>
        <p id="load-text">서울시 와이파이 데이터를 가져오고 있습니다.</p>
        <a id="close-modal">확인</a>
      </div>
    </div>

    <h1>와이파이 상세 보기</h1>
    
    <header id="main-header">
        <ul>
            <li><a href="/">홈</a></li>
            <li><a href="/history">히스토리</a></li>
            <li><a href="/bookmark">북마크목록</a></li>
            <li id="open-api"><a>OpenAPI</a></li>
        </ul>
    </header>

    <section>
        <form>
        	<%
        		ArrayList<ArrayList<String>> groups = bookmarkGroupRepository.findAll();
        	%>
            <label for="group-combo">
              북마크 목록
              <select id="group-combo" name="group-combo">
              	<% for(ArrayList<String> group : groups) { %>
              	<% 
              		String id = group.get(0);
              		String name = group.get(1);
              	%>
                <option value="<%=id%>"><%=name%></option>
                <% } %>
              </select>
            </label>
            <button id="addbookmark-btn" type="button">북마크 추가</button>
          </form>
    </section>

    <main>
        <table>
            <tbody>
            	<%
            		
	                ArrayList<Integer> cols = new ArrayList<>();
	                
            		for(int i = 1; i <= 17; i++)cols.add(i);
            		
	                int id = Integer.valueOf(request.getParameter("ID"));
            		
	                //데이터베이스에서 상세 데이터 가져오기.
            		ArrayList<ArrayList<String>> res = wifiRepository.findById(id, cols);
            		
	                boolean is_can = true;
	                
            		if(res.size() != 1){ //반드시 하나의 데이터 여야함!!
            			is_can = false;
            		}
            		
            		if(is_can){
            			
                		ArrayList<String> record = res.get(0);
                		
                		record.add(1, request.getParameter("DIST"));
            			
            			String[] columnNames = {"", "거리(Km)", "관리번호", "자치구", "와이파이명", "도로명주소", "상세주소", "설치위치(층)",
            					"설치유형", "설치기관", "서비스구분", "망종류", "설치년도", "실내외구분", "WIFI접속환경", "위도", "경도", "작업일자"};
            			
            			for(int i = 1; i < record.size(); i++){
            				
            				out.print("<tr>");
            				
            				out.print("<td>" + columnNames[i] + "</td>");
            				out.print("<td>" + record.get(i) + "</td>");
            				
            				out.print("</tr>");	
            			}	
            		}
            	%>
            </tbody>
        </table>
    </main>
<body>
</html>
