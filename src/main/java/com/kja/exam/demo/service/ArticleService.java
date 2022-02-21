package com.kja.exam.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kja.exam.demo.repository.ArticleRepository;
import com.kja.exam.demo.util.Ut;
import com.kja.exam.demo.vo.Article;
import com.kja.exam.demo.vo.ResultData;

@Service
public class ArticleService {
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public ResultData<Integer> writeArticle(int memberId, String title, String body) {
		articleRepository.writeArticle(memberId, title, body);
		int id = articleRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), "id", id);
	}

	public List<Article> getForPrintArticles(int actorId) {
		List<Article> articles = articleRepository.getForPrintArticles();

		for (Article article : articles) {
			updateForPrintData(actorId, article);
		}

		return articles;
	}

	public Article getForPrintArticle(int actorId, int id) {
		Article article = articleRepository.getForPrintArticle(id);
		updateForPrintData(actorId, article);

		return article;
	}
	
	private void updateForPrintData(int actorId, Article article) {
		if ( article == null ) {
			return;
		}
		
		ResultData actorCanDeleteRd = actorCanDelete(actorId, article);
		article.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public ResultData<Article> modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);

		Article article = getForPrintArticle(0, id);

		return ResultData.from("S-1", Ut.f("%d번 게시물이 수정되었습니다.", id), "article", article);

	}

	public ResultData actorCanModify(int actorId, Article article) {
		if (article == null) {
			return ResultData.from("F-1", "권한이 없습니다.");
		}

		if (article.getMemberId() != actorId) {
			return ResultData.from("F-2", "권한이 없습니다.");
		}

		return ResultData.from("S-2", "게시물 수정이 가능합니다.");
	}
	
	public ResultData actorCanDelete(int actorId, Article article) {
		if (article == null) {
			return ResultData.from("F-1", "권한이 없습니다.");
		}

		if (article.getMemberId() != actorId) {
			return ResultData.from("F-2", "권한이 없습니다.");
		}

		return ResultData.from("S-2", "게시물 삭제가 가능합니다.");
	}
}