package com.example.food_ordering.controller;

import com.example.food_ordering.entity.itemEntity;
import com.example.food_ordering.entity.mealsEntity;
import com.example.food_ordering.service.mealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mealsList")
@CrossOrigin(maxAge=3600,allowedHeaders = "*")//開放外部ip讀取資料權限
public class mealsController {

    @Autowired
    mealsService mealsService;

//    @GetMapping("/getAllMeals/{pageNo}")
//    ResponseEntity<Page<mealsEntity>> getAllByPageNo(@PathVariable Integer pageNo){
//        Page<mealsEntity> allByPageNo = mealsService.getAllByPageNo(pageNo);
//        return ResponseEntity.ok(allByPageNo);
//    }

    //取得Meals跟item對應得item項目值
    @GetMapping("/findItemCountByMeals")
    ResponseEntity< List<itemEntity>> findItemWithCount(){
        return ResponseEntity.ok(mealsService.findItemWithCount());
    }

    //篩選Meals對應的item並回傳Page
    @GetMapping("/findAllByItem/{pageNo}/{item}")
    ResponseEntity<Page<mealsEntity>>findts(@PathVariable Integer pageNo,@PathVariable Character item){
        Page<mealsEntity> itemCountByItem = mealsService.findItemCountByItem(pageNo, item);
        return ResponseEntity.ok(itemCountByItem);
    }
}
