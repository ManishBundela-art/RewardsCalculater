package com.retailer.RewardsCalculater.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.retailer.RewardsCalculater.dto.Rewards;
import com.retailer.RewardsCalculater.entity.Customer;
import com.retailer.RewardsCalculater.repository.CustomerRepository;
import com.retailer.RewardsCalculater.repository.TransactionRepository;
import com.retailer.RewardsCalculater.service.RewardsService;

@WebMvcTest(RewardsController.class)
public class RewardsControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	RewardsService rewardsService; 
	
	@MockBean
	CustomerRepository customerRepository; 
	
	@MockBean
	TransactionRepository transactionRepository;
	
	Customer customer;
	Rewards rewards;
	
	@BeforeEach
	void SetUp() {
		customer=new Customer();
		customer.setCustomerId(1L);
		customer.setCustomerName("Manish");
		//customerRepository.save(customer);
		
		rewards=new Rewards();
		rewards.setCustomerId(1L);
		rewards.setTotalRewards(325);
	}
	
	@ParameterizedTest
	@CsvSource({"1"})
	void getRewardsByCustomerIdTest_Exception(long id) throws Exception {		
		when(customerRepository.findByCustomerId(id)).thenReturn(customer);
		when(rewardsService.getRewardsByCustomerId(id)).thenReturn(rewards);
		
	  mockMvc.perform(get("/Customer/rewards/3")).andDo(print())
	  .andExpect(status().isOk());
	}
	
	@ParameterizedTest
	@CsvSource({"1"})
	void getRewardsByCustomerIdTest(long id) throws Exception {
		
		when(customerRepository.findByCustomerId(id)).thenReturn(customer);
		when(rewardsService.getRewardsByCustomerId(id)).thenReturn(rewards);
		
	  mockMvc.perform(get("/Customer/rewards/"+id)).andDo(print())
	  .andExpect(status().isOk());
	}
}
