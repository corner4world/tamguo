package com.tamguo.modules.book.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.book.dao.FileEntityMapper;
import com.tamguo.modules.book.model.FileEntity;
import com.tamguo.modules.book.service.IFileEntityService;

@Service
public class FileEntityServiceImpl extends ServiceImpl<FileEntityMapper, FileEntity> implements IFileEntityService{

}
