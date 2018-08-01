package com.tamguo.modules.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.sys.model.SysUserDataScopeEntity;
import com.tamguo.modules.sys.model.SysUserEntity;
import com.tamguo.modules.sys.model.SysUserRoleEntity;
import com.tamguo.modules.sys.model.condition.SysUserCondition;
import com.tamguo.modules.sys.model.enums.SysUserMgrTypeEnum;
import com.tamguo.modules.sys.utils.Result;

public interface ISysUserService extends IService<SysUserEntity>{
	
	public SysUserEntity queryByLoginCode(String loginCode);
	
	public Page<SysUserEntity> listData(SysUserCondition condition);
	
	/** 用户岗位*/
	public String queryUserPostByUserCode(String userCode);

	/** 检查登录账号*/
	public Boolean checkLoginCode(String oldLoginCode , String loginCode);

	/** 更新用户信息*/
	public void update(SysUserEntity user);

	/** 添加用户信息*/
	public void save(SysUserEntity user);

	/** 分配角色*/
	public void allowUserRole(SysUserEntity user);
	
	/** 获取用户角色*/
	public List<SysUserRoleEntity> findUserRole(String userCode);

	/** 用户数据权限*/
	public List<SysUserDataScopeEntity> selectUserDataScope(String userCode);

	/** 保存用户数据权限*/
	public void saveUserDataScope(SysUserEntity user);
	
	/** 保存用户数据权限*/
	public void saveUserDataScope(SysUserEntity user , SysUserMgrTypeEnum mgrType);

	/** 停用账号*/
	public Result disable(String userCode);

	/** 激活账号*/
	public Result enable(String userCode);

	/** 删除用户*/
	public Result delete(String userCode);
}
