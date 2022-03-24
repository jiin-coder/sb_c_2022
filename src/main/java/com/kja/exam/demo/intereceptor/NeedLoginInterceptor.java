package com.kja.exam.demo.intereceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kja.exam.demo.vo.Rq;

@Component
public class NeedLoginInterceptor implements HandlerInterceptor {
	private Rq rq;
	
	public NeedLoginInterceptor(Rq rq) {
		this.rq = rq;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		if (!rq.isLogined()) {
			// rq.printHistoryBackJs("로그인 후 이용해주세요.");
			rq.printReplaceJs("로그인 후 이용해주세요.", "../member/login");
			return false;
		}

		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
}   