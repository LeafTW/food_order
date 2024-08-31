package com.example.food_ordering.service.impl;

import com.example.food_ordering.config.RedisConfig;
import com.example.food_ordering.dto.MealsItemDTO;
import com.example.food_ordering.dto.Page;
import com.example.food_ordering.dto.UpdateItemRequest;
import com.example.food_ordering.entity.CartEntity;
import com.example.food_ordering.entity.ItemEntity;
import com.example.food_ordering.entity.MealsEntity;
import com.example.food_ordering.mapper.MealsMapper;
import com.example.food_ordering.service.MealsService;
import com.example.food_ordering.service.PageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class MealsServiceimpl implements MealsService {

    private static final Integer CARD_PAGE_COUNT = 8;

    @Autowired
    private MealsMapper mealsMapper;
    @Autowired
    PageService<MealsEntity> pageService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;


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
        List<ItemEntity> itemWithCount = mealsMapper.findItemWithCount();
        List<ItemEntity> returnMealsMapper =new ArrayList<>();
        itemWithCount.forEach(s-> {if(s.getShow() != Boolean.FALSE){
           returnMealsMapper.add(s);
        };});
        return returnMealsMapper;
    }

    @Override
    public void savedDataToRedis(String key, UpdateItemRequest show) {
        // 將List轉換為JSON字符串
        try {
            String jsonValue = objectMapper.writeValueAsString(show);
            // 保存資料到Redis，並設定過期時間為1小時
            redisTemplate.opsForValue().set(key, jsonValue, Duration.ofHours(1));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateItem(List<String> upDataList,Boolean show){
       upDataList.forEach(s->{
           mealsMapper.updateItemShow(show,s);
       } );
    }

    @Scheduled(fixedRate = 3000) // 每30秒檢查一次
    public void processScheduledData() {
        // 生成Redis鍵
        String key = "scheduledData:" + "30";

        // 從Redis中獲取JSON字符串
        String jsonValue = (String) redisTemplate.opsForValue().get(key);
        if (jsonValue != null) {
            try {
                // 將JSON字符串轉換為UpdateItemRequest
                UpdateItemRequest updateItemRequest = objectMapper.readValue(jsonValue, UpdateItemRequest.class);

                // 更新MySQL
                updateItem(updateItemRequest.getUpDataList(),updateItemRequest.isShow());

                String findItemWithCountWriteValueAsString = objectMapper.writeValueAsString(findItemWithCount());
                // 同步更新Redis
                redisTemplate.opsForValue().set("myEntity:" + "ItemShow", findItemWithCountWriteValueAsString);

                // 刪除Redis中的暫存資料
                redisTemplate.delete(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
