package com.tamguo.modules.tiku.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.tamguo.modules.tiku.model.enums.CourseStatusEnum;

@TableName(value="tiku_course")
public class CourseEntity {
	
	@TableId
	private String uid;
	private String subjectId;
	private String name;
	private Integer sort;
	private Integer questionNum;
	private Integer pointNum;
	private String remarks;
	
	@TableField(fill = FieldFill.INSERT)
	private String createBy;
	@TableField(fill = FieldFill.INSERT)
	private String updateBy;
	@TableField(fill = FieldFill.INSERT)
	private Date createDate;
	@TableField(fill = FieldFill.INSERT)
	private Date updateDate;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private CourseStatusEnum status;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}
	public Integer getPointNum() {
		return pointNum;
	}
	public void setPointNum(Integer pointNum) {
		this.pointNum = pointNum;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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
	public CourseStatusEnum getStatus() {
		return status;
	}
	public void setStatus(CourseStatusEnum status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
