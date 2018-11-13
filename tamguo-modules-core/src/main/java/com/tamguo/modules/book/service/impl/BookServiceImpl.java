package com.tamguo.modules.book.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.book.dao.BookMapper;
import com.tamguo.modules.book.model.BookEntity;
import com.tamguo.modules.book.service.BookService;

@Service(value="bbookServiceImpl")
public class BookServiceImpl extends ServiceImpl<BookMapper, BookEntity> implements BookService{

}
