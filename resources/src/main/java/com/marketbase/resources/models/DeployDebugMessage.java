package com.marketbase.resources.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class DeployDebugMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long orderId;

	private String message;

	private String type;

	private Date dateTime;

	public DeployDebugMessage() {
	}

	public DeployDebugMessage(Long orderId, String message, String type, Date dateTime) {
		this.orderId = orderId;
		this.message = message;
		this.type = type;
		this.dateTime = dateTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
