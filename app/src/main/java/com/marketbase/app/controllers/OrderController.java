package com.marketbase.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {

	@GetMapping("/new")
	public String newOrder() {
		return "orders/new";
	}

}
