package com.borrow.moneyLendingPlatform.borrower.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoanResponseDTO {

    private Long LoanId;
    private String fromBorrower;
    private Double amount;
    private Integer holdingPeriodMonths;
    private String purpose;
    private String status;

}
