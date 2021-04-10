package com.marketbase.techmanagers.beans;

import java.util.Set;

public class Order {
	private Long id;

	private User user;

	private Template template;

	private Set<Module> modules;

	private Float total;

	private String status;

	// payment gateway
	private String paymentGatewayType;

	private String paymentGatewayAPI;

	// server
	private String serverIP;

	private Integer serverPort;

	private String domainName;

	private String serverUser;

	private String serverUserPassword;

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

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
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

	public String getPaymentGatewayType() {
		return paymentGatewayType;
	}

	public void setPaymentGatewayType(String paymentGatewayType) {
		this.paymentGatewayType = paymentGatewayType;
	}

	public String getPaymentGatewayAPI() {
		return paymentGatewayAPI;
	}

	public void setPaymentGatewayAPI(String paymentGatewayAPI) {
		this.paymentGatewayAPI = paymentGatewayAPI;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getServerUser() {
		return serverUser;
	}

	public void setServerUser(String serverUser) {
		this.serverUser = serverUser;
	}

	public String getServerUserPassword() {
		return serverUserPassword;
	}

	public void setServerUserPassword(String serverUserPassword) {
		this.serverUserPassword = serverUserPassword;
	}
}
