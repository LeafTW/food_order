package com.example.food_ordering.service.impl;

import com.example.food_ordering.dto.Page;
import com.example.food_ordering.service.PageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PagesServiceimpl<T> implements PageService<T> {

    @Override
    public Page<T> getPages(List<T> Entity,Integer ListSize,Integer pageNo,Integer pageCount) {
        Integer totalPage=(int) Math.ceil((double)ListSize / pageCount);
        Page<T> tPage = new Page<>();
        tPage.setContent(Entity);
        tPage.setTotalPages(totalPage);
        tPage.setFirst(pageNo == 0);
        tPage.setLast(totalPage-1 == pageNo);
        tPage.setNumber(pageNo);
        return tPage;
    }
}
