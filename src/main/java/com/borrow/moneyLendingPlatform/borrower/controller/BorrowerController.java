package com.borrow.moneyLendingPlatform.borrower.controller;

import com.borrow.moneyLendingPlatform.borrower.dto.LoanReqDTO;
import com.borrow.moneyLendingPlatform.borrower.dto.LoanResponseDTO;
import com.borrow.moneyLendingPlatform.borrower.service.BorrowerService;
import com.borrow.moneyLendingPlatform.lender.dto.BidResponseDTO;
import com.borrow.moneyLendingPlatform.lender.entity.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/borrower")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    //(auth/borrower/loan-request)
    @PostMapping("/loan-request")
    public ResponseEntity<LoanResponseDTO> requestLoanPost(Authentication authentication, @RequestBody LoanReqDTO loanReqDTO){
        return ResponseEntity.ok(borrowerService.requestLoanCreation(authentication.getName(), loanReqDTO));
    }

    //(auth/borrower/loan-request/1)
    @GetMapping("/loan-request/{loanId}")
    public ResponseEntity<LoanResponseDTO> getLoanReq(Authentication authentication, @PathVariable Long loanId){
        return ResponseEntity.ok(borrowerService.getLoanWithId(authentication.getName(), loanId));
    }

    //(auth/borrower/my-loan-requests)
    @GetMapping("/my-loan-requests/borrower")
    public ResponseEntity<List<LoanResponseDTO>> getMyLoanReq(Authentication authentication){
        System.out.println("===================================================");
        System.out.println("================== got email from authentication is "+authentication.getName());
        return ResponseEntity.ok(borrowerService.getMyAllLoanReq(authentication.getName()));
    }

    //(auth/borrower/loan-request/1/bids)
    @GetMapping("/loan-request/{loanId}/bids")
    public ResponseEntity<List<Bid>> seeAllBidsForLoanId(@PathVariable Long loanId){
        return ResponseEntity.ok(borrowerService.allBidReqForLoanId(loanId));
    }

    //(auth/borrower/loan-request/bid/1/accept)
    @PostMapping("/loan-request/bid/{bidId}/accept")
    public ResponseEntity<Bid> acceptBid(Authentication authentication, @PathVariable Long bidId){
        return ResponseEntity.ok(borrowerService.acceptBid(authentication.getName(), bidId));
    }

}
