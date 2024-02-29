package com.example.food_ordering.service;

import com.example.food_ordering.entity.cartEntity;
import com.example.food_ordering.entity.itemEntity;
import com.example.food_ordering.entity.mealsEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface mealsService {


    void insertIntoCart(cartEntity e);

    List<itemEntity> findItemWithCount();

    public Page<mealsEntity> findItemCountByItem(Integer pageNo, Character item);

    List<cartEntity> findCartEntityByUsername(String username);

    void deleteCartEntityById(Long id);

    Integer updateCartEntityById(Integer quantity, Long id);

}
