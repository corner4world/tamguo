package com.tamguo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.tamguo.modules.member.service.IMemberService;
import com.tamguo.utils.ShiroUtils;

/**
 * 会员中心-首页
 * 
 * @author tamguo
 *
 */
@Controller
public class IndexController {

	@Autowired
	IMemberService iMemberService;
	
	@RequestMapping(value = {"index.html" , "/"}, method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model){
		model.setViewName("index");
		model.addObject("member", iMemberService.selectById(ShiroUtils.getMemberId()));
		return model;
	}
	
}
