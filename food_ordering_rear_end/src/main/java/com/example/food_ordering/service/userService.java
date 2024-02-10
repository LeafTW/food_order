package com.example.food_ordering.service;

import com.example.food_ordering.entity.userEntity;

import java.util.List;

public interface userService {

    boolean addUser(userEntity user);

    userEntity getUser(userEntity userEntity);


}
