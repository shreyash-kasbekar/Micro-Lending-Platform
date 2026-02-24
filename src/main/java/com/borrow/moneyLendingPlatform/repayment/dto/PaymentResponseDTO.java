package com.borrow.moneyLendingPlatform.repayment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentResponseDTO {
    public Long paymentId;
    public Integer repaymentScheduleNumber;
    public Instant paymentDate;
    public Instant DueDate;
    public String paymentStatus;

}
