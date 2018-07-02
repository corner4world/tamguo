package com.tamguo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.tamguo")
public class TamguoCrawlerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(TamguoCrawlerApplication.class).web(true).run(args);
	}
	
}
