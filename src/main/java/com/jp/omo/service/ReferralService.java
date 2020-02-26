package com.jp.omo.service;

import com.jp.omo.dto.AvailAndSaveCouponUsageDto;
import com.jp.omo.dto.JpResponseModel;
import com.jp.omo.dto.VerifyCouponDto;

public interface ReferralService {

	/**
	 * 
	 * @param chefId
	 * @return
	 */
	JpResponseModel referralCodeProvideService(long chefId);
	
	/**
	 * 
	 * @param chefId
	 * @param verifyCouponDto
	 * @return
	 */
	JpResponseModel validateReferalCode(long chefId, VerifyCouponDto verifyCouponDto);
	
	/**
	 * 
	 * @param userId
	 * @param availAndSaveCouponUsageDto
	 * @return
	 */
	JpResponseModel availReferralSaveService(long userId, AvailAndSaveCouponUsageDto availAndSaveCouponUsageDto);
	
}
