package com.example.food_ordering.controller;

import com.example.food_ordering.entity.TotalOrderEntity;
import com.example.food_ordering.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/orderController")
@CrossOrigin(maxAge = 3600, allowedHeaders = "*")//開放外部ip讀取資料權限
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * cart新增至order
     * @param username
     * @return
     */
    @PutMapping("/orderAdd/{username}")
    public ResponseEntity<String> CartToOrder(@PathVariable String username) {
        try {
            orderService.CartToOrder(username);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    /**
     * 取得 所有totalOrder
     * @param page
     * @param username
     * @param searchTerm
     * @return
     */
    @PostMapping("/totalOrderGet/{page}/{username}")
    public ResponseEntity<Page<TotalOrderEntity>> getTotalOrder(@PathVariable Integer page, @PathVariable String username, @RequestBody Map<String,String> searchTerm){
        Page<TotalOrderEntity> totalOrder = orderService.getTotalOrder(page, username,searchTerm.get("searchTerm"));
        return ResponseEntity.ok(totalOrder);
    }

}
