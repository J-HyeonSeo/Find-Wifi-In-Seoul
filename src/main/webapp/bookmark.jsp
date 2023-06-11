<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="repository.BookmarkRepository"%>
<%@page import="java.util.ArrayList"%>
<%@page import="repository.BookmarkGroupRepository"%>
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
    <script src="js/makegroup.js?ver=<%=formatedNow%>" defer></script>
    <title>와이파이 정보 구하기</title>
</head>
<body>
    
    <%
    	BookmarkGroupRepository bookmarkGroupRepository =
    			(BookmarkGroupRepository)request.getAttribute("bookmarkGroupRepository");
    	BookmarkRepository bookmarkRepository =
    			(BookmarkRepository)request.getAttribute("bookmarkRepository");
    %>
    
    <div id="myModal" class="modal">
      <div class="modal-content">
        <h2>데이터 로딩 중....</h2>
        <p id="load-text">서울시 와이파이 데이터를 가져오고 있습니다.</p>
        <a id="close-modal">확인</a>
      </div>
    </div>
    
    <!-- create new bookmark group -->
    <div id="group-modal" class="modal">
        <div class="modal-content">
          <h2>새 북마크 그룹</h2>
          <p>새 북마크를 만듭니다.</p>
          <p>
            <label for="bookmark-name">북마크 그룹 이름 </label>
            <br>
            <input type="text" id="bookmark-name" name="bookmark-name">
          </p>
          <p>
            <label for="order">순서 </label>
            <br>
            <input type="text" id="order" name="order">
          </p>
          <a id="create-group-btn">만들기</a>
        </div>
    </div>

    <!-- update bookmark group -->
    <div id="group-update-modal" class="modal">
      <div class="modal-content">
        <h2>북마크 정보 수정</h2>
        <p>북마크의 정보를 수정합니다.</p>
        <p>
          <label for="bookmark-update-name">북마크 그룹 이름 </label>
          <br>
          <input type="text" id="bookmark-update-name" name="bookmark-update-name">
        </p>
        <p>
          <label for="update-order">순서 </label>
          <br>
          <input type="text" id="update-order" name="update-order">
        </p>
        <a id="update-group-btn">수정</a>
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
    </header>

    <section>
        <button id="make-bookmark" type="button">북마크 그룹 만들기</button>
    </section>

    <main>
        <table>
            <thead>
                <tr>
                    <th>북마크그룹명</th>
                    <th>목록수</th>
                    <th>순서</th>
                    <th>등록일시</th>
                    <th>수정일시</th>
                    <th>관리</th>
                </tr>
            </thead>
            <tbody>
            	<% 
            		boolean is_draw = true;
            		ArrayList<ArrayList<String>> groups = bookmarkGroupRepository.findAll();
        		  	if(groups.size() == 0){
        		  		is_draw = false;
        		  	}
            	%>
            	<% if(is_draw){ %>
            		<% for(ArrayList<String> record : groups) { %>
		              <tr>
		              	<%
		              		int id = Integer.valueOf(record.get(0));
		              		String name = record.get(1);
		              		String link = "/bookmarkdetails?ID=" + id;
		              		String count = bookmarkRepository.countByGroup(id);
		              		String order = record.get(2);
		              		String registerdAt = record.get(3);
		              		String editedAt = record.get(4);
		              		if(editedAt == null || editedAt.equals("null")){
		              			editedAt = "-";
		              		}
		              	%>
		                <td><%=name%></td>
		                <td><a href="<%=link%>"><%=count%>개 저장됨.</a></td>
		                <td><%=order%></td>
		                <td><%=registerdAt%></td>
		                <td><%=editedAt%></td>
		                <td><button onclick="updateOpen(<%=id%>)">수정</button>   <button onclick="deleteOpen(<%=id%>)">삭제</button></td>
		              </tr>
		            <% } %>
	            <% } %>
            </tbody>
        </table>
        <% if(!is_draw){ %>
        	<h2>북마크가 없습니다.</h2>
        <% } %>
    </main>
<body>
</html>