package com.example.food_ordering.service;

import com.example.food_ordering.dto.MealsItemDTO;
import com.example.food_ordering.dto.Page;
import com.example.food_ordering.dto.UpdateItemRequest;
import com.example.food_ordering.entity.CartEntity;
import com.example.food_ordering.entity.ItemEntity;
import com.example.food_ordering.entity.MealsEntity;

import java.util.List;

public interface MealsService {


    void insertIntoCart(CartEntity e);

    List<ItemEntity> findItemWithCount();

    Page<MealsEntity> findItemCountByItem(Integer pageNo, String item);

    List<CartEntity> findCartEntityByUsername(String userName);

    void deleteCartEntityById(Long id);

    void updateCartEntityById(Integer quantity, Long id);
    List<MealsItemDTO> findMealsAllWithItem();

    void savedDataToRedis(String key, UpdateItemRequest show);

    void updateItem(List<String> upDataList,Boolean show);
}
