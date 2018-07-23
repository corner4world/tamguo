package com.tamguo.modules.sys.dao;

import java.util.List;

import com.tamguo.config.dao.SuperMapper;
import com.tamguo.modules.sys.model.SysOfficeEntity;
import com.tamguo.modules.sys.model.condition.SysOfficeCondition;

public interface SysOfficeMapper extends SuperMapper<SysOfficeEntity>{

	List<SysOfficeEntity> listData(SysOfficeCondition condition);

}
