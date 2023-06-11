<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="repository.WifiRepository"%>
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
    <script src="js/script.js?ver=<%=formatedNow%>" defer></script>
    <script src="js/loaddatas.js?ver=<%=formatedNow%>" defer></script>
    <title>와이파이 정보 구하기</title>
</head>
<body>
    
    <%
    	//필요한 객체를 가져와야함.
    	WifiRepository wifiRepository = (WifiRepository)request.getAttribute("wifiRepository");
		
    %>
    
    <div id="myModal" class="modal">
      <div class="modal-content">
        <h2>와이파이 데이터 가져오기</h2>
        <p id="load-text">서울시 와이파이 데이터를 가져오고 있습니다...</p>
        <a id="close-modal">확인</a>
      </div>
    </div>

    <h1>와이파이 정보 구하기</h1>
    
    <header id="main-header">
        <ul>
            <li><a href="/">홈</a></li>
            <li><a href="/history">히스토리</a></li>
            <li><a href="/bookmark">북마크목록</a></li>
            <li id="open-api"><a>OpenAPI</a></li>
        </ul>
        <form action="" method="get">
            <label for="LAT">위도 : </label>
            <input id="LAT" type="text" name="LAT">
            <label for="LNT">경도 : </label>
            <input id="LNT" type="text" name="LNT">
            <button id="load" type="button">내 위치 불러오기</button>
            <button>근처 WIFI 불러오기</button>
        </form>
    </header>
	
    <main>
        <table>
            <tr>
                <th scope="col">거리(Km)</td>
                <th scope="col">관리번호</td>
                <th scope="col">자치구</td>
                <th scope="col">와이파이명</td>
                <th scope="col">도로명주소</td>
                <th scope="col">상세주소</td>
                <th scope="col">설치유형</td>
              </tr>
              <%
              	boolean is_can = true;
              	
              	String tempx1 = request.getParameter("LAT");
              	String tempy1 = request.getParameter("LNT");
              	
              	if(tempx1 == null || tempx1.equals("")){
              		is_can = false;
              	}
              	
              	if(tempy1 == null || tempy1.equals("")){
              		is_can = false;
              	}
              	
              	if(is_can){
              		
              		double x1 = Double.parseDouble(tempx1);
              		double y1 = Double.parseDouble(tempy1);
              	
	              	ArrayList<ArrayList<String>> results = wifiRepository.findByDistance(20, x1, y1);
	              
	              	for(ArrayList<String> record : results){
	              		out.print("<tr>");
	              		
	              		double dist = Double.parseDouble(record.get(record.size() - 1));
	              		String sDist = String.format("%.3f", dist);
	              		
	              		out.print("<td>"+ sDist +"</td>");
	              		
	              		for(int i = 1; i < record.size()-3; i++){
	              			
	              			out.print("<td>");
	              			
	              			
	              			//와이파이명에 a태그 붙여주어야 함.
	              			if(i == 3){
	              				out.print( "<a href=\"/wifidetails?ID=" + record.get(0) + "&DIST=" + sDist + "\">" + record.get(i) +"</a>" );
	              			}else{
	              				out.print(record.get(i));
	              			}
	              			
	              			
	              			out.print("</td>");
	              			
	              		}
	              		out.print("</tr>");
	              	}
              	}
              %>
        </table>
        <% if(!is_can) { %>
        	<h2>와이파이를 조회해주세요.</h2>
        <% } %>
    </main>

</body>
</html>