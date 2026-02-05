package com.borrow.moneyLendingPlatform.loan.controller;

import com.borrow.moneyLendingPlatform.loan.dto.ContractLoanResponseDTO;
import com.borrow.moneyLendingPlatform.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/{loanId}")
    public ResponseEntity<ContractLoanResponseDTO> getLoan(@PathVariable Long loanId){
        return ResponseEntity.ok(loanService.getLoanById(loanId));
    }

    @GetMapping("/my/{borrowerId}")
    public ResponseEntity<List<ContractLoanResponseDTO>> getMyLoans(@PathVariable Long borrowerId){
        return ResponseEntity.ok(loanService.getBorrowerLoan(borrowerId));
    }
}
