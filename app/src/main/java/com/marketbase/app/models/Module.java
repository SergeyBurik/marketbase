package com.marketbase.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "module_")
public class Module {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "template_id")
	@JsonIgnore
	private Template template;

	private String name;

	private String moduleName;

	private Float price;

	@ManyToMany
	private Set<Order> orders;

	@Column(columnDefinition = "TEXT")
	private String description;

	public Module(Long id, Template template, String name, String moduleName, Float price, String description) {
		this.id = id;
		this.template = template;
		this.name = name;
		this.moduleName = moduleName;
		this.price = price;
		this.description = description;
	}

	public Module() {
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
