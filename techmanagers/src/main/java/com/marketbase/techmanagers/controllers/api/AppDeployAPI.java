package com.marketbase.techmanagers.controllers.api;

import com.marketbase.techmanagers.models.AppDeployTicket;
import com.marketbase.techmanagers.repositories.AppDeployTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apps/deploy")
public class AppDeployAPI {

	@Autowired
	AppDeployTicketRepository appDeployTicketRepository;

	@PostMapping("/tickets")
	private AppDeployTicket newAppDeployTicket(@RequestParam Long orderId) {
		return appDeployTicketRepository.save(new AppDeployTicket(orderId));
	}

	@GetMapping("/tickets")
	private List<AppDeployTicket> appDeployTickets() {
		return appDeployTicketRepository.findAll();
	}

}
