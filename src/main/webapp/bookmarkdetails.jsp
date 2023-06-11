<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="util.Distance"%>
<%@page import="repository.HistoryRepository"%>
<%@page import="java.util.Arrays"%>
<%@page import="repository.WifiRepository"%>
<%@page import="java.util.ArrayList"%>
<%@page import="repository.BookmarkGroupRepository"%>
<%@page import="repository.BookmarkRepository"%>
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
    <link rel="stylesheet" href="styles/maintable.css?ver=<%=formatedNow%>">
    <link rel="stylesheet" href="styles/form.css?ver=<%=formatedNow%>">
    <link rel="stylesheet" href="styles/modal.css?ver=<%=formatedNow%>">
    <script src="js/loaddatas.js?ver=<%=formatedNow%>" defer></script>
    <script src="js/bookmark-delete.js?ver=<%=formatedNow%>" defer></script>
    <title>와이파이 정보 구하기</title>
</head>
<body>
    
    <%
    	HistoryRepository historyRepository =
    			(HistoryRepository)request.getAttribute("historyRepository");
    	BookmarkGroupRepository bookmarkGroupRepository =
    			(BookmarkGroupRepository)request.getAttribute("bookmarkGroupRepository");
    	BookmarkRepository bookmarkRepository =
    			(BookmarkRepository)request.getAttribute("bookmarkRepository");
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

    <h1>북마크</h1>
    
    <header id="main-header">
        <ul>
            <li><a href="/">홈</a></li>
            <li><a href="/history">히스토리</a></li>
            <li><a href="/bookmark">북마크목록</a></li>
            <li id="open-api"><a>OpenAPI</a></li>
        </ul>
		<%
			int id = Integer.valueOf(request.getParameter("ID"));
			String groupName = bookmarkGroupRepository.findById(id, new ArrayList<Integer>(Arrays.asList(2))).get(0).get(0);
		%>
        <h1><%=groupName%></h1>

    </header>

    <main>
        <table>
            <thead>
                <tr>
                    <th>와이파이명</th>
                    <th>등록일자</th>
                    <th>관리</th>
                </tr>
            </thead>
            <tbody>
            	<%
            		boolean is_can = true;
            		ArrayList<ArrayList<String>> res = bookmarkRepository.findAllByGroup(id);
            		
            		if(res == null || res.size() == 0){
            			is_can = false;
            		}
            	%>
            	<% if(is_can) { %>
            		<% for(ArrayList<String> record : res) { %>
            			<%
            				int bookmarkID = Integer.valueOf(record.get(0));
            				int wifiID = Integer.valueOf(record.get(2));
            				String wifiName = wifiRepository.findById(wifiID, new ArrayList<Integer>(Arrays.asList(4))).get(0).get(0);
            				String registeredAt = record.get(3);
            				
            				//최근에 조회했던 위치 기록으로 거리를 산출함 없으면 ""로 넘겨줌.
            				String dist = "-";
            				double[] latestLoacation = historyRepository.findLatest();
            				
            				if(latestLoacation != null){
            					
            					double x1 = latestLoacation[0];
            					double y1 = latestLoacation[1];
            					
            					//wifiID를 기준으로 위도, 경도 가져옴.
            					ArrayList<ArrayList<String>> wifiLocation = wifiRepository.findById(wifiID, new ArrayList<>(Arrays.asList(15, 16)));
            					
            					double x2 = Double.valueOf(wifiLocation.get(0).get(0));
            					double y2 = Double.valueOf(wifiLocation.get(0).get(1));
            					
            					dist = String.format("%.3f", Distance.haversine(x1, y1, x2, y2));
            	
            				}
            				
            			%>
			              <tr>
			                <td><a href="/wifidetails?ID=<%=wifiID%>&DIST=<%=dist%>"><%=wifiName%></a></td>
			                <td><%=registeredAt%></td>
			                <td><button type="button" onclick="deleteBookmark(<%=bookmarkID%>)">삭제</button></td>
			              </tr>
              		<% } %>
              	<% } %>
            </tbody>
        </table>
        <% if(!is_can) { %>
        	<h2>해당 그룹에 북마크가 없습니다.</h2>
        <% } %>
    </main>
<body>
</html>
