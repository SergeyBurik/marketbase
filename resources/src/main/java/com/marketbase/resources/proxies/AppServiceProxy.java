package com.marketbase.resources.proxies;

import com.marketbase.resources.beans.Order;
import com.marketbase.resources.beans.SimpleResponse;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "resources-service", configuration = {AppServiceProxy.MultipartSupportConfig.class})
public interface AppServiceProxy {

	@RequestMapping(value = {"/api/orders/{id}"}, method = {RequestMethod.GET}, consumes = {"multipart/form-data"})
	Order getOrder(@PathVariable Long id);

	@RequestMapping(value = {"/api/orders/{id}/complete"}, method = {RequestMethod.POST}, consumes = {"application/json"})
	SimpleResponse completeOrder(@PathVariable Long id, @RequestParam String result);

	class MultipartSupportConfig {
		@Bean
		@Primary
		@Scope("prototype")
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder();
		}
	}

}