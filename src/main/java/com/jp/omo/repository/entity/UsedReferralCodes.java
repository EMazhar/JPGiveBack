package com.jp.omo.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "used_referral_codes")
public class UsedReferralCodes {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "used_ref_code_id",updatable = false, nullable = false)
	private long usedRefCodeId;
	
	@Column(name = "user_id")
	private long userId;
	
	@Column(name = "booking_id")
	private long bookingId;
	
	@Column(name = "referral_code_id")
	private long referralCodeId;
	
	@Column(name = "discount_amount")
	private double discountAmount;
	
	@Column(name = "used_datetime")
	private LocalDateTime usedDateTime;
}
