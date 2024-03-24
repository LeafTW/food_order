package com.example.food_ordering.repository;

import com.example.food_ordering.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    /**
     * 取得OrderEntity的orderItem 最大值
     * @return
     */
    @Query("SELECT DISTINCT MAX(c.orderItem) FROM OrderEntity c")
    Integer findMaxDistinctOrderId();





}
