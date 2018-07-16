package com.tamguo.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tamguo.admin.model.BookEntity;

public interface BookMapper extends BaseMapper<BookEntity>{

	List<BookEntity> queryPage(@Param(value="name")String name , Pagination page);

}
