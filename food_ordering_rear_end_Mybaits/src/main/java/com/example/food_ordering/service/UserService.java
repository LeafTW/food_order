package com.example.food_ordering.service;

import com.example.food_ordering.entity.UserEntity;
import com.example.food_ordering.error.ActionException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    boolean addUser(UserEntity user)  throws ActionException ;

    UserEntity getUser(UserEntity userEntity) throws ActionException;

    UserEntity updateUser(UserEntity userEntity);

    void deleteUser(String userName);

    String verifyUser(UserEntity user);

    UserEntity getToken(String authorHeader) throws ActionException;

}
