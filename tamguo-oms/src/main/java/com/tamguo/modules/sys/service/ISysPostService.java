package com.tamguo.modules.sys.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.sys.model.SysPostEntity;
import com.tamguo.modules.sys.model.condition.SysPostCondition;

public interface ISysPostService extends IService<SysPostEntity>{

	/** 查询列表*/
	Page<SysPostEntity> listData(SysPostCondition condition);

	/** 添加数据*/
	void add(SysPostEntity post);

	/** 修改数据*/
	void update(SysPostEntity post);

}
