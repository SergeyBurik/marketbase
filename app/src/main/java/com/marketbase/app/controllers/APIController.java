package com.marketbase.app.controllers;

import com.marketbase.app.models.Order;
import com.marketbase.app.models.Template;
import com.marketbase.app.repositories.OrderRepository;
import com.marketbase.app.repositories.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	OrderRepository orderRepository;

	@GetMapping("/templates/{id}")
	public Template getTemplate(@PathVariable Long id) {
		return templateRepository.findById(id).get();
	}

	@GetMapping("/orders/{id}")
	public Order getOrder(@PathVariable Long id) {
		return orderRepository.getOne(id);
	}
}
