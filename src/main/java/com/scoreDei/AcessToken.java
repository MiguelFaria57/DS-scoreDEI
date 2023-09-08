package com.scoreDei;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public class AcessToken {

    byte[] key = Base64.getDecoder().decode("ccOkgNV66XjHggfoMYVEC6U3fZQIqeob4rO0FjN166A=");

    public boolean hasToken(HttpServletRequest request){
        Cookie[] tokens = request.getCookies();
        if(tokens == null)
            return false;

        for (Cookie token : tokens)
            if (token.getName().equals("token"))
                return true;

        return false;
    }

    public boolean getPermission(HttpServletRequest request){
        Cookie[] tokens = request.getCookies();
        if(tokens == null){
            return false;
        }
        String permission = "";
        for (Cookie token : tokens){
            if (token.getName().equals("token")){
                Jws<Claims> result = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(key))
                        .build()
                        .parseClaimsJws(token.getValue());
                permission =  result.getBody().getAudience();
                break;
            }
        }
        return permission.equals("admin");
    }

    public void getToken(HttpServletResponse response, String username, String permission){
        Instant atual = Instant.now();
        String jwt = Jwts.builder()
                .setSubject("" + username)
                .setAudience(permission)
                .setIssuedAt(Date.from(atual))
                .setExpiration(Date.from(atual.plus(2, ChronoUnit.DAYS)))
                .signWith(Keys.hmacShaKeyFor(key))
                .compact();

        Cookie token = new Cookie("token", jwt);
        response.addCookie(token);

    }
}
