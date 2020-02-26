package com.jp.omo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jp.omo.repository.entity.UsedReferralCodes;

@Repository
public interface UsedReferralCodesRepository extends JpaRepository<UsedReferralCodes, Long> {

	/*
	 * @Query("SELECT COUNT(urc.usedRefCodeId) from UsedReferralCodes urc where urc.referralCodeId=?1"
	 * ) int getCountOfReferralCode(long referralCodeId);
	 */
	
	int countByReferralCodeId(long referralCodeId);
	
}
