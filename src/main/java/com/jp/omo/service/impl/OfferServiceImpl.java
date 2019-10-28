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
import com.jp.omo.dto.AvailAndSaveCouponUsageDto;
import com.jp.omo.dto.CouponDetailDto;
import com.jp.omo.dto.VerifyCouponDto;
import com.jp.omo.repository.CouponUsageRepository;
import com.jp.omo.repository.OfferDetailRepository;
import com.jp.omo.repository.entity.CouponUsages;
import com.jp.omo.repository.entity.OfferDetail;
import com.jp.omo.service.OfferService;

@Service
public class OfferServiceImpl implements OfferService {

	@Autowired
	private OfferDetailRepository offerDetailRepository;
	@Autowired(required = true)
	private ModelMapper modelMapper;
	@Autowired
	private CouponUsageRepository cpnUsgRepository;
	
	
	//1. active/Inactive
	//2. date
	//3. count of user usage
	//4. on total count
	//5. total golable amount
	@Override
	@Transactional
	public CouponDetailDto validateCouponService(long userId, VerifyCouponDto verifyCouponDto) {
		CouponDetailDto responseDto = new CouponDetailDto();
		try {
			OfferDetail offerDetail = offerDetailRepository.findByCouponCode(verifyCouponDto.getCouponCode());
			
			if(offerDetail == null) {
				throw new Exception("there is no record found in the table with this coupon code "+verifyCouponDto.getCouponCode());
			}
			List<CouponUsages> cpnUsages = cpnUsgRepository.findAllCouponUsagesByUserIdAndCouponCode(userId,
					verifyCouponDto.getCouponCode());
			LocalDateTime offerStartTime = offerDetail.getStartTime();
			LocalDateTime offerEndTime = offerDetail.getEndTime();
			// coupon usage
			// total coupon count
			// active code
			System.out.println("true");
			if ((cpnUsages.size() < offerDetail.getUserMaxCountToAvailCpn())) {
				
				if (offerDetail.getTotalCouponCount() > offerDetail.getUsedCouponCount()) {
					
					if (offerStartTime.equals(verifyCouponDto.getAvailDate()) || verifyCouponDto.getAvailDate().isAfter(offerStartTime)
							
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
					}else {
						responseDto.setStatus(ApplicationConstant.CouponExpired.name());
					}

				} else {
					responseDto.setStatus(ApplicationConstant.CouponCountExhausted.name());
					
				}

			} else {
				responseDto.setStatus(ApplicationConstant.UserUsageLimitExceeded.name());
			}
			return responseDto;
		}catch(Exception e) {
			e.printStackTrace();
			responseDto.setStatus(ApplicationConstant.CouponNotFound.name());
			return responseDto;
		}
}
	

	@Transactional
	@Override
	public CouponDetailDto availCouponSaveService(Long userId,AvailAndSaveCouponUsageDto availAndSaveCpnUsgDto) {
		CouponDetailDto responseDto = new CouponDetailDto();
		
		try {
		OfferDetail offerDetail = offerDetailRepository.findByCouponCode(availAndSaveCpnUsgDto.getCouponCode());
		
		CouponUsages cpnUsg = new CouponUsages();
		cpnUsg.setBookingId(availAndSaveCpnUsgDto.getBookingId());
		cpnUsg.setCouponCode(availAndSaveCpnUsgDto.getCouponCode());
		cpnUsg.setUserId(userId);
		cpnUsg.setCouponId(offerDetail.getCouponId());
		
		cpnUsg.setDiscountAmount(availAndSaveCpnUsgDto.getDiscountAmount());
	
		cpnUsgRepository.saveAndFlush(cpnUsg);
		
		offerDetail.setUsedCouponCount(offerDetail.getUsedCouponCount()+1);
		offerDetail.setUsedGlobalMaxAmount(offerDetail.getUsedGlobalMaxAmount()+availAndSaveCpnUsgDto.getDiscountAmount());
		offerDetailRepository.updateOfferDetail(offerDetail.getUsedGlobalMaxAmount(),
				offerDetail.getUsedCouponCount(),availAndSaveCpnUsgDto.getCouponCode());
		
		responseDto.setStatus(ApplicationConstant.CouponAvailSuccessfull.getStatus());
		
		}catch(Exception exception) {
			exception.printStackTrace();
			responseDto.setStatus(ApplicationConstant.CouponAvailfailed.getStatus());
		}
		return responseDto;
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}


	@Override
	@Transactional
	public void updateCouponOnBookingCancellation(Long bookingId) {
		CouponUsages cpnUsage = cpnUsgRepository.findCouponUsagesByBookingId(bookingId);
		OfferDetail offerDetail = offerDetailRepository.findOfferDetailByCouponCode(cpnUsage.getCouponCode());
		offerDetail.setUsedCouponCount(offerDetail.getUsedCouponCount()-1);
		offerDetail.setUsedGlobalMaxAmount(offerDetail.getUsedGlobalMaxAmount()-cpnUsage.getDiscountAmount());
		offerDetailRepository.saveAndFlush(offerDetail);
		cpnUsage.setSoftDeleteFlag((byte) 1);
		cpnUsgRepository.save(cpnUsage);
	}
}
