package com.kja.exam.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
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
	public ResultData doAdd(String title, String body) {
		if ( Ut.empty(title) ) {
			return ResultData.from("F-1", "title(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(body) ) {
			return ResultData.from("F-2", "body(을)를 입력해주세요.");
		}
		
		
		ResultData writeArticleRd  = articleService.writeArticle(title, body);
		int id = (int)writeArticleRd.getData1();
		
		Article article = articleService.getArticle(id);

		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), article);
	}
	// http://localhost:8011/usr/article/doAdd?title=제목1&body=1


	@RequestMapping("/usr/article/getArticles") 
	@ResponseBody
	public ResultData getArticles() {
		List<Article> articles = articleService.getArticles();
		
		return ResultData.from("S-1","게시물 리스트입니다.", articles);
	}
	// http://localhost:8011/usr/article/getArticles
	

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(int id) {
		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), article);
	}
	// http://localhost:8011/usr/article/getArticle?id=6


	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = articleService.getArticle(id);

		if (article == null) {
			return id + "번 게시물이 존재하지 않습니다.";
		}

		articleService.deleteArticle(id);

		return id + "번 게시물을 삭제하였습니다.";
	}
	// http://localhost:8011/usr/article/doDelete?id=1


	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		Article article = articleService.getArticle(id);

		if (article == null) {
			return id + "번 게시물이 존재하지 않습니다.";
		}

		articleService.modifyArticle(id, title, body);

		return id + "번 게시물을 수정하였습니다.";
	}
	// http://localhost:8011/usr/article/doModify?id=1&title=asdasdas&body=asdasd
}