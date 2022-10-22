package com.emresahna.demo.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private final static String accessToken = "256bit accessToken here";
    private final static Long expireTime = 2_592_000L * 1_000;

    public static String createToken(String username, String password){
        Date expirationDate = new Date(System.currentTimeMillis() + expireTime);

        Map<String,Object> extra = new HashMap<>();
        extra.put("username",username);

        return Jwts.builder()
                .setSubject(password)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(accessToken.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(accessToken.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String password = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(password, null, Collections.emptyList());
    }
}
