package com.jp.omo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jp.omo.repository.entity.OfferDetail;

/**
 * 
 * 
 * @author EHTESHAM MAZHAR
 *
 */

@Repository
public interface OfferDetailRepository extends JpaRepository<OfferDetail, Long> {

	/**
	 * 
	 * @param couponString
	 * @return
	 */
	OfferDetail findByCouponCode(String couponString);
	
	@Modifying
	@Query("UPDATE OfferDetail od SET  od.usedGlobalMaxAmount =?1, od.usedCouponCount =?2 WHERE od.couponCode =?3")
	Integer updateOfferDetail(Double usedGlobalMaxAmnt,Integer usedCouponCount,String couponCode);
}
