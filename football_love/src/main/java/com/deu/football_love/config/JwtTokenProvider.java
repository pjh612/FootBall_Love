package com.deu.football_love.config;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.deu.football_love.dto.LoginMemberResponse;
import com.deu.football_love.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("spring.jwt.secret")
    private String secretKey;

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 10;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    private final UserDetailsService userDetailsService;
    private final RedisService redisService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public  ValidRefreshTokenResponse validRefreshToken(String accessToken, String refreshToken)
    {
        List<Object> findInfo = redisService.getListValue(refreshToken);

        if (findInfo.size() < 2) {
            return new ValidRefreshTokenResponse(null, 401, null);
        }
        Authentication authentication = getAuthentication(accessToken);
        ArrayList<String> roles = ((ArrayList<SimpleGrantedAuthority>) authentication.getAuthorities()).stream().map(authority -> authority.getAuthority().toString()).collect(Collectors.toList());

        if (!authentication.getName().equals(findInfo.get(0))) {
            return new ValidRefreshTokenResponse(null, 403, null);
        }
        if (!validateToken(accessToken)) { //accessToken 재발행
            createToken(getUserPk(accessToken), roles, true);
        }
        else // refreshToken 재발행 불가
        {

        }
    }

    // Jwt 토큰 생성
    public String createToken(String userPk, List<String> roles, boolean accessOrRefresh) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + (accessOrRefresh ? TOKEN_VALIDATION_SECOND : REFRESH_TOKEN_VALIDATION_SECOND))) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token) {
        LoginMemberResponse userDetails = ((LoginMemberResponse)userDetailsService.loadUserByUsername(this.getUserPk(token)));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 파싱
    public String resolveToken(HttpServletRequest req, String headerName) {
        return req.getHeader(headerName);
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}