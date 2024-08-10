package com.example.food_ordering.service;

import com.example.food_ordering.dto.Page;

import java.util.List;

public interface PageService<T>{
    Page<T> getPages(List<T> Entity ,Integer listTotalSizw,Integer pageNo,Integer pageCount);
}
