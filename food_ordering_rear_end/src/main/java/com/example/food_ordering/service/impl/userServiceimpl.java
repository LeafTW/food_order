package com.example.food_ordering.service.impl;

import com.example.food_ordering.entity.userEntity;
import com.example.food_ordering.repository.userRepository;
import com.example.food_ordering.service.userService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class userServiceimpl implements userService {

    @Autowired
    private userRepository repository;

    /** 新增會員 */
    @Override
    public boolean addUser(userEntity user) {
        if (this.getUser(user) == null){
//            repository.addUser(user.getName(),user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone());
            repository.save(user);
            return true;
        }
        return false;
    }
    /** 查詢帳號 */
    @Override
    public userEntity getUser(userEntity user) {
        userEntity byUsername = repository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        return byUsername != null ?
                byUsername.getUsername().equals(user.getUsername()) &&
                        byUsername.getPassword().equals(user.getPassword()) ?
                        byUsername :null
                : null;
    }
    /** 更新帳號 */
    @Override
    public userEntity updateUser(userEntity userEntity) {
        userEntity user = repository.save(userEntity);
        return user;
    }
    /** 刪除帳號 */
    @Override
    @Transactional //確保方法在事務中執行。將該註解添加到你的Service方法上，以確保刪除操作發生在事務內部
    public void deleteUser(String username) {
        repository.deleteByUsername(username);
    }
}
