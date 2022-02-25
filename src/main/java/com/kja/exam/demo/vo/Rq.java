package com.kja.exam.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kja.exam.demo.util.Ut;

import lombok.Getter;

public class Rq {

	@Getter
	private boolean isLogined;

	@Getter
	private int loginedMemberId;
	private HttpServletRequest req;
	private HttpServletResponse resp;

	public Rq(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;

		HttpSession httpSession = req.getSession();
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;

	}

	public void printHistoryBackJs(String msg) {
		resp.setContentType("text/html; charset=UTF-8");
		
		println("<script>");

		if (!Ut.empty(msg)) {
			println("alert('" + msg + "');");
		}

		println("history.back();");

		println("</script>");
	}

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void println(String str) {
		print(str + "\n");
	}
}
