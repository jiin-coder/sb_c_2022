package com.kja.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kja.exam.demo.service.MemberService;
import com.kja.exam.demo.vo.Member;

@Controller
public class UsrMemberController {
	private MemberService memberService;
	
	public UsrMemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Member doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {
		int id = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		
		Member member = memberService.getMemberById(id);
		
		return member;
	}
	// http://localhost:8011/usr/member/doJoin?loginId=fpahstiq123&loginPw=sbs123414&name=%EB%B0%95%EC%84%B1%ED%99%94&nickname=%EB%B3%84%EC%9D%B4&cellphoneNo=01020181024&email=fpahstiq123@naver.com
}