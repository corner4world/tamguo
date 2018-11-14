package com.tamguo.modules.book.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.book.dao.FileUploadMapper;
import com.tamguo.modules.book.model.FileUploadEntity;
import com.tamguo.modules.book.service.IFileUploadService;

@Service
public class FileUploadServiceImpl extends ServiceImpl<FileUploadMapper, FileUploadEntity> implements IFileUploadService{

}
