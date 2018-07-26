package com.tamguo.modules.sys.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.sys.model.SysUserEntity;
import com.tamguo.modules.sys.model.condition.SysUserCondition;

public interface ISysUserService extends IService<SysUserEntity>{
	
	public SysUserEntity queryByLoginCode(String loginCode);
	
	public Page<SysUserEntity> listData(SysUserCondition condition);
}
