package com.tamguo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.modules.book.model.BookEntity;
import com.tamguo.modules.book.service.IBookService;

@Controller
public class BookController {
	
	@Autowired
	private IBookService iBookService;

	@RequestMapping(value="book/{id}" , method=RequestMethod.GET)
	public ModelAndView book(@PathVariable String id ,  ModelAndView model) {
		BookEntity book = iBookService.selectById(id);
		model.addObject("book", book);
		model.setViewName("book/book");
		return model;
	}
}
