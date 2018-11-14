package com.tamguo.modules.book.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.book.dao.BookCategoryMapper;
import com.tamguo.modules.book.model.BookCategoryEntity;
import com.tamguo.modules.book.service.IBookCategoryService;

@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategoryEntity> implements IBookCategoryService{

}
