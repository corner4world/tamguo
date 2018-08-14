package com.tamguo.web.tiku;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView indexAction(ModelAndView model) {
    	model.setViewName("index");
        return model;
    }
	
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView mainAction(ModelAndView model) {
    	model.setViewName("index");
        return model;
    }
    
    @RequestMapping(value = "/baidu_verify_5agfTbCO3Q", method = RequestMethod.GET)
    public ModelAndView baidu_verify_5agfTbCO3Q(ModelAndView model) {
    	model.setViewName("thirdparty/baidu_verify_5agfTbCO3Q");
        return model;
    }
    
    @RequestMapping(value = "/baidu_verify_iAm7387J0l", method = RequestMethod.GET)
    public ModelAndView baidu_verify_iAm7387J0l(ModelAndView model) {
    	model.setViewName("thirdparty/baidu_verify_iAm7387J0l");
        return model;
    }
    
    @RequestMapping(value = "/shenma-site-verification.txt", method = RequestMethod.GET)
    public ModelAndView shenma_site_verification(ModelAndView model) {
    	model.setViewName("thirdparty/shenma-site-verification");
        return model;
    }
    
    @RequestMapping(value = "/sogousiteverification", method = RequestMethod.GET)
    public ModelAndView sogousiteverification(ModelAndView model) {
    	model.setViewName("thirdparty/sogousiteverification");
        return model;
    }
	
	@RequestMapping(value = "error404", method = RequestMethod.GET)
    public ModelAndView error404(ModelAndView model) {
    	model.setViewName("404");
        return model;
    }

	@RequestMapping(value = "error500", method = RequestMethod.GET)
    public ModelAndView error500(ModelAndView model) {
    	model.setViewName("500");
        return model;
    }
}
