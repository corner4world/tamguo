package com.tamguo.modules.sys.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.model.SysUserEntity;
import com.tamguo.modules.sys.model.condition.SysUserCondition;

public interface ISysUserService {
	
	public SysUserEntity queryByUserName(String username);
	
	public Page<SysUserEntity> listData(SysUserCondition condition);
}
