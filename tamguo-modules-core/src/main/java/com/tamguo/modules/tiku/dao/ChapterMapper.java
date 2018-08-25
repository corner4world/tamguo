package com.tamguo.modules.tiku.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tamguo.config.dao.SuperMapper;
import com.tamguo.modules.tiku.model.ChapterEntity;

public interface ChapterMapper extends SuperMapper<ChapterEntity>{

	@Select("select * from tiku_chapter where parent_code = #{parentCode} and id = (select min(id) from tiku_chapter where id > #{id})")
	ChapterEntity selectNextChapter(@Param(value="parentCode")String parentCode , @Param(value="id")String id);

}
