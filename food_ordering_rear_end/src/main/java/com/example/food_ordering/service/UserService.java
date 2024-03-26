package com.example.food_ordering.service;

import com.example.food_ordering.entity.UserEntity;
import com.example.food_ordering.error.ActionException;

public interface UserService {

    boolean addUser(UserEntity user)  throws ActionException ;

    UserEntity getUser(UserEntity userEntity) throws ActionException;

    UserEntity updateUser(UserEntity userEntity);

    void deleteUser(String userName);

}
