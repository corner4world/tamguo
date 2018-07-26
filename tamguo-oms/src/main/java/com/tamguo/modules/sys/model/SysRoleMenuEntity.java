package com.tamguo.modules.sys.model;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value="sys_role_menu")
public class SysRoleMenuEntity {

	private String roleCode;
	private String menuCode;
	
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	
}
