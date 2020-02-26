package com.jp.omo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailAndSaveCouponUsageDto {

	private String offerType;//"offerType":"couponCode" or "referralCode"
	private String couponCode;
	private Long userId;
	private Long bookingId;
	private Double discountAmount;
	private LocalDateTime usedTime;

}
