package com.borrow.moneyLendingPlatform.user.controller;

import com.borrow.moneyLendingPlatform.config.JwtUtil;
import com.borrow.moneyLendingPlatform.user.dto.UserLoginReqdto;
import com.borrow.moneyLendingPlatform.user.dto.UserRegisterReqdto;
import com.borrow.moneyLendingPlatform.user.dto.UserResponsedto;
import com.borrow.moneyLendingPlatform.user.entity.Users;
import com.borrow.moneyLendingPlatform.user.repository.UserRepository;
import com.borrow.moneyLendingPlatform.user.service.UserService;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public UserController(AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponsedto> register(@RequestBody @Valid UserRegisterReqdto userRegisterReqdto){

        return ResponseEntity.ok(userService.registerUser(userRegisterReqdto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginReqdto request){

        // 1️⃣ Authenticate
        System.out.println("===================================================");
        System.out.println("================== Login details send for authentication ");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2️⃣ Generate JWT
        System.out.println("===================================================");
        System.out.println("================== token generation is in process");

        Users user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(()-> new RuntimeException("User Not Found in login While creating a JWT token"));
        String token = jwtUtil.generateToken(user);

        // 3️⃣ Create HttpOnly Cookie
        System.out.println("===================================================");
        System.out.println("================== http Cookie is going to be create");
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)          // prevents JS access (XSS protection)
                .secure(false)            // only over HTTPS (set false for local testing)
                .path("/")               // available for entire app
                .maxAge(Duration.ofHours(1)) // expiry
//                .sameSite("Strict")      // CSRF protection
                .build();
        System.out.println("===================================================");
        System.out.println("================== http Cookie is created");

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body("Login Successful");

//        return ResponseEntity.ok(userService.loginUser(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        // Clear security context
        SecurityContextHolder.clearContext();

        // Delete cookie
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) // true in production (HTTPS)
                .path("/")
                .maxAge(0) // this deletes cookie
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("Logged out successfully");
    }
}
