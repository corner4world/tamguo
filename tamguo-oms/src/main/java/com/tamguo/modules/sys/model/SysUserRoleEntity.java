package com.tamguo.modules.sys.model;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value="sys_user_role")
public class SysUserRoleEntity {

	private String userCode;
	private String roleCode;
	
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
}
