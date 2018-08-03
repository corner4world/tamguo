package com.tamguo.modules.tiku.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tamguo.modules.tiku.model.BookEntity;
import com.tamguo.modules.tiku.model.condition.BookCondition;

public interface BookMapper extends BaseMapper<BookEntity>{

	List<BookEntity> listData(Pagination page , BookCondition condition);
}
