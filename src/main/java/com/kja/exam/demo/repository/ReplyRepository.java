package com.kja.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kja.exam.demo.vo.Reply;

@Mapper
public interface ReplyRepository {
	
	/* 댓글 생성 */
	@Insert("""
			INSERT INTO reply
			SET regDate = NOW(),
			updateDate = NOW(),
			memberId = #{memberId},
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			body = #{body}
			""")
	void writeReply(int memberId, String relTypeCode, int relId, String body);

	@Select("""
			SELECT LAST_INSERT_ID()
			""")
	int getLastInsertId();

	/* 댓글리스트 불러오기 */
	@Select("""
			SELECT R.*,
			M.nickname AS extra__writerName
			FROM reply AS R
			LEFT JOIN `member` AS M
			ON R.memberId = M.id
			WHERE R.relTypeCode = #{relTypeCode}
			AND R.relId = #{relId}
			ORDER BY R.id DESC
			""")
	List<Reply> getForPrintReplies(String relTypeCode, int relId);
	
	/* 업데이트된 댓글 불러오기*/
	@Select("""
			SELECT R.*,
			M.nickname AS extra__writerName
			FROM reply AS R
			LEFT JOIN `member` AS M
			ON R.memberId = M.id
			WHERE R.id = #{id}
			""")
	Reply getForPrintReply(int id);

	/* 댓글 불러오기*/
	@Select("""
			SELECT R.*
			FROM reply AS R
			WHERE R.id = #{id}
			""")
	Reply getReply(int id);

	@Delete("""
			DELETE FROM reply
			WHERE id = #{id}
			""")
	void deleteReply(int id);

	@Update("""
			UPDATE reply
			SET updateDate = NOW(),
			`body` = #{body}
			WHERE id = #{id}
			""")
	void modifyReply(int id, String body);
}