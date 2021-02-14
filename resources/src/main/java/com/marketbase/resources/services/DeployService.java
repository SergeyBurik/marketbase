package com.marketbase.resources.services;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class DeployService {
	@Value("${host}")
	String serverHost;

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

		System.out.println(response);
		return response;
	}

	public void deploy(String username,
					   String password,
					   String host,
					   Long orderId) {
		Session session = null;
		ChannelExec channel = null;

		try {
			// connect
			session = new JSch().getSession(username, host, 22);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();

			// setup
			sendCommand(session, "echo " + password + " && sudo -s");
			sendCommand(session, "cd /home");
			sendCommand(session, "sudo apt install curl");
			sendCommand(session, "curl -s " + "http://127.0.0.1:8888" + "/order/" + orderId + "/file/setup.py | python3 -");

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (JSchException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
		}
	}

}
