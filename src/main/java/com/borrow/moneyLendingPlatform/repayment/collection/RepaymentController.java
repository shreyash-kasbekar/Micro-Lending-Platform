package com.borrow.moneyLendingPlatform.repayment.collection;

import com.borrow.moneyLendingPlatform.repayment.dto.PaymentRequestDTO;
import com.borrow.moneyLendingPlatform.repayment.dto.RepaymentScheduleDTO;
import com.borrow.moneyLendingPlatform.repayment.service.RepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Repayment")
public class RepaymentController {

    @Autowired
    private RepaymentService repaymentService;

    @GetMapping("/loan/{loanId}/schedule")
    public ResponseEntity<List<RepaymentScheduleDTO>> getTheListOfRepaymentSchedule(@PathVariable Long loanId){
        return ResponseEntity.ok(repaymentService.getTheListOfRepaymentSchedule(loanId));
    }

    @PostMapping("/repayment/pay/{repaymentScheduleId}")
    public ResponseEntity<> payRepaymentSchedule(@PathVariable Long repaymentScheduleId, @RequestBody PaymentRequestDTO){
        return ResponseEntity.ok(repaymentService.makingPayment())
    }
}
