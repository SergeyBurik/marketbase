package com.marketbase.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Template {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String preview;

	private String previewGif;

	private String projectName;

	@Column(columnDefinition = "TEXT")
	private String description;

	@OneToMany(mappedBy = "template")
	@JsonIgnore
	private Set<Order> orders;

	@OneToMany(mappedBy = "template")
	private Set<Module> modules;

	public Template() {
	}

	public Template(Long id, String name, String preview, String projectName) {
		this.id = id;
		this.name = name;
		this.preview = preview;
		this.projectName = projectName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String templateId) {
		this.projectName = templateId;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}
}
