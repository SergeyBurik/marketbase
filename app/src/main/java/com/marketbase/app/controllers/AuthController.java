package com.marketbase.app.controllers;

import com.marketbase.app.models.User;
import com.marketbase.app.repositories.UserRepository;
import com.marketbase.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AuthController {

	@Autowired
	UserService userService;

	@GetMapping("/join")
	public String singUp() {
		return "auth/signUp";
	}

	@PostMapping("/join")
	public String addUser(@RequestParam String username,
						  @RequestParam String first_name, @RequestParam String last_name,
						  @RequestParam String password, @RequestParam String email,
						  @RequestParam String phoneNumber) throws Exception {

		try {
			User u = userService.save(
					username, first_name,
					last_name, email,
					phoneNumber, password);
		} catch (Exception e) {
			return "redirect:/join?error="+ e.getMessage();
		}

		return "redirect:/login";
	}
}
