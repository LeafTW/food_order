package com.example.food_ordering.service;

import com.example.food_ordering.entity.userEntity;

public interface UserService {

    boolean addUser(userEntity user);

    userEntity getUser(userEntity userEntity);

    userEntity updateUser(userEntity userEntity);

    void deleteUser(String username);

}
