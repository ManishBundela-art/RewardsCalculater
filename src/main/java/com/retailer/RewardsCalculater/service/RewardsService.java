package com.retailer.RewardsCalculater.service;

import com.retailer.RewardsCalculater.dto.Rewards;

/**
 *  Perform to Business logic
 */
public interface RewardsService {
	public Rewards getRewardsByCustomerId(Long customerId);
	
	
}
