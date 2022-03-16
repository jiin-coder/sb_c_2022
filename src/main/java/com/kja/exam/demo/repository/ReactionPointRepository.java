package com.kja.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {

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
}
