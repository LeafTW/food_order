package com.example.food_ordering.service.impl;

import com.example.food_ordering.entity.CartEntity;
import com.example.food_ordering.entity.ItemEntity;
import com.example.food_ordering.entity.mealsEntity;
import com.example.food_ordering.repository.MealsRepository;
import com.example.food_ordering.service.MealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealsServiceimpl implements MealsService {

    private static final Integer CARD_PAGE_COUNT = 8;
    @Autowired
    MealsRepository mealsRepository;


    @Override
    public Page<mealsEntity> findItemCountByItem(Integer pageNo, Character item) {
        PageRequest page = PageRequest.of(pageNo, CARD_PAGE_COUNT);
        Page<mealsEntity> allByItem = mealsRepository.findAllByItem(item, page);
        return allByItem;
    }

    /**
     * cart query
     **/
    @Override
    public List<CartEntity> findCartEntityByUsername(String username) {
        List<CartEntity> cartEntityByUsername;
        if (!username.equals("undefined")) {
            cartEntityByUsername = mealsRepository.findCartEntityByUsername(username);
        } else {
            cartEntityByUsername = mealsRepository.findCartEntityByNullUsername();
        }
        return cartEntityByUsername;
    }

    @Override
    public void deleteCartEntityById(Long id) {
        mealsRepository.deleteCartEntityById(id);
    }

    @Override
    public Integer updateCartEntityById(Integer quantity, Long id) {
        Integer cartEntities = mealsRepository.updateCartEntityById(quantity, id);
        return cartEntities;
    }

    @Override
    public void insertIntoCart(CartEntity c) {
        mealsRepository.save(c);
    }

    @Override
    public List<ItemEntity> findItemWithCount() {
        return mealsRepository.findItemWithCount();
    }

}
