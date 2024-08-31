package com.example.food_ordering.repository;

import com.example.food_ordering.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    /**
     * 取得OrderEntity的orderItem 最大值
     * @return
     */
//    @Query("SELECT DISTINCT MAX(c.orderItem) FROM OrderEntity c") //JPQL寫法
    @Query(value = "select DISTINCT MAX(orderitem) from orders",nativeQuery = true) //SQL寫法
    Integer findMaxDistinctOrderId();





}
