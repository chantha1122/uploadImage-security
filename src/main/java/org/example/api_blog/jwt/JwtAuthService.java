package org.example.api_blog.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.api_blog.model.entity.Auth;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtAuthService {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; //5 hour
    public static final String SECRET = "h3b6mv05FxOfWOAKhhZe1ymkjdXVOpUCMi/7t/kpZbk=";
//using website to generate key secret https://www.devglan.com/online-tools/hmac-sha256-online
//Base64 encodes binary data into a set of 64 ASCII characters:
//Format	Base	Characters Used
//Binary	Base 2	0, 1
//Hex	Base 16	0–9, A–F
//Base64	Base 64	A–Z, a–z, 0–9, +, /, =

    private String createToken(Map<String, Object> claim, String subject){
        return Jwts.builder()
                .setClaims(claim)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //2. generate token for user
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof Auth auth) {
            claims.put("userId", auth.getUserId());
            claims.put("version", auth.getTokenVersion());
            claims.put("userName", auth.getUsername());

            return createToken(claims, auth.getEmail());
        }

        return createToken(claims, userDetails.getUsername());
    }

    //3. retrieving any information from token we will need the secret key
    private Claims extractAllClaim(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Integer extractVersion(String token) {
        return extractClaim(token, claims -> claims.get("version", Integer.class));
    }

    //4. extract a specific claim from the JWT token’s claims.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaim(token);
        return claimsResolver.apply(claims);
    }

    //5. retrieve username from jwt token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //6. retrieve expiration date from jwt token
    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //7. check expired token
    private Boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }
    //8. validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        System.out.println("JWT Subject: " + username);
        System.out.println("UserDetails Username: " + userDetails.getUsername());

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
