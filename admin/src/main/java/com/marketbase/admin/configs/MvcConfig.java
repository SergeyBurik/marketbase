package com.marketbase.admin.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

	@Value("${upload.path}")
	String uploadPath;

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("auth/login");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("/css/**")
//                .addResourceLocations("classpath:/resources/static/css");

		registry
				.addResourceHandler("/uploads/**")
				.addResourceLocations("file:///" + uploadPath + "/")
				.setCachePeriod(31556926);

	}
}