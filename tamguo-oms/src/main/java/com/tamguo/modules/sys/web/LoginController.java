package com.tamguo.modules.sys.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tamguo.modules.sys.service.ISysUserService;
import com.tamguo.modules.sys.utils.ExceptionSupport;
import com.tamguo.modules.sys.utils.Result;
import com.tamguo.modules.sys.utils.ShiroUtils;
import com.tamguo.modules.sys.utils.TamguoConstant;

@Controller
public class LoginController {
	
	@Autowired
	private ISysUserService iSysUserService;

	@RequestMapping(path="login")
	public String sysLogin(ModelAndView model) {
		if(ShiroUtils.isLogin()) {
			return "index";
		}
		return "login";
	}
	
	@ResponseBody
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public Result toLogin(HttpServletRequest request, HttpServletResponse response , String username , Boolean rememberUser,  String password, String validCode)
			throws IOException {
		try {
			String kaptcha = ShiroUtils.getKaptcha(TamguoConstant.KAPTCHA_SESSION_KEY);
			if (!validCode.equalsIgnoreCase(kaptcha)) {
				return Result.failResult("验证码错误");
			}
			Subject subject = ShiroUtils.getSubject();
			// sha256加密
			password = new Sha256Hash(password).toHex();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
			
			// 获取权限菜单
			request.getSession().setAttribute("userMenuList", iSysUserService.findUserMenuList());
		} catch (UnknownAccountException e) {
			return ExceptionSupport.resolverResult("找不到账户", this.getClass(), e);
		} catch (IncorrectCredentialsException e) {
			return ExceptionSupport.resolverResult("账户验证失败", this.getClass(), e);
		} catch (LockedAccountException e) {
			return ExceptionSupport.resolverResult("账户验证失败", this.getClass(), e);
		} catch (AuthenticationException e) {
			return ExceptionSupport.resolverResult("账户验证失败", this.getClass(), e);
		}
		request.getSession().setAttribute("currAdmin", ShiroUtils.getUser());
		return Result.successResult(username);
	}
	
}
