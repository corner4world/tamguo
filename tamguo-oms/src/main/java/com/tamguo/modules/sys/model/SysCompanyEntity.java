package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.modules.sys.model.enums.SysCompanyStatusEnum;

/**
 * The persistent class for the sys_company database table.
 * 
 */
@TableName(value="sys_company")
public class SysCompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String fullName;
	private String type;
	private String remarks;
	private String parentId;
	private Boolean isLeaf;
	private Integer treeLevel;
	private Date updateDate;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private SysCompanyStatusEnum status;
	
	@TableField(exist=false)
	private String parentName;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getTreeLevel() {
		return treeLevel;
	}
	public void setTreeLevel(Integer treeLevel) {
		this.treeLevel = treeLevel;
	}
	public Boolean getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public SysCompanyStatusEnum getStatus() {
		return status;
	}
	public void setStatus(SysCompanyStatusEnum status) {
		this.status = status;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}