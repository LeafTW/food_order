package com.example.food_ordering.repository;

import com.example.food_ordering.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    public UserEntity findByUsernameAndPassword(String username, String password);

    /**
     * 根据用户ID删除用户
     * @param username
     */
    void deleteByUsername(String username);
}
