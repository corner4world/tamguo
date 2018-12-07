package com.tamguo.modules.book.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.book.dao.DocumentMapper;
import com.tamguo.modules.book.model.DocumentEntity;
import com.tamguo.modules.book.model.enums.DocumentStatusEnum;
import com.tamguo.modules.book.service.IDocumentService;

@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, DocumentEntity> implements IDocumentService{

	@Transactional(readOnly=false)
	@Override
	public void modify(DocumentEntity document) {
		DocumentEntity entity = this.selectById(document.getId());
		if("yes".equals(document.getCover())) {
			// 覆盖修改
			entity.setContent(document.getContent());
			entity.setMarkdown(document.getMarkdown());
			entity.setStatus(DocumentStatusEnum.NORMAL);
			this.updateById(entity);
		} else {
			String content = entity.getContent();
			String markdown = entity.getMarkdown();
			
			// 更新内容
			entity.setContent(document.getContent());
			entity.setMarkdown(document.getMarkdown());
			this.updateById(entity);
			
			// 新增历史
			document.setId(null);
			document.setContent(content);
			document.setMarkdown(markdown);
			document.setBookId(entity.getBookId());
			document.setCreateDate(new Date());
			document.setIsOpen(entity.getIsOpen());
			document.setName(entity.getName());
			document.setOwner(entity.getOwner());
			document.setParentId(entity.getParentId());
			document.setUpdateDate(new Date());
			document.setStatus(DocumentStatusEnum.HISTORY);
			document.setBatchNo(entity.getBatchNo());
			this.insert(document);
			
		}
	}

	@SuppressWarnings("static-access")
	@Transactional(readOnly=false)
	@Override
	public void create(DocumentEntity document) {
		if(StringUtils.isEmpty(document.getId())) {
			document.setStatus(DocumentStatusEnum.NORMAL);
			document.setCreateDate(new Date());
			document.setUpdateDate(new Date());
			document.setOwner("system");
			document.setBatchNo(new com.baomidou.mybatisplus.toolkit.IdWorker().getIdStr());
			this.insert(document);
		}else {
			DocumentEntity entity = this.selectById(document.getId());
			entity.setName(document.getName());
			entity.setIsOpen(document.getIsOpen());
			this.updateById(entity);
		}
	}

}
