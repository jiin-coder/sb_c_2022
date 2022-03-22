<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="댓글 수정" />
<%@ include file="../common/head.jspf"%>

<script>
  let ReplyModify__submitDone = false;
  function ReplyModify__submit(form) {
    if ( ReplyModify__submitDone ) {
      return;
    }
    
    form.body.value = form.body.value.trim();
    
    if ( form.body.value.length == 0 ) {
      alert('내용을 입력해주세요.')
      form.body.focus();
      
      return;
    }
    
    ReplyModify__submitDone = true;
    form.submit();
  }
</script>

<section class="mt-5">
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="POST" action="../reply/doModify"
      onsubmit="ReplyModify__submit(this); return false;">
      <input type="hidden" name="id" value="${reply.id}" />
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>
        <tbody>
          <tr>
            <th>게시물 번호</th>
            <td>${reply.relId}</td>
          </tr>
          <tr>
            <th>게시물 제목</th>
            <td>${relDataTitle}</td>
          </tr>
          <tr>
            <th>댓글번호</th>
            <td>
              <div class="badge badge-primary">${reply.id}</div>
            </td>
          </tr>
          <tr>
            <th>댓글작성날짜</th>
            <td>${reply.forPrintType2RegDate}</td>
          </tr>
          <tr>
            <th>댓글수정날짜</th>
            <td>${reply.forPrintType2UpdateDate}</td>
          </tr>
          <tr>
            <th>댓글작성자</th>
            <td>${reply.extra__writerName}</td>
          </tr>
          <tr>
            <th>댓글추천</th>
            <td>
              <span class="badge badge-primary">${reply.goodReactionPoint}</span>
            </td>
          </tr>
          <tr>
            <th>댓글내용</th>
            <td>
              <textarea class="w-full textarea textarea-bordered" name="body" rows="5" placeholder="내용">${reply.body}</textarea>
            </td>
          </tr>
          <tr>
            <th>댓글수정</th>
            <td>
              <button type="submit" class="btn btn-primary">댓글수정</button>
              <button type="button" class="btn btn-secondary btn-outline" onclick="history.back();">뒤로가기</button>
            </td>
          </tr>
        </tbody>
      </table>
    </form>
</section>

<%@ include file="../common/foot.jspf"%>