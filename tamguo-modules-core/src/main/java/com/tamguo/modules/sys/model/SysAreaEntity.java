package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.tamguo.modules.sys.model.enums.SysAreaStatusEnum;


/**
 * The persistent class for the sys_area database table.
 * 
 */
@TableName(value="sys_area")
public class SysAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ROOT_AREA_CODE = "0";
	public static final String TREE_CODE_AREA_SEPARATE = ",";
	public static final String TREE_NAME_AREA_SEPARATE = "/";

	@TableId
	private String areaCode;
	private String areaName;
	private String areaType;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String createBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createDate;
	private String parentCode;
	private String parentCodes;
	private String remarks;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private SysAreaStatusEnum status;
	
	private Boolean treeLeaf;
	private BigDecimal treeLevel;
	private String treeNames;
	private BigDecimal treeSort;
	private String treeSorts;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;
	
	@TableField(exist=false)
	private List<SysAreaEntity> children;

	public SysAreaEntity() {
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaType() {
		return this.areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentCodes() {
		return this.parentCodes;
	}

	public void setParentCodes(String parentCodes) {
		this.parentCodes = parentCodes;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getTreeLevel() {
		return this.treeLevel;
	}

	public void setTreeLevel(BigDecimal treeLevel) {
		this.treeLevel = treeLevel;
	}

	public String getTreeNames() {
		return this.treeNames;
	}

	public void setTreeNames(String treeNames) {
		this.treeNames = treeNames;
	}

	public BigDecimal getTreeSort() {
		return this.treeSort;
	}

	public void setTreeSort(BigDecimal treeSort) {
		this.treeSort = treeSort;
	}

	public String getTreeSorts() {
		return this.treeSorts;
	}

	public void setTreeSorts(String treeSorts) {
		this.treeSorts = treeSorts;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getId() {
		return getAreaCode();
	}

	public Boolean getTreeLeaf() {
		return treeLeaf;
	}

	public void setTreeLeaf(Boolean treeLeaf) {
		this.treeLeaf = treeLeaf;
	}

	public SysAreaStatusEnum getStatus() {
		return status;
	}

	public void setStatus(SysAreaStatusEnum status) {
		this.status = status;
	}

	public List<SysAreaEntity> getChildren() {
		return children;
	}

	public void setChildren(List<SysAreaEntity> children) {
		this.children = children;
	}
	
	public String getValue() {
		return getAreaCode();
	}
	
	public String getLable() {
		return getAreaName();
	}
}