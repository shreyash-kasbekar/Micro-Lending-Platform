package com.borrow.moneyLendingPlatform.repayment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepaymentScheduleDTO {

    public Long repaymentScheduleId;
    public Long ForLoanId;
    public Instant dueDate;
    public Double emiAmount;
    public String repaymentStatus;
}
