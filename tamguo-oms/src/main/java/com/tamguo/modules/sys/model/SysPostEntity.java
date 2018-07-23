package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;
import com.tamguo.modules.sys.model.enums.SysPostStatusEnum;
import com.tamguo.modules.sys.model.enums.SysPostTypeEnum;

@TableName(value="sys_post")
public class SysPostEntity extends SuperEntity<SysPostEntity> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String code;
	private SysPostTypeEnum postType;
	private Integer sorts;
	private String remarks;
	private Date createDate;
	private Date updateDate;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private SysPostStatusEnum status;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public SysPostTypeEnum getPostType() {
		return postType;
	}
	public void setPostType(SysPostTypeEnum postType) {
		this.postType = postType;
	}
	public Integer getSorts() {
		return sorts;
	}
	public void setSorts(Integer sorts) {
		this.sorts = sorts;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public SysPostStatusEnum getStatus() {
		return status;
	}
	public void setStatus(SysPostStatusEnum status) {
		this.status = status;
	}
	
}
