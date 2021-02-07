package com.marketbase.app.controllers;

import com.marketbase.app.beans.SimpleResponse;
import com.marketbase.app.models.Module;
import com.marketbase.app.models.Order;
import com.marketbase.app.models.OrderProperties;
import com.marketbase.app.proxies.ResourcesServiceProxy;
import com.marketbase.app.repositories.ModuleRepository;
import com.marketbase.app.repositories.OrderRepository;
import com.marketbase.app.repositories.TemplateRepository;
import com.marketbase.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserService userService;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	ResourcesServiceProxy resourcesServiceProxy;

	@GetMapping("")
	public String orders(Model model) {
		model.addAttribute("orders", orderRepository.findAllByUser(userService.getCurrentUser()));
		return "orders/orders";
	}

	@GetMapping("/{id}")
	public String order(@PathVariable("id") Long id,
						Model model) {
		model.addAttribute("order", orderRepository.getOne(id));
		model.addAttribute("orderProperties", new OrderProperties());

		return "orders/order";
	}

	@PostMapping("/{id}/serverCredential")
	public String saveServerCredential(@PathVariable("id") Long id,
									   @RequestParam String paymentGateway,
									   @RequestParam String serverIP,
									   @RequestParam String domainName,
									   @RequestParam String paymentGatewayAPI,
									   @RequestParam Integer serverPort,
									   @RequestParam String serverUser) {
		Order order = orderRepository.getOne(id);

		order.setPaymentGatewayType(paymentGateway);
		order.setPaymentGatewayAPI(paymentGatewayAPI);

		order.setServerIP(serverIP);
		order.setServerPort(serverPort);
		order.setDomainName(domainName);
		order.setServerUser(serverUser);

		order.setStatus(new OrderProperties().READY_TO_DEPLOY);

		orderRepository.save(order);

		return "redirect:/orders/" + id;
	}

	@PostMapping("/{id}/deploy")
	public String deployProject(@PathVariable("id") Long id) throws Exception {
		Order order = orderRepository.getOne(id);

		// send request to deploy
		String modules = "";
		for (Module m: order.getModules()) { modules += m.getName() + ";"; }

		SimpleResponse response = resourcesServiceProxy.deploy(
				order.getId(),
				order.getServerIP(),
				order.getServerUser(),
				order.getServerUserPassword(),
				order.getDomainName()
		);

		if (response.getCode() == 200) {
			order.setStatus("DEPLOYED");
			orderRepository.save(order);

			return "redirect:/orders/" + id;
		} else {
			throw new Exception(response.getMessage());
		}
	}

	@GetMapping("/new")
	public String newOrder(Model model) {
		model.addAttribute("templates", templateRepository.findAll());
		return "orders/new";
	}

	@PostMapping("/new")
	public String saveOrder(@RequestParam Long templateId,
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
				"pending",
				connectedModules
		);

		orderRepository.save(order);
		return "redirect:/orders";
	}

}
