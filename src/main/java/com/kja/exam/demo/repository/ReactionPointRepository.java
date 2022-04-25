package com.kja.exam.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {

	/* 0 or 1로 결과 반환 : 리액션 가능한지 확인*/
	@Select("""
			<script>
			SELECT IFNULL(SUM(RP.point), 0) AS s
			FROM reactionPoint AS RP
			WHERE RP.relTypeCode = #{relTypeCode}
			AND RP.relId = #{relId}
			AND RP.memberId = #{memberId}
			</script>
			""")
	int getSumReactionPointByMemberId(String relTypeCode, int relId, int memberId);

	/* 좋아요 리액션 포인트 추가 : +1 */
	@Insert("""
			INSERT INTO reactionPoint
			SET regDate = NOW(),
			updateDate = NOW(),
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			memberId = #{memberId},
			`point` = 1
			""")
	void addGoodReactionPoint(int memberId, String relTypeCode, int relId);

	/* 싫어요 리액션 포인트 추가 : +1 */
	@Insert("""
			INSERT INTO reactionPoint
			SET regDate = NOW(),
			updateDate = NOW(),
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			memberId = #{memberId},
			`point` = -1
			""")
	void addBadReactionPoint(int memberId, String relTypeCode, int relId);
	
	/* 리액션 포인트 제거 */
	@Delete("""
			DELETE FROM reactionPoint
			WHERE relTypeCode = #{relTypeCode}
			AND relId = #{relId}
			AND memberId = #{memberId}
			""")
	void deleteReactionPoint(int memberId, String relTypeCode, int relId);
}
