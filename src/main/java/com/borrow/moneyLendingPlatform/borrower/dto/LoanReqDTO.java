package com.borrow.moneyLendingPlatform.borrower.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoanReqDTO {

    private Long fromUserId;
    private Double amount;
    private Integer holdingPeriodMonths;
    private String purpose;
}
