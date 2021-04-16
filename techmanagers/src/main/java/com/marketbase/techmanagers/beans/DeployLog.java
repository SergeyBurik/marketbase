package com.marketbase.techmanagers.beans;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DeployLog {
	private Long id;

	private Long orderId;

	private String message;

	private String type;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dateTime;

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

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
}
