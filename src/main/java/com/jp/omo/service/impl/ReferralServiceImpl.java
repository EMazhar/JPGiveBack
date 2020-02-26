package com.jp.omo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.omo.component.ApplicationConstant;
import com.jp.omo.component.OfrmoUtility;
import com.jp.omo.dto.AvailAndSaveCouponUsageDto;
import com.jp.omo.dto.CouponDetailDto;
import com.jp.omo.dto.JpResponseModel;
import com.jp.omo.dto.VerifyCouponDto;
import com.jp.omo.repository.UsedReferralCodesRepository;
import com.jp.omo.repository.UserReferralCodesRepository;
import com.jp.omo.repository.UserReferralRewardRepository;
import com.jp.omo.repository.entity.UsedReferralCodes;
import com.jp.omo.repository.entity.UserReferralCodes;
import com.jp.omo.service.ReferralService;

/**
 * 
 * @author EHTESHAM MAZHAR
 *
 */
@Service
public class ReferralServiceImpl implements ReferralService {

	@Autowired
	private UserReferralCodesRepository userReferralCodesRepository;
	@Autowired
	private UserReferralRewardRepository userReferralRewardRepository;
	@Autowired
	private UsedReferralCodesRepository usedReferralCodesRepository;
	@Autowired
	private OfrmoUtility ofrmoUtility;

	@Override
	public JpResponseModel referralCodeProvideService(long userId) {
		try {
			List<UserReferralCodes> userReferralCodes = userReferralCodesRepository.findByUserId(userId);
			if (userReferralCodes.isEmpty()) {
				return ofrmoUtility.prepareResponse(userReferralCodes, "There is no referral code for "+userId, true);
			}
			return ofrmoUtility.prepareResponse(userReferralCodes, "", true);
		} catch (Exception excep) {
			return ofrmoUtility.prepareResponse(null,"", false);
		}

	}

	@Override
	public JpResponseModel validateReferalCode(long userId, VerifyCouponDto verifyCouponDto) {
		CouponDetailDto responseDto = new CouponDetailDto();
		
		try {
			UserReferralCodes userReferralCodes = userReferralCodesRepository
					.findByReferralCode(verifyCouponDto.getCouponCode());
			if (userReferralCodes == null) {
				responseDto.setStatus(ApplicationConstant.CouponNotFound.name());
				return ofrmoUtility.prepareResponse(responseDto, "There is no such referral/coupon code Active/available", true);
			}
			if (userId == userReferralCodes.getUserId()) {
				responseDto.setStatus(ApplicationConstant.CouponNotAvailable.name());
				return ofrmoUtility.prepareResponse(responseDto, "You can not avail referral code for yourself.", true);
			}

			int rfCodeUsageCount = usedReferralCodesRepository
					.countByReferralCodeId(userReferralCodes.getReferralCodeId());
			//if (verifyCouponDto.getMinBookingAmount() >= userReferralCodes.getMinOrderAmount()) {

				if (rfCodeUsageCount < userReferralCodes.getTotalUsageCount()) {
					
					if (userReferralCodes.getGlobalMaxAmount() >= (userReferralCodes.getUsedGlobalMaxAmount()
							+ userReferralCodes.getMaxDiscountAmount())) {
						
						responseDto.setMaxDiscountAmount(userReferralCodes.getMaxDiscountAmount());
						responseDto.setCouponCode(userReferralCodes.getReferralCode());
						responseDto.setMinOrderAmount(userReferralCodes.getMinOrderAmount());
						responseDto.setStatus(ApplicationConstant.Applicable.name());
						responseDto.setCouponValue(userReferralCodes.getValue());
						if (userReferralCodes.getDiscountType() == 1) {
							responseDto.setDiscountType(ApplicationConstant.Percentage.name());
						} else if (userReferralCodes.getDiscountType() == 2) {
							responseDto.setDiscountType(ApplicationConstant.fixedValue.name());
						}
						
					} else {
						responseDto.setStatus(ApplicationConstant.UserUsageLimitExceeded.name());
					}

				} else {
					responseDto.setStatus(ApplicationConstant.CouponCountExhausted.name());
				}
			/*
			 * } else { return
			 * ofrmoUtility.prepareResponse(userReferralCodes.getReferralCode(),
			 * "Minimum order amount : " + userReferralCodes.getMinOrderAmount(), true); }
			 */
				responseDto.setOfferType("referral");
				return ofrmoUtility.prepareResponse(responseDto, "", true);
		} catch (Exception exception) {
			responseDto.setStatus(ApplicationConstant.CouponVerificationFailed.name());
			return ofrmoUtility.prepareResponse(responseDto, "referralCode", false);
		}
	}

	/**
	 * here, we are saving after booking is done. 
	 */
	@Override
	@Transactional
	public JpResponseModel availReferralSaveService(long userId,
			AvailAndSaveCouponUsageDto availAndSaveCouponUsageDto) {
		CouponDetailDto responseDto = new CouponDetailDto();
		responseDto.setOfferType("referral");
		try {
			
			UserReferralCodes userReferralCodes = userReferralCodesRepository
					.findByReferralCode(availAndSaveCouponUsageDto.getCouponCode());
			if(userReferralCodes==null) {
				responseDto.setStatus(ApplicationConstant.CouponNotFound.name());
				return ofrmoUtility.prepareResponse(responseDto,"", false);
			}

			UsedReferralCodes usedReferralCodes = new UsedReferralCodes();
			usedReferralCodes.setBookingId(availAndSaveCouponUsageDto.getBookingId());
			usedReferralCodes.setDiscountAmount(availAndSaveCouponUsageDto.getDiscountAmount());
			usedReferralCodes.setUserId(userId);
			usedReferralCodes.setReferralCodeId(userReferralCodes.getReferralCodeId());
			// usedReferralCodes.setUsedDateTime();
			usedReferralCodesRepository.saveAndFlush(usedReferralCodes);
			
			userReferralCodes.setUsedCount(userReferralCodes.getUsedCount() + 1);
			userReferralCodes.setUsedGlobalMaxAmount(
					userReferralCodes.getUsedGlobalMaxAmount() + availAndSaveCouponUsageDto.getDiscountAmount());
			
			double usedGlobalAmnt = userReferralCodes.getUsedGlobalMaxAmount() + availAndSaveCouponUsageDto.getDiscountAmount();
			
			userReferralCodesRepository.updateUserReferralCodes(usedGlobalAmnt,
					userReferralCodes.getUsedCount(), userReferralCodes.getReferralCode());
			responseDto.setStatus(ApplicationConstant.CouponAvailSuccessfull.name());
			
			return ofrmoUtility.prepareResponse(responseDto, "", true);
		} catch (Exception excep) {
			responseDto.setStatus(ApplicationConstant.CouponAvailfailed.name());
			return ofrmoUtility.prepareResponse(responseDto, "", false);

		}
	}

}
