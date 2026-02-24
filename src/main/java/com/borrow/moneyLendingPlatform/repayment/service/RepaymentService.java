package com.borrow.moneyLendingPlatform.repayment.service;

import com.borrow.moneyLendingPlatform.loan.entity.Loan;
import com.borrow.moneyLendingPlatform.loan.repository.LoanRepository;
import com.borrow.moneyLendingPlatform.repayment.dto.RepaymentScheduleDTO;
import com.borrow.moneyLendingPlatform.repayment.entity.RepaymentSchedule;
import com.borrow.moneyLendingPlatform.repayment.entity.RepaymentStatus;
import com.borrow.moneyLendingPlatform.repayment.repository.RepaymentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepaymentService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private RepaymentScheduleRepository repaymentScheduleRepository;


    //This is called from Loan Service
    public void creatingRepaymentSchedule(Loan loan){

        //This is the list of the all repayments, Each is the separate instance of RepaymentSchedule
        List<RepaymentSchedule> repaymentScheduleList = createRepaymentScheduleList(loan);
        repaymentScheduleRepository.saveAll(repaymentScheduleList);

    }

    //This method is called in this class
    //this method will hold main logic of creating RepaymentSchedule
    private List<RepaymentSchedule> createRepaymentScheduleList(Loan loan) {


        //This is the logic through which we are calculating emi for every RepaymentSchedule
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


    //this method is called in repaymentController
    public List<RepaymentScheduleDTO> getTheListOfRepaymentSchedule(Long loanId){

        Loan loanReq = loanRepository.findById(loanId).orElseThrow(()-> new RuntimeException("Loan not found with given id in 'getTheListOfRepaymentSchedule'"));

        List<RepaymentSchedule> repaymentSchedules = repaymentScheduleRepository.findByLoan(loanReq);
        List<RepaymentScheduleDTO> repaymentScheduleDTOS = new ArrayList<>();

        for(RepaymentSchedule repaymentSchedule: repaymentSchedules){
            repaymentScheduleDTOS.add(mapToRepaymentScheduleDTO(repaymentSchedule));
        }

        return repaymentScheduleDTOS;

    }

    private RepaymentScheduleDTO mapToRepaymentScheduleDTO(RepaymentSchedule repaymentSchedule){

        RepaymentScheduleDTO repaymentScheduleDTO = new RepaymentScheduleDTO();

        repaymentScheduleDTO.setRepaymentScheduleId(repaymentSchedule.getId());
        repaymentScheduleDTO.setForLoanId(repaymentSchedule.getLoan().getId());
        repaymentScheduleDTO.setRepaymentStatus(repaymentSchedule.getRepaymentStatus().toString());
        repaymentScheduleDTO.setEmiAmount(repaymentSchedule.getEmiAmount());
        repaymentScheduleDTO.setDueDate(repaymentSchedule.getDueDate());

        return repaymentScheduleDTO;
    }
}
