package com.tamguo.modules.tiku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.tiku.dao.BookMapper;
import com.tamguo.modules.tiku.model.BookEntity;
import com.tamguo.modules.tiku.model.condition.BookCondition;
import com.tamguo.modules.tiku.service.IBookService;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, BookEntity> implements IBookService{

	@Autowired
	BookMapper bookMapper;
	
	@Transactional(readOnly=false)
	@Override
	public Page<BookEntity> listData(BookCondition condition) {
		Page<BookEntity> page = new Page<>(condition.getPageNo() , condition.getPageSize());
		return page.setRecords(bookMapper.listData(page, condition));
	}

}
