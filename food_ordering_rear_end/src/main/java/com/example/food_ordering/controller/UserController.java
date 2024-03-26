package com.example.food_ordering.controller;

import com.example.food_ordering.entity.UserEntity;
import com.example.food_ordering.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    @Autowired
    private HttpServletRequest request;

    /**
     * 新增會員
     */
    @PostMapping("/addUser")
    public boolean addUser(@RequestBody UserEntity user) {
        if (userService.addUser(user)) {
            return true;
        }
        return false;
    }

    /**
     * 檢查登入
     */
    @PostMapping("/LoginUser")
    public ResponseEntity<?> LoginUser(@RequestBody UserEntity user) {
        UserEntity getuser = userService.getUser(user);
        if (getuser != null) {
            HttpSession session = request.getSession();
//            response. setHeader ("Access-Control-Allow-Origin", "http://localhost:8080");
            System.out.println("run session add"+request.getRequestId());
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
    public ResponseEntity<?> getUserInfo() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserEntity user = (UserEntity) session.getAttribute("userData");
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
    public UserEntity updateUser(@RequestBody UserEntity user) {
        UserEntity UserEntity = userService.updateUser(user);
        return UserEntity;
    }

    /**
     * 依據username刪除帳號
     */
    @DeleteMapping("/deleteUser/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

}
