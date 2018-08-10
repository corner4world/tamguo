package com.tamguo.dao;

import org.apache.ibatis.annotations.Param;
import com.tamguo.config.dao.SuperMapper;
import com.tamguo.model.ChapterEntity;

public interface ChapterMapper extends SuperMapper<ChapterEntity>{

	Integer queryCount(@Param(value="uid")String uid);
}
