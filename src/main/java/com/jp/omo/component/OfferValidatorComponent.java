package com.jp.omo.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jp.omo.dto.CouponDetailDto;
import com.jp.omo.repository.CouponUsageRepository;
import com.jp.omo.repository.entity.CouponUsages;
import com.jp.omo.repository.entity.OfferDetail;

/**
 * 
 * @author EHTESHAM MAZHAR
 *
 */
@Component
public class OfferValidatorComponent {

	/*
	 * @Autowired private CouponUsageRepository cpnUsgRepository;
	 * 
	 * 
	 * public Boolean validateCouponCode(OfferDetail offerDetail,CouponDetailDto
	 * cdDto) { int discountType = offerDetail.getDiscountType();
	 * 
	 * if(cpnUsg==null) { CouponUsages cpnUsages = new CouponUsages();
	 * cpnUsages.setBookingId(cdDto.getBookingId());
	 * cpnUsages.setCouponCode(cdDto.getCouponCode());
	 * cpnUsages.setUserId(cdDto.getUserId());
	 * cpnUsages.setUsedDatetime(cdDto.getCouponUsageTime());
	 * cpnUsgRepository.save(cpnUsages); return true; } return false; }
	 * 
	 * 
	 * double couponValue = offerDetail.getCouponValue();
	 * if(offerDetail.getTotalCount()>=offerDetail.getRemainingCount() ) {
	 * 
	 * }
	 * 
	 * 
	 * return false; }
	 */
}
