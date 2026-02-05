package com.borrow.moneyLendingPlatform.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponsedto {

    private Long id;
    private String username;
    private String phone;
    private String email;
    private Instant createdAt;
    private String role;
}
