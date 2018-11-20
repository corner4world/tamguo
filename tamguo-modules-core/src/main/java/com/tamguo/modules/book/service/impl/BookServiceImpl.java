package com.tamguo.modules.book.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.book.dao.BookMapper;
import com.tamguo.modules.book.model.BookEntity;
import com.tamguo.modules.book.service.IBookService;

@Service(value="bbookServiceImpl")
public class BookServiceImpl extends ServiceImpl<BookMapper, BookEntity> implements IBookService{

	@Transactional(readOnly=false)
	@Override
	public void saveBook(BookEntity book) {
		book.setCreateDate(new Date());
		book.setUpdateDate(new Date());
		book.setSeoDescription(book.getName());
		book.setSeoKeywords(book.getName());
		book.setSeoTitle(book.getName());
		this.insert(book);
	}

}
