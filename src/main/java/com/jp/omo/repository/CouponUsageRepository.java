package com.jp.omo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jp.omo.repository.entity.CouponUsages;

@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsages, Long> {

	/**
	 * 
	 * @param userId
	 * @param couponCode
	 * @return
	 */
	
	List<CouponUsages> findAllCouponUsagesByUserIdAndCouponCode(Long userId,String couponCode);
}
