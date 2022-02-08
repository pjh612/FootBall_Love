package com.deu.football_love.config;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = null;
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        if (cookies != null && jwtTokenProvider.getCookie((HttpServletRequest) request, JwtTokenProvider.ACCESS_TOKEN_NAME) != null)
            accessToken = jwtTokenProvider.getCookie((HttpServletRequest) request, JwtTokenProvider.ACCESS_TOKEN_NAME).getValue();
        if (!jwtTokenProvider.isLoggedOut(accessToken)) {
            log.info("로그아웃안됨");
            try {
                if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
                    log.info("유효한 토큰");
                    Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
                else
                {
                    log.info("만료된 토큰");
                }
            } catch (ExpiredJwtException e) {
                log.info("만료 exception!");
            }
        }
        else {
            SecurityContextHolder.getContext().setAuthentication(null);
            log.info("로그아웃 됨");
        }
        filterChain.doFilter(request, response);
    }
}