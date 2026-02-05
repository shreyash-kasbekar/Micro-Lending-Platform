package com.borrow.moneyLendingPlatform.loan.repository;

import com.borrow.moneyLendingPlatform.lender.entity.Bid;
import com.borrow.moneyLendingPlatform.loan.entity.Loan;
import com.borrow.moneyLendingPlatform.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByBorrower(Users borrower);
}
