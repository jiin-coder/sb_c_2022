package com.kja.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kja.exam.demo.service.MemberService;

@Controller
public class UsrMemberController {

	private MemberService memberService;
	
	public UsrMemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		return "성공";
	}
}