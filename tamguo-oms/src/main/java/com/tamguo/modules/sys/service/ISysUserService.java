package com.tamguo.modules.sys.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.sys.model.SysUserEntity;
import com.tamguo.modules.sys.model.condition.SysUserCondition;

public interface ISysUserService extends IService<SysUserEntity>{
	
	public SysUserEntity queryByLoginCode(String loginCode);
	
	public Page<SysUserEntity> listData(SysUserCondition condition);
	
	/** 用户岗位*/
	public String queryUserPostByUserCode(String userCode);

	/** 检查登录账号*/
	public Boolean checkLoginCode(String oldLoginCode , String loginCode);

	/** 更新用户信息*/
	public void update(SysUserEntity user);
}
