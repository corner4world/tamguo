package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.tamguo.modules.sys.model.enums.SysPostStatusEnum;


/**
 * The persistent class for the sys_post database table.
 * 
 */
@TableName(value="sys_post")
public class SysPostEntity extends Model<SysPostEntity> implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private String postCode;
	private String corpCode;
	private String corpName;
	@TableField(fill = FieldFill.INSERT)
	private String createBy;
	@TableField(fill = FieldFill.INSERT)
	private Date createDate;
	private String postName;
	private BigDecimal postSort;
	private String postType;
	private String remarks;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private SysPostStatusEnum status;
	@TableField(fill = FieldFill.INSERT)
	private String updateBy;
	@TableField(fill = FieldFill.INSERT)
	private Date updateDate;

	public SysPostEntity() {
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
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

	public String getPostName() {
		return this.postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public BigDecimal getPostSort() {
		return this.postSort;
	}

	public void setPostSort(BigDecimal postSort) {
		this.postSort = postSort;
	}

	public String getPostType() {
		return this.postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public SysPostStatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(SysPostStatusEnum status) {
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

	@Override
	protected Serializable pkVal() {
		return getPostCode();
	}

}