package com.jp.omo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDetailDto {
	private String OfferType;
	private String couponCode;
	//private Long userId;
	private double couponValue;
	private String discountType;
	private double minOrderAmount;
	private String status;
	private LocalDateTime endDate;
	private Double maxDiscountAmount;
	
}
