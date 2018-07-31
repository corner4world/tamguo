package com.tamguo.modules.sys.model;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value="sys_user_post")
public class SysUserPostEntity {

	private String userCode;
	private String postCode;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
}
