package com.tamguo.modules.tiku.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.tamguo.modules.tiku.service.IBookService;

@Controller
@RequestMapping(path="tiku/book")
public class BookController {

	/** 书籍*/
	private final String BOOK_LIST_PAGE = "modules/tiku/book/list";
	
	@Autowired
	private IBookService iBookService;
	
	@RequestMapping(path="list")
	public ModelAndView index(ModelAndView model) {
		model.setViewName(BOOK_LIST_PAGE);
		return model;
	}

	
}
