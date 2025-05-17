package com.retailer.RewardsCalculater.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.retailer.RewardsCalculater.dto.Rewards;
import com.retailer.RewardsCalculater.entity.Transaction;
import com.retailer.RewardsCalculater.repository.TransactionRepository;

@Service
public class RewardsServiceImpl implements RewardsService{

	@Autowired
	TransactionRepository transactionRepository;
	
	@Value(value="${spring.days.in.months}")
	private String daysInMonth;
	
	@Value(value="${spring.first.reward.limit}")
	private String firstRewardLimit;

	@Value(value="${spring.second.reward.limit}")
	private String secondRewardLimit;
	
	/**
	 * return total Rewards Calculate bases on month.. 
	 */
	@Override
	public Rewards getRewardsByCustomerId(Long customerId) {
		
		int daysInMonths=Integer.valueOf(daysInMonth);
		Timestamp lastMonthTimestamp = getDateBasedOnOffSetDays(daysInMonths);
		Timestamp lastSecondMonthTimestamp = getDateBasedOnOffSetDays(2*daysInMonths);
		Timestamp lastThirdMonthTimestamp = getDateBasedOnOffSetDays(3*daysInMonths);
		//it's used for find previous month of date. 
		
		//find list of Transaction month based..
		List<Transaction> lastMonthTransactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(
				customerId, lastMonthTimestamp, Timestamp.from(Instant.now()));
		List<Transaction> lastSecondMonthTransactions = transactionRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);
		List<Transaction> lastThirdMonthTransactions = transactionRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, lastThirdMonthTimestamp,
						lastSecondMonthTimestamp);
		
		Long lastMonthRewardPoints = getRewardsPerMonth(lastMonthTransactions);
		Long lastSecondMonthRewardPoints = getRewardsPerMonth(lastSecondMonthTransactions);
		Long lastThirdMonthRewardPoints = getRewardsPerMonth(lastThirdMonthTransactions);
		
		Rewards customerRewards = new Rewards();
		customerRewards.setCustomerId(customerId);
		customerRewards.setLastMonthRewardPoints(lastMonthRewardPoints);
		customerRewards.setLastSecondMonthRewardPoints(lastSecondMonthRewardPoints);
		customerRewards.setLastThirdMonthRewardPoints(lastThirdMonthRewardPoints);
		customerRewards.setTotalRewards(lastMonthRewardPoints + lastSecondMonthRewardPoints + lastThirdMonthRewardPoints);

		return customerRewards;
	}

	/**
	 * @param days
	 * @return before month date based on daysofMonth 
	 */
	public Timestamp getDateBasedOnOffSetDays(int days) {
		return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
	}

	/**
	 * @param transactions
	 * @return get this list and calculate the all value in single unit. 
	 */
	private Long getRewardsPerMonth(List<Transaction> transactions) {
		return transactions.stream().map(transaction -> calculateRewards(transaction))
				.collect(Collectors.summingLong(r -> r.longValue()));
	}
	
	/**
	 * @param t
	 * @return calculate list of value bases on month
	 */
	private Long calculateRewards(Transaction t) {
		int firstRewardLimits=Integer.valueOf(firstRewardLimit);
		int secondRewardLimits=Integer.valueOf(secondRewardLimit);
		//>50 && <100
		if (t.getTransactionAmount() > firstRewardLimits && t.getTransactionAmount() <= secondRewardLimits) {
			return Math.round(t.getTransactionAmount() - firstRewardLimits);
		} else if (t.getTransactionAmount() > secondRewardLimits) {
			return Math.round(t.getTransactionAmount() - secondRewardLimits) * 2
					+ (secondRewardLimits - firstRewardLimits);
		} else
			return 0L;		
	}
}
