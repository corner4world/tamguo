package com.tamguo.modules.tiku.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.tiku.model.KnowPointEntity;
import com.tamguo.modules.tiku.model.condition.BookCondition;

public interface IKnowPointService extends IService<KnowPointEntity>{

	Page<KnowPointEntity> listData(BookCondition condition);

	void save(KnowPointEntity book);

	void update(KnowPointEntity book);

	void delete(String id);

	void enable(String id);

	void disabled(String id);

	JSONArray treeData();

}
