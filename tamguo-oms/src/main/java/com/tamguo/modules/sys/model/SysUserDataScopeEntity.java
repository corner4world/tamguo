package com.tamguo.modules.sys.model;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value="sys_user_data_scope")
public class SysUserDataScopeEntity {

	private String userCode;
	private String ctrlType;
	private String ctrlData;
	private String ctrlPermi;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
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
