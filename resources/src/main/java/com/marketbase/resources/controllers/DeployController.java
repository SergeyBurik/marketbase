package com.marketbase.resources.controllers;

import com.marketbase.resources.beans.Module;
import com.marketbase.resources.beans.Order;
import com.marketbase.resources.beans.SimpleResponse;
import com.marketbase.resources.models.DeployDebugMessage;
import com.marketbase.resources.proxies.AppServiceProxy;
import com.marketbase.resources.repositories.AppPropertyRepository;
import com.marketbase.resources.repositories.DeployDebugMessageRepository;
import com.marketbase.resources.services.DeployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
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

	@Autowired
	AppPropertyRepository appPropertyRepository;

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

			// get modules string (ex. "storeapp;store;store/", ...)
			String modules = "";
			for (Module module: order.getModules()) {
				modules += module.getModuleName() + ";" + module.getNamespace() + ";" + module.getUrl() + ", ";
			}

			deployService.deploy(
					serverUser, serverPassword, serverIp, orderId,
					order.getTemplate().getProjectName(), order.getDomainName(),
					modules
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

	@PostMapping(value = "/{orderId}/debug", consumes = {"application/json"})
	public DeployDebugMessage deployDebug(@PathVariable("orderId") Long orderId,
										  @RequestBody DeployDebugMessage log) {
		log.setOrderId(orderId);

		// if error occurred while deploying
		if (log.getLevel().equals("ERROR")) {
			appServiceProxy.completeOrder(orderId, "FAILED");
		}

		return deployDebugMessageRepository.save(log);
	}

	@GetMapping("/{orderId}/logs")
	public List<DeployDebugMessage> getLogs(@PathVariable Long orderId) {
		return deployDebugMessageRepository.findByOrderId(orderId);
	}

}
