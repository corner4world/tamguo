package com.tamguo.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${file.storage.path}")
	private String fileStoragePath;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	   registry.addResourceHandler("/files/**").addResourceLocations("file:"+fileStoragePath);
    }
	
	@Bean(name="producer")  
    public DefaultKaptcha getKaptchaBean(){  
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();  
        Properties properties=new Properties();  
        properties.setProperty("kaptcha.border.color", "white");
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.image.width", "125");  
        properties.setProperty("kaptcha.image.height", "40");  
        properties.setProperty("kaptcha.session.key", "code"); 
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        properties.setProperty("kaptcha.textproducer.char.length", "4");  
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");         
        Config config=new Config(properties);  
        defaultKaptcha.setConfig(config);  
        return defaultKaptcha;  
    }  
	
}
