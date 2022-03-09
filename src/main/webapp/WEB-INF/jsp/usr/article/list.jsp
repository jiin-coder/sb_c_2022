<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="${board.name}게시물 리스트" />
<%@ include file="../common/head.jspf"%>


<section class="mt-5">
  <div class="container mx-auto px-3">
    <div>
      게시물 개수 :
      <div class="badge badge-primary">${articlesCount}</div>
      건
    </div>
    <div class="table-box-type-1">
      <table>
        <colgroup>
          <col width="50" />
          <col width="150" />
          <col width="150" />
          <col width="150" />
        </colgroup>

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
              <td>${article.extra__writerName}</td>
              <td><a class="btn-text-link" href="../article/detail?id=${article.id}">${article.title}</a></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <div class="page-menu mt-3">
      <div class="btn-group justify-center">
        <c:set var="pageMenuArmLen" value="9" />
        <c:set var="startPage" value="${page - pageMenuArmLen >= 1 ? page - pageMenuArmLen : 1}" />
        <c:set var="endPage" value="${page + pageMenuArmLen <= pagesCount ? page + pageMenuArmLen : pagesCount}" />

        <c:if test="${startPage > 1}">
          <a class="btn btn-sm" href="?page=1">1</a>
          <c:if test="${startPage > 2}">
            <a class="btn btn-sm btn-disabled">...</a>
          </c:if>
        </c:if>
        
        <c:forEach begin="${startPage}" end="${endPage}" var="i">
          <a class="btn btn-sm ${page == i ? 'btn-active' : ''}" href="?page=${i}">${i}</a>
        </c:forEach>
        
        <c:if test="${endPage < pagesCount}">
          <c:if test="${endPage < pagesCount - 1}">
            <a class="btn btn-sm btn-disabled">...</a>
          </c:if>
          <a class="btn btn-sm" href="?page=${pagesCount}">${pagesCount}</a>
        </c:if>
      
      </div>
    </div>

  </div>
</section>
<%@ include file="../common/foot.jspf"%>
