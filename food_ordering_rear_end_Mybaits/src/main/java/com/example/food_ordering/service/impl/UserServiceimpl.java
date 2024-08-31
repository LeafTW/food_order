package com.example.food_ordering.service.impl;

import com.example.food_ordering.entity.UserEntity;
import com.example.food_ordering.error.ActionException;
import com.example.food_ordering.repository.UserRepository;
import com.example.food_ordering.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserRepository repository;

    /**
     * 新增會員
     */
    @Override
    public boolean addUser(UserEntity user) throws ActionException {
        UserEntity byUsername = repository.findByUsername(user.getUsername());
        if (user.getUsername().isEmpty()) {
            throw new ActionException("帳號不能為空 ", "404");
        } else if (byUsername != null) {
            throw new ActionException("帳號重複", "500");
        }
        repository.save(user);
        return true;
    }

    /**
     * 查詢帳號
     */
    @Override
    public UserEntity getUser(UserEntity user) throws ActionException {
        UserEntity byUsername = repository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (user.getUsername().isEmpty()) {
            throw new ActionException("帳號不能為空 ", "404");
        } else if (byUsername == null) {
            throw new ActionException("查無此帳號 ", "500");
        }
        return byUsername;

    }

    /**
     * 更新帳號
     */
    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        com.example.food_ordering.entity.UserEntity user = repository.save(userEntity);
        return user;
    }

    /**
     * 刪除帳號
     */
    @Override
    @Transactional //確保方法在事務中執行。將該註解添加到你的Service方法上，以確保刪除操作發生在事務內部
    public void deleteUser(String userName) {
        repository.deleteByUsername(userName);
    }

    @Override
    public String verifyUser(UserEntity user) {
        /*
        if (user.getUsername() !=null & user.getPassword()!=null){
            Date expireDate =
                    //設定30min過期
                    new Date(System.currentTimeMillis()+ 30 * 60 * 1000);
            String jwtToken = Jwts.builder()
                    .setSubject(user.getUsername()) //我以Username當subject
                    .claim("password", user.getPassword()) // 使用claim來存放額外的資訊
                    .setExpiration(expireDate)
                    //MySecret是自訂的私鑰，HS512是自選的演算法，可以任意改變
                    .signWith(SignatureAlgorithm.HS512,"MySecret")
                    .compact();
            //直接將JWT傳回
            return jwtToken;
        }*/

        //=====================以下非對稱加密（公鑰私鑰）======================
        if (user.getUsername() !=null & user.getPassword()!=null) {
            // 讀取私鑰文件
            byte[] keyBytes = new byte[0];
            try {
                keyBytes = Files.readAllBytes(Paths.get("/Volumes/Data/Code/Java/My_Project/food_order/food_ordering_rear_end_Mybaits/src/main/resources/RSA_SSL/private_key.pem"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String privateKeyPEM = new String(keyBytes)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            // 解碼並生成私鑰
            KeyFactory keyFactory = null;
            PrivateKey privateKey = null;
            byte[] decodedKey = java.util.Base64.getDecoder().decode(privateKeyPEM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
            try {
                keyFactory = KeyFactory.getInstance("RSA");
                privateKey = keyFactory.generatePrivate(keySpec);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            // 設置 JWT 的內容
            String username = user.getUsername();
            String password = user.getPassword();
            Date expireDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000); //設定30min過期

            // 使用 RS256 生成 JWT
            String jwtToken = Jwts.builder()
                    .setSubject(username)
                    .claim("password", password)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.RS256, privateKey)
                    .compact();

            System.out.println("Generated JWT: " + jwtToken);

            return jwtToken;
        }
        return null ;
    }

    @Override
    public UserEntity getToken(String authorHeader) throws ActionException {
        String bearer = "Bearer ";

        if (authorHeader != null) {
            try {
                String token = authorHeader.substring(bearer.length());
                Claims claims = Jwts.parser().setSigningKey("MySecret")
                        .parseClaimsJws(token).getBody();
                String username = claims.getSubject();
                String password = claims.get("password", String.class); // 獲取密碼
                UserEntity userEntity = new UserEntity();
                userEntity.setUsername(username);
                userEntity.setPassword(password);
                UserEntity user = getUser(userEntity);
                return user;
            } catch (Exception e) {
                throw new ActionException("Token 無效", "500");
            }
        } else {
            throw new ActionException("未登入", "500");
        }
    }
}
