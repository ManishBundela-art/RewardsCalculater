package com.retailer.RewardsCalculater.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.retailer.RewardsCalculater.dto.CustomerDto;
import com.retailer.RewardsCalculater.entity.Transaction;

@DataJpaTest
public class TransactionRepositoryTest {

	@Autowired
	private TransactionRepository transactionRepository;
	
    CustomerDto customer;
	
    Timestamp fromTimestamp,toTimestamp;
    
	@BeforeEach
	void setUp() {
		int days=30;
		customer=new CustomerDto();
		customer.setCustomerId(1L);
		customer.setCustomerName("Customer01");
		
		fromTimestamp=Timestamp.valueOf(LocalDateTime.now().minusDays(days));
		toTimestamp=Timestamp.valueOf(LocalDateTime.now().minusDays(days*3));
		System.out.println(fromTimestamp + "::" +toTimestamp);
	}
 
	@AfterEach
	void tearDown() {
		customer=null;
		transactionRepository.deleteAll();		
		//fromTimestamp=null;
		//toTimestamp=null;
	}
	
	
	@Test
	void testfindAllByCustomerId_Found() {
		
		List<Transaction> transactions=transactionRepository.findAllByCustomerId(customer.getCustomerId());
		assertThat(!transactions.isEmpty()).isTrue();
	}
	
	@Test
	void testfindAllByCustomerId_NotFound() {
		List<Transaction> transactions=transactionRepository.findAllByCustomerId(6L);
		assertThat(transactions.isEmpty()).isTrue();	
	}
	
	
	  @Test 
	  void findAllByCustomerIdAndTransactionDateBetween_Found() {
	  List<Transaction> transactions=transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customer.getCustomerId(),
	   toTimestamp,fromTimestamp); 
	  System.out.println(transactions.get(0));
	  assertThat(!transactions.isEmpty()).isTrue(); }
	 

	@Test
    void findAllByCustomerIdAndTransactionDateBetween_NotFound() {
		List<Transaction> transactions=transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customer.getCustomerId(), fromTimestamp, Timestamp.from(Instant.now()));
		assertThat(transactions.isEmpty()).isTrue();
	}
}
