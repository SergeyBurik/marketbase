package com.marketbase.admin.proxies;

import com.marketbase.admin.beans.SimpleResponse;
import com.marketbase.admin.beans.SimpleUser;
import com.marketbase.admin.models.UserRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@FeignClient(name = "managers-service", url = "localhost:8001")
public interface ManagersServiceProxy {
	@PostMapping("/api/registration/")
	public SimpleResponse createManager(
			@RequestParam String username, @RequestParam String first_name,
			@RequestParam String last_name, @RequestParam String email,
			@RequestParam String password, @RequestParam Set<UserRole> role,
			@RequestParam MultipartFile avatar);

	@GetMapping("/api/users")
	public List<SimpleUser> getManagers();
}
