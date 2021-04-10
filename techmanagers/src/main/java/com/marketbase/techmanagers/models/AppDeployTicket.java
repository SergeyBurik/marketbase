package com.marketbase.techmanagers.models;

import javax.persistence.*;

@Entity
public class AppDeployTicket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long orderId;

	@ManyToOne
	@JoinColumn(name = "usr_id")
	private User user;

	public AppDeployTicket(Long orderId, User user) {
		this.orderId = orderId;
		this.user = user;
	}

	public AppDeployTicket(Long orderId) {
		this.orderId = orderId;
	}

	public AppDeployTicket() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
