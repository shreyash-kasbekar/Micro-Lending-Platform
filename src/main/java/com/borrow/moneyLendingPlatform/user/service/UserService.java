package com.borrow.moneyLendingPlatform.user.service;

import com.borrow.moneyLendingPlatform.user.dto.UserLoginReqdto;
import com.borrow.moneyLendingPlatform.user.dto.UserRegisterReqdto;
import com.borrow.moneyLendingPlatform.user.dto.UserResponsedto;
import com.borrow.moneyLendingPlatform.user.entity.Role;
import com.borrow.moneyLendingPlatform.user.entity.Users;
import com.borrow.moneyLendingPlatform.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponsedto registerUser(@Valid UserRegisterReqdto userRegisterReqdto) {

        Users isAlreadyPresent = userRepository.findUserByEmail(userRegisterReqdto.getEmail()).orElse(null);
        if (isAlreadyPresent != null){
            System.out.println("User already present with the id"+ isAlreadyPresent.getId());
            return null;
        }

        Users user = mapToEntity(userRegisterReqdto);
        Users savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    public UserResponsedto loginUser(@Valid UserLoginReqdto userLoginReqdto){

        Users gotUser = verifyUser(userLoginReqdto);

        if (gotUser == null){
            throw new RuntimeException("Something is wrong");
        }
        return mapToDTO(gotUser);
    }

    private Users verifyUser(UserLoginReqdto userLoginReqdto){
        String email = userLoginReqdto.getEmail();
        String password = userLoginReqdto.getPassword();

        Users tempuser = userRepository.findUserByEmail(userLoginReqdto.getEmail()).orElseThrow(()->new RuntimeException("no User found"));
        if (tempuser == null) {
            throw new RuntimeException("Null User!!");
        }
        else if (tempuser.getPassword().equalsIgnoreCase(password)) {
            return tempuser;
        }

        return null;
    }


    private Users mapToEntity(UserRegisterReqdto userRegisterReqdto){

        Users user = new Users();
        user.setUsername(userRegisterReqdto.getUsername());
        user.setEmail(userRegisterReqdto.getEmail());
        user.setPassword(userRegisterReqdto.getPassword());
        user.setPhone(userRegisterReqdto.getPhone());
        user.setAddress(userRegisterReqdto.getAddress());
        //Role setting
        user.setRole(Role.valueOf(userRegisterReqdto.getRole().toUpperCase()));

        return user;
    }

    private UserResponsedto mapToDTO(Users user){

        UserResponsedto userResponsedto = new UserResponsedto();
        userResponsedto.setId(user.getId());
        userResponsedto.setUsername(user.getUsername());
        userResponsedto.setEmail(user.getEmail());
        userResponsedto.setPhone(user.getPhone());
        userResponsedto.setCreatedAt(user.getCreatedAt());
        userResponsedto.setRole(user.getRole().toString());

        return userResponsedto;
    }
}
