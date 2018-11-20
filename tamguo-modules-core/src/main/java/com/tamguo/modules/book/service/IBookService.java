package com.tamguo.modules.book.service;

import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.book.model.BookEntity;

public interface IBookService extends IService<BookEntity>{

	/** 创建书籍*/
	void saveBook(BookEntity book);

}
