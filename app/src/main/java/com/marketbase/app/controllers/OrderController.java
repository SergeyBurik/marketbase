package com.marketbase.app.controllers;

import com.marketbase.app.models.OrderProperties;
import com.marketbase.app.repositories.OrderRepository;
import com.marketbase.app.repositories.TemplateRepository;
import com.marketbase.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserService userService;

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


	@GetMapping("/new")
	public String newOrder(Model model) {
		model.addAttribute("templates_", templateRepository.findAll());
		return "orders/new";
	}


}
