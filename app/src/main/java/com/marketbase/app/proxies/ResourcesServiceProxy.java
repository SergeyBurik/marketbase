package com.marketbase.app.proxies;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(name = "resources-service", configuration = {ResourcesServiceProxy.MultipartSupportConfig.class})
public interface 	ResourcesServiceProxy {

	@RequestMapping(value = {"/upload"}, method = {RequestMethod.POST}, consumes = {"multipart/form-data"})
	String saveFile(@RequestPart("file") MultipartFile file);

	class MultipartSupportConfig {
		@Bean
		@Primary
		@Scope("prototype")
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder();
		}
	}

}