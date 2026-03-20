package org.example.mediqback.user.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    // application.yml 에 있는 설정값을 가져옵니다.
    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.expire}")
    private int expire;

    // 비밀키를 암호화 알고리즘에 맞게 변환
    public SecretKey getEncodedKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    // 로그인 성공 시 JWT 토큰 생성
    public String createToken(Long idx, String email, String name, String role) {
        return Jwts.builder()
                .claim("idx", idx)
                .claim("email", email)
                .claim("name", name)
                .claim("role", role)
                .issuedAt(new Date()) // 발행일
                .expiration(new Date(System.currentTimeMillis() + expire)) // 만료일
                .signWith(getEncodedKey()) // 비밀키로 서명
                .compact();
    }

    // 토큰에서 회원 번호(idx) 꺼내기
    public Long getUserIdx(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getEncodedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("idx", Long.class);
    }

    // 토큰에서 이메일(username) 꺼내기
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getEncodedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("email", String.class);
    }

    // 토큰에서 권한(role) 꺼내기
    public String getRole(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getEncodedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("role", String.class);
    }

    // 새로 추가된 메서드: 토큰에서 이름(name) 꺼내기
    public String getName(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getEncodedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("name", String.class);
    }
}