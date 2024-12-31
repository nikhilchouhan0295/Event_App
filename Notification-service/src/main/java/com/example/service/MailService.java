package com.example.service;

import org.springframework.http.ResponseEntity;

import com.example.dto.MailDetails;

public interface MailService {
	
	ResponseEntity<?> sendMail(MailDetails details);

}
