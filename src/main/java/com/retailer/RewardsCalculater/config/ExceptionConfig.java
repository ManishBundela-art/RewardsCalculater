package com.retailer.RewardsCalculater.config;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Used handle the exception for Whole Application    
 */
@RestControllerAdvice
public class ExceptionConfig {

	@ExceptionHandler({CustomerIdNotFoundException.class})
	public ResponseEntity<ExceptionResponse> handleCustomerIdException(RuntimeException e){
		
		ExceptionResponse exceptionResponse=new ExceptionResponse(LocalDateTime.now(),e.getMessage(), "Customer Related Issue");
		
		return  new ResponseEntity<ExceptionResponse>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
}
