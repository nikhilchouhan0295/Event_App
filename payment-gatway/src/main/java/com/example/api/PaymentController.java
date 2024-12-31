package com.example.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.BankAccounts;
import com.example.model.RequestPamentCredential;
import com.example.service.PaymentSerrvice;

@RestController
@RequestMapping("/paymentApi")
public class PaymentController {
	
	@Autowired
	private PaymentSerrvice paymentService ;
	
	@GetMapping("/")
	public ResponseEntity<?> descCourse(){
		return new ResponseEntity<>("Bank Api Working",HttpStatus.OK);
	}
	
	
	@PostMapping("/createUserAccout")
	public ResponseEntity<?> createUserAccout(@RequestBody BankAccounts account){
		paymentService.createUserAccout(account);
		
		return new ResponseEntity<BankAccounts>(account,HttpStatus.CREATED);
	}
	
	
	@PostMapping("/paymentViaUpi")
	public ResponseEntity<?> paymentViaUpi(@RequestBody RequestPamentCredential rqPayment){
		boolean b =  paymentService.paymentViaUpi(rqPayment);
		return ResponseEntity.status(HttpStatus.OK).body(b);
	}
	
	
	@PostMapping("/paymentViaAccNu")
	public ResponseEntity<?> paymentViaAccNu(@RequestBody RequestPamentCredential rqPayment){
		boolean b = paymentService.paymentViaAccNu(rqPayment);
		return ResponseEntity.status(HttpStatus.OK).body(b);
	}
	

	
	
	
}
