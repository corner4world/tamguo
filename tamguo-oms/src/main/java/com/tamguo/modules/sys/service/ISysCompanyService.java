package com.tamguo.modules.sys.service;

import java.util.List;

import com.tamguo.modules.sys.model.SysCompanyEntity;

public interface ISysCompanyService {
	
	/** 公司属性结构*/
	List<SysCompanyEntity> treeData();
	
}
