package com.jp.omo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.jp.omo.component.ApplicationConstant;
import com.jp.omo.component.OfferValidatorComponent;
import com.jp.omo.component.OfrmoUtility;
import com.jp.omo.dto.AvailAndSaveCouponUsageDto;
import com.jp.omo.dto.CouponDetailDto;
import com.jp.omo.dto.JpResponseModel;
import com.jp.omo.dto.VerifyCouponDto;
import com.jp.omo.repository.CouponUsageRepository;
import com.jp.omo.repository.OfferDetailRepository;
import com.jp.omo.repository.entity.CouponUsages;
import com.jp.omo.repository.entity.OfferDetail;
import com.jp.omo.service.OfferService;
import com.jp.omo.service.ReferralService;

@Service
public class OfferServiceImpl implements OfferService {

	@Autowired
	private OfferDetailRepository offerDetailRepository;
	@Autowired(required = true)
	private ModelMapper modelMapper;
	@Autowired
	private CouponUsageRepository cpnUsgRepository;
	@Autowired
	private OfrmoUtility ofrmoUtility;
	@Autowired
	private ReferralService referralService;

	// 1. active/Inactive
	// 2. date
	// 3. count of user usage
	// 4. on total count
	// 5. total golable amount
	@Override
	@Transactional
	public JpResponseModel validateCouponService(long userId, VerifyCouponDto verifyCouponDto) {
		CouponDetailDto responseDto = new CouponDetailDto();
		
		try {
			OfferDetail offerDetail = offerDetailRepository.findOfferDetail(verifyCouponDto.getCouponCode());

			if (offerDetail == null) {
				return referralService.validateReferalCode(userId, verifyCouponDto);// call referral flow
				//responseDto.setStatus(ApplicationConstant.CouponNotFound.name());
				//return ofrmoUtility.prepareResponse(responseDto, "There is no such coupon available.", true);
			}
			List<CouponUsages> cpnUsages = cpnUsgRepository.findAllUedCouponForUser(userId,
					verifyCouponDto.getCouponCode());
			LocalDateTime offerStartTime = offerDetail.getStartTime();
			LocalDateTime offerEndTime = offerDetail.getEndTime();
			// coupon usage
			// total coupon count
			// active code
			System.out.println("true");
			if ((cpnUsages.size() < offerDetail.getUserMaxCountToAvailCpn())) {

				if (offerDetail.getTotalCouponCount() > offerDetail.getUsedCouponCount()) {

					if (offerStartTime.equals(verifyCouponDto.getAvailDate())
							|| verifyCouponDto.getAvailDate().isAfter(offerStartTime)

									&& (verifyCouponDto.getAvailDate().isBefore(offerEndTime)
											|| offerEndTime.equals(verifyCouponDto.getAvailDate()))) {
						Integer statusInCode = new Integer(ApplicationConstant.Active.getStatus());
						if (offerDetail.getStatus().equals(statusInCode)) {

							responseDto = modelMapper.map(offerDetail, CouponDetailDto.class);
							responseDto.setStatus(ApplicationConstant.Applicable.name());
							if (offerDetail.getDiscountType() == 1) {
								responseDto.setDiscountType(ApplicationConstant.Percentage.name());
							} else if (offerDetail.getDiscountType() == 2) {
								responseDto.setDiscountType(ApplicationConstant.fixedValue.name());
							}

						} else {
							responseDto.setStatus(ApplicationConstant.Inactive.name());

						}
					} else {
						responseDto.setStatus(ApplicationConstant.CouponExpired.name());
					}

				} else {
					responseDto.setStatus(ApplicationConstant.CouponCountExhausted.name());

				}

			} else {
				responseDto.setStatus(ApplicationConstant.UserUsageLimitExceeded.name());
			}
			responseDto.setOfferType("coupon");
			return ofrmoUtility.prepareResponse(responseDto, "couponCode", true);
		} catch (Exception e) {
			e.printStackTrace();

			return ofrmoUtility.prepareResponse(responseDto, "couponCode", false);
		}
	}

	@Transactional
	@Override
	public JpResponseModel availCouponSaveService(Long userId,AvailAndSaveCouponUsageDto availAndSaveCpnUsgDto) {
		CouponDetailDto responseDto = new CouponDetailDto();
		responseDto.setOfferType("coupon");
		try {
		OfferDetail offerDetail = offerDetailRepository.findOfferDetail(availAndSaveCpnUsgDto.getCouponCode());
		if(offerDetail == null) {
			responseDto.setStatus(ApplicationConstant.CouponNotAvailable.getStatus());
			return ofrmoUtility.prepareResponse(responseDto,"",true);
			
		}
		CouponUsages cpnUsg = new CouponUsages();
		cpnUsg.setBookingId(availAndSaveCpnUsgDto.getBookingId());
		cpnUsg.setCouponCode(availAndSaveCpnUsgDto.getCouponCode());
		cpnUsg.setUserId(userId);
		cpnUsg.setCouponId(offerDetail.getCouponId());
		cpnUsg.setUsedDatetime(LocalDateTime.now());
		cpnUsg.setDiscountAmount(availAndSaveCpnUsgDto.getDiscountAmount());
		cpnUsg.setSoftDeleteFlag((byte)0);
		
		cpnUsgRepository.saveAndFlush(cpnUsg);
		int usedCpnCount = (int)(offerDetail.getUsedCouponCount())+1;
		offerDetail.setUsedCouponCount(usedCpnCount);
		offerDetail.setUsedGlobalMaxAmount(offerDetail.getUsedGlobalMaxAmount()+availAndSaveCpnUsgDto.getDiscountAmount());
		offerDetailRepository.updateOfferDetail(offerDetail.getUsedGlobalMaxAmount(),
				offerDetail.getUsedCouponCount(),availAndSaveCpnUsgDto.getCouponCode());
		
		responseDto.setStatus(ApplicationConstant.CouponAvailSuccessfull.getStatus());
		return ofrmoUtility.prepareResponse(responseDto, "", true);
		
		}catch(Exception exception) {
			exception.printStackTrace();
			responseDto.setStatus(ApplicationConstant.CouponAvailfailed.getStatus());
			return ofrmoUtility.prepareResponse(responseDto, "", false);
		}
		
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Override
	@Transactional
	public JpResponseModel updateCouponOnBookingCancellation(Long bookingId) {
		try {
		CouponUsages cpnUsage = cpnUsgRepository.findCouponUsagesByBookingId(bookingId);
		OfferDetail offerDetail = offerDetailRepository.findOfferDetail(cpnUsage.getCouponCode());
		offerDetail.setUsedCouponCount(offerDetail.getUsedCouponCount() - 1);
		offerDetail.setUsedGlobalMaxAmount(offerDetail.getUsedGlobalMaxAmount() - cpnUsage.getDiscountAmount());
		offerDetailRepository.saveAndFlush(offerDetail);
		cpnUsage.setSoftDeleteFlag((byte) 1);
		cpnUsgRepository.save(cpnUsage);
		return ofrmoUtility.prepareResponse(null, "", true);
	}catch(Exception e) {
		return ofrmoUtility.prepareResponse(null,"",false);
	}
	}
	
}
