package com.tamguo.modules.book.service;

import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.book.model.DocumentEntity;

public interface IDocumentService extends IService<DocumentEntity>{

	/** 编辑文档*/
	void modify(DocumentEntity document);

	/** 创建文档 */
	void create(DocumentEntity document);

}
