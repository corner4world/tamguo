package com.tamguo.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.dao.BookMapper;
import com.tamguo.model.BookEntity;
import com.tamguo.service.IBookService;

@Service
public class BookService extends ServiceImpl<BookMapper, BookEntity> implements IBookService {

}
