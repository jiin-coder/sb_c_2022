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

	public ResultData<Integer> writeReply(int actorId, String relTypeCode, int relId, String body) {
		replyRepository.writeReply(actorId, relTypeCode, relId, body);
		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 댓글이 생성되었습니다.", id), "id", id);
	}
	
	public List<Reply> getForPrintReplies(Member actor, String relTypeCode, int relId) {
		return replyRepository.getForPrintReplies(relTypeCode, relId);
	}
}