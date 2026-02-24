package com.borrow.moneyLendingPlatform.loan.repository;

import com.borrow.moneyLendingPlatform.lender.entity.Bid;
import com.borrow.moneyLendingPlatform.loan.entity.Loan;
import com.borrow.moneyLendingPlatform.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByBorrower(Users borrower);
}
