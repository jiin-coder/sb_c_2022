<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
</head>
<body>

  <h1>게시물 리스트 페이지</h1>

  <header>

    <a href="/">로고</a>

    <ul>
      <li><a href="/">홈</a></li>
      <li><a href="/usrarticle/list">리스트</a></li>
    </ul>
  </header>



  <hr />

  <table border="1">
    <thead>
      <tr>
        <th>번호</th>
        <th>작성날짜</th>
        <th>수정날짜</th>
        <th>작성자</th>
        <th>제목</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="article" items="${articles}">
        <tr>
          <td>${article.id}</td>
          <td>${article.regDate.substring(2, 16)}</td>
          <td>${article.updateDate.substring(2, 16)}</td>
          <td>${article.memberId}</td>
          <td><a href="../article/detail?id=${article.id}">${article.title}</a></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</body>
</html>