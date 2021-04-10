package com.marketbase.app.controllers.api;

import com.marketbase.app.beans.DeployLog;
import com.marketbase.app.beans.SimpleResponse;
import com.marketbase.app.models.Module;
import com.marketbase.app.models.Order;
import com.marketbase.app.models.OrderProperties;
import com.marketbase.app.proxies.ResourcesServiceProxy;
import com.marketbase.app.proxies.TechmanagersServiceProxy;
import com.marketbase.app.repositories.ModuleRepository;
import com.marketbase.app.repositories.OrderRepository;
import com.marketbase.app.repositories.TemplateRepository;
import com.marketbase.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
public class OrderAPIController {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	UserService userService;

	@Autowired
	ResourcesServiceProxy resourcesServiceProxy;

	@Autowired
	TechmanagersServiceProxy techmanagersServiceProxy;

	@GetMapping("/{id}")
	public Order getOrder(@PathVariable Long id) {
		return orderRepository.findById(id).get();
	}

	@PostMapping("/{id}/serverCredential")
	public SimpleResponse saveServerCredential(@PathVariable("id") Long id,
											   @RequestParam String serverIP,
											   @RequestParam String domainName,
											   @RequestParam String serverUser,
											   @RequestParam String serverPassword) {
		// save order info
		Order order = orderRepository.getOne(id);

		order.setServerIP(serverIP);
		order.setDomainName(domainName);
		order.setServerUser(serverUser);
		order.setServerUserPassword(serverPassword);

		order.setStatus(new OrderProperties().READY_TO_DEPLOY);

		orderRepository.save(order);

		// send deploy ticket to tech managers
		techmanagersServiceProxy.createDeployTicket(id);

		return new SimpleResponse(200, "Server credential was changed.");
	}

	@PostMapping("/{id}/complete")
	public SimpleResponse completeOrder(@PathVariable Long id, @RequestParam String result) {
		Order order = orderRepository.getOne(id);
		OrderProperties orderProperties = new OrderProperties();

		if (result.equals("SUCCESS")) {
			order.setStatus(orderProperties.COMPLETED);
		} else if (result.equals("FAILED")) {
			order.setStatus(orderProperties.FAILED);
		}

		orderRepository.save(order);
		return new SimpleResponse(200, "");
	}

	@GetMapping("/{id}/logs")
	public List<DeployLog> orderDeployLogs(@PathVariable Long id) {
		return resourcesServiceProxy.getDeployLogs(id);
	}

	@PostMapping("/{id}/deploy")
	public SimpleResponse deployProject(@PathVariable("id") Long id) throws Exception {
		Order order = orderRepository.getOne(id);

		// send request to deploy
		SimpleResponse response = resourcesServiceProxy.deploy(
				order.getId(),
				order.getServerIP(),
				order.getServerUser(),
				order.getServerUserPassword()
		);

		if (response.getCode() == 200) {
			order.setStatus(new OrderProperties().DEPLOYING);
			orderRepository.save(order);

			return new SimpleResponse(
					200,
					"Deploying application"
			);
		} else {
			order.setStatus(new OrderProperties().FAILED);
			orderRepository.save(order);
			return new SimpleResponse(500, response.getMessage());
		}
	}

	@PostMapping("")
	public SimpleResponse saveOrder(@RequestParam Long templateId,
									@RequestParam String modules) {

		// connect chosen modules to the order
		Set<Module> connectedModules = new HashSet<>();
		Float total = 0.0f;
		for (String module : modules.split(";")) {
			Module m = moduleRepository.getOne(Long.parseLong(module));
			connectedModules.add(m);
			total += m.getPrice();
		}

		Order order = new Order(
				userService.getCurrentUser(),
				templateRepository.getOne(templateId),
				total,
				"Created",
				connectedModules
		);

		orderRepository.save(order);
		return new SimpleResponse(200, "Order was created.");
	}
}
