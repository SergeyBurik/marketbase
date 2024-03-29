package com.marketbase.resources.services;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.marketbase.resources.repositories.AppPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class DeployService {
	@Autowired
	AppPropertyRepository appPropertyRepository;

	String serverHost = "";

	@PostConstruct
	public void init() {
		serverHost = appPropertyRepository.getByKey("host").get().getValue();
	}

	private String sendCommand(Session session, String command) throws JSchException, InterruptedException {
		ChannelExec channel = (ChannelExec) session.openChannel("exec");
		channel.setCommand(command);
		ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
		channel.setOutputStream(responseStream);
		channel.connect();

		while (channel.isConnected()) {
			Thread.sleep(100);
		}

		String response = new String(responseStream.toByteArray());

		System.out.println(command + " : " + response);
		return response;
	}

	public void deploy(String username, String password,
					   String host, Long orderId,
					   String projectName, String domainName, String modules) throws InterruptedException, JSchException, IOException {

		SSHConnection ssh = new SSHConnection(username, password, host);
		ssh.execute(
				"cd /home; curl -s " + serverHost + "/order/" + orderId + "/file/setup.py --output setup.py; " +
				"ls -l; pwd; " +
				"python3 setup.py " + orderId + " " + serverHost + " " + projectName + " " + domainName + " \"" + modules + "\""
		);


		//		Session session = null;
//		ChannelExec channel = null;
//
//		try {
//			// connect
//			session = new JSch().getSession(username, host, 22);
//			session.setPassword(password);
//			session.setConfig("StrictHostKeyChecking", "no");
//			session.connect();
//
//			// setup
//			sendCommand(session, "echo " + password + " && sudo -s");
//			sendCommand(session, "cd /home");
//			sendCommand(session, "sudo apt install curl");
//
//			// download setup script and run
//			sendCommand(session, "curl -s " + serverHost + "/order/" + orderId + "/file/setup.py --output setup.py");
//			sendCommand(session, "ls -l");
//			sendCommand(session, "pwd");
//			sendCommand(session, "cat setup.py");
//
//
//			sendCommand(
//					session,
//					"python3 setup.py " + orderId + " " + serverHost + " " + projectName + " " + domainName + " " + modules
//			);
//
//		} finally {
//			if (session != null) {
//				session.disconnect();
//			}
//			if (channel != null) {
//				channel.disconnect();
//			}
//		}
	}

}
