package com.deu.football_love.config;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.auth.ValidRefreshTokenResponse;
import com.deu.football_love.service.redis.RedisService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public String createAccessToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() +  TOKEN_VALIDATION_SECOND)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();

        return accessToken;
    }

    public String createRefreshToken() {
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() +  REFRESH_TOKEN_VALIDATION_SECOND)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();

        return accessToken;
    }

    public Authentication getAuthentication(String token) {
        LoginInfo userDetails = ((LoginInfo)userDetailsService.loadUserByUsername(this.getUserPk(token)));
        log.info("loginInfo = {}", userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

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

    public String resolveToken(HttpServletRequest req, String headerName) {
        return req.getHeader(headerName);
    }

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