package com.tamguo.modules.book.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.modules.book.model.enums.BizTypeEnum;
import com.tamguo.modules.book.model.enums.FileUploadStatusEnum;

@TableName(value="b_file_upload")
public class FileUploadEntity extends Model<FileUploadEntity>{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String fileId;
	private String fileName;
	private String fileType;
	private String bizKey;	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private BizTypeEnum bizType;
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private FileUploadStatusEnum status;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private String remarks;
	
	@TableField(exist=false)
	private String filePath;
	@TableField(exist=false)
	private Long fileSize;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getBizKey() {
		return bizKey;
	}
	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	protected Serializable pkVal() {
		return getId();
	}
	public BizTypeEnum getBizType() {
		return bizType;
	}
	public void setBizType(BizTypeEnum bizType) {
		this.bizType = bizType;
	}
	public FileUploadStatusEnum getStatus() {
		return status;
	}
	public void setStatus(FileUploadStatusEnum status) {
		this.status = status;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
}
