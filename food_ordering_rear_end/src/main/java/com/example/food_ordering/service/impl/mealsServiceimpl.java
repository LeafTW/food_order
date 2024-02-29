package com.example.food_ordering.service.impl;

import com.example.food_ordering.entity.cartEntity;
import com.example.food_ordering.entity.itemEntity;
import com.example.food_ordering.entity.mealsEntity;
import com.example.food_ordering.repository.mealsRepository;
import com.example.food_ordering.service.mealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class mealsServiceimpl implements mealsService {

    private static final Integer CARD_PAGE_COUNT = 8;
    @Autowired
    mealsRepository mealsRepository;


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
    public List<cartEntity> findCartEntityByUsername(String username) {
        List<cartEntity> cartEntityByUsername;
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
    public void insertIntoCart(cartEntity c) {
        mealsRepository.save(c);
    }

    @Override
    public List<itemEntity> findItemWithCount() {
        return mealsRepository.findItemWithCount();
    }

}
