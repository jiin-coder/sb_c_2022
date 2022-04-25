package com.kja.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kja.exam.demo.intereceptor.BeforeActionInterceptor;
import com.kja.exam.demo.intereceptor.NeedLoginInterceptor;
import com.kja.exam.demo.intereceptor.NeedLogoutInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
	// beforeActionInterceptor 인터셉터 불러오기
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;

	// NeedLoginInterceptor 인터셉터 불러오기
	@Autowired
	NeedLoginInterceptor needLoginInterceptor;

	// needLogoutInterceptor 인터셉터 불러오기
	@Autowired
	NeedLogoutInterceptor needLogoutInterceptor;

	// 이 함수는 인터셉터를 적용하는 역할을 합니다.
	// addInterceptor : 등록할 인터셉터 설정
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration ir;
		
		ir = registry.addInterceptor(beforeActionInterceptor);
		ir.addPathPatterns("/**");
		ir.excludePathPatterns("/favicon.ico");
		ir.excludePathPatterns("/resource/**");
		ir.excludePathPatterns("/error");

		ir = registry.addInterceptor(needLoginInterceptor);
		ir.addPathPatterns("/usr/member/modify");
		ir.addPathPatterns("/usr/member/doModify");
		ir.addPathPatterns("/usr/reply/modify");
		ir.addPathPatterns("/usr/reply/doModify");
		ir.addPathPatterns("/usr/reply/doWrite");
		ir.addPathPatterns("/usr/reply/doDelete");
		

		ir.addPathPatterns("/usr/article/write");
		ir.addPathPatterns("/usr/article/doWrite");
		ir.addPathPatterns("/usr/article/modify");
		ir.addPathPatterns("/usr/article/doModify");
		ir.addPathPatterns("/usr/article/doDelete");
		ir.addPathPatterns("/usr/reactionPoint/doGoodReaction");
		ir.addPathPatterns("/usr/reactionPoint/doBadReaction");
		ir.addPathPatterns("/usr/reactionPoint/doCancelGoodReaction");
		ir.addPathPatterns("/usr/reactionPoint/doCancelBadReaction");

		ir = registry.addInterceptor(needLogoutInterceptor);
		ir.addPathPatterns("/usr/member/join");
		ir.addPathPatterns("/usr/member/getLoginIdDup");
		ir.addPathPatterns("/usr/member/doJoin");
		ir.addPathPatterns("/usr/member/login");
		ir.addPathPatterns("/usr/member/doLogin");
		ir.addPathPatterns("/usr/member/findLoginId");
		ir.addPathPatterns("/usr/member/doFindLoginId");
		ir.addPathPatterns("/usr/member/findLoginPw");
		ir.addPathPatterns("/usr/member/doFindLoginPw");
	}
}
