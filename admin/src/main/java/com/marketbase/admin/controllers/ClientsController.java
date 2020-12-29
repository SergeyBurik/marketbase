package com.marketbase.admin.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.marketbase.admin.beans.SimpleResponse;
import com.marketbase.admin.proxies.ManagersServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Map;

@Controller("/clients")
public class ClientsController {

	@Autowired
	ManagersServiceProxy managersServiceProxy;

	@GetMapping("")
	public String clients() {
		return "clients/clients";
	}

	@GetMapping("/create")
	public String newClients() {
		return "clients/newClients";
	}

	@PostMapping("/create")
	public ResponseEntity<Map> saveClients(@RequestParam String clients) {
		// save clients
		SimpleResponse response = managersServiceProxy.createClients(Arrays.asList(clients.split(" ")));

		if (response.getCode() == 200) {
			return new ResponseEntity<Map>(Map.of("code", 200), HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<Map>(Map.of(
					"code", response.getCode(),
					"message", response.getMessage()
			), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
