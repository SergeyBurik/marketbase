package com.marketbase.app.models;

import javax.persistence.*;

@Entity
@Table(name = "order_")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "usr_id")
	private User user;

	@ManyToOne
	@JoinColumn(name="template_id")
	private Template template;

	private Float total;

	private String status;

	public Order(Long id, User user, Template template, Float total, String status) {
		this.id = id;
		this.user = user;
		this.template = template;
		this.total = total;
		this.status = status;
	}

	public Order() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
