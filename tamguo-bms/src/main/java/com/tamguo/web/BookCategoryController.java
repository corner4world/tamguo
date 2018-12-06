package com.tamguo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.book.model.BookEntity;
import com.tamguo.modules.book.service.IBookCategoryService;
import com.tamguo.modules.book.service.IBookService;
import com.tamguo.modules.member.model.MemberEntity;
import com.tamguo.modules.member.service.IMemberService;

@Controller
public class BookCategoryController {
	
	@Autowired
	IBookService iBookService;
	@Autowired
	IMemberService iMemberService;
	@Autowired
	IBookCategoryService iBookCategoryService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="category/{id}.html")
	public ModelAndView bookCategory(@PathVariable String id , Integer current , ModelAndView model) {
		if(current == null) {
			current = 1;
		}
		model.setViewName("category");
		Page<BookEntity> bookPage = iBookService.selectPage(new Page<>(current, 18));
		for(BookEntity book : bookPage.getRecords()) {
			MemberEntity member = iMemberService.selectById(book.getOwner());
			if(member != null) {
				book.setMemberName(member.getUsername());
			}
		}
		// 查询分类
		model.addObject("bookCategoryList", iBookCategoryService.selectList(Condition.create().eq("parent_id", "0")));
		model.addObject("categoryId", id);
		model.addObject("bookPage", bookPage);
		return model;
	}
	
}
