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
@Table(name = "user_referral_codes")
public class UserReferralCodes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "referral_code_id",updatable = false, nullable = false)
	private Long referralCodeId;

	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "referral_code")
	private String referralCode;
	
	@Column(name = "value")
	private Double value;
	
	@Column(name = "activated_time")
	private LocalDateTime activationTime;
	
	@Column(name = "status")
	private String status;//check data type
	
	@Column(name = "total_usage_count")
	private Integer totalUsageCount;
	
	@Column(name = "used_count")
	private Integer usedCount;
	
	@Column(name = "max_discount_amount")
	private Double maxDiscountAmount;
	
	@Column(name = "min_order_amount")
	private Double minOrderAmount;
	
	@Column(name = "discount_type")
	private Integer DiscountType;
	
	@Column(name = "user_max_count")
	private Integer userMaxCount;
	
	@Column(name = "global_max_amount")
	private Double globalMaxAmount;
	
	@Column(name = "used_global_max_amount")
	private Double usedGlobalMaxAmount;
	
	public byte softdeleteflag;

}
