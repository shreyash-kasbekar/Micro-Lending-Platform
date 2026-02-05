package com.borrow.moneyLendingPlatform.borrower.repository;

import com.borrow.moneyLendingPlatform.borrower.entity.LoanRequest;
import com.borrow.moneyLendingPlatform.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {

    List<LoanRequest> findByBorrower(Users borrower);
}
