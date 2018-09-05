package com.tamguo.web.member;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.common.utils.Result;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.modules.member.service.IMemberService;

@Controller
public class PasswordController {
	
	@Autowired
	private IMemberService iMemberService;
	
	@RequestMapping(value = "password/find.html", method = RequestMethod.GET)
	public ModelAndView confirmAccount(ModelAndView model){
		model.setViewName("password/confirmAccount");
		return model;
	}
	
	@RequestMapping(value = "password/confirmAccount.html", method = RequestMethod.POST)
	public ModelAndView submitConfirmAccount(String username , String veritycode , ModelAndView model , HttpSession session){
		Result result = iMemberService.confirmAccount(username, veritycode);
		String kaptcha = session.getAttribute(SystemConstant.KAPTCHA_SESSION_KEY).toString();
		if (!veritycode.equalsIgnoreCase(kaptcha)) {
			result = Result.result(202, null, "验证码错误");
		}
		if(result.getCode() == 200){
			model.setViewName("password/securityCheck");
			model.addObject("result", result);
			model.addObject("isEmail", username.contains("@") ? "1" : "0");
		}else{
			model.setViewName("password/confirmAccount");
			model.addObject("account", username);
			model.addObject("username",username);
			model.addObject("veritycode", veritycode);
			model.addObject("code", result.getCode());
		}
		return model;
	}
	
	@RequestMapping(value = "password/securityCheck.html", method = RequestMethod.POST)
	public ModelAndView securityCheck(String username , String isEmail , String mobileVcode , ModelAndView model){
		Result result = iMemberService.securityCheck(username , isEmail , mobileVcode);
		if(result.getCode() == 200){
			model.addObject("username", username);
			model.addObject("resetPasswordKey" , result.getResult());
			model.setViewName("password/resetPassword");
		}else{
			model.addObject("result", result);
			model.addObject("isEmail", isEmail);
			model.addObject("codeError", "1");
			model.setViewName("password/securityCheck");
		}
		return model;
	}
	
	@RequestMapping(value = "password/resetPassword.html", method = RequestMethod.POST)
	public ModelAndView resetPassword(String resetPasswordKey , String username , String password , String verifypwd , ModelAndView model){
		Result result = iMemberService.resetPassword(resetPasswordKey , username , password , verifypwd);
		if(result.getCode() == 200){
			model.setViewName("password/resetPwSuccess");
		}else{
			model.setViewName("password/resetPassword");
		}
		return model;
	}
	
	@RequestMapping(value = "password/checkAccount.html", method = RequestMethod.GET)
	@ResponseBody
	public Result checkAccount(String account){
		return iMemberService.checkAccount(account);
	}
	
}
