package org.example.airport.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {
    // 生成 JWT 密钥的方法
    private static String generateJwtSecret() {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }
    // 生成 JWT 密钥（这里使用随机生成的密钥，实际应用中应使用更安全的方式存储密钥）
    private static final String jwtSecret = generateJwtSecret();
    // 长令牌过期时间（以毫秒为单位，这里设置为 1 天）
    private static final long longTokenExpiration = 24 * 60 * 60 * 1000;
    // 生成长令牌，包含用户名
    public String generateLongToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("exp", new Date(System.currentTimeMillis() + longTokenExpiration));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    // 验证长令牌
    public static boolean validateLongToken(String token, String username) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = claims.getExpiration();
            String storedUsername = (String) claims.get("username");

            return expiration.after(new Date()) && storedUsername.equals(username);
        } catch (Exception e) {
            return false;
        }
    }
    // 从长令牌中获取用户名
    public static String getUsernameFromLongToken(String longToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(longToken)
                    .getBody();
            return claims.get("username", String.class);
        } catch (Exception e) {
            return null;
        }
    }
}
