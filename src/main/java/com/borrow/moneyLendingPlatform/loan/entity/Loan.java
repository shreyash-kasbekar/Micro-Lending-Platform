package com.borrow.moneyLendingPlatform.loan.entity;

import com.borrow.moneyLendingPlatform.borrower.entity.LoanRequest;
import com.borrow.moneyLendingPlatform.user.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "loan")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private LoanRequest loanRequest;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Users borrower;

    @ManyToOne
    @JoinColumn(name = "lender_id")
    private Users lender;

    private Double principleAmount;

    private Double interestRate;

    private Integer tenureInMonths;

    private Instant startDate;

    private Instant endDate;

    @Enumerated(EnumType.STRING)
    private Status status;

}
