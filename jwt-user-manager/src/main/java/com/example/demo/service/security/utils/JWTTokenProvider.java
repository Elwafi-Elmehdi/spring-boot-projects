package com.example.demo.service.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.bean.UserPrinciple;
import com.example.demo.service.security.SecurityConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class JWTTokenProvider {

    @Value("${jwt.secret}")
    private String signation;

    public String generateJWTToken(UserPrinciple userPrinciple){
        String[] claims = getClaimsFromUser(userPrinciple);
        return JWT.create()
                .withIssuer(SecurityConsts.COMPANY_LLC)
                .withAudience(SecurityConsts.COMPANY_LLC_ADMINISTRATON)
                .withIssuedAt(new Date())
                .withSubject(userPrinciple.getUsername())
                .withArrayClaim(SecurityConsts.AUTHORITIES,claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConsts.EXPIRATION_TIME ))
                .sign(Algorithm.HMAC512(signation.getBytes()));
    }

    public List<GrantedAuthority> getAuthorities(String token){
        String[] claims = getClaimsFromToken(token);
        return Arrays.stream(claims)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Authentication getAuthentication(String username,
                                            List<GrantedAuthority> authorities,
                                            HttpServletRequest request){

        UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new
                UsernamePasswordAuthenticationToken(username,null,authorities);
        usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return usernamePasswordAuthToken;
    }

    public boolean isTokenValid(String username,String token){
        JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotEmpty(username) && isTokenExpired(verifier,token);
    }

    public String getSubject(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date experation = verifier.verify(token).getExpiresAt();
        return experation.before(new Date());
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier
                .verify(token)
                .getClaim(SecurityConsts.AUTHORITIES)
                .asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(signation);
            verifier = JWT.require(algorithm)
                    .withIssuer(SecurityConsts.COMPANY_LLC)
                    .build();
        }catch (JWTVerificationException exception) {
            throw new JWTVerificationException(SecurityConsts.TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }


    private String[] getClaimsFromUser(UserPrinciple userPrinciple) {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : userPrinciple.getAuthorities()){
             authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }
}
