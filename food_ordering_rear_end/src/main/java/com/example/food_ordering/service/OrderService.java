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
     * @param userName
     */
    void cartToOrder(String userName) ;

    void setTotalOrderEntity(List<CartEntity> cartEntityList,Integer orderItem) ;

     Page<TotalOrderEntity> getTotalOrder(Integer page,String userName,String searchTerm);
}
