package com.retailer.RewardsCalculater.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.retailer.RewardsCalculater.dto.Rewards;

@SpringBootTest
public class RewardsServiceImplTest {

	
	@Autowired
	RewardsServiceImpl rewardsServiceImpl;
		
	
	@ParameterizedTest
	@CsvSource({"1,335"})
	void getRewardsByCustomerIdTest_Success(long id,long expection) {
		Rewards rewards =rewardsServiceImpl.getRewardsByCustomerId(id);
		long totalRewards=rewards.getTotalRewards();
		assertEquals(expection, totalRewards, "Both are equals");
	}
	
	@Test
	void getRewardsByCustomerIdTest_NotSccess() {
		Rewards rewards =rewardsServiceImpl.getRewardsByCustomerId(1L);
		//System.out.println("::" + rewards.getTotalRewards());
		assertThat(rewards.getTotalRewards()!=255).isTrue();				
		
	}
}
