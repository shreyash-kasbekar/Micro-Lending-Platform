package com.borrow.moneyLendingPlatform.repayment.entity;

import com.borrow.moneyLendingPlatform.loan.entity.Loan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "repayment_schedule")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RepaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private Integer installmentNumber;

    private Instant dueDate;

    private Double emiAmount;

    @Enumerated(EnumType.STRING)
    private RepaymentStatus repaymentStatus;

}
