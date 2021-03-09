package com.marketbase.admin.beans;

import org.springframework.web.multipart.MultipartFile;

public class UserWithAvatar extends SimpleUser {

	private MultipartFile avatar;

	public UserWithAvatar(String username, String first_name, String last_name, String password, String email, MultipartFile avatar) {
		super(username, first_name, last_name, password, email);
		this.avatar = avatar;
	}

	public MultipartFile getAvatar() {
		return avatar;
	}

	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}
}
