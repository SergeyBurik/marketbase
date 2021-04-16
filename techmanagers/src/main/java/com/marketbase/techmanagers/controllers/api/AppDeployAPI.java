package com.marketbase.techmanagers.controllers.api;

import com.marketbase.techmanagers.beans.DeployLog;
import com.marketbase.techmanagers.beans.Order;
import com.marketbase.techmanagers.beans.SimpleResponse;
import com.marketbase.techmanagers.models.AppDeployTicket;
import com.marketbase.techmanagers.proxies.AppServiceProxy;
import com.marketbase.techmanagers.proxies.ResourcesServiceProxy;
import com.marketbase.techmanagers.repositories.AppDeployTicketRepository;
import com.marketbase.techmanagers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deploy")
public class AppDeployAPI {

	@Autowired
	AppDeployTicketRepository appDeployTicketRepository;

	@Autowired
	AppServiceProxy appServiceProxy;

	@Autowired
	ResourcesServiceProxy resourcesServiceProxy;

	@Autowired
	UserService userService;

	@PostMapping("/tickets")
	private AppDeployTicket newAppDeployTicket(@RequestParam Long orderId) {
		return appDeployTicketRepository.save(new AppDeployTicket(orderId));
	}

	@GetMapping("/tickets")
	private List<AppDeployTicket> appDeployTickets() {
		return appDeployTicketRepository.findByUser(null);
	}

	@GetMapping("/{id}/logs")
	public List<DeployLog> orderDeployLogs(@PathVariable Long id) {
		return resourcesServiceProxy.getDeployLogs(id);
	}

	@PostMapping("/{id}/accept")
	public SimpleResponse acceptTicket(@PathVariable Long id) {
		AppDeployTicket ticket = appDeployTicketRepository.findByOrderId(id);
		if (ticket.getUser() == null) {
			ticket.setUser(userService.getCurrentUser());
		} else {
			ticket.setUser(null);
		}
		appDeployTicketRepository.save(ticket);
		return new SimpleResponse(200, "");
	}

	@PostMapping("/{id}")
	public SimpleResponse deployProject(@PathVariable("id") Long id) throws Exception {
		Order order = appServiceProxy.getOrder(id);

		// send request to deploy
		SimpleResponse response = resourcesServiceProxy.deploy(
				order.getId(),
				order.getServerIP(),
				order.getServerUser(),
				order.getServerUserPassword()
		);

		if (response.getCode() == 200) {
			order.setStatus("Deploying"); // TODO: change it
			appServiceProxy.saveOrder(order.getId(), order);

			return new SimpleResponse(
					200,
					"Deploying application"
			);
		} else {
			order.setStatus("Failed");
			appServiceProxy.saveOrder(order.getId(), order);
			return new SimpleResponse(500, response.getMessage());
		}
	}

}
