package com.deu.football_love.config;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.auth.ValidRefreshTokenResponse;
import com.deu.football_love.service.redis.RedisService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {
    @Value("spring.jwt.secret")
    private String secretKey;

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 60;

    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 120;

    final static public String ACCESS_TOKEN_NAME = "accessToken";

    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    private final UserDetailsService userDetailsService;

    private final RedisService redisService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public ValidRefreshTokenResponse validateRefreshToken(String accessToken, String refreshToken)
    {
        List<Object> findInfo = redisService.getListValue(refreshToken);
        String userPk = getUserPk(accessToken);
        if (findInfo.size() < 2) {
            return new ValidRefreshTokenResponse(null, 401, null);
        }
        if (userPk.equals(findInfo.get(0)) && validateToken(refreshToken))
        {
            UserDetails findMember = userDetailsService.loadUserByUsername((String)findInfo.get(0));
            List<String> roles = findMember.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());
            String newAccessToken = createAccessToken((String)findInfo.get(0), roles);
            return new ValidRefreshTokenResponse((String)findInfo.get(0), 200, newAccessToken);
        }
        return new ValidRefreshTokenResponse(null, 403, null);
    }

    // Jwt 토큰 생성
    public String createAccessToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() +  TOKEN_VALIDATION_SECOND)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();

        return accessToken;
    }

    public String createRefreshToken() {
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() +  REFRESH_TOKEN_VALIDATION_SECOND)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();

        return accessToken;
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token) {
        LoginInfo userDetails = ((LoginInfo)userDetailsService.loadUserByUsername(this.getUserPk(token)));
        log.info("loginInfo = {}", userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserPk(String token) {
        try
        {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        }
        catch (ExpiredJwtException e)
        {
            //e.printStackTrace();
            return "Expired";
        }
        catch (JwtException e)
        {
            //e.printStackTrace();
            return "Invalid";
        }
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName)
    {
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }

    // Request의 Header에서 token 파싱
    public String resolveToken(HttpServletRequest req, String headerName) {
        return req.getHeader(headerName);
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
    }
    public boolean checkLogout(String token)
    {
        if(token == null || redisService.getStringValue(token) == null) {
            return false;
        }
        log.info("redis will be expired at = {}", redisService.getExpirationTime(token));
        return true;
    }
    public Long remainExpiration(String token)
    {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration().getTime() - new Date().getTime();
        }
        catch (ExpiredJwtException e) {
            return -1L;
        }
    }

    public Boolean isLoggedOut(String accessToken)
    {
        if (accessToken == null)
            return false;
        return redisService.getStringValue(accessToken) != null;
    }
}