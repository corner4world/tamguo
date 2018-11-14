package com.tamguo.modules.book.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value="b_book_category")
public class BookCategoryEntity extends Model<BookCategoryEntity>{

	private static final long serialVersionUID = 1L;
	
	@TableId
	private String id;
	private String parentId;
	private String parentIds;
	private String treeSort;
	private String treeSorts;
	private String treeLeaf;
	private String treeLevel;
	private String treeNames;
	private String name;
	private String seoTitle;
	private String seoKeywords;
	private String seoDescription;
	private Date createDate;
	private Date updateDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTreeSort() {
		return treeSort;
	}
	public void setTreeSort(String treeSort) {
		this.treeSort = treeSort;
	}
	public String getTreeSorts() {
		return treeSorts;
	}
	public void setTreeSorts(String treeSorts) {
		this.treeSorts = treeSorts;
	}
	public String getTreeLeaf() {
		return treeLeaf;
	}
	public void setTreeLeaf(String treeLeaf) {
		this.treeLeaf = treeLeaf;
	}
	public String getTreeLevel() {
		return treeLevel;
	}
	public void setTreeLevel(String treeLevel) {
		this.treeLevel = treeLevel;
	}
	public String getTreeNames() {
		return treeNames;
	}
	public void setTreeNames(String treeNames) {
		this.treeNames = treeNames;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getSeoKeywords() {
		return seoKeywords;
	}
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	@Override
	protected Serializable pkVal() {
		return getId();
	}
	
}
