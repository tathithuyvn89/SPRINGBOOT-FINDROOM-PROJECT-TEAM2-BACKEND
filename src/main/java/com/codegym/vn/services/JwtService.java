package com.codegym.vn.services;

import com.codegym.vn.models.CustomerPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Component
public class JwtService {
    private static final String SECRET_KEY = "123456789";
    private static final long EXPIRE_TIME = 86400000000L;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class.getName());


    public String generateTokenLogin(Authentication authentication){
        CustomerPrincipal customerPrincipal = (CustomerPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(customerPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRE_TIME*1000))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();

    }

    public boolean validateJwtToken( String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }
        return false;
    }

    public String getUsernameFromJwtToken(String token){
        String username = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject();
        return username;
    }
}
