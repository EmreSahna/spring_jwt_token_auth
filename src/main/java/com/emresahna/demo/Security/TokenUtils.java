package com.emresahna.demo.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private final static String accessToken = "256bit accessToken here";
    private final static Long expireTime = 2_592_000L * 1_000;

    public static String createToken(Long id){
        Date expirationDate = new Date(System.currentTimeMillis() + expireTime);
        Date issuedAtDate = new Date(System.currentTimeMillis());

        Map<String,Object> extra = new HashMap<>();
        extra.put("id",id);

        return Jwts.builder()
                .addClaims(extra)
                .setIssuedAt(issuedAtDate)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(accessToken.getBytes()),SignatureAlgorithm.HS256)
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
