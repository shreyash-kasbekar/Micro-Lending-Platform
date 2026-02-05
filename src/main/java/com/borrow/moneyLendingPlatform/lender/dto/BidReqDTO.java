package com.borrow.moneyLendingPlatform.lender.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BidReqDTO {
    private Long loanId;
    private Long fromUserId;
    private Double offeredAmount;
    private Double interestRate;
    private String message;
}
