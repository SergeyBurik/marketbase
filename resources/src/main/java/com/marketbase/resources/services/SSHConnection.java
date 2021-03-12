package com.marketbase.resources.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SSHConnection {
	private String user;
	private String password;
	private String host;

	public SSHConnection(String user, String password, String host) {
		this.user = user;
		this.password = password;
		this.host = host;
	}

	public void execute(String command) throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("sshpass -p '" + password + "' ssh " + user + "@" + host + "'" + command +"'");

		BufferedReader stdInput = new BufferedReader(new
				InputStreamReader(proc.getInputStream()));

		BufferedReader stdError = new BufferedReader(new
				InputStreamReader(proc.getErrorStream()));

		// Read the output from the command
		System.out.println("Output:\n");
		String s = null;
		while ((s = stdInput.readLine()) != null) {
			System.out.println(s);
		}

		// Read any errors from the attempted command
		System.out.println("Errors:\n");
		while ((s = stdError.readLine()) != null) {
			System.out.println(s);
		}
	}
}
