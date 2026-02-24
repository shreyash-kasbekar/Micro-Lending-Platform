package com.borrow.moneyLendingPlatform.repayment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentRequestDTO {
    Double amount;
    String paymentMode;
    String paymentMethod;
}
