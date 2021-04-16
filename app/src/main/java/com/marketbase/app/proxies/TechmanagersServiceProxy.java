package com.marketbase.app.proxies;

import com.marketbase.app.beans.SimpleResponse;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "techmanagers-service", configuration = {TechmanagersServiceProxy.MultipartSupportConfig.class})
public interface TechmanagersServiceProxy {

	@RequestMapping(value = {"/api/deploy/tickets"}, method =  {RequestMethod.POST})
	SimpleResponse createDeployTicket(@RequestParam Long orderId);

	class MultipartSupportConfig {
		@Bean
		@Primary
		@Scope("prototype")
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder();
		}
	}
}
