package com.example.food_ordering.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthorizationCheckFilter  extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        /* 對稱加密
        //如果不是登入就攔截
        if(req.getServletPath().startsWith("/orderController")){
            String authorHeader =  req.getHeader(AUTHORIZATION);
            String bearer ="Bearer ";
            //以jjwt驗證token，只要驗證成功就放行
            //驗證失敗會拋exception，直接將錯誤訊息傳回
            if(authorHeader!= null && authorHeader.startsWith(bearer)){
                try{
                    String token = authorHeader.substring(bearer.length());
                    Claims claims = Jwts.parser().setSigningKey("MySecret")
                            .parseClaimsJws(token).getBody();

                    System.out.println("JWT payload:"+claims.toString());

                    chain.doFilter(req, res);

                }catch(Exception e){
                    System.err.println("Error : "+e);
                    res.setStatus(FORBIDDEN.value());

                    Map<String, String> err = new HashMap<>();
                    err.put("jwt_err", e.getMessage());
                    res.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(res.getOutputStream(), err);
                }
            }else{
                res.setStatus(UNAUTHORIZED.value());
            }
        }else{
            chain.doFilter(req, res);
        }
*/

        if(req.getServletPath().startsWith("/orderController")){
            String authorHeader =  req.getHeader(AUTHORIZATION);
            String bearer ="Bearer ";
            //以jjwt驗證token，只要驗證成功就放行
            //驗證失敗會拋exception，直接將錯誤訊息傳回
            if(authorHeader!= null && authorHeader.startsWith(bearer)){
                try{
                    String token = authorHeader.substring(bearer.length());
                    // 讀取公鑰文件
                    byte[] keyBytes = Files.readAllBytes(Paths.get("/Volumes/Data/Code/Java/My_Project/food_order/food_ordering_rear_end_Mybaits/src/main/resources/RSA_SSL/public_key.pem"));
                    String publicKeyPEM = new String(keyBytes)
                            .replace("-----BEGIN PUBLIC KEY-----", "")
                            .replace("-----END PUBLIC KEY-----", "")
                            .replaceAll("\\s+", "");

                    // 解碼並生成公鑰
                    byte[] decodedKey = java.util.Base64.getDecoder().decode(publicKeyPEM);
                    //PKCS8EncodedKeySpec 是一種表示私鑰編碼格式的規範
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    //KeyFactory 是一個負責將密鑰（Key）編碼和解碼的工廠類。這裡通過 KeyFactory.getInstance("RSA") 獲取一個用於處理 RSA 演算法的 KeyFactory 實例。
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    //使用 KeyFactory 的 generatePrivate 方法，根據之前建立的 PKCS8EncodedKeySpec 生成一個 PrivateKey 對象。
                    PublicKey publicKey = keyFactory.generatePublic(keySpec);

                    // 使用 RS256 驗證 JWT
                    Claims claims = Jwts.parser()
                            .setSigningKey(publicKey)
                            .parseClaimsJws(token)
                            .getBody();

                    System.out.println("JWT payload:"+claims.toString());

                    chain.doFilter(req, res);

                }catch(Exception e){
                    System.err.println("Error : "+e);
                    res.setStatus(FORBIDDEN.value());

                    Map<String, String> err = new HashMap<>();
                    err.put("jwt_err", e.getMessage());
                    res.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(res.getOutputStream(), err);
                }
            }else{
                res.setStatus(UNAUTHORIZED.value());
            }
        }else{
            chain.doFilter(req, res);
        }
    }

}
