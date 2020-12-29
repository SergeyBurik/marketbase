package com.marketbase.admin.controllers;

import com.marketbase.admin.beans.SimpleUser;
import com.marketbase.admin.beans.UserWithAvatar;
import com.marketbase.admin.proxies.ManagersServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/managers")
public class ManagersController {

	@Autowired
	ManagersServiceProxy managersServiceProxy;

	@GetMapping("")
	public String managers(Model model) {
		model.addAttribute("managers", managersServiceProxy.getManagers());
		System.out.println(model.getAttribute("managers"));
		return "managers/managers";
	}

	@GetMapping("/{username}")
	public String managerInfo(@PathVariable String username, Model model) {
		model.addAttribute("manager", managersServiceProxy.getManagerByUsername(username));
		return "managers/manager";
	}

	@GetMapping("/create")
	public String createManager() {
		return "admin/createManager";
	}

	@PostMapping("/create")
	public String saveManager(SimpleUser user, @RequestParam MultipartFile avatar) {
		managersServiceProxy.createManager(
				user.getUsername(), user.getFirst_name(),
				user.getLast_name(), user.getEmail(),
				user.getPassword(), avatar);
		return "redirect:/managers";
	}

}
