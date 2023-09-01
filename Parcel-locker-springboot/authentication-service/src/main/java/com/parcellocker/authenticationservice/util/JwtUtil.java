package com.parcellocker.authenticationservice.util;

import com.parcellocker.authenticationservice.exception.JwtTokenMalformedException;
import com.parcellocker.authenticationservice.exception.JwtTokenMissingException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class JwtUtil {

    //Jwt token titkosítása. A titkosítás az applicaton.properties-ben található
    @Value("${jwt.secret}")
    private String jwtSecret;

    //Mennyi ideig érvényes a token. Ez is az applicaton.properties-ben található
    @Value("${jwt.token.validity}")
    private long tokenValidity;

    //Jwt token body részének lekérése
    //A body rész tartalmazza a subject mezőt és a claim mezőket, amiből több is lehet
    public Claims getClaims(final String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    //Token generálása
    //Email cím elhelyezése a token subject mezőjében
    //Szerepkörök elhelyezése a token body részében, azon belül a claim részben
    public String generateToken(String emailAddress, List<String> roles) {
        //Felhasználó email cím átadása a jwt tokennek. Ez a subject
        //Jwt token body része
        Claims claims = Jwts.claims().setSubject(emailAddress);
        //Felhasználó szerepköreinek átadása a jwt tokennek
        //Jwt token body része
        //Bármennyi objektumot, változót át lehet neki adni a 'claim' részben
        claims.put("roles", roles);

        //Mostani idő
        long nowMillis = System.currentTimeMillis();
        //Lejárati idő
        long expMillis = nowMillis + tokenValidity;
        Date exp = new Date(expMillis);
        //Token összeállítása
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    //Token validálása
    public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }
}
