package com.tamguo.modules.sys.dao;

import java.util.List;
import com.tamguo.config.dao.SuperMapper;
import com.tamguo.modules.sys.model.SysMenuEntity;
import com.tamguo.modules.sys.model.condition.SysMenuCondition;

public interface SysMenuMapper extends SuperMapper<SysMenuEntity>{

	List<SysMenuEntity> listData(SysMenuCondition condition);

	List<SysMenuEntity> selectMenuByRoleId(String roleCode);

}
