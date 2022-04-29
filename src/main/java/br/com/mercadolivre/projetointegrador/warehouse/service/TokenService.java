package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

  @Value("${jwt.expiration}")
  private String expiration;

  @Value("${jwt.secret}")
  private String secret;

  public String generateToken(Authentication authentication) {
    AppUser user = (AppUser) authentication.getPrincipal();

    Date now = new Date();
    Date exp = new Date(now.getTime() + Long.parseLong(expiration));

    return JWT.create()
        .withSubject(user.getId().toString())
        .withExpiresAt(exp)
        .sign(Algorithm.HMAC512(secret));
  }

  public String isTokenValid(String token) {
    try {
      return JWT.require(Algorithm.HMAC512(secret)).build().verify(token).getSubject();
    } catch (JWTVerificationException ex) {
      return null;
    }
  }
}
