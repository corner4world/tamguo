package com.tamguo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.tamguo.interceptor.SettingInterptor;


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Value("${file.storage.path}")
	private String fileStoragePath;
	@Autowired
	private SettingInterptor settingInterptor;
	
	/**
	 * 拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(settingInterptor).addPathPatterns("/**");
	}
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	   registry.addResourceHandler("/files/**").addResourceLocations("file:"+fileStoragePath);
       super.addResourceHandlers(registry);
    }
	
	@Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer(){
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
                container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html"));
            }
        };
    }

}
