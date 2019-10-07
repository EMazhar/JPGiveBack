package com.jp.omo.repository.entity;

import java.io.Serializable;
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
@Table(name = "used_coupons")
public class CouponUsages  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "used_coupon_id")
	private Long used_coupon_id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "booking_id")
	private Long bookingId;
	
	@Column(name = "discount_amount")
	private Double discountAmount;
	
	@Column(name = "used_datetime")
	private LocalDateTime usedDatetime;
	
	@Column(name = "coupon_code")
	private String couponCode;
	
	@Column(name = "coupon_id")
	private Long couponId;
	
}
