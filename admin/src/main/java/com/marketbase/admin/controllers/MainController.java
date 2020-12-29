package com.marketbase.admin.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class MainController {

	public String index() {
		return "main/index";
	}

}
