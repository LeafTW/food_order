package com.example.food_ordering.service.impl;

import com.example.food_ordering.entity.UserEntity;
import com.example.food_ordering.repository.UserRepository;
import com.example.food_ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserRepository repository;

    /** 新增會員 */
    @Override
    public boolean addUser(UserEntity user) {
        if (this.getUser(user) == null){
//            repository.addUser(user.getName(),user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone());
            repository.save(user);
            return true;
        }
        return false;
    }
    /** 查詢帳號 */
    @Override
    public UserEntity getUser(UserEntity user) {
        UserEntity byUsername = repository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        if (byUsername != null){
            if(byUsername.getUsername().equals(user.getUsername()) &&
                    byUsername.getPassword().equals(user.getPassword())){
                return byUsername;
            }return null;
        }
        return  null;
    }
    /** 更新帳號 */
    @Override
    public UserEntity updateUser(UserEntity UserEntity) {
        com.example.food_ordering.entity.UserEntity user = repository.save(UserEntity);
        return user;
    }
    /** 刪除帳號 */
    @Override
    @Transactional //確保方法在事務中執行。將該註解添加到你的Service方法上，以確保刪除操作發生在事務內部
    public void deleteUser(String username) {
        repository.deleteByUsername(username);
    }
}
