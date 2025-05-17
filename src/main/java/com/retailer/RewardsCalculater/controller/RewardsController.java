package com.retailer.RewardsCalculater.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.RewardsCalculater.config.CustomerIdNotFoundException;
import com.retailer.RewardsCalculater.dto.Rewards;
import com.retailer.RewardsCalculater.entity.Customer;
import com.retailer.RewardsCalculater.repository.CustomerRepository;
import com.retailer.RewardsCalculater.service.RewardsService;


/**
 * Perform the navigation logic for calculate the reward of Customer. 
 * and take input as parameter with uri.
 */
@RestController
@RequestMapping("/Customer")
public class RewardsController {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
    RewardsService rewardsService;
	
	@GetMapping("/rewards/{customerId}")
	public ResponseEntity<?> getRewardsByCustomerId(@PathVariable("customerId") Long customerId){
		    
		   Customer customer = customerRepository.findByCustomerId(Optional.ofNullable(customerId).orElse(0L));
			if(customer==null) {
				throw new CustomerIdNotFoundException("Invalid Customer Id") ;
			}	
			Rewards customerRewards = rewardsService.getRewardsByCustomerId(customerId);
	        return new ResponseEntity<>(customerRewards,HttpStatus.OK);		
	}	
}
