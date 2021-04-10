package com.marketbase.techmanagers.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Configuration
//@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {


	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("auth/login");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry
//				.addResourceHandler("/static/css/**")
//				.addResourceLocations(resourcesServerURL + "/css/");
//
//		registry
//				.addResourceHandler("/static/assets/**")
//				.addResourceLocations(resourcesServerURL + "/assets/");
//
//		registry
//				.addResourceHandler("/static/js/**")
//				.addResourceLocations(resourcesServerURL + "/js/");
//
//		registry
//				.addResourceHandler("/uploads/**")
//				.addResourceLocations(resourcesServerURL + "/uploads/")
//				.setCachePeriod(31556926);

	}

//	@PostConstruct
//	private void init() {
//		resourcesServerURL = appPropertyRepository.getByKey("resources.server").get().getValue();
//		uploadPath = appPropertyRepository.getByKey("upload.path").get().getValue();
//	}
}