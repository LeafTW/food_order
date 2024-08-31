package com.example.food_ordering.service.impl;

import com.example.food_ordering.entity.CartEntity;
import com.example.food_ordering.entity.OrderEntity;
import com.example.food_ordering.entity.TotalOrderEntity;
import com.example.food_ordering.repository.OrderRepository;
import com.example.food_ordering.repository.TotalOrderRepository;
import com.example.food_ordering.service.MealsService;
import com.example.food_ordering.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceimpl implements OrderService {

    private static final Integer ORDER_PAGE_COUNT = 8;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MealsService mealsService;
    @Autowired
    TotalOrderRepository totalOrderRepository;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * cart新增至order
     * @param userName
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void cartToOrder(String userName){
        List<CartEntity> cartEntityByUsername = mealsService.findCartEntityByUsername(userName);
        Integer orderIdMax = orderRepository.findMaxDistinctOrderId();
        if (orderIdMax == null) {
            orderIdMax = 0;
        } else {
            orderIdMax++;
        }
        for (var cartEntity : cartEntityByUsername) {
            OrderEntity orderEntity = new OrderEntity(cartEntity.getName(), cartEntity.getPrice(),
                    cartEntity.getQuantity()    , cartEntity.getUsername(),
                    (cartEntity.getPrice() * cartEntity.getQuantity()), orderIdMax);
            orderRepository.save(orderEntity);
            mealsService.deleteCartEntityById(cartEntity.getId());
        }
        setTotalOrderEntity(cartEntityByUsername,orderIdMax);
    }

    /**
     * 新增TotalOrderEntity
     * @param cartEntityList
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void setTotalOrderEntity(List<CartEntity> cartEntityList,Integer orderItem)  {
        TotalOrderEntity totalOrderEntity = new TotalOrderEntity();
        for(var i=0 ; i<cartEntityList.size(); i++){
            CartEntity cartEntity = cartEntityList.get(i);
            if (i==0) {
                totalOrderEntity.setName(cartEntity.getName());
                totalOrderEntity.setUsername(cartEntity.getUsername());
                totalOrderEntity.setOrderitem(orderItem);
                totalOrderEntity.setDate(Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
                totalOrderEntity.setState("準備中");
            }
            totalOrderEntity.setTotalprice(totalOrderEntity.getTotalprice()+
                    ( cartEntity.getPrice()*cartEntity.getQuantity()));
            totalOrderEntity.setTotalquantity(totalOrderEntity.getTotalquantity()+
                    cartEntity.getQuantity());
        }
        totalOrderRepository.save(totalOrderEntity);
    }

    /**
     * 取得TotalOrder所有DATA
     * @param page
     * @param userName
     * @param searchTerm 搜尋Name的值
     * @return
     */
    @Override
    public Page<TotalOrderEntity> getTotalOrder(Integer page, String userName,String searchTerm) {
        PageRequest pageRequest = PageRequest.of(page, ORDER_PAGE_COUNT);
        Page<TotalOrderEntity> totalOrderEntities;
        if (userName.equals("null")){
            if (searchTerm.equals("")){totalOrderEntities=totalOrderRepository.getTotalOrderEntityByUsernameIsNull(pageRequest);}
            else {totalOrderEntities = totalOrderRepository.getTotalOrderEntityByUsernameIsNullAndNameContaining(searchTerm,pageRequest);}
        }else {
            if (searchTerm.equals("")){totalOrderEntities=totalOrderRepository.getTotalOrderEntityByUsername(userName,pageRequest);}
            else{totalOrderEntities=totalOrderRepository.getTotalOrderEntityByUsernameAndNameContaining(userName,searchTerm,pageRequest);}
        }
        return totalOrderEntities;
    }

    /**
     * 刪除UserName=null的購物車欄位(Cart)
     * 每半小時執行一次
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    @Scheduled(cron="0 */30 * * * *")
    public void delectOrderByUsername() {
        System.out.println("Delect Null cart");
        orderRepository.deleteByNullUsername();
    }


}
