package com.example.food_ordering.repository;

import com.example.food_ordering.entity.TotalOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TotalOrderRepository extends JpaRepository<TotalOrderEntity,Long> {

    Page<TotalOrderEntity> getTotalOrderEntityByUsernameAndNameContaining(String username ,String Name,Pageable page);

    Page<TotalOrderEntity> getTotalOrderEntityByUsernameIsNullAndNameContaining(String Name, Pageable page);

    Page<TotalOrderEntity> getTotalOrderEntityByUsername(String username ,Pageable page);

    Page<TotalOrderEntity> getTotalOrderEntityByUsernameIsNull( Pageable page);
}
