package com.marketbase.admin.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailClient {

//	@Autowired
//	private JavaMailSender emailSender;
//
//	public void sendEmail(String to, String subject, String text) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom("sergioburik@gmail.com");
//		message.setTo(to);
//		message.setSubject(subject);
//		message.setText(text);
//		emailSender.send(message);
//	}
}