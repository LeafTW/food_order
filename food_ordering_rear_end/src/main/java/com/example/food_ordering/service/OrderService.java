package com.example.food_ordering.service;


import com.example.food_ordering.entity.CartEntity;
import com.example.food_ordering.entity.OrderEntity;
import com.example.food_ordering.entity.TotalOrderEntity;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderService {
    /**
     * 送出購物車資料
     * @param username
     */
    void CartToOrder(String username) throws Exception;

    void setTotalOrderEntity(List<CartEntity> cartEntityList,Integer orderItem) throws Exception;

     Page<TotalOrderEntity> getTotalOrder(Integer page,String username,String searchTerm);
}
