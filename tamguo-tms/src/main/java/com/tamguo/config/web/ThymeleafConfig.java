package com.tamguo.config.web;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
public class ThymeleafConfig {

	@Resource
	private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
	    if(viewResolver != null) {
	        Map<String, Object> vars = new HashMap<>();
	        vars.put("domainName", "http://localhost:8081/");
	        viewResolver.setStaticVariables(vars);
	    }
	}
}
