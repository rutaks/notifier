package rw.rutakayile.k8sproject.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rw.rutakayile.k8sproject.config.JwtConfigProperties;
import rw.rutakayile.k8sproject.model.Admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {
  private final long ONE_HOUR = 1000 * 60 * 60;
  @Autowired private JwtConfigProperties jwtConfigProperties;

  public String extractSubject(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Long extractUserId(String token) {
    return extractClaim(token, claims -> claims.get("user_id", Long.class));
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .setSigningKey(jwtConfigProperties.getKey())
        .parseClaimsJws(token)
        .getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public String generateToken(String email, Admin admin) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("firstName", admin.getFirstName());
    claims.put("lastName", admin.getLastName());
    claims.put("email", admin.getEmail());
    return createToken(claims, email);
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(
            new Date(System.currentTimeMillis() + (ONE_HOUR * jwtConfigProperties.getTime())))
        .signWith(SignatureAlgorithm.HS256, jwtConfigProperties.getKey())
        .compact();
  }

  private String createToken(Map<String, Object> claims, String subject, long expiryTime) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(
            new Date(System.currentTimeMillis() + (ONE_HOUR * expiryTime)))
        .signWith(SignatureAlgorithm.HS256, jwtConfigProperties.getKey())
        .compact();
  }

  public Boolean validateToken(String token, Admin admin) {
    final String email = extractSubject(token);
    boolean isUsernameCorrect = email.equals(admin.getEmail());
    return (isUsernameCorrect && !isTokenExpired(token));
  }
}
