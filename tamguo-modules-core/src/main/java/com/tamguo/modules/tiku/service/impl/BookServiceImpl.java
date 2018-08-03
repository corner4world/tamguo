package com.tamguo.modules.tiku.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.tiku.dao.BookMapper;
import com.tamguo.modules.tiku.model.BookEntity;
import com.tamguo.modules.tiku.service.IBookService;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, BookEntity> implements IBookService{

}
