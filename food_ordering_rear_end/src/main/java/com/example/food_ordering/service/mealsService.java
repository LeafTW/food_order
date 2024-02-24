package com.example.food_ordering.service;

import com.example.food_ordering.entity.itemEntity;
import com.example.food_ordering.entity.mealsEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface mealsService {

//    public Page<mealsEntity> getAllByPageNo(Integer pageNo);

    List<itemEntity> findItemWithCount();

    public Page<mealsEntity> findItemCountByItem(Integer pageNo,Character item);


}
