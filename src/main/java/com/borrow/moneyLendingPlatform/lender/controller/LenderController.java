package com.borrow.moneyLendingPlatform.lender.controller;

import com.borrow.moneyLendingPlatform.borrower.dto.LoanResponseDTO;
import com.borrow.moneyLendingPlatform.lender.dto.BidReqDTO;
import com.borrow.moneyLendingPlatform.lender.dto.BidResponseDTO;
import com.borrow.moneyLendingPlatform.lender.service.LenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<BidResponseDTO> placeBid(@RequestBody BidReqDTO bidReqDTO, Authentication authentication){
        return ResponseEntity.ok(lenderService.placeBidForLoanReq(bidReqDTO, authentication.getName()));
    }

    @GetMapping("bids")
    public ResponseEntity<List<BidResponseDTO>> getMyBids(Authentication authentication){
        return ResponseEntity.ok(lenderService.getMyAllBids(authentication.getName()));
    }
}
