package com.tamguo.modules.sys.dao;

import java.util.List;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.config.dao.SuperMapper;
import com.tamguo.modules.sys.model.SysRoleEntity;
import com.tamguo.modules.sys.model.condition.SysRoleCondition;

public interface SysRoleMapper extends SuperMapper<SysRoleEntity>{

	List<SysRoleEntity> listData(SysRoleCondition condition, Page<SysRoleEntity> page);

}
