package com.example.food_ordering.service;

import com.example.food_ordering.entity.CartEntity;
import com.example.food_ordering.entity.ItemEntity;
import com.example.food_ordering.entity.MealsEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MealsService {


    void insertIntoCart(CartEntity e);

    List<ItemEntity> findItemWithCount();

    public Page<MealsEntity> findItemCountByItem(Integer pageNo, String item);

    List<CartEntity> findCartEntityByUsername(String userName);

    void deleteCartEntityById(Long id);

    Integer updateCartEntityById(Integer quantity, Long id);

}
