package com.borrow.moneyLendingPlatform.repayment.service;

import com.borrow.moneyLendingPlatform.loan.entity.Loan;
import com.borrow.moneyLendingPlatform.repayment.entity.RepaymentSchedule;
import com.borrow.moneyLendingPlatform.repayment.entity.RepaymentStatus;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepaymentService {

    public void creatingRepaymentSchedule(Loan loan){

        //This is the list of the all repayments Each is the separate instance of RepaymentSchedule
        List<RepaymentSchedule> repaymentScheduleList = createRepaymentScheduleList(loan);
    }



    private List<RepaymentSchedule> createRepaymentScheduleList(Loan loan) {

        double totalInterest = loan.getPrincipleAmount() * loan.getInterestRate() / 100;
        double totalAmount = loan.getPrincipleAmount() + totalInterest;
        double emiAmount = totalAmount / loan.getTenureInMonths();

        List<RepaymentSchedule> repaymentScheduleList = new ArrayList<>();

        for (int i=1; i<=loan.getTenureInMonths(); i++) {

            RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
            repaymentSchedule.setLoan(loan);
            repaymentSchedule.setEmiAmount(emiAmount);
            repaymentSchedule.setInstallmentNumber(i);
            repaymentSchedule.setRepaymentStatus(RepaymentStatus.UPCOMING);
            repaymentSchedule.setDueDate(loan.getStartDate().plus(i, ChronoUnit.MONTHS));
            repaymentScheduleList.add(repaymentSchedule);
        }
        return repaymentScheduleList;
    }
}
