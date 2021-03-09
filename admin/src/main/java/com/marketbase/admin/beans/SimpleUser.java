package com.marketbase.admin.beans;

public class SimpleUser {

	private String username;

	private String first_name;

	private String last_name;

	private String password;

	private String email;


	public SimpleUser(String username, String first_name, String last_name, String password, String email) {
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
		this.password = password;
		this.email = email;
	}

	public SimpleUser() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
