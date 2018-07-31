package com.tamguo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tamguo.modules.sys.interceptor.SettingsInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${file.storage.path}")
	private String fileStoragePath;
	@Autowired
	private SettingsInterceptor settingsInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(settingsInterceptor).addPathPatterns("/**");
	}
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	   registry.addResourceHandler("/files/**").addResourceLocations("file:"+fileStoragePath);
    }
	
}
