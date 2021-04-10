package com.marketbase.techmanagers.configs;

import com.marketbase.techmanagers.models.User;
import com.marketbase.techmanagers.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@ControllerAdvice
public class AnnotationAdvice {

	@Autowired
	UserRepository userRepository;

	@ModelAttribute("projectName")
	public String projectName() {
		return "MarketBase";
	}

	@ModelAttribute("user")
	public User user() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username = "";

		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}

		Optional<User> user = userRepository.findByUsername(username);
		return user.orElse(null);
	}
}