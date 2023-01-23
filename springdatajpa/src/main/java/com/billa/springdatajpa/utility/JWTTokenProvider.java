package com.billa.springdatajpa.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.billa.springdatajpa.authentication.UserPrinciple;
import com.billa.springdatajpa.security.SecurityConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.billa.springdatajpa.security.SecurityConfig.TOKEN_CANT_BE_VERIFIED;
import static java.util.Arrays.stream;

@Component
public class JWTTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String generateJWTToken(UserPrinciple userPrinciple){
        String[] claims = getClaimsForUser(userPrinciple);
        return JWT.create().withIssuer(SecurityConfig.GET_ARRAYS_LLC).withAudience(SecurityConfig.GET_ARRAYS_LLC_ADMIN)
                .withIssuedAt(new Date()).withSubject(userPrinciple.getUsername())
                .withArrayClaim(SecurityConfig.AUTHORITIES,claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConfig.expirationTime))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        return stream(getClaimsFromToken(token)).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(SecurityConfig.AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(SecurityConfig.GET_ARRAYS_LLC).build();
        } catch (JWTVerificationException jwtVerificationException) {
            throw new JWTVerificationException(TOKEN_CANT_BE_VERIFIED);
        }
        return verifier;
    }

    private String[] getClaimsForUser(UserPrinciple userPrinciple) {
        List<String> authorities = new ArrayList<>();
        userPrinciple.getAuthorities().stream().forEach(aut ->{
            authorities.add(aut.getAuthority());
        });
        return authorities.toArray(new String[0]);
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }
    public boolean isTokenValid(String username, String token) {
        JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotBlank(username) && isTokenExpired(verifier, token);
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiratationDate = verifier.verify(token).getExpiresAt();

        return expiratationDate.before(new Date());
    }

    public String getSubject(String token) {
        return getJWTVerifier().verify(token).getSubject();
    }
}
