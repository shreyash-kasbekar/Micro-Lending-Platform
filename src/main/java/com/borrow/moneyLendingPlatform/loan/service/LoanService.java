package com.borrow.moneyLendingPlatform.loan.service;

import com.borrow.moneyLendingPlatform.borrower.entity.LoanRequest;
import com.borrow.moneyLendingPlatform.lender.entity.Bid;
import com.borrow.moneyLendingPlatform.loan.dto.ContractLoanResponseDTO;
import com.borrow.moneyLendingPlatform.loan.entity.Loan;
import com.borrow.moneyLendingPlatform.loan.entity.Status;
import com.borrow.moneyLendingPlatform.loan.repository.LoanRepository;
import com.borrow.moneyLendingPlatform.repayment.service.RepaymentService;
import com.borrow.moneyLendingPlatform.user.entity.Users;
import com.borrow.moneyLendingPlatform.user.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private RepaymentService repaymentService;
    @Autowired
    private UserRepository userRepository;

    //This is activated directly by borrower Service when Borrower accept certain bid
    public void bidAcceptedByBorrower(LoanRequest loanRequest, Bid bid) {
        Loan loan = mapLoanReqToLoan(loanRequest, bid);
        loanRepository.save(loan);
        System.out.println("Loan Is Stored");

        //when Loan is created we are creating RepaymentSchedule for that Loan
        repaymentService.creatingRepaymentSchedule(loan);
        System.out.println("Repayment Schedule is created");
    }


    public ContractLoanResponseDTO getLoanById(Long loanId){
        Loan loan =  loanRepository.findById(loanId).orElseThrow(()-> new RuntimeException("Loan with that Loan Id not found in getLoanById"));
        return mapTOContractLoanResponseDTO(loan);
    }

    private ContractLoanResponseDTO mapTOContractLoanResponseDTO(Loan loan) {
        ContractLoanResponseDTO loanResponseDTO = new ContractLoanResponseDTO();
        loanResponseDTO.setLoanId(loan.getId());
        loanResponseDTO.setLoanReqId(loan.getLoanRequest().getId());
        loanResponseDTO.setBorrowerId(loan.getBorrower().getId());
        loanResponseDTO.setLenderId(loan.getLender().getId());
        loanResponseDTO.setPrincipleAmount(loan.getPrincipleAmount());
        loanResponseDTO.setInterestRate(loan.getInterestRate());
        loanResponseDTO.setTenureInMonths(loan.getTenureInMonths());
        loanResponseDTO.setStartDate(loan.getStartDate());
        loanResponseDTO.setEndDate(loan.getEndDate());
        loanResponseDTO.setStatus(loan.getStatus().name());
        return loanResponseDTO;
    }


    private Loan mapLoanReqToLoan(LoanRequest loanRequest, Bid bid) {

        Loan loan = new Loan();

        // Relationship mapping
        loan.setLoanRequest(loanRequest);
        loan.setBorrower(loanRequest.getBorrower());
        loan.setLender(bid.getLender());

        // Financial mapping
        loan.setPrincipleAmount(bid.getOfferedAmount());
        loan.setInterestRate(bid.getInterestRate());
        loan.setTenureInMonths(loanRequest.getTenureInMonths());

        // Dates
        Instant startDate = Instant.now();
        loan.setStartDate(startDate);

        // calculate end date based on tenure
        Instant endDate = startDate.plusSeconds(loanRequest.getTenureInMonths() * 30L * 24 * 60 * 60);
        loan.setEndDate(endDate);

        // Status
        loan.setStatus(Status.ACTIVE);

        return loan;
    }

    public List<ContractLoanResponseDTO> getBorrowerLoan(Long borrowerId) {
        Users borrower = userRepository.findById(borrowerId).orElseThrow(()-> new RuntimeException("borrower not found in getBorrowerLoan() in LoanService"));

        List<Loan> loanList = loanRepository.findByBorrower(borrower);
        List<ContractLoanResponseDTO> contractLoanResponseDTOList = new ArrayList<>();

        for (Loan loan : loanList){
            contractLoanResponseDTOList.add(mapTOContractLoanResponseDTO(loan));
        }
        return contractLoanResponseDTOList;
    }
}