package com.leonid.LotteryBot.db;

import com.leonid.LotteryBot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUid(Long uid);
}
