package com.example.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.exception.PaymentException;

@ControllerAdvice
public class MyExceptionHandler {
	
	@ExceptionHandler(PaymentException.class)
	public ResponseEntity<?> courseExceptionHandler(PaymentException ex){
		System.out.println(ex.getMessage());
		
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
}
