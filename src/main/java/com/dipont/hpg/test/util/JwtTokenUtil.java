package com.dipont.hpg.test.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author alex
 * @date 2019/8/23  9:16
 */
@Component
@Slf4j
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String createToken(String userName){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims,userName);
    }

    public Boolean validateToken(String token){
        final String username = getUsernameFromToken(token);
        return !StringUtils.isEmpty(username)&& !isTokenExpired(token);
    }



    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    @Nullable
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        if(claims==null) {
            return null;
        }
        return claimsResolver.apply(claims);
    }
    @Nullable
    private Claims getAllClaimsFromToken(String token) {
        try{
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (MalformedJwtException ex){
            ex.printStackTrace();
            return null;
        }

    }
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

}