package com.borrow.moneyLendingPlatform.user.repository;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import com.borrow.moneyLendingPlatform.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findUserByUsername(String username);

    Optional<Users> findUserByEmail(String email);
}
