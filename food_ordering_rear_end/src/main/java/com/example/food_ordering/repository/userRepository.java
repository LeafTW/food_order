package com.example.food_ordering.repository;

import com.example.food_ordering.entity.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface userRepository extends JpaRepository<userEntity,Long> {

//    @Query(value = "INSERT INTO user (name, username, password, email, phone) VALUES (?1, ?2, ?3, ?4,?5)", nativeQuery = true)
//    @Modifying
//    @Transactional
//    void addUser(String name, String username, String password, String email, Integer phone);

    userEntity save(userEntity user);

    public userEntity findByUsernameAndPassword(String username,String password);

}
