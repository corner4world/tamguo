package com.tamguo.modules.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.sys.model.SysCompanyEntity;
import com.tamguo.modules.sys.model.condition.SysCompanyCondition;

public interface ISysCompanyService extends IService<SysCompanyEntity>{
	
	/** 公司树形结构*/
	List<SysCompanyEntity> treeData(String excludeId);

	/** 查询公司列表*/
	List<SysCompanyEntity> listData(SysCompanyCondition condition);
	
	/** 根据ID查询公司*/
	SysCompanyEntity select(String id);
}
