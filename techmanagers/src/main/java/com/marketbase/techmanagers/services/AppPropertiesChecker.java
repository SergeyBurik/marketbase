//package com.marketbase.techmanagers.services;
//
//import com.marketbase.app.repositories.AppPropertyRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class AppPropertiesChecker implements ApplicationListener<ContextRefreshedEvent> {
//
//	@Autowired
//	AppPropertyRepository appPropertyRepository;
//
//	@Override
//	public void onApplicationEvent(final ContextRefreshedEvent event) {
//		// check current properties
//		List<String> properties = Arrays.asList(
//				"upload.path", "resources.server"
//		);
//
//		for (String key: properties) {
//			if (!appPropertyRepository.getByKey(key).isPresent()) {
//				System.out.println("ERROR: " + key + " property is not present");
//			}
//		}
//
//	}
//}
