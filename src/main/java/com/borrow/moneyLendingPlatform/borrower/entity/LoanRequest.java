package com.borrow.moneyLendingPlatform.borrower.entity;

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
@Table(name = "loanRequest")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users borrower;

    private Double amount;

    private Integer tenureInMonths;

    private String purpose;

    private Status statusOfLoanReq;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
