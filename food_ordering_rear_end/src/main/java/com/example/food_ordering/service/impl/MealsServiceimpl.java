package com.example.food_ordering.service.impl;

import com.example.food_ordering.entity.CartEntity;
import com.example.food_ordering.entity.ItemEntity;
import com.example.food_ordering.entity.MealsEntity;
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
    public Page<MealsEntity> findItemCountByItem(Integer pageNo, String item) {
        PageRequest page = PageRequest.of(pageNo, CARD_PAGE_COUNT);
        Page<MealsEntity> allByItem = mealsRepository.findAllByItem(item, page);
        return allByItem;
    }

    /**
     * cart query
     **/
    @Override
    public List<CartEntity> findCartEntityByUsername(String userName) {
        List<CartEntity> cartEntityByUsername;
        if (!userName.equals("undefined") & !userName.equals("null")) {
            cartEntityByUsername = mealsRepository.findCartEntityByUsername(userName);
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
    public void insertIntoCart(CartEntity cartEntity) {
        mealsRepository.save(cartEntity);
    }

    @Override
    public List<ItemEntity> findItemWithCount() {
        return mealsRepository.findItemWithCount();
    }

}
