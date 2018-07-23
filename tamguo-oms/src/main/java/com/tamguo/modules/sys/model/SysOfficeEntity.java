package com.tamguo.modules.sys.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;
import com.tamguo.modules.sys.model.enums.SysOfficeStatusEnum;
import com.tamguo.modules.sys.model.enums.SysOfficeTypeEnum;

@TableName(value="sys_office")
public class SysOfficeEntity extends SuperEntity<SysOfficeEntity> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String parentId;
	private String name;
	private String fullName;
	private String officeCode;
	private Integer sorts;
	private SysOfficeTypeEnum sysOfficeTypeEnum;
	private String leader;
	private String tel;
	private String address;
	private String zipCode;
	private String email;
	private String remarks;
	private SysOfficeStatusEnum sysOfficeStatusEnum;
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	public Integer getSorts() {
		return sorts;
	}
	public void setSorts(Integer sorts) {
		this.sorts = sorts;
	}
	public SysOfficeTypeEnum getSysOfficeTypeEnum() {
		return sysOfficeTypeEnum;
	}
	public void setSysOfficeTypeEnum(SysOfficeTypeEnum sysOfficeTypeEnum) {
		this.sysOfficeTypeEnum = sysOfficeTypeEnum;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public SysOfficeStatusEnum getSysOfficeStatusEnum() {
		return sysOfficeStatusEnum;
	}
	public void setSysOfficeStatusEnum(SysOfficeStatusEnum sysOfficeStatusEnum) {
		this.sysOfficeStatusEnum = sysOfficeStatusEnum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
	
}
