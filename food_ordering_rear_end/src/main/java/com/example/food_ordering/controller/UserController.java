package com.example.food_ordering.controller;

import com.example.food_ordering.entity.userEntity;
import com.example.food_ordering.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userController")
@CrossOrigin(maxAge = 3600, allowedHeaders = "*")//開放外部ip讀取資料權限
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 新增會員
     */
    @PostMapping("/addUser")
    public boolean addUser(@RequestBody userEntity user) {
        if (userService.addUser(user)) {
            return true;
        }
        return false;
    }

    /**
     * 檢查登入
     */
    @PostMapping("/LoginUser")
    public ResponseEntity<?> LoginUser(HttpServletRequest request, @RequestBody userEntity user) {
        userEntity getuser = userService.getUser(user);

        if (getuser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userData", getuser);
            // 生命週期
            session.setMaxInactiveInterval(60);
            System.out.println(getuser);

            return ResponseEntity.ok() .body(getuser);
        } else {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

    /**
     * 取得user Session
     */
    @GetMapping("/getSession")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            userEntity user = (userEntity) session.getAttribute("userData");
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().body("未登入");
        }
    }

    /**
     * 登出 刪除user Session
     */
    @PutMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("已登出");
    }

    /**
     * 更新會員資料
     */
    @PostMapping("/updateUser")
    public userEntity updateUser(@RequestBody userEntity user) {
        userEntity userEntity = userService.updateUser(user);
        return userEntity;
    }

    /**
     * 依據username刪除帳號
     */
    @DeleteMapping("/deleteUser/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

}
