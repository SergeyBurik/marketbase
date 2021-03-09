package com.marketbase.managers.controllers;

import com.marketbase.managers.beans.SimpleResponse;
import com.marketbase.managers.models.Client;
import com.marketbase.managers.models.User;
import com.marketbase.managers.models.UserRole;
import com.marketbase.managers.repositories.ClientRepository;
import com.marketbase.managers.repositories.UserRepository;
import com.marketbase.managers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	UserService userService;

	@PostMapping(value = "/users")
	public User createUser(@RequestParam String username,
						   @RequestParam String first_name,
						   @RequestParam String last_name,
						   @RequestParam String email,
						   @RequestParam String password,
						   @RequestParam MultipartFile avatar) throws Exception {
		return userService.save(
				username, first_name, last_name,
				email, password, Collections.singleton(UserRole.MANAGER), avatar
		);
	}

	@PostMapping("/clients")
	public SimpleResponse createClients(@RequestParam List<String> clients) {
		// 'clients' is a list of phone numbers

		for (String client: clients) {
			// if client exists
			if (clientRepository
					.findByPhoneNumber(client)
					.isPresent()) {
				// miss him
				continue;
			}

			// saving client to db
			clientRepository.save(new Client("", client, "whatsapp"));
		}
		return new SimpleResponse(200, "");
	}

	@GetMapping("/clients")
	public List<Client> clients(@RequestParam(required = false) String status) {
		if (status != null) {
			return clientRepository.findByStatus(status);
		}

		return clientRepository.findAll();
	}

	@GetMapping("/users/{username}")
	public User user(@PathVariable String username) {
		return userRepository.findByUsername(username).get();
	}

	@GetMapping("/users")
	public List<User> users(@RequestParam(required = false) String username) {
		return userRepository.findAll();
	}

}
