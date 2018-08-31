package com.matus.cursomc.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

// Para que possa ser injetada em outras classes como componente
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public  String generateToken(String username){
        // Jwts.builder() ==> Gera o token
        // setSubject ==> Usuario
        // signWith ==> Como que o token vai ser assinado. Para dizer isso tem q falar qual o algoritomo de assinatura
//        vai ser usado e qual é o segredo
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }
}
