package com.marketbase.app.controllers.api;


import com.marketbase.app.models.Template;
import com.marketbase.app.repositories.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/templates")
public class TemplatesAPIController {

	@Autowired
	TemplateRepository templateRepository;

	@GetMapping("/{id}")
	public Template getTemplate(@PathVariable Long id) {
		return templateRepository.findById(id).get();
	}
}
