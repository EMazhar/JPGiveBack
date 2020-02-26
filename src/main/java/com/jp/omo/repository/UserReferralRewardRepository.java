package com.jp.omo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jp.omo.repository.entity.UserReferralReward;

/**
 * 
 * @author EHTESHAM MAZHAR
 *
 */
@Repository
public interface UserReferralRewardRepository extends JpaRepository<UserReferralReward, Long> {

}
