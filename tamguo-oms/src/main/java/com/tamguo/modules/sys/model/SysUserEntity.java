package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;
import com.tamguo.modules.sys.model.enums.SysUserStatusEnum;


/**
 * The persistent class for the reaps_sys_user database table.
 * 
 */
@TableName(value="sys_user")
public class SysUserEntity extends SuperEntity<SysUserEntity> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userName;
	private String nickName;
	private String name;
	private String mobile;
	private String email;
	private String password;
	private String roleIds;
	private String companyId;
	private Date createDate;
	private Date updateDate;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private SysUserStatusEnum status;
	
	private String safeKeyValue;
	private Long createTime;

	@TableField(exist=false)
	private List<String> roleIdList;
	
	@TableField(exist=false)
	private String companyName;
	
	public SysUserEntity() {
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleIds() {
		return this.roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public List<String> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public String getSafeKeyValue() {
		return safeKeyValue;
	}

	public void setSafeKeyValue(String safeKeyValue) {
		this.safeKeyValue = safeKeyValue;
	}

	public SysUserStatusEnum getStatus() {
		return status;
	}

	public void setStatus(SysUserStatusEnum status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


}