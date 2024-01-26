package com.platzi.market.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {
    private static final String KEY ="XvenusD123";

    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 100 *60 * 68 * 10))
                .signWith(SignatureAlgorithm.HS256, KEY).compact();
    }
    public boolean validateToken(String token, UserDetails userDetails){
        return userDetails.getUsername().equals(extractUsername(token)) && !siTokenExpired(token);

    }
    public  String extractUsername (String token) {
        return getClains(token).getSubject();

    }
    public boolean siTokenExpired (String token){
        return getClains(token).getExpiration().before(new Date());

    }

    private Claims getClains(String token){
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }


}

