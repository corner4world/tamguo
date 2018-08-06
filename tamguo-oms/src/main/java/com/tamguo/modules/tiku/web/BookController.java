package com.tamguo.modules.tiku.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.ExceptionSupport;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.tiku.model.BookEntity;
import com.tamguo.modules.tiku.model.condition.BookCondition;
import com.tamguo.modules.tiku.model.enums.SubjectStatusEnum;
import com.tamguo.modules.tiku.service.IBookService;
import com.tamguo.modules.tiku.service.ISubjectService;

@Controller
@RequestMapping(path="tiku/book")
public class BookController {

	/** 书籍*/
	private final String BOOK_LIST_PAGE = "modules/tiku/book/list";
	
	@Autowired
	private IBookService iBookService;
	@Autowired
	private ISubjectService iSubjectService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="list")
	public ModelAndView index(ModelAndView model) {
		model.setViewName(BOOK_LIST_PAGE);
		model.addObject("subjectList", iSubjectService.selectList(Condition.create().eq("status", SubjectStatusEnum.NORMAL.getValue())));
		return model;
	}
	
	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listData(BookCondition condition) {
		Page<BookEntity> page = iBookService.listData(condition);
		return Result.jqGridResult(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages());
	}

	@RequestMapping(path="save",method=RequestMethod.POST)
	@ResponseBody
	public Result save(BookEntity book) {
		try {
			iBookService.save(book);
			return Result.result(0, null, "保存书籍【"+book.getName()+"】成功");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("保存书籍", this.getClass(), e);
		}
	}
}
