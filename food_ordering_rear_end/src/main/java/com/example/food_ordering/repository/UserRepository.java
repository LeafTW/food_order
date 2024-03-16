package com.example.food_ordering.repository;

import com.example.food_ordering.entity.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<userEntity,Long> {

    userEntity save(userEntity user);
    public userEntity findByUsernameAndPassword(String username, String password);

    // 根据用户ID删除用户
    void deleteByUsername(String username);
}
