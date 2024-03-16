package com.example.food_ordering.service;

import com.example.food_ordering.entity.CartEntity;
import com.example.food_ordering.entity.ItemEntity;
import com.example.food_ordering.entity.mealsEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MealsService {


    void insertIntoCart(CartEntity e);

    List<ItemEntity> findItemWithCount();

    public Page<mealsEntity> findItemCountByItem(Integer pageNo, Character item);

    List<CartEntity> findCartEntityByUsername(String username);

    void deleteCartEntityById(Long id);

    Integer updateCartEntityById(Integer quantity, Long id);

}
