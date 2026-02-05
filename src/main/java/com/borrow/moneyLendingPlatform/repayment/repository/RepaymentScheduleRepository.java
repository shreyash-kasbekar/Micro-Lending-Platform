package com.borrow.moneyLendingPlatform.repayment.repository;

import com.borrow.moneyLendingPlatform.loan.entity.Loan;
import com.borrow.moneyLendingPlatform.repayment.entity.RepaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepaymentScheduleRepository extends JpaRepository<RepaymentSchedule, Long> {

    List<RepaymentSchedule> findByLoan(Loan loan);

    RepaymentSchedule findByInstallmentNumber(Integer installmentNumber);

    // Get last installment number for a loan
    //@Query("SELECT MAX(r.installmentNumber) FROM RepaymentSchedule r WHERE r.loan.id = :loanId")
    //Integer findLastInstallmentNumber(@Param("loanId") Long loanId);

}
