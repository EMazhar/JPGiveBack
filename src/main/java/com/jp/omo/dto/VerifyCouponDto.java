package com.jp.omo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VerifyCouponDto {

	private String couponCode;
	private LocalDateTime availDate;
}
