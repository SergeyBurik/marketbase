package com.marketbase.techmanagers.proxies;

import com.marketbase.techmanagers.beans.Order;
import com.marketbase.techmanagers.beans.SimpleResponse;
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

	@RequestMapping(value = {"/api/orders/{id}/setStatus"}, method =  {RequestMethod.POST}, headers = {"Content-Type: application/json"})
	SimpleResponse setOrderStatus(@PathVariable Long id, @RequestParam String status);

	class MultipartSupportConfig {
		@Bean
		@Primary
		@Scope("prototype")
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder();
		}
	}
}
