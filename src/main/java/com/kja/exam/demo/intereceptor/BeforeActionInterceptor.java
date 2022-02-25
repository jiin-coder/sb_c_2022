package com.kja.exam.demo.intereceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kja.exam.demo.vo.Rq;

@Component
public class BeforeActionInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		Rq rq = new Rq(req, resp);
		req.setAttribute("rq", rq);

		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
}

   