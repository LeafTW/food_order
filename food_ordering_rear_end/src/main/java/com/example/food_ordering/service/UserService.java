package com.example.food_ordering.service;

import com.example.food_ordering.entity.UserEntity;

public interface UserService {

    boolean addUser(UserEntity user);

    UserEntity getUser(UserEntity userEntity);

    UserEntity updateUser(UserEntity userEntity);

    void deleteUser(String userName);

}
