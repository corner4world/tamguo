package com.tamguo.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.sys.model.SysRoleEntity;
import com.tamguo.modules.sys.model.condition.SysRoleCondition;

public interface ISysRoleService extends IService<SysRoleEntity>{

	/** 列表查询*/
	Page<SysRoleEntity> listData(SysRoleCondition condition);

	/** 属性菜单*/
	Map<String, Object> menuTreeData(String roleCode);

	/** 分配功能权限*/
	void allowMenuPermission(SysRoleEntity role);

	/** 授权数据权限*/
	void allowDataScope(SysRoleEntity role);

	/** 角色树形结构*/
	List<SysRoleEntity> treeDate(String userType);

	/** 修改角色*/
	void update(SysRoleEntity role);

}
