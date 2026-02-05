package com.borrow.moneyLendingPlatform.lender.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BidResponseDTO {
    private Long bidId;
    private Long loanReqId;
    private Long lenderId;
    private Double offeredAmount;
    private Double interestRate;
    private String message;
    private String Status;
}
