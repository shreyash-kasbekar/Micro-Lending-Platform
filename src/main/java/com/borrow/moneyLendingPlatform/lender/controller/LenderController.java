package com.borrow.moneyLendingPlatform.lender.controller;

import com.borrow.moneyLendingPlatform.borrower.dto.LoanResponseDTO;
import com.borrow.moneyLendingPlatform.lender.dto.BidReqDTO;
import com.borrow.moneyLendingPlatform.lender.dto.BidResponseDTO;
import com.borrow.moneyLendingPlatform.lender.service.LenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/lender")
public class LenderController {

    @Autowired
    private LenderService lenderService;

    @GetMapping("/loan-requests")
    public ResponseEntity<List<LoanResponseDTO>> seeAllOpenLoanReq(){
        return ResponseEntity.ok(lenderService.getAllOpenLoanReq());
    }

    @PostMapping("/bid")
    public ResponseEntity<BidResponseDTO> postBid(@PathVariable BidReqDTO bidReqDTO){
        return ResponseEntity.ok(lenderService.placeBidForLoanReq(bidReqDTO));
    }

    @GetMapping("bids")
    public ResponseEntity<List<BidResponseDTO>> getMyBids(@PathVariable Long lenderId){
        return ResponseEntity.ok(lenderService.getMyAllBids(lenderId));
    }
}
