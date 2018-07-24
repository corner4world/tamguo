package com.tamguo.modules.sys.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.sys.model.SysUserEntity;
import com.tamguo.modules.sys.model.condition.SysUserCondition;

public interface ISysUserService {
	
	public SysUserEntity queryByLoginCode(String loginCode);
	
	public Page<SysUserEntity> listData(SysUserCondition condition);
}
