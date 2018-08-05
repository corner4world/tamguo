package com.tamguo.web.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.common.utils.Result;
import com.tamguo.modules.member.service.IMemberService;
import com.tamguo.utils.ShiroUtils;

@Controller
public class MemberController {
	
	@Autowired
	IMemberService iMemberService;

	@RequestMapping(value = "/member/index.html", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model){
		model.setViewName("member/index");
		model.addObject("member" , iMemberService.findByUid(ShiroUtils.getMember().getId()));
		return model;
	}
	
	@RequestMapping(value = "/member/findCurrMember.html", method = RequestMethod.GET)
	@ResponseBody
	public Result findCurrMember() {
		return Result.successResult(iMemberService.findCurrMember(ShiroUtils.getMemberId()));
	}
}
