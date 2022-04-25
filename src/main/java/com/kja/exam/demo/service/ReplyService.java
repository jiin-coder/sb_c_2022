package com.kja.exam.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kja.exam.demo.repository.ReplyRepository;
import com.kja.exam.demo.util.Ut;
import com.kja.exam.demo.vo.Member;
import com.kja.exam.demo.vo.Reply;
import com.kja.exam.demo.vo.ResultData;

@Service
public class ReplyService {
	private ReplyRepository replyRepository;

	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}

	/* 댓글 생성 */
	public ResultData<Integer> writeReply(int actorId, String relTypeCode, int relId, String body) {
		replyRepository.writeReply(actorId, relTypeCode, relId, body);
		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 댓글이 생성되었습니다.", id), "id", id);
	}
	
	/* 댓글리스트 가져오기 */
	public List<Reply> getForPrintReplies(Member actor, String relTypeCode, int relId) {
		List<Reply> replies = replyRepository.getForPrintReplies(relTypeCode, relId);

		for (Reply reply : replies) {
			updateForPrintData(actor, reply);
		}

		return replies;
	}

	
	/* 게시물 하나당 actorCanDelete와 actorCanModify를 담을 수 있도록  */
	private void updateForPrintData(Member actor, Reply reply) {
		if (reply == null) {
			return;
		}

		ResultData actorCanDeleteRd = actorCanDelete(actor, reply);
		reply.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());

		ResultData actorCanModifyRd = actorCanModify(actor, reply);
		reply.setExtra__actorCanModify(actorCanModifyRd.isSuccess());
	}

	/* 댓글 삭제가 가능한지 확인 */
	private ResultData actorCanModify(Member actor, Reply reply) {
		if (actor == null) {
			return ResultData.from("F-1", "댓글이 존재하지 않습니다.");
		}

		if (reply.getMemberId() != actor.getId()) {
			return ResultData.from("F-2", "권한이 없습니다.");
		}

		return ResultData.from("S-1", "댓글 삭제가 가능합니다.");
	}

	/* 댓글 수정이 가능한지 확인 */
	private ResultData actorCanDelete(Member actor, Reply reply) {
		if (actor == null) {
			return ResultData.from("F-1", "댓글이 존재하지 않습니다.");
		}

		if (reply.getMemberId() != actor.getId()) {
			return ResultData.from("F-2", "권한이 없습니다.");
		}

		return ResultData.from("S-1", "댓글 수정이 가능합니다.");
	}
	
	
	/* 업데이트된 댓글들로 불러오기*/
	public Reply getForPrintReply(Member actor, int id) {
		Reply reply = replyRepository.getForPrintReply(id);

		updateForPrintData(actor, reply);

		return reply;
	}

	/* 댓글 수정 완료메세지*/
	public ResultData<Integer> modifyReplyRd(int id, String body) {
		replyRepository.modifyReply(id, body);

		return ResultData.from("S-1", Ut.f("%d번 댓글을 수정하였습니다.", id));
	}

	/* 댓글 삭제 완료메세지*/
	public ResultData deleteReply(int id) {
		replyRepository.deleteReply(id);

		return ResultData.from("S-1", Ut.f("%d번 댓글을 삭제하였습니다.", id));
	}

	
}