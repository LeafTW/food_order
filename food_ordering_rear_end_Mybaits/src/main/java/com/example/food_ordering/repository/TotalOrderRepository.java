package com.example.food_ordering.repository;

import com.example.food_ordering.entity.TotalOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TotalOrderRepository extends JpaRepository<TotalOrderEntity,Long> {

    Page<TotalOrderEntity> getTotalOrderEntityByUsernameAndNameContaining(String username ,String name,Pageable page);

    Page<TotalOrderEntity> getTotalOrderEntityByUsernameIsNullAndNameContaining(String name, Pageable page);

    Page<TotalOrderEntity> getTotalOrderEntityByUsername(String username ,Pageable page);

    @Query(value = "select id,name,totalprice,totalquantity,username,orderitem,date,state from total_order where username=?",nativeQuery = true)
    List<TotalOrderEntity> getTotalOrderEntityByUsername(String username);

    Page<TotalOrderEntity> getTotalOrderEntityByUsernameIsNull( Pageable page);
}
