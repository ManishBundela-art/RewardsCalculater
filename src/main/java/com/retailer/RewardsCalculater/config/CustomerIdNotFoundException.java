package com.retailer.RewardsCalculater.config;

/**
 * used for Create customer Uncheck Exception 
 */
public class CustomerIdNotFoundException extends RuntimeException{

	public CustomerIdNotFoundException(String message) {
		super(message);
	}
}
