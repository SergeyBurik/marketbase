package com.marketbase.resources.controllers;

import com.marketbase.resources.beans.SimpleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deploy")
public class DeployController {

	@Value("${projects.path}")
	String projectsPath;

	@Value("${host}")
	String host;

	@PostMapping("")
	public SimpleResponse deploy(
			@RequestParam Long orderId,
			@RequestParam String serverIp,
			@RequestParam String serverUser,
			@RequestParam String serverPassword,
			@RequestParam String domainName
	) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(
					"start app_deployment.py "
							+ orderId + " "
							+ host + " "
							+ serverIp + " "
							+ serverUser + " "
							+ serverPassword + " "
							+ domainName
			);
		} catch (Exception e) {
			return new SimpleResponse(500, e.getMessage());
		}

		return new SimpleResponse(200, "");
	}

}
