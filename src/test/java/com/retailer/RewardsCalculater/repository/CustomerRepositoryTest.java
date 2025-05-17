package com.retailer.RewardsCalculater.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.retailer.RewardsCalculater.dto.CustomerDto;
import com.retailer.RewardsCalculater.entity.Customer;

/**
 * 
 */
@DataJpaTest
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;
	
	CustomerDto customer;
	
	@BeforeEach
	void setUp() {
		
		customer=new CustomerDto();
		customer.setCustomerId(1L);
		customer.setCustomerName("Customer01");
		
	}
 
	@AfterEach
	void tearDown() {
		customer=null;
		customerRepository.deleteAll();		
	}
	
	@Test
	void testFindByCustomerId_Success() {
		Customer customerEntity =customerRepository.findByCustomerId(customer.getCustomerId());
		assertThat(customer.getCustomerId()==customerEntity.getCustomerId());
		
	}
	
	
	@Test
	void testFindByCustomerId_Fail() {
		Customer customerEntity =customerRepository.findByCustomerId(6L);
		assertThat(customerEntity==null).isTrue();
		
	}
	
}
