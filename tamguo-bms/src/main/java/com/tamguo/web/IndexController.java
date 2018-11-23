package com.tamguo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.modules.book.model.BookEntity;
import com.tamguo.modules.book.service.IBookService;
import com.tamguo.modules.member.model.MemberEntity;
import com.tamguo.modules.member.service.IMemberService;

/**
 * Controller - 首页
 * 
 * @author tamguo
 *
 */
@Controller
public class IndexController {
	
	@Autowired
	IBookService iBookService;
	@Autowired
	IMemberService iMemberService;

	@RequestMapping(path= {"index" , "/" , "index.html"})
	public ModelAndView index(Integer current , ModelAndView model) {
		if(current == null) {
			current = 1;
		}
		model.setViewName("index");
		Page<BookEntity> bookPage = iBookService.selectPage(new Page<>(current, 18));
		for(BookEntity book : bookPage.getRecords()) {
			MemberEntity member = iMemberService.selectById(book.getOwner());
			if(member != null) {
				book.setMemberName(member.getUsername());
			}
		}
		model.addObject("bookPage", bookPage);
		model.setViewName("index");

		return model;
	}
	
    @RequestMapping(value = "/baidu_verify_buLyB5tU19.html", method = RequestMethod.GET)
    public ModelAndView baidu_verify_5agfTbCO3Q(ModelAndView model) {
    	model.setViewName("thirdparty/baidu_verify_buLyB5tU19");
        return model;
    }
	
}
