package com.example.ExceptionResponse;
 
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
 
@ControllerAdvice
public class InputValidation {
	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<?> Validation(DateTimeParseException e){
		return new ResponseEntity<>("Invalid date format,please enter the date in 'yyyy-mm-dd' format.",HttpStatus.BAD_REQUEST);
	}
 
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> Validation(MethodArgumentNotValidException e){
		List<String> error = e.getFieldErrors().stream().map(ex->ex.getField()+": "+ex.getDefaultMessage()).toList();
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
}