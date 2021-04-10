package com.marketbase.techmanagers.controllers;

import com.marketbase.techmanagers.models.AppDeployTicket;
import com.marketbase.techmanagers.proxies.AppServiceProxy;
import com.marketbase.techmanagers.repositories.AppDeployTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/deploy")
public class AppDeploy {

	@Autowired
	AppDeployTicketRepository appDeployTicketRepository;

	@Autowired
	AppServiceProxy appServiceProxy;

	@GetMapping("/tickets")
	private String appDeployTickets(Model model) {
		model.addAttribute("tickets", appDeployTicketRepository.findAll());
		return "deploy/tickets";
	}

	@GetMapping("/tickets/{id}")
	private String ticketView(Model model, @PathVariable Long id) {
		model.addAttribute("ticket", appServiceProxy.getOrder(id));
		return "deploy/ticket";
	}

}
