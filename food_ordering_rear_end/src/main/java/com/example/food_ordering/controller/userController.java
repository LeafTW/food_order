package com.example.food_ordering.controller;

import com.example.food_ordering.entity.userEntity;
import com.example.food_ordering.service.userService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userController")
@CrossOrigin(maxAge=3600,allowedHeaders = "*")//開放外部ip讀取資料權限
public class userController {

    @Autowired
    private userService userService;

    /**
     * 新增會員
     */
    @PostMapping("/addUser")
    public boolean addUser(@RequestBody userEntity  user){
        if(userService.addUser(user)){
            return true;
        }return false;
    }

    /**
     * 檢查是否登入成功
     */
    @PostMapping("/LoginUser")
    public userEntity LoginUser(@RequestBody userEntity user){
        userEntity getuser = userService.getUser(user);
        return getuser;
    }

    /**
     * 更新會員資料
     */
    @PostMapping("/updateUser")
    public userEntity updateUser(@RequestBody userEntity user){
        userEntity userEntity = userService.updateUser(user);
        return userEntity;
    }
}
