package com.kja.exam.demo.service;

import org.springframework.stereotype.Service;

import com.kja.exam.demo.repository.MemberRepository;
import com.kja.exam.demo.util.Ut;
import com.kja.exam.demo.vo.Member;
import com.kja.exam.demo.vo.ResultData;

@Service
public class MemberService {

	private MemberRepository memberRepository;
	private AttrService attrService;
	
	public MemberService(AttrService attrService, MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
		this.attrService = attrService;
	}

	/* 회원가입 */
	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		// 기존에 로그인id 중복체크
		Member oldmember = getMemberByLoginId(loginId);

		if (oldmember != null) {
			return ResultData.from("F-7", Ut.f("해당 로그인 아이디(%s)는 이미 사용중입니다.", loginId));
		}

		// 이름+이메일 중복체크
		oldmember = getMemberByNameAndEmail(name, email);

		if (oldmember != null) {
			return ResultData.from("F-7", Ut.f("해당 이름(%s)과 이메일(%s)은 이미 사용중입니다.", name, email));
		}

		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		int id = memberRepository.getLastInsertId();
		
		return ResultData.from("S-1", "회원가입이 완료되었습니다.", "id", id);
	}

	/* 이름과 이메일로 회원 탐색 */
	private Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	/* 회원 로그인id 탐색 */
	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	/* 회원 id 탐색 */
	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}
	
	/* 회원정보 수정 메세지*/
	public ResultData modify(int id, String loginPw, String name, String nickname, String email, String cellphoneNo) {

		memberRepository.modify(id, loginPw, name, nickname, email, cellphoneNo);

		return ResultData.from("S-1", "회원정보가 수정되었습니다.");
	}

	/* 회원수정 인증키 생성*/
	public String genMemberModifyAuthKey(int actorId) {
		String memberModifyAuthKey = Ut.getTempPassword(10);
		attrService.setValue("member", actorId, "extra", "memberModifyAuthKey", memberModifyAuthKey, Ut.getDateStrLater(60 * 5));
		return memberModifyAuthKey;
	}

	/* 회원수정 인증키 일치체크*/ 
	public ResultData checkMemberModifyAuthKey(int actorId, String memberModifyAuthKey) {
		String saved = attrService.getValue("member", actorId, "extra", "memberModifyAuthKey");
		
		if ( !saved.equals(memberModifyAuthKey)) {
			return  ResultData.from("F-1", "일치하지 않거나 만료되었습니다.");
		}
		
		return ResultData.from("S-1", "정상적인 코드입니다.");
	}
}
