package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;


/**
 * The persistent class for the sys_menu database table.
 * 
 */
@TableName(value="sys_menu")
public class SysMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String menuCode;
	private String createBy;
	private Date createDate;
	private String isShow;
	private String menuColor;
	private String menuHref;
	private String menuIcon;
	private String menuName;
	private String menuTarget;
	private String menuType;
	private String moduleCodes;
	private String parentCode;
	private String parentCodes;
	private String permission;
	private String remarks;
	private String status;
	private String sysCode;
	private String treeLeaf;
	private BigDecimal treeLevel;
	private String treeNames;
	private BigDecimal treeSort;
	private String treeSorts;
	private String updateBy;
	private Date updateDate;
	private BigDecimal weight;

	public SysMenuEntity() {
	}

	public String getMenuCode() {
		return this.menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
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

	public String getIsShow() {
		return this.isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getMenuColor() {
		return this.menuColor;
	}

	public void setMenuColor(String menuColor) {
		this.menuColor = menuColor;
	}

	public String getMenuHref() {
		return this.menuHref;
	}

	public void setMenuHref(String menuHref) {
		this.menuHref = menuHref;
	}

	public String getMenuIcon() {
		return this.menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuTarget() {
		return this.menuTarget;
	}

	public void setMenuTarget(String menuTarget) {
		this.menuTarget = menuTarget;
	}

	public String getMenuType() {
		return this.menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getModuleCodes() {
		return this.moduleCodes;
	}

	public void setModuleCodes(String moduleCodes) {
		this.moduleCodes = moduleCodes;
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

	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSysCode() {
		return this.sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getTreeLeaf() {
		return this.treeLeaf;
	}

	public void setTreeLeaf(String treeLeaf) {
		this.treeLeaf = treeLeaf;
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

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	// grid tree
	public String getId() {
		return getMenuCode();
	}

}