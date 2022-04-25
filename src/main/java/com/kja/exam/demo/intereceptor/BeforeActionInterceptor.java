package com.kja.exam.demo.intereceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kja.exam.demo.service.MemberService;
import com.kja.exam.demo.vo.Rq;

@Component
public class BeforeActionInterceptor implements HandlerInterceptor {
	private Rq rq;
	
	 /* 프로그램 실행 시 rq 생성 */
	public BeforeActionInterceptor(Rq rq) {
		this.rq = rq;
	}
	
	
	/* 컨트롤러 호출 전 실행 */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		req.setAttribute("rq", rq);
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
}

    