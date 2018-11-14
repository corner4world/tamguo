package com.tamguo.modules.book.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * Entity - 图片
 * 
 * @author tamguo
 *
 */
@TableName(value="b_file_entity")
public class FileEntity {

    @TableId("file_id")
	private String fileId;
    
	private String fileMd5;
	private String filePath;
	private String fileContentType;
	private String fileExtension;
	private Long fileSize;
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileMd5() {
		return fileMd5;
	}
	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getFileUrl() {
		return getFilePath();
	}
	
	public String getId() {
		return getFileId();
	}
}
