package com.tamguo.modules.sys.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tamguo.config.image.CaptchaUtils;
import com.tamguo.modules.sys.utils.ExceptionSupport;
import com.tamguo.modules.sys.utils.ShiroUtils;
import com.tamguo.modules.sys.utils.TamguoConstant;

@Controller
public class ValidCodeController {

	@RequestMapping("validCode")
	public void validCode(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		
		String a = CaptchaUtils.generateCaptcha(response.getOutputStream());
		ShiroUtils.setSessionAttribute(TamguoConstant.KAPTCHA_SESSION_KEY, a);
	}
	
	@RequestMapping("checkCode")
	@ResponseBody
	public Boolean checkCode(String validCode) throws ServletException, IOException {
		try {
			String kaptcha = ShiroUtils.getKaptcha(TamguoConstant.KAPTCHA_SESSION_KEY);
			if (validCode.equalsIgnoreCase(kaptcha)) {
				return true;
			}
		} catch (Exception e) {
			ExceptionSupport.resolverResult("验证编码错误", this.getClass(), e);
		}
		return false;
	}
	
}
