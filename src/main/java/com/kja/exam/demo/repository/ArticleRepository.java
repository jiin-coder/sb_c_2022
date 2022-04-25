package com.kja.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kja.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	public void writeArticle(@Param("memberId") int memberId, @Param("boardId") int boardId,
			@Param("title") String title, @Param("body") String body);

	/* 게시글 가져오기 */
	@Select("""
			<script>
			SELECT A.*,
			M.nickname AS extra__writerName
			FROM article AS A
			LEFT JOIN `member` AS M
			ON A.memberId = M.id
			WHERE 1
			AND A.id = #{id}
			</script>
			""")
	public Article getForPrintArticle(@Param("id") int id);

	/* 게시글 삭제 */
	public void deleteArticle(@Param("id") int id);

	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	@Select("""
			<script>
			SELECT A.*,
			M.nickname AS extra__writerName
			FROM article AS A
			LEFT JOIN `member` AS M
			ON A.memberId = M.id
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'">
						AND A.title LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'body'">
						AND A.body LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<otherwise>
						AND (
							A.title LIKE CONCAT('%', #{searchKeyword}, '%')
							OR
							A.body LIKE CONCAT('%', #{searchKeyword}, '%')
						)
					</otherwise>
				</choose>
			</if>
			ORDER BY A.id DESC
			<if test="limitTake != -1">
				LIMIT #{limitStart}, #{limitTake}
			</if>
			</script>
			""")
	public List<Article> getForPrintArticles(int boardId, String searchKeyword, String searchKeywordTypeCode,
			int limitStart, int limitTake);

	public int getLastInsertId();

	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM article AS A
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'">
						AND A.title LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'body'">
						AND A.body LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<otherwise>
						AND (
							A.title LIKE CONCAT('%', #{searchKeyword}, '%')
							OR
							A.body LIKE CONCAT('%', #{searchKeyword}, '%')
						)
					</otherwise>
				</choose>
			</if>
			</script>
			""")
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword);

	@Update("""
			<script>
			UPDATE article
			SET hitCount = hitCount + 1
			WHERE id = #{id}
			</script>
			""")
	public int increaseHitCount(int id);

	@Select("""
			<script>
			SELECT hitCount
			FROM article
			WHERE id = #{id}
			</script>
			""")
	public int getArticleHitCount(int id);

	@Update("""
			<script>
			UPDATE article
			SET goodReactionPoint = goodReactionPoint + 1
			WHERE id = #{id}
			</script>
			""")
	public int increaseGoodReactionPoint(int id);

	@Update("""
			<script>
			UPDATE article
			SET badReactionPoint = badReactionPoint + 1
			WHERE id = #{id}
			</script>
			""")
	public int increaseBadReactionPoint(int id);
	
	@Update("""
			<script>
			UPDATE article
			SET goodReactionPoint = goodReactionPoint - 1
			WHERE id = #{id}
			</script>
			""")
	public int decreaseGoodReactionPoint(int id);

	@Update("""
			<script>
			UPDATE article
			SET badReactionPoint = badReactionPoint - 1
			WHERE id = #{id}
			</script>
			""")
	public int decreaseBadReactionPoint(int id);
	
	@Select("""
			<script>
			SELECT *
			FROM article
			WHERE id = #{id}
			</script>
			""")
	public Article getArticle(int id);

}