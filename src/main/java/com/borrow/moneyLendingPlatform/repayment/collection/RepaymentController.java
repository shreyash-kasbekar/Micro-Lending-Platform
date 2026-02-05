package com.borrow.moneyLendingPlatform.repayment.collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Repayment")
public class RepaymentController {

    @GetMapping("/loan/{loanId}")
}
