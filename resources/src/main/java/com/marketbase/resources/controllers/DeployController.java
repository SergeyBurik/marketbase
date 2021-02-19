package com.marketbase.resources.controllers;

import com.marketbase.resources.beans.Order;
import com.marketbase.resources.beans.SimpleResponse;
import com.marketbase.resources.models.DeployDebugMessage;
import com.marketbase.resources.proxies.AppServiceProxy;
import com.marketbase.resources.repositories.DeployDebugMessageRepository;
import com.marketbase.resources.services.DeployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/deploy")
public class DeployController {

	@Value("${projects.path}")
	String projectsPath;

	@Autowired
	DeployService deployService;

	@Autowired
	AppServiceProxy appServiceProxy;

	@Autowired
	DeployDebugMessageRepository deployDebugMessageRepository;

	@PostMapping("")
	public SimpleResponse deploy(
			@RequestParam Long orderId,
			@RequestParam String serverIp,
			@RequestParam String serverUser,
			@RequestParam String serverPassword
	) {
		try {
			// deploy

			// get order
			Order order = appServiceProxy.getOrder(orderId);

			deployService.deploy(
					serverUser, serverPassword, serverIp, orderId,
					order.getTemplate().getProjectName(), order.getDomainName()
			);
		} catch (Exception e) {
			deployDebugMessageRepository.save(
					new DeployDebugMessage(
							orderId, e.getMessage(), "ERROR", new Timestamp(System.currentTimeMillis())
					)
			);
			return new SimpleResponse(500, e.getMessage());
		}

		return new SimpleResponse(200, "");
	}

	@PostMapping("/{orderId}/confirm")
	public SimpleResponse deployConfirmation(@PathVariable Long orderId) throws IOException {
		// confirm deployment of client's app
		URL url = new URL("http://" + appServiceProxy.getOrder(orderId).getDomainName());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		// check if app is alive
		int status = con.getResponseCode();
		if (status == 200) {
			appServiceProxy.completeOrder(orderId, "SUCCESS");
		} else {
			appServiceProxy.completeOrder(orderId, "FAILED");
		}

		return new SimpleResponse(200, "");
	}

	@PostMapping("/{orderId}/debug")
	public DeployDebugMessage deployDebug(@PathVariable("orderId") Long orderId,
										  @RequestParam String type,
										  @RequestParam String message,
										  @RequestParam Timestamp dateTime) {
		// if error occurred while deploying
		if (type.equals("ERROR")) {
			appServiceProxy.completeOrder(orderId, "FAILED");
		}

		return deployDebugMessageRepository.save(new DeployDebugMessage(
				orderId, message, type, dateTime
		));
	}

	@GetMapping("/{orderId}/logs")
	public List<DeployDebugMessage> getLogs(@PathVariable Long orderId) {
		return deployDebugMessageRepository.findByOrderId(orderId);
	}

}
