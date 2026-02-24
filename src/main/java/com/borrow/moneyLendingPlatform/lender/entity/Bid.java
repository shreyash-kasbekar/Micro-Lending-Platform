package com.borrow.moneyLendingPlatform.lender.entity;

import com.borrow.moneyLendingPlatform.borrower.entity.LoanRequest;
import com.borrow.moneyLendingPlatform.user.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "bid")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loanRequest_id")
    private LoanRequest loanRequest;

    @ManyToOne
    @JoinColumn(name = "lender_id")
    private Users lender;

    private Double offeredAmount;

    private Double interestRate;

    private String message;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}
