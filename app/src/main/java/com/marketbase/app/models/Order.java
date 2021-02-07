package com.marketbase.app.models;

import javax.persistence.*;
import java.util.*;

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

	@ManyToMany
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

	public Order(User user, Template template, Float total, String status, Set<Module> modules) {
		this.user = user;
		this.template = template;
		this.total = total;
		this.status = status;
		this.modules = modules;
	}

	public Order() {
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

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
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

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
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
