package com.jp.omo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	@Query("Select cpn from CouponUsages cpn where cpn.userId =?1 AND cpn.couponCode=?2 AND cpn.softDeleteFlag=0")
	List<CouponUsages> findAllCouponUsagesByUserIdAndCouponCode(Long userId,String couponCode);
	
	/**
	 * 
	 * @param bookingId
	 * @return
	 */
	CouponUsages findCouponUsagesByBookingId(Long bookingId);
}
