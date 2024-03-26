package com.example.food_ordering.service.impl;

import com.example.food_ordering.entity.UserEntity;
import com.example.food_ordering.error.ActionException;
import com.example.food_ordering.repository.UserRepository;
import com.example.food_ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserRepository repository;

    /**
     * 新增會員
     */
    @Override
    public boolean addUser(UserEntity user) throws ActionException {
        UserEntity byUsername = repository.findByUsername(user.getUsername());
        if (user.getUsername().isEmpty()) {
            throw new ActionException("帳號不能為空 ", "404");
        }else if(byUsername != null){
            throw new ActionException("帳號重複", "500");
        }
        repository.save(user);
        return true;
    }

    /**
     * 查詢帳號
     */
    @Override
    public UserEntity getUser(UserEntity user) throws ActionException {
        UserEntity byUsername = repository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (user.getUsername() .isEmpty()) {
            throw new ActionException("帳號不能為空 ", "404");
        }else if(byUsername == null){
            throw new ActionException("查無此帳號 ", "500");
        }
        return byUsername;

    }

    /**
     * 更新帳號
     */
    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        com.example.food_ordering.entity.UserEntity user = repository.save(userEntity);
        return user;
    }

    /**
     * 刪除帳號
     */
    @Override
    @Transactional //確保方法在事務中執行。將該註解添加到你的Service方法上，以確保刪除操作發生在事務內部
    public void deleteUser(String userName) {
        repository.deleteByUsername(userName);
    }
}
