package com.example.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.MailDetails;
import com.example.service.MailService;

import jakarta.validation.Valid;

@RestController
public class EmailContorller {
	
	@Autowired private MailService service;
	
	@PostMapping("/sendMail")
	
	public ResponseEntity<?> sendMail(@RequestBody @Valid  MailDetails details){
		System.out.println(details);
		return service.sendMail(details);
	}
}
