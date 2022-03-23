package com.kja.exam.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kja.exam.demo.vo.Attr;
@Mapper

public interface AttrRepository {

	@Delete("""
			DELETE FROM attr
			WHERE relId = #{relId}
			AND relTypeCode = #{relTypeCode}
			AND typeCode = #{typeCode}
			AND type2Code = #{type2Code}
			""")
	int remove(String relTypeCode, int relId, String typeCode, String type2Code);


	@Insert("""
			INSERT INTO attr (
				regDate,
				updateDate,
				relTypeCode,
				relId,
				typeCode,
				type2Code,
				value,
				expireDate
			)
			VALUES
			(
				NOW(),
				NOW(),
				#{relTypeCode},
				#{relId},
				#{typeCode},
				#{type2Code},
				#{value},
				#{expireDate}
			)
			ON DUPLICATE KEY UPDATE
			updateDate = NOW(),
			`value` = #{value},
			expireDate = #{expireDate}
			""")
	int setValue(String relTypeCode, int relId, String typeCode, String type2Code, String value, String expireDate);

	
	@Select("""
			SELECT *
			FROM attr
			WHERE relId = #{relId}
			AND relTypeCode = #{relTypeCode}
			AND typeCode = #{typeCode}
			AND type2Code = #{type2Code}
			AND (expireDate >= NOW() OR expireDate IS NULL)
			""")
	Attr get(String relTypeCode, int relId, String typeCode, String type2Code);


	@Select("""

			SELECT value
			FROM attr
			WHERE relId = #{relId}
			AND relTypeCode = #{relTypeCode}
			AND typeCode = #{typeCode}
			AND type2Code = #{type2Code}
			AND (expireDate >= NOW() OR expireDate IS NULL)

			""")
	String getValue(String relTypeCode, int relId, String typeCode, String type2Code);
}