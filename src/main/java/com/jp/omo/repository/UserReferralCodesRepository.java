package com.jp.omo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jp.omo.repository.entity.UserReferralCodes;



/**
 * 
 * @author EHTESHAM MAZHAR
 *
 */
@Repository                                                        
public interface UserReferralCodesRepository extends JpaRepository<UserReferralCodes, Long> {

	/**
	 * 
	 * @param referralCode
	 * @return
	 */
	@Query("SELECT urc from UserReferralCodes urc where urc.referralCode =?1 AND urc.softdeleteflag=0 AND urc.status=1")
	UserReferralCodes findByReferralCode(String referralCode);
	
	/**
	 * @param usedGlobalMaxAmnt
	 * @param userRfCodeCount
	 * @param referralCode
	 * @return
	 */
	@Modifying         
	@Query("UPDATE UserReferralCodes urc SET urc.usedGlobalMaxAmount =?1, urc.usedCount =?2 WHERE urc.referralCode =?3")
	Integer updateUserReferralCodes(Double usedGlobalMaxAmount,Integer usedCount,String referralCode);
	
	@Query("SELECT urc from UserReferralCodes urc where urc.userId=?1")
	List<UserReferralCodes> findByUserId(long userId);
}
