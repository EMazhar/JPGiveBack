package com.jp.omo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jp.omo.dto.AvailAndSaveCouponUsageDto;
import com.jp.omo.dto.JpResponseModel;
import com.jp.omo.dto.VerifyCouponDto;
import com.jp.omo.service.OfferService;
import com.jp.omo.service.ReferralService;

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
	@Autowired
	private ReferralService referralService;

	@GetMapping(value = "/health")
	public String getHealth() {
		return "Hi, Welcome to Offer Module !";
	}

	@PostMapping(value = "/validateOffer/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JpResponseModel> validateCoupon(@RequestBody VerifyCouponDto verifyCouponDto, @PathVariable long userId) {
		
		return  new ResponseEntity<JpResponseModel>(offerService.validateCouponService(userId, verifyCouponDto),
					HttpStatus.OK);		
	}


	/**
	 * this service is being called while booking and availing coupon/referral code
	 * and this will update the coupon usage / used referral
	 * 
	 * @param availCouponUsageDto
	 * @param userId
	 * @return
	 */
	@PostMapping(value = "/availCoupon/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JpResponseModel> availAndSaveCouponUsage(
			@RequestBody AvailAndSaveCouponUsageDto availCouponUsageDto, @PathVariable long userId) {
		
		ResponseEntity<JpResponseModel> response = null;

		if (availCouponUsageDto.getOfferType().equalsIgnoreCase("couponCode")) {
			response = new ResponseEntity<JpResponseModel>(
					offerService.availCouponSaveService(userId, availCouponUsageDto), HttpStatus.OK);
		} else if (availCouponUsageDto.getOfferType().equalsIgnoreCase("referralCode")) {
			response = new ResponseEntity<JpResponseModel>(
					referralService.availReferralSaveService(userId, availCouponUsageDto), HttpStatus.OK);
		}
		return response;
	}

	/**
	 * @request Payload : booking id of Long type
	 * @param requestMap
	 * @return
	 */
	@PostMapping(value = "/updateCoupon{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JpResponseModel> updateCouponAndUsage(@PathVariable long bookingId) {
		//long bookingId = Long.parseLong((String)requestMap.get("bookingId"));
		
		return new ResponseEntity<JpResponseModel>
		(offerService.updateCouponOnBookingCancellation(bookingId),HttpStatus.OK);
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	@PostMapping(value = "/fetchReferralCode/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JpResponseModel> getReferralCode(@PathVariable long userId) {
		//long userId = Long.parseLong((String)requestMap.get("userId"));
		return new ResponseEntity<JpResponseModel>(referralService.referralCodeProvideService(userId), HttpStatus.OK);
	}

}
