package com.tamguo.modules.tiku.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path="tiku/book")
public class BookController {

	/** 书籍*/
	private final String BOOK_LIST_PAGE = "modules/tiku/book/list";
	
	@RequestMapping(path="list")
	public ModelAndView index(ModelAndView model) {
		model.setViewName(BOOK_LIST_PAGE);
		return model;
	}

	
}
