package com.kja.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kja.exam.demo.service.ArticleService;
import com.kja.exam.demo.util.Ut;
import com.kja.exam.demo.vo.Article;
import com.kja.exam.demo.vo.ResultData;

@Controller
public class UsrArticleController {

	private ArticleService articleService;

	public UsrArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpSession httpSession, String title, String body) {
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}

		if (Ut.empty(title)) {
			return ResultData.from("F-1", "title(을)를 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return ResultData.from("F-2", "body(을)를 입력해주세요.");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(loginedMemberId, title, body);
		int id = writeArticleRd.getData1();

		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		return ResultData.newData(writeArticleRd, "article", article);
	}
	// http://localhost:8011/usr/article/doAdd?title=제목1117&body=1117

	
	@RequestMapping("/usr/article/list")
	public String showList(HttpSession httpSession, Model model) {
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}
		
		List<Article> articles = articleService.getForPrintArticles(loginedMemberId);
		
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpSession httpSession, Model model, int id) {
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}
		
		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}
	// http://localhost:8011/usr/article/getArticles

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(HttpSession httpSession, int id) {
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}
		
		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", "id", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), "article", article);
	}
	// http://localhost:8011/usr/article/getArticle?id=6

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpSession httpSession, int id) {
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return Ut.jsHistoryBack("로그인 후 이용해주세요.");
		}

		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		if (article.getMemberId() != loginedMemberId) {
			return Ut.jsHistoryBack("권한이 없습니다.");
		}

		if (article == null) {
			return Ut.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		articleService.deleteArticle(id);

		return Ut.jsReplace(Ut.f("%d번 게시물을 삭제하였습니다.", id), "../article/list");
	}
	// http://localhost:8011/usr/article/doDelete?id=1

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpSession httpSession, int id, String title, String body) {

		boolean isLogined = false;
		int loginedMemberId = 0;
		
		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		if (article == null) {
			ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(loginedMemberId, article);

		
		if ( actorCanModifyRd.isFail() ) {
			return actorCanModifyRd;
		}
		
		return articleService.modifyArticle(id, title, body);
	}
	// http://localhost:8011/usr/article/doModify?id=1&title=asdasdas&body=asdasd
}