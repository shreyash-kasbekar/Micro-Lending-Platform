package com.borrow.moneyLendingPlatform.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractLoanResponseDTO {
    private Long loanId;
    private Long loanReqId;
    private Long borrowerId;
    private Long lenderId;
    private Double principleAmount;
    private Double interestRate;
    private Integer tenureInMonths;
    private Instant startDate;
    private Instant endDate;
    private String status;
}
