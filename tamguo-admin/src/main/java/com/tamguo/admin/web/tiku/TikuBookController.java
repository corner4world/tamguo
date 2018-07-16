package com.tamguo.admin.web.tiku;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.admin.model.BookEntity;
import com.tamguo.admin.service.IBookService;
import com.tamguo.admin.util.ExceptionSupport;
import com.tamguo.admin.util.Result;

@Controller(value="tikuBookController")
public class TikuBookController {
	
	@Autowired
	IBookService iBookService;

	@RequestMapping("book/queryPage")
	@ResponseBody
	public Map<String, Object> queryPage(String name , Integer page , Integer limit) {
		try {
			Page<BookEntity> bookPage = iBookService.queryPage(name, new Page<>(page, limit));
			return Result.jqGridResult(bookPage.getRecords(), bookPage.getTotal(), bookPage.getSize(), bookPage.getCurrent(), bookPage.getPages());
		} catch (Exception e) {
			ExceptionSupport.resolverResult("查询书籍错误", this.getClass(), e);
			return null;
		}
	}
	
	@RequestMapping("book/info/{bookId}.html")
	@ResponseBody
	public Result info(@PathVariable String bookId){
		try {
			return Result.result(0, iBookService.selectById(bookId), null);
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("查询科目", this.getClass(), e);
		}
	}
	
}
