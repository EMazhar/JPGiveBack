package com.jp.omo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jp.omo.dto.AvailAndSaveCouponUsageDto;
import com.jp.omo.dto.CouponDetailDto;
import com.jp.omo.dto.VerifyCouponDto;
import com.jp.omo.service.OfferService;

/**
 * 
 * @author EHTESHAM MAZHAR
 *
 */

@RestController
@CrossOrigin()
public class OfferController {

	@Autowired
	private OfferService offerService;
	
	@PostMapping(value="/validateOffer/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CouponDetailDto> validateCoupon (@RequestBody VerifyCouponDto verifyCouponDto, @PathVariable long userId) {
		
		return  new ResponseEntity<CouponDetailDto>(offerService.validateCouponService(userId, verifyCouponDto),HttpStatus.OK);
	}
	
	@PostMapping(value="/availCoupon/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CouponDetailDto> availAndSaveCouponUsage (@RequestBody AvailAndSaveCouponUsageDto availCouponUsageDto, @PathVariable long userId) {
		
		return  new ResponseEntity<CouponDetailDto>(offerService.availCouponSaveService(userId, availCouponUsageDto),HttpStatus.OK);
	}
		
}
