package com.borrow.moneyLendingPlatform.user.controller;

import com.borrow.moneyLendingPlatform.user.dto.UserLoginReqdto;
import com.borrow.moneyLendingPlatform.user.dto.UserRegisterReqdto;
import com.borrow.moneyLendingPlatform.user.dto.UserResponsedto;
import com.borrow.moneyLendingPlatform.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponsedto> register(@RequestBody @Valid UserRegisterReqdto userRegisterReqdto){

        return ResponseEntity.ok(userService.registerUser(userRegisterReqdto));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserResponsedto> register(@RequestBody @Valid UserLoginReqdto userLoginReqdto){

        return ResponseEntity.ok(userService.loginUser(userLoginReqdto));
    }
}
