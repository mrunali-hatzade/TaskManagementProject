package com.taskmanagement.taskmanagementproject.Security;

import com.taskmanagement.taskmanagementproject.Entity.UserAuthentication;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component

public class JWTTokenUtil {
    private static final String jwtSecret = "JWTTOKEN";
    private static final long expireJWT = 8400000;
    public static String generateToken(UserAuthentication userOfficialEmail) {
        //List<String> authorities=roles.stream().map(role ->"ROLE"+role.name()).collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(String.valueOf(userOfficialEmail))
                //.claim("roles",authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireJWT))
                .signWith(SignatureAlgorithm.ES512, jwtSecret)
                .compact();
    }

    public String extractusername(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();

    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException e){
            return false;

        }
    }
}
