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
@Table(name = "user_referral_rewards")
public class UserReferralReward {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_ref_reward_id",updatable = false, nullable = false)
	private long userRefRewardId;
	
	@Column(name = "referrer_id")
	private long referrerId;
	
	@Column(name = "referrer_name")
	private String referrerName;
	
	@Column(name = "referred_user_name")
	private String referredUserName;
	
	@Column(name = "referred_user_id")
	private long referredUserId;
	
	@Column(name = "reward_type")
	private String rewardType;
	
	@Column(name = "reward_value")
	private String rewardValue;
	
	@Column(name = "reward_code")
	private String rewardCode;
	
	@Column(name = "reward_info")
	private String rewardInfo;
}
