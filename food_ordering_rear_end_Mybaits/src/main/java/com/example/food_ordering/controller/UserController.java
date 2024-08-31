package com.example.food_ordering.controller;

import com.example.food_ordering.entity.UserEntity;
import com.example.food_ordering.error.ActionException;
import com.example.food_ordering.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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
    public boolean addUser(@RequestBody UserEntity user) throws ActionException{
        return userService.addUser(user);
    }

    /**
     * 檢查登入
     */
    @PostMapping("/LoginUser")
    public ResponseEntity<?> loginUser(@RequestBody UserEntity user) throws ActionException {
        UserEntity getuser = userService.getUser(user);
//        HttpSession session = request.getSession();
//            response. setHeader ("Access-Control-Allow-Origin", "http://localhost:8080");
        System.out.println("run session add" + request.getRequestId());
//        session.setAttribute("userData", getuser);
        // 生命週期
//        session.setMaxInactiveInterval(60);
        System.out.println(getuser);
        return ResponseEntity.ok().body(getuser);
    }

    /**
     * 取得user Session
     */
    @GetMapping("/getSession")
    public ResponseEntity<?> getUserInfo() throws ActionException{
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserEntity user = (UserEntity) session.getAttribute("userData");
            return ResponseEntity.ok(user);
        } else {
            throw new ActionException("未登入","500");
        }
    }

    @GetMapping("/getToken")
    public ResponseEntity<?> getUserInfo(HttpServletRequest req) throws ActionException {
        if (req.getServletPath().startsWith("/userController")) {
            String authorHeader = req.getHeader(AUTHORIZATION);
            UserEntity user = userService.getToken(authorHeader);
            return ResponseEntity.ok(user);
        }
        throw new ActionException("未登入","500");
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

    /*
    獲取token
    */
    @PostMapping("/verifyUser")
    public String verifyUser(@RequestBody UserEntity user){
        String jwtToken = userService.verifyUser(user);
        return jwtToken;
    }

}
