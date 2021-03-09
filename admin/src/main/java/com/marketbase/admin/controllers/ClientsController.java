package com.marketbase.admin.controllers;

import com.marketbase.admin.beans.Client;
import com.marketbase.admin.beans.SimpleResponse;
import com.marketbase.admin.proxies.ManagersServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/clients")
public class ClientsController {

	@Autowired
	ManagersServiceProxy managersServiceProxy;

	@GetMapping("")
	public String clients(Model model, @RequestParam(required = false) String status) {
		Map<String, String> query = new HashMap<>();
		List<String> statuses = new ArrayList<>(Arrays.asList("pending", "completed"));
		if (status != null) {
			query = Map.of("status", status);
			statuses.remove(statuses.indexOf(status));
			statuses.add(0, status);
		}

		// get clients
		List<Client> clients = managersServiceProxy.getClients(query);

		model.addAttribute("clients", clients);
		model.addAttribute("statuses", statuses);
		return "clients/clients";
	}

	@GetMapping("/create")
	public String newClients() {
		return "clients/newClients";
	}

	@PostMapping("/create")
	public ResponseEntity<Map> saveClients(@RequestBody List<String> clients) {
		// save clients
		SimpleResponse response = managersServiceProxy.createClients(clients);

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
