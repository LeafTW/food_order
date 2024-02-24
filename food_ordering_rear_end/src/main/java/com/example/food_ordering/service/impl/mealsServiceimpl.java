package com.example.food_ordering.service.impl;

import com.example.food_ordering.entity.itemEntity;
import com.example.food_ordering.entity.mealsEntity;
import com.example.food_ordering.repository.mealsRepository;
import com.example.food_ordering.service.mealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class mealsServiceimpl implements mealsService {

    private static final Integer CARD_PAGE_COUNT=8;
    @Autowired
    mealsRepository mealsRepository;

//    @Override
//    public Page<mealsEntity> getAllByPageNo(Integer pageNo) {
//        PageRequest page = PageRequest.of(pageNo, CARD_PAGE_COUNT);
//        Page<mealsEntity> getPageByPageNo = mealsRepository.findAll(page);
//        return getPageByPageNo;
//    }


    @Override
    public Page<mealsEntity> findItemCountByItem(Integer pageNo,Character item) {
        PageRequest page = PageRequest.of(pageNo, CARD_PAGE_COUNT);
        Page<mealsEntity> allByItem = mealsRepository.findAllByItem( item,page);
        return allByItem;
    }

    @Override
    public List<itemEntity> findItemWithCount() {
        return mealsRepository.findItemWithCount();
    }

}
