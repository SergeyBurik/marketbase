package com.marketbase.techmanagers.proxies;

import com.marketbase.techmanagers.beans.Order;
import com.marketbase.techmanagers.models.AppDeployTicket;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "app-service", configuration = {AppServiceProxy.MultipartSupportConfig.class})
public interface AppServiceProxy {

	@RequestMapping(value = {"/api/orders/{id}"}, method =  {RequestMethod.GET})
	Order getOrder(@PathVariable Long id);

	class MultipartSupportConfig {
		@Bean
		@Primary
		@Scope("prototype")
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder();
		}
	}
}
