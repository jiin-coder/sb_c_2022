package com.kja.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kja.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	public void writeArticle(@Param("memberId") int memberId, @Param("title") String title, @Param("body") String body);

	public Article getArticle(@Param("id") int id);

	public void deleteArticle(@Param("id") int id);

	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	@Select("""
			SELECT A.*,
			M.nickname AS extra__writerName
			FROM article AS A
			LEFT JOIN member AS M
			ON A.memberId = M.id
			ORDER BY A.id DESC
			""")
	public List<Article> getArticles();

	public int getLastInsertId();
}