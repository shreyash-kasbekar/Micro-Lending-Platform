package com.borrow.moneyLendingPlatform.repayment.entity;

import com.borrow.moneyLendingPlatform.loan.entity.Loan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "repaymentSchedule_id")
    private RepaymentSchedule repaymentSchedule;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private Double AmountPaid;

    private Instant paymentDate;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

}
