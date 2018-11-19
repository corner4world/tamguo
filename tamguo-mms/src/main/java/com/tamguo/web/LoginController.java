package com.tamguo.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.common.image.CaptchaUtils;
import com.tamguo.common.utils.Result;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.utils.ShiroUtils;

@Controller
public class LoginController {

	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response , HttpSession session) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		
		String a = CaptchaUtils.generateCaptcha(response.getOutputStream());
		session.setAttribute(SystemConstant.KAPTCHA_SESSION_KEY, a);
	}
	
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model){
		model.setViewName("login");
		model.addObject("isVerifyCode" , "0");
		return model;
	}
	
	@RequestMapping(value = "/submitLogin.html", method = RequestMethod.POST)
	public ModelAndView submitLogin(String  username , String password , String verifyCode , ModelAndView model , HttpSession session , HttpServletResponse response) throws IOException{
		Result result = Result.successResult(null);
		if(StringUtils.isEmpty(verifyCode)) {
			result = Result.result(202, null, "请输入验证码");
		} else if(StringUtils.isNotEmpty(verifyCode)){
			String kaptcha = session.getAttribute(SystemConstant.KAPTCHA_SESSION_KEY).toString();
			if (!verifyCode.equalsIgnoreCase(kaptcha)) {
				result = Result.result(205, null, "验证码错误");
			} else {
				Subject subject = ShiroUtils.getSubject();
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				try {
					subject.login(token);
					
					session.setAttribute("currMember", ShiroUtils.getMember());
					response.sendRedirect("index.html");
					return null;
				} catch (UnknownAccountException e) {
					result = Result.result(201, null, "用户名或密码有误，请重新输入或找回密码");
				} catch (IncorrectCredentialsException e) {
					result = Result.result(202, null, "用户名或密码有误，请重新输入或找回密码");
				} catch (LockedAccountException e) {
					result = Result.result(203, null, "账号被锁定");
				} 
			}
		} 
		model.setViewName("login");	
		model.addObject("code", result.getCode());
		model.addObject("msg" , result.getMessage());
		model.addObject("username", username);
		return model;
	}
	
	@RequestMapping(value = "/miniLogin.html", method = RequestMethod.GET)
	@ResponseBody
    public Result miniLogin(String username , String password , String captcha, ModelAndView model , HttpSession session) {
		Result result = null;
		if(StringUtils.isEmpty(captcha)) {
			result = Result.result(204, null, "请输入验证码");
		} else if(StringUtils.isNotEmpty(captcha)){
			String kaptcha = session.getAttribute(SystemConstant.KAPTCHA_SESSION_KEY).toString();
			if (!captcha.equalsIgnoreCase(kaptcha)) {
				result = Result.result(205, null, "验证码错误");
			}else {
				Subject subject = ShiroUtils.getSubject();
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				try {
					subject.login(token);
					session.setAttribute("currMember", ShiroUtils.getMember());
					result = Result.successResult(ShiroUtils.getMember());
				} catch (UnknownAccountException e) {
					result = Result.result(201, null, "用户名或密码有误，请重新输入或找回密码");
				} catch (IncorrectCredentialsException e) {
					result = Result.result(202, null, "用户名或密码有误，请重新输入或找回密码");
				} catch (LockedAccountException e) {
					result = Result.result(203, null, "账号被锁定");
				} 
			}
		}
		return result;
    }

	@RequestMapping(value = "/isLogin.html", method = RequestMethod.GET)
	@ResponseBody
	public Result isLogin() {
		if(ShiroUtils.isLogin()) {
			return Result.result(1, null , "已经登录");
		}
		return Result.result(0, null, "未登录");
	}
}
