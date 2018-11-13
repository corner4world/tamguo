package com.tamguo.modules.tiku.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tamguo.modules.tiku.model.KnowPointEntity;
import com.tamguo.modules.tiku.model.condition.BookCondition;

public interface KnowPointMapper extends BaseMapper<KnowPointEntity>{

	List<KnowPointEntity> listData(Pagination page , BookCondition condition);
}
