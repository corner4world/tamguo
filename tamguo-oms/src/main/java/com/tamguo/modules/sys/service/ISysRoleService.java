package com.tamguo.modules.sys.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.model.SysRoleEntity;
import com.tamguo.modules.sys.model.condition.SysRoleCondition;

public interface ISysRoleService {

	Page<SysRoleEntity> listData(SysRoleCondition condition);

}
