package com.marketbase.resources.controllers;

import com.marketbase.resources.beans.SimpleResponse;
import com.marketbase.resources.services.DeployService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/deploy")
public class DeployController {

	@Value("${projects.path}")
	String projectsPath;


	@PostMapping("")
	public SimpleResponse deploy(
			@RequestParam Long orderId,
			@RequestParam String serverIp,
			@RequestParam String serverUser,
			@RequestParam String serverPassword
	) {
		try {
			// deploy
			new DeployService().deploy(serverUser, serverPassword, serverIp, orderId);
		} catch (Exception e) {
			return new SimpleResponse(500, e.getMessage());
		}

		return new SimpleResponse(200, "");
	}

	@GetMapping("/deploy/{id}/debug")
	public String deployDebug(@PathVariable("id") Long id,
							  @RequestParam String message) {
		return message;
	}

}
