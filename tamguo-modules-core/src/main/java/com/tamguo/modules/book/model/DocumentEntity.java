package com.tamguo.modules.book.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.modules.book.model.enums.DocumentStatusEnum;

@TableName(value="b_document")
public class DocumentEntity extends Model<DocumentEntity>{

	private static final long serialVersionUID = 1L;
	
	@TableId
	private String id;
	private String parentId;
	private String bookId;
	private String owner;
	private String name;
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private DocumentStatusEnum status;
	@TableField(value="is_open")
	private String isOpen;
	private Date createDate;
	private Date updateDate;
	
	private String content;
	private String markdown;
	
	@TableField(exist=false)
	private Integer level;
	@TableField(exist=false)
	private String rootId;
	@TableField(exist=false)
	private boolean leaf;
	@TableField(exist=false)
	private List<DocumentEntity> children;
	@TableField(exist=false)
	private String cover; 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMarkdown() {
		return markdown;
	}
	public void setMarkdown(String markdown) {
		this.markdown = markdown;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getRootId() {
		return rootId;
	}
	public void setRootId(String rootId) {
		this.rootId = rootId;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public List<DocumentEntity> getChildren() {
		return children;
	}
	public void setChildren(List<DocumentEntity> children) {
		this.children = children;
	}
	@Override
	protected Serializable pkVal() {
		return getId();
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public DocumentStatusEnum getStatus() {
		return status;
	}
	public void setStatus(DocumentStatusEnum status) {
		this.status = status;
	}
	
}
