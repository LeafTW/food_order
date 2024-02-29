package com.example.food_ordering.controller;

import com.example.food_ordering.entity.cartEntity;
import com.example.food_ordering.entity.itemEntity;
import com.example.food_ordering.entity.mealsEntity;
import com.example.food_ordering.service.mealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mealsList")
@CrossOrigin(maxAge=3600,allowedHeaders = "*")//開放外部ip讀取資料權限
public class mealsController {

    @Autowired
    mealsService mealsService;


    /**
     * 取得Meals跟item對應得item項目值
     */
    @GetMapping("/findItemCountByMeals")
    ResponseEntity< List<itemEntity>> findItemWithCount(){
        return ResponseEntity.ok(mealsService.findItemWithCount());
    }

    /**
     *  篩選Meals對應的item並回傳Page
     */
    @GetMapping("/findAllByItem/{pageNo}/{item}")
    ResponseEntity<Page<mealsEntity>>findts(@PathVariable Integer pageNo,@PathVariable Character item){
        Page<mealsEntity> itemCountByItem = mealsService.findItemCountByItem(pageNo, item);
        return ResponseEntity.ok(itemCountByItem);
    }

    /**
     * 新增cart
     */
    @PostMapping("/insertIntoCart")
    ResponseEntity insertIntoCart(@RequestBody cartEntity c){
        if (c == null){
            return ResponseEntity.ok(null);
        }else {
            mealsService.insertIntoCart(c);
            return ResponseEntity.ok(c);
        }
    }

    /**
     * 查詢cart
     */
    @PostMapping("/cartEntityByUsername/{username}")
    ResponseEntity<List<cartEntity>> cartEntityByUsername(@PathVariable String username){
        List<cartEntity> cartEntityByUsername = mealsService.findCartEntityByUsername(username);
        return ResponseEntity.ok(cartEntityByUsername);
    }

    /**
     * 刪除cart
     */
    @GetMapping("/deleteCartEntityById/{id}")
    public ResponseEntity<Boolean> deleteCartEntityById(@PathVariable Long id){
        try {
            mealsService.deleteCartEntityById(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }


    //更新cart quantity數量
    @PostMapping("/updateCartEntityById/{quantity}/{id}")
    public ResponseEntity<Integer> updateCartEntityById(@PathVariable Integer quantity, @PathVariable Long id){
        Integer cartEntities = mealsService.updateCartEntityById(quantity, id);
        return ResponseEntity.ok(cartEntities);
    }
}
