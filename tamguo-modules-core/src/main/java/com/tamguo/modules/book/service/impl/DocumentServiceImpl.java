package com.tamguo.modules.book.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.book.dao.DocumentMapper;
import com.tamguo.modules.book.model.DocumentEntity;
import com.tamguo.modules.book.service.IDocumentService;

@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, DocumentEntity> implements IDocumentService{

}
