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

	/* 게시물 작성 */
	public ResultData<Integer> writeArticle(int memberId, int boardId, String title, String body) {
		articleRepository.writeArticle(memberId, boardId, title, body);
		int id = articleRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), "id", id);
	}

	/* 게시글 페이징 */
	public List<Article> getForPrintArticles(int actorId, int boardId, String searchKeywordTypeCode, String searchKeyword, int itemsInAPage, int page) {
		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		
		List<Article> articles = articleRepository.getForPrintArticles(boardId, searchKeyword, searchKeywordTypeCode, limitStart, limitTake);

		for (Article article : articles) {
			updateForPrintData(actorId, article);
		}

		return articles;
	}

	/* 게시글 조회 (1개씩) */
	public Article getForPrintArticle(int actorId, int id) {
		Article article = articleRepository.getForPrintArticle(id);
		updateForPrintData(actorId, article);

		return article;
	}

	/* 게시글 수정 or 삭제 업데이트 메세지*/
	private void updateForPrintData(int actorId, Article article) {
		if (article == null) {
			return;
		}

		ResultData actorCanDeleteRd = actorCanDelete(actorId, article);
		article.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());

		ResultData actorCanModifyRd = actorCanModify(actorId, article);
		article.setExtra__actorCanModify(actorCanModifyRd.isSuccess());
	}

	/* 게시글 삭제 */
	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	/* 게시글 수정 확인 메시지 */
	public ResultData<Article> modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);

		Article article = getForPrintArticle(0, id);

		return ResultData.from("S-1", Ut.f("%d번 게시물이 수정되었습니다.", id), "article", article);

	}

	/* 게시글 수정 가능 확인 메세지 */
	public ResultData actorCanModify(int actorId, Article article) {
		if (article == null) {
			return ResultData.from("F-1", "권한이 없습니다.");
		}

		if (article.getMemberId() != actorId) {
			return ResultData.from("F-2", "권한이 없습니다.");
		}

		return ResultData.from("S-2", "게시물 수정이 가능합니다.");
	}

	/* 게시글 삭제 가능 확인 메세지 */
	public ResultData actorCanDelete(int actorId, Article article) {
		if (article == null) {
			return ResultData.from("F-1", "권한이 없습니다.");
		}

		if (article.getMemberId() != actorId) {
			return ResultData.from("F-2", "권한이 없습니다.");
		}

		return ResultData.from("S-2", "게시물 삭제가 가능합니다.");
	}

	/* 게시글 갯수 세기 */
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		return articleRepository.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
	}

	/* 게시글 조회수 증가 */
	public ResultData<Integer> increaseHitCount(int id) {
		int affectedRowsCount = articleRepository.increaseHitCount(id);
		
		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.", "affectedRowsCount", affectedRowsCount);
		}
		
		return ResultData.from("S-1", "조회수가 증가되었습니다.", "affectedRowsCount", affectedRowsCount);
	}

	/* 게시글 조회수 세기 */
	public int getArticleHitCount(int id) {
		return articleRepository.getArticleHitCount(id);
	}
	
	/* 좋아요 갯수 증가 메세지 */
	public ResultData increaseGoodReactionPoint(int relId) {
		int affectedRowsCount = articleRepository.increaseGoodReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "좋아요 수가 증가되었습니다.", "affectedRowsCount", affectedRowsCount);
	}

	/* 싫어요 갯수 증가 메세지 */
	public ResultData increaseBadReactionPoint(int relId) {
		int affectedRowsCount = articleRepository.increaseBadReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "싫어요 수가 증가되었습니다.", "affectedRowsCount", affectedRowsCount);
	}
	
	/* 좋아요 갯수 감소 메세지 */
	public ResultData decreaseGoodReactionPoint(int relId) {
		int affectedRowsCount = articleRepository.decreaseGoodReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "좋아요 수가 감소되었습니다.", "affectedRowsCount", affectedRowsCount);
	}

	/* 싫어요 갯수 감소 메세지 */
	public ResultData decreaseBadReactionPoint(int relId) {
		int affectedRowsCount = articleRepository.decreaseBadReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "싫어요 수가 감소되었습니다.", "affectedRowsCount", affectedRowsCount);
	}
	
	/* 게시글 불러오기*/
	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}
}