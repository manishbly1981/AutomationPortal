package com.student.AutomationPortal.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.student.AutomationPortal.service.EMailService;

@Service
public class EmailServiceImpl implements EMailService{

	private JavaMailSender javaMailSender;
	
	 @Value("${spring.mail.username}")
	    private String fromEmail;
	
	public EmailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender= javaMailSender;
	}
	
	@Override
	public void sendSimpleEmail(String toMail, String subject, String body) {
		SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
		simpleMailMessage.setFrom(fromEmail);
		simpleMailMessage.setTo(toMail);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(body);
		javaMailSender.send(simpleMailMessage);
		
	}
	

}
