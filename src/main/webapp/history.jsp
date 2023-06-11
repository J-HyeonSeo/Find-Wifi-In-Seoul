<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="repository.HistoryRepository"%>
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
    <link rel="stylesheet" href="styles/form.css?ver=<%=formatedNow%>">
    <link rel="stylesheet" href="styles/maintable.css?ver=<%=formatedNow%>">
    <link rel="stylesheet" href="styles/modal.css?ver=<%=formatedNow%>">
    <script src="js/loaddatas.js?ver=<%=formatedNow%>" defer></script>
    <script src="js/history-delete.js?ver=<%=formatedNow%>" defer></script>
    
    <title>와이파이 정보 구하기</title>
</head>
<body>
    
    <%
		HistoryRepository historyRepository = (HistoryRepository)request.getAttribute("historyRepository");
    %>
    
    <div id="myModal" class="modal">
      <div class="modal-content">
        <h2>데이터 로딩 중....</h2>
        <p id="load-text">서울시 와이파이 데이터를 가져오고 있습니다.</p>
        <a id="close-modal">확인</a>
      </div>
    </div>

    <h1>위치 히스토리 목록</h1>
    
    <header id="main-header">
        <ul>
            <li><a href="/">홈</a></li>
            <li><a href="/history">히스토리</a></li>
            <li><a href="/bookmark">북마크목록</a></li>
            <li id="open-api"><a>OpenAPI</a></li>
        </ul>
    </header>
    <br>
    <main>
        <table>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">위도</th>
                <th scope="col">경도</th>
                <th scope="col">조회일시</th>
                <th scope="col">관리</th>
              </tr>
              <%
              	boolean is_can = true;
              	ArrayList<ArrayList<String>> res = historyRepository.findAll();
              	if(res == null || res.size() == 0){
              		is_can = false;
              	}
              %>
              <% if(is_can) { %>
              	<% for(ArrayList<String> record : res) { %>
              		<%
              			int id = Integer.valueOf(record.get(0));
              			String lat = record.get(1);
              			String lnt = record.get(2);
              			String registerdAt = record.get(3);
              		%>
		              <tr>
		                <td><%=id%></td>
		                <td><%=lat%></td>
		                <td><%=lnt %></td>
		                <td><%=registerdAt%></td>
		                <td><button onclick="deleteHistory(<%=id%>)">삭제</button></td>
		              </tr>
	              <% } %>
              <% } %>
        </table>
        <% if(!is_can) { %>
        	<h2>위치 히스토리 기록이 존재하지 않습니다.</h2>
        <% } %>
    </main>

</body>
</html>