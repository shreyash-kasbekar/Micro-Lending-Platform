package com.borrow.moneyLendingPlatform.borrower.controller;

import com.borrow.moneyLendingPlatform.borrower.dto.LoanReqDTO;
import com.borrow.moneyLendingPlatform.borrower.dto.LoanResponseDTO;
import com.borrow.moneyLendingPlatform.borrower.service.BorrowerService;
import com.borrow.moneyLendingPlatform.lender.dto.BidResponseDTO;
import com.borrow.moneyLendingPlatform.lender.entity.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/borrower")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @PostMapping("/loan-request")
    public ResponseEntity<LoanResponseDTO> requestLoanPost(@RequestBody LoanReqDTO loanReqDTO){
        return ResponseEntity.ok(borrowerService.requestLoanCreation(loanReqDTO));
    }

    @GetMapping("/loan-request/{loanId}")
    public ResponseEntity<LoanResponseDTO> getLoanReq(@PathVariable Long loanId){
        return ResponseEntity.ok(borrowerService.getLoanWithId(loanId));
    }

    @GetMapping("/loan-requests/{borrowerId}")
    public ResponseEntity<List<LoanResponseDTO>> getMyLoanReq(@PathVariable Long borrowerId){
        return ResponseEntity.ok(borrowerService.getMyAllLoanReq(borrowerId));
    }

    @GetMapping("loan-request/{loanId}/bids")
    public ResponseEntity<List<Bid>> seeAllBidsForLoanId(@PathVariable Long loanId){
        return ResponseEntity.ok(borrowerService.allBidReqForLoanId(loanId));
    }

    @PostMapping("/loan-request/bid/{bidId}/accept")
    public ResponseEntity<Bid> acceptBid(@PathVariable Long bidId){
        return ResponseEntity.ok(borrowerService.acceptBid(bidId));
    }

}
