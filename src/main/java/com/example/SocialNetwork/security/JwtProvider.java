package com.example.SocialNetwork.security;

import com.example.SocialNetwork.exception.SocialNetworkException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/socialnetwork.jks");
            keyStore.load(resourceAsStream, "password".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new SocialNetworkException("Exception occurred while loading keystore" + e);
        }

    }
    public String generateToken(Authentication authentication){
       User principal = (User)authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("socialnetwork", "password".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SocialNetworkException("Exception occured while retrieving public key from keystore" + e);
        }
    }

    public boolean validateToken(String jwt){
        parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("socialnetwork").getPublicKey();
        } catch (KeyStoreException e) {
            throw  new SocialNetworkException("Exception occured while retrieving public key " + e );
        }

    }

    public String getUsernameFromJwt(String token){
        Claims claims = parserBuilder().setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        System.out.println("Claims --- " + claims);
        return  claims.getSubject();
    }

}
