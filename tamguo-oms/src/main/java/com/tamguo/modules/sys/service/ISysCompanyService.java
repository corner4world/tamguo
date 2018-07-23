package com.tamguo.modules.sys.service;

import java.util.List;

import com.tamguo.modules.sys.model.SysCompanyEntity;
import com.tamguo.modules.sys.model.condition.SysCompanyCondition;

public interface ISysCompanyService {
	
	/** 公司树形结构*/
	List<SysCompanyEntity> treeData();

	/** 查询公司列表*/
	List<SysCompanyEntity> listData(SysCompanyCondition condition);
	
}
