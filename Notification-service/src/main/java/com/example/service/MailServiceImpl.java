package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.dto.MailDetails;

@Service
public class MailServiceImpl implements MailService {
	
	@Autowired 
	JavaMailSender javaMailSender;
	
	@Value("{spring.mail.username}")
	private String sender;

	public ResponseEntity<?> sendMail(MailDetails details) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(sender);
		mailMessage.setTo(details.getRecipient());
		mailMessage.setSubject(details.getSubject());
		mailMessage.setText(details.getMsgBody());
		
		javaMailSender.send(mailMessage);
		
		return new ResponseEntity<>(true,HttpStatus.OK);
	}

	
	
}
