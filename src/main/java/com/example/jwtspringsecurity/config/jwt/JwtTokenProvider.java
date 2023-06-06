package com.example.jwtspringsecurity.config.jwt;

import com.example.jwtspringsecurity.config.token.Token;
import com.example.jwtspringsecurity.config.token.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Autowired
    TokenRepository tokenRepository;
    public String generateToken(Authentication auth) {
        // Generate a JWT token for the given user details
        String username = auth.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + 86400000);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();;
        return token;
    }

    public boolean validateToken(String token) {
        // Validate the JWT token's signature and expiration
        Token validToken = tokenRepository.findByToken(token);
        if(!validToken.isExpired()) {
            try {
                Jwts.parser().setSigningKey("secret").parseClaimsJws(token);
                return true;
            } catch (Exception ex) {
                throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
            }
        }else {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        // Extract the username from the JWT token
        Claims claims = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}