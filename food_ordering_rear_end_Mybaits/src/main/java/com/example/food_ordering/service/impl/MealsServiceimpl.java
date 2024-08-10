package com.example.food_ordering.service.impl;

import com.example.food_ordering.dto.MealsItemDTO;
import com.example.food_ordering.dto.Page;
import com.example.food_ordering.entity.CartEntity;
import com.example.food_ordering.entity.ItemEntity;
import com.example.food_ordering.entity.MealsEntity;
import com.example.food_ordering.mapper.MealsMapper;
import com.example.food_ordering.service.MealsService;
import com.example.food_ordering.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealsServiceimpl implements MealsService {

    private static final Integer CARD_PAGE_COUNT = 8;

    @Autowired
    private MealsMapper mealsMapper;

    @Autowired
    PageService<MealsEntity> pageService;

    public Page<MealsEntity>  findItemCountByItem(Integer pageNo, String item) {
        int offset = pageNo  * CARD_PAGE_COUNT;
        int ListSize = mealsMapper.findAllByItem(item).size();
        List<MealsEntity> allByItemWithPagination = mealsMapper.findAllByItemWithPagination(item, CARD_PAGE_COUNT, offset);
        Page<MealsEntity> pages = pageService.getPages(allByItemWithPagination,ListSize, pageNo, CARD_PAGE_COUNT);
        return pages;
    }

    @Override
    public List<CartEntity> findCartEntityByUsername(String userName) {
        if (!userName.equals("undefined") && !userName.equals("null")) {
            return mealsMapper.findCartEntityByUsername(userName);
        } else {
            return mealsMapper.findCartEntityByNullUsername();
        }
    }

    @Override
    public void deleteCartEntityById(Long id) {
        mealsMapper.deleteCartEntityById(id);
    }

    @Override
    public void updateCartEntityById(Integer quantity, Long id) {
         mealsMapper.updateCartEntityById(quantity, id);
    }

    @Override
    public List<MealsItemDTO> findMealsAllWithItem() {
        List<MealsItemDTO> mealsAllWithItem = mealsMapper.findMealsAllWithItem();
        return mealsAllWithItem;
    }

    @Override
    public void insertIntoCart(CartEntity cartEntity) {
        mealsMapper.saveCartEntity(cartEntity);
    }

    @Override
    public List<ItemEntity> findItemWithCount() {
        return mealsMapper.findItemWithCount();
    }


}
