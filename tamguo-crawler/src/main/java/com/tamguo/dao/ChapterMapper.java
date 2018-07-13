package com.tamguo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tamguo.config.dao.SuperMapper;
import com.tamguo.model.ChapterEntity;

public interface ChapterMapper extends SuperMapper<ChapterEntity>{

	List<ChapterEntity> queryList(Pagination questionPage);

	Integer queryCount(@Param(value="uid")String uid);
}
