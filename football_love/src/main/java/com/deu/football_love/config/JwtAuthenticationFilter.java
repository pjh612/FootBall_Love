package com.deu.football_love.config;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private JwtTokenProvider jwtTokenProvider;

    // Jwt Provider 주입
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를 filterChain에 등록
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = null;
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        if (cookies != null)
            accessToken = jwtTokenProvider.getCookie((HttpServletRequest) request, JwtTokenProvider.ACCESS_TOKEN_NAME).getValue();
        if (!jwtTokenProvider.isLoggedOut(accessToken)) {
            try {
                if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
                    Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (ExpiredJwtException e) {
                //재발급
            }
        }
        filterChain.doFilter(request, response);
    }
}