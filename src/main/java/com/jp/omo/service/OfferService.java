package com.jp.omo.service;

import com.jp.omo.dto.AvailAndSaveCouponUsageDto;
import com.jp.omo.dto.CouponDetailDto;
import com.jp.omo.dto.VerifyCouponDto;

/**
 * 
 * @author EHTESHAM MAZHAR
 *
 */
public interface OfferService {

	/**
	 * 
	 * @param chefId
	 * @param couponCode
	 * @return
	 */
	CouponDetailDto validateCouponService(long chefId, VerifyCouponDto verifyCouponDto);
	
	/**
	 * 
	 * @param availAndSaveCpnUsgDto
	 * @return status of avail coupon success or Failed
	 */
	CouponDetailDto availCouponSaveService(Long userId ,AvailAndSaveCouponUsageDto availAndSaveCpnUsgDto);
	
	/**
	 * 
	 * @param bookingId
	 */
	void updateCouponOnBookingCancellation (Long bookingId);
}
