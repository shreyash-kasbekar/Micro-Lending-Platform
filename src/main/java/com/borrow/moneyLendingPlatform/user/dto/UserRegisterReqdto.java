package com.borrow.moneyLendingPlatform.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegisterReqdto {

    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role;

}
