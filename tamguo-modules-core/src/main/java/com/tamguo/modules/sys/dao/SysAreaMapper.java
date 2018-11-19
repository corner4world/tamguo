package com.tamguo.modules.sys.dao;

import java.util.List;

import com.tamguo.config.dao.SuperMapper;
import com.tamguo.modules.sys.model.SysAreaEntity;
import com.tamguo.modules.sys.model.condition.SysAreaCondition;

public interface SysAreaMapper extends SuperMapper<SysAreaEntity>{

	List<SysAreaEntity> listData(SysAreaCondition condition);

}
