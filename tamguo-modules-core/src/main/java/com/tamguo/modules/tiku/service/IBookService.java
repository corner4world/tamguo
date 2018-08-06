package com.tamguo.modules.tiku.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.tiku.model.BookEntity;
import com.tamguo.modules.tiku.model.condition.BookCondition;

public interface IBookService extends IService<BookEntity>{

	Page<BookEntity> listData(BookCondition condition);

	void save(BookEntity book);

	void update(BookEntity book);

	void delete(String id);

	void enable(String id);

	void disabled(String id);

}
