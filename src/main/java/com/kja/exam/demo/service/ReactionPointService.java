package com.kja.exam.demo.service;

import org.springframework.stereotype.Service;

import com.kja.exam.demo.repository.ReactionPointRepository;
import com.kja.exam.demo.vo.ResultData;

@Service
public class ReactionPointService {
	private ReactionPointRepository reactionPointRepository;
	private ArticleService articleService;

	public ReactionPointService(ReactionPointRepository reactionPointRepository, ArticleService articleService) {
		this.reactionPointRepository = reactionPointRepository;
		this.articleService = articleService;
	}

	/* 좋아요/ 싫어요 가능한지 확인 메세지*/
	public ResultData actorCanMakeReactionPoint(int actorId, String relTypeCode, int relId) {
		if (actorId == 0) {
			return ResultData.from("F-1", "로그인 후 이용해주세요.");
		}

		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(relTypeCode, relId,
				actorId);

		if (sumReactionPointByMemberId != 0) {
			return ResultData.from("F-2", "리액션이 불가능합니다.", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}

		return ResultData.from("S-1", "리액션이 가능합니다.", "sumReactionPointByMemberId", sumReactionPointByMemberId);
	}

	/* 좋아요 증가 (최대 1번) 및 메세지 */
	public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.increaseGoodReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", "좋아요 처리 되었습니다");
	}

	/* 싫어요 증가 (최대 1번) 및 메세지 */
	public ResultData addBadReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.increaseBadReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 처리 되었습니다");
	}

	/* 좋아요 취소 메세지*/
	public ResultData deleteGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.deleteReactionPoint(actorId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.decreaseGoodReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", "좋아요가 취소처리 되었습니다");
	}

	/* 싫어요 취소 및 메세지*/
	public ResultData deleteBadReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.deleteReactionPoint(actorId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.decreaseBadReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 취소처리 되었습니다");
	}
}