package com.tamguo.modules.sys.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.sys.model.SysMenuEntity;
import com.tamguo.modules.sys.model.condition.SysMenuCondition;

public interface ISysMenuService extends IService<SysMenuEntity>{

	/** 列表数据*/
	List<SysMenuEntity> listData(SysMenuCondition condition);

	/** 树形结构*/
	JSONArray treeData(String excludeId);

	/** 新增菜单*/
	void save(SysMenuEntity menu);
	
}
