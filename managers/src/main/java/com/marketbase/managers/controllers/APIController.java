package com.marketbase.managers.controllers;

import com.marketbase.managers.models.User;
import com.marketbase.managers.models.UserRole;
import com.marketbase.managers.repositories.UserRepository;
import com.marketbase.managers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@PostMapping("/users")
	public User createUser(@RequestParam String username,
						   @RequestParam String first_name,
						   @RequestParam String last_name,
						   @RequestParam String email,
						   @RequestParam String password,
						   @RequestParam Set<UserRole> role,
						   @RequestParam MultipartFile avatar) throws Exception {
		return userService.save(
				username, first_name, last_name,
				email, password, role, avatar);
	}

	@GetMapping("/users")
	public List<User> users() {
		return userRepository.findAll();
	}

}
