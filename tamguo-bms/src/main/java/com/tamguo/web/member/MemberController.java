package com.tamguo.web.member;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.modules.member.model.MemberEntity;
import com.tamguo.modules.member.service.IMemberService;

@Controller
@RequestMapping(value="member")
public class MemberController {
	
	@Autowired
	IMemberService iMemberService;
	
	@RequestMapping("index.html")
	public ModelAndView index(HttpServletRequest request , ModelAndView model) {
		model.setViewName("member/index");
		MemberEntity currMember = (MemberEntity) request.getSession().getAttribute("currMember");
		MemberEntity member = iMemberService.selectById(currMember.getId());
		model.addObject("member", member);
		return model;
	}

}
