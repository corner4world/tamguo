package com.tamguo.admin.service;
/**
 * Service - 书籍
 * 
 * @author tamguo
 *
 */

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.admin.model.BookEntity;

public interface IBookService extends IService<BookEntity>{

	Page<BookEntity> queryPage(String name , Page<BookEntity> page);
}
