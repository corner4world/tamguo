package com.tamguo.modules.sys.dao;

import java.util.List;

import com.tamguo.config.dao.SuperMapper;
import com.tamguo.modules.sys.model.SysCompanyEntity;
import com.tamguo.modules.sys.model.condition.SysCompanyCondition;

public interface SysCompanyMapper extends SuperMapper<SysCompanyEntity>{

	List<SysCompanyEntity> listData(SysCompanyCondition condition);
	
	SysCompanyEntity selectByCode(String code);

}
