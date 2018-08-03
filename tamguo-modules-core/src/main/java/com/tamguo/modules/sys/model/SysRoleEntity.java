package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;


/**
 * The persistent class for the sys_role database table.
 * 
 */
@TableName(value="sys_role")
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private String roleCode;
	private String corpCode;
	private String corpName;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String createBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createDate;
	private String dataScope;
	private String isSys;
	private String remarks;
	private String roleName;
	private BigDecimal roleSort;
	private String roleType;
	private String status;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;
	private String userType;
	
	@TableField(exist=false)
	private String roleMenuListJson;
	@TableField(exist=false)
	private String roleDataScopeListJson;

	public SysRoleEntity() {
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getCorpCode() {
		return this.corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public String getCorpName() {
		return this.corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
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

	public String getDataScope() {
		return this.dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public String getIsSys() {
		return this.isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public BigDecimal getRoleSort() {
		return this.roleSort;
	}

	public void setRoleSort(BigDecimal roleSort) {
		this.roleSort = roleSort;
	}

	public String getRoleType() {
		return this.roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getId() {
		return getRoleCode();
	}

	public String getRoleMenuListJson() {
		return roleMenuListJson;
	}

	public void setRoleMenuListJson(String roleMenuListJson) {
		this.roleMenuListJson = roleMenuListJson;
	}

	public String getRoleDataScopeListJson() {
		return roleDataScopeListJson;
	}

	public void setRoleDataScopeListJson(String roleDataScopeListJson) {
		this.roleDataScopeListJson = roleDataScopeListJson;
	}

}