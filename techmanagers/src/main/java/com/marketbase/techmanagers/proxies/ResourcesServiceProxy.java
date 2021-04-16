package com.marketbase.techmanagers.proxies;

import com.marketbase.techmanagers.beans.DeployLog;
import com.marketbase.techmanagers.beans.SimpleResponse;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@FeignClient(name = "resources-service", configuration = {ResourcesServiceProxy.MultipartSupportConfig.class})
public interface ResourcesServiceProxy {

	@RequestMapping(value = {"/upload"}, method = {RequestMethod.POST}, consumes = {"multipart/form-data"})
	String saveFile(@RequestPart("file") MultipartFile file);

	@RequestMapping(value = {"/deploy"}, method =  {RequestMethod.POST})
	SimpleResponse deploy(@RequestParam Long orderId,
						  @RequestParam String serverIp,
						  @RequestParam String serverUser,
						  @RequestParam String serverPassword);

	@RequestMapping(value = {"/deploy/{orderId}/logs"}, method =  {RequestMethod.GET})
	List<DeployLog> getDeployLogs(@PathVariable Long orderId);

	class MultipartSupportConfig {
		@Bean
		@Primary
		@Scope("prototype")
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder();
		}
	}

}