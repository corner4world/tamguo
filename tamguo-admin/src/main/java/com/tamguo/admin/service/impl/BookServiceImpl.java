package com.tamguo.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.admin.dao.BookMapper;
import com.tamguo.admin.model.BookEntity;
import com.tamguo.admin.service.IBookService;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, BookEntity> implements IBookService{
	
	@Autowired
	BookMapper bookMapper;

	@Override
	public Page<BookEntity> queryPage(String name, Page<BookEntity> page) {
		return page.setRecords(bookMapper.queryPage(name , page));
	}

}
