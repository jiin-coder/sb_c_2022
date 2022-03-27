<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="회원가입" />
<%@ include file="../common/head.jspf"%>

<script type="text/javascript">
  let submitJoinFormDone = false;
  function submitJoinForm(form) {
    if (submitJoinFormDone) {
      alert('처리중입니다.');
      return;
    }
    form.loginId.value = form.loginId.value.trim();
    if (form.loginId.value.length == 0) {
      alert('로그인아이디를 입력해주세요.');
      form.loginId.focus();
      return;
    }
    form.loginPw.value = form.loginPw.value.trim();
    if (form.loginPw.value.length == 0) {
      alert('로그인비밀번호를 입력해주세요.');
      form.loginPw.focus();
      return;
    }
    form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
    if (form.loginPwConfirm.value.length == 0) {
      alert('로그인비밀번호 확인을 입력해주세요.');
      form.loginPwConfirm.focus();
      return;
    }
    if (form.loginPw.value != form.loginPwConfirm.value) {
      alert('로그인비밀번호 확인이 일치하지 않습니다.');
      form.loginPwConfirm.focus();
      return;
    }
    form.name.value = form.name.value.trim();
    if (form.name.value.length == 0) {
      alert('이름을 입력해주세요.');
      form.name.focus();
      return;
    }
    form.nickname.value = form.nickname.value.trim();
    if (form.nickname.value.length == 0) {
      alert('닉네임을 입력해주세요.');
      form.nickname.focus();
      return;
    }
    form.email.value = form.email.value.trim();
    if (form.email.value.length == 0) {
      alert('이메일을 입력해주세요.');
      form.email.focus();
      return;
    }
    form.cellphoneNo.value = form.cellphoneNo.value.trim();
    if (form.cellphoneNo.value.length == 0) {
      alert('휴대전화번호를 입력해주세요.');
      form.cellphoneNo.focus();
      return;
    }
    submitJoinFormDone = true;
    form.submit();
  }
</script>

<section class="mt-5">
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="POST"
      action="../member/doJoin"
      onsubmit="submitJoinForm(this); return false;">
      <input type="hidden" name="afterLoginUri"
        value="${param.afterLoginUri}" />
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>
        <tbody>
          <tr>
            <th>로그인아이디</th>
            <td>
              <input name="loginId" class="w-96 input input-bordered"
                type="text" placeholder="로그인아이디" />
            </td>
          </tr>
          <tr>
            <th>로그인비밀번호</th>
            <td>
              <input name="loginPw" class="w-96 input input-bordered"
                type="password" placeholder="로그인비밀번호" />
            </td>
          </tr>
          <tr>
            <th>로그인비밀번호 확인</th>
            <td>
              <input name="loginPwConfirm"
                class="w-96 input input-bordered" type="password"
                placeholder="로그인비밀번호 확인" />
            </td>
          </tr>
          <tr>
            <th>이름</th>
            <td>
              <input name="name" class="w-96 input input-bordered"
                type="text" placeholder="이름" />
            </td>
          </tr>
          <tr>
            <th>닉네임</th>
            <td>
              <input name="nickname" class="w-96 input input-bordered"
                type="text" placeholder="닉네임" />
            </td>
          </tr>
          <tr>
            <th>이메일</th>
            <td>
              <input name="email" class="w-96 input input-bordered"
                type="email" placeholder="이메일" />
            </td>
          </tr>
          <tr>
            <th>휴대전화번호</th>
            <td>
              <input name="cellphoneNo"
                class="w-96 input input-bordered" type="text"
                placeholder="휴대전화번호" />
            </td>
          </tr>
          <tr>
            <th>회원가입</th>
            <td>
              <button type="submit" class="btn btn-primary">회원가입</button>
              <button type="button"
                class="btn btn-outline btn-secondary"
                onclick="history.back();">뒤로가기</button>
            </td>
          </tr>
        </tbody>
      </table>
    </form>
  </div>
</section>

<%@ include file="../common/foot.jspf"%>