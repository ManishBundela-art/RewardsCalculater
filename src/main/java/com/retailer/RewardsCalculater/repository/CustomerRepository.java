package com.retailer.RewardsCalculater.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retailer.RewardsCalculater.entity.Customer;

/**
 * perform the CURD Logic..
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    public Customer findByCustomerId(Long customerId);
}
