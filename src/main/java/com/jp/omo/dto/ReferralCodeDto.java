package com.jp.omo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferralCodeDto {
	private String referralCode;
	//private Long userId;
	private double codeValue;
	private String discountType;
	private double minOrderAmount;
	private String status;
	private LocalDateTime endDate;
	private Double maxDiscountAmount;
	
}
