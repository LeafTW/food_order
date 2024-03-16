package com.example.food_ordering.service;

import com.example.food_ordering.entity.UserEntity;

public interface UserService {

    boolean addUser(UserEntity user);

    UserEntity getUser(UserEntity UserEntity);

    UserEntity updateUser(UserEntity UserEntity);

    void deleteUser(String username);

}
