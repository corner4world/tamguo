package com.tamguo.modules.sys.model;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value="sys_role_data_scope")
public class SysRoleDataScopeEntity {
	
	private String roleCode;
	private String ctrlType;
	private String ctrlData;
	private String ctrlPermi;
	
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getCtrlType() {
		return ctrlType;
	}
	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType;
	}
	public String getCtrlData() {
		return ctrlData;
	}
	public void setCtrlData(String ctrlData) {
		this.ctrlData = ctrlData;
	}
	public String getCtrlPermi() {
		return ctrlPermi;
	}
	public void setCtrlPermi(String ctrlPermi) {
		this.ctrlPermi = ctrlPermi;
	}

}
