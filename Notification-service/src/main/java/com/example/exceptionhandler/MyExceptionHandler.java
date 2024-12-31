package com.example.exceptionhandler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {
	
	@ExceptionHandler(MailException.class)
	public ResponseEntity<?> handlingMailException(MailException ex){
		return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handlingMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		List<FieldError> errList = ex.getFieldErrors();
		String errMsg = "";
		
		for(FieldError err:errList) {
			errMsg +=	err.getField()+": "+err.getDefaultMessage()+"\n";
		}
		
		
		return new ResponseEntity<>(errMsg,HttpStatus.BAD_REQUEST);
	}
	
	
}
