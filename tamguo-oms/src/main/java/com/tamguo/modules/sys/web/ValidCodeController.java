package com.tamguo.modules.sys.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tamguo.common.image.CaptchaUtils;
import com.tamguo.common.utils.ExceptionSupport;
import com.tamguo.common.utils.SystemConstant;
import com.tamguo.modules.sys.utils.ShiroUtils;

@Controller
public class ValidCodeController {

	@RequestMapping("validCode")
	public void validCode(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		
		String a = CaptchaUtils.generateCaptcha(response.getOutputStream());
		ShiroUtils.setSessionAttribute(SystemConstant.KAPTCHA_SESSION_KEY, a);
	}
	
	@RequestMapping("checkCode")
	@ResponseBody
	public Boolean checkCode(String validCode) throws ServletException, IOException {
		try {
			String kaptcha = ShiroUtils.getKaptcha(SystemConstant.KAPTCHA_SESSION_KEY);
			if (validCode.equalsIgnoreCase(kaptcha)) {
				return true;
			}
		} catch (Exception e) {
			ExceptionSupport.resolverResult("验证编码错误", this.getClass(), e);
		}
		return false;
	}
	
}
