package com.marketbase.admin.proxies;

import com.marketbase.admin.beans.SimpleResponse;
import com.marketbase.admin.beans.SimpleUser;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@FeignClient(name = "managers-service",  configuration = {ManagersServiceProxy.MultipartSupportConfig.class})
public interface ManagersServiceProxy {

	@RequestMapping(value= {"/api/users"}, consumes = {"multipart/form-data"})
	SimpleUser createManager(@RequestParam("username") String username,
							 @RequestParam("first_name") String first_name,
							 @RequestParam("last_name") String last_name,
							 @RequestParam("email") String email,
							 @RequestParam("password") String password,
							 @RequestPart("avatar") MultipartFile avatar);

	@GetMapping("/api/users")
	List<SimpleUser> getManagers();

	@PostMapping("/api/clients")
	SimpleResponse createClients(@RequestParam("clients") List<String> clients);

	@GetMapping("/api/users/{username}")
	SimpleUser getManagerByUsername(@PathVariable("username") String username);

	class MultipartSupportConfig {
		@Bean
		@Primary
		@Scope("prototype")
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder();
		}
	}
}