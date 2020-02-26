package com.jp.omo.service;

import com.jp.omo.dto.AvailAndSaveCouponUsageDto;
import com.jp.omo.dto.CouponDetailDto;
import com.jp.omo.dto.JpResponseModel;
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
	JpResponseModel validateCouponService(long chefId, VerifyCouponDto verifyCouponDto);
	
	/**
	 * 
	 * @param availAndSaveCpnUsgDto
	 * @return status of avail coupon success or Failed
	 */
	JpResponseModel availCouponSaveService(Long userId ,AvailAndSaveCouponUsageDto availAndSaveCpnUsgDto);
	
	/**
	 * 
	 * @param bookingId
	 */
	JpResponseModel updateCouponOnBookingCancellation (Long bookingId);
	
	
}
