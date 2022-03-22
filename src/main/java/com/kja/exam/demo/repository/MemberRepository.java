package com.kja.exam.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kja.exam.demo.vo.Member;

@Mapper
public interface MemberRepository {
	@Insert("""
			INSERT INTO `member`
			SET regDate = NOW(),
			updateDate = NOW(),
			loginId = #{loginId},
			loginPw = #{loginPw},
			`name` = #{name},
			nickname = #{nickname},
			cellphoneNo = #{cellphoneNo},
			email = #{email}
						""")
	void join(@Param("loginId") String loginId, @Param("loginPw") String loginPw, @Param("name") String name,
			@Param("nickname") String nickname, @Param("cellphoneNo") String cellphoneNo, @Param("email") String email);
	
	@Select("SELECT LAST_INSERT_ID()")
	int getLastInsertId();
	
	
	@Select("""
			SELECT *
			FROM `member` AS M
			WHERE M.id = #{id}
			""")
	Member getMemberById(@Param("id") int id);

	@Select("""
			SELECT *
			FROM `member` AS M
			WHERE M.loginId = #{loginId}
			""")
	Member getMemberByLoginId(@Param("loginId") String loginId);

	
	@Select("""
			SELECT *
			FROM `member` AS M
			WHERE M.name = #{name}
			AND M.email = #{email}
			
			""")
	Member getMemberByNameAndEmail(@Param("name") String name, @Param("email") String email);
	
	@Select("""
			<script>
			UPDATE `member`
			<set>
				updateDate = NOW(),
				<if test="loginPw != null">
				loginPw = #{loginPw},
				</if>
				<if test="name != null">
				name = #{name},
				</if>
				<if test="nickname != null">
				nickname = #{nickname},
				</if>
				<if test="email != null">
				email = #{email},
				</if>
				<if test="cellphoneNo != null">
				cellphoneNo = #{cellphoneNo},
				</if>
			</set>
			WHERE id = #{id}
			</script>
			""")
	void modify(int id, String loginPw, String name, String nickname, String email, String cellphoneNo);
}