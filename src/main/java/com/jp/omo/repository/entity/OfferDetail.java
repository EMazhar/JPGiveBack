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
@Table(name = "all_coupons")
public class OfferDetail implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coupon_id")
	private Long couponId;
	
	@Column(name = "coupon_code")
	private String couponCode;
	
	@Column(name = "coupon_value")
	private Double couponValue;

	@Column(name = "end_time")
	private LocalDateTime endTime;
	
	@Column(name = "start_time")
	private LocalDateTime startTime;
	
	@Column(name = "min_order_amount")
	private Double minOrderAmount;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "max_discount_amount")
	private Double maxDiscountAmount;
	
	@Column(name = "total_coupon_count")
	private Integer totalCouponCount;
	
	@Column(name = "used_coupon_count")
	private Integer usedCouponCount;
	
	@Column(name = "user_max_count_To_avail_cpn")
	private Byte userMaxCountToAvailCpn;
	
	@Column(name ="global_max_amount")
	private Double globalMaxAmount;
	
	@Column(name = "discount_type")
	private Integer discountType;
	
	@Column(name = "can_be_clubbed_flag")
	private Byte canbeClubbeFlag;
	
	@Column(name = "used_global_max_amount")
	private Double usedGlobalMaxAmount;
	
	@Column(name = "softdeleteflag")
	private Byte softdeleteflag;
	
}
