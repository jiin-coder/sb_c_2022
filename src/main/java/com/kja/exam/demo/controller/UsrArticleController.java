package com.kja.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kja.exam.demo.service.ArticleService;
import com.kja.exam.demo.util.Ut;
import com.kja.exam.demo.vo.Article;
import com.kja.exam.demo.vo.ResultData;
import com.kja.exam.demo.vo.Rq;

@Controller
public class UsrArticleController {

	private ArticleService articleService;

	public UsrArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpServletRequest req, String title, String body) {
		Rq rq = (Rq)(req.getAttribute("rq"));

		if (Ut.empty(title)) {
			return ResultData.from("F-1", "title(을)를 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return ResultData.from("F-2", "body(을)를 입력해주세요.");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);
		int id = writeArticleRd.getData1();

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		return ResultData.newData(writeArticleRd, "article", article);
	}
	// http://localhost:8011/usr/article/doAdd?title=제목1117&body=1117

	
	@RequestMapping("/usr/article/list")
	public String showList(HttpServletRequest req, Model model) {
		Rq rq = (Rq)(req.getAttribute("rq"));
		
		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId());
		
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {
		Rq rq = (Rq)(req.getAttribute("rq"));
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}
	// http://localhost:8011/usr/article/getArticles

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(HttpServletRequest req, int id) {
		Rq rq = (Rq)(req.getAttribute("rq"));
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", "id", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), "article", article);
	}
	// http://localhost:8011/usr/article/getArticle?id=6

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			Ut.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("권한이 없습니다.");
		}

		articleService.deleteArticle(id);

		return Ut.jsReplace(Ut.f("%d번 게시물을 삭제하였습니다.", id), "../article/list");
	}
	// http://localhost:8011/usr/article/doDelete?id=1

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpServletRequest req, int id, String title, String body) {
		Rq rq = (Rq)(req.getAttribute("rq"));
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		
		if ( actorCanModifyRd.isFail() ) {
			return actorCanModifyRd;
		}
		
		return articleService.modifyArticle(id, title, body);
	}
	// http://localhost:8011/usr/article/doModify?id=1&title=asdasdas&body=asdasd
}